package com.zytx.init;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 通用参数配置
 * 
 * @author SunZheng 189050@qq.com
 */
public class GlobalFunction {

	// 图象路径前缀，如以"c:/image"开始
	public static String imagePath;

	// 系统路径分隔符: unix是"/", windows是"\"
	public static char fileSeparator = File.separator.charAt(0);
	public static String sjbaSoftPath;
	public static String tcUploadPath;
	public static String tcAlarmPath;
	public static String tcCompanyPath;
	// cookie加密字串的密钥
	public static String defaultKey;
	//软件的城市版本
	public static String cityName;   //"0":代表成都， 1：南通 2:长沙
	//2018-04-27 运维时间分割点，大于就查当前和历史表，小于就查当前表
	public  static String ywlogTimePoint;
	//运维审核推送服务器地址
	public static String ywshenhetuisongServer;
	
	
	

}
