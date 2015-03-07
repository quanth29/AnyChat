package com.xwh.anychat.entity;

import java.util.ArrayList;
import java.util.HashMap;

import org.jivesoftware.smackx.vcardtemp.packet.VCard;

public class RosterInfoEntity {

	private HashMap<String, ArrayList<VCard>> rosterInfoData;

	public RosterInfoEntity() {
		super();
	}

	public RosterInfoEntity(HashMap<String, ArrayList<VCard>> rosterInfoData) {
		super();
		this.rosterInfoData = rosterInfoData;
	}

	public HashMap<String, ArrayList<VCard>> getRosterInfoData() {
		return rosterInfoData;
	}

	public void setRosterInfoData(HashMap<String, ArrayList<VCard>> rosterInfoData) {
		this.rosterInfoData = rosterInfoData;
	}

}
