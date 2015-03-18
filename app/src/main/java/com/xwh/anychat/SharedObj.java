package com.xwh.anychat;

/**
 * Created by 萧文翰 on 2015/3/18.
 */
import com.xwh.anychat.entity.RosterEntity;

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
	
	/**
	 * 好友列表
	 */
	public static RosterEntity rosterEntity;
}
