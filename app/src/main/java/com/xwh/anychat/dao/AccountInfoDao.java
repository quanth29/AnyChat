package com.xwh.anychat.dao;

/**
 * Created by 萧文翰 on 2015/3/18.
 */
import android.content.Context;

import com.xwh.anychat.entity.AccountInfoEntity;

import java.util.ArrayList;

public interface AccountInfoDao {

	/**
	 * 得到所有的登陆过的账户信息
	 * 
	 * @param context
	 * @return
	 */
	public ArrayList<AccountInfoEntity> getAllAccountInfo(Context context);

	/**
	 * 保存账户信息，也可用于更新
	 * 
	 * @param context
	 * @param accountInfo
	 * @param onlyPassword
	 */
	public void addOrUpdateAccountInfo(Context context, AccountInfoEntity accountInfo, boolean onlyPassword);

	/**
	 * 通过用户名获取密码
	 * 
	 * @param context
	 * @param username
	 */
	public String getPasswordByUsername(Context context, String username);

	/**
	 * 准备该用户所有数据表
	 * 
	 * @param context
	 * @param username
	 */
	public void prepareAccountDbData(Context context,String username);

}
