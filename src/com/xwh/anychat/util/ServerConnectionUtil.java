package com.xwh.anychat.util;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import android.content.Context;
import com.xwh.anychat.BuildConfig;

public class ServerConnectionUtil {
	private static ConnectionConfiguration connectionConfiguration;
	private static XMPPConnection xmppConnection;

	// 初始化，类内调用，每次执行服务器交互前调用此方法
	private static void init(Context context) throws Exception {
		if (xmppConnection == null || !xmppConnection.isConnected()) {
			DebugUtil.Log("The Server connection is not be established, so do it now");
			SmackConfiguration.DEBUG_ENABLED = true;
			connectionConfiguration = new ConnectionConfiguration(PrefUtil.getServerIP(context), PrefUtil.getServerPort(context));
			connectionConfiguration.setReconnectionAllowed(true);
			connectionConfiguration.setSendPresence(true);
			connectionConfiguration.setRosterLoadedAtLogin(true);
			connectionConfiguration.setSecurityMode(SecurityMode.disabled);
			connectionConfiguration.setDebuggerEnabled(BuildConfig.DEBUG);
			xmppConnection = new XMPPTCPConnection(connectionConfiguration);
			xmppConnection.connect();
			DebugUtil.Log("The Server connection has been established");
			DebugUtil.Log("Service name: " + xmppConnection.getServiceName() + "---" + xmppConnection.getHost());
		} else {
			DebugUtil.Log("The Server connection has been established, skip connection");
		}
	}

	/**
	 * 连接服务器
	 * 
	 * @throws Exception
	 */
	public static void connect(Context context) throws Exception {
		DebugUtil.Log("Connect the server, if the connection has been established, skip the connection.");
		init(context);
	}

	/**
	 * 得到XMPPConnection实例，若无连接，则会自动尝试连接。
	 * 
	 * @return
	 * @throws Exception
	 */
	public static XMPPConnection getServerConnection(Context context) throws Exception {
		init(context);
		return xmppConnection;
	}

	/**
	 * 注意！当且仅当确保服务器连接被成功建立时才能调用，否则可能报服务器未连接错误
	 * 
	 * @return
	 */
	public static XMPPConnection getConnection() {
		return xmppConnection;
	}

}
