package com.xwh.anychat;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

public class SharedObj {
	/**
	 * 在线用户自身的VCard
	 */
	public static VCard myVcard;
	
	/**
	 * 好友信息
	 */
	public static Roster roster;
}
