package com.xwh.anychat.util;

/**
 * Created by 萧文翰 on 2015/3/18.
 */
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
