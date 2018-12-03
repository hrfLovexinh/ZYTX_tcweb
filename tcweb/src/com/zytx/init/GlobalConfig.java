package com.zytx.init;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import com.et.ar.exception.ActiveRecordException;


/**
 * 系统初始化参数类
 * 
 * @author sunzheng
 * @version 1.0
 */
public class GlobalConfig implements InitializingBean {
	private static final Logger logger = Logger.getLogger(GlobalConfig.class);

	private Resource configLocation;
	private Properties properties;
	private  Timer ywtimer;     //查询运维超期

	public void setConfigLocation(Resource configLocation) {
		this.configLocation = configLocation;
	}

	public void afterPropertiesSet() throws Exception {
		if (configLocation == null)
			throw new IllegalArgumentException("初始化配置出错");
		properties = new Properties();
		properties.load(configLocation.getInputStream());
		init();
	
	}

	// 初始化系统
	private void init() {
		logger.debug("二维码后台管理系统初始化...");
		GlobalFunction.sjbaSoftPath = getProperty("com.zytx.sjba.soft.path");
		GlobalFunction.defaultKey = getProperty("com.zytx.sjba.defaultKey");
		GlobalFunction.tcAlarmPath =getProperty("com.zytx.alarmPath.url");
		GlobalFunction.cityName =getProperty("com.zytx.sjba.cityName");
		GlobalFunction.ywlogTimePoint=getProperty("com.zytx.sjba.ywlogTimePoint");
		GlobalFunction.ywshenhetuisongServer=getProperty("com.zytx.sjba.ywshenheTuisongserver");
		
		if (GlobalFunction.defaultKey == null)
			GlobalFunction.defaultKey = "hc_zytx";
		handleUploadImagePath();
	//	queryTwoCodeYWOutDate();   //暂时取消这个超期查询功能
		
	}
	
	private void handleUploadImagePath() {
		File file = new File(GlobalFunction.sjbaSoftPath);
		File parent = file.getParentFile();
		File tcFile    =null;
		File tcCompanyFile = null;
		String tcPath  =null;
		String tcCompanyPath = null;
		if (!parent.getPath().equals(file.getPath())) {
			
			tcPath = parent.getPath() + "/tc";
			tcCompanyPath=parent.getPath()+"/tccompany";
		} else {
			
			tcPath =GlobalFunction.sjbaSoftPath + "/tc";
			tcCompanyPath =GlobalFunction.sjbaSoftPath+"/tccompany";
		}
		
		tcFile    = new File(tcPath);
		tcCompanyFile = new File(tcCompanyPath);
		System.out.println(tcCompanyPath);
		if (!tcFile.exists()) {
			if (!tcFile.mkdirs()) {
				logger.error("GlobalConfig类,产生二维码上传目录错误");
			}
		}
		if (!tcCompanyFile.exists()) {
			if (!tcCompanyFile.mkdirs()) {
				logger.error("GlobalConfig类,产生二维码公司上传目录错误");
			}
		}
		GlobalFunction.tcUploadPath = tcPath;
		GlobalFunction.tcCompanyPath =tcCompanyPath; 
	}
	
	/*
	private void ywQuaCredRatingInitial(){
		long oneDay = 24 * 60 * 60 * 1000;  
		long initDelay  = getTimeMillis("02:00:00") - System.currentTimeMillis();    
	    initDelay = initDelay > 0 ? initDelay : oneDay + initDelay; 
		ScheduledExecutorService  executor2 = Executors.newScheduledThreadPool(1);
		executor2.scheduleAtFixedRate(new YwQuaCredRatingInitialTask(),
				initDelay,    
	            oneDay,    
	            TimeUnit.DAYS); 

	}
	*/
	/**  
	 * 获取指定时间对应的毫秒数  
	 * @param time "HH:mm:ss"  
	 * @return  
	 */    
	private static long getTimeMillis(String time) {    
	    try {    
	        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");    
	        DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");    
	        Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);    
	        return curDate.getTime();    
	    } catch (ParseException e) {    
	        e.printStackTrace();    
	        return 0;  
	    }    
	    
	}    
	
	/*
	private void queryTwoCodeYWOutDate(){
		ywtimer = new Timer();
		ywtimer.schedule(new YwOutDateTask(),0,24*60*60*1000);
	}
   
   */
	/**
	 * 获取指定名称的属性值
	 * 
	 * @param name
	 * @return
	 */
	public String getProperty(String name) {
		return properties.getProperty(name);
	}
	
	public static String twoCodeImageSource(String registNumber,String startTime){
		String imagePath="";
		String onePath="";
		String twoPath="";
		String threePath="";
		String fourPath="";
		String path = GlobalFunction.tcUploadPath;
		
		if(!"".equals(registNumber)){
			 onePath =String.valueOf(registNumber.charAt(0));
			 twoPath =String.valueOf(registNumber.charAt(1));
			 threePath=String.valueOf(registNumber.charAt(2));
			 fourPath =String.valueOf(registNumber.charAt(3));
		}
		
		if(!"".equals(startTime)){
			startTime=startTime.replace("-", "").replace(" ","").replace(":", "")+".jpg";
		}
		
		imagePath=path + File.separator +onePath+File.separator+twoPath+File.separator+threePath+File.separator+fourPath+File.separator+registNumber+File.separator+startTime;
		return imagePath;
		
	}

}
