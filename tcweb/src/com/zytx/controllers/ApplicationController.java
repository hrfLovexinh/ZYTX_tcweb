package com.zytx.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.et.ar.ActiveRecordBase;
import com.et.ar.exception.TransactionException;
import com.et.mvc.Controller;
import com.et.mvc.filter.BeforeFilter;
import com.zytx.models.UserInfo;

@BeforeFilter(execute="auth",except={"error","login"})
public class ApplicationController extends Controller {

	/**  
     * 权限检查过滤器，如果未登录则重定向到登录页面  
     * @return  
     * @throws java.lang.Exception  
     */  
    protected boolean auth() throws Exception{ 
    	/*
        if (session.getAttribute("sessionAccount") == null){   
        	redirect("/index.jsp");   
            return false;   
        }   
        return true;   */
    	
         if(session.getAttribute("sessionAccount") != null){
        	 return true;
         }
         else{
        	 Cookie[] cookies =  request.getCookies();
        	 String userName = "";
     		 String password = "";
     		if (cookies != null) {
     		   for (Cookie c : cookies) {
     			if (c.getName().equals("userName")) {
     			    userName = c.getValue();
     		      }
     			if (c.getName().equals("password")) {
     			   password = c.getValue();
     		      }
     		    }
            }
     		UserInfo user =UserInfo.findFirstBySql(UserInfo.class, "select t.*,te.userName,te.ispcsuper,te.isliulan,te.isyanshi from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id=te.userid where loginName= ? and t.isinvalid = 0",new Object[] { userName });
     		if(user!=null){
     			if(user.getPassword().equals(password)){
     				request.getSession().setAttribute("sessionAccount", user);
     			}
     			else{
     				redirect("/index.jsp"); 
     				return false;
     			}
     		}
     		else{
     			redirect("/index.jsp");	
     			return false;
     		}

         }
    	 return true; 
    }  
    
    protected void dbrollback(){
    	try {
			ActiveRecordBase.rollback();
		} catch (TransactionException e1) {
		}
	}

}
