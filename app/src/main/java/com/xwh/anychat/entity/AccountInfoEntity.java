package com.xwh.anychat.entity;

import org.jivesoftware.smackx.vcardtemp.packet.VCard;

import java.io.Serializable;

public class AccountInfoEntity implements Serializable {

	private static final long serialVersionUID = -8371184448015750470L;

	private String username;
	private String password;
	private byte[] avator;
	private String nickName;
	private String homeAddress;
	private String workAddress;
	private String homeEmail;
	private String workEmail;
	private String firstName;
	private String midName;
	private String lastName;
	private String jID;
	private String organization;
	private String organizationUnit;
	private String homePhone;
	private String workPhone;

	private VCard vCard;

	public AccountInfoEntity(String username, String password, byte[] avator, String nickName, String homeAddress, String workAddress, String homeEmail, String workEmail, String firstName, String midName,
			String lastName, String jID, String organization, String organizationUnit, String homePhone, String workPhone) {
		super();
		this.username = username;
		this.password = password;
		this.avator = avator;
		this.nickName = nickName;
		this.homeAddress = homeAddress;
		this.workAddress = workAddress;
		this.homeEmail = homeEmail;
		this.workEmail = workEmail;
		this.firstName = firstName;
		this.midName = midName;
		this.lastName = lastName;
		this.jID = jID;
		this.organization = organization;
		this.organizationUnit = organizationUnit;
		this.homePhone = homePhone;
		this.workPhone = workPhone;
	}

	public AccountInfoEntity() {
		super();
	}

	/**
	 * 从现有AccountInfo对象解析生成VCard对象，不包含用户名及密码
	 * 
	 * @return
	 */
	public VCard genVcard() {
		if (vCard != null) {
			vCard = null;
		}
		vCard = new VCard();
		vCard.setAddressFieldHome("EXTADR", homeAddress);
		vCard.setAddressFieldWork("EXTADR", workAddress);
		vCard.setAvatar(avator);
		vCard.setEmailHome(homeEmail);
		vCard.setEmailWork(workEmail);
		vCard.setFirstName(firstName);
		vCard.setMiddleName(midName);
		vCard.setLastName(lastName);
		vCard.setNickName(nickName);
		vCard.setJabberId(jID);
		vCard.setOrganization(organization);
		vCard.setOrganizationUnit(organizationUnit);
		vCard.setPhoneHome("CELL", homePhone);
		vCard.setPhoneWork("CELL", workPhone);
		return vCard;
	}

	/**
	 * 从一个现有的Vcard解析生成AccountInfo对象，不包含密码信息
	 * 
	 * @param vCard
	 */
	public AccountInfoEntity(VCard vCard, String username) {
		super();
		this.username = username;
		this.password = null;
		this.avator = vCard.getAvatar();
		this.nickName = vCard.getNickName();
		this.homeAddress = vCard.getAddressFieldHome("EXTADR");
		this.workAddress = vCard.getAddressFieldWork("EXTADR");
		this.homeEmail = vCard.getEmailHome();
		this.workEmail = vCard.getEmailWork();
		this.firstName = vCard.getFirstName();
		this.midName = vCard.getMiddleName();
		this.lastName = vCard.getLastName();
		this.jID = vCard.getJabberId();
		this.organization = vCard.getOrganization();
		this.organizationUnit = vCard.getOrganizationUnit();
		this.homePhone = vCard.getPhoneHome("CELL");
		this.workPhone = vCard.getPhoneWork("CELL");
	}

	public String getUsername() {
		if (username == null) {
			return "";
		}
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		if (password == null) {
			return "";
		}
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte[] getAvator() {
		if (avator == null) {
			return new byte[] {};
		}
		return avator;
	}

	public void setAvator(byte[] avator) {
		this.avator = avator;
	}

	public String getNickName() {
		if (nickName == null) {
			return "";
		}
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHomeAddress() {
		if (homeAddress == null) {
			return "";
		}
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getWorkAddress() {
		if (workAddress == null) {
			return "";
		}
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public String getHomeEmail() {
		if (homeEmail == null) {
			return "";
		}
		return homeEmail;
	}

	public void setHomeEmail(String homeEmail) {
		this.homeEmail = homeEmail;
	}

	public String getWorkEmail() {
		if (workEmail == null) {
			return "";
		}
		return workEmail;
	}

	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}

	public String getFirstName() {
		if (firstName == null) {
			return "";
		}
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMidName() {
		if (midName == null) {
			return "";
		}
		return midName;
	}

	public void setMidName(String midName) {
		this.midName = midName;
	}

	public String getLastName() {
		if (lastName == null) {
			return "";
		}
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getjID() {
		if (jID == null) {
			return "";
		}
		return jID;
	}

	public void setjID(String jID) {
		this.jID = jID;
	}

	public String getOrganization() {
		if (organization == null) {
			return "";
		}
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOrganizationUnit() {
		if (organizationUnit == null) {
			return "";
		}
		return organizationUnit;
	}

	public void setOrganizationUnit(String organizationUnit) {
		this.organizationUnit = organizationUnit;
	}

	public String getHomePhone() {
		if (homePhone == null) {
			return "";
		}
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getWorkPhone() {
		if (workPhone == null) {
			return "";
		}
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof AccountInfoEntity) {
			if (((AccountInfoEntity) o).getUsername().equals(username)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
