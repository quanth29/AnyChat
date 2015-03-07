/**
 * 本类主要负责程序内部的数值传递和通知提醒
 */

package com.xwh.anychat;

import com.xwh.anychat.config.Constants;
import com.xwh.anychat.db.AnyChatDatabaseHelper;
import com.xwh.anychat.fragment.RegFragment;
import com.xwh.anychat.fragment.ServerSetDialogFragment;
import com.xwh.anychat.fragment.ServerSetDialogFragment.onServerSetDialogOperation;
import com.xwh.anychat.service.ConnectionService;
import com.xwh.anychat.util.DebugUtil;
import com.xwh.anychat.util.PrefUtil;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

public abstract class BaseActivity extends ActionBarActivity implements onServerSetDialogOperation {
	public static int screenX, screenY;
	public static GlobalHandler globalHandler;

	private AlertDialog alertDialog;
	private Builder alertDialogBuilder;
	private ProgressDialog progressDialog;

	private ServerSetDialogFragment serverSetDialogFragment;

	public AnyChatDatabaseHelper dbHelper;

	public static String username, password;

	private Intent serviceIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DebugUtil.Log("APP is running...");
		screenX = getResources().getDisplayMetrics().widthPixels;
		screenY = getResources().getDisplayMetrics().heightPixels;
		DebugUtil.Log("This screen info is " + screenX + " * " + screenY);
		DebugUtil.Log("Prepare to initialize the server connection");
		globalHandler = new GlobalHandler();
		dbHelper = AnyChatDatabaseHelper.getInstance(BaseActivity.this);
		// 判断程序中是否设置了服务器信息，若存在，则按照设置的值尝试连接；反之，则弹出窗口进行设定。
		checkIfServerInfoSet();
	}

	// 检查服务器信息是否正确配置
	private void checkIfServerInfoSet() {
		if (PrefUtil.getServerIP(BaseActivity.this) == null || PrefUtil.getServerPort(BaseActivity.this) == -1) {
			DebugUtil.Log("No Server config, so configure now");
			if (serverSetDialogFragment == null) {
				serverSetDialogFragment = new ServerSetDialogFragment();
			}
			serverSetDialogFragment.setCancelable(false);
			serverSetDialogFragment.show(getSupportFragmentManager(), "ServerSet");
		} else {
			DebugUtil.Log("Load Server config OK, try to connect");
			if (serviceIntent == null) {
				serviceIntent = new Intent(BaseActivity.this, ConnectionService.class);
			}
			startService(serviceIntent);
		}
	}

	// 配置服务器信息完成，存储相关信息并启动服务
	@Override
	public void onConfirmClick(String serverIP, int serverPort) {
		if (serviceIntent == null) {
			serviceIntent = new Intent(BaseActivity.this, ConnectionService.class);
		}
		PrefUtil.storeServerIP(BaseActivity.this, serverIP);
		PrefUtil.storeServerPort(BaseActivity.this, serverPort);
		startService(serviceIntent);
	}

	// 创建没有网络连接的提示框
	private AlertDialog createNoNetworkDialog() {
		if (alertDialog != null) {
			alertDialog = null;
			if (alertDialogBuilder != null) {
				alertDialogBuilder = null;
			}
		}
		alertDialogBuilder = new Builder(BaseActivity.this);
		alertDialogBuilder.setTitle(R.string.global_nonetwork_title);
		alertDialogBuilder.setMessage(R.string.global_nonetwork_msg);
		alertDialogBuilder.setPositiveButton(R.string.global_nonetwork_okbtn, null);
		alertDialog = alertDialogBuilder.create();
		return alertDialog;
	}

	// 创建服务器连接失败的提示框
	private AlertDialog createServerErrDialog() {
		if (alertDialog != null) {
			alertDialog = null;
			if (alertDialogBuilder != null) {
				alertDialogBuilder = null;
			}
		}
		alertDialogBuilder = new Builder(BaseActivity.this);
		alertDialogBuilder.setTitle(R.string.global_servererr_title);
		alertDialogBuilder.setMessage(R.string.global_servererr_msg);
		alertDialogBuilder.setPositiveButton(R.string.global_servererr_okbtn, null);
		alertDialog = alertDialogBuilder.create();
		return alertDialog;
	}

	// 创建用户帐户对话框
	private ProgressDialog createRegisterNewUserProgressDialog() {
		if (progressDialog != null) {
			progressDialog = null;

		}
		progressDialog = new ProgressDialog(BaseActivity.this);
		progressDialog.setCancelable(false);
		progressDialog.setMessage(getResources().getString(R.string.reg_progressdialog_msg));
		return progressDialog;
	}

	// 创建通用等待对话框
	private ProgressDialog createCommonProgressDialog() {
		if (progressDialog != null) {
			progressDialog = null;

		}
		progressDialog = new ProgressDialog(BaseActivity.this);
		progressDialog.setCancelable(false);
		progressDialog.setMessage(getResources().getString(R.string.global_common_progressdialog_msg));
		return progressDialog;
	}

	// 创建注册成功后对话框
	private AlertDialog createRegisterOKDialog() {
		if (alertDialog != null) {
			alertDialog = null;
			if (alertDialogBuilder != null) {
				alertDialogBuilder = null;
			}
		}
		alertDialogBuilder = new Builder(BaseActivity.this);
		alertDialogBuilder.setTitle(R.string.reg_reg_ok_question_title);
		alertDialogBuilder.setMessage(R.string.reg_reg_ok_question_msg);
		alertDialogBuilder.setPositiveButton(R.string.reg_login_now, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				PrefUtil.storeAutoLogin(BaseActivity.this, false);
				PrefUtil.storeUsername(BaseActivity.this, BaseActivity.username);
				RegFragment.handler.sendEmptyMessage(Constants.REG_OK_CLOSE);
			}
		});
		alertDialogBuilder.setNegativeButton(R.string.reg_close, null);
		alertDialogBuilder.setCancelable(false);
		alertDialog = alertDialogBuilder.create();
		return alertDialog;
	}

	@SuppressLint("HandlerLeak")
	public class GlobalHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constants.GLOBAL_CODE_NO_NETWORK:
				createNoNetworkDialog().show();
				break;
			case Constants.GLOBAL_CODE_SERVER_ERR:
				createServerErrDialog().show();
				break;
			case Constants.GLOBAL_CODE_REG_WAITING:
				createRegisterNewUserProgressDialog().show();
				break;
			case Constants.GLOBAL_CODE_REG_OK:
				progressDialog.dismiss();
				createRegisterOKDialog().show();
				break;
			case Constants.GLOBAL_CODE_REG_ERR:
				progressDialog.dismiss();
				Toast.makeText(BaseActivity.this, getResources().getString(R.string.reg_reg_err), Toast.LENGTH_SHORT).show();
				break;
			case Constants.GLOBAL_CODE_LOGIN_OK:
				Toast.makeText(BaseActivity.this, getResources().getString(R.string.login_ok), Toast.LENGTH_SHORT).show();
				break;
			case Constants.GLOBAL_CODE_LOGIN_ERR:
				Toast.makeText(BaseActivity.this, getResources().getString(R.string.login_err), Toast.LENGTH_SHORT).show();
				break;
			case Constants.GLOBAL_CODE_COMMON_PROGRESS_DIALOG_SHOW:
				createCommonProgressDialog().show();
				break;
			case Constants.GLOBAL_CODE_COMMON_PROGRESS_DIALOG_DISMISS:
				progressDialog.dismiss();
				break;
			case Constants.GLOBAL_CODE_SAVE_PROFILE_OK:
				Toast.makeText(BaseActivity.this, getResources().getString(R.string.global_success), Toast.LENGTH_SHORT).show();
				break;
			case Constants.GLOBAL_CODE_SAVE_PEOFILE_ERR:
				Toast.makeText(BaseActivity.this, getResources().getString(R.string.global_failed), Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	}

}
