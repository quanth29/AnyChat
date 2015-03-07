package com.xwh.anychat.db;

import java.util.ArrayList;
import java.util.HashSet;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import com.xwh.anychat.entity.AccountInfoEntity;
import com.xwh.anychat.util.StrDecodeUtil;
import com.xwh.anychat.util.StrEncodeUtil;

public class AnyChatDatabaseHelper extends SQLiteOpenHelper {

	private final static String DB_NAME = "anychatdb";

	private final static int DB_VERSION = 1;

	// -----------------------------------------------------------------------------------------------------------
	// 应用登录信息
	private final String TABLE_ACCOUNT = "table_account";

	private final String TABLE_ACCOUNT_USERNAME = "username";

	private final String TABLE_ACCOUNT_PASSWORD = "password";

	private final String TABLE_ACCOUNT_AVATOR = "avator";

	private final String TABLE_ACCOUNT_NICKNAME = "nickname";

	private final String TABLE_ACCOUNT_HOME_ADDRESS = "homeaddr";

	private final String TABLE_ACCOUNT_WORK_ADDRESS = "workaddr";

	private final String TABLE_ACCOUNT_HOME_EMAIL = "homeemail";

	private final String TABLE_ACCOUNT_WORK_EMAIL = "workemail";

	private final String TABLE_ACCOUNT_FIRST_NAME = "firstname";

	private final String TABLE_ACCOUNT_MIDDLE_NAME = "midname";

	private final String TABLE_ACCOUNT_LAST_NAME = "lastname";

	private final String TABLE_ACCOUNT_JID = "jid";

	private final String TABLE_ACCOUNT_ORGANIZATION = "organization";

	private final String TABLE_ACCOUNT_ORGANIZAITON_UNIT = "organizationunit";

	private final String TABLE_ACCOUNT_HOME_PHONE = "homephone";

	private final String TABLE_ACCOUNT_WORK_PHONE = "workphone";

	// -----------------------------------------------------------------------------------------------------------
	// 好友列表
	private final String TABLE_ROSTER = "table_roster";

	private final String TABLE_ROSTER_GROUP = "mygroup";

	private final String TABLE_ROSTER_USERNAME = "username";

	private final String TABLE_ROSTER_AVATOR = "avator";

	private final String TABLE_ROSTER_NICKNAME = "nickname";

	private final String TABLE_ROSTER_HOME_ADDRESS = "homeaddr";

	private final String TABLE_ROSTER_WORK_ADDRESS = "workaddr";

	private final String TABLE_ROSTER_HOME_EMAIL = "homeemail";

	private final String TABLE_ROSTER_WORK_EMAIL = "workemail";

	private final String TABLE_ROSTER_FIRST_NAME = "firstname";

	private final String TABLE_ROSTER_MIDDLE_NAME = "midname";

	private final String TABLE_ROSTER_LAST_NAME = "lastname";

	private final String TABLE_ROSTER_JID = "jid";

	private final String TABLE_ROSTER_ORGANIZATION = "organization";

	private final String TABLE_ROSTER_ORGANIZAITON_UNIT = "organizationunit";

	private final String TABLE_ROSTER_HOME_PHONE = "homephone";

	private final String TABLE_ROSTER_WORK_PHONE = "workphone";

	private final String TABLE_ROSTER_STATUS = "status";

	// -----------------------------------------------------------------------------------------------------------

	private static AnyChatDatabaseHelper instance;

	public AnyChatDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, DB_NAME, factory, version);
	}

	private AnyChatDatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	public static AnyChatDatabaseHelper getInstance(Context context) {
		if (instance == null) {
			instance = new AnyChatDatabaseHelper(context);
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 用户账户列表
		db.beginTransaction();
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ACCOUNT + "(_id INTEGER PRIMARY KEY," + TABLE_ACCOUNT_AVATOR + " VERCHAR," + TABLE_ACCOUNT_FIRST_NAME + " VERCHAR,"
				+ TABLE_ACCOUNT_HOME_ADDRESS + " VERCHAR," + TABLE_ACCOUNT_HOME_EMAIL + " VERCHAR," + TABLE_ACCOUNT_HOME_PHONE + " VERCHAR," + TABLE_ACCOUNT_JID + " VERCHAR,"
				+ TABLE_ACCOUNT_LAST_NAME + " VERCHAR," + TABLE_ACCOUNT_MIDDLE_NAME + " VERCHAR," + TABLE_ACCOUNT_NICKNAME + " VERCHAR," + TABLE_ACCOUNT_ORGANIZAITON_UNIT + " VERCHAR,"
				+ TABLE_ACCOUNT_ORGANIZATION + " VERCHAR," + TABLE_ACCOUNT_PASSWORD + " VERCHAR," + TABLE_ACCOUNT_USERNAME + " VERCHAR," + TABLE_ACCOUNT_WORK_ADDRESS + " VERCHAR,"
				+ TABLE_ACCOUNT_WORK_EMAIL + " VERCHAR," + TABLE_ACCOUNT_WORK_PHONE + " VERCHAR)");
		db.setTransactionSuccessful();
		db.endTransaction();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	/**
	 * 得到所有保存的账户信息
	 * 
	 * @return
	 */
	public ArrayList<AccountInfoEntity> getAllStoredAccountInfo() {
		ArrayList<AccountInfoEntity> accountInfos = new ArrayList<AccountInfoEntity>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ACCOUNT, null);
		if (cursor == null || cursor.getCount() == 0) {
			cursor.close();
			db.close();
			return accountInfos;
		}
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			AccountInfoEntity accountInfo = new AccountInfoEntity();
			accountInfo.setUsername(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_USERNAME)))));
			accountInfo.setAvator(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_AVATOR))));
			accountInfo.setHomeAddress(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_HOME_ADDRESS)))));
			accountInfo.setHomeEmail(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_HOME_EMAIL)))));
			accountInfo.setHomePhone(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_HOME_PHONE)))));
			accountInfo.setjID(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_JID)))));
			accountInfo.setLastName(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_LAST_NAME)))));
			accountInfo.setMidName(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_MIDDLE_NAME)))));
			accountInfo.setNickName(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_NICKNAME)))));
			accountInfo.setOrganization(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_ORGANIZATION)))));
			accountInfo.setOrganizationUnit(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_ORGANIZAITON_UNIT)))));
			accountInfo.setPassword(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_PASSWORD)))));
			accountInfo.setWorkAddress(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_WORK_ADDRESS)))));
			accountInfo.setWorkEmail(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_WORK_EMAIL)))));
			accountInfo.setWorkPhone(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_WORK_PHONE)))));
			accountInfo.setFirstName(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_FIRST_NAME)))));
			accountInfos.add(accountInfo);
		}
		cursor.close();
		db.close();
		return accountInfos;
	}

	/**
	 * 添加或更新帐号（完整的信息更新）
	 * 
	 * @param accountInfo
	 */
	public void addOrUpdateAccountInfo(AccountInfoEntity accountInfo, boolean onlyPassword) {
		String username = accountInfo.getUsername();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ACCOUNT, null);
		if (cursor == null || cursor.getCount() == 0) {
			cursor.close();
			// 该账户不存在于记录中，添加新的
			ContentValues values = new ContentValues();
			values.put(TABLE_ACCOUNT_AVATOR, StrEncodeUtil.encodeForDBStore(accountInfo.getAvator()));
			values.put(TABLE_ACCOUNT_FIRST_NAME, StrEncodeUtil.encodeForDBStore(accountInfo.getFirstName().getBytes()));
			values.put(TABLE_ACCOUNT_HOME_ADDRESS, StrEncodeUtil.encodeForDBStore(accountInfo.getHomeAddress().getBytes()));
			values.put(TABLE_ACCOUNT_HOME_EMAIL, StrEncodeUtil.encodeForDBStore(accountInfo.getHomeEmail().getBytes()));
			values.put(TABLE_ACCOUNT_HOME_PHONE, StrEncodeUtil.encodeForDBStore(accountInfo.getHomePhone().getBytes()));
			values.put(TABLE_ACCOUNT_JID, StrEncodeUtil.encodeForDBStore(accountInfo.getjID().getBytes()));
			values.put(TABLE_ACCOUNT_LAST_NAME, StrEncodeUtil.encodeForDBStore(accountInfo.getLastName().getBytes()));
			values.put(TABLE_ACCOUNT_MIDDLE_NAME, StrEncodeUtil.encodeForDBStore(accountInfo.getMidName().getBytes()));
			values.put(TABLE_ACCOUNT_NICKNAME, StrEncodeUtil.encodeForDBStore(accountInfo.getNickName().getBytes()));
			values.put(TABLE_ACCOUNT_ORGANIZAITON_UNIT, StrEncodeUtil.encodeForDBStore(accountInfo.getOrganizationUnit().getBytes()));
			values.put(TABLE_ACCOUNT_ORGANIZATION, StrEncodeUtil.encodeForDBStore(accountInfo.getOrganization().getBytes()));
			values.put(TABLE_ACCOUNT_PASSWORD, StrEncodeUtil.encodeForDBStore(accountInfo.getPassword().getBytes()));
			values.put(TABLE_ACCOUNT_USERNAME, StrEncodeUtil.encodeForDBStore(accountInfo.getUsername().getBytes()));
			values.put(TABLE_ACCOUNT_WORK_ADDRESS, StrEncodeUtil.encodeForDBStore(accountInfo.getWorkAddress().getBytes()));
			values.put(TABLE_ACCOUNT_WORK_PHONE, StrEncodeUtil.encodeForDBStore(accountInfo.getWorkPhone().getBytes()));
			values.put(TABLE_ACCOUNT_WORK_EMAIL, StrEncodeUtil.encodeForDBStore(accountInfo.getWorkEmail().getBytes()));
			db.insert(TABLE_ACCOUNT, null, values);
			db.close();
			return;
		}
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			if (username.equals(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_USERNAME)))))) {
				// 存在该账户的登陆记录，更新
				ContentValues values = new ContentValues();
				if (!onlyPassword) {
					values.put(TABLE_ACCOUNT_AVATOR, StrEncodeUtil.encodeForDBStore(accountInfo.getAvator()));
					values.put(TABLE_ACCOUNT_FIRST_NAME, StrEncodeUtil.encodeForDBStore(accountInfo.getFirstName().getBytes()));
					values.put(TABLE_ACCOUNT_HOME_ADDRESS, StrEncodeUtil.encodeForDBStore(accountInfo.getHomeAddress().getBytes()));
					values.put(TABLE_ACCOUNT_HOME_EMAIL, StrEncodeUtil.encodeForDBStore(accountInfo.getHomeEmail().getBytes()));
					values.put(TABLE_ACCOUNT_HOME_PHONE, StrEncodeUtil.encodeForDBStore(accountInfo.getHomePhone().getBytes()));
					values.put(TABLE_ACCOUNT_JID, StrEncodeUtil.encodeForDBStore(accountInfo.getjID().getBytes()));
					values.put(TABLE_ACCOUNT_LAST_NAME, StrEncodeUtil.encodeForDBStore(accountInfo.getLastName().getBytes()));
					values.put(TABLE_ACCOUNT_MIDDLE_NAME, StrEncodeUtil.encodeForDBStore(accountInfo.getMidName().getBytes()));
					values.put(TABLE_ACCOUNT_NICKNAME, StrEncodeUtil.encodeForDBStore(accountInfo.getNickName().getBytes()));
					values.put(TABLE_ACCOUNT_ORGANIZAITON_UNIT, StrEncodeUtil.encodeForDBStore(accountInfo.getOrganizationUnit().getBytes()));
					values.put(TABLE_ACCOUNT_ORGANIZATION, StrEncodeUtil.encodeForDBStore(accountInfo.getOrganization().getBytes()));
					values.put(TABLE_ACCOUNT_WORK_ADDRESS, StrEncodeUtil.encodeForDBStore(accountInfo.getWorkAddress().getBytes()));
					values.put(TABLE_ACCOUNT_WORK_PHONE, StrEncodeUtil.encodeForDBStore(accountInfo.getWorkPhone().getBytes()));
					values.put(TABLE_ACCOUNT_WORK_EMAIL, StrEncodeUtil.encodeForDBStore(accountInfo.getWorkEmail().getBytes()));
				} else {
					values.put(TABLE_ACCOUNT_PASSWORD, StrEncodeUtil.encodeForDBStore(accountInfo.getPassword().getBytes()));
				}
				cursor.close();
				db.update(TABLE_ACCOUNT, values, TABLE_ACCOUNT_USERNAME + "=\'" + new String(StrEncodeUtil.encodeForDBStore(username.getBytes())) + "\'", null);
				db.close();
				return;
			}
		}
		cursor.close();
		// 该账户不存在记录中，添加新的
		ContentValues values = new ContentValues();
		values.put(TABLE_ACCOUNT_AVATOR, StrEncodeUtil.encodeForDBStore(accountInfo.getAvator()));
		values.put(TABLE_ACCOUNT_FIRST_NAME, StrEncodeUtil.encodeForDBStore(accountInfo.getFirstName().getBytes()));
		values.put(TABLE_ACCOUNT_HOME_ADDRESS, StrEncodeUtil.encodeForDBStore(accountInfo.getHomeAddress().getBytes()));
		values.put(TABLE_ACCOUNT_HOME_EMAIL, StrEncodeUtil.encodeForDBStore(accountInfo.getHomeEmail().getBytes()));
		values.put(TABLE_ACCOUNT_HOME_PHONE, StrEncodeUtil.encodeForDBStore(accountInfo.getHomePhone().getBytes()));
		values.put(TABLE_ACCOUNT_JID, StrEncodeUtil.encodeForDBStore(accountInfo.getjID().getBytes()));
		values.put(TABLE_ACCOUNT_LAST_NAME, StrEncodeUtil.encodeForDBStore(accountInfo.getLastName().getBytes()));
		values.put(TABLE_ACCOUNT_MIDDLE_NAME, StrEncodeUtil.encodeForDBStore(accountInfo.getMidName().getBytes()));
		values.put(TABLE_ACCOUNT_NICKNAME, StrEncodeUtil.encodeForDBStore(accountInfo.getNickName().getBytes()));
		values.put(TABLE_ACCOUNT_ORGANIZAITON_UNIT, StrEncodeUtil.encodeForDBStore(accountInfo.getOrganizationUnit().getBytes()));
		values.put(TABLE_ACCOUNT_ORGANIZATION, StrEncodeUtil.encodeForDBStore(accountInfo.getOrganization().getBytes()));
		values.put(TABLE_ACCOUNT_PASSWORD, StrEncodeUtil.encodeForDBStore(accountInfo.getPassword().getBytes()));
		values.put(TABLE_ACCOUNT_USERNAME, StrEncodeUtil.encodeForDBStore(accountInfo.getUsername().getBytes()));
		values.put(TABLE_ACCOUNT_WORK_ADDRESS, StrEncodeUtil.encodeForDBStore(accountInfo.getWorkAddress().getBytes()));
		values.put(TABLE_ACCOUNT_WORK_PHONE, StrEncodeUtil.encodeForDBStore(accountInfo.getWorkPhone().getBytes()));
		values.put(TABLE_ACCOUNT_WORK_EMAIL, StrEncodeUtil.encodeForDBStore(accountInfo.getWorkEmail().getBytes()));
		db.insert(TABLE_ACCOUNT, null, values);
		db.close();
		return;
	}

	/**
	 * 添加或更新好友信息
	 * 
	 * @param username
	 * @param vCard
	 * @param rosterUsername
	 * @param group
	 * @param status
	 */
	public void addOrUpdateRosterInfo(String username, VCard vCard, String rosterUsername, String group, String status) {
		username = StrEncodeUtil.encodeForDBStore(username.getBytes());
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ROSTER + "_" + username, null);
		ContentValues values = new ContentValues();
		values.put(TABLE_ROSTER_GROUP, StrEncodeUtil.encodeForDBStore(group.getBytes()));
		values.put(TABLE_ROSTER_USERNAME, StrEncodeUtil.encodeForDBStore(rosterUsername.getBytes()));
		values.put(TABLE_ROSTER_AVATOR, StrEncodeUtil.encodeForDBStore(vCard.getAvatar()));
		values.put(TABLE_ROSTER_FIRST_NAME, StrEncodeUtil.encodeForDBStore(vCard.getFirstName().getBytes()));
		values.put(TABLE_ROSTER_HOME_ADDRESS, StrEncodeUtil.encodeForDBStore(vCard.getAddressFieldHome("EXTADR").getBytes()));
		values.put(TABLE_ROSTER_HOME_EMAIL, StrEncodeUtil.encodeForDBStore(vCard.getEmailHome().getBytes()));
		values.put(TABLE_ROSTER_HOME_PHONE, StrEncodeUtil.encodeForDBStore(vCard.getPhoneHome("CELL").getBytes()));
		values.put(TABLE_ROSTER_JID, StrEncodeUtil.encodeForDBStore(vCard.getJabberId().getBytes()));
		values.put(TABLE_ROSTER_LAST_NAME, StrEncodeUtil.encodeForDBStore(vCard.getLastName().getBytes()));
		values.put(TABLE_ROSTER_MIDDLE_NAME, StrEncodeUtil.encodeForDBStore(vCard.getMiddleName().getBytes()));
		values.put(TABLE_ROSTER_NICKNAME, StrEncodeUtil.encodeForDBStore(vCard.getNickName().getBytes()));
		values.put(TABLE_ROSTER_ORGANIZAITON_UNIT, StrEncodeUtil.encodeForDBStore(vCard.getOrganizationUnit().getBytes()));
		values.put(TABLE_ROSTER_ORGANIZATION, StrEncodeUtil.encodeForDBStore(vCard.getOrganization().getBytes()));
		values.put(TABLE_ROSTER_WORK_ADDRESS, StrEncodeUtil.encodeForDBStore(vCard.getAddressFieldWork("EXTADR").getBytes()));
		values.put(TABLE_ROSTER_WORK_PHONE, StrEncodeUtil.encodeForDBStore(vCard.getPhoneWork("CELL").getBytes()));
		values.put(TABLE_ROSTER_WORK_EMAIL, StrEncodeUtil.encodeForDBStore(vCard.getEmailWork().getBytes()));
		values.put(TABLE_ROSTER_STATUS, StrEncodeUtil.encodeForDBStore(status.getBytes()));
		if (cursor == null || cursor.getCount() == 0) {
			cursor.close();
			// 该好友不存在于记录中，添加新的
			db.insert(TABLE_ROSTER, null, values);
			db.close();
			return;
		}
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			if (rosterUsername.equals(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ROSTER_USERNAME)))))) {
				// 存在该好友信息记录，更新
				values.remove(TABLE_ROSTER_USERNAME);
				cursor.close();
				db.update(TABLE_ACCOUNT, values, TABLE_ROSTER_USERNAME + "=\'" + new String(StrEncodeUtil.encodeForDBStore(rosterUsername.getBytes())) + "\'", null);
				db.close();
				return;
			}
		}
		// 不存在该好友记录，添加新的
		cursor.close();
		db.insert(TABLE_ROSTER, null, values);
		db.close();
		return;
	}

	/**
	 * 创建该用户应有的所有数据表
	 * 
	 * @param encodeForDBStore
	 */
	public void prepareAccountDBData(String userName) {
		SQLiteDatabase db = this.getReadableDatabase();
		// 好友列表
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ROSTER + "_" + StrEncodeUtil.encodeForDBStore(userName.getBytes()) + "(_id INTEGER PRIMARY KEY," + TABLE_ROSTER_USERNAME + " VERCHAR,"
				+ TABLE_ROSTER_NICKNAME + " VERCHAR," + TABLE_ROSTER_GROUP + " VERCHAR," + TABLE_ROSTER_AVATOR + " VERCHAR," + TABLE_ROSTER_FIRST_NAME + " VERCHAR," + TABLE_ROSTER_HOME_ADDRESS
				+ " VERCHAR," + TABLE_ROSTER_HOME_EMAIL + " VERCHAR," + TABLE_ROSTER_HOME_PHONE + " VERCHAR," + TABLE_ROSTER_JID + " VERCHAR," + TABLE_ROSTER_LAST_NAME + " VERCHAR,"
				+ TABLE_ROSTER_MIDDLE_NAME + " VERCHAR," + TABLE_ROSTER_ORGANIZAITON_UNIT + " VERCHAR," + TABLE_ROSTER_ORGANIZATION + " VERCHAR," + TABLE_ROSTER_WORK_ADDRESS + " VERCHAR,"
				+ TABLE_ROSTER_WORK_EMAIL + " VERCHAR," + TABLE_ROSTER_WORK_PHONE + " VERCHAR," + TABLE_ROSTER_STATUS + " VERCHAR)");
		db.close();
	}

	/**
	 * 通过用户名获取用户密码
	 * 
	 * @param username
	 * @return
	 */
	public String getPasswordByUsername(String username) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ACCOUNT, null);
		if (cursor == null || cursor.getCount() == 0) {
			cursor.close();
			db.close();
			return "";
		} else {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				if (new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_USERNAME)))).equals(username)) {
					String password = new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_PASSWORD))));
					cursor.close();
					db.close();
					return password;
				}
			}
			return "";
		}
	}

	/**
	 * 通过用户名获取该账户所有个人信息
	 * 
	 * @param username
	 * @return
	 */
	public AccountInfoEntity getAccountInfoByUsername(String username) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ACCOUNT, null);
		if (cursor == null || cursor.getCount() == 0) {
			cursor.close();
			db.close();
			return null;
		} else {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				if (new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_USERNAME)))).equals(username)) {
					AccountInfoEntity accountInfo = new AccountInfoEntity();
					accountInfo.setUsername(username);
					accountInfo.setPassword(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_PASSWORD)))));
					accountInfo.setAvator(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_AVATOR))));
					accountInfo.setFirstName(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_FIRST_NAME)))));
					accountInfo.setHomeAddress(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_HOME_ADDRESS)))));
					accountInfo.setHomeEmail(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_HOME_EMAIL)))));
					accountInfo.setHomePhone(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_HOME_PHONE)))));
					accountInfo.setjID(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_JID)))));
					accountInfo.setLastName(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_LAST_NAME)))));
					accountInfo.setMidName(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_MIDDLE_NAME)))));
					accountInfo.setNickName(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_NICKNAME)))));
					accountInfo.setOrganization(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_ORGANIZATION)))));
					accountInfo.setOrganizationUnit(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_ORGANIZAITON_UNIT)))));
					accountInfo.setWorkAddress(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_WORK_ADDRESS)))));
					accountInfo.setWorkEmail(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_WORK_EMAIL)))));
					accountInfo.setWorkPhone(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ACCOUNT_WORK_PHONE)))));
					cursor.close();
					db.close();
					return accountInfo;
				}
			}
			return null;
		}
	}

	/**
	 * 通过登陆的用户名获取该账户的好友分组信息
	 * 
	 * @param username
	 * @return
	 */
	public HashSet<String> getGroupRosterByUsername(String username) {
		HashSet<String> groupList = new HashSet<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ROSTER + "_" + StrEncodeUtil.encodeForDBStore(username.getBytes()), null);
		if (cursor == null || cursor.getCount() == 0) {
			cursor.close();
			db.close();
			return groupList;
		} else {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				groupList.add(new String(StrDecodeUtil.decodeForUIDisplay(cursor.getString(cursor.getColumnIndex(TABLE_ROSTER_GROUP)))));
			}
			cursor.close();
			db.close();
			return groupList;
		}
	}
}
