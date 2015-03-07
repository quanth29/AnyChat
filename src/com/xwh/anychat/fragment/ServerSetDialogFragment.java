package com.xwh.anychat.fragment;

import com.xwh.anychat.R;
import com.xwh.anychat.util.PrefUtil;
import com.xwh.anychat.util.ServerConnectionUtil;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ServerSetDialogFragment extends DialogFragment implements OnClickListener {

	private EditText serverIPEt, serverPortEt;
	private Button confirmBtn;

	private onServerSetDialogOperation operation;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			operation = (onServerSetDialogOperation) activity;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getDialog().setTitle(R.string.login_server_config_tv);
		View layoutView = inflater.inflate(R.layout.fragment_dialog_server_set, container, false);
		serverIPEt = (EditText) layoutView.findViewById(R.id.serverset_ip_et);
		serverPortEt = (EditText) layoutView.findViewById(R.id.serverset_port_et);
		confirmBtn = (Button) layoutView.findViewById(R.id.serverset_confirmbtn);
		confirmBtn.setOnClickListener(this);
		initData();
		return layoutView;
	}

	private void initData() {
		if (PrefUtil.getServerIP(getActivity()) != null) {
			serverIPEt.setText(PrefUtil.getServerIP(getActivity()));
		}
		if (PrefUtil.getServerPort(getActivity()) != -1) {
			serverPortEt.setText(String.valueOf(PrefUtil.getServerPort(getActivity())));
		}
		if (ServerConnectionUtil.getConnection() != null && ServerConnectionUtil.getConnection().isConnected()) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						ServerConnectionUtil.getConnection().disconnect();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}).start();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.serverset_confirmbtn:
			if (serverIPEt.getText().toString().equals("") || serverPortEt.getText().toString().equals("")) {
				Toast.makeText(getActivity(), getResources().getString(R.string.global_serverset_empty), Toast.LENGTH_SHORT).show();
				return;
			}
			dismiss();
			operation.onConfirmClick(serverIPEt.getText().toString(), Integer.valueOf(serverPortEt.getText().toString()));
			break;

		default:
			break;
		}
	}

	public interface onServerSetDialogOperation {

		public void onConfirmClick(String serverIP, int serverPort);
	}

}
