package com.xwh.anychat.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xwh.anychat.BaseActivity;
import com.xwh.anychat.R;
import com.xwh.anychat.biz.AccountBiz;
import com.xwh.anychat.biz.impl.AccountBizImpl;
import com.xwh.anychat.config.Constants;

public class RegFragment extends Fragment implements OnClickListener {

	private EditText usernameEt, passwordEt, confirmEt;
	private Button regBtn;

	private onRegFragmentOperation operation;

	private AccountBiz accountBiz;

	public static CloseMySelfHandler handler;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			operation = (onRegFragmentOperation) activity;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		accountBiz = new AccountBizImpl();
		handler = new CloseMySelfHandler();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layoutView = inflater.inflate(R.layout.fragment_reg, container, false);
		operation.needHideActionBar();
		usernameEt = (EditText) layoutView.findViewById(R.id.reg_username_et);
		passwordEt = (EditText) layoutView.findViewById(R.id.reg_password_et);
		confirmEt = (EditText) layoutView.findViewById(R.id.reg_confirm_et);
		regBtn = (Button) layoutView.findViewById(R.id.reg_reg_btn);
		regBtn.setOnClickListener(this);
		return layoutView;
	}

	@Override
	public void onPause() {
		super.onPause();
		usernameEt.setText("");
		passwordEt.setText("");
		confirmEt.setText("");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reg_reg_btn:
			if (usernameEt.getText().toString().equals("")) {
				Toast.makeText(getActivity(), getResources().getString(R.string.reg_empty_username), Toast.LENGTH_SHORT).show();
				return;
			}
			if (!passwordEt.getText().toString().equals(confirmEt.getText().toString())) {
				Toast.makeText(getActivity(), getResources().getString(R.string.reg_password_not_equal), Toast.LENGTH_SHORT).show();
				return;
			}
			BaseActivity.username = usernameEt.getText().toString();
			BaseActivity.password = passwordEt.getText().toString();
			if (accountBiz == null) {
				accountBiz = new AccountBizImpl();
			}
			new Thread(new Runnable() {

				@Override
				public void run() {
					BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_REG_WAITING);
					if (accountBiz.registerAccount(BaseActivity.username, BaseActivity.password, getActivity())) {
						BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_REG_OK);
					} else {
						BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_REG_ERR);
					}
				}
			}).start();
			break;

		default:
			break;
		}
	}

	@SuppressLint("HandlerLeak")
	public class CloseMySelfHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constants.REG_OK_CLOSE:
				getActivity().getSupportFragmentManager().popBackStack();
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
	}

	public interface onRegFragmentOperation {

		public void needHideActionBar();

	}
}
