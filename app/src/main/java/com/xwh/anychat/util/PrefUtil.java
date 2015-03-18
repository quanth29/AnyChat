package com.xwh.anychat.util;

/**
 * Created by 萧文翰 on 2015/3/18.
 */
import android.content.Context;
import android.content.SharedPreferences;

import com.xwh.anychat.config.Constants;

public class PrefUtil {

	private static SharedPreferences sharedPreferences;

	private static void init(Context context) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(Constants.SHAREDPRREF_NAME, 0);
		}
	}

	/**
	 * 存储用户名（仅用于自动登录）
	 * 
	 * @param context
	 * @param username
	 */
	public static void storeUsername(Context context, String username) {
		init(context);
		sharedPreferences.edit().putString(Constants.PREF_USERNAME, username).commit();
	}

	/**
	 * 取得存储的用户名（仅用于自动登录）
	 * 
	 * @param context
	 * @return
	 */
	public static String getUsername(Context context) {
		init(context);
		return sharedPreferences.getString(Constants.PREF_USERNAME, null);
	}

	/**
	 * 存储自动登录状态（仅用于自动登录）
	 * 
	 * @param context
	 * @param password
	 */
	public static void storeAutoLogin(Context context, boolean isAutoLogin) {
		init(context);
		sharedPreferences.edit().putBoolean(Constants.PREF_AUTOLOGIN, isAutoLogin).commit();
	}

	/**
	 * 取得自动登录状态（仅用于自动登录）
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getAutoLogin(Context context) {
		init(context);
		return sharedPreferences.getBoolean(Constants.PREF_AUTOLOGIN, false);
	}

	/**
	 * 存储服务器IP地址
	 * 
	 * @param context
	 * @param serverIP
	 */
	public static void storeServerIP(Context context, String serverIP) {
		init(context);
		sharedPreferences.edit().putString(Constants.SERVER_IP, serverIP).commit();
	}

	/**
	 * 存储服务器端口号
	 * 
	 * @param context
	 * @param serverPort
	 */
	public static void storeServerPort(Context context, int serverPort) {
		init(context);
		sharedPreferences.edit().putInt(Constants.SERVER_PORT, serverPort).commit();
	}

	/**
	 * 取得存储的服务器IP
	 * 
	 * @param context
	 * @return
	 */
	public static String getServerIP(Context context) {
		init(context);
		return sharedPreferences.getString(Constants.SERVER_IP, null);
	}

	/**
	 * 取得服务器的端口号
	 * 
	 * @param context
	 * @return
	 */
	public static int getServerPort(Context context) {
		init(context);
		return sharedPreferences.getInt(Constants.SERVER_PORT, -1);
	}
}
