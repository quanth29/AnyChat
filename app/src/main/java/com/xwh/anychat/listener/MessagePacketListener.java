package com.xwh.anychat.listener;

/**
 * Created by 萧文翰 on 2015/3/18.
 */
import com.xwh.anychat.util.DebugUtil;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;

public class MessagePacketListener implements PacketListener, PacketFilter {

	// PacketListener回调
	@Override
	public void processPacket(Packet packet) throws NotConnectedException {
		DebugUtil.Log(packet.toXML().toString());
	}

	// PacketFilter回调
	@Override
	public boolean accept(Packet packet) {
		return false;
	}

}
