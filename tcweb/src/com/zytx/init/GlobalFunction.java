package com.zytx.init;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * ͨ�ò�������
 * 
 * @author SunZheng 189050@qq.com
 */
public class GlobalFunction {

	// ͼ��·��ǰ׺������"c:/image"��ʼ
	public static String imagePath;

	// ϵͳ·���ָ���: unix��"/", windows��"\"
	public static char fileSeparator = File.separator.charAt(0);
	public static String sjbaSoftPath;
	public static String tcUploadPath;
	public static String tcAlarmPath;
	public static String tcCompanyPath;
	// cookie�����ִ�����Կ
	public static String defaultKey;
	//����ĳ��а汾
	public static String cityName;   //"0":����ɶ��� 1����ͨ 2:��ɳ
	//2018-04-27 ��άʱ��ָ�㣬���ھͲ鵱ǰ����ʷ��С�ھͲ鵱ǰ��
	public  static String ywlogTimePoint;
	//��ά������ͷ�������ַ
	public static String ywshenhetuisongServer;
	
	
	

}
