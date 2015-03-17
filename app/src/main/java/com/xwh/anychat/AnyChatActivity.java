/**
 * 本类主要进行Fragment管理事务及相关操作
 */

package com.xwh.anychat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.xwh.anychat.fragment.LoginFragment;
import com.xwh.anychat.fragment.LoginFragment.onLoginFragmentOperation;
import com.xwh.anychat.fragment.MainFragment;
import com.xwh.anychat.fragment.MainFragment.onMainFragmentOperation;
import com.xwh.anychat.fragment.RegFragment.onRegFragmentOperation;
import com.xwh.anychat.util.DebugUtil;
import com.xwh.anychat.util.ServerConnectionUtil;

public class AnyChatActivity extends BaseActivity implements onLoginFragmentOperation, onMainFragmentOperation, onRegFragmentOperation {

	private Fragment loginFragment, mainFragment;

	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_any_chat);
		initFragment();
	}

	// 初始化Fragment
	private void initFragment() {
		fragmentManager = getSupportFragmentManager();
		loginFragment = new LoginFragment();
		mainFragment = new MainFragment();
		DebugUtil.Log("Jump to LoginFragment");
		if (fragmentTransaction != null) {
			fragmentTransaction = null;
		}
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.anychat_fragment_container, loginFragment);
		fragmentTransaction.commit();
		fragmentManager.executePendingTransactions();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (ServerConnectionUtil.getConnection() != null && ServerConnectionUtil.getConnection().isConnected() && ServerConnectionUtil.getConnection().isAuthenticated()) {
			if (mainFragment.isDetached()) {
				DebugUtil.Log("Jump to MainFragment");
				if (fragmentTransaction != null) {
					fragmentTransaction = null;
				}
				fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.anychat_fragment_container, mainFragment);
				fragmentTransaction.commit();
				fragmentManager.executePendingTransactions();
			}
		}
	}

	// 隐藏ActionBar
	@Override
	public void needHideActionBar() {
		if (getSupportActionBar() != null) {
			getSupportActionBar().hide();
		}
	}

	// 显示ActionBar
	@Override
	public void needShowActionBar() {
		if (getSupportActionBar() != null) {
			getSupportActionBar().show();
		}
	}

	// 设置ActionBar
	@Override
	public void setActionBar(Toolbar toolbar) {
		setSupportActionBar(toolbar);
	}

}