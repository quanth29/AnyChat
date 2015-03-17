package com.xwh.anychat.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.xwh.anychat.BaseActivity;
import com.xwh.anychat.R;
import com.xwh.anychat.biz.AccountBiz;
import com.xwh.anychat.biz.impl.AccountBizImpl;
import com.xwh.anychat.config.Constants;
import com.xwh.anychat.dao.AccountInfoDao;
import com.xwh.anychat.dao.impl.AccountInfoDaoImpl;
import com.xwh.anychat.db.AnyChatDatabaseHelper;
import com.xwh.anychat.entity.AccountInfoEntity;
import com.xwh.anychat.util.DebugUtil;
import com.xwh.anychat.util.PrefUtil;

import java.util.ArrayList;

public class LoginFragment extends Fragment implements OnClickListener, OnCheckedChangeListener, TextWatcher {

	private EditText usernameEt, passwordEt;
	private CheckBox rememberCb, autoLoginCb;
	private Button loginBtn, newUserBtn;
	private TextView serverConfigTv;

	private onLoginFragmentOperation operation;

	private AccountInfoDao accountInfoDao;

	private AnyChatDatabaseHelper dbHelper;

	private ArrayList<AccountInfoEntity> accountInfos;

	private AccountBiz accountBiz;

	private ServerSetDialogFragment serverSetDialogFragment;
	private RegFragment regFragment;
	private MainFragment mainFragment;

	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;

	private JumpToMainHandler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		serverSetDialogFragment = new ServerSetDialogFragment();
		regFragment = new RegFragment();
		mainFragment = new MainFragment();
		fragmentManager = getActivity().getSupportFragmentManager();
		accountBiz = new AccountBizImpl();
		handler = new JumpToMainHandler();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			operation = (onLoginFragmentOperation) activity;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layoutView = inflater.inflate(R.layout.fragment_login, container, false);
		operation.needHideActionBar();
		usernameEt = (EditText) layoutView.findViewById(R.id.login_username_et);
		passwordEt = (EditText) layoutView.findViewById(R.id.login_password_et);
		rememberCb = (CheckBox) layoutView.findViewById(R.id.login_remember_cb);
		autoLoginCb = (CheckBox) layoutView.findViewById(R.id.login_auto_login_cb);
		loginBtn = (Button) layoutView.findViewById(R.id.login_login_btn);
		loginBtn.setOnClickListener(this);
		newUserBtn = (Button) layoutView.findViewById(R.id.login_newuser_btn);
		serverConfigTv = (TextView) layoutView.findViewById(R.id.login_server_config_tv);
		serverConfigTv.setOnClickListener(this);
		newUserBtn.setOnClickListener(this);
		autoLoginCb.setOnCheckedChangeListener(this);
		rememberCb.setOnCheckedChangeListener(this);
		usernameEt.addTextChangedListener(this);
		return layoutView;
	}

	@Override
	public void onResume() {
		super.onResume();
		accountInfos = getAllStoredAccountInfo();
		usernameEt.setText(PrefUtil.getUsername(getActivity()));
		if (dbHelper == null) {
			dbHelper = AnyChatDatabaseHelper.getInstance(getActivity());
		}
		passwordEt.setText(dbHelper.getPasswordByUsername(usernameEt.getText().toString()));
		if (PrefUtil.getAutoLogin(getActivity())) {
			startLogin(usernameEt.getText().toString(), passwordEt.getText().toString());
			autoLoginCb.setChecked(true);
			rememberCb.setChecked(true);
		} else {
			if (passwordEt.getText().toString() != null && !passwordEt.getText().toString().equals("")) {
				rememberCb.setChecked(true);
			}
			autoLoginCb.setChecked(false);
		}
	}

	// 开始登陆流程
	private void startLogin(String username, String password) {
		BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_COMMON_PROGRESS_DIALOG_SHOW);
		BaseActivity.username = username;
		BaseActivity.password = password;
		if (accountBiz == null) {
			accountBiz = new AccountBizImpl();
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				if (accountBiz.loginAccount(BaseActivity.username, BaseActivity.password, getActivity())) {
					saveAccountInfo();
					BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_LOGIN_OK);
					handler.sendEmptyMessage(Constants.LOGIN_JUMP_TO_MAIN);
					BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_COMMON_PROGRESS_DIALOG_DISMISS);
				} else {
					BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_LOGIN_ERR);
					BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_COMMON_PROGRESS_DIALOG_DISMISS);
				}
			}
		}).start();
	}

	@SuppressLint("HandlerLeak")
	class JumpToMainHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constants.LOGIN_JUMP_TO_MAIN:
				DebugUtil.Log("Jump to MainFragment");
				if (fragmentTransaction != null) {
					fragmentTransaction = null;
				}
				fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.anychat_fragment_container, mainFragment);
				fragmentTransaction.commit();
				fragmentManager.executePendingTransactions();
				break;

			default:
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_login_btn:
			startLogin(usernameEt.getText().toString(), passwordEt.getText().toString());
			break;

		case R.id.login_newuser_btn:
			DebugUtil.Log("Jump to RegFragment");
			if (fragmentTransaction != null) {
				fragmentTransaction = null;
			}
			fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.anychat_fragment_container, regFragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
			fragmentManager.executePendingTransactions();
			break;
		case R.id.login_server_config_tv:
			if (serverSetDialogFragment == null) {
				serverSetDialogFragment = new ServerSetDialogFragment();
			}
			serverSetDialogFragment.show(fragmentManager, "ServerSet");
			break;
		default:
			break;
		}
	}

	// 获取所有已保存的账户信息
	private ArrayList<AccountInfoEntity> getAllStoredAccountInfo() {
		if (accountInfoDao == null) {
			accountInfoDao = new AccountInfoDaoImpl();
		}
		return accountInfoDao.getAllAccountInfo(getActivity());
	}

	/**
	 * 判断用户是否需要储存信息并清除已经输入的内容（仅当登录成功后才会被调用）
	 */
	public void saveAccountInfo() {
		// 自动登录
		if (autoLoginCb.isChecked()) {
			rememberCb.setChecked(true);
			PrefUtil.storeAutoLogin(getActivity(), true);
			PrefUtil.storeUsername(getActivity(), usernameEt.getText().toString());
		} else {
			PrefUtil.storeAutoLogin(getActivity(), false);
			PrefUtil.storeUsername(getActivity(), usernameEt.getText().toString());
		}
		// 记住密码（若设置了自动登录，则自动选择记住密码）
		if (rememberCb.isChecked()) {
			if (accountInfoDao == null) {
				accountInfoDao = new AccountInfoDaoImpl();
			}
			AccountInfoEntity accountInfo = new AccountInfoEntity();
			accountInfo.setUsername(usernameEt.getText().toString());
			accountInfo.setPassword(passwordEt.getText().toString());
			accountInfoDao.addOrUpdateAccountInfo(getActivity(), accountInfo, true);
		} else {
			if (accountInfoDao == null) {
				accountInfoDao = new AccountInfoDaoImpl();
			}
			AccountInfoEntity accountInfo = new AccountInfoEntity();
			accountInfo.setUsername(usernameEt.getText().toString());
			accountInfo.setPassword("");
			accountInfoDao.addOrUpdateAccountInfo(getActivity(), accountInfo, true);
		}
		// 创建所有用户表
		accountInfoDao.prepareAccountDbData(getActivity(), BaseActivity.username);
	}

	/**
	 * 设置用户名及密码输入框的值
	 * 
	 * @param username
	 * @param password
	 */
	public void setEdittext(String username, String password) {
		usernameEt.setText(username);
		passwordEt.setText(password);
	}

	@Override
	public void onPause() {
		super.onPause();
		usernameEt.setText("");
		passwordEt.setText("");
	}

	public interface onLoginFragmentOperation {

		public void needHideActionBar();

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.login_auto_login_cb:
			if (isChecked) {
				rememberCb.setChecked(true);
			}
			break;
		case R.id.login_remember_cb:
			if (!isChecked) {
				autoLoginCb.setChecked(false);
			}
			break;
		default:
			break;
		}

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	public void afterTextChanged(Editable s) {
		// 用户名发生改变时，需要将密码输入框清空
		passwordEt.setText("");
		rememberCb.setChecked(false);
		autoLoginCb.setChecked(false);
		// 用户名发生改变时，需要检索是否存在已保存的账户，同时，如果已记住密码，则自动设置密码输入框的值为已保存的密码的值
		if (accountInfos != null && accountInfos.size() > 0) {
			for (int i = 0; i < accountInfos.size(); i++) {
				if (usernameEt.getText().toString().equalsIgnoreCase(accountInfos.get(i).getUsername())) {
					if (accountInfos.get(i).getPassword() != null && !accountInfos.get(i).getPassword().equals("")) {
						rememberCb.setChecked(true);
						passwordEt.setText(accountInfos.get(i).getPassword());
						return;
					}
				}
			}
		}
	}

}
