package com.zytx.controllers;

import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;

import com.et.ar.exception.ActiveRecordException;
import com.et.mvc.Controller;
import com.et.mvc.JsonView;
import com.et.mvc.JspView;
import com.et.mvc.View;
import com.et.mvc.filter.AfterFilter;
import com.et.mvc.filter.AfterFilters;
import com.et.mvc.filter.BeforeFilter;
import com.zytx.models.*;


@AfterFilter(execute="loginlog",only={"login"})
@BeforeFilter(execute = "systemExitlog",only={"systemExit"})
public class JoinController extends ApplicationController {

	public String add(UserInfo userInfo) throws Exception {
		String result = "failure";
		boolean flag = false;
		flag = userInfo.save();
		if (flag) {
			result = "success";
		} else {
			result = "failure";
		}
		return result;
	}
   
	public String login() throws Exception {
		System.out.println("开始登陆！");
		String result = "failure";
		String userName = "";
		String password = "";
	//	userName =userInfo.getLoginName();
	//	password =userInfo.getPassword();
		userName = request.getParameter("loginName");
		password = request.getParameter("password");
		UserInfo user =UserInfo.findFirstBySql(UserInfo.class, "select t.*,te.userName,te.ispcsuper,te.isliulan,te.isyanshi from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id=te.userid where loginName= ? and t.isinvalid = 0",new Object[] { userName });
		if(user!=null){
		 if(user.getRole() == 1 || user.getRole() ==2){
			 if(user.getPassword().equals(password)){
				  result ="success";
				  System.out.println(user.getLoginName()+"登陆成功");
				  request.getSession().setAttribute("sessionAccount", user);   
				  
				  Cookie uNCookie = new Cookie("userName", userName); 
				  Cookie pWCookie = new Cookie("password", password); 
				  uNCookie.setPath("/"); 
				  pWCookie.setPath("/");
				  response.addCookie(uNCookie);
				  response.addCookie(pWCookie);
			  }	
			  else{
				  result = "failure";
			  } 
		  }
		 else if(user.getRole() == 10 || user.getRole() ==11){  //质监
			 if(user.getPassword().equals(password)){
				  result ="success";
				  System.out.println(user.getLoginName()+"登陆成功");
				  request.getSession().setAttribute("sessionAccount", user);   
				  
				  Cookie uNCookie = new Cookie("userName", userName); 
				  Cookie pWCookie = new Cookie("password", password); 
				  uNCookie.setPath("/"); 
				  pWCookie.setPath("/");
				  response.addCookie(uNCookie);
				  response.addCookie(pWCookie);
			  }	
			  else{
				  result = "failure";
			  } 
		  } 
		 else if(user.getRole() == 22 || user.getRole() ==23){  //市质监局
			 if(user.getPassword().equals(password)){
				  result ="success";
				  System.out.println(user.getLoginName()+"登陆成功");
				  request.getSession().setAttribute("sessionAccount", user);   
				  
				  Cookie uNCookie = new Cookie("userName", userName); 
				  Cookie pWCookie = new Cookie("password", password); 
				  uNCookie.setPath("/"); 
				  pWCookie.setPath("/");
				  response.addCookie(uNCookie);
				  response.addCookie(pWCookie);
			  }	
			  else{
				  result = "failure";
			  } 
		  } 
		 else if(user.getRole() == 20 || user.getRole() ==21){    //街道办
			 if(user.getPassword().equals(password)){
				  result ="success";
				  System.out.println(user.getLoginName()+"登陆成功");
				  request.getSession().setAttribute("sessionAccount", user);   
				  
				  Cookie uNCookie = new Cookie("userName", userName); 
				  Cookie pWCookie = new Cookie("password", password); 
				  uNCookie.setPath("/"); 
				  pWCookie.setPath("/");
				  response.addCookie(uNCookie);
				  response.addCookie(pWCookie);
			  }	
			  else{
				  result = "failure";
			  } 
		  }
		 else if(user.getRole() == 8 || user.getRole() ==9){    //物管
			 if(user.getPassword().equals(password)){
				  result ="success";
				  System.out.println(user.getLoginName()+"登陆成功");
				  request.getSession().setAttribute("sessionAccount", user);   
				  
				  Cookie uNCookie = new Cookie("userName", userName); 
				  Cookie pWCookie = new Cookie("password", password); 
				  uNCookie.setPath("/"); 
				  pWCookie.setPath("/");
				  response.addCookie(uNCookie);
				  response.addCookie(pWCookie);
			  }	
			  else{
				  result = "failure";
			  } 
		  }
		 else if(user.getRole() == 16 || user.getRole() ==17){    //检验
			 if(user.getPassword().equals(password)){
				 if(user.getIskaoping() == 1){
				  result ="success";
				  System.out.println(user.getLoginName()+"登陆成功");
				  request.getSession().setAttribute("sessionAccount", user);   
				  
				  Cookie uNCookie = new Cookie("userName", userName); 
				  Cookie pWCookie = new Cookie("password", password); 
				  uNCookie.setPath("/"); 
				  pWCookie.setPath("/");
				  response.addCookie(uNCookie);
				  response.addCookie(pWCookie);
				  }
				 else{
					 result = "failure"; 
				 }
			  }	
			  else{
				  result = "failure";
			  } 
		  }
		 else if(user.getRole() == 30 || user.getRole() ==31){    //行业协会
			 if(user.getPassword().equals(password)){
				 if(user.getIskaoping() == 1){
				  result ="success";
				  System.out.println(user.getLoginName()+"登陆成功");
				  request.getSession().setAttribute("sessionAccount", user);   
				  
				  Cookie uNCookie = new Cookie("userName", userName); 
				  Cookie pWCookie = new Cookie("password", password); 
				  uNCookie.setPath("/"); 
				  pWCookie.setPath("/");
				  response.addCookie(uNCookie);
				  response.addCookie(pWCookie);
				  }
				 else{
					 result = "failure"; 
				 }
			  }	
			  else{
				  result = "failure";
			  } 
		  }
		 else{    //其它role不让登录系统
			 result = "failure";	 
		 }
		 }
		else{
		  
			  result = "failure";
		}
	  
		return result;
	}
	
   public void systemExit(){
	   try {
		UserInfo user=(UserInfo)session.getAttribute("sessionAccount");
		if(user!=null){
			System.out.println(user.getLoginName()+"正在退出系统");
			session.removeAttribute("sessionAccount");
		}
		redirect("/index.jsp");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   
   protected boolean  loginlog(){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
	//	 int userid = userinfo.getId();
		 if(userinfo != null){
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 String companyName ="";
		 TwoCodeLogInfo twoCodeLogInfo = new TwoCodeLogInfo();
		 String result = "failure";
	     boolean flag = false;
	     if(role == 10 || role == 11)
		 try {
			 TCUserInfoView user =UserInfo.findFirstBySql(TCUserInfoView.class, "select t.companyName from TCUserInfo t  where t.loginName= ?",new Object[] { userName });
			if(user != null){
				companyName = user.getCompanyName();
				twoCodeLogInfo.setLogPerson(userName);
				twoCodeLogInfo.setLogPersonCompany(companyName);
				twoCodeLogInfo.setLogAction("登陆");
				twoCodeLogInfo.setLogContext("系统登陆");
				flag = twoCodeLogInfo.save();
				if (flag) {
					result = "success";
				} else {
					result = "failure";
				}
			}
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
		 return true;
	 }
   
   protected boolean  systemExitlog(){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
	//	 int userid = userinfo.getId();
		 if(userinfo != null){
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 String companyName ="";
		 TwoCodeLogInfo twoCodeLogInfo = new TwoCodeLogInfo();
		 String result = "failure";
	     boolean flag = false;
	     if(role == 10 || role == 11)
		 try {
			 TCUserInfoView user =UserInfo.findFirstBySql(TCUserInfoView.class, "select t.companyName from TCUserInfo t  where t.loginName= ?",new Object[] { userName });
			if(user != null){
				companyName = user.getCompanyName();
				twoCodeLogInfo.setLogPerson(userName);
				twoCodeLogInfo.setLogPersonCompany(companyName);
				twoCodeLogInfo.setLogAction("退出");
				twoCodeLogInfo.setLogContext("系统退出");
				flag = twoCodeLogInfo.save();
				if (flag) {
					result = "success";
				} else {
					result = "failure";
				}
			}
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }	
		 return true;
	 }
}
