package com.xwh.anychat.service;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

import com.xwh.anychat.BaseActivity;
import com.xwh.anychat.SharedObj;
import com.xwh.anychat.biz.AccountBiz;
import com.xwh.anychat.biz.FriendBiz;
import com.xwh.anychat.biz.impl.AccountBizImpl;
import com.xwh.anychat.biz.impl.FriendBizImpl;
import com.xwh.anychat.config.Constants;
import com.xwh.anychat.fragment.AddNewFriendConfirmDialogFragment;
import com.xwh.anychat.fragment.FriendsFragment;
import com.xwh.anychat.fragment.MainFragment;
import com.xwh.anychat.listener.MessagePacketListener;
import com.xwh.anychat.listener.RosterChangeListener;
import com.xwh.anychat.util.DebugUtil;
import com.xwh.anychat.util.DeviceInfoUtil;
import com.xwh.anychat.util.ServerConnectionUtil;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class ConnectionService extends Service {

	private MessagePacketListener messagePacketListener;
	private RosterChangeListener rosterChangeListener;
	public static serviceHandler handler;

	private AccountBiz accountBiz;
	private FriendBiz friendBiz;
	private XMPPConnection xmppConnection;

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		DebugUtil.Log("ConnectionService has been launched");
		accountBiz = new AccountBizImpl();
		friendBiz = new FriendBizImpl();
		messagePacketListener = new MessagePacketListener();
		rosterChangeListener = new RosterChangeListener();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		DebugUtil.Log("ConnectionService has been started");
		if (handler == null) {
			handler = new serviceHandler();
		}
		connectToServer();
		return Service.START_STICKY;
	}

	// 连接到服务器
	private void connectToServer() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				if (DeviceInfoUtil.isNetworkConnected(ConnectionService.this)) {
					DebugUtil.Log("Network is available");
					try {
						ServerConnectionUtil.connect(ConnectionService.this);
						DebugUtil.Log("Connection has established");
						handler.sendEmptyMessage(Constants.SERVICE_SERVER_CONNECT_OK);
					} catch (Exception e) {
						e.printStackTrace();
						DebugUtil.Log("Failed to connect server");
						BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_SERVER_ERR);
						handler.sendEmptyMessage(Constants.SERVICE_NEED_STOP);
					}
				} else {
					DebugUtil.Log("No network access");
					BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_NO_NETWORK);
					handler.sendEmptyMessage(Constants.SERVICE_NEED_STOP);
				}
			}
		}).start();
	}

	// 读取用户设置和设置包监听
	private void loadSettings() {
		xmppConnection = ServerConnectionUtil.getConnection();
		if (messagePacketListener == null) {
			messagePacketListener = new MessagePacketListener();
		}
		if (accountBiz == null) {
			accountBiz = new AccountBizImpl();
		}
		if (friendBiz == null) {
			friendBiz = new FriendBizImpl();
		}
		xmppConnection.addPacketListener(messagePacketListener, messagePacketListener);
		DebugUtil.Log("packet Listening...");
	}

	// 同步用户Vcard
	private void SyncVCard() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					xmppConnection = ServerConnectionUtil.getServerConnection(getApplicationContext());
					SharedObj.myVcard = new VCard();
					SharedObj.myVcard.load(xmppConnection);
					if (accountBiz == null) {
						accountBiz = new AccountBizImpl();
					}
					accountBiz.saveMyAccountInfo(getApplicationContext(), SharedObj.myVcard, BaseActivity.username);
					if (MainFragment.handler != null) {
						MainFragment.handler.sendEmptyMessage(Constants.SERVICE_LOAD_VCARD_FINISH);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	// 同步用户好友列表
	private void SyncRoster() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					DebugUtil.Log("Start refresh roster info");
					xmppConnection = ServerConnectionUtil.getServerConnection(getApplicationContext());
					SharedObj.roster = xmppConnection.getRoster();
					if (friendBiz == null) {
						friendBiz = new FriendBizImpl();
					}
					friendBiz.updateRosterListInfo(getApplicationContext(), SharedObj.roster, BaseActivity.username);
					
					if (FriendsFragment.handler != null) {
						FriendsFragment.handler.sendEmptyMessage(Constants.SERVICE_LOAD_MY_ROSTER_FINISH);
					}
					if (AddNewFriendConfirmDialogFragment.handler != null) {
						AddNewFriendConfirmDialogFragment.handler.sendEmptyMessage(Constants.SERVICE_LOAD_MY_ROSTER_FINISH);
					}
					DebugUtil.Log("Refresh roster info over, set roster change listener now");
					if (rosterChangeListener == null) {
						rosterChangeListener = new RosterChangeListener();
					}
					SharedObj.roster.addRosterListener(rosterChangeListener);
					DebugUtil.Log("Roster change listener is ready.");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

	// 操作服务用的Handler
	@SuppressLint("HandlerLeak")
	public class serviceHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constants.SERVICE_NEED_STOP:
				stopSelf();
				break;
			case Constants.SERVICE_SERVER_CONNECT_OK:
				loadSettings();
				break;
			case Constants.SERVICE_LOAD_VCARD_START:
				SyncVCard();
				break;
			case Constants.SERVICE_LOAD_MY_ROSTER_START:
				SyncRoster();
				break;
			default:
				break;
			}
		}

	}

}
