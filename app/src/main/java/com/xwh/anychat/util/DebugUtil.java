package com.xwh.anychat.util;

import com.xwh.anychat.BuildConfig;
import com.xwh.anychat.config.Constants;

public class DebugUtil {

	/**
	 * 打印调试信息（Debug级别）
	 * 
	 * @param logStr
	 */
	public static void Log(String logStr) {
		if (BuildConfig.DEBUG) {
			android.util.Log.d(Constants.APP_NAME, logStr);
		}
	}
}
