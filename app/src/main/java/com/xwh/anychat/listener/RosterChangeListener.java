package com.xwh.anychat.listener;

/**
 * Created by 萧文翰 on 2015/3/18.
 */
import com.xwh.anychat.config.Constants;
import com.xwh.anychat.service.ConnectionService;
import com.xwh.anychat.util.DebugUtil;

import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;

import java.util.Collection;

public class RosterChangeListener implements RosterListener {

	@Override
	public void entriesAdded(Collection<String> addresses) {
		DebugUtil.Log("roster added");
//		ConnectionService.handler.sendEmptyMessage(Constants.SERVICE_LOAD_MY_ROSTER_START);
	}

	@Override
	public void entriesUpdated(Collection<String> addresses) {
		DebugUtil.Log("roster updated");
		ConnectionService.handler.sendEmptyMessage(Constants.SERVICE_LOAD_MY_ROSTER_START);
	}

	@Override
	public void entriesDeleted(Collection<String> addresses) {
		DebugUtil.Log("roster deleted");
//		ConnectionService.handler.sendEmptyMessage(Constants.SERVICE_LOAD_MY_ROSTER_START);
	}

	@Override
	public void presenceChanged(Presence presence) {
		DebugUtil.Log("roster presence changed");
		ConnectionService.handler.sendEmptyMessage(Constants.SERVICE_LOAD_MY_ROSTER_START);
	}

}
