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

public class YwRateCompanyImage extends HttpServlet{
	
	public void init(ServletConfig config) throws ServletException {	
	}
	
	protected void service(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpeg");
		
		String name = request.getParameter("name");
		String picPah="";
		String path = GlobalFunction.tcCompanyPath;
		if(name != null)
		picPah=path+"/"+name.substring(0, name.lastIndexOf("/"));
		
	//	System.out.println("path----"+path);
		File dir = new File(picPah);
		File file =new File(dir,File.separator+"/"+name.replace("/","")+".jpg");
		System.out.println(dir+File.separator+"/"+name.replace("/","")+".jpg");
		if(file.exists()){ 
		if (name != null) {
			try {    System.out.println("开始获取图片");
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
