package com.xwh.anychat.fragment;

import com.xwh.anychat.R;
import com.xwh.anychat.config.Constants;
import com.xwh.anychat.service.ConnectionService;
import com.xwh.anychat.util.DebugUtil;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FriendsFragment extends Fragment implements OnClickListener {

	private EditText searchFriEt;
	private Button addFriBtn;

	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;

	private SearchFriendFragment searchFriendFragment;

	public static UIHandler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new UIHandler();
		reloadRosterData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layoutView = inflater.inflate(R.layout.fragment_friends, container, false);
		searchFriEt = (EditText) layoutView.findViewById(R.id.friends_fragment_searchEt);
		addFriBtn = (Button) layoutView.findViewById(R.id.friends_fragment_addnewBtn);
		addFriBtn.setOnClickListener(this);
		fragmentManager = getActivity().getSupportFragmentManager();
		searchFriendFragment = new SearchFriendFragment();
		return layoutView;
	}

	@Override
	public void onResume() {
		super.onResume();
		refreshRosterData();
	}

	// 本地刷新好友列表信息
	private void refreshRosterData() {

	}

	// 后台服务刷新好友列表信息
	private void reloadRosterData() {
		ConnectionService.handler.sendEmptyMessage(Constants.SERVICE_LOAD_MY_ROSTER_START);
	}

	@SuppressLint("HandlerLeak")
	public class UIHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constants.SERVICE_LOAD_MY_ROSTER_FINISH:

				break;

			default:
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 添加好友按钮
		case R.id.friends_fragment_addnewBtn:
			DebugUtil.Log("Jump to SearchFriendFragment");
			fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction = fragmentTransaction.replace(R.id.anychat_fragment_container, searchFriendFragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
			fragmentManager.executePendingTransactions();
			break;

		default:
			break;
		}
	}
}
