package com.xwh.anychat.config;

public class Constants {

	/**
	 * 应用名
	 */
	public static final String APP_NAME = "AnyChat";

	/**
	 * 首选项名称
	 */
	public static final String SHAREDPRREF_NAME = "anyChat";

	/**
	 * 首选项——用户名（仅用于自动登录）
	 */
	public static final String PREF_USERNAME = "username";

	/**
	 * 首选项——是否自动登录（仅用于自动登录）
	 */
	public static final String PREF_AUTOLOGIN = "autologin";

	/**
	 * 服务器IP
	 */
	public static final String SERVER_IP = "server_ip";

	/**
	 * 服务器端口号
	 */
	public static final String SERVER_PORT = "server_port";

	/**
	 * 查看详细结果用户名
	 */
	public static final String VCARD_USERNAME = "username";

	/**
	 * 我的资料
	 */
	public static final String MY_PROFILE_USER_DATA = "account_info";

	/**
	 * 通用的文件管理器选取模式
	 */
	public static final String FILE_EXPLORER_SELECT_MODE = "mode";

	/**
	 * 文件管理器起始位置
	 */
	public static final String FILE_EXPLORER_START_PATH = "path";

	/**
	 * 文件管理器返回所选单个文件数据
	 */
	public static final String FILE_EXPLORER_RETURN_SINGLE_FILE = "file";

	/**
	 * 文件管理器启动来源
	 */
	public static final String FILE_EXPLORER_FROM = "from";

	/**
	 * 文件管理器启动来源-头像
	 */
	public static final String FILE_EXPLORER_FROM_AVATOR = "avator";
	
	/**
	 * 好友订阅状态-none
	 */
	public static final int SUBSCRIPTION_NONE=0x00;
	
	/**
	 * 好友订阅状态-from
	 */
	public static final int SUBCRIPTION_FROM=0x01;
	
	/**
	 * 好友订阅状态-to
	 */
	public static final int SUBCRIPTION_TO=0x02;
	
	/**
	 * 好友订阅状态-both
	 */
	public static final int SUBCRIPTION_BOTH=0x03;

	/**
	 * 无活动的网络链接
	 */
	public static final int GLOBAL_CODE_NO_NETWORK = 0x01;

	/**
	 * 连接服务器失败
	 */
	public static final int GLOBAL_CODE_SERVER_ERR = 0x02;

	/**
	 * 注册新用户开始
	 */
	public static final int GLOBAL_CODE_REG_WAITING = 0x03;

	/**
	 * 注册新用户成功
	 */
	public static final int GLOBAL_CODE_REG_OK = 0x04;

	/**
	 * 注册新用户失败
	 */
	public static final int GLOBAL_CODE_REG_ERR = 0x05;

	/**
	 * 登陆成功
	 */
	public static final int GLOBAL_CODE_LOGIN_OK = 0x06;

	/**
	 * 登录失败
	 */
	public static final int GLOBAL_CODE_LOGIN_ERR = 0x07;

	/**
	 * 显示通用等待对话框
	 */
	public static final int GLOBAL_CODE_COMMON_PROGRESS_DIALOG_SHOW = 0x08;

	/**
	 * 隐藏通用等待对话框
	 */
	public static final int GLOBAL_CODE_COMMON_PROGRESS_DIALOG_DISMISS = 0x09;

	/**
	 * 保存账户信息成功
	 */
	public static final int GLOBAL_CODE_SAVE_PROFILE_OK = 0x10;

	/**
	 * 保存账户信息失败
	 */
	public static final int GLOBAL_CODE_SAVE_PEOFILE_ERR = 0x11;

	/**
	 * 登陆成功，跳转到主界面
	 */
	public static final int LOGIN_JUMP_TO_MAIN = 0x01;

	/**
	 * 注册成功，关闭注册界面，返回上一级
	 */
	public static final int REG_OK_CLOSE = 0x01;

	/**
	 * 查找用户成功
	 */
	public static final int SEARCH_FRIENDS_OK = 0x01;

	/**
	 * 查找用户失败
	 */
	public static final int SEARCH_FRIENDS_ERR = 0x02;

	/**
	 * 停止后台服务
	 */
	public static final int SERVICE_NEED_STOP = 0x11;

	/**
	 * 连接服务器成功
	 */
	public static final int SERVICE_SERVER_CONNECT_OK = 0x12;

	/**
	 * 后台服务载入用户自身VCard信息开始
	 */
	public static final int SERVICE_LOAD_VCARD_START = 0x13;

	/**
	 * 后台服务载入用户自身VCard信息完成
	 */
	public static final int SERVICE_LOAD_VCARD_FINISH = 0x14;

	/**
	 * 后台服务载入用户好友列表信息开始
	 */
	public static final int SERVICE_LOAD_MY_ROSTER_START = 0x15;

	/**
	 * 后台服务载入用户好友列表信息结束
	 */
	public static final int SERVICE_LOAD_MY_ROSTER_FINISH = 0x16;

	/**
	 * 读取Vcard信息成功
	 */
	public static final int VCARD_LOAD_OK = 0x01;

	/**
	 * 读取Vcard信息失败
	 */
	public static final int VCARD_LOAD_ERR = 0x02;

	/**
	 * 文件管理器单选模式
	 */
	public static final int FILE_EXPLORER_MODE_SINGLE = 0x01;

	/**
	 * 文件管理器多选模式
	 */
	public static final int FILE_EXPLORER_MODE_MULTIPLE = 0x02;

	/**
	 * 选取文件完毕
	 */
	public static final int FILE_EXPLORER_SELECT_OVER = 0x03;

	/**
	 * 头像图片转换成功
	 */
	public static final int MY_PROFILE_AVATOR_LOAD_OVER = 0x01;

	/**
	 * 创建新分组成功
	 */
	public static final int ADD_NEW_FRIEND_CREATE_GROUP_OK = 0x01;

	/**
	 * 创建新分组失败
	 */
	public static final int ADD_NEW_FRIEND_CREATE_GROUP_ERR = 0x02;

	/**
	 * 添加好友成功
	 */
	public static final int ADD_NEW_FRIEND_ADD_OK = 0x03;

	/**
	 * 添加好友失败
	 */
	public static final int ADD_NEW_FRIEND_ADD_ERR = 0x04;
	
	/**
	 * 添加好友时的好友uid
	 */
	public static final String ADD_NEW_FRIEND_USERNAME = "username";

}
