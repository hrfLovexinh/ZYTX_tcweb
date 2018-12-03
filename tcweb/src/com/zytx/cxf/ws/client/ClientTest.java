package com.zytx.cxf.ws.client;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import net.sf.json.JSONObject;

import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;



public class ClientTest {
	public static void main(String[] args){  
	 
		TsDeviceService server = new TsDeviceService();  
		TsDeivceService hello = server.getTsDeivceServiceImplPort();
	//	String result = hello.updateQrCodeByQrCode("zyxt0703", "xr342fagf", "507598", "107598"); 
		JSONObject obj = new JSONObject();
		obj.put("maintainUnitName","四川力奥机电有限公司2");
		obj.put("maintenanceTel", "13880959868");
		obj.put("maintenanceMan", "曹英钊2");
		obj.put("maintainUnitCode", "000000");
		String result = hello.updateMaintanInfo("zyxt0703", "xr342fagf", "107598", obj.toString()); 
	/*	TsDeviceService server = new TsDeviceService();  
		TsDeivceService hello = server.getTsDeivceServiceImplPort();
		String result = hello.updateDeviceStatus("zyxt0703", "xr342fagf", "2", "107598"); */
		
		System.out.println(result); 
	
	/*
		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
		factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
		factoryBean.setServiceClass(JyDataUpdate.class);
		factoryBean.setAddress(":9000/JyDataUpdate?wsdl");
		JyDataUpdateService impl = (JyDataUpdateService) factoryBean.create();
        impl.jyDataUpdate("tjy", "tjy", "000007"); */
     /*   
		String result =0;
        JyDataUpdateServiceImp jyDataUpdateServiceImp = new JyDataUpdateServiceImp();                                                          
        result=jyDataUpdateServiceImp.jyDataUpdate("<info><userName>tjy</userName><password>tjy</password><registCode>123456258</registCode><jyCompanyId>877</jyCompanyId><inspector>abc</inspector>2012-02-23<inspectDate></inspectDate><nextInspectDate>2018-02-03</nextInspectDate><checkCategory>nianjian</checkCategory><checkResult>hege</checkResult><checkReportNum>123</checkReportNum></info>");
        System.out.println(result); 	*/
			
    }  
	
	/**********
	jkName:接口名称 
	*/
	public void jkdy(final String jkName,final String p1,final String p2){
		String jkresulut="";
		ExecutorService executor = Executors.newSingleThreadExecutor();  
		FutureTask<String> future =  
		       new FutureTask<String>(new Callable<String>() {//使用Callable接口作为构造参数  
		         public String call() {  
		           //真正的任务在这里执行，这里的返回值类型为String，可以为任意类型  
		        	 if("updateDeviceStatus".equals(jkName)){
		        		    TsDeviceService server = new TsDeviceService();  
							TsDeivceService hello = server.getTsDeivceServiceImplPort();
							String jkresult = hello.updateQrCodeByQrCode("zyxt0703", "xr342fagf", p1, p2);
							System.out.println(jkresult); 	 
		        	 }
		        	 return "sucess";
		       }});  
		executor.execute(future); 
		try {  
			jkresulut = future.get(5000, TimeUnit.MILLISECONDS); //取得结果，同时设置超时执行时间为5秒。同样可以用future.get()，不设置执行超时时间取得结果  
		} catch (InterruptedException e) {  
			future.cancel(true);  
		} catch (ExecutionException e) {  
			future.cancel(true);  
		} catch (TimeoutException e) {  
			future.cancel(true);  
		} finally {  
		    executor.shutdown();  
		}  
		
	}
}
