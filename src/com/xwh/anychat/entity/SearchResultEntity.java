package com.xwh.anychat.entity;

import org.jivesoftware.smackx.search.ReportedData.Row;

public class SearchResultEntity {
	private String jid;
	private String username;
	private String name;
	private String email;

	public SearchResultEntity() {
		super();
	}

	/**
	 * 从搜索结果Row对象解析生成SearchResult对象
	 * 
	 * @param row
	 */
	public SearchResultEntity(Row row) {
		super();
		this.jid = row.getValues("jid").get(0);
		this.username = row.getValues("Username").get(0);
		this.name = row.getValues("Name").get(0);
		this.email = row.getValues("Email").get(0);
	}

	public SearchResultEntity(String jid, String username, String name, String email) {
		super();
		this.jid = jid;
		this.username = username;
		this.name = name;
		this.email = email;
	}

	public String getJid() {
		if (this.jid == null) {
			return "";
		}
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	public String getUsername() {
		if (this.username == null) {
			return "";
		}
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		if (this.name == null) {
			return "";
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		if (this.email == null) {
			return "";
		}
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return this.username + " (" + this.jid + ") ";
	}
}
