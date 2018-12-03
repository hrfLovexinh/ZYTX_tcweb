package com.zytx.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.exception.ActiveRecordException;
import com.et.ar.exception.TransactionException;
import com.et.mvc.Controller;
import com.et.mvc.JsonView;
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

} 
