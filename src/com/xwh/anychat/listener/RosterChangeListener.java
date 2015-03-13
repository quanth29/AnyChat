package com.xwh.anychat.listener;

import java.util.Collection;

import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;

import com.xwh.anychat.config.Constants;
import com.xwh.anychat.service.ConnectionService;
import com.xwh.anychat.util.DebugUtil;

public class RosterChangeListener implements RosterListener {

	@Override
	public void entriesAdded(Collection<String> addresses) {
		DebugUtil.Log("roster added");
		ConnectionService.handler.sendEmptyMessage(Constants.SERVICE_LOAD_MY_ROSTER_START);
	}

	@Override
	public void entriesUpdated(Collection<String> addresses) {
		DebugUtil.Log("roster updated");
		ConnectionService.handler.sendEmptyMessage(Constants.SERVICE_LOAD_MY_ROSTER_START);
	}

	@Override
	public void entriesDeleted(Collection<String> addresses) {
		DebugUtil.Log("roster deleted");
		ConnectionService.handler.sendEmptyMessage(Constants.SERVICE_LOAD_MY_ROSTER_START);
	}

	@Override
	public void presenceChanged(Presence presence) {
		DebugUtil.Log("roster presence changed");
		ConnectionService.handler.sendEmptyMessage(Constants.SERVICE_LOAD_MY_ROSTER_START);
	}

}
