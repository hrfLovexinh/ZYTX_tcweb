package com.zytx.controllers;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.et.ar.ActiveRecordBase;
import com.et.ar.exception.ActiveRecordException;
import com.et.ar.exception.TransactionException;
import com.et.mvc.JsonView;
import com.et.mvc.MultipartFile;
import com.et.mvc.MultipartRequest;
import com.et.mvc.View;
import com.et.mvc.filter.AfterFilter;
import com.et.mvc.filter.AfterFilters;
import com.zytx.cxf.ws.client.TsDeivceService;
import com.zytx.cxf.ws.client.TsDeviceService;
import com.zytx.init.GlobalFunction;
import com.zytx.models.AreaInfo;
import com.zytx.models.CompanyInfo;
import com.zytx.models.CompanyInfoVO;
import com.zytx.models.CsEchartsElevaltorInfoVO;
import com.zytx.models.CsEchartsYwInfoVO;
import com.zytx.models.DbrukuinfoVO;
import com.zytx.models.DdAppendixEleInfo;
import com.zytx.models.DdElevaltorInfo;
import com.zytx.models.DdElevaltorInfoNewVO;
import com.zytx.models.DdElevaltorInfoVO;
import com.zytx.models.DdTwoCodeElevatorYwCompanyInfo;
import com.zytx.models.DdTwoCodeElevatorYwCompanyInfoNew;
import com.zytx.models.DdYuanShiElevaltorInfo;
import com.zytx.models.DdYuanShiElevaltorInfoVO;
import com.zytx.models.EleChangeInfo;
import com.zytx.models.EleChangeInfoVO;
import com.zytx.models.ElevaltorInfo;
import com.zytx.models.ElevaltorInfoNewVO;
import com.zytx.models.ElevaltorInfoVO;
import com.zytx.models.ElevaltorQueryExportVO;
import com.zytx.models.ElevatorCoordinate;
import com.zytx.models.GjQueryInfo;
import com.zytx.models.GjQueryInfoVO;
import com.zytx.models.JyNewRegistInfoVO;
import com.zytx.models.MapElevaltorInfoVO;
import com.zytx.models.NewDdYuanShiElevaltorInfoVO;
import com.zytx.models.SelevaltorInfo;
import com.zytx.models.SelevaltorInfoVO;
import com.zytx.models.SelevaltorInfoView;
import com.zytx.models.SysSetings;
import com.zytx.models.SysSetingsVO;
import com.zytx.models.SystjInfoVO;
import com.zytx.models.TCUserInfoView;
import com.zytx.models.TwoCodeDeviceRelationInfo;
import com.zytx.models.TwoCodeElelog;
import com.zytx.models.TwoCodeElevatorYwCompanyInfo;
import com.zytx.models.TwoCodeLogInfo;
import com.zytx.models.Twocode96333;
import com.zytx.models.Twocode96333pVO;
import com.zytx.models.TwocodeVO;
import com.zytx.models.UserExtInfo;
import com.zytx.models.UserInfo;
import com.zytx.models.UserInfoVO;
import com.zytx.models.YwInfoVO;
import com.zytx.models.YwKaoHmdInfo;
import com.zytx.models.YwKaoHmdInfoVO;
import com.zytx.models.YwKaoPingInfo;
import com.zytx.models.YwKaoPingInfoVO;
import com.zytx.models.YwQuaCredRating;
import com.zytx.models.YwQuaCredRatingConstant;
import com.zytx.models.YwQuaCredRatingHyxh;
import com.zytx.models.YwQuaCredRatingQzj;
import com.zytx.models.YwQuaCredRatingTjy;
import com.zytx.models.YwQuaCredRatingVO;
import com.zytx.models.YwSysSetingsItemDetailVO;
import com.zytx.models.ZXElevaltorInfoVO;
import com.zytx.util.ElevatorQueryExportExcel2;
import com.zytx.util.ExportExcel;
import com.zytx.util.ExportExcel2;
import com.zytx.util.OkHttpUtil;


@AfterFilters({
@AfterFilter(execute="relationrukulog",only={"relationrukudelevattor"}),
@AfterFilter(execute="deletelog",only={"delete"}),
@AfterFilter(execute="querylog",only={"query"}),
@AfterFilter(execute="putdailingelelog",only={"putdailingele"}),
@AfterFilter(execute="claimdailingelelog",only={"claimdailingele"}),
@AfterFilter(execute="elevatorlistlog",only={"elevatorlist"}),
@AfterFilter(execute="deletezhantieElelog",only={"deletezhantieEle"}),
@AfterFilter(execute="zhuxiaoElelog",only={"zhuXiao"})
})
public class ElevatorController extends ApplicationController{  
	 
	 protected String rukunames = "";
	 protected String rukunames2 = "";
	 protected String rukuresult = "";
	 protected int    deleteId =0;
	 protected String deleteresult = "";
	 protected String dlregistNumber ="";
	 protected String dlregistNumber2 ="";
	 protected String deleteregistNumber ="";
	 protected String deletezhantieregistNumber ="";
	 protected int    zhuxiaoId =0;
	 protected String zhuxiaoresult = "";
	 protected String zhuxiaoregistNumber ="";
	 
	 
	 public View cselevatorlistByCompanyId(int page, int rows,int companyId){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 
		 long total=0;
		 List<ElevaltorInfoVO> items = null;
		 if(userinfo != null){
		  try {
				if(companyId > 0){
				   total  =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, "select count(*) from TwoCodeCompanyEle where companyid = ? ", new Object[]{companyId});
				   String sql ="select te.registNumber,te.registCode,te.shemiFlag,te.address,te.buildingName,te.area,te.useNumber,te.nextInspectDate,te.dailingFlag,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =te.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime from TwoCodeCompanyEle t left join TwoCodeElevatorInfo te on t.registNumber = te.registNumber where t.companyid = ? ";
				   items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, new Object[]{companyId}, null, rows, (page-1)*rows);
				 }
				 } catch (ActiveRecordException e) {
				e.printStackTrace();
			}
		 }
		 Map<String, Object> result = new HashMap<String, Object>();
	     result.put("total", total);
		 result.put("rows", items);
		 return new JsonView(result);
		 
	 }
	
	 public View elevatorlistByOrder(int page, int rows,String sort,String order){		
			 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
			 String cityName = GlobalFunction.cityName;
			 int userid = userinfo.getId();
			 String userName =userinfo.getLoginName();
			 int  role = userinfo.getRole();
			
			 long total=0;
			 List<ElevaltorInfoNewVO> items = null;
			 
			 String SortName = request.getParameter("sort");  
		        //�״μ��ر���ʱsort��orderΪnull  
		     if(SortName == null){ 
                 if("cy".equals(userName)){
                	 SortName ="y.endTime";  
                 }
                 else{
		            SortName ="t.updateTime"; 
                 }
		      }
		     if("registNumber".equals(SortName)){
		    	 SortName ="t.registNumber"; 
		     }
		        
		     String SortValue =request.getParameter("order");  
		        if(SortValue == null){  
		            SortValue = "desc";        
		        } 
		       
		     String sql ="";
			 try {
		     if(role==2 || role ==1){ //ϵͳ����Ա TwoCodeCompanyInfo 
		    	 
				 total  =ElevaltorInfoNewVO.count(ElevaltorInfoNewVO.class, null, null); 
				 if("1".equals(cityName)){ 
				  sql ="select t.jyjyFlag, t.shibieCode,t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,t.azCompanyId,t.wgCompanyId,t.ywCompanyId,t.zjCompanyId,t.jyCompanyId,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,y.endTime as subTime,  isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.usePlace from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join YwManagerInfo  y  on t.registNumber = y.registNumber  ";			 
				 }
				 else{
				  sql ="select t.jyjyFlag,t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,t.azCompanyId,t.wgCompanyId,t.ywCompanyId,t.zjCompanyId,t.jyCompanyId,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,y.endTime as subTime,  isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.usePlace from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join YwManagerInfo  y  on t.registNumber = y.registNumber  ";
			//		 sql ="select t.jyjyFlag,t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join YwManagerInfo  y  on t.registNumber = y.registNumber  ";
					 
				 }
				 items = ElevaltorInfoNewVO.findBySql(ElevaltorInfoNewVO.class, sql, null, SortName+" "+SortValue, rows, (page-1)*rows);   	
		     }
		     
		     if(role==22 || role ==23){ //ϵͳ����Ա TwoCodeCompanyInfo  shizhijian
		    	 total  =ElevaltorInfoNewVO.count(ElevaltorInfoNewVO.class, null, null);
		    	 if("1".equals(cityName)){ 
		    	  sql ="select t.jyjyFlag,t.shibieCode,t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,y.endTime as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.usePlace  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join YwManagerInfo  y  on t.registNumber = y.registNumber ";    		 
		    	 }
		    	 else{
		  	      sql ="select t.jyjyFlag,t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,y.endTime as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.usePlace from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join YwManagerInfo  y  on t.registNumber = y.registNumber ";
		    	 }
		    	 items = ElevaltorInfoNewVO.findBySql(ElevaltorInfoNewVO.class, sql, null, SortName+" "+SortValue, rows, (page-1)*rows);
		    	
		     }
		     
		     if(role==8 || role ==9){  //wuguan
			        UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
			    	 if(userInfoVO != null){
			         int wgcompanyId = userInfoVO.getCompanyId();
			    //     String companyArea = userInfoVO.getArea();
			          total  =ElevaltorInfoNewVO.countBySql(ElevaltorInfoNewVO.class, "select count(*) from TwoCodeElevatorInfo t  where t.wgCompanyId=?  ", new Object[]{wgcompanyId});
			          if("1".equals(cityName)){
			        sql ="select t.jyjyFlag,t.shibieCode,t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName,t.usePlace from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,y.endTime as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join YwManagerInfo  y  on t.registNumber = y.registNumber where t.wgCompanyId=? ";    
			          }
			          else{
			          sql ="select t.jyjyFlag,t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName,t.usePlace from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,y.endTime as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join YwManagerInfo  y  on t.registNumber = y.registNumber where t.wgCompanyId=? ";  
			          }
			         items = ElevaltorInfoNewVO.findBySql(ElevaltorInfoNewVO.class, sql, new Object[]{wgcompanyId}, SortName+" "+SortValue, rows, (page-1)*rows);	
			    	 }
			     } 
		     
		     if(role==10 || role ==11){  //zhijian
		        UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
		    	 if(userInfoVO != null){
		         int zjcompanyId = userInfoVO.getCompanyId();
		         String companyArea = userInfoVO.getArea();
		          total  =ElevaltorInfoNewVO.countBySql(ElevaltorInfoNewVO.class, "select count(*) from TwoCodeElevatorInfo t  where t.area=?  ", new Object[]{companyArea});
		          if("1".equals(cityName)){
		        sql ="select t.jyjyFlag,t.shibieCode,t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,y.endTime as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.usePlace  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join YwManagerInfo  y  on t.registNumber = y.registNumber where t.area=? ";    
		          }
		          else{
		          sql ="select t.jyjyFlag,t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,y.endTime as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.usePlace  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join YwManagerInfo  y  on t.registNumber = y.registNumber where t.area=? ";  
		          }
		         items = ElevaltorInfoNewVO.findBySql(ElevaltorInfoNewVO.class, sql, new Object[]{companyArea}, SortName+" "+SortValue, rows, (page-1)*rows);	
		    	 }
		     } 
		     
		     if(role==20 || role ==21){
		    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
		    	 if(userInfoVO != null){
		         int townshipStreets = userInfoVO.getCompanyId();
		    	 total  =ElevaltorInfoNewVO.countBySql(ElevaltorInfoNewVO.class, "select count(*) from TwoCodeElevatorInfo t  where t.townshipStreets=? ", new Object[]{townshipStreets});
		    	  if("1".equals(cityName)){
		    	 sql ="select t.jyjyFlag,t.shibieCode,t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,y.endTime as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.usePlace  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join YwManagerInfo  y  on t.registNumber = y.registNumber where t.townshipStreets=?  ";
		    	  }
		    	  else{
		    	  sql ="select t.jyjyFlag,t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,y.endTime as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.usePlace  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join YwManagerInfo  y  on t.registNumber = y.registNumber where t.townshipStreets=?  ";
		    	  }
		    	 items = ElevaltorInfoNewVO.findBySql(ElevaltorInfoNewVO.class, sql, new Object[]{townshipStreets}, SortName+" "+SortValue, rows, (page-1)*rows);
		    	 
		    	 }
		     } 
			 } catch (ActiveRecordException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      Map<String, Object> result = new HashMap<String, Object>();
		      result.put("total", total);
			  result.put("rows", items);
	//		  System.out.println(new JsonView(result));
			  return new JsonView(result);
		    }

	 public View elevatorlist(int page, int rows) throws Exception{
		
	   //   long total = ImageVO.countBySql(ImageVO.class, "exec ���û���ȡͼƬ��Ϣ  ?", new Object[] { "test" });
	   //   long total =ImageVO.execute("exec ���û���ȡͼƬ��Ϣ ?", new Object[] { "test" });
	  //     List<ImageVO>  items = ImageVO.findBySql(ImageVO.class, "exec ���û���ȡͼƬ��Ϣ  ?", new Object[] { "test" });   
	   //    List<ImageVO> items = ImageVO.findBySql(ImageVO.class, "select CARNUM as carnum,DEV_ID as dev_id from image_201105", null, null, rows, (page-1)*rows);
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 long total=0;
		 List<ElevaltorInfoVO> items = null;
	     if(role==2 || role ==1){ //ϵͳ����Ա TwoCodeCompanyInfo 
	    	 total  =ElevaltorInfoVO.count(ElevaltorInfoVO.class, null, null);
	    	
	    //	 items = ElevaltorInfoVO.findAll(ElevaltorInfoVO.class, null, null, null, rows, (page-1)*rows);
	    	 String sql ="select t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,t.azCompanyId,t.wgCompanyId,t.ywCompanyId,t.zjCompanyId,t.jyCompanyId,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber";
	    //	 String sql ="select t.id,t.registNumber,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,isnull(t.deviceId2,'') as deviceId2,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where t.dailingFlag = 0 ";
	   // 	 String sql ="select t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber  where   t.dailingFlag = 0";
	  	   
	    	 items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null, "t.updateTime desc", rows, (page-1)*rows);
	    	 
	    	
	     }
	     
	     if(role==22 || role ==23){ //ϵͳ����Ա TwoCodeCompanyInfo 
	    	 total  =ElevaltorInfoVO.count(ElevaltorInfoVO.class, null, null);
	    //	 items = ElevaltorInfoVO.findAll(ElevaltorInfoVO.class, null, null, null, rows, (page-1)*rows);
	   // 	 String sql ="select t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,isnull(t.deviceId2,'') as deviceId2  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where t.dailingFlag = 0 ";
	    	 String sql ="select t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber ";
	  	   
	   // 	 String sql ="select t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber  where   t.dailingFlag = 0";
	  	   
	    	 items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null, "t.updateTime desc", rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==10 || role ==11){  
	    //	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	         UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         String companyArea = userInfoVO.getArea();
	    //	 total  =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, "select count(*) from TwoCodeElevatorInfo t  where t.zjCompanyId=? and t.dailingFlag = 0 ", new Object[]{zjcompanyId});
	         total  =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, "select count(*) from TwoCodeElevatorInfo t  where t.area=?  ", new Object[]{companyArea});
	    //	 String sql ="select t.dailingFlag,t.id,t.area,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where t.zjCompanyId=? ";
	         String sql ="select t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where t.area=? ";
	   	  
	    // 	 String sql ="select t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber  where  t.zjcompanyId = ? and t.dailingFlag = 0";
	  	   
	    	 items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, new Object[]{companyArea}, "t.updateTime desc", rows, (page-1)*rows);	
	    	 }
	     } 
	     
	     if(role==20 || role ==21){
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int townshipStreets = userInfoVO.getCompanyId();
	    	 total  =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, "select count(*) from TwoCodeElevatorInfo t  where t.townshipStreets=? ", new Object[]{townshipStreets});
	    	 
	    //	 String sql ="select t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,isnull(t.deviceId2,'') as deviceId2  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where t.townshipStreets=? and t.dailingFlag = 0 ";
	    	 String sql ="select t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where t.townshipStreets=?  ";
	  	   
	    //	 String sql ="select t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber  where  t.townshipStreets=? and t.dailingFlag = 0";
	  	   
	    	 items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, new Object[]{townshipStreets}, "t.updateTime desc", rows, (page-1)*rows);
	    	 
	    	 }
	     } 
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	 
	 public View getCompanyListByType(String companyType)throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 List<CompanyInfo> items = null;
		 if (role==10 || role==11){
			 TCUserInfoView tCUserInfoView =TCUserInfoView.findFirstBySql(TCUserInfoView.class, "select * from TCUserInfo where loginName = ?", new Object[] { userName });
			 String companyName =tCUserInfoView.getCompanyName();
			 items = CompanyInfo.findBySql(CompanyInfo.class,
						"select * from TwoCodeCompanyInfo where companyName = ?", new Object[] { companyName });
		 }
		 else{
		  items = CompanyInfo.findBySql(CompanyInfo.class,
					"select * from TwoCodeCompanyInfo where type = ?", new Object[] { companyType });
		 }
		 
		 return new JsonView(items);
	 }
	 
	 public View getCompanyListByArea(String companyArea)throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 List<CompanyInfo> items = null;
		 if (role==10 || role==11){
			 TCUserInfoView tCUserInfoView =TCUserInfoView.findFirstBySql(TCUserInfoView.class, "select * from TCUserInfo where loginName = ?", new Object[] { userName });
			 String companyName =tCUserInfoView.getCompanyName();
			 int zjCompanyId =tCUserInfoView.getCompanyId();
			 if(!"".equals(companyArea))
			 items = CompanyInfo.findBySql(CompanyInfo.class,
						"select tc.id, tc.companyName from TwoCodeCompanyInfo tc where tc.id in (select t.ywCompanyId from TwoCodeElevatorInfo  t where t.area=? and t.zjCompanyId =? group by t.ywcompanyid )", new Object[] { zjCompanyId,companyArea });
			 else
				 items = CompanyInfo.findBySql(CompanyInfo.class,
							"select tc.id, tc.companyName from TwoCodeCompanyInfo tc where tc.id in (select t.ywCompanyId from TwoCodeElevatorInfo  t where t.zjCompanyId =? group by t.ywcompanyid )", new Object[] { zjCompanyId});
		 }
		 else{
			 if(!"".equals(companyArea))
		      items = CompanyInfo.findBySql(CompanyInfo.class,
					"select tc.id, tc.companyName from TwoCodeCompanyInfo tc where tc.id in (select t.ywCompanyId from TwoCodeElevatorInfo  t where t.area=? group by t.ywcompanyid )", new Object[] { companyArea });
			 else
				 items = CompanyInfo.findBySql(CompanyInfo.class,
							"select tc.id, tc.companyName from TwoCodeCompanyInfo tc where tc.id in (select t.ywCompanyId from TwoCodeElevatorInfo  t  group by t.ywcompanyid )", null);
				 
		 }
		 
		 return new JsonView(items);
	 }
	 
	 public View areaInfoList(){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 List<AreaInfo> items = null;
		 try {
			items = AreaInfo.findBySql(AreaInfo.class, "select '' as areaCode, '' as area union all select t.areaCode, t.area  from TwoCodeAreaInfo t",null);
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return new JsonView(items);
	 }
	 
	 public View getCompanyinfoById(int id) throws Exception {
		   List<UserInfoVO> items = null;
		   String sql ="select tc.id as id,tc.companyName  from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id = te.userid left join TwoCodeCompanyInfo  tc on te.companyid = tc.id where t.id = ?";
		   items = UserInfoVO.findBySql(UserInfoVO.class, sql, new Object[] { id });
			return new JsonView(items);
		}
	 
	 
	 
	 public View getCompanyList(int page, int rows) throws Exception {
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 List<CompanyInfo> items = null;
		 if(role==10 || role==11){
			 TCUserInfoView tCUserInfoView =TCUserInfoView.findFirstBySql(TCUserInfoView.class, "select * from TCUserInfo where loginName = ?", new Object[] { userName });
			 String companyName =tCUserInfoView.getCompanyName();
			 items = CompanyInfo.findAll(CompanyInfo.class, "companyName=?", new Object[] { companyName });
		 }
		 else if(role==22 || role==23){
			 items = CompanyInfo.findAll(CompanyInfo.class, "type like ?", new Object[] { "%�ʼ�%" });
		 }
		 else{
			 items = CompanyInfo.findAll(CompanyInfo.class);
			}
		 
			return new JsonView(items);
		}
	 
	public View getWgCompanyList(int page, int rows) throws Exception {
		List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
				"select id,companyName from TwoCodeCompanyInfo where type = ?", new Object[] { "ʹ��" });
		return new JsonView(items);
	}
	
	public View getYwCompanyList(int page, int rows) throws Exception {
		List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
				"select id,companyName,ispasteyw from TwoCodeCompanyInfo where type = ? and ispasteyw = 0", new Object[] { "ά��" });
		return new JsonView(items);
	}
	
	public View getYwQuaRateCompanyList(String ratingDate) throws Exception {
		 System.out.println("ratingDate----"+ratingDate);
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
	//	 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 String area ="";
		 TCUserInfoView tCUserInfoView =TCUserInfoView.findFirstBySql(TCUserInfoView.class, "select * from TCUserInfo where loginName = ?", new Object[] { userName });
		 int ratingCompanyId=0;
		 CompanyInfoVO companyinfo =null;
		 CompanyInfoVO qzjcompanyinfo =null;
		 
		 String sql = "";
		 Object[] param=null;
		 if(tCUserInfoView != null){
			 ratingCompanyId=tCUserInfoView.getCompanyId();
			 
		 }
		 if(role == 30 || role == 31){
				//��ҵЭ��Ҫ�����ʼ��companyId
	    		companyinfo = CompanyInfoVO.findFirstBySql(CompanyInfoVO.class, "select id  from TwoCodeCompanyInfo where type = ?", new Object[]{"���ʼ�"});
	    		if(companyinfo != null){
	    			ratingCompanyId = companyinfo.getId();
	    		}
		 }
		 
		 if(role == 10 || role == 11){  //�����ʼ�
			 qzjcompanyinfo  =CompanyInfoVO.findFirstBySql(CompanyInfoVO.class, "select area  from TwoCodeCompanyInfo where id = ?", new Object[]{ratingCompanyId});
			 if(qzjcompanyinfo != null){
				 area = qzjcompanyinfo.getArea();
			 }
			 sql ="select isnull(te.ywCompanyId,0) as ywCompanyId, tt.id, tt.companyName,(case when  CHARINDEX('�ѿ���',tt.companyName)>0 then 1 else 0 end) as indexOrder from (select  tc.id,(case when isnull(tr.ratingCompanyId,0) =0  then tc.companyName else  tc.companyName+'(�ѿ���)' end) as companyName,ispasteyw from TwoCodeCompanyInfo tc left join (select ywCompanyID,ratingCompanyId from  TwoCodeYwQuaCredRating where  ratingDate = ? and ratingCompanyId =7   and ratingType =1) tr   on tc.id=tr.ywCompanyID where type = 'ά��' and ispasteyw = 0 ) tt  left join (select ywCompanyId from  TwoCodeElevatorInfo where area = ? group by ywCompanyId )te on tt.id = te.ywCompanyId   where te.ywCompanyId > 0 order by indexOrder desc ";
		     param = new Object[] {ratingDate,area,ratingCompanyId };
		 }
		 else{
			 sql = "select tt.id, tt.companyName,(case when  CHARINDEX('�ѿ���',tt.companyName)>0 then 1 else 0 end) as indexOrder  from (select tc.id,(case when isnull(tr.ratingCompanyId,0) =0  then tc.companyName else  tc.companyName+'(�ѿ���)' end) as companyName,ispasteyw from TwoCodeCompanyInfo tc left join (select ywCompanyID,ratingCompanyId from  TwoCodeYwQuaCredRating where  ratingDate = ? and ratingCompanyId =?   and ratingType =1) tr  on tc.id=tr.ywCompanyID where type = 'ά��' and ispasteyw = 0 ) tt order by indexOrder desc";
			 param = new Object[] {ratingDate,ratingCompanyId };
		 }
		 
		List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
				sql,  param);
		return new JsonView(items);
	}
	
	public View getYwAllCompanyList(int page, int rows) throws Exception {
	//	List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
	//			"select top 100 id,companyName from TwoCodeCompanyInfo where type = ? ", new Object[] { "ά��" });
		List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
							"select  id,companyName from TwoCodeCompanyInfo where type = ? ", new Object[] { "ά��" });
		return new JsonView(items);
	}
	
	public View getZzCompanyList(int page, int rows) throws Exception {
		List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
				"select id,companyName from TwoCodeCompanyInfo where type = ?", new Object[] { "����" });
		return new JsonView(items);
	}
	
	public View getZzCompanyList2(int id,int page, int rows) throws Exception {
		List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
				"select id,companyName from TwoCodeCompanyInfo where id = ?", new Object[] { id });
		return new JsonView(items);
	}
	
	public View getAzCompanyList(int page, int rows) throws Exception {
		List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
				"select id,companyName from TwoCodeCompanyInfo where type = ?", new Object[] { "��װ" });
		return new JsonView(items);
	}
	
	public View getJyCompanyList(int page, int rows) throws Exception {
		List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
				"select id,companyName from TwoCodeCompanyInfo where type = ?", new Object[] { "����" });
		return new JsonView(items);
	}
	
	public View getnewZjCompanyList(int page, int rows) throws Exception {
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<CompanyInfo> items =null;
		if(role==10 || role==11){
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         items = CompanyInfo.findBySql(CompanyInfo.class,
	 				"select id,companyName from TwoCodeCompanyInfo where type = ? and id = ?", new Object[] { "�ʼ�",zjcompanyId});
	    	 }
		}else{
		 items = CompanyInfo.findBySql(CompanyInfo.class,
				"select id,companyName from TwoCodeCompanyInfo where type like ?", new Object[] { "%�ʼ�%" });
		}
		return new JsonView(items);
	}
	
	public View getZjCompanyList(int page, int rows) throws Exception {
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<CompanyInfo> items =null;
		if(role==10 || role==11){
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         items = CompanyInfo.findBySql(CompanyInfo.class,
	 				"select id,companyName from TwoCodeCompanyInfo where type = ? and id = ?", new Object[] { "�ʼ�",zjcompanyId});
	    	 }
		}else{
		 items = CompanyInfo.findBySql(CompanyInfo.class,
				"select id,companyName from TwoCodeCompanyInfo where type = ?", new Object[] { "�ʼ�" });
		}
		return new JsonView(items);
	}
	
	public View getKpZjCompanyList(int page, int rows) throws Exception {
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<CompanyInfo> items =null;
		if(role==10 || role==11){
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         items = CompanyInfo.findBySql(CompanyInfo.class,
	 				"select id,companyName from TwoCodeCompanyInfo where type = ? and id = ?", new Object[] { "�ʼ�",zjcompanyId});
	    	 }
		}else{
		 items = CompanyInfo.findBySql(CompanyInfo.class,
				"select id,companyName from TwoCodeCompanyInfo where (type like ? or type = ? or type = ?) and companyName != ?", new Object[] { "%�ʼ�%","����","��ҵЭ��","δ֪" });
		}
		return new JsonView(items);
	}
	
	public View getzjAndjdbCompanyList(String companyType,int page, int rows) throws Exception {
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<CompanyInfo> items =null;
		if(role==10 || role==11){
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId,tu.area from  tcuserinfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         String area = userInfoVO.getArea();
	         System.out.println("area---"+area);
	         if("�ʼ�".equals(companyType)){
	         items = CompanyInfo.findBySql(CompanyInfo.class,
	 				"select id,companyName from TwoCodeCompanyInfo where type = ? and id = ?  ", new Object[] { "�ʼ�",zjcompanyId});
	    	 }
	         if("�ֵ���".equals(companyType)){
	        	 items = CompanyInfo.findBySql(CompanyInfo.class,
	 	 				"select id,companyName from TwoCodeCompanyInfo where type = ? and parea = ? ", new Object[] { "�ֵ���",area}); 
	          }
	         }
		}else{
		 if(!"".equals(companyType))
		 items = CompanyInfo.findBySql(CompanyInfo.class,
				"select id,companyName from TwoCodeCompanyInfo where type = ?", new Object[] { companyType });
		}
		return new JsonView(items);
	}
	
	public View getZjCompanyList2(String companyType,int page, int rows) throws Exception {
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<CompanyInfo> items =null;
		if(role==10 || role==11){
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId,tu.area from  tcuserinfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         String area = userInfoVO.getArea();
	         System.out.println("area---"+area);
	         if("�ʼ�".equals(companyType)){
	         items = CompanyInfo.findBySql(CompanyInfo.class,
	 				"select id,companyName from TwoCodeCompanyInfo where type = ? and id = ?", new Object[] { "�ʼ�",zjcompanyId});
	    	 }
	         if("�ֵ���".equals(companyType)){
	        	 items = CompanyInfo.findBySql(CompanyInfo.class,
	 	 				"select id,companyName from TwoCodeCompanyInfo where type = ? and parea = ? ", new Object[] { "�ֵ���",area}); 
	          }
	         }
		}else{
		 if(!"".equals(companyType))
		 items = CompanyInfo.findBySql(CompanyInfo.class,
				"select id,companyName from TwoCodeCompanyInfo where type = ?", new Object[] { companyType });
		}
		return new JsonView(items);
	}
	
	public View getjdbCompanyList(int page, int rows) throws Exception {
		List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
				"select id,companyName from TwoCodeCompanyInfo where type = ?", new Object[] { "�ֵ���" });
		return new JsonView(items);
	}
	
	public View getjdbCompanyListByarea(String companyArea){
		List<CompanyInfo> items =null;
		try {
			items = CompanyInfo.findBySql(CompanyInfo.class,
					"select id,companyName from TwoCodeCompanyInfo where type = ? and (parea = ? or companyName = ? )", new Object[] { "�ֵ���",companyArea,"δ֪" });
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JsonView(items);
	}
	
	public View getjdbCompanyListByNewarea(String companyArea){
		List<CompanyInfo> items =null;
		try {
			if(!"".equals(companyArea)){
			   items = CompanyInfo.findBySql(CompanyInfo.class,
					"select id,companyName from TwoCodeCompanyInfo where type = ? and (parea = ? or companyName = ? )", new Object[] { "�ֵ���",companyArea,"δ֪" });
			}
			else{
				items = new ArrayList<CompanyInfo>();
			}
			} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JsonView(items);
	}
	
	public View getjdbCompanyListByarea2(){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		List<CompanyInfo> items =null;
		try {
		 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where  tu.userid = ? ", new Object[] { userid });
			if(userInfoVO != null){
			int zjcompanyId = userInfoVO.getCompanyId();
		    String companyArea = userInfoVO.getArea();
			items = CompanyInfo.findBySql(CompanyInfo.class,"select id,companyName from TwoCodeCompanyInfo where type = ? and (parea = ? or companyName = ? )", new Object[] { "�ֵ���",companyArea,"δ֪" });
			}
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JsonView(items);
	}
	
	public View queryYwCompanyByReg(String registNumber) throws Exception {
		List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
				"select tc.id,tc.companyName from TwoCodeElevatorInfo t left join TwoCodeCompanyInfo tc  on t.ywCompanyId=tc.id where t.registNumber = ? ", new Object[] { registNumber });	
		return new JsonView(items);
	}
	
	 public String add(ElevaltorInfo elevaltorInfo){
		 String result = "failure";
		 if("".equals(elevaltorInfo.getMap_X())){
				elevaltorInfo.setMap_X("0.000000");
			}
			if("".equals(elevaltorInfo.getMap_Y())){
				elevaltorInfo.setMap_Y("0.000000");
			}
		//Ҫ������±�ǩ��Ϣ����updateTime�ֶΣ�С���Ǳ�Ҫ��
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = format1.format(new Date()); 
		elevaltorInfo.setUpdateTime(s);
		 try {
		//	String querySql ="select t.* from TwoCodeElevatorInfo t where t.registNumber =?";
			ElevaltorInfoVO  elevaltorInfoExit = null;
			elevaltorInfoExit = ElevaltorInfoVO.findFirst(ElevaltorInfoVO.class, "registNumber = ?", new Object[] { elevaltorInfo.getRegistNumber()});
		if(elevaltorInfoExit != null){
			 result = "exist";
		}
		else{
		 
		//�ж�registcode���ڲ�����
	     if(!"".equals(elevaltorInfo.getRegistCode())){
		 elevaltorInfoExit = ElevaltorInfoVO.findFirst(ElevaltorInfoVO.class, "registCode = ?", new Object[] { elevaltorInfo.getRegistCode()});
		 if(elevaltorInfoExit != null){
	    	 result = "exist2"; 
	     }
	     }
	    
		 String  areaStr ="%"+elevaltorInfo.getArea()+"%";
		 CompanyInfo companyInfo = CompanyInfo.findFirstBySql(CompanyInfo.class, "select id from TwoCodeCompanyInfo where companyName like ? and type = '�ʼ�'", new Object[] { areaStr });
		 if(companyInfo != null)
			 elevaltorInfo.setZjCompanyId(companyInfo.getId());
		 
		 if("".equals(elevaltorInfo.getDeviceId())){  //��������ϻ���豸
			 ActiveRecordBase.beginTransaction();
				boolean flag = elevaltorInfo.save();
				if (flag){
					ActiveRecordBase.commit();
					result ="success";
				}
				else { 
					ActiveRecordBase.rollback();
					result ="success";
				}
		   }
		 else{             //������ϻ���豸                        
			 ActiveRecordBase.beginTransaction();
			 if(elevaltorInfo.save()){
				 ElevaltorInfoVO elevaltorInfoVO = null;
				 String sql ="select t.id from TwoCodeDeviceRelationInfo t where t.registNumber =?";
				 elevaltorInfoVO =ElevaltorInfoVO.findFirstBySql(ElevaltorInfoVO.class, sql, new Object[] { elevaltorInfo.getRegistNumber()});
				 if(elevaltorInfoVO!=null){
					 if(TwoCodeDeviceRelationInfo.updateAll(TwoCodeDeviceRelationInfo.class, "deviceId = ?", new Object[]{elevaltorInfo.getDeviceId()}, "registNumber = ?", new Object[]{elevaltorInfo.getRegistNumber()})>0)
					 result ="success";
				 }
				 else{
					 TwoCodeDeviceRelationInfo twoCodeDeviceRelationInfo = new TwoCodeDeviceRelationInfo();
					 twoCodeDeviceRelationInfo.setRegistNumber(elevaltorInfo.getRegistNumber());
					 twoCodeDeviceRelationInfo.setDeviceId(elevaltorInfo.getDeviceId());
					 if(twoCodeDeviceRelationInfo.save())
					 result ="success";
				 }
			 }
			 ActiveRecordBase.commit();
		 }
	    
		}
		 }catch(Exception e){
			 e.printStackTrace();
			 try {
				  ActiveRecordBase.rollback();
				} catch (TransactionException et) {
					et.printStackTrace();
				}
		 }
		 
		 return result;
	 }
	 
	public String queryDid(String deviceId){
		System.out.println("��ʼajax--->queryDid");
		String isvalid ="0";
	//	String sql ="select tr.registNumber as deviceId from deviceinfo d left join TwoCodeDeviceRelationInfo tr on d.device_id =tr.deviceId where d.device_id =? and d.valid ='1'";
		String sql ="select tr.registNumber  from deviceinfo d left join TwoCodeDeviceRelationInfo tr on d.device_id =tr.deviceId where d.device_id =? and d.valid ='1'";
	//	String sql ="select tr.registNumber as deviceId from deviceinfo d , TwoCodeDeviceRelationInfo tr where d.device_id =tr.deviceId and d.device_id =? and d.valid ='1'";
		ElevaltorInfoVO elevaltorInfoVO = null;
		try {
			elevaltorInfoVO =ElevaltorInfoVO.findFirstBySql(ElevaltorInfoVO.class, sql, new Object[] { deviceId });
		//	elevaltorInfoVO =ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, new Object[] { deviceId });
			
		} catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		if(elevaltorInfoVO!=null){
			System.out.println("deviceId:"+elevaltorInfoVO.getDeviceId());
			if(elevaltorInfoVO.getRegistNumber()==null || "".equals(elevaltorInfoVO.getRegistNumber())){
			System.out.println("isvalid");
			isvalid ="1";
			}
			else{
				isvalid ="2";	 //�豸�Ѿ�������
			}
			}
		return isvalid;
	}
	
	 public View edit(int id)throws Exception{
		 String cityName = GlobalFunction.cityName;
		 System.out.println("edit---id:"+id);
		 String sql = "";
		 if("1".equals(cityName)){
		 sql ="select t.neleType,t.shibieCode,isnull(t.registNumber,'') as registNumber,isnull(t.address,'') as address,isnull(t.name,'') as name,t.eleType,isnull(t.registCode,'')as registCode,t.inoutDoor,isnull(t.propertyRightsUnit,'') as propertyRightsUnit,t.zzCompanyId,isnull(t.eleMode,'') as eleMode,t.wgCompanyId,t.jyCompanyId,isnull(t.useNumber,'') as useNumber,isnull(t.safetyManDepart,'') as safetyManDepart,isnull(t.safetyManPerson,'') as safetyManPerson,isnull(t.safetyManPersonTel,'') as safetyManPersonTel,isnull(t.inspector,'') as inspector,isnull(t.nextInspectDate,'') as nextInspectDate,isnull(t.completeAcceptanceDate,'') as completeAcceptanceDate,isnull(t.acceptanceDateDepart,'') as acceptanceDateDepart,isnull(t.acceptanceReportNum,'') as acceptanceReportNum,t.azCompanyId,t.ywCompanyId,t.zjCompanyId,t.townshipStreets,isnull(t.eleLoad,'') as eleLoad,isnull(t.speed,'') as speed,isnull(t.eleheight,'') as eleheight,isnull(t.elewidth,'') as elewidth,isnull(t.eleStop,'') as eleStop,isnull(t.mobileCode,'') as mobileCode,isnull(t.area,'') as area,isnull(t.buildingName,'') as buildingName,isnull(t.building,'') as building,isnull(t.unit,'') as unit,isnull(t.coordinates,'') as coordinates,t.map_X,t.map_Y,isnull(t.manufactDate,'') as manufactDate,isnull(t.factoryNum,'') as factoryNum,isnull(t.useDate,'') as useDate,isnull(t.checkCategory,'') as checkCategory,isnull(t.checkResult,'') as checkResult,isnull(t.checkReportNum,'') as checkReportNum,isnull(t.inspectDate,'') as inspectDate,isnull(t.registCode2,'') as registCode2,isnull(t.registor,'') as registor,isnull(t.note,'') as note,isnull(t.mContractVdate,'') as mContractVdate,isnull(t.handleCompany,'') as handleCompany,isnull(t.handleCompanyCode,'') as handleCompanyCode,isnull(t.changeWay,'') as changeWay,isnull(tc.deviceId,'') as deviceId,isnull(t.deviceId2,'') as deviceId2,t.usePlace  from TwoCodeElevatorInfo t left join TwoCodeDeviceRelationInfo tc on t.registNumber = tc.registNumber where t.id = ?";	  	 
		 }
		 else{
		 sql ="select isnull(t.registNumber,'') as registNumber,isnull(t.address,'') as address,isnull(t.name,'') as name,t.eleType,isnull(t.registCode,'')as registCode,t.inoutDoor,isnull(t.propertyRightsUnit,'') as propertyRightsUnit,t.zzCompanyId,isnull(t.eleMode,'') as eleMode,t.wgCompanyId,t.jyCompanyId,isnull(t.useNumber,'') as useNumber,isnull(t.safetyManDepart,'') as safetyManDepart,isnull(t.safetyManPerson,'') as safetyManPerson,isnull(t.safetyManPersonTel,'') as safetyManPersonTel,isnull(t.inspector,'') as inspector,isnull(t.nextInspectDate,'') as nextInspectDate,isnull(t.completeAcceptanceDate,'') as completeAcceptanceDate,isnull(t.acceptanceDateDepart,'') as acceptanceDateDepart,isnull(t.acceptanceReportNum,'') as acceptanceReportNum,t.azCompanyId,t.ywCompanyId,t.zjCompanyId,t.townshipStreets,isnull(t.eleLoad,'') as eleLoad,isnull(t.speed,'') as speed,isnull(t.eleheight,'') as eleheight,isnull(t.elewidth,'') as elewidth,isnull(t.eleStop,'') as eleStop,isnull(t.mobileCode,'') as mobileCode,isnull(t.area,'') as area,isnull(t.buildingName,'') as buildingName,isnull(t.building,'') as building,isnull(t.unit,'') as unit,isnull(t.coordinates,'') as coordinates,t.map_X,t.map_Y,isnull(t.manufactDate,'') as manufactDate,isnull(t.factoryNum,'') as factoryNum,isnull(t.useDate,'') as useDate,isnull(t.checkCategory,'') as checkCategory,isnull(t.checkResult,'') as checkResult,isnull(t.checkReportNum,'') as checkReportNum,isnull(t.inspectDate,'') as inspectDate,isnull(t.registCode2,'') as registCode2,isnull(t.registor,'') as registor,isnull(t.note,'') as note,isnull(t.mContractVdate,'') as mContractVdate,isnull(t.handleCompany,'') as handleCompany,isnull(t.handleCompanyCode,'') as handleCompanyCode,isnull(t.changeWay,'') as changeWay,isnull(tc.deviceId,'') as deviceId,isnull(t.deviceId2,'') as deviceId2,t.usePlace  from TwoCodeElevatorInfo t left join TwoCodeDeviceRelationInfo tc on t.registNumber = tc.registNumber where t.id = ?";
		 }
		 ElevaltorInfoVO elevaltorInfoVO =ElevaltorInfoVO.findFirstBySql(ElevaltorInfoVO.class, sql, new Object[] { id });
		 Map<String, Object> result = new HashMap<String, Object>();
		 if(elevaltorInfoVO !=null){
		 result.put("registNumber", elevaltorInfoVO.getRegistNumber());
		 if("1".equals(cityName)){
		 result.put("shibieCode", elevaltorInfoVO.getShibieCode());
		 result.put("neleType", elevaltorInfoVO.getNeleType());
		 }
		 result.put("address", elevaltorInfoVO.getAddress());
		 result.put("name", elevaltorInfoVO.getName());
		 result.put("eleType", elevaltorInfoVO.getEleType());
		 result.put("registCode", elevaltorInfoVO.getRegistCode());
		 result.put("inoutDoor", elevaltorInfoVO.getInoutDoor());
		 result.put("propertyRightsUnit", elevaltorInfoVO.getPropertyRightsUnit());
		 result.put("zzCompanyId", elevaltorInfoVO.getZzCompanyId());
		 result.put("eleMode", elevaltorInfoVO.getEleMode());
		 result.put("wgCompanyId", elevaltorInfoVO.getWgCompanyId());
		 result.put("useNumber", elevaltorInfoVO.getUseNumber());
		 result.put("safetyManDepart", elevaltorInfoVO.getSafetyManDepart());
		 result.put("safetyManPerson", elevaltorInfoVO.getSafetyManPerson());
		 result.put("safetyManPersonTel", elevaltorInfoVO.getSafetyManPersonTel());
		 result.put("jyCompanyId", elevaltorInfoVO.getJyCompanyId());
		 result.put("inspector", elevaltorInfoVO.getInspector());
		 result.put("nextInspectDate", elevaltorInfoVO.getNextInspectDate());
		 result.put("completeAcceptanceDate", elevaltorInfoVO.getCompleteAcceptanceDate());
		 result.put("acceptanceDateDepart", elevaltorInfoVO.getAcceptanceDateDepart());
		 result.put("acceptanceReportNum", elevaltorInfoVO.getAcceptanceReportNum());
		 result.put("azCompanyId", elevaltorInfoVO.getAzCompanyId());
		 result.put("ywCompanyId", elevaltorInfoVO.getYwCompanyId());
		 result.put("zjCompanyId", elevaltorInfoVO.getZjCompanyId());
		 result.put("eleLoad", elevaltorInfoVO.getEleLoad());
		 result.put("speed", elevaltorInfoVO.getSpeed());
		 result.put("eleheight", elevaltorInfoVO.getEleheight());
		 result.put("elewidth", elevaltorInfoVO.getElewidth());
		 result.put("eleStop", elevaltorInfoVO.getEleStop());
	//	 result.put("mobileCode", elevaltorInfoVO.getMobileCode());
		 result.put("deviceId", elevaltorInfoVO.getDeviceId());
		 result.put("deviceId2", elevaltorInfoVO.getDeviceId2());
		 
		 result.put("townshipStreets", elevaltorInfoVO.getTownshipStreets());
		 result.put("area", elevaltorInfoVO.getArea());
		 result.put("buildingName", elevaltorInfoVO.getBuildingName());
		 result.put("building", elevaltorInfoVO.getBuilding());
		 result.put("unit", elevaltorInfoVO.getUnit());
	//   result.put("coordinates", elevaltorInfoVO.getCoordinates());
		 result.put("map_X", elevaltorInfoVO.getMap_X());
		 result.put("map_Y", elevaltorInfoVO.getMap_Y());
		 
		 result.put("manufactDate", elevaltorInfoVO.getManufactDate());
		 result.put("factoryNum", elevaltorInfoVO.getFactoryNum());
		 result.put("useDate", elevaltorInfoVO.getUseDate());
		 result.put("checkCategory", elevaltorInfoVO.getCheckCategory());
		 result.put("checkResult", elevaltorInfoVO.getCheckResult());
		 result.put("checkReportNum", elevaltorInfoVO.getCheckReportNum());
		 result.put("inspectDate", elevaltorInfoVO.getInspectDate());
		 result.put("registCode2", elevaltorInfoVO.getRegistCode2());
		 result.put("registor", elevaltorInfoVO.getRegistor());
		 result.put("note", elevaltorInfoVO.getNote());
		 result.put("mContractVdate", elevaltorInfoVO.getmContractVdate());
		 result.put("handleCompany", elevaltorInfoVO.getHandleCompany());
		 result.put("handleCompanyCode", elevaltorInfoVO.getHandleCompanyCode());
		 result.put("changeWay", elevaltorInfoVO.getChangeWay());
		 result.put("usePlace", elevaltorInfoVO.getUsePlace());
		 }
		 return new JsonView(result);
	 }
	 
	 public View edit2(String id)throws Exception{ 
		 System.out.println("edit---registNumber:"+id);
		 String cityName = GlobalFunction.cityName;
		 String sql = "";
		 if("1".equals(cityName)){
		 sql ="select t.neleType,t.shibieCode,isnull(t.registNumber,'') as registNumber,isnull(t.address,'') as address,isnull(t.name,'') as name,t.eleType,isnull(t.registCode,'')as registCode,t.inoutDoor,isnull(t.propertyRightsUnit,'') as propertyRightsUnit,t.zzCompanyId,isnull(t.eleMode,'') as eleMode,t.wgCompanyId,t.jyCompanyId,isnull(t.useNumber,'') as useNumber,isnull(t.safetyManDepart,'') as safetyManDepart,isnull(t.safetyManPerson,'') as safetyManPerson,isnull(t.safetyManPersonTel,'') as safetyManPersonTel,isnull(t.inspector,'') as inspector,isnull(t.nextInspectDate,'') as nextInspectDate,isnull(t.completeAcceptanceDate,'') as completeAcceptanceDate,isnull(t.acceptanceDateDepart,'') as acceptanceDateDepart,isnull(t.acceptanceReportNum,'') as acceptanceReportNum,t.azCompanyId,t.ywCompanyId,t.zjCompanyId,t.townshipStreets,isnull(t.eleLoad,'') as eleLoad,isnull(t.speed,'') as speed,isnull(t.eleheight,'') as eleheight,isnull(t.elewidth,'') as elewidth,isnull(t.eleStop,'') as eleStop,isnull(t.mobileCode,'') as mobileCode,isnull(t.area,'') as area,isnull(t.buildingName,'') as buildingName,isnull(t.building,'') as building,isnull(t.unit,'') as unit,isnull(t.coordinates,'') as coordinates,t.map_X,t.map_Y,isnull(t.manufactDate,'') as manufactDate,isnull(t.factoryNum,'') as factoryNum,isnull(t.useDate,'') as useDate,isnull(t.checkCategory,'') as checkCategory,isnull(t.checkResult,'') as checkResult,isnull(t.checkReportNum,'') as checkReportNum,isnull(t.inspectDate,'') as inspectDate,isnull(t.registCode2,'') as registCode2,isnull(t.registor,'') as registor,isnull(t.note,'') as note,isnull(t.mContractVdate,'') as mContractVdate,isnull(t.handleCompany,'') as handleCompany,isnull(t.handleCompanyCode,'') as handleCompanyCode,isnull(t.changeWay,'') as changeWay,isnull(tc.deviceId,'') as deviceId,isnull(t.deviceId2,'') as deviceId2,t.usePlace  from TwoCodeElevatorInfo t left join TwoCodeDeviceRelationInfo tc on t.registNumber = tc.registNumber where t.registNumber = ?";			 
		 }
		 else{
		 sql ="select isnull(t.registNumber,'') as registNumber,isnull(t.address,'') as address,isnull(t.name,'') as name,t.eleType,isnull(t.registCode,'')as registCode,t.inoutDoor,isnull(t.propertyRightsUnit,'') as propertyRightsUnit,t.zzCompanyId,isnull(t.eleMode,'') as eleMode,t.wgCompanyId,t.jyCompanyId,isnull(t.useNumber,'') as useNumber,isnull(t.safetyManDepart,'') as safetyManDepart,isnull(t.safetyManPerson,'') as safetyManPerson,isnull(t.safetyManPersonTel,'') as safetyManPersonTel,isnull(t.inspector,'') as inspector,isnull(t.nextInspectDate,'') as nextInspectDate,isnull(t.completeAcceptanceDate,'') as completeAcceptanceDate,isnull(t.acceptanceDateDepart,'') as acceptanceDateDepart,isnull(t.acceptanceReportNum,'') as acceptanceReportNum,t.azCompanyId,t.ywCompanyId,t.zjCompanyId,t.townshipStreets,isnull(t.eleLoad,'') as eleLoad,isnull(t.speed,'') as speed,isnull(t.eleheight,'') as eleheight,isnull(t.elewidth,'') as elewidth,isnull(t.eleStop,'') as eleStop,isnull(t.mobileCode,'') as mobileCode,isnull(t.area,'') as area,isnull(t.buildingName,'') as buildingName,isnull(t.building,'') as building,isnull(t.unit,'') as unit,isnull(t.coordinates,'') as coordinates,t.map_X,t.map_Y,isnull(t.manufactDate,'') as manufactDate,isnull(t.factoryNum,'') as factoryNum,isnull(t.useDate,'') as useDate,isnull(t.checkCategory,'') as checkCategory,isnull(t.checkResult,'') as checkResult,isnull(t.checkReportNum,'') as checkReportNum,isnull(t.inspectDate,'') as inspectDate,isnull(t.registCode2,'') as registCode2,isnull(t.registor,'') as registor,isnull(t.note,'') as note,isnull(t.mContractVdate,'') as mContractVdate,isnull(t.handleCompany,'') as handleCompany,isnull(t.handleCompanyCode,'') as handleCompanyCode,isnull(t.changeWay,'') as changeWay,isnull(tc.deviceId,'') as deviceId,isnull(t.deviceId2,'') as deviceId2,t.usePlace  from TwoCodeElevatorInfo t left join TwoCodeDeviceRelationInfo tc on t.registNumber = tc.registNumber where t.registNumber = ?";
		 }
		 ElevaltorInfoVO elevaltorInfoVO =ElevaltorInfoVO.findFirstBySql(ElevaltorInfoVO.class, sql, new Object[] { id });
		 Map<String, Object> result = new HashMap<String, Object>();
		 if(elevaltorInfoVO !=null){
		 result.put("registNumber", elevaltorInfoVO.getRegistNumber());
		 if("1".equals(cityName)){
			 result.put("shibieCode", elevaltorInfoVO.getShibieCode());
			 result.put("neleType", elevaltorInfoVO.getNeleType());
			 }
		 result.put("address", elevaltorInfoVO.getAddress());
		 result.put("name", elevaltorInfoVO.getName());
		 result.put("eleType", elevaltorInfoVO.getEleType());
		 result.put("registCode", elevaltorInfoVO.getRegistCode());
		 result.put("inoutDoor", elevaltorInfoVO.getInoutDoor());
		 result.put("propertyRightsUnit", elevaltorInfoVO.getPropertyRightsUnit());
		 result.put("zzCompanyId", elevaltorInfoVO.getZzCompanyId());
		 result.put("eleMode", elevaltorInfoVO.getEleMode());
		 result.put("wgCompanyId", elevaltorInfoVO.getWgCompanyId());
		 result.put("useNumber", elevaltorInfoVO.getUseNumber());
		 result.put("safetyManDepart", elevaltorInfoVO.getSafetyManDepart());
		 result.put("safetyManPerson", elevaltorInfoVO.getSafetyManPerson());
		 result.put("safetyManPersonTel", elevaltorInfoVO.getSafetyManPersonTel());
		 result.put("jyCompanyId", elevaltorInfoVO.getJyCompanyId());
		 result.put("inspector", elevaltorInfoVO.getInspector());
		 result.put("nextInspectDate", elevaltorInfoVO.getNextInspectDate());
		 result.put("completeAcceptanceDate", elevaltorInfoVO.getCompleteAcceptanceDate());
		 result.put("acceptanceDateDepart", elevaltorInfoVO.getAcceptanceDateDepart());
		 result.put("acceptanceReportNum", elevaltorInfoVO.getAcceptanceReportNum());
		 result.put("azCompanyId", elevaltorInfoVO.getAzCompanyId());
		 result.put("ywCompanyId", elevaltorInfoVO.getYwCompanyId());
		 result.put("zjCompanyId", elevaltorInfoVO.getZjCompanyId());
		 result.put("eleLoad", elevaltorInfoVO.getEleLoad());
		 result.put("speed", elevaltorInfoVO.getSpeed());
		 result.put("eleheight", elevaltorInfoVO.getEleheight());
		 result.put("elewidth", elevaltorInfoVO.getElewidth());
		 result.put("eleStop", elevaltorInfoVO.getEleStop());
	//	 result.put("mobileCode", elevaltorInfoVO.getMobileCode());
		 result.put("deviceId", elevaltorInfoVO.getDeviceId());
		 result.put("deviceId2", elevaltorInfoVO.getDeviceId2());
		 
		 result.put("townshipStreets", elevaltorInfoVO.getTownshipStreets());
		 result.put("area", elevaltorInfoVO.getArea());
		 result.put("buildingName", elevaltorInfoVO.getBuildingName());
		 result.put("building", elevaltorInfoVO.getBuilding());
		 result.put("unit", elevaltorInfoVO.getUnit());
	//	 result.put("coordinates", elevaltorInfoVO.getCoordinates());
		 result.put("map_X", elevaltorInfoVO.getMap_X());
		 result.put("map_Y", elevaltorInfoVO.getMap_Y());
		 result.put("manufactDate", elevaltorInfoVO.getManufactDate());
		 result.put("factoryNum", elevaltorInfoVO.getFactoryNum());
		 result.put("useDate", elevaltorInfoVO.getUseDate());
		 result.put("checkCategory", elevaltorInfoVO.getCheckCategory());
		 result.put("checkResult", elevaltorInfoVO.getCheckResult());
		 result.put("checkReportNum", elevaltorInfoVO.getCheckReportNum());
		 result.put("inspectDate", elevaltorInfoVO.getInspectDate());
		 result.put("registCode2", elevaltorInfoVO.getRegistCode2());
		 result.put("registor", elevaltorInfoVO.getRegistor());
		 result.put("note", elevaltorInfoVO.getNote());
		 result.put("mContractVdate", elevaltorInfoVO.getmContractVdate());
		 result.put("handleCompany", elevaltorInfoVO.getHandleCompany());
		 result.put("handleCompanyCode", elevaltorInfoVO.getHandleCompanyCode());
		 result.put("changeWay", elevaltorInfoVO.getChangeWay());
		 result.put("usePlace", elevaltorInfoVO.getUsePlace());
		 }
		 return new JsonView(result);
	 }
	 
	 
	 public String update(ElevaltorInfo elevaltorInfo){
		 String cityName = GlobalFunction.cityName;
		 System.out.println("update---id:"+elevaltorInfo.getId());
		 String result = "failure";
		 int num = 0;
	//	 boolean num =false;
		 String deviceId ="";
		 deviceId =elevaltorInfo.getDeviceId();
		 
   //�����ϵ
		 boolean changeFlag =false;
		 boolean changeFlag2 =false;
		 String oldywCompanyName ="";
		 String ywCompanyName ="";
		 String odlwgCompanyName ="";
		 String wgCompanyName ="";
		 String str ="";
		 
		 ElevaltorInfoVO oldElevaltorInfoVO =null;
		 ElevaltorInfoVO elevaltorInfoExit = null;
		 
		 try {
		 //       ActiveRecordBase.beginTransaction();
		        if(!"".equals(elevaltorInfo.getRegistCode())){
		   		 elevaltorInfoExit = ElevaltorInfoVO.findFirst(ElevaltorInfoVO.class, "registCode = ?  and id != ?", new Object[] { elevaltorInfo.getRegistCode(),elevaltorInfo.getId()});
		   		 if(elevaltorInfoExit != null){
		   	    	 result = "exist2"; 
		   	    	 return  result;
		   	     }
		        }
		        //��0����ά�����ϵ
		        oldElevaltorInfoVO =ElevaltorInfoVO.findFirstBySql(ElevaltorInfoVO.class, "select t.ywCompanyId,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName from TwoCodeElevatorInfo t where t.registNumber=?", new Object[] { elevaltorInfo.getRegistNumber()});  
		        if(oldElevaltorInfoVO.getYwCompanyId() != elevaltorInfo.getYwCompanyId()){ //��ά��˾ID����˵����ά��˾�б䶯
		           oldywCompanyName=oldElevaltorInfoVO.getYwCompanyName();
		  //         CompanyInfo newywCompanyInfo = CompanyInfo.findFirst(CompanyInfo.class, "id=?", new Object[]{elevaltorInfo.getYwCompanyId()});
		  //         ywCompanyName = newywCompanyInfo.getCompanyName();
		 //          str ="ά����λ���:"+oldywCompanyName+"--->"+ywCompanyName+";";
		           changeFlag = true;
		       
		        }
		        //��0����ܱ����ϵ
		        if(oldElevaltorInfoVO.getWgCompanyId() != elevaltorInfo.getWgCompanyId()){//��ܹ�˾ID����˵����ܹ�˾�б䶯
		        	odlwgCompanyName =oldElevaltorInfoVO.getWgCompanyName();
		    //    	CompanyInfo newwgCompanyInfo = CompanyInfo.findFirst(CompanyInfo.class, "id=?", new Object[]{elevaltorInfo.getWgCompanyId()});
		    //    	wgCompanyName = newwgCompanyInfo.getCompanyName();
		    //    	str=str+"ʹ�õ�λ���:"+odlwgCompanyName+"--->"+wgCompanyName+";";
		        	 changeFlag2 = true;
		        }
		        /*
		        if(changeFlag){ //��������Ҫ��¼�ı��
		        UserInfo user=(UserInfo)session.getAttribute("sessionAccount");
		        if(user!=null){
		        System.out.println("������Ա��"+user.getLoginName());
		        System.out.println(str.substring(0, str.length()-1));
		        EleChangeInfo eleChangeInfo = new EleChangeInfo();
		        eleChangeInfo.setRegistNumber(elevaltorInfo.getRegistNumber());
		        eleChangeInfo.setChangeItem(str.substring(0, str.length()-1));
		        eleChangeInfo.setOperator(user.getLoginName());
		        eleChangeInfo.setHandleCompany(elevaltorInfo.getHandleCompany());
		        eleChangeInfo.setHandleCompanyCode(elevaltorInfo.getHandleCompanyCode());
		        eleChangeInfo.setChangeWay(elevaltorInfo.getChangeWay());
		        java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String changeTime = format2.format(new Date()); 
				eleChangeInfo.setChangeTime(changeTime);
				eleChangeInfo.save();   
		        }
		        }  */
		        
		        //��һ���ȴ����豸���ά�������ϵ
				TwoCodeDeviceRelationInfo twoCodeDeviceRelationInfo = TwoCodeDeviceRelationInfo.findFirst(TwoCodeDeviceRelationInfo.class, "registNumber =?", new Object[] { elevaltorInfo.getRegistNumber()});
				if(twoCodeDeviceRelationInfo == null){  //û�й�ϵ���ڣ������µĹ�ϵ
					if(!"".equals(deviceId) && deviceId.length()==16){
					twoCodeDeviceRelationInfo = new TwoCodeDeviceRelationInfo();
					twoCodeDeviceRelationInfo.setRegistNumber(elevaltorInfo.getRegistNumber());
					twoCodeDeviceRelationInfo.setDeviceId(deviceId);
					twoCodeDeviceRelationInfo.save();
					}
				}else{       //�й�ϵ���ڣ��������µĹ�ϵ������ԭ���Ĺ�ϵ
					String oldDeviceId = twoCodeDeviceRelationInfo.getDeviceId();
					if(!deviceId.equals(oldDeviceId)){
					TwoCodeDeviceRelationInfo.updateAll(TwoCodeDeviceRelationInfo.class, "deviceId = ?", new Object[] { deviceId }, "registNumber =?", new Object[] { elevaltorInfo.getRegistNumber()});	
					}
				}
		    //�ڶ������¶�ά���ǩ������Ϣ��
			//Ҫ������±�ǩ��Ϣ����updateTime�ֶΣ�С���Ǳ�Ҫ��
			java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String s = format1.format(new Date()); 
			if("".equals(elevaltorInfo.getMap_X())){
				elevaltorInfo.setMap_X("0.000000");
			}
			if("".equals(elevaltorInfo.getMap_Y())){
				elevaltorInfo.setMap_Y("0.000000");
			}
			
			 String  areaStr ="%"+elevaltorInfo.getArea()+"%";
			 CompanyInfo companyInfo = CompanyInfo.findFirstBySql(CompanyInfo.class, "select id from TwoCodeCompanyInfo where companyName like ? and type = '�ʼ�' and area ! ='' and area is not null ", new Object[] { areaStr });
			 if(companyInfo != null)
				 elevaltorInfo.setZjCompanyId(companyInfo.getId());
			 
			if(changeFlag || changeFlag2){
			if("1".equals(cityName)){
			num = ElevaltorInfo.updateAll(ElevaltorInfo.class, "address=?,name=?,eleType=?,registCode=?,inoutDoor=?,propertyRightsUnit=?,zzCompanyId=?,eleMode=?,wgCompanyId=?,useNumber=?,safetyManDepart=?,safetyManPerson=?,safetyManPersonTel=?,jyCompanyId=?,inspector=?,nextInspectDate=?,completeAcceptanceDate=?,acceptanceDateDepart=?,acceptanceReportNum=?,azCompanyId=?,ywCompanyId=?,zjCompanyId=?,eleLoad=?,speed=?,eleheight=?,elewidth=?,eleStop=?,area=?,townshipStreets=?,buildingName=?,building=?,unit=?,map_X=?,map_Y=?,manufactDate=?,factoryNum=?,useDate=?,checkCategory=?,checkResult=?,checkReportNum=?,inspectDate=?,registCode2=?,registor=?,note=?,mContractVdate=?,handleCompany=?,handleCompanyCode=?,changeWay=?,updateTime=?,ischangInfo=?,deviceId2=?,shibieCode=?,neleType=?,usePlace=?", new Object[]{elevaltorInfo.getAddress(),elevaltorInfo.getName(),elevaltorInfo.getEleType(),elevaltorInfo.getRegistCode(),elevaltorInfo.getInoutDoor(),elevaltorInfo.getPropertyRightsUnit(),elevaltorInfo.getZzCompanyId(),elevaltorInfo.getEleMode(),elevaltorInfo.getWgCompanyId(),elevaltorInfo.getUseNumber(),elevaltorInfo.getSafetyManDepart(),elevaltorInfo.getSafetyManPerson(),elevaltorInfo.getSafetyManPersonTel(),elevaltorInfo.getJyCompanyId(),elevaltorInfo.getInspector(),elevaltorInfo.getNextInspectDate(),elevaltorInfo.getCompleteAcceptanceDate(),elevaltorInfo.getAcceptanceDateDepart(),elevaltorInfo.getAcceptanceReportNum(),elevaltorInfo.getAzCompanyId(),elevaltorInfo.getYwCompanyId(),elevaltorInfo.getZjCompanyId(),elevaltorInfo.getEleLoad(),elevaltorInfo.getSpeed(),elevaltorInfo.getEleheight(),elevaltorInfo.getElewidth(),elevaltorInfo.getEleStop(),elevaltorInfo.getArea(),elevaltorInfo.getTownshipStreets(),elevaltorInfo.getBuildingName(),elevaltorInfo.getBuilding(),elevaltorInfo.getUnit(),elevaltorInfo.getMap_X(),elevaltorInfo.getMap_Y(),elevaltorInfo.getManufactDate(),elevaltorInfo.getFactoryNum(),elevaltorInfo.getUseDate(),elevaltorInfo.getCheckCategory(),elevaltorInfo.getCheckResult(),elevaltorInfo.getCheckReportNum(),elevaltorInfo.getInspectDate(),elevaltorInfo.getRegistCode2(),elevaltorInfo.getRegistor(),elevaltorInfo.getNote(),elevaltorInfo.getmContractVdate(),elevaltorInfo.getHandleCompany(),elevaltorInfo.getHandleCompanyCode(),elevaltorInfo.getChangeWay(),s,1,elevaltorInfo.getDeviceId2(),elevaltorInfo.getShibieCode(),elevaltorInfo.getNeleType(),elevaltorInfo.getUsePlace()}, "id=?", new Object[] { elevaltorInfo.getId()});		
			}else{
			num = ElevaltorInfo.updateAll(ElevaltorInfo.class, "address=?,name=?,eleType=?,registCode=?,inoutDoor=?,propertyRightsUnit=?,zzCompanyId=?,eleMode=?,wgCompanyId=?,useNumber=?,safetyManDepart=?,safetyManPerson=?,safetyManPersonTel=?,jyCompanyId=?,inspector=?,nextInspectDate=?,completeAcceptanceDate=?,acceptanceDateDepart=?,acceptanceReportNum=?,azCompanyId=?,ywCompanyId=?,zjCompanyId=?,eleLoad=?,speed=?,eleheight=?,elewidth=?,eleStop=?,area=?,townshipStreets=?,buildingName=?,building=?,unit=?,map_X=?,map_Y=?,manufactDate=?,factoryNum=?,useDate=?,checkCategory=?,checkResult=?,checkReportNum=?,inspectDate=?,registCode2=?,registor=?,note=?,mContractVdate=?,handleCompany=?,handleCompanyCode=?,changeWay=?,updateTime=?,ischangInfo=?,deviceId2=?,usePlace=?", new Object[]{elevaltorInfo.getAddress(),elevaltorInfo.getName(),elevaltorInfo.getEleType(),elevaltorInfo.getRegistCode(),elevaltorInfo.getInoutDoor(),elevaltorInfo.getPropertyRightsUnit(),elevaltorInfo.getZzCompanyId(),elevaltorInfo.getEleMode(),elevaltorInfo.getWgCompanyId(),elevaltorInfo.getUseNumber(),elevaltorInfo.getSafetyManDepart(),elevaltorInfo.getSafetyManPerson(),elevaltorInfo.getSafetyManPersonTel(),elevaltorInfo.getJyCompanyId(),elevaltorInfo.getInspector(),elevaltorInfo.getNextInspectDate(),elevaltorInfo.getCompleteAcceptanceDate(),elevaltorInfo.getAcceptanceDateDepart(),elevaltorInfo.getAcceptanceReportNum(),elevaltorInfo.getAzCompanyId(),elevaltorInfo.getYwCompanyId(),elevaltorInfo.getZjCompanyId(),elevaltorInfo.getEleLoad(),elevaltorInfo.getSpeed(),elevaltorInfo.getEleheight(),elevaltorInfo.getElewidth(),elevaltorInfo.getEleStop(),elevaltorInfo.getArea(),elevaltorInfo.getTownshipStreets(),elevaltorInfo.getBuildingName(),elevaltorInfo.getBuilding(),elevaltorInfo.getUnit(),elevaltorInfo.getMap_X(),elevaltorInfo.getMap_Y(),elevaltorInfo.getManufactDate(),elevaltorInfo.getFactoryNum(),elevaltorInfo.getUseDate(),elevaltorInfo.getCheckCategory(),elevaltorInfo.getCheckResult(),elevaltorInfo.getCheckReportNum(),elevaltorInfo.getInspectDate(),elevaltorInfo.getRegistCode2(),elevaltorInfo.getRegistor(),elevaltorInfo.getNote(),elevaltorInfo.getmContractVdate(),elevaltorInfo.getHandleCompany(),elevaltorInfo.getHandleCompanyCode(),elevaltorInfo.getChangeWay(),s,1,elevaltorInfo.getDeviceId2(),elevaltorInfo.getUsePlace()}, "id=?", new Object[] { elevaltorInfo.getId()});
			}
			}else{
			if("1".equals(cityName)){
				num = ElevaltorInfo.updateAll(ElevaltorInfo.class, "address=?,name=?,eleType=?,registCode=?,inoutDoor=?,propertyRightsUnit=?,zzCompanyId=?,eleMode=?,wgCompanyId=?,useNumber=?,safetyManDepart=?,safetyManPerson=?,safetyManPersonTel=?,jyCompanyId=?,inspector=?,nextInspectDate=?,completeAcceptanceDate=?,acceptanceDateDepart=?,acceptanceReportNum=?,azCompanyId=?,ywCompanyId=?,zjCompanyId=?,eleLoad=?,speed=?,eleheight=?,elewidth=?,eleStop=?,area=?,townshipStreets=?,buildingName=?,building=?,unit=?,map_X=?,map_Y=?,manufactDate=?,factoryNum=?,useDate=?,checkCategory=?,checkResult=?,checkReportNum=?,inspectDate=?,registCode2=?,registor=?,note=?,mContractVdate=?,handleCompany=?,handleCompanyCode=?,changeWay=?,updateTime=?,deviceId2=?,shibieCode=?,neleType=?,usePlace=?", new Object[]{elevaltorInfo.getAddress(),elevaltorInfo.getName(),elevaltorInfo.getEleType(),elevaltorInfo.getRegistCode(),elevaltorInfo.getInoutDoor(),elevaltorInfo.getPropertyRightsUnit(),elevaltorInfo.getZzCompanyId(),elevaltorInfo.getEleMode(),elevaltorInfo.getWgCompanyId(),elevaltorInfo.getUseNumber(),elevaltorInfo.getSafetyManDepart(),elevaltorInfo.getSafetyManPerson(),elevaltorInfo.getSafetyManPersonTel(),elevaltorInfo.getJyCompanyId(),elevaltorInfo.getInspector(),elevaltorInfo.getNextInspectDate(),elevaltorInfo.getCompleteAcceptanceDate(),elevaltorInfo.getAcceptanceDateDepart(),elevaltorInfo.getAcceptanceReportNum(),elevaltorInfo.getAzCompanyId(),elevaltorInfo.getYwCompanyId(),elevaltorInfo.getZjCompanyId(),elevaltorInfo.getEleLoad(),elevaltorInfo.getSpeed(),elevaltorInfo.getEleheight(),elevaltorInfo.getElewidth(),elevaltorInfo.getEleStop(),elevaltorInfo.getArea(),elevaltorInfo.getTownshipStreets(),elevaltorInfo.getBuildingName(),elevaltorInfo.getBuilding(),elevaltorInfo.getUnit(),elevaltorInfo.getMap_X(),elevaltorInfo.getMap_Y(),elevaltorInfo.getManufactDate(),elevaltorInfo.getFactoryNum(),elevaltorInfo.getUseDate(),elevaltorInfo.getCheckCategory(),elevaltorInfo.getCheckResult(),elevaltorInfo.getCheckReportNum(),elevaltorInfo.getInspectDate(),elevaltorInfo.getRegistCode2(),elevaltorInfo.getRegistor(),elevaltorInfo.getNote(),elevaltorInfo.getmContractVdate(),elevaltorInfo.getHandleCompany(),elevaltorInfo.getHandleCompanyCode(),elevaltorInfo.getChangeWay(),s,elevaltorInfo.getDeviceId2(),elevaltorInfo.getShibieCode(),elevaltorInfo.getNeleType(),elevaltorInfo.getUsePlace()}, "id=?", new Object[] { elevaltorInfo.getId()});			
			}else{
			num = ElevaltorInfo.updateAll(ElevaltorInfo.class, "address=?,name=?,eleType=?,registCode=?,inoutDoor=?,propertyRightsUnit=?,zzCompanyId=?,eleMode=?,wgCompanyId=?,useNumber=?,safetyManDepart=?,safetyManPerson=?,safetyManPersonTel=?,jyCompanyId=?,inspector=?,nextInspectDate=?,completeAcceptanceDate=?,acceptanceDateDepart=?,acceptanceReportNum=?,azCompanyId=?,ywCompanyId=?,zjCompanyId=?,eleLoad=?,speed=?,eleheight=?,elewidth=?,eleStop=?,area=?,townshipStreets=?,buildingName=?,building=?,unit=?,map_X=?,map_Y=?,manufactDate=?,factoryNum=?,useDate=?,checkCategory=?,checkResult=?,checkReportNum=?,inspectDate=?,registCode2=?,registor=?,note=?,mContractVdate=?,handleCompany=?,handleCompanyCode=?,changeWay=?,updateTime=?,deviceId2=?,usePlace=?", new Object[]{elevaltorInfo.getAddress(),elevaltorInfo.getName(),elevaltorInfo.getEleType(),elevaltorInfo.getRegistCode(),elevaltorInfo.getInoutDoor(),elevaltorInfo.getPropertyRightsUnit(),elevaltorInfo.getZzCompanyId(),elevaltorInfo.getEleMode(),elevaltorInfo.getWgCompanyId(),elevaltorInfo.getUseNumber(),elevaltorInfo.getSafetyManDepart(),elevaltorInfo.getSafetyManPerson(),elevaltorInfo.getSafetyManPersonTel(),elevaltorInfo.getJyCompanyId(),elevaltorInfo.getInspector(),elevaltorInfo.getNextInspectDate(),elevaltorInfo.getCompleteAcceptanceDate(),elevaltorInfo.getAcceptanceDateDepart(),elevaltorInfo.getAcceptanceReportNum(),elevaltorInfo.getAzCompanyId(),elevaltorInfo.getYwCompanyId(),elevaltorInfo.getZjCompanyId(),elevaltorInfo.getEleLoad(),elevaltorInfo.getSpeed(),elevaltorInfo.getEleheight(),elevaltorInfo.getElewidth(),elevaltorInfo.getEleStop(),elevaltorInfo.getArea(),elevaltorInfo.getTownshipStreets(),elevaltorInfo.getBuildingName(),elevaltorInfo.getBuilding(),elevaltorInfo.getUnit(),elevaltorInfo.getMap_X(),elevaltorInfo.getMap_Y(),elevaltorInfo.getManufactDate(),elevaltorInfo.getFactoryNum(),elevaltorInfo.getUseDate(),elevaltorInfo.getCheckCategory(),elevaltorInfo.getCheckResult(),elevaltorInfo.getCheckReportNum(),elevaltorInfo.getInspectDate(),elevaltorInfo.getRegistCode2(),elevaltorInfo.getRegistor(),elevaltorInfo.getNote(),elevaltorInfo.getmContractVdate(),elevaltorInfo.getHandleCompany(),elevaltorInfo.getHandleCompanyCode(),elevaltorInfo.getChangeWay(),s,elevaltorInfo.getDeviceId2(),elevaltorInfo.getUsePlace()}, "id=?", new Object[] { elevaltorInfo.getId()});	
			}
			}
		    if(num>0){
			result = "success"; 
			 if(changeFlag){
             CompanyInfo newywCompanyInfo = CompanyInfo.findFirst(CompanyInfo.class, "id=?", new Object[]{elevaltorInfo.getYwCompanyId()});
			 ywCompanyName = newywCompanyInfo.getCompanyName();
			 if(!oldywCompanyName.equals(ywCompanyName))
		     str ="ά����λ���:"+oldywCompanyName+"--->"+ywCompanyName+";";
			 }
			 
			 if(changeFlag2){
			CompanyInfo newwgCompanyInfo2 = CompanyInfo.findFirst(CompanyInfo.class, "id=?", new Object[]{elevaltorInfo.getWgCompanyId()});
		    wgCompanyName = newwgCompanyInfo2.getCompanyName();
		    if(!odlwgCompanyName.equals(wgCompanyName))
		     str=str+"ʹ�õ�λ���:"+odlwgCompanyName+"--->"+wgCompanyName+";";
			 }
			 
		    if(changeFlag || changeFlag2){ //��������Ҫ��¼�ı��
		        UserInfo user=(UserInfo)session.getAttribute("sessionAccount");
		        if(user!=null){
		        System.out.println("������Ա��"+user.getLoginName());
		        System.out.println(str.substring(0, str.length()-1));
		        EleChangeInfo eleChangeInfo = new EleChangeInfo();
		        eleChangeInfo.setRegistNumber(elevaltorInfo.getRegistNumber());
		        eleChangeInfo.setChangeItem(str.substring(0, str.length()-1));
		        eleChangeInfo.setOperator(user.getLoginName());
		        eleChangeInfo.setHandleCompany(elevaltorInfo.getHandleCompany());
		        eleChangeInfo.setHandleCompanyCode(elevaltorInfo.getHandleCompanyCode());
		        eleChangeInfo.setChangeWay(elevaltorInfo.getChangeWay());
		        java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String changeTime = format2.format(new Date()); 
				eleChangeInfo.setChangeTime(changeTime);
				eleChangeInfo.save();   
		        }
		        } 
			        
		    }
		    
		    //���������ϵ
		    
		//    ActiveRecordBase.commit();
		    
		 }
		catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			  e.printStackTrace();
			
		  } 
		 return result;
	 }
	 
	 /*
	 public String delete(ElevaltorInfoVO elevaltorInfoVO){
		 String result = "failure";
		 int id = elevaltorInfoVO.getId();
		 this.deleteId = id;
		 this.deleteregistNumber =elevaltorInfoVO.getRegistNumber();
		 this.deleteresult ="failure";
		 String registNumber = elevaltorInfoVO.getRegistNumber();
		 TwoCodeDeviceRelationInfo twoCodeDeviceRelationInfo =null;
		 ElevaltorInfo  elevaltorInfo = null;
		 try {
			ActiveRecordBase.beginTransaction();
			twoCodeDeviceRelationInfo = TwoCodeDeviceRelationInfo.findFirst(TwoCodeDeviceRelationInfo.class, "registNumber = ?", new Object[]{registNumber});
			if(twoCodeDeviceRelationInfo != null){   //ɾ�����ϻ�ӵĹ�����ϵ
				 twoCodeDeviceRelationInfo.destroy();
			}
			elevaltorInfo  = ElevaltorInfo.findFirst(ElevaltorInfo.class, "id = ?", new Object[]{ id});
			if(elevaltorInfo != null){
				String sql ="insert into TwoCodeDelElevatorInfo (twoCodeId,address,maintenUnit,mainteTelephone,inspectionUnit,useNumber,inspector,nextInspectDate,userId,groupId,registNumber,mobileCode,isVaild,ruKudate,registCode,eleType,name,eleMode,wgCompanyId,ywCompanyId,zzCompanyId,azCompanyId,jyCompanyId,zjCompanyId,speed,eleLoad,eleStop,inoutDoor,eleheight,elewidth,area,townshipStreets,buildingName,building,unit,coordinates,manufactDate,factoryNum,useDate,checkCategory,checkResult,checkReportNum,inspectDate,registCode2,registor,note,updateTime,map_X,map_Y,ischangInfo,safetyManDepart,safetyManPerson,safetyManPersonTel,propertyRightsUnit,completeAcceptanceDate,acceptanceDateDepart,acceptanceReportNum,mContractVdate,handleCompany,handleCompanyCode,changeWay,deviceId2,shehePersonId2,shenheTime2,cascadeTeamId,contractId,dailingFlag,macAddress,shemiFlag,shibieCode,neleType,dueDate) " +
				"select twoCodeId,address,maintenUnit,mainteTelephone,inspectionUnit,useNumber,inspector,nextInspectDate,userId,groupId,registNumber,mobileCode,isVaild,ruKudate,registCode,eleType,name,eleMode,wgCompanyId,ywCompanyId,zzCompanyId,azCompanyId,jyCompanyId,zjCompanyId,speed,eleLoad,eleStop,inoutDoor,eleheight,elewidth,area,townshipStreets,buildingName,building,unit,coordinates,manufactDate,factoryNum,useDate,checkCategory,checkResult,checkReportNum,inspectDate,registCode2,registor,note,updateTime,map_X,map_Y,ischangInfo,safetyManDepart,safetyManPerson,safetyManPersonTel,propertyRightsUnit,completeAcceptanceDate,acceptanceDateDepart,acceptanceReportNum,mContractVdate,handleCompany,handleCompanyCode,changeWay,deviceId2,shehePersonId2,shenheTime2,cascadeTeamId,contractId,dailingFlag,macAddress,shemiFlag,shibieCode,neleType,dueDate from  TwoCodeElevatorInfo where registNumber = ? " ;
				String ywsql ="insert into twocodeDelYwManagerInfo (userId,twoCodeId,ywKind,startTime,endTime,sPosition,ePosition,ywDetail,subTime,registNumber,maintainTypecode,remark,threedscanning,ywstatus,picNum,map_X0,map_Y0,map_X1,map_Y1,map_X2,map_Y2,ywResult,flexStartx,flexStarty,flexEndx,flexEndy,OpFlag) select userId,twoCodeId,ywKind,startTime,endTime,sPosition,ePosition,ywDetail,subTime,registNumber,maintainTypecode,remark,threedscanning,ywstatus,picNum,map_X0,map_Y0,map_X1,map_Y1,map_X2,map_Y2,ywResult,flexStartx,flexStarty,flexEndx,flexEndy,OpFlag from YwManagerInfo where registNumber = ? ";
				String ywsql2 ="insert into twocodeDelYwManagerInfo (userId,twoCodeId,ywKind,startTime,endTime,sPosition,ePosition,ywDetail,subTime,registNumber,maintainTypecode,remark,threedscanning,ywstatus,picNum,map_X0,map_Y0,map_X1,map_Y1,map_X2,map_Y2,ywResult,flexStartx,flexStarty,flexEndx,flexEndy,OpFlag) select userId,twoCodeId,ywKind,startTime,endTime,sPosition,ePosition,ywDetail,subTime,registNumber,maintainTypecode,remark,threedscanning,ywstatus,picNum,map_X0,map_Y0,map_X1,map_Y1,map_X2,map_Y2,ywResult,flexStartx,flexStarty,flexEndx,flexEndy,OpFlag from hisYwManagerInfo where registNumber = ? ";
				if(ElevaltorInfo.execute(sql, new Object[]{registNumber})>0 ){
					  //ɾ����ά���ǩ������Ϣ
					elevaltorInfo.destroy();
					DdElevaltorInfoVO.execute("delete from TwoCodeDdElevatorInfo where registNumber = ? ", new Object[]{registNumber});  //ɾ��Ԥ¼�����ݿ��б�ǩ��Ϣ
					
				}
				
				if(HisYwInfoVO.execute(ywsql2, new Object[]{registNumber})> 0){  //������ʷ��ά����Ϣ�ɹ���ɾ����ʷ��ά���б�ǩ��ά��Ϣ
					HisYwInfoVO.execute("delete from YwManagerInfo where registNumber = ? ", new Object[]{registNumber});
				}
				
				if(YwInfo.execute(ywsql, new Object[]{registNumber})> 0 ){ //������ά����Ϣ�ɹ���ɾ����ά���б�ǩ��ά��Ϣ
					YwInfo.execute("delete from YwManagerInfo where registNumber = ? ", new Object[]{registNumber});
				}
				
				
				result = "success";  
				this.deleteresult ="success";
			   
			}
			 ActiveRecordBase.commit();
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			try {
				ActiveRecordBase.rollback();
			} catch (TransactionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		 return result;
	 }
	 
	 */
	 
	 
	 public String delete(ElevaltorInfoVO elevaltorInfoVO){
		 String result = "failure";
		 int id = elevaltorInfoVO.getId();
		 this.deleteId = id;
		 this.deleteregistNumber =elevaltorInfoVO.getRegistNumber();
		 this.deleteresult ="failure";
		 String registNumber = elevaltorInfoVO.getRegistNumber();
		 try {
			 ActiveRecordBase.beginTransaction();
			 ActiveRecordBase.execute("exec proc_deleteElevatorinfo ?", new Object[] { registNumber });
			 ActiveRecordBase.commit();
			 result = "success";  
			 this.deleteresult ="success";
			
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			try {
				ActiveRecordBase.rollback();
			} catch (TransactionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		 return result;
	 }
	 public String sdelete(SelevaltorInfoView selevaltorInfoView){
		 String result = "failure";
		 int id = selevaltorInfoView.getId();
		 int source = selevaltorInfoView.getSource();
		 
		 
		 SelevaltorInfo  selevaltorInfo = null;
		 int num = 0;
		 try {
		    if(source == 1){
		    	selevaltorInfo = SelevaltorInfo.findFirst(SelevaltorInfo.class, "id = ?", new Object[]{id});
			    if(selevaltorInfo != null){   //ɾ���½���ͣ�õ���
				   selevaltorInfo.destroy();
				   result = "success";	
			    }
		    }
		    else{     //�����Ѿ�ճ����ǩ��ͣ�õ��ݱ�־������Ϊȡ��ͣ�õ���
		    	num = ElevaltorInfo.updateAll(ElevaltorInfo.class, "dailingFlag = ?", new Object[]{0}, "id = ?", new Object[]{id});
				if(num > 0)
					result = "success";
		    	
		    }
		} catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 return result;
	 }
	 
	 public View sedit(int id,int source){ 
		 
		 String sql ="";
		 Map<String, Object> result = new HashMap<String, Object>();
		
		 sql ="select t.id,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,t.building,t.unit,t.registCode,t.useNumber,t.area,t.townshipStreets,isnull(t.beizhu,'') as beizhu  from TwoCodeSelevatorInfo  t  where id = ?";
			     
		 SelevaltorInfoVO selevaltorInfoVO =null;
		try {
			selevaltorInfoVO = SelevaltorInfoVO.findFirstBySql(SelevaltorInfoVO.class, sql, new Object[] { id });
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 if(selevaltorInfoVO !=null){
		 result.put("buildingName", selevaltorInfoVO.getBuildingName());
		 result.put("building", selevaltorInfoVO.getBuilding());
		 result.put("address", selevaltorInfoVO.getAddress());
		 result.put("registCode", selevaltorInfoVO.getRegistCode());
		 result.put("unit", selevaltorInfoVO.getUnit());
		 result.put("useNumber", selevaltorInfoVO.getUseNumber());
		 result.put("area", selevaltorInfoVO.getArea());
		 result.put("beizhu", selevaltorInfoVO.getBeizhu());
		 result.put("townshipStreets", selevaltorInfoVO.getTownshipStreets());
		 } 
		 return new JsonView(result);
	 }
	 
	 public String supdate(SelevaltorInfo selevaltorInfo){
		 
		 String result = "failure";
		 int num = 0;
		 SelevaltorInfoVO oldSelevaltorInfoVO =null;
		 try {
			oldSelevaltorInfoVO =SelevaltorInfoVO.findFirstBySql(SelevaltorInfoVO.class, "select * from TwoCodeSelevatorInfo  where registCode=? and id != ?", new Object[] { selevaltorInfo.getRegistCode(),selevaltorInfo.getId()});        
			if(oldSelevaltorInfoVO != null){
				 result = "exist";
				 return result;
			}
				
			java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String s = format1.format(new Date()); 
			num = SelevaltorInfo.updateAll(SelevaltorInfo.class, "buildingName=?,building=?,address=?,registCode=?,unit=?,useNumber=?,area=?,beizhu=?,townshipStreets=?,updateTime=?", new Object[]{selevaltorInfo.getBuildingName(),selevaltorInfo.getBuilding(),selevaltorInfo.getAddress(),selevaltorInfo.getRegistCode(),selevaltorInfo.getUnit(),selevaltorInfo.getUseNumber(),selevaltorInfo.getArea(),selevaltorInfo.getBeizhu(),selevaltorInfo.getTownshipStreets(),s},"id=?", new Object[] { selevaltorInfo.getId()});
		    if(num>0)
			result = "success"; 
		    	    
		 }
		catch (ActiveRecordException e) {
			e.printStackTrace();
		  } 
		 return result;
	 }
	 
	 public String tingYong(ElevaltorInfoVO elevaltorInfoVO){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 String result = "failure";
		 int id = elevaltorInfoVO.getId();
		 String registNumber = elevaltorInfoVO.getRegistNumber();
		 java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String s = format1.format(new Date()); 
		 int num = 0;
		 try {
			     ActiveRecordBase.beginTransaction();
				num = ElevaltorInfo.updateAll(ElevaltorInfo.class, "dailingFlag = ?,updateTime=?", new Object[]{1,s}, "id = ? and registNumber =?", new Object[]{id,registNumber});
			    if(num >0){
			    	/*
			    	TsDeviceService server = new TsDeviceService();  
					TsDeivceService hello = server.getTsDeivceServiceImplPort();
					String jkresult = hello.updateDeviceStatus("zyxt0703", "xr342fagf", "5", registNumber);
					System.out.println(jkresult); */
			    	TwoCodeElelog  elelog =new TwoCodeElelog();
			    	elelog.setUserName(userName);
			    	elelog.setSubTime(s);
			    	elelog.setContext(registNumber);
			    	elelog.setCmd("tingYong");
			    	if(elelog.save())
			    	  result = "success";
			  //  	jkdy("updateDeviceStatus","5",registNumber);   //������֪ͨ�ƺ�
			   // 	jkdy("updateDeviceStatus","5","107598");
			    			    	
			    }
			    ActiveRecordBase.commit();
			
			} catch (ActiveRecordException e1) {
				try {
					ActiveRecordBase.rollback();
				} catch (TransactionException e) {
					e.printStackTrace();
				}
			}
		
		 return result;
	 }
	 
	 public String sheMi(ElevaltorInfoVO elevaltorInfoVO){
		 String result = "failure";
		 int id = elevaltorInfoVO.getId();
		 String registNumber = elevaltorInfoVO.getRegistNumber();
		 java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String s = format1.format(new Date()); 
		 int num = 0;
		 try {
				num = ElevaltorInfo.updateAll(ElevaltorInfo.class, "shemiFlag = ?,updateTime=?", new Object[]{1,s}, "id = ? and registNumber =?", new Object[]{id,registNumber});
			    if(num >0)
			    	result = "success";
			
			} catch (ActiveRecordException e1) {
				e1.printStackTrace();
			}
		
		 return result;
	 }
	 
	 public String redotingYong(ElevaltorInfoVO elevaltorInfoVO){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 String result = "failure";
		 int id = elevaltorInfoVO.getId();
		 String registNumber = elevaltorInfoVO.getRegistNumber();
		 java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String s = format1.format(new Date()); 
		 int num = 0;
		 try {  
			    ActiveRecordBase.beginTransaction();
				num = ElevaltorInfo.updateAll(ElevaltorInfo.class, "dailingFlag = ?,updateTime=?", new Object[]{0,s}, "id = ? and registNumber =?", new Object[]{id,registNumber});
			    if(num >0){
		/*	    	TsDeviceService server = new TsDeviceService();  
					TsDeivceService hello = server.getTsDeivceServiceImplPort();
					String jkresult = hello.updateDeviceStatus("zyxt0703", "xr342fagf", "2", registNumber);
					System.out.println(jkresult); */
			    	TwoCodeElelog  elelog =new TwoCodeElelog();
			    	elelog.setUserName(userName);
			    	elelog.setSubTime(s);
			    	elelog.setContext(registNumber);
			    	elelog.setCmd("retingYong");
			    	if(elelog.save())
			    		result = "success";	
			    	
			//    	jkdy("updateDeviceStatus","2",registNumber);  //������֪ͨ�ƺ�
			 	
			    }
			    ActiveRecordBase.commit();
			
			} catch (ActiveRecordException e1) {
				try {
					ActiveRecordBase.rollback();
				} catch (TransactionException e) {
					e.printStackTrace();
				}
			}
		
		 return result;
	 }
	 
	 public String redosheMi(ElevaltorInfoVO elevaltorInfoVO){
		 String result = "failure";
		 int id = elevaltorInfoVO.getId();
		 String registNumber = elevaltorInfoVO.getRegistNumber();
		 java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String s = format1.format(new Date()); 
		 int num = 0;
		 try {
				num = ElevaltorInfo.updateAll(ElevaltorInfo.class, "shemiFlag = ?,updateTime=?", new Object[]{0,s}, "id = ? and registNumber =?", new Object[]{id,registNumber});
			    if(num >0)
			    	result = "success";
			
			} catch (ActiveRecordException e1) {
				e1.printStackTrace();
			}
		
		 return result;
	 }
	 
	 
	 public String queryByOrderexportExcel(ElevaltorInfoVO info){
		 String result = "failure"; 
    	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 int ywCompanyId =0;  //��ѯ�õ�
		 int wgCompanyId =0;
		 int zjcompanyId2 =0;  //�ǲ�ѯ�õģ��ǵ�½������ϵͳ��̨����role��ͬչʾ��ͬ�б��õ�
		 int townshipStreets2 =0; //�ǲ�ѯ�õģ��ǵ�½������ϵͳ��̨����role��ͬչʾ��ͬ�б��õ�
	     
		 String registNumber ="";
		 String areaName="";
		 String address ="";
		 String buildingName="";
		 String registCode="";
		 int townshipStreets =0;
		 String qstartTime ="";
		 String qendTime="";
		 
		 address =info.getAddress();
		 registNumber =info.getRegistNumber();
		 areaName =info.getArea();
		 buildingName =info.getBuildingName();
		 registCode=info.getRegistCode();
		 ywCompanyId =info.getYwCompanyId();
		 wgCompanyId =info.getWgCompanyId();
		 townshipStreets =info.getTownshipStreets();
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";    
		 
	try {      
      if(role==10 || role==11){
        UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
      if(userInfoVO != null){
          zjcompanyId2 = userInfoVO.getCompanyId();
          String companyArea = userInfoVO.getArea();
          conditions = " t.area = '"+companyArea+"'";
          }	 
       }
      
      if(role==20 || role==21){
          UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
        if(userInfoVO != null){
     	   townshipStreets2 = userInfoVO.getCompanyId();
            conditions = " t.townshipStreets = "+townshipStreets2;
            }	 
         }
      
      if(!"".equals(registNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registNumber like '%"+registNumber+"%'";	
				} 
				else{
				 conditions =" t.registNumber like '%"+registNumber+"%'";	
				}
			 
		 }
      
      if(!"".equals(address)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.address like '%"+address+"%'";	
				} 
				else{
					 conditions =" t.address like '%"+address+"%'";	
				}
			 
		 }
      
      if(!"".equals(buildingName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.buildingName like '%"+buildingName+"%'";	
				} 
				else{
					conditions =" t.buildingName like '%"+buildingName+"%'";	
				}
			 
		 }
      
      if(!"".equals(registCode)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registCode like '%"+registCode+"%'";	
				} 
				else{
					conditions =" t.registCode like '%"+registCode+"%'";	
				}
			 
		 }
      
    
      
      if(!"".equals(areaName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.area like '%"+areaName+"%'";	
				} 
				else{
					conditions =" t.area like '%"+areaName+"%'";	
				}
			 
		 }
      
      if(ywCompanyId > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.ywCompanyId = "+ywCompanyId;	
				} 
				else{
					conditions =" t.ywCompanyId = "+ywCompanyId;	
				}
			 
			 
		 }
      
      if(wgCompanyId > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.wgCompanyId = "+wgCompanyId;	
				} 
				else{
					conditions =" t.wgCompanyId = "+wgCompanyId;	
				}
			 
			 
		 }
      
      if(townshipStreets > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.townshipStreets = "+townshipStreets;	
				} 
				else{
					conditions =" t.townshipStreets = "+townshipStreets;	
				}
			 
			 
		 }
      
      if(!"".equals(qstartTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and left(t.registDate,10)  >= '"+qstartTime+"'" ;	 
			 } 
			 else{
				 conditions =" left(t.registDate,10)  >= '"+qstartTime+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and left(t.registDate,10)  <= '"+qendTime+"'" ;	 
			 } 
			 else{
				 conditions =" left(t.registDate,10)  <= '"+qendTime+"'" ;	 
			 }
		 }
      
      
      if(!"".equals(conditions)){
	    sql ="select isnull(t.area,'') as area,isnull(t.registNumber,'') as registNumber,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,isnull(t.registCode,'') as registCode,isnull(t.useNumber,'') as useNumber,isnull(t.nextInspectDate,'') as nextInspectDate,isnull((select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��'),'') as wgCompanyName, isnull((select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��'),'') as ywCompanyName,isnull((select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����'),'') as zzCompanyName, isnull((select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ'),'') as azCompanyName,isnull((select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���'),'') as jdbCompanyName,isnull(y.endTime,'') as  subTime from TwoCodeElevatorInfo  t left join YwManagerInfo  y  on t.registNumber = y.registNumber  where "+ conditions;
        conditionsSql = "select count(*) from TwoCodeElevatorInfo  t  where "+ conditions;
		 }
	 else{
		sql ="select isnull(t.area,'') as area,isnull(t.registNumber,'') as registNumber,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,isnull(t.registCode,'') as registCode,isnull(t.useNumber,'') as useNumber,isnull(t.nextInspectDate,'') as nextInspectDate,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,isnull(y.endTime,'') as  subTime  from TwoCodeElevatorInfo  t left join  YwManagerInfo  y  on t.registNumber = y.registNumber ";
		conditionsSql = "select count(*) from TwoCodeElevatorInfo  t ";				 
		 }
    
     long total =ElevaltorQueryExportVO.countBySql(ElevaltorQueryExportVO.class, conditionsSql, null); 
  	 List<ElevaltorQueryExportVO> items=ElevaltorQueryExportVO.findBySql(ElevaltorQueryExportVO.class, sql, null, null);
         
  	 String[] hearders = new String[] {"���ݱ��","ע�����", "��ַ", "¥������", "��������", "�ֵ���", "��λ�ڲ����","�´μ�������","ʹ�õ�λ","ά����λ","���쵥λ","��װ��λ","��άʱ��"};//��ͷ���� 
  	 ElevatorQueryExportExcel2<ElevaltorQueryExportVO> ex = new ElevatorQueryExportExcel2<ElevaltorQueryExportVO>(); 
  	 SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");  
	 String filename = "�����б�_"+timeFormat.format(new Date())+".xlsx";  
	 response.setContentType("application/ms-excel;charset=UTF-8");  
	 response.setHeader("Content-Disposition", "attachment;filename="  
	            .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
	 OutputStream out = response.getOutputStream();  
     ex.exportExcel2(hearders, items, out,sql);  
//	 ex.exportExcel(tjtime,hearders, items, out);
     out.close(); 
  //��һ�зǳ��ؼ���������ʵ�����п��ܳ���Ī����������⣡����
     response.flushBuffer();//ǿ�н���Ӧ�����е����ݷ��͵�Ŀ�ĵ� 
     System.out.println("excel�����ɹ���");  
     } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
	 catch (UnsupportedEncodingException e1) {
			
			e1.printStackTrace();
		}
	 catch(IOException e2){
		 e2.printStackTrace();
	 }
	 
		 
		return result;
	 }
	 
	 public View queryByOrder(ElevaltorInfoVO info,int page, int rows,String sort,String order)throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String cityName = GlobalFunction.cityName;
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 
		 String registNumber ="";
		 int ywCompanyId =0;  //��ѯ�õ�
		 int wgCompanyId =0;
		 int zjcompanyId2 =0;  //�ǲ�ѯ�õģ��ǵ�½������ϵͳ��̨����role��ͬչʾ��ͬ�б��õ�
		 int wgCompanyId2 =0;  //�ǲ�ѯ�õģ��ǵ�½������ϵͳ��̨����role��ͬչʾ��ͬ�б��õ�
		 int townshipStreets2 =0; //�ǲ�ѯ�õģ��ǵ�½������ϵͳ��̨����role��ͬչʾ��ͬ�б��õ�
	//	 String companyName="";
		 String areaName="";
		 String address ="";
		 String buildingName="";
		 String registCode="";
	//	 String useNumber="";
		 int townshipStreets =0;
		 String qstartTime ="";
		 String qendTime="";
		 
		
		 address =info.getAddress();
		 
		 /*
		 if(info.getYwCompanyName() == null)
			 companyName ="%"+""+"%";
		 else
		 companyName ="%"+info.getYwCompanyName()+"%";
		 */
		 
		 registNumber =info.getRegistNumber();
		 areaName =info.getArea();
		 buildingName =info.getBuildingName();
		 registCode=info.getRegistCode();
		 ywCompanyId =info.getYwCompanyId();
		 wgCompanyId =info.getWgCompanyId();
	//	 useNumber=info.getUseNumber();
		 townshipStreets =info.getTownshipStreets();
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 String SortName = request.getParameter("sort");  
	        //�״μ��ر���ʱsort��orderΪnull  
	     if(SortName == null){ 
	    	  if("cy".equals(userName)){
	            SortName ="y.endTime"; 
	    	  }
	    	  else{
	    		  SortName ="t.updateTime";   
	    	  }
	      }
	     if("registNumber".equals(SortName)){
	    	 SortName ="t.registNumber"; 
	     }
	        
	     String SortValue =request.getParameter("order");  
	        if(SortValue == null){  
	            SortValue = "desc";        
	        }  
	     
	        if(role==8 || role==9){
	            UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
	          if(userInfoVO != null){
	              wgCompanyId2 = userInfoVO.getCompanyId();
	         //      String companyArea = userInfoVO.getArea();
	         //     conditions = " t.zjcompanyId = "+zjcompanyId2;
	              conditions = " t.wgCompanyId = "+wgCompanyId2;
	              }	 
	           }
	        
         if(role==10 || role==11){
           UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
         if(userInfoVO != null){
             zjcompanyId2 = userInfoVO.getCompanyId();
             String companyArea = userInfoVO.getArea();
        //     conditions = " t.zjcompanyId = "+zjcompanyId2;
             conditions = " t.area = '"+companyArea+"'";
             }	 
          }
         
         if(role==20 || role==21){
             UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
           if(userInfoVO != null){
        	   townshipStreets2 = userInfoVO.getCompanyId();
               conditions = " t.townshipStreets = "+townshipStreets2;
               }	 
            }
         
         if(!"".equals(registNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registNumber like '%"+registNumber+"%'";	
				} 
				else{
				 conditions =" t.registNumber like '%"+registNumber+"%'";	
				}
			 
		 }
         
         if(!"".equals(address)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.address like '%"+address+"%'";	
				} 
				else{
					 conditions =" t.address like '%"+address+"%'";	
				}
			 
		 }
         
         if(!"".equals(buildingName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.buildingName like '%"+buildingName+"%'";	
				} 
				else{
					conditions =" t.buildingName like '%"+buildingName+"%'";	
				}
			 
		 }
         
         if(!"".equals(registCode)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registCode like '%"+registCode+"%'";	
				} 
				else{
					conditions =" t.registCode like '%"+registCode+"%'";	
				}
			 
		 }
         
         /*
         if(!"".equals(useNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.useNumber like '%"+useNumber+"%'";	
				} 
				else{
					conditions =" t.useNumber like '%"+useNumber+"%'";	
				}
			 
		 } */
         
         if(!"".equals(areaName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.area like '%"+areaName+"%'";	
				} 
				else{
					conditions =" t.area like '%"+areaName+"%'";	
				}
			 
		 }
         
         if(ywCompanyId > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.ywCompanyId = "+ywCompanyId;	
				} 
				else{
					conditions =" t.ywCompanyId = "+ywCompanyId;	
				}
			 
			 
		 }
         
         if(wgCompanyId > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.wgCompanyId = "+wgCompanyId;	
				} 
				else{
					conditions =" t.wgCompanyId = "+wgCompanyId;	
				}
			 
			 
		 }
         
         if(townshipStreets > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.townshipStreets = "+townshipStreets;	
				} 
				else{
					conditions =" t.townshipStreets = "+townshipStreets;	
				}
			 
			 
		 }
         
         if(!"".equals(qstartTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and left(t.registDate,10)  >= '"+qstartTime+"'" ;	 
			 } 
			 else{
				 conditions =" left(t.registDate,10)  >= '"+qstartTime+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and left(t.registDate,10)  <= '"+qendTime+"'" ;	 
			 } 
			 else{
				 conditions =" left(t.registDate,10)  <= '"+qendTime+"'" ;	 
			 }
		 }
         
         if(!"".equals(conditions)){
        	 if("1".equals(cityName)){
        	 sql ="select jyjyFlag,t.shibieCode,t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,t.buildingName,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,t.azCompanyId,t.wgCompanyId,t.ywCompanyId,t.zjCompanyId,t.jyCompanyId,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,y.endTime as  subTime,tcr.deviceId,t.ischangInfo,t.usePlace  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join YwManagerInfo  y  on t.registNumber = y.registNumber  where "+ conditions;
             		 
        	 }
        	 else {
          sql ="select jyjyFlag,t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,t.buildingName,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,t.azCompanyId,t.wgCompanyId,t.ywCompanyId,t.zjCompanyId,t.jyCompanyId,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,y.endTime as  subTime,tcr.deviceId,t.ischangInfo,t.usePlace  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join YwManagerInfo  y  on t.registNumber = y.registNumber  where "+ conditions;
                	
        	 }  
     //     conditionsSql = "select count(*) from TwoCodeElevatorYwCompanyInfo  t  where "+ conditions;
          conditionsSql = "select count(*) from TwoCodeElevatorInfo  t  where "+ conditions;
        
   		 }
   		 else{
   			if("1".equals(cityName)){
   			 sql ="select jyjyFlag,t.shibieCode,t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,t.buildingName,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,t.azCompanyId,t.wgCompanyId,t.ywCompanyId,t.zjCompanyId,t.jyCompanyId,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,y.endTime as  subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.usePlace  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join YwManagerInfo  y  on t.registNumber = y.registNumber ";
    					
   			}
   			else{
         sql ="select jyjyFlag,t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,t.buildingName,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,t.azCompanyId,t.wgCompanyId,t.ywCompanyId,t.zjCompanyId,t.jyCompanyId,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,y.endTime as  subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.usePlace  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join YwManagerInfo  y  on t.registNumber = y.registNumber ";
   			}  
   		//	 conditionsSql = "select count(*) from TwoCodeElevatorYwCompanyInfo  t ";
   		conditionsSql = "select count(*) from TwoCodeElevatorInfo  t ";
   					 
   		 }

      
		 
	//	 long total =TwoCodeElevatorYwCompanyInfo.countBySql(TwoCodeElevatorYwCompanyInfo.class, conditionsSql, null); 
	//	 List<TwoCodeElevatorYwCompanyInfo> items=TwoCodeElevatorYwCompanyInfo.findBySql(TwoCodeElevatorYwCompanyInfo.class, sql, null, "t.updateTime desc", rows, (page-1)*rows);
         
         long total =ElevaltorInfoNewVO.countBySql(ElevaltorInfoNewVO.class, conditionsSql, null); 
     	 List<ElevaltorInfoNewVO> items=ElevaltorInfoNewVO.findBySql(ElevaltorInfoNewVO.class, sql, null, SortName+" "+SortValue, rows, (page-1)*rows);
             
		 
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		 }
	 
	 
	 
	 public View query(ElevaltorInfoVO info,int page, int rows)throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 
		 String registNumber ="";
		 int ywCompanyId =0;  //��ѯ�õ�
		 int wgCompanyId =0;
		 int zjcompanyId2 =0;  //�ǲ�ѯ�õģ��ǵ�½������ϵͳ��̨����role��ͬչʾ��ͬ�б��õ�
	//	 String companyName="";
		 String areaName="";
		 String address ="";
		 String buildingName="";
		 String registCode="";
	//	 String useNumber="";
		 int townshipStreets =0;
		 
		
		 address =info.getAddress();
		 
		 /*
		 if(info.getYwCompanyName() == null)
			 companyName ="%"+""+"%";
		 else
		 companyName ="%"+info.getYwCompanyName()+"%";
		 */
		 
		 registNumber =info.getRegistNumber();
		 areaName =info.getArea();
		 buildingName =info.getBuildingName();
		 registCode=info.getRegistCode();
		 ywCompanyId =info.getYwCompanyId();
		 wgCompanyId =info.getWgCompanyId();
	//	 useNumber=info.getUseNumber();
		 townshipStreets =info.getTownshipStreets();
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 
	        
	        
         if(role==10 || role==11){
           UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
         if(userInfoVO != null){
             zjcompanyId2 = userInfoVO.getCompanyId();
             String companyArea = userInfoVO.getArea();
        //     conditions = " t.zjcompanyId = "+zjcompanyId2;
             conditions = " t.area = '"+companyArea+"'";
             }	 
          }
         
         if(!"".equals(registNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registNumber like '%"+registNumber+"%'";	
				} 
				else{
				 conditions =" t.registNumber like '%"+registNumber+"%'";	
				}
			 
		 }
         
         if(!"".equals(address)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.address like '%"+address+"%'";	
				} 
				else{
					 conditions =" t.address like '%"+address+"%'";	
				}
			 
		 }
         
         if(!"".equals(buildingName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.buildingName like '%"+buildingName+"%'";	
				} 
				else{
					conditions =" t.buildingName like '%"+buildingName+"%'";	
				}
			 
		 }
         
         if(!"".equals(registCode)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registCode like '%"+registCode+"%'";	
				} 
				else{
					conditions =" t.registCode like '%"+registCode+"%'";	
				}
			 
		 }
         
         /*
         if(!"".equals(useNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.useNumber like '%"+useNumber+"%'";	
				} 
				else{
					conditions =" t.useNumber like '%"+useNumber+"%'";	
				}
			 
		 } */
         
         if(!"".equals(areaName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.area like '%"+areaName+"%'";	
				} 
				else{
					conditions =" t.area like '%"+areaName+"%'";	
				}
			 
		 }
         
         if(ywCompanyId > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.ywCompanyId = "+ywCompanyId;	
				} 
				else{
					conditions =" t.ywCompanyId = "+ywCompanyId;	
				}
			 
			 
		 }
         
         if(wgCompanyId > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.wgCompanyId = "+wgCompanyId;	
				} 
				else{
					conditions =" t.wgCompanyId = "+wgCompanyId;	
				}
			 
			 
		 }
         
         if(townshipStreets > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.townshipStreets = "+townshipStreets;	
				} 
				else{
					conditions =" t.townshipStreets = "+townshipStreets;	
				}
			 
			 
		 }
         
         
         
         if(!"".equals(conditions)){
   	//	  sql ="select t.shemiFlag,t.dailingFlag,t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber  where "+ conditions;  
    //    sql ="select t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,t.azCompanyId,t.wgCompanyId,t.ywCompanyId,t.zjCompanyId,t.jyCompanyId,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber  where "+ conditions;
          sql ="select t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,t.buildingName,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,t.azCompanyId,t.wgCompanyId,t.ywCompanyId,t.zjCompanyId,t.jyCompanyId,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select max(y.subTime) from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0') as subTime,tcr.deviceId,t.ischangInfo  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber  where "+ conditions;

     //     conditionsSql = "select count(*) from TwoCodeElevatorYwCompanyInfo  t  where "+ conditions;
          conditionsSql = "select count(*) from TwoCodeElevatorInfo  t  where "+ conditions;
        
   		 }
   		 else{
   	//	  sql ="select t.shemiFlag,t.dailingFlag,t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber ";
   	//	  sql ="select t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,t.azCompanyId,t.wgCompanyId,t.ywCompanyId,t.zjCompanyId,t.jyCompanyId,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber";
   		  sql ="select t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,t.buildingName,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,t.azCompanyId,t.wgCompanyId,t.ywCompanyId,t.zjCompanyId,t.jyCompanyId,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select max(y.subTime) from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' ) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber";

   		//	 conditionsSql = "select count(*) from TwoCodeElevatorYwCompanyInfo  t ";
   		conditionsSql = "select count(*) from TwoCodeElevatorInfo  t ";
   					 
   		 }

      
		 
	//	 long total =TwoCodeElevatorYwCompanyInfo.countBySql(TwoCodeElevatorYwCompanyInfo.class, conditionsSql, null); 
	//	 List<TwoCodeElevatorYwCompanyInfo> items=TwoCodeElevatorYwCompanyInfo.findBySql(TwoCodeElevatorYwCompanyInfo.class, sql, null, "t.updateTime desc", rows, (page-1)*rows);
         
         long total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, conditionsSql, null); 
     	 List<ElevaltorInfoVO> items=ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null, "t.updateTime desc", rows, (page-1)*rows);
             
		 
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		 }
	 
	 
	 
	 public View elechangelist(String registNumber,int page, int rows) throws Exception{
		
		 long total=0;
		 List<EleChangeInfoVO> items = null;
	     
	    	 total  =EleChangeInfoVO.count(EleChangeInfoVO.class, "registNumber=?", new Object[]{registNumber});
	    //	 items = ElevaltorInfoVO.findAll(ElevaltorInfoVO.class, null, null, null, rows, (page-1)*rows);
	    //	 String sql ="";
	    	 items = EleChangeInfoVO.findAll(EleChangeInfoVO.class, "registNumber=?", new Object[]{registNumber},null, rows, (page-1)*rows);
	    	
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	 
	
	 
	 public View ychecklist(int page, int rows) throws Exception{
		 System.out.println("ִ��ychecklist����");
	  
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 System.out.println("ElevatorController---34---��½����---"+userName);
		 System.out.println("ElevatorController---36---role---"+role);
		 long total=0;
		 List<ElevaltorInfoVO> items = null;
	     if(role==2 || role==1){ //ϵͳ����Ա  
	    	 total  =ElevaltorInfoVO.count(ElevaltorInfoVO.class, null, null);
	    //	 items = ElevaltorInfoVO.findAll(ElevaltorInfoVO.class, null, null, null, rows, (page-1)*rows);
	    	 String sql ="select t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,isnull(t.area,'') as area,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName  from TwoCodeElevatorInfo  t ";
	    	 items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==22 || role==23){ //ϵͳ����Ա  
	    	 total  =ElevaltorInfoVO.count(ElevaltorInfoVO.class, null, null);
	    //	 items = ElevaltorInfoVO.findAll(ElevaltorInfoVO.class, null, null, null, rows, (page-1)*rows);
	    	 String sql ="select t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,isnull(t.area,'') as area,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName  from TwoCodeElevatorInfo  t ";
	    	 items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==10 || role ==11){ //�ʼ��
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         total  =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, "select count(*) from TwoCodeElevatorInfo t  where t.zjCompanyId=?", new Object[]{zjcompanyId});
	         System.out.println("�����ʼ�ֱ�ǩ��ά����"+total);
	         String sql ="select t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,isnull(t.area,'') as area,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName  from TwoCodeElevatorInfo  t where t.zjCompanyId=?";
	  	   	 items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, new Object[]{zjcompanyId}, "t.id desc", rows, (page-1)*rows);
	    	 }
	     }
	     
	     if(role==20 || role ==21){ //�ֵ���
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         total  =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, "select count(*) from TwoCodeElevatorInfo t  where t.townshipStreets=?", new Object[]{zjcompanyId});
	         System.out.println("�����ʼ�ֱ�ǩ��ά����"+total);
	         String sql ="select t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,isnull(t.area,'') as area,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName  from TwoCodeElevatorInfo  t where t.townshipStreets=?";
	  	   	 items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, new Object[]{zjcompanyId}, "t.id desc", rows, (page-1)*rows);
	    	 }
	     }
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	 
	 public View ycquery(ElevaltorInfoVO info,int page, int rows)throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 String registNumber ="";
		 int zjcompanyId2 =0;  //�ǲ�ѯ�õģ��ǵ�½������ϵͳ��̨����role��ͬչʾ��ͬ�б��õ�
		 String address ="";
		 String buildingName ="";
		 String areaName="";
		 String qstartTime ="";
		 String qendTime="";
		 String qstartTime2 ="";
		 String qendTime2="";
		 int townshipStreets =0;
		 
		 
		 registNumber =info.getRegistNumber();
		 address =info.getAddress();
		 areaName =info.getArea();
		 buildingName =info.getBuildingName();
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 qstartTime2 =info.getQstartTime2();
		 qendTime2 = info.getQendTime2();
		 townshipStreets =info.getTownshipStreets();
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 if(role==10 || role==11){
	           UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	         if(userInfoVO != null){
	             zjcompanyId2 = userInfoVO.getCompanyId();
	             conditions = " t.zjcompanyId = "+zjcompanyId2;
	             }	 
	          }
		 
		 if(!"".equals(registNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registNumber like '%"+registNumber+"%'";	
				} 
				else{
				 conditions =" t.registNumber like '%"+registNumber+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(address)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.address like '%"+address+"%'";	
				} 
				else{
					 conditions =" t.address like '%"+address+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(buildingName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.buildingName like '%"+buildingName+"%'";	
				} 
				else{
					conditions =" t.buildingName like '%"+buildingName+"%'";	
				}
			 
		 }
         
		
         if(townshipStreets > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.townshipStreets = "+townshipStreets;	
				} 
				else{
					conditions =" t.townshipStreets = "+townshipStreets;	
				}
			 
			 
		 }
         
         if(!"".equals(areaName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.area like '%"+areaName+"%'";	
				} 
				else{
					conditions =" t.area like '%"+areaName+"%'";	
				}
			 
		 }
         
         if(!"".equals(qstartTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and left(t.inspectDate,10)  >= '"+qstartTime+"'" ;	 
			 } 
			 else{
				 conditions =" left(t.inspectDate,10)  >= '"+qstartTime+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and left(t.inspectDate,10)  <= '"+qendTime+"'" ;	 
			 } 
			 else{
				 conditions =" left(t.inspectDate,10)  <= '"+qendTime+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(qstartTime2)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and left(t.nextInspectDate,10)  >= '"+qstartTime2+"'" ;	 
			 } 
			 else{
				 conditions =" left(t.nextInspectDate,10)  >= '"+qstartTime2+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(qendTime2)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and left(t.nextInspectDate,10)  <= '"+qendTime2+"'" ;	 
			 } 
			 else{
				 conditions =" left(t.nextInspectDate,10)  <= '"+qendTime2+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(conditions)){
			  sql ="select t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,isnull(t.area,'') as area,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName  from TwoCodeElevatorInfo  t where "+ conditions;
			  conditionsSql = "select count(1)  from TwoCodeElevatorInfo  t  where "+ conditions;
			 }
			 else{
			  sql ="select t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,isnull(t.area,'') as area,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName  from TwoCodeElevatorInfo  t  ";
			  conditionsSql = "select count(1)  from TwoCodeElevatorInfo  t  ";
						 
			 }
		 /*
		 registNumber ="%"+info.getRegistNumber()+"%";
        
		 String conditions="";
		 Object[] param=null;
		 if(role==2 || role==1){
		 conditions ="t.registNumber like  ? ";
		 param = new Object[]{registNumber};
		 }
		 
		 if(role==22 || role==23){
			 conditions ="t.registNumber like  ? ";
			 param = new Object[]{registNumber};
			 }
		 
		 if(role==10 || role==11){
			 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	    	 zjcompanyId2 = userInfoVO.getCompanyId();
			 conditions =" t.registNumber like  ?  and t.zjcompanyId=?";
			 param = new Object[]{registNumber,zjcompanyId2};
		 }
			 
		 }
		 
		 if(role==20 || role==21){
			 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	    	 zjcompanyId2 = userInfoVO.getCompanyId();
			 conditions =" t.registNumber like  ?  and t.townshipStreets=?";
			 param = new Object[]{registNumber,zjcompanyId2};
		 }
			 
		 }
		 
	     */
		 
		 long total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, conditionsSql, null);
		  List<ElevaltorInfoVO> items=ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
		
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	 }
	 
	 public View ddelevatorlist(int page, int rows) throws Exception{
		 System.out.println("ִ��ddelevatorlist����");
		 String cityName = GlobalFunction.cityName;
	     UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 System.out.println("ElevatorController---34---��½����---"+userName);
		 System.out.println("ElevatorController---36---role---"+role);
		 long total=0;
		 List<DdElevaltorInfoNewVO> items = null;
		 String sql ="";
	     if(role==2 || role==1){ //ϵͳ����Ա TwoCodeCompanyInfo 
	    	 total  =DdElevaltorInfoNewVO.count(DdElevaltorInfoNewVO.class, "recordSate < ?", new Object[]{3});
	    	 if(!"0".equals(cityName)){
	    	 sql ="select t.jyjyFlag,t.shibieCode,t.area,isnull(t.picregistNumber,'') as picregistNumber,t.isnormalFlag,t.id,t.shenhe,isnull(t.subTime2,'') as subTime2,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,isnull(t.deviceId2,'') as deviceId2,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber  where t.recordSate <3";	 
	    	 }
	    	 else{
	    	  sql ="select t.jyjyFlag,t.shibieCode,t.area,isnull(t.picregistNumber,'') as picregistNumber,t.isnormalFlag,t.id,t.shenhe,isnull(t.subTime2,'') as subTime2,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,isnull(t.deviceId2,'') as deviceId2,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber  where t.recordSate <3";
	    	 }
	    	 items = DdElevaltorInfoNewVO.findBySql(DdElevaltorInfoNewVO.class, sql, null, "t.ruKuValid,t.id desc", rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==22 || role==23){ //ϵͳ����Ա TwoCodeCompanyInfo 
	    	 total  =DdElevaltorInfoNewVO.count(DdElevaltorInfoNewVO.class, "recordSate < ?", new Object[]{3});
	    	 if("1".equals(cityName)){
	    	  sql ="select t.jyjyFlag,t.shibieCode,isnull(t.picregistNumber,'') as picregistNumber,t.isnormalFlag,t.id,t.shenhe,t.subTime2 as subTime2,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,isnull(t.deviceId2,'') as deviceId2, t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber  where t.recordSate <3"; 	 
	    	 }else{
	     	  sql ="select t.jyjyFlag,t.shibieCode,isnull(t.picregistNumber,'') as picregistNumber,t.isnormalFlag,t.id,t.shenhe,t.subTime2 as subTime2,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,isnull(t.deviceId2,'') as deviceId2, t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber  where t.recordSate <3";
	    	 }
	     	 items = DdElevaltorInfoNewVO.findBySql(DdElevaltorInfoNewVO.class, sql, null, "t.ruKuValid,t.id desc", rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==10 || role ==11){
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	    	 total  =DdElevaltorInfoNewVO.countBySql(DdElevaltorInfoNewVO.class, "select count(*) from TwoCodeElevatorInfo t  where t.zjCompanyId=? and  t.recordSate <3", new Object[]{zjcompanyId});
	    	 System.out.println("�����ʼ�ֱ�ǩ������"+total);
	    	 if("1".equals(cityName)){
	         sql ="select t.jyjyFlag,t.shibieCode,isnull(t.picregistNumber,'') as picregistNumber,t.isnormalFlag,t.id,t.shenhe,t.subTime2 as subTime2,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,isnull(t.deviceId2,'') as deviceId2,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber  where t.zjCompanyId=? and t.recordSate <3";
	    	 }else{
	    	sql ="select t.jyjyFlag,t.shibieCode,isnull(t.picregistNumber,'') as picregistNumber,t.isnormalFlag,t.id,t.shenhe,t.subTime2 as subTime2,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,isnull(t.deviceId2,'') as deviceId2,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber  where t.zjCompanyId=? and t.recordSate <3";		 
	    	 }
	         items = DdElevaltorInfoNewVO.findBySql(DdElevaltorInfoNewVO.class, sql, new Object[]{zjcompanyId}, "t.ruKuValid,t.id desc", rows, (page-1)*rows);	
	    	 }
	     } 
	     
	      /*
	      if(items != null && items.size()>0){  
	    	 Iterator<DdElevaltorInfoVO> it =items.iterator();
	         while(it.hasNext()){
	        	 DdElevaltorInfoVO  ddElevaltorInfoVO = it.next();
	        	 int recordSate  =ddElevaltorInfoVO.getRecordSate();
	        	 if(recordSate >=1){
	        		 int arrageType =ddElevaltorInfoVO.getArrageType();
	        		 int pastePersonID=ddElevaltorInfoVO.getPastePersonID();
	        		 if(arrageType ==1){   //�������˾ճ��������
	        			 CompanyInfo companyInfo=CompanyInfo.findFirst(CompanyInfo.class, "id = ?", new Object[]{pastePersonID});
	        			 if(companyInfo != null)
	        				 ddElevaltorInfoVO.setPastePersonName(companyInfo.getCompanyName());
	        		 }
	        		 else{   //�����ѧ��
	        			 UserExtInfo  userExtInfo=UserExtInfo.findFirst(UserExtInfo.class, "userid = ?", new Object[]{pastePersonID});
	        			 if(userExtInfo != null)
	        				 ddElevaltorInfoVO.setPastePersonName(userExtInfo.getUserName());
	        		 }
	        	 }
	        	 if(recordSate >1){  System.out.println("recordSate>1:");

	        	 int subPersonID =ddElevaltorInfoVO.getSubPersonID();
	        	 UserExtInfo  userExtInfo=UserExtInfo.findFirst(UserExtInfo.class, "userid = ?", new Object[]{subPersonID});
    			 if(userExtInfo != null){
    				 System.out.println("SubPersonName:"+userExtInfo.getUserName());
    				 ddElevaltorInfoVO.setSubPersonName(userExtInfo.getUserName());
    			   }
	        	 }
	         }
	      }  */
	      
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	 
	 public View taskcompleteinfotishi(int id){
		 Map<String, Object> result = new HashMap<String, Object>();
		 DdElevaltorInfoVO ddElevaltorInfoVO =null;
		 if(id >0){
			
			 try {
				
			    String sql ="select t.subPersonID,isnull(t.mobileUploadbeizhu,'') as mobileUploadbeizhu,isnull(t.rukubeizhu,'') as rukubeizhu,t.arrageType,CONVERT(CHAR(19), t.pasteTime, 20) as pasteTime2,t.pastePersonID,t.pasteNote,CONVERT(CHAR(19), t.arrangeTime, 20) as arrangeTime2,t.subTime2 as subTime2,t.recordSate,(select te.userName from twocodeuserExtinfo te where   te.userid =t.arrangePersonID) as arrangePersonName  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber  where t.id = ?";
				ddElevaltorInfoVO  =  DdElevaltorInfoVO.findFirstBySql(DdElevaltorInfoVO.class, sql, new Object[]{id});
				if(ddElevaltorInfoVO != null){
					 int recordSate  =ddElevaltorInfoVO.getRecordSate();
		        	 if(recordSate >=1){
		        		 int arrageType =ddElevaltorInfoVO.getArrageType();
		        		 int pastePersonID=ddElevaltorInfoVO.getPastePersonID();
		        		 if(arrageType ==1){   //�������˾ճ��������
		        			 CompanyInfo companyInfo=CompanyInfo.findFirst(CompanyInfo.class, "id = ?", new Object[]{pastePersonID});
		        			 if(companyInfo != null)
		        				 ddElevaltorInfoVO.setPastePersonName(companyInfo.getCompanyName());
		        		 }
		        		 else{   //�����ѧ��
		        			 UserExtInfo  userExtInfo=UserExtInfo.findFirst(UserExtInfo.class, "userid = ?", new Object[]{pastePersonID});
		        			 if(userExtInfo != null)
		        				 ddElevaltorInfoVO.setPastePersonName(userExtInfo.getUserName());
		        		 }
		        	 }
		        	 if(recordSate >1){  
		        	 int subPersonID =ddElevaltorInfoVO.getSubPersonID();
		        	 UserExtInfo  userExtInfo=UserExtInfo.findFirst(UserExtInfo.class, "userid = ?", new Object[]{subPersonID});
	    			 if(userExtInfo != null){
	    				 ddElevaltorInfoVO.setSubPersonName(userExtInfo.getUserName());
	    			   }
		        	 }	
				}
			} catch (ActiveRecordException e) {
				e.printStackTrace();
			}
			
		 }
		 result.put("arrangePersonName", ddElevaltorInfoVO.getArrangePersonName());
		 result.put("arrangeTime2", ddElevaltorInfoVO.getArrangeTime2());
		 result.put("pastePersonName", ddElevaltorInfoVO.getPastePersonName());
		 result.put("pasteTime2", ddElevaltorInfoVO.getPasteTime2());
		 result.put("subPersonName",ddElevaltorInfoVO.getSubPersonName());
		 result.put("mobileUploadbeizhu",ddElevaltorInfoVO.getMobileUploadbeizhu());
		 result.put("pasteNote",ddElevaltorInfoVO.getPasteNote());
		 return new JsonView(result);
	 } 
	 
	 public View rukuddelevatorlist(int page, int rows) throws Exception{
		 System.out.println("ִ��rukuddelevatorlist����");
	     UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 long total=0;
		 List<DdElevaltorInfoVO> items = null;
	     if(role==2 || role==1){ //ϵͳ����Ա 
	    	 total  =DdElevaltorInfoVO.count(DdElevaltorInfoVO.class, "recordSate = ?", new Object[]{3});
	   // 	 String sql ="select  t.rukubeizhu,t.arrageType,t.pasteTime,t.pastePersonID,t.pasteNote,tad.userName,CONVERT(CHAR(19), t.arrangeTime, 20) as arrangeTime2,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,(select te.userName from twocodeuserExtinfo te where   te.userid =t.arrangePersonID) as arrangePersonName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,t.ischangInfo,t.ruKuValid  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.recordSate =3";
	    	 String sql ="select  t.rukubeizhu,t.arrageType,t.pasteTime,t.pastePersonID,t.pasteNote,t.mobileUploadbeizhu, t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,t.ruKuValid  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber  where t.recordSate =3";
	    	 
	    	 items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, null, "t.ruKuValid,t.id desc", rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==22 || role==23){ //ϵͳ����Ա TwoCodeCompanyInfo 
	    	 total  =DdElevaltorInfoVO.count(DdElevaltorInfoVO.class, "recordSate = ?", new Object[]{3});
	   // 	 String sql ="select t.rukubeizhu,t.arrageType,t.pasteTime,t.pastePersonID,t.pasteNote,tad.userName,CONVERT(CHAR(19), t.arrangeTime, 20) as arrangeTime2,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,(select te.userName from twocodeuserExtinfo te where   te.userid =t.arrangePersonID) as arrangePersonName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,t.ischangInfo,t.ruKuValid  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.recordSate =3";
	    	 String sql ="select t.rukubeizhu,t.arrageType,t.pasteTime,t.pastePersonID,t.pasteNote,t.mobileUploadbeizhu,t.arrangeTime as arrangeTime2,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,(select te.userName from twocodeuserExtinfo te where   te.userid =t.arrangePersonID) as arrangePersonName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,t.ruKuValid  from TwoCodeDdElevatorInfo  t  where t.recordSate =3";
	    	 items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, null, "t.ruKuValid,t.id desc", rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==10 || role ==11){
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	    	 total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class, "select count(*) from TwoCodeElevatorInfo t  where t.zjCompanyId=? and  t.recordSate =3", new Object[]{zjcompanyId});
	    //	 String sql ="select t.rukubeizhu,t.arrageType,t.pasteTime,t.pastePersonID,t.pasteNote,tad.userName,CONVERT(CHAR(19), t.arrangeTime, 20) as arrangeTime2,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,(select te.userName from twocodeuserExtinfo te where   te.userid =t.arrangePersonID) as arrangePersonName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,t.ischangInfo,t.ruKuValid  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.zjCompanyId=? and t.recordSate =3";
	    	 String sql ="select t.rukubeizhu,t.arrageType,t.pasteTime,t.pastePersonID,t.pasteNote,t.mobileUploadbeizhu,t.arrangeTime as arrangeTime2,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,(select te.userName from twocodeuserExtinfo te where   te.userid =t.arrangePersonID) as arrangePersonName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,t.ruKuValid  from TwoCodeDdElevatorInfo  t  where t.zjCompanyId=? and t.recordSate =3";
	 	    
	    	 items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, new Object[]{zjcompanyId}, "t.ruKuValid,t.id desc", rows, (page-1)*rows);	
	    	 }
	     } 
	     /*
	      if(items != null && items.size()>0){
	    	 Iterator<DdElevaltorInfoVO> it =items.iterator();
	         while(it.hasNext()){
	        	 DdElevaltorInfoVO  ddElevaltorInfoVO = it.next();
	        	 int recordSate  =ddElevaltorInfoVO.getRecordSate();
	        	 if(recordSate >=1){
	        		 int arrageType =ddElevaltorInfoVO.getArrageType();
	        		 int pastePersonID=ddElevaltorInfoVO.getPastePersonID();
	        		 if(arrageType ==1){   //�������˾ճ��������
	        			 CompanyInfo companyInfo=CompanyInfo.findFirst(CompanyInfo.class, "id = ?", new Object[]{pastePersonID});
	        			 if(companyInfo != null)
	        				 ddElevaltorInfoVO.setPastePersonName(companyInfo.getCompanyName());
	        		 }
	        		 else{   //�����ѧ��
	        			 UserExtInfo  userExtInfo=UserExtInfo.findFirst(UserExtInfo.class, "userid = ?", new Object[]{pastePersonID});
	        			 if(userExtInfo != null)
	        				 ddElevaltorInfoVO.setPastePersonName(userExtInfo.getUserName());
	        		 }
	        	 }
	         }
	      } */
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	 
	 public View hechaddelevatorlist(int page, int rows) throws Exception{
		
	     UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 long total=0;
		 List<DdElevaltorInfoVO> items = null;
	     if(role==2 || role==1){ //ϵͳ����Ա TwoCodeCompanyInfo 
	    	 total  =DdElevaltorInfoVO.count(DdElevaltorInfoVO.class, "recordSate = ?", new Object[]{4});
	    	 String sql ="select t.subPersonID,isnull(t.mobileUploadbeizhu,'') as mobileUploadbeizhu,isnull(t.rukubeizhu,'') as rukubeizhu,t.arrageType,t.pasteTime,t.pastePersonID,t.pasteNote,tad.userName,CONVERT(CHAR(19), t.arrangeTime, 20) as arrangeTime2,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,t.azCompanyId,t.wgCompanyId,t.ywCompanyId,t.zjCompanyId,t.jyCompanyId,t.townshipStreets,(select te.userName from twocodeuserExtinfo te where   te.userid =t.arrangePersonID) as arrangePersonName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.recordSate =4";
	   // 	 String sql ="select t.subPersonID,isnull(t.mobileUploadbeizhu,'') as mobileUploadbeizhu,isnull(t.rukubeizhu,'') as rukubeizhu,t.arrageType,t.pasteTime,t.pastePersonID,t.pasteNote,tad.userName,CONVERT(CHAR(19), t.arrangeTime, 20) as arrangeTime2,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,(select te.userName from twocodeuserExtinfo te where   te.userid =t.arrangePersonID) as arrangePersonName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.recordSate =4";
	    	 items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, null, "t.ruKuValid,t.id desc", rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==22 || role==23){ //ϵͳ����Ա TwoCodeCompanyInfo 
	    	 total  =DdElevaltorInfoVO.count(DdElevaltorInfoVO.class, "recordSate = ?", new Object[]{4});
	    	 String sql ="select t.subPersonID,isnull(t.mobileUploadbeizhu,'') as mobileUploadbeizhu,isnull(t.rukubeizhu,'') as rukubeizhu,t.arrageType,t.pasteTime,t.pastePersonID,t.pasteNote,tad.userName,CONVERT(CHAR(19), t.arrangeTime, 20) as arrangeTime2,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,t.azCompanyId,t.wgCompanyId,t.ywCompanyId,t.zjCompanyId,t.jyCompanyId,t.townshipStreets,(select te.userName from twocodeuserExtinfo te where   te.userid =t.arrangePersonID) as arrangePersonName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.recordSate =4";
	   // 	 String sql ="select t.subPersonID,isnull(t.mobileUploadbeizhu,'') as mobileUploadbeizhu,isnull(t.rukubeizhu,'') as rukubeizhu,t.arrageType,t.pasteTime,t.pastePersonID,t.pasteNote,tad.userName,CONVERT(CHAR(19), t.arrangeTime, 20) as arrangeTime2,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,(select te.userName from twocodeuserExtinfo te where   te.userid =t.arrangePersonID) as arrangePersonName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.recordSate =4";
	    	 items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, null, "t.ruKuValid,t.id desc", rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==10 || role ==11){
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	    	 total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class, "select count(*) from TwoCodeElevatorInfo t  where t.zjCompanyId=? and  t.recordSate =4", new Object[]{zjcompanyId});
	    	 System.out.println("�����ʼ�ֱ�ǩ������"+total);
	    	 String sql ="select t.subPersonID,isnull(t.mobileUploadbeizhu,'') as mobileUploadbeizhu,isnull(t.rukubeizhu,'') as rukubeizhu,t.arrageType,t.pasteTime,t.pastePersonID,t.pasteNote,tad.userName,CONVERT(CHAR(19), t.arrangeTime, 20) as arrangeTime2,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,t.azCompanyId,t.wgCompanyId,t.ywCompanyId,t.zjCompanyId,t.jyCompanyId,t.townshipStreets,(select te.userName from twocodeuserExtinfo te where   te.userid =t.arrangePersonID) as arrangePersonName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.zjCompanyId=? and t.recordSate =4";
	    //	 String sql ="select t.subPersonID,isnull(t.mobileUploadbeizhu,'') as mobileUploadbeizhu,isnull(t.rukubeizhu,'') as rukubeizhu,t.arrageType,t.pasteTime,t.pastePersonID,t.pasteNote,tad.userName,CONVERT(CHAR(19), t.arrangeTime, 20) as arrangeTime2,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,(select te.userName from twocodeuserExtinfo te where   te.userid =t.arrangePersonID) as arrangePersonName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.zjCompanyId=? and t.recordSate =4";
	    	 items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, new Object[]{zjcompanyId}, "t.ruKuValid,t.id desc", rows, (page-1)*rows);	
	    	 }
	     } 
	      if(items != null && items.size()>0){  
	    	 Iterator<DdElevaltorInfoVO> it =items.iterator();
	         while(it.hasNext()){
	        	 DdElevaltorInfoVO  ddElevaltorInfoVO = it.next();
	        	 int recordSate  =ddElevaltorInfoVO.getRecordSate();
	        	 if(recordSate >=1){
	        		 int arrageType =ddElevaltorInfoVO.getArrageType();
	        		 int pastePersonID=ddElevaltorInfoVO.getPastePersonID();
	        		 if(arrageType ==1){   //�������˾ճ��������
	        			 CompanyInfo companyInfo=CompanyInfo.findFirst(CompanyInfo.class, "id = ?", new Object[]{pastePersonID});
	        			 if(companyInfo != null)
	        				 ddElevaltorInfoVO.setPastePersonName(companyInfo.getCompanyName());
	        		 }
	        		 else{   //�����ѧ��
	        			 UserExtInfo  userExtInfo=UserExtInfo.findFirst(UserExtInfo.class, "userid = ?", new Object[]{pastePersonID});
	        			 if(userExtInfo != null)
	        				 ddElevaltorInfoVO.setPastePersonName(userExtInfo.getUserName());
	        		 }
	        	 }
	        	 if(recordSate >1){  System.out.println("recordSate>1:");

	        	 int subPersonID =ddElevaltorInfoVO.getSubPersonID();
	        	 UserExtInfo  userExtInfo=UserExtInfo.findFirst(UserExtInfo.class, "userid = ?", new Object[]{subPersonID});
    			 if(userExtInfo != null){
    				 System.out.println("SubPersonName:"+userExtInfo.getUserName());
    				 ddElevaltorInfoVO.setSubPersonName(userExtInfo.getUserName());
    			   }
	        	 }
	         }
	      }
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	 
	 
	 public String ddadd(DdElevaltorInfo elevaltorInfo){
		 /*
		 if("".equals(elevaltorInfo.getTownshipStreets2()) || elevaltorInfo.getTownshipStreets2()==null)
			 elevaltorInfo.setTownshipStreets(0);
		 else
			 elevaltorInfo.setTownshipStreets(Integer.valueOf(elevaltorInfo.getTownshipStreets2()).intValue());
		
		 
		 if("".equals(elevaltorInfo.getWgCompanyId2()) || elevaltorInfo.getWgCompanyId2() == null)
			 elevaltorInfo.setWgCompanyId(0);
		 else
			 elevaltorInfo.setWgCompanyId(Integer.valueOf(elevaltorInfo.getWgCompanyId2()).intValue());
		 
		 if("".equals(elevaltorInfo.getYwCompanyId2()) || elevaltorInfo.getYwCompanyId2() == null)
			 elevaltorInfo.setYwCompanyId(0);
		 else
			 elevaltorInfo.setYwCompanyId(Integer.valueOf(elevaltorInfo.getYwCompanyId2()).intValue());
		 
		 if("".equals(elevaltorInfo.getZzCompanyId2()) || elevaltorInfo.getZzCompanyId2() == null)
			 elevaltorInfo.setZzCompanyId(0);
		 else
			 elevaltorInfo.setZzCompanyId(Integer.valueOf(elevaltorInfo.getZzCompanyId2()).intValue());
		 
		 if("".equals(elevaltorInfo.getAzCompanyId2()) || elevaltorInfo.getAzCompanyId2() == null)
			 elevaltorInfo.setAzCompanyId(0);
		 else
			 elevaltorInfo.setAzCompanyId(Integer.valueOf(elevaltorInfo.getAzCompanyId2()).intValue());
		 
		 if("".equals(elevaltorInfo.getJyCompanyId2()) || elevaltorInfo.getJyCompanyId2() == null)
			 elevaltorInfo.setJyCompanyId(0);
		 else
			 elevaltorInfo.setJyCompanyId(Integer.valueOf(elevaltorInfo.getJyCompanyId2()).intValue());
		 
		 if("".equals(elevaltorInfo.getZjCompanyId2()) || elevaltorInfo.getZjCompanyId2() == null)
			 elevaltorInfo.setZjCompanyId(0);
		 else
			 elevaltorInfo.setZjCompanyId(Integer.valueOf(elevaltorInfo.getZjCompanyId2()).intValue());
		
		  */
		 if("".equals(elevaltorInfo.getMap_X()) || elevaltorInfo.getMap_X() == null )
				elevaltorInfo.setMap_X("0.000000");
		 else
			 elevaltorInfo.setMap_X(elevaltorInfo.getMap_X());
		if("".equals(elevaltorInfo.getMap_Y()) || elevaltorInfo.getMap_Y() == null)
				elevaltorInfo.setMap_Y("0.000000");
		else
			elevaltorInfo.setMap_Y(elevaltorInfo.getMap_Y());
		
		 
		//�ж��ܷ�����������������Ϊ�գ�����0
		 if(ruKuisvalid(elevaltorInfo))
			 elevaltorInfo.setRuKuValid(1);
		 else
			 elevaltorInfo.setRuKuValid(0);
		 
		 String result = "failure";
		//Ҫ������±�ǩ��Ϣ����updateTime�ֶΣ�С���Ǳ�Ҫ��
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = format1.format(new Date()); 
		elevaltorInfo.setUpdateTime(s);
		 try {
		//	String querySql ="select t.* from TwoCodeElevatorInfo t where t.registNumber =?";
			DdElevaltorInfoVO  elevaltorInfoExit = null;
			if(!"".equals(elevaltorInfo.getRegistNumber()) && elevaltorInfo.getRegistNumber() != null)
			elevaltorInfoExit = DdElevaltorInfoVO.findFirst(DdElevaltorInfoVO.class, "registNumber = ?", new Object[] { elevaltorInfo.getRegistNumber()});
	    if(elevaltorInfoExit != null){
		    result = "exist";
		    }
		else{
		 //�ж��ܷ�����������������Ϊ�գ�����0
		 if(ruKuisvalid(elevaltorInfo))
			 elevaltorInfo.setRuKuValid(1);
		 else
			 elevaltorInfo.setRuKuValid(0);
			 
		if ("".equals(elevaltorInfo.getDeviceId())) { // ��������ϻ���豸
			        ActiveRecordBase.beginTransaction();
					boolean flag = elevaltorInfo.save();
					if (flag){
						ActiveRecordBase.commit();
						result ="success";
					}
					else { 
						ActiveRecordBase.rollback();
						result ="success";
					}
			}
		 else{             //������ϻ���豸                        
			 ActiveRecordBase.beginTransaction();
			 boolean flag = elevaltorInfo.save();
			 if(flag){
				 DdElevaltorInfoVO elevaltorInfoVO = null;
				 String sql ="select t.id from TwoCodeDeviceRelationInfo t where t.registNumber =?";
				 elevaltorInfoVO =DdElevaltorInfoVO.findFirstBySql(DdElevaltorInfoVO.class, sql, new Object[] { elevaltorInfo.getRegistNumber()});
				 if(elevaltorInfoVO!=null){
					 if(TwoCodeDeviceRelationInfo.updateAll(TwoCodeDeviceRelationInfo.class, "deviceId = ?", new Object[]{elevaltorInfo.getDeviceId()}, "registNumber = ?", new Object[]{elevaltorInfo.getRegistNumber()})>0)
					 result ="success";
				 }
				 else{
					 TwoCodeDeviceRelationInfo twoCodeDeviceRelationInfo = new TwoCodeDeviceRelationInfo();
					 twoCodeDeviceRelationInfo.setRegistNumber(elevaltorInfo.getRegistNumber());
					 twoCodeDeviceRelationInfo.setDeviceId(elevaltorInfo.getDeviceId());
					 if(twoCodeDeviceRelationInfo.save())
					 result ="success";
				 }
				 ActiveRecordBase.commit();
			 }
			 else{
					  ActiveRecordBase.rollback();
			 }
			
		 }
		}
		 }catch(Exception e){
			 e.printStackTrace();
			 try {
				super.dbrollback();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
			 
		 }
		
		 return result;
		
		
	 }
	 
	 /*
	 public View compyNameddedit(int id)throws Exception{ 
		
		 String sql ="select t.zzCompanyId as zzId,(select companyName from TwoCodeCompanyInfo where id = t.zzCompanyId ) as zzCompanyName from TwoCodeDdElevatorInfo t where t.id = ?";
		 List<CompanyInfoVO> info =CompanyInfoVO.findBySql(CompanyInfoVO.class, sql, new Object[] { id });
		 return new JsonView(info);
		 
	 }
	*/ 
	 
	 //�����������޸Ĺ���
	 public View ddedit(int id)throws Exception{ 
		 String cityName = GlobalFunction.cityName;
		 String sql = "";
		 System.out.println("edit---id:"+id);
		 if(!"0".equals(cityName)){
		 sql ="select t.neleType,t.shibieCode,isnull(t.registNumber,'') as registNumber,isnull(t.address,'') as address,isnull(t.name,'') as name,t.eleType,isnull(t.registCode,'') as registCode,isnull(t.inoutDoor,'') as inoutDoor,isnull(t.propertyRightsUnit,'') as propertyRightsUnit,t.zzCompanyId ,isnull(t.eleMode,'') as eleMode,t.wgCompanyId,t.jyCompanyId,isnull(t.useNumber,'') as useNumber,isnull(t.safetyManDepart,'') as safetyManDepart,isnull(t.safetyManPerson,'') as safetyManPerson,isnull(t.safetyManPersonTel,'') as safetyManPersonTel,isnull(t.inspector,'') as inspector,isnull(t.nextInspectDate,'') as nextInspectDate,isnull(t.completeAcceptanceDate,'') as completeAcceptanceDate,isnull(t.acceptanceDateDepart,'') as acceptanceDateDepart,isnull(t.acceptanceReportNum,'') as acceptanceReportNum,t.azCompanyId,t.ywCompanyId,t.zjCompanyId,t.townshipStreets,isnull(t.eleLoad,'') as eleLoad,isnull(t.speed,'') as speed,isnull(t.eleheight,'') as eleheight,isnull(t.elewidth,'') as elewidth,isnull(t.eleStop,'') as eleStop,isnull(t.mobileCode,'') as mobileCode,isnull(t.area,'') as area,isnull(t.buildingName,'') as buildingName,isnull(t.building,'') as building,isnull(t.unit,'') as unit,isnull(t.coordinates,'') as coordinates,t.map_X,t.map_Y,isnull(t.manufactDate,'') as manufactDate,isnull(t.factoryNum,'') as factoryNum,isnull(t.useDate,'') as useDate,isnull(t.checkCategory,'') as checkCategory,isnull(t.checkResult,'') as checkResult,isnull(t.checkReportNum,'') as checkReportNum,isnull(t.inspectDate,'') as inspectDate,isnull(t.registCode2,'') as registCode2,isnull(t.registor,'') as registor,isnull(t.note,'') as note,isnull(t.mContractVdate,'') as mContractVdate,isnull(t.handleCompany,'') as handleCompany,isnull(t.handleCompanyCode,'') as handleCompanyCode,isnull(t.changeWay,'') as changeWay,isnull(tc.deviceId,'') as deviceId,isnull(t.deviceId2,'') as deviceId2  from TwoCodeDdElevatorInfo t left join TwoCodeDeviceRelationInfo tc on t.registNumber = tc.registNumber where t.id = ?";			 
		 }
		 else{
		 sql ="select isnull(t.registNumber,'') as registNumber,isnull(t.address,'') as address,isnull(t.name,'') as name,t.eleType,isnull(t.registCode,'') as registCode,isnull(t.inoutDoor,'') as inoutDoor,isnull(t.propertyRightsUnit,'') as propertyRightsUnit,t.zzCompanyId ,isnull(t.eleMode,'') as eleMode,t.wgCompanyId,t.jyCompanyId,isnull(t.useNumber,'') as useNumber,isnull(t.safetyManDepart,'') as safetyManDepart,isnull(t.safetyManPerson,'') as safetyManPerson,isnull(t.safetyManPersonTel,'') as safetyManPersonTel,isnull(t.inspector,'') as inspector,isnull(t.nextInspectDate,'') as nextInspectDate,isnull(t.completeAcceptanceDate,'') as completeAcceptanceDate,isnull(t.acceptanceDateDepart,'') as acceptanceDateDepart,isnull(t.acceptanceReportNum,'') as acceptanceReportNum,t.azCompanyId,t.ywCompanyId,t.zjCompanyId,t.townshipStreets,isnull(t.eleLoad,'') as eleLoad,isnull(t.speed,'') as speed,isnull(t.eleheight,'') as eleheight,isnull(t.elewidth,'') as elewidth,isnull(t.eleStop,'') as eleStop,isnull(t.mobileCode,'') as mobileCode,isnull(t.area,'') as area,isnull(t.buildingName,'') as buildingName,isnull(t.building,'') as building,isnull(t.unit,'') as unit,isnull(t.coordinates,'') as coordinates,t.map_X,t.map_Y,isnull(t.manufactDate,'') as manufactDate,isnull(t.factoryNum,'') as factoryNum,isnull(t.useDate,'') as useDate,isnull(t.checkCategory,'') as checkCategory,isnull(t.checkResult,'') as checkResult,isnull(t.checkReportNum,'') as checkReportNum,isnull(t.inspectDate,'') as inspectDate,isnull(t.registCode2,'') as registCode2,isnull(t.registor,'') as registor,isnull(t.note,'') as note,isnull(t.mContractVdate,'') as mContractVdate,isnull(t.handleCompany,'') as handleCompany,isnull(t.handleCompanyCode,'') as handleCompanyCode,isnull(t.changeWay,'') as changeWay,isnull(tc.deviceId,'') as deviceId,isnull(t.deviceId2,'') as deviceId2  from TwoCodeDdElevatorInfo t left join TwoCodeDeviceRelationInfo tc on t.registNumber = tc.registNumber where t.id = ?";
		 }
		 DdElevaltorInfoVO elevaltorInfoVO =DdElevaltorInfoVO.findFirstBySql(DdElevaltorInfoVO.class, sql, new Object[] { id });
		 Map<String, Object> result = new HashMap<String, Object>();
		 if(elevaltorInfoVO !=null){
		 result.put("registNumber", elevaltorInfoVO.getRegistNumber());
		 result.put("address", elevaltorInfoVO.getAddress());
		 if(!"0".equals(cityName)){
		 result.put("shibieCode", elevaltorInfoVO.getShibieCode());
		 result.put("neleType", elevaltorInfoVO.getNeleType());
		 }
		 result.put("name", elevaltorInfoVO.getName());
		 result.put("eleType", elevaltorInfoVO.getEleType());
		 result.put("registCode", elevaltorInfoVO.getRegistCode());
		 result.put("inoutDoor", elevaltorInfoVO.getInoutDoor());
		 result.put("propertyRightsUnit", elevaltorInfoVO.getPropertyRightsUnit());
		// System.out.println("zzCompanyId"+ elevaltorInfoVO.getZzCompanyId());
		// result.put("zzCompanyId2", elevaltorInfoVO.getZzCompanyId());
		 result.put("zzCompanyId",elevaltorInfoVO.getZzCompanyId());
	//	 result.put("zzCompanyName",elevaltorInfoVO.getZzCompanyName());
		 result.put("eleMode", elevaltorInfoVO.getEleMode());
		 result.put("wgCompanyId", elevaltorInfoVO.getWgCompanyId());
	//     result.put("wgCompanyName", elevaltorInfoVO.getWgCompanyName());
		 result.put("useNumber", elevaltorInfoVO.getUseNumber());
		 result.put("safetyManDepart", elevaltorInfoVO.getSafetyManDepart());
		 result.put("safetyManPerson", elevaltorInfoVO.getSafetyManPerson());
		 result.put("safetyManPersonTel", elevaltorInfoVO.getSafetyManPersonTel());
		 result.put("jyCompanyId", elevaltorInfoVO.getJyCompanyId());
		 result.put("inspector", elevaltorInfoVO.getInspector());
		 result.put("nextInspectDate", elevaltorInfoVO.getNextInspectDate());
		 result.put("completeAcceptanceDate", elevaltorInfoVO.getCompleteAcceptanceDate());
		 result.put("acceptanceDateDepart", elevaltorInfoVO.getAcceptanceDateDepart());
		 result.put("acceptanceReportNum", elevaltorInfoVO.getAcceptanceReportNum());
		 result.put("azCompanyId", elevaltorInfoVO.getAzCompanyId());
		 result.put("ywCompanyId", elevaltorInfoVO.getYwCompanyId());
		 result.put("zjCompanyId", elevaltorInfoVO.getZjCompanyId());
		 result.put("eleLoad", elevaltorInfoVO.getEleLoad());
		 result.put("speed", elevaltorInfoVO.getSpeed());
		 result.put("eleheight", elevaltorInfoVO.getEleheight());
		 result.put("elewidth", elevaltorInfoVO.getElewidth());
		 result.put("eleStop", elevaltorInfoVO.getEleStop());
	//	 result.put("mobileCode", elevaltorInfoVO.getMobileCode());
		 result.put("deviceId", elevaltorInfoVO.getDeviceId());
		 
		 result.put("townshipStreets", elevaltorInfoVO.getTownshipStreets());
		 result.put("area", elevaltorInfoVO.getArea());
		 result.put("buildingName", elevaltorInfoVO.getBuildingName());
		 result.put("building", elevaltorInfoVO.getBuilding());
		 result.put("unit", elevaltorInfoVO.getUnit());
	//   result.put("coordinates", elevaltorInfoVO.getCoordinates());
		 result.put("map_X", elevaltorInfoVO.getMap_X());
		 result.put("map_Y", elevaltorInfoVO.getMap_Y());
		 
		 result.put("manufactDate", elevaltorInfoVO.getManufactDate());
		 result.put("factoryNum", elevaltorInfoVO.getFactoryNum());
		 result.put("useDate", elevaltorInfoVO.getUseDate());
		 result.put("checkCategory", elevaltorInfoVO.getCheckCategory());
		 result.put("checkResult", elevaltorInfoVO.getCheckResult());
		 result.put("checkReportNum", elevaltorInfoVO.getCheckReportNum());
		 result.put("inspectDate", elevaltorInfoVO.getInspectDate());
		 result.put("registCode2", elevaltorInfoVO.getRegistCode2());
		 result.put("registor", elevaltorInfoVO.getRegistor());
		 result.put("note", elevaltorInfoVO.getNote());
		 result.put("mContractVdate", elevaltorInfoVO.getmContractVdate());
		 result.put("handleCompany", elevaltorInfoVO.getHandleCompany());
		 result.put("handleCompanyCode", elevaltorInfoVO.getHandleCompanyCode());
		 result.put("changeWay", elevaltorInfoVO.getChangeWay());
		 result.put("deviceId2", elevaltorInfoVO.getDeviceId2());
		 
		 }
		 return new JsonView(result);
	 }
	 
	 //��������˫����¼
	 public View ddedit2(String id)throws Exception{ 
		 String cityName = GlobalFunction.cityName;
		 System.out.println("edit---registNumber:"+id);
		 String sql ="";
		 if("1".equals(cityName)){
		sql ="select t.neleType,t.shibieCode,isnull(t.registNumber,'') as registNumber,isnull(t.address,'') as address,isnull(t.name,'') as name,t.eleType,isnull(t.registCode,'') as registCode,isnull(t.inoutDoor,'') as inoutDoor,isnull(t.propertyRightsUnit,'') as propertyRightsUnit,isnull(t.zzCompanyId,0) as zzCompanyId,isnull(t.eleMode,'') as eleMode,isnull(t.wgCompanyId,0) as wgCompanyId, isnull(t.jyCompanyId,0) as jyCompanyId, isnull(t.useNumber,'') as useNumber,isnull(t.safetyManDepart,'') as safetyManDepart,isnull(t.safetyManPerson,'') as safetyManPerson,isnull(t.safetyManPersonTel,'') as safetyManPersonTel,isnull(t.inspector,'') as inspector,isnull(t.nextInspectDate,'') as nextInspectDate,isnull(t.completeAcceptanceDate,'') as completeAcceptanceDate,isnull(t.acceptanceDateDepart,'') as acceptanceDateDepart,isnull(t.acceptanceReportNum,'') as acceptanceReportNum,isnull(t.azCompanyId,0) as azCompanyId,isnull(t.ywCompanyId,0) as ywCompanyId,isnull(t.zjCompanyId,0) as zjCompanyId, isnull(t.townshipStreets,0) as townshipStreets,isnull(t.eleLoad,'') as eleLoad,isnull(t.speed,'') as speed,isnull(t.eleheight,'') as eleheight,isnull(t.elewidth,'') as elewidth,isnull(t.eleStop,'') as eleStop,isnull(t.mobileCode,'') as mobileCode,isnull(t.area,'') as area,isnull(t.buildingName,'') as buildingName,isnull(t.building,'') as building,isnull(t.unit,'') as unit,isnull(t.coordinates,'') as coordinates,t.map_X,t.map_Y,isnull(t.manufactDate,'') as manufactDate,isnull(t.factoryNum,'') as factoryNum,isnull(t.useDate,'') as useDate,isnull(t.checkCategory,'') as checkCategory,isnull(t.checkResult,'') as checkResult,isnull(t.checkReportNum,'') as checkReportNum,isnull(t.inspectDate,'') as inspectDate,isnull(t.registCode2,'') as registCode2,isnull(t.registor,'') as registor,isnull(t.note,'') as note,isnull(t.mContractVdate,'') as mContractVdate,isnull(t.handleCompany,'') as handleCompany,isnull(t.handleCompanyCode,'') as handleCompanyCode,isnull(t.changeWay,'') as changeWay,isnull(tc.deviceId,'') as deviceId,isnull(t.deviceId2,'') as deviceId2  from TwoCodeDdElevatorInfo t left join TwoCodeDeviceRelationInfo tc on t.registNumber = tc.registNumber where t.id = ?";	 
		 }
		 else{
		  sql ="select isnull(t.registNumber,'') as registNumber,isnull(t.address,'') as address,isnull(t.name,'') as name,t.eleType,isnull(t.registCode,'') as registCode,isnull(t.inoutDoor,'') as inoutDoor,isnull(t.propertyRightsUnit,'') as propertyRightsUnit,isnull(t.zzCompanyId,0) as zzCompanyId,isnull(t.eleMode,'') as eleMode,isnull(t.wgCompanyId,0) as wgCompanyId, isnull(t.jyCompanyId,0) as jyCompanyId, isnull(t.useNumber,'') as useNumber,isnull(t.safetyManDepart,'') as safetyManDepart,isnull(t.safetyManPerson,'') as safetyManPerson,isnull(t.safetyManPersonTel,'') as safetyManPersonTel,isnull(t.inspector,'') as inspector,isnull(t.nextInspectDate,'') as nextInspectDate,isnull(t.completeAcceptanceDate,'') as completeAcceptanceDate,isnull(t.acceptanceDateDepart,'') as acceptanceDateDepart,isnull(t.acceptanceReportNum,'') as acceptanceReportNum,isnull(t.azCompanyId,0) as azCompanyId,isnull(t.ywCompanyId,0) as ywCompanyId,isnull(t.zjCompanyId,0) as zjCompanyId, isnull(t.townshipStreets,0) as townshipStreets,isnull(t.eleLoad,'') as eleLoad,isnull(t.speed,'') as speed,isnull(t.eleheight,'') as eleheight,isnull(t.elewidth,'') as elewidth,isnull(t.eleStop,'') as eleStop,isnull(t.mobileCode,'') as mobileCode,isnull(t.area,'') as area,isnull(t.buildingName,'') as buildingName,isnull(t.building,'') as building,isnull(t.unit,'') as unit,isnull(t.coordinates,'') as coordinates,t.map_X,t.map_Y,isnull(t.manufactDate,'') as manufactDate,isnull(t.factoryNum,'') as factoryNum,isnull(t.useDate,'') as useDate,isnull(t.checkCategory,'') as checkCategory,isnull(t.checkResult,'') as checkResult,isnull(t.checkReportNum,'') as checkReportNum,isnull(t.inspectDate,'') as inspectDate,isnull(t.registCode2,'') as registCode2,isnull(t.registor,'') as registor,isnull(t.note,'') as note,isnull(t.mContractVdate,'') as mContractVdate,isnull(t.handleCompany,'') as handleCompany,isnull(t.handleCompanyCode,'') as handleCompanyCode,isnull(t.changeWay,'') as changeWay,isnull(tc.deviceId,'') as deviceId,isnull(t.deviceId2,'') as deviceId2  from TwoCodeDdElevatorInfo t left join TwoCodeDeviceRelationInfo tc on t.registNumber = tc.registNumber where t.id = ?";
		 }
		 DdElevaltorInfoVO elevaltorInfoVO =DdElevaltorInfoVO.findFirstBySql(DdElevaltorInfoVO.class, sql, new Object[] { id });
		 Map<String, Object> result = new HashMap<String, Object>();
		 if(elevaltorInfoVO !=null){
		 result.put("registNumber", elevaltorInfoVO.getRegistNumber());
		 if(!"0".equals(cityName)){
			 result.put("shibieCode", elevaltorInfoVO.getShibieCode());
			 result.put("neleType", elevaltorInfoVO.getNeleType());
			 }
		 result.put("address", elevaltorInfoVO.getAddress());
		 result.put("name", elevaltorInfoVO.getName());
		 result.put("eleType", elevaltorInfoVO.getEleType());
		 result.put("registCode", elevaltorInfoVO.getRegistCode());
		 result.put("inoutDoor", elevaltorInfoVO.getInoutDoor());
		 result.put("propertyRightsUnit", elevaltorInfoVO.getPropertyRightsUnit());
		 result.put("zzCompanyId", elevaltorInfoVO.getZzCompanyId());
		 result.put("eleMode", elevaltorInfoVO.getEleMode());
		 result.put("wgCompanyId", elevaltorInfoVO.getWgCompanyId());
		 result.put("useNumber", elevaltorInfoVO.getUseNumber());
		 result.put("safetyManDepart", elevaltorInfoVO.getSafetyManDepart());
		 result.put("safetyManPerson", elevaltorInfoVO.getSafetyManPerson());
		 result.put("safetyManPersonTel", elevaltorInfoVO.getSafetyManPersonTel());
		 result.put("jyCompanyId", elevaltorInfoVO.getJyCompanyId());
		 result.put("inspector", elevaltorInfoVO.getInspector());
		 result.put("nextInspectDate", elevaltorInfoVO.getNextInspectDate());
		 result.put("completeAcceptanceDate", elevaltorInfoVO.getCompleteAcceptanceDate());
		 result.put("acceptanceDateDepart", elevaltorInfoVO.getAcceptanceDateDepart());
		 result.put("acceptanceReportNum", elevaltorInfoVO.getAcceptanceReportNum());
		 result.put("azCompanyId", elevaltorInfoVO.getAzCompanyId());
		 result.put("ywCompanyId", elevaltorInfoVO.getYwCompanyId());
		 result.put("zjCompanyId", elevaltorInfoVO.getZjCompanyId());
		 result.put("eleLoad", elevaltorInfoVO.getEleLoad());
		 result.put("speed", elevaltorInfoVO.getSpeed());
		 result.put("eleheight", elevaltorInfoVO.getEleheight());
		 result.put("elewidth", elevaltorInfoVO.getElewidth());
		 result.put("eleStop", elevaltorInfoVO.getEleStop());
	//	 result.put("mobileCode", elevaltorInfoVO.getMobileCode());
		 result.put("deviceId", elevaltorInfoVO.getDeviceId());
		 
		 result.put("townshipStreets", elevaltorInfoVO.getTownshipStreets());
		 result.put("area", elevaltorInfoVO.getArea());
		 result.put("buildingName", elevaltorInfoVO.getBuildingName());
		 result.put("building", elevaltorInfoVO.getBuilding());
		 result.put("unit", elevaltorInfoVO.getUnit());
	//	 result.put("coordinates", elevaltorInfoVO.getCoordinates());
		 result.put("map_X", elevaltorInfoVO.getMap_X());
		 result.put("map_Y", elevaltorInfoVO.getMap_Y());
		 result.put("manufactDate", elevaltorInfoVO.getManufactDate());
		 result.put("factoryNum", elevaltorInfoVO.getFactoryNum());
		 result.put("useDate", elevaltorInfoVO.getUseDate());
		 result.put("checkCategory", elevaltorInfoVO.getCheckCategory());
		 result.put("checkResult", elevaltorInfoVO.getCheckResult());
		 result.put("checkReportNum", elevaltorInfoVO.getCheckReportNum());
		 result.put("inspectDate", elevaltorInfoVO.getInspectDate());
		 result.put("registCode2", elevaltorInfoVO.getRegistCode2());
		 result.put("registor", elevaltorInfoVO.getRegistor());
		 result.put("note", elevaltorInfoVO.getNote());
		 result.put("mContractVdate", elevaltorInfoVO.getmContractVdate());
		 result.put("handleCompany", elevaltorInfoVO.getHandleCompany());
		 result.put("handleCompanyCode", elevaltorInfoVO.getHandleCompanyCode());
		 result.put("changeWay", elevaltorInfoVO.getChangeWay());
		 result.put("deviceId2", elevaltorInfoVO.getDeviceId2());
		 }
		 return new JsonView(result);
	 }
	 
	 
	 
	 //���������޸ĺ���¹���
	 public String ddupdate(DdElevaltorInfo elevaltorInfo){ 
		 String cityName = GlobalFunction.cityName;
		 /*
		 if("".equals(elevaltorInfo.getTownshipStreets2()) || elevaltorInfo.getTownshipStreets2() == null)
			 elevaltorInfo.setTownshipStreets(0);
		 else
			 elevaltorInfo.setTownshipStreets(Integer.valueOf(elevaltorInfo.getTownshipStreets2()).intValue());
		 
		 if("".equals(elevaltorInfo.getWgCompanyId2()) || elevaltorInfo.getWgCompanyId2() == null )
			 elevaltorInfo.setWgCompanyId(0);
		 else
			 elevaltorInfo.setWgCompanyId(Integer.valueOf(elevaltorInfo.getWgCompanyId2()).intValue());
		 
		 if("".equals(elevaltorInfo.getYwCompanyId2()) || elevaltorInfo.getYwCompanyId2() == null)
			 elevaltorInfo.setYwCompanyId(0);
		 else
			 elevaltorInfo.setYwCompanyId(Integer.valueOf(elevaltorInfo.getYwCompanyId2()).intValue());
		 
		 if("".equals(elevaltorInfo.getZzCompanyId2()) || elevaltorInfo.getZjCompanyId2() == null)
			 elevaltorInfo.setZzCompanyId(0);
		 else
			 elevaltorInfo.setZzCompanyId(Integer.valueOf(elevaltorInfo.getZzCompanyId2()).intValue());
		 
		 if("".equals(elevaltorInfo.getAzCompanyId2()) || elevaltorInfo.getAzCompanyId2() == null)
			 elevaltorInfo.setAzCompanyId(0);
		 else
			 elevaltorInfo.setAzCompanyId(Integer.valueOf(elevaltorInfo.getAzCompanyId2()).intValue());
		 
		 if("".equals(elevaltorInfo.getJyCompanyId2()) || elevaltorInfo.getJyCompanyId2() == null)
			 elevaltorInfo.setJyCompanyId(0);
		 else
			 elevaltorInfo.setJyCompanyId(Integer.valueOf(elevaltorInfo.getJyCompanyId2()).intValue());
		 
		 if("".equals(elevaltorInfo.getZjCompanyId2()) || elevaltorInfo.getZjCompanyId2() == null)
			 elevaltorInfo.setZjCompanyId(0);
		 else
			 elevaltorInfo.setZjCompanyId(Integer.valueOf(elevaltorInfo.getZjCompanyId2()).intValue());
		*/
		 
		 if("".equals(elevaltorInfo.getMap_X()) || elevaltorInfo.getMap_X() == null )
				elevaltorInfo.setMap_X("0.000000");
		 else
			 elevaltorInfo.setMap_X(elevaltorInfo.getMap_X());
		if("".equals(elevaltorInfo.getMap_Y()) || elevaltorInfo.getMap_Y() == null)
				elevaltorInfo.setMap_Y("0.000000");
		else
			elevaltorInfo.setMap_Y(elevaltorInfo.getMap_Y());
		 
		 String result = "failure";
		 int num = 0;
	//	 boolean num =false;
		 String deviceId ="";
		 deviceId =elevaltorInfo.getDeviceId();
		 
		//�ж��ܷ�����������������Ϊ�գ�����0
		 if(ruKuisvalid(elevaltorInfo))
			 elevaltorInfo.setRuKuValid(1);
		 else
			 elevaltorInfo.setRuKuValid(0);
		 
   //�����ϵ
	//	 boolean changeFlag =false;
	//	 String oldywCompanyName ="";
	//	 String ywCompanyName ="";
	//	 String odlwgCompanyName ="";
	//	 String wgCompanyName ="";
	//	 String str ="";
		 
	//	 DdElevaltorInfoVO oldElevaltorInfoVO =null;
		 
		 try {
		       
		        /* ������������Ҫ���������ϵ����Ϊ��û����ʽʹ�ã���������޸�
		        //��0����ά�����ϵ
		        oldElevaltorInfoVO =DdElevaltorInfoVO.findFirstBySql(DdElevaltorInfoVO.class, "select t.ywCompanyId,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName from TwoCodeElevatorInfo t where t.registNumber=?", new Object[] { elevaltorInfo.getRegistNumber()});  
		        if(oldElevaltorInfoVO.getYwCompanyId() != elevaltorInfo.getYwCompanyId()){ //��ά��˾ID����˵����ά��˾�б䶯
		           oldywCompanyName=oldElevaltorInfoVO.getYwCompanyName();
		           CompanyInfo newywCompanyInfo = CompanyInfo.findFirst(CompanyInfo.class, "id=?", new Object[]{elevaltorInfo.getYwCompanyId()});
		           ywCompanyName = newywCompanyInfo.getCompanyName();
		           str ="ά����λ���:"+oldywCompanyName+"--->"+ywCompanyName+";";
		           changeFlag = true;
		       
		        }
		        //��0����ܱ����ϵ
		        if(oldElevaltorInfoVO.getWgCompanyId() != elevaltorInfo.getWgCompanyId()){//��ܹ�˾ID����˵����ܹ�˾�б䶯
		        	odlwgCompanyName =oldElevaltorInfoVO.getWgCompanyName();
		        	CompanyInfo newwgCompanyInfo = CompanyInfo.findFirst(CompanyInfo.class, "id=?", new Object[]{elevaltorInfo.getWgCompanyId()});
		        	wgCompanyName = newwgCompanyInfo.getCompanyName();
		        	str=str+"ʹ�õ�λ���:"+odlwgCompanyName+"--->"+wgCompanyName+";";
		        	 changeFlag = true;
		        }
		        if(changeFlag){ //��������Ҫ��¼�ı��
		        UserInfo user=(UserInfo)session.getAttribute("sessionAccount");
		        if(user!=null)
		        System.out.println("������Ա��"+user.getLoginName());
		        System.out.println(str.substring(0, str.length()-1));
		        EleChangeInfo eleChangeInfo = new EleChangeInfo();
		        eleChangeInfo.setRegistNumber(elevaltorInfo.getRegistNumber());
		        eleChangeInfo.setChangeItem(str.substring(0, str.length()-1));
		        eleChangeInfo.setOperator(user.getLoginName());
		        eleChangeInfo.setHandleCompany(elevaltorInfo.getHandleCompany());
		        eleChangeInfo.setHandleCompanyCode(elevaltorInfo.getHandleCompanyCode());
		        eleChangeInfo.setChangeWay(elevaltorInfo.getChangeWay());
		        java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String changeTime = format2.format(new Date()); 
				eleChangeInfo.setChangeTime(changeTime);
				eleChangeInfo.save();   
		        }
		        */
		        //��һ������������ά���������ά�븽�ӹ�ϵ��  
		        String registNumber = "";
		        int recordSate =0;
		        String picregistNumber="";
		        DdElevaltorInfo oldDdElevaltorInfo = DdElevaltorInfo.findFirst(DdElevaltorInfo.class,"id =?",new Object[] { elevaltorInfo.getId()});
		        if(oldDdElevaltorInfo != null){
		        	registNumber=oldDdElevaltorInfo.getRegistNumber(); 
		        	recordSate = oldDdElevaltorInfo.getRecordSate();
		        	picregistNumber=oldDdElevaltorInfo.getPicregistNumber();
		        if(!"".equals(elevaltorInfo.getRegistNumber()) && elevaltorInfo.getRegistNumber() != null){
		        DdElevaltorInfo oldDdElevaltorInfo2 = DdElevaltorInfo.findFirst(DdElevaltorInfo.class,"registNumber = ? and id !=?",new Object[] { elevaltorInfo.getRegistNumber(),elevaltorInfo.getId()});
		        if(oldDdElevaltorInfo2 != null){
		        	result = "exist"; 
		        	return result;   
		        	}
		        }
		        DdAppendixEleInfo ddAppendixEleInfo  = DdAppendixEleInfo.findFirst(DdAppendixEleInfo.class, "registNumber =?", new Object[] { registNumber});
		        if(ddAppendixEleInfo != null){  //���ӹ�ϵ�����и��Ӽ�¼
		        	DdAppendixEleInfo.updateAll(DdAppendixEleInfo.class, "registNumber = ?", new Object[] { elevaltorInfo.getRegistNumber()}, "registNumber =?", new Object[] { registNumber});
		        }
		       }
		        /* ��¼ϵͳ��ǩ��������ά��
		        //�ڶ����ȴ����豸���ά�������ϵ
				TwoCodeDeviceRelationInfo twoCodeDeviceRelationInfo = TwoCodeDeviceRelationInfo.findFirst(TwoCodeDeviceRelationInfo.class, "registNumber =?", new Object[] { elevaltorInfo.getRegistNumber()});
				if(twoCodeDeviceRelationInfo == null){  //û�й�ϵ���ڣ������µĹ�ϵ
					if(!"".equals(deviceId) && deviceId.length()==16){
					twoCodeDeviceRelationInfo = new TwoCodeDeviceRelationInfo();
					twoCodeDeviceRelationInfo.setRegistNumber(elevaltorInfo.getRegistNumber());
					twoCodeDeviceRelationInfo.setDeviceId(deviceId);
					twoCodeDeviceRelationInfo.save();}
				}else{       //�й�ϵ���ڣ��������µĹ�ϵ������ԭ���Ĺ�ϵ
					String oldDeviceId = twoCodeDeviceRelationInfo.getDeviceId();
					if(!deviceId.equals(oldDeviceId)){
					TwoCodeDeviceRelationInfo.updateAll(TwoCodeDeviceRelationInfo.class, "deviceId = ?", new Object[] { deviceId }, "registNumber =?", new Object[] { elevaltorInfo.getRegistNumber()});	
					}
				}
				*/
		    //���������¶�ά���ǩ������Ϣ��
			//Ҫ������±�ǩ��Ϣ����updateTime�ֶΣ�С���Ǳ�Ҫ��
		    try {
				ActiveRecordBase.beginTransaction();
				java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String s = format1.format(new Date()); 
				 System.out.println("s"+s);
		//		if(changeFlag){
		//		num = DdElevaltorInfo.updateAll(DdElevaltorInfo.class, "address=?,name=?,eleType=?,registCode=?,inoutDoor=?,propertyRightsUnit=?,zzCompanyId=?,eleMode=?,wgCompanyId=?,useNumber=?,safetyManDepart=?,safetyManPerson=?,safetyManPersonTel=?,jyCompanyId=?,inspector=?,nextInspectDate=?,completeAcceptanceDate=?,acceptanceDateDepart=?,acceptanceReportNum=?,azCompanyId=?,ywCompanyId=?,zjCompanyId=?,eleLoad=?,speed=?,eleheight=?,elewidth=?,eleStop=?,area=?,townshipStreets=?,buildingName=?,building=?,unit=?,map_X=?,map_Y=?,manufactDate=?,factoryNum=?,useDate=?,checkCategory=?,checkResult=?,checkReportNum=?,inspectDate=?,registCode2=?,registor=?,note=?,mContractVdate=?,handleCompany=?,handleCompanyCode=?,changeWay=?,updateTime=?,ischangInfo=?", new Object[]{elevaltorInfo.getAddress(),elevaltorInfo.getName(),elevaltorInfo.getEleType(),elevaltorInfo.getRegistCode(),elevaltorInfo.getInoutDoor(),elevaltorInfo.getPropertyRightsUnit(),elevaltorInfo.getZzCompanyId(),elevaltorInfo.getEleMode(),elevaltorInfo.getWgCompanyId(),elevaltorInfo.getUseNumber(),elevaltorInfo.getSafetyManDepart(),elevaltorInfo.getSafetyManPerson(),elevaltorInfo.getSafetyManPersonTel(),elevaltorInfo.getJyCompanyId(),elevaltorInfo.getInspector(),elevaltorInfo.getNextInspectDate(),elevaltorInfo.getCompleteAcceptanceDate(),elevaltorInfo.getAcceptanceDateDepart(),elevaltorInfo.getAcceptanceReportNum(),elevaltorInfo.getAzCompanyId(),elevaltorInfo.getYwCompanyId(),elevaltorInfo.getZjCompanyId(),elevaltorInfo.getEleLoad(),elevaltorInfo.getSpeed(),elevaltorInfo.getEleheight(),elevaltorInfo.getElewidth(),elevaltorInfo.getEleStop(),elevaltorInfo.getArea(),elevaltorInfo.getTownshipStreets(),elevaltorInfo.getBuildingName(),elevaltorInfo.getBuilding(),elevaltorInfo.getUnit(),elevaltorInfo.getMap_X(),elevaltorInfo.getMap_Y(),elevaltorInfo.getManufactDate(),elevaltorInfo.getFactoryNum(),elevaltorInfo.getUseDate(),elevaltorInfo.getCheckCategory(),elevaltorInfo.getCheckResult(),elevaltorInfo.getCheckReportNum(),elevaltorInfo.getInspectDate(),elevaltorInfo.getRegistCode2(),elevaltorInfo.getRegistor(),elevaltorInfo.getNote(),elevaltorInfo.getmContractVdate(),elevaltorInfo.getHandleCompany(),elevaltorInfo.getHandleCompanyCode(),elevaltorInfo.getChangeWay(),s,1}, "id=?", new Object[] { elevaltorInfo.getId()});
		//		}else{
				 System.out.println("recordSate:"+recordSate);
				if(recordSate==0){
				    if(!elevaltorInfo.getRegistNumber().equals(registNumber)){
				     if(picregistNumber ==null || picregistNumber.length() != 6){
				    	 if(!"0".equals(cityName)){
				     num = DdElevaltorInfo.updateAll(DdElevaltorInfo.class, "registNumber=?,address=?,name=?,eleType=?,registCode=?,inoutDoor=?,propertyRightsUnit=?,zzCompanyId=?,eleMode=?,wgCompanyId=?,useNumber=?,safetyManDepart=?,safetyManPerson=?,safetyManPersonTel=?,jyCompanyId=?,inspector=?,nextInspectDate=?,completeAcceptanceDate=?,acceptanceDateDepart=?,acceptanceReportNum=?,azCompanyId=?,ywCompanyId=?,zjCompanyId=?,eleLoad=?,speed=?,eleheight=?,elewidth=?,eleStop=?,area=?,townshipStreets=?,buildingName=?,building=?,unit=?,map_X=?,map_Y=?,manufactDate=?,factoryNum=?,useDate=?,checkCategory=?,checkResult=?,checkReportNum=?,inspectDate=?,registCode2=?,registor=?,note=?,mContractVdate=?,handleCompany=?,handleCompanyCode=?,changeWay=?,updateTime=?,ruKuValid=?,deviceId2=?,picregistNumber=?,shibieCode=?,neleType=?", new Object[]{elevaltorInfo.getRegistNumber(),elevaltorInfo.getAddress(),elevaltorInfo.getName(),elevaltorInfo.getEleType(),elevaltorInfo.getRegistCode(),elevaltorInfo.getInoutDoor(),elevaltorInfo.getPropertyRightsUnit(),elevaltorInfo.getZzCompanyId(),elevaltorInfo.getEleMode(),elevaltorInfo.getWgCompanyId(),elevaltorInfo.getUseNumber(),elevaltorInfo.getSafetyManDepart(),elevaltorInfo.getSafetyManPerson(),elevaltorInfo.getSafetyManPersonTel(),elevaltorInfo.getJyCompanyId(),elevaltorInfo.getInspector(),elevaltorInfo.getNextInspectDate(),elevaltorInfo.getCompleteAcceptanceDate(),elevaltorInfo.getAcceptanceDateDepart(),elevaltorInfo.getAcceptanceReportNum(),elevaltorInfo.getAzCompanyId(),elevaltorInfo.getYwCompanyId(),elevaltorInfo.getZjCompanyId(),elevaltorInfo.getEleLoad(),elevaltorInfo.getSpeed(),elevaltorInfo.getEleheight(),elevaltorInfo.getElewidth(),elevaltorInfo.getEleStop(),elevaltorInfo.getArea(),elevaltorInfo.getTownshipStreets(),elevaltorInfo.getBuildingName(),elevaltorInfo.getBuilding(),elevaltorInfo.getUnit(),elevaltorInfo.getMap_X(),elevaltorInfo.getMap_Y(),elevaltorInfo.getManufactDate(),elevaltorInfo.getFactoryNum(),elevaltorInfo.getUseDate(),elevaltorInfo.getCheckCategory(),elevaltorInfo.getCheckResult(),elevaltorInfo.getCheckReportNum(),elevaltorInfo.getInspectDate(),elevaltorInfo.getRegistCode2(),elevaltorInfo.getRegistor(),elevaltorInfo.getNote(),elevaltorInfo.getmContractVdate(),elevaltorInfo.getHandleCompany(),elevaltorInfo.getHandleCompanyCode(),elevaltorInfo.getChangeWay(),s,elevaltorInfo.getRuKuValid(),elevaltorInfo.getDeviceId2(),registNumber,elevaltorInfo.getShibieCode(),elevaltorInfo.getNeleType()}, "id=?", new Object[] { elevaltorInfo.getId()});	  		 
				    	 }
				    	 else{
				   	 num = DdElevaltorInfo.updateAll(DdElevaltorInfo.class, "registNumber=?,address=?,name=?,eleType=?,registCode=?,inoutDoor=?,propertyRightsUnit=?,zzCompanyId=?,eleMode=?,wgCompanyId=?,useNumber=?,safetyManDepart=?,safetyManPerson=?,safetyManPersonTel=?,jyCompanyId=?,inspector=?,nextInspectDate=?,completeAcceptanceDate=?,acceptanceDateDepart=?,acceptanceReportNum=?,azCompanyId=?,ywCompanyId=?,zjCompanyId=?,eleLoad=?,speed=?,eleheight=?,elewidth=?,eleStop=?,area=?,townshipStreets=?,buildingName=?,building=?,unit=?,map_X=?,map_Y=?,manufactDate=?,factoryNum=?,useDate=?,checkCategory=?,checkResult=?,checkReportNum=?,inspectDate=?,registCode2=?,registor=?,note=?,mContractVdate=?,handleCompany=?,handleCompanyCode=?,changeWay=?,updateTime=?,ruKuValid=?,deviceId2=?,picregistNumber=?", new Object[]{elevaltorInfo.getRegistNumber(),elevaltorInfo.getAddress(),elevaltorInfo.getName(),elevaltorInfo.getEleType(),elevaltorInfo.getRegistCode(),elevaltorInfo.getInoutDoor(),elevaltorInfo.getPropertyRightsUnit(),elevaltorInfo.getZzCompanyId(),elevaltorInfo.getEleMode(),elevaltorInfo.getWgCompanyId(),elevaltorInfo.getUseNumber(),elevaltorInfo.getSafetyManDepart(),elevaltorInfo.getSafetyManPerson(),elevaltorInfo.getSafetyManPersonTel(),elevaltorInfo.getJyCompanyId(),elevaltorInfo.getInspector(),elevaltorInfo.getNextInspectDate(),elevaltorInfo.getCompleteAcceptanceDate(),elevaltorInfo.getAcceptanceDateDepart(),elevaltorInfo.getAcceptanceReportNum(),elevaltorInfo.getAzCompanyId(),elevaltorInfo.getYwCompanyId(),elevaltorInfo.getZjCompanyId(),elevaltorInfo.getEleLoad(),elevaltorInfo.getSpeed(),elevaltorInfo.getEleheight(),elevaltorInfo.getElewidth(),elevaltorInfo.getEleStop(),elevaltorInfo.getArea(),elevaltorInfo.getTownshipStreets(),elevaltorInfo.getBuildingName(),elevaltorInfo.getBuilding(),elevaltorInfo.getUnit(),elevaltorInfo.getMap_X(),elevaltorInfo.getMap_Y(),elevaltorInfo.getManufactDate(),elevaltorInfo.getFactoryNum(),elevaltorInfo.getUseDate(),elevaltorInfo.getCheckCategory(),elevaltorInfo.getCheckResult(),elevaltorInfo.getCheckReportNum(),elevaltorInfo.getInspectDate(),elevaltorInfo.getRegistCode2(),elevaltorInfo.getRegistor(),elevaltorInfo.getNote(),elevaltorInfo.getmContractVdate(),elevaltorInfo.getHandleCompany(),elevaltorInfo.getHandleCompanyCode(),elevaltorInfo.getChangeWay(),s,elevaltorInfo.getRuKuValid(),elevaltorInfo.getDeviceId2(),registNumber}, "id=?", new Object[] { elevaltorInfo.getId()});	  	
				    	 }
				    	 }
				   	 else{
				   		 if(!"0".equals(cityName)){
				   	 num = DdElevaltorInfo.updateAll(DdElevaltorInfo.class, "registNumber=?,address=?,name=?,eleType=?,registCode=?,inoutDoor=?,propertyRightsUnit=?,zzCompanyId=?,eleMode=?,wgCompanyId=?,useNumber=?,safetyManDepart=?,safetyManPerson=?,safetyManPersonTel=?,jyCompanyId=?,inspector=?,nextInspectDate=?,completeAcceptanceDate=?,acceptanceDateDepart=?,acceptanceReportNum=?,azCompanyId=?,ywCompanyId=?,zjCompanyId=?,eleLoad=?,speed=?,eleheight=?,elewidth=?,eleStop=?,area=?,townshipStreets=?,buildingName=?,building=?,unit=?,map_X=?,map_Y=?,manufactDate=?,factoryNum=?,useDate=?,checkCategory=?,checkResult=?,checkReportNum=?,inspectDate=?,registCode2=?,registor=?,note=?,mContractVdate=?,handleCompany=?,handleCompanyCode=?,changeWay=?,updateTime=?,ruKuValid=?,deviceId2=?,shibieCode=?,neleType=?", new Object[]{elevaltorInfo.getRegistNumber(),elevaltorInfo.getAddress(),elevaltorInfo.getName(),elevaltorInfo.getEleType(),elevaltorInfo.getRegistCode(),elevaltorInfo.getInoutDoor(),elevaltorInfo.getPropertyRightsUnit(),elevaltorInfo.getZzCompanyId(),elevaltorInfo.getEleMode(),elevaltorInfo.getWgCompanyId(),elevaltorInfo.getUseNumber(),elevaltorInfo.getSafetyManDepart(),elevaltorInfo.getSafetyManPerson(),elevaltorInfo.getSafetyManPersonTel(),elevaltorInfo.getJyCompanyId(),elevaltorInfo.getInspector(),elevaltorInfo.getNextInspectDate(),elevaltorInfo.getCompleteAcceptanceDate(),elevaltorInfo.getAcceptanceDateDepart(),elevaltorInfo.getAcceptanceReportNum(),elevaltorInfo.getAzCompanyId(),elevaltorInfo.getYwCompanyId(),elevaltorInfo.getZjCompanyId(),elevaltorInfo.getEleLoad(),elevaltorInfo.getSpeed(),elevaltorInfo.getEleheight(),elevaltorInfo.getElewidth(),elevaltorInfo.getEleStop(),elevaltorInfo.getArea(),elevaltorInfo.getTownshipStreets(),elevaltorInfo.getBuildingName(),elevaltorInfo.getBuilding(),elevaltorInfo.getUnit(),elevaltorInfo.getMap_X(),elevaltorInfo.getMap_Y(),elevaltorInfo.getManufactDate(),elevaltorInfo.getFactoryNum(),elevaltorInfo.getUseDate(),elevaltorInfo.getCheckCategory(),elevaltorInfo.getCheckResult(),elevaltorInfo.getCheckReportNum(),elevaltorInfo.getInspectDate(),elevaltorInfo.getRegistCode2(),elevaltorInfo.getRegistor(),elevaltorInfo.getNote(),elevaltorInfo.getmContractVdate(),elevaltorInfo.getHandleCompany(),elevaltorInfo.getHandleCompanyCode(),elevaltorInfo.getChangeWay(),s,elevaltorInfo.getRuKuValid(),elevaltorInfo.getDeviceId2(),elevaltorInfo.getShibieCode(),elevaltorInfo.getNeleType()}, "id=?", new Object[] { elevaltorInfo.getId()});		 
				   		 }
				   		 else{
				     num = DdElevaltorInfo.updateAll(DdElevaltorInfo.class, "registNumber=?,address=?,name=?,eleType=?,registCode=?,inoutDoor=?,propertyRightsUnit=?,zzCompanyId=?,eleMode=?,wgCompanyId=?,useNumber=?,safetyManDepart=?,safetyManPerson=?,safetyManPersonTel=?,jyCompanyId=?,inspector=?,nextInspectDate=?,completeAcceptanceDate=?,acceptanceDateDepart=?,acceptanceReportNum=?,azCompanyId=?,ywCompanyId=?,zjCompanyId=?,eleLoad=?,speed=?,eleheight=?,elewidth=?,eleStop=?,area=?,townshipStreets=?,buildingName=?,building=?,unit=?,map_X=?,map_Y=?,manufactDate=?,factoryNum=?,useDate=?,checkCategory=?,checkResult=?,checkReportNum=?,inspectDate=?,registCode2=?,registor=?,note=?,mContractVdate=?,handleCompany=?,handleCompanyCode=?,changeWay=?,updateTime=?,ruKuValid=?,deviceId2=?", new Object[]{elevaltorInfo.getRegistNumber(),elevaltorInfo.getAddress(),elevaltorInfo.getName(),elevaltorInfo.getEleType(),elevaltorInfo.getRegistCode(),elevaltorInfo.getInoutDoor(),elevaltorInfo.getPropertyRightsUnit(),elevaltorInfo.getZzCompanyId(),elevaltorInfo.getEleMode(),elevaltorInfo.getWgCompanyId(),elevaltorInfo.getUseNumber(),elevaltorInfo.getSafetyManDepart(),elevaltorInfo.getSafetyManPerson(),elevaltorInfo.getSafetyManPersonTel(),elevaltorInfo.getJyCompanyId(),elevaltorInfo.getInspector(),elevaltorInfo.getNextInspectDate(),elevaltorInfo.getCompleteAcceptanceDate(),elevaltorInfo.getAcceptanceDateDepart(),elevaltorInfo.getAcceptanceReportNum(),elevaltorInfo.getAzCompanyId(),elevaltorInfo.getYwCompanyId(),elevaltorInfo.getZjCompanyId(),elevaltorInfo.getEleLoad(),elevaltorInfo.getSpeed(),elevaltorInfo.getEleheight(),elevaltorInfo.getElewidth(),elevaltorInfo.getEleStop(),elevaltorInfo.getArea(),elevaltorInfo.getTownshipStreets(),elevaltorInfo.getBuildingName(),elevaltorInfo.getBuilding(),elevaltorInfo.getUnit(),elevaltorInfo.getMap_X(),elevaltorInfo.getMap_Y(),elevaltorInfo.getManufactDate(),elevaltorInfo.getFactoryNum(),elevaltorInfo.getUseDate(),elevaltorInfo.getCheckCategory(),elevaltorInfo.getCheckResult(),elevaltorInfo.getCheckReportNum(),elevaltorInfo.getInspectDate(),elevaltorInfo.getRegistCode2(),elevaltorInfo.getRegistor(),elevaltorInfo.getNote(),elevaltorInfo.getmContractVdate(),elevaltorInfo.getHandleCompany(),elevaltorInfo.getHandleCompanyCode(),elevaltorInfo.getChangeWay(),s,elevaltorInfo.getRuKuValid(),elevaltorInfo.getDeviceId2()}, "id=?", new Object[] { elevaltorInfo.getId()});	
				   		 }
				   		 }  
				     }
				    else{
				    	 if(!"0".equals(cityName)){
				    	num = DdElevaltorInfo.updateAll(DdElevaltorInfo.class, "registNumber=?,address=?,name=?,eleType=?,registCode=?,inoutDoor=?,propertyRightsUnit=?,zzCompanyId=?,eleMode=?,wgCompanyId=?,useNumber=?,safetyManDepart=?,safetyManPerson=?,safetyManPersonTel=?,jyCompanyId=?,inspector=?,nextInspectDate=?,completeAcceptanceDate=?,acceptanceDateDepart=?,acceptanceReportNum=?,azCompanyId=?,ywCompanyId=?,zjCompanyId=?,eleLoad=?,speed=?,eleheight=?,elewidth=?,eleStop=?,area=?,townshipStreets=?,buildingName=?,building=?,unit=?,map_X=?,map_Y=?,manufactDate=?,factoryNum=?,useDate=?,checkCategory=?,checkResult=?,checkReportNum=?,inspectDate=?,registCode2=?,registor=?,note=?,mContractVdate=?,handleCompany=?,handleCompanyCode=?,changeWay=?,updateTime=?,ruKuValid=?,deviceId2=?,shibieCode=?,neleType=?", new Object[]{elevaltorInfo.getRegistNumber(),elevaltorInfo.getAddress(),elevaltorInfo.getName(),elevaltorInfo.getEleType(),elevaltorInfo.getRegistCode(),elevaltorInfo.getInoutDoor(),elevaltorInfo.getPropertyRightsUnit(),elevaltorInfo.getZzCompanyId(),elevaltorInfo.getEleMode(),elevaltorInfo.getWgCompanyId(),elevaltorInfo.getUseNumber(),elevaltorInfo.getSafetyManDepart(),elevaltorInfo.getSafetyManPerson(),elevaltorInfo.getSafetyManPersonTel(),elevaltorInfo.getJyCompanyId(),elevaltorInfo.getInspector(),elevaltorInfo.getNextInspectDate(),elevaltorInfo.getCompleteAcceptanceDate(),elevaltorInfo.getAcceptanceDateDepart(),elevaltorInfo.getAcceptanceReportNum(),elevaltorInfo.getAzCompanyId(),elevaltorInfo.getYwCompanyId(),elevaltorInfo.getZjCompanyId(),elevaltorInfo.getEleLoad(),elevaltorInfo.getSpeed(),elevaltorInfo.getEleheight(),elevaltorInfo.getElewidth(),elevaltorInfo.getEleStop(),elevaltorInfo.getArea(),elevaltorInfo.getTownshipStreets(),elevaltorInfo.getBuildingName(),elevaltorInfo.getBuilding(),elevaltorInfo.getUnit(),elevaltorInfo.getMap_X(),elevaltorInfo.getMap_Y(),elevaltorInfo.getManufactDate(),elevaltorInfo.getFactoryNum(),elevaltorInfo.getUseDate(),elevaltorInfo.getCheckCategory(),elevaltorInfo.getCheckResult(),elevaltorInfo.getCheckReportNum(),elevaltorInfo.getInspectDate(),elevaltorInfo.getRegistCode2(),elevaltorInfo.getRegistor(),elevaltorInfo.getNote(),elevaltorInfo.getmContractVdate(),elevaltorInfo.getHandleCompany(),elevaltorInfo.getHandleCompanyCode(),elevaltorInfo.getChangeWay(),s,elevaltorInfo.getRuKuValid(),elevaltorInfo.getDeviceId2(),elevaltorInfo.getShibieCode(),elevaltorInfo.getNeleType()}, "id=?", new Object[] { elevaltorInfo.getId()});		 
				    	 }
				    	 else{
			    	 num = DdElevaltorInfo.updateAll(DdElevaltorInfo.class, "registNumber=?,address=?,name=?,eleType=?,registCode=?,inoutDoor=?,propertyRightsUnit=?,zzCompanyId=?,eleMode=?,wgCompanyId=?,useNumber=?,safetyManDepart=?,safetyManPerson=?,safetyManPersonTel=?,jyCompanyId=?,inspector=?,nextInspectDate=?,completeAcceptanceDate=?,acceptanceDateDepart=?,acceptanceReportNum=?,azCompanyId=?,ywCompanyId=?,zjCompanyId=?,eleLoad=?,speed=?,eleheight=?,elewidth=?,eleStop=?,area=?,townshipStreets=?,buildingName=?,building=?,unit=?,map_X=?,map_Y=?,manufactDate=?,factoryNum=?,useDate=?,checkCategory=?,checkResult=?,checkReportNum=?,inspectDate=?,registCode2=?,registor=?,note=?,mContractVdate=?,handleCompany=?,handleCompanyCode=?,changeWay=?,updateTime=?,ruKuValid=?,deviceId2=?", new Object[]{elevaltorInfo.getRegistNumber(),elevaltorInfo.getAddress(),elevaltorInfo.getName(),elevaltorInfo.getEleType(),elevaltorInfo.getRegistCode(),elevaltorInfo.getInoutDoor(),elevaltorInfo.getPropertyRightsUnit(),elevaltorInfo.getZzCompanyId(),elevaltorInfo.getEleMode(),elevaltorInfo.getWgCompanyId(),elevaltorInfo.getUseNumber(),elevaltorInfo.getSafetyManDepart(),elevaltorInfo.getSafetyManPerson(),elevaltorInfo.getSafetyManPersonTel(),elevaltorInfo.getJyCompanyId(),elevaltorInfo.getInspector(),elevaltorInfo.getNextInspectDate(),elevaltorInfo.getCompleteAcceptanceDate(),elevaltorInfo.getAcceptanceDateDepart(),elevaltorInfo.getAcceptanceReportNum(),elevaltorInfo.getAzCompanyId(),elevaltorInfo.getYwCompanyId(),elevaltorInfo.getZjCompanyId(),elevaltorInfo.getEleLoad(),elevaltorInfo.getSpeed(),elevaltorInfo.getEleheight(),elevaltorInfo.getElewidth(),elevaltorInfo.getEleStop(),elevaltorInfo.getArea(),elevaltorInfo.getTownshipStreets(),elevaltorInfo.getBuildingName(),elevaltorInfo.getBuilding(),elevaltorInfo.getUnit(),elevaltorInfo.getMap_X(),elevaltorInfo.getMap_Y(),elevaltorInfo.getManufactDate(),elevaltorInfo.getFactoryNum(),elevaltorInfo.getUseDate(),elevaltorInfo.getCheckCategory(),elevaltorInfo.getCheckResult(),elevaltorInfo.getCheckReportNum(),elevaltorInfo.getInspectDate(),elevaltorInfo.getRegistCode2(),elevaltorInfo.getRegistor(),elevaltorInfo.getNote(),elevaltorInfo.getmContractVdate(),elevaltorInfo.getHandleCompany(),elevaltorInfo.getHandleCompanyCode(),elevaltorInfo.getChangeWay(),s,elevaltorInfo.getRuKuValid(),elevaltorInfo.getDeviceId2()}, "id=?", new Object[] { elevaltorInfo.getId()});	
				    	 }
				    	 }
				 }
				if(recordSate==1 || recordSate==2){ 
					if(!elevaltorInfo.getRegistNumber().equals(registNumber)){
					if(picregistNumber ==null || picregistNumber.length() != 6){
					  if(!"0".equals(cityName)){
					  num = DdElevaltorInfo.updateAll(DdElevaltorInfo.class, "registNumber=?,address=?,name=?,eleType=?,registCode=?,inoutDoor=?,propertyRightsUnit=?,zzCompanyId=?,eleMode=?,wgCompanyId=?,useNumber=?,safetyManDepart=?,safetyManPerson=?,safetyManPersonTel=?,jyCompanyId=?,inspector=?,nextInspectDate=?,completeAcceptanceDate=?,acceptanceDateDepart=?,acceptanceReportNum=?,azCompanyId=?,ywCompanyId=?,zjCompanyId=?,eleLoad=?,speed=?,eleheight=?,elewidth=?,eleStop=?,area=?,townshipStreets=?,buildingName=?,building=?,unit=?,map_X=?,map_Y=?,manufactDate=?,factoryNum=?,useDate=?,checkCategory=?,checkResult=?,checkReportNum=?,inspectDate=?,registCode2=?,registor=?,note=?,mContractVdate=?,handleCompany=?,handleCompanyCode=?,changeWay=?,updateTime=?,ruKuValid=?,deviceId2=?,verifyPersonID=?,verifyTime=?,picregistNumber=?,shibieCode=?,neleType=?", new Object[]{elevaltorInfo.getRegistNumber(),elevaltorInfo.getAddress(),elevaltorInfo.getName(),elevaltorInfo.getEleType(),elevaltorInfo.getRegistCode(),elevaltorInfo.getInoutDoor(),elevaltorInfo.getPropertyRightsUnit(),elevaltorInfo.getZzCompanyId(),elevaltorInfo.getEleMode(),elevaltorInfo.getWgCompanyId(),elevaltorInfo.getUseNumber(),elevaltorInfo.getSafetyManDepart(),elevaltorInfo.getSafetyManPerson(),elevaltorInfo.getSafetyManPersonTel(),elevaltorInfo.getJyCompanyId(),elevaltorInfo.getInspector(),elevaltorInfo.getNextInspectDate(),elevaltorInfo.getCompleteAcceptanceDate(),elevaltorInfo.getAcceptanceDateDepart(),elevaltorInfo.getAcceptanceReportNum(),elevaltorInfo.getAzCompanyId(),elevaltorInfo.getYwCompanyId(),elevaltorInfo.getZjCompanyId(),elevaltorInfo.getEleLoad(),elevaltorInfo.getSpeed(),elevaltorInfo.getEleheight(),elevaltorInfo.getElewidth(),elevaltorInfo.getEleStop(),elevaltorInfo.getArea(),elevaltorInfo.getTownshipStreets(),elevaltorInfo.getBuildingName(),elevaltorInfo.getBuilding(),elevaltorInfo.getUnit(),elevaltorInfo.getMap_X(),elevaltorInfo.getMap_Y(),elevaltorInfo.getManufactDate(),elevaltorInfo.getFactoryNum(),elevaltorInfo.getUseDate(),elevaltorInfo.getCheckCategory(),elevaltorInfo.getCheckResult(),elevaltorInfo.getCheckReportNum(),elevaltorInfo.getInspectDate(),elevaltorInfo.getRegistCode2(),elevaltorInfo.getRegistor(),elevaltorInfo.getNote(),elevaltorInfo.getmContractVdate(),elevaltorInfo.getHandleCompany(),elevaltorInfo.getHandleCompanyCode(),elevaltorInfo.getChangeWay(),s,elevaltorInfo.getRuKuValid(),elevaltorInfo.getDeviceId2(),elevaltorInfo.getLuruPersonID(),s,registNumber,elevaltorInfo.getShibieCode(),elevaltorInfo.getNeleType()}, "id=?", new Object[] { elevaltorInfo.getId()});				
					  }
					  else{
					  num = DdElevaltorInfo.updateAll(DdElevaltorInfo.class, "registNumber=?,address=?,name=?,eleType=?,registCode=?,inoutDoor=?,propertyRightsUnit=?,zzCompanyId=?,eleMode=?,wgCompanyId=?,useNumber=?,safetyManDepart=?,safetyManPerson=?,safetyManPersonTel=?,jyCompanyId=?,inspector=?,nextInspectDate=?,completeAcceptanceDate=?,acceptanceDateDepart=?,acceptanceReportNum=?,azCompanyId=?,ywCompanyId=?,zjCompanyId=?,eleLoad=?,speed=?,eleheight=?,elewidth=?,eleStop=?,area=?,townshipStreets=?,buildingName=?,building=?,unit=?,map_X=?,map_Y=?,manufactDate=?,factoryNum=?,useDate=?,checkCategory=?,checkResult=?,checkReportNum=?,inspectDate=?,registCode2=?,registor=?,note=?,mContractVdate=?,handleCompany=?,handleCompanyCode=?,changeWay=?,updateTime=?,ruKuValid=?,deviceId2=?,verifyPersonID=?,verifyTime=?,picregistNumber=?", new Object[]{elevaltorInfo.getRegistNumber(),elevaltorInfo.getAddress(),elevaltorInfo.getName(),elevaltorInfo.getEleType(),elevaltorInfo.getRegistCode(),elevaltorInfo.getInoutDoor(),elevaltorInfo.getPropertyRightsUnit(),elevaltorInfo.getZzCompanyId(),elevaltorInfo.getEleMode(),elevaltorInfo.getWgCompanyId(),elevaltorInfo.getUseNumber(),elevaltorInfo.getSafetyManDepart(),elevaltorInfo.getSafetyManPerson(),elevaltorInfo.getSafetyManPersonTel(),elevaltorInfo.getJyCompanyId(),elevaltorInfo.getInspector(),elevaltorInfo.getNextInspectDate(),elevaltorInfo.getCompleteAcceptanceDate(),elevaltorInfo.getAcceptanceDateDepart(),elevaltorInfo.getAcceptanceReportNum(),elevaltorInfo.getAzCompanyId(),elevaltorInfo.getYwCompanyId(),elevaltorInfo.getZjCompanyId(),elevaltorInfo.getEleLoad(),elevaltorInfo.getSpeed(),elevaltorInfo.getEleheight(),elevaltorInfo.getElewidth(),elevaltorInfo.getEleStop(),elevaltorInfo.getArea(),elevaltorInfo.getTownshipStreets(),elevaltorInfo.getBuildingName(),elevaltorInfo.getBuilding(),elevaltorInfo.getUnit(),elevaltorInfo.getMap_X(),elevaltorInfo.getMap_Y(),elevaltorInfo.getManufactDate(),elevaltorInfo.getFactoryNum(),elevaltorInfo.getUseDate(),elevaltorInfo.getCheckCategory(),elevaltorInfo.getCheckResult(),elevaltorInfo.getCheckReportNum(),elevaltorInfo.getInspectDate(),elevaltorInfo.getRegistCode2(),elevaltorInfo.getRegistor(),elevaltorInfo.getNote(),elevaltorInfo.getmContractVdate(),elevaltorInfo.getHandleCompany(),elevaltorInfo.getHandleCompanyCode(),elevaltorInfo.getChangeWay(),s,elevaltorInfo.getRuKuValid(),elevaltorInfo.getDeviceId2(),elevaltorInfo.getLuruPersonID(),s,registNumber}, "id=?", new Object[] { elevaltorInfo.getId()});				
					  }
					 }
					else{
						if(!"0".equals(cityName)){
					    num = DdElevaltorInfo.updateAll(DdElevaltorInfo.class, "registNumber=?,address=?,name=?,eleType=?,registCode=?,inoutDoor=?,propertyRightsUnit=?,zzCompanyId=?,eleMode=?,wgCompanyId=?,useNumber=?,safetyManDepart=?,safetyManPerson=?,safetyManPersonTel=?,jyCompanyId=?,inspector=?,nextInspectDate=?,completeAcceptanceDate=?,acceptanceDateDepart=?,acceptanceReportNum=?,azCompanyId=?,ywCompanyId=?,zjCompanyId=?,eleLoad=?,speed=?,eleheight=?,elewidth=?,eleStop=?,area=?,townshipStreets=?,buildingName=?,building=?,unit=?,map_X=?,map_Y=?,manufactDate=?,factoryNum=?,useDate=?,checkCategory=?,checkResult=?,checkReportNum=?,inspectDate=?,registCode2=?,registor=?,note=?,mContractVdate=?,handleCompany=?,handleCompanyCode=?,changeWay=?,updateTime=?,ruKuValid=?,deviceId2=?,verifyPersonID=?,verifyTime=?,shibieCode=?,neleType=?", new Object[]{elevaltorInfo.getRegistNumber(),elevaltorInfo.getAddress(),elevaltorInfo.getName(),elevaltorInfo.getEleType(),elevaltorInfo.getRegistCode(),elevaltorInfo.getInoutDoor(),elevaltorInfo.getPropertyRightsUnit(),elevaltorInfo.getZzCompanyId(),elevaltorInfo.getEleMode(),elevaltorInfo.getWgCompanyId(),elevaltorInfo.getUseNumber(),elevaltorInfo.getSafetyManDepart(),elevaltorInfo.getSafetyManPerson(),elevaltorInfo.getSafetyManPersonTel(),elevaltorInfo.getJyCompanyId(),elevaltorInfo.getInspector(),elevaltorInfo.getNextInspectDate(),elevaltorInfo.getCompleteAcceptanceDate(),elevaltorInfo.getAcceptanceDateDepart(),elevaltorInfo.getAcceptanceReportNum(),elevaltorInfo.getAzCompanyId(),elevaltorInfo.getYwCompanyId(),elevaltorInfo.getZjCompanyId(),elevaltorInfo.getEleLoad(),elevaltorInfo.getSpeed(),elevaltorInfo.getEleheight(),elevaltorInfo.getElewidth(),elevaltorInfo.getEleStop(),elevaltorInfo.getArea(),elevaltorInfo.getTownshipStreets(),elevaltorInfo.getBuildingName(),elevaltorInfo.getBuilding(),elevaltorInfo.getUnit(),elevaltorInfo.getMap_X(),elevaltorInfo.getMap_Y(),elevaltorInfo.getManufactDate(),elevaltorInfo.getFactoryNum(),elevaltorInfo.getUseDate(),elevaltorInfo.getCheckCategory(),elevaltorInfo.getCheckResult(),elevaltorInfo.getCheckReportNum(),elevaltorInfo.getInspectDate(),elevaltorInfo.getRegistCode2(),elevaltorInfo.getRegistor(),elevaltorInfo.getNote(),elevaltorInfo.getmContractVdate(),elevaltorInfo.getHandleCompany(),elevaltorInfo.getHandleCompanyCode(),elevaltorInfo.getChangeWay(),s,elevaltorInfo.getRuKuValid(),elevaltorInfo.getDeviceId2(),elevaltorInfo.getLuruPersonID(),s,elevaltorInfo.getShibieCode(),elevaltorInfo.getNeleType()}, "id=?", new Object[] { elevaltorInfo.getId()});	
						}
						else{
					   num = DdElevaltorInfo.updateAll(DdElevaltorInfo.class, "registNumber=?,address=?,name=?,eleType=?,registCode=?,inoutDoor=?,propertyRightsUnit=?,zzCompanyId=?,eleMode=?,wgCompanyId=?,useNumber=?,safetyManDepart=?,safetyManPerson=?,safetyManPersonTel=?,jyCompanyId=?,inspector=?,nextInspectDate=?,completeAcceptanceDate=?,acceptanceDateDepart=?,acceptanceReportNum=?,azCompanyId=?,ywCompanyId=?,zjCompanyId=?,eleLoad=?,speed=?,eleheight=?,elewidth=?,eleStop=?,area=?,townshipStreets=?,buildingName=?,building=?,unit=?,map_X=?,map_Y=?,manufactDate=?,factoryNum=?,useDate=?,checkCategory=?,checkResult=?,checkReportNum=?,inspectDate=?,registCode2=?,registor=?,note=?,mContractVdate=?,handleCompany=?,handleCompanyCode=?,changeWay=?,updateTime=?,ruKuValid=?,deviceId2=?,verifyPersonID=?,verifyTime=?", new Object[]{elevaltorInfo.getRegistNumber(),elevaltorInfo.getAddress(),elevaltorInfo.getName(),elevaltorInfo.getEleType(),elevaltorInfo.getRegistCode(),elevaltorInfo.getInoutDoor(),elevaltorInfo.getPropertyRightsUnit(),elevaltorInfo.getZzCompanyId(),elevaltorInfo.getEleMode(),elevaltorInfo.getWgCompanyId(),elevaltorInfo.getUseNumber(),elevaltorInfo.getSafetyManDepart(),elevaltorInfo.getSafetyManPerson(),elevaltorInfo.getSafetyManPersonTel(),elevaltorInfo.getJyCompanyId(),elevaltorInfo.getInspector(),elevaltorInfo.getNextInspectDate(),elevaltorInfo.getCompleteAcceptanceDate(),elevaltorInfo.getAcceptanceDateDepart(),elevaltorInfo.getAcceptanceReportNum(),elevaltorInfo.getAzCompanyId(),elevaltorInfo.getYwCompanyId(),elevaltorInfo.getZjCompanyId(),elevaltorInfo.getEleLoad(),elevaltorInfo.getSpeed(),elevaltorInfo.getEleheight(),elevaltorInfo.getElewidth(),elevaltorInfo.getEleStop(),elevaltorInfo.getArea(),elevaltorInfo.getTownshipStreets(),elevaltorInfo.getBuildingName(),elevaltorInfo.getBuilding(),elevaltorInfo.getUnit(),elevaltorInfo.getMap_X(),elevaltorInfo.getMap_Y(),elevaltorInfo.getManufactDate(),elevaltorInfo.getFactoryNum(),elevaltorInfo.getUseDate(),elevaltorInfo.getCheckCategory(),elevaltorInfo.getCheckResult(),elevaltorInfo.getCheckReportNum(),elevaltorInfo.getInspectDate(),elevaltorInfo.getRegistCode2(),elevaltorInfo.getRegistor(),elevaltorInfo.getNote(),elevaltorInfo.getmContractVdate(),elevaltorInfo.getHandleCompany(),elevaltorInfo.getHandleCompanyCode(),elevaltorInfo.getChangeWay(),s,elevaltorInfo.getRuKuValid(),elevaltorInfo.getDeviceId2(),elevaltorInfo.getLuruPersonID(),s}, "id=?", new Object[] { elevaltorInfo.getId()});	
						}
						}
					}
					else{
					if(!"0".equals(cityName)){
				     num = DdElevaltorInfo.updateAll(DdElevaltorInfo.class, "registNumber=?,address=?,name=?,eleType=?,registCode=?,inoutDoor=?,propertyRightsUnit=?,zzCompanyId=?,eleMode=?,wgCompanyId=?,useNumber=?,safetyManDepart=?,safetyManPerson=?,safetyManPersonTel=?,jyCompanyId=?,inspector=?,nextInspectDate=?,completeAcceptanceDate=?,acceptanceDateDepart=?,acceptanceReportNum=?,azCompanyId=?,ywCompanyId=?,zjCompanyId=?,eleLoad=?,speed=?,eleheight=?,elewidth=?,eleStop=?,area=?,townshipStreets=?,buildingName=?,building=?,unit=?,map_X=?,map_Y=?,manufactDate=?,factoryNum=?,useDate=?,checkCategory=?,checkResult=?,checkReportNum=?,inspectDate=?,registCode2=?,registor=?,note=?,mContractVdate=?,handleCompany=?,handleCompanyCode=?,changeWay=?,updateTime=?,ruKuValid=?,deviceId2=?,verifyPersonID=?,verifyTime=?,shibieCode=?,neleType=?", new Object[]{elevaltorInfo.getRegistNumber(),elevaltorInfo.getAddress(),elevaltorInfo.getName(),elevaltorInfo.getEleType(),elevaltorInfo.getRegistCode(),elevaltorInfo.getInoutDoor(),elevaltorInfo.getPropertyRightsUnit(),elevaltorInfo.getZzCompanyId(),elevaltorInfo.getEleMode(),elevaltorInfo.getWgCompanyId(),elevaltorInfo.getUseNumber(),elevaltorInfo.getSafetyManDepart(),elevaltorInfo.getSafetyManPerson(),elevaltorInfo.getSafetyManPersonTel(),elevaltorInfo.getJyCompanyId(),elevaltorInfo.getInspector(),elevaltorInfo.getNextInspectDate(),elevaltorInfo.getCompleteAcceptanceDate(),elevaltorInfo.getAcceptanceDateDepart(),elevaltorInfo.getAcceptanceReportNum(),elevaltorInfo.getAzCompanyId(),elevaltorInfo.getYwCompanyId(),elevaltorInfo.getZjCompanyId(),elevaltorInfo.getEleLoad(),elevaltorInfo.getSpeed(),elevaltorInfo.getEleheight(),elevaltorInfo.getElewidth(),elevaltorInfo.getEleStop(),elevaltorInfo.getArea(),elevaltorInfo.getTownshipStreets(),elevaltorInfo.getBuildingName(),elevaltorInfo.getBuilding(),elevaltorInfo.getUnit(),elevaltorInfo.getMap_X(),elevaltorInfo.getMap_Y(),elevaltorInfo.getManufactDate(),elevaltorInfo.getFactoryNum(),elevaltorInfo.getUseDate(),elevaltorInfo.getCheckCategory(),elevaltorInfo.getCheckResult(),elevaltorInfo.getCheckReportNum(),elevaltorInfo.getInspectDate(),elevaltorInfo.getRegistCode2(),elevaltorInfo.getRegistor(),elevaltorInfo.getNote(),elevaltorInfo.getmContractVdate(),elevaltorInfo.getHandleCompany(),elevaltorInfo.getHandleCompanyCode(),elevaltorInfo.getChangeWay(),s,elevaltorInfo.getRuKuValid(),elevaltorInfo.getDeviceId2(),elevaltorInfo.getLuruPersonID(),s,elevaltorInfo.getShibieCode(),elevaltorInfo.getNeleType()}, "id=?", new Object[] { elevaltorInfo.getId()});			
					}
					else{
				    num = DdElevaltorInfo.updateAll(DdElevaltorInfo.class, "registNumber=?,address=?,name=?,eleType=?,registCode=?,inoutDoor=?,propertyRightsUnit=?,zzCompanyId=?,eleMode=?,wgCompanyId=?,useNumber=?,safetyManDepart=?,safetyManPerson=?,safetyManPersonTel=?,jyCompanyId=?,inspector=?,nextInspectDate=?,completeAcceptanceDate=?,acceptanceDateDepart=?,acceptanceReportNum=?,azCompanyId=?,ywCompanyId=?,zjCompanyId=?,eleLoad=?,speed=?,eleheight=?,elewidth=?,eleStop=?,area=?,townshipStreets=?,buildingName=?,building=?,unit=?,map_X=?,map_Y=?,manufactDate=?,factoryNum=?,useDate=?,checkCategory=?,checkResult=?,checkReportNum=?,inspectDate=?,registCode2=?,registor=?,note=?,mContractVdate=?,handleCompany=?,handleCompanyCode=?,changeWay=?,updateTime=?,ruKuValid=?,deviceId2=?,verifyPersonID=?,verifyTime=?", new Object[]{elevaltorInfo.getRegistNumber(),elevaltorInfo.getAddress(),elevaltorInfo.getName(),elevaltorInfo.getEleType(),elevaltorInfo.getRegistCode(),elevaltorInfo.getInoutDoor(),elevaltorInfo.getPropertyRightsUnit(),elevaltorInfo.getZzCompanyId(),elevaltorInfo.getEleMode(),elevaltorInfo.getWgCompanyId(),elevaltorInfo.getUseNumber(),elevaltorInfo.getSafetyManDepart(),elevaltorInfo.getSafetyManPerson(),elevaltorInfo.getSafetyManPersonTel(),elevaltorInfo.getJyCompanyId(),elevaltorInfo.getInspector(),elevaltorInfo.getNextInspectDate(),elevaltorInfo.getCompleteAcceptanceDate(),elevaltorInfo.getAcceptanceDateDepart(),elevaltorInfo.getAcceptanceReportNum(),elevaltorInfo.getAzCompanyId(),elevaltorInfo.getYwCompanyId(),elevaltorInfo.getZjCompanyId(),elevaltorInfo.getEleLoad(),elevaltorInfo.getSpeed(),elevaltorInfo.getEleheight(),elevaltorInfo.getElewidth(),elevaltorInfo.getEleStop(),elevaltorInfo.getArea(),elevaltorInfo.getTownshipStreets(),elevaltorInfo.getBuildingName(),elevaltorInfo.getBuilding(),elevaltorInfo.getUnit(),elevaltorInfo.getMap_X(),elevaltorInfo.getMap_Y(),elevaltorInfo.getManufactDate(),elevaltorInfo.getFactoryNum(),elevaltorInfo.getUseDate(),elevaltorInfo.getCheckCategory(),elevaltorInfo.getCheckResult(),elevaltorInfo.getCheckReportNum(),elevaltorInfo.getInspectDate(),elevaltorInfo.getRegistCode2(),elevaltorInfo.getRegistor(),elevaltorInfo.getNote(),elevaltorInfo.getmContractVdate(),elevaltorInfo.getHandleCompany(),elevaltorInfo.getHandleCompanyCode(),elevaltorInfo.getChangeWay(),s,elevaltorInfo.getRuKuValid(),elevaltorInfo.getDeviceId2(),elevaltorInfo.getLuruPersonID(),s}, "id=?", new Object[] { elevaltorInfo.getId()});	
					}
					}
				}
		//		}
			
			    if(num>0)
				result = "success"; 
			    
			    //���������ϵ
			    
			    ActiveRecordBase.commit();
			} catch (TransactionException e) {
				e.printStackTrace();
				ActiveRecordBase.rollback();
			}
		
		    
		 }
		catch (Exception e) {
			  e.printStackTrace();
			  return result;
		  } 
		 return result;
	 }
	 
	 
	 public String tjyjyelevatorTongGuo(ElevaltorInfoVO info){
		 String result = "failure";
		 int id = 0;
		 int resouceFlag = 0;
		 String registNumber ="";
		 String registCode = "";
		 String registCode2 = "";
	//	 String address2 ="";
	//	 String buildingName2 ="";
	//	 String building2 = "";
	//	 String unit2= "";
	//	 String useNumber2 ="";
		 
		 id =info.getId();
		 registNumber = info.getRegistNumber();
		 registCode = info.getRegistCode();
		 registCode2 = info.getRegistCode2();
	//	 address2 = info.getAddress2();
	//	 buildingName2 =info.getBuilding2();
	//	 building2 =info.getBuilding2();
	//	 unit2 = info.getUnit2();
	//	 useNumber2 =info.getUseNumber2();
		 
		 resouceFlag = info.getResouceFlag();
		 
		
		 if(0==resouceFlag){  //��ʽ��
			 try {
				int num = 0;
				num = ElevaltorInfoVO.updateAll(ElevaltorInfoVO.class, "registCode = ?, jyjyFlag = ? ", new Object[]{registCode2,1}, "id = ? and registNumber = ?", new Object[]{id,registNumber});
			    if(num > 0){
			    ElevaltorInfoVO.updateAll(ElevaltorInfoVO.class, "registCode = ?", new Object[]{registCode}, "registCode = ? and jyjyFlag != 1  and id != ? ", new Object[]{registCode2,id});	
			    result ="success";
			    }
			 } catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}
		 }
		 if(1==resouceFlag){ //ճ����
			 try {
				int num2 = 0;
				num2 =DdElevaltorInfoVO.updateAll(DdElevaltorInfoVO.class, "registCode = ?,jyjyFlag = ? ", new Object[]{registCode2,1}, "id = ? and registNumber = ?", new Object[]{id,registNumber});
			    if(num2 > 0){
			    	    DdElevaltorInfoVO.updateAll(DdElevaltorInfoVO.class, "registCode = ? ", new Object[]{registCode}, "registCode = ? and jyjyFlag != 1 and id ! = ?", new Object[]{registCode2,id});	
					    result ="success";	
			    }
			 } catch (ActiveRecordException e) {
				e.printStackTrace();
			}
				
		 }
		 return result;
	 }
	 
	 
	 public String tjyjyelevatorUnTongGuo(ElevaltorInfoVO info){
		 String result = "failure";
		 int id = 0;
		 int resouceFlag = 0;
		 String registNumber ="";
		 String registCode = "";
		 String registCode2 = "";
	//	 String address2 ="";
	//	 String buildingName2 ="";
	//	 String building2 = "";
	//	 String unit2= "";
	//	 String useNumber2 ="";
		 
		 id =info.getId();
		 registNumber = info.getRegistNumber();
		 registCode = info.getRegistCode();
		 registCode2 = info.getRegistCode2();
	//	 address2 = info.getAddress2();
	//	 buildingName2 =info.getBuilding2();
	//	 building2 =info.getBuilding2();
	//	 unit2 = info.getUnit2();
	//	 useNumber2 =info.getUseNumber2();
		 
		 resouceFlag = info.getResouceFlag();
		 
		
		 if(0==resouceFlag){  //��ʽ��
			 try {
				int num = 0;
				num = ElevaltorInfoVO.updateAll(ElevaltorInfoVO.class, "jyjyFlag = ? ", new Object[]{4}, "id = ? and registNumber = ?", new Object[]{id,registNumber});
			    if(num > 0){
			      result ="success";
			    }
			 } catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}
		 }
		 if(1==resouceFlag){ //ճ����
			 try {
				int num2 = 0;
				num2 =DdElevaltorInfoVO.updateAll(DdElevaltorInfoVO.class, "jyjyFlag = ? ", new Object[]{4}, "id = ? and registNumber = ?", new Object[]{id,registNumber});
			    if(num2 > 0){	   	
				  result ="success";	
			    }
			 } catch (ActiveRecordException e) {
				e.printStackTrace();
			}
				
		 }
		 return result;
	 }
	 
	 
	 //����ɾ������
	 public String dddelete(DdElevaltorInfoVO elevaltorInfoVO){
		 String result = "failure";
		 int id = elevaltorInfoVO.getId();
		 String registNumber = elevaltorInfoVO.getRegistNumber();
		 TwoCodeDeviceRelationInfo twoCodeDeviceRelationInfo =null;
		 DdElevaltorInfo  elevaltorInfo = null;
		 DdAppendixEleInfo ddAppendixEleInfo =null;
		 try {
			ActiveRecordBase.beginTransaction();
			ddAppendixEleInfo  = DdAppendixEleInfo.findFirst(DdAppendixEleInfo.class,"registNumber = ?", new Object[]{registNumber});
			if(ddAppendixEleInfo != null){  //ɾ��������ǩ���ӱ���Ϣ
				ddAppendixEleInfo.destroy();
			}
			twoCodeDeviceRelationInfo = TwoCodeDeviceRelationInfo.findFirst(TwoCodeDeviceRelationInfo.class, "registNumber = ?", new Object[]{registNumber});
			if(twoCodeDeviceRelationInfo != null){   //ɾ�����ϻ�ӵĹ�����ϵ
				 twoCodeDeviceRelationInfo.destroy();
			}
			elevaltorInfo  = DdElevaltorInfo.findFirst(DdElevaltorInfo.class, "id = ?", new Object[]{ id});
			if(elevaltorInfo != null){   //ɾ����ά���ǩ������Ϣ
				elevaltorInfo.destroy();
				result = "success";   
			}
			 ActiveRecordBase.commit();
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			try {
				ActiveRecordBase.rollback();
			} catch (TransactionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		 return result;
	 }
	 
	 //��������������������ѯ����
	 public View ddquery(DdElevaltorInfoVO info,int page, int rows)throws Exception{
		 String cityName = GlobalFunction.cityName;
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 
		 String registNumber ="";
		 int ywCompanyId =0;  //��ѯ�õ�
		 int zjcompanyId2 =0;  //�ǲ�ѯ�õģ��ǵ�½������ϵͳ��̨����role��ͬչʾ��ͬ�б��õ�
		 String companyName="";
		 String areaName="";
		 String address ="";
		 String buildingName="";
		 String registCode="";
		 String useNumber="";
		 int shenhe =0;
		 
		 int qresource =0;
		 String qstartTime ="";
		 String qendTime="";
		 
		
		 address =info.getAddress();
		 
		 
	     buildingName =info.getBuildingName();
		 
		 companyName =info.getYwCompanyName();
		 
		 
		 registNumber =info.getRegistNumber();
		 
		
		 areaName =info.getArea();
	
		 
		 ywCompanyId =info.getYwCompanyId();
		 
		 registCode=info.getRegistCode();
		 
		 useNumber=info.getUseNumber();
		 
		 shenhe = info.getShenhe();
		 
		 qresource = info.getQresource();
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 
		 System.out.println("��ѯ����registNumber----"+info.getRegistNumber());
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 /*
		 if(role==2 || role==1){
		     if("".equals(info.getRegistCode())){
		    	 if("".equals(info.getUseNumber())){  System.out.println("1685");    
		    		 conditions =" t.recordSate < 3 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ?  and (t.registCode like ? or t.registCode is null) and (t.useNumber like ? or t.useNumber is null) ";
					 conditionsSql=" recordSate < 3 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and (registCode like ? or registCode is null) and (useNumber like ? or useNumber is null) ";
			    	 
		    	 }
		    	 else{ System.out.println("1690");
		    	    conditions =" t.recordSate < 3 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ?  and (t.registCode like ? or t.registCode is null) and t.useNumber like ? ";
				    conditionsSql=" recordSate < 3 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and (registCode like ? or registCode is null) and useNumber like ? ";
		    	 }
		     }
		     else{
		    	 if("".equals(info.getUseNumber())){ System.out.println("1695");
		    		 conditions =" t.recordSate < 3 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ?  and t.registCode like ?  and (t.useNumber like ? or t.useNumber is null) ";
		             conditionsSql=" recordSate < 3 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and registCode like ? and (useNumber like ? or useNumber is null) ";
		    	
		    	 }
		    	 else{
		             conditions =" t.recordSate < 3 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ?  and t.registCode like ? and t.useNumber like ? ";
		             conditionsSql=" recordSate < 3 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and registCode like ?  and useNumber like ? ";
		    	 }
		     }
		 }
		 
		 if(role==22 || role==23){
			 if("".equals(info.getRegistCode())){
				 if("".equals(info.getUseNumber())){
						conditions =" t.recordSate < 3 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ? and (t.registCode like ? or t.registCode is null) and (t.useNumber like ? or t.useNumber is null) ";
						conditionsSql=" recordSate < 3 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and (registCode like ? or registCode is null) and (useNumber like ? or useNumber is null) ";	 
						 
				 }
				 else{
				    conditions =" t.recordSate < 3 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ? and (t.registCode like ? or t.registCode is null) and t.useNumber like ? ";
				    conditionsSql=" recordSate < 3 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and (registCode like ? or registCode is null) and useNumber like ? ";	 
				 }
			 }
			 else{
				 if("".equals(info.getUseNumber())){
			        conditions =" t.recordSate < 3 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ? and t.registCode like ?  and (t.useNumber like ? or t.useNumber is null) ";
			        conditionsSql=" recordSate < 3 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and registCode like ? and (useNumber like ? or useNumber is null) ";
				 }
				 else{
					 conditions =" t.recordSate < 3 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ? and t.registCode like ?  and t.useNumber like ? ";
					 conditionsSql=" recordSate < 3 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and registCode like ? and useNumber like ? ";
						 
				 }
			   }
			 }
		 
		 if(role==10 || role==11){
			 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	    	 zjcompanyId2 = userInfoVO.getCompanyId();
	    	 if("".equals(info.getRegistCode())){
	    		 if("".equals(info.getUseNumber())){
	    			 conditions =" t.recordSate < 3 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ? and (t.registCode like ? or  t.registCode is null) and t.zjcompanyId=? and (t.useNumber like ? or t.useNumber is null) ";
					 conditionsSql=" recordSate < 3 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and (registCode like ? or registCode is null) and zjcompanyId=? and (useNumber like ? or useNumber is null) ";
		    		 
	    		 }
	    		 else{
	    		 conditions =" t.recordSate < 3 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ? and (t.registCode like ? or  t.registCode is null) and t.zjcompanyId=? and t.useNumber like ? ";
				 conditionsSql=" recordSate < 3 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and (registCode like ? or registCode is null) and zjcompanyId=? and useNumber like ? ";
	    		 } 
	    	 }
	    	 else{
	    		 if("".equals(info.getUseNumber())){
			      conditions =" t.recordSate < 3 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ? and t.registCode like ? and t.zjcompanyId=? and (t.useNumber like ? or t.useNumber is null) ";
			      conditionsSql=" recordSate < 3 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and registCode like ? and zjcompanyId=? and (useNumber like ? or useNumber is null) ";
	    	    }
	    		 else{
	    			  conditions =" t.recordSate < 3 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ? and t.registCode like ? and t.zjcompanyId=? and t.useNumber like ? ";
				      conditionsSql=" recordSate < 3 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and registCode like ? and zjcompanyId=?  and useNumber like ? ";
		    	   	 
	    		 }
	    	 }
	     }
		 }
		 Object[] param=null;
		 if(ywCompanyId>0){
			 conditions +=" and t.ywCompanyId = ? ";
			 conditionsSql +=" and ywCompanyId = ?";
			 if(role==2 || role==1)
			 param = new Object[]{registNumber,areaName,address,buildingName,registCode,useNumber,ywCompanyId};
			 if(role==22 || role==23)
			 param = new Object[]{registNumber,areaName,address,buildingName,registCode,useNumber,ywCompanyId};
			 if(role==10 || role==11)
			 param = new Object[]{registNumber,areaName,address,buildingName,registCode,zjcompanyId2,useNumber,ywCompanyId};	 
		 }
		 else{
			 conditions +=" and t.companyName like ? ";
			 conditionsSql +=" and companyName like ?";
			 if(role==2 || role==1)
			 param = new Object[]{registNumber,areaName,address,buildingName,registCode,useNumber,companyName};
			 if(role==22 || role==23)
			 param = new Object[]{registNumber,areaName,address,buildingName,registCode,useNumber,companyName};
			 if(role==10 || role==11)
			  param = new Object[]{registNumber,areaName,address,buildingName,registCode,zjcompanyId2,useNumber,companyName};	 
		 }
		 
		 
		 System.out.println("����ѯ����-----"+conditions);
		 */
		
		
		 
	//	 String sql ="select t.shenhe,t.subPersonID,isnull(t.mobileUploadbeizhu,'') as mobileUploadbeizhu,isnull(t.rukubeizhu,'') as rukubeizhu,t.arrageType,t.pastePersonID,CONVERT(CHAR(19), t.pasteTime, 20) as pasteTime2,t.pasteNote,CONVERT(CHAR(19), t.arrangeTime, 20) as arrangeTime2,t.pasteNote,tad.userName,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,t.inspector,inspectDate,t.nextInspectDate,(select te.userName from twocodeuserExtinfo te where   te.userid =t.arrangePersonID) as arrangePersonName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where "+conditions;
	//	 String sql ="select isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.isnormalFlag,t.subPersonID,isnull(t.mobileUploadbeizhu,'') as mobileUploadbeizhu,isnull(t.rukubeizhu,'') as rukubeizhu,t.arrageType,t.pastePersonID,CONVERT(CHAR(19), t.pasteTime, 20) as pasteTime2,t.pasteNote,CONVERT(CHAR(19), t.arrangeTime, 20) as arrangeTime2,t.pasteNote,tad.userName,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,(select te.userName from twocodeuserExtinfo te where   te.userid =t.arrangePersonID) as arrangePersonName,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where "+conditions;
	//	 String sql ="select isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.isnormalFlag,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName  from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber  where "+conditions;
		
		 
	//	 long total =DdTwoCodeElevatorYwCompanyInfo.count(DdTwoCodeElevatorYwCompanyInfo.class, conditionsSql, param);
	//	 System.out.println("total----"+total);
		 
		 if(role==10 || role==11){
			 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	    	 zjcompanyId2 = userInfoVO.getCompanyId();
	    	 conditions = " t.zjcompanyId = "+zjcompanyId2;
		 }	 
		 }
		 
		 if(!"".equals(registNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registNumber like '%"+registNumber+"%'";	
				} 
				else{
				 conditions =" t.registNumber like '%"+registNumber+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(address)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.address like '%"+address+"%'";	
				} 
				else{
					 conditions =" t.address like '%"+address+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(buildingName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.buildingName like '%"+buildingName+"%'";	
				} 
				else{
					conditions =" t.buildingName like '%"+buildingName+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(useNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.useNumber like '%"+useNumber+"%'";	
				} 
				else{
					conditions =" t.useNumber like '%"+useNumber+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(areaName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.area like '%"+areaName+"%'";	
				} 
				else{
					conditions =" t.area like '%"+areaName+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(registCode)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registCode like '%"+registCode+"%'";	
				} 
				else{
					conditions =" t.registCode like '%"+registCode+"%'";	
				}
			 
		 }
		 
		 if(ywCompanyId > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.ywCompanyId = "+ywCompanyId;	
				} 
				else{
					conditions =" t.ywCompanyId = "+ywCompanyId;	
				}
			 
			 
		 }
		 
		 if(shenhe != 2){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.shenhe = "+shenhe;	
				} 
				else{
					conditions =" t.shenhe = "+shenhe;	
				}
			 }
		 
		 if(qresource ==0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and ((t.buildingName is null or t.buildingName = '') and (t.address is null or t.address = ''))";	 
			 }
			 else{
				 conditions =" ((t.buildingName is null or t.buildingName = '') and (t.address is null or t.address = ''))";	 
					 
			 }
			 
		 }
		 
		 if(qresource ==1){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and ((t.buildingName is not null and t.buildingName != '') or (t.address is not null and t.address != '')) ";	 
			 }
			 else{
				 conditions =" ((t.buildingName is not null and t.buildingName != '') or (t.address is not null and t.address != '')) ";	 
					 
			 }
			 
		 }
		 
		 if(!"".equals(qstartTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.subTime2  >= '"+qstartTime+"'" ;	 
			 } 
			 else{
				 conditions =" t.subTime2  >= '"+qstartTime+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.subTime2  <= '"+qendTime+"'" ;	 
			 } 
			 else{
				 conditions =" t.subTime2  <= '"+qendTime+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(conditions)){
			 if("1".equals(cityName)){
			sql ="select t.jyjyFlag,(select tw.shibieCode from TwoCodeDdElevatorInfo tw where tw.id=t.id) as shibieCode,isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.isnormalFlag,isnull(t.subTime2,'') as subTime2,t.id,isnull(t.registNumber,'') as registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,isnull(t.zzCompanyId,0) as zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,isnull(t.azCompanyId,0) as azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,isnull(t.wgCompanyId,0) as wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,isnull(t.ywCompanyId,0) as ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,isnull(t.jyCompanyId,0) as jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,isnull(t.zjCompanyId,0) as zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,isnull(t.townshipStreets,0) as townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName  from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber  where "+ conditions+"and t.recordSate <3";			 
			 }
			 else{
		    sql ="select t.jyjyFlag,(select tw.shibieCode from TwoCodeDdElevatorInfo tw where tw.id=t.id) as shibieCode,isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.isnormalFlag,isnull(t.subTime2,'') as subTime2,t.id,isnull(t.registNumber,'') as registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,isnull(t.zzCompanyId,0) as zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,isnull(t.azCompanyId,0) as azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,isnull(t.wgCompanyId,0) as wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,isnull(t.ywCompanyId,0) as ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,isnull(t.jyCompanyId,0) as jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,isnull(t.zjCompanyId,0) as zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,isnull(t.townshipStreets,0) as townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName  from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber  where "+ conditions+"and t.recordSate <3";
			 }
		  conditionsSql = "select t.registNumber from DdTwoCodeElevatorYwCompanyInfo  t  where "+ conditions+"and t.recordSate <3";
		 }
		 else{
			 if("1".equals(cityName)){
			sql ="select t.jyjyFlag,(select tw.shibieCode from TwoCodeDdElevatorInfo tw where tw.id=t.id) as shibieCode,isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.isnormalFlag,isnull(t.subTime2,'') as subTime2,t.id,isnull(t.registNumber,'') as registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,isnull(t.zzCompanyId,0) as zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,isnull(t.azCompanyId,0) as azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,isnull(t.wgCompanyId,0) as wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,isnull(t.ywCompanyId,0) as ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,isnull(t.jyCompanyId,0) as jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,isnull(t.zjCompanyId,0) as zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,isnull(t.townshipStreets,0) as townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName  from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where t.recordSate <3";	 
			 }
			 else{
		  sql ="select t.jyjyFlag,(select tw.shibieCode from TwoCodeDdElevatorInfo tw where tw.id=t.id) as shibieCode,isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.isnormalFlag,isnull(t.subTime2,'') as subTime2,t.id,isnull(t.registNumber,'') as registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,isnull(t.zzCompanyId,0) as zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,isnull(t.azCompanyId,0) as azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,isnull(t.wgCompanyId,0) as wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,isnull(t.ywCompanyId,0) as ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,isnull(t.jyCompanyId,0) as jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,isnull(t.zjCompanyId,0) as zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,isnull(t.townshipStreets,0) as townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName  from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where t.recordSate <3";
			 }
		  conditionsSql = "select t.registNumber from DdTwoCodeElevatorYwCompanyInfo  t  where t.recordSate <3";
					 
		 }
		 
	//	 List<DdTwoCodeElevatorYwCompanyInfo> items3=DdTwoCodeElevatorYwCompanyInfo.findBySql(DdTwoCodeElevatorYwCompanyInfo.class, conditionsSql, null, null);
		 List<DdTwoCodeElevatorYwCompanyInfoNew> items3=DdTwoCodeElevatorYwCompanyInfoNew.findBySql(DdTwoCodeElevatorYwCompanyInfoNew.class, conditionsSql, null, null);
		 
		 long total =items3.size();
		 
		 List<DdTwoCodeElevatorYwCompanyInfoNew> items=DdTwoCodeElevatorYwCompanyInfoNew.findBySql(DdTwoCodeElevatorYwCompanyInfoNew.class, sql, null, "t.ruKuValid,t.id desc", rows, (page-1)*rows);
		 
		 /*
		 if(items != null && items.size()>0){
	    	 Iterator<DdTwoCodeElevatorYwCompanyInfo> it =items.iterator();
	         while(it.hasNext()){
	        	 DdTwoCodeElevatorYwCompanyInfo  ddElevaltorInfoVO = it.next();
	        	 ddElevaltorInfoVO.setSubPersonName("");
	        	 int recordSate  =ddElevaltorInfoVO.getRecordSate();
	        	 if(recordSate >=1){
	        		 int arrageType =ddElevaltorInfoVO.getArrageType();
	        		 int pastePersonID=ddElevaltorInfoVO.getPastePersonID();
	        		 if(arrageType ==1){   //�������˾ճ��������
	        			 CompanyInfo companyInfo=CompanyInfo.findFirst(CompanyInfo.class, "id = ?", new Object[]{pastePersonID});
	        			 if(companyInfo != null)
	        				 ddElevaltorInfoVO.setPastePersonName(companyInfo.getCompanyName());
	        		 }
	        		 else{   //�����ѧ��
	        			 UserExtInfo  userExtInfo=UserExtInfo.findFirst(UserExtInfo.class, "userid = ?", new Object[]{pastePersonID});
	        			 if(userExtInfo != null){
	        				 ddElevaltorInfoVO.setPastePersonName(userExtInfo.getUserName());
	        			 }
	        		 }
	        	 }
	        	 if(recordSate >1){
		        	 int subPersonID =ddElevaltorInfoVO.getSubPersonID();
		        	 UserExtInfo  userExtInfo=UserExtInfo.findFirst(UserExtInfo.class, "userid = ?", new Object[]{subPersonID});
	    			 if(userExtInfo != null)
	    				 ddElevaltorInfoVO.setSubPersonName(userExtInfo.getUserName());
		        	 }
	         }
	      }
		  */
		  System.out.println("------"+items.size());
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		 }
	 
	 
	//����������������������ѯ����
	 public View rukuddquery(DdElevaltorInfoVO info,int page, int rows)throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 String registNumber ="";
		 int ywCompanyId =0;  //��ѯ�õ�
		 int zjcompanyId2 =0;  //�ǲ�ѯ�õģ��ǵ�½������ϵͳ��̨����role��ͬչʾ��ͬ�б��õ�
		 String companyName="";
		 String areaName="";
		 String address ="";
		 String buildingName="";
		 String registCode="";
		 String qstartTime ="";
		 String qendTime="";
		 
		
		 address =info.getAddress();
	     buildingName =info.getBuildingName();
		 companyName =info.getYwCompanyName();
		 registNumber =info.getRegistNumber();
		 areaName =info.getArea();
		 ywCompanyId =info.getYwCompanyId();
		 registCode=info.getRegistCode();
		 qstartTime=info.getQstartTime();
		 qendTime = info.getQendTime();
		 	 
		 String sql="";
		 String conditions="";
		 String conditionsSql="";
		 
		 /*
		 if(role==2 || role==1){
		     if("".equals(info.getRegistCode())){
		    	 conditions =" t.recordSate = 3 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ?  and (t.registCode like ? or t.registCode is null) ";
				 conditionsSql=" recordSate = 3 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and (registCode like ? or registCode is null) ";
		     }
		     else{
		      conditions =" t.recordSate = 3 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ?  and t.registCode like ? ";
		      conditionsSql=" recordSate = 3 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and registCode like ? ";
		  }
		 }
		 
		 if(role==22 || role==23){
			 if("".equals(info.getRegistCode())){
				conditions =" t.recordSate = 3 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ? and (t.registCode like ? or t.registCode is null) ";
				conditionsSql=" recordSate = 3 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and (registCode like ? or registCode is null) ";	 
			 }
			 else{
			  conditions =" t.recordSate = 3 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ? and t.registCode like ? ";
			  conditionsSql=" recordSate = 3 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and registCode like ? ";
			 }
			 }
		 
		 if(role==10 || role==11){
			 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	    	 zjcompanyId2 = userInfoVO.getCompanyId();
	    	 if("".equals(info.getRegistCode())){
	    		 conditions =" t.recordSate = 3 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ? and (t.registCode like ? or  t.registCode is null) and t.zjcompanyId=? ";
				 conditionsSql=" recordSate = 3 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and (registCode like ? or registCode is null) and zjcompanyId=? ";
		    	  
	    	 }
	    	 else{
			 conditions =" t.recordSate = 3 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ? and t.registCode like ? and t.zjcompanyId=? ";
			 conditionsSql=" recordSate = 3 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and registCode like ? and zjcompanyId=? ";
	    	 }
	     }
		 }
		 Object[] param=null;
		 if(ywCompanyId>0){
			 conditions +=" and t.ywCompanyId = ? ";
			 conditionsSql +=" and ywCompanyId = ?";
			 if(role==2 || role==1)
			 param = new Object[]{registNumber,areaName,address,buildingName,registCode,ywCompanyId};
			 if(role==22 || role==23)
			 param = new Object[]{registNumber,areaName,address,buildingName,registCode,ywCompanyId};
			 if(role==10 || role==11)
			 param = new Object[]{registNumber,areaName,address,buildingName,registCode,zjcompanyId2,ywCompanyId};	 
		 }
		 else{
			 conditions +=" and t.companyName like ? ";
			 conditionsSql +=" and companyName like ?";
			 if(role==2 || role==1)
			 param = new Object[]{registNumber,areaName,address,buildingName,registCode,companyName};
			 if(role==22 || role==23)
			 param = new Object[]{registNumber,areaName,address,buildingName,registCode,companyName};
			 if(role==10 || role==11)
			  param = new Object[]{registNumber,areaName,address,buildingName,registCode,zjcompanyId2,companyName};	 
		 }
		 
		 
		 System.out.println("����ѯ����-----"+conditions);
		 
		*/
		 
	//	 String sql ="select isnull(t.rukubeizhu,'') as rukubeizhu,t.arrageType,t.pastePersonID,t.pasteTime,t.pasteNote,CONVERT(CHAR(19), t.arrangeTime, 20) as arrangeTime2,t.pasteNote,tad.userName,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,t.inspector,inspectDate,t.nextInspectDate,(select te.userName from twocodeuserExtinfo te where   te.userid =t.arrangePersonID) as arrangePersonName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,isnull(t.deviceId2,'') as deviceId2  from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where "+conditions;
		 
		 
	//	 long total =DdTwoCodeElevatorYwCompanyInfo.count(DdTwoCodeElevatorYwCompanyInfo.class, conditionsSql, param);
	//	 System.out.println("total----"+total);
		 
	//	 List<DdTwoCodeElevatorYwCompanyInfo> items=DdTwoCodeElevatorYwCompanyInfo.findBySql(DdTwoCodeElevatorYwCompanyInfo.class, sql, param, null, rows, (page-1)*rows);
		
		 if(role==10 || role==11){
			 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	    	 zjcompanyId2 = userInfoVO.getCompanyId();
	    	 conditions = " t.zjcompanyId = "+zjcompanyId2;
	    	 }
		 }
		 
		 if(!"".equals(registNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registNumber like '%"+registNumber+"%'";	
				} 
				else{
				 conditions =" t.registNumber like '%"+registNumber+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(areaName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.area like '%"+areaName+"%'";	
				} 
				else{
				 conditions =" t.area like '%"+areaName+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(address)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.address like '%"+address+"%'";	
				} 
				else{
					 conditions =" t.address like '%"+address+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(buildingName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.buildingName like '%"+buildingName+"%'";	
				} 
				else{
					 conditions =" t.buildingName like '%"+buildingName+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(registCode)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registCode like '%"+registCode+"%'";	
				} 
				else{
					 conditions =" t.registCode like '%"+registCode+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(companyName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.companyName like '%"+companyName+"%'";	
				} 
				else{
					conditions =" t.companyName like '%"+companyName+"%'";	
				}
			 
		 }
		 
		 if(ywCompanyId > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.ywCompanyId = "+ywCompanyId;	
				} 
				else{
					conditions =" t.ywCompanyId = "+ywCompanyId;	
				}
			 
			 
		 }
		 
		 if(!"".equals(qstartTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.pasteTime  >= '"+qstartTime+"'" ;	 
			 } 
			 else{
				 conditions =" t.pasteTime  >= '"+qstartTime+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.pasteTime  <= '"+qendTime+"'" ;	 
			 } 
			 else{
				 conditions =" t.pasteTime  <= '"+qendTime+"'" ;	 
			 }
		 }
		 
		
		 System.out.println(conditions);
		 if(!"".equals(conditions)){
		//	 sql ="select isnull(t.rukubeizhu,'') as rukubeizhu,t.arrageType,t.pastePersonID,t.pasteTime,t.pasteNote,CONVERT(CHAR(19), t.arrangeTime, 20) as arrangeTime2,t.pasteNote,tad.userName,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,t.inspector,inspectDate,t.nextInspectDate,(select te.userName from twocodeuserExtinfo te where   te.userid =t.arrangePersonID) as arrangePersonName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,isnull(t.deviceId2,'') as deviceId2  from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where" + conditions+"and t.recordSate =3 ";
			 sql ="select isnull(t.rukubeizhu,'') as rukubeizhu,isnull(t.mobileUploadbeizhu,'') as mobileUploadbeizhu,t.arrageType,t.pastePersonID,t.pasteTime,t.pasteNote,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,t.inspector,inspectDate,t.nextInspectDate,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,t.ruKuValid  from DdTwoCodeElevatorYwCompanyInfo  t  where" + conditions+"and t.recordSate =3 ";
			 conditionsSql="select count(*) from DdTwoCodeElevatorYwCompanyInfo t where "+ conditions+"and t.recordSate =3"; 
		 }
		 else{
			 sql ="select isnull(t.rukubeizhu,'') as rukubeizhu,isnull(t.mobileUploadbeizhu,'') as mobileUploadbeizhu,t.arrageType,t.pastePersonID,t.pasteTime,t.pasteNote,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,t.inspector,inspectDate,t.nextInspectDate,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,t.ruKuValid  from DdTwoCodeElevatorYwCompanyInfo  t  where t.recordSate =3 ";
			 conditionsSql="select count(*) from DdTwoCodeElevatorYwCompanyInfo t where t.recordSate = 3 ";	 
		 
		 }
		 
    	 long total =DdTwoCodeElevatorYwCompanyInfo.countBySql(DdTwoCodeElevatorYwCompanyInfo.class, conditionsSql, null);
			 
		 List<DdTwoCodeElevatorYwCompanyInfo> items=DdTwoCodeElevatorYwCompanyInfo.findBySql(DdTwoCodeElevatorYwCompanyInfo.class, sql, null, null, rows, (page-1)*rows);
				
		 /*
		 if(items != null && items.size()>0){
	    	 Iterator<DdTwoCodeElevatorYwCompanyInfo> it =items.iterator();
	         while(it.hasNext()){
	        	 DdTwoCodeElevatorYwCompanyInfo  ddElevaltorInfoVO = it.next();
	        	 int recordSate  =ddElevaltorInfoVO.getRecordSate();
	        	 if(recordSate >=1){
	        		 int arrageType =ddElevaltorInfoVO.getArrageType();
	        		 int pastePersonID=ddElevaltorInfoVO.getPastePersonID();
	        		 if(arrageType ==1){   //�������˾ճ��������
	        			 CompanyInfo companyInfo=CompanyInfo.findFirst(CompanyInfo.class, "id = ?", new Object[]{pastePersonID});
	        			 if(companyInfo != null)
	        				 ddElevaltorInfoVO.setPastePersonName(companyInfo.getCompanyName());
	        		 }
	        		 else{   //�����ѧ��
	        			 UserExtInfo  userExtInfo=UserExtInfo.findFirst(UserExtInfo.class, "userid = ?", new Object[]{pastePersonID});
	        			 if(userExtInfo != null)
	        				 ddElevaltorInfoVO.setPastePersonName(userExtInfo.getUserName());
	        		 }
	        	 }
	         }
	      } */
		 
		//  System.out.println("------"+items.size());
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		 }
	 
	 //��������������������ѯ����
	 public View hechaddquery(DdElevaltorInfoVO info,int page, int rows)throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 System.out.println("ElevatorController---query---��½����---"+userName);
		 System.out.println("ElevatorController---query---role---"+role);
		 
		 String registNumber ="";
		 int ywCompanyId =0;  //��ѯ�õ�
		 int zjcompanyId2 =0;  //�ǲ�ѯ�õģ��ǵ�½������ϵͳ��̨����role��ͬչʾ��ͬ�б��õ�
		 String companyName="";
		 String areaName="";
		 String address ="";
		 String buildingName="";
		 String registCode="";
		 String useNumber="";
		 
		 
		 if(info.getAddress()==null)
			 address ="%"+""+"%";	 
		 else
		 address ="%"+info.getAddress()+"%";
		 
		 
		 if(info.getBuildingName()==null)
			 buildingName ="%"+""+"%";	 
		 else
			 buildingName ="%"+info.getBuildingName()+"%";
		 
		 if(info.getYwCompanyName()==null)
			 companyName ="%"+""+"%";
		 else
		 companyName ="%"+info.getYwCompanyName()+"%";
		 
		 if(info.getRegistNumber()==null)
			 registNumber="%"+""+"%";
		 else
		 registNumber ="%"+info.getRegistNumber()+"%";
		 
		 if(info.getArea()==null)
			 areaName="%"+""+"%";
		 else
		 areaName ="%"+info.getArea()+"%";
	
		 
		 ywCompanyId =info.getYwCompanyId();
		 
		 if(info.getRegistCode()==null)
			 registCode="%"+""+"%";
		 else
			 registCode="%"+info.getRegistCode()+"%";
		 
		 if(info.getUseNumber()==null)
			 useNumber="%"+""+"%";
		 else
			 useNumber="%"+info.getUseNumber()+"%";
		 
		 System.out.println("��ѯ����registNumber----"+info.getRegistNumber());
		 
		 
		 String conditions="";
		 String conditionsSql="";
		 if(role==2 || role==1){
		     if("".equals(info.getRegistCode())){
		    	 if("".equals(info.getUseNumber())){
		    		 conditions =" t.recordSate = 4 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ?  and (t.registCode like ? or t.registCode is null) and (t.useNumber like ? or t.useNumber is null) ";
					 conditionsSql=" recordSate = 4 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and (registCode like ? or registCode is null) and (useNumber like ? or useNumber is null) ";
			    	 
		    	 }
		    	 else{
		    	    conditions =" t.recordSate = 4 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ?  and (t.registCode like ? or t.registCode is null) and t.useNumber like ? ";
				    conditionsSql=" recordSate = 4 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and (registCode like ? or registCode is null) and useNumber like ? ";
		    	 }
		     }
		     else{
		    	 if("".equals(info.getUseNumber())){
		    		 conditions =" t.recordSate = 4 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ?  and t.registCode like ?  and (t.useNumber like ? or t.useNumber is null) ";
		             conditionsSql=" recordSate = 4 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and registCode like ? and (useNumber like ? or useNumber is null) ";
		    	
		    	 }
		    	 else{
		             conditions =" t.recordSate = 4 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ?  and t.registCode like ? and t.useNumber like ? ";
		             conditionsSql=" recordSate = 4 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and registCode like ?  and useNumber like ? ";
		    	 }
		     }
		 }
		 
		 if(role==22 || role==23){
			 if("".equals(info.getRegistCode())){
				 if("".equals(info.getUseNumber())){
						conditions =" t.recordSate = 4 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ? and (t.registCode like ? or t.registCode is null) and (t.useNumber like ? or t.useNumber is null) ";
						conditionsSql=" recordSate = 4 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and (registCode like ? or registCode is null) and (useNumber like ? or useNumber is null) ";	 
						 
				 }
				 else{
				    conditions =" t.recordSate = 4 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ? and (t.registCode like ? or t.registCode is null) and t.useNumber like ? ";
				    conditionsSql=" recordSate = 4 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and (registCode like ? or registCode is null) and useNumber like ? ";	 
				 }
			 }
			 else{
				 if("".equals(info.getUseNumber())){
			        conditions =" t.recordSate = 4 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ? and t.registCode like ?  and (t.useNumber like ? or t.useNumber is null) ";
			        conditionsSql=" recordSate = 4 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and registCode like ? and (useNumber like ? or useNumber is null) ";
				 }
				 else{
					 conditions =" t.recordSate = 4 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ? and t.registCode like ?  and t.useNumber like ? ";
					 conditionsSql=" recordSate = 4 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and registCode like ? and useNumber like ? ";
						 
				 }
			   }
			 }
		 
		 if(role==10 || role==11){
			 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	    	 zjcompanyId2 = userInfoVO.getCompanyId();
	    	 if("".equals(info.getRegistCode())){
	    		 if("".equals(info.getUseNumber())){
	    			 conditions =" t.recordSate = 4 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ? and (t.registCode like ? or  t.registCode is null) and t.zjcompanyId=? and (t.useNumber like ? or t.useNumber is null) ";
					 conditionsSql=" recordSate = 4 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and (registCode like ? or registCode is null) and zjcompanyId=? and (useNumber like ? or useNumber is null) ";
		    		 
	    		 }
	    		 else{
	    		 conditions =" t.recordSate = 4 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ? and (t.registCode like ? or  t.registCode is null) and t.zjcompanyId=? and t.useNumber like ? ";
				 conditionsSql=" recordSate = 4 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and (registCode like ? or registCode is null) and zjcompanyId=? and useNumber like ? ";
	    		 } 
	    	 }
	    	 else{
	    		 if("".equals(info.getUseNumber())){
			      conditions =" t.recordSate = 4 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ? and t.registCode like ? and t.zjcompanyId=? and (t.useNumber like ? or t.useNumber is null) ";
			      conditionsSql=" recordSate = 4 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and registCode like ? and zjcompanyId=? and (useNumber like ? or useNumber is null) ";
	    	    }
	    		 else{
	    			  conditions =" t.recordSate = 4 and t.registNumber like  ?  and t.area like ? and t.address like ? and t.buildingName like ? and t.registCode like ? and t.zjcompanyId=? and t.useNumber like ? ";
				      conditionsSql=" recordSate = 4 and registNumber like  ?  and area like ? and address like ? and buildingName like ? and registCode like ? and zjcompanyId=?  and useNumber like ? ";
		    	   	 
	    		 }
	    	 }
	     }
		 }
		 Object[] param=null;
		 if(ywCompanyId>0){
			 conditions +=" and t.ywCompanyId = ? ";
			 conditionsSql +=" and ywCompanyId = ?";
			 if(role==2 || role==1)
			 param = new Object[]{registNumber,areaName,address,buildingName,registCode,ywCompanyId,useNumber};
			 if(role==22 || role==23)
			 param = new Object[]{registNumber,areaName,address,buildingName,registCode,ywCompanyId,useNumber};
			 if(role==10 || role==11)
			 param = new Object[]{registNumber,areaName,address,buildingName,registCode,zjcompanyId2,ywCompanyId,useNumber};	 
		 }
		 else{
			 conditions +=" and t.companyName like ? ";
			 conditionsSql +=" and companyName like ?";
			 if(role==2 || role==1)
			 param = new Object[]{registNumber,areaName,address,buildingName,registCode,useNumber,companyName};
			 if(role==22 || role==23)
			 param = new Object[]{registNumber,areaName,address,buildingName,registCode,useNumber,companyName};
			 if(role==10 || role==11)
			  param = new Object[]{registNumber,areaName,address,buildingName,registCode,zjcompanyId2,useNumber,companyName};	 
		 }
		 
		 
		 System.out.println("����ѯ����-----"+conditions);
		 
		
		 
		 String sql ="select t.subPersonID,isnull(t.mobileUploadbeizhu,'') as mobileUploadbeizhu,isnull(t.rukubeizhu,'') as rukubeizhu,t.arrageType,t.pastePersonID,t.pasteTime,t.pasteNote,CONVERT(CHAR(19), t.arrangeTime, 20) as arrangeTime2,t.pasteNote,tad.userName,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.recordSate,t.registCode,t.useNumber,t.inspector,inspectDate,t.nextInspectDate,(select te.userName from twocodeuserExtinfo te where   te.userid =t.arrangePersonID) as arrangePersonName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where "+conditions;
		 
		 
		 long total =DdTwoCodeElevatorYwCompanyInfo.count(DdTwoCodeElevatorYwCompanyInfo.class, conditionsSql, param);
		 System.out.println("total----"+total);
		 
		 List<DdTwoCodeElevatorYwCompanyInfo> items=DdTwoCodeElevatorYwCompanyInfo.findBySql(DdTwoCodeElevatorYwCompanyInfo.class, sql, param, null, rows, (page-1)*rows);
		 
		 if(items != null && items.size()>0){
	    	 Iterator<DdTwoCodeElevatorYwCompanyInfo> it =items.iterator();
	         while(it.hasNext()){
	        	 DdTwoCodeElevatorYwCompanyInfo  ddElevaltorInfoVO = it.next();
	        	 ddElevaltorInfoVO.setSubPersonName("");
	        	 int recordSate  =ddElevaltorInfoVO.getRecordSate();
	        	 if(recordSate >=1){
	        		 int arrageType =ddElevaltorInfoVO.getArrageType();
	        		 int pastePersonID=ddElevaltorInfoVO.getPastePersonID();
	        		 if(arrageType ==1){   //�������˾ճ��������
	        			 CompanyInfo companyInfo=CompanyInfo.findFirst(CompanyInfo.class, "id = ?", new Object[]{pastePersonID});
	        			 if(companyInfo != null)
	        				 ddElevaltorInfoVO.setPastePersonName(companyInfo.getCompanyName());
	        		 }
	        		 else{   //�����ѧ��
	        			 UserExtInfo  userExtInfo=UserExtInfo.findFirst(UserExtInfo.class, "userid = ?", new Object[]{pastePersonID});
	        			 if(userExtInfo != null){
	        				 ddElevaltorInfoVO.setPastePersonName(userExtInfo.getUserName());
	        			 }
	        		 }
	        	 }
	        	 if(recordSate >1){
		        	 int subPersonID =ddElevaltorInfoVO.getSubPersonID();
		        	 UserExtInfo  userExtInfo=UserExtInfo.findFirst(UserExtInfo.class, "userid = ?", new Object[]{subPersonID});
	    			 if(userExtInfo != null)
	    				 ddElevaltorInfoVO.setSubPersonName(userExtInfo.getUserName());
		        	 }
	         }
	      }
		 
		  System.out.println("------"+items.size());
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		 }
	 
	 //���������ж��Ƿ���������������������Ϊ�յĶ���Ϊ�գ�
	 public  boolean  ruKuisvalid(DdElevaltorInfo info){
		 boolean isvalid = false;
		 String registNumber="";
		 String address="";
		 String area="";
		 int townshipStreets=0;
		 String eleType="";
		 String inoutDoor="";
		 int zzCompanyId=0;
		 int wgCompanyId=0;
		 int jyCompanyId=0;
		 int azCompanyId=0;
		 int ywCompanyId=0;
		 int zjCompanyId=0;
		 String registCode ="";
		 
		 registNumber =info.getRegistNumber();
		 address = info.getAddress();
		 area = info.getArea();
		 townshipStreets= info.getTownshipStreets();
		 eleType = info.getEleType();
		 inoutDoor = info.getInoutDoor();
		 zzCompanyId = info.getZzCompanyId();
		 wgCompanyId = info.getWgCompanyId();
		 jyCompanyId = info.getJyCompanyId();
		 azCompanyId = info.getAzCompanyId();
		 ywCompanyId = info.getYwCompanyId();
		 zjCompanyId = info.getZjCompanyId();
		 registCode  = info.getRegistCode();
		 
		 if("".equals(registNumber)){
			 isvalid = false;
			 return isvalid;
		 }
		 else{
			 isvalid = true;
		 }
		 
		 if("".equals(registCode) || registCode == null){
			 isvalid = false;
			 return isvalid;
		 }
		 else{
			 isvalid = true;
		 }
		 
		 if("".equals(address)){
			 isvalid = false;
			 return isvalid;
		 }
		 else{
			 isvalid = true;
		 }
			 
		 if("".equals(area)){
			 isvalid = false;
			 return isvalid;
		 }
		 else{
			 isvalid = true;
		 }
		 
		 
		 if("".equals(eleType) || eleType==null){
			 isvalid = false;
			 return isvalid;
		 }
		 else{
			 isvalid = true;
		 }
		 
		 if("".equals(inoutDoor) || inoutDoor==null){
			 isvalid = false;
			 return isvalid;
		 }
		 else{
			 isvalid = true;
		 }
		 
		 if(townshipStreets==0){
			 isvalid = false;
			 return isvalid;
		 }
		 else{
			 isvalid = true;
		 }
		 
		 if(townshipStreets==0){
			 isvalid = false;
			 return isvalid;
		 }
		 else{
			 isvalid = true;
		 }
		 
		 if(zzCompanyId==0){
			 isvalid = false;
			 return isvalid;
		 }
		 else{
			 isvalid = true;
		 }
		 
		 if(wgCompanyId==0){
			 isvalid = false;
			 return isvalid;
		 }
		 else{
			 isvalid = true;
		 }
		 
		 if(jyCompanyId==0){
			 isvalid = false;
			 return isvalid;
		 }
		 else{
			 isvalid = true;
		 }
		 
		 if(azCompanyId==0){
			 isvalid = false;
			 return isvalid;
		 }
		 else{
			 isvalid = true;
		 }
		 
		 if(ywCompanyId==0){
			 isvalid = false;
			 return isvalid;
		 }
		 else{
			 isvalid = true;
		 }
		 
		 if(zjCompanyId==0){
			 isvalid = false;
			 return isvalid;
		 }
		 else{
			 isvalid = true;
		 }
		 
		 return  isvalid;
	 }
	 
	 public String ddrukucheck(int id){
		 String cityName = GlobalFunction.cityName;
		 String result = "����ʧ��";
		 System.out.println("Ҫ����������IDΪ----"+id);
		 DdElevaltorInfoVO ddElevaltorInfoVO = null;
		 ElevaltorInfoVO elevaltorInfoVO = null;
		 CompanyInfo companyInfo =null;
		 String[] strArea =null;
		 try {
			List<AreaInfo> areaList = AreaInfo.findBySql(AreaInfo.class, "select area from TwoCodeAreaInfo");
			if(areaList != null){
				strArea = new String[areaList.size()];
				for(int i=0; i < areaList.size(); i++){
					strArea[i]=areaList.get(i).getArea();
				}
			}		
		} catch (ActiveRecordException e2) {
			System.out.println("ddrukucheck---�쳣");
			e2.printStackTrace();
		}
		 System.out.println("ddrukucheck---3915---"+Arrays.toString(strArea));
	//	 String[] strArea = new String[]{"����","����","��ţ","�ɻ�","���","����","�츮��","��׽�","��Ȫ��","�¶�","�½�","˫��","ۯ��","����","����","�ѽ�","�½�","������","����","����","����","����","���","����"};
		 String registNumber="";
		 String address="";
		 String area="";
		 String eleType="";
		 String inoutDoor="";
		 String buildingName="";
		 String building="";
		 String unit="";
		 String registCode="";
		 int townshipStreets=0;
		 String[] streleType=new String[]{"ҷ����ǿ����������","ҷ�������˿͵���","ҷ�������ػ�����","ǿ�������ػ�����","Һѹ��������","Һѹ�˿͵���","Һѹ�ػ�����","�Զ��������Զ����е�","�Զ�����","�Զ����е�","�������͵���","��������","����Ա����","�������"};
		 String[] strinoutDoor=new String[]{"����","����"};
		 int zzCompanyId=0;
		 int wgCompanyId=0;
		 int jyCompanyId=0;
		 int azCompanyId=0;
		 int ywCompanyId=0;
		 int zjCompanyId=0;
		 DdAppendixEleInfo ddAppendixEleInfo =null;
		 try {
			ddElevaltorInfoVO = DdElevaltorInfoVO.findFirst(DdElevaltorInfoVO.class, "id=?", new Object[] { id});
			if(ddElevaltorInfoVO != null){
			//���ݱ��
			registNumber=ddElevaltorInfoVO.getRegistNumber();
			elevaltorInfoVO = ElevaltorInfoVO.findFirst(ElevaltorInfoVO.class, "registNumber=?", new Object[] {registNumber});
			if(elevaltorInfoVO != null){  //���ձ���ң��ҵ��ˣ�˵���Ѿ����ڸñ�ż�¼���������
				result = "����Ѿ����ڣ������ظ����";
				DdElevaltorInfoVO.updateAll(DdElevaltorInfoVO.class, "ruKuValid=?,rukubeizhu=?", new Object[] {2,result}, "id=?", new Object[] { id});
				return result;
			}
			
			address =ddElevaltorInfoVO.getAddress();
			buildingName =ddElevaltorInfoVO.getBuildingName();
			building =ddElevaltorInfoVO.getBuilding();
			unit =ddElevaltorInfoVO.getUnit();
			elevaltorInfoVO =ElevaltorInfoVO.findFirst(ElevaltorInfoVO.class, "address=? and buildingName=? and building=? and unit=?", new Object[] {address,buildingName,building,unit});
			if(elevaltorInfoVO != null){  //���������ַ��¥�̣���Ԫ�����ң��ҵ��ˣ�˵���Ѿ����ڸõ�ַ��¼���������
				result = "��ַ��¥�̣���Ԫ�����Ѿ����ڣ������ظ����";
				DdElevaltorInfoVO.updateAll(DdElevaltorInfoVO.class, "ruKuValid=?,rukubeizhu=?", new Object[] {2,result}, "id=?", new Object[] { id});
				return result;
			}
			
			registCode=ddElevaltorInfoVO.getRegistCode();
			elevaltorInfoVO =ElevaltorInfoVO.findFirst(ElevaltorInfoVO.class, "registCode=?", new Object[] {registCode});
			if(elevaltorInfoVO != null){  //���������ַ��¥�̣���Ԫ�����ң��ҵ��ˣ�˵���Ѿ����ڸõ�ַ��¼���������
				result = "�ǼǱ���Ѿ����ڣ������ظ����";
				DdElevaltorInfoVO.updateAll(DdElevaltorInfoVO.class, "ruKuValid=?,rukubeizhu=?", new Object[] {2,result}, "id=?", new Object[] { id});
				return result;
			}
			
			
			//����
			area = ddElevaltorInfoVO.getArea();
			if(strArea != null){
			if(!contains(strArea,area)){
				result = "�������������ڣ��������";
				DdElevaltorInfoVO.updateAll(DdElevaltorInfoVO.class, "ruKuValid=?,rukubeizhu=?", new Object[] {2,result}, "id=?", new Object[] { id});
				return result;
			}
			}
			//�������
			eleType = ddElevaltorInfoVO.getEleType();
			if(!contains(streleType,eleType)){
				result = "����������𲻴��ڣ��������";
				DdElevaltorInfoVO.updateAll(DdElevaltorInfoVO.class, "ruKuValid=?,rukubeizhu=?", new Object[] {2,result}, "id=?", new Object[] { id});
				return result;
			}
			
			//������
			inoutDoor = ddElevaltorInfoVO.getInoutDoor();
			if(!contains(strinoutDoor,inoutDoor)){
				result = "�������������ⲻ���ڣ��������";
				DdElevaltorInfoVO.updateAll(DdElevaltorInfoVO.class, "ruKuValid=?,rukubeizhu=?", new Object[] {2,result}, "id=?", new Object[] { id});
				return result;
			}
			
			//�ֵ���
			townshipStreets =ddElevaltorInfoVO.getTownshipStreets();
			companyInfo = CompanyInfo.findFirst(CompanyInfo.class, "id=?", new Object[] { townshipStreets});
			if(companyInfo == null){
				result = "�����ֵ��첻���ڣ��������";
				DdElevaltorInfoVO.updateAll(DdElevaltorInfoVO.class, "ruKuValid=?,rukubeizhu=?", new Object[] {2,result}, "id=?", new Object[] { id});
				return result;
			}
			
			//���칫˾
			zzCompanyId = ddElevaltorInfoVO.getZzCompanyId();
			companyInfo = CompanyInfo.findFirst(CompanyInfo.class, "id=?", new Object[] { zzCompanyId});
			if(companyInfo == null){
				result = "�������쵥λ�����ڣ��������";
				DdElevaltorInfoVO.updateAll(DdElevaltorInfoVO.class, "ruKuValid=?,rukubeizhu=?", new Object[] {2,result}, "id=?", new Object[] { id});
				return result;
			}
			
			//ʹ�õ�λ
			wgCompanyId = ddElevaltorInfoVO.getZzCompanyId();
			companyInfo = CompanyInfo.findFirst(CompanyInfo.class, "id=?", new Object[] { wgCompanyId});
			if(companyInfo == null){
				result = "����ʹ�õ�λ�����ڣ��������";
				DdElevaltorInfoVO.updateAll(DdElevaltorInfoVO.class, "ruKuValid=?,rukubeizhu=?", new Object[] {2,result}, "id=?", new Object[] { id});
				return result;
			}
			
			//���鵥λ
			jyCompanyId = ddElevaltorInfoVO.getZzCompanyId();
			companyInfo = CompanyInfo.findFirst(CompanyInfo.class, "id=?", new Object[] { jyCompanyId});
			if(companyInfo == null){
				result = "�������鵥λ�����ڣ��������";
				DdElevaltorInfoVO.updateAll(DdElevaltorInfoVO.class, "ruKuValid=?,rukubeizhu=?", new Object[] {2,result}, "id=?", new Object[] { id});
				return result;
			}
			
			//��װ
			azCompanyId = ddElevaltorInfoVO.getZzCompanyId();
			companyInfo = CompanyInfo.findFirst(CompanyInfo.class, "id=?", new Object[] { azCompanyId});
			if(companyInfo == null){
				result = "������װ��λ�����ڣ��������";
				DdElevaltorInfoVO.updateAll(DdElevaltorInfoVO.class, "ruKuValid=?,rukubeizhu=?", new Object[] {2,result}, "id=?", new Object[] { id});
				return result;
			}
			
			//ά��
			ywCompanyId = ddElevaltorInfoVO.getZzCompanyId();
			companyInfo = CompanyInfo.findFirst(CompanyInfo.class, "id=?", new Object[] { ywCompanyId});
			if(companyInfo == null){
				result = "����ά����λ�����ڣ��������";
				DdElevaltorInfoVO.updateAll(DdElevaltorInfoVO.class, "ruKuValid=?,rukubeizhu=?", new Object[] {2,result}, "id=?", new Object[] { id});
				return result;
			}
			
			//�ʼ�
			zjCompanyId = ddElevaltorInfoVO.getZzCompanyId();
			companyInfo = CompanyInfo.findFirst(CompanyInfo.class, "id=?", new Object[] { zjCompanyId});
			if(companyInfo == null){
				result = "�����ʼ൥λ�����ڣ��������";
				DdElevaltorInfoVO.updateAll(DdElevaltorInfoVO.class, "ruKuValid=?,rukubeizhu=?", new Object[] {2,result}, "id=?", new Object[] { id});
				return result;
			}
			
			//���������������ˣ����Խ�����������
			ActiveRecordBase.beginTransaction();
			  if(!"0".equals(cityName)){   //������shibieCode
				ActiveRecordBase.execute("exec pro_copyDdElevatorInfo2 ?", new Object[] { id });
			  }
			  else{
			    ActiveRecordBase.execute("exec pro_copyDdElevatorInfo ?", new Object[] { id });
			  }
			if(!"".equals(registNumber)){
				ddAppendixEleInfo  = DdAppendixEleInfo.findFirst(DdAppendixEleInfo.class,"registNumber = ?", new Object[]{registNumber});
				if(ddAppendixEleInfo != null){  //ɾ��������ǩ���ӱ���Ϣ
					ddAppendixEleInfo.destroy();
				}
			}
			ActiveRecordBase.commit();
			result = "success"; 
			}
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				ActiveRecordBase.rollback();
			} catch (TransactionException e1) {
			}
		}
		 return result;
	 }
	 
	 public String ddhecha(int id){
		 String result = "����ʧ��";
		 System.out.println("Ҫ���к˲����IDΪ----"+id);
		 if(id >0){
		 try {
			int num =DdElevaltorInfoVO.updateAll(DdElevaltorInfoVO.class, "recordSate = ?", new Object[]{4}, "id =?", new Object[]{id});
			if(num >0)
				result ="success";
			
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		 }  
		 return result;
	 } 
	 
	 /**
	  *  �ж�ĳ���ַ����Ƿ������������
	  *  @param stringArray ԭ����
	  *  @param source ���ҵ��ַ���
	  *  @return �Ƿ��ҵ�
	  */
	 public static boolean contains(String[] stringArray, String source) {
	  // ת��Ϊlist
	  List<String> tempList = Arrays.asList(stringArray);
	  
	  // ����list�İ�������,�����ж�
	  if(tempList.contains(source))
	  {
	   return true;
	  } else {
	   return false;
	  }
	 }
	 
	 //����registNumber������ά��Ϣ
	 public View queryYwinfoByReg(String registNumber) throws Exception{
		 System.out.println("ִ��queryYwinfoByReg����");
		 long total=0;
		 YwInfoVO ywInfoVO = null;
	     String sql ="select u.userName,u.contactPhone from YwManagerInfo y left join TwoCodeUserExtInfo u on y.userid = u.userid where y.registNumber =? order by y.id desc";
		 ywInfoVO = YwInfoVO.findFirstBySql(YwInfoVO.class, sql, new Object[] { registNumber });
	      Map<String, Object> result = new HashMap<String, Object>();
	      if(ywInfoVO != null){
	      result.put("����", ywInfoVO.getUserName());
		  result.put("��ϵ�绰", ywInfoVO.getContactPhone());
	      }
	      else{
	    	  result.put("����", "");
			  result.put("��ϵ�绰","");  
	      }
		  return new JsonView(result);
	    }
	 
	 
	 public View sysYwcqSetingsEdit(){
		 Map<String, Object> result = new HashMap<String, Object>();
		 SysSetingsVO sysSetingsVO = null;
		 String sql ="select t.setingsSwitch,isnull(t.startTime,'') as startTime,isnull(t.endTime,'') as endTime   from TwoCodeSysSetings t where t.itemName = ?";
		 try {
			sysSetingsVO = SysSetingsVO.findFirstBySql(SysSetingsVO.class,sql, new Object[]{"0"});
			if(sysSetingsVO !=null){
				 result.put("ywcqswitch", sysSetingsVO.getSetingsSwitch());
				 result.put("qstartTime", sysSetingsVO.getStartTime());
				 result.put("qendTime", sysSetingsVO.getEndTime());
			}
		 }catch (ActiveRecordException e) {
				e.printStackTrace();
			}
		 return new JsonView(result);	
	 }
	 
	 public String sysYwcqSetings(int ywcqswitch,String qstartTime,String qendTime){
		 String result = "failure";
		 int num =0;
		 System.out.println("ywcqswitch--->"+ywcqswitch);
		 System.out.println("qstartTime--->"+qstartTime);
		 System.out.println("qendTime--->"+qendTime);
		 SysSetingsVO sysSetingsVO = null;
		 String sql ="select t.setingsSwitch,isnull(t.startTime,'') as startTime,isnull(t.endTime,'') as endTime   from TwoCodeSysSetings t where t.itemName = ?";
		 try {
			sysSetingsVO = SysSetingsVO.findFirstBySql(SysSetingsVO.class,sql, new Object[]{"0"});
			if(sysSetingsVO ==null){
				SysSetings sysSetings = new SysSetings();
				sysSetings.setItemName("0");
				sysSetings.setSetingsSwitch(ywcqswitch);
				sysSetings.setStartTime(qstartTime);
				sysSetings.setEndTime(qendTime);
				sysSetings.save();
				result ="success";
			}
			else{
				num =SysSetings.updateAll(SysSetings.class, "setingsSwitch=?,startTime=?,endTime=?", new Object[]{ywcqswitch,qstartTime,qendTime}, "itemName=?", new Object[]{"0"});
				if(num>0)	
				result ="success";
			}
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
	
		 return result;
	 }
	 
	 
	 public View ddelevatortasklist(int page, int rows) throws Exception{
		 System.out.println("ִ��ddelevatortasklist����");
	   //   long total = ImageVO.countBySql(ImageVO.class, "exec ���û���ȡͼƬ��Ϣ  ?", new Object[] { "test" });
	   //   long total =ImageVO.execute("exec ���û���ȡͼƬ��Ϣ ?", new Object[] { "test" });
	  //     List<ImageVO>  items = ImageVO.findBySql(ImageVO.class, "exec ���û���ȡͼƬ��Ϣ  ?", new Object[] { "test" });   
	   //    List<ImageVO> items = ImageVO.findBySql(ImageVO.class, "select CARNUM as carnum,DEV_ID as dev_id from image_201105", null, null, rows, (page-1)*rows);
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 System.out.println("ElevatorController---34---��½����---"+userName);
		 System.out.println("ElevatorController---36---role---"+role);
		 long total=0;
		 List<DdElevaltorInfoVO> items = null;
	     if(role==2 || role==1){ //ϵͳ����Ա TwoCodeCompanyInfo 
	    //	 total  =DdElevaltorInfoVO.count(DdElevaltorInfoVO.class, null, null);
	    	 total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class, "select count(*) from TwoCodeDdElevatorInfo where recordSate = ?", new Object[]{0});
	    //	 items = ElevaltorInfoVO.findAll(ElevaltorInfoVO.class, null, null, null, rows, (page-1)*rows);
	    	 String sql ="select tad.pasteTime,tad.userName,tad.subTime as subTime2,t.id,t.shibieCode,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.recordSate =0";
	    	 items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, null, "t.buildingName,t.address,t.useNumber", rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==22 || role==23){ //ϵͳ����Ա TwoCodeCompanyInfo 
	    	 total  =DdElevaltorInfoVO.count(DdElevaltorInfoVO.class, null, null);
	    //	 items = ElevaltorInfoVO.findAll(ElevaltorInfoVO.class, null, null, null, rows, (page-1)*rows);
	    	 String sql ="select tad.pasteTime,tad.userName,tad.subTime as subTime2,t.id,t.shibieCode,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.recordSate =0";
	     	 items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, null, "t.buildingName,t.address,t.useNumber", rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==10 || role ==11){
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	    	 total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class, "select count(*) from TwoCodeElevatorInfo t  where t.zjCompanyId=?", new Object[]{zjcompanyId});
	    	 System.out.println("�����ʼ�ֱ�ǩ������"+total);
	    	 String sql ="select tad.pasteTime,tad.userName,tad.subTime as subTime2,t.id,t.shibieCode,t.registNumber,t.address,t.registCode,t.useNumber,t.buildingName,t.building,t.unit,t.inspector,t.inspectDate,t.nextInspectDate,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.zjCompanyId=? and  t.recordSate =0";
	    	 items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, new Object[]{zjcompanyId}, "t.buildingName,t.address,t.useNumber", rows, (page-1)*rows);	
	    	 }
	     } 
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	 
	 //�������������б�������������ѯ����
	 public View ddtaskquery(DdElevaltorInfoVO info,int page, int rows)throws Exception{
		 
		 System.out.println("ddtaskquery");
		
		 String address ="";
		 String buildingName ="";
		 String building="";
		 String buildingStr ="";
		 String buildingStr2 ="";
		 String unit="";
		 String unitStr ="";
		 String unitStr2 ="";
		 int ywCompanyId =0;
		 
		 if(info.getAddress()==null)
			 address ="%"+""+"%";	 
		 else
		 address ="%"+info.getAddress()+"%";
		 
		 if(info.getBuildingName()==null)
			 buildingName ="%"+""+"%";
		 else
			 buildingName ="%"+info.getBuildingName()+"%";
		 
		 if(info.getBuilding()==null)
			 building="%"+""+"%";
		 else
			 building ="%"+info.getBuilding()+"%";
		 
		 if(info.getUnit()==null)
			 unit="%"+""+"%";
		 else
			 unit ="%"+info.getUnit()+"%";
		 
		 ywCompanyId =info.getYwCompanyId();
		 
		 if("".equals(info.getBuilding()) || info.getBuilding()==null){
			 buildingStr = " (t.building like " +" \'%"+""+"%"+"\'"+" or t.building is null )"; 
			 buildingStr2 = " (building like " +" \'%"+""+"%"+"\'"+" or building is null )"; 
			 
		 }
		 else{
			 buildingStr = " t.building like " +" \'%"+info.getBuilding()+"%\' ";  
			 buildingStr2 = " building like " +" \'%"+info.getBuilding()+"%\' ";  
		 }
		 
		 if("".equals(info.getUnit()) || info.getUnit()==null){
			 unitStr = " (t.unit like " +" \'%"+""+"%"+"\'"+" or t.unit is null )"; 
			 unitStr2 = " (unit like " +" \'%"+""+"%"+"\'"+" or unit is null )"; 
			 
		 }
		 else{
			 unitStr = " t.unit like " +" \'%"+info.getUnit()+"%\' ";  
			 unitStr2 = " unit like " +" \'%"+info.getUnit()+"%\' ";  
		 }
		 String conditions="";
		 String conditionsSql="";
		 Object[] param=null;
		 if(ywCompanyId == 0){
	//	 conditions =" t.recordSate =0 and t.address like  ?  and t.buildingName like ? and t.building like ? and t.unit like ?";
	//	 conditionsSql=" recordSate =0  and address like  ?  and buildingName like ? and building like ? and unit like ?";
		 conditions =" t.recordSate =0 and t.address like  ?  and t.buildingName like ? and " + buildingStr + " and "+unitStr;
		 conditionsSql=" recordSate =0  and address like  ?  and buildingName like ? and " + buildingStr2 + " and "+unitStr2;
		 param = new Object[]{address,buildingName};
		 }
		 else{
	//		 conditions =" t.recordSate =0 and t.address like  ?  and t.buildingName like ? and t.building like ? and t.unit like ? and t.ywCompanyId = ?";
	//		 conditionsSql=" recordSate =0  and address like  ?  and buildingName like ? and building like ? and unit like ? and ywCompanyId = ?";
			 conditions =" t.recordSate =0 and t.address like  ?  and t.buildingName like ? and " + buildingStr + " and "+unitStr+"  and t.ywCompanyId = ?";
			 conditionsSql=" recordSate =0  and address like  ?  and buildingName like ? and " + buildingStr2 + " and "+unitStr2+" and ywCompanyId = ?";
			 param = new Object[]{address,buildingName,ywCompanyId};
		 }
		 
		 
		 System.out.println("����ѯ����-----"+conditions);
		 
		
		 
		 String sql ="select tad.pasteTime,tad.userName,tad.subTime as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.registCode,t.useNumber,t.inspector,inspectDate,t.nextInspectDate,(select tw.shibieCode from TwoCodeDdElevatorInfo tw where tw.id=t.id) as shibieCode,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where  "+conditions;
		 
		 
		 long total =DdTwoCodeElevatorYwCompanyInfo.count(DdTwoCodeElevatorYwCompanyInfo.class, conditionsSql, param);
		 System.out.println("total----"+total);
		 
		 List<DdTwoCodeElevatorYwCompanyInfo> items=DdTwoCodeElevatorYwCompanyInfo.findBySql(DdTwoCodeElevatorYwCompanyInfo.class, sql, param, "t.buildingName,t.address,t.useNumber",rows, (page-1)*rows);
			
		 
		  System.out.println("------"+items.size()); 
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		 }
	 
	 public View getpastePersonList(int page, int rows) throws Exception {
			List<TCUserInfoView> items = TCUserInfoView.findBySql(TCUserInfoView.class,
					"select * from TCUserInfo where type = ?", new Object[] { "ճ��" });
			return new JsonView(items);
		}
	 
	 public String ddtasklistarrange(int arrangePersonID,int arrageType,int pastePersonID,String names){  
		 String result = "failure";
		 try {
			    ActiveRecordBase.beginTransaction();
				ActiveRecordBase.execute("exec pro_ddElevatorTask ?,?,?,?", new Object[] { arrangePersonID,arrageType,pastePersonID,names });
				ActiveRecordBase.commit();
				result ="success";
		      }catch (ActiveRecordException e) {
					e.printStackTrace();
					try {
						ActiveRecordBase.rollback();
					}catch (TransactionException e1) {
						e1.printStackTrace();
					}
				}	
		 
		
		 return result;
	 }
	 
	 public String realationLouPanEdit(String names,String buildingName){  
		 String result = "failure";
		 System.out.println("names:"+names);
		 System.out.println("buildingName:"+buildingName);
		 
		 try {
			    ActiveRecordBase.beginTransaction();
				ActiveRecordBase.execute("exec pro_realationLouPanEdit ?,?", new Object[] { names,buildingName });
				ActiveRecordBase.commit();
				result ="success";
		      }catch (ActiveRecordException e) {
					e.printStackTrace();
					try {
						ActiveRecordBase.rollback();
					}catch (TransactionException e1) {
						e1.printStackTrace();
					}
				}	
		 
		
		 return result;
	 }
	 
	 public String realationAreaEdit(String names,String area){  
		 String result = "failure";
		 System.out.println("names:"+names);
		 System.out.println("area:"+area);
		 
		 try {
			    ActiveRecordBase.beginTransaction();
				ActiveRecordBase.execute("exec pro_realationAreaEdit ?,?", new Object[] { names,area });
				ActiveRecordBase.commit();
				result ="success";
		      }catch (ActiveRecordException e) {
					e.printStackTrace();
					try {
						ActiveRecordBase.rollback();
					}catch (TransactionException e1) {
						e1.printStackTrace();
					}
				}	
		 
		
		 return result;
	 }
	 
	 public String dbrelationrukudelevattor(String names,String names2){
		 String result = "failure";
		 this.rukunames = names;
		 this.rukunames2 = names2;
		
		 try {
			    ActiveRecordBase.beginTransaction();   
				ActiveRecordBase.execute("exec proc_dbrelationrukuddelevator ?", new Object[] { names});
				ActiveRecordBase.commit();    
				result ="success";
				this.rukuresult =result; 
		      }catch (ActiveRecordException e) {
					e.printStackTrace();
					try {
						ActiveRecordBase.rollback();
					}catch (TransactionException e1) {
						e1.printStackTrace();
					}
				}	
		 
		 return result;
	 }
	 
	 public View dbrelationrukudelevattorRet(String names,String names2){
		 String result = "failure";
		 this.rukunames = names;
		 this.rukunames2 = names2;
	//	 String registNumberres ="registNumberres";
	//	 String registCoderes ="registCoderes";
		 List<DbrukuinfoVO> list = null;
		 Map<String, Object> obj = new HashMap<String, Object>();
		 try {  
		//	    ActiveRecordBase.beginTransaction();  
		//		ActiveRecordBase.execute("exec proc_dbrelationrukuddelevatorRet ?,? output,? output ", new Object[] { names,registNumberres,registCoderes});
			    list=DbrukuinfoVO.findBySql(DbrukuinfoVO.class, "exec proc_dbrelationrukuddelevatorRet ?",new Object[]{names});
			    if(list != null && list.size() > 0){
			    	DbrukuinfoVO dbrukuinfoVO = list.get(0);
			    	if(dbrukuinfoVO != null){	
			    		 obj.put("registNumberrestr",dbrukuinfoVO.getRegistNumberrestr());
			    		 obj.put("registCoderestr",dbrukuinfoVO.getRegistCoderestr());
			    	}
			    }
			//	ActiveRecordBase.commit();   
				result ="success";
				
				this.rukuresult =result; 
		      }catch (ActiveRecordException e) {
		    	  e.printStackTrace();
				}	
		 
	//	 return result;
		 return new JsonView(obj);
	 }
	 
	
	 public String relationrukudelevattor(String names,String names2){
			
		 System.out.println("relationrukudelevattor");
		 String result = "failure";
		 this.rukunames = names;
		 this.rukunames2 = names2;
		 String cityName = GlobalFunction.cityName;
		 
		 
		 try { 
			    ActiveRecordBase.beginTransaction();  
			    /*if("1".equals(cityName)){
			    ActiveRecordBase.execute("exec proc_ntrelationrukuddelevator ?,?", new Object[] { names,names2});	
			    }
			    else*/ if(!"0".equals(cityName)){
			    	UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
			    	String userName = "";
			    	String shuserName = "";
			    	int shuserId = 0;
			    	if(userinfo!=null){
			    		userName = userinfo.getLoginName();
			    		}
			    		else{
			    			 Cookie[] cookies =  request.getCookies();
			    				if (cookies != null) {
			    				   for (Cookie c : cookies) {
			    					if (c.getName().equals("userName")) {
			    					    userName = c.getValue();
			    				      }
			    				    }
			    		    }
			    		}
			    	UserInfoVO user =UserInfo.findFirstBySql(UserInfoVO.class, "select t.id as id,te.userName as userName from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id=te.userid where t.loginName= ?",new Object[] { userName });
			    	if(user != null) {
			    		shuserName = user.getUserName();
			    		shuserId = user.getId();
			    	}
			    	ActiveRecordBase.execute("exec proc_newrelationrukuddelevator ?,?,?,?", new Object[] { names,names2,shuserName,shuserId});
			    } else {
			    	ActiveRecordBase.execute("exec proc_relationrukuddelevator ?,?", new Object[] { names,names2});
			    }
				ActiveRecordBase.commit();    
				result ="success";
				this.rukuresult =result; 
		      }catch (ActiveRecordException e) {
					e.printStackTrace();
					try {
						ActiveRecordBase.rollback();
					}catch (TransactionException e1) {
						e1.printStackTrace();
					}
				}	
		 
		 return result;
	 }
	 
	 
	 public String relationrukudelevattor3(String names,String names2){
			
		 System.out.println("xzrelationrukudelevattor");
		 String result = "failure";
		 this.rukunames = names;
		 this.rukunames2 = names2;
		 
		 try { 
			    ActiveRecordBase.beginTransaction();  
				ActiveRecordBase.execute("exec proc_xzrelationrukuddelevator ?,?", new Object[] { names,names2});
				ActiveRecordBase.commit();    
				result ="success";
				this.rukuresult =result; 
		      }catch (ActiveRecordException e) {
					e.printStackTrace();
					try {
						ActiveRecordBase.rollback();
					}catch (TransactionException e1) {
						e1.printStackTrace();
					}
				}	
		 
		 return result;
	 }
	 
	 public String strelationrukudelevattor(String names,String names2){
		 System.out.println("strelationrukudelevattor");
		 String result = "failure";
		 this.rukunames = names;
		 this.rukunames2 = names2;
		
		 try {
			    ActiveRecordBase.beginTransaction();   
				ActiveRecordBase.execute("exec proc_strelationrukuddelevator ?,?", new Object[] { names,names2});
				ActiveRecordBase.commit();    
				result ="success";
				this.rukuresult =result; 
		      }catch (ActiveRecordException e) {
					e.printStackTrace();
					try {
						ActiveRecordBase.rollback();
					}catch (TransactionException e1) {
						e1.printStackTrace();
					}
				}	
		 
		 return result;
	 }
	 

	 
	 public View ddetaskstatisticlist(int page, int rows) throws Exception{
		 System.out.println("ִ��ddetaskstatisticlist����");
		 String cityName = GlobalFunction.cityName;
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 long total=0;
		 List<DdElevaltorInfoVO> items = null;
	     if(role==2 || role==1){ //ϵͳ����Ա TwoCodeCompanyInfo 
	    	 total  =DdElevaltorInfoVO.count(DdElevaltorInfoVO.class, "recordSate >0", null);
	    	 String  sql ="select isnull(t.deviceId2,'') as deviceId2,isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.arrageType,t.recordSate,t.pastePersonID,t.arrangeTime,tad.pasteTime,tad.userName,t.subTime2 as subTime2,t.id,isnull(t.registNumber,'') as registNumber,t.address,t.buildingName,t.building,t.unit,t.registCode,t.useNumber,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,isnull(te.userName,'') as subPersonName  from TwoCodeDdElevatorInfo  t left join TwoCodeUserExtInfo te on t.subPersonID =te.userid left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.recordSate >0";
		    	
	    //	 items = ElevaltorInfoVO.findAll(ElevaltorInfoVO.class, null, null, null, rows, (page-1)*rows);
	   // 	 String sql ="select t.shenhe,t.arrageType,t.recordSate,t.pastePersonID,t.arrangeTime,tad.pasteTime,tad.userName,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.recordSate >0";
	   // 	 String sql ="select isnull(t.deviceId2,'') as deviceId2,isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.arrageType,t.recordSate,t.pastePersonID,t.arrangeTime,tad.pasteTime,tad.userName,t.subTime2 as subTime2,t.id,isnull(t.registNumber,'') as registNumber,t.address,t.buildingName,t.building,t.unit,t.registCode,t.useNumber,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.recordSate >0";
	    	 if("1".equals(cityName)){
	    		 sql ="select isnull(t.shibieCode,'') as shibieCode,isnull(t.deviceId2,'') as deviceId2,isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.arrageType,t.recordSate,t.pastePersonID,t.arrangeTime,tad.pasteTime,tad.userName,t.subTime2 as subTime2,t.id,isnull(t.registNumber,'') as registNumber,t.address,t.buildingName,t.building,t.unit,t.registCode,t.useNumber,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,isnull(te.userName,'') as subPersonName  from TwoCodeDdElevatorInfo  t left join TwoCodeUserExtInfo te on t.subPersonID =te.userid left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.recordSate >0";
	 	    		 
	    	 }
	    	 else{
	    	     sql ="select isnull(t.deviceId2,'') as deviceId2,isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.arrageType,t.recordSate,t.pastePersonID,t.arrangeTime,tad.pasteTime,tad.userName,t.subTime2 as subTime2,t.id,isnull(t.registNumber,'') as registNumber,t.address,t.buildingName,t.building,t.unit,t.registCode,t.useNumber,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,isnull(te.userName,'') as subPersonName  from TwoCodeDdElevatorInfo  t left join TwoCodeUserExtInfo te on t.subPersonID =te.userid left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.recordSate >0";
	    	 }
	    	 items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==22 || role==23){ //ϵͳ����Ա TwoCodeCompanyInfo 
	    	 total  =DdElevaltorInfoVO.count(DdElevaltorInfoVO.class, "t.recordSate >0", null);
	    //	 items = ElevaltorInfoVO.findAll(ElevaltorInfoVO.class, null, null, null, rows, (page-1)*rows);
	   // 	 String sql ="select t.shenhe,t.arrageType,t.recordSate,t.pastePersonID,t.arrangeTime,tad.pasteTime,tad.userName,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.recordSate >0";
	   // 	 String sql ="select  isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.arrageType,t.recordSate,t.pastePersonID,t.arrangeTime,tad.pasteTime,tad.userName,t.subTime2 as subTime2,t.id,isnull(t.registNumber,'') as registNumber,t.address,t.buildingName,t.building,t.unit,t.registCode,t.useNumber,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.recordSate >0";
	    	 String sql ="select  isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.arrageType,t.recordSate,t.pastePersonID,t.arrangeTime,tad.pasteTime,tad.userName,t.subTime2 as subTime2,t.id,isnull(t.registNumber,'') as registNumber,t.address,t.buildingName,t.building,t.unit,t.registCode,t.useNumber,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,isnull(te.userName,'') as subPersonName  from TwoCodeDdElevatorInfo  t left join TwoCodeUserExtInfo te on t.subPersonID =te.userid left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.recordSate >0";
		    	
	    	 items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, null, null, rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==10 || role ==11){
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	    	 total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class, "select count(*) from TwoCodeDdElevatorInfo t  where t.zjCompanyId=? and t.recordSate >0", new Object[]{zjcompanyId});
	    	 System.out.println("�����ʼ�ֱ�ǩ������"+total);
	    //	 String sql ="select t.shenhe,t.arrageType,t.recordSate,t.pastePersonID,t.arrangeTime,tad.pasteTime,tad.userName,t.subTime2 as subTime2,t.id,t.registNumber,t.address,t.registCode,t.useNumber,t.buildingName,t.building,t.unit,t.inspector,t.inspectDate,t.nextInspectDate,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.zjCompanyId=? and  t.recordSate >0";
	   // 	 String sql ="select isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.arrageType,t.recordSate,t.pastePersonID,t.arrangeTime,tad.pasteTime,tad.userName,t.subTime2 as subTime2,t.id,isnull(t.registNumber,'') as registNumber,t.address,t.registCode,t.useNumber,t.buildingName,t.building,t.unit,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from TwoCodeDdElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.zjCompanyId=? and  t.recordSate >0";
	    	 String sql ="select isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.arrageType,t.recordSate,t.pastePersonID,t.arrangeTime,tad.pasteTime,tad.userName,t.subTime2 as subTime2,t.id,isnull(t.registNumber,'') as registNumber,t.address,t.registCode,t.useNumber,t.buildingName,t.building,t.unit,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,isnull(te.userName,'') as subPersonName  from TwoCodeDdElevatorInfo t left join TwoCodeUserExtInfo te on t.subPersonID =te.userid left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.zjCompanyId=? and  t.recordSate >0";
	    	 
	    	 items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, new Object[]{zjcompanyId}, null, rows, (page-1)*rows);	
	    	 }
	     }  
	     
	     if(items != null && items.size()>0){
	    	 Iterator<DdElevaltorInfoVO> it =items.iterator();
	         while(it.hasNext()){
	        	 DdElevaltorInfoVO  ddElevaltorInfoVO = it.next();
	        	 int recordSate  =ddElevaltorInfoVO.getRecordSate();
	        	 if(recordSate >=1){
	        		 int arrageType =ddElevaltorInfoVO.getArrageType();
	        		 int pastePersonID=ddElevaltorInfoVO.getPastePersonID();
	        		 if(arrageType ==1){   //�������˾ճ��������
	        			 CompanyInfo companyInfo=CompanyInfo.findFirst(CompanyInfo.class, "id = ?", new Object[]{pastePersonID});
	        			 if(companyInfo != null)
	        				 ddElevaltorInfoVO.setPastePersonName(companyInfo.getCompanyName());
	        		 }
	        		 else{   //�����ѧ��
	        			 UserExtInfo  userExtInfo=UserExtInfo.findFirst(UserExtInfo.class, "userid = ?", new Object[]{pastePersonID});
	        			 if(userExtInfo != null)
	        				 ddElevaltorInfoVO.setPastePersonName(userExtInfo.getUserName());
	        		 }
	        	 }
	         }
	      }
	     
	      Map<String, Object> result = new HashMap<String, Object>();
	//      List<DdElevaltorInfoVO> items =new ArrayList<DdElevaltorInfoVO>();
	      result.put("total", total);
	 	  result.put("rows", items);
	//      result.put("total", 0);
	// 	  result.put("rows",items.size());
		  return new JsonView(result);
	    }
	 
	 //�������ͳ�ư�����������ѯ����
	 public View ndetaskliststatisticquery(DdElevaltorInfoVO info,int page, int rows){
		 int recordSate =0;  //0:���� 1:δ��� 2�����
		 int arrageType =1;  //0:���� 1:��ά��˾ 2����
		 int pastePersonID =0;
		 String qstartTime ="";
		 String qendTime="";
		 String buildingName="";
		 String area ="";
		 String registNumber ="";
		 int shenhe =0;
		 String deviceId2="0";
		 String cityName = GlobalFunction.cityName;
		 
		 recordSate = info.getRecordSate();
		 arrageType = info.getArrageType();
		 pastePersonID = info.getPastePersonID();
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 buildingName =info.getBuildingName();
		 registNumber =info.getRegistNumber();
		 area  = info.getArea();
		 shenhe =info.getShenhe();
		 deviceId2=info.getDeviceId2();
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		  if(!"".equals(registNumber)){
				 if(!"".equals(conditions)){
					 conditions =conditions+" and t.registNumber like '%"+registNumber+"%'";	
					} 
					else{
					 conditions =" t.registNumber like '%"+registNumber+"%'";	
					}
				 
			 }
		  
		  if(!"".equals(buildingName)){
				 if(!"".equals(conditions)){
					 conditions =conditions+" and t.buildingName like '%"+buildingName+"%'";	
					} 
					else{
						conditions =" t.buildingName like '%"+buildingName+"%'";	
					}
				 
			 }
		  
		  if(!"".equals(qstartTime)){
				 if(!"".equals(conditions)){
					 conditions =conditions+" and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end )  >= '"+qstartTime+"'" ;	 
				 } 
				 else{
					 conditions =" (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end )  >= '"+qstartTime+"'" ;	 
				 }
			 }
			 
			 if(!"".equals(qendTime)){
				 if(!"".equals(conditions)){
					 conditions =conditions+" and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end )  <= '"+qendTime+"'" ;	 
				 } 
				 else{
					 conditions =" (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end )  <= '"+qendTime+"'" ;	 
				 }
			 }
			 
			 if(arrageType >0){
				 if(!"".equals(conditions)){
					 conditions =conditions+" and t.arrageType = "+arrageType;	 
				 } 
				 else{
					 conditions ="t.arrageType = "+arrageType;	  
				 } 
			 }
			 
			 if(pastePersonID >0){
				 if(!"".equals(conditions)){
					 conditions =conditions+" and t.pastePersonID = "+pastePersonID;	 
				 } 
				 else{
					 conditions ="t.pastePersonID = "+pastePersonID;	  
				 } 
			 }
			 
			 if(recordSate ==1){
				 if(!"".equals(conditions)){
					 conditions =conditions+" and t.recordSate = "+recordSate;	 
				 } 
				 else{
					 conditions ="t.recordSate = "+recordSate;	  
				 } 
			 }
			 
			 if(recordSate ==2){
				 if(!"".equals(conditions)){
					 conditions =conditions+" and t.recordSate > 1 ";	 
				 } 
				 else{
					 conditions ="t.recordSate > 1 ";	  
				 } 
			 }
			 
			 if(recordSate ==0){
				 if(!"".equals(conditions)){
					 conditions =conditions+" and t.recordSate > 0 ";	 
				 } 
				 else{
					 conditions ="t.recordSate > 0 ";	  
				 } 
			 }
		  
			 if(!"".equals(area)){
				 if(!"".equals(conditions)){
					 conditions =conditions+" and t.area like '%"+area+"%'";	
					} 
					else{
						conditions =" t.area like '%"+area+"%'";	
					}
				 
			 }
			 
			 if(shenhe < 2){
				 if(!"".equals(conditions)){
					 conditions =conditions+" and t.shenhe = "+shenhe;	 
				 } 
				 else{
					 conditions ="t.shenhe = "+shenhe;	  
				 } 
			 }
			 
			 if("1".equals(deviceId2)){
				 if(!"".equals(conditions)){
					 conditions =conditions+" and (t.deviceId2 is not null and t.deviceId2 !="+"\'"+"\' ) ";	
				 }
				 else{
					 conditions =" (t.deviceId2 is not null and t.deviceId2 !="+"\'"+"\' ) "; 
				 } 
			 }
			 
			 if("2".equals(deviceId2)){
				 if(!"".equals(conditions)){
					 conditions =conditions+" (t.deviceId2 is  null or t.deviceId2 ="+"\'"+"\' ) ";	
				 }
				 else{
					 conditions =" (t.deviceId2 is  null or t.deviceId2 ="+"\'"+"\' ) "; 
				 } 
			 } 
			 
			  if(!"".equals(conditions)){
			//	  sql ="select isnull(t.deviceId2,'') as deviceId2,isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.arrageType,t.recordSate,t.pastePersonID,(case when tad.pasteTime is null then ''  else CONVERT(varchar(100), tad.pasteTime, 20) end) as  pasteTime2,tad.userName,(case when t.subTime2 is null then '' else  CONVERT(varchar(100), t.subTime2, 20) end) as subTime2,t.id,isnull(t.registNumber,'') as registNumber,t.address,t.buildingName,t.building,t.unit,(case when t.arrangeTime is null then ''  else CONVERT(varchar(100), t.arrangeTime, 20) end)  as arrangeTime2,t.recordSate,t.registCode,t.useNumber,(select tc.userName from TCUserInfo tc where   tc.id =t.pastePersonID) as userName,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where  "+ conditions;  
				  if("1".equals(cityName)){
				     sql ="select isnull(t.shibieCode,'') as shibieCode,isnull(t.deviceId2,'') as deviceId2,isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.arrageType,t.recordSate,t.pastePersonID,(case when tad.pasteTime is null then ''  else CONVERT(varchar(100), tad.pasteTime, 20) end) as  pasteTime2,tad.userName,(case when t.subTime2 is null then '' else  CONVERT(varchar(100), t.subTime2, 20) end) as subTime2,t.id,isnull(t.registNumber,'') as registNumber,t.address,t.buildingName,t.building,t.unit,(case when t.arrangeTime is null then ''  else CONVERT(varchar(100), t.arrangeTime, 20) end)  as arrangeTime2,t.recordSate,t.registCode,t.useNumber,(select tc.userName from TCUserInfo tc where   tc.id =t.pastePersonID) as userName,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,isnull(te.userName,'') as subPersonName from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeUserExtInfo te on t.subPersonID =te.userid left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where  "+ conditions;	  
				  }
				  else{
				     sql ="select isnull(t.deviceId2,'') as deviceId2,isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.arrageType,t.recordSate,t.pastePersonID,(case when tad.pasteTime is null then ''  else CONVERT(varchar(100), tad.pasteTime, 20) end) as  pasteTime2,tad.userName,(case when t.subTime2 is null then '' else  CONVERT(varchar(100), t.subTime2, 20) end) as subTime2,t.id,isnull(t.registNumber,'') as registNumber,t.address,t.buildingName,t.building,t.unit,(case when t.arrangeTime is null then ''  else CONVERT(varchar(100), t.arrangeTime, 20) end)  as arrangeTime2,t.recordSate,t.registCode,t.useNumber,(select tc.userName from TCUserInfo tc where   tc.id =t.pastePersonID) as userName,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,isnull(te.userName,'') as subPersonName from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeUserExtInfo te on t.subPersonID =te.userid left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where  "+ conditions;  
				  } 
				  conditionsSql = "select count(*) from DdTwoCodeElevatorYwCompanyInfo  t  where "+ conditions;
		   		 }
		   	else{
		   		  if("1".equals(cityName)){
		   			  sql ="select isnull(t.shibieCode,'') as shibieCode,isnull(t.deviceId2,'') as deviceId2,isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.arrageType,t.recordSate,t.pastePersonID,(case when tad.pasteTime is null then ''  else CONVERT(varchar(100), tad.pasteTime, 20) end) as  pasteTime2,tad.userName,(case when t.subTime2 is null then '' else  CONVERT(varchar(100), t.subTime2, 20) end) as subTime2,t.id,isnull(t.registNumber,'') as registNumber,t.address,t.buildingName,t.building,t.unit,(case when t.arrangeTime is null then ''  else CONVERT(varchar(100), t.arrangeTime, 20) end)  as arrangeTime2,t.recordSate,t.registCode,t.useNumber,(select tc.userName from TCUserInfo tc where   tc.id =t.pastePersonID) as userName,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,isnull(te.userName,'') as subPersonName from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeUserExtInfo te on t.subPersonID =te.userid left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber  ";	 	 
		   		  }
		   		  else{
		   		     sql ="select isnull(t.deviceId2,'') as deviceId2,isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.arrageType,t.recordSate,t.pastePersonID,(case when tad.pasteTime is null then ''  else CONVERT(varchar(100), tad.pasteTime, 20) end) as  pasteTime2,tad.userName,(case when t.subTime2 is null then '' else  CONVERT(varchar(100), t.subTime2, 20) end) as subTime2,t.id,isnull(t.registNumber,'') as registNumber,t.address,t.buildingName,t.building,t.unit,(case when t.arrangeTime is null then ''  else CONVERT(varchar(100), t.arrangeTime, 20) end)  as arrangeTime2,t.recordSate,t.registCode,t.useNumber,(select tc.userName from TCUserInfo tc where   tc.id =t.pastePersonID) as userName,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid,isnull(te.userName,'') as subPersonName from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeUserExtInfo te on t.subPersonID =te.userid left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber  ";
		   		  }
		   		  conditionsSql = "select count(*) from DdTwoCodeElevatorYwCompanyInfo  t  ";
		   					 
		   	} 
			 
			     long total =0;
			     List<DdTwoCodeElevatorYwCompanyInfo> items =null;
				try {
					total = DdTwoCodeElevatorYwCompanyInfo.countBySql(DdTwoCodeElevatorYwCompanyInfo.class, conditionsSql, null);
					items=DdTwoCodeElevatorYwCompanyInfo.findBySql(DdTwoCodeElevatorYwCompanyInfo.class, sql, null, "t.id desc", rows, (page-1)*rows);
					
				} catch (ActiveRecordException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				 	
				 
				 
				  Map<String, Object> result = new HashMap<String, Object>();
				  
				  if(items != null && items.size()>0){ 
				    	 Iterator<DdTwoCodeElevatorYwCompanyInfo> it =items.iterator();
				         while(it.hasNext()){
				        	 DdTwoCodeElevatorYwCompanyInfo  ddElevaltorInfoVO = it.next();
				        	 int recordSate2  =ddElevaltorInfoVO.getRecordSate();
				        	 if(recordSate2 >=1){
				        		 int arrageType2 =ddElevaltorInfoVO.getArrageType();
				        		 int pastePersonID2=ddElevaltorInfoVO.getPastePersonID();
				        		 CompanyInfo companyInfo =null;
				        		 try {
				        		 if(arrageType2 ==1){   //�������˾ճ��������
	                      			companyInfo = CompanyInfo.findFirst(CompanyInfo.class, "id = ?", new Object[]{pastePersonID2});
				        			 if(companyInfo != null)
				        				 ddElevaltorInfoVO.setPastePersonName(companyInfo.getCompanyName());
				        		 }
				        		 else{   //�����ѧ��
				        			 UserExtInfo  userExtInfo=UserExtInfo.findFirst(UserExtInfo.class, "userid = ?", new Object[]{pastePersonID2});
				        			 if(userExtInfo != null)
				        				 ddElevaltorInfoVO.setPastePersonName(userExtInfo.getUserName());
				        		 }
				        		 } catch (ActiveRecordException e) {
										
										e.printStackTrace();
									}
				        	 }
				         } 
				      }
				  
				  result.put("total", total);
				  result.put("rows", items);
				  return new JsonView(result);
	
		 
	 }
	 
	 //�������ͳ�ư�����������ѯ����
	 public View detaskliststatisticquery(DdElevaltorInfoVO info,int page, int rows)throws Exception{
		 
		 int recordSate =0;
		 int arrageType =1;
		 int pastePersonID =0;
		 String qstartTime ="";
		 String qendTime="";
		 String buildingName="";
		 String area ="";
		 int shenhe =0;
		 String deviceId2="0";
		 
		 recordSate = info.getRecordSate();
		 arrageType = info.getArrageType();
		 pastePersonID = info.getPastePersonID();
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 buildingName="%"+info.getBuildingName()+"%";
		 area  = info.getArea();
		 shenhe =info.getShenhe();
		 deviceId2=info.getDeviceId2();
		 
		 String conditions="";
		 String conditionsSql="";
		 Object[] param=null;
		if (recordSate == 0) {  System.out.println("recordSate---"+recordSate);
			if ("".equals(qstartTime) && "".equals(qendTime)) {
				if (pastePersonID > 0) {    System.out.println("pastePersonID---"+pastePersonID);
					if (arrageType > 0) {  
						if("".equals(buildingName)){
						   conditions = "  t.arrageType = ? and t.pastePersonID = ? and (t.buildingName like ? or t.buildingName is null) ";
						   conditionsSql = " arrageType = ? and pastePersonID = ? and (buildingName like ? or buildingName is null) ";
						   param = new Object[] { arrageType, pastePersonID,buildingName};
						}
						else{ System.out.println("buildingName---"+buildingName);
							conditions = "  t.arrageType = ? and t.pastePersonID = ? and t.buildingName like ? ";
							conditionsSql = " arrageType = ? and pastePersonID = ? and buildingName like ?  ";
							param = new Object[] { arrageType, pastePersonID,buildingName};
							
						}
					} else {
						if("".equals(buildingName)){
						   conditions = "   t.pastePersonID = ? and (t.buildingName like ? or t.buildingName is null) ";
						   conditionsSql = "  pastePersonID = ? and (buildingName like ? or buildingName is null) ";
						   param = new Object[] { pastePersonID,buildingName };
						}
						else{
							   conditions = "   t.pastePersonID = ? and t.buildingName like ?  ";
							   conditionsSql = "  pastePersonID = ? and buildingName like ?  ";
							   param = new Object[] { pastePersonID,buildingName };
						
						}
					}
				} else {
					if (arrageType > 0) {
						if("".equals(buildingName)){
						conditions = "  t.arrageType = ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = " arrageType = ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { arrageType,buildingName };
						}
						else{
							conditions = "  t.arrageType = ? and t.buildingName like ?  ";
							conditionsSql = " arrageType = ? and buildingName like ?  ";
							param = new Object[] { arrageType,buildingName };
							
						}
					}
					else{   //��registNumber ռλ
						if("".equals(buildingName)){
					//	conditions = "  t.registNumber like ? ";
					//	conditionsSql = " registNumber like ?  ";
					//	param = new Object[] { "%"+""+"%" };
						}
						else{
					//		conditions = "  t.registNumber like ? and t.buildingName like ? ";
					//		conditionsSql = " registNumber like ? and buildingName like ?  ";
					//		param = new Object[] { "%"+""+"%",buildingName };
							conditions = " t.buildingName like ? ";
							conditionsSql = " buildingName like ?  ";
							param = new Object[] { buildingName };
						
						}
					}

				}
			}
			if ("".equals(qstartTime) && !"".equals(qendTime)) {
				if (pastePersonID > 0) {
					if (arrageType > 0) {
						if("".equals(buildingName)){
				//		conditions = "   t.arrageType = ? and t.pastePersonID = ? and t.subTime2 <= ? and (t.buildingName like ? or t.buildingName is null) ";
				//		conditionsSql = "  arrageType = ? and pastePersonID = ? and subTime2 <= ? and (buildingName like ? or buildingName is null) ";
						conditions = "   t.arrageType = ? and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end )  <= ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = "  arrageType = ? and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end )  <= ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { arrageType, pastePersonID,qendTime,buildingName};
						}
						else{
					//		conditions = "   t.arrageType = ? and t.pastePersonID = ? and t.subTime2 <= ? and t.buildingName like ?  ";
					//		conditionsSql = "  arrageType = ? and pastePersonID = ? and subTime2 <= ? and buildingName like ? ";
							conditions = "   t.arrageType = ? and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ?  ";
							conditionsSql = "  arrageType = ? and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ? ";
							param = new Object[] { arrageType, pastePersonID,qendTime,buildingName};
							
						}
					} else {
						if("".equals(buildingName)){
			//			conditions = " t.pastePersonID = ? and t.subTime2 <= ? and (t.buildingName like ? or t.buildingName is null) ";
			//			conditionsSql = " pastePersonID = ? and subTime2 <= ? and (buildingName like ? or buildingName is null) ";
						conditions = " t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = " pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { pastePersonID, qendTime,buildingName };
						}
						else{
			//				conditions = " t.pastePersonID = ? and t.subTime2 <= ? and t.buildingName like ?  ";
			//				conditionsSql = " pastePersonID = ? and subTime2 <= ? and buildingName like ?  ";
			                conditions = " t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ?  ";
						    conditionsSql = " pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ?  ";
							param = new Object[] { pastePersonID, qendTime,buildingName };
							
						}
					}
				} else {
					if (arrageType > 0) {
						if("".equals(buildingName)){
				//    	conditions = "   t.arrageType = ?  and t.subTime2 <= ? and (t.buildingName like ? or t.buildingName is null) ";
				//		conditionsSql = "  arrageType = ?  and subTime2 <= ? and (buildingName like ? or buildingName is null) ";
					    conditions = "   t.arrageType = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = "  arrageType = ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null) ";		
						param = new Object[] { arrageType, qendTime,buildingName };
						}
						else{
				//			conditions = "   t.arrageType = ?  and t.subTime2 <= ? and t.buildingName like ?  ";
				//			conditionsSql = "  arrageType = ?  and subTime2 <= ? and buildingName like ?  ";
						    conditions = "   t.arrageType = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ?  ";
						    conditionsSql = "  arrageType = ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ?  ";
							param = new Object[] { arrageType, qendTime,buildingName };
								
						}
					} else {
						if("".equals(buildingName)){
				//		conditions = "  t.subTime2 <= ?  and (t.buildingName like ? or t.buildingName is null) ";
			   //		conditionsSql = "  subTime2 <= ? and (buildingName like ? or buildingName is null) ";
						conditions = "  (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ?  and (t.buildingName like ? or t.buildingName is null) ";
					    conditionsSql = "  (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { qendTime,buildingName };
						}
						else{
					//		conditions = "  t.subTime2 <= ?  and t.buildingName like ? ";
					//		conditionsSql = "  subTime2 <= ? and buildingName like ?  ";
     						conditions = "  (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ?  and t.buildingName like ? ";
					        conditionsSql = "  (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ?  ";
							param = new Object[] { qendTime,buildingName };
							
						}
					}
				}
			}
			if (!"".equals(qstartTime) && "".equals(qendTime)) {
				if (pastePersonID > 0) {
					if (arrageType > 0) {
						if("".equals(buildingName)){
				//		conditions = "   t.arrageType = ? and t.pastePersonID = ? and t.subTime2 >= ? and (t.buildingName like ? or t.buildingName is null) ";
				//		conditionsSql = "  arrageType = ? and pastePersonID = ? and subTime2 >= ? and (buildingName like ? or buildingName is null) ";
						conditions = "   t.arrageType = ? and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = "  arrageType = ? and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (buildingName like ? or buildingName is null) ";	
						param = new Object[] { arrageType, pastePersonID,qstartTime,buildingName };
						}
						else{
							conditions = "   t.arrageType = ? and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and t.buildingName like ?  ";
							conditionsSql = "  arrageType = ? and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and buildingName like ?  ";
							param = new Object[] { arrageType, pastePersonID,qstartTime,buildingName };
								
						}
					} else {
						if("".equals(buildingName)){
						conditions = " t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (t.buildingName like ? or t.buildingName is null)";
						conditionsSql = " pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { pastePersonID, qstartTime,buildingName };
						}
						else{
							conditions = " t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and t.buildingName like ? ";
							conditionsSql = " pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and buildingName like ? ";
							param = new Object[] { pastePersonID, qstartTime,buildingName };
							
						}
					}
				} else {
					if (arrageType > 0) {
						if("".equals(buildingName)){
						conditions = "   t.arrageType = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = "  arrageType = ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { arrageType, qstartTime,buildingName };
						}
						else{
							conditions = "   t.arrageType = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and t.buildingName like ? ";
							conditionsSql = "  arrageType = ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and buildingName like ?  ";
							param = new Object[] { arrageType, qstartTime,buildingName };
								
						}
					} else {
						if("".equals(buildingName)){
						conditions = " (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = " (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { qstartTime,buildingName };
						}
						else{
							conditions = " (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and t.buildingName like ?  ";
							conditionsSql = " (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and buildingName like ?  ";
							param = new Object[] { qstartTime,buildingName };
							
						}
					}
				}
			}
			if (!"".equals(qstartTime) && !"".equals(qendTime)) {
				if (pastePersonID > 0) {
					if (arrageType > 0) {
						if("".equals(buildingName)){
						conditions = "   t.arrageType = ? and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = "  arrageType = ? and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null)  ";
						param = new Object[] { arrageType, pastePersonID,qstartTime, qendTime,buildingName };
						}
						else{
							conditions = "   t.arrageType = ? and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ?  ";
							conditionsSql = "  arrageType = ? and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ? ";
							param = new Object[] { arrageType, pastePersonID,qstartTime, qendTime,buildingName };
								
						}
					} else {
						if("".equals(buildingName)){
						conditions = "  t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = "  pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { pastePersonID, qstartTime,qendTime,buildingName };
						}
						else{
							conditions = "  t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ?  ";
							conditionsSql = "  pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ?  ";
							param = new Object[] { pastePersonID, qstartTime,qendTime,buildingName };
								
						}
					}
				} else {
					if (pastePersonID > 0) {
						if("".equals(buildingName)){
						conditions = "   t.arrageType = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = "  arrageType = ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { arrageType, qstartTime, qendTime,buildingName };
						}
						else{
							conditions = "   t.arrageType = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ? ";
							conditionsSql = "  arrageType = ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ?  ";
							param = new Object[] { arrageType, qstartTime, qendTime,buildingName };
								
						}
					} else {
						if("".equals(buildingName)){
						conditions = " (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = "  (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { qstartTime, qendTime,buildingName };
						}
						else{
							conditions = " (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ? ";
							conditionsSql = "  (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ?  ";
							param = new Object[] { qstartTime, qendTime,buildingName };
								
						}
					}
				}
			}
			if("".equals(conditions)){
			conditions = "t.recordSate >0";
			conditionsSql ="recordSate >0";
			}
			else{
				conditions =conditions+" and t.recordSate >0 ";
				conditionsSql ="and recordSate >0 "; 
			}
			
		}
		 
		else if (recordSate == 1) {   System.out.println("recordSate---"+recordSate);
			if ("".equals(qstartTime) && "".equals(qendTime)) { 
				if (pastePersonID > 0) { 
					if (arrageType > 0) { 
						if("".equals(buildingName)){
						conditions = "  t.recordSate = ? and t.arrageType = ? and t.pastePersonID = ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = " recordSate = ? and arrageType = ? and pastePersonID = ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { recordSate, arrageType,pastePersonID,buildingName };
						}
						else{   
							if("".equals(buildingName)){ 
							conditions = "  t.recordSate = ? and t.arrageType = ? and t.pastePersonID = ? and  (t.buildingName like ? or t.buildingName is null) ";
							conditionsSql = " recordSate = ? and arrageType = ? and pastePersonID = ?  and (buildingName like ? or buildingName is null)  ";
							param = new Object[] { recordSate, arrageType,pastePersonID,buildingName };
							}
							else{
								conditions = "  t.recordSate = ? and t.arrageType = ? and t.pastePersonID = ? and t.buildingName like ? ";
								conditionsSql = " recordSate = ? and arrageType = ? and pastePersonID = ? and buildingName like ?   ";
								param = new Object[] { recordSate, arrageType,pastePersonID,buildingName };
									
							}	
						}
					} else {
						if("".equals(buildingName)){
						conditions = "  t.recordSate = ? and t.pastePersonID = ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = " recordSate = ? and pastePersonID = ? and (buildingName like ? or buildingName is null)";
						param = new Object[] { recordSate, pastePersonID,buildingName };
						}
						else{
							conditions = "  t.recordSate = ? and t.pastePersonID = ? and t.buildingName like ?  ";
							conditionsSql = " recordSate = ? and pastePersonID = ? and buildingName like ? ";
							param = new Object[] { recordSate, pastePersonID,buildingName };
								
						}
					}
				} else {
					if (arrageType > 0) {
						if("".equals(buildingName)){
						conditions = "  t.recordSate = ? and t.arrageType = ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = " recordSate = ? and arrageType = ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { recordSate, arrageType,buildingName };
						}
						else{
							conditions = "  t.recordSate = ? and t.arrageType = ? and t.buildingName like ?  ";
							conditionsSql = " recordSate = ? and arrageType = ? and buildingName like ?  ";
							param = new Object[] { recordSate, arrageType,buildingName };
								
						}
					} else {
						if("".equals(buildingName)){
						conditions = "  t.recordSate = ? and (t.buildingName like ? or t.buildingName is null)  ";
						conditionsSql = " recordSate = ? and (buildingName like ? or buildingName is null)  ";
						param = new Object[] { recordSate,buildingName };
						}
						else{
							conditions = "  t.recordSate = ? and t.buildingName like ?  ";
							conditionsSql = " recordSate = ? and buildingName like ?  ";
							param = new Object[] { recordSate,buildingName };
							
						}
					}
				}
			}
			if ("".equals(qstartTime) && !"".equals(qendTime)) {
				if (pastePersonID > 0) {
					if (arrageType > 0) {
						if("".equals(buildingName)){
						conditions = "  t.recordSate = ? and t.arrageType = ? and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and (t.buildingName like ? or t.buildingName is null)  ";
						conditionsSql = " recordSate = ? and arrageType = ? and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { recordSate, arrageType,pastePersonID, qendTime,buildingName };
						}
						else{
							conditions = "  t.recordSate = ? and t.arrageType = ? and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ?  ";
							conditionsSql = " recordSate = ? and arrageType = ? and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ?  ";
							param = new Object[] { recordSate, arrageType,pastePersonID, qendTime,buildingName };
							
						}
					} else {
						if("".equals(buildingName)){
						conditions = "  t.recordSate = ?  and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = " recordSate = ?  and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { recordSate, pastePersonID,qendTime,buildingName };
						}
						else{
							conditions = "  t.recordSate = ?  and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ?  ";
							conditionsSql = " recordSate = ?  and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ?  ";
							param = new Object[] { recordSate, pastePersonID,qendTime,buildingName };
								
						}
					}
				} else {
					if (arrageType > 0) {
						if("".equals(buildingName)){
						conditions = "  t.recordSate = ? and t.arrageType = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = " recordSate = ? and arrageType = ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { recordSate, arrageType, qendTime,buildingName };
						}
						else{
							conditions = "  t.recordSate = ? and t.arrageType = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ? ";
							conditionsSql = " recordSate = ? and arrageType = ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ?  ";
							param = new Object[] { recordSate, arrageType, qendTime,buildingName };
								
						}
					} else {
						if("".equals(buildingName)){
						conditions = "  t.recordSate = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = " recordSate = ?   and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { recordSate, qendTime,buildingName };
						}
						else{
							conditions = "  t.recordSate = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ?  ";
							conditionsSql = " recordSate = ?   and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ?  ";
							param = new Object[] { recordSate, qendTime,buildingName };
							
						}
					}
				}
			}
			if (!"".equals(qstartTime) && "".equals(qendTime)) {
				if (pastePersonID > 0) {
					if (arrageType > 0) {
						if("".equals(buildingName)){
						conditions = "  t.recordSate = ? and t.arrageType = ? and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = " recordSate = ? and arrageType = ? and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { recordSate, arrageType,pastePersonID, qstartTime,buildingName };
						}
						else{
							conditions = "  t.recordSate = ? and t.arrageType = ? and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and t.buildingName like ? ";
							conditionsSql = " recordSate = ? and arrageType = ? and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and buildingName like ? ";
							param = new Object[] { recordSate, arrageType,pastePersonID, qstartTime,buildingName };
								
						}
					} else {
						if("".equals(buildingName)){
						conditions = "  t.recordSate = ?  and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = " recordSate = ? and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { recordSate, pastePersonID,qstartTime,buildingName};
						}
						else{
							conditions = "  t.recordSate = ?  and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and t.buildingName like ? ";
							conditionsSql = " recordSate = ? and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and buildingName like ? ";
							param = new Object[] { recordSate, pastePersonID,qstartTime,buildingName};
								
						}
					}
				} else {
					if (arrageType > 0) {
						if("".equals(buildingName)){
						conditions = "  t.recordSate = ? and t.arrageType = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = " recordSate = ? and arrageType = ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { recordSate, arrageType,qstartTime,buildingName };
						}else{
							conditions = "  t.recordSate = ? and t.arrageType = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and t.buildingName like ?  ";
							conditionsSql = " recordSate = ? and arrageType = ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and buildingName like ?  ";
							param = new Object[] { recordSate, arrageType,qstartTime,buildingName };
								
						}
					} else {
						if("".equals(buildingName)){
						conditions = "  t.recordSate = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = " recordSate = ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { recordSate, qstartTime,buildingName };
						}
						else{
							conditions = "  t.recordSate = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and t.buildingName like ? ";
							conditionsSql = " recordSate = ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and buildingName like ? ";
							param = new Object[] { recordSate, qstartTime,buildingName };
								
						}
					}
				}
			}
			if (!"".equals(qstartTime) && !"".equals(qendTime)) {
				if (pastePersonID > 0) {
					if (arrageType > 0) {
						if("".equals(buildingName)){ System.out.println("3303");
						conditions = "  t.recordSate = ? and t.arrageType = ? and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = " recordSate = ? and arrageType = ? and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { recordSate, arrageType,pastePersonID, qstartTime, qendTime,buildingName };
						}
						else{
							conditions = "  t.recordSate = ? and t.arrageType = ? and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ?  ";
							conditionsSql = " recordSate = ? and arrageType = ? and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ?  ";
							param = new Object[] { recordSate, arrageType,pastePersonID, qstartTime, qendTime,buildingName };
								
						}
					} else {
						if("".equals(buildingName)){
						conditions = "  t.recordSate = ?  and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = " recordSate = ?  and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { recordSate, pastePersonID,qstartTime, qendTime,buildingName };
						}
						else{
							conditions = "  t.recordSate = ?  and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ? ";
							conditionsSql = " recordSate = ?  and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ? ";
							param = new Object[] { recordSate, pastePersonID,qstartTime, qendTime,buildingName };
								
						}
					}
				} else {
					if (arrageType > 0) {
						if("".equals(buildingName)){
						conditions = "  t.recordSate = ? and t.arrageType = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = " recordSate = ? and arrageType = ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { recordSate, arrageType,qstartTime, qendTime,buildingName };
						}else{
							conditions = "  t.recordSate = ? and t.arrageType = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ?  ";
							conditionsSql = " recordSate = ? and arrageType = ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ?  ";
							param = new Object[] { recordSate, arrageType,qstartTime, qendTime,buildingName };
								
						}
					} else {
						if("".equals(buildingName)){ System.out.println("3340");
						conditions = "  t.recordSate = ?   and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ?  and (t.buildingName like ? or t.buildingName is null) ";
						conditionsSql = " recordSate = ?   and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null) ";
						param = new Object[] { recordSate, qstartTime, qendTime,buildingName };
						}
						else{
							conditions = "  t.recordSate = ?   and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ?  and t.buildingName like ?  ";
							conditionsSql = " recordSate = ?   and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ?  ";
							param = new Object[] { recordSate, qstartTime, qendTime,buildingName };
								
						}
					}
				}
			}

		}
		 else{    System.out.println("recordSate---"+recordSate);
			 if("".equals(qstartTime) && "".equals(qendTime)){ 
				 if(pastePersonID > 0){
					 if(arrageType > 0){
						 if("".equals(buildingName)){
		                conditions ="  t.recordSate >= ? and t.arrageType = ? and t.pastePersonID = ? and (t.buildingName like ? or t.buildingName is null) ";
		                conditionsSql="  recordSate >= ? and arrageType = ? and pastePersonID = ? and (buildingName like ? or buildingName is null) ";
		                param = new Object[]{recordSate,arrageType,pastePersonID,buildingName}; 
		                }
						 else{    System.out.println("2972");
						     conditions ="  t.recordSate >= ? and t.arrageType = ? and t.pastePersonID = ? and t.buildingName like ?  ";
				                conditionsSql="  recordSate >= ? and arrageType = ? and pastePersonID = ? and buildingName like ? ";
				                param = new Object[]{recordSate,arrageType,pastePersonID,buildingName}; 
				              	 
						 }
		       }
					 else{
						 if("".equals(buildingName)){
						   conditions ="  t.recordSate >= ?  and t.pastePersonID = ? and (t.buildingName like ? or t.buildingName is null)";
					       conditionsSql="  recordSate >= ?  and pastePersonID = ? and (buildingName like ? or buildingName is null) ";
					       param = new Object[]{recordSate,pastePersonID,buildingName}; 
					       }
						 else{
							  conditions ="  t.recordSate >= ?  and t.pastePersonID = ? and t.buildingName like ? ";
						       conditionsSql="  recordSate >= ?  and pastePersonID = ? and buildingName like ?  ";
						       param = new Object[]{recordSate,pastePersonID,buildingName}; 
						       
						 }
					 }
				 }
				 else{
					 if(arrageType > 0){
					   if("".equals(buildingName)){
					   conditions ="  t.recordSate >= ? and t.arrageType = ? and (t.buildingName like ? or t.buildingName is null)";
				       conditionsSql="  recordSate >= ? and arrageType = ? and (buildingName like ? or buildingName is null) ";
				       param = new Object[]{recordSate,arrageType,buildingName};
				       }
					   else{
						   conditions ="  t.recordSate >= ? and t.arrageType = ? and t.buildingName like ? ";
					       conditionsSql="  recordSate >= ? and arrageType = ? and buildingName like ?  ";
					       param = new Object[]{recordSate,arrageType,buildingName};
					         
					   }
				       }
					 else{
						 if("".equals(buildingName)){
						   conditions ="  t.recordSate >= ? and (t.buildingName like ? or t.buildingName is null) ";
					       conditionsSql="  recordSate >= ? and (buildingName like ? or buildingName is null) ";
					       param = new Object[]{recordSate,buildingName}; 
					       }
						 else{
							   conditions ="  t.recordSate >= ? and t.buildingName like ? ";
						       conditionsSql="  recordSate >= ? and buildingName like ? ";
						       param = new Object[]{recordSate,buildingName}; 
						       
						 }
					 }
				 }
		     }
			 if("".equals(qstartTime) && !"".equals(qendTime)){
				 if(pastePersonID > 0){
					 if(arrageType > 0){
						 if("".equals(buildingName)){
				          conditions ="  t.recordSate >= ? and t.arrageType = ? and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and (t.buildingName like ? or t.buildingName is null) ";
				          conditionsSql=" recordSate >= ? and arrageType = ? and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null) ";
				          param = new Object[]{recordSate,arrageType,pastePersonID,qendTime,buildingName};
				   }
						 else{
							  conditions ="  t.recordSate >= ? and t.arrageType = ? and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ?  ";
					          conditionsSql=" recordSate >= ? and arrageType = ? and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ?  ";
					          param = new Object[]{recordSate,arrageType,pastePersonID,qendTime,buildingName};
						 
						 }
				   }
					 else{
						 if("".equals(buildingName)){
						   conditions ="  t.recordSate >= ?  and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and (t.buildingName like ? or t.buildingName is null) ";
						   conditionsSql=" recordSate >= ?  and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null) ";
						   param = new Object[]{recordSate,pastePersonID,qendTime,buildingName}; 
						   }
						 else{
							  conditions ="  t.recordSate >= ?  and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ?  ";
							   conditionsSql=" recordSate >= ?  and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ?  ";
							   param = new Object[]{recordSate,pastePersonID,qendTime,buildingName}; 
							    
						 }
					 }
				  }
				 else{
					  if(arrageType > 0){
						  if("".equals(buildingName)){
					   conditions ="  t.recordSate >= ? and t.arrageType = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and (t.buildingName like ? or t.buildingName is null) ";
					   conditionsSql=" recordSate >= ? and arrageType = ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null) ";
					   param = new Object[]{recordSate,arrageType,qendTime,buildingName};
					   }
						  else{
							   conditions ="  t.recordSate >= ? and t.arrageType = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ?  ";
							   conditionsSql=" recordSate >= ? and arrageType = ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ?  ";
							   param = new Object[]{recordSate,arrageType,qendTime,buildingName};
							 	  
						  }
					   }
					  else{
						  if("".equals(buildingName)){
						  conditions ="  t.recordSate >= ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and (t.buildingName like ? or t.buildingName is null) ";
						   conditionsSql=" recordSate >= ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null) ";
						   param = new Object[]{recordSate,qendTime,buildingName};
						   }
						  else{
							  conditions ="  t.recordSate >= ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ? ";
							   conditionsSql=" recordSate >= ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ?  ";
							   param = new Object[]{recordSate,qendTime,buildingName};
							  	  
						  }
					  }
				 }
				 }
				 if(!"".equals(qstartTime) && "".equals(qendTime)){
					 if(pastePersonID > 0){
						 if(arrageType > 0){
							 if("".equals(buildingName)){
				                conditions ="  t.recordSate>= ? and t.arrageType = ? and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (t.buildingName like ? or t.buildingName is null) ";
				                conditionsSql=" recordSate >= ? and arrageType = ? and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (buildingName like ? or buildingName is null) ";
				                param = new Object[]{recordSate,arrageType,pastePersonID,qstartTime,buildingName};
				       }
							 else{
								    conditions ="  t.recordSate>= ? and t.arrageType = ? and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and t.buildingName like ?  ";
								    conditionsSql=" recordSate >= ? and arrageType = ? and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and buildingName like ?  ";
								    param = new Object[]{recordSate,arrageType,pastePersonID,qstartTime,buildingName};
								     	 
							 }
				   }
						 else{
							 if("".equals(buildingName)){
							   conditions ="  t.recordSate>= ?  and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (t.buildingName like ? or t.buildingName is null) ";
							   conditionsSql=" recordSate >= ?  and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (buildingName like ? or buildingName is null) ";
							   param = new Object[]{recordSate,pastePersonID,qstartTime,buildingName}; 
							   }
							 else{
								 conditions ="  t.recordSate>= ?  and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and t.buildingName like ?  ";
								   conditionsSql=" recordSate >= ?  and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and buildingName like ?  ";
								   param = new Object[]{recordSate,pastePersonID,qstartTime,buildingName}; 
								  	 
							 }
						 }
					 }
					 else{
						 if(arrageType > 0){
							 if("".equals(buildingName)){
						     conditions ="  t.recordSate>= ? and t.arrageType = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (t.buildingName like ? or t.buildingName is null) ";
						     conditionsSql=" recordSate >= ? and arrageType = ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (buildingName like ? or buildingName is null) ";
						     param = new Object[]{recordSate,arrageType,qstartTime,buildingName}; 
						   }
							 else{
								  conditions ="  t.recordSate>= ? and t.arrageType = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and t.buildingName like ?  ";
								     conditionsSql=" recordSate >= ? and arrageType = ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and buildingName like ? ";
								     param = new Object[]{recordSate,arrageType,qstartTime,buildingName}; 
								 	 
							 }
						   }
						 else{
							 if("".equals(buildingName)){
							   conditions ="  t.recordSate>= ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ?  and (t.buildingName like ? or t.buildingName is null) ";
							   conditionsSql=" recordSate >= ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (buildingName like ? or buildingName is null) ";
							   param = new Object[]{recordSate,qstartTime,buildingName};
							   }
							 else{
								  conditions ="  t.recordSate>= ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ?  and t.buildingName like ?  ";
								   conditionsSql=" recordSate >= ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and buildingName like ? ";
								   param = new Object[]{recordSate,qstartTime,buildingName};
								  	 
							 }
						 }
					 }
				 }
				 if(!"".equals(qstartTime) && !"".equals(qendTime)){
					 if(pastePersonID > 0){
						 if(arrageType > 0){
							 if("".equals(buildingName)){
				            conditions ="  t.recordSate >= ? and t.arrageType = ? and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and (t.buildingName like ? or t.buildingName is null)";
				            conditionsSql=" recordSate >= ? and arrageType = ? and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ?  and (buildingName like ? or buildingName is null)";
				            param = new Object[]{recordSate,arrageType,pastePersonID,qstartTime,qendTime,buildingName}; 
				           }else{
				        	   conditions ="  t.recordSate >= ? and t.arrageType = ? and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ? ";
					            conditionsSql=" recordSate >= ? and arrageType = ? and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ?  and buildingName like ? ";
					            param = new Object[]{recordSate,arrageType,pastePersonID,qstartTime,qendTime,buildingName}; 
					             
				           }
				         }
						 else{
							 if("".equals(buildingName)){
							   conditions ="  t.recordSate >= ?  and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and (t.buildingName like ? or t.buildingName is null) ";
							   conditionsSql=" recordSate >= ?  and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null) ";
							   param = new Object[]{recordSate,pastePersonID,qstartTime,qendTime,buildingName};
							   }
							 else{
								   conditions ="  t.recordSate >= ?  and t.pastePersonID = ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ?  ";
								   conditionsSql=" recordSate >= ?  and pastePersonID = ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ?  ";
								   param = new Object[]{recordSate,pastePersonID,qstartTime,qendTime,buildingName};
								  	 
							 }
						 }
					 }
					 else{
						 if(arrageType > 0){
							 if("".equals(buildingName)){
						   conditions ="  t.recordSate >= ? and t.arrageType = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and (t.buildingName like ? or t.buildingName is null) ";
						   conditionsSql=" recordSate >= ? and arrageType = ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null) ";
						   param = new Object[]{recordSate,arrageType,qstartTime,qendTime,buildingName};
						   }
							 else{
								 conditions ="  t.recordSate >= ? and t.arrageType = ?  and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ?  ";
								   conditionsSql=" recordSate >= ? and arrageType = ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ?  ";
								   param = new Object[]{recordSate,arrageType,qstartTime,qendTime,buildingName};
								  	 
							 }
						   }
						 else{
							 if("".equals(buildingName)){
							 conditions ="  t.recordSate >= ?   and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and (t.buildingName like ? or t.buildingName is null) ";
							   conditionsSql=" recordSate >= ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and (buildingName like ? or buildingName is null) ";
							   param = new Object[]{recordSate,qstartTime,qendTime,buildingName};
							   }
							 else{
								 conditions ="  t.recordSate >= ?   and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) > ? and (case when t.recordSate = 1 then t.arrangeTime  else t.subTime2  end ) <= ? and t.buildingName like ?  ";
								   conditionsSql=" recordSate >= ?  and (case when recordSate = 1 then arrangeTime  else subTime2  end ) > ? and (case when recordSate = 1 then arrangeTime  else subTime2  end ) <= ? and buildingName like ?  ";
								   param = new Object[]{recordSate,qstartTime,qendTime,buildingName};
								  	 
							 }
						 }
					 }
				 }
					 
		 }
		 
		 System.out.println("����ѯ����-----"+conditions);
		 
		 String sql="";
		 if(arrageType==1)
		// sql ="select t.arrageType,t.recordSate,t.pastePersonID,(case when tad.pasteTime is null then ''  else CONVERT(varchar(100), tad.pasteTime, 20) end) as  pasteTime2,tad.userName,(case when t.subTime2 is null then '' else  CONVERT(varchar(100), t.subTime2, 20) end) as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,(case when t.arrangeTime is null then ''  else CONVERT(varchar(100), t.arrangeTime, 20) end)  as arrangeTime2,t.recordSate,t.registCode,t.useNumber,t.inspector,inspectDate,t.nextInspectDate,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.pastePersonID and tc.type ='ά��') as userName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where "+conditions;
		// sql ="select t.arrageType,t.recordSate,t.pastePersonID,(case when tad.pasteTime is null then ''  else CONVERT(varchar(100), tad.pasteTime, 20) end) as  pasteTime2,tad.userName,(case when t.subTime2 is null then '' else  CONVERT(varchar(100), t.subTime2, 20) end) as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,(case when t.arrangeTime is null then ''  else CONVERT(varchar(100), t.arrangeTime, 20) end)  as arrangeTime2,t.recordSate,t.registCode,t.useNumber,t.inspector,inspectDate,t.nextInspectDate,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.pastePersonID and tc.type ='ά��') as userName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where "+conditions;
		   sql ="select isnull(t.deviceId2,'') as deviceId2,isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.arrageType,t.recordSate,t.pastePersonID,(case when tad.pasteTime is null then ''  else CONVERT(varchar(100), tad.pasteTime, 20) end) as  pasteTime2,tad.userName,(case when t.subTime2 is null then '' else  CONVERT(varchar(100), t.subTime2, 20) end) as subTime2,t.id,isnull(t.registNumber,'') as registNumber,t.address,t.buildingName,t.building,t.unit,(case when t.arrangeTime is null then ''  else CONVERT(varchar(100), t.arrangeTime, 20) end)  as arrangeTime2,t.recordSate,t.registCode,t.useNumber,(select tc.userName from TCUserInfo tc where   tc.id =t.pastePersonID) as userName,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where "+conditions;
			 else
	   //	 sql ="select t.arrageType,t.recordSate,t.pastePersonID,(case when tad.pasteTime is null then ''  else CONVERT(varchar(100), tad.pasteTime, 20) end) as  pasteTime2,(case when t.subTime2 is null then '' else  CONVERT(varchar(100), t.subTime2, 20) end) as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,(case when t.arrangeTime is null then ''  else CONVERT(varchar(100), t.arrangeTime, 20) end)  as arrangeTime2,t.recordSate,t.registCode,t.useNumber,t.inspector,inspectDate,t.nextInspectDate,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select tc.userName from TCUserInfo tc where   tc.id =t.pastePersonID) as userName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where "+conditions;
			sql ="select isnull(t.deviceId2,'') as deviceId2,isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.arrageType,t.recordSate,t.pastePersonID,(case when tad.pasteTime is null then ''  else CONVERT(varchar(100), tad.pasteTime, 20) end) as  pasteTime2,(case when t.subTime2 is null then '' else  CONVERT(varchar(100), t.subTime2, 20) end) as subTime2,t.id,isnull(t.registNumber,'') as registNumber,t.address,t.buildingName,t.building,t.unit,(case when t.arrangeTime is null then ''  else CONVERT(varchar(100), t.arrangeTime, 20) end)  as arrangeTime2,t.recordSate,t.registCode,t.useNumber,(select tc.userName from TCUserInfo tc where   tc.id =t.pastePersonID) as userName,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where "+conditions;
		     
		 if(!"".equals(area)){
			 if(!"".equals(conditions))
		//	 sql ="select t.arrageType,t.recordSate,t.pastePersonID,(case when tad.pasteTime is null then ''  else CONVERT(varchar(100), tad.pasteTime, 20) end) as  pasteTime2,(case when t.subTime2 is null then '' else  CONVERT(varchar(100), t.subTime2, 20) end) as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,(case when t.arrangeTime is null then ''  else CONVERT(varchar(100), t.arrangeTime, 20) end)  as arrangeTime2,t.recordSate,t.registCode,t.useNumber,t.inspector,inspectDate,t.nextInspectDate,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select tc.userName from TCUserInfo tc where   tc.id =t.pastePersonID) as userName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.area = "+"\'"+area+"\'"+" and  "+conditions;
			 sql ="select isnull(t.deviceId2,'') as deviceId2,isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.arrageType,t.recordSate,t.pastePersonID,(case when tad.pasteTime is null then ''  else CONVERT(varchar(100), tad.pasteTime, 20) end) as  pasteTime2,(case when t.subTime2 is null then '' else  CONVERT(varchar(100), t.subTime2, 20) end) as subTime2,t.id,isnull(t.registNumber,'') as registNumber,t.address,t.buildingName,t.building,t.unit,(case when t.arrangeTime is null then ''  else CONVERT(varchar(100), t.arrangeTime, 20) end)  as arrangeTime2,t.recordSate,t.registCode,t.useNumber,(select tc.userName from TCUserInfo tc where   tc.id =t.pastePersonID) as userName,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.area = "+"\'"+area+"\'"+" and  "+conditions;
			 else
		//	 sql ="select t.arrageType,t.recordSate,t.pastePersonID,(case when tad.pasteTime is null then ''  else CONVERT(varchar(100), tad.pasteTime, 20) end) as  pasteTime2,(case when t.subTime2 is null then '' else  CONVERT(varchar(100), t.subTime2, 20) end) as subTime2,t.id,t.registNumber,t.address,t.buildingName,t.building,t.unit,(case when t.arrangeTime is null then ''  else CONVERT(varchar(100), t.arrangeTime, 20) end)  as arrangeTime2,t.recordSate,t.registCode,t.useNumber,t.inspector,inspectDate,t.nextInspectDate,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select tc.userName from TCUserInfo tc where   tc.id =t.pastePersonID) as userName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.area = "+"\'"+area+"\'";
			 sql ="select isnull(t.deviceId2,'') as deviceId2,isnull(t.picregistNumber,'') as picregistNumber,t.shenhe,t.arrageType,t.recordSate,t.pastePersonID,(case when tad.pasteTime is null then ''  else CONVERT(varchar(100), tad.pasteTime, 20) end) as  pasteTime2,(case when t.subTime2 is null then '' else  CONVERT(varchar(100), t.subTime2, 20) end) as subTime2,t.id,isnull(t.registNumber,'') as registNumber,t.address,t.buildingName,t.building,t.unit,(case when t.arrangeTime is null then ''  else CONVERT(varchar(100), t.arrangeTime, 20) end)  as arrangeTime2,t.recordSate,t.registCode,t.useNumber,(select tc.userName from TCUserInfo tc where   tc.id =t.pastePersonID) as userName,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,t.ruKuValid  from DdTwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber left join TwoCodeDdEleappendix tad on t.registNumber = tad.registNumber where t.area = "+"\'"+area+"\'";
				
			 if(!"".equals(conditionsSql))
				 conditions =conditions+"and area = ? ";
		 }
		 
		 if(shenhe != 2){
			if(sql.contains("where")){
				sql =sql+" and t.shenhe = "+shenhe;	
			} 
			else{
				sql =sql + " where t.shenhe = "+shenhe;	
			}
		 }
		 
		 if("1".equals(deviceId2)){
			 if(sql.contains("where")){
					sql =sql+" and (t.deviceId2 is not null and t.deviceId2 !="+"\'"+"\' ) ";	
				} 
				else{
					sql =sql + " where (t.deviceId2 is not null and t.deviceId2 !="+"\'"+"\' ) ";
				}
		 }
		 
		 if("2".equals(deviceId2)){
			 if(sql.contains("where")){
					sql =sql+" and (t.deviceId2 is  null or t.deviceId2 ="+"\'"+"\' ) ";	
				} 
				else{
					sql =sql + " where (t.deviceId2 is  null or t.deviceId2 ="+"\'"+"\' ) ";
				}
		 }
	//	 long total =DdTwoCodeElevatorYwCompanyInfo.count(DdTwoCodeElevatorYwCompanyInfo.class, conditionsSql, param);
		 List<DdTwoCodeElevatorYwCompanyInfo> items3=DdTwoCodeElevatorYwCompanyInfo.findBySql(DdTwoCodeElevatorYwCompanyInfo.class, sql, param, null);
		 long total =items3.size();
		 System.out.println("total----"+total);
		 
		 List<DdTwoCodeElevatorYwCompanyInfo> items =null;
		 if(shenhe != 2)
			 items=DdTwoCodeElevatorYwCompanyInfo.findBySql(DdTwoCodeElevatorYwCompanyInfo.class, sql, param, "t.shenheTime",rows, (page-1)*rows);	 
		 else
		     items=DdTwoCodeElevatorYwCompanyInfo.findBySql(DdTwoCodeElevatorYwCompanyInfo.class, sql, param, "t.registNumber",rows, (page-1)*rows);
			
		  System.out.println("3546");
		  System.out.println("------"+items.size()); 
		  Map<String, Object> result = new HashMap<String, Object>();
		  
		  if(items != null && items.size()>0){ 
		    	 Iterator<DdTwoCodeElevatorYwCompanyInfo> it =items.iterator();
		         while(it.hasNext()){
		        	 DdTwoCodeElevatorYwCompanyInfo  ddElevaltorInfoVO = it.next();
		        	 int recordSate2  =ddElevaltorInfoVO.getRecordSate();
		        	 if(recordSate2 >=1){
		        		 int arrageType2 =ddElevaltorInfoVO.getArrageType();
		        		 int pastePersonID2=ddElevaltorInfoVO.getPastePersonID();
		        		 
		        		 if(arrageType2 ==1){   //�������˾ճ��������
		        			 CompanyInfo companyInfo=CompanyInfo.findFirst(CompanyInfo.class, "id = ?", new Object[]{pastePersonID2});
		        			 if(companyInfo != null)
		        				 ddElevaltorInfoVO.setPastePersonName(companyInfo.getCompanyName());
		        		 }
		        		 else{   //�����ѧ��
		        			 UserExtInfo  userExtInfo=UserExtInfo.findFirst(UserExtInfo.class, "userid = ?", new Object[]{pastePersonID2});
		        			 if(userExtInfo != null)
		        				 ddElevaltorInfoVO.setPastePersonName(userExtInfo.getUserName());
		        		 }
		        		 
		        	 }
		         } 
		      }
		  
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		 }
	 
	 public View jyinfotishi(String registNumber){
		 Map<String, Object> result = new HashMap<String, Object>();
		
		 String jyCompanyName="";
		 String inspector="";
		 String checkReportNum="";
		 String checkCategory="";
		 String checkResult="";
		 String inspectDate="";
		 String nextInspectDate="";
		 String sql ="";
		 
		 if(!"".equals(registNumber)){
			 DdElevaltorInfoVO ddElevaltorInfoVO =null;
			 try {
			   sql ="select  (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,isnull(t.inspector,'') as inspector,isnull(t.checkReportNum,'') as checkReportNum,isnull(t.checkCategory,'') as checkCategory,isnull(t.checkResult,'') as checkResult,isnull(t.inspectDate,'') as inspectDate, isnull(t.nextInspectDate,'') as nextInspectDate from TwoCodeElevatorInfo t   where t.registNumber = ? ";
				ddElevaltorInfoVO  =  DdElevaltorInfoVO.findFirstBySql(DdElevaltorInfoVO.class, sql, new Object[]{registNumber});
				if(ddElevaltorInfoVO != null){
					jyCompanyName = ddElevaltorInfoVO.getJyCompanyName();
					inspector = ddElevaltorInfoVO.getInspector();
					checkReportNum =ddElevaltorInfoVO.getCheckReportNum();
					checkCategory = ddElevaltorInfoVO.getCheckCategory();
					checkResult=ddElevaltorInfoVO.getCheckResult();
					inspectDate =ddElevaltorInfoVO.getInspectDate();
					nextInspectDate=ddElevaltorInfoVO.getNextInspectDate();
				}
				else{
					sql ="select  (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,isnull(t.inspector,'') as inspector,isnull(t.checkReportNum,'') as checkReportNum,isnull(t.checkCategory,'') as checkCategory,isnull(t.checkResult,'') as checkResult,isnull(t.inspectDate,'') as inspectDate, isnull(t.nextInspectDate,'') as nextInspectDate from TwoCodeddElevatorInfo t   where t.registNumber = ? ";
					ddElevaltorInfoVO  =  DdElevaltorInfoVO.findFirstBySql(DdElevaltorInfoVO.class, sql, new Object[]{registNumber});
					if(ddElevaltorInfoVO != null){
						jyCompanyName = ddElevaltorInfoVO.getJyCompanyName();
						inspector = ddElevaltorInfoVO.getInspector();
						checkReportNum =ddElevaltorInfoVO.getCheckReportNum();
						checkCategory = ddElevaltorInfoVO.getCheckCategory();
						checkResult=ddElevaltorInfoVO.getCheckResult();
						inspectDate =ddElevaltorInfoVO.getInspectDate();
						nextInspectDate=ddElevaltorInfoVO.getNextInspectDate();	
					}
				}
				 result.put("jyCompanyName", jyCompanyName);
				 result.put("inspector", inspector);
				 result.put("checkReportNum", checkReportNum);
				 result.put("checkCategory", checkCategory);
				 result.put("checkResult",checkResult);
				 result.put("inspectDate",inspectDate);
				 result.put("nextInspectDate",nextInspectDate);
			} catch (ActiveRecordException e) {
				e.printStackTrace();
			}
			
		 }
		 else{
		 result.put("jyCompanyName", "");
		 result.put("inspector", "");
		 result.put("checkReportNum", "");
		 result.put("checkCategory", "");
		 result.put("checkResult","");
		 result.put("inspectDate","");
		 result.put("nextInspectDate","");
		 }
		 return new JsonView(result);
	 } 
	 
	 
	 public View companyinfotishi(int id){
		 Map<String, Object> result = new HashMap<String, Object>();
		 String jdbCompanyName="";
		 String wgCompanyName="";
		 String ywCompanyName="";
		 String zzCompanyName ="";
		 String azCompanyName="";
		 String jyCompanyName="";
		 String zjCompanyName="";
		 
		 if(id >0){
			 DdElevaltorInfoVO ddElevaltorInfoVO =null;
			 try {
				String sql ="select (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName from TwoCodeDdElevatorInfo t   where t.id = ? ";
				ddElevaltorInfoVO  =  DdElevaltorInfoVO.findFirstBySql(DdElevaltorInfoVO.class, sql, new Object[]{id});
				if(ddElevaltorInfoVO != null){
					jdbCompanyName = ddElevaltorInfoVO.getJdbCompanyName();
					wgCompanyName = ddElevaltorInfoVO.getWgCompanyName();
					ywCompanyName =ddElevaltorInfoVO.getYwCompanyName();
					zzCompanyName = ddElevaltorInfoVO.getZzCompanyName();
					azCompanyName=ddElevaltorInfoVO.getAzCompanyName();
					jyCompanyName =ddElevaltorInfoVO.getJyCompanyName();
					zjCompanyName=ddElevaltorInfoVO.getZjCompanyName();
				}
			} catch (ActiveRecordException e) {
				e.printStackTrace();
			}
			
		 }
		 result.put("jdbCompanyName", jdbCompanyName);
		 result.put("wgCompanyName", wgCompanyName);
		 result.put("ywCompanyName", ywCompanyName);
		 result.put("zzCompanyName", zzCompanyName);
		 result.put("azCompanyName",azCompanyName);
		 result.put("jyCompanyName",jyCompanyName);
		 result.put("zjCompanyName",zjCompanyName);
		 return new JsonView(result);
	 } 
	 
	 
	 public View checktishi(int id){
		 Map<String, Object> result = new HashMap<String, Object>();
		 String shenHeState="";
		 String shenHeBeiZhu="";
		 String shenhePersonName="";
		 String shenheTime ="";
		 String registNumber="";
		 if(id >0){
			 DdElevaltorInfoVO ddElevaltorInfoVO =null;
			 try {
				String sql ="select t.registNumber,t.shenHeState,t.shenHeBeiZhu,(case when t.shenheTime is null then ''  else CONVERT(varchar(100),t.shenheTime, 20) end) as shenheTime,te.userName as shenhePersonName from TwoCodeDdElevatorInfo t left join TwoCodeUserExtInfo te on t.shehePersonId = te.userid  where t.id = ? ";
				ddElevaltorInfoVO  =  DdElevaltorInfoVO.findFirstBySql(DdElevaltorInfoVO.class, sql, new Object[]{id});
				if(ddElevaltorInfoVO != null){
					System.out.println("sourceStr--->"+ddElevaltorInfoVO.getShenHeState());
					shenHeState = shenHeStatejiexi(ddElevaltorInfoVO.getShenHeState());
					shenHeBeiZhu = ddElevaltorInfoVO.getShenHeBeiZhu();
					shenhePersonName =ddElevaltorInfoVO.getShenhePersonName();
					shenheTime = ddElevaltorInfoVO.getShenheTime().substring(0, 16);
					registNumber=ddElevaltorInfoVO.getRegistNumber();
				}
			} catch (ActiveRecordException e) {
				e.printStackTrace();
			}
			
		 }
		 result.put("shenHeState", shenHeState);
		 result.put("shenHeBeiZhu", shenHeBeiZhu);
		 result.put("shehePersonName", shenhePersonName);
		 result.put("shenheTime", shenheTime);
		 result.put("registNumber",registNumber);
		 System.out.println(new JsonView(result));
		 return new JsonView(result);
	 } 
	 
	 public String shenHeStatejiexi(String sourceStr){
		 String resultStr ="";
		 if(!"".equals(sourceStr) && sourceStr != null){
			 if("1".equals(String.valueOf(sourceStr.charAt(0))))
				 resultStr ="���ݶ�ά���ǩ���ݱ�Ŵ���;"; 
			 if("1".equals(String.valueOf(sourceStr.charAt(1))))
				 resultStr =resultStr+"���ݵǼǱ�Ŵ���;"; 
			 if("1".equals(String.valueOf(sourceStr.charAt(2))))
				 resultStr =resultStr+"���ݵ�ַ����;"; 
			 if("1".equals(String.valueOf(sourceStr.charAt(3))))
				 resultStr =resultStr+"����¥�̴���;";
			 if("1".equals(String.valueOf(sourceStr.charAt(4))))
				 resultStr =resultStr+"���ݶ�����;";
			 if("1".equals(String.valueOf(sourceStr.charAt(5))))
				 resultStr =resultStr+"���ݵ�Ԫ����;";
			 if("1".equals(String.valueOf(sourceStr.charAt(6))))
				 resultStr =resultStr+"�����ڲ���Ŵ���;";
			 if("1".equals(String.valueOf(sourceStr.charAt(7))))
				 resultStr =resultStr+"���������������;";
			 if("1".equals(String.valueOf(sourceStr.charAt(8))))
				 resultStr =resultStr+"����ͣ��״̬����;";
		 }
		 System.out.println("resultStr---"+resultStr);
		 return resultStr;
	 }
	 
	//ճ����������������������ѯ����
	 public View zhantieddquery(DdElevaltorInfoVO info,int page, int rows){
		 String cityName = GlobalFunction.cityName;
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 
		
		
		 String areaName="";
		 String address ="";
		 String buildingName="";
		 String registCode="";
		 String registNumber = "";
		 String shibieCode="";
		 long total =0;
		 List<DdElevaltorInfoVO> items= null;
		 
		 String area="";
		 String sql ="";
		 String conditions="";
		 String conditionsSql="";
		 
		 address =info.getAddress();
		 buildingName =info.getBuildingName();
		 areaName =info.getArea();
		 registCode =info.getRegistCode();
		 registNumber = info.getRegistNumber();
		 shibieCode = info.getShibieCode();
		 
		 if(!"".equals(address)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.address like '%"+address+"%'";	
				} 
				else{
					 conditions =" t.address like '%"+address+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(buildingName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.buildingName like '%"+buildingName+"%'";	
				} 
				else{
					conditions =" t.buildingName like '%"+buildingName+"%'";	
				}
			 
		 }
		 if(role!=10 && role != 11){
		 if(!"".equals(areaName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.area like '%"+areaName+"%'";	
				} 
				else{
					conditions =" t.area like '%"+areaName+"%'";	
				}
			 
		 }
		 }
		 
		 if(!"".equals(registCode)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registCode like '%"+registCode+"%'";	
				} 
				else{
					 conditions =" t.registCode like '%"+registCode+"%'";	
				}
			 
		 }
		 if(!"".equals(registNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registNumber like '%"+registNumber+"%'";
			 } else {
				 conditions =" t.registNumber like '%"+registNumber+"%'";	
			 }
		 }
		 if(!"".equals(shibieCode)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.shibieCode like '%"+shibieCode+"%'";
			 } else {
				 conditions =" t.shibieCode like '%"+shibieCode+"%'";	
			 }
		 }
		 
		 if(role==2 || role==1){
			 if(!"".equals(conditions)){
				sql ="select isnull(t.mobileUploadbeizhu,'') as mobileUploadbeizhu,t.shibieCode, isnull(t.picregistNumber,'') as picregistNumber,t.subTime2 as subTime2,t.id,t.registNumber, t.address,t.buildingName,t.building,t.unit,t.registCode,t.useNumber,t.area from TwoCodeDdElevatorInfo  t  where t.recordSate != 3 and t.recordSate > 1 and "+conditions;
				conditionsSql = "select count(*) from  TwoCodeDdElevatorInfo  t  where t.recordSate != 3 and t.recordSate > 1 and "+conditions;
			 }
			 else{
				 sql ="select isnull(t.mobileUploadbeizhu,'') as mobileUploadbeizhu,t.shibieCode, isnull(t.picregistNumber,'') as picregistNumber,t.subTime2 as subTime2,t.id,t.registNumber, t.address,t.buildingName,t.building,t.unit,t.registCode,t.useNumber,t.area from TwoCodeDdElevatorInfo  t  where t.recordSate != 3 and t.recordSate > 1";
				 conditionsSql = "select count(*) from  TwoCodeDdElevatorInfo  t  where t.recordSate != 3 and t.recordSate > 1  ";
				
			 }
		 }
		 
		 
		 if(role==22 || role==23){
			 if(!"".equals(conditions)){
				 sql ="select isnull(t.mobileUploadbeizhu,'') as mobileUploadbeizhu,t.shibieCode, isnull(t.picregistNumber,'') as picregistNumber,t.subTime2 as subTime2,t.id,t.registNumber, t.address,t.buildingName,t.building,t.unit,t.registCode,t.useNumber,t.area from TwoCodeDdElevatorInfo  t  where t.recordSate != 3 and t.recordSate > 1 and "+conditions;
				 conditionsSql = "select count(*) from  TwoCodeDdElevatorInfo  t  where t.recordSate != 3 and t.recordSate > 1 and "+conditions;
				 }
			 else{
				 sql ="select isnull(t.mobileUploadbeizhu,'') as mobileUploadbeizhu,t.shibieCode, isnull(t.picregistNumber,'') as picregistNumber,t.subTime2 as subTime2,t.id,t.registNumber, t.address,t.buildingName,t.building,t.unit,t.registCode,t.useNumber,t.area from TwoCodeDdElevatorInfo  t  where t.recordSate != 3 and t.recordSate > 1";
				 conditionsSql = "select count(*) from  TwoCodeDdElevatorInfo  t  where t.recordSate != 3 and t.recordSate > 1  ";
				
			 }
		 }
		 
		 
		 if(role==10 || role==11){
			 UserInfoVO userInfoVO = null;
				try {
					userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.area from  tcuserinfo tu  where tu.userid = ?", new Object[] { userid });
				} catch (ActiveRecordException e) {
					e.printStackTrace();
				}
		    	 if(userInfoVO != null){
		    		 area = userInfoVO.getArea();
		    		 if(!"".equals(conditions)){
						 conditions =conditions+" and t.area like '%"+area+"%'";	
						} 
						else{
							 conditions =" t.area like '%"+area+"%'";	
						}
		    	 }
		    	 if(!"".equals(conditions)){
					 sql ="select isnull(t.mobileUploadbeizhu,'') as mobileUploadbeizhu,t.shibieCode, isnull(t.picregistNumber,'') as picregistNumber,t.subTime2 as subTime2,t.id,t.registNumber, t.address,t.buildingName,t.building,t.unit,t.registCode,t.useNumber,t.area from TwoCodeDdElevatorInfo  t  where t.recordSate != 3 and t.recordSate > 1 and "+conditions;
					 conditionsSql = "select count(*) from  TwoCodeDdElevatorInfo  t  where t.recordSate != 3 and t.recordSate > 1 and "+conditions;
					 }
				 else{
					 sql ="select isnull(t.mobileUploadbeizhu,'') as mobileUploadbeizhu,t.shibieCode, isnull(t.picregistNumber,'') as picregistNumber,t.subTime2 as subTime2,t.id,t.registNumber, t.address,t.buildingName,t.building,t.unit,t.registCode,t.useNumber,t.area from TwoCodeDdElevatorInfo  t  where t.recordSate != 3 and t.recordSate > 1";
					 conditionsSql = "select count(*) from  TwoCodeDdElevatorInfo  t  where t.recordSate != 3 and t.recordSate > 1  ";
					
				 }
		    	 
		 }
		 
		 try {
		 total =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class, conditionsSql, null);
		 items=DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, null, null, rows, (page-1)*rows);
		 } catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		 }
	 
	 public View autozhantieddquery(int page, int rows){
		 String cityName = GlobalFunction.cityName;
		long total =0;
		List<DdElevaltorInfoVO> items= null;
		String sql ="";
		/*if("1".equals(cityName)){ 
		 sql ="select isnull(b.picregistNumber,'') as picregistNumber,b.id,b.registNumber, b.address,b.buildingName,b.building,b.unit,b.registCode,b.useNumber,b.area,b.subTime2 as subTime2 from zytysba.dbo.TwoCodeNEWYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode and (charindex(a.address, b.address) > 0 or charindex(b.address, a.address) > 0) and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0"; 
			
		}
		else if("2".equals(cityName)){
		sql ="select isnull(b.picregistNumber,'') as picregistNumber,b.id,isnull(b.shibieCode,''),b.registNumber, b.address,b.buildingName,b.building,b.unit,b.registCode,b.useNumber,b.area,b.subTime2 as subTime2 from TwoCodeYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode and (charindex(a.address, b.address) > 0 or charindex(b.address, a.address) > 0) and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0"; 			
		}
		else{*/
		 sql ="select isnull(b.picregistNumber,'') as picregistNumber,b.id,b.registNumber, b.address,b.buildingName,b.building,b.unit,b.registCode,b.useNumber,b.area,b.subTime2 as subTime2 from TwoCodeYuanShiNewData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode and (charindex(a.address, b.address) > 0 or charindex(b.address, a.address) > 0) and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0"; 
		//}
		try {
			/*if("1".equals(cityName)){ 
			    total = DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class, "select count(*)  from zytysba.dbo.TwoCodeNEWYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode and (charindex(a.address, b.address) > 0 or charindex(b.address, a.address) > 0) and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 ", null);
			}
			else{*/
				total = DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class, "select count(*)  from TwoCodeYuanShiNewData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode and (charindex(a.address, b.address) > 0 or charindex(b.address, a.address) > 0) and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 ", null);
					
			//}
			items=DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, null, "b.registCode", rows, (page-1)*rows);
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		  Map<String, Object> result = new HashMap<String, Object>();
		  if(total == 0){
			  result.put("total", 0);
			  result.put("rows", null);
		  }
		  else{
		  result.put("total", total);
		  result.put("rows", items);
		  }
		  return new JsonView(result);
	 }
	 
	 public View xzautozhantieddquery(int page, int rows){
		long total =0;
		List<DdElevaltorInfoVO> items= null;
		String sql ="";
		 sql ="select isnull(b.picregistNumber,'') as picregistNumber,b.id,b.registNumber, b.address,b.buildingName,b.building,b.unit,b.registCode,b.useNumber,b.area,b.subTime2 as subTime2 from JyNewRegistInfo a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode and (charindex(a.address, b.address) > 0 or charindex(b.address, a.address) > 0) and b.recordSate = 2 and (a.isusedFlag = 0 or a.isusedFlag is null)"; 
		try {
				total = DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class, "select count(*)  from JyNewRegistInfo a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode and (charindex(a.address, b.address) > 0 or charindex(b.address, a.address) > 0) and b.recordSate = 2 and (a.isusedFlag = 0 or a.isusedFlag is null) ", null);
			items=DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, null, "b.registCode", rows, (page-1)*rows);
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		  Map<String, Object> result = new HashMap<String, Object>();
		  if(total == 0){
			  result.put("total", 0);
			  result.put("rows", null);
		  }
		  else{
		  result.put("total", total);
		  result.put("rows", items);
		  }
		  return new JsonView(result);
	 }
	 
	 public View autozhantieddquery2(DdElevaltorInfoVO info,int page, int rows){
		 String cityName = GlobalFunction.cityName;
		    String area  ="";
		    String conditionSql ="";
		    if(!"".equals(info.getArea())){
		    area ="%"+info.getArea()+"%";
		    conditionSql ="and b.area like '"+area+"'";
		    }
		    String sql ="";
			long total =0;
			List<DdElevaltorInfoVO> items= null;
			 
			if("1".equals(cityName)){
				if("".equals(conditionSql))
				   sql ="select isnull(b.picregistNumber,'') as picregistNumber,b.id,b.registNumber, b.address,b.buildingName,b.building,b.unit,b.registCode,b.useNumber,b.area,b.subTime2 as subTime2 from zytysba.dbo.TwoCodeNEWYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 "+conditionSql; 
				else
				   sql ="select isnull(b.picregistNumber,'') as picregistNumber,b.id,b.registNumber, b.address,b.buildingName,b.building,b.unit,b.registCode,b.useNumber,b.area,b.subTime2 as subTime2 from zytysba.dbo.TwoCodeNEWYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 "+conditionSql; 
						    	
			}
			else if("2".equals(cityName)){
				 if("".equals(conditionSql))
				     sql ="select isnull(b.picregistNumber,'') as picregistNumber,b.id,isnull(b.shibieCode,''),b.registNumber, b.address,b.buildingName,b.building,b.unit,b.registCode,b.useNumber,b.area,b.subTime2 as subTime2 from TwoCodeYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 "+conditionSql; 
				   else
					 sql ="select isnull(b.picregistNumber,'') as picregistNumber,b.id,isnull(b.shibieCode,''),b.registNumber, b.address,b.buildingName,b.building,b.unit,b.registCode,b.useNumber,b.area,b.subTime2 as subTime2 from TwoCodeYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 "+conditionSql; 
						
				
			}
			else{
			   if("".equals(conditionSql))
			     sql ="select isnull(b.picregistNumber,'') as picregistNumber,b.id,b.registNumber, b.address,b.buildingName,b.building,b.unit,b.registCode,b.useNumber,b.area,b.subTime2 as subTime2 from TwoCodeYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 "+conditionSql; 
			   else
				 sql ="select isnull(b.picregistNumber,'') as picregistNumber,b.id,b.registNumber, b.address,b.buildingName,b.building,b.unit,b.registCode,b.useNumber,b.area,b.subTime2 as subTime2 from TwoCodeYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 "+conditionSql; 
			}
			try {  
				if("".equals(conditionSql))
				    total = DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class, "select count(*)  from TwoCodeYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode and  b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 ", null);
				else
				    total = DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class, "select count(*)  from TwoCodeYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode and  b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 "+conditionSql, null);
				 
				items=DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, null, "b.registCode,b.id", rows, (page-1)*rows);  
			} catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String registCode ="";
	/*		if(items != null){
				Iterator<DdElevaltorInfoVO> it = items.iterator();
				while(it.hasNext()){
					DdElevaltorInfoVO	ddElevaltorInfoVO = it.next();
					if(registCode.equals(ddElevaltorInfoVO.getRegistCode()) && !"".equals(ddElevaltorInfoVO.getRegistCode())){
						ddElevaltorInfoVO.setRegistCodeCFFlag(1);
						
					}
					else{
						ddElevaltorInfoVO.setRegistCodeCFFlag(0);
						registCode = ddElevaltorInfoVO.getRegistCode();
					}
				}
			}
		*/	 
			if(!"1".equals(cityName)){
			if(items != null){
				for(int i =0 ; i<items.size(); i++){
					DdElevaltorInfoVO	ddElevaltorInfoVO = items.get(i);
					if(registCode.equals(ddElevaltorInfoVO.getRegistCode()) && !"".equals(ddElevaltorInfoVO.getRegistCode())){
						ddElevaltorInfoVO.setRegistCodeCFFlag(1);
						if(i > 0)
							items.get(i-1).setRegistCodeCFFlag(1);
						
					}
					else{
						ddElevaltorInfoVO.setRegistCodeCFFlag(0);
						registCode = ddElevaltorInfoVO.getRegistCode();
					}
				}
			}
			}
			  Map<String, Object> result = new HashMap<String, Object>();
			  result.put("total", total);
			  result.put("rows", items);
			  return new JsonView(result);
		 }
	 
	 
	 public View autozhantieddquery3(DdElevaltorInfoVO info,int page, int rows){
		 String cityName = GlobalFunction.cityName;
		    String area  ="";
		    String conditionSql ="";
		    if(!"".equals(info.getArea())){
		    area ="%"+info.getArea()+"%";
		    conditionSql ="and b.area like '"+area+"'";
		    }
		    String sql ="";
			long total =0;
			List<DdElevaltorInfoVO> items= null;
			 
			/*if("1".equals(cityName)){
				if("".equals(conditionSql))
				   sql ="select isnull(b.picregistNumber,'') as picregistNumber,b.id,b.registNumber, b.address,b.buildingName,b.building,b.unit,b.registCode,b.useNumber,b.area,b.subTime2 as subTime2 from zytysba.dbo.TwoCodeNEWYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 "+conditionSql; 
				else
				   sql ="select isnull(b.picregistNumber,'') as picregistNumber,b.id,b.registNumber, b.address,b.buildingName,b.building,b.unit,b.registCode,b.useNumber,b.area,b.subTime2 as subTime2 from zytysba.dbo.TwoCodeNEWYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 "+conditionSql; 
						    	
			}
			else if("2".equals(cityName)){
				 if("".equals(conditionSql))
				     sql ="select isnull(b.picregistNumber,'') as picregistNumber,b.id,isnull(b.shibieCode,''),b.registNumber, b.address,b.buildingName,b.building,b.unit,b.registCode,b.useNumber,b.area,b.subTime2 as subTime2 from TwoCodeYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 "+conditionSql; 
				   else
					 sql ="select isnull(b.picregistNumber,'') as picregistNumber,b.id,isnull(b.shibieCode,''),b.registNumber, b.address,b.buildingName,b.building,b.unit,b.registCode,b.useNumber,b.area,b.subTime2 as subTime2 from TwoCodeYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 "+conditionSql; 
						
				
			}
			else{*/
			   if("".equals(conditionSql))
			     sql ="select isnull(b.picregistNumber,'') as picregistNumber,b.id,b.registNumber, b.address,b.buildingName,b.building,b.unit,b.registCode,b.useNumber,b.area,b.subTime2 as subTime2 from TwoCodeYuanShiNewData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 "+conditionSql; 
			   else
				 sql ="select isnull(b.picregistNumber,'') as picregistNumber,b.id,b.registNumber, b.address,b.buildingName,b.building,b.unit,b.registCode,b.useNumber,b.area,b.subTime2 as subTime2 from TwoCodeYuanShiNewData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 "+conditionSql; 
			//}
			try {  
				if("".equals(conditionSql))
				    total = DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class, "select count(*)  from TwoCodeYuanShiNewData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode and  b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 ", null);
				else
				    total = DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class, "select count(*)  from TwoCodeYuanShiNewData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode and  b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 "+conditionSql, null);
				 
				items=DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, null, "b.registCode,b.id", rows, (page-1)*rows);  
			} catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String registCode ="";
	/*		if(items != null){
				Iterator<DdElevaltorInfoVO> it = items.iterator();
				while(it.hasNext()){
					DdElevaltorInfoVO	ddElevaltorInfoVO = it.next();
					if(registCode.equals(ddElevaltorInfoVO.getRegistCode()) && !"".equals(ddElevaltorInfoVO.getRegistCode())){
						ddElevaltorInfoVO.setRegistCodeCFFlag(1);
						
					}
					else{
						ddElevaltorInfoVO.setRegistCodeCFFlag(0);
						registCode = ddElevaltorInfoVO.getRegistCode();
					}
				}
			}
		*/	 
			if(!"1".equals(cityName)){
			if(items != null){
				for(int i =0 ; i<items.size(); i++){
					DdElevaltorInfoVO	ddElevaltorInfoVO = items.get(i);
					if(registCode.equals(ddElevaltorInfoVO.getRegistCode()) && !"".equals(ddElevaltorInfoVO.getRegistCode())){
						ddElevaltorInfoVO.setRegistCodeCFFlag(1);
						if(i > 0)
							items.get(i-1).setRegistCodeCFFlag(1);
						
					}
					else{
						ddElevaltorInfoVO.setRegistCodeCFFlag(0);
						registCode = ddElevaltorInfoVO.getRegistCode();
					}
				}
			}
			}
			  Map<String, Object> result = new HashMap<String, Object>();
			  result.put("total", total);
			  result.put("rows", items);
			  return new JsonView(result);
		 }
	 
	 public View autozhantieddquery4(DdElevaltorInfoVO info,int page, int rows){
		 String cityName = GlobalFunction.cityName;
		    String area  ="";
		    String conditionSql ="";
		    if(!"".equals(info.getArea())){
		    area ="%"+info.getArea()+"%";
		    conditionSql ="and b.area like '"+area+"'";
		    }
		    String sql ="";
			long total =0;
			List<DdElevaltorInfoVO> items= null;
			if("".equals(conditionSql))
			    sql ="select isnull(b.picregistNumber,'') as picregistNumber,b.id,b.registNumber, b.address,b.buildingName,b.building,b.unit,b.registCode,b.useNumber,b.area,b.subTime2 as subTime2 from JyNewRegistInfo a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and (a.isusedFlag = 0 or a.isusedFlag is null) "+conditionSql; 
			else
				sql ="select isnull(b.picregistNumber,'') as picregistNumber,b.id,b.registNumber, b.address,b.buildingName,b.building,b.unit,b.registCode,b.useNumber,b.area,b.subTime2 as subTime2 from JyNewRegistInfo a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and (a.isusedFlag = 0 or a.isusedFlag is null) "+conditionSql; 
			try {  
				if("".equals(conditionSql))
				    total = DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class, "select count(*)  from JyNewRegistInfo a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode and  b.recordSate = 2 and (a.isusedFlag = 0 or a.isusedFlag is null) ", null);
				else
				    total = DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class, "select count(*)  from JyNewRegistInfo a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode and  b.recordSate = 2 and (a.isusedFlag = 0 or a.isusedFlag is null) "+conditionSql, null);
				 
				items=DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, null, "b.registCode,b.id", rows, (page-1)*rows);  
			} catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String registCode ="";
			if(!"1".equals(cityName)){
			if(items != null){
				for(int i =0 ; i<items.size(); i++){
					DdElevaltorInfoVO	ddElevaltorInfoVO = items.get(i);
					if(registCode.equals(ddElevaltorInfoVO.getRegistCode()) && !"".equals(ddElevaltorInfoVO.getRegistCode())){
						ddElevaltorInfoVO.setRegistCodeCFFlag(1);
						if(i > 0)
							items.get(i-1).setRegistCodeCFFlag(1);
						
					}
					else{
						ddElevaltorInfoVO.setRegistCodeCFFlag(0);
						registCode = ddElevaltorInfoVO.getRegistCode();
					}
				}
			}
			}
			  Map<String, Object> result = new HashMap<String, Object>();
			  result.put("total", total);
			  result.put("rows", items);
			  return new JsonView(result);
		 }
	 
	 public View stautozhantieddquery2(DdElevaltorInfoVO info,int page, int rows){
		    
		 long total =0;
			List<DdElevaltorInfoVO> items= null;
		//	String countSql =" ";
			String sql ="select b.id,b.registNumber, b.address,b.buildingName,b.building,b.unit,b.registCode,b.useNumber,b.area,b.subTime2 from TwoCodeSelevatorInfo a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 "; 
			try {
				total = DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class, "select count(*)  from TwoCodeSelevatorInfo a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2  ", null);
			    items=DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, null, "b.registCode", rows, (page-1)*rows);
			} catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			  Map<String, Object> result = new HashMap<String, Object>();
			  result.put("total", total);
			  result.put("rows", items);
			  return new JsonView(result);
		 }
	 
	 public View stautoyuanshiddquery2(DdElevaltorInfoVO info,int page, int rows){
		    
		 long total =0;
			List<SelevaltorInfoVO> items= null;
		//	String countSql =" ";
			String sql ="select  a.id,a.address,a.buildingName,b.building,a.registCode,a.area from TwoCodeSelevatorInfo a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 "; 
			try {
				total = SelevaltorInfoVO.countBySql(SelevaltorInfoVO.class, "select count(*)  from TwoCodeSelevatorInfo a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 ", null);
			    items=SelevaltorInfoVO.findBySql(SelevaltorInfoVO.class, sql, null, "a.registCode", rows, (page-1)*rows);
			} catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			  Map<String, Object> result = new HashMap<String, Object>();
			  result.put("total", total);
			  result.put("rows", items);
			  return new JsonView(result);
		 }
	 
	  
	 
	 public View autoyuanshiddquery2(DdElevaltorInfoVO info,int page, int rows){
		 String cityName = GlobalFunction.cityName;
		  String area  ="";
		  String conditionSql ="";
		    if(!"".equals(info.getArea())){
		    area ="%"+info.getArea()+"%";
		    conditionSql ="and a.area like '"+area+"'";
		    }
		    
		 String sql ="";
		 long total =0;
		 Map<String, Object> result = new HashMap<String, Object>();
		 try {
		 if("1".equals(cityName)){
			 List<NewDdYuanShiElevaltorInfoVO> items= null;
			 if("".equals(conditionSql))
					sql ="select a.id,a.address,a.address2,a.wgCompanyName,a.registCode,a.useNumber,a.area from TwoCodeNewYuanShiData a, zytsjba.dbo.TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0"; 		
			 else
				  sql ="select a.id,a.address,a.address2,a.wgCompanyName,a.registCode,a.useNumber,a.area from TwoCodeNewYuanShiData a, zytsjba.dbo.TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0  "+conditionSql; 
							
			if("".equals(conditionSql))
				total =NewDdYuanShiElevaltorInfoVO.countBySql(NewDdYuanShiElevaltorInfoVO.class, "select count(*)  from TwoCodeNewYuanShiData a, zytsjba.dbo.TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 ", null);
			else
				total =NewDdYuanShiElevaltorInfoVO.countBySql(NewDdYuanShiElevaltorInfoVO.class, "select count(*)  from TwoCodeNewYuanShiData a, zytsjba.dbo.TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 "+conditionSql, null); 	 
				items=NewDdYuanShiElevaltorInfoVO.findBySql(NewDdYuanShiElevaltorInfoVO.class, sql, null, "a.registCode,a.id", rows, (page-1)*rows);
							 
				result.put("total", total);
				result.put("rows", items);		 
		 }
		 else if("2".equals(cityName)){
			 List<DdYuanShiElevaltorInfoVO> items= null;
			 if("".equals(conditionSql))
			    sql ="select distinct a.id,a.address,a.address2,a.wgCompanyName,a.registCode,a.useNumber,a.area,a.shibieCode from TwoCodeYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0"; 
			 else
				sql ="select distinct a.id,a.address,a.address2,a.wgCompanyName,a.registCode,a.useNumber,a.area,a.shibieCode from TwoCodeYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0  "+conditionSql; 
			
		     if("".equals(conditionSql))
				 total =DdYuanShiElevaltorInfoVO.countBySql(DdYuanShiElevaltorInfoVO.class, "select count(t.id) from  ( select distinct a.id    from TwoCodeYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 ) t ", null);
			 else
				 total =DdYuanShiElevaltorInfoVO.countBySql(DdYuanShiElevaltorInfoVO.class, "select count(t.id) from ( select distinct a.id  from TwoCodeYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 "+conditionSql +" )  t ", null); 
				    
					 
				items=DdYuanShiElevaltorInfoVO.findBySql(DdYuanShiElevaltorInfoVO.class, sql, null, "a.registCode,a.id", rows, (page-1)*rows);
				result.put("total", total);
				result.put("rows", items); 
		 }
		 else{
		 List<DdYuanShiElevaltorInfoVO> items= null;
		 if("".equals(conditionSql))
		    sql ="select distinct a.id,a.address,a.address2,a.wgCompanyName,a.registCode,a.useNumber,a.area from TwoCodeYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0"; 
		 else
			sql ="select distinct a.id,a.address,a.address2,a.wgCompanyName,a.registCode,a.useNumber,a.area from TwoCodeYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0  "+conditionSql; 
		
	     if("".equals(conditionSql))
			 total =DdYuanShiElevaltorInfoVO.countBySql(DdYuanShiElevaltorInfoVO.class, "select count(t.id) from  ( select distinct a.id    from TwoCodeYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 ) t ", null);
		 else
			 total =DdYuanShiElevaltorInfoVO.countBySql(DdYuanShiElevaltorInfoVO.class, "select count(t.id) from ( select distinct a.id  from TwoCodeYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 "+conditionSql +" )  t ", null); 
			    
				 
			items=DdYuanShiElevaltorInfoVO.findBySql(DdYuanShiElevaltorInfoVO.class, sql, null, "a.registCode,a.id", rows, (page-1)*rows);
			result.put("total", total);
			result.put("rows", items);
		 }
		 } catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}
		
		 
		  return new JsonView(result);
		 
	 }
	 
	 public View autoyuanshiddquery3(DdElevaltorInfoVO info,int page, int rows){
		 String cityName = GlobalFunction.cityName;
		  String area  ="";
		  String conditionSql ="";
		    if(!"".equals(info.getArea())){
		    area ="%"+info.getArea()+"%";
		    conditionSql ="and a.area like '"+area+"'";
		    }
		    
		 String sql ="";
		 long total =0;
		 Map<String, Object> result = new HashMap<String, Object>();
		 try {
		 /*if("1".equals(cityName)){
			 List<NewDdYuanShiElevaltorInfoVO> items= null;
			 if("".equals(conditionSql))
					sql ="select a.id,a.address,a.address2,a.wgCompanyName,a.registCode,a.useNumber,a.area from TwoCodeNewYuanShiData a, zytsjba.dbo.TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0"; 		
			 else
				  sql ="select a.id,a.address,a.address2,a.wgCompanyName,a.registCode,a.useNumber,a.area from TwoCodeNewYuanShiData a, zytsjba.dbo.TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0  "+conditionSql; 
							
			if("".equals(conditionSql))
				total =NewDdYuanShiElevaltorInfoVO.countBySql(NewDdYuanShiElevaltorInfoVO.class, "select count(*)  from TwoCodeNewYuanShiData a, zytsjba.dbo.TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 ", null);
			else
				total =NewDdYuanShiElevaltorInfoVO.countBySql(NewDdYuanShiElevaltorInfoVO.class, "select count(*)  from TwoCodeNewYuanShiData a, zytsjba.dbo.TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 "+conditionSql, null); 	 
				items=NewDdYuanShiElevaltorInfoVO.findBySql(NewDdYuanShiElevaltorInfoVO.class, sql, null, "a.registCode,a.id", rows, (page-1)*rows);
							 
				result.put("total", total);
				result.put("rows", items);		 
		 }
		 else if("2".equals(cityName)){
			 List<DdYuanShiElevaltorInfoVO> items= null;
			 if("".equals(conditionSql))
			    sql ="select distinct a.id,a.address,a.address2,a.wgCompanyName,a.registCode,a.useNumber,a.area,a.shibieCode from TwoCodeYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0"; 
			 else
				sql ="select distinct a.id,a.address,a.address2,a.wgCompanyName,a.registCode,a.useNumber,a.area,a.shibieCode from TwoCodeYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0  "+conditionSql; 
			
		     if("".equals(conditionSql))
				 total =DdYuanShiElevaltorInfoVO.countBySql(DdYuanShiElevaltorInfoVO.class, "select count(t.id) from  ( select distinct a.id    from TwoCodeYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 ) t ", null);
			 else
				 total =DdYuanShiElevaltorInfoVO.countBySql(DdYuanShiElevaltorInfoVO.class, "select count(t.id) from ( select distinct a.id  from TwoCodeYuanShiData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 "+conditionSql +" )  t ", null); 
				    
					 
				items=DdYuanShiElevaltorInfoVO.findBySql(DdYuanShiElevaltorInfoVO.class, sql, null, "a.registCode,a.id", rows, (page-1)*rows);
				result.put("total", total);
				result.put("rows", items); 
		 }
		 else{*/
		 List<DdYuanShiElevaltorInfoVO> items= null;
		 if("".equals(conditionSql))
		    sql ="select distinct a.id,a.address,a.buildingName,a.wgCompanyName,a.registCode,a.useNumber,a.area from TwoCodeYuanShiNewData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0"; 
		 else
			sql ="select distinct a.id,a.address,a.buildingName,a.wgCompanyName,a.registCode,a.useNumber,a.area from TwoCodeYuanShiNewData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0  "+conditionSql; 
		
	     if("".equals(conditionSql))
			 total =DdYuanShiElevaltorInfoVO.countBySql(DdYuanShiElevaltorInfoVO.class, "select count(t.id) from  ( select distinct a.id    from TwoCodeYuanShiNewData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 ) t ", null);
		 else
			 total =DdYuanShiElevaltorInfoVO.countBySql(DdYuanShiElevaltorInfoVO.class, "select count(t.id) from ( select distinct a.id  from TwoCodeYuanShiNewData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 "+conditionSql +" )  t ", null); 
			    
				 
			items=DdYuanShiElevaltorInfoVO.findBySql(DdYuanShiElevaltorInfoVO.class, sql, null, "a.registCode,a.id", rows, (page-1)*rows);
			result.put("total", total);
			result.put("rows", items);
		// }
		 } catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}
		
		 
		  return new JsonView(result);
		 
	 }
	 
	 public View autoyuanshiddquery4(int page, int rows){
		 String sql ="";
		 long total =0;
		 Map<String, Object> result = new HashMap<String, Object>();
		 try {
		 List<DdYuanShiElevaltorInfoVO> items= null;
		    sql ="select distinct a.id,a.address,a.registNumber,a.registor,a.registCode,a.registDate,a.registCompanyName from JyNewRegistInfo a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and (a.isusedFlag = 0 or a.isusedFlag is null)"; 
		
			 total =DdYuanShiElevaltorInfoVO.countBySql(DdYuanShiElevaltorInfoVO.class, "select count(t.id) from  ( select distinct a.id    from JyNewRegistInfo a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode  and b.recordSate = 2 and (a.isusedFlag = 0 or a.isusedFlag is null) ) t ", null);
			    
				 
			items=DdYuanShiElevaltorInfoVO.findBySql(DdYuanShiElevaltorInfoVO.class, sql, null, "a.registCode,a.id", rows, (page-1)*rows);
			result.put("total", total);
			result.put("rows", items);
		 } catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}
		
		 
		  return new JsonView(result);
		 
	 }
	 
	//ԭʼ���ݰ�����������ѯ����
	 public View yuanshiddquery(DdElevaltorInfoVO info,int page, int rows)throws Exception{
		 String cityName = GlobalFunction.cityName;
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 String areaName="";
		 String address ="";
		 String wgCompanyName="";
		 String registCode="";
		 String address2 ="";
		 String shibieCode="";
		 
		 String area ="";
		 String conditions="";
		 String conditionsSql="";
		 
		 address = info.getAddress();
		 address2 = info.getAddress2();
		 wgCompanyName = info.getWgCompanyName();
		 areaName = info.getArea();
		 registCode = info.getRegistCode();
		 shibieCode = info.getShibieCode();
		 
		 if(!"".equals(address)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.address like '%"+address+"%'";	
				} 
				else{
				 conditions =" t.address like '%"+address+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(address2)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.address2 like '%"+address2+"%'";	
				} 
				else{
				 conditions =" t.address2 like '%"+address2+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(wgCompanyName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and c.wgCompanyName like '%"+wgCompanyName+"%'";	
				} 
				else{
				 conditions =" t.wgCompanyName like '%"+wgCompanyName+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(areaName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.area like '%"+areaName+"%'";	
				} 
				else{
				 conditions =" t.area like '%"+areaName+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(registCode)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registCode like '%"+registCode+"%'";	
				} 
				else{
				 conditions =" t.registCode like '%"+registCode+"%'";	
				}
			 
		 }
		 if(!"".equals(shibieCode)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.shibieCode like '%"+shibieCode+"%'";	
				} 
				else{
				 conditions =" t.shibieCode like '%"+shibieCode+"%'";	
				}
			 
		 }
		 
		 
		 
		
		/* 
		 if(info.getAddress()==null)
			 address ="%"+""+"%";	 
		 else
		 address ="%"+info.getAddress()+"%";
		 
		 if(info.getAddress2()==null)
			 address2 ="%"+""+"%";	 
		 else
		 address2 ="%"+info.getAddress2()+"%";
		 
		 
		 if(info.getWgCompanyName()==null)
			 wgCompanyName ="%"+""+"%";	 
		 else
			 wgCompanyName ="%"+info.getWgCompanyName()+"%";
		 
		 
		 if(info.getArea()==null)
			 areaName="%"+""+"%";
		 else
		 areaName ="%"+info.getArea()+"%";
			 
		 if(info.getRegistCode()==null)
			 registCode="%"+""+"%";
		 else
			 registCode="%"+info.getRegistCode()+"%";
		 */
		 
		/*
		 if(role==2 || role==1){
		    
		      conditions ="  t.isusedFlag = 0 and t.area like ? and t.address like ? and t.wgCompanyName like ?  and address2 like ? and t.registCode like ? ";
		      conditionsSql="  isusedFlag = 0 and area like ? and address like ? and wgCompanyName like ? and address2 like ? and registCode like ? ";
		 }
		 
		 if(role==22 || role==23){
			    
		      conditions ="  t.isusedFlag = 0 and t.area like ? and t.address like ? and t.wgCompanyName like ?  and address2 like ? and t.registCode like ? ";
		      conditionsSql="  isusedFlag = 0 and area like ? and address like ? and wgCompanyName like ? and address2 like ? and registCode like ? ";
		 }
		 */
		 if(role==10 || role==11){
			 UserInfoVO userInfoVO = null;
				try {
					userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.area from  tcuserinfo tu  where tu.userid = ?", new Object[] { userid });
				} catch (ActiveRecordException e) {
					e.printStackTrace();
				}
		    	 if(userInfoVO != null){
		    		 area = "%"+userInfoVO.getArea()+"%";
		    		 if(!"".equals(conditions)){
						 conditions =conditions+" and t.area like '%"+area+"%'";	
						} 
						else{
						 conditions =" t.area like '%"+area+"%'";	
						}
		         
		    	 }
		//      conditions ="  t.isusedFlag = 0 and   t.address like ? and t.wgCompanyName like ?  and address2 like ? and t.registCode like ? and area like ? ";
		//      conditionsSql="  isusedFlag = 0  and address like ? and wgCompanyName like ? and address2 like ? and registCode like ?  and  area like ? ";
		 }
	/*	 Object[] param=null;
			
		 if(role==2 || role==1)
	     param = new Object[]{areaName,address,wgCompanyName,address2,registCode};
		 
		 if(role==22 || role==23)
		     param = new Object[]{areaName,address,wgCompanyName,address2,registCode};
		 
		 if(role==10 || role==11)
		     param = new Object[]{address,wgCompanyName,address2,registCode,area};
		 
		 String sql ="";
		 long total =0;
		 Map<String, Object> result = new HashMap<String, Object>();
		 if("1".equals(cityName)){
		  sql ="select t.id,t.shibieCode,t.address,t.address2,t.wgCompanyName,t.wgContactor,t.wgPhone,t.eleStop,t.acceptanceReportNum,t.inspectDate,t.dueDate,t.checkResult,t.factoryNum,t.manufactDate,t.zzCompanyName,t.ywCompanyName,t.ywLevel,t.ywContactor,t.ywPhone,t.azCompanyName,t.speed,t.eleLoad,t.jyCompanyName,t.registCode,t.useNumber,t.eleType,t.neleType,t.eleMode,t.area,t.townshipStreetsName from TwoCodeNewYuanShiData  t  where "+conditions;	 	 
		  total =NewDdYuanShiElevaltorInfoVO.count(NewDdYuanShiElevaltorInfoVO.class, conditionsSql, param);
		  List<NewDdYuanShiElevaltorInfoVO> items=NewDdYuanShiElevaltorInfoVO.findBySql(NewDdYuanShiElevaltorInfoVO.class, sql, param, "t.registCode", rows, (page-1)*rows);
		  result.put("total", total);
		  result.put("rows", items);
		 }
		 else{
		 sql ="select t.id,t.address,t.address2,t.wgCompanyName,t.registCode,t.useNumber,t.area from TwoCodeYuanShiData  t  where "+conditions;
         total=DdYuanShiElevaltorInfoVO.count(DdYuanShiElevaltorInfoVO.class, conditionsSql, param);
		 List<DdYuanShiElevaltorInfoVO> items=DdYuanShiElevaltorInfoVO.findBySql(DdYuanShiElevaltorInfoVO.class, sql, param, "t.id", rows, (page-1)*rows);
		 result.put("total", total);
		 result.put("rows", items);
		 }
		 */
		 String sql ="";
		 long total =0;
		 Map<String, Object> result = new HashMap<String, Object>();
		 if("1".equals(cityName)){
			if(!"".equals(conditions)){
				 sql ="select t.id,t.shibieCode,t.address,t.address2,t.wgCompanyName,t.wgContactor,t.wgPhone,t.eleStop,t.acceptanceReportNum,t.inspectDate,t.dueDate,t.checkResult,t.factoryNum,t.manufactDate,t.zzCompanyName,t.ywCompanyName,t.ywLevel,t.ywContactor,t.ywPhone,t.azCompanyName,t.speed,t.eleLoad,t.jyCompanyName,t.registCode,t.useNumber,t.eleType,t.neleType,t.eleMode,t.area,t.townshipStreetsName from TwoCodeNewYuanShiData  t  where t.isusedFlag = 0 and "+conditions;	 	 
				 conditionsSql = "select count(*) from TwoCodeNewYuanShiData  t  where t.isusedFlag = 0 and "+conditions;
			}
			else{
				 sql ="select t.id,t.shibieCode,t.address,t.address2,t.wgCompanyName,t.wgContactor,t.wgPhone,t.eleStop,t.acceptanceReportNum,t.inspectDate,t.dueDate,t.checkResult,t.factoryNum,t.manufactDate,t.zzCompanyName,t.ywCompanyName,t.ywLevel,t.ywContactor,t.ywPhone,t.azCompanyName,t.speed,t.eleLoad,t.jyCompanyName,t.registCode,t.useNumber,t.eleType,t.neleType,t.eleMode,t.area,t.townshipStreetsName from TwoCodeNewYuanShiData  t where t.isusedFlag = 0 ";	 	 
				 conditionsSql = "select count(*) from TwoCodeNewYuanShiData  t where t.isusedFlag = 0 ";
			}
			total =NewDdYuanShiElevaltorInfoVO.countBySql(NewDdYuanShiElevaltorInfoVO.class, conditionsSql, null);
			List<NewDdYuanShiElevaltorInfoVO> items=NewDdYuanShiElevaltorInfoVO.findBySql(NewDdYuanShiElevaltorInfoVO.class, sql, null, "t.registCode", rows, (page-1)*rows);
			result.put("rows", items);
			result.put("total", total);
			
		 }
		 else{
			 if(!"".equals(conditions)){
				 sql ="select t.id,t.shibieCode,t.address,t.address2,t.wgCompanyName,t.registCode,t.useNumber,t.area from TwoCodeYuanShiData  t  where t.isusedFlag = 0  and "+conditions; 
				 conditionsSql = "select count(*) from TwoCodeYuanShiData  t  where t.isusedFlag = 0   and  "+conditions;
			 }
			 else{
				 sql ="select t.id,t.shibieCode,t.address,t.address2,t.wgCompanyName,t.registCode,t.useNumber,t.area from TwoCodeYuanShiData  t where  t.isusedFlag = 0 "; 
				 conditionsSql = "select count(*) from TwoCodeYuanShiData  t where t.isusedFlag = 0 ";
			 }
			 total=DdYuanShiElevaltorInfoVO.countBySql(DdYuanShiElevaltorInfoVO.class, conditionsSql, null);
			 List<DdYuanShiElevaltorInfoVO> items=DdYuanShiElevaltorInfoVO.findBySql(DdYuanShiElevaltorInfoVO.class, sql, null, "t.registCode", rows, (page-1)*rows);
				
			 result.put("rows", items);
			 result.put("total", total);
		 }
		 
		  return new JsonView(result);
		 }
	 
	 public View xzautoyuanshiddquery(int page, int rows){
		 long total =0;
		 Map<String, Object> result = new HashMap<String, Object>();
		 String sql ="";
		  sql ="select a.id,a.address,a.registNumber,a.registor,a.registCode,a.registDate,a.registCompanyName from JyNewRegistInfo a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode and (charindex(a.address, b.address) > 0 or charindex(b.address, a.address) > 0) and b.recordSate = 2 and (a.isusedFlag = 0 or a.isusedFlag is null)"; 
		 try {
			  List<DdYuanShiElevaltorInfoVO> items= null;
			  total =DdYuanShiElevaltorInfoVO.countBySql(DdYuanShiElevaltorInfoVO.class, "select count(*)  from JyNewRegistInfo a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode and (charindex(a.address, b.address) > 0 or charindex(b.address, a.address) > 0) and b.recordSate = 2 and (a.isusedFlag = 0 or a.isusedFlag is null) ", null);
			  items=DdYuanShiElevaltorInfoVO.findBySql(DdYuanShiElevaltorInfoVO.class, sql, null, "a.registCode", rows, (page-1)*rows);
			  result.put("total", total);
			  result.put("rows", items);
		 } catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		
		 
		  return new JsonView(result);
		 
	 }
	 
	 public View autoyuanshiddquery(int page, int rows){
		 String cityName = GlobalFunction.cityName;
		 
		 long total =0;
		 Map<String, Object> result = new HashMap<String, Object>();
		 String sql ="";
		 /*if("1".equals(cityName)){
		  sql ="select a.id,a.shibieCode,a.address,a.address2,a.wgCompanyName,a.wgContactor,a.wgPhone,a.eleStop,a.acceptanceReportNum,a.inspectDate,a.dueDate,a.checkResult,a.factoryNum,a.manufactDate,a.zzCompanyName,a.ywCompanyName,a.ywLevel,a.ywContactor,a.ywPhone,a.azCompanyName,a.speed,a.eleLoad,a.jyCompanyName,a.registCode,a.useNumber,a.eleType,a.neleType,a.eleMode,a.area,a.townshipStreetsName from TwoCodeNewYuanShiData a, zytsjba.dbo.TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode and (charindex(a.address, b.address) > 0 or charindex(b.address, a.address) > 0) and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0"; 
		      
		 }
		 else{*/
		  sql ="select a.id,a.address,a.buildingName,a.wgCompanyName,a.registCode,a.useNumber,a.area from TwoCodeYuanShiNewData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode and (charindex(a.address, b.address) > 0 or charindex(b.address, a.address) > 0) and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0"; 
		 //}
		 try {
			 /*if("1".equals(cityName)){
			  List<NewDdYuanShiElevaltorInfoVO> items= null; 
			  total =NewDdYuanShiElevaltorInfoVO.countBySql(NewDdYuanShiElevaltorInfoVO.class, "select count(*)  from TwoCodeNewYuanShiData a, zytsjba.dbo.TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode and (charindex(a.address, b.address) > 0 or charindex(b.address, a.address) > 0) and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 ", null);
			  items=NewDdYuanShiElevaltorInfoVO.findBySql(NewDdYuanShiElevaltorInfoVO.class, sql, null, "a.registCode", rows, (page-1)*rows);
			  result.put("total", total);
			  result.put("rows", items); 
			 }
			 else{*/
			  List<DdYuanShiElevaltorInfoVO> items= null;
			  total =DdYuanShiElevaltorInfoVO.countBySql(DdYuanShiElevaltorInfoVO.class, "select count(*)  from TwoCodeYuanShiNewData a, TwoCodeDdElevatorInfo b where rtrim(ltrim(a.registcode)) <> '' and a.registcode = b.registcode and (charindex(a.address, b.address) > 0 or charindex(b.address, a.address) > 0) and b.recordSate = 2 and a.area = b.area and a.isusedFlag = 0 ", null);
			  items=DdYuanShiElevaltorInfoVO.findBySql(DdYuanShiElevaltorInfoVO.class, sql, null, "a.registCode", rows, (page-1)*rows);
			  result.put("total", total);
			  result.put("rows", items);
			 //}
		 } catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		
		 
		  return new JsonView(result);
		 
	 }
	 
	 
	 protected boolean  relationrukulog(){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 Logger log = Logger.getLogger(ElevatorController.class);
		 log.info("userid:"+userid+";"+"userName:"+userName+";"+"names:"+this.rukunames+";"+"names2:"+this.rukunames2+";"+"result:"+this.rukuresult);
		 return true;
	 }
	 
	 protected boolean  deletelog(){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 Logger log = Logger.getLogger(ElevatorController.class);
		 log.info("userid:"+userid+";"+"userName:"+userName+";"+"deleteId:"+this.deleteId+";"+"registNumber:"+this.deleteregistNumber+";"+"result:"+this.deleteresult);
		 return true;
	 }
	 
	 public String ddeachRecordupdate(DdElevaltorInfo elevaltorInfo){
		 String result = "failure";
		 int num =0;
		 java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String s = format1.format(new Date()); 
		 System.out.println("registNumber:"+elevaltorInfo.getRegistNumber());
		 try {
			DdElevaltorInfo oldDdElevaltorInfo2 = DdElevaltorInfo.findFirst(DdElevaltorInfo.class,"id =?",new Object[] {elevaltorInfo.getId()});
			if(oldDdElevaltorInfo2 != null){
				num = DdElevaltorInfo.updateAll(DdElevaltorInfo.class, "registNumber=?,address=?,registCode=?,useNumber=?,area=?,buildingName=?,building=?,unit=?,updateTime=?", new Object[]{elevaltorInfo.getRegistNumber(),elevaltorInfo.getAddress(),elevaltorInfo.getRegistCode(),elevaltorInfo.getUseNumber(),elevaltorInfo.getArea(),elevaltorInfo.getBuildingName(),elevaltorInfo.getBuilding(),elevaltorInfo.getUnit(),s}, "id=?", new Object[] { elevaltorInfo.getId()});			
			    if(num > 0 )
			    	result = "success";	
			}
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
		 return result;
	 }
	 
	 public View getAutoYwCompanyList4(String q, int limit) throws Exception {
		 q = new String(q.getBytes("ISO-8859-1"), "UTF-8");
			List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
					"select top 10 id,companyName from TwoCodeCompanyInfo where (type = ? or type = ?)  and companyName like ?", new Object[] { "ά��","����", "%"+q+"%"});
		
			List<Map<String, Object>> listjson = new ArrayList<Map<String, Object>>();
			if(items != null){
		    for(CompanyInfo info : items){
		    	Map<String, Object> result = new HashMap<String, Object>();
		    	result.put("companyName", info.getCompanyName());
		    	result.put("id", info.getId());
		    	listjson.add(result);
		    }
			}
		
			
			return new JsonView(listjson);  
		}
	 
	 public View getAutoYwCompanyList5(String q, int limit) throws Exception {
		 q = new String(q.getBytes("ISO-8859-1"), "UTF-8");
			List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
					"select top 50 id,companyName from TwoCodeCompanyInfo where type = ? and ispasteyw = 0 and companyName like ?", new Object[] { "ά��", "%"+q+"%"});
		
			List<Map<String, Object>> listjson = new ArrayList<Map<String, Object>>();
			if(items != null){
		    for(CompanyInfo info : items){
		    	Map<String, Object> result = new HashMap<String, Object>();
		    	result.put("companyName", info.getCompanyName());
		    	result.put("id", info.getId());
		    	listjson.add(result);
		    }
			}
		
			
			return new JsonView(listjson);  
		}
	 
	 public View getAutoYwCompanyList(String q, int limit) throws Exception {
		 q = new String(q.getBytes("ISO-8859-1"), "UTF-8");
			List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
					"select top 50 id,companyName from TwoCodeCompanyInfo where type = ? and ispasteyw = 0 and companyName like ?", new Object[] { "ά��", "%"+q+"%"});
		
			List<Map<String, Object>> listjson = new ArrayList<Map<String, Object>>();
			if(items != null){
		    for(CompanyInfo info : items){
		    	Map<String, Object> result = new HashMap<String, Object>();
		    	result.put("companyName", info.getCompanyName());
		    	result.put("id", info.getId());
		    	listjson.add(result);
		    }
			}
		
			
			return new JsonView(listjson);  
		}
	 
	 
	 public View getAutoYwCompanyList3(String q,String qtype, int limit) throws Exception {
		 q = new String(q.getBytes("ISO-8859-1"), "UTF-8");
		 System.out.println("qtype"+qtype);
		 List<CompanyInfo> items = null;
		   if("1".equals(qtype)){
			 items = CompanyInfo.findBySql(CompanyInfo.class,
					"select id,companyName from TwoCodeCompanyInfo where type = ?  and companyName like ?", new Object[] { "ά��", "%"+q+"%"});
		   }
		   else{  
			   items = CompanyInfo.findBySql(CompanyInfo.class,
						"select id,userName as companyName from TCUserInfo where type = ?  ", new Object[] { "ճ��"});
			 
		   }
			List<Map<String, Object>> listjson = new ArrayList<Map<String, Object>>();
			if(items != null){
		    for(CompanyInfo info : items){
		    	Map<String, Object> result = new HashMap<String, Object>();
		    	result.put("companyName", info.getCompanyName());
		    	result.put("id", info.getId());
		    	listjson.add(result);
		    }
			}
		
			
			return new JsonView(listjson);  
		}
	 
	 public View getAutoYwCompanyList2(String q,String qtype, int limit) throws Exception {
		 q = new String(q.getBytes("ISO-8859-1"), "UTF-8");
		 System.out.println("qtype"+qtype);
		 List<CompanyInfo> items = null;
		   if("1".equals(qtype)){
			 items = CompanyInfo.findBySql(CompanyInfo.class,
					"select id,companyName from TwoCodeCompanyInfo where type = ? and ispasteyw = 0 and companyName like ?", new Object[] { "ά��", "%"+q+"%"});
		   }
		   else{  
			   items = CompanyInfo.findBySql(CompanyInfo.class,
						"select id,userName as companyName from TCUserInfo where type = ?  ", new Object[] { "ճ��"});
			 
		   }
			List<Map<String, Object>> listjson = new ArrayList<Map<String, Object>>();
			if(items != null){
		    for(CompanyInfo info : items){
		    	Map<String, Object> result = new HashMap<String, Object>();
		    	result.put("companyName", info.getCompanyName());
		    	result.put("id", info.getId());
		    	listjson.add(result);
		    }
			}
		
			
			return new JsonView(listjson);  
		}
	 
	 public View getAutoZzCompanyList(String q, int limit) throws Exception {
		 	q = new String(q.getBytes("ISO-8859-1"), "UTF-8");
			List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
					"select top 10 id,companyName from TwoCodeCompanyInfo where type = ?  and companyName like ?", new Object[] { "����", "%"+q+"%"});
		
			List<Map<String, Object>> listjson = new ArrayList<Map<String, Object>>();
			if(items != null){
		    for(CompanyInfo info : items){
		    	Map<String, Object> result = new HashMap<String, Object>();
		    	result.put("companyName", info.getCompanyName());
		    	result.put("id", info.getId());
		    	listjson.add(result);
		    }
			}
		
			
			return new JsonView(listjson);  
		}
	 
	 public View getAutoJyCompanyList(String q, int limit) throws Exception {
		 q = new String(q.getBytes("ISO-8859-1"), "UTF-8");
			List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
					"select top 10 id,companyName from TwoCodeCompanyInfo where type = ?  and companyName like ?", new Object[] { "����", "%"+q+"%"});
		
			List<Map<String, Object>> listjson = new ArrayList<Map<String, Object>>();
			if(items != null){
		    for(CompanyInfo info : items){
		    	Map<String, Object> result = new HashMap<String, Object>();
		    	result.put("companyName", info.getCompanyName());
		    	result.put("id", info.getId());
		    	listjson.add(result);
		    }
			}
		
			
			return new JsonView(listjson);  
		}
	 
	 public View getAutoAzCompanyList(String q, int limit) throws Exception {
		 q = new String(q.getBytes("ISO-8859-1"), "UTF-8");
			List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
					"select top 10 id,companyName from TwoCodeCompanyInfo where type = ?  and companyName like ?", new Object[] { "��װ", "%"+q+"%"});
		
			List<Map<String, Object>> listjson = new ArrayList<Map<String, Object>>();
			if(items != null){
		    for(CompanyInfo info : items){
		    	Map<String, Object> result = new HashMap<String, Object>();
		    	result.put("companyName", info.getCompanyName());
		    	result.put("id", info.getId());
		    	listjson.add(result);
		    }
			}
		
			
			return new JsonView(listjson);  
		}
	 
	 public View getAutoZjCompanyList(String q, int limit) throws Exception {
		 q = new String(q.getBytes("ISO-8859-1"), "UTF-8");
			List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
					"select top 10 id,companyName from TwoCodeCompanyInfo where type = ?  and companyName like ?", new Object[] { "�ʼ�", "%"+q+"%"});
		
			List<Map<String, Object>> listjson = new ArrayList<Map<String, Object>>();
			if(items != null){
		    for(CompanyInfo info : items){
		    	Map<String, Object> result = new HashMap<String, Object>();
		    	result.put("companyName", info.getCompanyName());
		    	result.put("id", info.getId());
		    	listjson.add(result);
		    }
			}
		
			return new JsonView(listjson);  
		}
	 
	 public View getAutoJdbCompanyList(String q, int limit) throws Exception {
		 q = new String(q.getBytes("ISO-8859-1"), "UTF-8");
			List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
					"select id,companyName from TwoCodeCompanyInfo where type = ?  and companyName like ?", new Object[] { "�ֵ���", "%"+q+"%"});
		
			List<Map<String, Object>> listjson = new ArrayList<Map<String, Object>>();
			if(items != null){
		    for(CompanyInfo info : items){
		    	Map<String, Object> result = new HashMap<String, Object>();
		    	result.put("companyName", info.getCompanyName());
		    	result.put("id", info.getId());
		    	listjson.add(result);
		    }
			}
		
			return new JsonView(listjson);  
		}
	 
	 public View getNewAutoJdbCompanyList(String areaType,String q, int limit) throws Exception {
		 q = new String(q.getBytes("ISO-8859-1"), "UTF-8");
			List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
					"select id,companyName from TwoCodeCompanyInfo where type = ?  and companyName like ? and parea = ?", new Object[] { "�ֵ���", "%"+q+"%",areaType});
		
			List<Map<String, Object>> listjson = new ArrayList<Map<String, Object>>();
			if(items != null){
		    for(CompanyInfo info : items){
		    	Map<String, Object> result = new HashMap<String, Object>();
		    	result.put("companyName", info.getCompanyName());
		    	result.put("id", info.getId());
		    	listjson.add(result);
		    }
			}
		
			return new JsonView(listjson);  
		}
	 
	 public View getAutoWgCompanyList(String q, int limit) throws Exception {
		 q = new String(q.getBytes("ISO-8859-1"), "UTF-8");
			List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
					"select top 100 id,companyName from TwoCodeCompanyInfo where type = ?  and companyName like ?", new Object[] { "ʹ��", "%"+q+"%"});
		
			List<Map<String, Object>> listjson = new ArrayList<Map<String, Object>>();
			if(items != null){
		    for(CompanyInfo info : items){
		    	Map<String, Object> result = new HashMap<String, Object>();
		    	result.put("companyName", info.getCompanyName());
		    	result.put("id", info.getId());
		    	listjson.add(result);
		    }
			}
		
			
			return new JsonView(listjson);  
		}
	 
	 public View findAutoUserCompanyList(String q, int limit) throws UnsupportedEncodingException{
		 q = new String(q.getBytes("ISO-8859-1"), "UTF-8");
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 String sql="";
		 Object[] param=null;
		 if(role == 22 || role == 23){
			sql = "select id,companyName from TwoCodeCompanyInfo where type like ?  and companyName like ?"; 
			param = new Object[]{"%�ʼ�%","%"+q+"%"};
		 }
		 else if(role ==10 || role ==11){
			sql = "select id,companyName from TwoCodeCompanyInfo where type = ?  and companyName like ?";
			param = new Object[]{"�ʼ�","%"+q+"%"};
		 }
		 else{
			 sql = "select id,companyName from TwoCodeCompanyInfo where  companyName like ?";
			 param = new Object[]{"%"+q+"%"};
		 }
		 
		 List<CompanyInfo> items =null;
		try {
			items = CompanyInfo.findBySql(CompanyInfo.class,sql, param);
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		
			List<Map<String, Object>> listjson = new ArrayList<Map<String, Object>>();
			if(items != null){
		    for(CompanyInfo info : items){
		    	Map<String, Object> result = new HashMap<String, Object>();
		    	result.put("companyName", info.getCompanyName());
		    	result.put("id", info.getId());
		    	listjson.add(result);
		    }
			}
		
			
			return new JsonView(listjson);  
	 }
	 
	 public View findAutoUserCompanyList2(String compayType,String q, int limit) throws UnsupportedEncodingException{
		 q = new String(q.getBytes("ISO-8859-1"), "UTF-8");
		 System.out.println("compayType--->"+compayType);
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 String sql="";
		 Object[] param=null;
		 if(role == 22 || role == 23){
			sql = "select id,companyName from TwoCodeCompanyInfo where type like ?  and companyName like ?"; 
			param = new Object[]{"%�ʼ�%","%"+q+"%"};
		 }
		 else if(role ==10 || role ==11){
			sql = "select id,companyName from TwoCodeCompanyInfo where type = ?  and companyName like ?";
			param = new Object[]{"�ʼ�","%"+q+"%"};
		 }
		 else{
			 sql = "select top 10 id,companyName from TwoCodeCompanyInfo where  companyName like ? and type = ?";
			 param = new Object[]{"%"+q+"%",compayType};
		 }
		 
		 List<CompanyInfo> items =null;
		try {
			items = CompanyInfo.findBySql(CompanyInfo.class,sql, param);
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		
			List<Map<String, Object>> listjson = new ArrayList<Map<String, Object>>();
			if(items != null){
		    for(CompanyInfo info : items){
		    	Map<String, Object> result = new HashMap<String, Object>();
		    	result.put("companyName", info.getCompanyName());
		    	result.put("id", info.getId());
		    	listjson.add(result);
		    }
			}
		
			
			return new JsonView(listjson);  
	 }
	 
	 
	 public View  contractElevatorList(int id,int page, int rows){
		 
		  long total=0;
		  List<ElevaltorInfoVO> items = null;
		  String sql ="select count(*) from  TwoCodeElevatorInfo where contractId = ? ";
		  String sql2 ="select registNumber,address,buildingName,registCode,useNumber from TwoCodeElevatorInfo where contractId = ? ";
		  try {
			total = ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, sql, new Object[]{id});
			items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql2, new Object[]{id} , "id", rows, (page-1)*rows);
		  } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		  }
		  Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	 }
	 
	
	 
	 public View csywElevatorlist(int page,int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int contractCompanyId =0;
		 long total=0;
		  List<ElevaltorInfoVO> items = null;
		  String sql ="select count(*) from  TwoCodeElevatorInfo where ywCompanyId = ? ";
		  String sql2 ="select id,registNumber,area,address,buildingName,registCode,useNumber from TwoCodeElevatorInfo where ywCompanyId = ? ";
		
		 try {
				TCUserInfoView user =TCUserInfoView.findFirst(TCUserInfoView.class, "userid= ?", new Object[]{userid});
				if(user != null){   //���ú�ͬ��������ά��˾id
					contractCompanyId = user.getCompanyId();
			   }
				total = ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, sql, new Object[]{contractCompanyId});
				items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql2, new Object[]{contractCompanyId} , "id", rows, (page-1)*rows);
			
			
			} catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}
			 Map<String, Object> result = new HashMap<String, Object>();
		      result.put("total", total);
			  result.put("rows", items);
			  return new JsonView(result);
	 }
	 
	 public View csywElevatorquery(ElevaltorInfoVO info,int page,int rows){
		 String area ="";
		 String address="";
		 
		 area = info.getArea();
		 address ="%" + info.getAddress() + "%";
		 
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int contractCompanyId =0;
		 long total=0;
		  List<ElevaltorInfoVO> items = null;
		  String sql ="select count(*) from  TwoCodeElevatorInfo where ywCompanyId = ? and area = ? and address like ? ";
		  String sql2 ="select registNumber,area,address,buildingName,registCode,useNumber from TwoCodeElevatorInfo where ywCompanyId = ?  and area = ? and address like ? ";
		
		 try {
				TCUserInfoView user =TCUserInfoView.findFirst(TCUserInfoView.class, "userid= ?", new Object[]{userid});
				if(user != null){   //���ú�ͬ��������ά��˾id
					contractCompanyId = user.getCompanyId();
			   }
				total = ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, sql, new Object[]{contractCompanyId,area,address});
				items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql2, new Object[]{contractCompanyId,area,address} , "id", rows, (page-1)*rows);
			
			
			} catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}
			 Map<String, Object> result = new HashMap<String, Object>();
		      result.put("total", total);
			  result.put("rows", items);
			  return new JsonView(result);
		 
	 }
	 
	 public String eleRealationContract(String names,int id){  
		 String result = "failure";
		 System.out.println("names:"+names);
		 
		 try {
			    ActiveRecordBase.beginTransaction();
				ActiveRecordBase.execute("exec pro_elerRealationContract ?,?", new Object[] { names,id});
				ActiveRecordBase.commit();
				result ="success";
		      }catch (ActiveRecordException e) {
					e.printStackTrace();
					try {
						ActiveRecordBase.rollback();
					}catch (TransactionException e1) {
						e1.printStackTrace();
					}
				}	
		 
		 return result;
	 }
	 
	 protected boolean  querylog(){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
	//	 int userid = userinfo.getId();
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
				twoCodeLogInfo.setLogAction("��ѯ");
				twoCodeLogInfo.setLogContext("���ݹ�����ѯ");
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
			
		 return true;
	 }
	 
	 public String putdailingele(int id){  
		 String result = "failure";
		 int zjcompanyId =0;
		 try {  
			    ElevaltorInfoVO   elevaltorInfoVO =ElevaltorInfoVO.findFirst(ElevaltorInfoVO.class, "id = ? ", new Object[]{id});
			    if(elevaltorInfoVO != null)
			    	this.dlregistNumber =elevaltorInfoVO.getRegistNumber(); 
			    CompanyInfo	 zjCompanyInfo =CompanyInfo.findFirst(CompanyInfo.class, "companyName =? and type = ?", new Object[] {"","�ʼ�"});
			    if(zjCompanyInfo != null)
			    	zjcompanyId = zjCompanyInfo.getId();
				if(zjcompanyId > 0){
				ActiveRecordBase.execute("update TwoCodeElevatorInfo set dailingFlag = 1,zjcompanyId = ?  where id = ?", new Object[]{zjcompanyId,id});
				result ="success";
				}
		      }catch (ActiveRecordException e) {
					e.printStackTrace();
				}	
		 
		 return result;
	 }
	 
	 protected boolean  putdailingelelog(){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
	//	 int userid = userinfo.getId();
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
				twoCodeLogInfo.setLogAction("����");
				twoCodeLogInfo.setLogContext(this.dlregistNumber+"���ݷ��������");
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
			
		 return true;
	 }
	 
	 public String claimdailingele(int id){
		 String result = "failure";
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int zjcompanyId =0;
		 try { 
			 ElevaltorInfoVO   elevaltorInfoVO =ElevaltorInfoVO.findFirst(ElevaltorInfoVO.class, "id = ? ", new Object[]{id});
			    if(elevaltorInfoVO != null)
			    	this.dlregistNumber2 =elevaltorInfoVO.getRegistNumber(); 
			
			   UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	   if(userInfoVO != null){
	            zjcompanyId = userInfoVO.getCompanyId();
				if(zjcompanyId > 0){
				ActiveRecordBase.execute("update TwoCodeElevatorInfo set dailingFlag = 0,zjcompanyId = ?  where id = ?", new Object[]{zjcompanyId,id});
				result ="success";
				}
	    	   }
		      }catch (ActiveRecordException e) {
					e.printStackTrace();
				}	
		 
		 return result; 	 
	 }
	 
	 protected boolean  claimdailingelelog(){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
	//	 int userid = userinfo.getId();
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
				twoCodeLogInfo.setLogAction("����");
				twoCodeLogInfo.setLogContext(this.dlregistNumber2+"���ݱ�����");
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
			
		 return true;
	 }
	 
	 protected boolean  elevatorlistlog(){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
	//	 int userid = userinfo.getId();
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
				twoCodeLogInfo.setLogAction("����");
				twoCodeLogInfo.setLogContext("���ݹ����б�");
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
			
		 return true;
	 }
	 
	 public View dailingelevatorlist(int page, int rows) throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 long total=0;
		 List<ElevaltorInfoVO> items = null;
	     if(role==2 || role ==1){ //ϵͳ����Ա TwoCodeCompanyInfo 
	    	 total  =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, "select count(*) from TwoCodeElevatorInfo where dailingFlag = 1", null);
	      //   String sql ="select t.id,t.registNumber,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,isnull(t.deviceId2,'') as deviceId2,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where dailingFlag = 1";
	         String sql ="select t.id,t.registNumber,t.address,t.buildingName,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,(select top 1 max(y.subTime)  from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0') as subTime,tcr.deviceId,t.ischangInfo,t.deviceId2,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where dailingFlag = 1";    	
	    	 items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null, "t.updateTime desc", rows, (page-1)*rows);
	    	
	    	
	     }
	     
	     if(role==22 || role ==23){ //ϵͳ����Ա TwoCodeCompanyInfo 
	    	 total  =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class,  "select count(*) from TwoCodeElevatorInfo where dailingFlag = 1", null);
	    //     String sql ="select t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,isnull(t.deviceId2,'') as deviceId2  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where dailingFlag = 1";
	    	 String sql ="select t.id,t.registNumber,t.address, t.buildingName,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select max(y.subTime) from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0') as subTime,tcr.deviceId,t.ischangInfo,t.deviceId2  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where dailingFlag = 1";
	    	 
	    	 items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null, "t.updateTime desc", rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==10 || role ==11){
	    	 total  =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, "select count(*) from TwoCodeElevatorInfo t  where t.dailingFlag = 1 ", null);
	    	 String sql ="select t.id,t.registNumber,t.address,t.buildingName,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select max(y.subTime) from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' ) as subTime,tcr.deviceId,t.ischangInfo,t.deviceId2  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where  t.dailingFlag = 1 ";
	    	 items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null, "t.updateTime desc", rows, (page-1)*rows);	
	    	 }
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	 
	 public View sdailingelevatorlistByOrder(int page, int rows,String sort,String order){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 String SortName = request.getParameter("sort");  
	        //�״μ��ر���ʱsort��orderΪnull  
	        if(SortName == null){  
	            SortName ="t.source"; 
	        }
	        
	     String SortValue =request.getParameter("order");  
	        if(SortValue == null){  
	            SortValue = "asc";        
	        }  
	        
	        
		 long total=0;
		 List<SelevaltorInfoView> items = null;
		 try {
	     if(role==2 || role ==1){ //ϵͳ����Ա TwoCodeCompanyInfo 
			  total  =SelevaltorInfoView.countBySql(SelevaltorInfoView.class, "select count(*) from TwoCodeSElevatorInfoVIEW ", null);
	         String sql ="select t.source,t.id,t.registNumber,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,t.unit,t.registCode,t.useNumber,t.area,t.wgCompanyName,t.ywCompanyName,t.beizhu,t.endTime  from TwoCodeSElevatorInfoVIEW  t ";
	    	 items = SelevaltorInfoView.findBySql(SelevaltorInfoView.class, sql, null, SortName+" "+SortValue, rows, (page-1)*rows);
	    	
	    	
	     }
	     
	     if(role==22 || role ==23){ //ϵͳ����Ա TwoCodeCompanyInfo 
	    	 total  =SelevaltorInfoView.countBySql(SelevaltorInfoView.class,  "select count(*) from TwoCodeSElevatorInfoVIEW ", null);
	         String sql ="select t.source,t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.unit,t.registCode,t.useNumber,t.area,t.wgCompanyName,t.ywCompanyName,t.beizhu,t.endTime  from TwoCodeSElevatorInfoVIEW  t";
	    	 items = SelevaltorInfoView.findBySql(SelevaltorInfoView.class, sql, null, SortName+" "+SortValue, rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==10 || role ==11){
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
	    	 if(userInfoVO != null){
	         String companyArea = userInfoVO.getArea();
	    	 total  =SelevaltorInfoView.countBySql(SelevaltorInfoView.class, "select count(*) from TwoCodeSElevatorInfoVIEW t  where t.area = ? ", new Object[]{companyArea} );
	    	 String sql ="select t.source,t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.unit,t.registCode,t.useNumber,t.area,t.wgCompanyName,t.ywCompanyName,t.beizhu,t.endTime  from TwoCodeSElevatorInfoVIEW  t  where  t.area = ?";
	    	 items = SelevaltorInfoView.findBySql(SelevaltorInfoView.class, sql, new Object[]{companyArea}, SortName+" "+SortValue, rows, (page-1)*rows);	
	    	 }
	     }
	     } catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	 
	 public View sdailingelevatorlist(int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 long total=0;
		 List<SelevaltorInfoView> items = null;
		 try {
	     if(role==2 || role ==1){ //ϵͳ����Ա TwoCodeCompanyInfo 
			  total  =SelevaltorInfoView.countBySql(SelevaltorInfoView.class, "select count(*) from TwoCodeSElevatorInfoVIEW ", null);
	         String sql ="select t.source,t.id,t.registNumber,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,t.unit,t.registCode,t.useNumber,t.area,t.wgCompanyName,t.ywCompanyName,t.beizhu  from TwoCodeSElevatorInfoVIEW  t ";
	    	 items = SelevaltorInfoView.findBySql(SelevaltorInfoView.class, sql, null, "t.source", rows, (page-1)*rows);
	    	
	    	
	     }
	     
	     if(role==22 || role ==23){ //ϵͳ����Ա TwoCodeCompanyInfo 
	    	 total  =SelevaltorInfoView.countBySql(SelevaltorInfoView.class,  "select count(*) from TwoCodeSElevatorInfoVIEW ", null);
	         String sql ="select t.source,t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.unit,t.registCode,t.useNumber,t.area,t.wgCompanyName,t.ywCompanyName,t.beizhu  from TwoCodeSElevatorInfoVIEW  t";
	    	 items = SelevaltorInfoView.findBySql(SelevaltorInfoView.class, sql, null, "t.source", rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==10 || role ==11){
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
	    	 if(userInfoVO != null){
	         String companyArea = userInfoVO.getArea();
	    	 total  =SelevaltorInfoView.countBySql(SelevaltorInfoView.class, "select count(*) from TwoCodeSElevatorInfoVIEW t  where t.area = ? ", new Object[]{companyArea} );
	    	 String sql ="select t.source,t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.unit,t.registCode,t.useNumber,t.area,t.wgCompanyName,t.ywCompanyName,t.beizhu  from TwoCodeSElevatorInfoVIEW  t  where  t.area = ?";
	    	 items = SelevaltorInfoView.findBySql(SelevaltorInfoView.class, sql, new Object[]{companyArea}, "t.source", rows, (page-1)*rows);	
	    	 }
	     }
	     } catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	 
	
	 
	 public View shemielevatorlist(int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 long total=0;
		 List<ElevaltorInfoVO> items = null;
		 try {
	     if(role==2 || role ==1){ //ϵͳ����Ա TwoCodeCompanyInfo 
			  total  =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, "select count(*) from TwoCodeElevatorInfo where shemiFlag = ?", new Object[]{1});
	         String sql ="select t.shemiFlag,t.id,t.registNumber,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,t.unit,t.registCode,t.useNumber,t.area,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName  from TwoCodeElevatorInfo  t where shemiFlag = 1 ";
	    	 items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null, "t.updateTime desc", rows, (page-1)*rows);
	    	
	    	
	     }
	     
	     if(role==22 || role ==23){ //ϵͳ����Ա TwoCodeCompanyInfo 
	    	 total  =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, "select count(*) from TwoCodeElevatorInfo where shemiFlag = ?", new Object[]{1});
	         String sql ="select t.shemiFlag,t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.unit,t.registCode,t.useNumber,t.area,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName  from TwoCodeElevatorInfo  t where shemiFlag = 1 ";
	    	 items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null, "t.updateTime desc", rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==10 || role ==11){
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
	    	 if(userInfoVO != null){
	         String companyArea = userInfoVO.getArea();
	    	 total  =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, "select count(*) from TwoCodeElevatorInfo t  where t.area = ? and t.shemiFlag = 1", new Object[]{companyArea} );
	    	 String sql ="select t.shemiFlag,t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.unit,t.registCode,t.useNumber,t.area,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName  from TwoCodeElevatorInfo  t  where  t.area = ? and t.shemiFlag = 1";
	    	 items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, new Object[]{companyArea}, "t.updateTime desc", rows, (page-1)*rows);	
	    	 }
	     }
	     } catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	 
	 public View dailingelequery(ElevaltorInfoVO info,int page, int rows)throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 System.out.println("ElevatorController---query---��½����---"+userName);
		 System.out.println("ElevatorController---query---role---"+role);
		 
		 String registNumber ="";
		 int ywCompanyId =0;  //��ѯ�õ�
		 int zjcompanyId2 =0;  //�ǲ�ѯ�õģ��ǵ�½������ϵͳ��̨����role��ͬչʾ��ͬ�б��õ�
		 String companyName="";
		 String areaName="";
		 String address ="";
		 String registCode="";
		 String useNumber="";
		 
		 if(info.getAddress()==null)
			 address ="%"+""+"%";	 
		 else
		 address ="%"+info.getAddress()+"%";
		 
		 if(info.getYwCompanyName() == null)
			 companyName ="%"+""+"%";
		 else
		 companyName ="%"+info.getYwCompanyName()+"%";
		 
		 if(info.getRegistNumber() == null)
			 registNumber ="%"+""+"%";
		 else	 
		 registNumber ="%"+info.getRegistNumber()+"%";
		 
		 if(info.getArea()==null)
			 areaName ="%"+""+"%";
		 else
		 areaName ="%"+info.getArea()+"%";
		 
		 if(info.getRegistCode()==null)
			 registCode="%"+""+"%";
		 else
			 registCode="%"+info.getRegistCode()+"%";
		 
		 ywCompanyId =info.getYwCompanyId();
		 
		 if(info.getUseNumber()==null)
			 useNumber="%"+""+"%";
		 else
			 useNumber="%"+info.getUseNumber()+"%";
		 
		 
		 
		 String conditions="";
		 String conditionsSql="";
		 if(role==2 || role==1){
		    if("".equals(info.getRegistCode())){ 
		    	 if("".equals(info.getUseNumber())){
		    	       conditions =" t.registNumber like  ?  and t.area like ? and  (t.registCode like ? or t.registCode is null) and (t.useNumber like ? or t.useNumber is null) and ((t.address like ? or t.buildingName like ?) or  (t.wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?)))";
				       conditionsSql=" registNumber like  ?  and area like ? and (registCode like ? or registCode is null) and (useNumber like ? or useNumber is null) and ((address like ? or buildingName like ?) or  (wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?)))";
				    
		    	 }
		    	 else{
		         conditions =" t.registNumber like  ?  and t.area like ? and  (t.registCode like ? or t.registCode is null) and t.useNumber like ? and ((t.address like ? or t.buildingName like ?) or  (t.wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?)))";
		         conditionsSql=" registNumber like  ?  and area like ? and (registCode like ? or registCode is null) and useNumber like ? and ((address like ? or buildingName like ?) or  (wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?)))";
		      }
		    }
		    else{
		    	 if("".equals(info.getUseNumber())){
		    		 conditions =" t.registNumber like  ?  and t.area like ? and  t.registCode like ? and (t.useNumber like ? or t.useNumber is null) and ((t.address like ? or t.buildingName like ?) or  (t.wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?)))";
				     conditionsSql=" registNumber like  ?  and area like ? and registCode like ?  and (useNumber like ? or useNumber is null) and ((address like ? or buildingName like ?) or  (wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?)))";
			    	  
		    	 }
		    	 else{
		    	 conditions =" t.registNumber like  ?  and t.area like ? and  t.registCode like ? and t.useNumber like ? and ((t.address like ? or t.buildingName like ?) or  (t.wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?)))";
			     conditionsSql=" registNumber like  ?  and area like ? and registCode like ?  and useNumber like ? and ((address like ? or buildingName like ?) or  (wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?)))";
		    	 }
		    }
		 } 
		 if(role==22 || role==23){
			  if("".equals(info.getRegistCode())){
				  if("".equals(info.getUseNumber())){
				      conditions =" t.registNumber like  ?  and t.area like ? and (t.registCode like ? or t.registCode is null) and (t.useNumber like ? or t.useNumber is null) and ((t.address like ? or t.buildingName like ?) or  (t.wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?)))";
			          conditionsSql=" registNumber like  ?  and area like ? and (registCode like ? or registCode is null) and (useNumber like ? or useNumber is null) and ((address like ? or buildingName like ?) or  (wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?)))";
				     
				  }
				  else{
			         conditions =" t.registNumber like  ?  and t.area like ? and (t.registCode like ? or t.registCode is null) and t.useNumber like ? and ((t.address like ? or t.buildingName like ?) or  (t.wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?)))";
		             conditionsSql=" registNumber like  ?  and area like ? and (registCode like ? or registCode is null) and useNumber like ? and ((address like ? or buildingName like ?) or  (wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?)))";
			    }
			  }
			  else{
				  if("".equals(info.getUseNumber())){
					  conditions =" t.registNumber like  ?  and t.area like ? and t.registCode like ? and (t.useNumber like ? or t.useNumber is null) and ((t.address like ? or t.buildingName like ?) or  (t.wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?)))";
				      conditionsSql=" registNumber like  ?  and area like ? and registCode like ?  and (useNumber like ? or useNumber is null) and ((address like ? or buildingName like ?) or  (wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?)))";
					
				  }
				  else{
				  conditions =" t.registNumber like  ?  and t.area like ? and t.registCode like ? and t.useNumber like ? and ((t.address like ? or t.buildingName like ?) or  (t.wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?)))";
			      conditionsSql=" registNumber like  ?  and area like ? and registCode like ?  and useNumber like ? and ((address like ? or buildingName like ?) or  (wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?)))";
				  }
			  }
		 }
		 if(role==10 || role==11){
		//	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    //	 if(userInfoVO != null){
	  // 	 zjcompanyId2 = userInfoVO.getCompanyId();
	    	 if("".equals(info.getRegistCode())){
	    		  if("".equals(info.getUseNumber())){
	    			    conditions =" t.registNumber like  ?  and t.area like ? and (t.registCode like ? or t.registCode is null) and (t.useNumber like ? or t.useNumber is null) and ((t.address like ? or t.buildingName like ?) or  (t.wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?))) ";
	 			        conditionsSql=" registNumber like  ?  and area like ?  and (registCode like ? or registCode is null) and (useNumber like ? or useNumber is null) and ((address like ? or buildingName like ?) or  (wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?))) ";
	 		          
	    		  }
	    		  else{
			        conditions =" t.registNumber like  ?  and t.area like ? and (t.registCode like ? or t.registCode is null) and t.useNumber like ? and ((t.address like ? or t.buildingName like ?) or  (t.wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?))) ";
			        conditionsSql=" registNumber like  ?  and area like ?  and (registCode like ? or registCode is null) and useNumber like ? and ((address like ? or buildingName like ?) or  (wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?))) ";
		          }
	    	 }
	    	 else{
	    		 if("".equals(info.getUseNumber())){
	    		     conditions =" t.registNumber like  ?  and t.area like ? and t.registCode like ? and (t.useNumber like ? or t.useNumber is null) and ((t.address like ? or t.buildingName like ?) or  (t.wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?))) ";
				     conditionsSql=" registNumber like  ?  and area like ?  and registCode like ? and (useNumber like ? or useNumber is null) and ((address like ? or buildingName like ?) or  (wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?))) ";
	    	
	    		 }
	    		 else{
	    		     conditions =" t.registNumber like  ?  and t.area like ? and t.registCode like ? and t.useNumber like ? and ((t.address like ? or t.buildingName like ?) or  (t.wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?))) ";
				     conditionsSql=" registNumber like  ?  and area like ?  and registCode like ? and useNumber like ? and ((address like ? or buildingName like ?) or  (wgCompanyId in (select tcc.id from TwoCodeCompanyInfo tcc where   tcc.type ='ʹ��' and tcc.companyName like ?))) ";
	    		 }
	    		}
	    //	 }
		 }
		 Object[] param=null;
		 if(ywCompanyId>0){
			 conditions +=" and t.ywCompanyId = ? ";
			 conditionsSql +=" and ywCompanyId = ?";
			 if(role==2 || role==1)
			 param = new Object[]{registNumber,areaName,registCode,useNumber,address,address,address,ywCompanyId};
			 if(role==22 || role==23)
			 param = new Object[]{registNumber,areaName,registCode,useNumber,address,address,address,ywCompanyId};
			 if(role==10 || role==11)
			 param = new Object[]{registNumber,areaName,registCode,useNumber,address,address,address,ywCompanyId};	 
		 }
		 else{
			 conditions +=" and t.companyName like ? ";
			 conditionsSql +=" and companyName like ?";
			 if(role==2 || role==1)
			 param = new Object[]{registNumber,areaName,registCode,useNumber,address,address,address,companyName};
			 if(role==22 || role==23)
			 param = new Object[]{registNumber,areaName,registCode,useNumber,address,address,address,companyName};
			 if(role==10 || role==11)
			  param = new Object[]{registNumber,areaName,registCode,useNumber,address,address,address,companyName};	 
		 }
		 
		 
		 System.out.println("����ѯ����-----"+conditions);
		 
		
		 conditionsSql = conditionsSql+" and dailingFlag = 1 ";
		 conditions    = conditions + " and t.dailingFlag = 1 ";
		 String sql ="select t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.useNumber,t.inspector,inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorYwCompanyInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where "+conditions;
		 
		 
		 long total =TwoCodeElevatorYwCompanyInfo.count(TwoCodeElevatorYwCompanyInfo.class, conditionsSql, param);
		 System.out.println("total----"+total);
		 
		 List<TwoCodeElevatorYwCompanyInfo> items=TwoCodeElevatorYwCompanyInfo.findBySql(TwoCodeElevatorYwCompanyInfo.class, sql, param, "t.updateTime desc", rows, (page-1)*rows);
			
		 
		  System.out.println("------"+items.size());
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		 }
	 
	 public View sdailingelequeryByOrder(SelevaltorInfoView info,int page, int rows,String sort,String order)throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 String SortName = request.getParameter("sort");  
	        //�״μ��ر���ʱsort��orderΪnull  
	        if(SortName == null){  
	            SortName ="source,registNumber";  
	        }  
	        String SortValue =request.getParameter("order");  
	        if(SortValue == null){  
	            SortValue = "asc";        
	        }  
		 
		 String registNumber ="";
		 int ywCompanyId =0;  //��ѯ�õ�
	//	 int zjcompanyId2 =0;  //�ǲ�ѯ�õģ��ǵ�½������ϵͳ��̨����role��ͬչʾ��ͬ�б��õ�
		 String companyName="";
		 String areaName="";
		 String address ="";
		 String registCode="";
		 String buildingName = "";
		 String useNumber="";
		 
		 
         address =info.getAddress();
		 registNumber =info.getRegistNumber();
		 areaName =info.getArea();
		 buildingName =info.getBuildingName();
		 registCode=info.getRegistCode();
		
		
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 
	        
	        
         if(role==10 || role==11){
           UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
         if(userInfoVO != null){
             String companyArea = userInfoVO.getArea();
             conditions = " t.area = '"+companyArea+"'";
             }	 
          }
         
         if(!"".equals(registNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registNumber like '%"+registNumber+"%'";	
				} 
				else{
				 conditions =" t.registNumber like '%"+registNumber+"%'";	
				}
			 
		 }
         
         if(!"".equals(address)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.address like '%"+address+"%'";	
				} 
				else{
					 conditions =" t.address like '%"+address+"%'";	
				}
			 
		 }
         
         if(!"".equals(buildingName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.buildingName like '%"+buildingName+"%'";	
				} 
				else{
					conditions =" t.buildingName like '%"+buildingName+"%'";	
				}
			 
		 }
         
         if(!"".equals(registCode)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registCode like '%"+registCode+"%'";	
				} 
				else{
					conditions =" t.registCode like '%"+registCode+"%'";	
				}
			 
		 }
         
         
         if(!"".equals(useNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.useNumber like '%"+useNumber+"%'";	
				} 
				else{
					conditions =" t.useNumber like '%"+useNumber+"%'";	
				}
			 
		 } 
         
         if(!"".equals(areaName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.area like '%"+areaName+"%'";	
				} 
				else{
					conditions =" t.area like '%"+areaName+"%'";	
				}
			 
		 }
		 
		 
         if(!"".equals(conditions)){
      		  sql ="select  t.source,t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.unit,t.registCode,t.useNumber,t.area,t.wgCompanyName,t.ywCompanyName,t.beizhu,t.endTime  from TwoCodeSElevatorInfoVIEW  t  where "+ conditions;  
      		  conditionsSql = "select count(*) from TwoCodeSElevatorInfoVIEW  t  where "+ conditions;
      		 }
      		 else{
      		  sql ="select  t.source,t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.unit,t.registCode,t.useNumber,t.area,t.wgCompanyName,t.ywCompanyName,t.beizhu,t.endTime  from TwoCodeSElevatorInfoVIEW  t ";
      		  conditionsSql = "select count(*) from TwoCodeSElevatorInfoVIEW  t ";
      					 
      		 }

		 
		
         long total =SelevaltorInfoView.countBySql(SelevaltorInfoView.class, conditionsSql, null); 
		 List<SelevaltorInfoView> items=SelevaltorInfoView.findBySql(SelevaltorInfoView.class, sql, null, SortName+" "+SortValue, rows, (page-1)*rows);
			
		 
		 
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		 }
	 
	 
	 
	 public View sdailingelequery(SelevaltorInfoView info,int page, int rows)throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 String registNumber ="";
		 int ywCompanyId =0;  //��ѯ�õ�
	//	 int zjcompanyId2 =0;  //�ǲ�ѯ�õģ��ǵ�½������ϵͳ��̨����role��ͬչʾ��ͬ�б��õ�
		 String companyName="";
		 String areaName="";
		 String address ="";
		 String registCode="";
		 String buildingName = "";
		 String useNumber="";
		 
		 
         address =info.getAddress();
		 registNumber =info.getRegistNumber();
		 areaName =info.getArea();
		 buildingName =info.getBuildingName();
		 registCode=info.getRegistCode();
		
		
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 
	        
	        
         if(role==10 || role==11){
           UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
         if(userInfoVO != null){
             String companyArea = userInfoVO.getArea();
             conditions = " t.area = '"+companyArea+"'";
             }	 
          }
         
         if(!"".equals(registNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registNumber like '%"+registNumber+"%'";	
				} 
				else{
				 conditions =" t.registNumber like '%"+registNumber+"%'";	
				}
			 
		 }
         
         if(!"".equals(address)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.address like '%"+address+"%'";	
				} 
				else{
					 conditions =" t.address like '%"+address+"%'";	
				}
			 
		 }
         
         if(!"".equals(buildingName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.buildingName like '%"+buildingName+"%'";	
				} 
				else{
					conditions =" t.buildingName like '%"+buildingName+"%'";	
				}
			 
		 }
         
         if(!"".equals(registCode)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registCode like '%"+registCode+"%'";	
				} 
				else{
					conditions =" t.registCode like '%"+registCode+"%'";	
				}
			 
		 }
         
         
         if(!"".equals(useNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.useNumber like '%"+useNumber+"%'";	
				} 
				else{
					conditions =" t.useNumber like '%"+useNumber+"%'";	
				}
			 
		 } 
         
         if(!"".equals(areaName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.area like '%"+areaName+"%'";	
				} 
				else{
					conditions =" t.area like '%"+areaName+"%'";	
				}
			 
		 }
		 
		 
         if(!"".equals(conditions)){
      		  sql ="select  t.source,t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.unit,t.registCode,t.useNumber,t.area,t.wgCompanyName,t.ywCompanyName,t.beizhu  from TwoCodeSElevatorInfoVIEW  t  where "+ conditions;  
      		  conditionsSql = "select count(*) from TwoCodeSElevatorInfoVIEW  t  where "+ conditions;
      		 }
      		 else{
      		  sql ="select  t.source,t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.unit,t.registCode,t.useNumber,t.area,t.wgCompanyName,t.ywCompanyName,t.beizhu  from TwoCodeSElevatorInfoVIEW  t ";
      		  conditionsSql = "select count(*) from TwoCodeSElevatorInfoVIEW  t ";
      					 
      		 }

		 
		
         long total =SelevaltorInfoView.countBySql(SelevaltorInfoView.class, conditionsSql, null); 
		 List<SelevaltorInfoView> items=SelevaltorInfoView.findBySql(SelevaltorInfoView.class, sql, null, "t.updateTime desc", rows, (page-1)*rows);
			
		 
		 
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		 }
	 
	 
	 public View smquery(SelevaltorInfoView info,int page, int rows)throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 String registNumber ="";
		 int ywCompanyId =0;  //��ѯ�õ�
	//	 int zjcompanyId2 =0;  //�ǲ�ѯ�õģ��ǵ�½������ϵͳ��̨����role��ͬչʾ��ͬ�б��õ�
		 String companyName="";
		 String areaName="";
		 String address ="";
		 String registCode="";
		 String buildingName = "";
		 String useNumber="";
		 
		 
         address =info.getAddress();
		 registNumber =info.getRegistNumber();
		 areaName =info.getArea();
		 buildingName =info.getBuildingName();
		 registCode=info.getRegistCode();
		
		
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 
	        
	        
         if(role==10 || role==11){
           UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
         if(userInfoVO != null){
             String companyArea = userInfoVO.getArea();
             conditions = " t.area = '"+companyArea+"'";
             }	 
          }
         
         if(!"".equals(registNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registNumber like '%"+registNumber+"%'";	
				} 
				else{
				 conditions =" t.registNumber like '%"+registNumber+"%'";	
				}
			 
		 }
         
         if(!"".equals(address)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.address like '%"+address+"%'";	
				} 
				else{
					 conditions =" t.address like '%"+address+"%'";	
				}
			 
		 }
         
         if(!"".equals(buildingName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.buildingName like '%"+buildingName+"%'";	
				} 
				else{
					conditions =" t.buildingName like '%"+buildingName+"%'";	
				}
			 
		 }
         
         if(!"".equals(registCode)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registCode like '%"+registCode+"%'";	
				} 
				else{
					conditions =" t.registCode like '%"+registCode+"%'";	
				}
			 
		 }
         
         
         if(!"".equals(useNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.useNumber like '%"+useNumber+"%'";	
				} 
				else{
					conditions =" t.useNumber like '%"+useNumber+"%'";	
				}
			 
		 } 
         
         if(!"".equals(areaName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.area like '%"+areaName+"%'";	
				} 
				else{
					conditions =" t.area like '%"+areaName+"%'";	
				}
			 
		 }
		 
		 
         if(!"".equals(conditions)){
      		  sql ="select t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,t.azCompanyId,t.wgCompanyId,t.ywCompanyId,t.zjCompanyId,t.jyCompanyId,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where "+ conditions+" and t.shemiFlag = 1";  
      		  conditionsSql = "select count(*) from TwoCodeElevatorInfo  t  where "+ conditions+" and t.shemiFlag = 1 ";
      		 }
      		 else{
      			 sql ="select t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,t.azCompanyId,t.wgCompanyId,t.ywCompanyId,t.zjCompanyId,t.jyCompanyId,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where   t.shemiFlag = 1";  
         		 conditionsSql = "select count(*) from TwoCodeElevatorInfo  t  where  t.shemiFlag = 1 ";
         				 
      		 }

		 
		
         long total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, conditionsSql, null); 
		 List<ElevaltorInfoVO> items=ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null, "t.updateTime desc", rows, (page-1)*rows);
			
		 
		 
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		 }
	 
	 
	 
	 public View queryconditionlist(){
			UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		    String userName =userinfo.getLoginName();
		    List<GjQueryInfoVO> items =null;
		    
			try {
				
				ActiveRecordBase.beginTransaction();
				items = GjQueryInfoVO.findBySql(GjQueryInfoVO.class,"select id,quname,qswitch,qshow,qindex,qconditions,qcontent from TwoCodeGjqueryInfo where logname = ? ", new Object[] { userName});
				if(items == null || items.size() < 1){   
					ActiveRecordBase.execute("exec pro_initialgjquery ?", new Object[] { userName});
					ActiveRecordBase.commit();
					items = GjQueryInfoVO.findBySql(GjQueryInfoVO.class,"select id,quname,qswitch,qshow,qindex,qconditions,qcontent from TwoCodeGjqueryInfo where logname = ? ", new Object[] { userName});
					
			}
				
			}catch (Exception e){
			    try {
				   ActiveRecordBase.rollback();
			    } catch (TransactionException e1) {
						e1.printStackTrace();
					}
						
			}
		    Map<String, Object> result = new HashMap<String, Object>();
			result.put("rows", items);
			return new JsonView(result);
		
		}
	 
	 public View gjquery(GjQueryInfo info,int page, int rows){
		 String cityName = GlobalFunction.cityName;
		System.out.println("gjquery--->begin");
		String sql ="";
		String countSql="";
		String conditon ="";
		String qregistNumber ="";
		String qregistCode ="";
		String qarea="";
		String qbuildingName="";
		int  qywCompanyId =0;
		String qmContractVdate="";
		String qmContractVdate2="";
	//	String qinspectDate ="";
	//	String qinspectDate2 ="";
		String qeleType="";
		String qregistCode2="";
		int qzzCompanyId =0;
		String qfactoryNum="";
		int qazCompanyId =0;
		String quseDate ="";
		String quseDate2 ="";
		int qwgCompanyId =0;
		String quseNumber ="";
		
		
		qregistNumber = info.getQregistNumber();
		qregistCode = info.getQregistCode();
		qarea = info.getQarea();
		qbuildingName = info.getQbuildingName();
		qywCompanyId =info.getQywCompanyId();
		
		qmContractVdate =info.getQmContractVdate();
		qmContractVdate2 =info.getQmContractVdate2();
	//	qinspectDate = info.getQinspectDate();
	//	qinspectDate2 = info.getQinspectDate2();
		qeleType = info.getQeleType();
		qregistCode2 =info.getQregistCode2();
		qzzCompanyId =info.getQzzCompanyId();
		qfactoryNum = info.getQfactoryNum();
		qazCompanyId = info.getQazCompanyId();
		quseDate = info.getQuseDate();
		quseDate2 =info.getQuseDate2();
		quseNumber = info.getQuseNumber();
		
		if("".equals(conditon)){
			if(!"".equals(qregistNumber))
				conditon = "  where t.registNumber like \'%"+qregistNumber+"%\'";
		}
		
		
		if("".equals(conditon)){
			if(!"".equals(qregistCode))
				conditon ="  where t.registCode like \'%"+qregistCode+"%\'";
			
		}
		else{
			if(!"".equals(qregistCode))
				conditon =conditon+"  and  t.registCode like \'%"+qregistCode+"%\'";
		}
		
		
		if("".equals(conditon)){
			if(!"".equals(qarea))
				conditon ="  where t.area = \'"+qarea+"\'";
		}
		else{
			if(!"".equals(qarea))
				conditon =conditon+"  and t.area like \'"+qarea+"\'";
		}
		
		
		if("".equals(conditon)){
			if(!"".equals(qbuildingName))
				conditon ="  where t.buildingName like \'%"+qbuildingName+"%\'";
		}
		else{
			if(!"".equals(qbuildingName))
				conditon =conditon+"  and t.buildingName like \'%"+qbuildingName+"%\'";
		}

		
		if("".equals(conditon)){
			if(qywCompanyId >0 )
				conditon ="  where t.ywCompanyId = "+qywCompanyId;
		}
		else{
			if(qywCompanyId >0 )
				conditon =conditon+"  and t.ywCompanyId = "+qywCompanyId;
		}
		
		
		if("".equals(conditon)){
			if(!"".equals(qmContractVdate))
				conditon ="  where t.mContractVdate >= \'"+qmContractVdate+"\'";
		}
		else{
			if(!"".equals(qmContractVdate))
				conditon =conditon+"  and t.mContractVdate >= \'"+qmContractVdate+"\'";
		}
		
		if("".equals(conditon)){
			if(!"".equals(qmContractVdate2))
				conditon ="  where t.mContractVdate <= \'"+qmContractVdate2+"\'";
		}
		else{
			if(!"".equals(qmContractVdate2))
				conditon =conditon+"  and t.mContractVdate <= \'"+qmContractVdate2+"\'";
		}
		
		/*
		if("".equals(conditon)){
			if(!"".equals(qinspectDate))
				conditon ="  where t.inspectDate >= \'"+qinspectDate+"\'";
		}
		else{
			if(!"".equals(qinspectDate))
				conditon =conditon+"  and t.inspectDate >= \'"+qinspectDate+"\'";
		}
		
		
		if("".equals(conditon)){
			if(!"".equals(qinspectDate2))
				conditon ="  where t.inspectDate <= \'"+qinspectDate2+"\'";
		}
		else{
			if(!"".equals(qinspectDate2))
				conditon =conditon+"  and t.inspectDate <= \'"+qinspectDate2+"\'";
		}
		*/
		if("".equals(conditon)){
			if(!"".equals(qeleType))
				conditon ="  where t.eleType = \'"+qeleType+"\'";
		}
		else{
			if(!"".equals(qeleType))
				conditon =conditon+"  and t.eleType = \'"+qeleType+"\'";
		}
		
		if("".equals(conditon)){
			if(!"".equals(qregistCode2))
				conditon ="  where t.registCode2 like \'%"+qregistCode2+"%\'";
		}
		else{
			if(!"".equals(qregistCode2))
				conditon =conditon+"  and t.registCode2 like \'%"+qregistCode2+"%\'";
		}
		
		
		
		if("".equals(conditon)){
			if(qzzCompanyId >0 )
				conditon ="  where t.zzCompanyId = "+qzzCompanyId;
		}
		else{
			if(qzzCompanyId >0 )
				conditon =conditon+"  and t.zzCompanyId = "+qzzCompanyId;
		}
		
		 
		if("".equals(conditon)){
			if(!"".equals(qfactoryNum))
				conditon ="  where t.factoryNum like \'%"+qfactoryNum+"%\'";
		}
		else{
			if(!"".equals(qfactoryNum))
				conditon =conditon+"  and t.factoryNum like \'%"+qfactoryNum+"%\'";
		}
		
		
		if("".equals(conditon)){
			if(qazCompanyId >0 )
				conditon ="  where t.azCompanyId = "+qazCompanyId;
		}
		else{
			if(qazCompanyId >0 )
				conditon =conditon+"  and t.azCompanyId = "+qazCompanyId;
		}
		
		
		if("".equals(conditon)){
			if(!"".equals(quseDate))
				conditon ="  where t.useDate >= \'"+quseDate+"\'";
		}
		else{
			if(!"".equals(quseDate))
				conditon =conditon+"  and t.useDate >= \'"+quseDate+"\'";
		}
		
		
		if("".equals(conditon)){
			if(!"".equals(quseDate2))
				conditon ="  where t.useDate <= \'"+quseDate2+"\'";
		}
		else{
			if(!"".equals(quseDate2))
				conditon =conditon+"  and t.useDate <= \'"+quseDate2+"\'";
		}
		
		
		if("".equals(conditon)){
			if(qwgCompanyId >0 )
				conditon ="  where t.wgCompanyId = "+qwgCompanyId;
		}
		else{
			if(qwgCompanyId >0 )
				conditon =conditon+"  and t.wgCompanyId = "+qwgCompanyId;
		}
		
		
		if("".equals(conditon)){
			if(!"".equals(quseNumber))
				conditon ="  where t.useNumber like \'%"+quseNumber+"%\'";
		}
		else{
			if(!"".equals(quseNumber))
				conditon =conditon+"  and t.useNumber like \'%"+quseNumber+"%\'";
		}
		
		
		
		 long total=0;
		 List<ElevaltorInfoVO> items = null;
		 
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 try {
		 if(role ==1 || role ==2){
		 if("1".equals(cityName)){
		sql ="select t.jyjyFlag,t.shibieCode,t.id,t.registNumber,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,isnull(t.deviceId2,'') as deviceId2,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber "+conditon;	 
		 }
		 else{
		 sql ="select t.jyjyFlag,t.id,t.registNumber,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,isnull(t.deviceId2,'') as deviceId2,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber "+conditon;
		 }
		 countSql ="select count(*) from TwoCodeElevatorInfo t "+ conditon;
		 total = ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, countSql, null); 
		 items=ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null, "t.updateTime desc", rows, (page-1)*rows);   
		 }
		
		 if(role ==22 || role ==23){
		  if("1".equals(cityName)){
		 sql ="select t.jyjyFlag,t.shibieCode,t.id,t.registNumber,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,isnull(t.deviceId2,'') as deviceId2,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber "+conditon;  
		  }
		  else {
		   sql ="select t.jyjyFlag,t.id,t.registNumber,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,isnull(t.deviceId2,'') as deviceId2,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber "+conditon;
		  }
		 countSql ="select count(*) from TwoCodeElevatorInfo t "+ conditon;
		 total = ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, countSql, null); 
		 items=ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null, "t.updateTime desc", rows, (page-1)*rows);   
				 	 
		 }
		 
		 if(role ==10 || role==11){
			 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         if("".equals(conditon)){
	          countSql ="select count(*) from TwoCodeElevatorInfo  t  where t.zjCompanyId=? and t.dailingFlag = 0";
	          if("1".equals(cityName)){
	          sql ="select t.jyjyFlag,t.shibieCode,t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,isnull(t.deviceId2,'') as deviceId2  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where t.zjCompanyId=? and t.dailingFlag = 0 ";  
	          }
	          else{
	          sql ="select t.jyjyFlag,t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,isnull(t.deviceId2,'') as deviceId2  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where t.zjCompanyId=? and t.dailingFlag = 0 ";
	          }
	          }
	         else{
	          countSql ="select count(*) from TwoCodeElevatorInfo  t " + conditon +" and  t.zjCompanyId=? and t.dailingFlag = 0";
	          if("1".equals(cityName)){
	          sql ="select t.jyjyFlag,t.shibieCode,t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,isnull(t.deviceId2,'') as deviceId2  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber "+ conditon +" and  t.zjCompanyId=? and t.dailingFlag = 0";        	  
	          }
	          else{
	          sql ="select t.jyjyFlag,t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,isnull(t.deviceId2,'') as deviceId2  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber "+ conditon +" and  t.zjCompanyId=? and t.dailingFlag = 0";    
	          }
	          }
	    	 total  =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, countSql, new Object[]{zjcompanyId});
	    	 System.out.println("�����ʼ�ֱ�ǩ������"+total);
	    	 items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, new Object[]{zjcompanyId}, "t.updateTime desc", rows, (page-1)*rows);	
		 }
		 }
		 
		 if(role ==20 || role==21){
			 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         if("".equals(conditon)){
	          countSql ="select count(*) from TwoCodeElevatorInfo  t  where t.townshipStreets=? and t.dailingFlag = 0";
	          if("1".equals(cityName)){
	          sql ="select t.jyjyFlag,t.shibieCode,t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,isnull(t.deviceId2,'') as deviceId2  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where t.townshipStreets=? and t.dailingFlag = 0 ";       
	          }
	          else{
	          sql ="select t.jyjyFlag,t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,isnull(t.deviceId2,'') as deviceId2  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where t.townshipStreets=? and t.dailingFlag = 0 ";
	          }
	          }
	         else{
	          countSql ="select count(*) from TwoCodeElevatorInfo  t " + conditon +" and  t.townshipStreets=? and t.dailingFlag = 0";
	          if("1".equals(cityName)){
	          sql ="select t.jyjyFlag,t.shibieCode,t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,isnull(t.deviceId2,'') as deviceId2  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber "+ conditon +" and  t.townshipStreets=? and t.dailingFlag = 0";          
	          }
	          else{
	          sql ="select t.jyjyFlag,t.id,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo,isnull(t.deviceId2,'') as deviceId2  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber "+ conditon +" and  t.townshipStreets=? and t.dailingFlag = 0";    
	          }
	          }
	    	 total  =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, countSql, new Object[]{zjcompanyId});
	    	 items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, new Object[]{zjcompanyId}, "t.updateTime desc", rows, (page-1)*rows);	
		 }
		 }
		} catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}	
		 
		 
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		 
		
		 
	 }
	 
	 public String gjquerysave(GjQueryInfo qjqueryinfo){
		 String result = "failure";
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String userName =userinfo.getLoginName();
		 qjqueryinfo.setLoginName(userName);
		 GjQueryInfoVO gjQueryInfoVO = null;
		 
		 try {
		 gjQueryInfoVO = GjQueryInfoVO.findFirst(GjQueryInfoVO.class, "loginName = ?", new Object[] { userName });
		 if(gjQueryInfoVO == null){
			
			ActiveRecordBase.beginTransaction();
			boolean flag;
			flag = qjqueryinfo.save();
			 
			if (flag){
				ActiveRecordBase.commit();
				result ="success";
			}
			else { 
				ActiveRecordBase.rollback();
				result ="success";
			}
		 }
		 else{
			//����
			 int num =0;
			 num = GjQueryInfo.updateAll(GjQueryInfo.class, "qregistNumber=?,qregistCode=?,qarea=?,qbuildingName=?,qywCompanyId=?,qmContractVdate=?,qmContractVdate2=?,qinspectDate=?,qinspectDate2=?,qeleType=?,qregistCode2=?,qzzCompanyId=?,qfactoryNum=?,qazCompanyId=?,quseDate=?,quseDate2=?,qwgCompanyId=?,quseNumber=?", new Object[]{qjqueryinfo.getQregistNumber(),qjqueryinfo.getQregistCode(),qjqueryinfo.getQarea(),qjqueryinfo.getQbuildingName(),qjqueryinfo.getQywCompanyId(),qjqueryinfo.getQmContractVdate(),qjqueryinfo.getQmContractVdate2(),qjqueryinfo.getQinspectDate(),qjqueryinfo.getQinspectDate2(),qjqueryinfo.getQeleType(),qjqueryinfo.getQregistCode2(),qjqueryinfo.getQzzCompanyId(),qjqueryinfo.getQfactoryNum(),qjqueryinfo.getQazCompanyId(),qjqueryinfo.getQuseDate(),qjqueryinfo.getQuseDate2(),qjqueryinfo.getQwgCompanyId(),qjqueryinfo.getQuseNumber()}, "loginName=?", new Object[] { userName});			
			 if(num > 0)
				 System.out.println("�߼���ѯ���ø��³ɹ�");
			 
		 }
		 } catch (TransactionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}
			
			return  result;
	   }
	 
	 public View gjqueryedit(){ 
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String userName =userinfo.getLoginName();
		 String sql ="select isnull(t.qregistNumber,'') as qregistNumber,isnull(t.qregistCode,'') as qregistCode,isnull(t.qarea ,'') as qarea,isnull(t.qbuildingName,'')as qbuildingName,t.qywCompanyId,isnull(t.qmContractVdate,'') as qmContractVdate,isnull(t.qmContractVdate2,'') as qmContractVdate2,isnull(t.qinspectDate,'') as qinspectDate,isnull(t.qinspectDate2,'') as qinspectDate2,isnull(t.qeleType,'') as qeleType,isnull(t.qregistCode2,'') as qregistCode2,t.qzzCompanyId,isnull(t.qfactoryNum,'') as qfactoryNum,t.qazCompanyId,isnull(t.quseDate,'') as quseDate,isnull(t.quseDate2,'') as quseDate2,t.qwgCompanyId,isnull(t.quseNumber,'') as quseNumber  from TwoCodeGjqueryInfo  t  where t.loginName = ?";
		 GjQueryInfoVO gjQueryInfoVO;
		 Map<String, Object> result = new HashMap<String, Object>();
		try {
			gjQueryInfoVO = GjQueryInfoVO.findFirstBySql(GjQueryInfoVO.class, sql, new Object[] { userName });
		
		
		 if(gjQueryInfoVO !=null){
		 result.put("qregistNumber", gjQueryInfoVO.getQregistNumber());
		 result.put("qregistCode", gjQueryInfoVO.getQregistCode());
		 result.put("qarea", gjQueryInfoVO.getQarea());
		 result.put("qbuildingName", gjQueryInfoVO.getQbuildingName());
		 result.put("qywCompanyId", gjQueryInfoVO.getQywCompanyId());
		 result.put("qmContractVdate", gjQueryInfoVO.getQmContractVdate());
		 result.put("qmContractVdate2", gjQueryInfoVO.getQmContractVdate2());
	//	 result.put("qinspectDate", gjQueryInfoVO.getQinspectDate());
	//	 result.put("qinspectDate2", gjQueryInfoVO.getQinspectDate2());
		 result.put("qeleType", gjQueryInfoVO.getQeleType());
		 result.put("qregistCode2", gjQueryInfoVO.getQregistCode2());
		 result.put("qzzCompanyId", gjQueryInfoVO.getQzzCompanyId());
		 result.put("qfactoryNum", gjQueryInfoVO.getQfactoryNum());
		 result.put("qazCompanyId", gjQueryInfoVO.getQazCompanyId());
		 result.put("quseDate", gjQueryInfoVO.getQuseDate());
		 result.put("quseDate2", gjQueryInfoVO.getQuseDate2());
		 result.put("qwgCompanyId", gjQueryInfoVO.getQwgCompanyId());
		 result.put("quseNumber", gjQueryInfoVO.getQuseNumber());
		
	 }
		 } catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 return new JsonView(result);
   } 
	 
	 public View lyztstatisticslist(int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<DdElevaltorInfoVO> items = new ArrayList<DdElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 long total=0;
	//	 String sql="select t.id,t.companyName as ywCompanyName,t.issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = t.id ) as tdetotal from  TwoCodeCompanyInfo t  where  t.type ='����'";
		 String sql="select t.id,t.companyName as ywCompanyName,(select sum(rcount) from TwoCodePasteReleaseTab where companyId =t.id) as issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = t.id ) as tdetotal from  TwoCodeCompanyInfo t  where  t.type ='����'";
		 
		 try {
			total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class,"select count(*) from TwoCodeCompanyInfo t  where  type ='����'",null);
			items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, null, "t.tdetotal desc,t.id desc", rows, (page-1)*rows);
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	
	 }
	 
	 public View lyztstatisticsquery(DdElevaltorInfoVO info,int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 String areaName="";
		 
		
		 areaName =info.getArea();
		 
		 String sql = "";
		 String conditionsSql="";
		 
		 String conditions2="";
		 String  conditions3="";
		 
		 List<DdElevaltorInfoVO> items = new ArrayList<DdElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
	 
		 long total=0;
		
		 
		 if(!"".equals(areaName)){
			 if(!"".equals(conditions2)){
				 conditions2 =conditions2+" and td.area like '%"+areaName+"%'";	
				} 
				else{
					conditions2 =" td.area like '%"+areaName+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(areaName)){
			 if(!"".equals(conditions3)){
				 conditions3 =conditions3+" and td.area like '%"+areaName+"%'";	
				} 
				else{
					conditions3 =" td.area like '%"+areaName+"%'";	
				}
			 
		 }
		 
		
			 if(!"".equals(conditions2)){
				 if("".equals(conditions3))
			//	     sql="select t.id,t.companyName as ywCompanyName,t.issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = t.id ) as tdetotal  from  TwoCodeCompanyInfo t  where  t.type ='����' ";
					 sql="select t.id,t.companyName as ywCompanyName,(select sum(rcount) from TwoCodePasteReleaseTab where companyId =t.id) as issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = t.id ) as tdetotal  from  TwoCodeCompanyInfo t  where  t.type ='����' ";
					 
				else
			//		 sql="select t.id,t.companyName as ywCompanyName,t.issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and "+ conditions3+" and  te.companyid = t.id ) as tdetotal  from  TwoCodeCompanyInfo t  where  t.type ='����' ";
					 sql="select t.id,t.companyName as ywCompanyName,(select sum(rcount) from TwoCodePasteReleaseTab where companyId =t.id) as issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and "+ conditions3+" and  te.companyid = t.id ) as tdetotal  from  TwoCodeCompanyInfo t  where  t.type ='����' ";
						 
			 }else{
				 if("".equals(conditions3))
			//	   sql="select t.id,t.companyName as ywCompanyName,t.issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = t.id ) as tdetotal  from  TwoCodeCompanyInfo t  where  t.type ='����' ";
				   sql="select t.id,t.companyName as ywCompanyName,(select sum(rcount) from TwoCodePasteReleaseTab where companyId =t.id) as issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = t.id ) as tdetotal  from  TwoCodeCompanyInfo t  where  t.type ='����' ";	
				else
				//	 sql="select t.id,t.companyName as ywCompanyName,t.issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and "+ conditions3+" and  te.companyid = t.id ) as tdetotal from  TwoCodeCompanyInfo t  where  t.type ='����' ";
					sql="select t.id,t.companyName as ywCompanyName,(select sum(rcount) from TwoCodePasteReleaseTab where companyId =t.id) as issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and "+ conditions3+" and  te.companyid = t.id ) as tdetotal from  TwoCodeCompanyInfo t  where  t.type ='����' ";
					
			 }
			 conditionsSql ="select count(*) from TwoCodeCompanyInfo t  where  type ='����' ";
				
		
		 
		 try {
			     total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class,conditionsSql,null);
				items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, null, "t.tdetotal desc,t.id desc", rows, (page-1)*rows);
			} catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}
			 result.put("total", total);
			 result.put("rows", items);  
			 return new JsonView(result);	
		 
	 }
	 
	 public View ztstatisticslist(int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<DdElevaltorInfoVO> items = new ArrayList<DdElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 long total=0;
	//	 String sql="select t.id,t.companyName as ywCompanyName,t.issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = t.id ) as tdetotal,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and  td.subTime2 >=dateadd(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),0)) and  td.subTime2 < DATEADD(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),7))  and  te.companyid = t.id ) as pretdetotal from  TwoCodeCompanyInfo t  where t.type ='ά��' or t.type ='����'";
		 String sql="select t.id,t.companyName as ywCompanyName,(select sum(rcount) from TwoCodePasteReleaseTab where companyId =t.id) as issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = t.id ) as tdetotal,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and  td.subTime2 >=dateadd(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),0)) and  td.subTime2 < DATEADD(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),7))  and  te.companyid = t.id ) as pretdetotal from  TwoCodeCompanyInfo t  where t.type ='ά��' or t.type ='����'";
			
		 try {
			total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class,"select count(*) from TwoCodeCompanyInfo t  where type ='ά��' or type ='����'",null);
			items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, null, "tdetotal desc,t.id desc", rows, (page-1)*rows);
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	
	 }
	 
	 public View ztstatisticsquery(DdElevaltorInfoVO info,int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 int ywCompanyId =0;
		 String qstartTime ="";
		 String qendTime="";
		 String areaName="";
		 
		 ywCompanyId =info.getCompanyId();
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 areaName =info.getArea();
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 String conditions2="";
		 String  conditions3="";
		 
		 List<DdElevaltorInfoVO> items = new ArrayList<DdElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
	 
		 long total=0;
		 if(ywCompanyId > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.id = "+ywCompanyId;	
				} 
				else{
					conditions =" t.id = "+ywCompanyId;	
				}
		 }
		 
		 
		 if("".equals(qstartTime) && "".equals(qendTime)){
			 conditions2 ="td.subTime2 >=dateadd(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),0)) and  td.subTime2 < DATEADD(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),7)) ";
		 }
		 else{
		 if(!"".equals(qstartTime)){
			 if(!"".equals(conditions2)){
				 conditions2 =conditions2+" and td.subTime2 >= '"+qstartTime+"'";	
				} 
				else{
					conditions2 =" td.subTime2 >= '"+qstartTime+"'";	
				}
			
			
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions2)){
				 conditions2 =conditions2+" and td.subTime2 < '"+qendTime+"'";	
				} 
				else{
					conditions2 =" td.subTime2 < '"+qendTime+"'";	
				}
			
			
		 }
		 }
		 
		 if(!"".equals(areaName)){
			 if(!"".equals(conditions2)){
				 conditions2 =conditions2+" and td.area like '%"+areaName+"%'";	
				} 
				else{
					conditions2 =" td.area like '%"+areaName+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(areaName)){
			 if(!"".equals(conditions3)){
				 conditions3 =conditions3+" and td.area like '%"+areaName+"%'";	
				} 
				else{
					conditions3 =" td.area like '%"+areaName+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(conditions)){
			 if(!"".equals(conditions2)){
				 if("".equals(conditions3))
				   //  sql="select t.id,t.companyName as ywCompanyName,t.issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = t.id ) as tdetotal,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and "+ conditions2+"  and  te.companyid = t.id ) as pretdetotal from  TwoCodeCompanyInfo t  where (t.type ='ά��' or t.type ='����') and "+conditions;
					 sql="select t.id,t.companyName as ywCompanyName,(select sum(rcount) from TwoCodePasteReleaseTab where companyId =t.id) as issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = t.id ) as tdetotal,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and "+ conditions2+"  and  te.companyid = t.id ) as pretdetotal from  TwoCodeCompanyInfo t  where (t.type ='ά��' or t.type ='����') and "+conditions;
				 
				else
				//	 sql="select t.id,t.companyName as ywCompanyName,t.issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and "+ conditions3+"  and  te.companyid = t.id ) as tdetotal,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and "+ conditions2+"  and  te.companyid = t.id ) as pretdetotal from  TwoCodeCompanyInfo t  where (t.type ='ά��' or t.type ='����') and "+conditions;
					 sql="select t.id,t.companyName as ywCompanyName,(select sum(rcount) from TwoCodePasteReleaseTab where companyId =t.id) as issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and "+ conditions3+"  and  te.companyid = t.id ) as tdetotal,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and "+ conditions2+"  and  te.companyid = t.id ) as pretdetotal from  TwoCodeCompanyInfo t  where (t.type ='ά��' or t.type ='����') and "+conditions;
						 
			 }else{
				 if("".equals(conditions3))
			//	    sql="select t.id,t.companyName as ywCompanyName,t.issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = t.id ) as tdetotal,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and  td.subTime2 >=dateadd(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),0)) and  td.subTime2 < DATEADD(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),7))  and  te.companyid = t.id ) as pretdetotal from  TwoCodeCompanyInfo t  where (t.type ='ά��' or t.type ='����') and "+conditions;
					sql="select t.id,t.companyName as ywCompanyName,(select sum(rcount) from TwoCodePasteReleaseTab where companyId =t.id) as issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = t.id ) as tdetotal,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and  td.subTime2 >=dateadd(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),0)) and  td.subTime2 < DATEADD(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),7))  and  te.companyid = t.id ) as pretdetotal from  TwoCodeCompanyInfo t  where (t.type ='ά��' or t.type ='����') and "+conditions;		
				else
			//		sql="select t.id,t.companyName as ywCompanyName,t.issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and "+ conditions3+" and  te.companyid = t.id ) as tdetotal,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and  td.subTime2 >=dateadd(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),0)) and  td.subTime2 < DATEADD(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),7))  and  te.companyid = t.id ) as pretdetotal from  TwoCodeCompanyInfo t  where (t.type ='ά��' or t.type ='����') and "+conditions;
					sql="select t.id,t.companyName as ywCompanyName,(select sum(rcount) from TwoCodePasteReleaseTab where companyId =t.id) as issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and "+ conditions3+" and  te.companyid = t.id ) as tdetotal,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and  td.subTime2 >=dateadd(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),0)) and  td.subTime2 < DATEADD(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),7))  and  te.companyid = t.id ) as pretdetotal from  TwoCodeCompanyInfo t  where (t.type ='ά��' or t.type ='����') and "+conditions;
					
			 }
			 conditionsSql ="select count(*) from TwoCodeCompanyInfo t  where (type ='ά��' or type ='����') and "+conditions;
		 }else{
			 if(!"".equals(conditions2)){
				 if("".equals(conditions3))
			//	     sql="select t.id,t.companyName as ywCompanyName,t.issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = t.id ) as tdetotal,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and "+ conditions2+"  and  te.companyid = t.id ) as pretdetotal from  TwoCodeCompanyInfo t  where t.type ='ά��' or t.type ='����' ";
					 sql="select t.id,t.companyName as ywCompanyName,(select sum(rcount) from TwoCodePasteReleaseTab where companyId =t.id) as issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = t.id ) as tdetotal,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and "+ conditions2+"  and  te.companyid = t.id ) as pretdetotal from  TwoCodeCompanyInfo t  where t.type ='ά��' or t.type ='����' ";
				 
				else
				//	 sql="select t.id,t.companyName as ywCompanyName,t.issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and "+ conditions3+" and  te.companyid = t.id ) as tdetotal,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and "+ conditions2+"  and  te.companyid = t.id ) as pretdetotal from  TwoCodeCompanyInfo t  where t.type ='ά��' or t.type ='����' ";
					sql="select t.id,t.companyName as ywCompanyName,(select sum(rcount) from TwoCodePasteReleaseTab where companyId =t.id) as issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and "+ conditions3+" and  te.companyid = t.id ) as tdetotal,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and "+ conditions2+"  and  te.companyid = t.id ) as pretdetotal from  TwoCodeCompanyInfo t  where t.type ='ά��' or t.type ='����' ";
						 
			 }else{
				 if("".equals(conditions3))
				//   sql="select t.id,t.companyName as ywCompanyName,t.issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = t.id ) as tdetotal,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and  td.subTime2 >=dateadd(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),0)) and  td.subTime2 < DATEADD(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),7))  and  te.companyid = t.id ) as pretdetotal from  TwoCodeCompanyInfo t  where t.type ='ά��' or t.type ='����' ";
					 sql="select t.id,t.companyName as ywCompanyName,(select sum(rcount) from TwoCodePasteReleaseTab where companyId =t.id) as issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = t.id ) as tdetotal,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and  td.subTime2 >=dateadd(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),0)) and  td.subTime2 < DATEADD(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),7))  and  te.companyid = t.id ) as pretdetotal from  TwoCodeCompanyInfo t  where t.type ='ά��' or t.type ='����' ";
					
				else
				//	 sql="select t.id,t.companyName as ywCompanyName,t.issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and "+ conditions3+" and  te.companyid = t.id ) as tdetotal,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and  td.subTime2 >=dateadd(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),0)) and  td.subTime2 < DATEADD(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),7))  and  te.companyid = t.id ) as pretdetotal from  TwoCodeCompanyInfo t  where t.type ='ά��' or t.type ='����' ";
					 sql="select t.id,t.companyName as ywCompanyName,(select sum(rcount) from TwoCodePasteReleaseTab where companyId =t.id) as issueetotal,t.type,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and "+ conditions3+" and  te.companyid = t.id ) as tdetotal,(select count(td.registNumber) from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and  td.subTime2 >=dateadd(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),0)) and  td.subTime2 < DATEADD(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),7))  and  te.companyid = t.id ) as pretdetotal from  TwoCodeCompanyInfo t  where t.type ='ά��' or t.type ='����' ";
			        
			 }
			 conditionsSql ="select count(*) from TwoCodeCompanyInfo t  where type ='ά��' or type ='����' ";
				
		 }
		 
		 try {
			     total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class,conditionsSql,null);
				items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, null, "t.tdetotal desc,t.id desc", rows, (page-1)*rows);
			} catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}
			 result.put("total", total);
			 result.put("rows", items);  
			 return new JsonView(result);	
		 
	 }
	 
	 public View lytdetotallistByOrder(DdElevaltorInfoVO info, int page, int rows,String sort,String order){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 int ywCompanyId =0;
		 String area ="";
		 area = info.getArea();
		 
		 // ��ȡ��ѯ����  
	        String SortName = request.getParameter("sort");  
	         
	        if(SortName == null){  
	            SortName ="td.subTime2";  
	        }
	       
	        String SortValue =request.getParameter("order");  
	        if(SortValue == null){  
	            SortValue = "asc";        
	        }  
		 
		 ywCompanyId = info.getYwCompanyId();
		 List<DdElevaltorInfoVO> items = new ArrayList<DdElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 String sql ="";
		 String countSql ="";
		 Object[] param=null;
		 
		 long total=0;
		 if("".equals(area)){
			countSql ="select count(*) from TwoCodeDdElevatorInfo td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = ?";
		    sql="select td.registNumber,td.registCode,td.address,td.buildingName,td.subTime2,td.recordSate,td.checkReportNum from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and    te.companyid = ? ";
		    param =new Object[]{ywCompanyId}; 
		 }
		 else{
			 countSql ="select count(*) from TwoCodeDdElevatorInfo td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = ? and td.area = '"+area+"'";
			 sql="select td.registNumber,td.registCode,td.address,td.buildingName,td.subTime2,td.recordSate,td.checkReportNum from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and    te.companyid = ?  and td.area = '"+area+"'";
			 param=new Object[]{ywCompanyId}; 
		 }
		 try {
			
			total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class,countSql,param);
			items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, new Object[]{ywCompanyId}, SortName+" "+SortValue, rows, (page-1)*rows);
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	
	 }
	 
	 
	 public View lytdetotallist(DdElevaltorInfoVO info, int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 int ywCompanyId =0;
		 String area ="";
		 area = info.getArea();
		 
		 ywCompanyId = info.getYwCompanyId();
		 List<DdElevaltorInfoVO> items = new ArrayList<DdElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 String sql ="";
		 
		 long total=0;
		 if("".equals(area))
		    sql="select td.registNumber,td.registCode,td.address,td.buildingName,td.subTime2,td.recordSate,td.checkReportNum from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and    te.companyid = ? ";
		 else
			sql="select td.registNumber,td.registCode,td.address,td.buildingName,td.subTime2,td.recordSate,td.checkReportNum from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and    te.companyid = ?  and td.area = '"+area+"'";
		 try {
			total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class,"select count(*) from TwoCodeDdElevatorInfo td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = ? and td.area = '"+area+"'",new Object[]{ywCompanyId});
			items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, new Object[]{ywCompanyId}, "td.subTime2 desc", rows, (page-1)*rows);
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	
	 }
	 
	 
	 public View tdetotallist(DdElevaltorInfoVO info, int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 int ywCompanyId =0;
		 String area ="";
		 area = info.getArea();
		 
		 ywCompanyId = info.getYwCompanyId();
		 List<DdElevaltorInfoVO> items = new ArrayList<DdElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 String sql ="";
		
		 
		 long total=0;
		 try {
		 if("".equals(area)){
		    sql="select td.registNumber,isnull(td.registCode,'') as registCode,td.address,td.buildingName,td.subTime2,td.recordSate from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and    te.companyid = ? ";
		    total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class,"select count(*) from TwoCodeDdElevatorInfo td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = ? ",new Object[]{ywCompanyId});
		 }
		   else
		 {
			sql="select td.registNumber,isnull(td.registCode,'') as registCode,td.address,td.buildingName,td.subTime2,td.recordSate from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and    te.companyid = ?  and td.area = '"+area+"'";
			total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class,"select count(*) from TwoCodeDdElevatorInfo td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = ? and td.area = '"+area+"'",new Object[]{ywCompanyId});
		 }
		
	//		total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class,"select count(*) from TwoCodeDdElevatorInfo td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  te.companyid = ? and td.area = '"+area+"'",new Object[]{ywCompanyId});
			items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, new Object[]{ywCompanyId}, "td.subTime2 desc", rows, (page-1)*rows);
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	
	 }
	 
	 
	 public View pretdetotallist(DdElevaltorInfoVO info, int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 int ywCompanyId =0;
		 String qstartTime ="";
		 String qendTime="";
		 
		 String conditions2="";
		 
		 
		 ywCompanyId = info.getYwCompanyId();
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 
		 if(!"".equals(qstartTime)){
			 if(!"".equals(conditions2)){
				 conditions2 =conditions2+" and td.subTime2 >= '"+qstartTime+"'";	
				} 
				else{
					conditions2 =" td.subTime2 >= '"+qstartTime+"'";	
				}
			
			
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions2)){
				 conditions2 =conditions2+" and td.subTime2 < '"+qendTime+"'";	
				} 
				else{
					conditions2 =" td.subTime2 < '"+qendTime+"'";	
				}
			
			
		 }
		 List<DdElevaltorInfoVO> items = new ArrayList<DdElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 long total=0;
		 try {
		 if(!"".equals(conditions2)){
			 String sql="select td.registNumber,isnull(td.registCode,'') as registCode,td.address,td.buildingName,td.subTime2,td.recordSate from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1 and "+ conditions2+"  and    te.companyid = ? ";
			 String countSql ="select count(*) from TwoCodeDdElevatorInfo td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and "+ conditions2+" and  te.companyid = ?";
			 total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class,countSql,new Object[]{ywCompanyId});
			 items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, new Object[]{ywCompanyId}, "td.subTime2 desc", rows, (page-1)*rows);
				 
		 }
		 else{
		 String sql="select td.registNumber,isnull(td.registCode,'') as registCode,td.address,td.buildingName,td.subTime2,td.recordSate from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and  td.subTime2 >=dateadd(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),0)) and  td.subTime2 < DATEADD(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),7))   and    te.companyid = ? ";
		 String countSql ="select count(*) from TwoCodeDdElevatorInfo td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and  td.subTime2 >=dateadd(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),0)) and  td.subTime2 < DATEADD(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),7))   and  te.companyid = ?";
		 total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class,countSql,new Object[]{ywCompanyId});
		 items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, new Object[]{ywCompanyId}, "td.subTime2 desc", rows, (page-1)*rows);
		 }
		 } catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	
	 }
	 
	 public View ztworkerlist(DdElevaltorInfoVO info,int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 int ywCompanyId =0;
		 
		 ywCompanyId = info.getYwCompanyId();
		 List<DdElevaltorInfoVO> items = new ArrayList<DdElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 long total=0;
		 String sql="select  td1.userid,tc.loginName,tc.userName,(select  count(*)  from TwoCodeDdElevatorInfo where  subPersonID = td1.userid  and recordSate >1 ) as utdetotal,(select  count(*)  from TwoCodeDdElevatorInfo where  subPersonID = td1.userid and  recordSate >1  and  subTime2 >=dateadd(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),0))  and  subTime2 < DATEADD(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),7)) )  as upretdetotal from (select te.userid  from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where  td.recordSate >1  and te.companyid = ?  group by te.userid) td1 left join tcuserinfo tc on td1.userid =tc.id";
		 try {
			total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class,"select count(td1.userid) from (select te.userid  from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where  td.recordSate >1  and te.companyid = ?  group by te.userid) td1 left join tcuserinfo tc on td1.userid =tc.id",new Object[]{ywCompanyId});
			items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, new Object[]{ywCompanyId}, "td1.userid desc", rows, (page-1)*rows);
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	
	 }
	 
	 public View ztworkerlistquery(DdElevaltorInfoVO info,int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 int ywCompanyId =0;
		 String qstartTime ="";
		 String qendTime="";
		 
		 ywCompanyId =info.getCompanyId();
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 String conditions2="";
		 
		 List<DdElevaltorInfoVO> items = new ArrayList<DdElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
	 
		 long total=0;
		 
		 
		 if(!"".equals(qstartTime)){
			 if(!"".equals(conditions2)){
				 conditions2 =conditions2+" and subTime2 >= '"+qstartTime+"'";	
				} 
				else{
					conditions2 =" subTime2 >= '"+qstartTime+"'";	
				}
			
			
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions2)){
				 conditions2 =conditions2+" and subTime2 < '"+qendTime+"'";	
				} 
				else{
					conditions2 =" subTime2 < '"+qendTime+"'";	
				}
			
			
		 }
		 
			
		 try {
		 
			 if(!"".equals(conditions2)){
				 sql="select  td1.userid,tc.loginName,tc.userName,(select  count(*)  from TwoCodeDdElevatorInfo where  subPersonID = td1.userid  and recordSate >1 ) as utdetotal,(select  count(*)  from TwoCodeDdElevatorInfo where  subPersonID = td1.userid and  recordSate >1  and " +conditions2 +" )  as upretdetotal from (select te.userid  from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where  td.recordSate >1  and te.companyid = ?  group by te.userid) td1 left join tcuserinfo tc on td1.userid =tc.id";
				 total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class,"select count(td1.userid) from (select te.userid  from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where  td.recordSate >1  and te.companyid = ?  group by te.userid) td1 left join tcuserinfo tc on td1.userid =tc.id",new Object[]{ywCompanyId});
			     items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, new Object[]{ywCompanyId}, "td1.userid desc", rows, (page-1)*rows);
						
			 }else{
				 sql="select  td1.userid,tc.loginName,tc.userName,(select  count(*)  from TwoCodeDdElevatorInfo where  subPersonID = td1.userid  and recordSate >1 ) as utdetotal,(select  count(*)  from TwoCodeDdElevatorInfo where  subPersonID = td1.userid and  recordSate >1  and  subTime2 >=dateadd(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),0))  and  subTime2 < DATEADD(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),7)) )  as upretdetotal from (select te.userid  from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where  td.recordSate >1  and te.companyid = ?  group by te.userid) td1 left join tcuserinfo tc on td1.userid =tc.id";
				 total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class,"select count(td1.userid) from (select te.userid  from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where  td.recordSate >1  and te.companyid = ?  group by te.userid) td1 left join tcuserinfo tc on td1.userid =tc.id",new Object[]{ywCompanyId});
			     items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, new Object[]{ywCompanyId}, "td1.userid desc", rows, (page-1)*rows);
						 
			 }
		 } catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		 
		 
			 result.put("total", total);
			 result.put("rows", items);  
			 return new JsonView(result);	
		 
	 }
	 
	 public View utdetotallist(DdElevaltorInfoVO info, int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 int puserid =0;
		 
		 puserid = info.getUserid();  //ճ����Աid
		 List<DdElevaltorInfoVO> items = new ArrayList<DdElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 long total=0;
		 String sql="select td.registNumber,isnull(td.registCode,'') as registCode, td.address,td.buildingName,td.subTime2,td.recordSate from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and    td.subPersonID = ? ";
		 try {
			total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class,"select count(*) from TwoCodeDdElevatorInfo td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1   and  td.subPersonID = ?",new Object[]{puserid});
			items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, new Object[]{puserid}, "td.subTime2 desc", rows, (page-1)*rows);
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	
	 }
	 
	 public View upretdetotallist(DdElevaltorInfoVO info, int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 int puserid =0;
		 String qstartTime ="";
		 String qendTime="";
		 
		 String conditions2="";
		 
		 
		 puserid = info.getUserid();  //ճ����Աid
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 
		 if(!"".equals(qstartTime)){
			 if(!"".equals(conditions2)){
				 conditions2 =conditions2+" and td.subTime2 >= '"+qstartTime+"'";	
				} 
				else{
					conditions2 =" td.subTime2 >= '"+qstartTime+"'";	
				}
			
			
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions2)){
				 conditions2 =conditions2+" and td.subTime2 < '"+qendTime+"'";	
				} 
				else{
					conditions2 =" td.subTime2 < '"+qendTime+"'";	
				}
			
			
		 }
		 List<DdElevaltorInfoVO> items = new ArrayList<DdElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 long total=0;
		 try {
		 if(!"".equals(conditions2)){
			 String sql="select td.registNumber,isnull(td.registCode,'') as registCode,td.address,td.buildingName,td.subTime2,td.recordSate from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1 and "+ conditions2+"    and    td.subPersonID = ? ";
			 String countSql ="select count(*) from TwoCodeDdElevatorInfo td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and "+ conditions2+"    and  td.subPersonID = ?";
			 total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class,countSql,new Object[]{puserid});
			 items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, new Object[]{puserid}, "td.subTime2 desc", rows, (page-1)*rows);
				 
		 }
		 else{
		 String sql="select td.registNumber,isnull(td.registCode,'') as registCode,td.address,td.buildingName,td.subTime2,td.recordSate from  TwoCodeDdElevatorInfo  td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and  td.subTime2 >=dateadd(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),0)) and  td.subTime2 < DATEADD(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),7))   and    td.subPersonID = ? ";
		 String countSql ="select count(*) from TwoCodeDdElevatorInfo td left join  TwoCodeUserExtInfo te on   td.subPersonID = te.userid where   td.recordSate >1  and  td.subTime2 >=dateadd(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),0)) and  td.subTime2 < DATEADD(week,-1,DATEADD(week,DATEDIFF(week,0,getdate()),7))   and  td.subPersonID = ?";
		 total  =DdElevaltorInfoVO.countBySql(DdElevaltorInfoVO.class,countSql,new Object[]{puserid});
		 items = DdElevaltorInfoVO.findBySql(DdElevaltorInfoVO.class, sql, new Object[]{puserid}, "td.subTime2 desc", rows, (page-1)*rows);
		 }
		 } catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	
	 }
	 
	 
	 public String selevatorinfoadd(SelevaltorInfo selevaltorInfo){
		 String result = "failure";
		 
		//Ҫ������±�ǩ��Ϣ����updateTime�ֶΣ�С���Ǳ�Ҫ��
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = format1.format(new Date()); 
		selevaltorInfo.setUpdateTime(s);
		 try {
			SelevaltorInfoVO  selevaltorInfoExit = null;
			selevaltorInfoExit = SelevaltorInfoVO.findFirst(SelevaltorInfoVO.class, "registCode = ?", new Object[] { selevaltorInfo.getRegistCode()});
		    if(selevaltorInfoExit != null){
			 result = "exist";
		     }
		else{
			    boolean flag = selevaltorInfo.save();
				if (flag){	
					result ="success";
				}
				else { 
					
					result ="success";
				}
		   }
		 } catch (ActiveRecordException e) {
				
				e.printStackTrace();
			} 
		 return result;
}
	 
	 public String sysYwgshbSetings(String companyName,String ncompanyName){
		 String result = "failure";
		
		try {
				
				ActiveRecordBase.beginTransaction();
				if(!"".equals(companyName) || !"".equals(ncompanyName)){    
					ActiveRecordBase.execute("exec proc_ChangeCompany ?,?", new Object[] { companyName,ncompanyName});
					ActiveRecordBase.commit();
					result ="success";
			}
				
			}catch (Exception e){
			    try {
				   ActiveRecordBase.rollback();
			    } catch (TransactionException e1) {
						e1.printStackTrace();
					}
						
			}
	
		 return result;
	 }
	 
	public String eletcinfochange(String registNumber,String nregistNumber){
		 String result = "failure";
		 try {
				System.out.println("registNumber--"+registNumber);  System.out.println("nregistNumber--"+nregistNumber);
				ActiveRecordBase.beginTransaction();
				if(!"".equals(registNumber) || !"".equals(nregistNumber)){    
					ActiveRecordBase.execute("exec proc_eletcinfochange ?,?", new Object[] {registNumber,nregistNumber});
					ActiveRecordBase.commit();
					result ="success";
					/*
					TsDeviceService server = new TsDeviceService();  
					TsDeivceService hello = server.getTsDeivceServiceImplPort();
					String jkresult = hello.updateQrCodeByQrCode("zyxt0703", "xr342fagf", registNumber, nregistNumber);
					System.out.println(jkresult);  */
					jkdy("updateQrCodeByQrCode",registNumber,nregistNumber);
					
			}
				
			}catch (Exception e){
			    try {
				   ActiveRecordBase.rollback();
			    } catch (TransactionException e1) {
						e1.printStackTrace();
					}
						
			}
		 return result;
	}
	 
	 public String  deletezhantieEle(int id){
		 String result = "failure";
		 this.deleteresult ="failure";
		 if(id > 0){
		 int num = 0;
		 ElevaltorInfoVO elevaltorInfoVO = null;
		 DdElevaltorInfoVO ddelevaltorInfoVO =null;
		 try {
			ddelevaltorInfoVO = DdElevaltorInfoVO.findFirst(DdElevaltorInfoVO.class, "id=?", new Object[] { id });
			if (ddelevaltorInfoVO != null) {
				this.deleteId = id;
				String registNumber = ddelevaltorInfoVO.getRegistNumber();
				if (!"".equals(registNumber)){
					this.deletezhantieregistNumber = registNumber;
					elevaltorInfoVO = ElevaltorInfoVO.findFirst(ElevaltorInfoVO.class, "registNumber=?",new Object[] { registNumber });
				    if (elevaltorInfoVO != null) {
					   num = DdElevaltorInfoVO.updateAll(DdElevaltorInfoVO.class,"recordSate =?", new Object[] { 3 }, "id =?",new Object[] { id });
					   if(num > 0){
						   result = "success";
						   this.deleteresult ="success";
					   }
				     }
				    else{
				    	ddelevaltorInfoVO.destroy();
						result = "success";
						this.deleteresult ="success";	
				    }
				}
				else {
					ddelevaltorInfoVO.destroy();
					result = "success";
					this.deleteresult ="success";
				}	
				
			}
			
			} catch (ActiveRecordException e) {
				e.printStackTrace();
				this.deleteresult ="failure";
			}
		 
		 }
		 return result;
		 
     }
	 
	 protected boolean  deletezhantieElelog(){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 Logger log = Logger.getLogger(ElevatorController.class);
		 log.info("userid:"+userid+";"+"userName:"+userName+";"+"deleteId:"+this.deleteId+";"+"registNumber:"+this.deletezhantieregistNumber+";"+"result:"+this.deleteresult);
		 return true;
	 }
	 
	 public View systongji(int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<ElevaltorInfoVO> items = new ArrayList<ElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 String cityName = GlobalFunction.cityName;
		 long total=0;
		 String sql ="";
	//	 String sql ="select area,count(registNumber) as etotal,sum(CASE WHEN dailingFlag = 1 THEN 1 ELSE 0 END) as setotal from twocodeelevatorinfo group by area";  
	//	 String sql ="select t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t  left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.area";
	//	 String sql ="select t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal,sum(CASE WHEN r.remarkLevel = 3 THEN 1 ELSE 0 END )  as tsrtotal from twocodeelevatorinfo t  left join YwManagerInfo  y on t.registNumber = y.registNumber left join twocodeRemark  r on t.registNumber = r.registNumber group by t.area ";
	//	 String sql ="select t1.area,t1.etotal,t1.setotal,t1.ncqetotal,isnull(rv.tsrtotal,0) as tsrtotal,isnull(rv.undotsrtotal,0) as  undotsrtotal from (select  t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.area ) t1 left join TwoCodeReamarkEleView  rv  on t1.area =  rv.area"; 
		 if("0".equals(cityName)){
		  sql ="select (CASE t1.area WHEN '������' THEN 1  WHEN '������' THEN 2 WHEN '��ţ��' THEN 3 WHEN '�����' THEN 4 WHEN '�ɻ���' THEN 5 WHEN '������' THEN 6 WHEN '�츮����' THEN 7 WHEN '��Ȫ����' THEN 8 WHEN '��׽���' THEN 9 WHEN '�¶���' THEN 10 WHEN '�½���' THEN 11 WHEN '������' THEN 12 WHEN '˫����' THEN 13 WHEN 'ۯ����' THEN 14 WHEN '������' THEN 15 WHEN '�ѽ���' THEN 16 WHEN '�½���' THEN 17 WHEN '��������' THEN 18 WHEN '������' THEN 19 WHEN '������' THEN 20 WHEN '������' THEN 21 WHEN '������' THEN 22 ELSE  0 END) as mrindex, t1.area,t1.etotal,t1.setotal,t1.ncqetotal,isnull(rv.tsrtotal,0) as tsrtotal,isnull(rv.undotsrtotal,0) as  undotsrtotal from (select  t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber   group by t.area ) t1 left join TwoCodeReamarkEleView  rv  on t1.area =  rv.area order by mrindex "; 
		 }
		 else{
		  sql ="select  t1.area,t1.etotal,t1.setotal,t1.ncqetotal,isnull(rv.tsrtotal,0) as tsrtotal,isnull(rv.undotsrtotal,0) as  undotsrtotal from (select  t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber    group by t.area ) t1 left join TwoCodeReamarkEleView  rv  on t1.area =  rv.area "; 
		 }
		 try { 
		 total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, "select count(1) from (select area   from twocodeelevatorinfo group by area) t", null);
		 items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null,null, rows, (page-1)*rows);
	 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	
	 }
	 
	 public View zjsystongji(int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<ElevaltorInfoVO> items = new ArrayList<ElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
	 
		 long total=0;
	//	 String sql ="select t1.townshipStreets,(select companyName from twocodecompanyinfo where id =t1.townshipStreets ) as jdbCompanyName,t1.etotal,t1.setotal,t1.ncqetotal,isnull(rv.tsrtotal,0) as tsrtotal,isnull(rv.undotsrtotal,0) as  undotsrtotal from (select  t.townshipStreets,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.townshipStreets ) t1 left join TwoCodeReamarkEleByJdbView  rv  on t1.townshipStreets =  rv.townshipStreets left join TwoCodeCompanyinfo tc on  t1.townshipStreets =tc.id where tc.parea = ? "; 
		 String sql ="select tc.id as townshipStreets,tc.companyName  as jdbCompanyName,isnull(t1.etotal,0) as etotal,isnull(t1.setotal,0) as setotal, isnull(t1.ncqetotal,0) as ncqetotal,isnull(rv.tsrtotal,0) as tsrtotal,isnull(rv.undotsrtotal,0) as  undotsrtotal from TwoCodeCompanyinfo tc left join (select  t.townshipStreets,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.townshipStreets ) t1 on  t1.townshipStreets =tc.id left join TwoCodeReamarkEleByJdbView  rv  on t1.townshipStreets =  rv.townshipStreets where tc.parea = ?  and tc.type ='�ֵ���'";
		 if(role==10 || role ==11){
		 try { 
		 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
			     
	     if(userInfoVO != null){
	    	 String companyArea = userInfoVO.getArea();
		//   total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, "select count(1) from (select townshipStreets   from twocodeelevatorinfo group by townshipStreets) t left join TwoCodeCompanyinfo tc on  t.townshipStreets =tc.id where tc.parea = ? ", new Object[]{companyArea});
	    	 total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, "select count(1) from TwoCodeCompanyinfo tc left join (select townshipStreets   from twocodeelevatorinfo group by townshipStreets) t on  t.townshipStreets =tc.id where tc.parea = ? ", new Object[]{companyArea});
		     
	    	 items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, new Object[]{companyArea},null, rows, (page-1)*rows);
	        } 
		 }catch (ActiveRecordException e) {	
			   e.printStackTrace();
		       }
		 }
		
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	
		 
	 }
	 
	 public View jdbsystongji(int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<ElevaltorInfoVO> items = new ArrayList<ElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
	 
		 long total=0;
	//	 String sql ="select t1.townshipStreets,(select companyName from twocodecompanyinfo where id =t1.townshipStreets ) as jdbCompanyName,t1.etotal,t1.setotal,t1.ncqetotal,isnull(rv.tsrtotal,0) as tsrtotal,isnull(rv.undotsrtotal,0) as  undotsrtotal from (select  t.townshipStreets,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.townshipStreets ) t1 left join TwoCodeReamarkEleByJdbView  rv  on t1.townshipStreets =  rv.townshipStreets left join TwoCodeCompanyinfo tc on  t1.townshipStreets =tc.id where tc.parea = ? "; 
		 String sql ="select tc.id as townshipStreets,tc.companyName  as jdbCompanyName,isnull(t1.etotal,0) as etotal,isnull(t1.setotal,0) as setotal, isnull(t1.ncqetotal,0) as ncqetotal,isnull(rv.tsrtotal,0) as tsrtotal,isnull(rv.undotsrtotal,0) as  undotsrtotal from TwoCodeCompanyinfo tc left join (select  t.townshipStreets,t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber where t.area = ? group by t.townshipStreets,t.area having (t.townshipStreets = ? or t.townshipStreets =?) ) t1 on  t1.townshipStreets =tc.id left join TwoCodeReamarkEleByJdbAreaView  rv  on t1.townshipStreets =  rv.townshipStreets where (tc.id = ? or ( tc.id =? and rv.area= ?))  and tc.type ='�ֵ���'";
		 if(role==20 || role ==21){
		 try { 
		 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.parea as area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
			     
	     if(userInfoVO != null){
	    	 String companyArea = userInfoVO.getArea();
	    	 int companyId = userInfoVO.getCompanyId();
	    	 CompanyInfoVO  wzCompanyInfo  =CompanyInfoVO.findFirstBySql(CompanyInfoVO.class, "select t.id from TwoCodeCompanyInfo t where t.companyName ='δ֪' and t.type='�ֵ���' ", null);
	    	 int wzcompanyId =0;
	    	 if(wzCompanyInfo != null)
	    		 wzcompanyId = wzCompanyInfo.getId();   //δ֪�ֵ����id
		//   total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, "select count(1) from (select townshipStreets   from twocodeelevatorinfo group by townshipStreets) t left join TwoCodeCompanyinfo tc on  t.townshipStreets =tc.id where tc.parea = ? ", new Object[]{companyArea});
	    	 total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, "select count(1) from TwoCodeCompanyinfo tc  where tc.id = ? or tc.id = ? ", new Object[]{companyId,wzcompanyId});
		     
	    	 items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, new Object[]{companyArea,companyId,wzcompanyId,companyId,wzcompanyId,companyArea},null, rows, (page-1)*rows);
	        } 
		 }catch (ActiveRecordException e) {	
			   e.printStackTrace();
		       }
		 }
		
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	
		 
	 }
	 
	 public View systongjiquery2(String ratingDate){
		 
		 
		 List<ElevaltorInfoVO> items = null;
		 String tjtime="";
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
		 tjtime =df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		 
		 String sql = "";
		 
		 Map<String, Object> result = new HashMap<String, Object>();
		 try {
		
		 if(tjtime.substring(0,7).equals(ratingDate)){  //��ǰ��Ҫִ�д洢���̣����������TwoCodeSystjû�У���ִ�д洢����
			 sql ="select (CASE t1.area WHEN '������' THEN 1  WHEN '������' THEN 2 WHEN '��ţ��' THEN 3 WHEN '�����' THEN 4 WHEN '�ɻ���' THEN 5 WHEN '������' THEN 6 WHEN '�츮����' THEN 7 WHEN '��Ȫ����' THEN 8 WHEN '��׽���' THEN 9 WHEN '�¶���' THEN 10 WHEN '�½���' THEN 11 WHEN '������' THEN 12 WHEN '˫����' THEN 13 WHEN 'ۯ����' THEN 14 WHEN '������' THEN 15 WHEN '�ѽ���' THEN 16 WHEN '�½���' THEN 17 WHEN '��������' THEN 18 WHEN '������' THEN 19 WHEN '������' THEN 20 WHEN '������' THEN 21 WHEN '������' THEN 22 ELSE  0 END) as mrindex,  t1.area,t1.etotal,t1.setotal,t1.ncqetotal,isnull(rv.tsrtotal,0) as tsrtotal,isnull(rv.undotsrtotal,0) as  undotsrtotal from (select  t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.area ) t1 left join TwoCodeReamarkEleView  rv  on t1.area =  rv.area order by mrindex";
			 items= ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null);	 
		 }
		 else {
			 sql ="select * from TwoCodeSystj where ratingDate = ? order by  mrindex";
			items= ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, new Object[]{ratingDate});
			if(items == null || items.size() < 1){
			items=  ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, "exec pro_systongjiquery2 ? ", new Object[] { ratingDate});
			} 
			}
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		 
		 result.put("rows", items);  
		 return new JsonView(result);	 
	 }
	 
	 
	 public View systongjiquery(ElevaltorInfoVO info,int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 
		 String area ="";
		 String conditions="";
		 area = info.getArea();
		 
		 String countSql="";
		 String sql ="";
		 
		 if(!"".equals(area)){
			 if(!"".equals(conditions)){
	
				 conditions =conditions+" and t1.area >= '"+area+"'";	
				} 
				else{
					conditions =" t1.area = '"+area+"'";	
				} 
		 }
		 List<ElevaltorInfoVO> items = new ArrayList<ElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
	 
		 long total=0;
	//	 String sql ="select area,count(registNumber) as etotal,sum(CASE WHEN dailingFlag = 1 THEN 1 ELSE 0 END) as setotal from twocodeelevatorinfo group by area";  
	//	 String sql ="select t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t  left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.area";
	//	 String sql ="select t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal,sum(CASE WHEN r.remarkLevel = 3 THEN 1 ELSE 0 END )  as tsrtotal from twocodeelevatorinfo t  left join YwManagerInfo  y on t.registNumber = y.registNumber left join twocodeRemark  r on t.registNumber = r.registNumber group by t.area ";
		 if("".equals(conditions)){
		 countSql ="select count(1) from (select area   from twocodeelevatorinfo group by area) t";
		 sql ="select t1.area,t1.etotal,t1.setotal,t1.ncqetotal,isnull(rv.tsrtotal,0) as tsrtotal,isnull(rv.undotsrtotal,0) as  undotsrtotal from (select  t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.area ) t1 left join TwoCodeReamarkEleView  rv  on t1.area =  rv.area"; 
		 }
		 else
		 {  
		 countSql ="select count(1)  from (select area   from twocodeelevatorinfo t1 where  "+conditions+" group by area ) t";	 
		 sql ="select t1.area,t1.etotal,t1.setotal,t1.ncqetotal,isnull(rv.tsrtotal,0) as tsrtotal,isnull(rv.undotsrtotal,0) as  undotsrtotal from (select  t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.area ) t1 left join TwoCodeReamarkEleView  rv  on t1.area =  rv.area  where "+conditions; 
					 
		 }
		 try {
		
		     total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, countSql, null);
		     items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null,null, rows, (page-1)*rows);
		
	 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	 
	 }
	 
	 public View ncqetotallist( ElevaltorInfoVO info,int page, int rows){
		 String area ="";
		 String conditions="";
		 area = info.getArea();
		 
		 String countSql="";
		 String sql ="";
		 
		 List<ElevaltorInfoVO> items = new ArrayList<ElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
	 
		 long total=0;
		 countSql ="select count(*) from twocodeelevatorinfo  t   left join YwManagerInfo  y on  t.registNumber = y.registNumber where DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15  and t.dailingFlag = 0 and t.area = '"+area+"'";
		 sql ="select t.registNumber,t.address,t.buildingName,t.building,t.unit,t.area,y.endTime from twocodeelevatorinfo  t   left join YwManagerInfo  y on  t.registNumber = y.registNumber where DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15  and t.dailingFlag = 0 and t.area = '"+area+"'";
		 
		 try {
				
		     total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, countSql, null);
		     items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null,null, rows, (page-1)*rows);
		
	 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	 
		 
	 }
	 
	 public View ncqetotallist2( ElevaltorInfoVO info,int page, int rows){
		 String area ="";
		 String conditions="";
		 area = info.getArea();
		 
		 String countSql="";
		 String sql ="";
		 
		 List<ElevaltorInfoVO> items = new ArrayList<ElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
	 
		 long total=0;
		 countSql ="select count(*) from twocodeelevatorinfo  t   left join YwManagerInfo  y on  t.registNumber = y.registNumber where DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15  and t.dailingFlag = 0 and t.area = '"+area+"'";
		 sql ="select t.registNumber,t.address,t.buildingName,t.building,t.unit,tc.companyname as wgcompanyName,tc.telephone,t.area,y.endTime,tu.userName,tu.companyName as ywcompanyName,tu.contactPhone  from twocodeelevatorinfo  t   left join YwManagerInfo  y on  t.registNumber = y.registNumber left join tcuserinfo  tu on tu.userid = y.userid left join twocodecompanyinfo tc on tc.id = t.wgcompanyId where DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15  and t.dailingFlag = 0 and t.area = '"+area+"'";
		 
		 try {
				
		     total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, countSql, null);
		     items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null,null, rows, (page-1)*rows);
		
	 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	 
		 
	 }
	 
	 public View ncqetotallist2ByOrder2( ElevaltorInfoVO info,int page, int rows,String sort,String order){
		 String area ="";
		 String ratingDate = "";
		 String ratingDateTemp ="";
		 String endTime ="";
		 String conditions="";
		 area = info.getArea();
		 ratingDate = info.getRatingDate();

		 long total=0;
		 List<ElevaltorInfoVO> items = new ArrayList<ElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 try {
		 ratingDateTemp =ratingDate+"-01";
		 DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		  
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(format.parse(ratingDateTemp));
		 calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
		// ��ӡ  
		 
		 endTime =format.format(calendar.getTime());
	     endTime =endTime + " 23:59:59";
		 
		 String countSql="";
		 String sql ="";
		 
		
		 
		 String SortName = request.getParameter("sort");  
	        //�״μ��ر���ʱsort��orderΪnull  
	     if(SortName == null){  
	            SortName ="t.registNumber"; 
	      }
	     if("registNumber".equals(SortName)){
	    	 SortName ="t.registNumber"; 
	     }
	     if("address".equals(SortName)){
	    	 SortName ="t.address"; 
	     }
	        
	     String SortValue =request.getParameter("order");  
	        if(SortValue == null){  
	            SortValue = "desc";        
	        }  
	 
	      java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM");
	      String s = format1.format(new Date());
	      int ratingDateMonthSpace = 0;
	      
	      ratingDateMonthSpace =dogetMonthSpace(ratingDate,s); 
			if(ratingDateMonthSpace != 0){  //���Ǳ��µĲ�ѯ����ʷ���в�ѯ
				 System.out.print("�Ǳ�������");
				 countSql ="select count(*) from twocodeelevatorinfo  t   left join (select registNumber,max(endTime) as endTime from HisYWManagerInfo where left(endTime,7) = '" +ratingDate+"' group by registNumber ) y on  t.registNumber = y.registNumber where t.area = '"+area+"'  and t.dailingFlag = 0 and DATEDIFF(dd,convert(datetime,isnull(y.endTime,'2012-05-01'),120),'"+endTime+"' ) > 15";
				 sql = "select t.registNumber,t.address,t.buildingName,t.building,t.unit,tc.companyname as wgcompanyName,tc.telephone,t.area,isnull(y.endTime,(select max(endTime) from HisYWManagerInfo where registNumber = t.registNumber and endTime < '" +ratingDate+"' )) as endTime   from twocodeelevatorinfo t   left join (select registNumber,max(endTime) as endTime from HisYWManagerInfo where left(endTime,7) = '"+ratingDate+"' group by registNumber )  y on t.registNumber = y.registNumber   left join twocodecompanyinfo tc on tc.id = t.wgcompanyId where t.area = '"+area+"'  and t.dailingFlag = 0 and DATEDIFF(dd,convert(datetime,isnull(y.endTime,'2012-05-01'),120),'"+endTime+"' ) > 15";	
				 
			}
			else{   //�������ݵĲ�ѯ
				 System.out.print("��������");
				 countSql ="select count(*) from twocodeelevatorinfo  t   left join YwManagerInfo  y on  t.registNumber = y.registNumber where DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and ISDATE(y.endTime) = 1 and t.dailingFlag = 0 and t.area = '"+area+"'";
				 sql ="select t.registNumber,t.address,t.buildingName,t.building,t.unit,tc.companyname as wgcompanyName,tc.telephone,t.area,y.endTime,tu.userName,(select companyName from twocodecompanyinfo where id = t.ywcompanyId) as ywcompanyName,tu.contactPhone  from twocodeelevatorinfo  t   left join YwManagerInfo  y on  t.registNumber = y.registNumber left join tcuserinfo  tu on tu.userid = y.userid left join twocodecompanyinfo tc on tc.id = t.wgcompanyId where DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and ISDATE(y.endTime) = 1 and t.dailingFlag = 0 and t.area = '"+area+"'";
					 
			}
	      
	
		
				
		     total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, countSql, null);
		     items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null,SortName+" "+SortValue, rows, (page-1)*rows);
		   
		
	 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
	 catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	 
		 
	 }
	 
	 public View ncqetotallist2ByOrder( ElevaltorInfoVO info,int page, int rows,String sort,String order){
		 String area ="";
		 String conditions="";
		 area = info.getArea();
		 
		 String countSql="";
		 String sql ="";
		 
		 List<ElevaltorInfoVO> items = new ArrayList<ElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 String SortName = request.getParameter("sort");  
	        //�״μ��ر���ʱsort��orderΪnull  
	     if(SortName == null){  
	            SortName ="t.registNumber"; 
	      }
	     if("registNumber".equals(SortName)){
	    	 SortName ="t.registNumber"; 
	     }
	     if("address".equals(SortName)){
	    	 SortName ="t.address"; 
	     }
	        
	     String SortValue =request.getParameter("order");  
	        if(SortValue == null){  
	            SortValue = "desc";        
	        }  
	 
		 long total=0;
		 
		 countSql ="select count(*) from twocodeelevatorinfo  t   left join YwManagerInfo  y on  t.registNumber = y.registNumber where DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and ISDATE(y.endTime) = 1 and t.dailingFlag = 0 and t.area = '"+area+"'";
	//	 sql ="select t.registNumber,t.address,t.buildingName,t.building,t.unit,tc.companyname as wgcompanyName,tc.telephone,t.area,y.endTime,tu.userName,tu.companyName as ywcompanyName,tu.contactPhone  from twocodeelevatorinfo  t   left join YwManagerInfo  y on  t.registNumber = y.registNumber left join tcuserinfo  tu on tu.userid = y.userid left join twocodecompanyinfo tc on tc.id = t.wgcompanyId where DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and ISDATE(y.endTime) = 1 and t.dailingFlag = 0 and t.area = '"+area+"'";
		 sql ="select t.registNumber,t.address,t.buildingName,t.building,t.unit,tc.companyname as wgcompanyName,tc.telephone,t.area,y.endTime,tu.userName,(select companyName from twocodecompanyinfo where id = t.ywcompanyId) as ywcompanyName,tu.contactPhone  from twocodeelevatorinfo  t   left join YwManagerInfo  y on  t.registNumber = y.registNumber left join tcuserinfo  tu on tu.userid = y.userid left join twocodecompanyinfo tc on tc.id = t.wgcompanyId where DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and ISDATE(y.endTime) = 1 and t.dailingFlag = 0 and t.area = '"+area+"'";
		 
		 try {
				
		     total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, countSql, null);
		     items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null,SortName+" "+SortValue, rows, (page-1)*rows);
		
	 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	 
		 
	 }
	 
	 public View ncqetotallist2zjByOrder( ElevaltorInfoVO info,int page, int rows,String sort,String order){
		 int townshipStreets =0;
		 String conditions="";
		 townshipStreets = info.getTownshipStreets();
		 
		 String countSql="";
		 String sql ="";
		 
		 List<ElevaltorInfoVO> items = new ArrayList<ElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 String SortName = request.getParameter("sort");  
	        //�״μ��ر���ʱsort��orderΪnull  
	     if(SortName == null){  
	            SortName ="t.registNumber"; 
	      }
	     if("registNumber".equals(SortName)){
	    	 SortName ="t.registNumber"; 
	     }
	     if("address".equals(SortName)){
	    	 SortName ="t.address"; 
	     }
	        
	     String SortValue =request.getParameter("order");  
	        if(SortValue == null){  
	            SortValue = "desc";        
	        }  
	 
		 long total=0;
		 
		 countSql ="select count(*) from twocodeelevatorinfo  t   left join YwManagerInfo  y on  t.registNumber = y.registNumber where DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15  and t.dailingFlag = 0 and t.townshipStreets = "+townshipStreets;
	//	 sql ="select t.registNumber,t.address,t.buildingName,t.building,t.unit,tc.companyname as wgcompanyName,tc.telephone,t.area,y.endTime,tu.userName,tu.companyName as ywcompanyName,tu.contactPhone  from twocodeelevatorinfo  t   left join YwManagerInfo  y on  t.registNumber = y.registNumber left join tcuserinfo  tu on tu.userid = y.userid left join twocodecompanyinfo tc on tc.id = t.wgcompanyId where DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15  and t.dailingFlag = 0 and t.townshipStreets = "+townshipStreets;
		 sql ="select t.registNumber,t.address,t.buildingName,t.building,t.unit,tc.companyname as wgcompanyName,tc.telephone,t.area,y.endTime,tu.userName,(select companyName from twocodecompanyinfo where id = t.ywcompanyId) as ywcompanyName,tu.contactPhone  from twocodeelevatorinfo  t   left join YwManagerInfo  y on  t.registNumber = y.registNumber left join tcuserinfo  tu on tu.userid = y.userid left join twocodecompanyinfo tc on tc.id = t.wgcompanyId where DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15  and t.dailingFlag = 0 and t.townshipStreets = "+townshipStreets;
		 
		 try {
				
		     total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, countSql, null);
		     items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null,SortName+" "+SortValue, rows, (page-1)*rows);
		
	 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	 
		 
	 }
	 
	 public View ncqetotallist2jdbByOrder( ElevaltorInfoVO info,int page, int rows,String sort,String order){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 
		 int townshipStreets =0;
		 String conditions="";
		 townshipStreets = info.getTownshipStreets();
		 
		 String countSql="";
		 String sql ="";
		 
		 List<ElevaltorInfoVO> items = new ArrayList<ElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 String SortName = request.getParameter("sort");  
	        //�״μ��ر���ʱsort��orderΪnull  
	     if(SortName == null){  
	            SortName ="t.registNumber"; 
	      }
	     if("registNumber".equals(SortName)){
	    	 SortName ="t.registNumber"; 
	     }
	     if("address".equals(SortName)){
	    	 SortName ="t.address"; 
	     }
	        
	     String SortValue =request.getParameter("order");  
	        if(SortValue == null){  
	            SortValue = "desc";        
	        }  
	     
	        
	     int wzcompanyId =0;
		 String area ="";
		 long total=0;
	     try {
	     CompanyInfoVO  wzCompanyInfo  =CompanyInfoVO.findFirstBySql(CompanyInfoVO.class, "select t.id from TwoCodeCompanyInfo t where t.companyName ='δ֪' and t.type='�ֵ���' ", null);
	     if(wzCompanyInfo != null){
			 wzcompanyId = wzCompanyInfo.getId();
			 
		 }
	     if(role==20 || role ==21){
			
			 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.parea as area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
				     
		     if(userInfoVO != null){
		    	  area = userInfoVO.getArea();
		     }
			 }
		 
		 
		 if(wzcompanyId ==townshipStreets && townshipStreets > 0){   //δ֪�ֵ���Ҫֻ��ʾ�������ڵ�δ֪�ֵ���
			 countSql ="select count(*) from twocodeelevatorinfo  t   left join YwManagerInfo  y on  t.registNumber = y.registNumber where DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15  and t.dailingFlag = 0 and t.townshipStreets = "+townshipStreets+" and t.area = '"+area+"'";
			 sql ="select t.registNumber,t.address,t.buildingName,t.building,t.unit,tc.companyname as wgcompanyName,tc.telephone,t.area,y.endTime,tu.userName,tu.companyName as ywcompanyName,tu.contactPhone  from twocodeelevatorinfo  t   left join YwManagerInfo  y on  t.registNumber = y.registNumber left join tcuserinfo  tu on tu.userid = y.userid left join twocodecompanyinfo tc on tc.id = t.wgcompanyId where DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15  and t.dailingFlag = 0 and t.townshipStreets = "+townshipStreets+" and t.area = '"+area+"'";
				 
		 }
		 else{
		 countSql ="select count(*) from twocodeelevatorinfo  t   left join YwManagerInfo  y on  t.registNumber = y.registNumber where DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15  and t.dailingFlag = 0 and t.townshipStreets = "+townshipStreets;
		 sql ="select t.registNumber,t.address,t.buildingName,t.building,t.unit,tc.companyname as wgcompanyName,tc.telephone,t.area,y.endTime,tu.userName,tu.companyName as ywcompanyName,tu.contactPhone  from twocodeelevatorinfo  t   left join YwManagerInfo  y on  t.registNumber = y.registNumber left join tcuserinfo  tu on tu.userid = y.userid left join twocodecompanyinfo tc on tc.id = t.wgcompanyId where DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15  and t.dailingFlag = 0 and t.townshipStreets = "+townshipStreets;
		 }
		 
		
				
		     total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, countSql, null);
		     items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null,SortName+" "+SortValue, rows, (page-1)*rows);
		
	 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	 
		 
	 }
	 
	 
	 public View undotsrtotallist( ElevaltorInfoVO info,int page, int rows){
		 String area ="";
		 String conditions="";
		 area = info.getArea();
		 
		 String countSql="";
		 String sql ="";
		 
		 List<ElevaltorInfoVO> items = new ArrayList<ElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
	 
		 long total=0;
		 countSql ="select count(*) from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber where r.remarkLevel = '3' and t.dailingFlag = 0 and t.area = '"+area+"'";
		 sql ="select r.registNumber,t.address,t.buildingName,t.building,t.unit,t.area,r.remarkDate,r.remarkInfo from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber where r.remarkLevel = '3'   and t.dailingFlag = 0 and t.area = '"+area+"'";
		 
		 try {
				
		     total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, countSql, null);
		     items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null,null, rows, (page-1)*rows);
		
	 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	 
		 
	 }
	 
	 public View undotsrtotallist2ByOrder( ElevaltorInfoVO info,int page, int rows,String sort,String order){
		 String area ="";
		 String conditions="";
		 area = info.getArea();

		 String countSql="";
		 String sql ="";
		 
		 List<ElevaltorInfoVO> items = new ArrayList<ElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 String SortName = request.getParameter("sort");  
	        //�״μ��ر���ʱsort��orderΪnull  
	     if(SortName == null){  
	            SortName ="t.registNumber"; 
	      }
	     if("registNumber".equals(SortName)){
	    	 SortName ="t.registNumber"; 
	     }
	     if("address".equals(SortName)){
	    	 SortName ="t.address"; 
	     }
	        
	     String SortValue =request.getParameter("order");  
	        if(SortValue == null){  
	            SortValue = "desc";        
	        }  
	 
		 long total=0;
		 countSql ="select count(*) from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber where r.remarkLevel = '3' and process_type =0 and t.dailingFlag = 0 and t.area = '"+area+"'";
		 sql ="select r.registNumber,t.address,t.buildingName,t.building,t.unit,t.area,r.remarkDate,r.remarkInfo,tc.companyname as wgcompanyName,tc.telephone,tu.userName,tu.companyName as ywcompanyName,tu.contactPhone from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber left join YwManagerInfo  y on  t.registNumber = y.registNumber  left join tcuserinfo  tu on tu.userid = y.userid left join twocodecompanyinfo tc on tc.id = t.wgcompanyId where r.remarkLevel = '3'  and process_type =0  and t.dailingFlag = 0 and t.area = '"+area+"'";
		 
		 try {
				
		     total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, countSql, null);
		     items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null,SortName+" "+SortValue, rows, (page-1)*rows);
		
	 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	 
		 
	 }
	             
	 public View undotsrtotallist2zjByOrder( ElevaltorInfoVO info,int page, int rows,String sort,String order){
		 int townshipStreets =0;
		 String conditions="";
		 townshipStreets = info.getTownshipStreets();
   
		 String countSql="";
		 String sql ="";
		 
		 List<ElevaltorInfoVO> items = new ArrayList<ElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 String SortName = request.getParameter("sort");  
	        //�״μ��ر���ʱsort��orderΪnull  
	     if(SortName == null){  
	            SortName ="t.registNumber"; 
	      }
	     if("registNumber".equals(SortName)){
	    	 SortName ="t.registNumber"; 
	     }
	     if("address".equals(SortName)){
	    	 SortName ="t.address"; 
	     }
	        
	     String SortValue =request.getParameter("order");  
	        if(SortValue == null){  
	            SortValue = "desc";        
	        }  
	 
		 long total=0;
		 countSql ="select count(*) from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber where r.remarkLevel = '3' and process_type=0  and t.dailingFlag = 0 and t.townshipStreets = "+townshipStreets;
		 sql ="select r.registNumber,t.area,t.address,t.buildingName,t.building,t.unit,t.townshipStreets,r.remarkDate,r.remarkInfo,tc.companyname as wgcompanyName,tc.telephone,tu.userName,tu.companyName as ywcompanyName,tu.contactPhone from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber left join YwManagerInfo  y on  t.registNumber = y.registNumber  left join tcuserinfo  tu on tu.userid = y.userid left join twocodecompanyinfo tc on tc.id = t.wgcompanyId where r.remarkLevel = '3'  and process_type=0 and t.dailingFlag = 0 and t.townshipStreets = "+townshipStreets;
		 
		 try {
				
		     total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, countSql, null);
		     items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null,SortName+" "+SortValue, rows, (page-1)*rows);
		
	 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	 
		 
	 }
	 
	 public View undotsrtotallist2jdbByOrder( ElevaltorInfoVO info,int page, int rows,String sort,String order){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 int townshipStreets =0;
		 String conditions="";
		 townshipStreets = info.getTownshipStreets();
   
		 String countSql="";
		 String sql ="";
		 
		 List<ElevaltorInfoVO> items = new ArrayList<ElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 String SortName = request.getParameter("sort");  
	        //�״μ��ر���ʱsort��orderΪnull  
	     if(SortName == null){  
	            SortName ="t.registNumber"; 
	      }
	     if("registNumber".equals(SortName)){
	    	 SortName ="t.registNumber"; 
	     }
	     if("address".equals(SortName)){
	    	 SortName ="t.address"; 
	     }
	        
	     String SortValue =request.getParameter("order");  
	        if(SortValue == null){  
	            SortValue = "desc";        
	        }  
	 
		 long total=0;
		 try {
		 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.parea as area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
		 String companyArea ="";
	     if(userInfoVO != null){
	    	  companyArea = userInfoVO.getArea();
	     }
		 countSql ="select count(*) from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber where r.remarkLevel = '3' and process_type =0 and t.dailingFlag = 0 and t.townshipStreets = "+townshipStreets+" and t.area = '"+companyArea+"'";
		 sql ="select r.registNumber,t.area,t.address,t.buildingName,t.building,t.unit,t.townshipStreets,r.remarkDate,r.remarkInfo,tc.companyname as wgcompanyName,tc.telephone,tu.userName,tu.companyName as ywcompanyName,tu.contactPhone from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber left join YwManagerInfo  y on  t.registNumber = y.registNumber  left join tcuserinfo  tu on tu.userid = y.userid left join twocodecompanyinfo tc on tc.id = t.wgcompanyId where r.remarkLevel = '3'  and process_type =0 and t.dailingFlag = 0 and t.townshipStreets = "+townshipStreets+" and t.area = '"+companyArea+"'";
		
		
				
		     total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, countSql, null);
		     items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null,SortName+" "+SortValue, rows, (page-1)*rows);
		
	 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	 
		 
	 }
	 
	 public View undotsrquery( ElevaltorInfoVO info,int page, int rows){
		 String area ="";
		 String registNumber ="";
		 String buildingName ="";
		 String conditions="";
		 area = info.getArea();
		 registNumber =info.getRegistNumber();
		 buildingName =info.getBuildingName();
		 
		 String countSql="";
		 String sql ="";
		 
		 if(!"".equals(registNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and r.registNumber like '%"+registNumber+"%'";	
				} 
				else{
				 conditions =" r.registNumber like '%"+registNumber+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(buildingName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.buildingName like '%"+buildingName+"%'";	
				} 
				else{
				 conditions =" t.buildingName like '%"+buildingName+"%'";	
				}
			 
		 }
		 
		 
		 List<ElevaltorInfoVO> items = new ArrayList<ElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
	 
		 long total=0;
		 if(!"".equals(conditions)){
		 countSql ="select count(*) from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber where "+conditions+" and  r.remarkLevel = '3' and t.dailingFlag = 0 and t.area = '"+area+"'";
		 sql ="select r.registNumber,t.address,t.buildingName,t.building,t.unit,t.area,r.remarkDate,r.remarkInfo from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber where "+conditions+" and r.remarkLevel = '3'   and t.dailingFlag = 0 and t.area = '"+area+"'";
		 }
		 else{
			 countSql ="select count(*) from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber where   r.remarkLevel = '3' and t.dailingFlag = 0 and t.area = '"+area+"'";
			 sql ="select r.registNumber,t.address,t.buildingName,t.building,t.unit,t.area,r.remarkDate,r.remarkInfo from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber where r.remarkLevel = '3'   and t.dailingFlag = 0 and t.area = '"+area+"'";
				 
		 }
		 try {
				
		     total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, countSql, null);
		     items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null,null, rows, (page-1)*rows);
		
	 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	 
		 
	 }
	 
	 public View undotsrquery2ByOrder( ElevaltorInfoVO info,int page, int rows,String sort,String order){
		 String area ="";
		 String registNumber ="";
		 String buildingName ="";
		 String conditions="";
		 area = info.getArea();
		 registNumber =info.getRegistNumber();
		 buildingName =info.getBuildingName();
		 
		 String countSql="";
		 String sql ="";
		 
		 String SortName = request.getParameter("sort");  
	        //�״μ��ر���ʱsort��orderΪnull  
	     if(SortName == null){  
	            SortName ="t.registNumber"; 
	      }
	     if("registNumber".equals(SortName)){
	    	 SortName ="t.registNumber"; 
	     }
	     if("address".equals(SortName)){
	    	 SortName ="t.address"; 
	     }
	        
	     String SortValue =request.getParameter("order");  
	        if(SortValue == null){  
	            SortValue = "desc";        
	        }  
		 
		 if(!"".equals(registNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and r.registNumber like '%"+registNumber+"%'";	
				} 
				else{
				 conditions =" r.registNumber like '%"+registNumber+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(buildingName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.buildingName like '%"+buildingName+"%'";	
				} 
				else{
				 conditions =" t.buildingName like '%"+buildingName+"%'";	
				}
			 
		 }
		 
		 
		 List<ElevaltorInfoVO> items = new ArrayList<ElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
	 
		 long total=0;
		 if(!"".equals(conditions)){
		 countSql ="select count(*) from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber where "+conditions+" and  r.remarkLevel = '3' and process_type = 0 and t.dailingFlag = 0 and t.area = '"+area+"'";
		 sql ="select r.registNumber,t.address,t.buildingName,t.building,t.unit,t.area,r.remarkDate,r.remarkInfo, tc.companyname as wgcompanyName,tc.telephone,tu.userName,tu.companyName as ywcompanyName,tu.contactPhone from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber left join YwManagerInfo  y on  t.registNumber = y.registNumber  left join tcuserinfo  tu on tu.userid = y.userid left join twocodecompanyinfo tc on tc.id = t.wgcompanyId where "+conditions+" and r.remarkLevel = '3'  and process_type = 0  and t.dailingFlag = 0 and t.area = '"+area+"'";
		 }
		 else{
			 countSql ="select count(*) from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber where   r.remarkLevel = '3'  and process_type = 0 and t.dailingFlag = 0 and t.area = '"+area+"'";
			 sql ="select r.registNumber,t.address,t.buildingName,t.building,t.unit,t.area,r.remarkDate,r.remarkInfo, tc.companyname as wgcompanyName,tc.telephone,tu.userName,tu.companyName as ywcompanyName,tu.contactPhone from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber left join YwManagerInfo  y on  t.registNumber = y.registNumber  left join tcuserinfo  tu on tu.userid = y.userid left join twocodecompanyinfo tc on tc.id = t.wgcompanyId where r.remarkLevel = '3'  and process_type = 0  and t.dailingFlag = 0 and t.area = '"+area+"'";
				 
		 }
		 try {
				
		     total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, countSql, null);
		     items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null,SortName+" "+SortValue, rows, (page-1)*rows);
		
	 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	 
		 
	 }
	 
	 public View undotsrquery2zjByOrder( ElevaltorInfoVO info,int page, int rows,String sort,String order){
		 int townshipStreets =0;
		 String registNumber ="";
		 String buildingName ="";
		 String conditions="";
		 townshipStreets = info.getTownshipStreets();
		 registNumber =info.getRegistNumber();
		 buildingName =info.getBuildingName();
		 
		 String countSql="";
		 String sql ="";
		 
		 String SortName = request.getParameter("sort");  
	        //�״μ��ر���ʱsort��orderΪnull  
	     if(SortName == null){  
	            SortName ="t.registNumber"; 
	      }
	     if("registNumber".equals(SortName)){
	    	 SortName ="t.registNumber"; 
	     }
	     if("address".equals(SortName)){
	    	 SortName ="t.address"; 
	     }
	        
	     String SortValue =request.getParameter("order");  
	        if(SortValue == null){  
	            SortValue = "desc";        
	        }  
		 
		 if(!"".equals(registNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and r.registNumber like '%"+registNumber+"%'";	
				} 
				else{
				 conditions =" r.registNumber like '%"+registNumber+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(buildingName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.buildingName like '%"+buildingName+"%'";	
				} 
				else{
				 conditions =" t.buildingName like '%"+buildingName+"%'";	
				}
			 
		 }
		 
		 
		 List<ElevaltorInfoVO> items = new ArrayList<ElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
	 
		 long total=0;
		 if(!"".equals(conditions)){
		 countSql ="select count(*) from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber where "+conditions+" and  r.remarkLevel = '3' and process_type = 0 and t.dailingFlag = 0 and t.townshipStreets = "+townshipStreets;
		 sql ="select r.registNumber,t.address,t.buildingName,t.building,t.unit,t.area,r.remarkDate,r.remarkInfo, tc.companyname as wgcompanyName,tc.telephone,tu.userName,tu.companyName as ywcompanyName,tu.contactPhone from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber left join YwManagerInfo  y on  t.registNumber = y.registNumber  left join tcuserinfo  tu on tu.userid = y.userid left join twocodecompanyinfo tc on tc.id = t.wgcompanyId where "+conditions+" and r.remarkLevel = '3'  and process_type = 0 and t.dailingFlag = 0 and t.townshipStreets = "+townshipStreets;
		 }
		 else{
			 countSql ="select count(*) from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber where   r.remarkLevel = '3' and process_type = 0 and t.dailingFlag = 0 and t.townshipStreets = "+townshipStreets;
			 sql ="select r.registNumber,t.address,t.buildingName,t.building,t.unit,t.area,r.remarkDate,r.remarkInfo, tc.companyname as wgcompanyName,tc.telephone,tu.userName,tu.companyName as ywcompanyName,tu.contactPhone from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber left join YwManagerInfo  y on  t.registNumber = y.registNumber  left join tcuserinfo  tu on tu.userid = y.userid left join twocodecompanyinfo tc on tc.id = t.wgcompanyId where r.remarkLevel = '3'  and process_type = 0 and t.dailingFlag = 0 and t.townshipStreets = "+townshipStreets;
				 
		 }
		 try {
				
		     total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, countSql, null);
		     items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null,SortName+" "+SortValue, rows, (page-1)*rows);
		
	 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	 
		 
	 }
	
	 public View undotsrquery2jdbByOrder( ElevaltorInfoVO info,int page, int rows,String sort,String order){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 int townshipStreets =0;
		 String registNumber ="";
		 String buildingName ="";
		 String conditions="";
		 townshipStreets = info.getTownshipStreets();
		 registNumber =info.getRegistNumber();
		 buildingName =info.getBuildingName();
		 
		 String countSql="";
		 String sql ="";
		 
		 String SortName = request.getParameter("sort");  
	        //�״μ��ر���ʱsort��orderΪnull
		    //Ĭ�ϵ������յ��ݱ�����ź�
	     if(SortName == null){  
	            SortName ="t.registNumber"; 
	      }
	     if("registNumber".equals(SortName)){
	    	 SortName ="t.registNumber"; 
	     }
	     if("address".equals(SortName)){
	    	 SortName ="t.address"; 
	     }
	        
	     String SortValue =request.getParameter("order");  
	        if(SortValue == null){  
	            SortValue = "desc";        
	        }  
		 
		 if(!"".equals(registNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and r.registNumber like '%"+registNumber+"%'";	
				} 
				else{
				 conditions =" r.registNumber like '%"+registNumber+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(buildingName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.buildingName like '%"+buildingName+"%'";	
				} 
				else{
				 conditions =" t.buildingName like '%"+buildingName+"%'";	
				}
			 
		 }
		 
		 
		 List<ElevaltorInfoVO> items = new ArrayList<ElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
	 
		 long total=0;
		 try {
		 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.parea as area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
		 String companyArea ="";
	     if(userInfoVO != null){
	    	  companyArea = userInfoVO.getArea();
	     }
		 if(!"".equals(conditions)){
		 countSql ="select count(*) from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber where "+conditions+" and  r.remarkLevel = '3' and process_type = 0 and t.dailingFlag = 0 and t.townshipStreets = "+townshipStreets+" and t.area = '"+companyArea+"'";
		 sql ="select r.registNumber,t.address,t.buildingName,t.building,t.unit,t.area,r.remarkDate,r.remarkInfo, tc.companyname as wgcompanyName,tc.telephone,tu.userName,tu.companyName as ywcompanyName,tu.contactPhone from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber left join YwManagerInfo  y on  t.registNumber = y.registNumber  left join tcuserinfo  tu on tu.userid = y.userid left join twocodecompanyinfo tc on tc.id = t.wgcompanyId where "+conditions+" and r.remarkLevel = '3'  and process_type = 0 and t.dailingFlag = 0 and t.townshipStreets = "+townshipStreets+" and t.area = '"+companyArea+"'";
		 }
		 else{
			 countSql ="select count(*) from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber where   r.remarkLevel = '3' and process_type = 0 and t.dailingFlag = 0 and t.townshipStreets = "+townshipStreets+" and t.area = '"+companyArea+"'";
			 sql ="select r.registNumber,t.address,t.buildingName,t.building,t.unit,t.area,r.remarkDate,r.remarkInfo, tc.companyname as wgcompanyName,tc.telephone,tu.userName,tu.companyName as ywcompanyName,tu.contactPhone from TwoCodeRemark  r   left join twocodeelevatorinfo  t on  r.registNumber = t.registNumber left join YwManagerInfo  y on  t.registNumber = y.registNumber  left join tcuserinfo  tu on tu.userid = y.userid left join twocodecompanyinfo tc on tc.id = t.wgcompanyId where r.remarkLevel = '3'  and process_type = 0 and t.dailingFlag = 0 and t.townshipStreets = "+townshipStreets+" and t.area = '"+companyArea+"'";
				 
		 }
		
				
		     total =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, countSql, null);
		     items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null,SortName+" "+SortValue, rows, (page-1)*rows);
		
	 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	 
		 
	 }
	
	 public View alarmListElevatorInfo(){
		
		 List<MapElevaltorInfoVO> items = new ArrayList<MapElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 String sql ="";
		 sql = "select t.map_X,t.map_Y,t.registNumber from TwoCodeWarnInfo w left join twocodeelevatorinfo  t  on w.registNumber = t.registNumber where t.registNumber is not null and t.map_X != '0' and t.map_Y ! ='0' group by t.map_X,t.map_Y,t.registNumber" ;
		 try { 
			items =MapElevaltorInfoVO.findBySql(MapElevaltorInfoVO.class, sql); 
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 result.put("alarmEleInfo", items);   
		 return new JsonView(result);	 
	 }
	 
	 public View tousuListElevatorInfo(){
			
		 List<MapElevaltorInfoVO> items = new ArrayList<MapElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 String sql ="";
		 sql = "select t.map_X,t.map_Y,t.registNumber from TwoCodeRemark w left join twocodeelevatorinfo  t  on w.registNumber = t.registNumber where t.registNumber is not null and t.map_X != '0' and t.map_Y ! ='0' and w.remarkLevel='3' group by t.map_X,t.map_Y,t.registNumber" ;
		 try { 
			items =MapElevaltorInfoVO.findBySql(MapElevaltorInfoVO.class, sql); 
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 result.put("tousuEleInfo", items);  
		 return new JsonView(result);	 
	 }
	 
	 public View eletjEcharts(){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<ElevaltorInfoVO> items = new ArrayList<ElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 String sql ="";
		 try {
//		 sql = "select t.area,count(t.registNumber) as etotal from  twocodeelevatorinfo  t  group by t.area  order by etotal desc" ;
		 if(role ==1 || role==2){   //Ϊ����ʾ���������ڼ�¼
        	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.loginName  from  TwoCodeUserInfo tu  where tu.id = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	    		 String userName =userInfoVO.getLoginName();
	    		 if("cy".equals(userName)){
	    	      sql ="select t1.area,t1.etotal,t1.setotal,0 as ncqetotal,isnull(rv.tsrtotal,0) as tsrtotal,isnull(rv.undotsrtotal,0) as  undotsrtotal from (select  t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.area ) t1 left join TwoCodeReamarkEleView  rv  on t1.area =  rv.area order by t1.etotal desc";
	    		}
	    		 else{   
	    	    sql ="select t1.area,t1.etotal,t1.setotal,t1.ncqetotal,isnull(rv.tsrtotal,0) as tsrtotal,isnull(rv.undotsrtotal,0) as  undotsrtotal from (select  t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.area ) t1 left join TwoCodeReamarkEleView  rv  on t1.area =  rv.area order by t1.etotal desc";
	    					
	    		 }
	    			 
	    	 }
		}
		 else{ 
		 sql ="select t1.area,t1.etotal,t1.setotal,t1.ncqetotal,isnull(rv.tsrtotal,0) as tsrtotal,isnull(rv.undotsrtotal,0) as  undotsrtotal from (select  t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.area ) t1 left join TwoCodeReamarkEleView  rv  on t1.area =  rv.area order by t1.etotal desc";
		 }  
			items =ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql); 
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	 }
		 result.put("tjEcharts", items);  
		 return new JsonView(result);
	 }
	 
	 public View elestjMap(){
		 List<ElevaltorInfoVO> items = new ArrayList<ElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 String sql ="";
		 sql ="select t1.area,count(t1.registNumber) as setotal from TwoCodeSElevatorInfoVIEW  t1 where t1.source = 0 group by  t1.area ";
		 try { 
			items =ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql); 
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	 }
		 result.put("elestjMap", items);  
		 return new JsonView(result);
	 }
	 
	 
	 public View eleMapRightTJ(){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 ElevaltorInfoVO item =  null;
		 Map<String, Object> result = new HashMap<String, Object>();
	 
		 long total=0;
		 String sql ="";
		 try { 
	//	 String sql ="select  count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN t.shemiFlag = 1 THEN 1 ELSE 0 END) as smetotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal,SUM(CASE WHEN (tr.remarkLevel = '3' AND tr.process_type = 0) THEN 1 ELSE 0 END) AS undotsrtotal from twocodeelevatorinfo t  left join YwManagerInfo  y on t.registNumber = y.registNumber left join twocodeRemark  tr on t.registNumber = tr.registNumber"; 
	//	 String sql ="select  count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN t.shemiFlag = 1 THEN 1 ELSE 0 END) as smetotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t  left join YwManagerInfo  y on t.registNumber = y.registNumber "; 
		  if(role ==1 || role==2){   //Ϊ����ʾ���������ڼ�¼
        	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.loginName  from  TwoCodeUserInfo tu  where tu.id = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	    		 String userName =userInfoVO.getLoginName();
	    		 if("cy".equals(userName)){
	    		sql ="select  count(t.registNumber) as etotal,(select count(*) from TwoCodeSElevatorInfoVIEW ) as setotal,sum(CASE WHEN t.shemiFlag = 1 THEN 1 ELSE 0 END) as smetotal,0 as ncqetotal,(select COUNT(*) from TwoCodeCompanyInfo where type ='ά��' and companyName <> 'δ֪' and ispasteyw = 0) as ywcompanytotal from twocodeelevatorinfo t  left join YwManagerInfo  y on t.registNumber = y.registNumber "; 	 
	    		 }
	    		 else{
	    		 sql ="select  count(t.registNumber) as etotal,(select count(*) from TwoCodeSElevatorInfoVIEW ) as setotal,sum(CASE WHEN t.shemiFlag = 1 THEN 1 ELSE 0 END) as smetotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal,(select COUNT(*) from TwoCodeCompanyInfo where type ='ά��' and companyName <> 'δ֪' and ispasteyw = 0) as ywcompanytotal from twocodeelevatorinfo t  left join YwManagerInfo  y on t.registNumber = y.registNumber "; 
	    				
	    		 }
	    			 
	    	 }
		}
		  else{
			  sql ="select  count(t.registNumber) as etotal,(select count(*) from TwoCodeSElevatorInfoVIEW ) as setotal,sum(CASE WHEN t.shemiFlag = 1 THEN 1 ELSE 0 END) as smetotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal,(select COUNT(*) from TwoCodeCompanyInfo where type ='ά��' and companyName <> 'δ֪' and ispasteyw = 0) as ywcompanytotal from twocodeelevatorinfo t  left join YwManagerInfo  y on t.registNumber = y.registNumber "; 
				
		  }
		 
		
		 item = ElevaltorInfoVO.findFirstBySql(ElevaltorInfoVO.class, sql, null);
	 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 if(item != null){
	     result.put("etotal", item.getEtotal());  
	     result.put("setotal", item.getSetotal());  
	     result.put("smetotal", item.getSmetotal()); 
	     result.put("ncqetotal", item.getNcqetotal());
	     result.put("undotsrtotal", item.getUndotsrtotal());
	     result.put("ywcompanytotal", item.getYwcompanytotal());
		 }
		 return new JsonView(result);	
	 }
	 
	 
	 public View eleMapRightTJByArea(String svalue){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 ElevaltorInfoVO item =  null;
		 Map<String, Object> result = new HashMap<String, Object>();
	     
		 System.out.println("svalue---"+svalue);
		 long total=0;
		 String sql ="";
		 String condition = "";
		 String condition2 = "";
		 
		 if(!"".equals(svalue)){
			 condition = " where t.area = '"+svalue+"'";
			 condition2 = " where area = '"+svalue+"'";
		 }
			 
		 try { 
		  if(role ==1 || role==2){   //Ϊ����ʾ���������ڼ�¼
        	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.loginName  from  TwoCodeUserInfo tu  where tu.id = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	    		 String userName =userInfoVO.getLoginName();
	    		 if("cy".equals(userName)){
	    	//	sql ="select  count(t.registNumber) as etotal,(select count(*) from TwoCodeSElevatorInfoVIEW ) as setotal,sum(CASE WHEN t.shemiFlag = 1 THEN 1 ELSE 0 END) as smetotal,0 as ncqetotal,(select COUNT(*) from TwoCodeCompanyInfo where type ='ά��' and companyName <> 'δ֪' and ispasteyw = 0) as ywcompanytotal from twocodeelevatorinfo t  left join YwManagerInfo  y on t.registNumber = y.registNumber "; 	 
	    		sql ="select  count(t.registNumber) as etotal,(select count(*) from TwoCodeSElevatorInfoVIEW "+ condition2 +"  ) as setotal,sum(CASE WHEN t.shemiFlag = 1 THEN 1 ELSE 0 END) as smetotal,0 as ncqetotal,(select COUNT(*) from TwoCodeCompanyInfo where type ='ά��' and companyName <> 'δ֪' and ispasteyw = 0 and id in (select ywCompanyId from TwoCodeElevatorInfo "+ condition2 + " group by ywCompanyId ) ) as ywcompanytotal from twocodeelevatorinfo t  left join YwManagerInfo  y on t.registNumber = y.registNumber "+condition; 	 
	 	    		
	    		 }
	    		 else{
	    		 sql ="select  count(t.registNumber) as etotal,(select count(*) from TwoCodeSElevatorInfoVIEW "+ condition2 +"  ) as setotal,sum(CASE WHEN t.shemiFlag = 1 THEN 1 ELSE 0 END) as smetotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal,(select COUNT(*) from TwoCodeCompanyInfo where type ='ά��' and companyName <> 'δ֪' and ispasteyw = 0 and id in (select ywCompanyId from TwoCodeElevatorInfo "+ condition2 + " group by ywCompanyId ) ) as ywcompanytotal from twocodeelevatorinfo t  left join YwManagerInfo  y on t.registNumber = y.registNumber "+condition; 
	    				
	    		 }
	    			 
	    	 }
		}
		  else{
		//	  sql ="select  count(t.registNumber) as etotal,(select count(*) from TwoCodeSElevatorInfoVIEW ) as setotal,sum(CASE WHEN t.shemiFlag = 1 THEN 1 ELSE 0 END) as smetotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal,(select COUNT(*) from TwoCodeCompanyInfo where type ='ά��' and companyName <> 'δ֪' and ispasteyw = 0) as ywcompanytotal from twocodeelevatorinfo t  left join YwManagerInfo  y on t.registNumber = y.registNumber "+condition; 
			  sql ="select  count(t.registNumber) as etotal,(select count(*) from TwoCodeSElevatorInfoVIEW "+ condition2 +"  ) as setotal,sum(CASE WHEN t.shemiFlag = 1 THEN 1 ELSE 0 END) as smetotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal,(select COUNT(*) from TwoCodeCompanyInfo where type ='ά��' and companyName <> 'δ֪' and ispasteyw = 0 and id in (select ywCompanyId from TwoCodeElevatorInfo "+ condition2 + " group by ywCompanyId ) ) as ywcompanytotal from twocodeelevatorinfo t  left join YwManagerInfo  y on t.registNumber = y.registNumber "+condition; 
	    		
		  }
		 
		
		 item = ElevaltorInfoVO.findFirstBySql(ElevaltorInfoVO.class, sql, null);
	 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 if(item != null){
	     result.put("etotal", item.getEtotal());  
	     result.put("setotal", item.getSetotal());  
	     result.put("smetotal", item.getSmetotal()); 
	     result.put("ncqetotal", item.getNcqetotal());
	     result.put("undotsrtotal", item.getUndotsrtotal());
	     result.put("ywcompanytotal", item.getYwcompanytotal());
		 }
		 return new JsonView(result);	
	 }
	 
	 public String zhuXiao(ElevaltorInfoVO elevaltorInfoVO){
		 String result = "failure";
		 int id = elevaltorInfoVO.getId();
		 this.zhuxiaoId = id;
		 this.zhuxiaoregistNumber =elevaltorInfoVO.getRegistNumber();
		 this.zhuxiaoresult ="failure";
		 String registNumber = elevaltorInfoVO.getRegistNumber();
		 try {
	//		 ActiveRecordBase.execute("exec proc_deleteElevatorinfo ?", new Object[] { registNumber });
			 ActiveRecordBase.execute("exec proc_zhuXiaoElevatorinfo ?", new Object[] { registNumber });
			 result = "success";
			 this.zhuxiaoresult="success";
		} catch (ActiveRecordException e) {		
			e.printStackTrace();
		}
		 return result;
	 }
	 
	 protected boolean  zhuxiaoElelog(){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 Logger log = Logger.getLogger(ElevatorController.class);
		 log.info("userid:"+userid+";"+"userName:"+userName+";"+"zhuxiaoId:"+this.zhuxiaoId+";"+"registNumber:"+this.zhuxiaoregistNumber+";"+"result:"+this.zhuxiaoresult);
		 return true;
	 }
	 
	 public View zxelevatorlistByOrder(int page, int rows,String sort,String order){
			
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 long total=0;
		 List<ZXElevaltorInfoVO> items = null;
		 
		 String SortName = request.getParameter("sort");  
	        //�״μ��ر���ʱsort��orderΪnull  
	     if(SortName == null){  
	            SortName ="t.updateTime"; 
	      }
	     if("registNumber".equals(SortName)){
	    	 SortName ="t.registNumber"; 
	     }
	        
	     String SortValue =request.getParameter("order");  
	        if(SortValue == null){  
	            SortValue = "desc";        
	        }  
		 try {
	     if(role==2 || role ==1){ //ϵͳ����Ա TwoCodeCompanyInfo 
	    	
			 total  =ZXElevaltorInfoVO.count(ZXElevaltorInfoVO.class, null, null);
	      	 String sql ="select t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,t.azCompanyId,t.wgCompanyId,t.ywCompanyId,t.zjCompanyId,t.jyCompanyId,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,t.ischangInfo  from TwoCodeZhuXElevatorInfo  t   "; 
			 items = ZXElevaltorInfoVO.findBySql(ZXElevaltorInfoVO.class, sql, null, SortName+" "+SortValue, rows, (page-1)*rows);    	
	     }
	     
	     if(role==22 || role ==23){ //ϵͳ����Ա TwoCodeCompanyInfo  shizhijian
	    	 total  =ZXElevaltorInfoVO.count(ZXElevaltorInfoVO.class, null, null);
	   // 	 String sql ="select t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber ";
	    	 String sql ="select t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,t.ischangInfo  from TwoCodeZhuXElevatorInfo  t  ";
	    	 
	    	 items = ZXElevaltorInfoVO.findBySql(ZXElevaltorInfoVO.class, sql, null, SortName+" "+SortValue, rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==10 || role ==11){  //zhijian
	        UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         String companyArea = userInfoVO.getArea();
	    //	 total  =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, "select count(*) from TwoCodeElevatorInfo t  where t.zjCompanyId=? and t.dailingFlag = 0 ", new Object[]{zjcompanyId});
	         total  =ZXElevaltorInfoVO.countBySql(ZXElevaltorInfoVO.class, "select count(*) from TwoCodeZhuXElevatorInfo t  where t.area=?  ", new Object[]{companyArea});
	    //	 String sql ="select t.dailingFlag,t.id,t.area,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where t.zjCompanyId=? ";
	   //    String sql ="select t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,(select top 1 y.subTime from YwManagerInfo y  where y.registNumber=t.registNumber and ywkind='0' order by y.id desc) as subTime,isnull(tcr.deviceId,'') as deviceId,t.ischangInfo  from TwoCodeElevatorInfo  t left join TwoCodeDeviceRelationInfo tcr on t.registNumber = tcr.registNumber where t.area=? ";  
	         String sql ="select t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,isnull(t.buildingName,'') as buildingName,t.registCode,t.area,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId ) as zzCompanyName,t.azCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId ) as azCompanyName,t.wgCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId ) as wgCompanyName,t.ywCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId ) as ywCompanyName,t.jyCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId) as jyCompanyName,t.zjCompanyId,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId) as zjCompanyName,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets) as jdbCompanyName,t.ischangInfo  from TwoCodeZhuXElevatorInfo  t  where t.area=? ";  
	    	 
	         items = ZXElevaltorInfoVO.findBySql(ZXElevaltorInfoVO.class, sql, new Object[]{companyArea}, SortName+" "+SortValue, rows, (page-1)*rows);	
	    	 }
	     } 
	     
	     
		 } catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }

	 public View queryZXByOrder(ElevaltorInfoVO info,int page, int rows,String sort,String order)throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 
		 String registNumber ="";
		
		 int zjcompanyId2 =0;  //�ǲ�ѯ�õģ��ǵ�½������ϵͳ��̨����role��ͬչʾ��ͬ�б��õ�
		 int townshipStreets2 =0; //�ǲ�ѯ�õģ��ǵ�½������ϵͳ��̨����role��ͬչʾ��ͬ�б��õ�
	
		 String areaName="";
		 String address ="";
		 String buildingName="";
		 String registCode="";
		 int townshipStreets =0;
		 
		
		 address =info.getAddress();
		 
		
		 
		 registNumber =info.getRegistNumber();
		 areaName =info.getArea();
		 buildingName =info.getBuildingName();
		 registCode=info.getRegistCode();
		
		 townshipStreets =info.getTownshipStreets();
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 String SortName = request.getParameter("sort");  
	        //�״μ��ر���ʱsort��orderΪnull  
	     if(SortName == null){  
	            SortName ="t.updateTime"; 
	      }
	     if("registNumber".equals(SortName)){
	    	 SortName ="t.registNumber"; 
	     }
	        
	     String SortValue =request.getParameter("order");  
	        if(SortValue == null){  
	            SortValue = "desc";        
	        }  
	        
	        
         if(role==10 || role==11){
           UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
         if(userInfoVO != null){
             zjcompanyId2 = userInfoVO.getCompanyId();
             String companyArea = userInfoVO.getArea();
        //     conditions = " t.zjcompanyId = "+zjcompanyId2;
             conditions = " t.area = '"+companyArea+"'";
             }	 
          }
         
         if(role==20 || role==21){
             UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
           if(userInfoVO != null){
        	   townshipStreets2 = userInfoVO.getCompanyId();
               conditions = " t.townshipStreets = "+townshipStreets2;
               }	 
            }
         
         if(!"".equals(registNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registNumber like '%"+registNumber+"%'";	
				} 
				else{
				 conditions =" t.registNumber like '%"+registNumber+"%'";	
				}
			 
		 }
         
         if(!"".equals(address)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.address like '%"+address+"%'";	
				} 
				else{
					 conditions =" t.address like '%"+address+"%'";	
				}
			 
		 }
         
         if(!"".equals(buildingName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.buildingName like '%"+buildingName+"%'";	
				} 
				else{
					conditions =" t.buildingName like '%"+buildingName+"%'";	
				}
			 
		 }
         
         if(!"".equals(registCode)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registCode like '%"+registCode+"%'";	
				} 
				else{
					conditions =" t.registCode like '%"+registCode+"%'";	
				}
			 
		 }
         
      
         
         if(!"".equals(areaName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.area like '%"+areaName+"%'";	
				} 
				else{
					conditions =" t.area like '%"+areaName+"%'";	
				}
			 
		 }
         
        
         
         if(townshipStreets > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.townshipStreets = "+townshipStreets;	
				} 
				else{
					conditions =" t.townshipStreets = "+townshipStreets;	
				}
			 
			 
		 }
         
         
         
         if(!"".equals(conditions)){
          sql ="select t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,t.buildingName,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,t.azCompanyId,t.wgCompanyId,t.ywCompanyId,t.zjCompanyId,t.jyCompanyId,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,t.ischangInfo  from TwoCodeZhuXElevatorInfo  t   where "+ conditions;
          conditionsSql = "select count(*) from TwoCodeZhuXElevatorInfo  t  where "+ conditions;
        
   		 }
   		 else{
         sql ="select t.shemiFlag,t.dailingFlag,t.id,t.area,t.registNumber,t.address,t.buildingName,t.registCode,t.useNumber,t.inspector,t.inspectDate,t.nextInspectDate,t.zzCompanyId,t.azCompanyId,t.wgCompanyId,t.ywCompanyId,t.zjCompanyId,t.jyCompanyId,t.townshipStreets,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.wgCompanyId and tc.type ='ʹ��') as wgCompanyName, (select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.ywCompanyId and tc.type ='ά��') as ywCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zzCompanyId and tc.type ='����') as zzCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.azCompanyId and tc.type ='��װ') as azCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.jyCompanyId and tc.type ='����') as jyCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.zjCompanyId and tc.type ='�ʼ�') as zjCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =t.townshipStreets and tc.type ='�ֵ���') as jdbCompanyName,t.ischangInfo  from TwoCodeZhuXElevatorInfo  t  ";
   		 conditionsSql = "select count(*) from TwoCodeElevatorInfo  t ";
   					 
   		 }
     
         long total =ZXElevaltorInfoVO.countBySql(ZXElevaltorInfoVO.class, conditionsSql, null); 
     	 List<ZXElevaltorInfoVO> items=ZXElevaltorInfoVO.findBySql(ZXElevaltorInfoVO.class, sql, null, SortName+" "+SortValue, rows, (page-1)*rows);
             
		 
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		 }
	 
	 /*
	public void downLoad() throws Exception {  
    String searchKey=new String(request.getParameter("searchKey").getBytes("ISO8859-1"), "UTF-8");;  
    String searchValue=new String(request.getParameter("searchValue").getBytes("ISO8859-1"), "UTF-8");;  
    //System.out.println("excel1"+searchKey);  
    //System.out.println("excel2"+searchValue);  
      
    String cond = "1=1";  
    List<Object> tmpArgs = new ArrayList<Object>();  
    if (!searchKey.equals("none")) { // ��ѯ����  
        cond += " and " + searchKey + " like ?";  
        tmpArgs.add("%" + searchValue + "%");  
    }  
    Object[] args = tmpArgs.toArray();  
    List<Address> dataset = Address.findAll(Address.class, cond, args);   
    String[] hearders = new String[] {"���", "��ҵ���", "��ҵ����", "����ʡ��", "��ϸ��ַ", "��������","���ȼ�",  
            "�Ǽ�ʱ��","״̬"};//��ͷ����  
      
    ExportExcel<Address> ex = new ExportExcel<Address>();  
      
    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");  
    String filename = timeFormat.format(new Date())+".xls";  
    response.setContentType("application/ms-excel;charset=UTF-8");  
    response.setHeader("Content-Disposition", "attachment;filename="  
            .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));  
    OutputStream out = response.getOutputStream();  
       ex.exportExcel(hearders, dataset, out);  
       out.close();  
       //System.out.println("excel�����ɹ���");  
}  
	 */
	
     /// <returns></returns>
	 public String exportExcel2(String ratingDate)
     {   
    	 String result = "failure"; 
    	 String cityName = GlobalFunction.cityName;
    	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<SystjInfoVO> items = new ArrayList<SystjInfoVO>();
		 String sql ="";
		 String tjtime ="";
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
		 tjtime =df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		 
		 try { 
		 if("0".equals(cityName)){
			 if(tjtime.substring(0,7).equals(ratingDate)){
			 sql ="(select (CASE t1.area WHEN '������' THEN 1  WHEN '������' THEN 2 WHEN '��ţ��' THEN 3 WHEN '�����' THEN 4 WHEN '�ɻ���' THEN 5 WHEN '������' THEN 6 WHEN '�츮����' THEN 7 WHEN '��Ȫ����' THEN 8 WHEN '��׽���' THEN 9 WHEN '�¶���' THEN 10 WHEN '�½���' THEN 11 WHEN '������' THEN 12 WHEN '˫����' THEN 13 WHEN 'ۯ����' THEN 14 WHEN '������' THEN 15 WHEN '�ѽ���' THEN 16 WHEN '�½���' THEN 17 WHEN '��������' THEN 18 WHEN '������' THEN 19 WHEN '������' THEN 20 WHEN '������' THEN 21 WHEN '������' THEN 22 ELSE  0 END) as mrindex, (CASE t1.area WHEN '������' THEN '������'  WHEN '������' THEN '������' WHEN '��ţ' THEN '��ţ��' WHEN '�����' THEN '�����' WHEN '�ɻ���' THEN '�ɻ���' WHEN '������' THEN '������' WHEN '�츮����' THEN '�츮����' WHEN '��Ȫ��' THEN '��Ȫ����' WHEN '��׽���' THEN '��׽���' WHEN '�¶���' THEN '�¶���' WHEN '�½���' THEN '�½���' WHEN '������' THEN '������' WHEN '˫����' THEN '˫����' WHEN 'ۯ��' THEN 'ۯ��' WHEN '������' THEN '������' WHEN '�ѽ���' THEN '�ѽ���' WHEN '�½���' THEN '�½���' WHEN '��������' THEN '��������' WHEN '������' THEN '������' WHEN '������' THEN '������' WHEN '������' THEN '������' WHEN '������' THEN '������' ELSE  t1.area END) as area,t1.etotal,t1.setotal,t1.ncqetotal,isnull(rv.tsrtotal,0) as tsrtotal,isnull(rv.undotsrtotal,0) as  undotsrtotal,(case when tsrtotal > 0 then cast((undotsrtotal* 1./tsrtotal*100) as   numeric(5,2) ) else 0.00 end )as nutotal,(case when (etotal-setotal) > 0  then cast((ncqetotal* 1./(etotal - setotal)*100 ) as numeric(5,2)) else 0.00 end ) as cql from (select  t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.area ) t1 left join TwoCodeReamarkEleView  rv  on t1.area =  rv.area ) UNION ALL (select 100 as mrindex,'�ϼ�' as area,sum(t1.etotal) as etotal,sum(t1.setotal) as setotal,sum(t1.ncqetotal) as ncqetotal,sum(rv.tsrtotal) as tsrtotal,sum(rv.undotsrtotal) as undotsrtotal,0.00 as nutotal,0.00 as cql from (select  t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.area ) t1 left join TwoCodeReamarkEleView  rv  on t1.area =  rv.area ) order by mrindex";
			 items = SystjInfoVO.findBySql(SystjInfoVO.class, sql, null,null);			 
			 }
	//	    sql ="(select (CASE t1.area WHEN '������' THEN 1  WHEN '������' THEN 2 WHEN '��ţ��' THEN 3 WHEN '�����' THEN 4 WHEN '�ɻ���' THEN 5 WHEN '������' THEN 6 WHEN '�츮����' THEN 7 WHEN '��Ȫ����' THEN 8 WHEN '��׽���' THEN 9 WHEN '�¶���' THEN 10 WHEN '�½���' THEN 11 WHEN '������' THEN 12 WHEN '˫����' THEN 13 WHEN 'ۯ��' THEN 14 WHEN '������' THEN 15 WHEN '�ѽ���' THEN 16 WHEN '�½���' THEN 17 WHEN '��������' THEN 18 WHEN '������' THEN 19 WHEN '������' THEN 20 WHEN '������' THEN 21 WHEN '������' THEN 22 ELSE  0 END) as mrindex, (CASE t1.area WHEN '������' THEN '������'  WHEN '������' THEN '������' WHEN '��ţ' THEN '��ţ��' WHEN '�����' THEN '�����' WHEN '�ɻ���' THEN '�ɻ���' WHEN '������' THEN '������' WHEN '�츮����' THEN '�츮����' WHEN '��Ȫ��' THEN '��Ȫ����' WHEN '��׽���' THEN '��׽���' WHEN '�¶���' THEN '�¶���' WHEN '�½���' THEN '�½���' WHEN '������' THEN '������' WHEN '˫����' THEN '˫����' WHEN 'ۯ��' THEN 'ۯ��' WHEN '������' THEN '������' WHEN '�ѽ���' THEN '�ѽ���' WHEN '�½���' THEN '�½���' WHEN '��������' THEN '��������' WHEN '������' THEN '������' WHEN '������' THEN '������' WHEN '������' THEN '������' WHEN '������' THEN '������' ELSE  t1.area END) as area,t1.etotal,t1.setotal,t1.ncqetotal,isnull(rv.tsrtotal,0) as tsrtotal,isnull(rv.undotsrtotal,0) as  undotsrtotal,(case when tsrtotal > 0 then cast((undotsrtotal* 1./tsrtotal*100) as   numeric(5,2) ) else 0.00 end )as nutotal,(case when (etotal-setotal) > 0  then cast((ncqetotal* 1./(etotal - setotal)*100 ) as numeric(5,2)) else 0.00 end ) as cql from (select  t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.area ) t1 left join TwoCodeReamarkEleView  rv  on t1.area =  rv.area ) UNION ALL (select 100 as mrindex,'�ϼ�' as area,sum(t1.etotal) as etotal,sum(t1.setotal) as setotal,sum(t1.ncqetotal) as ncqetotal,sum(rv.tsrtotal) as tsrtotal,sum(rv.undotsrtotal) as undotsrtotal,0.00 as nutotal,0.00 as cql from (select  t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.area ) t1 left join TwoCodeReamarkEleView  rv  on t1.area =  rv.area ) order by mrindex";
			else {
			sql ="(select mrindex,area,etotal,setotal,ncqetotal,tsrtotal,undotsrtotal,(case when tsrtotal > 0 then cast((undotsrtotal* 1./tsrtotal*100) as   numeric(5,2) ) else 0.00 end )as nutotal,(case when (etotal-setotal) > 0  then cast((ncqetotal* 1./(etotal - setotal)*100 ) as numeric(5,2)) else 0.00 end ) as cql from TwoCodeSystj where ratingDate = ? ) UNION ALL (select 100 as mrindex,'�ϼ�' as area,sum(t1.etotal) as etotal,sum(t1.setotal) as setotal,sum(t1.ncqetotal) as ncqetotal,sum(rv.tsrtotal) as tsrtotal,sum(rv.undotsrtotal) as undotsrtotal,0.00 as nutotal,0.00 as cql from (select  t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.area ) t1 left join TwoCodeReamarkEleView  rv  on t1.area =  rv.area ) order by mrindex";
			items= SystjInfoVO.findBySql(SystjInfoVO.class, sql, new Object[]{ratingDate});
			}
		 }
		 else{
			sql ="(select 1 as mrindex,t1.area as area,t1.etotal,t1.setotal,t1.ncqetotal,isnull(rv.tsrtotal,0) as tsrtotal,isnull(rv.undotsrtotal,0) as  undotsrtotal,(case when tsrtotal > 0 then cast((undotsrtotal* 1./tsrtotal*100) as   numeric(5,2) ) else 0.00 end )as nutotal,(case when (etotal-setotal) > 0  then cast((ncqetotal* 1./(etotal - setotal)*100 ) as numeric(5,2)) else 0.00 end ) as cql from (select  t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.area ) t1 left join TwoCodeReamarkEleView  rv  on t1.area =  rv.area ) UNION ALL (select 100 as mrindex,'�ϼ�' as area,sum(t1.etotal) as etotal,sum(t1.setotal) as setotal,sum(t1.ncqetotal) as ncqetotal,sum(rv.tsrtotal) as tsrtotal,sum(rv.undotsrtotal) as undotsrtotal,0.00 as nutotal,0.00 as cql from (select  t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.area ) t1 left join TwoCodeReamarkEleView  rv  on t1.area =  rv.area ) order by mrindex";		 
			items = SystjInfoVO.findBySql(SystjInfoVO.class, sql, null,null);
		 }
		
			
			 String[] hearders = new String[] {"��������","��������", "ͣ������", "ά����������", "Ͷ������", "Ͷ��δ����", "Ͷ��δ������","ά��������"};//��ͷ���� 
			 SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");  
			 String filename = "����ͳ�Ʊ�_"+timeFormat.format(new Date())+".xls";  
			 response.setContentType("application/ms-excel;charset=UTF-8");  
			 response.setHeader("Content-Disposition", "attachment;filename="  
			            .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
			 OutputStream out = response.getOutputStream(); 
			 if("0".equals(cityName)){
				 if(tjtime.substring(0,7).equals(ratingDate)){
					 ExportExcel<SystjInfoVO> ex = new ExportExcel<SystjInfoVO>(); 
					 ex.exportExcel(hearders, items, out); 
				 }
				 else{
			      ExportExcel2<SystjInfoVO> ex = new ExportExcel2<SystjInfoVO>(); 
			      ex.exportExcel(ratingDate,hearders, items, out);
				 }
			 }
			 else{
			 ExportExcel<SystjInfoVO> ex = new ExportExcel<SystjInfoVO>(); 
			 ex.exportExcel(hearders, items, out); 
			 }
				
		     out.close(); 
		   //��һ�зǳ��ؼ���������ʵ�����п��ܳ���Ī����������⣡����
		     response.flushBuffer();//ǿ�н���Ӧ�����е����ݷ��͵�Ŀ�ĵ� 
		     System.out.println("excel�����ɹ���"); 
		     
	 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
	 catch (UnsupportedEncodingException e1) {
			
			e1.printStackTrace();
		}
	 catch(IOException e2){
		 e2.printStackTrace();
	 }
	 
		 
		return result;
     } 
	 
	 
     public String exportExcel()
     {   
    	 String result = "failure"; 
    	 String cityName = GlobalFunction.cityName;
    	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<SystjInfoVO> items = new ArrayList<SystjInfoVO>();
		 String sql ="";
	//   String sql ="select (CASE t1.area WHEN '����' THEN 1  WHEN '����' THEN 2 WHEN '��ţ' THEN 3 WHEN '���' THEN 4 WHEN '�ɻ�' THEN 5 WHEN '����' THEN 6 WHEN '�츮��' THEN 7 WHEN '��Ȫ��' THEN 8 WHEN '��׽�' THEN 9 WHEN '�¶�' THEN 10 WHEN '�½�' THEN 11 WHEN '����' THEN 12 WHEN '˫��' THEN 13 WHEN 'ۯ��' THEN 14 WHEN '����' THEN 15 WHEN '�ѽ�' THEN 16 WHEN '�½�' THEN 17 WHEN '������' THEN 18 WHEN '����' THEN 19 WHEN '����' THEN 20 WHEN '����' THEN 21 WHEN '����' THEN 22 ELSE  0 END) as mrindex, (CASE t1.area WHEN '����' THEN '������'  WHEN '����' THEN '������' WHEN '��ţ' THEN '��ţ��' WHEN '���' THEN '�����' WHEN '�ɻ�' THEN '�ɻ���' WHEN '����' THEN '������' WHEN '�츮��' THEN '�츮����' WHEN '��Ȫ��' THEN '��Ȫ����' WHEN '��׽�' THEN '��׽���' WHEN '�¶�' THEN '�¶���' WHEN '�½�' THEN '�½���' WHEN '����' THEN '������' WHEN '˫��' THEN '˫����' WHEN 'ۯ��' THEN 'ۯ��' WHEN '����' THEN '������' WHEN '�ѽ�' THEN '�ѽ���' WHEN '�½�' THEN '�½���' WHEN '������' THEN '��������' WHEN '����' THEN '������' WHEN '����' THEN '������' WHEN '����' THEN '������' WHEN '����' THEN '������' ELSE  t1.area END) as area,t1.etotal,t1.setotal,t1.ncqetotal,isnull(rv.tsrtotal,0) as tsrtotal,isnull(rv.undotsrtotal,0) as  undotsrtotal,cast((undotsrtotal* 1./tsrtotal*100) as   numeric(5,2) )as nutotal,cast((ncqetotal* 1./(etotal - setotal)*100 ) as numeric(5,2)) as cql from (select  t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.area ) t1 left join TwoCodeReamarkEleView  rv  on t1.area =  rv.area order by mrindex  "; 
		 if("0".equals(cityName)){
		    sql ="(select (CASE t1.area WHEN '������' THEN 1  WHEN '������' THEN 2 WHEN '��ţ��' THEN 3 WHEN '�����' THEN 4 WHEN '�ɻ���' THEN 5 WHEN '������' THEN 6 WHEN '�츮����' THEN 7 WHEN '��Ȫ����' THEN 8 WHEN '��׽���' THEN 9 WHEN '�¶���' THEN 10 WHEN '�½���' THEN 11 WHEN '������' THEN 12 WHEN '˫����' THEN 13 WHEN 'ۯ����' THEN 14 WHEN '������' THEN 15 WHEN '�ѽ���' THEN 16 WHEN '�½���' THEN 17 WHEN '��������' THEN 18 WHEN '������' THEN 19 WHEN '������' THEN 20 WHEN '������' THEN 21 WHEN '������' THEN 22 ELSE  0 END) as mrindex, (CASE t1.area WHEN '������' THEN '������'  WHEN '������' THEN '������' WHEN '��ţ' THEN '��ţ��' WHEN '�����' THEN '�����' WHEN '�ɻ���' THEN '�ɻ���' WHEN '������' THEN '������' WHEN '�츮����' THEN '�츮����' WHEN '��Ȫ��' THEN '��Ȫ����' WHEN '��׽���' THEN '��׽���' WHEN '�¶���' THEN '�¶���' WHEN '�½���' THEN '�½���' WHEN '������' THEN '������' WHEN '˫����' THEN '˫����' WHEN 'ۯ��' THEN 'ۯ��' WHEN '������' THEN '������' WHEN '�ѽ���' THEN '�ѽ���' WHEN '�½���' THEN '�½���' WHEN '��������' THEN '��������' WHEN '������' THEN '������' WHEN '������' THEN '������' WHEN '������' THEN '������' WHEN '������' THEN '������' ELSE  t1.area END) as area,t1.etotal,t1.setotal,t1.ncqetotal,isnull(rv.tsrtotal,0) as tsrtotal,isnull(rv.undotsrtotal,0) as  undotsrtotal,(case when tsrtotal > 0 then cast((undotsrtotal* 1./tsrtotal*100) as   numeric(5,2) ) else 0.00 end )as nutotal,(case when (etotal-setotal) > 0  then cast((ncqetotal* 1./(etotal - setotal)*100 ) as numeric(5,2)) else 0.00 end ) as cql from (select  t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.area ) t1 left join TwoCodeReamarkEleView  rv  on t1.area =  rv.area ) UNION ALL (select 100 as mrindex,'�ϼ�' as area,sum(t1.etotal) as etotal,sum(t1.setotal) as setotal,sum(t1.ncqetotal) as ncqetotal,sum(rv.tsrtotal) as tsrtotal,sum(rv.undotsrtotal) as undotsrtotal,0.00 as nutotal,0.00 as cql from (select  t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.area ) t1 left join TwoCodeReamarkEleView  rv  on t1.area =  rv.area ) order by mrindex";
		 }
		 else{
			sql ="(select 1 as mrindex,t1.area as area,t1.etotal,t1.setotal,t1.ncqetotal,isnull(rv.tsrtotal,0) as tsrtotal,isnull(rv.undotsrtotal,0) as  undotsrtotal,(case when tsrtotal > 0 then cast((undotsrtotal* 1./tsrtotal*100) as   numeric(5,2) ) else 0.00 end )as nutotal,(case when (etotal-setotal) > 0  then cast((ncqetotal* 1./(etotal - setotal)*100 ) as numeric(5,2)) else 0.00 end ) as cql from (select  t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.area ) t1 left join TwoCodeReamarkEleView  rv  on t1.area =  rv.area ) UNION ALL (select 100 as mrindex,'�ϼ�' as area,sum(t1.etotal) as etotal,sum(t1.setotal) as setotal,sum(t1.ncqetotal) as ncqetotal,sum(rv.tsrtotal) as tsrtotal,sum(rv.undotsrtotal) as undotsrtotal,0.00 as nutotal,0.00 as cql from (select  t.area,count(t.registNumber) as etotal,sum(CASE WHEN t.dailingFlag = 1 THEN 1 ELSE 0 END) as setotal,sum(CASE WHEN  (DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and t.dailingFlag = 0) THEN 1 ELSE 0 END) as ncqetotal from twocodeelevatorinfo t   left join YwManagerInfo  y on t.registNumber = y.registNumber group by t.area ) t1 left join TwoCodeReamarkEleView  rv  on t1.area =  rv.area ) order by mrindex";		 
		 }
		 try { 
			 items = SystjInfoVO.findBySql(SystjInfoVO.class, sql, null,null);
			 String[] hearders = new String[] {"��������","��������", "ͣ������", "ά����������", "Ͷ������", "Ͷ��δ����", "Ͷ��δ������","ά��������"};//��ͷ���� 
			 ExportExcel<SystjInfoVO> ex = new ExportExcel<SystjInfoVO>(); 
			 SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");  
			 String filename = "����ͳ�Ʊ�_"+timeFormat.format(new Date())+".xls";  
			 response.setContentType("application/ms-excel;charset=UTF-8");  
			 response.setHeader("Content-Disposition", "attachment;filename="  
			            .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
			 OutputStream out = response.getOutputStream();  
		     ex.exportExcel(hearders, items, out);  
		//	 ex.exportExcel(tjtime,hearders, items, out);
		     out.close(); 
		   //��һ�зǳ��ؼ���������ʵ�����п��ܳ���Ī����������⣡����
		     response.flushBuffer();//ǿ�н���Ӧ�����е����ݷ��͵�Ŀ�ĵ� 
		     System.out.println("excel�����ɹ���"); 
		     
	 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
	 catch (UnsupportedEncodingException e1) {
			
			e1.printStackTrace();
		}
	 catch(IOException e2){
		 e2.printStackTrace();
	 }
	 
		 
		return result;
     } 
     
     public View tjyjyelevatorlist(int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 	        
		 long total=0;
		 List<ElevaltorInfoVO> items = null;
		 try {
	     if(role==2 || role ==1){ //ϵͳ����Ա
			 total  =ElevaltorInfoVO.countBySql(ElevaltorInfoVO.class, "select count(*) from (select registNumber from TwoCodeElevatorInfo where jyjyFlag =2 or jyjyFlag =3 union all select registNumber from TwoCodeddElevatorInfo where (jyjyFlag =2  or jyjyFlag =3 ) and recordSate = 2) t ", null);
	         String sql ="select tt.id,tt.registNumber,tt.registCode,tt.area,tt.address,tt.buildingName,tt.building,tt.unit,tt.useNumber,tt.resouceFlag,tt.registCode2,tt.area2,tt.address2,tt.buildingName2,tt.building2,tt.unit2,tt.useNumber2,tt.jyjyFlag,tt.registCode3,tt.area3,tt.address3,tt.buildingName3,tt.building3,tt.unit3,tt.useNumber3 from (select t.id,t.registNumber,t.registCode,ISNULL(t.area,'') as area, ISNULL(t.address,'') as address,ISNULL(t.buildingName,'') as buildingName, ISNULL(t.building,'') as building,ISNULL(t.unit,'') as unit,ISNULL(t.useNumber,'') as useNumber,0 as resouceFlag,tj.registCode as registCode2,tj.area as area2, tj.address as address2,tj.buildingName as buildingName2,tj.building as building2,tj.unit as unit2,tj.useNumber as useNumber2,t.jyjyFlag,td.registCode as registCode3,ISNULL(td.area,'') as area3, ISNULL(td.address,'') as address3,ISNULL(td.buildingName,'') as buildingName3, ISNULL(td.building,'') as building3,ISNULL(td.unit,'') as unit3,ISNULL(td.useNumber,'') as useNumber3  from TwoCodeElevatorInfo t left join TwoCodeJyJyData tj on t.registNumber =tj.registNumber left join TwoCodeddElevatorInfo td on t.registNumber = td.registNumber   where t.jyjyFlag =2 or t.jyjyFlag =3 union all select t.id, t.registNumber,t.registCode,ISNULL(t.area,'') as area, ISNULL(t.address,'') as address,ISNULL(t.buildingName,'') as buildingName, ISNULL(t.building,'') as building,ISNULL(t.unit,'') as unit,ISNULL(t.useNumber,'') as useNumber,1 as resouceFlag,tj.registCode as registCode2,tj.area as area2, tj.address as address2,tj.buildingName as buildingName2,tj.building as building2,tj.unit as unit2,tj.useNumber as useNumber2,t.jyjyFlag,t.registCode as registCode3,ISNULL(t.area,'') as area3, ISNULL(t.address,'') as address3,ISNULL(t.buildingName,'') as buildingName3, ISNULL(t.building,'') as building3,ISNULL(t.unit,'') as unit3,ISNULL(t.useNumber,'') as useNumber3  from TwoCodeddElevatorInfo t left join TwoCodeJyJyData tj on t.registNumber =tj.registNumber where (jyjyFlag =2 or jyjyFlag =3 ) and recordSate = 2) tt ";
	    	 items = ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, null, null, rows, (page-1)*rows);
	    	  	
	     }
	     
	     } catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
     
     
     public View tjyaddelevatorlistByOrder(int page, int rows,String sort,String order){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 String SortName = request.getParameter("sort");  
	        //�״μ��ر���ʱsort��orderΪnull  
	        if(SortName == null){  
	            SortName ="t.registNumber"; 
	        }
	        
	     String SortValue =request.getParameter("order");  
	        if(SortValue == null){  
	            SortValue = "asc";        
	        }  
	        
	        
		 long total=0;
		 List<JyNewRegistInfoVO> items = null;
		 try {
	     if(role==2 || role ==1){ //ϵͳ����Ա TwoCodeCompanyInfo 
			  total  =JyNewRegistInfoVO.countBySql(JyNewRegistInfoVO.class, "select count(*) from JyNewRegistInfo ", null);
	         String sql ="select t.registNumber,t.registCode,t.registor,isnull(t.address,'') as address,t.registDate  from JyNewRegistInfo  t ";
	    	 items = JyNewRegistInfoVO.findBySql(JyNewRegistInfoVO.class, sql, null, SortName+" "+SortValue, rows, (page-1)*rows);
	    	
	    	
	     }
	     
	     if(role==22 || role ==23){ //ϵͳ����Ա TwoCodeCompanyInfo 
	    	 total  =JyNewRegistInfoVO.countBySql(JyNewRegistInfoVO.class,  "select count(*) from JyNewRegistInfo ", null);
	    	  String sql ="select t.registNumber,t.registCode,t.registor,isnull(t.address,'') as address,t.registDate  from JyNewRegistInfo  t ";
	    	 items = JyNewRegistInfoVO.findBySql(JyNewRegistInfoVO.class, sql, null, SortName+" "+SortValue, rows, (page-1)*rows);
	    	
	     }
	     
	    
	     } catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
     
     public View tjyaddelevatorlistqueryByOrder(JyNewRegistInfoVO info,int page, int rows,String sort,String order){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 String SortName = request.getParameter("sort");  
	        //�״μ��ر���ʱsort��orderΪnull  
	        if(SortName == null){  
	            SortName ="registNumber";  
	        }  
	        String SortValue =request.getParameter("order");  
	        if(SortValue == null){  
	            SortValue = "asc";        
	        }  
		 
		 String registNumber ="";
		
		 String address ="";
		 String registCode="";
		
		 
		 
         address =info.getAddress();
		 registNumber =info.getRegistNumber();
		 registCode=info.getRegistCode();
		
		
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 
	        
	        
         
         
         if(!"".equals(registNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registNumber like '%"+registNumber+"%'";	
				} 
				else{
				 conditions =" t.registNumber like '%"+registNumber+"%'";	
				}
			 
		 }
         
         if(!"".equals(address)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.address like '%"+address+"%'";	
				} 
				else{
					 conditions =" t.address like '%"+address+"%'";	
				}
			 
		 }
         
         
         if(!"".equals(registCode)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registCode like '%"+registCode+"%'";	
				} 
				else{
					conditions =" t.registCode like '%"+registCode+"%'";	
				}
			 
		 }
         
         
         
		 
		 
         if(!"".equals(conditions)){
      		  sql ="select t.registNumber,t.registCode,t.registor,isnull(t.address,'') as address,t.registDate  from JyNewRegistInfo  t  where "+ conditions;  
      		  conditionsSql = "select count(*) from JyNewRegistInfo  t  where "+ conditions;
      		 }
      		 else{
      		  sql ="select t.registNumber,t.registCode,t.registor,isnull(t.address,'') as address,t.registDate  from JyNewRegistInfo  t ";
      		  conditionsSql = "select count(*) from JyNewRegistInfo  t ";
      					 
      		 }

		 
		
         long total = 0;
         List<JyNewRegistInfoVO> items = null;
		try {
			total = JyNewRegistInfoVO.countBySql(JyNewRegistInfoVO.class, conditionsSql, null);
		    items=JyNewRegistInfoVO.findBySql(JyNewRegistInfoVO.class, sql, null, SortName+" "+SortValue, rows, (page-1)*rows);
				
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		 
		 
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		 }
     
     /**********
 	jkName:�ӿ����� 
 	*/
 	public void jkdy(final String jkName,final String p1,final String p2){
 		String jkresulut="";
 		ExecutorService executor = Executors.newSingleThreadExecutor();  
 		FutureTask<String> future =  
 		       new FutureTask<String>(new Callable<String>() {//ʹ��Callable�ӿ���Ϊ�������  
 		         public String call() { 
 		        	 String callresult ="";
 		           //����������������ִ�У�����ķ���ֵ����ΪString������Ϊ��������  
 		        	 if("updateDeviceStatus".equals(jkName)){
 		        		    TsDeviceService server = new TsDeviceService();  
 							TsDeivceService hello = server.getTsDeivceServiceImplPort();
 						    callresult = hello.updateDeviceStatus("zyxt0703", "xr342fagf", p1, p2);
 								 
 		        	 }
 		        	 if("updateQrCodeByQrCode".equals(jkName)){
		        		    TsDeviceService server = new TsDeviceService();  
							TsDeivceService hello = server.getTsDeivceServiceImplPort();
							callresult = hello.updateQrCodeByQrCode("zyxt0703", "xr342fagf", p1, p2);
							
		        	 }
 		        	 return callresult;
 		       }});  
 		executor.execute(future); 
 		try {  
 			jkresulut = future.get(5000, TimeUnit.MILLISECONDS); //ȡ�ý����ͬʱ���ó�ʱִ��ʱ��Ϊ5�롣ͬ��������future.get()��������ִ�г�ʱʱ��ȡ�ý��  
 			System.out.println(jkresulut);
 		} catch (InterruptedException e) {  
 			future.cancel(true);  
 		} catch (ExecutionException e) {  
 			future.cancel(true);  
 		} catch (TimeoutException e) {  
 			future.cancel(true);  
 		} finally {  
 		    executor.shutdown();  
 		}  
 		
 	}
 	
 	 public static int dogetMonthSpace(String date1, String date2){ 
      int result = 0;
      int month  = 0;
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
       Calendar c1 = Calendar.getInstance();
       Calendar c2 = Calendar.getInstance();
       try {
		c1.setTime(sdf.parse(date1));
		c2.setTime(sdf.parse(date2));
	} catch (ParseException e) {	
		e.printStackTrace();
		System.out.println("�������ڽ�������");
	}
       
       result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
       month = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12;
       
      return Math.abs(month + result); 

}
 	 
 	 //�ؼ�Ժ��ּ�¼����
 	public String ywSysSetingstjy(int ywCompanyID,String ratingDate,YwQuaCredRatingTjy info){
 		String result = "failure";
 		int ratingDateMonthSpace = 0;
 		UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		int userid = userinfo.getId();
	//	String userName =userinfo.getLoginName();
		int  role = userinfo.getRole();
 		int num = 0;
 		int num2 =0;
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM");
		java.text.DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String s = format1.format(new Date()); 
 		String s2 =format2.format(new Date()); 
		ratingDateMonthSpace =dogetMonthSpace(ratingDate,s); 
		if(ratingDateMonthSpace != 0){
			return "failure2";
		}
 		UserExtInfo  info2 =null;
 		String userName ="";
 		int companyid = 0;
 		
 		YwQuaCredRating pInfo = null;
 		YwQuaCredRatingVO cInfo = null;
 		
 		 try {
  	    	ActiveRecordBase.beginTransaction();
  	    	info2 = UserExtInfo.findFirstBySql(UserExtInfo.class, "select userName,companyid from TwoCodeUserExtInfo where userid = ?", new Object[]{userid});
  	    	if(info2 != null){
  	    		userName = info2.getUserName();
  	    		companyid = info2.getCompanyid();	
  	    		System.out.println("companyid----"+companyid);
  	    	}
  	    	
  	  	YwQuaCredRatingVO vo =YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class, "select id from TwoCodeYwQuaCredRating where ratingCompanyId = ? and ratingDate = ? and ratingType = ? and  ywCompanyID = ? ", new Object[]{companyid,info.getRatingDate(),1,info.getYwCompanyID()});
  	     if(vo != null){ //�����ּ�¼�͸���
  	    	num = YwQuaCredRatingTjy.updateAll(YwQuaCredRatingTjy.class,"regularInspectionTimes=?,regularInspectionsj=?,regularInspectionbz=?,inspectElevatorTimes=?,inspectElevatorTimes2=?,inspectElevatorsj=?,inspectElevatorbz=?,acceptInspElevatorTimes=?,acceptInspElevatorsj=?,acceptInspElevatorbz=?,maintenSceneInfoTimes=?,maintenSceneInfosj=?,maintenSceneInfobz=?,ratingUserName=?,detailratingDate=?",
						new Object[] { info.getRegularInspectionTimes(),info.getRegularInspectionsj(),info.getRegularInspectionbz(),info.getInspectElevatorTimes(),info.getInspectElevatorTimes2(),info.getInspectElevatorsj(),info.getInspectElevatorbz(),info.getAcceptInspElevatorTimes(),info.getAcceptInspElevatorsj(),info.getAcceptInspElevatorbz(),info.getMaintenSceneInfoTimes(),info.getMaintenSceneInfosj(),info.getMaintenSceneInfobz(),userName,s2}, "ywCompanyID =? and ratingDate=? and ratingCompanyId = ? and ratingType = ? ",new Object[] {info.getYwCompanyID(),info.getRatingDate(),companyid,1});
  	    	if(num > 0){   //�Ӽ�¼���³ɹ�����������¼
  	    		 String sql= "";
  	    		 pInfo = YwQuaCredRating.findFirstBySql(YwQuaCredRating.class, "select * from TwoCodeYwQuaCredRating where ywCompanyID =?  and ratingDate = ? and ratingType = ?", new Object[]{info.getYwCompanyID(),info.getRatingDate(),0});
			      if(pInfo != null){
			    	  sql ="select sum(inspectElevatorTimes) as inspectElevatorTimes,sum(inspectElevatorTimes2) as inspectElevatorTimes2,sum(acceptInspElevatorTimes) as acceptInspElevatorTimes,sum(maintenSceneInfoTimes) as maintenSceneInfoTimes from TwoCodeYwQuaCredRating where ywCompanyID =?   and ratingDate = ? and ratingType = ?";
	   			        cInfo =YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class, sql, new Object[]{info.getYwCompanyID(),info.getRatingDate(),1});
	   			        int inspectElevatorTimes =0;
			    		int inspectElevatorTimes2 =0;   
			    		int acceptInspElevatorTimes=0;  //���ܼල������
			    		int maintenSceneInfoTimes =0;
			    		int complaintsEventsTimes=0;
			    		int complaintsEventsTimes2=0;
			    		int maintenBusinessTimes = 0;
	   			        if(cInfo != null){	
	   			    		//�������������ʼ๲�д�ֵĲ���
	   			    		inspectElevatorTimes =cInfo.getInspectElevatorTimes();
	   			    		inspectElevatorTimes2 =cInfo.getInspectElevatorTimes2();   
	   			    		acceptInspElevatorTimes=cInfo.getAcceptInspElevatorTimes();  //���ܼල������
	   			    		maintenSceneInfoTimes =cInfo.getMaintenSceneInfoTimes();
	   			    		complaintsEventsTimes=cInfo.getComplaintsEventsTimes();
	   			    		complaintsEventsTimes2=cInfo.getComplaintsEventsTimes2();
	   			    		maintenBusinessTimes = cInfo.getMaintenBusinessTimes();
	   			         }	
	   			    		
	   			  //  		pInfo = YwQuaCredRating.findFirstBySql(YwQuaCredRating.class, "select complaintsEventssj,maintenBusinesssj,honestsj,punishmentsj from TwoCodeYwQuaCredRating where ywCompanyID =?  and ratingDate = ? and ratingType = ?", new Object[]{info.getYwCompanyID(),info.getRatingDate(),0});
	   			    		pInfo.setInspectElevatorsj(100-5*inspectElevatorTimes-20*inspectElevatorTimes2);
	   			    		pInfo.setAcceptInspElevatorsj(0-10*acceptInspElevatorTimes);
	   			    		pInfo.setMaintenSceneInfosj(0-10*maintenSceneInfoTimes);
	   			    		pInfo.setComplaintsEventssj(100-5*complaintsEventsTimes-20*complaintsEventsTimes2);
	   			    		pInfo.setMaintenBusinesssj(0-20*maintenBusinessTimes);
	   			    		
	   			    	//��������¼�Ĵ���
	   			    		pInfo.setInspectElevatorTimes(inspectElevatorTimes);
	   			    		pInfo.setInspectElevatorTimes2(inspectElevatorTimes2);
	   			    		pInfo.setAcceptInspElevatorTimes(acceptInspElevatorTimes);
	   			    		pInfo.setMaintenSceneInfoTimes(maintenSceneInfoTimes);
	   			    		pInfo.setComplaintsEventsTimes(complaintsEventsTimes);
	   			    		pInfo.setComplaintsEventsTimes2(complaintsEventsTimes2);
	   			    		pInfo.setMaintenBusinessTimes(maintenBusinessTimes);
	   			    	   
	   			           //�ؼ��鵥����ֵĲ���
	   			    	    pInfo.setRegularInspectionTimes(info.getRegularInspectionTimes());
	   			    	    pInfo.setRegularInspectionsj(info.getRegularInspectionsj());
	   			    	    pInfo.setRegularInspectionbz(info.getRegularInspectionbz());
	   			    	    
	   			    	   if(pInfo.update()){
				    			result = "success";
				    		}	
	  			    	 
			          }
  	    	      }
  	         }
  	    	else{ //û�п����Ӽ�¼������
 	    		info.setYwCompanyID(ywCompanyID);
 	    		info.setRatingDate(ratingDate);   
 	    		info.setRatingType(1);
 	    		info.setRatingCompanyId(companyid);
 	    		info.setRatingUserName(userName);
 	    		boolean saveFlag = false;
 	    		saveFlag = info.save();
 	    		if(saveFlag){   
 	    		   //�Ӽ�¼���ӳɹ�����������¼
 	    	//		  pInfo = YwQuaCredRating.findFirstBySql(YwQuaCredRating.class, "select complaintsEventssj,maintenBusinesssj,honestsj,punishmentsj from TwoCodeYwQuaCredRating where ywCompanyID =?  and ratingDate = ? and ratingType = ?", new Object[]{info.getYwCompanyID(),info.getRatingDate(),0});
 	    			pInfo = YwQuaCredRating.findFirstBySql(YwQuaCredRating.class, "select *  from TwoCodeYwQuaCredRating where ywCompanyID =?  and ratingDate = ? and ratingType = ?", new Object[]{info.getYwCompanyID(),info.getRatingDate(),0});
 	    			  if(pInfo != null){ 	
 	 			      String sql= "";
 	 			      sql ="select sum(inspectElevatorTimes) as inspectElevatorTimes,sum(inspectElevatorTimes2) as inspectElevatorTimes2,sum(acceptInspElevatorTimes) as acceptInspElevatorTimes,sum(maintenSceneInfoTimes) as maintenSceneInfoTimes from TwoCodeYwQuaCredRating where ywCompanyID =?   and ratingDate = ? and ratingType = ?";
	   			      cInfo =YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class, sql, new Object[]{info.getYwCompanyID(),info.getRatingDate(),1});
	   			    int inspectElevatorTimes =0;
		    		int inspectElevatorTimes2 =0;   
		    		int acceptInspElevatorTimes=0;  //���ܼල������
		    		int maintenSceneInfoTimes =0;
		    		int complaintsEventsTimes=0;
		    		int complaintsEventsTimes2=0;
		    		int maintenBusinessTimes = 0;
	   			        if(cInfo != null){	
	   			    		//�������������ʼ๲�д�ֵĲ���
	   			    		 inspectElevatorTimes =cInfo.getInspectElevatorTimes();
	   			    		 inspectElevatorTimes2 =cInfo.getInspectElevatorTimes2();   
	   			    		 acceptInspElevatorTimes=cInfo.getAcceptInspElevatorTimes();  //���ܼල������
	   			    		 maintenSceneInfoTimes =cInfo.getMaintenSceneInfoTimes();  //ά���ֳ�
	   			    		 complaintsEventsTimes=cInfo.getComplaintsEventsTimes();
	   			    		 complaintsEventsTimes2=cInfo.getComplaintsEventsTimes2();
	   			    		 maintenBusinessTimes = cInfo.getMaintenBusinessTimes();
	   			    	}
	   			    		
	   			//    		pInfo = YwQuaCredRating.findFirstBySql(YwQuaCredRating.class, "select complaintsEventssj,maintenBusinesssj,honestsj,punishmentsj from TwoCodeYwQuaCredRating where ywCompanyID =?  and ratingDate = ? and ratingType = ?", new Object[]{info.getYwCompanyID(),info.getRatingDate(),0});
	   			    		pInfo.setInspectElevatorsj(100-5*inspectElevatorTimes-20*inspectElevatorTimes2);
	   			    		pInfo.setAcceptInspElevatorsj(0-10*acceptInspElevatorTimes);
	   			    		pInfo.setMaintenSceneInfosj(0-10*maintenSceneInfoTimes);
	   			    		pInfo.setComplaintsEventssj(100-5*complaintsEventsTimes-20*complaintsEventsTimes2);
	   			    		pInfo.setMaintenBusinesssj(0-20*maintenBusinessTimes);
	   			    		
	   			    	//��������¼�Ĵ���
	   			    		pInfo.setInspectElevatorTimes(inspectElevatorTimes);
	   			    		pInfo.setInspectElevatorTimes2(inspectElevatorTimes2);
	   			    		pInfo.setAcceptInspElevatorTimes(acceptInspElevatorTimes);
	   			    		pInfo.setMaintenSceneInfoTimes(maintenSceneInfoTimes);
	   			    		pInfo.setComplaintsEventsTimes(complaintsEventsTimes);
	   			    		pInfo.setComplaintsEventsTimes2(complaintsEventsTimes2);
	   			    		pInfo.setMaintenBusinessTimes(maintenBusinessTimes);
	   			    	   
	   			           //�ؼ��鵥����ֵĲ���
	   			    	    pInfo.setRegularInspectionTimes(info.getRegularInspectionTimes());
	   			    	    pInfo.setRegularInspectionsj(info.getRegularInspectionsj());
	   			    	    pInfo.setRegularInspectionbz(info.getRegularInspectionbz());
	   			    	    
	   			    	   if(pInfo.update()){
				    			result = "success";
				    		}		     
 	               }
  	    	    }
  	    	}
  	        ActiveRecordBase.commit();	
  	       }
          catch (ActiveRecordException e) {
    	     try {
			     ActiveRecordBase.rollback();
		         } 
    	     catch (TransactionException e1) {
			      e1.printStackTrace();
		         }
	       }
	return result;
}
 	 
    //�����ʼౣ���ּ�¼
 	public String ywSysSetingsqzj(int ywCompanyID,String ratingDate,YwQuaCredRatingQzj info){
 		String result = "failure";
 		int ratingDateMonthSpace = 0;
 		UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		int userid = userinfo.getId();
	//	String userName =userinfo.getLoginName();
		int  role = userinfo.getRole();
 		int num = 0;
 		int num2 =0;
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM");
		java.text.DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String s = format1.format(new Date()); 
 		String s2 =format2.format(new Date()); 
		ratingDateMonthSpace =dogetMonthSpace(ratingDate,s); 
		if(ratingDateMonthSpace != 0){
			return "failure2";
		}
 		UserExtInfo  info2 =null;
 		String userName ="";
 		int companyid = 0;
 		
 		YwQuaCredRating pInfo = null;
 		YwQuaCredRatingVO cInfo = null;
 		
 		 try {
  	    	ActiveRecordBase.beginTransaction();
  	    	info2 = UserExtInfo.findFirstBySql(UserExtInfo.class, "select userName,companyid from TwoCodeUserExtInfo where userid = ?", new Object[]{userid});
  	    	if(info2 != null){
  	    		userName = info2.getUserName();
  	    		companyid = info2.getCompanyid();	
  	    		System.out.println("companyid----"+companyid);
  	    	}
  	    	
  	  	YwQuaCredRatingVO vo =YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class, "select id from TwoCodeYwQuaCredRating where ratingCompanyId = ? and ratingDate = ? and ratingType = ? and  ywCompanyID = ? ", new Object[]{companyid,info.getRatingDate(),1,info.getYwCompanyID()});
  	     if(vo != null){ //�����ּ�¼�͸���
  	    	num = YwQuaCredRatingQzj.updateAll(YwQuaCredRatingQzj.class,"inspectElevatorTimes=?,inspectElevatorTimes2=?,inspectElevatorsj=?,inspectElevatorbz=?,acceptInspElevatorTimes=?,acceptInspElevatorsj=?,acceptInspElevatorbz=?,maintenSceneInfoTimes=?,maintenSceneInfosj=?,maintenSceneInfobz=?,complaintsEventsTimes=?,complaintsEventsTimes2=?,complaintsEventssj=?,complaintsEventsbz=?,maintenBusinessTimes=?,maintenBusinesssj=?,maintenBusinessbz=?,honestTimes=?,honestsj=?,honestbz=?,punishmentTimes=?,punishmentTimes2=?,punishmentTimes3=?,punishmentTimes4=?,punishmentsj=?,punishmentbz=?,ratingUserName=?,detailratingDate=?",
						new Object[] { info.getInspectElevatorTimes(),info.getInspectElevatorTimes2(),info.getInspectElevatorsj(),info.getInspectElevatorbz(),info.getAcceptInspElevatorTimes(),info.getAcceptInspElevatorsj(),info.getAcceptInspElevatorbz(),info.getMaintenSceneInfoTimes(),info.getMaintenSceneInfosj(),info.getMaintenSceneInfobz(),info.getComplaintsEventsTimes(),info.getComplaintsEventsTimes2(),info.getComplaintsEventssj(),info.getComplaintsEventsbz(),info.getMaintenBusinessTimes(),info.getMaintenBusinesssj(),info.getMaintenBusinessbz(),info.getHonestTimes(),info.getHonestsj(),info.getHonestbz(),info.getPunishmentTimes(),info.getPunishmentTimes2(),info.getPunishmentTimes3(),info.getPunishmentTimes4(),info.getPunishmentsj(),info.getPunishmentbz(),userName,s2}, "ywCompanyID =? and ratingDate=? and ratingCompanyId = ? and ratingType = ? ",new Object[] {info.getYwCompanyID(),info.getRatingDate(),companyid,1});
  	    	if(num > 0){   //�Ӽ�¼���³ɹ�����������¼
  	    		 String sql= "";
  	    		 pInfo = YwQuaCredRating.findFirstBySql(YwQuaCredRating.class, "select * from TwoCodeYwQuaCredRating where ywCompanyID =?  and ratingDate = ? and ratingType = ?", new Object[]{info.getYwCompanyID(),info.getRatingDate(),0});
			      if(pInfo != null){
			    		//��ͬ��ֵĲ���ȡ�Ӽ�¼֮��
	  			        sql ="select sum(inspectElevatorTimes) as inspectElevatorTimes,sum(inspectElevatorTimes2) as inspectElevatorTimes2,sum(acceptInspElevatorTimes) as acceptInspElevatorTimes,sum(maintenSceneInfoTimes) as maintenSceneInfoTimes,sum(complaintsEventsTimes) as complaintsEventsTimes,sum(complaintsEventsTimes2) as complaintsEventsTimes2,sum(maintenBusinessTimes) as maintenBusinessTimes,sum(honestTimes) as honestTimes,sum(punishmentTimes) as punishmentTimes,sum(punishmentTimes2) as punishmentTimes2,sum(punishmentTimes3) as punishmentTimes3,sum(punishmentTimes4) as punishmentTimes4 from TwoCodeYwQuaCredRating where ywCompanyID =?   and ratingDate = ? and ratingType = ?";
	  			        cInfo =YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class, sql, new Object[]{info.getYwCompanyID(),info.getRatingDate(),1});
	  			        int inspectElevatorTimes =0;
			    		int inspectElevatorTimes2 =0;   
			    		int acceptInspElevatorTimes=0;  //���ܼල������
			    		int maintenSceneInfoTimes =0;
			    		int complaintsEventsTimes=0;
			    		int complaintsEventsTimes2=0;
			    		int maintenBusinessTimes = 0;
			    		int honestTimes =0;
			    		int punishmentTimes=0;
			    		int punishmentTimes2=0;
			    		int punishmentTimes3=0;
			    		int punishmentTimes4=0;
	  			        if(cInfo != null){	
	  			    		//���������ʼ�,�����ؼ�Ժ���д�ֵĲ���
	  			    		inspectElevatorTimes =cInfo.getInspectElevatorTimes();
	  			    		inspectElevatorTimes2 =cInfo.getInspectElevatorTimes2();   
	  			    		acceptInspElevatorTimes=cInfo.getAcceptInspElevatorTimes();  //���ܼල������
	  			    		maintenSceneInfoTimes =cInfo.getMaintenSceneInfoTimes();
	  			    		complaintsEventsTimes=cInfo.getComplaintsEventsTimes();
	  			    		complaintsEventsTimes2=cInfo.getComplaintsEventsTimes2();
	  			    		maintenBusinessTimes = cInfo.getMaintenBusinessTimes();
	  			    		honestTimes =cInfo.getHonestTimes();
	  			    		punishmentTimes=cInfo.getPunishmentTimes();
	  			    		punishmentTimes2=cInfo.getPunishmentTimes2();
	  			    		punishmentTimes3=cInfo.getPunishmentTimes3();
	  			    		punishmentTimes4=cInfo.getPunishmentTimes4();
	  			    	}
	  			    //		pInfo = YwQuaCredRating.findFirstBySql(YwQuaCredRating.class, "select complaintsEventssj,maintenBusinesssj,honestsj,punishmentsj from TwoCodeYwQuaCredRating where ywCompanyID =?  and ratingDate = ? and ratingType = ?", new Object[]{info.getYwCompanyID(),info.getRatingDate(),0});
	  			    		pInfo.setInspectElevatorsj(100-5*inspectElevatorTimes-20*inspectElevatorTimes2);
	  			    		pInfo.setAcceptInspElevatorsj(0-10*acceptInspElevatorTimes);
	  			    		pInfo.setMaintenSceneInfosj(0-10*maintenSceneInfoTimes);
	  			    		pInfo.setComplaintsEventssj(100-5*complaintsEventsTimes-20*complaintsEventsTimes2);
	  			    		pInfo.setMaintenBusinesssj(0-20*maintenBusinessTimes);
	  			    		pInfo.setHonestsj(0-20*honestTimes);
	  			    		pInfo.setPunishmentsj(100-2*punishmentTimes-5*punishmentTimes2-20*punishmentTimes3-20*punishmentTimes4);
	  			    		
	  			    	//��������¼�Ĵ���
	  			    		pInfo.setInspectElevatorTimes(inspectElevatorTimes);
	  			    		pInfo.setInspectElevatorTimes2(inspectElevatorTimes2);
	  			    		pInfo.setAcceptInspElevatorTimes(acceptInspElevatorTimes);
	  			    		pInfo.setMaintenBusinessTimes(maintenBusinessTimes);
	  			    		pInfo.setComplaintsEventsTimes(complaintsEventsTimes);
	  			    		pInfo.setComplaintsEventsTimes2(complaintsEventsTimes2);
	  			    		pInfo.setMaintenBusinessTimes(maintenBusinessTimes);
	  			    		pInfo.setHonestTimes(honestTimes);
	  			    		pInfo.setPunishmentTimes(punishmentTimes);
	  			    		pInfo.setPunishmentTimes2(punishmentTimes2);
	  			    		pInfo.setPunishmentTimes3(punishmentTimes3);
	  			    		pInfo.setPunishmentTimes4(punishmentTimes4);
	  			    	   
	  			           //���������У����ʼ൥����ֵĲ��֣�û�е�����ֵģ����Բ���Ҫ������
	  			    	    if(pInfo.update()){
				    			result = "success";
				    		}
			          }
  	    	      }
  	         }
  	    	else{ //û�п����Ӽ�¼������
 	    		info.setYwCompanyID(ywCompanyID);
 	    		info.setRatingDate(ratingDate);
 	    		info.setRatingType(1);
 	    		info.setRatingCompanyId(companyid);
 	    		info.setRatingUserName(userName);
 	    		boolean saveFlag = false;
 	    		saveFlag = info.save();
 	    		if(saveFlag){   
 	    		   //�Ӽ�¼���ӳɹ�����������¼
 	    	//		  pInfo = YwQuaCredRating.findFirstBySql(YwQuaCredRating.class, "select complaintsEventssj,maintenBusinesssj,honestsj,punishmentsj from TwoCodeYwQuaCredRating where ywCompanyID =?  and ratingDate = ? and ratingType = ?", new Object[]{info.getYwCompanyID(),info.getRatingDate(),0});
 	    			pInfo = YwQuaCredRating.findFirstBySql(YwQuaCredRating.class, "select *  from TwoCodeYwQuaCredRating where ywCompanyID =?  and ratingDate = ? and ratingType = ?", new Object[]{info.getYwCompanyID(),info.getRatingDate(),0});
 	    			  if(pInfo != null){ 	
 	 			      String sql= "";
 	 			    //��(��)���ʼ� ��ͬ��ֵĲ���ȡ�Ӽ�¼֮��
	  			        sql ="select sum(inspectElevatorTimes) as inspectElevatorTimes,sum(inspectElevatorTimes2) as inspectElevatorTimes2,sum(acceptInspElevatorTimes) as acceptInspElevatorTimes,sum(maintenSceneInfoTimes) as maintenSceneInfoTimes,sum(complaintsEventsTimes) as complaintsEventsTimes,sum(complaintsEventsTimes2) as complaintsEventsTimes2,sum(maintenBusinessTimes) as maintenBusinessTimes,sum(honestTimes) as honestTimes,sum(punishmentTimes) as punishmentTimes,sum(punishmentTimes2) as punishmentTimes2,sum(punishmentTimes3) as punishmentTimes3,sum(punishmentTimes4) as punishmentTimes4 from TwoCodeYwQuaCredRating where ywCompanyID =?   and ratingDate = ? and ratingType = ?";
	  			        cInfo =YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class, sql, new Object[]{info.getYwCompanyID(),info.getRatingDate(),1});
	  			        int inspectElevatorTimes =0;
			    		int inspectElevatorTimes2 =0;   
			    		int acceptInspElevatorTimes=0;  //���ܼල������
			    		int maintenSceneInfoTimes =0;
			    		int complaintsEventsTimes=0;
			    		int complaintsEventsTimes2=0;
			    		int maintenBusinessTimes = 0;
			    		int honestTimes =0;
			    		int punishmentTimes=0;
			    		int punishmentTimes2=0;
			    		int punishmentTimes3=0;
			    		int punishmentTimes4=0;
	  			        if(cInfo != null){	
	  			    		//���������ʼ�,�����ؼ�Ժ���д�ֵĲ���
	  			    		 inspectElevatorTimes =cInfo.getInspectElevatorTimes();
	  			    		 inspectElevatorTimes2 =cInfo.getInspectElevatorTimes2();   
	  			    		 acceptInspElevatorTimes=cInfo.getAcceptInspElevatorTimes();  //���ܼල������
	  			    		 maintenSceneInfoTimes =cInfo.getMaintenSceneInfoTimes();
	  			    		 complaintsEventsTimes=cInfo.getComplaintsEventsTimes();
	  			    		 complaintsEventsTimes2=cInfo.getComplaintsEventsTimes2();
	  			    	     maintenBusinessTimes = cInfo.getMaintenBusinessTimes();
	  			    		 honestTimes =cInfo.getHonestTimes();
	  			    	     punishmentTimes=cInfo.getPunishmentTimes();
	  			    		 punishmentTimes2=cInfo.getPunishmentTimes2();
	  			    		 punishmentTimes3=cInfo.getPunishmentTimes3();
	  			    		 punishmentTimes4=cInfo.getPunishmentTimes4();
	  			    	}
	  			    //		pInfo = YwQuaCredRating.findFirstBySql(YwQuaCredRating.class, "select complaintsEventssj,maintenBusinesssj,honestsj,punishmentsj from TwoCodeYwQuaCredRating where ywCompanyID =?  and ratingDate = ? and ratingType = ?", new Object[]{info.getYwCompanyID(),info.getRatingDate(),0});
	  			    		pInfo.setInspectElevatorsj(100-5*inspectElevatorTimes-20*inspectElevatorTimes2);
	  			    		pInfo.setAcceptInspElevatorsj(0-10*acceptInspElevatorTimes);
	  			    		pInfo.setMaintenSceneInfosj(0-10*maintenSceneInfoTimes);
	  			    		pInfo.setComplaintsEventssj(100-5*complaintsEventsTimes-20*complaintsEventsTimes2);
	  			    		pInfo.setMaintenBusinesssj(0-20*maintenBusinessTimes);
	  			    		pInfo.setHonestsj(0-20*honestTimes);
	  			    		pInfo.setPunishmentsj(100-2*punishmentTimes-5*punishmentTimes2-20*punishmentTimes3-20*punishmentTimes4);
	  			    		
	  			    		
	  			    	//��������¼�Ĵ���
	  			    		pInfo.setInspectElevatorTimes(inspectElevatorTimes);
	  			    		pInfo.setInspectElevatorTimes2(inspectElevatorTimes2);
	  			    		pInfo.setAcceptInspElevatorTimes(acceptInspElevatorTimes);
	  			    		pInfo.setMaintenBusinessTimes(maintenBusinessTimes);
	  			    		pInfo.setComplaintsEventsTimes(complaintsEventsTimes);
	  			    		pInfo.setComplaintsEventsTimes2(complaintsEventsTimes2);
	  			    		pInfo.setMaintenBusinessTimes(maintenBusinessTimes);
	  			    		pInfo.setHonestTimes(honestTimes);
	  			    		pInfo.setPunishmentTimes(punishmentTimes);
	  			    		pInfo.setPunishmentTimes2(punishmentTimes2);
	  			    		pInfo.setPunishmentTimes3(punishmentTimes3);
	  			    		pInfo.setPunishmentTimes4(punishmentTimes4);
	  			    	   
	  			           //���������У����ʼ൥����ֵĲ��֣�û�е�����ֵģ����Բ���Ҫ������
	  			    	    if(pInfo.update()){
				    			result = "success";
				    		}	
  	    	            } 	     
 	               }
  	    	    }
  	        ActiveRecordBase.commit();	
  	       }
          catch (ActiveRecordException e) {
    	     try {
			     ActiveRecordBase.rollback();
		         } 
    	     catch (TransactionException e1) {
			      e1.printStackTrace();
		         }
	       }
	return result;
}
 	
 	public String ywSysSetingshyxh(int ywCompanyID,String ratingDate,YwQuaCredRatingHyxh info){
 		System.out.println("ywSysSetingshyxh");
 		System.out.println("ratingDate"+ratingDate);
 		String result = "failure";
 		int ratingDateMonthSpace = 0;
 		UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		int userid = userinfo.getId();
		int  role = userinfo.getRole();
 		int num = 0;
 		int num2 =0;
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM");
		java.text.DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String s = format1.format(new Date()); 
 		String s2 =format2.format(new Date()); 
		ratingDateMonthSpace =dogetMonthSpace(ratingDate,s); 
		if(ratingDateMonthSpace != 0){
			return "failure2";
		}
		UserExtInfo  info2 =null;
 		String userName ="";
 		CompanyInfoVO companyinfo = null;
 		int companyid = 0;
 		YwQuaCredRating pInfo = null;
 		YwQuaCredRatingVO cInfo = null;
 		
 		try {
			ActiveRecordBase.beginTransaction();
		
	    	info2 = UserExtInfo.findFirstBySql(UserExtInfo.class, "select userName,companyid from TwoCodeUserExtInfo where userid = ?", new Object[]{userid});
	    	if(info2 != null){
	    		userName = info2.getUserName();
	    	//	companyid = info2.getCompanyid();	//��ҵЭ��Ҫ�����ʼ��companyId
	    		companyinfo = CompanyInfoVO.findFirstBySql(CompanyInfoVO.class, "select id  from TwoCodeCompanyInfo where type = ?", new Object[]{"���ʼ�"});
	    		if(companyinfo != null){
	    			companyid = companyinfo.getId();
	    		}
	    		System.out.println("companyid----"+companyid);
	    	}
	    	YwQuaCredRatingVO vo =YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class, "select id from TwoCodeYwQuaCredRating where ratingCompanyId = ? and ratingDate = ? and ratingType = ? and  ywCompanyID = ? ", new Object[]{companyid,info.getRatingDate(),1,info.getYwCompanyID()});
	    	if(vo != null){   //�����ۼ�¼�͸���
	    		num = YwQuaCredRating.updateAll(YwQuaCredRating.class,"officeSpace =?,officeSpacesj=?,officeSpacebz=?,fixedTelOnDuty=?,fixedTelOnDutysj=?,fixedTelOnDutybz=?,telOnDutyunattendedTimes=?,telOnDutyunattendedsj=?,telOnDutyunattendedbz=?,enterpriseChangeTimes=?,enterpriseChangesj=?,enterpriseChangebz=?,enterpriseRecord=?,enterpriseRecordsj=?,enterpriseRecordbz=?,ratingUserName=?,detailratingDate=?",
						new Object[] { info.getOfficeSpace(),info.getOfficeSpacesj(),info.getOfficeSpacebz(),info.getFixedTelOnDuty(),info.getFixedTelOnDutysj(),info.getFixedTelOnDutybz(),info.getTelOnDutyunattendedTimes(),info.getTelOnDutyunattendedsj(),info.getTelOnDutyunattendedbz(),info.getEnterpriseChangeTimes(),info.getEnterpriseChangesj(),info.getEnterpriseChangebz(),info.getEnterpriseRecord(),info.getEnterpriseRecordsj(),info.getEnterpriseRecordbz(),userName,s2}, "ywCompanyID =? and ratingDate=? and ratingCompanyId = ? and ratingType = ? ",new Object[] {info.getYwCompanyID(),info.getRatingDate(),companyid,1}); 
	    		if(num > 0){   //�Ӽ�¼���³ɹ�����������¼
	    			 String sql= "";
	    			 pInfo = YwQuaCredRating.findFirstBySql(YwQuaCredRating.class, "select * from TwoCodeYwQuaCredRating where ywCompanyID =?  and ratingDate = ? and ratingType = ?", new Object[]{info.getYwCompanyID(),info.getRatingDate(),0});
	    			 if(pInfo != null){
	    				pInfo.setOfficeSpace(info.getOfficeSpace());
	 			        pInfo.setOfficeSpacesj(info.getOfficeSpacesj());
	 			    	pInfo.setOfficeSpacebz(info.getOfficeSpacebz());
	 			    	pInfo.setFixedTelOnDuty(info.getFixedTelOnDuty());
	 			    	pInfo.setFixedTelOnDutysj(info.getFixedTelOnDutysj());
	 			    	pInfo.setFixedTelOnDutybz(info.getFixedTelOnDutybz());
	 			    	pInfo.setTelOnDutyunattendedTimes(info.getTelOnDutyunattendedTimes());
	 			    	pInfo.setTelOnDutyunattendedsj(info.getTelOnDutyunattendedsj());
	 			    	pInfo.setTelOnDutyunattendedbz(info.getTelOnDutyunattendedbz());
	 			    	pInfo.setEnterpriseChangeTimes(info.getEnterpriseChangeTimes());
	 			    	pInfo.setEnterpriseChangesj(info.getEnterpriseChangesj());
	 			    	pInfo.setEnterpriseChangebz(info.getEnterpriseChangebz());
	 			    	pInfo.setEnterpriseRecord(info.getEnterpriseRecord());
	 			    	pInfo.setEnterpriseRecordsj(info.getEnterpriseRecordsj());
	 			    	pInfo.setEnterpriseRecordbz(info.getEnterpriseRecordbz());
	 			    	
	 			    	if(pInfo.update()){
 			    //			int unum = YwQuaCredRatingConstant.updateAll(YwQuaCredRatingConstant.class, "officeSpace =?,fixedTelOnDuty=?", new Object[]{info.getOfficeSpace(),info.getFixedTelOnDuty()}, "ywCompanyID = ? ", new Object[]{ywCompanyID} ); 
	 			    		int unum = YwQuaCredRatingConstant.updateAll(YwQuaCredRatingConstant.class, "officeSpace =?,officeSpacesj=?,officeSpacebz=?,fixedTelOnDuty=?,fixedTelOnDutysj=?,fixedTelOnDutybz=?", new Object[]{info.getOfficeSpace(),info.getOfficeSpacesj(),info.getOfficeSpacebz(),info.getFixedTelOnDuty(),info.getFixedTelOnDutysj(),info.getFixedTelOnDutybz()}, "ywCompanyID = ? ", new Object[]{ywCompanyID} ); 

	 			    		if( unum == 0){
	 			    				YwQuaCredRatingConstant yqcrc = new YwQuaCredRatingConstant();
	 			    				yqcrc.setYwCompanyID(ywCompanyID);
	 			    				yqcrc.setOfficeSpace(info.getOfficeSpace());
	 			    				yqcrc.setOfficeSpacesj(info.getOfficeSpacesj());
	 			    				yqcrc.setFixedTelOnDuty(info.getFixedTelOnDuty());
	 			    				yqcrc.setFixedTelOnDutysj(info.getFixedTelOnDutysj());
	 			    				
	 			    				yqcrc.setOfficeSpacebz(info.getOfficeSpacebz());
	 			    				yqcrc.setFixedTelOnDutybz(info.getFixedTelOnDutybz());	
	 			    				yqcrc.save();
	 			    			}
 			    			result = "success";
 			    		}	
	    			 }
	    		}
	    		
	    	}
	    	else{  //û�п����Ӽ�¼������
	    		info.setYwCompanyID(ywCompanyID);
 	    		info.setRatingDate(ratingDate);
 	    		info.setRatingType(1);
 	    		info.setRatingCompanyId(companyid);
 	    		info.setRatingUserName(userName);
 	    		boolean saveFlag = false;
 	    		saveFlag = info.save();
 	    		if(saveFlag){   
  	    		   //�Ӽ�¼���ӳɹ�����������¼
  	    	          pInfo = YwQuaCredRating.findFirstBySql(YwQuaCredRating.class, "select *  from TwoCodeYwQuaCredRating where ywCompanyID =?  and ratingDate = ? and ratingType = ?", new Object[]{info.getYwCompanyID(),info.getRatingDate(),0});
  	    			  if(pInfo != null){
  	    				 String sql= "";
  	    				    pInfo.setOfficeSpace(info.getOfficeSpace());
	 			    		pInfo.setOfficeSpacesj(info.getOfficeSpacesj());
	 			    		pInfo.setOfficeSpacebz(info.getOfficeSpacebz());
	 			    		pInfo.setFixedTelOnDuty(info.getFixedTelOnDuty());
	 			    		pInfo.setFixedTelOnDutysj(info.getFixedTelOnDutysj());
	 			    		pInfo.setFixedTelOnDutybz(info.getFixedTelOnDutybz());
	 			    		pInfo.setTelOnDutyunattendedTimes(info.getTelOnDutyunattendedTimes());
	 			    		pInfo.setTelOnDutyunattendedsj(info.getTelOnDutyunattendedsj());
	 			    		pInfo.setTelOnDutyunattendedbz(info.getTelOnDutyunattendedbz());
	 			    		pInfo.setEnterpriseChangeTimes(info.getEnterpriseChangeTimes());
	 			    		pInfo.setEnterpriseChangesj(info.getEnterpriseChangesj());
	 			    		pInfo.setEnterpriseChangebz(info.getEnterpriseChangebz());
	 			    		pInfo.setEnterpriseRecord(info.getEnterpriseRecord());
	 			    		pInfo.setEnterpriseRecordsj(info.getEnterpriseRecordsj());
	 			    		pInfo.setEnterpriseRecordbz(info.getEnterpriseRecordbz());
	 			    		if(pInfo.update()){ //���ּ�¼���³ɹ����������ֳ���
 	 			    	//		int unum = YwQuaCredRatingConstant.updateAll(YwQuaCredRatingConstant.class, "officeSpace =?,fixedTelOnDuty=?", new Object[]{info.getOfficeSpace(),info.getFixedTelOnDuty()}, "ywCompanyID = ? ", new Object[]{ywCompanyID} ); 
	 			    			int unum = YwQuaCredRatingConstant.updateAll(YwQuaCredRatingConstant.class, "officeSpace =?,officeSpacesj=?,officeSpacebz=?,fixedTelOnDuty=?,fixedTelOnDutysj=?,fixedTelOnDutybz=?", new Object[]{info.getOfficeSpace(),info.getOfficeSpacesj(),info.getOfficeSpacebz(),info.getFixedTelOnDuty(),info.getFixedTelOnDutysj(),info.getFixedTelOnDutybz()}, "ywCompanyID = ? ", new Object[]{ywCompanyID} ); 

 	 			    			if( unum == 0){
 	 			    				YwQuaCredRatingConstant yqcrc = new YwQuaCredRatingConstant();
 	 			    				yqcrc.setYwCompanyID(ywCompanyID);
 	 			    				yqcrc.setOfficeSpace(info.getOfficeSpace());
 	 			    				yqcrc.setOfficeSpacesj(info.getOfficeSpacesj());
	 			    				yqcrc.setFixedTelOnDuty(info.getFixedTelOnDuty());
	 			    				yqcrc.setFixedTelOnDutysj(info.getFixedTelOnDutysj());
	 			    				
	 			    				yqcrc.setOfficeSpacebz(info.getOfficeSpacebz());
	 			    				yqcrc.setFixedTelOnDutybz(info.getFixedTelOnDutybz());
 	 			    				yqcrc.save();
 	 			    			}
 	 			    			result = "success";
 	 			    		}	
  	    			  }
  	    			  }
	    	}
	    	 ActiveRecordBase.commit();		
 		} catch (ActiveRecordException e) {
 			try {
				ActiveRecordBase.rollback();
			} catch (TransactionException e1) {
				e1.printStackTrace();
			}
		}
 		return result;
 	}
 	
 	public String ywSysSetings(int ywCompanyID,String ratingDate,YwQuaCredRating info){
 		System.out.println("ywSysSetings");
 		String result = "failure";
 		int ratingDateMonthSpace = 0;
 		UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		int userid = userinfo.getId();
	//	String userName =userinfo.getLoginName();
		int  role = userinfo.getRole();
 		int num = 0;
 		int num2 =0;
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM");
		java.text.DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String s = format1.format(new Date()); 
 		String s2 =format2.format(new Date()); 
		ratingDateMonthSpace =dogetMonthSpace(ratingDate,s); 
		if(ratingDateMonthSpace != 0){
			return "failure2";
		}
 		UserExtInfo  info2 =null;
 		String userName ="";
 		int companyid = 0;
 		YwQuaCredRating pInfo = null;
 		YwQuaCredRatingVO cInfo = null;
 	//	boolean saveFlag =false;
 		
 	     try {
 	    	ActiveRecordBase.beginTransaction();
 	    	info2 = UserExtInfo.findFirstBySql(UserExtInfo.class, "select userName,companyid from TwoCodeUserExtInfo where userid = ?", new Object[]{userid});
 	    	if(info2 != null){
 	    		userName = info2.getUserName();
 	    		companyid = info2.getCompanyid();	
 	    		System.out.println("companyid----"+companyid);
 	    	}
 	    	
 	    	YwQuaCredRatingVO vo =YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class, "select id from TwoCodeYwQuaCredRating where ratingCompanyId = ? and ratingDate = ? and ratingType = ? and  ywCompanyID = ? ", new Object[]{companyid,info.getRatingDate(),1,info.getYwCompanyID()});
 	    	if(vo != null){  //�����ۼ�¼�͸���
 	    	/*	num = YwQuaCredRating.updateAll(YwQuaCredRating.class,"officeSpace =?,officeSpacesj=?,officeSpacebz=?,headQuarters=?,headQuarterssj=?,headQuartersbz=?,fixedTelOnDuty=?,fixedTelOnDutysj=?,fixedTelOnDutybz=?,telOnDutyunattendedTimes=?,telOnDutyunattendedsj=?,telOnDutyunattendedbz=?,enterpriseChangeTimes=?,enterpriseChangesj=?,enterpriseChangebz=?,enterpriseRecord=?,enterpriseRecordsj=?,enterpriseRecordbz=?,regularInspectionTimes=?,regularInspectionsj=?,regularInspectionbz=?,inspectElevatorTimes=?,inspectElevatorTimes2=?,inspectElevatorsj=?,inspectElevatorbz=?,acceptInspElevatorTimes=?,acceptInspElevatorsj=?,acceptInspElevatorbz=?,maintenSceneInfoTimes=?,maintenSceneInfosj=?,maintenSceneInfobz=?,malignantEventsTimes=?,malignantEventsTimes2=?,malignantEventsTimes3=?,malignantEventssj=?,malignantEventsbz=?,complaintsEventsTimes=?,complaintsEventsTimes2=?,complaintsEventssj=?,complaintsEventsbz=?,maintenBusinessTimes=?,maintenBusinesssj=?,maintenBusinessbz=?,honestTimes=?,honestsj=?,honestbz=?,punishmentTimes=?,punishmentTimes2=?,punishmentTimes3=?,punishmentTimes4=?,punishmentsj=?,punishmentbz=?,firstRescueTimes=?,firstRescuesj=?,firstRescuebz=?,secondRescueTimes=?,secondRescueTimes2=?,secondRescuesj=?,secondRescuebz=?,secondRescuePoint=?,secondRescuePointsj=?,secondRescuePointbz=?,rescueResponseTimes=?,rescueResponsesj=?,rescueResponsebz=?,tiringPeopleTimes=?,tiringPeoplesj=?,tiringPeoplebz=?,positiveEnergyTimes=?,positiveEnergysj=?,positiveEnergybz=?,expertsSuggestionTimes=?,expertsSuggestionsj=?,expertsSuggestionbz=?,positiveWork=?,positiveWorksj=?,positiveWorkbz=?,remoteMonitor=?,remoteMonitorsj=?,remoteMonitorbz=?,elevatorInsurance=?,elevatorInsurancesj=?,elevatorInsurancebz=?,techinnovationTimes=?,techinnovationTimes2=?,techinnovationTimes3=?,techinnovationTimes4=?,techinnovationTimes5=?,techinnovationsj=?,techinnovationbz=?,ratingUserName=?,detailratingDate=?",
 						new Object[] { info.getOfficeSpace(),info.getOfficeSpacesj(),info.getOfficeSpacebz(),info.getHeadQuarters(),info.getHeadQuarterssj(),info.getHeadQuartersbz(),info.getFixedTelOnDuty(),info.getFixedTelOnDutysj(),info.getFixedTelOnDutybz(),info.getTelOnDutyunattendedTimes(),info.getTelOnDutyunattendedsj(),info.getTelOnDutyunattendedbz(),info.getEnterpriseChangeTimes(),info.getEnterpriseChangesj(),info.getEnterpriseChangebz(),info.getEnterpriseRecord(),info.getEnterpriseRecordsj(),info.getEnterpriseRecordbz(),info.getRegularInspectionTimes(),info.getRegularInspectionsj(),info.getRegularInspectionbz(),info.getInspectElevatorTimes(),info.getInspectElevatorTimes2(),info.getInspectElevatorsj(),info.getInspectElevatorbz(),info.getAcceptInspElevatorTimes(),info.getAcceptInspElevatorsj(),info.getAcceptInspElevatorbz(),info.getMaintenSceneInfoTimes(),info.getMaintenSceneInfosj(),info.getMaintenSceneInfobz(),info.getMalignantEventsTimes(),info.getMalignantEventsTimes2(),info.getMalignantEventsTimes3(),info.getMalignantEventssj(),info.getMalignantEventsbz(),info.getComplaintsEventsTimes(),info.getComplaintsEventsTimes2(),info.getComplaintsEventssj(),info.getComplaintsEventsbz(),info.getMaintenBusinessTimes(),info.getMaintenBusinesssj(),info.getMaintenBusinessbz(),info.getHonestTimes(),info.getHonestsj(),info.getHonestbz(),info.getPunishmentTimes(),info.getPunishmentTimes2(),info.getPunishmentTimes3(),info.getPunishmentTimes4(),info.getPunishmentsj(),info.getPunishmentbz(),info.getFirstRescueTimes(),info.getFirstRescuesj(),info.getFirstRescuebz(),info.getSecondRescueTimes(),info.getSecondRescueTimes2(),info.getSecondRescuesj(),info.getSecondRescuebz(),info.getSecondRescuePoint(),info.getSecondRescuePointsj(),info.getSecondRescuePointbz(),info.getRescueResponseTimes(),info.getRescueResponsesj(),info.getRescueResponsebz(),info.getTiringPeopleTimes(),info.getTiringPeoplesj(),info.getTiringPeoplebz(),info.getPositiveEnergyTimes(),info.getPositiveEnergysj(),info.getPositiveEnergybz(),info.getExpertsSuggestionTimes(),info.getExpertsSuggestionsj(),info.getExpertsSuggestionbz(),info.getPositiveWork(),info.getPositiveWorksj(),info.getPositiveWorkbz(),info.getRemoteMonitor(),info.getRemoteMonitorsj(),info.getRemoteMonitorbz(),info.getElevatorInsurance(),info.getElevatorInsurancesj(),info.getElevatorInsurancebz(),info.getTechinnovationTimes(),info.getTechinnovationTimes2(),info.getTechinnovationTimes3(),info.getTechinnovationTimes4(),info.getTechinnovationTimes5(),info.getTechinnovationsj(),info.getTechinnovationbz(),userName,s2}, "ywCompanyID =? and ratingDate=? and ratingCompanyId = ? and ratingType = ? ",new Object[] {info.getYwCompanyID(),info.getRatingDate(),companyid,1}); */
 	    		num = YwQuaCredRating.updateAll(YwQuaCredRating.class,"officeSpace =?,officeSpacesj=?,officeSpacebz=?,fixedTelOnDuty=?,fixedTelOnDutysj=?,fixedTelOnDutybz=?,telOnDutyunattendedTimes=?,telOnDutyunattendedsj=?,telOnDutyunattendedbz=?,enterpriseChangeTimes=?,enterpriseChangesj=?,enterpriseChangebz=?,enterpriseRecord=?,enterpriseRecordsj=?,enterpriseRecordbz=?,regularInspectionTimes=?,regularInspectionsj=?,regularInspectionbz=?,inspectElevatorTimes=?,inspectElevatorTimes2=?,inspectElevatorsj=?,inspectElevatorbz=?,acceptInspElevatorTimes=?,acceptInspElevatorsj=?,acceptInspElevatorbz=?,maintenSceneInfoTimes=?,maintenSceneInfosj=?,maintenSceneInfobz=?,malignantEventsTimes=?,malignantEventsTimes2=?,malignantEventsTimes3=?,malignantEventssj=?,malignantEventsbz=?,complaintsEventsTimes=?,complaintsEventsTimes2=?,complaintsEventssj=?,complaintsEventsbz=?,maintenBusinessTimes=?,maintenBusinesssj=?,maintenBusinessbz=?,honestTimes=?,honestsj=?,honestbz=?,punishmentTimes=?,punishmentTimes2=?,punishmentTimes3=?,punishmentTimes4=?,punishmentsj=?,punishmentbz=?,firstRescueTimes=?,firstRescuesj=?,firstRescuebz=?,secondRescueTimes=?,secondRescueTimes2=?,secondRescuesj=?,secondRescuebz=?,secondRescuePoint=?,secondRescuePointsj=?,secondRescuePointbz=?,rescueResponseTimes=?,rescueResponsesj=?,rescueResponsebz=?,tiringPeopleTimes=?,tiringPeoplesj=?,tiringPeoplebz=?,positiveEnergyTimes=?,positiveEnergysj=?,positiveEnergybz=?,expertsSuggestionTimes=?,expertsSuggestionsj=?,expertsSuggestionbz=?,positiveWork=?,positiveWorksj=?,positiveWorkbz=?,remoteMonitor=?,remoteMonitorsj=?,remoteMonitorbz=?,elevatorInsurance=?,elevatorInsurancesj=?,elevatorInsurancebz=?,techinnovationTimes=?,techinnovationTimes2=?,techinnovationTimes3=?,techinnovationTimes4=?,techinnovationTimes5=?,techinnovationsj=?,techinnovationbz=?,ratingUserName=?,detailratingDate=?",
					new Object[] { info.getOfficeSpace(),info.getOfficeSpacesj(),info.getOfficeSpacebz(),info.getFixedTelOnDuty(),info.getFixedTelOnDutysj(),info.getFixedTelOnDutybz(),info.getTelOnDutyunattendedTimes(),info.getTelOnDutyunattendedsj(),info.getTelOnDutyunattendedbz(),info.getEnterpriseChangeTimes(),info.getEnterpriseChangesj(),info.getEnterpriseChangebz(),info.getEnterpriseRecord(),info.getEnterpriseRecordsj(),info.getEnterpriseRecordbz(),info.getRegularInspectionTimes(),info.getRegularInspectionsj(),info.getRegularInspectionbz(),info.getInspectElevatorTimes(),info.getInspectElevatorTimes2(),info.getInspectElevatorsj(),info.getInspectElevatorbz(),info.getAcceptInspElevatorTimes(),info.getAcceptInspElevatorsj(),info.getAcceptInspElevatorbz(),info.getMaintenSceneInfoTimes(),info.getMaintenSceneInfosj(),info.getMaintenSceneInfobz(),info.getMalignantEventsTimes(),info.getMalignantEventsTimes2(),info.getMalignantEventsTimes3(),info.getMalignantEventssj(),info.getMalignantEventsbz(),info.getComplaintsEventsTimes(),info.getComplaintsEventsTimes2(),info.getComplaintsEventssj(),info.getComplaintsEventsbz(),info.getMaintenBusinessTimes(),info.getMaintenBusinesssj(),info.getMaintenBusinessbz(),info.getHonestTimes(),info.getHonestsj(),info.getHonestbz(),info.getPunishmentTimes(),info.getPunishmentTimes2(),info.getPunishmentTimes3(),info.getPunishmentTimes4(),info.getPunishmentsj(),info.getPunishmentbz(),info.getFirstRescueTimes(),info.getFirstRescuesj(),info.getFirstRescuebz(),info.getSecondRescueTimes(),info.getSecondRescueTimes2(),info.getSecondRescuesj(),info.getSecondRescuebz(),info.getSecondRescuePoint(),info.getSecondRescuePointsj(),info.getSecondRescuePointbz(),info.getRescueResponseTimes(),info.getRescueResponsesj(),info.getRescueResponsebz(),info.getTiringPeopleTimes(),info.getTiringPeoplesj(),info.getTiringPeoplebz(),info.getPositiveEnergyTimes(),info.getPositiveEnergysj(),info.getPositiveEnergybz(),info.getExpertsSuggestionTimes(),info.getExpertsSuggestionsj(),info.getExpertsSuggestionbz(),info.getPositiveWork(),info.getPositiveWorksj(),info.getPositiveWorkbz(),info.getRemoteMonitor(),info.getRemoteMonitorsj(),info.getRemoteMonitorbz(),info.getElevatorInsurance(),info.getElevatorInsurancesj(),info.getElevatorInsurancebz(),info.getTechinnovationTimes(),info.getTechinnovationTimes2(),info.getTechinnovationTimes3(),info.getTechinnovationTimes4(),info.getTechinnovationTimes5(),info.getTechinnovationsj(),info.getTechinnovationbz(),userName,s2}, "ywCompanyID =? and ratingDate=? and ratingCompanyId = ? and ratingType = ? ",new Object[] {info.getYwCompanyID(),info.getRatingDate(),companyid,1}); 
		  
 	    		if(num > 0){   //�Ӽ�¼���³ɹ�����������¼	
 			      String sql= "";
 			// 	   pInfo = YwQuaCredRating.findFirstBySql(YwQuaCredRating.class, "select complaintsEventssj,maintenBusinesssj,honestsj,punishmentsj from TwoCodeYwQuaCredRating where ywCompanyID =?  and ratingDate = ? and ratingType = ?", new Object[]{info.getYwCompanyID(),info.getRatingDate(),0});
 			       pInfo = YwQuaCredRating.findFirstBySql(YwQuaCredRating.class, "select * from TwoCodeYwQuaCredRating where ywCompanyID =?  and ratingDate = ? and ratingType = ?", new Object[]{info.getYwCompanyID(),info.getRatingDate(),0});
			      if(pInfo != null){
 			      if(role == 22 || role ==23){ //���ʼ� 
 			    	 //��ͬ��ֵĲ���ȡ�Ӽ�¼֮��
 			        sql ="select sum(complaintsEventsTimes) as complaintsEventsTimes,sum(complaintsEventsTimes2) as complaintsEventsTimes2,sum(maintenBusinessTimes) as maintenBusinessTimes,sum(honestTimes) as honestTimes,sum(punishmentTimes) as punishmentTimes,sum(punishmentTimes2) as punishmentTimes2,sum(punishmentTimes3) as punishmentTimes3,sum(punishmentTimes4) as punishmentTimes4 from TwoCodeYwQuaCredRating where ywCompanyID =?   and ratingDate = ? and ratingType = ?";
 			        cInfo =YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class, sql, new Object[]{info.getYwCompanyID(),info.getRatingDate(),1});
 			        int complaintsEventsTimes=0;
		    		int complaintsEventsTimes2=0;
		    		int maintenBusinessTimes = 0;
		    		int honestTimes =0;
		    		int punishmentTimes=0;
		    		int punishmentTimes2=0;
		    		int punishmentTimes3=0;
		    		int punishmentTimes4=0;
 			        if(cInfo != null){	
 			    		//�����������ʼ๲�д�ֵĲ���
 			    		complaintsEventsTimes=cInfo.getComplaintsEventsTimes();
 			    		complaintsEventsTimes2=cInfo.getComplaintsEventsTimes2();
 			    		maintenBusinessTimes = cInfo.getMaintenBusinessTimes();
 			    		honestTimes =cInfo.getHonestTimes();
 			    		punishmentTimes=cInfo.getPunishmentTimes();
 			    		punishmentTimes2=cInfo.getPunishmentTimes2();
 			    		punishmentTimes3=cInfo.getPunishmentTimes3();
 			    		punishmentTimes4=cInfo.getPunishmentTimes4();
 			        }
 			    		pInfo.setComplaintsEventssj(100-5*complaintsEventsTimes-20*complaintsEventsTimes2);
 			    		pInfo.setMaintenBusinesssj(0-20*maintenBusinessTimes);
 			    		pInfo.setHonestsj(0-20*honestTimes);
 			    		pInfo.setPunishmentsj(100-2*punishmentTimes-5*punishmentTimes2-20*punishmentTimes3-20*punishmentTimes4);
 			    		
 			    		//��������¼�Ĵ���
 			    		pInfo.setComplaintsEventsTimes(complaintsEventsTimes);
 			    		pInfo.setComplaintsEventsTimes2(complaintsEventsTimes2);
 			    	    pInfo.setMaintenBusinessTimes(maintenBusinessTimes);
 			    		pInfo.setHonestTimes(honestTimes);
 			    		pInfo.setPunishmentTimes(punishmentTimes);
 			    		pInfo.setPunishmentTimes2(punishmentTimes2);
 			    		pInfo.setPunishmentTimes3(punishmentTimes3);
 			    		pInfo.setPunishmentTimes4(punishmentTimes4);
 			    		
 			    		//�������ʼ൥����ֵĲ���
 			    		pInfo.setOfficeSpace(info.getOfficeSpace());
 			    		pInfo.setOfficeSpacesj(info.getOfficeSpacesj());
 			    		pInfo.setOfficeSpacebz(info.getOfficeSpacebz());
 			    	//	pInfo.setHeadQuarters(info.getHeadQuarters());
 			    	//	pInfo.setHeadQuarterssj(info.getHeadQuarterssj());
 			    		pInfo.setFixedTelOnDuty(info.getFixedTelOnDuty());
 			    		pInfo.setFixedTelOnDutysj(info.getFixedTelOnDutysj());
 			    		pInfo.setFixedTelOnDutybz(info.getFixedTelOnDutybz());
 			    		pInfo.setTelOnDutyunattendedTimes(info.getTelOnDutyunattendedTimes());
 			    		pInfo.setTelOnDutyunattendedsj(info.getTelOnDutyunattendedsj());
 			    		pInfo.setTelOnDutyunattendedbz(info.getTelOnDutyunattendedbz());
 			    		pInfo.setEnterpriseChangeTimes(info.getEnterpriseChangeTimes());
 			    		pInfo.setEnterpriseChangesj(info.getEnterpriseChangesj());
 			    		pInfo.setEnterpriseChangebz(info.getEnterpriseChangebz());
 			    		pInfo.setEnterpriseRecord(info.getEnterpriseRecord());
 			    		pInfo.setEnterpriseRecordsj(info.getEnterpriseRecordsj());
 			    		pInfo.setEnterpriseRecordbz(info.getEnterpriseRecordbz());// malignantEventsTimes
 			    		pInfo.setMalignantEventsTimes(info.getMalignantEventsTimes());
 			    		pInfo.setMalignantEventsTimes2(info.getMalignantEventsTimes2());
 			    		pInfo.setMalignantEventsTimes3(info.getMalignantEventsTimes3());
 			    		pInfo.setMalignantEventssj(info.getMalignantEventssj());
 			    		pInfo.setMalignantEventsbz(info.getMalignantEventsbz());
 			    		pInfo.setSecondRescuePoint(info.getSecondRescuePoint());
 			    		pInfo.setSecondRescuePointsj(info.getSecondRescuePointsj());
 			    		pInfo.setSecondRescuePointbz(info.getSecondRescuePointbz());
 			    		pInfo.setPositiveEnergyTimes(info.getPositiveEnergyTimes());
 			    		pInfo.setPositiveEnergysj(info.getPositiveEnergysj());
 			    		pInfo.setPositiveEnergybz(info.getPositiveEnergybz());
 			    		pInfo.setExpertsSuggestionTimes(info.getExpertsSuggestionTimes());
 			    		pInfo.setExpertsSuggestionsj(info.getExpertsSuggestionsj());
 			    		pInfo.setExpertsSuggestionbz(info.getExpertsSuggestionbz());
 			    		pInfo.setPositiveWork(info.getPositiveWork());
 			    		pInfo.setPositiveWorksj(info.getPositiveWorksj());
 			    		pInfo.setPositiveWorkbz(info.getPositiveWorkbz());
 			    		pInfo.setRemoteMonitor(info.getRemoteMonitor());
 			    		pInfo.setRemoteMonitorsj(info.getRemoteMonitorsj());
 			    		pInfo.setRemoteMonitorbz(info.getRemoteMonitorbz());
 			    		pInfo.setElevatorInsurance(info.getElevatorInsurance());
 			    		pInfo.setElevatorInsurancesj(info.getElevatorInsurancesj());
 			    		pInfo.setElevatorInsurancebz(info.getElevatorInsurancebz());
 			    		pInfo.setTechinnovationTimes(info.getTechinnovationTimes());
 			    		pInfo.setTechinnovationTimes2(info.getTechinnovationTimes2());
 			    		pInfo.setTechinnovationTimes3(info.getTechinnovationTimes3());
 			    		pInfo.setTechinnovationTimes4(info.getTechinnovationTimes4());
 			    		pInfo.setTechinnovationTimes5(info.getTechinnovationTimes5());
 			    		pInfo.setTechinnovationsj(info.getTechinnovationsj());
 			    		pInfo.setTechinnovationbz(info.getTechinnovationbz());
 			    		if(pInfo.update()){
 			    			int unum = YwQuaCredRatingConstant.updateAll(YwQuaCredRatingConstant.class, "officeSpace =?,officeSpacesj=?,officeSpacebz=?,fixedTelOnDuty=?,fixedTelOnDutysj=?,fixedTelOnDutybz=?", new Object[]{info.getOfficeSpace(),info.getOfficeSpacesj(),info.getOfficeSpacebz(),info.getFixedTelOnDuty(),info.getFixedTelOnDutysj(),info.getFixedTelOnDutybz()}, "ywCompanyID = ? ", new Object[]{ywCompanyID} ); 
	 			    		if( unum == 0){
	 			    				YwQuaCredRatingConstant yqcrc = new YwQuaCredRatingConstant();
	 			    				yqcrc.setYwCompanyID(ywCompanyID);
	 			    				yqcrc.setOfficeSpace(info.getOfficeSpace());
	 			    				yqcrc.setOfficeSpacesj(info.getOfficeSpacesj());
	 			    				yqcrc.setFixedTelOnDuty(info.getFixedTelOnDuty());
	 			    				yqcrc.setFixedTelOnDutysj(info.getFixedTelOnDutysj());
	 			    				
	 			    				yqcrc.setOfficeSpacebz(info.getOfficeSpacebz());
	 			    				yqcrc.setFixedTelOnDutybz(info.getFixedTelOnDutybz());
	 			    				yqcrc.save();
	 			    			}
 			    			result = "success";
 			    		}	
 			    	}
 			     if(role == 10 || role ==11){ //��(��)���ʼ� 
 			    	//��ͬ��ֵĲ���ȡ�Ӽ�¼֮��
  			        sql ="select sum(inspectElevatorTimes) as inspectElevatorTimes,sum(inspectElevatorTimes2) as inspectElevatorTimes2,sum(acceptInspElevatorTimes) as acceptInspElevatorTimes,sum(maintenSceneInfoTimes) as maintenSceneInfoTimes,sum(complaintsEventsTimes) as complaintsEventsTimes,sum(complaintsEventsTimes2) as complaintsEventsTimes2,sum(maintenBusinessTimes) as maintenBusinessTimes,sum(honestTimes) as honestTimes,sum(punishmentTimes) as punishmentTimes,sum(punishmentTimes2) as punishmentTimes2,sum(punishmentTimes3) as punishmentTimes3,sum(punishmentTimes4) as punishmentTimes4 from TwoCodeYwQuaCredRating where ywCompanyID =?   and ratingDate = ? and ratingType = ?";
  			        cInfo =YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class, sql, new Object[]{info.getYwCompanyID(),info.getRatingDate(),1});
  			        int inspectElevatorTimes =0;
		    		int inspectElevatorTimes2 =0;   
		    		int acceptInspElevatorTimes=0;  //���ܼල������
		    		int maintenSceneInfoTimes =0;
		    		int complaintsEventsTimes=0;
		    		int complaintsEventsTimes2=0;
		    		int maintenBusinessTimes = 0;
		    		int honestTimes =0;
		    		int punishmentTimes=0;
		    		int punishmentTimes2=0;
		    		int punishmentTimes3=0;
		    		int punishmentTimes4=0;
  			        if(cInfo != null){	
  			    		//���������ʼ�,�����ؼ�Ժ���д�ֵĲ���
  			    		inspectElevatorTimes =cInfo.getInspectElevatorTimes();
  			    		inspectElevatorTimes2 =cInfo.getInspectElevatorTimes2();   
  			    		acceptInspElevatorTimes=cInfo.getAcceptInspElevatorTimes();  //���ܼල������
  			    		maintenSceneInfoTimes =cInfo.getMaintenSceneInfoTimes();
  			    		complaintsEventsTimes=cInfo.getComplaintsEventsTimes();
  			    		complaintsEventsTimes2=cInfo.getComplaintsEventsTimes2();
  			    		maintenBusinessTimes = cInfo.getMaintenBusinessTimes();
  			    		honestTimes =cInfo.getHonestTimes();
  			    		punishmentTimes=cInfo.getPunishmentTimes();
  			    		punishmentTimes2=cInfo.getPunishmentTimes2();
  			    		punishmentTimes3=cInfo.getPunishmentTimes3();
  			    		punishmentTimes4=cInfo.getPunishmentTimes4();
  			    	}
  			    //		pInfo = YwQuaCredRating.findFirstBySql(YwQuaCredRating.class, "select complaintsEventssj,maintenBusinesssj,honestsj,punishmentsj from TwoCodeYwQuaCredRating where ywCompanyID =?  and ratingDate = ? and ratingType = ?", new Object[]{info.getYwCompanyID(),info.getRatingDate(),0});
  			    		pInfo.setInspectElevatorsj(100-5*inspectElevatorTimes-20*inspectElevatorTimes2);
  			    		pInfo.setAcceptInspElevatorsj(0-10*acceptInspElevatorTimes);
  			    		pInfo.setMaintenSceneInfosj(0-10*maintenSceneInfoTimes);
  			    		pInfo.setComplaintsEventssj(100-5*complaintsEventsTimes-20*complaintsEventsTimes2);
  			    		pInfo.setMaintenBusinesssj(0-20*maintenBusinessTimes);
  			    		pInfo.setHonestsj(0-20*honestTimes);
  			    		pInfo.setPunishmentsj(100-2*punishmentTimes-5*punishmentTimes2-20*punishmentTimes3-20*punishmentTimes4);
  			    		
  			    	//��������¼�Ĵ���
  			    		pInfo.setInspectElevatorTimes(inspectElevatorTimes);
  			    		pInfo.setInspectElevatorTimes2(inspectElevatorTimes2);
  			    		pInfo.setAcceptInspElevatorTimes(acceptInspElevatorTimes);
  			    		pInfo.setMaintenBusinessTimes(maintenBusinessTimes);
  			    		pInfo.setComplaintsEventsTimes(complaintsEventsTimes);
  			    		pInfo.setComplaintsEventsTimes2(complaintsEventsTimes2);
  			    		pInfo.setMaintenBusinessTimes(maintenBusinessTimes);
  			    		pInfo.setHonestTimes(honestTimes);
  			    		pInfo.setPunishmentTimes(punishmentTimes);
  			    		pInfo.setPunishmentTimes2(punishmentTimes2);
  			    		pInfo.setPunishmentTimes3(punishmentTimes3);
  			    		pInfo.setPunishmentTimes4(punishmentTimes4);
  			    	   
  			           //���������У����ʼ൥����ֵĲ��֣�û�е�����ֵģ����Բ���Ҫ������
  			    	    if(pInfo.update()){
			    			result = "success";
			    		}	
 			    	 
 			     }
 			     
 			    if(role == 16 || role ==17){  //�ؼ�Ժ
 			    	sql ="select sum(inspectElevatorTimes) as inspectElevatorTimes,sum(inspectElevatorTimes2) as inspectElevatorTimes2,sum(acceptInspElevatorTimes) as acceptInspElevatorTimes,sum(maintenSceneInfoTimes) as maintenSceneInfoTimes from TwoCodeYwQuaCredRating where ywCompanyID =?   and ratingDate = ? and ratingType = ?";
   			        cInfo =YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class, sql, new Object[]{info.getYwCompanyID(),info.getRatingDate(),1});
   			        int inspectElevatorTimes =0;
		    		int inspectElevatorTimes2 =0;   
		    		int acceptInspElevatorTimes=0;  //���ܼල������
		    		int maintenSceneInfoTimes =0;
		    		int complaintsEventsTimes=0;
		    		int complaintsEventsTimes2=0;
		    		int maintenBusinessTimes = 0;
   			        if(cInfo != null){	
   			    		//�������������ʼ๲�д�ֵĲ���
   			    		inspectElevatorTimes =cInfo.getInspectElevatorTimes();
   			    		inspectElevatorTimes2 =cInfo.getInspectElevatorTimes2();   
   			    		acceptInspElevatorTimes=cInfo.getAcceptInspElevatorTimes();  //���ܼල������
   			    		maintenSceneInfoTimes =cInfo.getMaintenSceneInfoTimes();
   			    		complaintsEventsTimes=cInfo.getComplaintsEventsTimes();
   			    		complaintsEventsTimes2=cInfo.getComplaintsEventsTimes2();
   			    		maintenBusinessTimes = cInfo.getMaintenBusinessTimes();
   			         }	
   			    		
   			  //  		pInfo = YwQuaCredRating.findFirstBySql(YwQuaCredRating.class, "select complaintsEventssj,maintenBusinesssj,honestsj,punishmentsj from TwoCodeYwQuaCredRating where ywCompanyID =?  and ratingDate = ? and ratingType = ?", new Object[]{info.getYwCompanyID(),info.getRatingDate(),0});
   			    		pInfo.setInspectElevatorsj(100-5*inspectElevatorTimes-20*inspectElevatorTimes2);
   			    		pInfo.setAcceptInspElevatorsj(0-10*acceptInspElevatorTimes);
   			    		pInfo.setMaintenSceneInfosj(0-10*maintenSceneInfoTimes);
   			    		pInfo.setComplaintsEventssj(100-5*complaintsEventsTimes-20*complaintsEventsTimes2);
   			    		pInfo.setMaintenBusinesssj(0-20*maintenBusinessTimes);
   			    		
   			    	//��������¼�Ĵ���
   			    		pInfo.setInspectElevatorTimes(inspectElevatorTimes);
   			    		pInfo.setInspectElevatorTimes2(inspectElevatorTimes2);
   			    		pInfo.setAcceptInspElevatorTimes(acceptInspElevatorTimes);
   			    		pInfo.setMaintenSceneInfoTimes(maintenSceneInfoTimes);
   			    		pInfo.setComplaintsEventsTimes(complaintsEventsTimes);
   			    		pInfo.setComplaintsEventsTimes2(complaintsEventsTimes2);
   			    		pInfo.setMaintenBusinessTimes(maintenBusinessTimes);
   			    	   
   			           //�ؼ��鵥����ֵĲ���
   			    	    pInfo.setRegularInspectionTimes(info.getRegularInspectionTimes());
   			    	    pInfo.setRegularInspectionsj(info.getRegularInspectionsj());
   			    	    pInfo.setRegularInspectionbz(info.getRegularInspectionbz());
   			    	    
   			    	   if(pInfo.update()){
			    			result = "success";
			    		}	
  			    	 
 			    }
 			    }
 	    	}
 	    	}
 	    	else{  //û�п����Ӽ�¼������
 	    		info.setYwCompanyID(ywCompanyID);
 	    		info.setRatingDate(ratingDate);
 	    		info.setRatingType(1);
 	    		info.setRatingCompanyId(companyid);
 	    		info.setRatingUserName(userName);
 	    		boolean saveFlag = false;
 	    		saveFlag = info.save();
 	    		if(saveFlag){   
 	    		   //�Ӽ�¼���ӳɹ�����������¼
 	    	//		  pInfo = YwQuaCredRating.findFirstBySql(YwQuaCredRating.class, "select complaintsEventssj,maintenBusinesssj,honestsj,punishmentsj from TwoCodeYwQuaCredRating where ywCompanyID =?  and ratingDate = ? and ratingType = ?", new Object[]{info.getYwCompanyID(),info.getRatingDate(),0});
 	    			pInfo = YwQuaCredRating.findFirstBySql(YwQuaCredRating.class, "select *  from TwoCodeYwQuaCredRating where ywCompanyID =?  and ratingDate = ? and ratingType = ?", new Object[]{info.getYwCompanyID(),info.getRatingDate(),0});
 	    			  if(pInfo != null){	
 	 			      String sql= "";
 	 			      if(role == 22 || role ==23){ //���ʼ� 
 	 			    	  
 	 			    	 //��ͬ��ֵĲ���ȡ�Ӽ�¼֮��
 	 			        sql ="select sum(complaintsEventsTimes) as complaintsEventsTimes,sum(complaintsEventsTimes2) as complaintsEventsTimes2,sum(maintenBusinessTimes) as maintenBusinessTimes,sum(honestTimes) as honestTimes,sum(punishmentTimes) as punishmentTimes,sum(punishmentTimes2) as punishmentTimes2,sum(punishmentTimes3) as punishmentTimes3,sum(punishmentTimes4) as punishmentTimes4 from TwoCodeYwQuaCredRating where ywCompanyID =?   and ratingDate = ? and ratingType = ?";
 	 			        cInfo =YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class, sql, new Object[]{info.getYwCompanyID(),info.getRatingDate(),1});
 	 			        int complaintsEventsTimes =0;
 	 			        int complaintsEventsTimes2 =0;
 	 			        int maintenBusinessTimes = 0;
 	 			        int honestTimes =0;
 	 			        int punishmentTimes =0;
 	 			        int punishmentTimes2 =0;
 	 			        int punishmentTimes3 =0;
 	 			        int punishmentTimes4 =0;
 	 			    	if(cInfo != null){	
 	 			    		//�����������ʼ๲�д�ֵĲ���
 	 			    		 complaintsEventsTimes=cInfo.getComplaintsEventsTimes();
 	 			    		 complaintsEventsTimes2=cInfo.getComplaintsEventsTimes2();
 	 			    		 maintenBusinessTimes = cInfo.getMaintenBusinessTimes();
 	 			    		 honestTimes =cInfo.getHonestTimes();
 	 			    		 punishmentTimes=cInfo.getPunishmentTimes();
 	 			    		 punishmentTimes2=cInfo.getPunishmentTimes2();
 	 			    		 punishmentTimes3=cInfo.getPunishmentTimes3();
 	 			    		 punishmentTimes4=cInfo.getPunishmentTimes4();
 	 			    	}	
 	 			    		pInfo.setComplaintsEventssj(100-5*complaintsEventsTimes-20*complaintsEventsTimes2);
 	 			    		pInfo.setMaintenBusinesssj(0-20*maintenBusinessTimes);
 	 			    		pInfo.setHonestsj(0-20*honestTimes);
 	 			    		pInfo.setPunishmentsj(100-2*punishmentTimes-5*punishmentTimes2-20*punishmentTimes3-20*punishmentTimes4);
 	 			    		
 	 			    	//��������¼�Ĵ���
 	 			    		pInfo.setComplaintsEventsTimes(complaintsEventsTimes);
 	 			    		pInfo.setComplaintsEventsTimes2(complaintsEventsTimes2);
 	 			    	    pInfo.setMaintenBusinessTimes(maintenBusinessTimes);
 	 			    		pInfo.setHonestTimes(honestTimes);
 	 			    		pInfo.setPunishmentTimes(punishmentTimes);
 	 			    		pInfo.setPunishmentTimes2(punishmentTimes2);
 	 			    		pInfo.setPunishmentTimes3(punishmentTimes3);
 	 			    		pInfo.setPunishmentTimes4(punishmentTimes4);
 	 			    	
 	 			    		//�������ʼ൥����ֵĲ���
 	 			    		pInfo.setOfficeSpace(info.getOfficeSpace());
 	 			    		pInfo.setOfficeSpacesj(info.getOfficeSpacesj());
 	 			    		pInfo.setOfficeSpacebz(info.getOfficeSpacebz());
 	 			    	//	pInfo.setHeadQuarters(info.getHeadQuarters());
 	 			    	//	pInfo.setHeadQuarterssj(info.getHeadQuarterssj());
 	 			    		pInfo.setFixedTelOnDuty(info.getFixedTelOnDuty());
 	 			    		pInfo.setFixedTelOnDutysj(info.getFixedTelOnDutysj());
 	 			    		pInfo.setFixedTelOnDutybz(info.getFixedTelOnDutybz());
 	 			    		pInfo.setTelOnDutyunattendedTimes(info.getTelOnDutyunattendedTimes());
 	 			    		pInfo.setTelOnDutyunattendedsj(info.getTelOnDutyunattendedsj());
 	 			    		pInfo.setTelOnDutyunattendedbz(info.getTelOnDutyunattendedbz());
 	 			    		pInfo.setEnterpriseChangeTimes(info.getEnterpriseChangeTimes());
 	 			    		pInfo.setEnterpriseChangesj(info.getEnterpriseChangesj());
 	 			    		pInfo.setEnterpriseChangebz(info.getEnterpriseChangebz());
 	 			    		pInfo.setEnterpriseRecord(info.getEnterpriseRecord());
 	 			    		pInfo.setEnterpriseRecordsj(info.getEnterpriseRecordsj());
 	 			    		pInfo.setEnterpriseRecordbz(info.getEnterpriseRecordbz());// malignantEventsTimes
 	 			    		pInfo.setMalignantEventsTimes(info.getMalignantEventsTimes());
 	 			    		pInfo.setMalignantEventsTimes2(info.getMalignantEventsTimes2());
 	 			    		pInfo.setMalignantEventsTimes3(info.getMalignantEventsTimes3());
 	 			    		pInfo.setMalignantEventssj(info.getMalignantEventssj());
 	 			    		pInfo.setMalignantEventsbz(info.getMalignantEventsbz());
 	 			    		pInfo.setSecondRescuePoint(info.getSecondRescuePoint());
 	 			    		pInfo.setSecondRescuePointsj(info.getSecondRescuePointsj());
 	 			    		pInfo.setSecondRescuePointbz(info.getSecondRescuePointbz());
 	 			    		pInfo.setPositiveEnergyTimes(info.getPositiveEnergyTimes());
 	 			    		pInfo.setPositiveEnergysj(info.getPositiveEnergysj());
 	 			    		pInfo.setPositiveEnergybz(info.getPositiveEnergybz());
 	 			    		pInfo.setExpertsSuggestionTimes(info.getExpertsSuggestionTimes());
 	 			    		pInfo.setExpertsSuggestionsj(info.getExpertsSuggestionsj());
 	 			    		pInfo.setExpertsSuggestionbz(info.getExpertsSuggestionbz());
 	 			    		pInfo.setPositiveWork(info.getPositiveWork());
 	 			    		pInfo.setPositiveWorksj(info.getPositiveWorksj());
 	 			    		pInfo.setPositiveWorkbz(info.getPositiveWorkbz());
 	 			    		pInfo.setRemoteMonitor(info.getRemoteMonitor());
 	 			    		pInfo.setRemoteMonitorsj(info.getRemoteMonitorsj());
 	 			    		pInfo.setRemoteMonitorbz(info.getRemoteMonitorbz());
 	 			    		pInfo.setElevatorInsurance(info.getElevatorInsurance());
 	 			    		pInfo.setElevatorInsurancesj(info.getElevatorInsurancesj());
 	 			    		pInfo.setElevatorInsurancebz(info.getElevatorInsurancebz());
 	 			    		pInfo.setTechinnovationTimes(info.getTechinnovationTimes());
 	 			    		pInfo.setTechinnovationTimes2(info.getTechinnovationTimes2());
 	 			    		pInfo.setTechinnovationTimes3(info.getTechinnovationTimes3());
 	 			    		pInfo.setTechinnovationTimes4(info.getTechinnovationTimes4());
 	 			    		pInfo.setTechinnovationTimes5(info.getTechinnovationTimes5());
 	 			    		pInfo.setTechinnovationsj(info.getTechinnovationsj());
 	 			    		pInfo.setTechinnovationbz(info.getTechinnovationbz());
 	 			    		if(pInfo.update()){ //���ּ�¼���³ɹ����������ֳ���
 	 			    //			int unum = YwQuaCredRatingConstant.updateAll(YwQuaCredRatingConstant.class, "officeSpace =?,fixedTelOnDuty=?", new Object[]{info.getOfficeSpace(),info.getFixedTelOnDuty()}, "ywCompanyID = ? ", new Object[]{ywCompanyID} ); 
 	 			    			int unum = YwQuaCredRatingConstant.updateAll(YwQuaCredRatingConstant.class, "officeSpace =?,officeSpacesj=?,officeSpacebz=?,fixedTelOnDuty=?,fixedTelOnDutysj=?,fixedTelOnDutybz=?", new Object[]{info.getOfficeSpace(),info.getOfficeSpacesj(),info.getOfficeSpacebz(),info.getFixedTelOnDuty(),info.getFixedTelOnDutysj(),info.getFixedTelOnDutybz()}, "ywCompanyID = ? ", new Object[]{ywCompanyID} ); 
 	 		 			    	
 	 			    			if( unum == 0){
 	 			    				YwQuaCredRatingConstant yqcrc = new YwQuaCredRatingConstant();
 	 			    				yqcrc.setYwCompanyID(ywCompanyID);
 	 			    				yqcrc.setOfficeSpace(info.getOfficeSpace());
 	 			    				yqcrc.setOfficeSpacesj(info.getOfficeSpacesj());
	 			    				yqcrc.setFixedTelOnDuty(info.getFixedTelOnDuty());
	 			    				yqcrc.setFixedTelOnDutysj(info.getFixedTelOnDutysj());
	 			    				
	 			    				yqcrc.setOfficeSpacebz(info.getOfficeSpacebz());
	 			    				yqcrc.setFixedTelOnDutybz(info.getFixedTelOnDutybz());
	 			    				
 	 			    				yqcrc.save();
 	 			    			}
 	 			    			result = "success";
 	 			    		}	
 	 			    	}
 	 			     if(role == 10 || role ==11){ //��(��)���ʼ� 
 	 			    	//��ͬ��ֵĲ���ȡ�Ӽ�¼֮��
 	  			        sql ="select sum(inspectElevatorTimes) as inspectElevatorTimes,sum(inspectElevatorTimes2) as inspectElevatorTimes2,sum(acceptInspElevatorTimes) as acceptInspElevatorTimes,sum(maintenSceneInfoTimes) as maintenSceneInfoTimes,sum(complaintsEventsTimes) as complaintsEventsTimes,sum(complaintsEventsTimes2) as complaintsEventsTimes2,sum(maintenBusinessTimes) as maintenBusinessTimes,sum(honestTimes) as honestTimes,sum(punishmentTimes) as punishmentTimes,sum(punishmentTimes2) as punishmentTimes2,sum(punishmentTimes3) as punishmentTimes3,sum(punishmentTimes4) as punishmentTimes4 from TwoCodeYwQuaCredRating where ywCompanyID =?   and ratingDate = ? and ratingType = ?";
 	  			        cInfo =YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class, sql, new Object[]{info.getYwCompanyID(),info.getRatingDate(),1});
 	  			        int inspectElevatorTimes =0;
			    		int inspectElevatorTimes2 =0;   
			    		int acceptInspElevatorTimes=0;  //���ܼල������
			    		int maintenSceneInfoTimes =0;
			    		int complaintsEventsTimes=0;
			    		int complaintsEventsTimes2=0;
			    		int maintenBusinessTimes = 0;
			    		int honestTimes =0;
			    		int punishmentTimes=0;
			    		int punishmentTimes2=0;
			    		int punishmentTimes3=0;
			    		int punishmentTimes4=0;
 	  			        if(cInfo != null){	
 	  			    		//���������ʼ�,�����ؼ�Ժ���д�ֵĲ���
 	  			    		 inspectElevatorTimes =cInfo.getInspectElevatorTimes();
 	  			    		 inspectElevatorTimes2 =cInfo.getInspectElevatorTimes2();   
 	  			    		 acceptInspElevatorTimes=cInfo.getAcceptInspElevatorTimes();  //���ܼල������
 	  			    		 maintenSceneInfoTimes =cInfo.getMaintenSceneInfoTimes();
 	  			    		 complaintsEventsTimes=cInfo.getComplaintsEventsTimes();
 	  			    		 complaintsEventsTimes2=cInfo.getComplaintsEventsTimes2();
 	  			    	     maintenBusinessTimes = cInfo.getMaintenBusinessTimes();
 	  			    		 honestTimes =cInfo.getHonestTimes();
 	  			    	     punishmentTimes=cInfo.getPunishmentTimes();
 	  			    		 punishmentTimes2=cInfo.getPunishmentTimes2();
 	  			    		 punishmentTimes3=cInfo.getPunishmentTimes3();
 	  			    		 punishmentTimes4=cInfo.getPunishmentTimes4();
 	  			    	}
 	  			    //		pInfo = YwQuaCredRating.findFirstBySql(YwQuaCredRating.class, "select complaintsEventssj,maintenBusinesssj,honestsj,punishmentsj from TwoCodeYwQuaCredRating where ywCompanyID =?  and ratingDate = ? and ratingType = ?", new Object[]{info.getYwCompanyID(),info.getRatingDate(),0});
 	  			    		pInfo.setInspectElevatorsj(100-5*inspectElevatorTimes-20*inspectElevatorTimes2);
 	  			    		pInfo.setAcceptInspElevatorsj(0-10*acceptInspElevatorTimes);
 	  			    		pInfo.setMaintenSceneInfosj(0-10*maintenSceneInfoTimes);
 	  			    		pInfo.setComplaintsEventssj(100-5*complaintsEventsTimes-20*complaintsEventsTimes2);
 	  			    		pInfo.setMaintenBusinesssj(0-20*maintenBusinessTimes);
 	  			    		pInfo.setHonestsj(0-20*honestTimes);
 	  			    		pInfo.setPunishmentsj(100-2*punishmentTimes-5*punishmentTimes2-20*punishmentTimes3-20*punishmentTimes4);
 	  			    		
 	  			    		
 	  			    	//��������¼�Ĵ���
 	  			    		pInfo.setInspectElevatorTimes(inspectElevatorTimes);
 	  			    		pInfo.setInspectElevatorTimes2(inspectElevatorTimes2);
 	  			    		pInfo.setAcceptInspElevatorTimes(acceptInspElevatorTimes);
 	  			    		pInfo.setMaintenBusinessTimes(maintenBusinessTimes);
 	  			    		pInfo.setComplaintsEventsTimes(complaintsEventsTimes);
 	  			    		pInfo.setComplaintsEventsTimes2(complaintsEventsTimes2);
 	  			    		pInfo.setMaintenBusinessTimes(maintenBusinessTimes);
 	  			    		pInfo.setHonestTimes(honestTimes);
 	  			    		pInfo.setPunishmentTimes(punishmentTimes);
 	  			    		pInfo.setPunishmentTimes2(punishmentTimes2);
 	  			    		pInfo.setPunishmentTimes3(punishmentTimes3);
 	  			    		pInfo.setPunishmentTimes4(punishmentTimes4);
 	  			    	   
 	  			           //���������У����ʼ൥����ֵĲ��֣�û�е�����ֵģ����Բ���Ҫ������
 	  			    	    if(pInfo.update()){
 				    			result = "success";
 				    		}	
 	 			    	 
 	 			     }
 	 			     
 	 			    if(role == 16 || role ==17){  //�ؼ�Ժ
 	 			    	 sql ="select sum(inspectElevatorTimes) as inspectElevatorTimes,sum(inspectElevatorTimes2) as inspectElevatorTimes2,sum(acceptInspElevatorTimes) as acceptInspElevatorTimes,sum(maintenSceneInfoTimes) as maintenSceneInfoTimes from TwoCodeYwQuaCredRating where ywCompanyID =?   and ratingDate = ? and ratingType = ?";
 	   			        cInfo =YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class, sql, new Object[]{info.getYwCompanyID(),info.getRatingDate(),1});
 	   			        int inspectElevatorTimes =0;
			    		int inspectElevatorTimes2 =0;   
			    		int acceptInspElevatorTimes=0;  //���ܼල������
			    		int maintenSceneInfoTimes =0;
			    		int complaintsEventsTimes=0;
			    		int complaintsEventsTimes2=0;
			    		int maintenBusinessTimes = 0;
 	   			        if(cInfo != null){	
 	   			    		//�������������ʼ๲�д�ֵĲ���
 	   			    		 inspectElevatorTimes =cInfo.getInspectElevatorTimes();
 	   			    		 inspectElevatorTimes2 =cInfo.getInspectElevatorTimes2();   
 	   			    		 acceptInspElevatorTimes=cInfo.getAcceptInspElevatorTimes();  //���ܼල������
 	   			    		 maintenSceneInfoTimes =cInfo.getMaintenSceneInfoTimes();  //ά���ֳ�
 	   			    		 complaintsEventsTimes=cInfo.getComplaintsEventsTimes();
 	   			    		 complaintsEventsTimes2=cInfo.getComplaintsEventsTimes2();
 	   			    		 maintenBusinessTimes = cInfo.getMaintenBusinessTimes();
 	   			    	}
 	   			    		
 	   			//    		pInfo = YwQuaCredRating.findFirstBySql(YwQuaCredRating.class, "select complaintsEventssj,maintenBusinesssj,honestsj,punishmentsj from TwoCodeYwQuaCredRating where ywCompanyID =?  and ratingDate = ? and ratingType = ?", new Object[]{info.getYwCompanyID(),info.getRatingDate(),0});
 	   			    		pInfo.setInspectElevatorsj(100-5*inspectElevatorTimes-20*inspectElevatorTimes2);
 	   			    		pInfo.setAcceptInspElevatorsj(0-10*acceptInspElevatorTimes);
 	   			    		pInfo.setMaintenSceneInfosj(0-10*maintenSceneInfoTimes);
 	   			    		pInfo.setComplaintsEventssj(100-5*complaintsEventsTimes-20*complaintsEventsTimes2);
 	   			    		pInfo.setMaintenBusinesssj(0-20*maintenBusinessTimes);
 	   			    		
 	   			    	//��������¼�Ĵ���
 	   			    		pInfo.setInspectElevatorTimes(inspectElevatorTimes);
 	   			    		pInfo.setInspectElevatorTimes2(inspectElevatorTimes2);
 	   			    		pInfo.setAcceptInspElevatorTimes(acceptInspElevatorTimes);
 	   			    		pInfo.setMaintenSceneInfoTimes(maintenSceneInfoTimes);
 	   			    		pInfo.setComplaintsEventsTimes(complaintsEventsTimes);
 	   			    		pInfo.setComplaintsEventsTimes2(complaintsEventsTimes2);
 	   			    		pInfo.setMaintenBusinessTimes(maintenBusinessTimes);
 	   			    	   
 	   			           //�ؼ��鵥����ֵĲ���
 	   			    	    pInfo.setRegularInspectionTimes(info.getRegularInspectionTimes());
 	   			    	    pInfo.setRegularInspectionsj(info.getRegularInspectionsj());
 	   			    	    pInfo.setRegularInspectionbz(info.getRegularInspectionbz());
 	   			    	    
 	   			    	   if(pInfo.update()){
 				    			result = "success";
 				    		}	
 	  			    	 
 	 			    }
 	    	
 	    		} 
 	    		}
 	    	}
 	    	 ActiveRecordBase.commit();			
 	     } catch (ActiveRecordException e) {
 	    	try {
				ActiveRecordBase.rollback();
			} catch (TransactionException e1) {
				e1.printStackTrace();
			}
		}
		  
		
		
 		return result;
 	}
 	
 	public View ywsyssetings2ByComIdRatDate(int ywCompanyID,String ratingDate){
 		
 		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 
		 UserExtInfo  info =null;
		 String userName ="";
	 	 int companyid = 0;
	 	 CompanyInfoVO companyinfo =null;
	 	 String sql = "";
	 	 Map<String, Object> result = new HashMap<String, Object>();
		 if((role == 22 || role ==23) || (role == 30 || role ==31)){ //���ʼ� ,��ҵЭ��
			 try {
				info = UserExtInfo.findFirstBySql(UserExtInfo.class, "select userName,companyid from TwoCodeUserExtInfo where userid = ?", new Object[]{userid});
				if(info != null){
		 	    	//userName = info.getUserName();
		 	        companyid = info.getCompanyid();	
		 	    	}
				if(role == 30 || role ==31){
					//��ҵЭ��Ҫ�����ʼ��companyId
		    		companyinfo = CompanyInfoVO.findFirstBySql(CompanyInfoVO.class, "select id  from TwoCodeCompanyInfo where type = ?", new Object[]{"���ʼ�"});
		    		if(companyinfo != null){
		    			companyid = companyinfo.getId();
		    		}
				}
			//	sql ="select  t.*,officeSpace, officeSpacesj,officeSpacebz, headQuarters,headQuarterssj,headQuartersbz,fixedTelOnDuty,fixedTelOnDutysj,fixedTelOnDutybz,telOnDutyunattendedTimes,telOnDutyunattendedsj,telOnDutyunattendedbz,enterpriseChangeTimes,enterpriseChangesj,enterpriseChangebz,enterpriseRecord,enterpriseRecordsj,malignantEventsTimes,malignantEventsTimes2,malignantEventsTimes3,malignantEventssj,malignantEventsbz,secondRescuePoint,secondRescuePointsj,secondRescuePointbz,positiveEnergyTimes,positiveEnergysj, positiveEnergybz,expertsSuggestionTimes,expertsSuggestionsj,expertsSuggestionbz,positiveWork,positiveWorksj,positiveWorkbz,remoteMonitor,remoteMonitorsj,remoteMonitorbz,elevatorInsurance,elevatorInsurancesj,elevatorInsurancebz,techinnovationTimes,techinnovationTimes2,techinnovationTimes3,techinnovationTimes4,techinnovationTimes5,techinnovationsj,complaintsEventsTimes,complaintsEventsTimes2,complaintsEventssj,complaintsEventsbz,maintenBusinessTimes,maintenBusinesssj,maintenBusinessbz,honestTimes, honestsj,honestbz,punishmentTimes,punishmentTimes2,punishmentTimes3,punishmentTimes4,punishmentsj,positiveEnergybz from TwoCodeYwQuaCredRating   left join (select ywCompanyID,ratingDate,regularInspectionTimes,regularInspectionsj,inspectElevatorTimes,inspectElevatorTimes2,inspectElevatorsj,acceptInspElevatorTimes, acceptInspElevatorsj,maintenSceneInfoTimes,maintenSceneInfosj from TwoCodeYwQuaCredRating where  ratingDate = ? and ratingType = 0 and  ywCompanyID = ? )  t on TwoCodeYwQuaCredRating.ywCompanyID = t.ywCompanyID and  TwoCodeYwQuaCredRating.ratingDate = t.ratingDate where ratingCompanyId = ? and TwoCodeYwQuaCredRating.ratingDate = ? and TwoCodeYwQuaCredRating.ratingType = 1 and  TwoCodeYwQuaCredRating.ywCompanyID = ? ";
			    sql ="select  * from TwoCodeYwQuaCredRating where   ratingDate = ? and ratingCompanyId = ? and ratingType = 1 and  ywCompanyID = ?";
			//	YwQuaCredRatingVO vo =YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class, "select * from TwoCodeYwQuaCredRating where ratingCompanyId = ? and ratingDate = ? and ratingType = ? and  ywCompanyID = ? ", new Object[]{companyid,ratingDate,1,ywCompanyID});
				YwQuaCredRatingVO vo =YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class, sql, new Object[]{ratingDate,companyid,ywCompanyID});
				if(vo != null){
					//�ʼ൥�������Ŀ
		 	    	result.put("officeSpace",vo.getOfficeSpace());
		 	    	result.put("officeSpacesj",vo.getOfficeSpacesj());
		 	    	result.put("officeSpacebz",vo.getOfficeSpacebz());
		 	    	result.put("headQuarters",vo.getHeadQuarters());
		 	    	result.put("headQuarterssj",vo.getHeadQuarterssj());
		 	    	result.put("headQuartersbz",vo.getHeadQuartersbz());
		 	    	result.put("fixedTelOnDuty",vo.getFixedTelOnDuty());
		 	    	result.put("fixedTelOnDutysj",vo.getFixedTelOnDutysj());
		 	    	result.put("fixedTelOnDutybz",vo.getFixedTelOnDutybz());
		 	    	result.put("telOnDutyunattendedTimes",vo.getTelOnDutyunattendedTimes());
		 	    	result.put("telOnDutyunattendedsj",vo.getTelOnDutyunattendedsj());
		 	    	result.put("telOnDutyunattendedbz",vo.getTelOnDutyunattendedbz());
		 	    	result.put("enterpriseChangeTimes",vo.getEnterpriseChangeTimes());
		 	    	result.put("enterpriseChangesj",vo.getEnterpriseChangesj());
		 	    	result.put("enterpriseChangebz",vo.getEnterpriseChangebz());
		 	    	result.put("enterpriseRecord",vo.getEnterpriseRecord());
		 	    	result.put("enterpriseRecordsj",vo.getEnterpriseRecordsj());
		 	    	result.put("enterpriseRecordbz",vo.getEnterpriseRecordbz());
		 	    	result.put("malignantEventsTimes",vo.getMalignantEventsTimes());
		 	    	result.put("malignantEventsTimes2",vo.getMalignantEventsTimes2());
		 	    	result.put("malignantEventsTimes3",vo.getMalignantEventsTimes3());
		 	    	result.put("malignantEventssj",vo.getMalignantEventssj());
		 	    	result.put("malignantEventsbz",vo.getMalignantEventsbz());
		 	    	result.put("secondRescuePoint",vo.getSecondRescuePoint());
		 	    	result.put("secondRescuePointsj",vo.getSecondRescuePointsj());
		 	    	result.put("secondRescuePointbz",vo.getSecondRescuePointbz());
		 	    	result.put("positiveEnergyTimes",vo.getPositiveEnergyTimes());
		 	    	result.put("positiveEnergysj",vo.getPositiveEnergysj());
		 	    	result.put("positiveEnergybz",vo.getPositiveEnergybz());
		 	    	result.put("expertsSuggestionTimes",vo.getExpertsSuggestionTimes());
		 	    	result.put("expertsSuggestionsj",vo.getExpertsSuggestionsj());
		 	    	result.put("expertsSuggestionbz",vo.getExpertsSuggestionbz());
		 	    	result.put("positiveWork",vo.getPositiveWork());
		 	    	result.put("positiveWorksj",vo.getPositiveWorksj());
		 	    	result.put("positiveWorkbz",vo.getPositiveWorkbz());
		 	    	result.put("remoteMonitor",vo.getRemoteMonitor());
		 	    	result.put("remoteMonitorsj",vo.getRemoteMonitorsj());
		 	    	result.put("remoteMonitorbz",vo.getRemoteMonitorbz());
		 	    	result.put("elevatorInsurance",vo.getElevatorInsurance());
		 	    	result.put("elevatorInsurancesj",vo.getElevatorInsurancesj());
		 	    	result.put("elevatorInsurancebz",vo.getElevatorInsurancebz());
		 	    	result.put("techinnovationTimes",vo.getTechinnovationTimes());
		 	    	result.put("techinnovationTimes2",vo.getTechinnovationTimes2());
		 	    	result.put("techinnovationTimes3",vo.getTechinnovationTimes3());
		 	    	result.put("techinnovationTimes4",vo.getTechinnovationTimes4());
		 	    	result.put("techinnovationTimes5",vo.getTechinnovationTimes5());
		 	    	result.put("techinnovationsj",vo.getTechinnovationsj());
		 	    	result.put("techinnovationbz",vo.getTechinnovationbz());
		 	    	
		 	    	//��������ͬ��ֵ���Ŀ
		 	    	result.put("complaintsEventsTimes", vo.getComplaintsEventsTimes());
		 	    	result.put("complaintsEventsTimes2",vo.getComplaintsEventsTimes2());
		 	    	result.put("complaintsEventssj", vo.getComplaintsEventssj());
		 	    	result.put("complaintsEventsbz", vo.getComplaintsEventsbz());
		 	    	result.put("maintenBusinessTimes", vo.getMaintenBusinessTimes());
		 	    	result.put("maintenBusinesssj", vo.getMaintenBusinesssj());
		 	    	result.put("maintenBusinessbz", vo.getMaintenBusinessbz());
		 	    	result.put("honestTimes",vo.getHonestTimes());
		 	    	result.put("honestsj",vo.getHonestsj());
		 	    	result.put("honestbz",vo.getHonestbz());
		 	    	result.put("punishmentTimes",vo.getPunishmentTimes());
		 	    	result.put("punishmentTimes2",vo.getPunishmentTimes2());
		 	    	result.put("punishmentTimes3",vo.getPunishmentTimes3());
		 	    	result.put("punishmentTimes4",vo.getPunishmentTimes4());
		 	    	result.put("punishmentsj",vo.getPunishmentsj());
		 	    	result.put("punishmentbz",vo.getPunishmentbz());
		 	    	
		 	    	//�����ʼ�ִ�ֵ���Ŀ
		 	    	result.put("regularInspectionTimes",vo.getRegularInspectionTimes());
		 	    	result.put("regularInspectionsj",vo.getRegularInspectionsj());
		 	    	result.put("inspectElevatorTimes",vo.getInspectElevatorTimes());
		 	    	result.put("inspectElevatorTimes2",vo.getInspectElevatorTimes2());
		 	    	result.put("inspectElevatorsj",vo.getInspectElevatorsj());
		 	    	result.put("acceptInspElevatorTimes",vo.getAcceptInspElevatorTimes());
		 	    	result.put("acceptInspElevatorsj",vo.getAcceptInspElevatorsj());
		 	    	result.put("maintenSceneInfoTimes",vo.getMaintenSceneInfoTimes());
		 	    	result.put("maintenSceneInfosj",vo.getMaintenSceneInfosj());
		 	    	
		 	    }
				else{  //Ϊ�յ�ʱ���ȡ����
			//		YwQuaCredRatingConstant ycrcvo = YwQuaCredRatingConstant.findFirstBySql(YwQuaCredRatingConstant.class, "select officeSpace,officeSpacesj,fixedTelOnDuty,fixedTelOnDutysj from TwoCodeYwQuaCredRatConstant where ywCompanyID = ? ", new Object[]{ywCompanyID});
					YwQuaCredRatingConstant ycrcvo = YwQuaCredRatingConstant.findFirstBySql(YwQuaCredRatingConstant.class, "select officeSpace,officeSpacesj,officeSpacebz,fixedTelOnDuty,fixedTelOnDutysj,fixedTelOnDutybz from TwoCodeYwQuaCredRatConstant where ywCompanyID = ? ", new Object[]{ywCompanyID});
					   
					if(ycrcvo != null){
				    	result.put("officeSpace",ycrcvo.getOfficeSpace());
				    	result.put("officeSpacesj",ycrcvo.getOfficeSpacesj());
				    	result.put("fixedTelOnDuty",ycrcvo.getFixedTelOnDuty());
				    	result.put("fixedTelOnDutysj",ycrcvo.getFixedTelOnDutysj());
				    	
				    	result.put("officeSpacebz",ycrcvo.getOfficeSpacebz());
				    	result.put("fixedTelOnDutybz",ycrcvo.getFixedTelOnDutybz());
				    }
				    else{
				    	result.put("officeSpace",0);
				    	result.put("officeSpacesj",95);
				    	result.put("fixedTelOnDuty","");
				    	result.put("fixedTelOnDutysj",-10);
				    	
				    	result.put("officeSpacebz","");
				    	result.put("fixedTelOnDutybz","");
				       }
				    
				    	//Ϊ�յ�ʱ������ΪĬ��ֵ
				 //   	result.put("officeSpacebz","");
			 	    	result.put("headQuarters",0);
			 	    	result.put("headQuarterssj",10);
			 	    	result.put("headQuartersbz","");
			 	 //   	result.put("fixedTelOnDutybz","");
			 	    	result.put("telOnDutyunattendedTimes",0);
			 	    	result.put("telOnDutyunattendedsj",0);
			 	    	result.put("telOnDutyunattendedbz","");
			 	    	result.put("enterpriseChangeTimes",0);
			 	    	result.put("enterpriseChangesj",0);
			 	    	result.put("enterpriseChangebz","");
			 	    	result.put("enterpriseRecord",0);
			 	    	result.put("enterpriseRecordsj",0);
			 	    	result.put("enterpriseRecordbz","");
			 	    	result.put("malignantEventsTimes",0);
			 	    	result.put("malignantEventsTimes2",0);
			 	    	result.put("malignantEventsTimes3",0);
			 	    	result.put("malignantEventssj",100);
			 	    	result.put("malignantEventsbz","");
			 	    	result.put("secondRescuePoint",0);
			 	    	result.put("secondRescuePointsj",0);
			 	    	result.put("secondRescuePointbz","");
			 	    	result.put("positiveEnergyTimes",0);
			 	    	result.put("positiveEnergysj",0);
			 	    	result.put("positiveEnergybz","");
			 	    	result.put("expertsSuggestionTimes",0);
			 	    	result.put("expertsSuggestionsj",0);
			 	    	result.put("expertsSuggestionbz","");
			 	    	result.put("positiveWork",0);
			 	    	result.put("positiveWorksj",0);
			 	    	result.put("positiveWorkbz","");
			 	    	result.put("remoteMonitor",0);
			 	    	result.put("remoteMonitorsj",0);
			 	    	result.put("remoteMonitorbz","");
			 	    	result.put("elevatorInsurance",0);
			 	    	result.put("elevatorInsurancesj",0);
			 	    	result.put("elevatorInsurancebz","");
			 	    	result.put("techinnovationTimes",0);
			 	    	result.put("techinnovationTimes2",0);
			 	    	result.put("techinnovationTimes3",0);
			 	    	result.put("techinnovationTimes4",0);
			 	    	result.put("techinnovationTimes5",0);
			 	    	result.put("techinnovationsj",0);
			 	    	result.put("techinnovationbz","");
			 	    	
			 	    	result.put("complaintsEventsTimes", 0);
			 	    	result.put("complaintsEventsTimes2",0);
			 	    	result.put("complaintsEventssj", 100);
			 	    	result.put("complaintsEventsbz", "");
			 	    	result.put("maintenBusinessTimes", 0);
			 	    	result.put("maintenBusinesssj", 0);
			 	    	result.put("maintenBusinessbz", "");
			 	    	result.put("honestTimes",0);
			 	    	result.put("honestsj",0);
			 	    	result.put("honestbz","");
			 	    	result.put("punishmentTimes",0);
			 	    	result.put("punishmentTimes2",0);
			 	    	result.put("punishmentTimes3",0);
			 	    	result.put("punishmentTimes4",0);
			 	    	result.put("punishmentsj",100);
			 	    	result.put("punishmentbz","");
				    	
			 	    	result.put("regularInspectionTimes",0);
			 	    	result.put("regularInspectionsj",100);
			 	    	result.put("inspectElevatorTimes",0);
			 	    	result.put("inspectElevatorTimes2",0);
			 	    	result.put("inspectElevatorsj",100);
			 	    	result.put("inspectElevatorbz","");
			 	    	result.put("acceptInspElevatorTimes",0);
			 	    	result.put("acceptInspElevatorsj",0);
			 	    	result.put("acceptInspElevatorsjbz","");
			 	    	result.put("maintenSceneInfoTimes",0);
			 	    	result.put("maintenSceneInfosj",0);
			 	    	result.put("maintenSceneInfobz","");
			 	    	
			 	    	result.put("maintenanceEleCount", "");
			 	    	result.put("maintenanceEleCountsj", 100);
			 	    	result.put("avgmaintenanceEleCount", "");
			 	    	result.put("avgmaintenanceEleCountsj", 100);
			 	    	result.put("infoComRatesj", 100);
			 	    	result.put("infoComRate", 100);
			 	    	result.put("infoComRatesj", 100);
			 	    	result.put("sweepCodeRate", 100);
			 	    	result.put("sweepCodeRatesj", 100);
			 	    	result.put("sweepCodeInTimeRate", 100);
			 	    	result.put("sweepCodeInTimeRatesj", 100);
			 	    	result.put("alarmDealwith", 0);
			 	    	result.put("alarmDealwithsj", 100);
				  
				}
			 } catch (ActiveRecordException e) {	
				e.printStackTrace();
			}   	
		 }
		 
		 if(role == 10 || role == 11){ //�����ʼ� 
			 try {
				info = UserExtInfo.findFirstBySql(UserExtInfo.class, "select userName,companyid from TwoCodeUserExtInfo where userid = ?", new Object[]{userid});
				if(info != null){
		 	    	//userName = info.getUserName();
		 	        companyid = info.getCompanyid();	
		 	    	}
		//		sql ="select t.*,inspectElevatorTimes,inspectElevatorTimes2,inspectElevatorsj,acceptInspElevatorTimes,acceptInspElevatorsj,maintenSceneInfoTimes,maintenSceneInfosj,complaintsEventsTimes,complaintsEventsTimes2,complaintsEventssj,complaintsEventsbz,maintenBusinessTimes,maintenBusinesssj,maintenBusinessbz,honestTimes, honestsj,honestbz,punishmentTimes,punishmentTimes2,punishmentTimes3,punishmentTimes4,punishmentsj,punishmentbz from TwoCodeYwQuaCredRating  left join (select ywCompanyID,ratingDate,officeSpace, officeSpacesj,officeSpacebz, headQuarters,headQuarterssj,headQuartersbz,fixedTelOnDuty,fixedTelOnDutysj,fixedTelOnDutybz,telOnDutyunattendedTimes,telOnDutyunattendedsj,telOnDutyunattendedbz,enterpriseChangeTimes,enterpriseChangesj,enterpriseChangebz,enterpriseRecord,enterpriseRecordsj,regularInspectionTimes,regularInspectionsj,regularInspectionbz,malignantEventsTimes,malignantEventsTimes2,malignantEventsTimes3,malignantEventssj,malignantEventsbz,secondRescuePoint,secondRescuePointsj,secondRescuePointbz,positiveEnergyTimes,positiveEnergysj, positiveEnergybz,expertsSuggestionTimes,expertsSuggestionsj,expertsSuggestionbz,positiveWork,positiveWorksj,positiveWorkbz,remoteMonitor,remoteMonitorsj,remoteMonitorbz,elevatorInsurance,elevatorInsurancesj,elevatorInsurancebz,techinnovationTimes,techinnovationTimes2,techinnovationTimes3,techinnovationTimes4,techinnovationTimes5,techinnovationsj from TwoCodeYwQuaCredRating where  ratingDate = ? and ratingType = 0 and  ywCompanyID = ? )  t on TwoCodeYwQuaCredRating.ywCompanyID = t.ywCompanyID and  TwoCodeYwQuaCredRating.ratingDate = t.ratingDate where ratingCompanyId = ? and TwoCodeYwQuaCredRating.ratingDate = ? and TwoCodeYwQuaCredRating.ratingType = 1 and  TwoCodeYwQuaCredRating.ywCompanyID = ?";
				 sql ="select  * from TwoCodeYwQuaCredRating where   ratingDate = ? and ratingCompanyId = ? and ratingType = 1 and  ywCompanyID = ?";
			//	YwQuaCredRatingVO vo =YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class, "select * from TwoCodeYwQuaCredRating where ratingCompanyId = ? and ratingDate = ? and ratingType = ? and  ywCompanyID = ? ", new Object[]{companyid,ratingDate,1,ywCompanyID});
				YwQuaCredRatingVO vo =YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class, sql, new Object[]{ratingDate,companyid,ywCompanyID});
				if(vo != null){
					//�����ʼ๲ͬ��ֵ���Ŀ
					result.put("inspectElevatorTimes",vo.getInspectElevatorTimes());
		 	    	result.put("inspectElevatorTimes2",vo.getInspectElevatorTimes2());
		 	    	result.put("inspectElevatorsj",vo.getInspectElevatorsj());
		 	    	result.put("inspectElevatorbz",vo.getInspectElevatorbz());
		 	    	result.put("acceptInspElevatorTimes",vo.getAcceptInspElevatorTimes());
		 	    	result.put("acceptInspElevatorsj",vo.getAcceptInspElevatorsj());
		 	    	result.put("acceptInspElevatorbz",vo.getAcceptInspElevatorbz());
		 	    	result.put("complaintsEventsTimes", vo.getComplaintsEventsTimes());
		 	    	result.put("complaintsEventsTimes2",vo.getComplaintsEventsTimes2());
		 	    	result.put("complaintsEventssj", vo.getComplaintsEventssj());
		 	    	result.put("complaintsEventsbz", vo.getComplaintsEventsbz());
		 	    	result.put("maintenSceneInfoTimes",vo.getMaintenSceneInfoTimes());
		 	    	result.put("maintenSceneInfosj",vo.getMaintenSceneInfosj());
		 	    	result.put("maintenSceneInfobz",vo.getMaintenSceneInfobz());
		 	    	result.put("maintenBusinessTimes", vo.getMaintenBusinessTimes());
		 	    	result.put("maintenBusinesssj", vo.getMaintenBusinesssj());
		 	    	result.put("maintenBusinessbz", vo.getMaintenBusinessbz());
		 	    	result.put("honestTimes",vo.getHonestTimes());
		 	    	result.put("honestsj",vo.getHonestsj());
		 	    	result.put("honestbz",vo.getHonestbz());
		 	    	result.put("punishmentTimes",vo.getPunishmentTimes());
		 	    	result.put("punishmentTimes2",vo.getPunishmentTimes2());
		 	    	result.put("punishmentTimes3",vo.getPunishmentTimes3());
		 	    	result.put("punishmentTimes4",vo.getPunishmentTimes4());
		 	    	result.put("punishmentsj",vo.getPunishmentsj());
		 	    	result.put("punishmentbz",vo.getPunishmentbz());
		 	    	
		 	   	//���������ʼ�ִ�ֵ���Ŀ
		 	    	result.put("regularInspectionTimes",vo.getRegularInspectionTimes());
		 	    	result.put("regularInspectionsj",vo.getRegularInspectionsj());
		 	    	result.put("officeSpace",vo.getOfficeSpace());
		 	    	result.put("officeSpacesj",vo.getOfficeSpacesj());
		 	    	result.put("officeSpacebz",vo.getOfficeSpacebz());
		 	    	result.put("headQuarters",vo.getHeadQuarters());
		 	    	result.put("headQuarterssj",vo.getHeadQuarterssj());
		 	    	result.put("headQuartersbz",vo.getHeadQuartersbz());
		 	    	result.put("fixedTelOnDuty",vo.getFixedTelOnDuty());
		 	    	result.put("fixedTelOnDutysj",vo.getFixedTelOnDutysj());
		 	    	result.put("fixedTelOnDutybz",vo.getFirstRescuebz());
		 	    	result.put("telOnDutyunattendedTimes",vo.getTelOnDutyunattendedTimes());
		 	    	result.put("telOnDutyunattendedsj",vo.getTelOnDutyunattendedsj());
		 	    	result.put("telOnDutyunattendedbz",vo.getTelOnDutyunattendedbz());
		 	    	result.put("enterpriseChangeTimes",vo.getEnterpriseChangeTimes());
		 	    	result.put("enterpriseChangesj",vo.getEnterpriseChangesj());
		 	    	result.put("enterpriseChangebz",vo.getEnterpriseChangebz());
		 	    	result.put("enterpriseRecord",vo.getEnterpriseRecord());
		 	    	result.put("enterpriseRecordsj",vo.getEnterpriseRecordsj());
		 	    	result.put("malignantEventsTimes",vo.getMalignantEventsTimes());
		 	    	result.put("malignantEventsTimes2",vo.getMalignantEventsTimes2());
		 	    	result.put("malignantEventsTimes3",vo.getMalignantEventsTimes3());
		 	    	result.put("malignantEventssj",vo.getMalignantEventssj());
		 	    	result.put("malignantEventsbz",vo.getMalignantEventsbz());
		 	    	result.put("secondRescuePoint",vo.getSecondRescuePoint());
		 	    	result.put("secondRescuePointsj",vo.getSecondRescuePointsj());
		 	    	result.put("secondRescuePointbz",vo.getSecondRescuebz());
		 	    	result.put("positiveEnergyTimes",vo.getPositiveEnergyTimes());
		 	    	result.put("positiveEnergysj",vo.getPositiveEnergysj());
		 	    	result.put("positiveEnergybz",vo.getPositiveEnergybz());
		 	    	result.put("expertsSuggestionTimes",vo.getExpertsSuggestionTimes());
		 	    	result.put("expertsSuggestionsj",vo.getExpertsSuggestionsj());
		 	    	result.put("expertsSuggestionbz",vo.getExpertsSuggestionbz());
		 	    	result.put("positiveWork",vo.getPositiveWork());
		 	    	result.put("positiveWorksj",vo.getPositiveWorksj());
		 	    	result.put("positiveWorkbz",vo.getPositiveWorkbz());
		 	    	result.put("remoteMonitor",vo.getRemoteMonitor());
		 	    	result.put("remoteMonitorsj",vo.getRemoteMonitorsj());
		 	    	result.put("remoteMonitorbz",vo.getRemoteMonitorbz());
		 	    	result.put("elevatorInsurance",vo.getElevatorInsurance());
		 	    	result.put("elevatorInsurancesj",vo.getElevatorInsurancesj());
		 	    	result.put("elevatorInsurancebz",vo.getElevatorInsurancebz());
		 	    	result.put("techinnovationTimes",vo.getTechinnovationTimes());
		 	    	result.put("techinnovationTimes2",vo.getTechinnovationTimes2());
		 	    	result.put("techinnovationTimes3",vo.getTechinnovationTimes3());
		 	    	result.put("techinnovationTimes4",vo.getTechinnovationTimes4());
		 	    	result.put("techinnovationTimes5",vo.getTechinnovationTimes5());
		 	    	result.put("techinnovationsj",vo.getTechinnovationsj());
		 	    	 	
		 	    }
				else{  //Ϊ�յ�ʱ���ȡ����
				/*	YwQuaCredRatingConstant ycrcvo = YwQuaCredRatingConstant.findFirstBySql(YwQuaCredRatingConstant.class, "select officeSpace,officeSpacesj,fixedTelOnDuty,fixedTelOnDutysj from TwoCodeYwQuaCredRatConstant where ywCompanyID = ? ", new Object[]{ywCompanyID});
				    if(ycrcvo != null){
				    	result.put("officeSpace",ycrcvo.getOfficeSpace());
				    	result.put("officeSpacesj",ycrcvo.getOfficeSpacesj());
				    	result.put("fixedTelOnDuty",ycrcvo.getFixedTelOnDuty());
				    	result.put("fixedTelOnDutysj",ycrcvo.getFixedTelOnDutysj());
				    }
				    else{
				    	result.put("officeSpace",0);
				    	result.put("officeSpacesj",95);
				    	result.put("fixedTelOnDuty","");
				    	result.put("fixedTelOnDutysj",-10);
				       }
				  */  
				    	//Ϊ�յ�ʱ������ΪĬ��ֵ
					    result.put("officeSpace",0);
			    	    result.put("officeSpacesj",95);
			    	    result.put("fixedTelOnDuty","");
			    	    result.put("fixedTelOnDutysj",-10);    
					
				    	result.put("officeSpacebz","");
			 	    	result.put("headQuarters",0);
			 	    	result.put("headQuarterssj",10);
			 	    	result.put("headQuartersbz","");
			 	    	result.put("fixedTelOnDutybz","");
			 	    	result.put("telOnDutyunattendedTimes",0);
			 	    	result.put("telOnDutyunattendedsj",0);
			 	    	result.put("telOnDutyunattendedbz","");
			 	    	result.put("enterpriseChangeTimes",0);
			 	    	result.put("enterpriseChangesj",0);
			 	    	result.put("enterpriseChangebz","");
			 	    	result.put("enterpriseRecord",0);
			 	    	result.put("enterpriseRecordsj",0);
			 	    	result.put("enterpriseRecordbz","");
			 	    	result.put("malignantEventsTimes",0);
			 	    	result.put("malignantEventsTimes2",0);
			 	    	result.put("malignantEventsTimes3",0);
			 	    	result.put("malignantEventssj",100);
			 	    	result.put("malignantEventsbz","");
			 	    	result.put("secondRescuePoint",0);
			 	    	result.put("secondRescuePointsj",0);
			 	    	result.put("secondRescuePointbz","");
			 	    	result.put("positiveEnergyTimes",0);
			 	    	result.put("positiveEnergysj",0);
			 	    	result.put("positiveEnergybz","");
			 	    	result.put("expertsSuggestionTimes",0);
			 	    	result.put("expertsSuggestionsj",0);
			 	    	result.put("expertsSuggestionbz","");
			 	    	result.put("positiveWork",0);
			 	    	result.put("positiveWorksj",0);
			 	    	result.put("positiveWorkbz","");
			 	    	result.put("remoteMonitor",0);
			 	    	result.put("remoteMonitorsj",0);
			 	    	result.put("remoteMonitorbz","");
			 	    	result.put("elevatorInsurance",0);
			 	    	result.put("elevatorInsurancesj",0);
			 	    	result.put("elevatorInsurancebz","");
			 	    	result.put("techinnovationTimes",0);
			 	    	result.put("techinnovationTimes2",0);
			 	    	result.put("techinnovationTimes3",0);
			 	    	result.put("techinnovationTimes4",0);
			 	    	result.put("techinnovationTimes5",0);
			 	    	result.put("techinnovationsj",0);
			 	    	result.put("techinnovationbz","");
			 	    	
			 	    	result.put("complaintsEventsTimes", 0);
			 	    	result.put("complaintsEventsTimes2",0);
			 	    	result.put("complaintsEventssj", 100);
			 	    	result.put("complaintsEventsbz", "");
			 	    	result.put("maintenBusinessTimes", 0);
			 	    	result.put("maintenBusinesssj", 0);
			 	    	result.put("maintenBusinessbz", "");
			 	    	result.put("honestTimes",0);
			 	    	result.put("honestsj",0);
			 	    	result.put("honestbz","");
			 	    	result.put("punishmentTimes",0);
			 	    	result.put("punishmentTimes2",0);
			 	    	result.put("punishmentTimes3",0);
			 	    	result.put("punishmentTimes4",0);
			 	    	result.put("punishmentsj",100);
			 	    	result.put("punishmentbz","");
				    	
			 	    	result.put("regularInspectionTimes",0);
			 	    	result.put("regularInspectionsj",100);
			 	    	result.put("inspectElevatorTimes",0);
			 	    	result.put("inspectElevatorTimes2",0);
			 	    	result.put("inspectElevatorsj",100);
			 	    	result.put("inspectElevatorbz","");
			 	    	result.put("acceptInspElevatorTimes",0);
			 	    	result.put("acceptInspElevatorsj",0);
			 	    	result.put("acceptInspElevatorbz","");
			 	    	result.put("maintenSceneInfoTimes",0);
			 	    	result.put("maintenSceneInfosj",0);
			 	    	result.put("maintenSceneInfobz","");
			 	    	
			 	    	


			 	    	result.put("maintenanceEleCount", "");
			 	    	result.put("maintenanceEleCountsj", 100);
			 	    	result.put("avgmaintenanceEleCount", "");
			 	    	result.put("avgmaintenanceEleCountsj", 100);
			 	    	result.put("infoComRatesj", 100);
			 	    	result.put("infoComRate", 100);
			 	    	result.put("infoComRatesj", 100);
			 	    	result.put("sweepCodeRate", 100);
			 	    	result.put("sweepCodeRatesj", 100);
			 	    	result.put("sweepCodeInTimeRate", 100);
			 	    	result.put("sweepCodeInTimeRatesj", 100);
			 	    	result.put("alarmDealwith", 0);
			 	    	result.put("alarmDealwithsj", 100);
			 	    	
				}
			 } catch (ActiveRecordException e) {	
				e.printStackTrace();
			}   	
		 }
		 
		 if(role == 16 || role == 17){ //���� 
			 try {
				info = UserExtInfo.findFirstBySql(UserExtInfo.class, "select userName,companyid from TwoCodeUserExtInfo where userid = ?", new Object[]{userid});
				if(info != null){
		 	    	//userName = info.getUserName();
		 	        companyid = info.getCompanyid();	
		 	    	}
			//	sql ="select t.*, regularInspectionTimes,regularInspectionsj,regularInspectionbz,inspectElevatorTimes,inspectElevatorTimes2,inspectElevatorsj,inspectElevatorbz,acceptInspElevatorTimes,acceptInspElevatorsj,acceptInspElevatorbz,maintenSceneInfoTimes,maintenSceneInfosj,maintenSceneInfobz from TwoCodeYwQuaCredRating  left join (select ywCompanyID,ratingDate,officeSpace, officeSpacesj,officeSpacebz, headQuarters,headQuarterssj,headQuartersbz,fixedTelOnDuty,fixedTelOnDutysj,fixedTelOnDutybz,telOnDutyunattendedTimes,telOnDutyunattendedsj,telOnDutyunattendedbz,enterpriseChangeTimes,enterpriseChangesj,enterpriseChangebz,enterpriseRecord,enterpriseRecordsj,malignantEventsTimes,malignantEventsTimes2,malignantEventsTimes3,malignantEventssj,malignantEventsbz,complaintsEventsTimes,complaintsEventsTimes2,complaintsEventssj,complaintsEventsbz,maintenBusinessTimes,maintenBusinesssj,maintenBusinessbz,honestTimes, honestsj,honestbz,punishmentTimes,punishmentTimes2,punishmentTimes3,punishmentTimes4,punishmentsj,punishmentbz,secondRescuePoint,secondRescuePointsj,secondRescuePointbz,positiveEnergyTimes,positiveEnergysj, positiveEnergybz,expertsSuggestionTimes,expertsSuggestionsj,expertsSuggestionbz,positiveWork,positiveWorksj,positiveWorkbz,remoteMonitor,remoteMonitorsj,remoteMonitorbz,elevatorInsurance,elevatorInsurancesj,elevatorInsurancebz,techinnovationTimes,techinnovationTimes2,techinnovationTimes3,techinnovationTimes4,techinnovationTimes5,techinnovationsj from TwoCodeYwQuaCredRating where  ratingDate = ? and ratingType = 0 and  ywCompanyID = ? )  t on TwoCodeYwQuaCredRating.ywCompanyID = t.ywCompanyID and  TwoCodeYwQuaCredRating.ratingDate = t.ratingDate where ratingCompanyId = ? and TwoCodeYwQuaCredRating.ratingDate = ? and TwoCodeYwQuaCredRating.ratingType = 1 and  TwoCodeYwQuaCredRating.ywCompanyID = ?";
				 sql ="select  * from TwoCodeYwQuaCredRating where   ratingDate = ? and ratingCompanyId = ? and ratingType = 1 and  ywCompanyID = ?";
				
			//	YwQuaCredRatingVO vo =YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class, "select * from TwoCodeYwQuaCredRating where ratingCompanyId = ? and ratingDate = ? and ratingType = ? and  ywCompanyID = ? ", new Object[]{companyid,ratingDate,1,ywCompanyID});
				YwQuaCredRatingVO vo =YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class, sql, new Object[]{ratingDate,companyid,ywCompanyID});
				if(vo != null){
					//���鵥����ֵ���Ŀ
					result.put("regularInspectionTimes",vo.getRegularInspectionTimes());
		 	    	result.put("regularInspectionsj",vo.getRegularInspectionsj());
		 	    	result.put("regularInspectionbz",vo.getRegularInspectionbz());
		 	    	//���鹲ͬ��ֵ���Ŀ
					result.put("inspectElevatorTimes",vo.getInspectElevatorTimes());
		 	    	result.put("inspectElevatorTimes2",vo.getInspectElevatorTimes2());
		 	    	result.put("inspectElevatorsj",vo.getInspectElevatorsj());
		 	    	result.put("inspectElevatorbz",vo.getInspectElevatorbz());
		 	    	result.put("acceptInspElevatorTimes",vo.getAcceptInspElevatorTimes());
		 	    	result.put("acceptInspElevatorsj",vo.getAcceptInspElevatorsj());
		 	    	result.put("acceptInspElevatorbz",vo.getAcceptInspElevatorbz());
		 	    	result.put("maintenSceneInfoTimes",vo.getMaintenSceneInfoTimes());
		 	    	result.put("maintenSceneInfosj",vo.getMaintenSceneInfosj());
		 	    	result.put("maintenSceneInfobz",vo.getMaintenSceneInfobz());
		 	    	
		 	    	
		 	    	
		 	   	//���Ǽ����ֵ���Ŀ
		 	    	
		 	    	result.put("officeSpace",vo.getOfficeSpace());
		 	    	result.put("officeSpacesj",vo.getOfficeSpacesj());
		 	    	result.put("officeSpacebz",vo.getOfficeSpacebz());
		 	    	result.put("headQuarters",vo.getHeadQuarters());
		 	    	result.put("headQuarterssj",vo.getHeadQuarterssj());
		 	    	result.put("headQuartersbz",vo.getHeadQuartersbz());
		 	    	result.put("fixedTelOnDuty",vo.getFixedTelOnDuty());
		 	    	result.put("fixedTelOnDutysj",vo.getFixedTelOnDutysj());
		 	    	result.put("fixedTelOnDutybz",vo.getFirstRescuebz());
		 	    	result.put("telOnDutyunattendedTimes",vo.getTelOnDutyunattendedTimes());
		 	    	result.put("telOnDutyunattendedsj",vo.getTelOnDutyunattendedsj());
		 	    	result.put("telOnDutyunattendedbz",vo.getTelOnDutyunattendedbz());
		 	    	result.put("enterpriseChangeTimes",vo.getEnterpriseChangeTimes());
		 	    	result.put("enterpriseChangesj",vo.getEnterpriseChangesj());
		 	    	result.put("enterpriseChangebz",vo.getEnterpriseChangebz());
		 	    	result.put("enterpriseRecord",vo.getEnterpriseRecord());
		 	    	result.put("enterpriseRecordsj",vo.getEnterpriseRecordsj());
		 	    	result.put("malignantEventsTimes",vo.getMalignantEventsTimes());
		 	    	result.put("malignantEventsTimes2",vo.getMalignantEventsTimes2());
		 	    	result.put("malignantEventsTimes3",vo.getMalignantEventsTimes3());
		 	    	result.put("malignantEventssj",vo.getMalignantEventssj());
		 	    	result.put("malignantEventsbz",vo.getMalignantEventsbz());
		 	    	result.put("honestTimes",vo.getHonestTimes());
		 	    	result.put("honestsj",vo.getHonestsj());
		 	    	result.put("honestbz",vo.getHonestbz());
		 	    	result.put("punishmentTimes",vo.getPunishmentTimes());
		 	    	result.put("punishmentTimes2",vo.getPunishmentTimes2());
		 	    	result.put("punishmentTimes3",vo.getPunishmentTimes3());
		 	    	result.put("punishmentTimes4",vo.getPunishmentTimes4());
		 	    	result.put("punishmentsj",vo.getPunishmentsj());
		 	    	result.put("punishmentbz",vo.getPunishmentbz());
		 	    	result.put("secondRescuePoint",vo.getSecondRescuePoint());
		 	    	result.put("secondRescuePointsj",vo.getSecondRescuePointsj());
		 	    	result.put("secondRescuePointbz",vo.getSecondRescuebz());
		 	    	result.put("positiveEnergyTimes",vo.getPositiveEnergyTimes());
		 	    	result.put("positiveEnergysj",vo.getPositiveEnergysj());
		 	    	result.put("positiveEnergybz",vo.getPositiveEnergybz());
		 	    	result.put("expertsSuggestionTimes",vo.getExpertsSuggestionTimes());
		 	    	result.put("expertsSuggestionsj",vo.getExpertsSuggestionsj());
		 	    	result.put("expertsSuggestionbz",vo.getExpertsSuggestionbz());
		 	    	result.put("positiveWork",vo.getPositiveWork());
		 	    	result.put("positiveWorksj",vo.getPositiveWorksj());
		 	    	result.put("positiveWorkbz",vo.getPositiveWorkbz());
		 	    	result.put("remoteMonitor",vo.getRemoteMonitor());
		 	    	result.put("remoteMonitorsj",vo.getRemoteMonitorsj());
		 	    	result.put("remoteMonitorbz",vo.getRemoteMonitorbz());
		 	    	result.put("elevatorInsurance",vo.getElevatorInsurance());
		 	    	result.put("elevatorInsurancesj",vo.getElevatorInsurancesj());
		 	    	result.put("elevatorInsurancebz",vo.getElevatorInsurancebz());
		 	    	result.put("techinnovationTimes",vo.getTechinnovationTimes());
		 	    	result.put("techinnovationTimes2",vo.getTechinnovationTimes2());
		 	    	result.put("techinnovationTimes3",vo.getTechinnovationTimes3());
		 	    	result.put("techinnovationTimes4",vo.getTechinnovationTimes4());
		 	    	result.put("techinnovationTimes5",vo.getTechinnovationTimes5());
		 	    	result.put("techinnovationsj",vo.getTechinnovationsj());
		 	    	 	
		 	    }
				else{  //Ϊ�յ�ʱ���ȡ����
			/*		YwQuaCredRatingConstant ycrcvo = YwQuaCredRatingConstant.findFirstBySql(YwQuaCredRatingConstant.class, "select officeSpace,officeSpacesj,fixedTelOnDuty,fixedTelOnDutysj from TwoCodeYwQuaCredRatConstant where ywCompanyID = ? ", new Object[]{ywCompanyID});
				    if(ycrcvo != null){
				    	result.put("officeSpace",ycrcvo.getOfficeSpace());
				    	result.put("officeSpacesj",ycrcvo.getOfficeSpacesj());
				    	result.put("fixedTelOnDuty",ycrcvo.getFixedTelOnDuty());
				    	result.put("fixedTelOnDutysj",ycrcvo.getFixedTelOnDutysj());
				    }
				    else{
				    	result.put("officeSpace",0);
				    	result.put("officeSpacesj",95);
				    	result.put("fixedTelOnDuty","");
				    	result.put("fixedTelOnDutysj",-10);
				       }
				*/    
				    	//Ϊ�յ�ʱ������ΪĬ��ֵ
					     result.put("officeSpace",0);
			    	     result.put("officeSpacesj",95);
			    	     result.put("fixedTelOnDuty","");
			    	     result.put("fixedTelOnDutysj",-10);
					
				    	result.put("officeSpacebz","");
			 	    	result.put("headQuarters",0);
			 	    	result.put("headQuarterssj",10);
			 	    	result.put("headQuartersbz","");
			 	    	result.put("fixedTelOnDutybz","");
			 	    	result.put("telOnDutyunattendedTimes",0);
			 	    	result.put("telOnDutyunattendedsj",0);
			 	    	result.put("telOnDutyunattendedbz","");
			 	    	result.put("enterpriseChangeTimes",0);
			 	    	result.put("enterpriseChangesj",0);
			 	    	result.put("enterpriseChangebz","");
			 	    	result.put("enterpriseRecord",0);
			 	    	result.put("enterpriseRecordsj",0);
			 	    	result.put("enterpriseRecordbz","");
			 	    	result.put("malignantEventsTimes",0);
			 	    	result.put("malignantEventsTimes2",0);
			 	    	result.put("malignantEventsTimes3",0);
			 	    	result.put("malignantEventssj",100);
			 	    	result.put("malignantEventsbz","");
			 	    	result.put("secondRescuePoint",0);
			 	    	result.put("secondRescuePointsj",0);
			 	    	result.put("secondRescuePointbz","");
			 	    	result.put("positiveEnergyTimes",0);
			 	    	result.put("positiveEnergysj",0);
			 	    	result.put("positiveEnergybz","");
			 	    	result.put("expertsSuggestionTimes",0);
			 	    	result.put("expertsSuggestionsj",0);
			 	    	result.put("expertsSuggestionbz","");
			 	    	result.put("positiveWork",0);
			 	    	result.put("positiveWorksj",0);
			 	    	result.put("positiveWorkbz","");
			 	    	result.put("remoteMonitor",0);
			 	    	result.put("remoteMonitorsj",0);
			 	    	result.put("remoteMonitorbz","");
			 	    	result.put("elevatorInsurance",0);
			 	    	result.put("elevatorInsurancesj",0);
			 	    	result.put("elevatorInsurancebz","");
			 	    	result.put("techinnovationTimes",0);
			 	    	result.put("techinnovationTimes2",0);
			 	    	result.put("techinnovationTimes3",0);
			 	    	result.put("techinnovationTimes4",0);
			 	    	result.put("techinnovationTimes5",0);
			 	    	result.put("techinnovationsj",0);
			 	    	result.put("techinnovationbz","");
			 	    	
			 	    	result.put("complaintsEventsTimes", 0);
			 	    	result.put("complaintsEventsTimes2",0);
			 	    	result.put("complaintsEventssj", 100);
			 	    	result.put("complaintsEventsbz", "");
			 	    	result.put("maintenBusinessTimes", 0);
			 	    	result.put("maintenBusinesssj", 0);
			 	    	result.put("maintenBusinessbz", "");
			 	    	result.put("honestTimes",0);
			 	    	result.put("honestsj",0);
			 	    	result.put("honestbz","");
			 	    	result.put("punishmentTimes",0);
			 	    	result.put("punishmentTimes2",0);
			 	    	result.put("punishmentTimes3",0);
			 	    	result.put("punishmentTimes4",0);
			 	    	result.put("punishmentsj",100);
			 	    	result.put("punishmentbz","");
				    	
			 	    	result.put("regularInspectionTimes",0);
			 	    	result.put("regularInspectionsj",100);
			 	    	result.put("regularInspectionbz","");
			 	    	result.put("inspectElevatorTimes",0);
			 	    	result.put("inspectElevatorTimes2",0);
			 	    	result.put("inspectElevatorsj",100);
			 	    	result.put("inspectElevatorbz","");
			 	    	result.put("acceptInspElevatorTimes",0);
			 	    	result.put("acceptInspElevatorsj",0);
			 	    	result.put("acceptInspElevatorbz","");
			 	    	result.put("maintenSceneInfoTimes",0);
			 	    	result.put("maintenSceneInfosj",0);
			 	    	result.put("maintenSceneInfobz","");
			 	    	
			 	    	result.put("maintenanceEleCount", "");
			 	    	result.put("maintenanceEleCountsj", 100);
			 	    	result.put("avgmaintenanceEleCount", "");
			 	    	result.put("avgmaintenanceEleCountsj", 100);
			 	    	result.put("infoComRatesj", 100);
			 	    	result.put("infoComRate", 100);
			 	    	result.put("infoComRatesj", 100);
			 	    	result.put("sweepCodeRate", 100);
			 	    	result.put("sweepCodeRatesj", 100);
			 	    	result.put("sweepCodeInTimeRate", 100);
			 	    	result.put("sweepCodeInTimeRatesj", 100);
			 	    	result.put("alarmDealwith", 0);
			 	    	result.put("alarmDealwithsj", 100);
				}
			 } catch (ActiveRecordException e) {	
				e.printStackTrace();
			}   	
		 }
 		
 		 return new JsonView(result);
 	}
 	
 	public View ywSysSetingsItemDetail(String ItemName,int ywCompanyID,String ratingDate){
 		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
 		 System.out.println(ItemName);
 		 System.out.println(ratingDate);
 		 int  role = userinfo.getRole();
 		 int userid = userinfo.getId();
 		 String sql ="";
 		 List<YwSysSetingsItemDetailVO> items = null;
 		 Map<String, Object> result = new HashMap<String, Object>();
 		 if("officeSpace".equals(ItemName)){
 		//	 sql = "select '�칫���' as itemName, officeSpace as itemContent,officeSpacebz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and tc.type= '���ʼ�' ";	 
 			
 			 sql = "select '�칫���' as itemName, convert(varchar,officeSpace)+'�O' as itemContent,officeSpacebz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and tc.type= '���ʼ�' ";	 
 			
 		 }
 		 else if("headQuarters".equals(ItemName)){
 	//		 sql = "select '�ܲ����ڵ�' as itemName, (case when headQuarters =0 then '�ɶ�' else '���' end )as itemContent,headQuartersbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and tc.type= '���ʼ�' ";	 	
 			
 			 sql = "select '�ܲ����ڵ�' as itemName, (case when headQuarters =0 then '�ɶ�' else '���' end )as itemContent,headQuartersbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and tc.type= '���ʼ�' ";	 	
 			
 		 }
 		 else if("maintenanceEleCount".equals(ItemName)){
 		//	 sql = "select 'ά����������' as itemName, maintenanceEleCount as itemContent,'' as itembz, '��ȫ����ƽ̨' as ratingUserName,'��ȫ����ƽ̨' as ratingCompanyName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =0  ";	 	
 			
 			 sql = "select 'ά����������' as itemName, convert(varchar,maintenanceEleCount)+'̨' as itemContent,'' as itembz, '��ȫ����ƽ̨' as ratingUserName,'��ȫ����ƽ̨' as ratingCompanyName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =0  ";	 	
 			
 		 }
 		else if("avgmaintenanceEleCount".equals(ItemName)){
 	 	//	 sql = "select '�˾�ά������' as itemName, avgmaintenanceEleCount as itemContent,'' as itembz, '��ȫ����ƽ̨' as ratingUserName,'��ȫ����ƽ̨' as ratingCompanyName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =0  ";	 	
 			
 			sql = "select '�˾�ά������' as itemName, avgmaintenanceEleCount as itemContent,'' as itembz, '��ȫ����ƽ̨' as ratingUserName,'��ȫ����ƽ̨' as ratingCompanyName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =0  ";	 	
 			 	 
 		 }
 		else if("fixedTelOnDuty".equals(ItemName)){
	 	//	 sql = "select 'ֵ��̶��绰' as itemName, fixedTelOnDuty as itemContent,fixedTelOnDutybz as itembz, (select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and tc.type= '���ʼ�'  ";	 			 	 
 			
 			sql = "select 'ֵ��̶��绰' as itemName, fixedTelOnDuty as itemContent,fixedTelOnDutybz as itembz, (select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and tc.type= '���ʼ�'  ";	 			 	 
 			
 		}
 		else if("telOnDutyunattendedTimes".equals(ItemName)){
	 	//	 sql = "select '�绰ֵ�����˽���' as itemName, telOnDutyunattendedTimes as itemContent,telOnDutyunattendedbz as itembz, (select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and tc.type= '���ʼ�'  ";	 			 	 
 			
 			sql = "select '�绰ֵ�����˽���' as itemName,  convert(varchar,telOnDutyunattendedTimes)+'��' as itemContent,telOnDutyunattendedbz as itembz, (select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and tc.type= '���ʼ�'  ";	 			 	 
 			
 		}
 		else if("enterpriseChangeTimes".equals(ItemName)){
	 	//	 sql = "select 'ά����ҵ���' as itemName, enterpriseChangeTimes as itemContent,enterpriseChangebz as itembz, (select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and tc.type= '���ʼ�'  ";	 			 	 
 			
 			sql = "select 'ά����ҵ���' as itemName, convert(varchar,enterpriseChangeTimes)+'��' as itemContent,enterpriseChangebz as itembz, (select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and tc.type= '���ʼ�'  ";	 			 	 
 			
 		}
 		else if("enterpriseRecord".equals(ItemName)){
	 //		 sql = "select '��ҵ����' as itemName, (case when headQuarters =0 then '��' else '��' end ) as itemContent,enterpriseRecordbz as itembz, (select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and tc.type= '���ʼ�'  ";	 			 	 
 			
 			sql = "select '��ҵ����' as itemName, (case when headQuarters =0 then '��' else '��' end ) as itemContent,enterpriseRecordbz as itembz, (select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and tc.type= '���ʼ�'  ";	 			 	 
 			
 		}
 		 else if("infoComRate".equals(ItemName)){
 	//		 sql = "select '��Ϣ������' as itemName, infoComRate as itemContent,'' as itembz, '��ȫ����ƽ̨' as ratingUserName,'��ȫ����ƽ̨' as ratingCompanyName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =0  ";	 	
 			
 			 sql = "select '��Ϣ������' as itemName, infoComRate as itemContent,'' as itembz, '��ȫ����ƽ̨' as ratingUserName,'��ȫ����ƽ̨' as ratingCompanyName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =0  ";	 	
 			
 		 }
 		else if("sweepCodeRate".equals(ItemName)){
 	 	//	 sql = "select 'ɨ��ά����' as itemName, sweepCodeRate as itemContent,'' as itembz, '��ȫ����ƽ̨' as ratingUserName,'��ȫ����ƽ̨' as ratingCompanyName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =0  ";	 	
 			
 			sql = "select 'ɨ��ά����' as itemName, sweepCodeRate as itemContent,'' as itembz, '��ȫ����ƽ̨' as ratingUserName,'��ȫ����ƽ̨' as ratingCompanyName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =0  ";	 	
 				 	 
 		 }
 		else if("sweepCodeInTimeRate".equals(ItemName)){
	 	//	 sql = "select '��ʱɨ��ά����' as itemName, sweepCodeInTimeRate as itemContent,'' as itembz, '��ȫ����ƽ̨' as ratingUserName,'��ȫ����ƽ̨' as ratingCompanyName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =0  ";	 	
 			
 			sql = "select '��ʱɨ��ά����' as itemName, sweepCodeInTimeRate as itemContent,'' as itembz, '��ȫ����ƽ̨' as ratingUserName,'��ȫ����ƽ̨' as ratingCompanyName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =0  ";	 	
 				 
		 }
 		else if("alarmDealwith".equals(ItemName)){
	 	//	 sql = "select 'ƽ̨�����������' as itemName, alarmDealwith as itemContent,'' as itembz, '��ȫ����ƽ̨' as ratingUserName,'��ȫ����ƽ̨' as ratingCompanyName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =0  ";	 	
 			
 			sql = "select 'ƽ̨�����������' as itemName, convert(varchar,alarmDealwith)+'̨' as itemContent,'' as itembz, '��ȫ����ƽ̨' as ratingUserName,'��ȫ����ƽ̨' as ratingCompanyName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =0  ";	 	
 				 	 
		 }
 		else if("regularInspectionTimes".equals(ItemName)){
 		//	 sql = "select '���ݶ��ڼ������' as itemName, regularInspectionTimes as itemContent,regularInspectionbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and tc.type= '����' ";	 
 			
 			sql = "select '���ݶ��ڼ������' as itemName, convert(varchar,regularInspectionTimes)+'̨' as itemContent,regularInspectionbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and tc.type= '����' ";	 
 			
 			
 		}
 		else if("inspectElevatorTimes".equals(ItemName)){
		//	 sql = "select '���õ��ݼල������' as itemName, (convert(varchar,inspectElevatorTimes)+'��������'+';'+convert(varchar,inspectElevatorTimes2)+'��·�̽�') as itemContent,inspectElevatorbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and ( tc.type= '����' or tc.type= '�ʼ�') ";	 
 			
 			sql = "select '���õ��ݼල������' as itemName, (convert(varchar,inspectElevatorTimes)+'̨:��������<br>'+convert(varchar,inspectElevatorTimes2)+'̨:��·�̽�') as itemContent,inspectElevatorbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and ( tc.type= '����' or tc.type= '�ʼ�') ";	 
 			
 		}
 		else if("acceptInspElevatorTimes".equals(ItemName)){
		//	 sql = "select '���ܼල������' as itemName, acceptInspElevatorTimes as itemContent,acceptInspElevatorbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and ( tc.type= '����' or tc.type= '�ʼ�') ";	 
 			
 			sql = "select '���ܼල������' as itemName, convert(varchar,acceptInspElevatorTimes)+'��' as itemContent,acceptInspElevatorbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and ( tc.type= '����' or tc.type= '�ʼ�') ";	 
 			
 		}
 		else if("maintenSceneInfoTimes".equals(ItemName)){
		//	 sql = "select 'ά���ֳ��������' as itemName, maintenSceneInfoTimes as itemContent,maintenSceneInfobz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and ( tc.type= '����' or tc.type= '�ʼ�') ";	 
 			
 			sql = "select 'ά���ֳ��������' as itemName, convert(varchar,maintenSceneInfoTimes)+'��' as itemContent,maintenSceneInfobz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and ( tc.type= '����' or tc.type= '�ʼ�') ";	 
 			
         }
 		else if("malignantEventsTimes".equals(ItemName)){
		//	 sql = "select '��ά��ԭ������ȫ�¹��ش����Ӱ���¼�' as itemName, (convert(varchar,malignantEventsTimes)+'һ���¹�'+convert(varchar,malignantEventsTimes2)+'�ϴ��¹� '+convert(varchar,malignantEventsTimes3)+'�ش��¹�' )as itemContent,malignantEventsbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and  tc.type= '���ʼ�'  ";	 
 			
 			sql = "select '��ά��ԭ������ȫ�¹��ش����Ӱ���¼�' as itemName, (convert(varchar,malignantEventsTimes)+'��:һ���¹�<br>'+convert(varchar,malignantEventsTimes2)+'��:�ϴ��¹�<br>'+convert(varchar,malignantEventsTimes3)+'��:�ش��¹�') as itemContent,malignantEventsbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and  tc.type= '���ʼ�'  ";	 
 			
 		}
 		else if("complaintsEventsTimes".equals(ItemName)){
		//	 sql = "select '��ά��������������Ͷ��' as itemName, (convert(varchar,complaintsEventsTimes)+'һ��Ͷ�� '+convert(varchar,complaintsEventsTimes2)+'����Ͷ�� ' )as itemContent,complaintsEventsbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and ( tc.type= '���ʼ�' or tc.type= '�ʼ�') ";	 
 			
 			sql = "select '��ά��������������Ͷ��' as itemName, (convert(varchar,complaintsEventsTimes)+'��:һ��Ͷ��<br> '+convert(varchar,complaintsEventsTimes2)+'��:����Ͷ�� ' )as itemContent,complaintsEventsbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and ( tc.type= '���ʼ�' or tc.type= '�ʼ�') ";	 
 			
 		}
 		else if("maintenBusinessTimes".equals(ItemName)){
		//	 sql = "select 'ά��ҵ�����' as itemName, maintenBusinessTimes as itemContent,maintenBusinessbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and ( tc.type= '���ʼ�' or tc.type= '�ʼ�') ";	 
 			
 			sql = "select 'ά��ҵ�����' as itemName, convert(varchar,maintenBusinessTimes)+'��' as itemContent,maintenBusinessbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and ( tc.type= '���ʼ�' or tc.type= '�ʼ�') ";	 
 			
 		}
 		else if("honestTimes".equals(ItemName)){
		//	 sql = "select '��ʵ�������' as itemName, honestTimes as itemContent,honestbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and ( tc.type= '���ʼ�' or tc.type= '�ʼ�') ";	 
 			
 			sql = "select '��ʵ�������' as itemName, convert(varchar,honestTimes)+'��' as itemContent,honestbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and ( tc.type= '���ʼ�' or tc.type= '�ʼ�') ";	 
 			
 		}
 		else if("punishmentTimes".equals(ItemName)){
		//	 sql = "select '�����鴦���������' as itemName, (convert(varchar,punishmentTimes)+'ά��������ά������������'+convert(varchar,punishmentTimes2)+'���´��������ͨ�� '+convert(varchar,punishmentTimes3)+'�������´���ָ���ͨ��2�μ�����'+convert(varchar,punishmentTimes4)+'Υ�����ɡ����漰�����淶' )as itemContent,punishmentbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and ( tc.type= '���ʼ�' or tc.type= '�ʼ�') ";	 
 			
 			sql = "select '�����鴦���������' as itemName, (convert(varchar,punishmentTimes)+'��:ά��������ά������������<br>'+convert(varchar,punishmentTimes2)+'��:���´��������ͨ��<br> '+convert(varchar,punishmentTimes3)+'��:�������´���ָ���ͨ��2�μ�����<br>'+convert(varchar,punishmentTimes4)+'��:Υ�����ɡ����漰�����淶' )as itemContent,punishmentbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and ( tc.type= '���ʼ�' or tc.type= '�ʼ�') ";	 
 			
 		}
 		else if("firstRescueTimes".equals(ItemName)){
	 	//	 sql = "select 'һ����Ԯ���' as itemName, firstRescueTimes as itemContent,'' as itembz, '96933ƽ̨' as ratingUserName,'96933ƽ̨' as ratingCompanyName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =0  ";	 	 		 	 
 			
 			sql = "select 'һ����Ԯ���' as itemName, convert(varchar,firstRescueTimes)+'��' as itemContent,'' as itembz, '96933ƽ̨' as ratingUserName,'96933ƽ̨' as ratingCompanyName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =0  ";	 	 		 	 
 			
 		}
 		else if("secondRescueTimes".equals(ItemName)){
	 //		 sql = "select '������Ԯ���' as itemName, (convert(varchar,secondRescueTimes)+'δʵʩ'+';'+convert(varchar,secondRescueTimes2)+'ʵʩ)as itemContent,'' as itembz, '96933ƽ̨' as ratingUserName,'96933ƽ̨' as ratingCompanyName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =0  ";	 	
 			
 			sql = "select '������Ԯ���' as itemName, (convert(varchar,secondRescueTimes)+'��:δʵʩ<br>'+convert(varchar,secondRescueTimes2)+'��:ʵʩ')as itemContent,'' as itembz, '96933ƽ̨' as ratingUserName,'96933ƽ̨' as ratingCompanyName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =0  ";	 	
 				 
		 }
 		 else if("secondRescuePoint".equals(ItemName)){
 	//		 sql = "select '���������Ԯ����' as itemName, secondRescuePoint as itemContent,secondRescuePointbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and tc.type= '���ʼ�' ";	 	
 			
 			 sql = "select '���������Ԯ����' as itemName, convert(varchar,secondRescuePoint)+'��' as itemContent,secondRescuePointbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and tc.type= '���ʼ�' ";	 	
 			
 		 }
 		else if("rescueResponseTimes".equals(ItemName)){
	 //		 sql = "select 'Ӧ����Ԯ��Ӧ���' as itemName, rescueResponseTimes as itemContent,'' as itembz, '96933ƽ̨' as ratingUserName,'96933ƽ̨' as ratingCompanyName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =0  ";	 	 		 	 
 			
 			sql = "select 'Ӧ����Ԯ��Ӧ���' as itemName, convert(varchar,rescueResponseTimes)+'��' as itemContent,'' as itembz, '96933ƽ̨' as ratingUserName,'96933ƽ̨' as ratingCompanyName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =0  ";	 	 		 	 
 			
 		}
		else if("tiringPeopleTimes".equals(ItemName)){
	 	//	 sql = "select '��ά�������������˹���' as itemName, tiringPeopleTimes as itemContent,'' as itembz, '96933ƽ̨' as ratingUserName,'96933ƽ̨' as ratingCompanyName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =0  ";	 	
			
			sql = "select '��ά�������������˹���' as itemName, convert(varchar,tiringPeopleTimes)+'��' as itemContent,'' as itembz, '96933ƽ̨' as ratingUserName,'96933ƽ̨' as ratingCompanyName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =0  ";	 	
				 	 
		 }
		 else if("positiveEnergyTimes".equals(ItemName)){
 	//		 sql = "select '�׼��ײߡ��ٱ�Υ��Υ�桢��ҵǱ����' as itemName, positiveEnergyTimes as itemContent,positiveEnergybz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and tc.type= '���ʼ�' ";	 	
			 
			 sql = "select '�׼��ײߡ��ٱ�Υ��Υ�桢��ҵǱ����' as itemName, convert(varchar,positiveEnergyTimes)+'��' as itemContent,positiveEnergybz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and tc.type= '���ʼ�' ";	 	
			 
		 }
		 else if("expertsSuggestionTimes".equals(ItemName)){
 	//		 sql = "select '�ṩר�Ҽ�����֧�֣�������ϼ��¹ʵ��鴦��' as itemName, expertsSuggestionTimes as itemContent,expertsSuggestionbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and tc.type= '���ʼ�' ";	 	
			
			 sql = "select '�ṩר�Ҽ�����֧�֣�������ϼ��¹ʵ��鴦��' as itemName, convert(varchar,expertsSuggestionTimes)+'��' as itemContent,expertsSuggestionbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and tc.type= '���ʼ�' ";	 	
			
		 }
		 else if("positiveWork".equals(ItemName)){
 		//	 sql = "select '�����нӼ�ܲ���ָ������ά��' as itemName, positiveWork as itemContent,positiveWorkbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and tc.type= '���ʼ�' ";	 	
			
			 sql = "select '�����нӼ�ܲ���ָ������ά��' as itemName, convert(varchar,positiveWork)+'̨' as itemContent,positiveWorkbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and tc.type= '���ʼ�' ";	 	
			 
		 }
		 else if("remoteMonitor".equals(ItemName)){
 	//		 sql = "select '����Զ�̼�ء������˱���' as itemName, remoteMonitor as itemContent,remoteMonitorbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and tc.type= '���ʼ�' ";	 	
			
			 sql = "select '����Զ�̼�ء������˱���' as itemName, convert(varchar,remoteMonitor)+'̨' as itemContent,remoteMonitorbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and tc.type= '���ʼ�' ";	 	
			 
		 }
		 else if("elevatorInsurance".equals(ItemName)){
 		//	 sql = "select '����������α���' as itemName, elevatorInsurance as itemContent,elevatorInsurancebz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and tc.type= '���ʼ�' ";	 	
			 
			 sql = "select '����������α���' as itemName, convert(varchar,elevatorInsurance)+'̨' as itemContent,elevatorInsurancebz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and tc.type= '���ʼ�' ";	 	
			
		 }
		 else if("techinnovation".equals(ItemName)){
 		//	 sql = "select '������չ�������¼���������' as itemName, (convert(varchar,techinnovationTimes)+'1��'+convert(varchar,techinnovationTimes2)+'2��'+convert(varchar,techinnovationTimes3)+'3��'+convert(varchar,techinnovationTimes4)+'4��'+convert(varchar,techinnovationTimes5)+'5��') as itemContent,secondRescuePointbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=substring(CONVERT(varchar(12), getdate(), 120),0,8) and ratingType =1 and tc.type= '���ʼ�' ";	 	
			 
			 sql = "select '������չ�������¼���������' as itemName, (convert(varchar,techinnovationTimes)+'��:1��<br>'+convert(varchar,techinnovationTimes2)+'��:2��<br>'+convert(varchar,techinnovationTimes3)+'��:3��<br>'+convert(varchar,techinnovationTimes4)+'��:4��<br>'+convert(varchar,techinnovationTimes5)+'��:5��') as itemContent,techinnovationbz as itembz,(select companyName from twoCodeCompanyinfo where id = ratingCompanyId) as ratingCompanyName, ratingUserName,CONVERT(varchar(100),detailratingDate, 120) as detailratingDate  from TwoCodeYwQuaCredRating left join TwoCodeCompanyInfo tc on TwoCodeYwQuaCredRating.ratingCompanyId =tc.id where ywCompanyID = ?  and  ratingDate=? and ratingType =1 and tc.type= '���ʼ�' ";	 	
			
		 }
		 else{
			 result.put("rows", null);
			 return new JsonView(result);
		 }
 		
 		try {
 			if(role != 22 && role != 23){
 		 		UserInfoVO userInfoVO;
 					int companyId =0;
 					userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
 					if(userInfoVO != null){
 						companyId = userInfoVO.getCompanyId();
 						sql = sql+" and ratingCompanyId ="+companyId;	 
 			 		
 					}
 			}
			items = YwSysSetingsItemDetailVO.findBySql(YwSysSetingsItemDetailVO.class, sql,  new Object[]{ywCompanyID,ratingDate});
		} catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		
		
		 
		 result.put("rows", items);
		 return new JsonView(result);
 		 
 	}
 	
 	public View kpCompanyinfo(){
		List<CompanyInfo> items =null;
		try {	
			 items = CompanyInfo.findBySql(CompanyInfo.class,"select id,companyName from TwoCodeCompanyInfo where type = '�ʼ�' or type ='����' or type='���ʼ�' order by type desc ", null);	
			} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JsonView(items);
	}
 	
 	
 	public View queryKpDetailinfoList(YwQuaCredRatingVO info){
 		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
 		 
 		 int  role = userinfo.getRole();
 		 int userid = userinfo.getId();
 		 
 		YwQuaCredRatingVO items = null;
 		int ratingCompanyId =0;
 		int ywCompanyId= 0 ;
 		String ratingDate="";
 		
 		ratingCompanyId = info.getRatingCompanyId();
 		ywCompanyId= info.getYwCompanyID();
 		ratingDate = info.getRatingDate();
 		String sql="";
 		CompanyInfoVO ratingCompanyIdVO = null;
 		String ratingCompanyIdType="";
 		 Map<String, Object> result = new HashMap<String, Object>();
 		try {	
 		 
 			ratingCompanyIdVO=CompanyInfoVO.findFirstBySql(CompanyInfoVO.class, "select type from  TwoCodeCompanyInfo where id = ?", new Object[]{ratingCompanyId});
 			if(ratingCompanyIdVO != null)
 				ratingCompanyIdType =ratingCompanyIdVO.getType();
 			
 		  if("���ʼ�".equals(ratingCompanyIdType)){	
 		 		  sql = "select officeSpace,officeSpacesj,officeSpacebz,headQuarters,headQuarterssj,headQuartersbz,fixedTelOnDuty,fixedTelOnDutysj,fixedTelOnDutybz,telOnDutyunattendedTimes,telOnDutyunattendedsj,telOnDutyunattendedbz,enterpriseChangeTimes,enterpriseChangesj,enterpriseChangebz,enterpriseRecord,enterpriseRecordsj,enterpriseRecordbz,malignantEventsTimes,malignantEventsTimes2,malignantEventsTimes3,malignantEventssj,malignantEventsbz,complaintsEventsTimes,complaintsEventsTimes2,complaintsEventssj,complaintsEventsbz,maintenBusinessTimes,maintenBusinesssj,maintenBusinessbz,honestTimes,honestsj,honestbz,punishmentTimes,punishmentTimes2,punishmentTimes3,punishmentTimes4,punishmentsj,punishmentbz,secondRescuePoint,secondRescuePointsj,secondRescuePointbz,positiveEnergyTimes,positiveEnergysj,positiveEnergybz,expertsSuggestionTimes,expertsSuggestionsj,expertsSuggestionbz,positiveWork,positiveWorksj,positiveWorkbz,remoteMonitor,remoteMonitorsj,remoteMonitorbz,elevatorInsurance,elevatorInsurancesj,elevatorInsurancebz,techinnovationTimes,techinnovationTimes2,techinnovationTimes3,techinnovationTimes4,techinnovationTimes5,techinnovationsj,techinnovationbz from TwoCodeYwQuaCredRating where ratingType =1 and ratingDate = ? and ratingCompanyId =? and ywCompanyID = ? ";
 		 		
 		 				items = YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class,sql, new Object[]{ratingDate,ratingCompanyId,ywCompanyId});	
 		 				if(items != null){
 		 					result.put("officeSpace",items.getOfficeSpace());
 				 	    	result.put("officeSpacesj",items.getOfficeSpacesj()-100);
 				 	    	result.put("officeSpacebz",items.getOfficeSpacebz());
 				 	    	result.put("headQuarters",items.getHeadQuarters());
 				 	    	result.put("headQuarterssj",items.getHeadQuarterssj());
 				 	    	result.put("headQuartersbz",items.getHeadQuartersbz());
 				 	    	result.put("fixedTelOnDuty",items.getFixedTelOnDuty());
 				 	    	result.put("fixedTelOnDutysj",items.getFixedTelOnDutysj());
 				 	    	result.put("fixedTelOnDutybz",items.getFixedTelOnDutybz());
 				 	    	result.put("telOnDutyunattendedTimes",items.getTelOnDutyunattendedTimes());
 				 	    	result.put("telOnDutyunattendedsj",items.getTelOnDutyunattendedsj());
 				 	    	result.put("telOnDutyunattendedbz",items.getTelOnDutyunattendedbz());
 				 	    	result.put("enterpriseChangeTimes",items.getEnterpriseChangeTimes());
 				 	    	result.put("enterpriseChangesj",items.getEnterpriseChangesj());
 				 	    	result.put("enterpriseChangebz",items.getEnterpriseChangebz());
 				 	    	result.put("enterpriseRecord",items.getEnterpriseRecord());
 				 	    	result.put("enterpriseRecordsj",items.getEnterpriseRecordsj());
 				 	    	result.put("enterpriseRecordbz",items.getEnterpriseRecordbz());
 				 	    	result.put("malignantEventsTimes",items.getMalignantEventsTimes());
 				 	    	result.put("malignantEventsTimes2",items.getMalignantEventsTimes2());
 				 	    	result.put("malignantEventsTimes3",items.getMalignantEventsTimes3());
 				 	    	result.put("malignantEventssj",items.getMalignantEventssj()-100);  
 				 	    	result.put("malignantEventsbz",items.getMalignantEventsbz());
 				 	    	result.put("secondRescuePoint",items.getSecondRescuePoint());
 				 	    	result.put("secondRescuePointsj",items.getSecondRescuePointsj());
 				 	    	result.put("secondRescuePointbz",items.getSecondRescuePointbz());
 				 	    	result.put("positiveEnergyTimes",items.getPositiveEnergyTimes());
 				 	    	result.put("positiveEnergysj",items.getPositiveEnergysj());
 				 	    	result.put("positiveEnergybz",items.getPositiveEnergybz());
 				 	    	result.put("expertsSuggestionTimes",items.getExpertsSuggestionTimes());
 				 	    	result.put("expertsSuggestionsj",items.getExpertsSuggestionsj());
 				 	    	result.put("expertsSuggestionbz",items.getExpertsSuggestionbz());
 				 	    	result.put("positiveWork",items.getPositiveWork());
 				 	    	result.put("positiveWorksj",items.getPositiveWorksj());
 				 	    	result.put("positiveWorkbz",items.getPositiveWorkbz());
 				 	    	result.put("remoteMonitor",items.getRemoteMonitor());
 				 	    	result.put("remoteMonitorsj",items.getRemoteMonitorsj());
 				 	    	result.put("remoteMonitorbz",items.getRemoteMonitorbz());
 				 	    	result.put("elevatorInsurance",items.getElevatorInsurance());
 				 	    	result.put("elevatorInsurancesj",items.getElevatorInsurancesj());
 				 	    	result.put("elevatorInsurancebz",items.getElevatorInsurancebz());
 				 	    	result.put("techinnovationTimes",items.getTechinnovationTimes());
 				 	    	result.put("techinnovationTimes2",items.getTechinnovationTimes2());
 				 	    	result.put("techinnovationTimes3",items.getTechinnovationTimes3());
 				 	    	result.put("techinnovationTimes4",items.getTechinnovationTimes4());
 				 	    	result.put("techinnovationTimes5",items.getTechinnovationTimes5());
 				 	    	result.put("techinnovationsj",items.getTechinnovationsj());
 				 	    	result.put("techinnovationbz",items.getTechinnovationbz());
 				 	    	
 				 	    //��������ͬ��ֵ���Ŀ
 				 	    	result.put("complaintsEventsTimes", items.getComplaintsEventsTimes());
 				 	    	result.put("complaintsEventsTimes2",items.getComplaintsEventsTimes2());
 				 	    	result.put("complaintsEventssj", items.getComplaintsEventssj()-100);
 				 	    	result.put("complaintsEventsbz", items.getComplaintsEventsbz());
 				 	    	result.put("maintenBusinessTimes", items.getMaintenBusinessTimes());
 				 	    	result.put("maintenBusinesssj", items.getMaintenBusinesssj());
 				 	    	result.put("maintenBusinessbz", items.getMaintenBusinessbz());
 				 	    	result.put("honestTimes",items.getHonestTimes());
 				 	    	result.put("honestsj",items.getHonestsj());
 				 	    	result.put("honestbz",items.getHonestbz());
 				 	    	result.put("punishmentTimes",items.getPunishmentTimes());
 				 	    	result.put("punishmentTimes2",items.getPunishmentTimes2());
 				 	    	result.put("punishmentTimes3",items.getPunishmentTimes3());
 				 	    	result.put("punishmentTimes4",items.getPunishmentTimes4());
 				 	    	result.put("punishmentsj",items.getPunishmentsj()-100);
 				 	    	result.put("punishmentbz",items.getPunishmentbz());
 		 				}
 		 			}
 			
 		 if("�ʼ�".equals(ratingCompanyIdType)){	
 		  sql = "select inspectElevatorTimes,inspectElevatorTimes2,inspectElevatorsj,inspectElevatorbz,acceptInspElevatorTimes,acceptInspElevatorsj,acceptInspElevatorbz,maintenSceneInfoTimes,maintenSceneInfosj,maintenSceneInfobz,complaintsEventsTimes,complaintsEventsTimes2,complaintsEventssj,complaintsEventsbz,maintenBusinessTimes,maintenBusinesssj,maintenBusinessbz,honestTimes,honestsj,honestbz,punishmentTimes,punishmentTimes2,punishmentTimes3,punishmentTimes4,punishmentsj,punishmentbz from TwoCodeYwQuaCredRating where ratingType =1 and ratingDate = ? and ratingCompanyId =? and ywCompanyID = ? ";
 		
 		
 				items = YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class,sql, new Object[]{ratingDate,ratingCompanyId,ywCompanyId});	
 				if(items != null){
 					result.put("inspectElevatorTimes",items.getInspectElevatorTimes());
		 	    	result.put("inspectElevatorTimes2",items.getInspectElevatorTimes2());
		 	    	result.put("inspectElevatorsj",items.getInspectElevatorsj()-100);
		 	    	result.put("inspectElevatorbz",items.getInspectElevatorbz());
		 	    	result.put("acceptInspElevatorTimes",items.getAcceptInspElevatorTimes());
		 	    	result.put("acceptInspElevatorsj",items.getAcceptInspElevatorsj());
		 	    	result.put("acceptInspElevatorbz",items.getAcceptInspElevatorbz());
		 	    	result.put("maintenSceneInfoTimes",items.getMaintenSceneInfoTimes());
		 	    	result.put("maintenSceneInfosj",items.getMaintenSceneInfosj());
		 	    	result.put("maintenSceneInfobz",items.getMaintenSceneInfobz());
 					result.put("complaintsEventsTimes", items.getComplaintsEventsTimes());
		 	    	result.put("complaintsEventsTimes2",items.getComplaintsEventsTimes2());
		 	    	result.put("complaintsEventssj", items.getComplaintsEventssj()-100);
		 	    	result.put("complaintsEventsbz", items.getComplaintsEventsbz());
		 	    	result.put("maintenBusinessTimes", items.getMaintenBusinessTimes());
		 	    	result.put("maintenBusinesssj", items.getMaintenBusinesssj());
		 	    	result.put("maintenBusinessbz", items.getMaintenBusinessbz());
		 	    	result.put("honestTimes",items.getHonestTimes());
		 	    	result.put("honestsj",items.getHonestsj());
		 	    	result.put("honestbz",items.getHonestbz());
		 	    	result.put("punishmentTimes",items.getPunishmentTimes());
		 	    	result.put("punishmentTimes2",items.getPunishmentTimes2());
		 	    	result.put("punishmentTimes3",items.getPunishmentTimes3());
		 	    	result.put("punishmentTimes4",items.getPunishmentTimes4());
		 	    	result.put("punishmentsj",items.getPunishmentsj()-100);
		 	    	result.put("punishmentbz",items.getPunishmentbz());
 				}
 			}
 		if("����".equals(ratingCompanyIdType)){
 			 sql = "select regularInspectionTimes,regularInspectionsj,regularInspectionbz,inspectElevatorTimes,inspectElevatorTimes2,inspectElevatorsj,inspectElevatorbz,acceptInspElevatorTimes,acceptInspElevatorsj,acceptInspElevatorbz,maintenSceneInfoTimes,maintenSceneInfosj,maintenSceneInfobz from TwoCodeYwQuaCredRating where ratingType =1 and ratingDate = ? and ratingCompanyId =? and ywCompanyID = ? ";
 	 		
 	 		
				items = YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class,sql, new Object[]{ratingDate,ratingCompanyId,ywCompanyId});	
				if(items != null){
					result.put("regularInspectionTimes",items.getRegularInspectionTimes());
		 	    	result.put("regularInspectionsj",items.getRegularInspectionsj()-100);
		 	    	result.put("regularInspectionbz",items.getRegularInspectionbz());
					result.put("inspectElevatorTimes",items.getInspectElevatorTimes());
		 	    	result.put("inspectElevatorTimes2",items.getInspectElevatorTimes2());
		 	    	result.put("inspectElevatorsj",items.getInspectElevatorsj()-100);
		 	    	result.put("inspectElevatorbz",items.getInspectElevatorbz());
		 	    	result.put("acceptInspElevatorTimes",items.getAcceptInspElevatorTimes());
		 	    	result.put("acceptInspElevatorsj",items.getAcceptInspElevatorsj());
		 	    	result.put("acceptInspElevatorbz",items.getAcceptInspElevatorbz());
		 	    	result.put("maintenSceneInfoTimes",items.getMaintenSceneInfoTimes());
		 	    	result.put("maintenSceneInfosj",items.getMaintenSceneInfosj());
		 	    	result.put("maintenSceneInfobz",items.getMaintenSceneInfobz());
					
				}
 		}
 		}catch (ActiveRecordException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
		return new JsonView(result);
	}
 	
	public View kpinfoListMul(){
		String result = "failure";
 		UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		int userid = userinfo.getId();
		int  role = userinfo.getRole();
		
		List<YwKaoPingInfo> items =null;
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM");
		
		String s = format1.format(new Date()); 
		try {	
			if(role == 30 || role ==31)
			items = YwKaoPingInfo.findBySql(YwKaoPingInfo.class,"select t.id,t.ratingCompanyId,t.ywCompanyID,t.ratingDate,tc.type,(select companyName from twocodeCompanyInfo where id =t.ratingCompanyId ) as ratingCompanyName,(select companyName from twocodeCompanyInfo where id =t.ywCompanyID ) as ywCompanyName,ratingUserName,detailratingDate,(officeSpacesj+headQuarterssj+fixedTelOnDutysj+telOnDutyunattendedsj+enterpriseChangesj+enterpriseRecordsj-100)   as scoreTotal  from TwoCodeYwQuaCredRating  t left join  twocodecompanyinfo tc on t.ratingCompanyId = tc.id  where  tc.type = '���ʼ�' and ratingType = 1 and t.ratingDate = \'"+s+"\'", null);	
			if(role == 10 || role ==11)
			items = YwKaoPingInfo.findBySql(YwKaoPingInfo.class,"select t.id,t.ratingCompanyId,t.ywCompanyID,t.ratingDate,tc.type,(select companyName from twocodeCompanyInfo where id =t.ratingCompanyId ) as ratingCompanyName,(select companyName from twocodeCompanyInfo where id =t.ywCompanyID ) as ywCompanyName,ratingUserName,detailratingDate,(inspectElevatorsj+acceptInspElevatorsj+maintenSceneInfosj+complaintsEventssj+maintenBusinesssj+honestsj+punishmentsj -300)   as scoreTotal  from TwoCodeYwQuaCredRating  t left join  twocodecompanyinfo tc on t.ratingCompanyId = tc.id  where  tc.type = '�ʼ�' and ratingType = 1 and t.ratingDate = \'"+s+"\'", null);	
			if(role == 16 || role ==17)
			items = YwKaoPingInfo.findBySql(YwKaoPingInfo.class,"select t.id,t.ratingCompanyId,t.ywCompanyID,t.ratingDate,tc.type,(select companyName from twocodeCompanyInfo where id =t.ratingCompanyId ) as ratingCompanyName,(select companyName from twocodeCompanyInfo where id =t.ywCompanyID ) as ywCompanyName,ratingUserName,detailratingDate,(regularInspectionsj+inspectElevatorsj+acceptInspElevatorsj+maintenSceneInfosj-200)   as scoreTotal  from TwoCodeYwQuaCredRating  t left join  twocodecompanyinfo tc on t.ratingCompanyId = tc.id  where  tc.type = '����' and ratingType = 1 and t.ratingDate = \'"+s+"\'", null);	
						
			
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JsonView(items);
	}
	
public View queryKpinfoListMul(YwKaoPingInfoVO info){
	    UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
	    int userid = userinfo.getId();
	    int  role = userinfo.getRole();
 		
 		int companyId =0;
 		int ywCompanyId= 0 ;
 		String ratingDate="";
 		
 		companyId = info.getCompanyId();
 		ywCompanyId= info.getYwCompanyId();
 		ratingDate = info.getRatingDate();
 		
 	
 		
		List<YwKaoPingInfo> items =null;
		String sql = "";
		if(role == 30 || role ==31)
		    sql = "select t.id,t.ratingCompanyId,t.ywCompanyID,t.ratingDate,tc.type,(select companyName from twocodeCompanyInfo where id =t.ratingCompanyId ) as ratingCompanyName,(select companyName from twocodeCompanyInfo where id =t.ywCompanyID ) as ywCompanyName,ratingUserName,detailratingDate, (officeSpacesj+headQuarterssj+fixedTelOnDutysj+telOnDutyunattendedsj+enterpriseChangesj+enterpriseRecordsj -100 ) as scoreTotal  from TwoCodeYwQuaCredRating  t left join  twocodecompanyinfo tc on t.ratingCompanyId = tc.id  where ratingType = 1 and tc.type = '���ʼ�'";
		if(role == 10 || role ==11)
			sql = "select t.id,t.ratingCompanyId,t.ywCompanyID,t.ratingDate,tc.type,(select companyName from twocodeCompanyInfo where id =t.ratingCompanyId ) as ratingCompanyName,(select companyName from twocodeCompanyInfo where id =t.ywCompanyID ) as ywCompanyName,ratingUserName,detailratingDate, (select companyName from twocodeCompanyInfo where id =t.ywCompanyID ) as ywCompanyName,ratingUserName,detailratingDate,(inspectElevatorsj+acceptInspElevatorsj+maintenSceneInfosj+complaintsEventssj+maintenBusinesssj+honestsj+punishmentsj -300)   as scoreTotal  from TwoCodeYwQuaCredRating  t left join  twocodecompanyinfo tc on t.ratingCompanyId = tc.id  where ratingType = 1 and tc.type = '�ʼ�'";
		if(role == 16 || role ==17)
			sql = "select t.id,t.ratingCompanyId,t.ywCompanyID,t.ratingDate,tc.type,(select companyName from twocodeCompanyInfo where id =t.ratingCompanyId ) as ratingCompanyName,(select companyName from twocodeCompanyInfo where id =t.ywCompanyID ) as ywCompanyName,ratingUserName,detailratingDate, (select companyName from twocodeCompanyInfo where id =t.ywCompanyID ) as ywCompanyName,ratingUserName,detailratingDate,(regularInspectionsj+inspectElevatorsj+acceptInspElevatorsj+maintenSceneInfosj-200)  as scoreTotal  from TwoCodeYwQuaCredRating  t left join  twocodecompanyinfo tc on t.ratingCompanyId = tc.id  where ratingType = 1 and tc.type = '����'";
		      
		//	if(companyId > 0)
	//		sql = sql + " and  t.ratingCompanyId = "+companyId;
		if(ywCompanyId > 0)
			sql = sql + " and  t.ywCompanyID = "+ywCompanyId;
		if(!"".equals(ratingDate))
			sql = sql + " and t.ratingDate = '"+ratingDate+"'";
		
		try {	
		//	 items = YwKaoPingInfo.findBySql(YwKaoPingInfo.class,"select id,(select companyName from twocodeCompanyInfo where id =ratingCompanyId ) as ratingCompanyName,(select companyName from twocodeCompanyInfo where id =ywCompanyID ) as ywCompanyName,ratingUserName,detailratingDate,0 as scoreTotal from TwoCodeYwQuaCredRating where ratingType = 1 ", null);	
			items = YwKaoPingInfo.findBySql(YwKaoPingInfo.class,sql, null);	
			
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JsonView(items);
	}
 	
 	public View kpinfoList(){
		List<YwKaoPingInfo> items =null;
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM");
		
		String s = format1.format(new Date()); 
		try {	
		//	 items = YwKaoPingInfo.findBySql(YwKaoPingInfo.class,"select id,(select companyName from twocodeCompanyInfo where id =ratingCompanyId ) as ratingCompanyName,(select companyName from twocodeCompanyInfo where id =ywCompanyID ) as ywCompanyName,ratingUserName,detailratingDate,0 as scoreTotal from TwoCodeYwQuaCredRating where ratingType = 1 ", null);	
			items = YwKaoPingInfo.findBySql(YwKaoPingInfo.class,"select t.id,t.ratingCompanyId,t.ywCompanyID,t.ratingDate,tc.type,(select companyName from twocodeCompanyInfo where id =t.ratingCompanyId ) as ratingCompanyName,(select companyName from twocodeCompanyInfo where id =t.ywCompanyID ) as ywCompanyName,ratingUserName,detailratingDate,case when tc.type ='���ʼ�' then  (officeSpacesj+headQuarterssj+fixedTelOnDutysj+telOnDutyunattendedsj+enterpriseChangesj+enterpriseRecordsj+malignantEventssj+complaintsEventssj+maintenBusinesssj+honestsj+punishmentsj+secondRescuePointsj+positiveEnergysj+expertsSuggestionsj+positiveWorksj+remoteMonitorsj+elevatorInsurancesj+techinnovationsj) -400 when  tc.type ='�ʼ�' then (inspectElevatorsj+acceptInspElevatorsj+maintenSceneInfosj+complaintsEventssj+maintenBusinesssj+honestsj+punishmentsj)-300 when  tc.type ='����' then (regularInspectionsj+inspectElevatorsj+acceptInspElevatorsj+maintenSceneInfosj)-200 else 0 end as scoreTotal  from TwoCodeYwQuaCredRating  t left join  twocodecompanyinfo tc on t.ratingCompanyId = tc.id  where  ratingType = 1 and t.ratingDate = \'"+s+"\'", null);	
			
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JsonView(items);
	}
 	
 	public View queryKpinfoList(YwKaoPingInfoVO info){
 		
 		
 		int companyId =0;
 		int ywCompanyId= 0 ;
 		String ratingDate="";
 		
 		companyId = info.getCompanyId();
 		ywCompanyId= info.getYwCompanyId();
 		ratingDate = info.getRatingDate();
 		
 	
 		
		List<YwKaoPingInfo> items =null;
		String sql = "";
		sql = "select t.id,t.ratingCompanyId,t.ywCompanyID,t.ratingDate,tc.type,(select companyName from twocodeCompanyInfo where id =t.ratingCompanyId ) as ratingCompanyName,(select companyName from twocodeCompanyInfo where id =t.ywCompanyID ) as ywCompanyName,ratingUserName,detailratingDate,case when tc.type ='���ʼ�' then  (officeSpacesj+headQuarterssj+fixedTelOnDutysj+telOnDutyunattendedsj+enterpriseChangesj+enterpriseRecordsj+malignantEventssj+complaintsEventssj+maintenBusinesssj+honestsj+punishmentsj+secondRescuePointsj+positiveEnergysj+expertsSuggestionsj+positiveWorksj+remoteMonitorsj+elevatorInsurancesj+techinnovationsj) -400 when  tc.type ='�ʼ�' then (inspectElevatorsj+acceptInspElevatorsj+maintenSceneInfosj+complaintsEventssj+maintenBusinesssj+honestsj+punishmentsj)-300 when  tc.type ='����' then (regularInspectionsj+inspectElevatorsj+acceptInspElevatorsj+maintenSceneInfosj)-200 else 0 end as scoreTotal  from TwoCodeYwQuaCredRating  t left join  twocodecompanyinfo tc on t.ratingCompanyId = tc.id  where ratingType = 1 ";
		if(companyId > 0)
			sql = sql + " and  t.ratingCompanyId = "+companyId;
		if(ywCompanyId > 0)
			sql = sql + " and  t.ywCompanyID = "+ywCompanyId;
		if(!"".equals(ratingDate))
			sql = sql + " and t.ratingDate = '"+ratingDate+"'";
		
		try {	
		//	 items = YwKaoPingInfo.findBySql(YwKaoPingInfo.class,"select id,(select companyName from twocodeCompanyInfo where id =ratingCompanyId ) as ratingCompanyName,(select companyName from twocodeCompanyInfo where id =ywCompanyID ) as ywCompanyName,ratingUserName,detailratingDate,0 as scoreTotal from TwoCodeYwQuaCredRating where ratingType = 1 ", null);	
			items = YwKaoPingInfo.findBySql(YwKaoPingInfo.class,sql, null);	
			
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JsonView(items);
	}
	 
 	public String ywsyssetingImgUpload(String imgName){
       
 		String result="failure";
 		String path = GlobalFunction.tcCompanyPath;
 	
    	MultipartFile picFile = null;
 		MultipartRequest req = (MultipartRequest)request; 
 //		picFile = req.getFile("officeSpaceImg");
 		picFile = req.getFile(imgName.substring(0, imgName.indexOf("/")));
 		
 		System.out.println("imgName---"+imgName.substring(0, imgName.lastIndexOf("/")));
 	    String picPah =path+"/"+imgName.substring(0, imgName.lastIndexOf("/"));
 	    
 	   File file = new File(picPah);
 	  if (!file.exists()) {
			if (!file.mkdirs()) {
				System.out.println("GlobalConfig��,������ά���ϴ�Ŀ¼����");
			}
		}
 	 
		if(picFile!= null && picFile.getSize()>0){ System.out.println("contentType--->"+picFile.getContentType());
		 try {  	
	//		picFile.transferTo(new File(GlobalFunction.tcCompanyPath+"/"+imgName.replace("/","")+".jpg"));
			picFile.transferTo(new File(picPah+"/"+imgName.replace("/","")+".jpg"));
			result="success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 }
 		return result;
 	
}
 	
 	 public View queryinfoByReg(String registNumber){
		
		 String sql ="select isnull(t.registNumber,'') as registNumber,isnull(t.registCode,'') as registCode, isnull(t.address,'') as address,isnull(t.area,'') as area,isnull(t.buildingName,'') as buildingName,isnull(t.building,'') as building,isnull(t.unit,'') as unit  from TwoCodeElevatorInfo t  where t.registNumber = ?";	
		 String sql2 ="select isnull(t.registNumber,'') as registNumber,isnull(t.registCode,'') as registCode,isnull(t.address,'') as address,isnull(t.area,'') as area,isnull(t.buildingName,'') as buildingName,isnull(t.building,'') as building,isnull(t.unit,'') as unit  from TwoCodeddElevatorInfo t  where t.registNumber = ?";	
			
		 ElevaltorInfoVO elevaltorInfoVO =null;   
		 DdElevaltorInfoVO elevaltorInfoVO2 =null;
		 Map<String, Object> result = new HashMap<String, Object>();
		try {
			elevaltorInfoVO = ElevaltorInfoVO.findFirstBySql(ElevaltorInfoVO.class, sql, new Object[] { registNumber });
		    if(elevaltorInfoVO !=null){
		      result.put("address", elevaltorInfoVO.getAddress());
		      result.put("registCode", elevaltorInfoVO.getRegistCode());
		      result.put("area", elevaltorInfoVO.getArea());
		      result.put("buildingName", elevaltorInfoVO.getBuildingName());
		      result.put("building", elevaltorInfoVO.getBuilding());
		      result.put("unit", elevaltorInfoVO.getUnit()); 
		      return new JsonView(result);
		     }
		    else{
		    	elevaltorInfoVO2 = DdElevaltorInfoVO.findFirstBySql(DdElevaltorInfoVO.class, sql2, new Object[] { registNumber });	
		    	 if(elevaltorInfoVO2 !=null){
				      result.put("address", elevaltorInfoVO2.getAddress());
				      result.put("registCode", elevaltorInfoVO2.getRegistCode());
				      result.put("area", elevaltorInfoVO2.getArea());
				      result.put("buildingName", elevaltorInfoVO2.getBuildingName());
				      result.put("building", elevaltorInfoVO2.getBuilding());
				      result.put("unit", elevaltorInfoVO2.getUnit()); 
				      return new JsonView(result);
				     }
		    }
		    	
		} 
		 catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}
		  result.put("address", "");
	      result.put("registCode", "");
	      result.put("area", "");
	      result.put("buildingName", "");
	      result.put("building", "");
	      result.put("unit", ""); 
		 return new JsonView(result);
	 }
 	 
 	public String intohmd(YwKaoHmdInfoVO info){
 		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
 		 int userid = userinfo.getId();
 		 String result = "failure";
         java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
		 String s = format1.format(new Date()); 
		 
 		 YwKaoHmdInfo hmdinfo = new YwKaoHmdInfo();
 		 hmdinfo.setOperatoruid(userid);
 		 hmdinfo.setHmdReason(info.getHmdReason());
 		 hmdinfo.setYwcompanyId(info.getYwcompanyId());
 		 hmdinfo.setYwCompanyName(info.getYwCompanyName());
 		 hmdinfo.setHmdDate(s);
 		 
 		 try {
			if(hmdinfo.save()){
				CompanyInfo.updateAll(CompanyInfo.class, "hmdFlag = ?",new Object[]{1}, "id=?", new Object[] {info.getYwcompanyId()});
				result = "success";
			}
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		 return result;
 	}
 	
 	public String outhmd(YwKaoHmdInfoVO info){
 		 String result = "failure";
 		 int num = 0;
 		 
		 try {  
			    ActiveRecordBase.beginTransaction();
				num = CompanyInfo.updateAll(CompanyInfo.class,"hmdFlag = ?", new Object[]{0}, "id = ?", new Object[]{info.getYwcompanyId()});
			    if(num >0){
			    	YwKaoHmdInfoVO.deleteAll(YwKaoHmdInfoVO.class, "ywcompanyId = ? ", new Object[]{info.getYwcompanyId()});
			    }
			    result = "success";
			    ActiveRecordBase.commit();
			} catch (ActiveRecordException e) {
				try {
					ActiveRecordBase.rollback();
				} catch (TransactionException e1) {
					e1.printStackTrace();
				}
			}
		
		 return result;
 		 		
 	}
 	 
 	public View kphmdtbList(){
 		List<YwKaoHmdInfo> items =null;
 		String sql ="";
 		
 		sql ="select * from TwoCodeYwcompanyHMD";
 		try {
			items = YwKaoHmdInfo.findBySql(YwKaoHmdInfo.class, sql, null);
		} catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
 		return new JsonView(items);
 	}
 	
 	 public View cseletjEcharts(){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<CsEchartsElevaltorInfoVO> items = new ArrayList<CsEchartsElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
	//	 JSONObject obj = new JSONObject();
		 String sql ="";
		 try {
		 sql ="select top 200 count(*) as etotal,tc.companyName from TwoCodeCompanyInfo tc left join TwoCodeElevatorInfo t on tc.id = t.ywcompanyId where tc.type = 'ά��' group by tc.companyName  order by etotal desc"; 
		 items =CsEchartsElevaltorInfoVO.findBySql(CsEchartsElevaltorInfoVO.class, sql); 
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	 }   
	
		 result.put("tjEcharts", items);  
		 return new JsonView(result);
	 }
 	 
 	public View cseletjMrEcharts(){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<CsEchartsElevaltorInfoVO> items = new ArrayList<CsEchartsElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 String sql ="";
		 try {
		 sql ="select top 200 count(*) as etotal,tc.companyName from TwoCodeCompanyInfo tc left join TwoCodeElevatorInfo t on tc.id = t.wgcompanyId where tc.type = 'ʹ��' group by tc.companyName  order by etotal desc"; 
		 items =CsEchartsElevaltorInfoVO.findBySql(CsEchartsElevaltorInfoVO.class, sql); 
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	 }   
	
		 result.put("tjMrEcharts", items);  
		 return new JsonView(result);
	 }
 	 
 	
 	
 	/*
 	  var mapData = [
            {name: '��ɳ��',value:1000,bfb:100},
            {name: '������',value:0,bfb:0},
            {name: '������', value:0,bfb:0},
            {name: '������', value:0,bfb:0},
            {name: '������', value:0,bfb:0},
            {name: '�껨��', value:0,bfb:0},
            {name: '��´��', value:0,bfb:0},
            {name: 'ܽ����', value:0,bfb:0},
            {name: '�����', value:0,bfb:0}
     ];
 	 * */
 	public View cseletjMapEcharts(){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<CsEchartsElevaltorInfoVO> items = new ArrayList<CsEchartsElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 String sql ="";
		 try {
		 sql ="select top 20 count(*) as etotal,t.area as companyName from  TwoCodeElevatorInfo t group by t.area  order by etotal desc"; 
		 items =CsEchartsElevaltorInfoVO.findBySql(CsEchartsElevaltorInfoVO.class, sql); 
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	 }   
		
	
		 result.put("tjMapEcharts", items);  
		 return new JsonView(result);
	 }
 	
 	public View cseletjWeibaoEcharts(){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<CsEchartsYwInfoVO> items = new ArrayList<CsEchartsYwInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 String sql ="";
		 try {
	//	 sql ="select top 1 count(*) as companyTotal from  TwoCodeCompanyInfo where  type = 'ά��' union select top 1 count(*) as companyTotal from YwManagerInfo group by ywcompanyId"; 
         sql ="select top 1 count(*) as companyTotal,1 as qindex from  TwoCodeCompanyInfo where  type = 'ά��'  union select count(*),2 as qindex from  (select top 1 count(*) as companyTotal from YwManagerInfo group by ywcompanyId) t union select top 1 COUNT(*) as companyTotal,3 as qindex from TCUserInfo where role = 18 union select top 1 COUNT(*) as companyTotal,4 as qindex from TwoCodeElevatorInfo union select top 1 COUNT(*) as companyTotal,5 as qindex from TwoCodeElevatorInfo where dailingFlag =1 union select top 1 COUNT(*) as companyTotal,6 as qindex from TwoCodeElevatorInfo where shemiFlag = 1 union select top 1 COUNT(*) as companyTotal,7 as qindex from  TwoCodeCompanyInfo where  type = 'ʹ��'  order by qindex";
		 items =CsEchartsYwInfoVO.findBySql(CsEchartsYwInfoVO.class, sql); 
		} catch (ActiveRecordException e) {
			
			e.printStackTrace();
	 }   
		
	
		 result.put("tjWeibaoEcharts", items);  
		 return new JsonView(result);
	 }
 	
	public View cseletjEllipseEcharts(){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<CsEchartsYwInfoVO> items = new ArrayList<CsEchartsYwInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 String sql ="";
		 try {
	//	 sql ="select top 1 count(*) as companyTotal from  TwoCodeCompanyInfo where  type = 'ά��' union select top 1 count(*) as companyTotal from YwManagerInfo group by ywcompanyId"; 
        sql =" select top 1 COUNT(*) as companyTotal from  TwoCodeCompanyInfo where  type = 'ʹ��'  ";
		 items =CsEchartsYwInfoVO.findBySql(CsEchartsYwInfoVO.class, sql); 
		} catch (ActiveRecordException e) {
			
			e.printStackTrace();
	 }   
		
	
		 result.put("tjEllipseEcharts", items);  
		 return new JsonView(result);
	 }
	
	public View cseletjTabChartsEcharts(){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<CsEchartsElevaltorInfoVO> items = new ArrayList<CsEchartsElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 String sql ="";
		 try {
        sql =" select  count(*) as etotal,t.area as companyName from  TwoCodeElevatorInfo t group by t.area  order by etotal desc ";
		 items =CsEchartsElevaltorInfoVO.findBySql(CsEchartsElevaltorInfoVO.class, sql); 
		} catch (ActiveRecordException e) {
			
			e.printStackTrace();
	 }   
		
	
		 result.put("tjTabChartsEcharts", items);  
		 return new JsonView(result);
	 }
	
	public View daoruZhantieData(MultipartFile zhantieData) {
		String filename = zhantieData.getOriginalFilename();
		System.out.println(filename);
		String ext = filename.substring(filename.lastIndexOf(".") + 1);
		//��ȡһ���ļ�������
		Workbook workBook=null;
		Map<String, Object> result =null;
		if(ext.equals("xlsx")) {
			//����xlsx��ʽ
			
				try {
					workBook = new XSSFWorkbook(zhantieData.getInputStream());
					result = daoru(workBook);
					//�ͷ���Դ
					workBook.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} else if(ext.equals("xls")) {
			//����xls��ʽ
			try {
				workBook = new XSSFWorkbook(zhantieData.getInputStream());
				result = daoru(workBook);
				//�ͷ���Դ
				workBook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("�ϴ��ļ���ʽ����!");
		}
		return new JsonView(result);
		
	}
	public Map daoru(Workbook workBook) {
		//��ȡһ��sheet
		Sheet sheet = workBook.getSheetAt(0);
		//��ȡ��һ��
		//int firstRowNum = sheet.getFirstRowNum();
		//��ȡ���һ��
		int lastRowNum = sheet.getLastRowNum();
		String value;
		//��ȡ��һ������
		Row rowFirst = sheet.getRow(0);
		//int lastCellNum = rowFirst.getLastCellNum();
		//System.out.println(lastCellNum);
		 //����map����
		 Map<String, Object> result = new HashMap<String, Object>();
		 //ʶ�����Ѿ����ڵ����ݼ���
		 List<DdElevaltorInfo> shibieCodes = new ArrayList<DdElevaltorInfo>();
		 //ע�����Ѿ����ڵ����ݼ���
		 List<DdElevaltorInfo> registCodes = new ArrayList<DdElevaltorInfo>();
		 //����ɹ�����������
		 long daoruCount = 0L;
		for(int i=1;i<=lastRowNum;i++) {
			//����Ԥ¼��Ϣ����
			DdElevaltorInfo ddElevaltorInfo = new DdElevaltorInfo();
			//��ȡ������
			Row row = sheet.getRow(i);
//			for(int j=0;j<lastCellNum;j++) {
//				Cell cell = row.getCell(j);
//				if(cell != null) {
//					try {
//						value = cell.getStringCellValue();
//					} catch (Exception e) {
//						Double cellValue = cell.getNumericCellValue();
//						value = new DecimalFormat("#").format(cellValue);
//					}
//					System.out.print(value + "  ");
//				} else {
//					System.out.print(null + "  ");
//				}
//			}
			if(row!=null) {
				//��ַ
				if(row.getCell(0)!=null) {
					try {
						ddElevaltorInfo.setAddress(row.getCell(0).getStringCellValue().replaceAll(" +", "").length()>1?row.getCell(0).getStringCellValue().replaceAll(" +", ""):""); 
					} catch (Exception e) {
						Double cellValue = row.getCell(0).getNumericCellValue();
						ddElevaltorInfo.setAddress(new DecimalFormat("#").format(cellValue));
					}
				}
				//���ݱ��
				if(row.getCell(1)!=null) {
					try {
						ddElevaltorInfo.setRegistNumber(row.getCell(1).getStringCellValue()); 
					} catch (Exception e) {
						Double cellValue = row.getCell(1).getNumericCellValue();
						ddElevaltorInfo.setRegistNumber(new DecimalFormat("#").format(cellValue));
					}
				}
				//ע����
				if(row.getCell(2)!=null) {
					try {
						ddElevaltorInfo.setRegistCode(row.getCell(2).getStringCellValue()); 
					} catch (Exception e) {
						Double cellValue = row.getCell(2).getNumericCellValue();
						ddElevaltorInfo.setRegistCode(new DecimalFormat("#").format(cellValue));
					}
				}
				//����
				if(row.getCell(3)!=null) {
					try {
						ddElevaltorInfo.setArea(row.getCell(3).getStringCellValue().replaceAll(" +", "").length()>1?row.getCell(3).getStringCellValue().replaceAll(" +", ""):""); 
					} catch (Exception e) {
						Double cellValue = row.getCell(3).getNumericCellValue();
						ddElevaltorInfo.setArea(new DecimalFormat("#").format(cellValue));
					}
				}
				//¥������
				if(row.getCell(4)!=null) {
					try {
						ddElevaltorInfo.setBuildingName(row.getCell(4).getStringCellValue().replaceAll(" +", "").length()>1?row.getCell(4).getStringCellValue().replaceAll(" +", ""):""); 
					} catch (Exception e) {
						Double cellValue = row.getCell(4).getNumericCellValue();
						ddElevaltorInfo.setBuildingName(new DecimalFormat("#").format(cellValue));
					}
				}
				//��
				if(row.getCell(5)!=null) {
					try {
						ddElevaltorInfo.setBuilding(row.getCell(5).getStringCellValue()); 
					} catch (Exception e) {
						Double cellValue = row.getCell(5).getNumericCellValue();
						ddElevaltorInfo.setBuilding(new DecimalFormat("#").format(cellValue));
					}
				}
				//��Ԫ
				if(row.getCell(6)!=null) {
					try {
						ddElevaltorInfo.setUnit(row.getCell(6).getStringCellValue()); 
					} catch (Exception e) {
						Double cellValue = row.getCell(6).getNumericCellValue();
						ddElevaltorInfo.setUnit(new DecimalFormat("#").format(cellValue));
					}
				}
				//ʶ����
				if(row.getCell(7)!=null) {
					try {
						ddElevaltorInfo.setShibieCode(row.getCell(7).getStringCellValue()); 
					} catch (Exception e) {
						Double cellValue = row.getCell(7).getNumericCellValue();
						ddElevaltorInfo.setShibieCode(new DecimalFormat("#").format(cellValue));
					}
				}
			}
			
			//��������
			daoruCount = daoruadd(ddElevaltorInfo,daoruCount,shibieCodes,registCodes);
		}
		//System.out.println(firstRowNum);
		//System.out.println(lastRowNum);
		
		//���ؽ��
		result.put("daoruCount", daoruCount);
		result.put("shibieCodes", shibieCodes);
		result.put("registCodes", registCodes);
		return result;
	}
	
	 public long daoruadd(DdElevaltorInfo elevaltorInfo,long daoruCount,List<DdElevaltorInfo> shibieCodes,List<DdElevaltorInfo> registCodes){
		 DdElevaltorInfoVO  elevaltorInfoExit = null;
		 try {
				if(!"".equals(elevaltorInfo.getShibieCode()) && elevaltorInfo.getShibieCode() != null)
					elevaltorInfoExit = DdElevaltorInfoVO.findFirst(DdElevaltorInfoVO.class, "shibieCode = ?", new Object[] { elevaltorInfo.getShibieCode()});
				if(elevaltorInfoExit != null){
			    	//ʶ�����Ѵ���
			    	shibieCodes.add(elevaltorInfo);
			    	if(!"".equals(elevaltorInfo.getRegistCode()) && elevaltorInfo.getRegistCode() != null)
						elevaltorInfoExit = DdElevaltorInfoVO.findFirst(DdElevaltorInfoVO.class, "registCode = ?", new Object[] { elevaltorInfo.getRegistCode()});
					if(elevaltorInfoExit != null){
				    	//ע����,ʶ���붼����
						registCodes.add(elevaltorInfo);
						return daoruCount;
					    }
					return daoruCount;
			    }
				if(!"".equals(elevaltorInfo.getRegistCode()) && elevaltorInfo.getRegistCode() != null)
					elevaltorInfoExit = DdElevaltorInfoVO.findFirst(DdElevaltorInfoVO.class, "registCode = ?", new Object[] { elevaltorInfo.getRegistCode()});
				if(elevaltorInfoExit != null){
			    	//ע�����Ѵ���
					registCodes.add(elevaltorInfo);
					return daoruCount;
				    }
		 if("".equals(elevaltorInfo.getMap_X()) || elevaltorInfo.getMap_X() == null )
				elevaltorInfo.setMap_X("0.000000");
		 else
			 elevaltorInfo.setMap_X(elevaltorInfo.getMap_X());
		if("".equals(elevaltorInfo.getMap_Y()) || elevaltorInfo.getMap_Y() == null)
				elevaltorInfo.setMap_Y("0.000000");
		else
			elevaltorInfo.setMap_Y(elevaltorInfo.getMap_Y());
		//Ҫ������±�ǩ��Ϣ����updateTime�ֶΣ�С���Ǳ�Ҫ��
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = format1.format(new Date()); 
		elevaltorInfo.setUpdateTime(s);
		 //�ж��ܷ�����������������Ϊ�գ�����0
		 if(ruKuisvalid(elevaltorInfo))
			 elevaltorInfo.setRuKuValid(1);
		 else
			 elevaltorInfo.setRuKuValid(0);
			 
			        ActiveRecordBase.beginTransaction();
					boolean flag = elevaltorInfo.save();
					if (flag){
						ActiveRecordBase.commit();
						daoruCount++;
					}
					else { 
						ActiveRecordBase.rollback();
					}
		 }catch(Exception e){
			 e.printStackTrace();
			 try {
				super.dbrollback();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
			 
		 }
		 return daoruCount;
		
	 }
	 
	 //����ʡ��������ѯ
	 public View Jyregistddquery(JyNewRegistInfoVO info,int page, int rows) throws Exception {

		 String cityName = GlobalFunction.cityName;
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 String registNumber="";
		 String address ="";
		 String registor="";
		 String registCode="";
		 String registCompanyName ="";
		 String registDate = "";
		 
		 String conditions="";
		 String conditionsSql="";
		 
		 address = info.getAddress();
		 registNumber = info.getRegistNumber();
		 registor = info.getRegistor();
		 registCompanyName = info.getRegistCompanyName();
		 registCode = info.getRegistCode();
		 registDate = info.getRegistDate();
		 
		 if(!"".equals(address)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.address like '%"+address+"%'";	
				} 
				else{
				 conditions =" t.address like '%"+address+"%'";	
				}
			 
		 }
		 if(!"".equals(registDate)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registDate='"+registDate + "'";	
				} 
				else{
				 conditions =" t.registDate='"+registDate + "'";	
				}
			 
		 }
		 
		 if(!"".equals(registNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registNumber like '"+registNumber+"%'";	
				} 
				else{
				 conditions =" t.registNumber like '"+registNumber+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(registor)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registor like '%"+registor+"%'";	
				} 
				else{
				 conditions =" t.registor like '%"+registor+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(registCompanyName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registCompanyName like '%"+registCompanyName+"%'";	
				} 
				else{
				 conditions =" t.registCompanyName like '%"+registCompanyName+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(registCode)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.registCode like '"+registCode+"%'";	
				} 
				else{
				 conditions =" t.registCode like '"+registCode+"%'";	
				}
			 
		 }
		
		 String sql ="";
		 long total =0;
		 Map<String, Object> result = new HashMap<String, Object>();
		 if("1".equals(cityName)){
			if(!"".equals(conditions)){
				 sql ="select t.id,t.registCode,t.address,t.registNumber,t.registor,t.registCompanyName,t.registDate from JyNewRegistInfo t  where "+conditions;	 	 
				 conditionsSql = "select count(*) from JyNewRegistInfo  t  where "+conditions;
			}
			else{
				 sql ="select t.id,t.registCode,t.address,t.registNumber,t.registor,t.registCompanyName,t.registDate from JyNewRegistInfo t";	 	 
				 conditionsSql = "select count(*) from JyNewRegistInfo  t";
			}
			total =JyNewRegistInfoVO.countBySql(JyNewRegistInfoVO.class, conditionsSql, null);
			List<JyNewRegistInfoVO> items=JyNewRegistInfoVO.findBySql(JyNewRegistInfoVO.class, sql, null, "t.registCode", rows, (page-1)*rows);
			result.put("rows", items);
			result.put("total", total);
			
		 }
		 else{
			 if(!"".equals(conditions)){
				 sql ="select t.id,t.registCode,t.address,t.registNumber,t.registor,t.registCompanyName,t.registDate from JyNewRegistInfo t  where "+conditions; 
				 conditionsSql = "select count(*) from JyNewRegistInfo  t  where "+conditions;
			 }
			 else{
				 sql ="select t.id,t.registCode,t.address,t.registNumber,t.registor,t.registCompanyName,t.registDate from JyNewRegistInfo t"; 
				 conditionsSql = "select count(*) from JyNewRegistInfo  t";
			 }
			 total=JyNewRegistInfoVO.countBySql(JyNewRegistInfoVO.class, conditionsSql, null);
			 List<JyNewRegistInfoVO> items=JyNewRegistInfoVO.findBySql(JyNewRegistInfoVO.class, sql, null, "t.registCode", rows, (page-1)*rows);
				
			 result.put("rows", items);
			 result.put("total", total);
		 }
		 
		  return new JsonView(result);
		 
	 }
	 
	 public String daoruJcData(MultipartFile jcData) {
		 String filename = jcData.getOriginalFilename();
			System.out.println(filename);
			String ext = filename.substring(filename.lastIndexOf(".") + 1);
			//��ȡһ���ļ�������
			Workbook workBook=null;
			//Map<String, Object> result =null;
			String result = "";
			if(ext.equals("xlsx")) {
				//����xlsx��ʽ
				
					try {
						workBook = new XSSFWorkbook(jcData.getInputStream());
						result = daoruJc(workBook);
						//�ͷ���Դ
						workBook.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} else if(ext.equals("xls")) {
				//����xls��ʽ
				try {
					workBook = new XSSFWorkbook(jcData.getInputStream());
					result = daoruJc(workBook);
					//�ͷ���Դ
					workBook.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println("�ϴ��ļ���ʽ����!");
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("result", result);
			//return new JsonView(map);
			return result;
	 }
	
	 public String daoruJc(Workbook workBook) {
		//��ȡһ��sheet
			Sheet sheet = workBook.getSheetAt(0);
			//��ȡ��һ��
			//int firstRowNum = sheet.getFirstRowNum();
			//��ȡ���һ��
			int lastRowNum = sheet.getLastRowNum();
			String value;
			//��ȡ��һ������
			Row rowFirst = sheet.getRow(0);
			//int lastCellNum = rowFirst.getLastCellNum();
			System.out.println(lastRowNum + "������");
			 //����map����
			 Map<String, Object> result = new HashMap<String, Object>();
			 //ʶ�����Ѿ����ڵ����ݼ���
			 //List<DdElevaltorInfo> shibieCodes = new ArrayList<DdElevaltorInfo>();
			 //ע�����Ѿ����ڵ����ݼ���
			 //List<DdElevaltorInfo> registCodes = new ArrayList<DdElevaltorInfo>();
			 //����ɹ�����������
			 long daoruCount = 0L;
			for(int i=1;i<=lastRowNum;i++) {
				//��ȡ��i������
				Row row = sheet.getRow(i);
				//boolean flag = false;
				DdYuanShiElevaltorInfo yuanShiElevaltorInfo = new DdYuanShiElevaltorInfo();
				CompanyInfo wgCompanyInfo = new CompanyInfo();
				CompanyInfo ywCompanyInfo = new CompanyInfo();
				CompanyInfo zzCompanyInfo = new CompanyInfo();
				CompanyInfo azCompanyInfo = new CompanyInfo();
				CompanyInfo jyCompanyInfo = new CompanyInfo();
				if(row != null) {
					rowcol:for(int j=0;j<=rowFirst.getLastCellNum();j++) {
						String str = "";
						String cellStr = "";
						if(row.getCell(j) != null){
							try {
								str = row.getCell(j).getStringCellValue();
							} catch (Exception e) {
								if (DateUtil.isCellDateFormatted(row.getCell(j))) {
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
									 Date date = row.getCell(j).getDateCellValue();  
									 str = sdf.format(date); 
								} else {
									Double cellValue = row.getCell(j).getNumericCellValue();
									str = new DecimalFormat("#").format(cellValue);
								}
							}
							try{
								cellStr = rowFirst.getCell(j).getStringCellValue();
								if("���ݱ��".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setRegistNumber(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("�ǼǱ��".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setRegistCode2(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("ʶ����".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setShibieCode(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("ʹ�õǼ�֤����".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setUseRegistCode(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("��λ�ڲ����".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setUseNumber(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("ע�����".equals(cellStr.replaceAll(" +", ""))) {
									/*if("".equals(str.replaceAll(" +", ""))){
										flag = true;
										break rowcol;
									}*/
									yuanShiElevaltorInfo.setRegistCode(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("ע��Ǽ���Ա".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setRegistor(str.replaceAll(" +", "").length()>1?str.replaceAll(" +", ""):"");
									continue;
								}
								if("ע������".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setRegistDate(str);
									continue;
								}
								if("��Ȩ��λ".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setPropertyRightsUnit(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("��Ȩ��λ����".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setPropertyRightsUnitCompanyCode(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("��Ȩ��λ��ַ".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setPropertyRightsUnitAddress(str.replaceAll(" +", "").length()>1?str.replaceAll(" +", ""):"");
									continue;
								}
								if("ʹ�õ�λ".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setWgCompanyName(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("ʹ�õ�λ����".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setWgCompanyCode(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("ʹ�õ�λ��ַ".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setWgCompanyAddress(str.replaceAll(" +", "").length()>1?str.replaceAll(" +", ""):"");
									continue;
								}
								if("ʹ�õ�λ��������".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setWgCompanyZip(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("��ȫ��������".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setSafetyManDepart(str.replaceAll(" +", "").length()>1?str.replaceAll(" +", ""):"");
									continue;
								}
								if("��ȫ������Ա".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setSafetyManPerson(str.replaceAll(" +", "").length()>1?str.replaceAll(" +", ""):"");
									continue;
								}
								if("��ȫ������Ա��ϵ�绰".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setSafetyManTel(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("��������".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setArea(str.replaceAll(" +", "").length()>1?str.replaceAll(" +", ""):"");
									continue;
								}
								if("��������/�ֵ�".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setTownshipStreets(str.replaceAll(" +", "").length()>1?str.replaceAll(" +", ""):"");
									continue;
								}
								if("��ַ".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setAddress(str.replaceAll(" +", "").length()>1?str.replaceAll(" +", ""):"");
									continue;
								}
								if("¥������".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setBuildingName(str.replaceAll(" +", "").length()>1?str.replaceAll(" +", ""):"");
									continue;
								}
								if("���ڶ�".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setBuilding(str);
									continue;
								}
								if("���ڵ�Ԫ".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setUnit(str);
									continue;
								}
								if("�������".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setEleType(str.replaceAll(" +", "").length()>1?str.replaceAll(" +", ""):"");
									continue;
								}
								if("��������".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setName(str.replaceAll(" +", "").length()>1?str.replaceAll(" +", ""):"");
									continue;
								}
								if("�����ͺ�".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setEleMode(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("���쵥λ".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setZzCompanyName(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("���쵥λ����".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setZzCompanyCode(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("���쵥λ�ʸ�֤������".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setZzCompanyCertificateName(str.replaceAll(" +", "").length()>1?str.replaceAll(" +", ""):"");
									continue;
								}
								if("���쵥λ�ʸ�֤����".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setZzCompanyCode(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("���쵥λ��ϵ�绰".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setZzCompanyTel(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("��������".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setManufactDate(str);
									continue;
								}
								if("�������".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setFactoryNum(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("��װ��λ".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setAzCompanyName(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("��װ��λ����".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setAzCompanyCode(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("��װ��λ�ʸ�֤����".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setAzCompanyCertificateCode(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("��װ��λ��Ŀ������".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setAzCompanyMan(str.replaceAll(" +", "").length()>1?str.replaceAll(" +", ""):"");
									continue;
								}
								if("��װ��λ��ϵ�绰".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setAzCompanyTel(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("������������".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setCompleteAcceptanceDate(str);
									continue;
								}
								if("���ռ������".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setAcceptanceDateDepart(str.replaceAll(" +", "").length()>1?str.replaceAll(" +", ""):"");
									continue;
								}
								if("���ձ�����".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setAcceptanceReportNum(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("Ͷ������".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setUseDate(str);
									continue;
								}
								if("ά�ޱ�����λ".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setYwCompanyName(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("ά����λ����".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setYwCompanyCode(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("ά����λ�ʸ�֤����".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setYwCompanyCertificateCode(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("ά����Ա".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setYwMan(str.replaceAll(" +", "").length()>1?str.replaceAll(" +", ""):"");
									continue;
								}
								if("ά����Ա��ϵ�绰".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setYwManTel(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("ά����ͬ��Ч��".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setmContractVdate(str);
									continue;
								}
								if("�����ٶ�".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setSpeed(str);
									continue;
								}
								if("��غ�".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setEleLoad(str);
									continue;
								}
								if("��վ".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setEleStop(str);
									continue;
								}
								if("���鵥λ".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setJyCompanyName(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("���鵥λ����".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setJyCompanyCode(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("�������".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setCheckCategory(str.replaceAll(" +", "").length()>1?str.replaceAll(" +", ""):"");
									continue;
								}
								if("�������".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setCheckResult(str.replaceAll(" +", "").length()>1?str.replaceAll(" +", ""):"");
									continue;
								}
								if("������".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setCheckReportNum(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("��������".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setInspectDate(str);
									continue;
								}
								if("�´μ�������".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setNextInspectDate(str);
									continue;
								}
								if("���ݱ����ʽ".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setChangeWay(str.replaceAll(" +", "").length()>1?str.replaceAll(" +", ""):"");
									continue;
								}
								if("���ݱ����Ŀ".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setHandleProject(str.replaceAll(" +", "").length()>1?str.replaceAll(" +", ""):"");
									continue;
								}
								if("���ݱ������".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setHandleDate(str);
									continue;
								}
								if("����а쵥λ".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setHandleCompany(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("����а쵥λ����".equals(cellStr.replaceAll(" +", ""))) {
									yuanShiElevaltorInfo.setHandleCompanyCode(str.replaceAll(" +", "").length()>2?str.replaceAll(" +", ""):"");
									continue;
								}
								if("ͣ�ñ�־����/��".equals(cellStr.replaceAll(" +", ""))) {
									if("��".equals(str)){
										yuanShiElevaltorInfo.setDailingFlag(1);
									}
									continue;
								}
							}catch(Exception e) {
								e.printStackTrace();
								return "�����쳣���������Ա��ϵ";
							}
						}
					}
					//if(!flag) {
					if(yuanShiElevaltorInfo.getRegistCode() != null && !"".equals(yuanShiElevaltorInfo.getRegistCode())){
						yuanShiElevaltorInfo.setIsusedFlag(0);
						try{
							//��������
							DdYuanShiElevaltorInfo yuanShiElevaltorInfo2 = null;
							if(yuanShiElevaltorInfo.getRegistCode() != null) {
								yuanShiElevaltorInfo2 = DdYuanShiElevaltorInfo.findFirst(DdYuanShiElevaltorInfo.class, "registCode=?", new Object[]{yuanShiElevaltorInfo.getRegistCode()});
								if(yuanShiElevaltorInfo2 != null) {
									continue;
								}
							} 
							if(yuanShiElevaltorInfo.getRegistNumber() != null) {
								yuanShiElevaltorInfo2 = DdYuanShiElevaltorInfo.findFirst(DdYuanShiElevaltorInfo.class, "registNumber=?", new Object[]{yuanShiElevaltorInfo.getRegistNumber()});
								if(yuanShiElevaltorInfo2 != null) {
									continue;
								}
							} 
							//ActiveRecordBase.beginTransaction();
							boolean flags = yuanShiElevaltorInfo.save();
							if (flags){
								//ActiveRecordBase.commit();
								daoruCount++;
							}
							//else { 
								//ActiveRecordBase.rollback();
							//}
						}catch(Exception e) {
							e.printStackTrace();
							return "�����쳣���������Ա��ϵ";
						}
					}
					}
				}
				return "�ɹ�����" + daoruCount + "������";
		 
	 }
	 
	 //�����޸�
	 public String plModify(ElevaltorInfoVO elevaltorInfoVo,String ywCompanyTel,String wgCompanyTel,String ids) {

		 String buildingName = elevaltorInfoVo.getBuildingName();
		 String wgCompanyName = elevaltorInfoVo.getWgCompanyName();
		 String ywCompanyName = elevaltorInfoVo.getYwCompanyName();
		 String safetyManDepart = elevaltorInfoVo.getSafetyManDepart();
		 String safetyManPerson = elevaltorInfoVo.getSafetyManPerson();
		 String safetyManPersonTel = elevaltorInfoVo.getSafetyManPersonTel();
		 String usePlace = elevaltorInfoVo.getUsePlace();
		 
		 try {
			 List<Object> params = new ArrayList<Object>();
			 String sql = "";
			 java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 String s = format1.format(new Date());
			 if(!"".equals(wgCompanyName) && wgCompanyName != null) {
				 CompanyInfo companyInfo = CompanyInfo.findFirst(CompanyInfo.class, "companyName=? and type=?", new Object[] {wgCompanyName,"ʹ��"});
				 if(companyInfo == null) {
					 return "ʹ�õ�λ��������������д";
				 }
				 if(!"".equals(wgCompanyTel) && wgCompanyTel != null) {
					 CompanyInfo.updateAll(CompanyInfo.class, "telephone=?,updateTime=?", new Object[] {wgCompanyTel.replaceAll("-", "").replaceAll(" ", ""),s}, "id=?", new Object[] {companyInfo.getId()});
				 }
				 if("".equals(sql)) {
					 sql += "wgCompanyId=?";
				 } else {
					 sql += ",wgCompanyId=?";
				 }
				 params.add(companyInfo.getId());
			 }
			 if(!"".equals(ywCompanyName) && ywCompanyName != null) {
				 CompanyInfo companyInfo = CompanyInfo.findFirst(CompanyInfo.class, "companyName=? and type=?", new Object[] {ywCompanyName,"ά��"});
				 if(companyInfo == null) {
					 return "ά����λ��������������д";
				 }
				 if(!"".equals(ywCompanyTel) && ywCompanyTel != null) {
					 CompanyInfo.updateAll(CompanyInfo.class, "telephone=?,updateTime=?", new Object[] {ywCompanyTel.replaceAll("-", "").replaceAll(" ", ""),s}, "id=?", new Object[] {companyInfo.getId()});
				 }
				 if("".equals(sql)) {
					 sql += "ywCompanyId=?";
				 } else {
					 sql += ",ywCompanyId=?";
				 }
				 params.add(companyInfo.getId());
			 }
			 //����id
			 String[] elevaltorInfoIds = ids.split(",");
			 if(!"".equals(buildingName) && buildingName != null) {
				 if("".equals(sql)) {
					 sql += "buildingName=?";
				 } else {
					 sql += ",buildingName=?";
				 }
				 params.add(buildingName);
			 }
			 if(!"".equals(safetyManDepart) && safetyManDepart != null) {
				 if("".equals(sql)) {
					 sql += "safetyManDepart=?";
				 } else {
					 sql += ",safetyManDepart=?";
				 }
				 params.add(safetyManDepart);
			 }
			 if(!"".equals(safetyManPerson) && safetyManPerson != null) {
				 if("".equals(sql)) {
					 sql += "safetyManPerson=?";
				 } else {
					 sql += ",safetyManPerson=?";
				 }
				 params.add(safetyManPerson);
			 }
			 if(!"".equals(safetyManPersonTel) && safetyManPersonTel != null) {
				 if("".equals(sql)) {
					 sql += "safetyManPersonTel=?";
				 } else {
					 sql += ",safetyManPersonTel=?";
				 }
				 params.add(safetyManPersonTel.replaceAll("-", "").replaceAll(" ", ""));
			 }
			 if(!"".equals(usePlace) && usePlace != null) {
				 if("".equals(sql)) {
					 sql += "usePlace=?";
				 } else {
					 sql += ",usePlace=?";
				 }
				 params.add(usePlace);
			 }
			 //System.out.println(sql);
			 //System.out.println(params.toArray().toString());
			 //����id�������޸�
			 if("".equals(sql)) {
				 return "������Ϣ��";
			 }
			 for(int i=0;i<elevaltorInfoIds.length;i++) {
				 ElevaltorInfo.updateAll(ElevaltorInfo.class, sql, params.toArray(), "id=?", new Object[] {elevaltorInfoIds[i]});
				 
			 }
			 	
		    return "�޸ĳɹ�";
		 }
		catch (Exception e) {
			e.printStackTrace();
			 return "�޸��쳣";
		  } 
	 
	 }
	 
	 	//�Ͼ�ԭʼ���ݰ�����������ѯ����
		 public View yuanshiddquery2(DdElevaltorInfoVO info,int page, int rows)throws Exception{
			 String cityName = GlobalFunction.cityName;
			 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
			 int userid = userinfo.getId();
			 String userName =userinfo.getLoginName();
			 int  role = userinfo.getRole();
			
			 String areaName="";
			 String address ="";
			 String wgCompanyName="";
			 String registCode="";
			 String buildingName ="";
			 String shibieCode="";
			 
			 String area ="";
			 String conditions="";
			 String conditionsSql="";
			 
			 address = info.getAddress();
			 buildingName = info.getBuildingName();
			 wgCompanyName = info.getWgCompanyName();
			 areaName = info.getArea();
			 registCode = info.getRegistCode();
			 shibieCode = info.getShibieCode();
			 
			 if(!"".equals(address)){
				 if(!"".equals(conditions)){
					 conditions =conditions+" and t.address like '%"+address+"%'";	
					} 
					else{
					 conditions =" t.address like '%"+address+"%'";	
					}
				 
			 }
			 
			 if(!"".equals(buildingName)){
				 if(!"".equals(conditions)){
					 conditions =conditions+" and t.buildingName like '%"+buildingName+"%'";	
					} 
					else{
					 conditions =" t.buildingName like '%"+buildingName+"%'";	
					}
				 
			 }
			 
			 if(!"".equals(wgCompanyName)){
				 if(!"".equals(conditions)){
					 conditions =conditions+" and t.wgCompanyName like '%"+wgCompanyName+"%'";	
					} 
					else{
					 conditions =" t.wgCompanyName like '%"+wgCompanyName+"%'";	
					}
				 
			 }
			 
			 if(!"".equals(areaName)){
				 if(!"".equals(conditions)){
					 conditions =conditions+" and t.area like '%"+areaName+"%'";	
					} 
					else{
					 conditions =" t.area like '%"+areaName+"%'";	
					}
				 
			 }
			 
			 if(!"".equals(registCode)){
				 if(!"".equals(conditions)){
					 conditions =conditions+" and t.registCode like '%"+registCode+"%'";	
					} 
					else{
					 conditions =" t.registCode like '%"+registCode+"%'";	
					}
				 
			 }
			 if(!"".equals(shibieCode)){
				 if(!"".equals(conditions)){
					 conditions =conditions+" and t.shibieCode like '%"+shibieCode+"%'";	
					} 
					else{
					 conditions =" t.shibieCode like '%"+shibieCode+"%'";	
					}
				 
			 }
			 if(role==10 || role==11){
				 UserInfoVO userInfoVO = null;
					try {
						userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.area from  tcuserinfo tu  where tu.userid = ?", new Object[] { userid });
					} catch (ActiveRecordException e) {
						e.printStackTrace();
					}
			    	 if(userInfoVO != null){
			    		 area = "%"+userInfoVO.getArea()+"%";
			    		 if(!"".equals(conditions)){
							 conditions =conditions+" and t.area like '%"+area+"%'";	
							} 
							else{
							 conditions =" t.area like '%"+area+"%'";	
							}
			         
			    	 }
			 }
			 String sql ="";
			 long total =0;
			 Map<String, Object> result = new HashMap<String, Object>();
				 if(!"".equals(conditions)){
					 sql ="select t.id,t.shibieCode,t.address,t.buildingName,t.wgCompanyName,t.registCode,t.useNumber,t.area from TwoCodeYuanShiNewData  t where t.isusedFlag = 0  and "+conditions; 
					 conditionsSql = "select count(*) from TwoCodeYuanShiNewData  t where t.isusedFlag = 0   and  "+conditions;
				 }
				 else{
					 sql ="select t.id,t.shibieCode,t.address,t.buildingName,t.wgCompanyName,t.registCode,t.useNumber,t.area from TwoCodeYuanShiNewData  t where  t.isusedFlag = 0 "; 
					 conditionsSql = "select count(*) from TwoCodeYuanShiNewData  t where t.isusedFlag = 0 ";
				 }
				 total=DdYuanShiElevaltorInfoVO.countBySql(DdYuanShiElevaltorInfoVO.class, conditionsSql, null);
				 List<DdYuanShiElevaltorInfoVO> items=DdYuanShiElevaltorInfoVO.findBySql(DdYuanShiElevaltorInfoVO.class, sql, null, "t.registCode", rows, (page-1)*rows);
					
				 result.put("rows", items);
				 result.put("total", total);
			 
			  return new JsonView(result);
			 }
		 
		 
	public View elevatorCoordinate(int page, int rows) throws Exception {
		String sql = "select * from (select e.registNumber,e.address,e.registCode," +
		"e.map_X as emap_X,e.map_Y as emap_Y,d.map_X as dmap_X,d.map_Y as dmap_Y,p.map_X as pmap_X,p.map_Y as pmap_Y," +
		"case when LEFT(d.map_X,1)='0' or LEFT(d.map_Y,1)='0' or LEFT(e.map_X,1)='0' or LEFT(e.map_Y,1)='0' or d.map_X='' or d.map_Y='' or e.map_X='' or e.map_Y='' then null else ACOS(SIN((d.map_Y * PI()) / 180) * SIN((e.map_Y * PI()) / 180) + COS((d.map_Y * PI()) / 180) * COS((e.map_Y * PI()) / 180) * COS((d.map_X * PI()) / 180 - (e.map_X * PI()) / 180)) * 6378.137*1000 end as eddistance," +
		"case when LEFT(p.map_X,1)='0' or LEFT(p.map_Y,1)='0' or LEFT(e.map_X,1)='0' or LEFT(e.map_Y,1)='0' or p.map_X='' or p.map_Y='' or e.map_X='' or e.map_Y='' then null else ACOS(SIN((e.map_Y * PI()) / 180) * SIN((p.map_Y * PI()) / 180) + COS((e.map_Y * PI()) / 180) * COS((p.map_Y * PI()) / 180) * COS((e.map_X * PI()) / 180 - (p.map_X * PI()) / 180)) * 6378.137*1000 end as epdistance," +
		"case when LEFT(p.map_X,1)='0' or LEFT(p.map_Y,1)='0' or LEFT(d.map_X,1)='0' or LEFT(d.map_Y,1)='0' or p.map_X='' or p.map_Y='' or d.map_X='' or d.map_Y='' then null else ACOS(SIN((d.map_Y * PI()) / 180) * SIN((p.map_Y * PI()) / 180) + COS((d.map_Y * PI()) / 180) * COS((p.map_Y * PI()) / 180) * COS((d.map_X * PI()) / 180 - (p.map_X * PI()) / 180)) * 6378.137*1000 end as dpdistance " +
		"from TwoCodeElevatorInfo e left join TwoCodeDdElevatorInfo d on e.registNumber=d.registNumber left join Twocode96333pdetailInfo p on d.registNumber=p.registNumber) t where not (t.eddistance<50 and t.epdistance<50 and t.dpdistance<50) ORDER BY registNumber";
		String csql = "select count(*) from (select e.registNumber,e.address,e.registCode," +
				"e.map_X as emap_X,e.map_Y as emap_Y,d.map_X as dmap_X,d.map_Y as dmap_Y,p.map_X as pmap_X,p.map_Y as pmap_Y," +
				"case when LEFT(d.map_X,1)='0' or LEFT(d.map_Y,1)='0' or LEFT(e.map_X,1)='0' or LEFT(e.map_Y,1)='0' or d.map_X='' or d.map_Y='' or e.map_X='' or e.map_Y='' then null else ACOS(SIN((d.map_Y * PI()) / 180) * SIN((e.map_Y * PI()) / 180) + COS((d.map_Y * PI()) / 180) * COS((e.map_Y * PI()) / 180) * COS((d.map_X * PI()) / 180 - (e.map_X * PI()) / 180)) * 6378.137*1000 end as eddistance," +
				"case when LEFT(p.map_X,1)='0' or LEFT(p.map_Y,1)='0' or LEFT(e.map_X,1)='0' or LEFT(e.map_Y,1)='0' or p.map_X='' or p.map_Y='' or e.map_X='' or e.map_Y='' then null else ACOS(SIN((e.map_Y * PI()) / 180) * SIN((p.map_Y * PI()) / 180) + COS((e.map_Y * PI()) / 180) * COS((p.map_Y * PI()) / 180) * COS((e.map_X * PI()) / 180 - (p.map_X * PI()) / 180)) * 6378.137*1000 end as epdistance," +
				"case when LEFT(p.map_X,1)='0' or LEFT(p.map_Y,1)='0' or LEFT(d.map_X,1)='0' or LEFT(d.map_Y,1)='0' or p.map_X='' or p.map_Y='' or d.map_X='' or d.map_Y='' then null else ACOS(SIN((d.map_Y * PI()) / 180) * SIN((p.map_Y * PI()) / 180) + COS((d.map_Y * PI()) / 180) * COS((p.map_Y * PI()) / 180) * COS((d.map_X * PI()) / 180 - (p.map_X * PI()) / 180)) * 6378.137*1000 end as dpdistance " +
				"from TwoCodeElevatorInfo e left join TwoCodeDdElevatorInfo d on e.registNumber=d.registNumber left join Twocode96333pdetailInfo p on d.registNumber=p.registNumber) t where not (t.eddistance<50 and t.epdistance<50 and t.dpdistance<50)";
		long total = ElevatorCoordinate.countBySql(ElevatorCoordinate.class, csql, null);
		List<ElevatorCoordinate> items = ElevatorCoordinate.findBySql(ElevatorCoordinate.class, sql, null, null,  rows, (page-1)*rows);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", items);
		return new JsonView(result);
	}
	
	
	//���ݵ��ݵ�ַ��������
	public String updateCoordinate() throws Exception {
		String sql = "select id,precise,address from TwoCodeElevatorInfo where precise is null or precise!=2";
		long count = ElevaltorInfo.count(ElevaltorInfo.class, "precise is null or precise!=2", null);
		List<ElevaltorInfo> items = null;
		for(int i = 1; i <= Math.ceil(count/500); i ++) {
			items = ElevaltorInfo.findBySql(ElevaltorInfo.class, sql, null, null, 500, (i - 1) * 500);
			for (ElevaltorInfo item : items) {
				getCoordinateBybd(item);
			}
		}
		return "���³ɹ�";
	}

	public void getCoordinateBybd(ElevaltorInfo item) throws ActiveRecordException {
		try{
			String url = "http://api.map.baidu.com/geocoder/v2/?output=json&ak=c7fvr8IyDuF0Y1qibqzrjBeMRhqzQe2k&city=�ɶ�&address="+item.getAddress();
		    Map<String,String> queries = new HashMap<String,String>();
		    String string = OkHttpUtil.get(url,queries);
		    JSONObject statusObiect = JSON.parseObject(string);
		    Integer status = statusObiect.getInteger("status");
		    item.setPrecise("0");
		    if(status.equals(0)){
		        JSONObject jsonObject = JSON.parseObject(string).getJSONObject("result").getJSONObject("location");
		        JSONObject result = JSON.parseObject(string).getJSONObject("result");
		        item.setMap_X(jsonObject.getString("lng"));
		        item.setMap_Y(jsonObject.getString("lat"));
		        item.setPrecise(result.getString("precise"));
		    }else{
		        item.setPrecise("0");

		    }
		    ElevaltorInfo.updateAll(ElevaltorInfo.class, "map_X=?,map_Y=?,precise=?", new Object[]{item.getMap_X(),item.getMap_Y(),item.getPrecise()}, "id=?", new Object[]{item.getId()});
		}catch(Exception e) {
			/*e.printStackTrace();
			item.setMap_X("0.000000");
			item.setMap_Y("0.000000");
			item.setPrecise("0");
			ElevaltorInfo.updateAll(ElevaltorInfo.class, "map_X=?,map_Y=?,precise=?", new Object[]{item.getMap_X(),item.getMap_Y(),item.getPrecise()}, "id=?", new Object[]{item.getId()});*/
			System.out.println("�����Ե�");
			getCoordinateBybd(item);
		}
	}
	
	//����������Ч��
	public String checkCoordinate() throws ActiveRecordException {
		boolean flag = false;
		long count = ElevaltorInfo.count(ElevaltorInfo.class, null, null);
		List<ElevaltorInfo> eitems = null;
		for(int i = 1; i <= Math.ceil(count/500); i ++) {
			eitems = ElevaltorInfo.findAll(ElevaltorInfo.class, null, null, null, 500, (i - 1) * 500);
			for (ElevaltorInfo eitem : eitems) {
				flag = parseAddressByCoordinate(eitem.getMap_X(),eitem.getMap_Y());
				if(!flag) {
					ElevaltorInfo.updateAll(ElevaltorInfo.class, "map_X=?,map_Y=?,precise=?", new Object[]{"0.000000","0.000000","0"}, "id=?", new Object[]{eitem.getId()});
				}
			}
		}
		count = DdElevaltorInfo.count(DdElevaltorInfo.class, null, null);
		List<DdElevaltorInfo> ditems = null;
		for(int i = 1; i <= Math.ceil(count/500); i ++) {
			ditems = DdElevaltorInfo.findAll(DdElevaltorInfo.class, null, null, null, 500, (i - 1) * 500);
			for (DdElevaltorInfo ditem : ditems) {
				flag = parseAddressByCoordinate(ditem.getMap_X(),ditem.getMap_Y());
				if(!flag) {
					DdElevaltorInfo.updateAll(DdElevaltorInfo.class, "map_X=?,map_Y=?", new Object[]{"0.000000","0.000000"}, "id=?", new Object[]{ditem.getId()});
				}
			}
		}
		count = Twocode96333pVO.count(Twocode96333pVO.class, null, null);
		List<Twocode96333pVO> pitems = null;
		for(int i = 1; i <= Math.ceil(count/500); i ++) {
			pitems = Twocode96333pVO.findAll(Twocode96333pVO.class, null, null, null, 500, (i - 1) * 500);
			for (Twocode96333pVO pitem : pitems) {
				flag = parseAddressByCoordinate(pitem.getMap_X(),pitem.getMap_Y());
				if(!flag) {
					Twocode96333pVO.updateAll(Twocode96333pVO.class, "map_X=?,map_Y=?", new Object[]{"0.000000","0.000000"}, "id=?", new Object[]{pitem.getId()});
				}
			}
		}
		return "�������";
	}
	
	public boolean parseAddressByCoordinate(String map_X,String map_Y) {
		try{
			String url = "http://api.map.baidu.com/geocoder?location=" + map_Y + "," + map_X + "&coord_type=gcj02&output=json&src=webapp.baidu.openAPIdemo";
		    Map<String,String> queries = new HashMap<String,String>();
		    String string = OkHttpUtil.get(url,queries);
		    JSONObject statusObiect = JSON.parseObject(string);
		    String status = statusObiect.getString("status");
		    if("OK".equals(status)) {
		    	JSONObject resultObject = statusObiect.getJSONObject("result");
		    	JSONObject addressObject = resultObject.getJSONObject("addressComponent");
		    	String city = addressObject.getString("city");
		    	String province = addressObject.getString("province");
		    	String formatted_address = resultObject.getString("formatted_address");
		    	System.out.println(formatted_address);
		    	if("�Ĵ�ʡ".equals(province) && "�ɶ���".equals(city)) {
		    		return true;
		    	} else {
		    		return false;
		    	}
		    }else {
		    	return false;
		    }
		}catch(Exception e) {
			//e.printStackTrace();
			System.out.println("�����Ե�");
			return parseAddressByCoordinate(map_X,map_Y);
		}
	    
	   /* if(status.equals(0)){
	        JSONObject jsonObject = JSON.parseObject(string).getJSONObject("result").getJSONObject("location");
	        JSONObject result = JSON.parseObject(string).getJSONObject("result");
	        item.setMap_X(jsonObject.getString("lng"));
	        item.setMap_Y(jsonObject.getString("lat"));
	        item.setPrecise(result.getString("precise"));
	    }else{
	        item.setPrecise("0");

	    }*/
	}
 	 
}