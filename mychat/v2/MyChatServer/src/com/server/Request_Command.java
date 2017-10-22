package com.server;
/**
 * 请求命令字典
 * @author Xiloer
 *
 */
public class Request_Command {
	//请求登陆
	public static final byte LOGIN = 1;
	//请求注册
	public static final byte REG = LOGIN + 1;
	//请求广播文本信息
	public static final byte SEND_TEXT = REG + 1;
	//请求向指定用户发送文本信息
	public static final byte SEND_TEXT_TO_ONE = SEND_TEXT + 1;
	//请求广播文件
	public static final byte SEND_FILE = SEND_TEXT_TO_ONE + 1;
	//请求向指定用户发送文件
	public static final byte SEND_FILE_TO_ONE = SEND_FILE + 1;
	//请求传送文件回执
	public static final byte SEND_FILE_TO_ONE_1 = SEND_FILE_TO_ONE + 1;
	//请求开始传送文件
	public static final byte SEND_FILE_TO_ONE_2 = SEND_FILE_TO_ONE_1 + 1;
}
