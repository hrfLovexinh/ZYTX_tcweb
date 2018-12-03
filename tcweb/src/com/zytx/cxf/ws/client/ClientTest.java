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
		obj.put("maintainUnitName","�Ĵ����»������޹�˾2");
		obj.put("maintenanceTel", "13880959868");
		obj.put("maintenanceMan", "��Ӣ��2");
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
	jkName:�ӿ����� 
	*/
	public void jkdy(final String jkName,final String p1,final String p2){
		String jkresulut="";
		ExecutorService executor = Executors.newSingleThreadExecutor();  
		FutureTask<String> future =  
		       new FutureTask<String>(new Callable<String>() {//ʹ��Callable�ӿ���Ϊ�������  
		         public String call() {  
		           //����������������ִ�У�����ķ���ֵ����ΪString������Ϊ��������  
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
			jkresulut = future.get(5000, TimeUnit.MILLISECONDS); //ȡ�ý����ͬʱ���ó�ʱִ��ʱ��Ϊ5�롣ͬ��������future.get()��������ִ�г�ʱʱ��ȡ�ý��  
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
