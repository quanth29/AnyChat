package com.xwh.anychat.biz;

import android.content.Context;

import com.xwh.anychat.entity.RosterEntity;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smackx.search.ReportedData.Row;

import java.util.HashSet;
import java.util.List;

public interface FriendBiz {

	/**
	 * 查找服务器上的联系人
	 * 
	 * @param context
	 * @param searchContent
	 * @return
	 */
	public List<Row> searchPerson(Context context, String searchContent);

	/**
	 * 更新好友列表
	 * 
	 * @param context
	 * @param roster
	 * @param username
	 */
	public void updateRosterListInfo(Context context, Roster roster, String username);

	/**
	 * 获取好友分组信息（从Roster对象中解析）
	 * 
	 * @param context
	 * @param roster
	 */
	public List<String> getRosterGroupList(Context context, Roster roster);

	/**
	 * 获取好友分组信息（从本地通过username获取）
	 * 
	 * @param context
	 * @param username
	 * @return
	 */
	public HashSet<String> getRosterGroupList(Context context, String username);

	/**
	 * 建立新好友分组
	 * 
	 * @param context
	 * @param groupName
	 * @return
	 */
	public boolean createNewGroup(Context context, String groupName);

	/**
	 * 添加好友到指定的分组
	 * 
	 * @param context
	 * @param username
	 * @param groupName
	 * @return
	 */
	public boolean addFriend(Context context, String username, String groupName);

	/**
	 * 获取保存到本地的好友列表
	 * 
	 * @param context
	 * @param username
	 * @return
	 */
	public RosterEntity getAllRoster(Context context, String username);

	/**
	 * 本地移除不存在的好友
	 * 
	 * @param context
	 * @param username
	 * @param roster
	 */
	public void removeNotExistRoster(Context context, String username, Roster roster);

}
