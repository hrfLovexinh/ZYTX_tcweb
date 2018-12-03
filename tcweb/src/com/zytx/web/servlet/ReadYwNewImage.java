package com.zytx.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zytx.init.GlobalFunction;

public class ReadYwNewImage extends HttpServlet{
	
	public void init(ServletConfig config) throws ServletException {	
	}
	
	protected void service(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpeg");
		System.out.println("开始获取运维新过滤图片");
		String index = request.getParameter("index");
		System.out.println("index---"+index);
		String cmd = request.getParameter("cmd");
		System.out.println("cmd---"+cmd);
		String registNumber = request.getParameter("registNumber");
		String onePath =String.valueOf(registNumber.charAt(0));
		String twoPath =String.valueOf(registNumber.charAt(1));
		String threePath=String.valueOf(registNumber.charAt(2));
		String fourPath =String.valueOf(registNumber.charAt(3));
		String startTime = request.getParameter("startTime");
		String path = GlobalFunction.tcUploadPath;
		
		
			
		File dir = new File(path);
		File dir2 = new File(dir + File.separator +onePath+File.separator+twoPath+File.separator+threePath+File.separator+fourPath+File.separator+registNumber);
		File file =null;
		if("ywxq".equals(cmd))
		   file =new File(dir2,"ywxq"+startTime.replace("-", "").replace(" ","").replace(":", "")+"_"+index+".jpg");
		else
		   file =new File(dir2,startTime.replace("-", "").replace(" ","").replace(":", "")+".jpg");
		System.out.println("file---"+file);
		if(file.exists()){
		if (registNumber != null && startTime !=null) {
			try {
			InputStream fis = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			int a = 0; 
			byte[] temp = new byte[1024]; 
			while((a = fis.read(temp))>0){ 
			int b = 0; 
			b+=a; 
			os.write(temp,0,b); 
			} 
			os.flush(); 
			fis.close(); 
			os.close(); 
		}catch(Exception e){
			e.printStackTrace(); 
			}
		}	
		}
	}
}
