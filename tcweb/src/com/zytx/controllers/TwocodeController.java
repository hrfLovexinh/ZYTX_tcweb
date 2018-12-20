package com.zytx.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import java.util.Date;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.exception.ActiveRecordException;
import com.et.ar.exception.TransactionException;
import com.et.mvc.Controller;
import com.et.mvc.JsonView;
import com.et.mvc.MultipartFile;
import com.et.mvc.View;
import com.et.mvc.filter.BeforeFilter;
import com.zytx.converters.DateConverter;
import com.zytx.converters.TimestampConverter;
import com.zytx.init.GlobalFunction;
import com.zytx.models.CSElevaltorInfoVO;
import com.zytx.models.CompanyInfo;
import com.zytx.models.CompanyInfoNew;
import com.zytx.models.CompanyInfoVO;
import com.zytx.models.ContractInfo;
import com.zytx.models.ContractInfoVO;
import com.zytx.models.ContractPaymentInfo;
import com.zytx.models.ContractPaymentInfoVO;
import com.zytx.models.DdAppendixEleInfo;
import com.zytx.models.DdElevaltorInfo;
import com.zytx.models.DdElevaltorInfoVO;
import com.zytx.models.DdTwoCodeElevatorYwCompanyInfo;
import com.zytx.models.DdYuanShiElevaltorInfoVO;
import com.zytx.models.EleChangeInfo;
import com.zytx.models.EleChangeInfoVO;
import com.zytx.models.ElevaltorInfo;
import com.zytx.models.ElevaltorInfoNewVO;
import com.zytx.models.ElevaltorInfoVO;
import com.zytx.models.ImageVO;
import com.zytx.models.CarDevCard;
import com.zytx.models.SysSetings;
import com.zytx.models.SysSetingsVO;
import com.zytx.models.TCUserInfoView;
import com.zytx.models.TwoCodeElevatorYwCompanyInfo;
import com.zytx.models.TwoCodeInfo;
import com.zytx.models.Twocode96333;
import com.zytx.models.Twocode96333VO;
import com.zytx.models.Twocode96333pVO;
import com.zytx.models.Twocode96333r;
import com.zytx.models.Twocode96333rVO;
import com.zytx.models.TwocodeVO;
import com.zytx.models.UserExtInfo;
import com.zytx.models.UserInfo;
import com.zytx.models.ImageInfo;
import com.zytx.models.ImageInfo2;
import com.zytx.models.TwoCodeDeviceRelationInfo;
import com.zytx.models.UserInfoVO;
import com.zytx.models.YwInfoVO;
import com.zytx.models.YwQuaCredRatingVO;
import com.zytx.models.YwRankingInfoVO;
import com.et.mvc.filter.AfterFilter;
import com.et.mvc.filter.AfterFilters;


public class TwocodeController extends ApplicationController{ 
	
	public View twocodelist(int page, int rows){
		  
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String cityName = GlobalFunction.cityName;
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 long total=0;
		 List<TwocodeVO> items = null;
		 String sql ="";
		 
		 sql = "select *,isnull((select companyName from twocodecompanyinfo where id = ywcompanyId),'') as ywCompanyName from TwoCodeInfo";
		 try {
			total = TwocodeVO.count(TwocodeVO.class, null, null);
			items = TwocodeVO.findBySql(TwocodeVO.class, sql,null,null, rows, (page-1)*rows);
		} catch (ActiveRecordException e) {
		
			e.printStackTrace();
		}   	
		  
		  Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);

		  return new JsonView(result);
	}
	
	public View twocode96333list(int page, int rows){
		  
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String cityName = GlobalFunction.cityName;
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 long total=0;
		 List<Twocode96333VO> items = null;
		 String sql ="";
		 
		 sql = "select id,(select count(*)  from Twocode96333pdetailInfo) as pztcount,(select count(*)  from Twocode96333pdetailInfo where psate = 0) as dhspcount,(select sum(case when rtype = 0 then rcount  else -rcount  end) from Twocode96333rInfo) as rtcount,(select sum(pcount) from Twocode96333Info) as ptcount,puserName,ptime,pcount,pbeizhu from Twocode96333Info";
		 try {
			total = Twocode96333VO.count(Twocode96333VO.class, null, null);
			items = Twocode96333VO.findBySql(Twocode96333VO.class, sql,null,null, rows, (page-1)*rows);
		} catch (ActiveRecordException e) {
		
			e.printStackTrace();
		}   	
		  
		  Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);

		  return new JsonView(result);
	}
	
	public View twocode96333lylist(int page, int rows){
		  
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String cityName = GlobalFunction.cityName;
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 long total=0;
		 List<Twocode96333rVO> items = null;
		 String sql ="";
		 
		 sql = "select (select count(*)  from Twocode96333pdetailInfo) as pztcount,(select count(*)  from Twocode96333pdetailInfo where psate = 0) as dhspcount,(select sum(case when rtype = 0 then rcount  else -rcount  end) from Twocode96333rInfo) as rtcount,(select companyName from twocodeCompanyinfo where id =rcompanyId) as rcompanyName,ruserName,rtime,rcount,rtype,rbeizhu from Twocode96333rInfo";
		 try {
			total = Twocode96333rVO.count(Twocode96333rVO.class, null, null);
			items = Twocode96333rVO.findBySql(Twocode96333rVO.class, sql,null,null, rows, (page-1)*rows);
		} catch (ActiveRecordException e) {
		
			e.printStackTrace();
		}   	
		  
		  Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);

		  return new JsonView(result);
	}
	
	
	public View twocode96333ztlist(int page, int rows){
		  
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String cityName = GlobalFunction.cityName;
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 long total=0;
		 List<Twocode96333pVO> items = null;
		 String sql ="";
		 
		 sql = "select (select count(*)  from Twocode96333pdetailInfo) as pcount,(select count(*)  from Twocode96333pdetailInfo where psate = 0) as dhspcount, registNumber,pcompanyName, puserName,ptime,subtime,pbeizhu,psate from Twocode96333pdetailInfo";
		 try {
			total = Twocode96333pVO.count(Twocode96333pVO.class, null, null);
			items = Twocode96333pVO.findBySql(Twocode96333pVO.class, sql,null,null, rows, (page-1)*rows);
		} catch (ActiveRecordException e) {
		
			e.printStackTrace();
		}   	
		  
		  Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);

		  return new JsonView(result);
	}
	
	 public String add(TwocodeVO twocodeVO){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String cityName = GlobalFunction.cityName;
		 int userid = userinfo.getId();
	//	 String userName =userinfo.getLoginName();
		 String userName ="";
		 String result = "failure";
		 String sregistNumber =twocodeVO.getSregistNumber();
		 String eregistNumber =twocodeVO.getEregistNumber();
		  
		 try {
			 UserExtInfo  userExtInfo=UserExtInfo.findFirst(UserExtInfo.class, "userid = ?", new Object[]{userid});
			 if(userExtInfo != null)
				 userName = userExtInfo.getUserName(); 
			 ActiveRecordBase.beginTransaction();
			 ActiveRecordBase.execute("exec proc_twoCodeinfo ?,?,?", new Object[] {  Integer.parseInt(sregistNumber),Integer.parseInt(eregistNumber),userName });  
			 ActiveRecordBase.commit();
			 result = "success";  
			 
		} catch (ActiveRecordException e) {
			
			try {
				ActiveRecordBase.rollback();
			} catch (TransactionException e1) {
			
				e1.printStackTrace();
			}
			e.printStackTrace();
		} 
		 return result;
	 }
	 
	 public String add96333(Twocode96333VO twocode96333VO){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String cityName = GlobalFunction.cityName;
		 int userid = userinfo.getId();
	//	 String userName =userinfo.getLoginName();
		 String userName ="";
		 String result = "failure";
		 boolean flag = false;
		 int pcount =twocode96333VO.getPcount();
		 String pbeizhu =twocode96333VO.getPbeizhu();
		 Twocode96333  myTwocode96333 =new Twocode96333();
		 try {
			 UserExtInfo  userExtInfo=UserExtInfo.findFirst(UserExtInfo.class, "userid = ?", new Object[]{userid});
			 if(userExtInfo != null)
				 userName = userExtInfo.getUserName(); 
			 
			 java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 String s = format1.format(new Date()); 
			 myTwocode96333.setPuserId(userid);
			 myTwocode96333.setPuserName(userName);
			 myTwocode96333.setPtime(s);
			 myTwocode96333.setPbeizhu(pbeizhu);
			 myTwocode96333.setPcount(pcount);
			 
			 flag= myTwocode96333.save();
			 if(flag)	    
			 result = "success"; 
			 
			
			 
		} catch (ActiveRecordException e) {
			
			
			e.printStackTrace();
		} 
		 return result;
	 }
	 
	 public String add96333ly(Twocode96333rVO twocode96333rVO){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String cityName = GlobalFunction.cityName;
		 int userid = userinfo.getId();
	//	 String userName =userinfo.getLoginName();
		 String userName ="";
		 String result = "failure";
		 boolean flag = false;
		 
		 int rcompanyId =twocode96333rVO.getYwCompanyIdinfo2();
		 String ruserName =twocode96333rVO.getRuserName();
		 String rtime =twocode96333rVO.getRtime();
		 int rcount = twocode96333rVO.getRcount();
		 String rbeizhu =twocode96333rVO.getRbeizhu();
		 
		 Twocode96333r  myTwocode96333r =new Twocode96333r();
		 try {
			 UserExtInfo  userExtInfo=UserExtInfo.findFirst(UserExtInfo.class, "userid = ?", new Object[]{userid});
			 if(userExtInfo != null)
				 userName = userExtInfo.getUserName(); 
			 
			 myTwocode96333r.setRuserId(userid);
			 myTwocode96333r.setRcompanyId(rcompanyId);
			 myTwocode96333r.setRuserName(ruserName);
			 myTwocode96333r.setRtime(rtime);
			 myTwocode96333r.setRcount(rcount);
			 myTwocode96333r.setRbeizhu(rbeizhu);
			 
			 flag= myTwocode96333r.save();
			 if(flag)	    
			 result = "success"; 
			 
			
			 
		} catch (ActiveRecordException e) {
			
			
			e.printStackTrace();
		} 
		 return result;
	 }
	 
	 public String add96333lyhs(Twocode96333rVO twocode96333rVO){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String cityName = GlobalFunction.cityName;
		 int userid = userinfo.getId();
	//	 String userName =userinfo.getLoginName();
		 String userName ="";
		 String result = "failure";
		 boolean flag = false;
		 
		 int rcompanyId =twocode96333rVO.getYwCompanyIdinfo2();
		 String ruserName =twocode96333rVO.getRuserName();
		 String rtime =twocode96333rVO.getRtime();
		 int rcount = twocode96333rVO.getRcount();
		 String rbeizhu =twocode96333rVO.getRbeizhu();
		 
		 Twocode96333r  myTwocode96333r =new Twocode96333r();
		 try {
			 UserExtInfo  userExtInfo=UserExtInfo.findFirst(UserExtInfo.class, "userid = ?", new Object[]{userid});
			 if(userExtInfo != null)
				 userName = userExtInfo.getUserName(); 
			 
			 myTwocode96333r.setRuserId(userid);
			 myTwocode96333r.setRcompanyId(rcompanyId);
			 myTwocode96333r.setRuserName(ruserName);
			 myTwocode96333r.setRtime(rtime);
			 myTwocode96333r.setRcount(rcount);
			 myTwocode96333r.setRbeizhu(rbeizhu);
			 myTwocode96333r.setRtype(1);    // 0:领用，1:回收
			 
			 flag= myTwocode96333r.save();
			 if(flag)	    
			 result = "success"; 
			 
			
			 
		} catch (ActiveRecordException e) {
			
			
			e.printStackTrace();
		} 
		 return result;
	 }
	 
	 public String delete(TwocodeVO twocodeVO){
		 String result = "failure";
		 int id = twocodeVO.getId();
		 String registNumber = twocodeVO.getRegistNumber();
		 
		 TwocodeVO myTwocodeVO = null;
		 try {
			 myTwocodeVO = TwocodeVO.findFirst(TwocodeVO.class, "id = ?", new Object[]{id});
			if(myTwocodeVO != null){   //删除记录
				myTwocodeVO.destroy();
				result = "success"; 
			}
			
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		 return result;
	 }
	 
	 public String delete96333(Twocode96333VO twocode96333VO){
		 String result = "failure";
		 int id = twocode96333VO.getId();
		
		 
		 Twocode96333VO myTwocode96333VO = null;
		 try {
			 myTwocode96333VO = Twocode96333VO.findFirst(Twocode96333VO.class, "id = ?", new Object[]{id});
			if(myTwocode96333VO != null){   //删除记录
				myTwocode96333VO.destroy();
				result = "success"; 
			}
			
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		 return result;
	 }
	 
	 
	 
	 public View query(TwocodeVO twocodeVO,int page, int rows){
		 
		 String sregistNumber = "";
		 String eregistNumber = "" ;
		 
		 sregistNumber = twocodeVO.getSregistNumber();
		 eregistNumber = twocodeVO.getEregistNumber();
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 if(!"".equals(sregistNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registNumber >= "+sregistNumber;	
				} 
				else{
				 conditions =" t.registNumber  >= "+sregistNumber;	
				}
			 
		 }
		 
		 if(!"".equals(eregistNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registNumber <= "+eregistNumber;	
				} 
				else{
				 conditions =" t.registNumber <= "+eregistNumber;	
				}
			 
		 }
		 
		 if(!"".equals(conditions)){
			 sql ="select t.*,isnull((select companyName from twocodecompanyinfo where id = ywcompanyId),'') as ywCompanyName from TwoCodeInfo t where "+ conditions;
			 conditionsSql = "select count(*) from TwoCodeInfo  t  where "+ conditions;
		 }
		 else{
			 sql ="select t.*,isnull((select companyName from twocodecompanyinfo where id = ywcompanyId),'') as ywCompanyName from TwoCodeInfo t ";
			 conditionsSql = "select count(*) from TwoCodeInfo  t ";
		 }
		 
		long total =0;
		List<TwocodeVO> items= null;
		try {
			total = TwocodeVO.countBySql(TwocodeVO.class, conditionsSql, null);
			items=TwocodeVO.findBySql(TwocodeVO.class, sql, null, null,rows, (page-1)*rows);
		} catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		
         
		 
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
         
	 }

public View query96333zt(Twocode96333pVO twocode96333pVO,int page, int rows){
		 
	     int ywCompanyId =0;  //查询用的
		 
	     ywCompanyId = twocode96333pVO.getYwCompanyIdinfo2();
		
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 if(ywCompanyId > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and pcompanyId = "+ywCompanyId;	
				} 
				else{
					conditions =" pcompanyId = "+ywCompanyId;	
				}
			 
			 
		 }
		 
		 if(!"".equals(conditions)){
			 sql ="select (select count(*)  from Twocode96333pdetailInfo) as pcount,(select count(*)  from Twocode96333pdetailInfo where psate = 0) as dhspcount, registNumber,pcompanyName, puserName,ptime,subtime,pbeizhu,psate from Twocode96333pdetailInfo  where "+ conditions;
			 conditionsSql = "select count(*) from Twocode96333pdetailInfo  where "+ conditions;
		 }
		 else{
			 sql ="select (select count(*)  from Twocode96333pdetailInfo) as pcount,(select count(*)  from Twocode96333pdetailInfo where psate = 0) as dhspcount, registNumber,pcompanyName, puserName,ptime,subtime,pbeizhu,psate from Twocode96333pdetailInfo  ";
			 conditionsSql = "select count(*) from Twocode96333pdetailInfo ";
		 }
		 
		long total =0;
		List<Twocode96333pVO> items= null;
		try {
			total = Twocode96333pVO.countBySql(Twocode96333pVO.class, conditionsSql, null);
			items=Twocode96333pVO.findBySql(Twocode96333pVO.class, sql, null, null,rows, (page-1)*rows);
		} catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		
         	 
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
         
	 }
	 
 public View query96333ly(Twocode96333rVO twocode96333rVO,int page, int rows){
		 
	     int ywCompanyId =0;  //查询用的
		 
	     ywCompanyId = twocode96333rVO.getYwCompanyIdinfo2();
		
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 if(ywCompanyId > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and rcompanyId = "+ywCompanyId;	
				} 
				else{
					conditions =" rcompanyId = "+ywCompanyId;	
				}
			 
			 
		 }
		 
		 if(!"".equals(conditions)){
			 sql ="select (select count(*)  from Twocode96333pdetailInfo) as pztcount,(select count(*)  from Twocode96333pdetailInfo where psate = 0) as dhspcount,(select sum(case when rtype = 0 then rcount  else -rcount  end) from Twocode96333rInfo) as rtcount,(select companyName from twocodeCompanyinfo where id =rcompanyId) as rcompanyName,ruserName,rtime,rcount,rtype,rbeizhu from Twocode96333rInfo  where "+ conditions;
			 conditionsSql = "select count(*) from Twocode96333rInfo  where "+ conditions;
		 }
		 else{
			 sql ="select (select count(*)  from Twocode96333pdetailInfo) as pztcount,(select count(*)  from Twocode96333pdetailInfo where psate = 0) as dhspcount,(select sum(case when rtype = 0 then rcount  else -rcount  end) from Twocode96333rInfo) as rtcount,(select companyName from twocodeCompanyinfo where id =rcompanyId) as rcompanyName,ruserName,rtime,rcount,rtype,rbeizhu from Twocode96333rInfo ";
			 conditionsSql = "select count(*) from Twocode96333rInfo ";
		 }
		 
		long total =0;
		List<Twocode96333rVO> items= null;
		try {
			total = Twocode96333rVO.countBySql(Twocode96333rVO.class, conditionsSql, null);
			items=Twocode96333rVO.findBySql(Twocode96333rVO.class, sql, null, null,rows, (page-1)*rows);
		} catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		
         	 
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
         
	 }
	 
	 public View lingyong(TwocodeVO twocodeVO){
	//	 String result = "failure";
		 String sregistNumber =twocodeVO.getSregistNumber();
		 String eregistNumber =twocodeVO.getEregistNumber();
		 int ywCompanyId = twocodeVO.getYwCompanyId();
		 String userName  = twocodeVO.getUserName();
		 String rtime = twocodeVO.getRtime();
		 String loginName = twocodeVO.getLoginName();
		 
		 long total=0;
		 List<TwocodeVO> items = null;
	     System.out.println("ling");
		 try {
	//		 ActiveRecordBase.beginTransaction();
	//		 ActiveRecordBase.execute("exec proc_twoCodeinfolingyong ?,?,?,?,?", new Object[] {  Integer.parseInt(sregistNumber),Integer.parseInt(eregistNumber),ywCompanyId,userName,rtime });  
			 items =TwocodeVO.findBySql(TwocodeVO.class, "exec proc_twoCodeinfolingyong ?,?,?,?,?,?",new Object[] {Integer.parseInt(sregistNumber),Integer.parseInt(eregistNumber),ywCompanyId,userName,rtime,loginName});
	//		 ActiveRecordBase.commit();
	//		 result = "success";  
			 
			 
		} catch (ActiveRecordException e) {
			
		
			e.printStackTrace();
		} 
		 Map<String, Object> result = new HashMap<String, Object>();
		 result.put("rows", items);
		return new JsonView(result);
	 }
	 
	 public View dlingyong(TwocodeVO twocodeVO){
	//	 String result = "failure";
		 String sregistNumber =twocodeVO.getSregistNumber();
		 int ywCompanyId = twocodeVO.getYwCompanyId();
		 String userName  = twocodeVO.getUserName();
		 String rtime = twocodeVO.getRtime();
		 String loginName = twocodeVO.getLoginName();
		 
		 List<TwocodeVO> items = null;
		 Map<String, Object> result = new HashMap<String, Object>();
		 try {
			 items = TwocodeVO.findBySql(TwocodeVO.class, "select *,(select companyName from twocodecompanyinfo where id =ywcompanyId) as ywCompanyName from twocodeinfo where registNumber = ? and infoState > 0 ", new Object[]{sregistNumber});
			 if(items != null){ 
				 result.put("rows", items);
				 return new JsonView(result);
			 }
			 else{
			     TwocodeVO.updateAll(TwocodeVO.class, "ywCompanyId=?,userName = ?,rtime=?,loginName=?,infoState=?", new Object[]{ywCompanyId,userName,rtime,loginName,1}, "registNumber = ?", new Object[]{sregistNumber});
			 }
	
			 
		} catch (ActiveRecordException e) {	
				e.printStackTrace();
			}
			
		
		 result.put("rows", items);
		 return new JsonView(result);
	 }
	 
	public String alterlingyong(TwocodeVO twocodeVO) {
		String result = "failure";
		String sregistNumber = twocodeVO.getSregistNumber();
		int ywCompanyId = twocodeVO.getYwCompanyId();
		String userName = twocodeVO.getUserName();
		String rtime = twocodeVO.getRtime();
		String loginName = twocodeVO.getLoginName();

		List<TwocodeVO> items = null;
		int num =0;
		try {	
		  num =TwocodeVO.updateAll(TwocodeVO.class,"ywCompanyId=?,userName = ?,rtime=?,loginName=?,infoState=?",new Object[] { ywCompanyId, userName, rtime,loginName, 1 },"registNumber = ?", new Object[] { sregistNumber });
		  if(num >0)
			  result = "success";  
		}
        catch (ActiveRecordException e) {
			e.printStackTrace();
		}

		return result;
		
	}
	
	
	//入库的导入的模板下载
	public void downLoad(String way) {
		String filename = "";
		Workbook workBook=null;
		workBook = new XSSFWorkbook();
		Sheet sheet = workBook.createSheet("sheet1");
		// 设置表格默认列宽度为15个字节  
		sheet.setDefaultColumnWidth((short) 30);  
        // 生成一个样式  
        CellStyle style = workBook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);    
        style.setBorderLeft(BorderStyle.THIN);  
        style.setBorderRight(BorderStyle.THIN);  
        style.setBorderTop(BorderStyle.THIN);  
        style.setAlignment(HorizontalAlignment.CENTER);   
        // 生成一个字体  
        Font font = workBook.createFont();  
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());  
        font.setBold(true);
        font.setFontHeightInPoints((short)12);
        // 把字体应用到当前的样式  
        style.setFont(font); 
		Row row = null;
		Cell cell = null;
		if("ruku".equals(way)) {
			filename = "标签入库信息.xlsx";
	        String[] headers = new String[]{"标签编号"};
	        row =  sheet.createRow(0);
	        for (int i = 0;i < headers.length;i ++) {
	        	cell = row.createCell(i);
	        	cell.setCellValue(headers[i]);
	        	cell.setCellStyle(style);
	        }
		}else {
			filename = "标签领用信息.xlsx";
			String[] headers = new String[]{"标签编号","领用类型（个人/公司）","领用人单位","领用人姓名","领用人账号（个人领用必填）","领用日期"};
	        row =  sheet.createRow(0);
	        for (int i = 0;i < headers.length;i ++) {
	        	cell = row.createCell(i);
	        	cell.setCellValue(headers[i]);
	        	cell.setCellStyle(style);
	        }
		}
        OutputStream out = null;
        try {
            response.setContentType("application/ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
            out = response.getOutputStream();
            workBook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
            	workBook.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	//标签入库导入
	public View rukuExport(MultipartFile file) {
		//获取入库人信息
		String userName = "";
		Cookie[] cookies;
		UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		if(userinfo!=null){
			userName = userinfo.getLoginName();
			}
			else{
				 cookies =  request.getCookies();
					if (cookies != null) {
					   for (Cookie c : cookies) {
						if (c.getName().equals("userName")) {
						    userName = c.getValue();
					      }
					    }
			    }
			}
		try {
			UserInfoVO user =UserInfoVO.findFirstBySql(UserInfoVO.class, "select isnull(te.contactPhone,'') as contactPhone,isnull(te.userName,'') as userName from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid where loginName= ?",new Object[] { userName });
			userName = user.getUserName();
		} catch (ActiveRecordException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//获取上传文件名
		String filename = file.getOriginalFilename();
		System.out.println(filename);
		//获取一个文件工作簿
		Workbook workBook=null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int rowNum = 1;
		List<TwocodeVO> result = new ArrayList<TwocodeVO>();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			workBook = new XSSFWorkbook(file.getInputStream());
			//获取一个sheet
			Sheet sheet = workBook.getSheetAt(0);
			Row row = null;
			Cell cell = null;
			String registNumber = "";
			String incomplete = "";
			int incompleteCount = 0;
			TwocodeVO twocodeInfo = null;
			List<TwocodeVO> twocodeInfos = null;
			int registNumberExistCount = 0;
			int count = 0;
			while(sheet.getRow(rowNum) != null) {
				row = sheet.getRow(rowNum);
				cell = row.getCell(0);
				try {
					registNumber = row.getCell(0).getStringCellValue(); 
				} catch (Exception e) {
					Double cellValue = row.getCell(0).getNumericCellValue();
					registNumber = new DecimalFormat("#").format(cellValue);
				}
				if("".equals(registNumber)) {
					continue;
				}
				//判断数据有效性
				if(!registNumber.matches("[0-9]{6}")) {
					incomplete = incomplete + ", " + registNumber;
					incompleteCount ++;
					//计数第几行
					rowNum ++;
					continue;
				}
				//查询数据库
				twocodeInfos = TwocodeVO.findBySql(TwocodeVO.class, "select t.*,c.companyName as ywCompanyName from TwoCodeInfo t left join TwoCodeCompanyInfo c on t.ywcompanyId=c.id where t.registNumber=?", new Object[]{registNumber});
				if(twocodeInfos != null && twocodeInfos.size() != 0) {
					twocodeInfo = twocodeInfos.get(0);
				} else {
					twocodeInfo = null;
				}
				if(twocodeInfo == null) {
					TwoCodeInfo twoCode = new TwoCodeInfo();
					//入库
					twoCode.setRegistNumber(registNumber);
					twoCode.setPtime(dateFormat.format(new Date()));
					twoCode.setPuserName(userName);
					twoCode.save();
					count ++;
				} else {
					result.add(twocodeInfo);
					registNumberExistCount ++;
				}
				//计数第几行
				rowNum ++;
			}
			if(!"".equals(incomplete)) {
				incomplete = incomplete.substring(2);
			}
			map.put("count", count);
			map.put("incomplete", incomplete);
			map.put("result", result);
			map.put("importFailer", registNumberExistCount + incompleteCount);
			map.put("success", "success");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("success", "failer");
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("success", "failer");
		} finally {
			//释放资源
			try {
				workBook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new JsonView(map);
	}
	
	//标签领用导入
	public View chukuExport(MultipartFile file) {
		Map<String,Object> result = new HashMap<String,Object>();
		int exportSuccess = 0;
		int registNumberNoExistCount = 0;
		int companyNoExistCount = 0;
		int incompleteCount = 0;
		String incomplete = "";
		String registNumberNoExist = "";
		String companyNoExist = "";
		int failDataCount = 0;
		List<TwocodeVO> failData = new ArrayList<TwocodeVO>();
		//获取上传文件名
		String filename = file.getOriginalFilename();
		System.out.println(filename);
		//获取一个文件工作簿
		Workbook workBook=null;
		int rowNum = 1;
		String type = "";
		try {
			workBook = new XSSFWorkbook(file.getInputStream());
			//获取一个sheet
			Sheet sheet = workBook.getSheetAt(0);
			Row row = null;
			List<TwocodeVO> twocodeInfos = null;
			TwocodeVO twocodeInfo = null;
			CompanyInfo companyInfo = null;
			String str = "";
			String cellStr = "";
			Row rowFirst = sheet.getRow(0);
			while(sheet.getRow(rowNum) != null) {
				row = sheet.getRow(rowNum);
				TwocodeVO twoCode = new TwocodeVO();
				for(int i = 0;i < rowFirst.getLastCellNum();i ++) {
					if(row.getCell(i) == null) {
						str = "";
					} else {
						try {
							str = row.getCell(i).getStringCellValue();
						} catch (Exception e) {
							if (DateUtil.isCellDateFormatted(row.getCell(i))) {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								Date date = row.getCell(i).getDateCellValue();  
								str = sdf.format(date); 
							} else {
								Double cellValue = row.getCell(i).getNumericCellValue();
								str = new DecimalFormat("#").format(cellValue);
							}
						}
					}
					cellStr = rowFirst.getCell(i).getStringCellValue();
					if("标签编号".equals(cellStr)) {
						twoCode.setRegistNumber(str);
					}
					if("领用类型（个人/公司）".equals(cellStr)) {
						type = str;
					}
					if("领用人单位".equals(cellStr)) {
						twoCode.setYwCompanyName(str.length()>2?str:"");
					}
					if("领用人姓名".equals(cellStr)) {
						twoCode.setUserName(str.length()>1?str:"");
					}
					if("领用人账号（个人领用必填）".equals(cellStr)) {
						twoCode.setLoginName(str.length()>2?str:"");
					}
					if("领用日期".equals(cellStr)) {
						twoCode.setRtime(str.length()>2?str:"");
					}
				}
				if(!twoCode.getRegistNumber().matches("[0-9]{6}")) {
					incompleteCount ++;
					incomplete = incomplete + ", " + twoCode.getRegistNumber();
					//计数第几行
					rowNum ++;
					continue;
				}
				if(twoCode.getUserName() == null || "".equals(twoCode.getUserName())) {
					incompleteCount ++;
					incomplete = incomplete + ", " + twoCode.getRegistNumber();
					//计数第几行
					rowNum ++;
					continue;
				}
				if(twoCode.getRtime() == null || "".equals(twoCode.getRtime())) {
					incompleteCount ++;
					incomplete = incomplete + ", " + twoCode.getRegistNumber();
					//计数第几行
					rowNum ++;
					continue;
				}
				if("个人".equals(type)) {
					if(twoCode.getLoginName() == null || "".equals(twoCode.getLoginName())) {
						incompleteCount ++;
						incomplete = incomplete + ", " + twoCode.getRegistNumber();
						//计数第几行
						rowNum ++;
						continue;
					}
				} else if ("公司".equals(type)) {
					if(twoCode.getYwCompanyName() == null || "".equals(twoCode.getYwCompanyName())) {
						incompleteCount ++;
						incomplete = incomplete + ", " + twoCode.getRegistNumber();
						//计数第几行
						rowNum ++;
						continue;
					}
				} else {
					if((twoCode.getLoginName() == null || "".equals(twoCode.getLoginName())) && (twoCode.getYwCompanyName() == null || "".equals(twoCode.getYwCompanyName()))) {
						incompleteCount ++;
						incomplete = incomplete + ", " + twoCode.getRegistNumber();
						//计数第几行
						rowNum ++;
						continue;
					}
				}
				//查询数据库
				//twocodeInfo = TwocodeVO.findFirst(TwocodeVO.class, "registNumber=?", new Object[]{twoCode.getRegistNumber()});
				twocodeInfos = TwocodeVO.findBySql(TwocodeVO.class, "select t.*,c.companyName as ywCompanyName from TwoCodeInfo t left join TwoCodeCompanyInfo c on t.ywcompanyId=c.id where t.registNumber=?", new Object[]{twoCode.getRegistNumber()});
				if(twocodeInfos != null && twocodeInfos.size() != 0) {
					twocodeInfo = twocodeInfos.get(0);
				} else {
					twocodeInfo = null;
				}
				if(twocodeInfo == null) {
					//标签编号不存在
					registNumberNoExistCount ++;
					registNumberNoExist = registNumberNoExist + ", " + twoCode.getRegistNumber();
				} else {
					//标签编号存在
					if(!"".equals(twoCode.getYwCompanyName())) {
						companyInfo = CompanyInfo.findFirst(CompanyInfo.class, "companyName=? and type=?", new Object[]{twoCode.getYwCompanyName(),"维保"});
						if(companyInfo == null) {
							//领用人单位不存在
							companyNoExistCount ++;
							companyNoExist = companyNoExist + ", " + twoCode.getRegistNumber();
							//计数第几行
							rowNum ++;
							continue;
						} else {
							twoCode.setYwCompanyId(companyInfo.getId());
						}
					}
					if(twocodeInfo.getInfoState() != 0) {
						//已经被领用或者被粘贴了
						failDataCount ++;
						failData.add(twocodeInfo);
						//计数第几行
						rowNum ++;
						continue;
					}
					//更新数据
					TwoCodeInfo.updateAll(TwoCodeInfo.class, "infoState=?,ywcompanyId=?,userName=?,loginName=?,rtime=?", new Object[]{1, twoCode.getYwCompanyId(),twoCode.getUserName(),twoCode.getLoginName(),twoCode.getRtime()},"registNumber=?" ,new Object[]{twoCode.getRegistNumber()});
					exportSuccess ++;
				}
				//计数第几行
				rowNum ++;
			}
			if(!"".equals(incomplete))
				incomplete = incomplete.substring(2);
			if(!"".equals(registNumberNoExist))
				registNumberNoExist = registNumberNoExist.substring(2);
			if(!"".equals(companyNoExist))
				companyNoExist = companyNoExist.substring(2);
			result.put("exportSuccess", exportSuccess);
			result.put("registNumberNoExistCount", registNumberNoExistCount);
			result.put("companyNoExistCount", companyNoExistCount);
			result.put("incompleteCount", incompleteCount);
			result.put("incomplete", incomplete);
			result.put("registNumberNoExist", registNumberNoExist);
			result.put("companyNoExist", companyNoExist);
			result.put("failDataCount", failDataCount);
			result.put("failData", failData);
			result.put("exportFailer", registNumberNoExistCount + companyNoExistCount + incompleteCount + failDataCount);
			result.put("success", "success");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("success", "failer");
		} finally {
			//释放资源
			try {
				workBook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new JsonView(result);
	}

} 
