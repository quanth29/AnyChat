package com.xwh.anychat.listener;

import java.util.Collection;

import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;

import com.xwh.anychat.util.DebugUtil;

public class RosterChangeListener implements RosterListener {

	@Override
	public void entriesAdded(Collection<String> addresses) {
		DebugUtil.Log("roster added");
	}

	@Override
	public void entriesUpdated(Collection<String> addresses) {
		DebugUtil.Log("roster updated");
	}

	@Override
	public void entriesDeleted(Collection<String> addresses) {
		DebugUtil.Log("roster deleted");
	}

	@Override
	public void presenceChanged(Presence presence) {
		DebugUtil.Log("roster presence changed");
	}

}
