package com.xwh.anychat.entity;

import java.util.ArrayList;
import java.util.HashMap;

public class RosterEntity {

	private HashMap<String, ArrayList<SingleRosterEntity>> allRosterData;

	public RosterEntity() {
		super();
		allRosterData = new HashMap<String, ArrayList<SingleRosterEntity>>();
	}

	public HashMap<String, ArrayList<SingleRosterEntity>> getAllRosterData() {
		return allRosterData;
	}

	/**
	 * 重新设定数据
	 * 
	 * @param rosterEntity
	 */
	public void refreshData(RosterEntity rosterEntity) {
		if (this.allRosterData == null) {
			allRosterData = new HashMap<String, ArrayList<SingleRosterEntity>>();
		}
		allRosterData.clear();
		if (rosterEntity != null && rosterEntity.getAllRosterData() != null && rosterEntity.getAllRosterData().size() > 0) {
			allRosterData.putAll(rosterEntity.getAllRosterData());
		}
	}

	/**
	 * 添加一个好友信息
	 * 
	 * @param name
	 * @param status
	 * @param type
	 * @param user
	 * @param group
	 */
	public void addSingleRosterData(String name, String status, String type, String user, String group) {
		if (allRosterData == null) {
			allRosterData = new HashMap<String, ArrayList<SingleRosterEntity>>();
		}
		if (!allRosterData.containsKey(group)) {
			allRosterData.put(group, new ArrayList<SingleRosterEntity>());
		}
		allRosterData.get(group).add(new SingleRosterEntity(name, status, type, user));
	}

	/**
	 * 判断一个用户是否在好友列表中
	 * 
	 * @param user
	 * @return
	 */
	public boolean contains(String user) {
		if (allRosterData != null && allRosterData.size() > 0) {
			for (ArrayList<SingleRosterEntity> singleRosterEntities : allRosterData.values()) {
				if (singleRosterEntities != null && singleRosterEntities.size() > 0) {
					for (SingleRosterEntity singleRosterEntity : singleRosterEntities) {
						if (singleRosterEntity.getUser().equals(user)) {
							return true;
						}
					}
				}
			}
		} else {
			return false;
		}
		return false;
	}

	/**
	 * 判断一个用户是否在指定的组中
	 * 
	 * @param user
	 * @param group
	 * @return
	 */
	public boolean contains(String user, String group) {
		if (allRosterData != null && allRosterData.size() > 0) {
			if (allRosterData.containsKey(group)) {
				if (allRosterData.get(group) != null && allRosterData.get(group).size() > 0) {
					for (SingleRosterEntity singleRosterEntity : allRosterData.get(group)) {
						if (singleRosterEntity.getUser().equals(user)) {
							return true;
						}
					}
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
		return false;
	}

	public class SingleRosterEntity {

		private String name;
		private String status;
		private String type;
		private String user;

		public SingleRosterEntity() {
			super();
		}

		public SingleRosterEntity(String name, String status, String type, String user) {
			super();
			this.name = name;
			this.status = status;
			this.type = type;
			this.user = user;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		@Override
		public boolean equals(Object o) {

			if (o instanceof SingleRosterEntity) {
				if (((SingleRosterEntity) o).getUser().equals(user)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

	}

}
