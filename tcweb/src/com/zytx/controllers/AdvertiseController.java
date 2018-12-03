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
import com.zytx.models.DdAppendixEleInfo;
import com.zytx.models.DdElevaltorInfo;
import com.zytx.models.DdElevaltorInfoVO;
import com.zytx.models.DdTwoCodeElevatorYwCompanyInfo;
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
import com.zytx.models.UserExtInfo;
import com.zytx.models.UserInfo;
import com.zytx.models.ImageInfo;
import com.zytx.models.ImageInfo2;
import com.zytx.models.TwoCodeDeviceRelationInfo;
import com.zytx.models.EleChangeInfo;
import com.zytx.models.UserInfoVO;
import com.zytx.models.TCUserInfoView;
import com.zytx.models.YwInfoVO;
import com.et.mvc.filter.AfterFilter;


//@AfterFilter(execute="eleChange",only={"update"})
public class AdvertiseController extends ApplicationController{  
	 public View query(DdElevaltorInfoVO info,int page, int rows){
		  
		  UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		  int userid = userinfo.getId();
		  String userName =userinfo.getLoginName();
		  int  role = userinfo.getRole();
		  String areaName="";
		  String address ="";
		  String buildingName="";
		  String deviceId2="0";
		
		  
		  if(info.getAddress()==null)
			 address ="%"+""+"%";	 
		  else
			 address ="%"+info.getAddress()+"%";
			 
			 
		  if(info.getBuildingName()==null)
			 buildingName ="%"+""+"%";	 
		  else
			 buildingName ="%"+info.getBuildingName()+"%";
		  
		  if(info.getArea()==null)
			  areaName="%"+""+"%";
		   else
			  areaName ="%"+info.getArea()+"%";
		  
		  deviceId2 =info.getDeviceId2();
		
		  
		//  String countBysql ="";
		  String countBysql1="select count(*) from TwoCodeDdElevatorInfo t where t.area like ? and t.address like ? and t.buildingName like ?  and t.recordSate !=3 ";
		  String countBysql2="select count(*) from TwoCodeElevatorInfo t where t.area like ? and t.address like ? and t.buildingName like ?  ";
		  
		  String sql ="";
		  String sql1 ="select isnull(t.registNumber,'') as registNumber,isnull(t.address,'') as address, isnull(t.buildingName,'') as buildingName,isnull(t.area,'') as area,isnull(t.useNumber,'') as useNumber,isnull(t.building,'') as building,isnull(t.unit,'') as unit from TwoCodeDdElevatorInfo t where t.area like ? and t.address like ? and t.buildingName like ? and t.recordSate !=3 ";
		  String sql2 ="select isnull(t.registNumber,'') as registNumber,isnull(t.address,'') as address, isnull(t.buildingName,'') as buildingName,isnull(t.area,'') as area,isnull(t.useNumber,'') as useNumber,isnull(t.building,'') as building,isnull(t.unit,'') as unit from TwoCodeElevatorInfo t where t.area like ? and t.address like ? and t.buildingName like ? ";
			
		  if(role==100){
			  if("1".equals(deviceId2)){
				  countBysql1=countBysql1 + " and (t.deviceId2 is not null and t.deviceId2 !="+"\'"+"\' ) ";
				  countBysql2=countBysql2 + " and (t.deviceId2 is not null and t.deviceId2 !="+"\'"+"\' ) ";
				  sql1 =sql1 + " and (t.deviceId2 is not null and t.deviceId2 !="+"\'"+"\' ) ";
				  sql2 =sql2 + " and (t.deviceId2 is not null and t.deviceId2 !="+"\'"+"\' ) ";
					
			  }
			  if("2".equals(deviceId2)){
				  countBysql1=countBysql1 + " and (t.deviceId2 is  null or t.deviceId2 ="+"\'"+"\' ) ";
				  countBysql2=countBysql2 + " and (t.deviceId2 is  null or t.deviceId2 ="+"\'"+"\' ) ";
				  sql1 =sql1 + " and (t.deviceId2 is  null or t.deviceId2 ="+"\'"+"\' ) ";
				  sql2 =sql2 + " and (t.deviceId2 is  null or t.deviceId2 ="+"\'"+"\' ) ";
					
			  }
		  }
		  Object[] param=null;
		  param = new Object[]{areaName,address,buildingName,areaName,address,buildingName};
		  
		  
		  sql =sql1+" union all "+sql2;
		  sql ="select isnull(tb.registNumber,'') as registNumber,isnull(tb.address,'') as address, isnull(tb.buildingName,'') as buildingName,isnull(tb.area,'') as area,isnull(tb.useNumber,'') as useNumber,isnull(tb.building,'') as building,isnull(tb.unit,'') as unit from ( " +sql1+" union all "+sql2+ " ) tb ";
		  long total=0;
		  long total1=0;
		  long total2=0;
		  List<DdElevaltorInfoVO> items=null;
		  try {
			total1 = DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class, countBysql1, param); 
			total2 = DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class, countBysql2, param);
			total = total1 + total2;
		    items=DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, param, "tb.registNumber desc", rows, (page-1)*rows);
		} catch (ActiveRecordException e) {
		   e.printStackTrace();
		   System.out.print("AdvertiseController--->query:“Ï≥£");
		}
		 
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total",total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }

	 public View statisticquery(DdElevaltorInfoVO info,int page, int rows){
		  
		  UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		  int userid = userinfo.getId();
		  String userName =userinfo.getLoginName();
		  int  role = userinfo.getRole();
		  int companyId =0;
		  
		  try {
			UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
			 if(userInfoVO != null){
				 companyId = userInfoVO.getCompanyId();
			 }
		} catch (ActiveRecordException e1) {
			e1.printStackTrace();
		}
	    	
		  String areaName="";
		  String address ="";
		  String buildingName="";
		  String qstartTime ="";
		  String qendTime="";
		  int shehePersonId2 =0;
		  
		  if(info.getAddress()==null)
			 address ="%"+""+"%";	 
		  else
			 address ="%"+info.getAddress()+"%";
			 
			 
		  if(info.getBuildingName()==null)
			 buildingName ="%"+""+"%";	 
		  else
			 buildingName ="%"+info.getBuildingName()+"%";
		  
		  if(info.getArea()==null)
			  areaName="%"+""+"%";
		   else
			  areaName ="%"+info.getArea()+"%";
		  
		  qstartTime =info.getQstartTime();
		  qendTime = info.getQendTime();
		  shehePersonId2=info.getShehePersonId2();
		  
		//  String countBysql ="";
		  String countBysql1="select count(*) from TwoCodeDdElevatorInfo t  left join TwoCodeUserExtInfo tu on t.shehePersonId2 = tu.userid where t.area like ? and t.address like ? and t.buildingName like ?  and t.recordSate !=3 and tu.companyId = ? ";
		  String countBysql2="select count(*) from TwoCodeElevatorInfo t  left join TwoCodeUserExtInfo tu on t.shehePersonId2 = tu.userid  where t.area like ? and t.address like ? and t.buildingName like ?  and tu.companyId = ? ";
		  
		  String sql ="";
		  String sql1 ="select CONVERT(CHAR(19), t.shenheTime2, 120) as shenheTime2,isnull(tu.userName,'') as userName,isnull(t.registNumber,'') as registNumber,isnull(t.address,'') as address, isnull(t.buildingName,'') as buildingName,isnull(t.area,'') as area,isnull(t.useNumber,'') as useNumber,isnull(t.building,'') as building,isnull(t.unit,'') as unit,isnull(t.deviceId2,'') as deviceId2 from TwoCodeDdElevatorInfo t left join TwoCodeUserExtInfo tu on t.shehePersonId2 = tu.userid where t.area like ? and t.address like ? and t.buildingName like ? and t.recordSate !=3  and tu.companyId = ? ";
		  String sql2 ="select CONVERT(CHAR(19), t.shenheTime2, 120) as shenheTime2,isnull(tu.userName,'') as userName,isnull(t.registNumber,'') as registNumber,isnull(t.address,'') as address, isnull(t.buildingName,'') as buildingName,isnull(t.area,'') as area,isnull(t.useNumber,'') as useNumber,isnull(t.building,'') as building,isnull(t.unit,'') as unit,isnull(t.deviceId2,'') as deviceId2 from TwoCodeElevatorInfo t left join TwoCodeUserExtInfo tu on t.shehePersonId2 = tu.userid where t.area like ? and t.address like ? and t.buildingName like ? and tu.companyId = ? ";
			
		  if(role==100){
				  countBysql1=countBysql1 + " and (t.deviceId2 is not null and t.deviceId2 !="+"\'"+"\' ) ";
				  countBysql2=countBysql2 + " and (t.deviceId2 is not null and t.deviceId2 !="+"\'"+"\' ) ";
				  sql1 =sql1 + " and (t.deviceId2 is not null and t.deviceId2 !="+"\'"+"\' ) ";
				  sql2 =sql2 + " and (t.deviceId2 is not null and t.deviceId2 !="+"\'"+"\' ) ";	
		  }
		  Object[] param=null;
		  param = new Object[]{areaName,address,buildingName,companyId,areaName,address,buildingName,companyId};
		  
		  if(!"".equals(qstartTime)){
			  countBysql1 = countBysql1 + " and t.shenheTime2 > " +"\'"+ qstartTime +"\'";
			  countBysql2 = countBysql2 + " and t.shenheTime2 > " +"\'"+ qstartTime +"\'";
			  sql1 = sql1 + " and t.shenheTime2 > " + "\'"+qstartTime+"\'";
			  sql2 = sql2 + " and t.shenheTime2 > " + "\'"+qstartTime+"\'";
			  
		  }
		  if(!"".equals(qendTime))
		  {
			  countBysql1 = countBysql1 + " and t.shenheTime2 <= " + "\'"+qendTime+"\'"; 
			  countBysql2 = countBysql2 + " and t.shenheTime2 <= " + "\'"+qendTime+"\'"; 
			  sql1 = sql1 + " and t.shenheTime2 <= " + "\'"+qendTime+"\'"; 
			  sql2 = sql2 + " and t.shenheTime2 <= " + "\'"+qendTime+"\'"; 
		  }
		  
		  if(shehePersonId2 > 0){
			  countBysql1 = countBysql1 + " and t.shehePersonId2 = " + shehePersonId2;
			  countBysql2 = countBysql2 + " and t.shehePersonId2 = " + shehePersonId2;
			  sql1 = sql1 + " and t.shehePersonId2 = " + shehePersonId2;
			  sql2 = sql2 + " and t.shehePersonId2 = " + shehePersonId2;
			 
		  }
		  
		  sql =sql1+" union all "+sql2;
		  
		  sql ="select CONVERT(CHAR(19), tb.shenheTime2, 120) as shenheTime2,isnull(tb.userName,'') as userName,isnull(tb.registNumber,'') as registNumber,isnull(tb.address,'') as address, isnull(tb.buildingName,'') as buildingName,isnull(tb.area,'') as area,isnull(tb.useNumber,'') as useNumber,isnull(tb.building,'') as building,isnull(tb.unit,'') as unit,isnull(tb.deviceId2,'') as deviceId2 from ( " + sql + " ) tb";
		  long total=0;
		  long total1=0;
		  long total2=0;
		  List<DdElevaltorInfoVO> items=null;
		  try {
			total1 = DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class, countBysql1, param); 
			total2 = ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, countBysql2, param);
			total = total1 + total2;
		    items=DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, param, "tb.registNumber desc", rows, (page-1)*rows);
		} catch (ActiveRecordException e) {
		   e.printStackTrace();
		   System.out.print("AdvertiseController--->statisticquery:“Ï≥£");
		}
		 
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total",total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	 
	 public View allUserByCompanyId(int page, int rows) throws Exception {
		  UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		  int userid = userinfo.getId();
		  String userName =userinfo.getLoginName();
		  int  role = userinfo.getRole();
		  int companyId =0;
		  
		  try {
			UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
			 if(userInfoVO != null){
				 companyId = userInfoVO.getCompanyId();
			 }
		} catch (ActiveRecordException e1) {
			e1.printStackTrace();
		}
			List<TCUserInfoView> items = TCUserInfoView.findBySql(TCUserInfoView.class,
					"select * from TCUserInfo where companyid = ?", new Object[] { companyId });
			return new JsonView(items);
		}
	 
	

} 
