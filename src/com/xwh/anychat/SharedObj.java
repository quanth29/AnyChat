package com.xwh.anychat;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

import com.xwh.anychat.entity.RosterEntity;

public class SharedObj {
	/**
	 * 在线用户自身的VCard
	 */
	public static VCard myVcard;
	
	/**
	 * 好友信息
	 */
	public static Roster roster;
	
	/**
	 * 好友列表
	 */
	public static RosterEntity rosterEntity;
}
