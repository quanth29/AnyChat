package com.xwh.anychat.biz;

/**
 * Created by 萧文翰 on 2015/3/18.
 */
import android.content.Context;

import com.xwh.anychat.entity.AccountInfoEntity;

import org.jivesoftware.smackx.vcardtemp.packet.VCard;

public interface AccountBiz {

	/**
	 * 注册新用户
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * 
	 * @return
	 */
	public boolean registerAccount(String username, String password, Context context);

	/**
	 * 登录
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	public boolean loginAccount(String username, String password, Context context);

	/**
	 * 获取自己的VCard信息（必须保证已登录）
	 * 
	 * @return
	 */
	public VCard getMyVcard(Context context);

	/**
	 * 获得自己的所有账户信息（读取本地数据库）
	 * 
	 * @param context
	 * @return
	 */
	public AccountInfoEntity getMyAccountInfo(Context context, String username);

	/**
	 * 保存自己的所有账户信息（写本地数据库）
	 * 
	 * @param context
	 * @param vCard
	 * @param userName
	 * @return
	 */
	public void saveMyAccountInfo(Context context, VCard vCard, String userName);

	/**
	 * 保存我的资料（仅远程）
	 * 
	 * @param context
	 */
	public boolean saveMyVcard(Context context, VCard vCard);

}
