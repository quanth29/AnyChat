package com.xwh.anychat.fragment;

/**
 * Created by 萧文翰 on 2015/3/18.
 */
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xwh.anychat.BaseActivity;
import com.xwh.anychat.R;
import com.xwh.anychat.biz.AccountBiz;
import com.xwh.anychat.biz.FileExplorerBiz;
import com.xwh.anychat.biz.impl.AccountBizImpl;
import com.xwh.anychat.biz.impl.FileExplorerBizImpl;
import com.xwh.anychat.config.Constants;
import com.xwh.anychat.dao.AccountInfoDao;
import com.xwh.anychat.dao.impl.AccountInfoDaoImpl;
import com.xwh.anychat.entity.AccountInfoEntity;
import com.xwh.anychat.util.BitmapToAvatorUtil;
import com.xwh.anychat.util.DebugUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class MyProfileFragment extends Fragment implements OnClickListener {

	private TextView usernameTv;
	private ImageView avatorIv;
	private EditText nicknameEt, jIdEt, firstnameEt, midnameEt, lastnameEt, workphoneEt, homephoneEt, workemailEt, homeemailEt, orgEt, orgunitEt, workaddrEt, homeaddrEt;
	private Button switchViewBtn, applyBtn;

	private byte[] avatorBitmap;

	private LinearLayout nameLl;

	private boolean isShowMoreOpr;

	private AccountInfoEntity accountInfo;

	private AccountBiz accountBiz;
	private AccountInfoDao accountInfoDao;
	private FragmentTransaction fragmentTransaction;

	private CommonFileExplorerFragment commonFileExplorerFragment;
	private FileExplorerBiz fileExplorerBiz;

	public static AvatorFileHandler avatorFileHandler;

	private Bitmap avator;

	private String avatorPath;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DebugUtil.Log("Load my VCard data");
		accountInfo = (AccountInfoEntity) getArguments().getSerializable(Constants.MY_PROFILE_USER_DATA);
		if (accountInfo.getAvator().length != 0) {
			avatorBitmap = accountInfo.getAvator();
			avator = BitmapFactory.decodeByteArray(avatorBitmap, 0, accountInfo.getAvator().length);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layoutView = inflater.inflate(R.layout.fragment_myprofile, container, false);
		usernameTv = (TextView) layoutView.findViewById(R.id.myprofile_username);
		jIdEt = (EditText) layoutView.findViewById(R.id.myprofile_jid);
		avatorIv = (ImageView) layoutView.findViewById(R.id.myprofile_avator);
		nicknameEt = (EditText) layoutView.findViewById(R.id.myprofile_nickname);
		firstnameEt = (EditText) layoutView.findViewById(R.id.myprofile_firstname);
		midnameEt = (EditText) layoutView.findViewById(R.id.myprofile_midname);
		lastnameEt = (EditText) layoutView.findViewById(R.id.myprofile_lastname);
		workphoneEt = (EditText) layoutView.findViewById(R.id.myprofile_workphone);
		homephoneEt = (EditText) layoutView.findViewById(R.id.myprofile_homephone);
		workemailEt = (EditText) layoutView.findViewById(R.id.myprofile_workemail);
		homeemailEt = (EditText) layoutView.findViewById(R.id.myprofile_homeemail);
		workaddrEt = (EditText) layoutView.findViewById(R.id.myprofile_workaddr);
		homeaddrEt = (EditText) layoutView.findViewById(R.id.myprofile_homeaddr);
		orgEt = (EditText) layoutView.findViewById(R.id.myprofile_org);
		orgunitEt = (EditText) layoutView.findViewById(R.id.myprofile_orgunit);
		switchViewBtn = (Button) layoutView.findViewById(R.id.myprofile_morebtn);
		applyBtn = (Button) layoutView.findViewById(R.id.myprofile_savebtn);
		nameLl = (LinearLayout) layoutView.findViewById(R.id.myprofile_name);
		switchViewBtn.setOnClickListener(this);
		applyBtn.setOnClickListener(this);
		avatorIv.setOnClickListener(this);
		switchViewBtn.setText(R.string.myprofile_morebtn_show);
		isShowMoreOpr = false;
		showLessOpr();
		initData();
		return layoutView;
	}

	// 初始化显示内容
	private void initData() {
		commonFileExplorerFragment = new CommonFileExplorerFragment();
		avatorFileHandler = new AvatorFileHandler();
		fileExplorerBiz = new FileExplorerBizImpl();
		usernameTv.setText(getResources().getString(R.string.myprofile_username) + " " + accountInfo.getUsername());
		nicknameEt.setText(accountInfo.getNickName());
		jIdEt.setText(accountInfo.getjID());
		if (avator == null) {
			avatorIv.setImageResource(R.drawable.ic_launcher);
		} else {
			avatorIv.setImageBitmap(avator);
		}
		firstnameEt.setText(accountInfo.getFirstName());
		midnameEt.setText(accountInfo.getMidName());
		lastnameEt.setText(accountInfo.getLastName());
		workphoneEt.setText(accountInfo.getWorkPhone());
		homephoneEt.setText(accountInfo.getHomePhone());
		workemailEt.setText(accountInfo.getWorkEmail());
		homeemailEt.setText(accountInfo.getHomeEmail());
		workaddrEt.setText(accountInfo.getWorkAddress());
		homeaddrEt.setText(accountInfo.getHomeAddress());
		orgEt.setText(accountInfo.getOrganization());
		orgunitEt.setText(accountInfo.getOrganizationUnit());
		accountBiz = new AccountBizImpl();
		accountInfoDao = new AccountInfoDaoImpl();
	}

	// 跳转至相片选择界面
	public void jumpToFileSelect() {
		DebugUtil.Log("Jump to FileSelect from MyProfileFragment");
		if (fragmentTransaction != null) {
			fragmentTransaction = null;
		}
		fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
		Bundle bundle = new Bundle();
		bundle.putInt(Constants.FILE_EXPLORER_SELECT_MODE, Constants.FILE_EXPLORER_MODE_SINGLE);
		bundle.putString(Constants.FILE_EXPLORER_START_PATH, fileExplorerBiz.getDCIMPath());
		bundle.putString(Constants.FILE_EXPLORER_FROM, Constants.FILE_EXPLORER_FROM_AVATOR);
		commonFileExplorerFragment.setArguments(bundle);
		fragmentTransaction.replace(R.id.anychat_fragment_container, commonFileExplorerFragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
		getActivity().getSupportFragmentManager().executePendingTransactions();
	}

	// 显示更少项
	private void showLessOpr() {
		nameLl.setVisibility(View.GONE);
		homeaddrEt.setVisibility(View.GONE);
		homeemailEt.setVisibility(View.GONE);
		homephoneEt.setVisibility(View.GONE);
	}

	// 显示更多项
	private void showMoreOpr() {
		nameLl.setVisibility(View.VISIBLE);
		homeaddrEt.setVisibility(View.VISIBLE);
		homeemailEt.setVisibility(View.VISIBLE);
		homephoneEt.setVisibility(View.VISIBLE);
	}

	// 用于用户账户头像处理的Handler
	@SuppressLint("HandlerLeak")
	public class AvatorFileHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constants.FILE_EXPLORER_SELECT_OVER:
				BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_COMMON_PROGRESS_DIALOG_SHOW);
				// 选取图片文件结束
				avatorPath = msg.getData().getString(Constants.FILE_EXPLORER_RETURN_SINGLE_FILE);
				new Thread(new Runnable() {

					@Override
					public void run() {
						DebugUtil.Log(avatorPath + " has selected");
						// 处理所选图片，压缩至200*200以内，并加以显示
						avator = BitmapToAvatorUtil.resizeBitmap(avatorPath, 200, 200);
						avatorBitmap = BitmapToAvatorUtil.bitmapToBytes(avator);
						avatorFileHandler.sendEmptyMessage(Constants.MY_PROFILE_AVATOR_LOAD_OVER);
					}
				}).start();
				break;
			case Constants.MY_PROFILE_AVATOR_LOAD_OVER:
				BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_COMMON_PROGRESS_DIALOG_DISMISS);
				avatorIv.setImageBitmap(avator);
				CompressFormat format = Bitmap.CompressFormat.PNG;
				int quality = 80;
				OutputStream stream = null;
				try {
					stream = new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + "a.png");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				avator.compress(format, quality, stream);
				getActivity().getSupportFragmentManager().popBackStack();
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (avator != null && avator.isRecycled()) {
			avator.recycle();
		}
		avator = null;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myprofile_avator:
			if (fileExplorerBiz == null) {
				fileExplorerBiz = new FileExplorerBizImpl();
			}
			if (fileExplorerBiz.isExternalStorageAvailable()) {
				jumpToFileSelect();
			} else {
				Toast.makeText(getActivity(), getResources().getString(R.string.fileexplorer_storagenotavailable), Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.myprofile_morebtn:
			if (isShowMoreOpr) {
				showLessOpr();
				isShowMoreOpr = false;
				switchViewBtn.setText(R.string.myprofile_morebtn_show);
			} else {
				showMoreOpr();
				isShowMoreOpr = true;
				switchViewBtn.setText(R.string.myprofile_morebtn_dismiss);
			}
			break;

		case R.id.myprofile_savebtn:
			BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_COMMON_PROGRESS_DIALOG_SHOW);
			accountInfo.setjID(jIdEt.getText().toString());
			accountInfo.setAvator(avatorBitmap);
			accountInfo.setFirstName(firstnameEt.getText().toString());
			accountInfo.setHomeAddress(homeaddrEt.getText().toString());
			accountInfo.setHomeEmail(homeemailEt.getText().toString());
			accountInfo.setHomePhone(homephoneEt.getText().toString());
			accountInfo.setLastName(lastnameEt.getText().toString());
			accountInfo.setMidName(midnameEt.getText().toString());
			accountInfo.setNickName(nicknameEt.getText().toString());
			accountInfo.setOrganization(orgEt.getText().toString());
			accountInfo.setOrganizationUnit(orgunitEt.getText().toString());
			accountInfo.setWorkAddress(workaddrEt.getText().toString());
			accountInfo.setWorkEmail(workemailEt.getText().toString());
			accountInfo.setWorkPhone(workphoneEt.getText().toString());
			new Thread(new Runnable() {

				@Override
				public void run() {
					if (accountBiz.saveMyVcard(getActivity(), accountInfo.genVcard())) {
						// 保存成功，存储数据库
						accountInfoDao.addOrUpdateAccountInfo(getActivity(), accountInfo, false);
						BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_COMMON_PROGRESS_DIALOG_DISMISS);
						BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_SAVE_PROFILE_OK);
						getActivity().getSupportFragmentManager().popBackStack();
					} else {
						BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_COMMON_PROGRESS_DIALOG_DISMISS);
						BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_SAVE_PEOFILE_ERR);
					}
				}
			}).start();
			break;
		default:
			break;
		}
	}

}
