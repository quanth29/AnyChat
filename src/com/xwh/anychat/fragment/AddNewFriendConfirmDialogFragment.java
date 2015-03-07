package com.xwh.anychat.fragment;

import java.util.ArrayList;

import com.xwh.anychat.BaseActivity;
import com.xwh.anychat.R;
import com.xwh.anychat.SharedObj;
import com.xwh.anychat.biz.FriendBiz;
import com.xwh.anychat.biz.impl.FriendBizImpl;
import com.xwh.anychat.config.Constants;
import com.xwh.anychat.service.ConnectionService;
import com.xwh.anychat.util.DebugUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNewFriendConfirmDialogFragment extends DialogFragment implements OnClickListener, TextWatcher, OnItemSelectedListener {
	private Button createNewGroupBtn, okBtn, cancelBtn;
	private EditText groupNameInputEt;
	private RelativeLayout createNewGroupRl;
	private Spinner groupNameSp;

	private FriendBiz friendBiz;
	private ArrayList<String> groupNameList;

	public static UIHandler handler;

	private String username;
	private String groupname;

	private ArrayAdapter<String> arrayAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		friendBiz = new FriendBizImpl();
		groupNameList = new ArrayList<String>();
		handler = new UIHandler();
		username = getArguments().getString(Constants.ADD_NEW_FRIEND_USERNAME);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getDialog().setTitle(R.string.add_new_confirm_title);
		View layoutView = inflater.inflate(R.layout.fragment_dialog_add_new_friend_confirm, container, false);
		createNewGroupBtn = (Button) layoutView.findViewById(R.id.add_new_confirm_add_group_btn);
		okBtn = (Button) layoutView.findViewById(R.id.add_new_confirm_ok_btn);
		cancelBtn = (Button) layoutView.findViewById(R.id.add_new_confirm_cancel_btn);
		groupNameInputEt = (EditText) layoutView.findViewById(R.id.add_new_confirm_new_group_name_et);
		createNewGroupRl = (RelativeLayout) layoutView.findViewById(R.id.add_new_confirm_new_group_rl);
		groupNameSp = (Spinner) layoutView.findViewById(R.id.add_new_confirm_sp);
		groupNameInputEt.addTextChangedListener(this);
		createNewGroupBtn.setOnClickListener(this);
		okBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
		groupNameSp.setOnItemSelectedListener(this);
		initData();
		return layoutView;
	}

	// 载入与刷新数据
	private void initData() {
		// 本地载入好友分组信息
		refreshGroupInfoDataLocal();
		// 远程同步好友分组信息
		refreshGroupInfoData();
	}

	private void refreshGroupInfoDataLocal() {
		if (groupNameList != null) {
			groupNameList.clear();
		} else {
			groupNameList = new ArrayList<String>();
		}
		// 尝试从本地已有数据中载入分组信息
		if (SharedObj.roster == null) {
			groupNameList.addAll(friendBiz.getRosterGroupList(getActivity(), BaseActivity.username));
		} else {
			groupNameList.addAll(friendBiz.getRosterGroupList(getActivity(), SharedObj.roster));
		}
		if (arrayAdapter == null) {
			arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, groupNameList);
			groupNameSp.setAdapter(arrayAdapter);
		} else {
			arrayAdapter.notifyDataSetChanged();
		}
	}

	// 远程同步好友分组信息
	private void refreshGroupInfoData() {
		ConnectionService.handler.sendEmptyMessage(Constants.SERVICE_LOAD_MY_ROSTER_START);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_new_confirm_cancel_btn:
			dismiss();
			break;
		case R.id.add_new_confirm_add_group_btn:
			createNewGroupRl.setVisibility(View.VISIBLE);
			break;
		case R.id.add_new_confirm_ok_btn:
			if (createNewGroupRl.getVisibility() == View.VISIBLE) {
				// 创建新分组
				DebugUtil.Log("Let's create a new group first");
				// 如果用户需要新建分组，则创建新的分组
				groupname = groupNameInputEt.getText().toString();
				if (groupname.equals("")) {
					Toast.makeText(getActivity(), R.string.add_new_confirm_empty_new_group, Toast.LENGTH_SHORT).show();
				} else {
					BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_COMMON_PROGRESS_DIALOG_SHOW);
					new Thread(new Runnable() {

						@Override
						public void run() {
							if (friendBiz.createNewGroup(getActivity(), groupname)) {
								handler.sendEmptyMessage(Constants.ADD_NEW_FRIEND_CREATE_GROUP_OK);
								// 将联系人加至指定分组
								DebugUtil.Log("Let's add this person to your friend list");
								new Thread(new Runnable() {

									@Override
									public void run() {

										if (friendBiz.addFriend(getActivity(), username, groupname)) {
											DebugUtil.Log("This person to " + groupname);
											handler.sendEmptyMessage(Constants.ADD_NEW_FRIEND_ADD_OK);
										} else {
											handler.sendEmptyMessage(Constants.ADD_NEW_FRIEND_ADD_ERR);
										}
									}
								}).start();
							} else {
								handler.sendEmptyMessage(Constants.ADD_NEW_FRIEND_CREATE_GROUP_ERR);
							}
						}
					}).start();
				}
				DebugUtil.Log("Group has been created");
			} else {
				// 从已选择的分组中选取
				if (groupNameList == null || groupNameList.size() == 0) {
					Toast.makeText(getActivity(), R.string.add_new_confirm_empty_group, Toast.LENGTH_SHORT).show();
					break;
				}
				DebugUtil.Log("Let's add this person to your friend list");
				BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_COMMON_PROGRESS_DIALOG_SHOW);
				new Thread(new Runnable() {

					@Override
					public void run() {

						if (friendBiz.addFriend(getActivity(), username, groupname)) {
							DebugUtil.Log("This person to " + groupname);
							handler.sendEmptyMessage(Constants.ADD_NEW_FRIEND_ADD_OK);
						} else {
							handler.sendEmptyMessage(Constants.ADD_NEW_FRIEND_ADD_ERR);
						}
					}
				}).start();
			}

			break;
		default:
			break;
		}
	}

	@SuppressLint("HandlerLeak")
	public class UIHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constants.SERVICE_LOAD_MY_ROSTER_FINISH:
				refreshGroupInfoDataLocal();
				break;
			case Constants.ADD_NEW_FRIEND_CREATE_GROUP_OK:
				break;
			case Constants.ADD_NEW_FRIEND_CREATE_GROUP_ERR:
				Toast.makeText(getActivity(), R.string.add_new_confirm_group_create_err, Toast.LENGTH_SHORT).show();
				break;
			case Constants.ADD_NEW_FRIEND_ADD_OK:
				BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_COMMON_PROGRESS_DIALOG_DISMISS);
				Toast.makeText(getActivity(), R.string.add_new_confirm_add_ok, Toast.LENGTH_SHORT).show();
				dismiss();
				break;
			case Constants.ADD_NEW_FRIEND_ADD_ERR:
				BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_COMMON_PROGRESS_DIALOG_DISMISS);
				Toast.makeText(getActivity(), R.string.add_new_confirm_add_err, Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
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
		if (s.toString().equals("")) {
			groupNameSp.setVisibility(View.VISIBLE);
		} else {
			groupNameSp.setVisibility(View.GONE);
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		groupname = groupNameList.get(position);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

}
