package com.xwh.anychat.fragment;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.xwh.anychat.BaseActivity;
import com.xwh.anychat.R;
import com.xwh.anychat.config.Constants;
import com.xwh.anychat.util.ServerConnectionUtil;

public class VCardResultDialogFragment extends DialogFragment implements OnClickListener {
	private ImageView avatorIv;
	private TextView orgTv, phoneTv, emailTv;
	private Button addToContactBtn;

	private String userName;

	private VCard vCard;

	private XMPPConnection xmppConnection;
	private UIHandler handler;

	private Bitmap avator;

	private AddNewFriendConfirmDialogFragment addNewFriendConfirmDialogFragment;
	private FragmentManager fragmentManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addNewFriendConfirmDialogFragment = new AddNewFriendConfirmDialogFragment();
		fragmentManager = getActivity().getSupportFragmentManager();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		userName = getArguments().getString(Constants.VCARD_USERNAME);
		getDialog().setTitle(userName + getResources().getString(R.string.vcardinfo_title));
		View layoutView = inflater.inflate(R.layout.fragment_dialog_person_detail_info, container, false);
		avatorIv = (ImageView) layoutView.findViewById(R.id.vcard_avator_iv);
		orgTv = (TextView) layoutView.findViewById(R.id.vcard_org_tv);
		phoneTv = (TextView) layoutView.findViewById(R.id.vcard_phone_tv);
		emailTv = (TextView) layoutView.findViewById(R.id.vcard_email_tv);
		addToContactBtn = (Button) layoutView.findViewById(R.id.vcard_addthisperson_btn);
		addToContactBtn.setOnClickListener(this);
		initData();
		return layoutView;
	}

	private void initData() {
		handler = new UIHandler();
		BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_COMMON_PROGRESS_DIALOG_SHOW);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					xmppConnection = ServerConnectionUtil.getServerConnection(getActivity());
					vCard = new VCard();
					vCard.load(xmppConnection, userName + "@" + xmppConnection.getServiceName());
					handler.sendEmptyMessage(Constants.VCARD_LOAD_OK);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendEmptyMessage(Constants.VCARD_LOAD_ERR);
				}
			}
		}).start();
	}

	@SuppressLint("HandlerLeak")
	private class UIHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constants.VCARD_LOAD_OK:
				if (vCard.getAvatar() == null) {
					avatorIv.setImageResource(R.drawable.ic_launcher);
				} else {
					avator = null;
					avator = BitmapFactory.decodeByteArray(vCard.getAvatar(), 0, vCard.getAvatar().length);
					avatorIv.setImageBitmap(avator);
				}
				orgTv.setText(vCard.getOrganization());
				phoneTv.setText(vCard.getPhoneWork("CELL"));
				emailTv.setText(vCard.getEmailWork());
				if (userName.equals(xmppConnection.getUser().split("@")[0])) {
					addToContactBtn.setVisibility(View.GONE);
				} else {
					addToContactBtn.setVisibility(View.VISIBLE);
				}
				BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_COMMON_PROGRESS_DIALOG_DISMISS);
				break;

			case Constants.VCARD_LOAD_ERR:
				Toast.makeText(getActivity(), getResources().getString(R.string.vcardinfo_errtoload), Toast.LENGTH_SHORT).show();
				BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_COMMON_PROGRESS_DIALOG_DISMISS);
				break;

			default:
				break;
			}
			super.handleMessage(msg);
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
		case R.id.vcard_addthisperson_btn:
			if (addNewFriendConfirmDialogFragment == null) {
				addNewFriendConfirmDialogFragment = new AddNewFriendConfirmDialogFragment();
			}
			Bundle bundle=new Bundle();
			bundle.putString(Constants.ADD_NEW_FRIEND_USERNAME, userName);
			addNewFriendConfirmDialogFragment.setArguments(bundle);
			addNewFriendConfirmDialogFragment.show(fragmentManager, "AddNewFriendConfirm");
			break;

		default:
			break;
		}
	}
}
