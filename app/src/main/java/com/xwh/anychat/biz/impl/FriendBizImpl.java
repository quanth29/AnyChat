package com.xwh.anychat.biz.impl;

/**
 * Created by 萧文翰 on 2015/3/18.
 */
import android.content.Context;

import com.xwh.anychat.biz.FriendBiz;
import com.xwh.anychat.db.AnyChatDatabaseHelper;
import com.xwh.anychat.entity.RosterEntity;
import com.xwh.anychat.util.DebugUtil;
import com.xwh.anychat.util.ServerConnectionUtil;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smackx.search.ReportedData;
import org.jivesoftware.smackx.search.ReportedData.Row;
import org.jivesoftware.smackx.search.UserSearchManager;
import org.jivesoftware.smackx.xdata.Form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class FriendBizImpl implements FriendBiz {

	private XMPPConnection xmppConnection;

	private AnyChatDatabaseHelper databaseHelper;

	@Override
	public List<Row> searchPerson(Context context, String searchContent) {

		try {
			xmppConnection = ServerConnectionUtil.getServerConnection(context);
			DebugUtil.Log("Search username: " + searchContent + " at " + xmppConnection.getServiceName());
			UserSearchManager usm = new UserSearchManager(xmppConnection);
			Form searchForm = usm.getSearchForm("search." + xmppConnection.getServiceName());
			Form answerForm = searchForm.createAnswerForm();
			answerForm.setAnswer("Username", true);
			answerForm.setAnswer("search", searchContent);
			ReportedData data = usm.getSearchResults(answerForm, "search." + xmppConnection.getServiceName());
			return data.getRows();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void updateRosterListInfo(Context context, Roster roster, String username) {
		if (databaseHelper == null) {
			databaseHelper = AnyChatDatabaseHelper.getInstance(context);
		}
		// 添加及更新好友
		Collection<RosterGroup> groupList = roster.getGroups();
		for (RosterGroup rosterGroup : groupList) {
			Collection<RosterEntry> rosterList = rosterGroup.getEntries();
			for (RosterEntry rosterEntry : rosterList) {
				databaseHelper.addOrUpdateRosterInfo(username, rosterEntry);
			}
		}
	}

	@Override
	public List<String> getRosterGroupList(Context context, Roster roster) {
		ArrayList<String> returnData = new ArrayList<String>();
		Collection<RosterGroup> groupList = roster.getGroups();
		for (RosterGroup rosterGroup : groupList) {
			returnData.add(rosterGroup.getName());
		}
		return returnData;
	}

	@Override
	public HashSet<String> getRosterGroupList(Context context, String username) {
		if (databaseHelper == null) {
			databaseHelper = AnyChatDatabaseHelper.getInstance(context);
		}
		return databaseHelper.getGroupRosterByUsername(username);
	}

	@Override
	public boolean createNewGroup(Context context, String groupName) {
		try {
			xmppConnection = ServerConnectionUtil.getServerConnection(context);
			xmppConnection.getRoster().createGroup(groupName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addFriend(Context context, String username, String groupName) {
		try {
			xmppConnection = ServerConnectionUtil.getServerConnection(context);
			xmppConnection.getRoster().createEntry(username + "@" + xmppConnection.getServiceName(), username, new String[] { groupName });
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public RosterEntity getAllRoster(Context context, String username) {
		if (databaseHelper == null) {
			databaseHelper = AnyChatDatabaseHelper.getInstance(context);
		}
		return databaseHelper.getAllStoredRosterList(username);
	}

	@Override
	public void removeNotExistRoster(Context context, String username, Roster roster) {
		// 移除已删除的好友
		if (databaseHelper == null) {
			databaseHelper = AnyChatDatabaseHelper.getInstance(context);
		}
		ArrayList<String> allJId = databaseHelper.getAllStoredRosterList(username).getAllUserJId();
		if (allJId != null && allJId.size() > 0 && roster != null) {
			for (int i = 0; i < allJId.size(); i++) {
                if(!roster.contains(allJId.get(i))){
                    databaseHelper.removeRosterInfo(username, allJId.get(i));
                }
			}
		}
	}
}
