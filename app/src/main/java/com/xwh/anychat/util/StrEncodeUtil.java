package com.xwh.anychat.util;

public class StrEncodeUtil {
	public static final String HEX_STRING = "0123456789ABCDEF";

	/**
	 * 将文字转换为十六进制编码
	 * 
	 * @param b
	 * @return
	 */
	public static String encodeForDBStore(byte[] b) {
		if (b == null || b.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < b.length; ++i) {
			sb = sb.append(HEX_STRING.charAt(0xf & b[i] >> 4) + "" + HEX_STRING.charAt(b[i] & 0xf));
		}
		return sb.toString();
	}

}
