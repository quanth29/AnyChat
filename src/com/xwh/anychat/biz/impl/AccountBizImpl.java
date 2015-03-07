package com.xwh.anychat.biz.impl;

import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Registration;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

import android.content.Context;

import com.xwh.anychat.biz.AccountBiz;
import com.xwh.anychat.db.AnyChatDatabaseHelper;
import com.xwh.anychat.entity.AccountInfoEntity;
import com.xwh.anychat.util.DebugUtil;
import com.xwh.anychat.util.ServerConnectionUtil;

public class AccountBizImpl implements AccountBiz {

	private XMPPConnection xmppConnection;

	private AnyChatDatabaseHelper databaseHelper;

	@Override
	public boolean registerAccount(String username, String password, Context context) {

		try {
			DebugUtil.Log("Start Register new account:" + username);
			xmppConnection = ServerConnectionUtil.getServerConnection(context);
			Registration registration = new Registration();
			registration.setType(IQ.Type.SET);
			registration.setTo(xmppConnection.getServiceName());
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("username", username);
			attributes.put("password", password);
			registration.setAttributes(attributes);
			PacketFilter filter = new AndFilter(new PacketIDFilter(registration.getPacketID()), new PacketTypeFilter(IQ.class));
			PacketCollector collector = xmppConnection.createPacketCollector(filter);
			xmppConnection.sendPacket(registration);
			IQ result = (IQ) collector.nextResult(xmppConnection.getPacketReplyTimeout());
			collector.cancel();
			if (result == null || result.getType() == IQ.Type.ERROR) {
				DebugUtil.Log("An error occured during registion, maybe the same username");
				return false;
			} else {
				DebugUtil.Log("Registion has OK!");
				return true;
			}
		} catch (Exception e) {
			DebugUtil.Log("An exception occured during registion");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean loginAccount(String username, String password, Context context) {
		try {
			DebugUtil.Log("Start login:" + username);
			xmppConnection = ServerConnectionUtil.getServerConnection(context);
			if (xmppConnection.isAuthenticated() && xmppConnection.getUser().contains(username)) {
				DebugUtil.Log("Already logged:" + xmppConnection.getUser());
			} else {
				if (xmppConnection.isAuthenticated()) {
					DebugUtil.Log("Already authenticated, disconnect the connection first");
					xmppConnection.disconnect();
					DebugUtil.Log("Reconnect");
					xmppConnection.connect();
				}
				DebugUtil.Log("Logging " + username);
				xmppConnection.login(username, password);
			}
			return true;
		} catch (Exception e) {
			try {
				xmppConnection.disconnect();
			} catch (Exception disconnectException) {
				disconnectException.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public VCard getMyVcard(Context context) {
		try {
			VCard vCard = new VCard();
			vCard.load(ServerConnectionUtil.getConnection());
			return vCard;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public AccountInfoEntity getMyAccountInfo(Context context, String username) {
		if (databaseHelper == null) {
			databaseHelper = AnyChatDatabaseHelper.getInstance(context);
		}
		return databaseHelper.getAccountInfoByUsername(username);
	}

	@Override
	public boolean saveMyVcard(Context context, VCard vCard) {
		try {
			DebugUtil.Log("Upload user profile");
			xmppConnection = ServerConnectionUtil.getServerConnection(context);
			vCard.save(xmppConnection);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void saveMyAccountInfo(Context context, VCard vCard, String userName) {
		if (databaseHelper == null) {
			databaseHelper = AnyChatDatabaseHelper.getInstance(context);
		}
		databaseHelper.addOrUpdateAccountInfo(new AccountInfoEntity(vCard, userName), false);
	}

}
