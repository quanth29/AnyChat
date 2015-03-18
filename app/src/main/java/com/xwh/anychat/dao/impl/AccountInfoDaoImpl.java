package com.xwh.anychat.dao.impl;

/**
 * Created by 萧文翰 on 2015/3/18.
 */
import android.content.Context;

import com.xwh.anychat.dao.AccountInfoDao;
import com.xwh.anychat.db.AnyChatDatabaseHelper;
import com.xwh.anychat.entity.AccountInfoEntity;

import java.util.ArrayList;

public class AccountInfoDaoImpl implements AccountInfoDao {

	private AnyChatDatabaseHelper dbHelper;

	@Override
	public ArrayList<AccountInfoEntity> getAllAccountInfo(Context context) {
		if (dbHelper == null) {
			dbHelper = AnyChatDatabaseHelper.getInstance(context);
		}
		return dbHelper.getAllStoredAccountInfo();
	}

	@Override
	public void addOrUpdateAccountInfo(Context context, AccountInfoEntity accountInfo, boolean onlyPassword) {
		if (dbHelper == null) {
			dbHelper = AnyChatDatabaseHelper.getInstance(context);
		}
		dbHelper.addOrUpdateAccountInfo(accountInfo, onlyPassword);
	}

	@Override
	public String getPasswordByUsername(Context context, String username) {
		if (dbHelper == null) {
			dbHelper = AnyChatDatabaseHelper.getInstance(context);
		}
		return dbHelper.getPasswordByUsername(username);
	}

	@Override
	public void prepareAccountDbData(Context context, String username) {
		if (dbHelper == null) {
			dbHelper = AnyChatDatabaseHelper.getInstance(context);
		}
		dbHelper.prepareAccountDBData(username);
	}
}
