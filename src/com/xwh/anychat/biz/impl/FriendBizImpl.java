package com.xwh.anychat.biz.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smackx.search.ReportedData;
import org.jivesoftware.smackx.search.UserSearchManager;
import org.jivesoftware.smackx.search.ReportedData.Row;
import org.jivesoftware.smackx.xdata.Form;

import android.content.Context;

import com.xwh.anychat.biz.FriendBiz;
import com.xwh.anychat.db.AnyChatDatabaseHelper;
import com.xwh.anychat.util.DebugUtil;
import com.xwh.anychat.util.ServerConnectionUtil;

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
		Collection<RosterGroup> groupList = roster.getGroups();
		for (RosterGroup rosterGroup : groupList) {
			Collection<RosterEntry> rosterList = rosterGroup.getEntries();
			for (RosterEntry rosterEntry : rosterList) {
				// rosterEntry.
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
}
