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

public class ReadAzImage extends HttpServlet{
	
	public void init(ServletConfig config) throws ServletException {	
	}
	
	protected void service(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpeg");
		System.out.println("开始获取用户过滤图片2");
		String registNumber = request.getParameter("registNumber");
		String subTime = request.getParameter("subTime");
		String index = request.getParameter("index");
		String path = GlobalFunction.tcUploadPath;   
		String onePath =String.valueOf(registNumber.charAt(0));
		String twoPath =String.valueOf(registNumber.charAt(1));
		String threePath=String.valueOf(registNumber.charAt(2));
		String fourPath =String.valueOf(registNumber.charAt(3));
		File dir = new File(path);
	//	File dir2 = new File(dir + File.separator +registNumber);
		File dir2 = new File(dir + File.separator +onePath+File.separator+twoPath+File.separator+threePath+File.separator+fourPath+File.separator+registNumber);
		
		File file =new File(dir2,subTime.replace("-", "").replace(" ","").replace(":", "")+index+".jpg");
		System.out.println("dir2--->"+dir2);
		System.out.println("child--->"+subTime.replace("-", "").replace(" ","").replace(":", "")+index+".jpg");
		System.out.println("filePath--->"+file.getPath());
		System.out.println("file.exists()--->"+file.exists());
		if(file.exists()){
		if (registNumber != null && subTime !=null) {
			try {    System.out.println("图片存在开始读图片");
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
