package com.xwh.anychat.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.xwh.anychat.BaseActivity;
import com.xwh.anychat.R;
import com.xwh.anychat.SharedObj;
import com.xwh.anychat.biz.AccountBiz;
import com.xwh.anychat.biz.impl.AccountBizImpl;
import com.xwh.anychat.config.Constants;
import com.xwh.anychat.entity.AccountInfoEntity;
import com.xwh.anychat.service.ConnectionService;
import com.xwh.anychat.util.DebugUtil;
import com.xwh.anychat.util.ServerConnectionUtil;
import com.xwh.anychat.view.SlidingTabLayout;

import org.jivesoftware.smack.XMPPConnection;

import java.util.ArrayList;

public class MainFragment extends Fragment implements OnClickListener {

	private onMainFragmentOperation operation;

	private Toolbar toolbar;
	private SlidingTabLayout slidingTabLayout;
	private ViewPager viewPager;

	private ArrayList<Fragment> fragments;

	private RecentFragment recentFragment;
	private FriendsFragment friendsFragment;
	private MoreOprFragment moreOprFragment;

	private ViewpagerAdapter viewpagerAdapter;

	private XMPPConnection xmppConnection;

	private AccountBiz accountBiz;

	private String username;

	private FragmentTransaction fragmentTransaction;
	private FragmentManager fragmentManager;

	private MyProfileFragment myProfileFragment;
	private LoginFragment loginFragment;

	private AccountInfoEntity accountInfoEntity;

	public static UIHandler handler;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			operation = (onMainFragmentOperation) activity;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		recentFragment = new RecentFragment();
		friendsFragment = new FriendsFragment();
		moreOprFragment = new MoreOprFragment();
		myProfileFragment = new MyProfileFragment();
		loginFragment = new LoginFragment();
		fragments = new ArrayList<Fragment>();
		fragments.add(recentFragment);
		fragments.add(friendsFragment);
		fragments.add(moreOprFragment);
		viewpagerAdapter = new ViewpagerAdapter(getChildFragmentManager());
		fragmentManager = getActivity().getSupportFragmentManager();
		handler = new UIHandler();
		initData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layoutView = inflater.inflate(R.layout.fragment_main, container, false);
		toolbar = (Toolbar) layoutView.findViewById(R.id.mainfragment_tb);
		toolbar.setOnClickListener(this);
		slidingTabLayout = (SlidingTabLayout) layoutView.findViewById(R.id.mainfragment_stl);
		viewPager = (ViewPager) layoutView.findViewById(R.id.mainfragment_vp);
		initFragments();
		return layoutView;
	}

	// 初始化数据
	private void initData() {
		DebugUtil.Log("Refresh userinfo at MainFragment");
		// 检查是否已经连接并登陆
		if (!checkIfLoggedIn()) {
			operation.needHideActionBar();
			DebugUtil.Log("Jump to LoginFragment");
			if (fragmentTransaction != null) {
				fragmentTransaction = null;
			}
			fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.anychat_fragment_container, loginFragment);
			fragmentTransaction.commit();
			fragmentManager.executePendingTransactions();
			return;
		}
		xmppConnection = ServerConnectionUtil.getConnection();
		// 通知服务获取用户信息
		ConnectionService.handler.sendEmptyMessage(Constants.SERVICE_LOAD_VCARD_START);
	}

	private boolean checkIfLoggedIn() {
		return ServerConnectionUtil.getConnection() != null && ServerConnectionUtil.getConnection().isConnected() && ServerConnectionUtil.getConnection().isAuthenticated();
	}

	// 初始化Fragment
	private void initFragments() {
		viewPager.setOffscreenPageLimit(fragments.size());
		viewPager.setAdapter(viewpagerAdapter);
		slidingTabLayout.setViewPager(viewPager, BaseActivity.screenX);
		if (accountBiz == null) {
			accountBiz = new AccountBizImpl();
		}
		// 通过本地保存的数据获取用户信息
		accountInfoEntity = accountBiz.getMyAccountInfo(getActivity(), BaseActivity.username);
		if (accountInfoEntity.getNickName() == null || accountInfoEntity.getNickName().equals("")) {
			toolbar.setTitle(BaseActivity.username);
		} else {
			toolbar.setTitle(accountInfoEntity.getNickName());
		}
		operation.needShowActionBar();
		operation.setActionBar(toolbar);
	}

	@SuppressLint("HandlerLeak")
	public class UIHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constants.SERVICE_LOAD_VCARD_FINISH:

				if (SharedObj.myVcard.getNickName() == null || SharedObj.myVcard.getNickName().equals("")) {
					username = xmppConnection.getUser();
				} else {
					username = SharedObj.myVcard.getNickName();
				}
				toolbar.setTitle(username);
				operation.needShowActionBar();
				operation.setActionBar(toolbar);
				break;

			default:
				break;
			}
		}
	}

	public interface onMainFragmentOperation {

		public void needShowActionBar();

		public void needHideActionBar();

		public void setActionBar(Toolbar toolbar);

	}

	// ViewPager适配器
	class ViewpagerAdapter extends FragmentPagerAdapter {

		public ViewpagerAdapter(FragmentManager fm) {
			super(fm);

		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:

				return getResources().getString(R.string.recent_title);
			case 1:

				return getResources().getString(R.string.friends_title);
			case 2:

				return getResources().getString(R.string.moreopr_title);
			default:
				break;
			}

			return super.getPageTitle(position);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mainfragment_tb:
			DebugUtil.Log("Jump to MyProfileFragment");
			if (fragmentTransaction != null) {
				fragmentTransaction = null;
			}
			fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.anychat_fragment_container, myProfileFragment);
			Bundle bundle = new Bundle();
			bundle.putSerializable(Constants.MY_PROFILE_USER_DATA, new AccountInfoEntity(SharedObj.myVcard, username));
			myProfileFragment.setArguments(bundle);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
			fragmentManager.executePendingTransactions();
			break;

		default:
			break;
		}
	}

}
