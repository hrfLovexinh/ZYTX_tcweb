package com.zytx.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;



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
import com.zytx.models.AlarmInfo;
import com.zytx.models.AlarmInfoVO;
import com.zytx.models.CompanyInfo;
import com.zytx.models.ElevaltorInfo;
import com.zytx.models.ElevaltorInfoVO;
import com.zytx.models.ImageVO;
import com.zytx.models.CarDevCard;
import com.zytx.models.TCUserInfoView;
import com.zytx.models.UserExtInfo;
import com.zytx.models.UserInfo;
import com.zytx.models.ImageInfo;
import com.zytx.models.ImageInfo2;
import com.zytx.models.TwoCodeDeviceRelationInfo;
import com.zytx.models.UserInfoVO;
import com.zytx.models.WgGroup;
import com.zytx.models.YwInfo;
import com.zytx.models.YwInfoVO;

public class WgController extends ApplicationController{  
	 public View wglistByOrder(int page, int rows){
		  System.out.println("wglistByOrder");
		  
		  long total=0;
		  List<WgGroup> items = null;
		  String sql ="select 1 as id,'xyz' as gName, '456' as gheader";
		  try {
			total =WgGroup.countBySql(WgGroup.class,"select count(*) from  twoCodeCompanyinfo",null); 	
			items = WgGroup.findBySql(WgGroup.class, sql, null, "t.id desc", rows, (page-1)*rows);
		} catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 
		  
		  Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		  
	 }
} 
