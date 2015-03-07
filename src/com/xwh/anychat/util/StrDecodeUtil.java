package com.xwh.anychat.util;

public class StrDecodeUtil {
	public static final String HEX_STRING = "0123456789ABCDEF";

	/**
	 * 将16进制编码转换为文字byte[]
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] decodeForUIDisplay(String s) {
		if (s == null || s.length() == 0) {
			return new byte[] {};
		}
		StringBuilder sb = new StringBuilder(s);
		byte[] result = new byte[s.length() / 2];
		for (int i = 0; i < result.length; ++i) {
			result[i] = (byte) Integer.parseInt(sb.substring(i * 2, i * 2 + 2), 16);
		}
		return result;
	}
}
