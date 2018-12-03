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
import com.zytx.models.JobPerformanceVO;
import com.zytx.models.SysSetings;
import com.zytx.models.SysSetingsVO;
import com.zytx.models.TCUserInfoView;
import com.zytx.models.TwoCodeElevatorYwCompanyInfo;
import com.zytx.models.UserExtInfo;
import com.zytx.models.UserInfo;
import com.zytx.models.ImageInfo;
import com.zytx.models.ImageInfo2;
import com.zytx.models.TwoCodeDeviceRelationInfo;
import com.zytx.models.UserInfoVO;
import com.zytx.models.YwInfoVO;
import com.et.mvc.filter.AfterFilter;
import com.et.mvc.filter.AfterFilters;


public class JobController extends ApplicationController{ 
	
	public View jobPerformance(){
		Map<String, Object> result = new HashMap<String, Object>();
		  UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		  int userid = userinfo.getId();
		  String userName =userinfo.getLoginName();
		  int  role = userinfo.getRole();
		  
		  List<JobPerformanceVO> items = null;
		  String sql="select '救援案件数' as itemName,666 as jjtotal,778 as qytotal,836 as jntotal,996 as whtotal,915 as chtotal,1118 as gxtotal,396 as tfxtotal,537 as lqytotal,157 as qbjtotal,758 as xdtotal,470 as wjtotal,172 as jttotal,871 as sltotal,623 as pxtotal,113 as dytotal,84 as pjtotal,134 as xjtotal,203 as djytotal,157 as pztotal,101 as qltotal,138 as cztotal,127 as jytotal" +
		  " union all select '一级救援' as itemName,600 as jjtotal,701 as qytotal,754 as jntotal,899 as whtotal,823 as chtotal,1008 as gxtotal,330 as tfxtotal,485 as lqytotal,142 as qbjtotal,684 as xdtotal,415 as wjtotal,156 as jttotal,786 as sltotal,562 as pxtotal,102 as dytotal,40 as pjtotal,121 as xjtotal,184 as djytotal,142 as pztotal,92 as qltotal,125 as cztotal,115 as jytotal" +
          " union all select '二级救援' as itemName,60 as jjtotal,70 as qytotal,75 as jntotal,89 as whtotal,82 as chtotal,100 as gxtotal,33 as tfxtotal,48 as lqytotal,14 as qbjtotal,68 as xdtotal,41 as wjtotal,15 as jttotal,78 as sltotal,56 as pxtotal,10 as dytotal,4 as pjtotal,12 as xjtotal,18 as djytotal,14 as pztotal,9 as qltotal,12 as cztotal,11 as jytotal" +
          " union all select '三级救援' as itemName,6 as jjtotal,7 as qytotal,7 as jntotal,8 as whtotal,8 as chtotal,10 as gxtotal,3 as tfxtotal,4 as lqytotal,1 as qbjtotal,6 as xdtotal,4 as wjtotal,1 as jttotal,7 as sltotal,5 as pxtotal,1 as dytotal,4 as pjtotal,1 as xjtotal,1 as djytotal,1 as pztotal,0 as qltotal,1 as cztotal,1 as jytotal";
		  
		  try {
			items = JobPerformanceVO.findBySql(JobPerformanceVO.class, sql, null);
		} catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
	    	
		  result.put("rows", items);
		  return new JsonView(result);
	}
	
} 
