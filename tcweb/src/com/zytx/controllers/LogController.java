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
import com.zytx.models.CompanyInfo;
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
import com.zytx.models.ElevaltorInfoVO;
import com.zytx.models.ImageVO;
import com.zytx.models.CarDevCard;
import com.zytx.models.SysSetings;
import com.zytx.models.SysSetingsVO;
import com.zytx.models.TCUserInfoView;
import com.zytx.models.TwoCodeElevatorYwCompanyInfo;
import com.zytx.models.TwoCodeLogInfoVO;
import com.zytx.models.UserExtInfo;
import com.zytx.models.UserInfo;
import com.zytx.models.ImageInfo;
import com.zytx.models.ImageInfo2;
import com.zytx.models.TwoCodeDeviceRelationInfo;
import com.zytx.models.UserInfoVO;
import com.zytx.models.YwInfoVO;
import com.et.mvc.filter.AfterFilter;
import com.et.mvc.filter.AfterFilters;


public class LogController extends ApplicationController{ 
	
	
	 public View zhijianloglist(int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 long total=0;
		 List<TwoCodeLogInfoVO> items = null; 
		 try {
			total  =TwoCodeLogInfoVO.count(TwoCodeLogInfoVO.class, null, null);
			String sql ="select id,logPerson,logPersonCompany,CONVERT(CHAR(19), logTime, 20) as logTime,logAction,logContext from TwoCodeloginfo";
			items = TwoCodeLogInfoVO.findBySql(TwoCodeLogInfoVO.class, sql, null, "id desc", rows, (page-1)*rows);
		    	
		} catch (ActiveRecordException e) {	
			e.printStackTrace();
		}
		Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		 
	 }
	
	 public View zhijianlogquery(TwoCodeLogInfoVO info,int page, int rows){
		 String qstartTime ="";
		 String qendTime="";
		 String logPerson="";
		 String logPersonCompany ="";
		 
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 logPerson="%"+info.getLogPerson()+"%";
		 logPersonCompany="%"+info.getLogPersonCompany()+"%";
		 
		 List<TwoCodeLogInfoVO> items = null; 
		 long total =0;
		 String sql ="";
		 String sql2 ="";
		 Object[] param=null;
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 sql ="select count(*) from TwoCodeloginfo where logPerson like ? and logPersonCompany like ? ";
		 param = new Object[]{logPerson,logPersonCompany};
		 sql2 ="select id,logPerson,logPersonCompany,CONVERT(CHAR(19), logTime, 20) as logTime,logAction,logContext from TwoCodeloginfo where logPerson like ? and logPersonCompany like ?  ";
		 
		 if(!"".equals(qstartTime) && "".equals(qendTime)){
			sql = sql + " and  logTime >= ? ";
			sql2 = sql2 + " and  logTime >= ? ";
			param = new Object[]{logPerson,logPersonCompany,qstartTime};
		 }
		 else if("".equals(qstartTime) && !"".equals(qendTime) ){
			 sql = sql + " and logTime <= ?"; 
			 sql2 = sql2 + " and logTime <= ? ";
			 param = new Object[]{logPerson,logPersonCompany,qendTime};
		 }
		 else{
			 if(!"".equals(qstartTime) && !"".equals(qendTime) ){
			  sql = sql + " and logTime >= ?  and  logTime < = ? ";  
			  sql2 = sql2 + " and logTime >= ?  and  logTime < = ?  ";
			  param = new Object[]{logPerson,logPersonCompany,qstartTime,qendTime};
			 }
		 }
		 try {
			total  =TwoCodeLogInfoVO.countBySql(TwoCodeLogInfoVO.class, sql,param);
			items=TwoCodeLogInfoVO.findBySql(TwoCodeLogInfoVO.class, sql2, param, "id desc",rows, (page-1)*rows);
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		
		 result.put("total", total);
		 result.put("rows", items);
		 return new JsonView(result);	
	 }
	 
		
} 
