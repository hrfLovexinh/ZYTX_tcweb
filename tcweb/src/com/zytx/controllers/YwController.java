package com.zytx.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;



import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.exception.ActiveRecordException;
import com.et.ar.exception.TransactionException;
import com.et.mvc.Controller;
import com.et.mvc.JsonView;
import com.et.mvc.View;
import com.et.mvc.filter.AfterFilter;
import com.et.mvc.filter.AfterFilters;
import com.et.mvc.filter.BeforeFilter;
import com.zytx.converters.DateConverter;
import com.zytx.converters.TimestampConverter;
import com.zytx.init.GlobalFunction;
import com.zytx.models.AlarmInfo;
import com.zytx.models.CSElevaltorInfoVO;
import com.zytx.models.CompanyInfo;
import com.zytx.models.DdElevaltorInfoVO;
import com.zytx.models.ElevaltorInfo;
import com.zytx.models.ElevaltorInfoVO;
import com.zytx.models.ElevaltorQueryExportVO;
import com.zytx.models.HisYwInfoVO;
import com.zytx.models.ImageVO;
import com.zytx.models.CarDevCard;
import com.zytx.models.NcqQueryYwInfoExportVO;
import com.zytx.models.SysSetingsVO;
import com.zytx.models.SystjInfoVO;
import com.zytx.models.TCUserInfoView;
import com.zytx.models.TYwInfoVO;
import com.zytx.models.TwoCodeElevatorYwCompanyInfo;
import com.zytx.models.TwoCodeLogInfo;
import com.zytx.models.TwoCodeLogInfoVO;
import com.zytx.models.TwoCodePCLogInfoVO;
import com.zytx.models.TwoCodePCYwEffective;
import com.zytx.models.TwoCodePCYwEffectiveVO;
import com.zytx.models.UserExtInfo;
import com.zytx.models.UserInfo;
import com.zytx.models.ImageInfo;
import com.zytx.models.ImageInfo2;
import com.zytx.models.TwoCodeDeviceRelationInfo;
import com.zytx.models.UserInfoVO;
import com.zytx.models.XcInfoVO;
import com.zytx.models.YWOutDate;
import com.zytx.models.YWOutDateVO;
import com.zytx.models.YwInfo;
import com.zytx.models.YwInfoVO;
import com.zytx.models.YwQuaCredRatingVO;
import com.zytx.models.YwRankingInfoVO;
import com.zytx.models.YwlogVO;
import com.zytx.models.YwstatisticsVO;
import com.zytx.models.ZXYwInfoVO;
import com.zytx.util.ElevatorQueryExportExcel2;
import com.zytx.util.ExportExcel;
import com.zytx.util.NcqQueryYwInfoExportExcel;
import com.zytx.util.YwstatExportExcel;

@AfterFilters({
	@AfterFilter(execute="ywlistlog",only={"ywlist"}),
	@AfterFilter(execute="nywcqlistlog",only={"nywcqlist"}),
	@AfterFilter(execute="newtempYwRukulog",only={"newtempYwRuku"}),
	@AfterFilter(execute="ywstatisticslistlog",only={"ywstatisticslist"})
	})
public class YwController extends ApplicationController{  
	
	 protected String rukunames = "";
	 protected String rukuresult = "";
	 protected String rukureason = "";
	 protected String registNumberStr ="";
	 
	 public View ywlistByOrder(int page, int rows,String sort,String order){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 long total=0;
		 List<YwInfoVO> items = null;
		 String SortName = request.getParameter("sort");  
	        //首次加载表格时sort和order为null  
	     if(SortName == null){
	    		 SortName ="id";  
	      }
	  
	        
	     String SortValue =request.getParameter("order");  
	        if(SortValue == null){  
	            SortValue = "desc";        
	        }  
		 try {
	     if(role==2 || role==1){ //系统管理员  
	    //	 total =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from YwManagerInfo where ywKind = '0'", null);
	    	
	    //	 total =YwInfoVO.countBySql(YwInfoVO.class, "select count(1) from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber  where  ywKind = '0' and t.id =(select max(id)   from YwManagerInfo where registNumber = t.registNumber and endTime =(select max(endTime) from YwManagerInfo where registNumber = t.registNumber)) and te.dailingFlag = 0 ", null);
	 	   
	    //   total =YwInfoVO.countBySql(YwInfoVO.class, "select count(1) from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber  where  ywKind = '0' and t.id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) and te.dailingFlag = 0 ", null);
	    	 total =YwInfoVO.countBySql(YwInfoVO.class, "select count(1) from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber  where  ywKind = '0' and  te.dailingFlag = 0 ", null);
					
	    	 
	    //	 items = ElevaltorInfoVO.findAll(ElevaltorInfoVO.class, null, null, null, rows, (page-1)*rows);
	   // 	 String sql ="select t.id,t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,t.ywResult,t.flexStartx,t.flexStarty,t.flexEndx,t.flexEndy,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when t.ePosition = '0' then '轿厢' else '机房' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,tu.userid as userId,tui.loginName,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=t.userId left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.ywKind ='0' and t.id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) and te.dailingFlag = 0 ";
	   // 	 String sql ="select t.id,t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,t.ywResult,t.flexStartx,t.flexStarty,t.flexEndx,t.flexEndy,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when t.ePosition = '0' then '轿厢' else '机房' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,tu.userid as userId,tui.loginName,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=t.userId left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.ywKind ='0' and t.id =(select max(id)   from YwManagerInfo where registNumber = t.registNumber and endTime =(select max(endTime) from YwManagerInfo where registNumber = t.registNumber)) and te.dailingFlag = 0 ";
	  //  	 String sql ="select t.id,t.registNumber,te.address,te.buildingName,t.ywResult,t.flexStartx,t.flexStarty,t.flexEndx,t.flexEndy,t.ywKind,t.maintainTypecode,t.startTime,t.endTime,t.subTime,t.startTime,t.endTime,t.ywstatus,t.picNum,tu.userName,tu.userid as userId,tui.loginName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=t.userId left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.ywKind ='0' and t.id =(select max(id)   from YwManagerInfo where registNumber = t.registNumber and endTime =(select max(endTime) from YwManagerInfo where registNumber = t.registNumber)) and te.dailingFlag = 0 ";
	  //  	 String sql ="select t.id,t.registNumber,te.address,te.buildingName,t.ywResult,t.flexStartx,t.flexStarty,t.flexEndx,t.flexEndy,t.ywKind,t.maintainTypecode,t.startTime,t.endTime,t.subTime,t.startTime,t.endTime,t.ywstatus,t.picNum,tu.userName,tu.userid as userId,tui.loginName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=t.userId left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.ywKind ='0' and t.id =(select max(id)   from YwManagerInfo where registNumber = t.registNumber) and te.dailingFlag = 0 ";
			 String sql ="select t.id,t.registNumber,te.address,te.buildingName,t.ywResult,t.flexStartx,t.flexStarty,t.flexEndx,t.flexEndy,t.ywKind,t.maintainTypecode,t.startTime,t.endTime,t.subTime,t.startTime,t.endTime,t.ywstatus,t.picNum,tu.userName,tu.userid as userId,tui.loginName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=t.userId left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.ywKind ='0'  and te.dailingFlag = 0 ";
		     
			//为了演示不看到超期记录
            	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.loginName  from  TwoCodeUserInfo tu  where tu.id = ?", new Object[] { userid });
    	    	 if(userInfoVO != null){
    	    		 String userName2 =userInfoVO.getLoginName();
    	    		 if("cy".equals(userName2)){
    	    			 total =YwInfoVO.countBySql(YwInfoVO.class, "select count(1) from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber  where  ywKind = '0' and  te.dailingFlag = 0 and DATEDIFF(dd,convert(datetime,t.endTime,120),getdate()) < 15", null); 
    	 				 sql = "select t.id,t.registNumber,te.address,te.buildingName,t.ywResult,t.flexStartx,t.flexStarty,t.flexEndx,t.flexEndy,t.ywKind,t.maintainTypecode,t.startTime,t.endTime,t.subTime,t.startTime,t.endTime,t.ywstatus,t.picNum,tu.userName,tu.userid as userId,tui.loginName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=t.userId left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.ywKind ='0'  and te.dailingFlag = 0 and DATEDIFF(dd,convert(datetime,t.endTime,120),getdate()) < 15";
    	    		 }
    	    	 }
			
	    	 items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "t."+SortName+" "+SortValue, rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==22 || role==23){ //市质监，顶层质监局
	    //	 total  =YwInfoVO.count(YwInfoVO.class, null, null);
	    //	 total =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from YwManagerInfo where ywKind = '0'", null);
	    //	 total =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from (select ts.* from  (select max(t.id) as id, registNumber from YwManagerInfo  t  where t.ywKind ='0' group by t.registNumber)  tf left join YwManagerInfo  ts on tf.id = ts.id) th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid and te.dailingFlag = 0 ", null);
	    //	 total =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from (select ts.* from  (select max(t.id) as id, registNumber from YwManagerInfo  t  where t.ywKind ='0' group by t.registNumber)  tf left join YwManagerInfo  ts on tf.id = ts.id) th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid and te.dailingFlag = 0 ", null);
	    	 total =YwInfoVO.countBySql(YwInfoVO.class, "select count(th.registNumber) from (select t.registNumber from  YwManagerInfo t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber  where  te.dailingFlag = 0  group  by t.registNumber) th", null);
	   	     
	    	 //	 items = ElevaltorInfoVO.findAll(ElevaltorInfoVO.class, null, null, null, rows, (page-1)*rows);
	    //	 String sql ="select t.id,t.registNumber,isnull(te.address,'') as address,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,isnull(tc.companyName,'') as companyName from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.ywKind ='0'";
	   // 	 String sql ="select th.id,th.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,(case when th.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(th.maintainTypecode,'') as maintainTypecode,th.startTime,th.endTime,th.subTime, (case when datediff(minute,th.startTime,th.endTime)>0 then datediff(minute,th.startTime,th.endTime) else '0' end)  as dateSpan, (case when th.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when th.ePosition = '0' then '轿厢' else '机房' end) as ePosition,th.ywstatus,th.picNum,isnull(tu.userName,'')as userName,tu.userid as userId,tui.loginName,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from (select ts.* from  (select max(t.id) as id, registNumber from YwManagerInfo  t  where t.ywKind ='0' group by t.registNumber)  tf left join YwManagerInfo  ts on tf.id = ts.id) th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid and te.dailingFlag = 0 ";
	    //	 String sql ="select th.id,th.registNumber,te.address,te.buildingName,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,th.ywKind,th.maintainTypecode,th.startTime,th.endTime,th.subTime,th.startTime,th.endTime,th.ywstatus,th.picNum,tu.userName,tu.userid as userId,tui.loginName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from (select ts.* from  (select max(t.id) as id, registNumber from YwManagerInfo  t  where t.ywKind ='0' group by t.registNumber)  tf left join YwManagerInfo  ts on tf.id = ts.id) th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid and te.dailingFlag = 0 ";
	    	 String sql ="select th.id,th.registNumber,te.address,te.buildingName,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,th.ywKind,th.maintainTypecode,th.startTime,th.endTime,th.subTime,th.startTime,th.endTime,th.ywstatus,th.picNum,tu.userName,tu.userid as userId,tui.loginName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from  YwManagerInfo   th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where th.ywKind ='0' and te.dailingFlag = 0 "; 
	    	 items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "th."+SortName+" "+SortValue, rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==10 || role ==11){ //质监局
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	    //   total  =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from YwManagerInfo y left join TwoCodeElevatorInfo t on y.registNumber = t.registNumber where t.zjCompanyId=? and  y.ywstatus='1' and y.ywKind='0'", new Object[]{zjcompanyId});
	    //   total  =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from (select ts.* from  (select max(t.id) as id, registNumber from YwManagerInfo  t  where t.ywKind ='0' and ywstatus='1' group by t.registNumber)  tf left join YwManagerInfo  ts on tf.id = ts.id) th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where te.zjCompanyId=?  and th.ywstatus='1' and th.ywKind='0' and te.dailingFlag = 0 ", new Object[]{zjcompanyId});
	         total  =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from (select t.registNumber from  YwManagerInfo t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber where te.zjCompanyId=?  and t.ywstatus='1' and t.ywKind='0' and te.dailingFlag = 0 ) th", new Object[]{zjcompanyId});
	 	    
	    //   String sql ="select t.id,t.registNumber,isnull(te.address,'') as address,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,isnull(tc.companyName,'') as companyName from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where te.zjCompanyId=? and t.ywstatus='1' and t.ywKind ='0'";
	    //   String sql ="select th.id,th.registNumber,te.address,te.buildingName,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,(case when th.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(th.maintainTypecode,'') as maintainTypecode,th.startTime,th.endTime,th.subTime, (case when datediff(minute,th.startTime,th.endTime)>0 then datediff(minute,th.startTime,th.endTime) else '0' end)  as dateSpan, (case when th.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when th.ePosition = '0' then '轿厢' else '机房' end) as ePosition,th.ywstatus,th.picNum,isnull(tu.userName,'')as userName,tu.userid as userId,tui.loginName,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from (select ts.* from  (select max(t.id) as id, registNumber from YwManagerInfo  t  where t.ywKind ='0' and  ywstatus='1' group by t.registNumber)  tf left join YwManagerInfo  ts on tf.id = ts.id) th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where te.zjCompanyId=? and th.ywstatus='1' and th.ywKind ='0' and te.dailingFlag = 0 ";
	    //   String sql ="select th.id,th.registNumber,te.address,te.buildingName,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,th.ywKind,th.maintainTypecode,th.startTime,th.endTime,th.subTime, th.startTime,th.endTime,th.ywstatus,th.picNum,tu.userName,tu.userid as userId,tui.loginName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from (select ts.* from  (select max(t.id) as id, registNumber from YwManagerInfo  t  where t.ywKind ='0' and  ywstatus='1' group by t.registNumber)  tf left join YwManagerInfo  ts on tf.id = ts.id) th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where te.zjCompanyId=? and th.ywstatus='1' and th.ywKind ='0' and te.dailingFlag = 0 ";
	         String sql ="select th.id,th.registNumber,te.address,te.buildingName,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,th.ywKind,th.maintainTypecode,th.startTime,th.endTime,th.subTime,th.startTime,th.endTime,th.ywstatus,th.picNum,tu.userName,tu.userid as userId,tui.loginName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from  YwManagerInfo  th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where te.zjCompanyId=? and th.ywstatus='1' and th.ywKind ='0' and te.dailingFlag = 0 "; 
	         items = YwInfoVO.findBySql(YwInfoVO.class, sql, new Object[]{zjcompanyId}, "th."+SortName+" "+SortValue, rows, (page-1)*rows);
	    	 }
	     }
	     
	     if(role==20 || role ==21){ //街道办
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	    //   total  =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from (select ts.* from  (select max(t.id) as id, registNumber from YwManagerInfo  t  where t.ywKind ='0' and ywstatus='1' group by t.registNumber)  tf left join YwManagerInfo  ts on tf.id = ts.id) th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where te.townshipStreets=?  and th.ywstatus='1' and th.ywKind='0' and te.dailingFlag = 0 ", new Object[]{zjcompanyId});
	         total  =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from (select t.registNumber from YwManagerInfo t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber   where te.townshipStreets=?  and t.ywstatus='1' and t.ywKind='0' and te.dailingFlag = 0  ) th", new Object[]{zjcompanyId});
	  	   
	         
	    //   String sql ="select th.id,th.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,(case when th.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(th.maintainTypecode,'') as maintainTypecode,th.startTime,th.endTime,th.subTime, (case when datediff(minute,th.startTime,th.endTime)>0 then datediff(minute,th.startTime,th.endTime) else '0' end)  as dateSpan, (case when th.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when th.ePosition = '0' then '轿厢' else '机房' end) as ePosition,th.ywstatus,th.picNum,isnull(tu.userName,'')as userName,tu.userid as userId,tui.loginName,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from (select ts.* from  (select max(t.id) as id, registNumber from YwManagerInfo  t  where t.ywKind ='0' and  ywstatus='1' group by t.registNumber)  tf left join YwManagerInfo  ts on tf.id = ts.id) th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where te.townshipStreets=? and th.ywstatus='1' and th.ywKind ='0' and te.dailingFlag = 0 ";
	         String sql ="select th.id,th.registNumber,te.address,te.buildingName,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,th.ywKind,th.maintainTypecode,th.startTime,th.endTime,th.subTime, th.startTime,th.endTime,th.ywstatus,th.picNum,tu.userName,tu.userid as userId,tui.loginName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from  YwManagerInfo  th   left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where te.townshipStreets=? and th.ywstatus='1' and th.ywKind ='0' and te.dailingFlag = 0 ";
	    	 
	         items = YwInfoVO.findBySql(YwInfoVO.class, sql, new Object[]{zjcompanyId}, "th."+SortName+" "+SortValue, rows, (page-1)*rows);
	    	 }
	     }
	     
	     if(role==8 || role ==9){ //物管
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int wgCompanyId = userInfoVO.getCompanyId();
	    //   total  =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from (select ts.* from  (select max(t.id) as id, registNumber from YwManagerInfo  t  where t.ywKind ='0' and ywstatus='1' group by t.registNumber)  tf left join YwManagerInfo  ts on tf.id = ts.id) th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where te.townshipStreets=?  and th.ywstatus='1' and th.ywKind='0' and te.dailingFlag = 0 ", new Object[]{zjcompanyId});
	         total  =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from (select t.registNumber from YwManagerInfo t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber   where te.wgCompanyId=?  and t.ywstatus='1' and t.ywKind='0' and te.dailingFlag = 0  ) th", new Object[]{wgCompanyId});
	  	   
	         
	    //   String sql ="select th.id,th.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,(case when th.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(th.maintainTypecode,'') as maintainTypecode,th.startTime,th.endTime,th.subTime, (case when datediff(minute,th.startTime,th.endTime)>0 then datediff(minute,th.startTime,th.endTime) else '0' end)  as dateSpan, (case when th.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when th.ePosition = '0' then '轿厢' else '机房' end) as ePosition,th.ywstatus,th.picNum,isnull(tu.userName,'')as userName,tu.userid as userId,tui.loginName,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from (select ts.* from  (select max(t.id) as id, registNumber from YwManagerInfo  t  where t.ywKind ='0' and  ywstatus='1' group by t.registNumber)  tf left join YwManagerInfo  ts on tf.id = ts.id) th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where te.townshipStreets=? and th.ywstatus='1' and th.ywKind ='0' and te.dailingFlag = 0 ";
	         String sql ="select th.id,th.registNumber,te.address,te.buildingName,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,th.ywKind,th.maintainTypecode,th.startTime,th.endTime,th.subTime, th.startTime,th.endTime,th.ywstatus,th.picNum,tu.userName,tu.userid as userId,tui.loginName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from  YwManagerInfo  th   left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where te.wgCompanyId=? and th.ywstatus='1' and th.ywKind ='0' and te.dailingFlag = 0 ";
	    	 
	         items = YwInfoVO.findBySql(YwInfoVO.class, sql, new Object[]{wgCompanyId}, "th."+SortName+" "+SortValue, rows, (page-1)*rows);
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
	 
	 
	 public View ywlist(int page, int rows) throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 long total=0;
		 List<YwInfoVO> items = null;
	     if(role==2 || role==1){ //系统管理员  
	    //	 total =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from YwManagerInfo where ywKind = '0'", null);
	    	 total =YwInfoVO.countBySql(YwInfoVO.class, "select count(1) from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber  where  ywKind = '0' and t.id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) and te.dailingFlag = 0 ", null);
	    //	 total =YwInfoVO.countBySql(YwInfoVO.class, "select count(1) from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber  where  ywKind = '0' and t.id =(select max(id)   from YwManagerInfo where registNumber = t.registNumber and endTime =(select max(endTime) from YwManagerInfo where registNumber = t.registNumber)) and te.dailingFlag = 0 ", null);
	 	    
	    //	 items = ElevaltorInfoVO.findAll(ElevaltorInfoVO.class, null, null, null, rows, (page-1)*rows);
	   // 	 String sql ="select t.id,t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,t.ywResult,t.flexStartx,t.flexStarty,t.flexEndx,t.flexEndy,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when t.ePosition = '0' then '轿厢' else '机房' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,tu.userid as userId,tui.loginName,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=t.userId left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.ywKind ='0' and t.id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) and te.dailingFlag = 0 ";
	   // 	 String sql ="select t.id,t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,t.ywResult,t.flexStartx,t.flexStarty,t.flexEndx,t.flexEndy,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when t.ePosition = '0' then '轿厢' else '机房' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,tu.userid as userId,tui.loginName,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=t.userId left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.ywKind ='0' and t.id =(select max(id)   from YwManagerInfo where registNumber = t.registNumber and endTime =(select max(endTime) from YwManagerInfo where registNumber = t.registNumber)) and te.dailingFlag = 0 ";
	  //  	 String sql ="select t.id,t.registNumber,te.address,te.buildingName,t.ywResult,t.flexStartx,t.flexStarty,t.flexEndx,t.flexEndy,t.ywKind,t.maintainTypecode,t.startTime,t.endTime,t.subTime,t.startTime,t.endTime,t.ywstatus,t.picNum,tu.userName,tu.userid as userId,tui.loginName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=t.userId left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.ywKind ='0' and t.id =(select max(id)   from YwManagerInfo where registNumber = t.registNumber and endTime =(select max(endTime) from YwManagerInfo where registNumber = t.registNumber)) and te.dailingFlag = 0 ";
	    	 String sql ="select t.id,t.registNumber,te.address,te.buildingName,t.ywResult,t.flexStartx,t.flexStarty,t.flexEndx,t.flexEndy,t.ywKind,t.maintainTypecode,t.startTime,t.endTime,t.subTime,t.startTime,t.endTime,t.ywstatus,t.picNum,tu.userName,tu.userid as userId,tui.loginName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=t.userId left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.ywKind ='0' and t.id =(select max(id)   from YwManagerInfo where registNumber = t.registNumber) and te.dailingFlag = 0 ";
	    	 
	    	 items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==22 || role==23){ //市质监，顶层质监局
	    //	 total  =YwInfoVO.count(YwInfoVO.class, null, null);
	    //	 total =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from YwManagerInfo where ywKind = '0'", null);
	    //	 total =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from (select ts.* from  (select max(t.id) as id, registNumber from YwManagerInfo  t  where t.ywKind ='0' group by t.registNumber)  tf left join YwManagerInfo  ts on tf.id = ts.id) th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid and te.dailingFlag = 0 ", null);
	    //	 total =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from (select ts.* from  (select max(t.id) as id, registNumber from YwManagerInfo  t  where t.ywKind ='0' group by t.registNumber)  tf left join YwManagerInfo  ts on tf.id = ts.id) th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid and te.dailingFlag = 0 ", null);
	    	 total =YwInfoVO.countBySql(YwInfoVO.class, "select count(th.registNumber) from (select t.registNumber from  YwManagerInfo t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber  where  te.dailingFlag = 0  group  by t.registNumber) th", null);
	   	     
	    	 //	 items = ElevaltorInfoVO.findAll(ElevaltorInfoVO.class, null, null, null, rows, (page-1)*rows);
	    //	 String sql ="select t.id,t.registNumber,isnull(te.address,'') as address,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,isnull(tc.companyName,'') as companyName from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.ywKind ='0'";
	   // 	 String sql ="select th.id,th.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,(case when th.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(th.maintainTypecode,'') as maintainTypecode,th.startTime,th.endTime,th.subTime, (case when datediff(minute,th.startTime,th.endTime)>0 then datediff(minute,th.startTime,th.endTime) else '0' end)  as dateSpan, (case when th.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when th.ePosition = '0' then '轿厢' else '机房' end) as ePosition,th.ywstatus,th.picNum,isnull(tu.userName,'')as userName,tu.userid as userId,tui.loginName,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from (select ts.* from  (select max(t.id) as id, registNumber from YwManagerInfo  t  where t.ywKind ='0' group by t.registNumber)  tf left join YwManagerInfo  ts on tf.id = ts.id) th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid and te.dailingFlag = 0 ";
	    //	 String sql ="select th.id,th.registNumber,te.address,te.buildingName,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,th.ywKind,th.maintainTypecode,th.startTime,th.endTime,th.subTime,th.startTime,th.endTime,th.ywstatus,th.picNum,tu.userName,tu.userid as userId,tui.loginName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from (select ts.* from  (select max(t.id) as id, registNumber from YwManagerInfo  t  where t.ywKind ='0' group by t.registNumber)  tf left join YwManagerInfo  ts on tf.id = ts.id) th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid and te.dailingFlag = 0 ";
	    	 String sql ="select th.id,th.registNumber,te.address,te.buildingName,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,th.ywKind,th.maintainTypecode,th.startTime,th.endTime,th.subTime,th.startTime,th.endTime,th.ywstatus,th.picNum,tu.userName,tu.userid as userId,tui.loginName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from  YwManagerInfo   th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where th.ywKind ='0' and te.dailingFlag = 0 "; 
	    	 items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "th.id desc", rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==10 || role ==11){ //质监局
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	    //   total  =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from YwManagerInfo y left join TwoCodeElevatorInfo t on y.registNumber = t.registNumber where t.zjCompanyId=? and  y.ywstatus='1' and y.ywKind='0'", new Object[]{zjcompanyId});
	    //   total  =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from (select ts.* from  (select max(t.id) as id, registNumber from YwManagerInfo  t  where t.ywKind ='0' and ywstatus='1' group by t.registNumber)  tf left join YwManagerInfo  ts on tf.id = ts.id) th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where te.zjCompanyId=?  and th.ywstatus='1' and th.ywKind='0' and te.dailingFlag = 0 ", new Object[]{zjcompanyId});
	         total  =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from (select t.registNumber from  YwManagerInfo t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber where te.zjCompanyId=?  and t.ywstatus='1' and t.ywKind='0' and te.dailingFlag = 0 ) th", new Object[]{zjcompanyId});
	 	    
	    //   String sql ="select t.id,t.registNumber,isnull(te.address,'') as address,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,isnull(tc.companyName,'') as companyName from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where te.zjCompanyId=? and t.ywstatus='1' and t.ywKind ='0'";
	    //   String sql ="select th.id,th.registNumber,te.address,te.buildingName,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,(case when th.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(th.maintainTypecode,'') as maintainTypecode,th.startTime,th.endTime,th.subTime, (case when datediff(minute,th.startTime,th.endTime)>0 then datediff(minute,th.startTime,th.endTime) else '0' end)  as dateSpan, (case when th.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when th.ePosition = '0' then '轿厢' else '机房' end) as ePosition,th.ywstatus,th.picNum,isnull(tu.userName,'')as userName,tu.userid as userId,tui.loginName,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from (select ts.* from  (select max(t.id) as id, registNumber from YwManagerInfo  t  where t.ywKind ='0' and  ywstatus='1' group by t.registNumber)  tf left join YwManagerInfo  ts on tf.id = ts.id) th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where te.zjCompanyId=? and th.ywstatus='1' and th.ywKind ='0' and te.dailingFlag = 0 ";
	    //   String sql ="select th.id,th.registNumber,te.address,te.buildingName,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,th.ywKind,th.maintainTypecode,th.startTime,th.endTime,th.subTime, th.startTime,th.endTime,th.ywstatus,th.picNum,tu.userName,tu.userid as userId,tui.loginName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from (select ts.* from  (select max(t.id) as id, registNumber from YwManagerInfo  t  where t.ywKind ='0' and  ywstatus='1' group by t.registNumber)  tf left join YwManagerInfo  ts on tf.id = ts.id) th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where te.zjCompanyId=? and th.ywstatus='1' and th.ywKind ='0' and te.dailingFlag = 0 ";
	         String sql ="select th.id,th.registNumber,te.address,te.buildingName,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,th.ywKind,th.maintainTypecode,th.startTime,th.endTime,th.subTime,th.startTime,th.endTime,th.ywstatus,th.picNum,tu.userName,tu.userid as userId,tui.loginName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from  YwManagerInfo  th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where te.zjCompanyId=? and th.ywstatus='1' and th.ywKind ='0' and te.dailingFlag = 0 "; 
	         items = YwInfoVO.findBySql(YwInfoVO.class, sql, new Object[]{zjcompanyId}, "th.id desc", rows, (page-1)*rows);
	    	 }
	     }
	     
	     if(role==20 || role ==21){ //街道办
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         total  =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from (select ts.* from  (select max(t.id) as id, registNumber from YwManagerInfo  t  where t.ywKind ='0' and ywstatus='1' group by t.registNumber)  tf left join YwManagerInfo  ts on tf.id = ts.id) th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where te.townshipStreets=?  and th.ywstatus='1' and th.ywKind='0' and te.dailingFlag = 0 ", new Object[]{zjcompanyId});
	    //     System.out.println("所属街道办标签运维数："+total);
	         String sql ="select th.id,th.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,(case when th.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(th.maintainTypecode,'') as maintainTypecode,th.startTime,th.endTime,th.subTime, (case when datediff(minute,th.startTime,th.endTime)>0 then datediff(minute,th.startTime,th.endTime) else '0' end)  as dateSpan, (case when th.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when th.ePosition = '0' then '轿厢' else '机房' end) as ePosition,th.ywstatus,th.picNum,isnull(tu.userName,'')as userName,tu.userid as userId,tui.loginName,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo where id = te.townshipStreets) as jdbCompanyName from (select ts.* from  (select max(t.id) as id, registNumber from YwManagerInfo  t  where t.ywKind ='0' and  ywstatus='1' group by t.registNumber)  tf left join YwManagerInfo  ts on tf.id = ts.id) th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where te.townshipStreets=? and th.ywstatus='1' and th.ywKind ='0' and te.dailingFlag = 0 ";
	    	 items = YwInfoVO.findBySql(YwInfoVO.class, sql, new Object[]{zjcompanyId}, "th.id desc", rows, (page-1)*rows);
	    	 }
	     }
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	 
	 
	 public View ywlistByreg(String registNumber,int page, int rows) throws Exception{
		
		 long total=0;
		 List<YwInfoVO> items = null;
	    
	//    	 total  =YwInfoVO.count(YwInfoVO.class, null, null);
		     total = YwInfoVO.countBySql(YwInfoVO.class, "select count(*)  from hisYwManagerInfo  t where t.registNumber=?", new Object[]{registNumber});
	    	 String sql ="select t.ywstatus,t.id,t.registNumber,t.ywResult,t.flexStartx,t.flexStarty,t.flexEndx,t.flexEndy,isnull(te.address,'') as address,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,isnull(tu.userName,'')as userName,isnull(tc.companyName,'') as companyName from HisYwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.registNumber=?";
	    	 items = YwInfoVO.findBySql(YwInfoVO.class, sql, new Object[]{registNumber}, "t.id desc", rows, (page-1)*rows);
	    	
	  
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	 
	 public View zxywlistByreg(String registNumber,int page, int rows) throws Exception{
			
		 long total=0;
		 List<ZXYwInfoVO> items = null;
	    
	//    	 total  =YwInfoVO.count(YwInfoVO.class, null, null);
		     total = ZXYwInfoVO.countBySql(ZXYwInfoVO.class, "select count(*)  from TwoCodeZhuXYwManagerInfo  t where t.registNumber=?", new Object[]{registNumber});
	    	 String sql ="select t.id,t.registNumber,t.ywResult,t.flexStartx,t.flexStarty,t.flexEndx,t.flexEndy,isnull(te.address,'') as address,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,isnull(tu.userName,'')as userName,isnull(tc.companyName,'') as companyName from TwoCodeZhuXYwManagerInfo  t left join TwoCodeZhuXElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.registNumber=?";
	    	 items = ZXYwInfoVO.findBySql(ZXYwInfoVO.class, sql, new Object[]{registNumber}, "t.id desc", rows, (page-1)*rows);
	    	
	  
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	 
	 /*
	 
	 public View getCompanyListByType(String companyType)throws Exception{
		 List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
					"select * from TwoCodeCompanyInfo where type = ?", new Object[] { companyType });
		 return new JsonView(items);
	 }
	 
	 public View getCompanyList(int page, int rows) throws Exception {
			List<CompanyInfo> items = CompanyInfo.findAll(CompanyInfo.class);
			return new JsonView(items);
		}
	 
	public View getWgCompanyList(int page, int rows) throws Exception {
		List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
				"select * from TwoCodeCompanyInfo where type = ?", new Object[] { "物管" });
		return new JsonView(items);
	}
	
	public View getYwCompanyList(int page, int rows) throws Exception {
		List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
				"select * from TwoCodeCompanyInfo where type = ?", new Object[] { "运维" });
		return new JsonView(items);
	}
	
	public View getZzCompanyList(int page, int rows) throws Exception {
		List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
				"select * from TwoCodeCompanyInfo where type = ?", new Object[] { "制造" });
		return new JsonView(items);
	}
	
	public View getAzCompanyList(int page, int rows) throws Exception {
		List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
				"select * from TwoCodeCompanyInfo where type = ?", new Object[] { "安装" });
		return new JsonView(items);
	}
	
	public View getJyCompanyList(int page, int rows) throws Exception {
		List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
				"select * from TwoCodeCompanyInfo where type = ?", new Object[] { "检验" });
		return new JsonView(items);
	}
	
	public View getZjCompanyList(int page, int rows) throws Exception {
		List<CompanyInfo> items = CompanyInfo.findBySql(CompanyInfo.class,
				"select * from TwoCodeCompanyInfo where type = ?", new Object[] { "质监" });
		return new JsonView(items);
	}
	 
	 */
	 public String add(YwInfo ywInfo){
		 String result = "failure";
		 int ywCompanyId = 0; 
		 int ywCompanyId2 = 0;
		 try {
		//	String querySql ="select t.* from TwoCodeElevatorInfo t where t.registNumber =?";
			ElevaltorInfoVO  elevaltorInfoExit = null;
			UserExtInfo       userExtInfo = null;
			elevaltorInfoExit = ElevaltorInfoVO.findFirst(ElevaltorInfoVO.class, "registNumber = ?", new Object[] { ywInfo.getRegistNumber()});
		if(elevaltorInfoExit == null){
			 result = "exist";
		}
		else{
		   ywCompanyId = elevaltorInfoExit.getYwCompanyId();
		   System.out.println("标签所在运维公司ywCompanyId--->"+ywCompanyId);
		   if(ywCompanyId ==ywInfo.getYwCompanyId()){   //标签所在的运维公司就是页面所选择的运维公司
		   userExtInfo = UserExtInfo.findFirst(UserExtInfo.class, "userid = ?", new Object[] { ywInfo.getUserId()});
		   if(userExtInfo != null){
			   ywCompanyId2 = userExtInfo.getCompanyid();  
		   }
		   if(ywCompanyId == ywCompanyId2){ //代表运维人员所在的运维公司和标签所在运维公司ID一致
		   ywInfo.setTwoCodeId("");
		   ywInfo.setSubTime(ywInfo.getStartTime());
		   ywInfo.setYwstatus("1");
		   ywInfo.setPicNum(0);
		   
		   if("".equals(ywInfo.getMap_X0()) || ywInfo.getMap_X0() == null)
			   ywInfo.setMap_X0("0.000000");
		   
		   if("".equals(ywInfo.getMap_Y0()) || ywInfo.getMap_Y0() == null)
			   ywInfo.setMap_Y0("0.000000");
		   
		   if("".equals(ywInfo.getMap_X1()) || ywInfo.getMap_X1() == null)
			   ywInfo.setMap_X1("0.000000");
		   
		   if("".equals(ywInfo.getMap_Y1()) || ywInfo.getMap_Y1() == null)
			   ywInfo.setMap_Y1("0.000000");
		   
		   if("".equals(ywInfo.getMap_X2()) || ywInfo.getMap_X2() == null)
			   ywInfo.setMap_X2("0.000000");
		   
		   if("".equals(ywInfo.getMap_Y2()) || ywInfo.getMap_Y2() == null)
			   ywInfo.setMap_Y2("0.000000");
		   
		   if("".equals(ywInfo.getThreedscanning()) || ywInfo.getThreedscanning() == null)
			   ywInfo.setThreedscanning("0");
		   if(ywInfo.save())
			  result ="success";
		   }
		   else{
			   result ="failure3";     //所选择运维人员不属于标签所在运维公司的运维人员
		   }
		   }
		   else
			   result ="failure2";     //所选择的运维公司不属于标签所在的运维公司
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
		System.out.println("开始ajax--->queryDid");
		String isvalid ="0";
		String sql ="select tr.registNumber as deviceId from deviceinfo d left join TwoCodeDeviceRelationInfo tr on d.device_id =tr.deviceId where d.device_id =? and valid ='1'";
		ElevaltorInfoVO elevaltorInfoVO = null;
		try {
			elevaltorInfoVO =ElevaltorInfoVO.findFirstBySql(ElevaltorInfoVO.class, sql, new Object[] { deviceId });
		//	elevaltorInfoVO =ElevaltorInfoVO.findBySql(ElevaltorInfoVO.class, sql, new Object[] { deviceId });
			
		} catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		if(elevaltorInfoVO!=null){
			System.out.println("deviceId:"+elevaltorInfoVO.getDeviceId());
			if(elevaltorInfoVO.getDeviceId()==null){
			System.out.println("isvalid");
			isvalid ="1";
			}
			}
		return isvalid;
	}
	
	 public View edit(int id)throws Exception{ 
		 System.out.println("edit---id:"+id);
		 String sql ="select t.id,t.registNumber,isnull(te.address,'') as address,t.ywKind, isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, t.sPosition, t.ePosition,t.remark,tu.userid as userId,isnull(tu.userName,'')as userName,tc.id as companyId,isnull(tc.companyName,'') as companyName from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId where t.id = ?";
		 YwInfoVO ywInfoVO =YwInfoVO.findFirstBySql(YwInfoVO.class, sql, new Object[] { id });
		 Map<String, Object> result = new HashMap<String, Object>();
		 if(ywInfoVO !=null){
		 result.put("registNumber", ywInfoVO.getRegistNumber());
		 result.put("address", ywInfoVO.getAddress());
		 result.put("ywKind", ywInfoVO.getYwKind());
		 result.put("maintainTypecode", ywInfoVO.getMaintainTypecode());
		 result.put("startTime", ywInfoVO.getStartTime());
		 result.put("endTime", ywInfoVO.getEndTime());
		 result.put("dateSpan", ywInfoVO.getDateSpan());
		 result.put("sPosition", ywInfoVO.getsPosition());
		 result.put("ePosition", ywInfoVO.getePosition());
		 result.put("userId", ywInfoVO.getUserId());
		 result.put("ywCompanyId", ywInfoVO.getCompanyId());
		 result.put("subTime", ywInfoVO.getSubTime());
		 result.put("remark", ywInfoVO.getRemark());
		 
		 }
		 return new JsonView(result);
	 }
	 
	 
	 public String update(YwInfo ywInfo){
		 System.out.println("update---id:"+ywInfo.getId());
		 String result = "failure";
		 int num = 0;
		
		 try {
			ywInfo.setTwoCodeId("");
			ywInfo.setSubTime(ywInfo.getStartTime());
			num = YwInfo.updateAll(YwInfo.class, "registNumber=?,userId=?,ywKind=?,maintainTypecode=?,startTime=?,endTime=?,subTime=?,sPosition=?,ePosition=?,remark=?", new Object[]{ywInfo.getRegistNumber(),ywInfo.getUserId(),ywInfo.getYwKind(),ywInfo.getMaintainTypecode(),ywInfo.getStartTime(),ywInfo.getEndTime(),ywInfo.getSubTime(),ywInfo.getsPosition(),ywInfo.getePosition(),ywInfo.getRemark()}, "id=?", new Object[] { ywInfo.getId()});
			if(num>0)
			result = "success";
		 }
		catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			  e.printStackTrace();
		  } 
		 return result;
	 }
	 
	 public String delete(YwInfoVO ywInfoVO){
		 String result = "failure";
		 int id = ywInfoVO.getId();
		 System.out.println("ywInfoVO----delete----id:"+id);
		 YwInfoVO myYwInfoVO =null;
		 try {
			 myYwInfoVO = YwInfoVO.findFirst(YwInfoVO.class, "id = ?", new Object[]{id});
			if(myYwInfoVO != null){   //删除运维信息记录
				myYwInfoVO.destroy();
				result = "success"; 
			}
			
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		 return result;
	 }
	 
	 public String tempydelete(YwInfoVO ywInfoVO){
		 String result = "failure";
		 int id = ywInfoVO.getId();
		 System.out.println("tempydelete---id:"+id);
		 TYwInfoVO myYwInfoVO =null;
		 try {
			 myYwInfoVO = TYwInfoVO.findFirst(TYwInfoVO.class, "id = ?", new Object[]{id});
			if(myYwInfoVO != null){   //删除临时运维信息记录
				myYwInfoVO.destroy();
				result = "success"; 
			}
			
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		 return result;
	 }
	 
	
	 public View queryByOrder(YwInfoVO info,int page, int rows,String sort,String order){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
	//	 String userName =userinfo.getLoginName();
		 
		 int  role = userinfo.getRole();
		 
		 String registNumber ="";
		 String address ="";
		 String buildingName ="";
		 String loginName ="";
		 int ywCompanyId =0;
		 int wgCompanyId =0;
		 String areaName="";
		 int ywResult =100;
		 String qstartTime ="";
		 String qendTime="";
		 int zjcompanyId2 =0;  //非查询用的，是登陆过程中系统后台根据role不同展示不同列表用的
		 int wgcompanyId2 =0;  //非查询用的，是登陆过程中系统后台根据role不同展示不同列表用的
		 int townshipStreets =0;
		 String userName = "";
		 String  ywstatus ="";
		 
		 registNumber =info.getRegistNumber();
		 address =info.getAddress();
		 areaName =info.getArea();
		 buildingName =info.getBuildingName();
		 loginName = info.getLoginName();
		 ywResult=info.getYwResult();     //审核结论查询
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 ywCompanyId =info.getCompanyId();
		 wgCompanyId =info.getCompanyId2();
		 townshipStreets =info.getTownshipStreets();
		 userName = info.getUserName();
		 ywstatus = info.getYwstatus();
		 /*
		 registNumber ="%"+info.getRegistNumber()+"%";
		 buildingName ="%"+info.getBuildingName()+"%";
		 loginName ="%"+info.getLoginName()+"%";
		 ywCompanyId =info.getCompanyId();
		 if(info.getArea()==null)
			 areaName ="%"+""+"%";
		 else
		 areaName ="%"+info.getArea()+"%";
		 ywResult=info.getYwResult();     //审核结论查询
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
         */
		 
		 String SortName = request.getParameter("sort");  
	        //首次加载表格时sort和order为null  
	     if(SortName == null){  
	            SortName ="id"; 
	      }
	    
	        
	     String SortValue =request.getParameter("order");  
	        if(SortValue == null){  
	            SortValue = "desc";        
	        }  
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 long total = 0;
		 List<YwInfoVO> items = null;
		 try {
		 if(role==10 || role==11){
	           UserInfoVO userInfoVO;
			
				userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
			
	         if(userInfoVO != null){
	             zjcompanyId2 = userInfoVO.getCompanyId();
	             conditions = " te.zjcompanyId = "+zjcompanyId2;
	             }	 
	          }
		 if(role==20 || role==21){
	           UserInfoVO userInfoVO;
			
				userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
			
	         if(userInfoVO != null){
	             zjcompanyId2 = userInfoVO.getCompanyId();
	             conditions = " te.townshipStreets = "+zjcompanyId2;
	             }	 
	          }
		 
		 if(role==8 || role==9){
	           UserInfoVO userInfoVO;
			
				userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
			
	         if(userInfoVO != null){
	        	 wgcompanyId2 = userInfoVO.getCompanyId();
	             conditions = " te.wgCompanyId = "+wgcompanyId2;
	             }	 
	          }
		 
		 if(!"".equals(registNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and th.registNumber like '%"+registNumber+"%'";	
				} 
				else{
				 conditions =" th.registNumber like '%"+registNumber+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(address)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and te.address like '%"+address+"%'";	
				} 
				else{
					 conditions =" te.address like '%"+address+"%'";	
				}
			 
		 }
         
         if(!"".equals(buildingName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and te.buildingName like '%"+buildingName+"%'";	
				} 
				else{
					conditions =" te.buildingName like '%"+buildingName+"%'";	
				}
			 
		 }
         
         if(!"".equals(loginName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and tui.loginName like '%"+loginName+"%'";	
				} 
				else{
					conditions =" tui.loginName like '%"+loginName+"%'";	
				}
			 
		 }
         
         if(ywCompanyId > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and te.ywCompanyId = "+ywCompanyId;	
				} 
				else{
					conditions =" te.ywCompanyId = "+ywCompanyId;	
				}
			 
			 
		 }
         
         
         
         if(wgCompanyId > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and te.wgCompanyId = "+wgCompanyId;	
				} 
				else{
					conditions =" te.wgCompanyId = "+wgCompanyId;	
				}
			 
			 
		 }
         
         
         if(townshipStreets > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and te.townshipStreets = "+townshipStreets;	
				} 
				else{
					conditions =" te.townshipStreets = "+townshipStreets;	
				}
			 
			 
		 }
         
         if(!"".equals(areaName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and te.area like '%"+areaName+"%'";	
				} 
				else{
					conditions =" te.area like '%"+areaName+"%'";	
				}
			 
		 }
         
         if(!"".equals(qstartTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and left(th.startTime,10)  >= '"+qstartTime+"'" ;	 
			 } 
			 else{
				 conditions =" left(th.startTime,10)  >= '"+qstartTime+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and left(th.startTime,10)  <= '"+qendTime+"'" ;	 
			 } 
			 else{
				 conditions =" left(th.startTime,10)  <= '"+qendTime+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(userName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and tu.userName like '%"+userName+"%'";	
				} 
				else{
					conditions =" tu.userName like '%"+userName+"%'";	
				}
			 
		 }
		 
		 if(!"100".equals(ywstatus)){
			 if(!"".equals(conditions)){
				 if("0".equals(ywstatus)){
					   conditions =conditions+" and (th.ywstatus = '0' or th.ywstatus = '8') ";	 
					 }
					 else{
				      conditions =conditions+" and th.ywstatus = '"+ywstatus+"'";	
					 }
				} 
				else{
					if("0".equals(ywstatus)){
						 conditions ="  (th.ywstatus = '0' or th.ywstatus = '8') ";	 
						 }
					else{
					   conditions =" th.ywstatus = '"+ywstatus+"'";
					}
				}
			 
		 }
		 
		 if(ywResult != 100){
			 if(!"".equals(conditions)){
				
				   conditions =conditions+" and th.ywResult = "+ywResult;	
				
				} 
				else{
					
					    conditions =" th.ywResult = "+ywResult;	
					
				}
			 
			 
		 }
		 
		 if(!"".equals(conditions)){
	    //   sql ="select th.id,tui.id as userId,tui.loginName,th.registNumber,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,(case when th.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(th.maintainTypecode,'') as maintainTypecode,th.startTime,th.endTime,th.subTime, (case when datediff(minute,th.startTime,th.endTime)>0 then datediff(minute,th.startTime,th.endTime) else '0' end)  as dateSpan, (case when th.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when th.ePosition = '0' then '轿厢' else '机房' end) as ePosition,th.ywstatus,th.picNum,isnull(tu.userName,'')as userName,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName from YwManagerInfo th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid  where "+ conditions+"  and th.ywKind ='0'  and te.dailingFlag = 0  and th.id =(select max(id) from YwManagerInfo where registNumber = th.registNumber) ";
		//	 conditionsSql = "select count(1) from  YwManagerInfo  th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid   where "+ conditions+"  and th.ywKind ='0' and te.dailingFlag = 0  and th.id =(select max(id) from YwManagerInfo where registNumber = th.registNumber) ";
			 sql ="select th.id,tui.id as userId,tui.loginName,th.registNumber,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,te.address,te.buildingName,th.maintainTypecode,th.startTime,th.endTime,th.subTime,th.startTime,th.endTime,th.ywstatus,th.picNum,tu.userName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName from YwManagerInfo th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid  where "+ conditions+"  and th.ywKind ='0'  and te.dailingFlag = 0 ";
			 conditionsSql = "select count(1) from  YwManagerInfo  th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid   where "+ conditions+"  and th.ywKind ='0' and te.dailingFlag = 0   ";
			
			 if(role ==1 || role==2){   //为了演示不看到超期记录
            //	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.loginName  from  TwoCodeUserInfo tu  where tu.id = ?", new Object[] { userid });
				 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.isyanshi  from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
					
				 if(userInfoVO != null){
    	    	//	 String userName2 =userInfoVO.getLoginName();
					 int isyanshi =userInfoVO.getIsyanshi();
    	    	//	 if("cy".equals(userName2)){
					 if(isyanshi == 1){
    	    			 sql ="select th.id,tui.id as userId,tui.loginName,th.registNumber,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,te.address,te.buildingName,th.maintainTypecode,th.startTime,th.endTime,th.subTime,th.startTime,th.endTime,th.ywstatus,th.picNum,tu.userName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName from YwManagerInfo th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid  where "+ conditions+"  and th.ywKind ='0'  and te.dailingFlag = 0  and DATEDIFF(dd,convert(datetime,th.endTime,120),getdate()) < 15";
    	    			 conditionsSql = "select count(1) from  YwManagerInfo  th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid   where "+ conditions+"  and th.ywKind ='0' and te.dailingFlag = 0  and DATEDIFF(dd,convert(datetime,th.endTime,120),getdate()) < 15 ";
    	    			
    	    		 }
    	    	 }
			}
		 }
			 else{
	   //    sql ="select th.id,tui.id as userId,tui.loginName,th.registNumber,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,(case when th.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(th.maintainTypecode,'') as maintainTypecode,th.startTime,th.endTime,th.subTime, (case when datediff(minute,th.startTime,th.endTime)>0 then datediff(minute,th.startTime,th.endTime) else '0' end)  as dateSpan, (case when th.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when th.ePosition = '0' then '轿厢' else '机房' end) as ePosition,th.ywstatus,th.picNum,isnull(tu.userName,'')as userName,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName from  YwManagerInfo  th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid  where  th.ywKind ='0' and  te.dailingFlag = 0  and th.id =(select max(id) from YwManagerInfo where registNumber = th.registNumber) ";
	  //	 conditionsSql = "select count(1) from  YwManagerInfo   th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid   where  th.ywKind ='0' and te.dailingFlag = 0 and th.id =(select max(id) from YwManagerInfo where registNumber = th.registNumber) ";
		     sql ="select th.id,tui.id as userId,tui.loginName,th.registNumber,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,te.address,te.buildingName,th.maintainTypecode,th.startTime,th.endTime,th.subTime,th.startTime,th.endTime,th.ywstatus,th.picNum,tu.userName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName from  YwManagerInfo  th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid  where  th.ywKind ='0' and  te.dailingFlag = 0  ";
			 conditionsSql = "select count(1) from  YwManagerInfo   th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid   where  th.ywKind ='0' and te.dailingFlag = 0 ";
			
			 if(role ==1 || role==2){   //为了演示不看到超期记录
      //      	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.loginName  from  TwoCodeUserInfo tu  where tu.id = ?", new Object[] { userid });
				 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.isyanshi  from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
				 
				 if(userInfoVO != null){
    	    	//	 String userName2 =userInfoVO.getLoginName();
					 int isyanshi =userInfoVO.getIsyanshi();
    	    /*		 if("cy".equals(userName2)){
    	    			  sql ="select th.id,tui.id as userId,tui.loginName,th.registNumber,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,te.address,te.buildingName,th.maintainTypecode,th.startTime,th.endTime,th.subTime,th.startTime,th.endTime,th.ywstatus,th.picNum,tu.userName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName from  YwManagerInfo  th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid  where  th.ywKind ='0' and  te.dailingFlag = 0  and DATEDIFF(dd,convert(datetime,th.endTime,120),getdate()) < 15";
    	    			   conditionsSql = "select count(1) from  YwManagerInfo   th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid   where  th.ywKind ='0' and te.dailingFlag = 0 and DATEDIFF(dd,convert(datetime,th.endTime,120),getdate()) < 15";
    	    				
    	    		 } */
					 if(isyanshi == 1){
					   sql ="select th.id,tui.id as userId,tui.loginName,th.registNumber,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,te.address,te.buildingName,th.maintainTypecode,th.startTime,th.endTime,th.subTime,th.startTime,th.endTime,th.ywstatus,th.picNum,tu.userName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName from  YwManagerInfo  th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid  where  th.ywKind ='0' and  te.dailingFlag = 0  and DATEDIFF(dd,convert(datetime,th.endTime,120),getdate()) < 15";
   	    			   conditionsSql = "select count(1) from  YwManagerInfo   th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid   where  th.ywKind ='0' and te.dailingFlag = 0 and DATEDIFF(dd,convert(datetime,th.endTime,120),getdate()) < 15";
   	    			
	    	    		}
    	    	 }
			}
			 }
		  
		 
		   total =YwInfoVO.countBySql(YwInfoVO.class, conditionsSql, null);
		   items=YwInfoVO.findBySql(YwInfoVO.class, sql, null, "th."+SortName+" "+SortValue, rows, (page-1)*rows);
		 } catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  Map<String, Object> result = new HashMap<String, Object>();
		  
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	 }
	 
	 public View query(YwInfoVO info,int page, int rows)throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 String registNumber ="";
		 String address ="";
		 String buildingName ="";
		 String loginName ="";
		 int ywCompanyId =0;
		 int wgCompanyId =0;
		 String areaName="";
		 int ywResult =0;
		 String qstartTime ="";
		 String qendTime="";
		 int zjcompanyId2 =0;  //非查询用的，是登陆过程中系统后台根据role不同展示不同列表用的
		 int townshipStreets =0;
		 
		 registNumber =info.getRegistNumber();
		 address =info.getAddress();
		 areaName =info.getArea();
		 buildingName =info.getBuildingName();
		 loginName = info.getLoginName();
		 ywResult=info.getYwResult();     //审核结论查询
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 ywCompanyId =info.getCompanyId();
		 wgCompanyId =info.getCompanyId2();
		 townshipStreets =info.getTownshipStreets();
		 /*
		 registNumber ="%"+info.getRegistNumber()+"%";
		 buildingName ="%"+info.getBuildingName()+"%";
		 loginName ="%"+info.getLoginName()+"%";
		 ywCompanyId =info.getCompanyId();
		 if(info.getArea()==null)
			 areaName ="%"+""+"%";
		 else
		 areaName ="%"+info.getArea()+"%";
		 ywResult=info.getYwResult();     //审核结论查询
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
         */
		 
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 if(role==10 || role==11){
	           UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	         if(userInfoVO != null){
	             zjcompanyId2 = userInfoVO.getCompanyId();
	             conditions = " te.zjcompanyId = "+zjcompanyId2;
	             }	 
	          }
		 
		 if(!"".equals(registNumber)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and th.registNumber like '%"+registNumber+"%'";	
				} 
				else{
				 conditions =" th.registNumber like '%"+registNumber+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(address)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and te.address like '%"+address+"%'";	
				} 
				else{
					 conditions =" te.address like '%"+address+"%'";	
				}
			 
		 }
         
         if(!"".equals(buildingName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and te.buildingName like '%"+buildingName+"%'";	
				} 
				else{
					conditions =" te.buildingName like '%"+buildingName+"%'";	
				}
			 
		 }
         
         if(!"".equals(loginName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and tui.loginName like '%"+loginName+"%'";	
				} 
				else{
					conditions =" tui.loginName like '%"+loginName+"%'";	
				}
			 
		 }
         
         if(ywCompanyId > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and te.ywCompanyId = "+ywCompanyId;	
				} 
				else{
					conditions =" te.ywCompanyId = "+ywCompanyId;	
				}
			 
			 
		 }
         
         if(wgCompanyId > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and te.wgCompanyId = "+wgCompanyId;	
				} 
				else{
					conditions =" te.wgCompanyId = "+wgCompanyId;	
				}
			 
			 
		 }
         
         
         if(townshipStreets > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and te.townshipStreets = "+townshipStreets;	
				} 
				else{
					conditions =" te.townshipStreets = "+townshipStreets;	
				}
			 
			 
		 }
         
         if(!"".equals(areaName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and te.area like '%"+areaName+"%'";	
				} 
				else{
					conditions =" te.area like '%"+areaName+"%'";	
				}
			 
		 }
         
         if(!"".equals(qstartTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and left(th.startTime,10)  >= '"+qstartTime+"'" ;	 
			 } 
			 else{
				 conditions =" left(th.startTime,10)  >= '"+qstartTime+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and left(th.startTime,10)  <= '"+qendTime+"'" ;	 
			 } 
			 else{
				 conditions =" left(th.startTime,10)  <= '"+qendTime+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(conditions)){
	    //   sql ="select th.id,tui.id as userId,tui.loginName,th.registNumber,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,(case when th.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(th.maintainTypecode,'') as maintainTypecode,th.startTime,th.endTime,th.subTime, (case when datediff(minute,th.startTime,th.endTime)>0 then datediff(minute,th.startTime,th.endTime) else '0' end)  as dateSpan, (case when th.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when th.ePosition = '0' then '轿厢' else '机房' end) as ePosition,th.ywstatus,th.picNum,isnull(tu.userName,'')as userName,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName from YwManagerInfo th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid  where "+ conditions+"  and th.ywKind ='0'  and te.dailingFlag = 0  and th.id =(select max(id) from YwManagerInfo where registNumber = th.registNumber) ";
		//	 conditionsSql = "select count(1) from  YwManagerInfo  th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid   where "+ conditions+"  and th.ywKind ='0' and te.dailingFlag = 0  and th.id =(select max(id) from YwManagerInfo where registNumber = th.registNumber) ";
			 sql ="select th.id,tui.id as userId,tui.loginName,th.registNumber,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,te.address,te.buildingName,th.maintainTypecode,th.startTime,th.endTime,th.subTime,th.startTime,th.endTime,th.ywstatus,th.picNum,tu.userName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName from YwManagerInfo th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid  where "+ conditions+"  and th.ywKind ='0'  and te.dailingFlag = 0 ";
			 conditionsSql = "select count(1) from  YwManagerInfo  th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid   where "+ conditions+"  and th.ywKind ='0' and te.dailingFlag = 0   ";
					 	
		 }
			 else{
	   //    sql ="select th.id,tui.id as userId,tui.loginName,th.registNumber,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,(case when th.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(th.maintainTypecode,'') as maintainTypecode,th.startTime,th.endTime,th.subTime, (case when datediff(minute,th.startTime,th.endTime)>0 then datediff(minute,th.startTime,th.endTime) else '0' end)  as dateSpan, (case when th.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when th.ePosition = '0' then '轿厢' else '机房' end) as ePosition,th.ywstatus,th.picNum,isnull(tu.userName,'')as userName,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName from  YwManagerInfo  th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid  where  th.ywKind ='0' and  te.dailingFlag = 0  and th.id =(select max(id) from YwManagerInfo where registNumber = th.registNumber) ";
	  //	 conditionsSql = "select count(1) from  YwManagerInfo   th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid   where  th.ywKind ='0' and te.dailingFlag = 0 and th.id =(select max(id) from YwManagerInfo where registNumber = th.registNumber) ";
		     sql ="select th.id,tui.id as userId,tui.loginName,th.registNumber,th.ywResult,th.flexStartx,th.flexStarty,th.flexEndx,th.flexEndy,te.address,te.buildingName,th.maintainTypecode,th.startTime,th.endTime,th.subTime,th.startTime,th.endTime,th.ywstatus,th.picNum,tu.userName,tc.companyName,(select companyName from TwoCodeCompanyInfo where id = te.wgCompanyId) as wgcompanyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName from  YwManagerInfo  th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid  where  th.ywKind ='0' and  te.dailingFlag = 0  ";
			 conditionsSql = "select count(1) from  YwManagerInfo   th left join TwoCodeElevatorInfo te on th.registNumber =te.registNumber left join TwoCodeUserinfo tui on tui.id=th.userId left join TwoCodeUserExtInfo tu on tu.userid = th.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid   where  th.ywKind ='0' and te.dailingFlag = 0 ";
					
			 }
		
		 
		  long total =YwInfoVO.countBySql(YwInfoVO.class, conditionsSql, null);
		  List<YwInfoVO> items=YwInfoVO.findBySql(YwInfoVO.class, sql, null, "th.id desc", rows, (page-1)*rows);
			
		  Map<String, Object> result = new HashMap<String, Object>();
		  
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	 }
	 
	
	 
	 public String updateYwResultById(int id,int ywResult,int flexStartx,int flexStarty, int flexEndx ,int flexEndy){
    	 System.out.println("ywResult--->"+ywResult);
    	 String result = "failure";
    	 int num = 0;
    	 int num2 =0;
    	 int num3 =0;
    	
    		 
 			try {
				num = YwInfo.updateAll(YwInfo.class,"ywResult=?,flexStartx=?,flexStarty=?,flexEndx=?,flexEndy=?", new Object[]{ywResult,flexStartx,flexStarty,flexEndx,flexEndy}, "id=?", new Object[] { id});
			} catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 			if(num>0){
 				
 				result = "success";
 			}
 			
    	 return result;
    }
	 
	  public String newshenheByid(int id,String map_x,String map_y,int ywResult,int flexStartx,int flexStarty, int flexEndx ,int flexEndy){
	    	 System.out.println("map_x--->"+map_x);
	    	 System.out.println("map_Y--->"+map_y);
	    	 System.out.println("ywResult--->"+ywResult);
	    	 String result = "failure";
	    	 int num = 0;
	    	 int num2 =0;
	    	 int num3 =0;
	    	 try {
	    		 ActiveRecordBase.beginTransaction();
	 			num = YwInfo.updateAll(YwInfo.class, "ywstatus=?,ywResult=?,ywimageValidty=?,flexStartx=?,flexStarty=?,flexEndx=?,flexEndy=?", new Object[]{"1",ywResult,ywResult,flexStartx,flexStarty,flexEndx,flexEndy}, "id=?", new Object[] { id});
	 			if(num>0){
	 				
	 				YwInfoVO ywInfoVO =(YwInfoVO)YwInfoVO.findFirstBySql(YwInfoVO.class, "select t.registNumber  from YwManagerInfo t where t.id=?", new Object[] { id});
	 				if(ywInfoVO != null){
	 					
	 				//更新历史运维记录中对应的那条相同的记录	
	 				
	 				String registNumber = ywInfoVO.getRegistNumber();
	 				String startTime = ywInfoVO.getStartTime();
	 				String endTime = ywInfoVO.getEndTime();
	 				String ywStatus ="1";
	 				ywStatus=ywInfoVO.getYwstatus();
	 				num3 = HisYwInfoVO.updateAll(HisYwInfoVO.class, "ywStatus=?,ywResult=?,ywimageValidty=?",new Object[]{ywStatus,ywResult,ywResult}, "registNumber =?,startTime=?,endTime=?", new Object[]{registNumber,startTime,endTime});
	 				
	 				
	 				num2 =ElevaltorInfo.updateAll(ElevaltorInfo.class, "map_X=?,map_Y=?", new Object[]{map_x,map_y}, "registNumber=?", new Object[] {ywInfoVO.getRegistNumber()});
	 			
	 				}
	 				result = "success";
	 			}
	 			 ActiveRecordBase.commit();
	 			
	 		 }
	 		catch (ActiveRecordException e) {
	 			try {
					  ActiveRecordBase.rollback();
					} catch (TransactionException et) {
						et.printStackTrace();
					}
	 		  } 
	    	 return result;
	    }
    
	 
    public String shenheByid(int id,String map_x,String map_y,int ywResult,int flexStartx,int flexStarty, int flexEndx ,int flexEndy){
    	 System.out.println("map_x--->"+map_x);
    	 System.out.println("map_Y--->"+map_y);
    	 System.out.println("ywResult--->"+ywResult);
    	 String result = "failure";
    	 int num = 0;
    	 int num2 =0;
    	 int num3 =0;
    	 try {
    		 ActiveRecordBase.beginTransaction();
 			num = YwInfo.updateAll(YwInfo.class, "ywstatus=?,ywResult=?,flexStartx=?,flexStarty=?,flexEndx=?,flexEndy=?", new Object[]{"1",ywResult,flexStartx,flexStarty,flexEndx,flexEndy}, "id=?", new Object[] { id});
 			if(num>0){
 				
 				YwInfoVO ywInfoVO =(YwInfoVO)YwInfoVO.findFirstBySql(YwInfoVO.class, "select t.registNumber  from YwManagerInfo t where t.id=?", new Object[] { id});
 				if(ywInfoVO != null){
 					
 				//更新历史运维记录中对应的那条相同的记录	
 				/*
 				String registNumber = ywInfoVO.getRegistNumber();
 				String startTime = ywInfoVO.getStartTime();
 				String endTime = ywInfoVO.getEndTime();
 				String ywStatus ="1";
 				ywStatus=ywInfoVO.getYwstatus();
 				num3 = HisYwInfoVO.updateAll(HisYwInfoVO.class, "ywStatus=?",new Object[]{ywStatus}, "registNumber =?,startTime=?,endTime=?", new Object[]{registNumber,startTime,endTime});
 				*/
 				
 				num2 =ElevaltorInfo.updateAll(ElevaltorInfo.class, "map_X=?,map_Y=?", new Object[]{map_x,map_y}, "registNumber=?", new Object[] {ywInfoVO.getRegistNumber()});
 				if(num2>0)
 				{
 				/*
 				YWOutDateVO yWOutDateVO =(YWOutDateVO)YWOutDateVO.findFirstBySql(YWOutDateVO.class,"select t.id,t.endTime from  TwoCodeYWOutDate t where t.registNumber=? and t.isProcessFlag =?",new Object[] {ywInfoVO.getRegistNumber(),0});
 				if(yWOutDateVO != null){
 					int  pid = yWOutDateVO.getId();
 					java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String s = format1.format(new Date());
 					long days =0;
 					long diff = new Date().getTime()-yWOutDateVO.getEndTime().getTime();
 					days = diff / (1000 * 60 * 60 * 24);
 					if(days > 15){
 					num3 = YWOutDateVO.updateAll(YWOutDateVO.class, "isProcessFlag=?,dealWithTime=?,tcOutDateFlag=?", new Object[] {1,s,3}, "id=?", new Object[]{pid});
 					if(num3 >0)
 					 System.out.println("已经处理超期信息");
 					}
 				}
 				*/
 			//	if("success".equals(ywOutDateFun(ywInfoVO.getRegistNumber())))
 			//	 System.out.println("添加超期信息成功");
 				}
 				}
 				result = "success";
 			}
 			 ActiveRecordBase.commit();
 			
 		 }
 		catch (ActiveRecordException e) {
 			try {
				  ActiveRecordBase.rollback();
				} catch (TransactionException et) {
					et.printStackTrace();
				}
 		  } 
    	 return result;
    }
    
   
	public String ywOutDateFun(String registNumber){ System.out.println("ywOutDateFun");
    	String result = "failure";
		List<YwInfoVO> list = null;
		YwInfoVO nextalarm = null;
		YwInfoVO prealarm  = null;
		Date nextStartTime =null;
		Date preEndTime = null;
		Date nextsubTime =null;
		YWOutDate yWOutDate = new YWOutDate();
		try {
			list =YwInfoVO.findBySql(YwInfoVO.class, "select top 2 t.* from YwManagerInfo t where t.registNumber=? and t.ywstatus='1' and t.ywKind='0' order by t.endTime desc", new Object[]{registNumber});
		} catch (ActiveRecordException e1) {
			System.out.println("ywOutDateFun查询出错");
			e1.printStackTrace();
		}
		if(list != null && list.size()==2){
			for(int i=0;i<2;i++){
				if(i==0)
					nextalarm =list.get(i);	
				else
					prealarm =list.get(i);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			Calendar properWhTime = Calendar.getInstance();
			try {
				nextStartTime =sdf.parse(nextalarm.getStartTime());
				System.out.println("nextStartTime"+nextStartTime);
				preEndTime   = sdf.parse(prealarm.getEndTime());
				System.out.println("preEndTime"+nextStartTime);
				long days =0;
			    long diff = nextStartTime.getTime()-preEndTime.getTime();
			    days = diff / (1000 * 60 * 60 * 24);
			    if(days >15){
			    System.out.println("registNumber"+registNumber);
			    yWOutDate.setRegistNumber(registNumber);
			    properWhTime.setTime(preEndTime);
			    properWhTime.add(Calendar.DAY_OF_YEAR,15); 
			    yWOutDate.setProperWhTime(sdf.format(properWhTime.getTime()));
			    yWOutDate.setBeyondTime((int)days-15);
			    yWOutDate.setBeyondLevel( "运维超期");
			    yWOutDate.setDealWithTime(nextalarm.getStartTime());
			    yWOutDate.setIsProcessFlag(1);
			    yWOutDate.setTcOutDateFlag(1);
			    yWOutDate.setEndTime(prealarm.getEndTime());
			    try {
					if(yWOutDate.save())
						System.out.println("添加成功");
				} catch (ActiveRecordException e) {
					// TODO Auto-generated catch block
					System.out.println("添加出错");
					e.printStackTrace();
				}
			    }
			    else{
			    nextsubTime	=sdf.parse(nextalarm.getSubTime());
			    long days2 =0;
			    long diff2 = nextsubTime.getTime()-preEndTime.getTime();
			    days2 = diff2 / (1000 * 60 * 60 * 24);
			    if(days2 >15){
			    yWOutDate.setRegistNumber(registNumber);
			    properWhTime.setTime(preEndTime);
			    properWhTime.add(Calendar.DAY_OF_YEAR,15); 
			    yWOutDate.setProperWhTime(sdf.format(properWhTime.getTime()));
			    yWOutDate.setBeyondTime((int)days2-15);
			    yWOutDate.setBeyondLevel( "上传超期");
			    yWOutDate.setIsProcessFlag(1);
			    yWOutDate.setTcOutDateFlag(2);
			    yWOutDate.setDealWithTime(nextalarm.getSubTime());
			    yWOutDate.setEndTime(prealarm.getEndTime());
			    try {
					if(yWOutDate.save())
						System.out.println("添加成功");
				} catch (ActiveRecordException e) {
					// TODO Auto-generated catch block
					System.out.println("添加出错");
					e.printStackTrace();
				}
			    }
			    }	
					 result ="success";
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
		 return result;
	}
	
	  public String newshenheByid2(int id,int ywResult,int flexStartx,int flexStarty, int flexEndx ,int flexEndy){
		   	 String result = "failure";
		   	 int num = 0;
		   	 int num3 =0;
		   	 try {  
		   		    ActiveRecordBase.beginTransaction();
					num = YwInfo.updateAll(YwInfo.class, "ywstatus=?,ywResult=?,ywimageValidty=?,flexStartx=?,flexStarty=?,flexEndx=?,flexEndy=?", new Object[]{"1",ywResult,ywResult,flexStartx,flexStarty,flexEndx,flexEndy}, "id=?", new Object[] { id});	
					if(num>0){
						YwInfoVO ywInfoVO =(YwInfoVO)YwInfoVO.findFirstBySql(YwInfoVO.class, "select t.registNumber,t.ywStatus,t.startTime,t.endTime  from YwManagerInfo t where t.id=?", new Object[] { id});
						if(ywInfoVO != null){
							//更新历史运维记录中对应的那条相同的记录	
			 				
			 				String registNumber = ywInfoVO.getRegistNumber();
			 				String startTime = ywInfoVO.getStartTime();
			 				String endTime = ywInfoVO.getEndTime();
			 				String ywStatus ="1";
			 				ywStatus=ywInfoVO.getYwstatus();
			 				num3 = HisYwInfoVO.updateAll(HisYwInfoVO.class, "ywStatus=?,ywResult=?,ywimageValidty=?",new Object[]{ywStatus,ywResult,ywResult}, "registNumber =? and startTime=? and endTime=?", new Object[]{registNumber,startTime,endTime});
			 				
							/*
							YWOutDateVO yWOutDateVO =(YWOutDateVO)YWOutDateVO.findFirstBySql(YWOutDateVO.class,"select t.id,t.endTime from  TwoCodeYWOutDate t where t.registNumber=? and t.isProcessFlag =?",new Object[] {ywInfoVO.getRegistNumber(),0});
							if(yWOutDateVO != null){
			 					int  pid = yWOutDateVO.getId();
			 					java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								String s = format1.format(new Date());
			 					long days =0;
			 					long diff = new Date().getTime()-yWOutDateVO.getEndTime().getTime();
			 					days = diff / (1000 * 60 * 60 * 24);
			 					if(days > 15){
			 					num3 = YWOutDateVO.updateAll(YWOutDateVO.class, "isProcessFlag=?,dealWithTime=?,tcOutDateFlag=?", new Object[] {1,s,3}, "id=?", new Object[]{pid});
			 					if(num3 >0)
			 					 System.out.println("已经处理超期信息");
			 					}
			 				}
			 				*/
						//	if("success".equals(ywOutDateFun(ywInfoVO.getRegistNumber())))
				 		//		 System.out.println("添加超期信息成功");
						}
						result = "success";
						}
					 ActiveRecordBase.commit();
					}
					
				catch (ActiveRecordException e) {
					try {
						  ActiveRecordBase.rollback();
						} catch (TransactionException et) {
							et.printStackTrace();
						}
					  e.printStackTrace();
				  } 
		   	 return result;
		   }
    
    public String shenheByid2(int id,int ywResult,int flexStartx,int flexStarty, int flexEndx ,int flexEndy){
   	 String result = "failure";
   	 int num = 0;
   	 int num3 =0;
   	 try {  
   		    ActiveRecordBase.beginTransaction();
			num = YwInfo.updateAll(YwInfo.class, "ywstatus=?,ywResult=?,flexStartx=?,flexStarty=?,flexEndx=?,flexEndy=?", new Object[]{"1",ywResult,flexStartx,flexStarty,flexEndx,flexEndy}, "id=?", new Object[] { id});	
			if(num>0){
				YwInfoVO ywInfoVO =(YwInfoVO)YwInfoVO.findFirstBySql(YwInfoVO.class, "select t.registNumber,t.ywStatus,t.startTime,t.endTime  from YwManagerInfo t where t.id=?", new Object[] { id});
				if(ywInfoVO != null){
					//更新历史运维记录中对应的那条相同的记录	
	 				
	 				String registNumber = ywInfoVO.getRegistNumber();
	 				String startTime = ywInfoVO.getStartTime();
	 				String endTime = ywInfoVO.getEndTime();
	 				String ywStatus ="1";
	 				ywStatus=ywInfoVO.getYwstatus();
	 				num3 = HisYwInfoVO.updateAll(HisYwInfoVO.class, "ywStatus=?",new Object[]{ywStatus}, "registNumber =? and startTime=? and endTime=?", new Object[]{registNumber,startTime,endTime});
	 				
					/*
					YWOutDateVO yWOutDateVO =(YWOutDateVO)YWOutDateVO.findFirstBySql(YWOutDateVO.class,"select t.id,t.endTime from  TwoCodeYWOutDate t where t.registNumber=? and t.isProcessFlag =?",new Object[] {ywInfoVO.getRegistNumber(),0});
					if(yWOutDateVO != null){
	 					int  pid = yWOutDateVO.getId();
	 					java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String s = format1.format(new Date());
	 					long days =0;
	 					long diff = new Date().getTime()-yWOutDateVO.getEndTime().getTime();
	 					days = diff / (1000 * 60 * 60 * 24);
	 					if(days > 15){
	 					num3 = YWOutDateVO.updateAll(YWOutDateVO.class, "isProcessFlag=?,dealWithTime=?,tcOutDateFlag=?", new Object[] {1,s,3}, "id=?", new Object[]{pid});
	 					if(num3 >0)
	 					 System.out.println("已经处理超期信息");
	 					}
	 				}
	 				*/
				//	if("success".equals(ywOutDateFun(ywInfoVO.getRegistNumber())))
		 		//		 System.out.println("添加超期信息成功");
				}
				result = "success";
				}
			 ActiveRecordBase.commit();
			}
			
		catch (ActiveRecordException e) {
			try {
				  ActiveRecordBase.rollback();
				} catch (TransactionException et) {
					et.printStackTrace();
				}
			  e.printStackTrace();
		  } 
   	 return result;
   }
    
    public String newshenheByid3(int id,int ywResult){
     	 String result = "failure";
     	 int num = 0;
     	 int num3 =0;
     	 try {
  			num = YwInfo.updateAll(YwInfo.class, "ywstatus=?,ywResult=?,ywimageValidty=?", new Object[]{"4",ywResult,ywResult}, "id=?", new Object[] { id});
  			if(num>0){
  				
  				YwInfoVO ywInfoVO =(YwInfoVO)YwInfoVO.findFirstBySql(YwInfoVO.class, "select t.registNumber,t.ywStatus,t.startTime,t.endTime  from YwManagerInfo t where t.id=?", new Object[] { id});
				if(ywInfoVO != null){
					//更新历史运维记录中对应的那条相同的记录	
	 				
	 				String registNumber = ywInfoVO.getRegistNumber();
	 				String startTime = ywInfoVO.getStartTime();
	 				String endTime = ywInfoVO.getEndTime();
	 				String ywStatus ="1";
	 				ywStatus=ywInfoVO.getYwstatus();
	 				num3 = HisYwInfoVO.updateAll(HisYwInfoVO.class, "ywStatus=?,ywResult=?,ywimageValidty=?",new Object[]{ywStatus,ywResult,ywResult}, "registNumber =? and startTime=? and endTime=?", new Object[]{registNumber,startTime,endTime});
				}
  				result = "success";
  				}
  			}
  			
  		catch (ActiveRecordException e) {
  			// TODO Auto-generated catch block
  			  e.printStackTrace();
  		  } 
     	 return result;
     }
    
    public String shenheByid3(int id,int ywResult){
      	 String result = "failure";
      	 int num = 0;
      	 int num3 =0;
      	 try {
   			num = YwInfo.updateAll(YwInfo.class, "ywstatus=?,ywResult=?", new Object[]{"4",ywResult}, "id=?", new Object[] { id});
   			if(num>0){
   				
   				YwInfoVO ywInfoVO =(YwInfoVO)YwInfoVO.findFirstBySql(YwInfoVO.class, "select t.registNumber,t.ywStatus,t.startTime,t.endTime  from YwManagerInfo t where t.id=?", new Object[] { id});
				if(ywInfoVO != null){
					//更新历史运维记录中对应的那条相同的记录	
	 				
	 				String registNumber = ywInfoVO.getRegistNumber();
	 				String startTime = ywInfoVO.getStartTime();
	 				String endTime = ywInfoVO.getEndTime();
	 				String ywStatus ="1";
	 				ywStatus=ywInfoVO.getYwstatus();
	 				num3 = HisYwInfoVO.updateAll(HisYwInfoVO.class, "ywStatus=?",new Object[]{ywStatus}, "registNumber =? and startTime=? and endTime=?", new Object[]{registNumber,startTime,endTime});
				}
   				result = "success";
   				}
   			}
   			
   		catch (ActiveRecordException e) {
   			// TODO Auto-generated catch block
   			  e.printStackTrace();
   		  } 
      	 return result;
      }
    
    public View shenhePointByXcReg(int id){
    	XcInfoVO myYwInfoVO =null;
    	 Map<String, Object> obj = new HashMap<String, Object>();
    	String sql ="select te.map_x,te.map_y,t.map_X0,t.map_Y0,t.map_X1,t.map_Y1,t.map_X2,t.map_Y2 from XcManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber where t.id = ?";
    	try {
			myYwInfoVO =XcInfoVO.findFirstBySql(XcInfoVO.class, sql, new Object[] {id});
			if(myYwInfoVO != null){
				obj.put("map_x",myYwInfoVO.getMap_X());
				obj.put("map_y",myYwInfoVO.getMap_Y());
				obj.put("map_x0", myYwInfoVO.getMap_X0());
				obj.put("map_y0", myYwInfoVO.getMap_Y0());
				obj.put("map_x1", myYwInfoVO.getMap_X1());
				obj.put("map_y1", myYwInfoVO.getMap_Y1());
				obj.put("map_x2", myYwInfoVO.getMap_X2());
				obj.put("map_y2", myYwInfoVO.getMap_Y2());	
			}
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new JsonView(obj);
    }
    
    public View shenhePointByReg(int id){
    	YwInfoVO myYwInfoVO =null;
    	 Map<String, Object> obj = new HashMap<String, Object>();
    	String sql ="select te.map_x,te.map_y,t.map_X0,t.map_Y0,t.map_X1,t.map_Y1,t.map_X2,t.map_Y2 from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber where t.id = ?";
    	try {
			myYwInfoVO =YwInfoVO.findFirstBySql(YwInfoVO.class, sql, new Object[] {id});
			if(myYwInfoVO != null){
				obj.put("map_x",myYwInfoVO.getMap_X());
				obj.put("map_y",myYwInfoVO.getMap_Y());
				obj.put("map_x0", myYwInfoVO.getMap_X0());
				obj.put("map_y0", myYwInfoVO.getMap_Y0());
				obj.put("map_x1", myYwInfoVO.getMap_X1());
				obj.put("map_y1", myYwInfoVO.getMap_Y1());
				obj.put("map_x2", myYwInfoVO.getMap_X2());
				obj.put("map_y2", myYwInfoVO.getMap_Y2());	
			}
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new JsonView(obj);
    }
    
    public View shenhePointByhisReg(int id){
    	HisYwInfoVO myYwInfoVO =null;
    	 Map<String, Object> obj = new HashMap<String, Object>();
    	String sql ="select te.map_x,te.map_y,t.map_X0,t.map_Y0,t.map_X1,t.map_Y1,t.map_X2,t.map_Y2 from hisYwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber where t.id = ?";
    	try {
			myYwInfoVO =HisYwInfoVO.findFirstBySql(HisYwInfoVO.class, sql, new Object[] {id});
			if(myYwInfoVO != null){
				obj.put("map_x",myYwInfoVO.getMap_X());
				obj.put("map_y",myYwInfoVO.getMap_Y());
				obj.put("map_x0", myYwInfoVO.getMap_X0());
				obj.put("map_y0", myYwInfoVO.getMap_Y0());
				obj.put("map_x1", myYwInfoVO.getMap_X1());
				obj.put("map_y1", myYwInfoVO.getMap_Y1());
				obj.put("map_x2", myYwInfoVO.getMap_X2());
				obj.put("map_y2", myYwInfoVO.getMap_Y2());	
			}
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new JsonView(obj);
    }
    
    
    public View nywcqlist(int page, int rows){
    	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<YwInfoVO> items = new ArrayList<YwInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
	 
		 long total=0;
		 String sql ="";
		 
		 try {
		 if(role==10 || role ==11){ //质监局
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         total  =YwInfoVO.countBySql(YwInfoVO.class, "select count(1) from (select registNumber  from YwManagerInfo t where t.id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) and  DATEDIFF(dd,convert(datetime,t.endTime,120),getdate()) > 15 ) t1 left join TwoCodeElevatorInfo te on t1.registNumber =te.registNumber where te.dailingFlag = 0 and te.zjcompanyId = "+zjcompanyId,null);
		//	 sql ="select distinct t.id, t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,isnull(tc.companyName,'') as companyName,t.endTime,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile  from YwManagerInfo t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on tc.id=te.ywCompanyId   where t.id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) and  DATEDIFF(dd,convert(datetime,t.endTime,120),getdate()) > 15 and te.dailingFlag = 0 and te.zjcompanyId = "+zjcompanyId;
			 sql ="select distinct t.id, t.registNumber,te.registCode,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,isnull(tc.companyName,'') as companyName,t.endTime,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName,tcu.userName,tcu.contactPhone as telephonemobile  from YwManagerInfo t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on tc.id=te.ywCompanyId  left join  TCuserInfo tcu  on t.userid =tcu.userid  where DATEDIFF(dd,convert(datetime,t.endTime,120),getdate()) > 15  and te.dailingFlag = 0 and te.zjcompanyId = "+zjcompanyId;
	         items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
		
	     }
		 }
		 else if(role==20 || role ==21){ //街道办
			 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int townshipStreets = userInfoVO.getCompanyId();
	         total  =YwInfoVO.countBySql(YwInfoVO.class, "select count(1) from (select registNumber  from YwManagerInfo t where t.id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) and  DATEDIFF(dd,convert(datetime,t.endTime,120),getdate()) > 15 ) t1 left join TwoCodeElevatorInfo te on t1.registNumber =te.registNumber where te.dailingFlag = 0 and te.townshipStreets = "+townshipStreets,null);
		//	 sql ="select distinct t.id, t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,isnull(tc.companyName,'') as companyName,t.endTime,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile  from YwManagerInfo t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on tc.id=te.ywCompanyId   where t.id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) and  DATEDIFF(dd,convert(datetime,t.endTime,120),getdate()) > 15 and te.dailingFlag = 0 and te.townshipStreets = "+townshipStreets;
	         sql ="select distinct t.id, t.registNumber,te.registCode,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,isnull(tc.companyName,'') as companyName,t.endTime,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName,tcu.userName,tcu.contactPhone as telephonemobile  from YwManagerInfo t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on tc.id=te.ywCompanyId  left join  TCuserInfo tcu  on t.userid =tcu.userid  where DATEDIFF(dd,convert(datetime,t.endTime,120),getdate()) > 15  and te.dailingFlag = 0  and te.townshipStreets = "+townshipStreets;
				
	         items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
		
		 }
		 }
		 else
		 {  
			
			total  =YwInfoVO.countBySql(YwInfoVO.class, "select count(1) from YwManagerInfo t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber where t.id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) and  DATEDIFF(dd,convert(datetime,t.endTime,120),getdate()) > 15 and te.dailingFlag = 0",null);
		//	sql ="select distinct t.id, t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,isnull(tc.companyName,'') as companyName,t.endTime,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile  from YwManagerInfo t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on tc.id=te.ywCompanyId   where t.id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) and  DATEDIFF(dd,convert(datetime,t.endTime,120),getdate()) > 15  and te.dailingFlag = 0 ";
			sql ="select distinct t.id, t.registNumber,te.registCode,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,isnull(tc.companyName,'') as companyName,t.endTime,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName,tcu.userName,tcu.contactPhone as telephonemobile  from YwManagerInfo t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on tc.id=te.ywCompanyId  left join  TCuserInfo tcu  on t.userid =tcu.userid  where DATEDIFF(dd,convert(datetime,t.endTime,120),getdate()) > 15  and te.dailingFlag = 0 ";
			items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
			
            if(role ==1 || role==2){   //为了演示不看到超期记录
            	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.isyanshi  from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
    	    	 if(userInfoVO != null){
    	    	/*	 String userName =userInfoVO.getLoginName();
    	    		 if("cy".equals(userName)){
    	    			 total = 0;
    	    			 items = new ArrayList<YwInfoVO>();
    	    		 } */
    	    		int isyanshi =userInfoVO.getIsyanshi();
    	    		if(isyanshi == 1){
    	    			total = 0 ;
    	    			items = new ArrayList<YwInfoVO>();
    	    		}
    	    	 }
			}
		 }
		 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	
    	
    }
    
    public View ywstatisticslist(int page, int rows){
   	     UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<YwInfoVO> items = new ArrayList<YwInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
	 
		 long total=0;
		 String sql ="";
		 
		 try {
		 if(role==10 || role ==11){ //质监局
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         Object[] param=null;
	    //     total  =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from  (select t.ywcompanyId from TwoCodeElevatorInfo  t where t.zjcompanyId = ? group by t.ywcompanyId) t2   ",new Object[]{zjcompanyId});
		//	 sql ="select t2.*,tc.companyName,isnull(t7.ncqetotal,0) as ncqetotal,isnull(t4.utotal,0) as utotal,isnull(t8.nutotal,0) as nutotal,(select  count(distinct t.registNumber)  from TCYEUView  t where t.ywcompanyid  = t2.ywcompanyid) as eywtotal,(select  count(t.registNumber)  from TwoCodeElevatorInfo  t left join ywmanagerinfo y  on t.registNumber = y.registNumber where t.ywcompanyid = t2.ywcompanyid and t.zjcompanyId = "+zjcompanyId+" and y.registNumber is  null ) as uncqetotal  from  (select count(*) as etotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t where t.zjcompanyId = ? group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t6.userId) as nutotal,t6.companyId from (select tyce.userId,tc.companyId from (select  y.userid,max(y.endTime) as endTime  from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role=18   ) tyc  left join ywmanagerinfo y on tyc.userid = y.userid left join TwoCodeElevatorInfo  t on y.registNumber = t.registNumber where t.zjcompanyid =? group by y.userid  ) tyce left join tcuserinfo tc on tyce.userid = tc.id  ) t6 group by t6.companyId) t8 on  t4.companyid =t8.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id left join (select count(t3.registNumber) as ncqetotal,t.ywcompanyId from (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  inner join TwoCodeElevatorInfo  t on t.registNumber = t3.registNumber where t.zjcompanyId = ? group  by ywcompanyid) t7  on t2.ywcompanyid =t7.ywcompanyId ";
	    	 sql ="select t2.*,-1 as uncqetotal,tc.companyName,t7.ncqetotal,t4.utotal,t8.nutotal,(select  count(distinct t.registNumber)  from TCYEUView  t where t.ywcompanyid  = t2.ywcompanyid  and dailingflag =0 and t.zjcompanyId = "+zjcompanyId+"  ) as eywtotal  from  (select count(*) as etotal,sum(case when t.dailingFlag = 1 then 1 else 0 end) as dailingetotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t where t.zjcompanyId = ? group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 and t.isinvalid = 0 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t6.userId) as nutotal,t6.companyId from (select tyce.userId,tc.companyId from (select  y.userid,max(y.endTime) as endTime  from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role=18   ) tyc  left join ywmanagerinfo y on tyc.userid = y.userid left join TwoCodeElevatorInfo  t on y.registNumber = t.registNumber where t.zjcompanyid =? group by y.userid  ) tyce left join tcuserinfo tc on tyce.userid = tc.id  ) t6 group by t6.companyId) t8 on  t4.companyid =t8.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id left join (select count(t3.registNumber) as ncqetotal,t.ywcompanyId from (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join twoCodeelevatorinfo te on t1.registNumber =  te.registNumber  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  inner join TwoCodeElevatorInfo  t on t.registNumber = t3.registNumber where t.zjcompanyId = ? group  by ywcompanyid) t7  on t2.ywcompanyid =t7.ywcompanyId ";
			
	         param = new Object[]{zjcompanyId,zjcompanyId,zjcompanyId};
		//	 items = YwInfoVO.findBySql(YwInfoVO.class, sql, param, "t2.etotal desc,t4.utotal desc ", rows, (page-1)*rows);
	         items = YwInfoVO.findBySql(YwInfoVO.class, sql, param, "t2.etotal desc,t4.utotal desc ");
		     
	     }
		 }
		 else
		 {
		//	total  =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from  (select t.ywcompanyId from TwoCodeElevatorInfo  t group by t.ywcompanyId) t2  ",null);
	    	sql ="select t2.*, -1 as uncqetotal,tc.companyName,t4.utotal,t6.nutotal,(select  count(distinct t.registNumber)  from TCYEUView  t where t.ywcompanyid  = t2.ywcompanyid and dailingflag =0 ) as eywtotal,(select count(t1.registnumber)  from YwManagerInfo t1 left join twoCodeelevatorinfo te on t1.registNumber =  te.registNumber  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0  and  te.ywcompanyid =t2.ywcompanyid ) as ncqetotal  from  (select count(*) as etotal,sum(case when t.dailingFlag = 1 then 1 else 0 end) as dailingetotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 and t.isinvalid = 0 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t5.userid) as nutotal, t5.companyid from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role =18) t5 group by t5.companyid) t6 on  t4.companyid =t6.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id ";
			
		//	items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "t2.etotal desc,t4.utotal desc ", rows, (page-1)*rows);
	    //	items =YwInfoVO.findBySql(YwInfoVO.class, "exec pro_Ywstatistics ",null);
	    	items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "t2.etotal desc,t4.utotal desc ");
			
			
		 }
		 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	
   	
   }
	 
 //   public View ncqetotallist(int ywcompanyId,int page, int rows){  
    public View ncqetotallist( YwInfoVO info,int page, int rows){
    	UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 long total=0;
		 int zjcompanyId2 =0;  //非查询用的，是登陆过程中系统后台根据role不同展示不同列表用的
		 String qstartTime ="";
		 String qendTime="";
		 int ywcompanyId =0;
		 String area ="";
		 ywcompanyId = info.getYwcompanyId();
		 
		 area = info.getArea();
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 
		 String sql ="";
		 String conditionsSql="";
		 Object[] param=null;
		 
		 String conditions2="";
		 String conditions ="";
		 
		 if(!"".equals(area)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.area ='"+area+"'";	
				} 
				else{
					conditions =" t.area ='"+area+"'";	
				}
			 
		 }
		 
		 if(!"".equals(qstartTime)){
			 if(!"".equals(conditions2)){
				 conditions2 =conditions2+" and convert(datetime,endTime,120) >= '"+qstartTime+"'";	
				} 
				else{
					conditions2 =" convert(datetime,endTime,120) >= '"+qstartTime+"'";	
				}
			
			
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions2)){
				 conditions2 =conditions2+" and convert(datetime,endTime,120) <= '"+qendTime+"'";	
				} 
				else{
					conditions2 =" convert(datetime,endTime,120) <= '"+qendTime+"'";	
				}
			
			
		 }
		 
		 List<YwInfoVO> items = new ArrayList<YwInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 UserInfoVO userInfoVO = null;
		 if(role==10 || role==11){    
				try {
					userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
		           if(userInfoVO != null){
		        	   zjcompanyId2 =userInfoVO.getCompanyId();
		        	   if(!"".equals(conditions2)){
		        		   if(!"".equals(conditions)){
		        			   total =YwInfoVO.countBySql(YwInfoVO.class,"select count(t.registnumber) from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1 left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where "+conditions2+"and t.ywcompanyid = "+ywcompanyId+" and  t.zjcompanyid ="+zjcompanyId2+" and "+conditions,null);
						       sql="select t.registnumber,t.address,t.buildingname,t3.endTime from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1 left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15  and te.dailingFlag = 0) t3  on t.registNumber = t3.registNumber where "+conditions2+"and t.ywcompanyid = "+ywcompanyId + " and t.zjcompanyid ="+zjcompanyId2+" and "+conditions;   
	   
		        		   }
		        		   else{
		        		   total =YwInfoVO.countBySql(YwInfoVO.class,"select count(t.registnumber) from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1 left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where "+conditions2+"and t.ywcompanyid = "+ywcompanyId+" and  t.zjcompanyid ="+zjcompanyId2,null);
					       sql="select t.registnumber,t.address,t.buildingname,t3.endTime from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1 left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15  and te.dailingFlag = 0) t3  on t.registNumber = t3.registNumber where "+conditions2+"and t.ywcompanyid = "+ywcompanyId + " and t.zjcompanyid ="+zjcompanyId2;   
		        		   }
		        		 }
		        	   else{
		        		   if(!"".equals(conditions)){
		        			   total =YwInfoVO.countBySql(YwInfoVO.class,"select count(t.registnumber) from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where t.ywcompanyid = "+ywcompanyId+" and  t.zjcompanyid ="+zjcompanyId2+" and "+conditions,null);
						       sql="select t.registnumber,t.address,t.buildingname,t3.endTime from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where t.ywcompanyid = "+ywcompanyId + " and t.zjcompanyid ="+zjcompanyId2+" and "+conditions; 
			        		     
		        		   }
		        		   else{
		        	       total =YwInfoVO.countBySql(YwInfoVO.class,"select count(t.registnumber) from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where t.ywcompanyid = "+ywcompanyId+" and  t.zjcompanyid ="+zjcompanyId2,null);
					       sql="select t.registnumber,t.address,t.buildingname,t3.endTime from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where t.ywcompanyid = "+ywcompanyId + " and t.zjcompanyid ="+zjcompanyId2; 
		        		   }
		        		  }
					    items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
					  
		             }
				} catch (ActiveRecordException e) {
					e.printStackTrace();
				}
				
		        }	 
		
		 if(role ==1 || role ==2){
			try {
				if(!"".equals(conditions2)){
					 if(!"".equals(conditions)){
						 total =YwInfoVO.countBySql(YwInfoVO.class,"select count(t.registnumber) from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where  "+conditions2+"and t.ywcompanyid = "+ywcompanyId+" and "+conditions,null);
						 sql="select t.registnumber,t.address,t.buildingname,t3.endTime from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where  "+conditions2+"and t.ywcompanyid = "+ywcompanyId+" and "+conditions; 		
						    
					 }
					 else{
					total =YwInfoVO.countBySql(YwInfoVO.class,"select count(t.registnumber) from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where  "+conditions2+"and t.ywcompanyid = "+ywcompanyId,null);
				    sql="select t.registnumber,t.address,t.buildingname,t3.endTime from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where  "+conditions2+"and t.ywcompanyid = "+ywcompanyId; 		
				    }
				}
				else{
					if(!"".equals(conditions)){
						  total =YwInfoVO.countBySql(YwInfoVO.class,"select count(t.registnumber) from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1 left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where t.ywcompanyid = "+ywcompanyId+" and "+conditions,null);
					         sql="select t.registnumber,t.address,t.buildingname,t3.endTime from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where t.ywcompanyid = "+ywcompanyId+" and "+conditions; 	
					}
					else{
				     total =YwInfoVO.countBySql(YwInfoVO.class,"select count(t.registnumber) from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1 left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where t.ywcompanyid = "+ywcompanyId,null);
			         sql="select t.registnumber,t.address,t.buildingname,t3.endTime from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where t.ywcompanyid = "+ywcompanyId; 
				     }
				}
			    items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
			} catch (ActiveRecordException e) {
				e.printStackTrace();
			}
			}
		 
		 if(role ==22 || role ==23){
				try {
					if(!"".equals(conditions2)){
						if(!"".equals(conditions)){
							total =YwInfoVO.countBySql(YwInfoVO.class,"select count(t.registnumber) from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where  "+conditions2+"and t.ywcompanyid = "+ywcompanyId+" and "+conditions,null);
						    sql="select t.registnumber,t.address,t.buildingname,t3.endTime from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where "+conditions2+"and t.ywcompanyid = "+ywcompanyId+" and "+conditions; 		
						   	
						}
						else{
						total =YwInfoVO.countBySql(YwInfoVO.class,"select count(t.registnumber) from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where  "+conditions2+"and t.ywcompanyid = "+ywcompanyId,null);
					    sql="select t.registnumber,t.address,t.buildingname,t3.endTime from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where "+conditions2+"and t.ywcompanyid = "+ywcompanyId; 		
					    }
					}
					else{
						if(!"".equals(conditions)){
							 total =YwInfoVO.countBySql(YwInfoVO.class,"select count(t.registnumber) from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where t.ywcompanyid = "+ywcompanyId+" and "+conditions,null);
						       sql="select t.registnumber,t.address,t.buildingname,t3.endTime from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where t.ywcompanyid = "+ywcompanyId+" and "+conditions; 
						}
						else{
					   total =YwInfoVO.countBySql(YwInfoVO.class,"select count(t.registnumber) from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where t.ywcompanyid = "+ywcompanyId,null);
				       sql="select t.registnumber,t.address,t.buildingname,t3.endTime from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where t.ywcompanyid = "+ywcompanyId; 
					   }
					}
				    items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
				} catch (ActiveRecordException e) {
					e.printStackTrace();
				}
				}
		 result.put("total", total);
		 result.put("rows", items);
		 return new JsonView(result);	
    }
    
    public View uywncqetotallist(int ywcompanyId,int userId,int page, int rows){
    	UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 long total=0;
		 int zjcompanyId2 =0;  //非查询用的，是登陆过程中系统后台根据role不同展示不同列表用的
		 
		 String sql ="";
		 String conditionsSql="";
		 Object[] param=null;
		 
		 List<YwInfoVO> items = new ArrayList<YwInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 UserInfoVO userInfoVO = null;
		 if(role==10 || role==11){    
				try {
					userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
		           if(userInfoVO != null){
		        	   zjcompanyId2 =userInfoVO.getCompanyId();
		        	   total =YwInfoVO.countBySql(YwInfoVO.class,"select count(t.registnumber) from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  ym.registNumber as registNumber,(select max(endTime)  from ywmanagerinfo where registNumber = ym.registNumber) as  endTime from  YwManagerInfo ym where ym.userid = "+userId+" group by ym.registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  on t.registNumber = t3.registNumber where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId+" and  t.zjcompanyid ="+zjcompanyId2,null);
					    sql="select t.registnumber,t.address,t.buildingname,t3.endTime from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  ym.registNumber as registNumber,(select max(endTime)  from ywmanagerinfo where registNumber = ym.registNumber) as  endTime from  YwManagerInfo ym where userid = "+userId+" group by ym.registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  on t.registNumber = t3.registNumber where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId + " and t.zjcompanyid ="+zjcompanyId2; 
					    items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
					
		             }
				} catch (ActiveRecordException e) {
					e.printStackTrace();
				}
				
		        }	 
		
		 if(role ==1 || role ==2){
			try {
				total =YwInfoVO.countBySql(YwInfoVO.class,"select count(t.registnumber) from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  ym.registNumber as registNumber,(select max(endTime)  from ywmanagerinfo where registNumber = ym.registNumber) as  endTime from  YwManagerInfo ym where userid = "+userId+" group by ym.registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  on t.registNumber = t3.registNumber where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId,null);
			    sql="select t.registnumber,t.address,t.buildingname,t3.endTime from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  ym.registNumber as registNumber,(select max(endTime)  from ywmanagerinfo where registNumber = ym.registNumber) as  endTime from  YwManagerInfo ym where userid = "+userId+" group by ym.registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  on t.registNumber = t3.registNumber where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId; 
			    items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
			} catch (ActiveRecordException e) {
				e.printStackTrace();
			}
			}
		 
		 if(role ==22 || role ==23){
				try {
					total =YwInfoVO.countBySql(YwInfoVO.class,"select count(t.registnumber) from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  ym.registNumber as registNumber,(select max(endTime)  from ywmanagerinfo where registNumber = ym.registNumber) as  endTime from  YwManagerInfo ym where userid = "+userId+" group by ym.registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  on t.registNumber = t3.registNumber where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId,null);
				    sql="select t.registnumber,t.address,t.buildingname,t3.endTime from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  ym.registNumber as registNumber,(select max(endTime)  from ywmanagerinfo where registNumber = ym.registNumber) as  endTime from  YwManagerInfo ym where userid = "+userId+" group by ym.registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  on t.registNumber = t3.registNumber where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId; 
				    items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
				} catch (ActiveRecordException e) {
					e.printStackTrace();
				}
				}
		 result.put("total", total);
		 result.put("rows", items);
		 return new JsonView(result);	
    }
    
   // public View uncqetotallist(int ywcompanyId,int page, int rows){
    public View uncqetotallist( YwInfoVO info,int page, int rows){
    	UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 long total=0;
		 int zjcompanyId2 =0;  //非查询用的，是登陆过程中系统后台根据role不同展示不同列表用的
		 String qstartTime ="";
		 String qendTime="";
		 int ywcompanyId =0;
		 String area ="";
		 
		 String sql ="";
		 String conditionsSql="";
		 Object[] param=null;
		 
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 ywcompanyId =info.getYwcompanyId();
		 area = info.getArea();
		 
		 String conditions2="";
		 String conditions3="";
		 
		 if(!"".equals(qstartTime)){
			 if(!"".equals(conditions2)){
				 conditions2 =conditions2+" and convert(datetime,endTime,120) >= '"+qstartTime+"'";	
				} 
				else{
					conditions2 =" convert(datetime,endTime,120) >= '"+qstartTime+"'";	
				}
			
			
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions2)){
				 conditions2 =conditions2+" and convert(datetime,endTime,120) <= '"+qendTime+"'";	
				} 
				else{
					conditions2 =" convert(datetime,endTime,120) <= '"+qendTime+"'";	
				}
			
			
		 }
		 
		 if(!"".equals(area)){
			 if(!"".equals(conditions3)){
				 conditions3 =conditions3+" and te.area = '"+area+"'";	
				} 
				else{
					conditions3 =" te.area = '"+area+"'";	
				} 
			 
		 }
		 
		 List<YwInfoVO> items = new ArrayList<YwInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 UserInfoVO userInfoVO = null;
		 if(role==10 || role==11){    
				try {
					userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
		           if(userInfoVO != null){
		        	   zjcompanyId2 =userInfoVO.getCompanyId();

		        	   if(!"".equals(conditions2)){
		        		   if(!"".equals(conditions3)){
		        		   total =YwInfoVO.countBySql(YwInfoVO.class,"select count(te.registNumber)  from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId + " and t.zjcompanyid ="+zjcompanyId2+" ) te  left join (select registNumber from YwManagerInfo where "+conditions2+" group by registNumber ) y on te.registNumber  =y.registNumber  where  "+conditions3+"and y.registNumber is null ",null);
						   sql="select te.registNumber,te.address,te.buildingname from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId + " and t.zjcompanyid ="+zjcompanyId2+" ) te  left join (select registNumber from YwManagerInfo where "+conditions2+" group by registNumber ) y on te.registNumber  =y.registNumber  where "+conditions3+"and y.registNumber is null " ;
		        		   }
		        		   else{
		        			   total =YwInfoVO.countBySql(YwInfoVO.class,"select count(te.registNumber)  from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId + " and t.zjcompanyid ="+zjcompanyId2+" ) te  left join (select registNumber from YwManagerInfo where "+conditions2+" group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null ",null);
							   sql="select te.registNumber,te.address,te.buildingname from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId + " and t.zjcompanyid ="+zjcompanyId2+" ) te  left join (select registNumber from YwManagerInfo where "+conditions2+" group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null ";
			        		     
		        		   }
		        	   }
		        	   else{
		        		   if(!"".equals(conditions3)){
		        	       total =YwInfoVO.countBySql(YwInfoVO.class,"select count(te.registNumber)  from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId + " and t.zjcompanyid ="+zjcompanyId2+" ) te  left join (select registNumber from YwManagerInfo group by registNumber ) y on te.registNumber  =y.registNumber  where "+conditions3+"and y.registNumber is null ",null);
					       sql="select te.registNumber,te.address,te.buildingname from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId + " and t.zjcompanyid ="+zjcompanyId2+" ) te  left join (select registNumber from YwManagerInfo group by registNumber ) y on te.registNumber  =y.registNumber  where "+conditions3+"and y.registNumber is null ";
		        		   }
		        		   else{
		        			   total =YwInfoVO.countBySql(YwInfoVO.class,"select count(te.registNumber)  from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId + " and t.zjcompanyid ="+zjcompanyId2+" ) te  left join (select registNumber from YwManagerInfo group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null",null);
						       sql="select te.registNumber,te.address,te.buildingname from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId + " and t.zjcompanyid ="+zjcompanyId2+" ) te  left join (select registNumber from YwManagerInfo group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null ";
			        		      
		        		   }
		        	   }
					   items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "te.registNumber desc", rows, (page-1)*rows);
					
		             }
				} catch (ActiveRecordException e) {
					e.printStackTrace();
				}
				
		        }	 
		
		 if(role ==1 || role ==2){
			try {
				if(!"".equals(conditions2)){
					if(!"".equals(conditions3)){
					   total =YwInfoVO.countBySql(YwInfoVO.class,"select count(te.registNumber)  from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId +" ) te  left join (select registNumber from YwManagerInfo where "+conditions2+" group by registNumber ) y on te.registNumber  =y.registNumber  where "+conditions3+"and y.registNumber is null ",null);
					   sql="select te.registNumber,te.address,te.buildingname from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId +" ) te  left join (select registNumber from YwManagerInfo where "+conditions2+" group by registNumber ) y on te.registNumber  =y.registNumber  where "+conditions3+"and y.registNumber is null ";
					}
					else{
						 total =YwInfoVO.countBySql(YwInfoVO.class,"select count(te.registNumber)  from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId +" ) te  left join (select registNumber from YwManagerInfo where "+conditions2+" group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null",null);
						 sql="select te.registNumber,te.address,te.buildingname from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId +" ) te  left join (select registNumber from YwManagerInfo where "+conditions2+" group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null ";
						
					}
				}
				else{
					if(!"".equals(conditions3)){
				    total =YwInfoVO.countBySql(YwInfoVO.class,"select count(te.registNumber)  from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId +" ) te  left join (select registNumber from YwManagerInfo group by registNumber ) y on te.registNumber  =y.registNumber  where "+conditions3+"and y.registNumber is null ",null);
				    sql="select te.registNumber,te.address,te.buildingname from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId +" ) te  left join (select registNumber from YwManagerInfo group by registNumber ) y on te.registNumber  =y.registNumber  where "+conditions3+"and y.registNumber is null ";
					}
					else{
						total =YwInfoVO.countBySql(YwInfoVO.class,"select count(te.registNumber)  from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId +" ) te  left join (select registNumber from YwManagerInfo group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null ",null);
					    sql="select te.registNumber,te.address,te.buildingname from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId +" ) te  left join (select registNumber from YwManagerInfo group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null ";
						
					}
			    }
				   items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "te.registNumber desc", rows, (page-1)*rows);
				} catch (ActiveRecordException e) {
				e.printStackTrace();
			}
			}
		 
		 if(role ==22 || role ==23){
				try {
					if(!"".equals(conditions2)){
						if(!"".equals(conditions3)){
					      total =YwInfoVO.countBySql(YwInfoVO.class,"select count(te.registNumber)  from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId +" ) te  left join (select registNumber from YwManagerInfo where "+conditions2+" group by registNumber ) y on te.registNumber  =y.registNumber  where "+conditions3+"and y.registNumber is null ",null);
					      sql="select te.registNumber,te.address,te.buildingname from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId +" ) te  left join (select registNumber from YwManagerInfo where "+conditions2+" group by registNumber ) y on te.registNumber  =y.registNumber  where "+conditions3+"and y.registNumber is null ";
						}
						else {
						      total =YwInfoVO.countBySql(YwInfoVO.class,"select count(te.registNumber)  from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId +" ) te  left join (select registNumber from YwManagerInfo where "+conditions2+" group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null ",null);
						      sql="select te.registNumber,te.address,te.buildingname from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId +" ) te  left join (select registNumber from YwManagerInfo where "+conditions2+" group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null ";
							
						}
					}
					else{
						if(!"".equals(conditions3)){
							  total =YwInfoVO.countBySql(YwInfoVO.class,"select count(te.registNumber)  from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId +" ) te  left join (select registNumber from YwManagerInfo group by registNumber ) y on te.registNumber  =y.registNumber  where "+conditions3+" and y.registNumber is null ",null);
							  //	   sql="select te.registNumber,te.address,te.buildingname from (select t.registNumber,t.address,t.buildingname from TwoCodeElevatorInfo t  where t.ywcompanyid = "+ywcompanyId +" ) te  left join (select registNumber from YwManagerInfo group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null ";
							  //	   items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "te.registNumber desc", rows, (page-1)*rows);
								     sql="select te.registNumber,te.address,te.buildingname from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId +" ) te  left join (select registNumber from YwManagerInfo group by registNumber ) y on te.registNumber  =y.registNumber  where "+conditions3+" and y.registNumber is null ";
								
						}
						else{
					      total =YwInfoVO.countBySql(YwInfoVO.class,"select count(te.registNumber)  from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId +" ) te  left join (select registNumber from YwManagerInfo group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null ",null);
				  //	   sql="select te.registNumber,te.address,te.buildingname from (select t.registNumber,t.address,t.buildingname from TwoCodeElevatorInfo t  where t.ywcompanyid = "+ywcompanyId +" ) te  left join (select registNumber from YwManagerInfo group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null ";
				  //	   items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "te.registNumber desc", rows, (page-1)*rows);
					     sql="select te.registNumber,te.address,te.buildingname from (select t.registNumber,t.address,t.buildingname,t.area from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and t.ywcompanyid = "+ywcompanyId +" ) te  left join (select registNumber from YwManagerInfo group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null ";
					}
					}
					 items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "te.registNumber desc", rows, (page-1)*rows);
						
				} catch (ActiveRecordException e) {
					e.printStackTrace();
				}
				}
		 result.put("total", total);
		 result.put("rows", items);
		 return new JsonView(result);	
    }
    
    public View nutotallist(int ywcompanyId,int page, int rows){
    	UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 long total=0;
		 int zjcompanyId2 =0;  //非查询用的，是登陆过程中系统后台根据role不同展示不同列表用的
		 
		 String sql = "";
		 String conditionsSql="";
		 Object[] param=null;
		 
		 List<YwInfoVO> items = new ArrayList<YwInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 UserInfoVO userInfoVO = null;
		 if(role==10 || role==11){    
				try {
					userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
		           if(userInfoVO != null){
		        	   zjcompanyId2 =userInfoVO.getCompanyId();
		        	// sql="select tyce.*,tc.loginName,tc.userName,isnull(tc.qualificationvalidate,'') as qualificationvalidate from (select  y.userid,max(y.endTime) as endTime  from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role = 18 and  tc.companyid =?  ) tyc  left join ywmanagerinfo y on tyc.userid = y.userid left join TwoCodeElevatorInfo  t on y.registNumber = t.registNumber where t.zjcompanyid =? group by y.userid  ) tyce left join tcuserinfo tc on tyce.userid = tc.id "; 
		        	//   sql="select t.loginName,t.userName, isnull(t.qualificationvalidate,'') as qualificationvalidate,(select count(y.registNumber) from  ( select registNumber from ywmanagerinfo  where userid =t.id group  by registNumber ) y inner join TwoCodeElevatorInfo te on y.registNumber  =te.registNumber where te.ywcompanyid =t.companyid and te.zjcompanyid =? ) as uywetotal,(select count(y.registNumber) from  ( select registNumber from ywmanagerinfo  where userid =t.id  and DATEDIFF(dd,convert(datetime,endTime,120),getdate()) > 15 group  by registNumber ) y inner join TwoCodeElevatorInfo te on y.registNumber  =te.registNumber where te.ywcompanyid =t.companyid  and te.zjcompanyid =?) as uywcqetotal from  tcuserinfo t inner join ( select t.userid from ywmanagerinfo t group by t.userid ) ty on t.id = ty.userid where t.role = 18 and t.companyid  = "+ywcompanyId;   
		        	   conditionsSql = "select count(t.userid) from (select userid  from  TCYEUView    where ywCompanyId = ?  and zjCompanyId = ?  and  role =18  group by userid ) t ";
				  //   sql ="select t.userId,tv.loginName,tv.userName, isnull(tv.qualificationvalidate,'') as qualificationvalidate,(select count(distinct registNumber) from TCYEUView where userid = t.userid )   as  uywetotal,(select count(distinct registNumber) from TCYEUView where userid = t.userid and DATEDIFF(dd,convert(datetime,endTime,120),getdate()) > 15 )  as uywcqetotal from (select userid  from  TCYEUView    where ywCompanyId = ?  and zjCompanyId = ?  and role =18	group by userid ) t left join  TCUserInfo   tv on   t.userid  = tv.userid";
		        	   sql ="select tv.userid,tv.loginName,tv.userName, isnull(tv.qualificationvalidate,'') as qualificationvalidate,(select count(distinct registNumber) from TCYEUView where userid = t.userid )   as  uywetotal, (select count(y.registNumber) from (select tcy.registNumber,(select max(endTime)  from TCYEUView where registNumber = tcy.registNumber) as  endTime from  TCYEUView tcy where tcy.userid = tv.userid  group by tcy.registNumber) y where DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 )  as uywcqetotal from (select userid  from  TCYEUView    where ywCompanyId = ?  and zjCompanyId = ?  and role =18	group by userid ) t left join  TCUserInfo   tv on   t.userid  = tv.userid";
			        	
		        	   param = new Object[]{ywcompanyId,zjcompanyId2};
					   total =YwInfoVO.countBySql(YwInfoVO.class,conditionsSql,param);
					   items = YwInfoVO.findBySql(YwInfoVO.class, sql, param, "t.userId desc", rows, (page-1)*rows);
					   
				    }
				} catch (ActiveRecordException e) {
					e.printStackTrace();
				}
				
		        }	 
		

		 if(role ==1 || role ==2){
			try {
				total =YwInfoVO.countBySql(YwInfoVO.class,"select count(t.loginName) from  tcuserinfo t inner join ( select t.userid from ywmanagerinfo t group by t.userid ) ty on t.id = ty.userid where t.role = 18 and t.companyid  = "+ywcompanyId,null);
		//	    sql="select t.loginName,t.userName, isnull(t.qualificationvalidate,'') as qualificationvalidate,(select top 1 endTime from ywmanagerinfo where userid = t.id order by endTime desc ) as endTime from  tcuserinfo t inner join ( select t.userid from ywmanagerinfo t group by t.userid ) ty on t.id = ty.userid where t.role = 18 and t.companyid  = "+ywcompanyId; 
				sql="select t.userId,t.loginName,t.userName, isnull(t.qualificationvalidate,'') as qualificationvalidate,(select count(y.registNumber) from  ( select registNumber from ywmanagerinfo  where userid =t.id group  by registNumber ) y inner join TwoCodeElevatorInfo te on y.registNumber  =te.registNumber where te.ywcompanyid =t.companyid) as uywetotal,(select count(y.registNumber) from  ( select ym.registNumber,(select max(endTime)  from ywmanagerinfo where registNumber = ym.registNumber) as  endTime from ywmanagerinfo ym  where ym.userid =t.id  group  by ym.registNumber ) y inner join TwoCodeElevatorInfo te on y.registNumber  =te.registNumber where te.dailingFlag =0 and te.ywcompanyid =t.companyid and DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15) as uywcqetotal from  tcuserinfo t inner join ( select t.userid from ywmanagerinfo t group by t.userid ) ty on t.id = ty.userid where t.role = 18 and t.companyid  = "+ywcompanyId;   
				items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
			} catch (ActiveRecordException e) {
				e.printStackTrace();
			}
			}
		 
		 if(role ==22 || role ==23){
				try {
					total =YwInfoVO.countBySql(YwInfoVO.class,"select count(t.loginName) from  tcuserinfo t inner join ( select t.userid from ywmanagerinfo t group by t.userid ) ty on t.id = ty.userid where t.role = 18 and t.companyid  = "+ywcompanyId,null);
				//  sql="select t.loginName,t.userName, isnull(t.qualificationvalidate,'') as qualificationvalidate,(select top 1 endTime from ywmanagerinfo where userid = t.id order by endTime desc ) as endTime from  tcuserinfo t inner join ( select t.userid from ywmanagerinfo t group by t.userid ) ty on t.id = ty.userid where t.role = 18 and t.companyid  = "+ywcompanyId; 
					sql="select t.userId,t.loginName,t.userName, isnull(t.qualificationvalidate,'') as qualificationvalidate,(select count(y.registNumber) from  ( select registNumber from ywmanagerinfo  where userid =t.id group  by registNumber ) y inner join TwoCodeElevatorInfo te on y.registNumber  =te.registNumber where te.ywcompanyid =t.companyid) as uywetotal,(select count(y.registNumber) from  ( select ym.registNumber,(select max(endTime)  from ywmanagerinfo where registNumber = ym.registNumber) as  endTime from ywmanagerinfo ym  where ym.userid =t.id   group  by ym.registNumber ) y inner join TwoCodeElevatorInfo te on y.registNumber  =te.registNumber where te.dailingFlag =0 and te.ywcompanyid =t.companyid  and DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15) as uywcqetotal from  tcuserinfo t inner join ( select t.userid from ywmanagerinfo t group by t.userid ) ty on t.id = ty.userid where t.role = 18 and t.companyid  = "+ywcompanyId;   	
					items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
				} catch (ActiveRecordException e) {
					e.printStackTrace();
				}
				}
		 result.put("total", total);
		 result.put("rows", items);
		 return new JsonView(result);	
    }
	 
    public View ywcqlist(int page, int rows) throws Exception{
		
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 long total=0;
		 List<YWOutDateVO> items = new ArrayList<YWOutDateVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 //加入系统设置，如果系统配置了运维超期关闭，则查不出运维超期，如果配置了运维超期开启，才能查出
		 SysSetingsVO sysSetingsVO = null;
		 String ywsql ="select t.setingsSwitch,isnull(t.startTime,'') as startTime,isnull(t.endTime,'') as endTime   from TwoCodeSysSetings t where t.itemName = ?";
		 sysSetingsVO = SysSetingsVO.findFirstBySql(SysSetingsVO.class,ywsql, new Object[]{"0"});
		 if(sysSetingsVO == null){
			 total =0;
		//	 items =null;
			 
		 }
		 else{
			 int setingsSwitch =sysSetingsVO.getSetingsSwitch();
			 String startTime =sysSetingsVO.getStartTime();
			 String endTime  =sysSetingsVO.getEndTime();
			 String conditions="";
			 String conditions2="";   //区县质监
			 if(!"".equals(startTime)){
				 if(!"".equals(conditions)){
					 conditions =conditions+" and left(CONVERT(varchar(100), t.properWhTime, 120),10)  >= '"+startTime+"'" ; 
				 } 
				 else{
					 conditions =" left(CONVERT(varchar(100), t.properWhTime, 120),10)  >= '"+startTime+"'" ;	 
				 }
				 
				 if(!"".equals(conditions2)){
					 conditions2 =conditions2+" and left(CONVERT(varchar(100), y.properWhTime, 120),10)  >= '"+startTime+"'" ; 
				 } 
				 else{
					 conditions2 =" left(CONVERT(varchar(100), y.properWhTime, 120),10)  >= '"+startTime+"'" ;	 
				 }
			 }
			 
			 if(!"".equals(endTime)){
				 if(!"".equals(conditions)){
					 conditions =conditions+" and left(CONVERT(varchar(100), t.properWhTime, 120),10)  <= '"+endTime+"'" ;	 
				 } 
				 else{
					 conditions =" left(CONVERT(varchar(100), t.properWhTime, 120),10)  <= '"+endTime+"'" ;	 
				 }
				 
				 if(!"".equals(conditions2)){
					 conditions2 =conditions2+" and left(CONVERT(varchar(100), y.properWhTime, 120),10)  <= '"+endTime+"'" ;	 
				 } 
				 else{
					 conditions2 =" left(CONVERT(varchar(100),y.properWhTime, 120),10)  <= '"+endTime+"'" ;	 
				 }
			 }
			 
			 if(setingsSwitch==0){  //运维超期关闭 
			    total =0;
		//	    items = null; 
			 }
		    else{ 
		 
	         if(role==2 || role==1){ //系统管理员  
	        	if(!"".equals(conditions)){
	    	//    total  =YWOutDateVO.countBySql(YWOutDateVO.class, "select count(*) from TwoCodeYWOutDate t where (t.tcOutDateFlag =? or t.tcOutDateFlag =?) and left(CONVERT(varchar(100), t.properWhTime, 120),10) >='"+startTime+"' and left(CONVERT(varchar(100), t.properWhTime, 120),10) <='"+endTime+"'", new Object[]{1,2});
	        	total  =YWOutDateVO.countBySql(YWOutDateVO.class, "select count(*) from TwoCodeYWOutDate t where (t.tcOutDateFlag =? or t.tcOutDateFlag =?) and "+ conditions, new Object[]{1,2});
	  	    	   
	       // 	String sql ="select t.id,t.registNumber,t.endTime,t.tcOutDateFlag,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,t.properWhTime,t.isProcessFlag,isnull(t.dealWithTime,'') as dealWithTime,t.beyondTime,t.beyondLevel,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile from TwoCodeYWOutDate  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on tc.id=te.ywCompanyId where (t.tcOutDateFlag =1 or t.tcOutDateFlag =2) and left(CONVERT(varchar(100), t.properWhTime, 120),10) >='"+startTime+"' and left(CONVERT(varchar(100), t.properWhTime, 120),10) <='"+endTime+"'";
	        	String sql ="select t.id,t.registNumber,t.endTime,t.tcOutDateFlag,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,t.properWhTime,t.isProcessFlag,isnull(t.dealWithTime,'') as dealWithTime,t.beyondTime,t.beyondLevel,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile from TwoCodeYWOutDate  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on tc.id=te.ywCompanyId where (t.tcOutDateFlag =1 or t.tcOutDateFlag =2) and " + conditions;
	    	    
	        	items = YWOutDateVO.findBySql(YWOutDateVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
	        	}
	        	else{
	        		total  =YWOutDateVO.countBySql(YWOutDateVO.class, "select count(*) from TwoCodeYWOutDate t where (t.tcOutDateFlag =? or t.tcOutDateFlag =?)  ", new Object[]{1,2});
	        		String sql ="select t.id,t.registNumber,t.endTime,t.tcOutDateFlag,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,t.properWhTime,t.isProcessFlag,isnull(t.dealWithTime,'') as dealWithTime,t.beyondTime,t.beyondLevel,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile from TwoCodeYWOutDate  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on tc.id=te.ywCompanyId where (t.tcOutDateFlag =1 or t.tcOutDateFlag =2) ";
		    	    
		        	items = YWOutDateVO.findBySql(YWOutDateVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
		        	
	        	}
	    	  //为了演示不看到超期记录
	    //	    UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.loginName  from  TwoCodeUserInfo tu  where tu.id = ?", new Object[] { userid });
	    	    UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.isyanshi  from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	    
	    	    if(userInfoVO != null){
   	  //  		 String userName2 =userInfoVO.getLoginName();
	    	    	int isyanshi =userInfoVO.getIsyanshi();	
   	    	//	 if("cy".equals(userName2)){
	    	    	if(isyanshi == 1){
   	    			 total = 0;
   	    			 items = new ArrayList<YWOutDateVO>();
   	    		 }
   	    	 }
	         } 
	        if(role==22 || role==23){ //系统管理员  
	          if(!"".equals(conditions)){
	        	 total  =YWOutDateVO.countBySql(YWOutDateVO.class, "select count(*) from TwoCodeYWOutDate t where (t.tcOutDateFlag =? or t.tcOutDateFlag =?) and " +conditions, new Object[]{1,2});
	 	     	 String sql ="select t.id,t.registNumber,t.endTime,t.tcOutDateFlag,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,t.properWhTime,t.isProcessFlag,isnull(t.dealWithTime,'') as dealWithTime,t.beyondTime,t.beyondLevel,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile from TwoCodeYWOutDate  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on tc.id=te.ywCompanyId where (t.tcOutDateFlag =1 or t.tcOutDateFlag =2) and "+conditions;
	 	     	 items = YWOutDateVO.findBySql(YWOutDateVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
	 	           
	          }
	          else{
	        	  total  =YWOutDateVO.countBySql(YWOutDateVO.class, "select count(*) from TwoCodeYWOutDate t where (t.tcOutDateFlag =? or t.tcOutDateFlag =?) " , new Object[]{1,2});
		 	      String sql ="select t.id,t.registNumber,t.endTime,t.tcOutDateFlag,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,t.properWhTime,t.isProcessFlag,isnull(t.dealWithTime,'') as dealWithTime,t.beyondTime,t.beyondLevel,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile from TwoCodeYWOutDate  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on tc.id=te.ywCompanyId where (t.tcOutDateFlag =1 or t.tcOutDateFlag =2) ";
		 	      items = YWOutDateVO.findBySql(YWOutDateVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
	          
	          }
	     		
	         } 
	       if(role==10 || role ==11){ //质监局
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         
	         if(!"".equals(conditions2)){
	        	 total  =YWOutDateVO.countBySql(YWOutDateVO.class, "select count(*) from TwoCodeYWOutDate y left join TwoCodeElevatorInfo t on y.registNumber = t.registNumber where t.zjCompanyId=? and (y.tcOutDateFlag=? or y.tcOutDateFlag=?) and "+conditions2, new Object[]{zjcompanyId,1,2});
	 	         String sql ="select t.id,t.registNumber,t.endTime,t.tcOutDateFlag,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,t.properWhTime,t.isProcessFlag,isnull(t.dealWithTime,'') as dealWithTime,t.beyondTime,t.beyondLevel,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile from TwoCodeYWOutDate  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on tc.id=te.ywCompanyId  where te.zjCompanyId=? and (t.tcOutDateFlag =1 or t.tcOutDateFlag =2) and "+conditions2;
	 	    	 items = YWOutDateVO.findBySql(YWOutDateVO.class, sql, new Object[]{zjcompanyId}, "t.id desc", rows, (page-1)*rows);
	 	    	 
	         }
	         else{
	        	 total  =YWOutDateVO.countBySql(YWOutDateVO.class, "select count(*) from TwoCodeYWOutDate y left join TwoCodeElevatorInfo t on y.registNumber = t.registNumber where t.zjCompanyId=? and (y.tcOutDateFlag=? or y.tcOutDateFlag=?)", new Object[]{zjcompanyId,1,2});
	 	         String sql ="select t.id,t.registNumber,t.endTime,t.tcOutDateFlag,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,t.properWhTime,t.isProcessFlag,isnull(t.dealWithTime,'') as dealWithTime,t.beyondTime,t.beyondLevel,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile from TwoCodeYWOutDate  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on tc.id=te.ywCompanyId  where te.zjCompanyId=? and (t.tcOutDateFlag =1 or t.tcOutDateFlag =2) ";
	 	    	 items = YWOutDateVO.findBySql(YWOutDateVO.class, sql, new Object[]{zjcompanyId}, "t.id desc", rows, (page-1)*rows);
	 	    	}
	         }  
	       }
	       if(role==20 || role ==21){ //街道办
		    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
		    	 if(userInfoVO != null){
		         int zjcompanyId = userInfoVO.getCompanyId();
		         if(!"".equals(conditions2)){
		        	  total  =YWOutDateVO.countBySql(YWOutDateVO.class, "select count(*) from TwoCodeYWOutDate y left join TwoCodeElevatorInfo t on y.registNumber = t.registNumber where t.townshipStreets=? and (y.tcOutDateFlag=? or y.tcOutDateFlag=?) and "+conditions2, new Object[]{zjcompanyId,1,2});
				      String sql ="select t.id,t.registNumber,t.endTime,t.tcOutDateFlag,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,t.properWhTime,t.isProcessFlag,isnull(t.dealWithTime,'') as dealWithTime,t.beyondTime,t.beyondLevel,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile from TwoCodeYWOutDate  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on tc.id=te.ywCompanyId  where te.townshipStreets=? and (t.tcOutDateFlag =1 or t.tcOutDateFlag =2) and "+conditions2;
				      items = YWOutDateVO.findBySql(YWOutDateVO.class, sql, new Object[]{zjcompanyId}, "t.id desc", rows, (page-1)*rows);
				    	 
		         }
		         else{
		        	  total  =YWOutDateVO.countBySql(YWOutDateVO.class, "select count(*) from TwoCodeYWOutDate y left join TwoCodeElevatorInfo t on y.registNumber = t.registNumber where t.townshipStreets=? and (y.tcOutDateFlag=? or y.tcOutDateFlag=?)", new Object[]{zjcompanyId,1,2});
				      String sql ="select t.id,t.registNumber,t.endTime,t.tcOutDateFlag,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,t.properWhTime,t.isProcessFlag,isnull(t.dealWithTime,'') as dealWithTime,t.beyondTime,t.beyondLevel,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile from TwoCodeYWOutDate  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on tc.id=te.ywCompanyId  where te.townshipStreets=? and (t.tcOutDateFlag =1 or t.tcOutDateFlag =2) ";
				      items = YWOutDateVO.findBySql(YWOutDateVO.class, sql, new Object[]{zjcompanyId}, "t.id desc", rows, (page-1)*rows);
		    	 }
		         } 
	       }
		  }
		 }
		 
	      result.put("total", total);
		  result.put("rows", items);  
		  return new JsonView(result);
	    }
    
    public String ncqqueryexportExcel(YwInfoVO info){ 
         String result = "failure"; 
    //	Map<String, Object> result = new HashMap<String, Object>();
    	UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 String registNumber ="";
		 String ncqtype ="";
		 int zjcompanyId2 =0;  //非查询用的，是登陆过程中系统后台根据role不同展示不同列表用的
		 String qstartTime ="";
		 String qendTime="";
		 String areaName="";
		 int townshipStreets =0;
		 int ywCompanyId =0;
		 
		 registNumber = info.getRegistNumber();
		 ncqtype = info.getNcqtype();
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 areaName =info.getArea();
		 townshipStreets =info.getTownshipStreets();
		 ywCompanyId =info.getCompanyId();
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 List<NcqQueryYwInfoExportVO> items = new ArrayList<NcqQueryYwInfoExportVO>();
		
	 
		 long total=0;
		 
		 UserInfoVO userInfoVO =null;
		 if(role==10 || role==11){    
			try {
				userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
			} catch (ActiveRecordException e) {
				e.printStackTrace();
			}
	         if(userInfoVO != null){
	             zjcompanyId2 = userInfoVO.getCompanyId();
	             if("1".equals(ncqtype)){
	             conditions = " t.zjcompanyId = "+zjcompanyId2;
	             }
	             else{
	             conditions = " te.zjcompanyId = "+zjcompanyId2; 
	             }
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
		 
		 if(!"".equals(areaName)){
			 if("1".equals(ncqtype)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.area like '%"+areaName+"%'";	
				} 
				else{
					conditions =" t.area like '%"+areaName+"%'";	
				}
			 }
			 else{
				 if(!"".equals(conditions)){
					 conditions =conditions+" and te.area like '%"+areaName+"%'";	
					} 
					else{
						conditions =" te.area like '%"+areaName+"%'";	
					} 
			 }
		 }
		
		 if(ywCompanyId > 0){
			 if("1".equals(ncqtype)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.ywCompanyId = "+ywCompanyId;	
				} 
				else{
					conditions =" t.ywCompanyId = "+ywCompanyId;	
				}
			 }
			 else{
				 if(!"".equals(conditions)){
					 conditions =conditions+" and te.ywCompanyId = "+ywCompanyId;	
					} 
					else{
						conditions =" te.ywCompanyId = "+ywCompanyId;	
					}	 
			 }
		 }
		 
		 if(townshipStreets > 0){
			 if("0".equals(ncqtype)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and te.townshipStreets = "+townshipStreets;	
				} 
				else{
					conditions =" te.townshipStreets = "+townshipStreets;	
				}
			 }
			 else{
				 if(!"".equals(conditions)){
					 conditions =conditions+" and t.townshipStreets = "+townshipStreets;	
					} 
					else{
						conditions =" t.townshipStreets = "+townshipStreets;	
					}	 
			 }
			 
			 
		 }
		 
		 
		 if(!"".equals(qstartTime)){
			 if("0".equals(ncqtype)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.endTime  >= '"+qstartTime+"'" ;	 
			 } 
			 else{
				 conditions =" t.endTime  >= '"+qstartTime+"'" ;	 
			 }
			 }
		 }
		 
		 if(!"".equals(qendTime)){
			 if("0".equals(ncqtype)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.endTime  <= '"+qendTime+"'" ;	 
			 } 
			 else{
				 conditions =" t.endTime  <= '"+qendTime+"'" ;	 
			 }
		    }
		 }
		 
		 
		 if("1".equals(ncqtype)){  
		 if(!"".equals(conditions)){ 
			  sql ="select distinct t.registNumber,t.registCode,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,isnull(t.area,'') as area,isnull(tc.companyName,'') as companyName,'' as endTime,isnull((select companyName from TwoCodeCompanyInfo  where   id =t.townshipStreets),'') as jdbCompanyName,isnull((select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ),'') as userName,isnull((select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ),'') as telephonemobile   from  TwoCodeElevatorInfo  t left join TwoCodeCompanyInfo tc on tc.id=t.ywCompanyId where "+ conditions+" and (select count(1) as num from (select registNumber from YwManagerInfo group by registNumber) a where a.registNumber = t.registNumber) = 0  and t.dailingFlag = 0";
			  conditionsSql = "select count(1)  from TwoCodeElevatorInfo  t  where "+ conditions+" and (select count(1) as num from (select registNumber from YwManagerInfo group by registNumber) a where a.registNumber = t.registNumber) = 0 and t.dailingFlag = 0 ";
			 }
			 else{ 
				 sql ="select t.registNumber,t.registCode,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,isnull(t.area,'') as area,isnull(tc.companyName,'') as companyName,'' as endTime,isnull((select companyName from TwoCodeCompanyInfo  where   id =t.townshipStreets),'') as jdbCompanyName,isnull((select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ),'') as userName,isnull((select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ),'') as telephonemobile  from  TwoCodeElevatorInfo  t left join TwoCodeCompanyInfo tc on tc.id=t.ywCompanyId where  (select count(1) as num from (select registNumber from YwManagerInfo group by registNumber) a where a.registNumber = t.registNumber) = 0  and t.dailingFlag = 0 ";
				 conditionsSql = "select count(1)  from TwoCodeElevatorInfo  t  where  (select count(1) as num from (select registNumber from YwManagerInfo group by registNumber) a where a.registNumber = t.registNumber) = 0  and t.dailingFlag = 0";			 
			 }
		 }
		 
		 if("0".equals(ncqtype)){ 
			 if(!"".equals(conditions)){  
				  sql ="select  t.registNumber,te.registCode,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,isnull(tc.companyName,'') as companyName,isnull(t.endTime,'') as endTime,isnull((select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets),'') as jdbCompanyName,isnull((select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ),'') as userName,isnull((select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ),'') as telephonemobile  from YwManagerInfo t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on tc.id=te.ywCompanyId  where "+ conditions+" and t.id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) and  DATEDIFF(dd,convert(datetime,t.endTime,120),getdate()) > 15  and te.dailingFlag  = 0";
				  conditionsSql = "select count(1) from YwManagerInfo   t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber where "+ conditions+" and t.id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) and  DATEDIFF(dd,convert(datetime,t.endTime,120),getdate()) > 15  and te.dailingFlag = 0 ";
				 }
				 else{ 
					 sql ="select t.registNumber,te.registCode,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,isnull(tc.companyName,'') as companyName,isnull(t.endTime,'') as endTime,isnull((select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets),'') as jdbCompanyName,isnull((select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ),'') as userName,isnull((select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ),'') as telephonemobile  from YwManagerInfo t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on tc.id=te.ywCompanyId  where  t.id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) and  DATEDIFF(dd,convert(datetime,t.endTime,120),getdate()) > 15  and te.dailingFlag  = 0 ";
					 conditionsSql = "select count(1) from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber  where  t.id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) and  DATEDIFF(dd,convert(datetime,t.endTime,120),getdate()) > 15  and te.dailingFlag = 0 ";				 
				 }  
		 }
		 
		 try {
		//	total =NcqQueryYwInfoExportVO.countBySql(NcqQueryYwInfoExportVO.class, conditionsSql, null);
			 items = NcqQueryYwInfoExportVO.findBySql(NcqQueryYwInfoExportVO.class,sql, null, null);
			
			
			 String[] hearders = new String[] {"电梯编号","注册代码","地址", "楼盘名称","维保单位", "行政区划", "街道办", "维保人员","维保人员手机","上次运维时间","超期天数"};//表头数组 
			 NcqQueryYwInfoExportExcel<NcqQueryYwInfoExportVO> ex = new NcqQueryYwInfoExportExcel<NcqQueryYwInfoExportVO>();
			 SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			 String filename ="";
			 if("1".equals(ncqtype)){
				  filename = "从未运维列表_"+timeFormat.format(new Date())+".xlsx";  
			 }else{
				  filename = "运维超期列表_"+timeFormat.format(new Date())+".xlsx";   
			 }
			 
			 response.setContentType("application/ms-excel;charset=UTF-8");  
			 response.setHeader("Content-Disposition", "attachment;filename="  
			            .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
			 OutputStream out = response.getOutputStream();  
		     ex.exportExcel2(hearders, items, out,sql);  
		//	 ex.exportExcel(tjtime,hearders, items, out);
		     out.close(); 
		  //这一行非常关键，否则在实际中有可能出现莫名其妙的问题！！！
		     response.flushBuffer();//强行将响应缓存中的内容发送到目的地 
		     System.out.println("excel导出成功！");   
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
    
    
    public View ncqquery(YwInfoVO info,int page, int rows){
    	UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 String registNumber ="";
		 String ncqtype ="";
		 int zjcompanyId2 =0;  //非查询用的，是登陆过程中系统后台根据role不同展示不同列表用的
		 String qstartTime ="";
		 String qendTime="";
		 String areaName="";
		 int townshipStreets =0;
		 int ywCompanyId =0;
		 
		 registNumber = info.getRegistNumber();
		 ncqtype = info.getNcqtype();
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 areaName =info.getArea();
		 townshipStreets =info.getTownshipStreets();
		 ywCompanyId =info.getCompanyId();
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 List<YwInfoVO> items = new ArrayList<YwInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
	 
		 long total=0;
		 
		 UserInfoVO userInfoVO =null;
		 if(role==10 || role==11){    
			try {
				userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
			} catch (ActiveRecordException e) {
				e.printStackTrace();
			}
	         if(userInfoVO != null){
	             zjcompanyId2 = userInfoVO.getCompanyId();
	             if("1".equals(ncqtype)){
	             conditions = " t.zjcompanyId = "+zjcompanyId2;
	             }
	             else{
	             conditions = " te.zjcompanyId = "+zjcompanyId2; 
	             }
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
		 
		 if(!"".equals(areaName)){
			 if("1".equals(ncqtype)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.area like '%"+areaName+"%'";	
				} 
				else{
					conditions =" t.area like '%"+areaName+"%'";	
				}
			 }
			 else{
				 if(!"".equals(conditions)){
					 conditions =conditions+" and te.area like '%"+areaName+"%'";	
					} 
					else{
						conditions =" te.area like '%"+areaName+"%'";	
					} 
			 }
		 }
		 
		 if(ywCompanyId > 0){
			 if("1".equals(ncqtype)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.ywCompanyId = "+ywCompanyId;	
				} 
				else{
					conditions =" t.ywCompanyId = "+ywCompanyId;	
				}
			 }
			 else{
				 if(!"".equals(conditions)){
					 conditions =conditions+" and te.ywCompanyId = "+ywCompanyId;	
					} 
					else{
						conditions =" te.ywCompanyId = "+ywCompanyId;	
					}	 
			 }
		 }
		 
		 if(townshipStreets > 0){
			 if("0".equals(ncqtype)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and te.townshipStreets = "+townshipStreets;	
				} 
				else{
					conditions =" te.townshipStreets = "+townshipStreets;	
				}
			 }
			 else{
				 if(!"".equals(conditions)){
					 conditions =conditions+" and t.townshipStreets = "+townshipStreets;	
					} 
					else{
						conditions =" t.townshipStreets = "+townshipStreets;	
					}	 
			 }
			 
			 
		 }
		 
		 
		 if(!"".equals(qstartTime)){
			 if("0".equals(ncqtype)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.endTime  >= '"+qstartTime+"'" ;	 
			 } 
			 else{
				 conditions =" t.endTime  >= '"+qstartTime+"'" ;	 
			 }
			 }
		 }
		 
		 if(!"".equals(qendTime)){
			 if("0".equals(ncqtype)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.endTime  <= '"+qendTime+"'" ;	 
			 } 
			 else{
				 conditions =" t.endTime  <= '"+qendTime+"'" ;	 
			 }
		    }
		 }
		 
		 
		 if("1".equals(ncqtype)){
		 if(!"".equals(conditions)){
			  sql ="select distinct t.id,t.registNumber,t.registCode,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,isnull(t.area,'') as area,isnull(tc.companyName,'') as companyName,'' as endTime,(select companyName from TwoCodeCompanyInfo  where   id =t.townshipStreets) as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile   from  TwoCodeElevatorInfo  t left join TwoCodeCompanyInfo tc on tc.id=t.ywCompanyId where "+ conditions+" and (select count(1) as num from (select registNumber from YwManagerInfo group by registNumber) a where a.registNumber = t.registNumber) = 0  and t.dailingFlag = 0";
			  conditionsSql = "select count(1)  from TwoCodeElevatorInfo  t  where "+ conditions+" and (select count(1) as num from (select registNumber from YwManagerInfo group by registNumber) a where a.registNumber = t.registNumber) = 0 and t.dailingFlag = 0 ";
			 }
			 else{
				 sql ="select t.registNumber,t.registCode,isnull(t.address,'') as address,isnull(t.buildingName,'') as buildingName,isnull(t.area,'') as area,isnull(tc.companyName,'') as companyName,'' as endTime,(select companyName from TwoCodeCompanyInfo  where   id =t.townshipStreets) as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile  from  TwoCodeElevatorInfo  t left join TwoCodeCompanyInfo tc on tc.id=t.ywCompanyId where  (select count(1) as num from (select registNumber from YwManagerInfo group by registNumber) a where a.registNumber = t.registNumber) = 0  and t.dailingFlag = 0 ";
				 conditionsSql = "select count(1)  from TwoCodeElevatorInfo  t  where  (select count(1) as num from (select registNumber from YwManagerInfo group by registNumber) a where a.registNumber = t.registNumber) = 0  and t.dailingFlag = 0";			 
			 }
		 }
		 
		 if("0".equals(ncqtype)){
			 if(!"".equals(conditions)){
				  sql ="select distinct t.id,t.registNumber,te.registCode,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,isnull(tc.companyName,'') as companyName,t.endTime,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile  from YwManagerInfo t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on tc.id=te.ywCompanyId  where "+ conditions+" and t.id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) and  DATEDIFF(dd,convert(datetime,t.endTime,120),getdate()) > 15  and te.dailingFlag  = 0";
				  conditionsSql = "select count(1) from YwManagerInfo   t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber where "+ conditions+" and t.id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) and  DATEDIFF(dd,convert(datetime,t.endTime,120),getdate()) > 15  and te.dailingFlag = 0 ";
				 }
				 else{
					 sql ="select t.registNumber,te.registCode,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,isnull(tc.companyName,'') as companyName,t.endTime,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile  from YwManagerInfo t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on tc.id=te.ywCompanyId  where  t.id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) and  DATEDIFF(dd,convert(datetime,t.endTime,120),getdate()) > 15  and te.dailingFlag  = 0 ";
					 conditionsSql = "select count(1) from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber  where  t.id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) and  DATEDIFF(dd,convert(datetime,t.endTime,120),getdate()) > 15  and te.dailingFlag = 0 ";				 
				 }  
		 }
		 
		 try {
			total =YwInfoVO.countBySql(YwInfoVO.class, conditionsSql, null);
			items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
			
			if(role ==1 || role==2){   //为了演示不看到超期记录
          /* UserInfoVO userInfoVO2 = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.loginName  from  TwoCodeUserInfo tu  where tu.id = ?", new Object[] { userid });
   	    	 if(userInfoVO2 != null){
   	    		 String userName2 =userInfoVO2.getLoginName();
   	    		 if("cy".equals(userName2)){
   	    			 total = 0;
   	    			 items = new ArrayList<YwInfoVO>();
   	    		 }
   	    	 } */
				 UserInfoVO userInfoVO2 = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.isyanshi  from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
    	    	 if(userInfoVO2 != null){
    	    		int isyanshi =userInfoVO2.getIsyanshi();
    	    		if(isyanshi == 1){
    	    			total = 0 ;
    	    			items = new ArrayList<YwInfoVO>();
    	    		}
    	    	 }
				
			}


			
		} catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);
		 
    }
    
    public View nutotalquery(YwInfoVO info,int page, int rows){
    	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 int zjcompanyId2 =0;  //非查询用的，是登陆过程中系统后台根据role不同展示不同列表用的
		 
		 String loginName ="";
		 int ywCompanyId =0;
		 
		 loginName = info.getLoginName();
		 ywCompanyId= info.getYwcompanyId();
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 long total=0;
		 UserInfoVO userInfoVO =null;
		 
		 List<YwInfoVO> items = new ArrayList<YwInfoVO>();  
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 if(ywCompanyId > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and tc.companyid = "+ywCompanyId;	
				} 
				else{
					conditions =" tc.companyid = "+ywCompanyId;	
				}
		 }
		 
		 if(!"".equals(loginName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and tc.loginName like '%"+loginName+"%'";	
				} 
				else{
					conditions =" tc.loginName like '%"+loginName+"%'";	
				}
			 
		 }
		 
		 if(role==10 || role==11){
			 try {
					userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
			  if(userInfoVO != null){
	             zjcompanyId2 = userInfoVO.getCompanyId();
	       //      conditions = " t.zjcompanyId = "+zjcompanyId2;
	             if(!"".equals(conditions)){
	             //	   sql="select tyce.*,tc.loginName,tc.userName,isnull(tc.qualificationvalidate,'') as qualificationvalidate from (select  y.userid,max(y.endTime) as endTime  from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where  " +conditions+" and tc.role = 18   ) tyc  left join ywmanagerinfo y on tyc.userid = y.userid left join TwoCodeElevatorInfo  t on y.registNumber = t.registNumber where t.zjcompanyid ="+zjcompanyId2+" group by y.userid  ) tyce left join tcuserinfo tc on tyce.userid = tc.id " ; 
	            // 	   conditionsSql ="select count(tc.loginName) from (select  y.userid,max(y.endTime) as endTime  from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where  " +conditions+"  and tc.role = 18  ) tyc  left join ywmanagerinfo y on tyc.userid = y.userid left join TwoCodeElevatorInfo  t on y.registNumber = t.registNumber where t.zjcompanyid ="+zjcompanyId2+" group by y.userid  ) tyce left join tcuserinfo tc on tyce.userid = tc.id ";
	            	 sql="select tc.userId,tc.loginName,tc.userName, isnull(tc.qualificationvalidate,'') as qualificationvalidate,(select count(y.registNumber) from  ( select registNumber from ywmanagerinfo  where userid =tc.id group  by registNumber ) y inner join TwoCodeElevatorInfo te on y.registNumber  =te.registNumber where te.ywcompanyid =tc.companyid and te.zjcompanyid = " + zjcompanyId2+" ) as uywetotal,(select count(y.registNumber) from  ( select ym.registNumber,(select max(endTime)  from ywmanagerinfo where registNumber = ym.registNumber) as  endTime from ywmanagerinfo ym   where ym.userid =tc.id  group  by ym.registNumber  ) y inner join TwoCodeElevatorInfo te on y.registNumber  =te.registNumber where te.ywcompanyid =tc.companyid  and DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and te.zjcompanyid = " + zjcompanyId2+" ) as uywcqetotal from  tcuserinfo tc inner join ( select tc.userId from TCYEUView tc where "+conditions+" and tc.companyid = 6 and tc.role = 18  and tc.zjcompanyId = "+zjcompanyId2+" group by tc.userId) ty  on tc.id = ty.userid "; 
	             	 conditionsSql ="select count(tc.loginName) from  tcuserinfo tc inner join (select tc.userId from TCYEUView tc where"+conditions+"and tc.companyid = 6 and tc.role = 18  and tc.zjcompanyId = "+zjcompanyId2+" group by userId) ty  on tc.id = ty.userid ";
	                         
	             } 
	             	 else{
	             		 sql="select tc.userId,tc.loginName,tc.userName, isnull(tc.qualificationvalidate,'') as qualificationvalidate,(select count(y.registNumber) from  ( select registNumber from ywmanagerinfo  where userid =tc.id group  by registNumber ) y inner join TwoCodeElevatorInfo te on y.registNumber  =te.registNumber where te.ywcompanyid =tc.companyid and te.zjcompanyid = " + zjcompanyId2+" ) as uywetotal,(select count(y.registNumber) from  ( select ym.registNumber,(select max(endTime)  from ywmanagerinfo where registNumber = ym.registNumber) as  endTime from ywmanagerinfo ym   where ym.userid =tc.id  group  by ym.registNumber  ) y inner join TwoCodeElevatorInfo te on y.registNumber  =te.registNumber where te.ywcompanyid =tc.companyid and DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15 and te.zjcompanyid = " + zjcompanyId2+" ) as uywcqetotal from  tcuserinfo tc inner join ( select tc2.userId from TCYEUView tc2 where tc2.companyid = 6 and tc2.role = 18  and tc2.zjcompanyId = "+zjcompanyId2+" group by tc2.userId) ty  on tc.id = ty.userid  "; 
	             	//	 conditionsSql ="select count(tc.loginName) from  tcuserinfo tc inner join ( select t.userid from ywmanagerinfo t group by t.userid ) ty on tc.id = ty.userid where  tc.role = 18 ";
	             		conditionsSql ="select count(tc.loginName) from  tcuserinfo tc inner join (select tc.userId from TCYEUView tc where tc.companyid = 6 and tc.role = 18  and tc.zjcompanyId = "+zjcompanyId2+" group by userId) ty  on tc.id = ty.userid ";
	             	     }
	             total =YwInfoVO.countBySql(YwInfoVO.class, conditionsSql, null);
	             items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "tc.id desc", rows, (page-1)*rows);
	             
	             }	 
				} catch (ActiveRecordException e) {
					e.printStackTrace();
				}  
	          }
		 
		
		 
		 if(role==1 || role==2){
			 try {
			 if(!"".equals(conditions)){
           	   sql="select tc.userId,tc.loginName,tc.userName, isnull(tc.qualificationvalidate,'') as qualificationvalidate,(select count(y.registNumber) from  ( select registNumber from ywmanagerinfo  where userid =tc.id group  by registNumber ) y inner join TwoCodeElevatorInfo te on y.registNumber  =te.registNumber where te.ywcompanyid =tc.companyid) as uywetotal,(select count(y.registNumber) from  ( select ym.registNumber,(select max(endTime)  from ywmanagerinfo where registNumber = ym.registNumber) as  endTime from ywmanagerinfo ym   where ym.userid =tc.id  group  by ym.registNumber  ) y inner join TwoCodeElevatorInfo te on y.registNumber  =te.registNumber where te.ywcompanyid =tc.companyid and DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15) as uywcqetotal from  tcuserinfo tc inner join ( select t.userid from ywmanagerinfo t group by t.userid ) ty on tc.id = ty.userid where " +conditions+" and tc.role = 18 " ; 
           	   conditionsSql ="select count(tc.loginName) from  tcuserinfo tc inner join ( select t.userid from ywmanagerinfo t group by t.userid ) ty on tc.id = ty.userid where " +conditions+" and tc.role = 18 ";
                   } 
           	 else{
           		 sql="select tc,userId,tc.loginName,tc.userName, isnull(tc.qualificationvalidate,'') as qualificationvalidate,(select count(y.registNumber) from  ( select registNumber from ywmanagerinfo  where userid =tc.id group  by registNumber ) y inner join TwoCodeElevatorInfo te on y.registNumber  =te.registNumber where te.ywcompanyid =tc.companyid) as uywetotal,(select count(y.registNumber) from  ( select ym.registNumber,(select max(endTime)  from ywmanagerinfo where registNumber = ym.registNumber) as  endTime from ywmanagerinfo ym   where ym.userid =tc.id  group  by ym.registNumber  ) y inner join TwoCodeElevatorInfo te on y.registNumber  =te.registNumber where te.ywcompanyid =tc.companyid and DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15) as uywcqetotal from  tcuserinfo tc inner join ( select t.userid from ywmanagerinfo t group by t.userid ) ty on tc.id = ty.userid where tc.role = 18 "; 
             	 conditionsSql ="select count(tc.loginName) from  tcuserinfo tc inner join ( select t.userid from ywmanagerinfo t group by t.userid ) ty on tc.id = ty.userid where tc.role = 18 ";
                     }
			 total =YwInfoVO.countBySql(YwInfoVO.class, conditionsSql, null);
			   items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "tc.id desc", rows, (page-1)*rows);
		    }
		  	 catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}
        }
		 
		 if(role==22 || role==23){
			 try {
			 if(!"".equals(conditions)){
           	 //  sql="select tc.loginName,tc.userName, isnull(tc.qualificationvalidate,'') as qualificationvalidate,(select top 1 endTime from ywmanagerinfo where userid = tc.id order by endTime desc ) as endTime from  tcuserinfo tc inner join ( select t.userid from ywmanagerinfo t group by t.userid ) ty on tc.id = ty.userid where " +conditions+" and tc.role = 18 " ; 
           	//   conditionsSql ="select count(tc.loginName) from  tcuserinfo tc inner join ( select t.userid from ywmanagerinfo t group by t.userid ) ty on tc.id = ty.userid where " +conditions+" and tc.role = 18 ";
				 sql="select tc.userId,tc.loginName,tc.userName, isnull(tc.qualificationvalidate,'') as qualificationvalidate,(select count(y.registNumber) from  ( select registNumber from ywmanagerinfo  where userid =tc.id group  by registNumber ) y inner join TwoCodeElevatorInfo te on y.registNumber  =te.registNumber where te.ywcompanyid =tc.companyid) as uywetotal,(select count(y.registNumber) from  ( select ym.registNumber,(select max(endTime)  from ywmanagerinfo where registNumber = ym.registNumber) as  endTime from ywmanagerinfo ym  where  ym.userid =tc.id  group  by ym.registNumber  ) y inner join TwoCodeElevatorInfo te on y.registNumber  =te.registNumber where te.ywcompanyid =tc.companyid and DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15) as uywcqetotal from  tcuserinfo tc inner join ( select t.userid from ywmanagerinfo t group by t.userid ) ty on tc.id = ty.userid where " +conditions+" and tc.role = 18 " ; 
	           	 conditionsSql ="select count(tc.loginName) from  tcuserinfo tc inner join ( select t.userid from ywmanagerinfo t group by t.userid ) ty on tc.id = ty.userid where " +conditions+" and tc.role = 18 ";
	                  
			 } 
           	 else{
           //	 sql="select tc.loginName,tc.userName, isnull(tc.qualificationvalidate,'') as qualificationvalidate,(select top 1 endTime from ywmanagerinfo where userid = tc.id order by endTime desc ) as endTime from  tcuserinfo tc inner join ( select t.userid from ywmanagerinfo t group by t.userid ) ty on tc.id = ty.userid where tc.role = 18 "; 
           //  	 conditionsSql ="select count(tc.loginName) from  tcuserinfo tc inner join ( select t.userid from ywmanagerinfo t group by t.userid ) ty on tc.id = ty.userid where tc.role = 18 ";
           		 sql="select tc.userId,tc.loginName,tc.userName, isnull(tc.qualificationvalidate,'') as qualificationvalidate,(select count(y.registNumber) from  ( select registNumber from ywmanagerinfo  where userid =tc.id group  by registNumber ) y inner join TwoCodeElevatorInfo te on y.registNumber  =te.registNumber where te.ywcompanyid =tc.companyid) as uywetotal,(select count(y.registNumber) from  ( select ym.registNumber,(select max(endTime)  from ywmanagerinfo where registNumber = ym.registNumber) as  endTime from ywmanagerinfo ym  where  ym.userid =tc.id  group  by ym.registNumber  ) y inner join TwoCodeElevatorInfo te on y.registNumber  =te.registNumber where te.ywcompanyid =tc.companyid and DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15) as uywcqetotal from  tcuserinfo tc inner join ( select t.userid from ywmanagerinfo t group by t.userid ) ty on tc.id = ty.userid where tc.role = 18 "; 
             	 conditionsSql ="select count(tc.loginName) from  tcuserinfo tc inner join ( select t.userid from ywmanagerinfo t group by t.userid ) ty on tc.id = ty.userid where tc.role = 18 ";
                      
           	 }
			 total =YwInfoVO.countBySql(YwInfoVO.class, conditionsSql, null);
			   items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "tc.id desc", rows, (page-1)*rows);
		    }
		  	 catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}
        }
			
			 result.put("total", total);
			 result.put("rows", items);  
			 return new JsonView(result);	
		 
		 
    }
    
    public View ncqetotalquery(YwInfoVO info,int page, int rows){
    	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 int zjcompanyId2 =0;  //非查询用的，是登陆过程中系统后台根据role不同展示不同列表用的
		 String registNumber ="";
		 String address ="";
		 String buildingName ="";
		 int ywCompanyId =0;
		 String qstartTime ="";
		 String qendTime="";
		 
		 registNumber =info.getRegistNumber();
		 address =info.getAddress();
		 buildingName =info.getBuildingName();
		 ywCompanyId= info.getYwcompanyId();
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 String conditions2="";
		 
		 long total=0;
		 UserInfoVO userInfoVO =null;
		 
		 List<YwInfoVO> items = new ArrayList<YwInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		
		 
		 if(role==10 || role==11){
			 try {
					userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
				} catch (ActiveRecordException e) {
					e.printStackTrace();
				}    if(userInfoVO != null){
	             zjcompanyId2 = userInfoVO.getCompanyId();
	             conditions = " t.zjcompanyId = "+zjcompanyId2;
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
         
         if(!"".equals(qstartTime)){
			 if(!"".equals(conditions2)){
				 conditions2 =conditions2+" and convert(datetime,endTime,120) >= '"+qstartTime+"'";	
				} 
				else{
					conditions2 =" convert(datetime,endTime,120) >= '"+qstartTime+"'";	
				}
			
			
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions2)){
				 conditions2 =conditions2+" and convert(datetime,endTime,120) <= '"+qendTime+"'";	
				} 
				else{
					conditions2 =" convert(datetime,endTime,120) <= '"+qendTime+"'";	
				}
			
			
		 }
         
           if(!"".equals(conditions)){
        	   if(!"".equals(conditions2)){
        		   sql="select t.registnumber,t.address,t.buildingname,t3.endTime from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1 left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where  "+conditions2+"and "+conditions; 
         	      conditionsSql ="select count(t.registnumber) from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where  "+conditions2+"and "+conditions;
        	    }
        	   else{
        	      sql="select t.registnumber,t.address,t.buildingname,t3.endTime from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where  "+conditions; 
        	      conditionsSql ="select count(t.registnumber) from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where  "+conditions;
                }
               }
        	else{
        		if(!"".equals(conditions2)){
        			 sql="select t.registnumber,t.address,t.buildingname,t3.endTime from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where  "+conditions2; 
               	     conditionsSql ="select count(t.registnumber) from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber where  "+conditions2;    
        		}
        		else{
        	     sql="select t.registnumber,t.address,t.buildingname,t3.endTime from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber "; 
           	     conditionsSql ="select count(t.registnumber) from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  left join TwoCodeElevatorInfo te on t1.registNumber = te.registNumber where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0 ) t3  on t.registNumber = t3.registNumber ";
                 }
        	}
           try {
				total =YwInfoVO.countBySql(YwInfoVO.class, conditionsSql, null);
				items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
			} catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}
			 result.put("total", total);
			 result.put("rows", items);  
			 return new JsonView(result);	
		
    }
    
    
    public View uywncqetotalquery(YwInfoVO info,int page, int rows){
   	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 int zjcompanyId2 =0;  //非查询用的，是登陆过程中系统后台根据role不同展示不同列表用的
		 String registNumber ="";
		 String address ="";
		 String buildingName ="";
		 int ywCompanyId =0;
		 int userId =0;       //页面传过来的userId,用来查询某个特定运维员工运维超期的电梯数目
		 
		 registNumber =info.getRegistNumber();
		 address =info.getAddress();
		 buildingName =info.getBuildingName();
		 ywCompanyId= info.getYwcompanyId();
		 userId = info.getUserId();
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 long total=0;
		 UserInfoVO userInfoVO =null;
		 
		 List<YwInfoVO> items = new ArrayList<YwInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 if(ywCompanyId > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.ywCompanyId = "+ywCompanyId;	
				} 
				else{
					conditions =" t.ywCompanyId = "+ywCompanyId;	
				}
		 }
		 
		 if(role==10 || role==11){
			 try {
					userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
				} catch (ActiveRecordException e) {
					e.printStackTrace();
				}    if(userInfoVO != null){
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
        
          if(!"".equals(conditions)){
       	   sql="select t.registnumber,t.address,t.buildingname,t3.endTime from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select ym.registNumber,(select max(endTime)  from ywmanagerinfo where registNumber = ym.registNumber) as  endTime from ywmanagerinfo ym where ym.userId ="+userId+" group by ym.registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  on t.registNumber = t3.registNumber where  "+conditions; 
       	   conditionsSql ="select count(t.registnumber) from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select ym.registNumber,(select max(endTime)  from ywmanagerinfo where registNumber = ym.registNumber) as  endTime from ywmanagerinfo ym where ym.userId ="+userId+" group by ym.registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  on t.registNumber = t3.registNumber where  "+conditions;
               } 
       	else{
       	   sql="select t.registnumber,t.address,t.buildingname,t3.endTime from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select ym.registNumber,(select max(endTime)  from ywmanagerinfo where registNumber = ym.registNumber) as  endTime from ywmanagerinfo ym where ym.userId ="+userId+" group by ym.registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  on t.registNumber = t3.registNumber "; 
          	   conditionsSql ="select count(t.registnumber) from TwoCodeElevatorInfo t inner join  (select t1.registNumber,t1.endTime  from (select ym.registNumber,(select max(endTime)  from ywmanagerinfo where registNumber = ym.registNumber) as  endTime from ywmanagerinfo ym where ym.userId ="+userId+" group by ym.registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  on t.registNumber = t3.registNumber ";
                }
          try {
				total =YwInfoVO.countBySql(YwInfoVO.class, conditionsSql, null);
				items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
			} catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}
			 result.put("total", total);
			 result.put("rows", items);  
			 return new JsonView(result);	
		
   }
    public View uncqetotalquery(YwInfoVO info,int page, int rows){
   	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 int zjcompanyId2 =0;  //非查询用的，是登陆过程中系统后台根据role不同展示不同列表用的
		 String registNumber ="";
		 String address ="";
		 String buildingName ="";
		 int ywCompanyId =0;
		 
		 String qstartTime ="";
		 String qendTime="";
		 String area ="";
		 
		 registNumber =info.getRegistNumber();
		 address =info.getAddress();
		 buildingName =info.getBuildingName();
		 ywCompanyId= info.getYwcompanyId(); 
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 area = info.getArea();
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 String conditions2="";
	//	 String conditions3="";
		 
		 long total=0;
		 UserInfoVO userInfoVO =null;
		 
		 List<YwInfoVO> items = new ArrayList<YwInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 
		 
		 
		 if(role==10 || role==11){
			 try {
					userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
				} catch (ActiveRecordException e) {
					e.printStackTrace();
				}    if(userInfoVO != null){
	             zjcompanyId2 = userInfoVO.getCompanyId();
	             conditions = " t.zjcompanyId = "+zjcompanyId2;
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
        
        if(!"".equals(area)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.area = '"+area+"'";	
				} 
				else{
					conditions =" t.area = '"+area+"'";	
				} 
			 
		 }
        
        if(!"".equals(qstartTime)){
			 if(!"".equals(conditions2)){  
				 conditions2 =conditions2+" and convert(datetime,endTime,120) >= '"+qstartTime+"'";	
				} 
				else{  
					conditions2 =" convert(datetime,endTime,120) >= '"+qstartTime+"'";	
				}
			
			
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions2)){
				 conditions2 =conditions2+" and convert(datetime,endTime,120) <= '"+qendTime+"'";	
				} 
				else{
					conditions2 =" convert(datetime,endTime,120) <= '"+qendTime+"'";	
				}
			
			
		 }
		 
		 
		  
          if(!"".equals(conditions)){   
        	  if(!"".equals(conditions2)){    
        	   sql="select te.registNumber,te.address,te.buildingname from (select t.registNumber,t.address,t.buildingname from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and "+conditions+" ) te  left join (select registNumber from YwManagerInfo  where "+conditions2+" group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null ";
       		   conditionsSql ="select count(te.registNumber) from (select t.registNumber,t.address,t.buildingname from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and "+conditions+" ) te  left join (select registNumber from YwManagerInfo  where "+conditions2+" group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null";
                  
        	  }
        	  else{
       	   sql="select te.registNumber,te.address,te.buildingname from (select t.registNumber,t.address,t.buildingname from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and "+conditions+" ) te  left join (select registNumber from YwManagerInfo group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null ";
		   conditionsSql ="select count(te.registNumber) from (select t.registNumber,t.address,t.buildingname from TwoCodeElevatorInfo t  where t.dailingFlag = 0 and "+conditions+" ) te  left join (select registNumber from YwManagerInfo group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null";
               }
          }
       	else{   
       	 if(!"".equals(conditions2)){  
       	  sql="select te.registNumber,te.address,te.buildingname from (select t.registNumber,t.address,t.buildingname from TwoCodeElevatorInfo t where t.dailingFlag = 0   ) te  left join (select registNumber from YwManagerInfo  where "+conditions2+" group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null ";
		   conditionsSql ="select count(te.registNumber) from (select t.registNumber,t.address,t.buildingname from TwoCodeElevatorInfo t where t.dailingFlag = 0  ) te  left join (select registNumber from YwManagerInfo  where "+conditions2+" group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null";
          
       	 }
       	 else{
       	   sql="select te.registNumber,te.address,te.buildingname from (select t.registNumber,t.address,t.buildingname from TwoCodeElevatorInfo t  where t.dailingFlag = 0 ) te  left join (select registNumber from YwManagerInfo group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null ";
		   conditionsSql ="select count(te.registNumber) from (select t.registNumber,t.address,t.buildingname from TwoCodeElevatorInfo t where t.dailingFlag = 0  ) te  left join (select registNumber from YwManagerInfo group by registNumber ) y on te.registNumber  =y.registNumber  where y.registNumber is null";
            }
       	}
          try {
				total =YwInfoVO.countBySql(YwInfoVO.class, conditionsSql, null);
				items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "te.registNumber desc", rows, (page-1)*rows);
			} catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}
			 result.put("total", total);
			 result.put("rows", items);  
			 return new JsonView(result);	
		
   }
   
    
    
    public View ywstatisticsquery(YwInfoVO info,int page, int rows){
    	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 int zjcompanyId2 =0;  //非查询用的，是登陆过程中系统后台根据role不同展示不同列表用的
		 int ywCompanyId =0;
		 String areaName="";
		 String qstartTime ="";
		 String qendTime="";
		 
		 ywCompanyId =info.getCompanyId();
		 areaName =info.getArea();
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 String conditions2="";
		 
		 List<YwInfoVO> items = new ArrayList<YwInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
	 
		 long total=0;
		 UserInfoVO userInfoVO =null;
		 if(role==10 || role==11){    
				try {
					userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
				} catch (ActiveRecordException e) {
					e.printStackTrace();
				}
		         if(userInfoVO != null){
		             zjcompanyId2 = userInfoVO.getCompanyId();
		             conditions = " t.zjcompanyId = "+zjcompanyId2;
		            
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
		 
		 if(!"".equals(areaName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.area like '%"+areaName+"%'";	
				} 
				else{
					conditions =" t.area like '%"+areaName+"%'";	
				}
			
			
		 }
		 
		 if(!"".equals(qstartTime)){
			 if(!"".equals(conditions2)){
				 conditions2 =conditions2+" and convert(datetime,endTime,120) >= '"+qstartTime+"'";	
				} 
				else{
					conditions2 =" convert(datetime,endTime,120) >= '"+qstartTime+"'";	
				}
			
			
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions2)){
				 conditions2 =conditions2+" and convert(datetime,endTime,120) <= '"+qendTime+"'";	
				} 
				else{
					conditions2 =" convert(datetime,endTime,120) <= '"+qendTime+"'";	
				}
			
			
		 }
		 
		 if(!"".equals(conditions)){
			  if(!"".equals(conditions2)){
			//  sql ="select t2.*,tc.companyName,isnull(t7.ncqetotal,0) as ncqetotal,isnull(t4.utotal,0) as utotal,isnull(t8.nutotal,0) as nutotal,(select  count(t.registNumber)  from TwoCodeElevatorInfo  t left join (select * from ywmanagerinfo where  " + conditions2+ " ) y  on t.registNumber = y.registNumber where   t.ywcompanyid = t2.ywcompanyid and y.registNumber is null  ) as uncqetotal  from  (select count(*) as etotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t where "+ conditions + " group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t6.userId) as nutotal,t6.companyId from (select tyce.userId,tc.companyId from (select  y.userid,max(y.endTime) as endTime  from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role=18   ) tyc  left join ywmanagerinfo y on tyc.userid = y.userid left join TwoCodeElevatorInfo  t on y.registNumber = t.registNumber where "+conditions + " group by y.userid  ) tyce left join tcuserinfo tc on tyce.userid = tc.id  ) t6 group by t6.companyId) t8 on  t4.companyid =t8.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id left join (select count(t3.registNumber) as ncqetotal,t.ywcompanyId from (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo where "+conditions2+" group by registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  inner join TwoCodeElevatorInfo  t on t.registNumber = t3.registNumber where "+conditions + " group  by ywcompanyid) t7  on t2.ywcompanyid =t7.ywcompanyId ";
				  sql ="select t2.*,-1 as uncqetotal,tc.companyName,t7.ncqetotal,t4.utotal,t8.nutotal,(select  count(distinct t.registNumber)  from TCYEUView  t where " + conditions2+ " and t.dailingFlag = 0 and t.ywcompanyid  = t2.ywcompanyid and "+ conditions+" )  as eywtotal  from  (select count(*) as etotal,sum(case when t.dailingFlag = 1 then 1 else 0 end) as dailingetotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t where "+ conditions + " group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 and t.isinvalid = 0 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t6.userId) as nutotal,t6.companyId from (select tyce.userId,tc.companyId from (select  y.userid,max(y.endTime) as endTime  from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role=18   ) tyc  left join ywmanagerinfo y on tyc.userid = y.userid left join TwoCodeElevatorInfo  t on y.registNumber = t.registNumber where "+conditions + " group by y.userid  ) tyce left join tcuserinfo tc on tyce.userid = tc.id  ) t6 group by t6.companyId) t8 on  t4.companyid =t8.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id left join (select count(t3.registNumber) as ncqetotal,t.ywcompanyId from (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo where "+conditions2+" group by registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  inner join TwoCodeElevatorInfo  t on t.registNumber = t3.registNumber where t.dailingFlag  = 0  and "+conditions + " group  by ywcompanyid) t7  on t2.ywcompanyid =t7.ywcompanyId ";
				   	  
			   }else{
			   //  sql ="select t2.*,tc.companyName,isnull(t7.ncqetotal,0) as ncqetotal,isnull(t4.utotal,0) as utotal,isnull(t8.nutotal,0) as nutotal,(select  count(t.registNumber)  from TwoCodeElevatorInfo  t left join ywmanagerinfo y  on t.registNumber = y.registNumber where  t.ywcompanyid = t2.ywcompanyid and y.registNumber is null  ) as uncqetotal  from  (select count(*) as etotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t where "+ conditions + " group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t6.userId) as nutotal,t6.companyId from (select tyce.userId,tc.companyId from (select  y.userid,max(y.endTime) as endTime  from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role=18   ) tyc  left join ywmanagerinfo y on tyc.userid = y.userid left join TwoCodeElevatorInfo  t on y.registNumber = t.registNumber where "+conditions + " group by y.userid  ) tyce left join tcuserinfo tc on tyce.userid = tc.id  ) t6 group by t6.companyId) t8 on  t4.companyid =t8.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id left join (select count(t3.registNumber) as ncqetotal,t.ywcompanyId from (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  inner join TwoCodeElevatorInfo  t on t.registNumber = t3.registNumber where "+conditions + " group  by ywcompanyid) t7  on t2.ywcompanyid =t7.ywcompanyId ";
				   sql ="select t2.*,-1 as uncqetotal,tc.companyName,t7.ncqetotal,t4.utotal,t8.nutotal,(select  count(distinct t.registNumber)  from TCYEUView  t where t.dailingFlag = 0 and t.ywcompanyid  = t2.ywcompanyid and "+ conditions+" )  as eywtotal  from  (select count(*) as etotal,sum(case when t.dailingFlag = 1 then 1 else 0 end) as dailingetotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t where "+ conditions + " group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 and t.isinvalid = 0 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t6.userId) as nutotal,t6.companyId from (select tyce.userId,tc.companyId from (select  y.userid,max(y.endTime) as endTime  from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role=18   ) tyc  left join ywmanagerinfo y on tyc.userid = y.userid left join TwoCodeElevatorInfo  t on y.registNumber = t.registNumber where "+conditions + " group by y.userid  ) tyce left join tcuserinfo tc on tyce.userid = tc.id  ) t6 group by t6.companyId) t8 on  t4.companyid =t8.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id left join (select count(t3.registNumber) as ncqetotal,t.ywcompanyId from (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  inner join TwoCodeElevatorInfo  t on t.registNumber = t3.registNumber where  t.dailingFlag  = 0 and "+conditions + " group  by ywcompanyid) t7  on t2.ywcompanyid =t7.ywcompanyId ";
				      
			   }
			  conditionsSql = "select count(*) from  (select t.ywcompanyId from TwoCodeElevatorInfo  t where  t.dailingFlag  = 0 and " +conditions + " group by t.ywcompanyId) t2 ";
			  }
		 else{
			 if(!"".equals(conditions2)){
		//	  sql ="select t2.*,tc.companyName,isnull(t7.ncqetotal,0) as ncqetotal,isnull(t4.utotal,0) as utotal,isnull(t6.nutotal,0) as nutotal,(select  count(t.registNumber)  from TwoCodeElevatorInfo  t left join (select * from ywmanagerinfo where  " + conditions2+ " ) y   on t.registNumber = y.registNumber where   t.ywcompanyid = t2.ywcompanyid and y.registNumber is null  ) as uncqetotal    from  (select count(*) as etotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t5.userid) as nutotal, t5.companyid from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role =18) t5 group by t5.companyid) t6 on  t4.companyid =t6.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id left join (select count(t3.registNumber) as ncqetotal,t.ywcompanyId from (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo where "+conditions2+" group by registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  inner join TwoCodeElevatorInfo  t on t.registNumber = t3.registNumber  group  by ywcompanyid) t7  on t2.ywcompanyid =t7.ywcompanyId ";	 
			  sql ="select t2.*,-1 as uncqetotal,tc.companyName,t7.ncqetotal,t4.utotal,t6.nutotal,(select  count(distinct t.registNumber)  from TCYEUView  t where " + conditions2+ " and t.dailingFlag = 0 and t.ywcompanyid  = t2.ywcompanyid)  as eywtotal    from  (select count(*) as etotal,sum(case when t.dailingFlag = 1 then 1 else 0 end) as dailingetotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 and t.isinvalid = 0 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t5.userid) as nutotal, t5.companyid from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role =18) t5 group by t5.companyid) t6 on  t4.companyid =t6.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id left join (select count(t3.registNumber) as ncqetotal,t.ywcompanyId from (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo where "+conditions2+" group by registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  inner join TwoCodeElevatorInfo  t on t.registNumber = t3.registNumber where t.dailingFlag  = 0 group  by ywcompanyid) t7  on t2.ywcompanyid =t7.ywcompanyId ";	 		
			 }
			 else{
		//	   sql ="select t2.*,tc.companyName,isnull(t7.ncqetotal,0) as ncqetotal,isnull(t4.utotal,0) as utotal,isnull(t6.nutotal,0) as nutotal,(select  count(t.registNumber)  from TwoCodeElevatorInfo  t left join ywmanagerinfo y  on t.registNumber = y.registNumber where t.ywcompanyid = t2.ywcompanyid and y.registNumber is null  ) as uncqetotal  from  (select count(*) as etotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t5.userid) as nutotal, t5.companyid from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role =18) t5 group by t5.companyid) t6 on  t4.companyid =t6.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id left join (select count(t3.registNumber) as ncqetotal,t.ywcompanyId from (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  inner join TwoCodeElevatorInfo  t on t.registNumber = t3.registNumber  group  by ywcompanyid) t7  on t2.ywcompanyid =t7.ywcompanyId ";	
			  sql ="select t2.*,-1 as uncqetotal,tc.companyName,t7.ncqetotal,t4.utotal,t6.nutotal,(select  count(distinct t.registNumber)  from TCYEUView  t where t.dailingFlag = 0 and t.ywcompanyid  = t2.ywcompanyid)  as eywtotal  from  (select count(*) as etotal,sum(case when t.dailingFlag = 1 then 1 else 0 end) as dailingetotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 and t.isinvalid = 0 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t5.userid) as nutotal, t5.companyid from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role =18) t5 group by t5.companyid) t6 on  t4.companyid =t6.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id left join (select count(t3.registNumber) as ncqetotal,t.ywcompanyId from (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  inner join TwoCodeElevatorInfo  t on t.registNumber = t3.registNumber where t.dailingFlag  = 0 group  by ywcompanyid) t7  on t2.ywcompanyid =t7.ywcompanyId ";	
					
			 }
			  conditionsSql ="select count(*) from  (select t.ywcompanyId from TwoCodeElevatorInfo  t where t.dailingFlag  = 0 group by t.ywcompanyId) t2  ";
			}
		 
		 /*
		 try {
			 if(role==10 || role ==11){ //质监局
		    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
		    	 if(userInfoVO != null){
		         int zjcompanyId = userInfoVO.getCompanyId();
		         Object[] param=null;
		         total  =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from  (select t.ywcompanyId from TwoCodeElevatorInfo  t where t.zjcompanyId = ? group by t.ywcompanyId) t2   ",new Object[]{zjcompanyId});
				 sql ="select t2.*,tc.companyName,isnull(t7.ncqetotal,0) as ncqetotal,isnull(t4.utotal,0) as utotal,isnull(t8.nutotal,0) as nutotal  from  (select count(*) as etotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t where t.zjcompanyId = ? group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t6.userId) as nutotal,t6.companyId from (select tyce.userId,tc.companyId from (select  y.userid,max(y.endTime) as endTime  from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role=18   ) tyc  left join ywmanagerinfo y on tyc.userid = y.userid left join TwoCodeElevatorInfo  t on y.registNumber = t.registNumber where t.zjcompanyid =? group by y.userid  ) tyce left join tcuserinfo tc on tyce.userid = tc.id  ) t6 group by t6.companyId) t8 on  t4.companyid =t8.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id left join (select count(t3.registNumber) as ncqetotal,t.ywcompanyId from (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  inner join TwoCodeElevatorInfo  t on t.registNumber = t3.registNumber where t.zjcompanyId = ? group  by ywcompanyid) t7  on t2.ywcompanyid =t7.ywcompanyId ";
				 param = new Object[]{zjcompanyId,zjcompanyId,zjcompanyId};
				 items = YwInfoVO.findBySql(YwInfoVO.class, sql, param, "t2.ywcompanyId desc", rows, (page-1)*rows);
			     
		     }
			 }
			 else
			 {
				total  =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from  (select t.ywcompanyId from TwoCodeElevatorInfo  t group by t.ywcompanyId) t2  ",null);
				sql ="select t2.*,tc.companyName,isnull(t7.ncqetotal,0) as ncqetotal,isnull(t4.utotal,0) as utotal,isnull(t6.nutotal,0) as nutotal  from  (select count(*) as etotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t5.userid) as nutotal, t5.companyid from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role =18) t5 group by t5.companyid) t6 on  t4.companyid =t6.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id left join (select count(t3.registNumber) as ncqetotal,t.ywcompanyId from (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  inner join TwoCodeElevatorInfo  t on t.registNumber = t3.registNumber  group  by ywcompanyid) t7  on t2.ywcompanyid =t7.ywcompanyId ";
				items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "t2.ywcompanyId desc", rows, (page-1)*rows);
				
			 }
			 } catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}
			*/
		 try {
		//		total =YwInfoVO.countBySql(YwInfoVO.class, conditionsSql, null);
		//		items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "t2.etotal desc,t4.utotal desc ", rows, (page-1)*rows);
			 items = YwInfoVO.findBySql(YwInfoVO.class, sql, null, "t2.etotal desc,t4.utotal desc ");
			} catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}
			 result.put("total", total);
			 result.put("rows", items);  
			 return new JsonView(result);	
    }
    
    public View cqquery(YWOutDateVO info,int page, int rows)throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 String registNumber ="";
		 int  is_Process =2;
		 String qstartTime ="";
		 String qendTime="";
		 String areaName="";
		 int zjcompanyId2 =0;  //非查询用的，是登陆过程中系统后台根据role不同展示不同列表用的
		 int townshipStreets =0;
		 int ywCompanyId =0;
		 
	//	 registNumber ="%"+info.getRegistNumber()+"%";
		 registNumber =info.getRegistNumber();
		 is_Process = info.getIsProcessFlag();
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 areaName =info.getArea();
		 townshipStreets =info.getTownshipStreets();
		 ywCompanyId =info.getCompanyId();
		 
	//	 String conditions="";
	//	 Object[] param=null;
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 
		 if(role==10 || role==11){
	           UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	         if(userInfoVO != null){
	             zjcompanyId2 = userInfoVO.getCompanyId();
	             conditions = " te.zjcompanyId = "+zjcompanyId2;
	             }	 
	          }
		 
		 if(role==20 || role==21){
	           UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	         if(userInfoVO != null){
	             zjcompanyId2 = userInfoVO.getCompanyId();
	             conditions = " te.zjcompanyId = "+zjcompanyId2;
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
		 
		 if(is_Process != 2){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.isProcessFlag ="+is_Process;	
				} 
				else{
				 conditions =" t.isProcessFlag = "+is_Process;	
				}
			 
		 }
		 
		 if(!"".equals(areaName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and te.area like '%"+areaName+"%'";	
				} 
				else{
					conditions =" te.area like '%"+areaName+"%'";	
				}
			 
		 }
		 
		 if(ywCompanyId > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and te.ywCompanyId = "+ywCompanyId;	
				} 
				else{
					conditions =" te.ywCompanyId = "+ywCompanyId;	
				}
		 }
		 
		 if(townshipStreets > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and te.townshipStreets = "+townshipStreets;	
				} 
				else{
					conditions =" te.townshipStreets = "+townshipStreets;	
				}
			 
			 
		 }
		 
         
         if(!"".equals(qstartTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and left(CONVERT(varchar(100), t.properWhTime, 120),10)  >= '"+qstartTime+"'" ;	 
			 } 
			 else{
				 conditions =" left(CONVERT(varchar(100), t.properWhTime, 120),10)  >= '"+qstartTime+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(qendTime)){ System.out.println("qendTime----"+qendTime);
			 if(!"".equals(conditions)){
				 conditions =conditions+" and left(CONVERT(varchar(100), t.properWhTime, 120),10)  <= '"+qendTime+"'" ;	 
			 } 
			 else{
				 conditions =" left(CONVERT(varchar(100), t.properWhTime, 120),10)  <= '"+qendTime+"'" ;	 
			 }
		 }
		 //加入系统设置，如果系统配置了运维超期关闭，则查不出运维超期，如果配置了运维超期开启，才能查出
		 SysSetingsVO sysSetingsVO = null;
		 String ywsql ="select t.setingsSwitch,isnull(t.startTime,'') as startTime,isnull(t.endTime,'') as endTime   from TwoCodeSysSetings t where t.itemName = ?";
		 sysSetingsVO = SysSetingsVO.findFirstBySql(SysSetingsVO.class,ywsql, new Object[]{"0"});
		 if(sysSetingsVO == null){
			 result.put("total", 0);
			 result.put("rows", null);
			 return new JsonView(result);
			 
		 }
		 else{
			 int setingsSwitch =sysSetingsVO.getSetingsSwitch();
			 String startTime =sysSetingsVO.getStartTime();
			 String endTime  =sysSetingsVO.getEndTime(); 
			 System.out.println("setingsSwitch---"+setingsSwitch);
			 if(setingsSwitch==0){  //运维超期关闭
				 result.put("total", 0);
				 result.put("rows", null);
				 return new JsonView(result); 
			 }
		   else{    
		/*
			 
		 if(role==2 || role==1){
			 if(is_Process==2){
		 conditions =" t.registNumber like  ? ";
		 param = new Object[]{registNumber};
			 }
			 else{
				 conditions =" t.registNumber like  ? and t.isProcessFlag=?";
				 param = new Object[]{registNumber,is_Process};
			 }
		 }
		 if(role==22 || role==23){
			 if(is_Process==2){
		 conditions =" t.registNumber like  ? ";
		 param = new Object[]{registNumber};
			 }
			 else{
				 conditions =" t.registNumber like  ? and t.isProcessFlag=?";
				 param = new Object[]{registNumber,is_Process};
			 }
		 }
		 if(role==10 || role==11){
			 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	    	 zjcompanyId2 = userInfoVO.getCompanyId();
	    	 if(is_Process==2){
			 conditions =" t.registNumber like  ?   and te.zjcompanyId=?";
			 param = new Object[]{registNumber,zjcompanyId2};
			 }
	    	 else{
	    		 conditions =" t.registNumber like  ? and  t.isProcessFlag=?  and te.zjcompanyId=?";
				 param = new Object[]{registNumber,is_Process,zjcompanyId2}; 
	    	 }
		 }
			 
		 }
		 
		 if(role==20 || role==21){
			 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	    	 zjcompanyId2 = userInfoVO.getCompanyId();
	    	 if(is_Process==2){
			 conditions =" t.registNumber like  ?   and te.townshipStreets=?";
			 param = new Object[]{registNumber,zjcompanyId2};
			 }
	    	 else{
	    		 conditions =" t.registNumber like  ? and  t.isProcessFlag=?  and te.townshipStreets=?";
				 param = new Object[]{registNumber,is_Process,zjcompanyId2}; 
	    	 }
		 }
			 
		 }
		 
	//	 long total = TCUserInfoView.count(TCUserInfoView.class, conditions, param);
		 String sql ="";
		 if("".equals(qstartTime) && "".equals(qendTime))
		 sql ="select t.id,t.registNumber,t.endTime,t.tcOutDateFlag,isnull(te.address,'') as address,t.properWhTime,t.isProcessFlag,isnull(t.dealWithTime,'') as dealWithTime,t.beyondTime,t.beyondLevel,isnull(tc.companyName,'') as companyName from TwoCodeYWOutDate  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber  left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId where (t.tcOutDateFlag =1 or t.tcOutDateFlag =2) and left(CONVERT(varchar(100), t.properWhTime, 120),10) >='"+startTime+"' and left(CONVERT(varchar(100), t.properWhTime, 120),10) <='"+endTime+"' and "+conditions;
		 if("".equals(qstartTime) && !"".equals(qendTime))
		 sql ="select t.id,t.registNumber,t.endTime,t.tcOutDateFlag,isnull(te.address,'') as address,t.properWhTime,t.isProcessFlag,isnull(t.dealWithTime,'') as dealWithTime,t.beyondTime,t.beyondLevel,isnull(tc.companyName,'') as companyName from TwoCodeYWOutDate  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber  left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId where (t.tcOutDateFlag =1 or t.tcOutDateFlag =2) and left(CONVERT(varchar(100), t.properWhTime, 120),10) <= '"+qendTime+"' and left(CONVERT(varchar(100), t.properWhTime, 120),10) >='"+startTime+"' and left(CONVERT(varchar(100), t.properWhTime, 120),10) <='"+endTime+"' and "+conditions; 
		 if(!"".equals(qstartTime) && "".equals(qendTime))
		 sql ="select t.id,t.registNumber,t.endTime,t.tcOutDateFlag,isnull(te.address,'') as address,t.properWhTime,t.isProcessFlag,isnull(t.dealWithTime,'') as dealWithTime,t.beyondTime,t.beyondLevel,isnull(tc.companyName,'') as companyName from TwoCodeYWOutDate  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber  left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId where (t.tcOutDateFlag =1 or t.tcOutDateFlag =2) and left(CONVERT(varchar(100), t.properWhTime, 120),10) >= '"+qstartTime+"' and left(CONVERT(varchar(100), t.properWhTime, 120),10) >='"+startTime+"' and left(CONVERT(varchar(100), t.properWhTime, 120),10) <='"+endTime+"' and "+conditions; 
		 if(!"".equals(qstartTime) && !"".equals(qendTime))	
		 sql ="select t.id,t.registNumber,t.endTime,t.tcOutDateFlag,isnull(te.address,'') as address,t.properWhTime,t.isProcessFlag,isnull(t.dealWithTime,'') as dealWithTime,t.beyondTime,t.beyondLevel,isnull(tc.companyName,'') as companyName from TwoCodeYWOutDate  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber  left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId where (t.tcOutDateFlag =1 or t.tcOutDateFlag =2) and left(CONVERT(varchar(100), t.properWhTime, 120),10) >= '"+qstartTime+"' and left(CONVERT(varchar(100), t.properWhTime, 120),10) <= '"+qendTime+"' and left(CONVERT(varchar(100), t.properWhTime, 120),10) >='"+startTime+"' and left(CONVERT(varchar(100), t.properWhTime, 120),10) <='"+endTime+"' and "+conditions; 	
		 List<YWOutDateVO>  items2 = YWOutDateVO.findBySql(YWOutDateVO.class, sql, param, null);
	   	 List<YWOutDateVO>  items = YWOutDateVO.findBySql(YWOutDateVO.class, sql, param, "t.id desc", rows, (page-1)*rows);
		 System.out.println("------"+items.size());
		 
		  result.put("total", items2.size());
		  result.put("rows", items);
		  return new JsonView(result); */
			   
			 if(!"".equals(conditions)){
				//      sql ="select t.id,t.registNumber,t.endTime,t.tcOutDateFlag,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,t.properWhTime,t.isProcessFlag,isnull(t.dealWithTime,'') as dealWithTime,t.beyondTime,t.beyondLevel,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile from TwoCodeYWOutDate  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber  left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId   where "+ conditions+" and (t.tcOutDateFlag =1 or t.tcOutDateFlag =2) and left(CONVERT(varchar(100), t.properWhTime, 120),10) >='"+startTime+"' and left(CONVERT(varchar(100), t.properWhTime, 120),10) <='"+endTime+"'";
				//      conditionsSql ="select count(1) from TwoCodeYWOutDate  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber where "+ conditions+" and (t.tcOutDateFlag =1 or t.tcOutDateFlag =2) and left(CONVERT(varchar(100), t.properWhTime, 120),10) >='"+startTime+"' and left(CONVERT(varchar(100), t.properWhTime, 120),10) <='"+endTime+"'";
			      sql ="select t.id,t.registNumber,t.endTime,t.tcOutDateFlag,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,t.properWhTime,t.isProcessFlag,isnull(t.dealWithTime,'') as dealWithTime,t.beyondTime,t.beyondLevel,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile from TwoCodeYWOutDate  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber  left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId   where "+ conditions+" and (t.tcOutDateFlag =1 or t.tcOutDateFlag =2) ";
			      conditionsSql ="select count(1) from TwoCodeYWOutDate  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber where "+ conditions+" and (t.tcOutDateFlag =1 or t.tcOutDateFlag =2) ";
					    	
			           }
			 else{
				//	 sql ="select t.id,t.registNumber,t.endTime,t.tcOutDateFlag,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,t.properWhTime,t.isProcessFlag,isnull(t.dealWithTime,'') as dealWithTime,t.beyondTime,t.beyondLevel,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile from TwoCodeYWOutDate  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber  left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId where (t.tcOutDateFlag =1 or t.tcOutDateFlag =2) and left(CONVERT(varchar(100), t.properWhTime, 120),10) >='"+startTime+"' and left(CONVERT(varchar(100), t.properWhTime, 120),10) <='"+endTime+"'";
				//	 conditionsSql ="select count(1) from TwoCodeYWOutDate  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber where (t.tcOutDateFlag =1 or t.tcOutDateFlag =2) and left(CONVERT(varchar(100), t.properWhTime, 120),10) >='"+startTime+"' and left(CONVERT(varchar(100), t.properWhTime, 120),10) <='"+endTime+"'";	 
				 sql ="select t.id,t.registNumber,t.endTime,t.tcOutDateFlag,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,t.properWhTime,t.isProcessFlag,isnull(t.dealWithTime,'') as dealWithTime,t.beyondTime,t.beyondLevel,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo  where   id =te.townshipStreets) as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile from TwoCodeYWOutDate  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber  left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId ";
				 conditionsSql ="select count(1) from TwoCodeYWOutDate  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber";	 
					  
			 }
			 long total =YWOutDateVO.countBySql(YWOutDateVO.class, conditionsSql, null);
			  List<YWOutDateVO> items=YWOutDateVO.findBySql(YWOutDateVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
			  
			  
			  if(role ==1 || role==2){   //为了演示不看到超期记录
	        /*    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.loginName  from  TwoCodeUserInfo tu  where tu.id = ?", new Object[] { userid });
	    	    	 if(userInfoVO != null){
	    	    		 String userName2 =userInfoVO.getLoginName();
	    	    		 if("cy".equals(userName2)){
	    	    			 total = 0;
	    	    			 items = new ArrayList<YWOutDateVO>();
	    	    		 }
	    	    	 } */
				  UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.isyanshi  from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	    	 if(userInfoVO != null){
	    	    		int isyanshi =userInfoVO.getIsyanshi();
	    	    		if(isyanshi == 1){
	    	    			total = 0 ;
	    	    			items = new ArrayList<YWOutDateVO>();
	    	    		}
	    	    	 }
				}
			  result.put("total", total);
			  result.put("rows", items);
			  return new JsonView(result);
			 }  
		 }
	 }
    
    public View xclist(int page, int rows) throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 long total=0;
		 List<XcInfoVO> items = null;
	     if(role==2 || role==1){ //系统管理员  
	    	 total  =XcInfoVO.count(XcInfoVO.class, "ywKind = ?", new Object[]{"1"});
	    //	 items = ElevaltorInfoVO.findAll(ElevaltorInfoVO.class, null, null, null, rows, (page-1)*rows);
	    	 String sql ="select isnull(t.remark,'') as remark,t.id,t.registNumber,isnull(te.address,'') as address,isnull(te.area,'') as area,isnull(te.buildingName,'') as buildingName,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,isnull(tu.contactPhone,'') as contactPhone,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo where id= te.townshipStreets) as jdbCompanyName from XcManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.ywKind ='1'";
	    	 items = XcInfoVO.findBySql(XcInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
	    	
	     }
	     if(role==22 || role==23){ //系统管理员  
	    	 total  =XcInfoVO.count(XcInfoVO.class, "ywKind = ?", new Object[]{"1"});
	    //	 items = ElevaltorInfoVO.findAll(ElevaltorInfoVO.class, null, null, null, rows, (page-1)*rows);
	    	 String sql ="select isnull(t.remark,'') as remark,t.id,t.registNumber,isnull(te.address,'') as address,isnull(te.area,'') as area,isnull(te.buildingName,'') as buildingName,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,isnull(tu.contactPhone,'') as contactPhone,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo where id= te.townshipStreets) as jdbCompanyName from XcManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.ywKind ='1'";
	    	 items = XcInfoVO.findBySql(XcInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
	    	
	     }
	     if(role==10 || role ==11){ //质监局
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         total  =XcInfoVO.countBySql(XcInfoVO.class, "select count(*) from YwManagerInfo y left join TwoCodeElevatorInfo t on y.registNumber = t.registNumber where t.zjCompanyId=? and y.ywKind ='1'", new Object[]{zjcompanyId});
	         System.out.println("所属质监局标签运维数："+total);
	         String sql ="select isnull(t.remark,'') as remark,t.id,t.registNumber,isnull(te.address,'') as address,isnull(te.area,'') as area,isnull(te.buildingName,'') as buildingName,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,isnull(tu.contactPhone,'') as contactPhone,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo where id= te.townshipStreets) as jdbCompanyName from XcManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where te.zjCompanyId=? and t.ywstatus=1 and  t.ywKind ='1'";
	    	 items = XcInfoVO.findBySql(XcInfoVO.class, sql, new Object[]{zjcompanyId}, "t.id desc", rows, (page-1)*rows);
	    	 }
	     }
	     if(role==20 || role ==21){ //街道办
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         total  =XcInfoVO.countBySql(XcInfoVO.class, "select count(*) from YwManagerInfo y left join TwoCodeElevatorInfo t on y.registNumber = t.registNumber where t.townshipStreets=? and y.ywKind ='1'", new Object[]{zjcompanyId});
	         System.out.println("所属街道办标签运维数："+total);
	         String sql ="select isnull(t.remark) as remark,t.id,t.registNumber,isnull(te.address,'') as address,isnull(te.area,'') as area,isnull(te.buildingName,'') as buildingName,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,isnull(tu.contactPhone,'') as contactPhone,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo where id= te.townshipStreets) as jdbCompanyName from XcManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where te.townshipStreets=? and t.ywstatus=1 and  t.ywKind ='1'";
	    	 items = XcInfoVO.findBySql(XcInfoVO.class, sql, new Object[]{zjcompanyId}, "t.id desc", rows, (page-1)*rows);
	    	 }
	     }
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	 
    public View xcquery(XcInfoVO info,int page, int rows)throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 String registNumber ="";
		 int zjcompanyId2 =0;  //非查询用的，是登陆过程中系统后台根据role不同展示不同列表用的
		 String buildingName ="";
		 String areaName="";
		 String qstartTime ="";
		 String qendTime="";
		 int townshipStreets =0;
		 String ywuserName ="";
		 
		 registNumber =info.getRegistNumber();
		 areaName =info.getArea();
		 buildingName =info.getBuildingName();
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 townshipStreets =info.getTownshipStreets();
		 ywuserName = info.getUserName();
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 if(role==10 || role==11){
	           UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	         if(userInfoVO != null){
	             zjcompanyId2 = userInfoVO.getCompanyId();
	             conditions = " te.zjcompanyId = "+zjcompanyId2;
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
		 
		 if(!"".equals(buildingName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and te.buildingName like '%"+buildingName+"%'";	
				} 
				else{
					conditions =" te.buildingName like '%"+buildingName+"%'";	
				}
			 
		 }
         
		
         if(townshipStreets > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and te.townshipStreets = "+townshipStreets;	
				} 
				else{
					conditions =" te.townshipStreets = "+townshipStreets;	
				}
			 
			 
		 }
         
         if(!"".equals(areaName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and te.area like '%"+areaName+"%'";	
				} 
				else{
					conditions =" te.area like '%"+areaName+"%'";	
				}
			 
		 }
         
         if(!"".equals(qstartTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and left(t.startTime,10)  >= '"+qstartTime+"'" ;	 
			 } 
			 else{
				 conditions =" left(t.startTime,10)  >= '"+qstartTime+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and left(t.startTime,10)  <= '"+qendTime+"'" ;	 
			 } 
			 else{
				 conditions =" left(t.startTime,10)  <= '"+qendTime+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(ywuserName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and tu.userName like '%"+ywuserName+"%'";	
				} 
				else{
					conditions =" tu.userName like '%"+ywuserName+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(conditions)){
			  sql ="select isnull(t.remark,'') as remark,t.id,t.registNumber,isnull(te.address,'') as address,isnull(te.area,'') as area,isnull(te.buildingName,'') as buildingName,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,isnull(tu.contactPhone,'') as contactPhone,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo where id= te.townshipStreets) as jdbCompanyName from XcManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId where "+ conditions+"  and t.ywKind ='1' ";
			  conditionsSql = "select count(1)  from XcManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId   where "+ conditions+"  and t.ywKind ='1' ";
			 }
			 else{
			  sql ="select isnull(t.remark,'') as remark,t.id,t.registNumber,isnull(te.address,'') as address,isnull(te.area,'') as area,isnull(te.buildingName,'') as buildingName,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,isnull(tu.contactPhone,'') as contactPhone,isnull(tc.companyName,'') as companyName,(select companyName from TwoCodeCompanyInfo where id= te.townshipStreets) as jdbCompanyName from XcManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId where  t.ywKind ='1' ";
			  conditionsSql = "select count(1)  from XcManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId   where  t.ywKind ='1' ";
						 
			 }
        
		/*
		 Object[] param=null;
		 if(role==2 || role==1){
		 conditions ="t.registNumber like  ?  and t.ywKind = '1'";
		 param = new Object[]{registNumber};
		 }
		 if(role==22 || role==23){
			 conditions ="t.registNumber like  ?  and t.ywKind = '1'";
			 param = new Object[]{registNumber};
			 }
		 if(role==10 || role==11){
			 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	    	 zjcompanyId2 = userInfoVO.getCompanyId();
			 conditions =" t.registNumber like  ?  and t.ywstatus='1' and t.ywKind = '1' and te.zjcompanyId=?";
			 param = new Object[]{registNumber,zjcompanyId2};
		 }
			 
		 }
		 
		 if(role==20 || role==21){
			 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	    	 zjcompanyId2 = userInfoVO.getCompanyId();
			 conditions =" t.registNumber like  ?  and t.ywstatus='1' and t.ywKind = '1' and te.townshipStreets=?";
			 param = new Object[]{registNumber,zjcompanyId2};
		 }
			 
		 }
		 */
		 
	//	 long total = TCUserInfoView.count(TCUserInfoView.class, conditions, param);
	//   	 String sql ="select t.id,t.registNumber,isnull(te.address,'') as address,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,isnull(tc.companyName,'') as companyName from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId where "+conditions;
	//   	 List<YwInfoVO>  items2 = YwInfoVO.findBySql(YwInfoVO.class, sql, param, null);
	//   	 List<YwInfoVO>  items = YwInfoVO.findBySql(YwInfoVO.class, sql, param, "t.id desc", rows, (page-1)*rows);
		  long total =XcInfoVO.countBySql(XcInfoVO.class, conditionsSql, null);
		  List<XcInfoVO> items=XcInfoVO.findBySql(XcInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
		
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	 }
    
    public View tempywlist(int page, int rows) throws Exception{
	  
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 long total=0;
		 List<TYwInfoVO> items = null;
	     if(role==2 || role==1){ //系统管理员  
	     //	 total  =YwInfoVO.count(YwInfoVO.class, null, null);
	    	 total =TYwInfoVO.countBySql(TYwInfoVO.class, "select count(*) from TempYwManagerInfo t where t.ywKind = '0' and t.id =(select max(id) from TempYwManagerInfo where registNumber = t.registNumber)", null);
	    //	 items = ElevaltorInfoVO.findAll(ElevaltorInfoVO.class, null, null, null, rows, (page-1)*rows);
	   	//   String sql ="select t.id,t.userId,t.registNumber,isnull(tce.address,'') as address,isnull(tce.buildingName,'') as buildingName,isnull(tce.area,'') as area,tcc.companyName, (case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,(select companyName from  TwoCodeCompanyInfo where id = tu.companyId) as companyName2,t.ywSource from TempYwManagerInfo  t  left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeElevatorInfo tce on t.registNumber = tce.registNumber left join TwoCodeCompanyInfo tcc on tce.ywCompanyId = tcc.id where t.ywKind ='0' and t.id =(select max(id) from TempYwManagerInfo where registNumber = t.registNumber) ";
	    	 String sql ="select t.id,t.userId,t.registNumber,isnull(tce.address,'') as address,isnull(tce.buildingName,'') as buildingName,isnull(tce.area,'') as area,tcc.companyName, (case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,(select companyName from  TwoCodeCompanyInfo where id = tu.companyId) as companyName2,t.ywSource,isnull(tcde.address,'') as tcdaddress,isnull(tcde.buildingName,'') as tcdbuildingName,tcu.companyName as subCompanyName from TempYwManagerInfo  t  left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeElevatorInfo tce on t.registNumber = tce.registNumber left join TwoCodeddElevatorInfo tcde on t.registNumber = tcde.registNumber left join tcuserinfo   tcu on  tcde.subPersonId =tcu.userid left join TwoCodeCompanyInfo tcc on tce.ywCompanyId = tcc.id where t.ywKind ='0' and t.id =(select max(id) from TempYwManagerInfo where registNumber = t.registNumber) ";
	    	 
	    	 items = TYwInfoVO.findBySql(TYwInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
	    	
	     }
	     
	     if(role==22 || role==23){ //市质监，顶层质监  
	 	    	 total =TYwInfoVO.countBySql(TYwInfoVO.class, "select count(*) from TempYwManagerInfo t where t.ywKind = '0' and t.id =(select max(id) from TempYwManagerInfo where registNumber = t.registNumber)", null);
	 	    //	 String sql ="select t.id,t.userId,t.registNumber,isnull(tce.address,'') as address,isnull(tce.buildingName,'') as buildingName,isnull(tce.area,'') as area,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,t.ywSource from TempYwManagerInfo  t  left join TwoCodeUserExtInfo tu on tu.userid = t.userId  left join TwoCodeElevatorInfo tce on t.registNumber = tce.registNumber where t.ywKind ='0' and t.ywSource = 1";
	 	   	     String sql ="select t.id,t.userId,t.registNumber,isnull(tce.address,'') as address,isnull(tce.buildingName,'') as buildingName,isnull(tce.area,'') as area,tcc.companyName,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,(select companyName from  TwoCodeCompanyInfo where id = tu.companyId) as companyName2,t.ywSource from TempYwManagerInfo  t  left join TwoCodeUserExtInfo tu on tu.userid = t.userId  left join TwoCodeElevatorInfo tce on t.registNumber = tce.registNumber left join TwoCodeCompanyInfo tcc on tce.ywCompanyId = tcc.id where t.ywKind ='0' and t.id =(select max(id) from TempYwManagerInfo where registNumber = t.registNumber) ";
	 	    	 items = TYwInfoVO.findBySql(TYwInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
	 	    	
	 	     }
	 	     
	 	 if(role==10 || role ==11){ //质监局
	 	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[]{ userid });
	 	    	 if(userInfoVO != null){
	 	         int zjcompanyId = userInfoVO.getCompanyId();
	 	         total =TYwInfoVO.countBySql(TYwInfoVO.class, "select count(*) from TempYwManagerInfo t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber where t.ywKind = '0' and t.id =(select max(id) from TempYwManagerInfo where registNumber = t.registNumber) and  te.zjCompanyId=? ",  new Object[]{zjcompanyId});
	 	  //  	 String sql ="select t.id,t.userId,t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(tce.area,'') as area,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,t.ywSource from TempYwManagerInfo  t  left join TwoCodeUserExtInfo tu on tu.userid = t.userId  left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber where t.ywKind ='0' and t.ywSource = 1 and te.zjCompanyId= ?";
	 	  //       String sql ="select t.id,t.userId,t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,t.ywSource from TempYwManagerInfo  t  left join TwoCodeUserExtInfo tu on tu.userid = t.userId  left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber where t.ywKind ='0'  and t.id =(select max(id) from TempYwManagerInfo where registNumber = t.registNumber) and te.zjCompanyId= ?";
	 	         String sql ="select t.id,t.userId,t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,tcc.companyName,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,(select companyName from  TwoCodeCompanyInfo where id = tu.companyId) as companyName2,t.ywSource from TempYwManagerInfo  t  left join TwoCodeUserExtInfo tu on tu.userid = t.userId  left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tcc on te.ywCompanyId = tcc.id  where t.ywKind ='0'  and t.id =(select max(id) from TempYwManagerInfo where registNumber = t.registNumber) and te.zjCompanyId= ? ";
	 	    	
	 	         items = TYwInfoVO.findBySql(TYwInfoVO.class, sql, new Object[]{zjcompanyId}, "t.id desc", rows, (page-1)*rows);
	 	      
	 	    	 }
	 	     }
	       
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
    
    public View tempquery(TYwInfoVO info,int page, int rows)throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
//		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 String registNumber ="";
		 String userName ="";
		 String buildingName ="";
		 String qstartTime ="";
		 String qendTime="";
		 
		 int zjcompanyId2 =0;  //非查询用的，是登陆过程中系统后台根据role不同展示不同列表用的
		 
		 int eucompanyName =100;
		 
	//	 registNumber ="%"+info.getRegistNumber()+"%";
		 registNumber =info.getRegistNumber();
		 userName = info.getUserName();
		 buildingName =info.getBuildingName();
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 
		 eucompanyName =info.getEucompanyName();
        
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 if(role==10 || role==11){
	           UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	         if(userInfoVO != null){
	             zjcompanyId2 = userInfoVO.getCompanyId();
	             conditions = " te.zjcompanyId = "+zjcompanyId2;
	             }	 
	          }
		 
		 if(role==20 || role==21){
	           UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	         if(userInfoVO != null){
	             zjcompanyId2 = userInfoVO.getCompanyId();
	             conditions = " te.zjcompanyId = "+zjcompanyId2;
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
		/* 
		 if(!"".equals(userName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and tu.userName like '%"+userName+"%'";	
				} 
				else{
				 conditions =" tu.userName like '%"+userName+"%'";	
				}
			 
		 }
		 */
		 
		 if(!"".equals(userName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and charindex('"+userName+"',tu.userName)<>0 ";	
				} 
				else{
				 conditions =" charindex('"+userName+"',tu.userName)<>0 ";	
				}
			 
		 }
		 
		 if(!"".equals(buildingName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and te.buildingName like '%"+buildingName+"%'";	
				} 
				else{
				 conditions =" te.buildingName like '%"+buildingName+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(qstartTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and left(t.startTime,10)  >= '"+qstartTime+"'" ;	 
			 } 
			 else{
				 conditions =" left(t.startTime,10)  >= '"+qstartTime+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and left(t.startTime,10)  <= '"+qendTime+"'" ;	 
			 } 
			 else{
				 conditions =" left(t.startTime,10)  <= '"+qendTime+"'" ;	 
			 }
		 }
		 
		 if(eucompanyName == 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and isnull(te.ywCompanyId,0) = isnull(tu.companyid,0) ";
			 }
			 else{
				 conditions ="  isnull(te.ywCompanyId,0) = isnull(tu.companyid,0) ";
			 }
		 }
		 
		 if(eucompanyName == 1){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and isnull(te.ywCompanyId,0) != isnull(tu.companyid,0) ";
			 }
			 else{
				 conditions ="  isnull(te.ywCompanyId,0) != isnull(tu.companyid,0) ";
			 }
		 }
		 
		 if(role ==1 || role ==2){
			 if(!"".equals(conditions)){ 
				 sql ="select t.id,t.userId,t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,t.ywSource,(select companyName from TwoCodeCompanyInfo where id = te.ywCompanyId) as companyName,(select companyName from  TwoCodeCompanyInfo where id = tu.companyId) as companyName2,isnull(tcde.address,'') as tcdaddress,isnull(tcde.buildingName,'') as tcdbuildingName,tcu.companyName as subCompanyName  from TempYwManagerInfo  t  left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeddElevatorInfo tcde on t.registNumber = tcde.registNumber left join tcuserinfo   tcu on  tcde.subPersonId =tcu.userid  where  t.id =(select max(id) from TempYwManagerInfo where registNumber = t.registNumber) and "+ conditions;   
				 conditionsSql = "select count(*) from TempYwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId where  t.id =(select max(id) from TempYwManagerInfo where registNumber = t.registNumber) and "+ conditions;
		   		 }
		     else{
		   		  sql ="select t.id,t.userId,t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,t.ywSource,(select companyName from TwoCodeCompanyInfo where id = te.ywCompanyId) as companyName,(select companyName from  TwoCodeCompanyInfo where id = tu.companyId) as companyName2,isnull(tcde.address,'') as tcdaddress,isnull(tcde.buildingName,'') as tcdbuildingName,tcu.companyName as subCompanyName   from TempYwManagerInfo  t  left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeddElevatorInfo tcde on t.registNumber = tcde.registNumber left join tcuserinfo   tcu on  tcde.subPersonId =tcu.userid where t.id =(select max(id) from TempYwManagerInfo where registNumber = t.registNumber)";	   		
		   		  conditionsSql = "select count(*) from TempYwManagerInfo  t where  t.id =(select max(id) from TempYwManagerInfo where registNumber = t.registNumber) ";
		   					 
		   		 }
			 
		 }
		 else{
		     if(!"".equals(conditions)){
	          sql ="select t.id,t.userId,t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,t.ywSource,(select companyName from TwoCodeCompanyInfo where id = te.ywCompanyId) as companyName,(select companyName from  TwoCodeCompanyInfo where id = tu.companyId) as companyName2 from TempYwManagerInfo  t  left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber  where  t.id =(select max(id) from TempYwManagerInfo where registNumber = t.registNumber) and "+ conditions;  	  
		      conditionsSql = "select count(*) from TempYwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber where  t.id =(select max(id) from TempYwManagerInfo where registNumber = t.registNumber) and "+ conditions;
	   		 }
	   		 else{
	   		  sql ="select t.id,t.userId,t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,t.ywSource,(select companyName from TwoCodeCompanyInfo where id = te.ywCompanyId) as companyName,(select companyName from  TwoCodeCompanyInfo where id = tu.companyId) as companyName2  from TempYwManagerInfo  t  left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber where t.id =(select max(id) from TempYwManagerInfo where registNumber = t.registNumber)";
	   		  conditionsSql = "select count(*) from TempYwManagerInfo  t where  t.id =(select max(id) from TempYwManagerInfo where registNumber = t.registNumber) ";
	   					 
	   		 }
		 }
		
		 
		 long total =TYwInfoVO.countBySql(TYwInfoVO.class, conditionsSql, null); 
		 List<TYwInfoVO> items=TYwInfoVO.findBySql(TYwInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
		
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		
	 }
    
    public View temprukuquery(String id)throws Exception{
    	 String sql ="select t.id,t.userId,t.registNumber,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,t.ywSource,tc.id as companyId,isnull(tc.companyName,'') as companyName from TempYwManagerInfo  t  left join TwoCodeUserExtInfo tu on tu.userid = t.userId  left join TwoCodeElevatorInfo te on t.registNumber=te.registNumber left join TwoCodeCompanyInfo tc  on te.ywCompanyId= tc.id where t.ywKind ='0' and t.id = ?";
	     TYwInfoVO tYwInfoVO =TYwInfoVO.findFirstBySql(TYwInfoVO.class, sql, new Object[] { id });
	     Map<String, Object> result = new HashMap<String, Object>();
	     result.put("rukuid", id);
	     int userId =0;
	     int registNumberywCompanyId =0;
	     String userName ="";
	     if(tYwInfoVO != null){
	    	 result.put("registNumber",tYwInfoVO.getRegistNumber());
	    	 result.put("companyName",tYwInfoVO.getCompanyName());
	    	 result.put("userName",tYwInfoVO.getUserName());
	   // 	 result.put("companyName2",tYwInfoVO.getCompanyName());
	    	 userId = tYwInfoVO.getUserId();
	    	 registNumberywCompanyId = tYwInfoVO.getCompanyId();
	    	 userName =tYwInfoVO.getUserName();
	     }
	     
	     if(userId>0){
	    	 UserInfoVO  userInfoVO =UserInfoVO.findFirst(UserInfoVO.class,"id =?",new Object[] { userId });
	    	 if(userInfoVO != null){
	    		 List<TCUserInfoView> tCUserInfoView =null;
	    		 String loginName=userInfoVO.getLoginName();
	    		 if(registNumberywCompanyId>0){
	    			  tCUserInfoView =TCUserInfoView.findAll(TCUserInfoView.class, "companyId = ?", new Object[]{registNumberywCompanyId});
	    			  if(tCUserInfoView != null){
	    				   Iterator<TCUserInfoView> it = tCUserInfoView.iterator();
	    				   String loginName2 ="";
	    				   String companyName2="";
	    				   while(it.hasNext()){
	    					   TCUserInfoView  eachTCUserInfoView = it.next();
	    					   loginName2 = eachTCUserInfoView.getLoginName();
	    					   companyName2 =eachTCUserInfoView.getCompanyName();
	    					   if(!"".equals(loginName2) && loginName2.equals(loginName)){
	    					   result.put("companyName2",companyName2);
	    					   return new JsonView(result);
	    					   }
	    				   }
	    			  }
	    		 }
	    		 
	    		 TCUserInfoView  tCUserInfoView2 =TCUserInfoView.findFirst(TCUserInfoView.class, "userName=?", new Object[] { userName });
	    		 if(tCUserInfoView2 != null)
	    			 result.put("companyName2",tCUserInfoView2.getCompanyName()); 
	    	 }
	     }
	     return new JsonView(result);
    }
    
    public String newtempYwRuku(String names,String r,String registNumberStr){ 
    	 System.out.println("r--"+r);
    	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 String result = "failure";
		 this.rukunames = names;
		 this.rukureason = r;
		 this.registNumberStr =registNumberStr;
		 
		 try {
			    ActiveRecordBase.beginTransaction();   
		//		ActiveRecordBase.execute("exec proc_tempYwRuku ?", new Object[] {names});
			    ActiveRecordBase.execute("exec proc_tempYwRuku2 ?,?", new Object[] {names,userName});
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
    
    public String tempYwRuku(String names){  
    	
    	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 String result = "failure";
		 this.rukunames = names;
		 try {
			    ActiveRecordBase.beginTransaction();   
		//		ActiveRecordBase.execute("exec proc_tempYwRuku ?", new Object[] {names});
			    ActiveRecordBase.execute("exec proc_tempYwRuku2 ?,?", new Object[] {names,userName});
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
    
    
    public String tempRuku(int id,String companyName){
      	 String result = "failure";
      	 TYwInfoVO tYwInfoVO =null;
		try {
			tYwInfoVO = TYwInfoVO.findFirst(TYwInfoVO.class, "id = ?", new Object[] {id});
		//	System.out.println("tYwInfoVO--->"+tYwInfoVO);
		} catch (ActiveRecordException e) {
	
			e.printStackTrace();
		}
      	 if(tYwInfoVO != null){
      		YwInfo  ywInfo = new YwInfo();
      		if("".equals(tYwInfoVO.getRegistNumber()) || tYwInfoVO.getRegistNumber() == null){
      			System.out.println("registNumber--->"+tYwInfoVO.getRegistNumber());
      			return result = "failure";
      		}
      		ywInfo.setRegistNumber(tYwInfoVO.getRegistNumber());
      		
      		if(!"".equals(companyName) && companyName !=null){
      			try {
					CompanyInfo companyInfo =CompanyInfo.findFirst(CompanyInfo.class, "companyName = ? and type ='维保'", new Object[]{companyName});
					if(companyInfo != null)
						ywInfo.setYwCompanyId(companyInfo.getId());
				} catch (ActiveRecordException e) {
					e.printStackTrace();
				}
      		}
      		
      		
      		if(tYwInfoVO.getUserId()==0) {
      			System.out.println("userId--->"+tYwInfoVO.getUserId());
      			return result = "failure";
      		}
      		else
      		   ywInfo.setUserId(tYwInfoVO.getUserId());
      		
      		if("".equals(tYwInfoVO.getYwKind())){
      			System.out.println("ywKind--->"+tYwInfoVO.getYwKind());
      			return result = "failure";
      		}
      		else
      			ywInfo.setYwKind(tYwInfoVO.getYwKind());
      		
      		if("".equals(tYwInfoVO.getStartTime())){
      			System.out.println("startTime--->"+tYwInfoVO.getStartTime());
      			return result = "failure";
      		}
      		else
      			ywInfo.setStartTime(tYwInfoVO.getStartTime());
      		
      		if("".equals(tYwInfoVO.getEndTime())){
      			System.out.println("endTime--->"+tYwInfoVO.getEndTime());
      			return result = "failure";
      		}
      		else
      			ywInfo.setEndTime(tYwInfoVO.getEndTime());
      		
      		if("".equals(tYwInfoVO.getsPosition())){
      			System.out.println("sPosition--->"+tYwInfoVO.getsPosition());
      			return result = "failure";
      		}
      		else
      			ywInfo.setsPosition(tYwInfoVO.getsPosition());
      		
      		if("".equals(tYwInfoVO.getePosition())){
      			System.out.println("ePosition--->"+tYwInfoVO.getePosition());
      			return result = "failure";
      		}
      		else
      			ywInfo.setePosition(tYwInfoVO.getePosition());
      		
      		if("".equals(tYwInfoVO.getMaintainTypecode())){
      			System.out.println("maintainTypecode--- >"+tYwInfoVO.getMaintainTypecode());
      			return result = "failure";
      		}
      		else
      			ywInfo.setMaintainTypecode(tYwInfoVO.getMaintainTypecode());
      		
      		/*
      		if("".equals(tYwInfoVO.getRemark())){
      			System.out.println("remark--->"+tYwInfoVO.getRemark());
      			return result = "failure";
      		}
      		else */
      			ywInfo.setRemark(tYwInfoVO.getRemark());
      		
      		
      		if("".equals(tYwInfoVO.getThreedscanning()) || tYwInfoVO.getThreedscanning() == null){
      			System.out.println("threedscanning--->"+tYwInfoVO.getThreedscanning());
      			return result = "failure";
      		}
      		else
      			ywInfo.setThreedscanning(tYwInfoVO.getThreedscanning());

      		if("".equals(tYwInfoVO.getMap_X0()) || tYwInfoVO.getMap_X0() == null){
      			System.out.println("map_X0--->"+tYwInfoVO.getMap_X0());
      			return result = "failure";
      		}
      		else
      			ywInfo.setMap_X0(tYwInfoVO.getMap_X0());
      		
      		if("".equals(tYwInfoVO.getMap_Y0()) || tYwInfoVO.getMap_Y0() == null){
      			System.out.println("map_Y0--->"+tYwInfoVO.getMap_Y0());
      			return result = "failure";
      		}
      		else
      			ywInfo.setMap_Y0(tYwInfoVO.getMap_Y0());
      		

      		if("".equals(tYwInfoVO.getMap_X1()) || tYwInfoVO.getMap_X1() == null){
      			System.out.println("map_X1--->"+tYwInfoVO.getMap_X1());
      			return result = "failure";
      			}
      		else
      			ywInfo.setMap_X1(tYwInfoVO.getMap_X1());
      		
      		if("".equals(tYwInfoVO.getMap_Y1()) || tYwInfoVO.getMap_Y1() == null){
      			System.out.println("map_Y1--->"+tYwInfoVO.getMap_Y1());
      			return result = "failure";
      		}
      		else
      			ywInfo.setMap_Y1(tYwInfoVO.getMap_Y1());
      		
      		if("".equals(tYwInfoVO.getMap_X2()) || tYwInfoVO.getMap_X2() == null){
      			System.out.println("map_X2--->"+tYwInfoVO.getMap_X2());
      			return result = "failure";
      			}
      		else
      			ywInfo.setMap_X2(tYwInfoVO.getMap_X2());
      		
      		if("".equals(tYwInfoVO.getMap_Y2()) || tYwInfoVO.getMap_Y2() == null){
      			System.out.println("map_Y2--->"+tYwInfoVO.getMap_Y2());
      			return result = "failure";
      		}
      		else
      			ywInfo.setMap_Y2(tYwInfoVO.getMap_Y2());
      		
      	    try {
				ActiveRecordBase.beginTransaction();
      		    result =add(ywInfo);
      		    System.out.println("temrukuResult--->"+result);
      		if("success".equals(result)){
      			try {
					if(tYwInfoVO.destroy()>0)
						result ="success";
				} catch (ActiveRecordException e) {
					e.printStackTrace();
				}
      		}
      		 ActiveRecordBase.commit();
      	    }
      	  catch (TransactionException e) {
      		try {
				  ActiveRecordBase.rollback();
				} catch (TransactionException et) {
					et.printStackTrace();
				}
			}
      		
      	 }
      	 
      	 
      		 
      	 return result;
    }
    
    public View ywloglist(int page, int rows) throws Exception{
		 
	  
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 long total=0;
		 List<YwlogVO> items = null;
		 
		 java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String s = format1.format(new Date()); 
		 
	     if(role==2 || role==1){ //系统管理员  
	     //	 total  =YwInfoVO.count(YwInfoVO.class, null, null);
	    	 
	    	if(s.compareTo(GlobalFunction.ywlogTimePoint) < 0){     //日志数量还不多，不需要分表查询
	    	 total =YwlogVO.countBySql(YwlogVO.class, "select count(*) from TwoCodeYwlog", null);
	    //	 items = ElevaltorInfoVO.findAll(ElevaltorInfoVO.class, null, null, null, rows, (page-1)*rows);
	    	 String sql ="select * from TwoCodeYwlog";
	    	 items = YwlogVO.findBySql(YwlogVO.class, sql, null, "id desc", rows, (page-1)*rows);
	    	 }
	    	else{                  //日志数量多，需要分表来查
	    	  total =YwlogVO.countBySql(YwlogVO.class, " select COUNT(*) from (select * from TwoCodeHisYwlog  where subTime > dateadd(month,-3,GETDATE()) union select * from TwoCodeYwlog) t", null);	
	    	  String sql ="select t.* from (select * from TwoCodeHisYwlog  where subTime > dateadd(month,-3,GETDATE()) union select * from TwoCodeYwlog) t";
		      items = YwlogVO.findBySql(YwlogVO.class, sql, null, "id desc", rows, (page-1)*rows);
	    	}
	     }
	     
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
    
    public View ywlogquery(YwlogVO info,int page, int rows)throws Exception{
	//	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String userName ="";
		 String context ="";
	//	 int  role = userinfo.getRole();
		// System.out.println("ElevatorController---query---登陆名称---"+userName);
		// System.out.println("ElevatorController---query---role---"+role);
		 
		
	//	 userName ="%"+info.getUserName()+"%";
		 userName =info.getUserName();
		 context = info.getContext();
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 long total=0;
		 List<YwlogVO>  items =null;
		 if(!"".equals(userName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.userName like '%"+userName+"%'";	
				} 
				else{
				 conditions =" t.userName like '%"+userName+"%'";	
				}
			 
		 }
		 if(!"".equals(context)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.context like '%"+context+"%'";	
				} 
				else{
				 conditions =" t.context like '%"+context+"%'";	
				}
			 
		 }
		 
		 java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String s = format1.format(new Date()); 
		 
		 if(!"".equals(conditions)){
			 if(s.compareTo(GlobalFunction.ywlogTimePoint) < 0){     //日志数量还不多，不需要分表查询
			 sql ="select id,userName,context,subTime from TwoCodeYwlog t where  "+ conditions;
			 conditionsSql="select count(1) from TwoCodeYwlog t where "+conditions;
			 total =YwlogVO.countBySql(YwlogVO.class, conditionsSql, null);
		   	 items = YwlogVO.findBySql(YwlogVO.class, sql, null, "id desc", rows, (page-1)*rows);
			 }
			 else{
				 sql ="select id,userName,context,subTime  from (select * from TwoCodeHisYwlog  where subTime > dateadd(month,-3,GETDATE()) union select * from TwoCodeYwlog)  t where  "+ conditions;
				 conditionsSql="select count(1) from (select * from TwoCodeHisYwlog  where subTime > dateadd(month,-3,GETDATE()) union select * from TwoCodeYwlog)  t where "+conditions;
				 total =YwlogVO.countBySql(YwlogVO.class, conditionsSql, null);
			   	 items = YwlogVO.findBySql(YwlogVO.class, sql, null, "id desc", rows, (page-1)*rows);
			 }
		 }
		 else{ 
			 if(s.compareTo(GlobalFunction.ywlogTimePoint) < 0){     //日志数量还不多，不需要分表查询
			 sql ="select id,userName,context,subTime from TwoCodeYwlog t ";
			 conditionsSql="select count(1) from TwoCodeYwlog t  ";
			 total =YwlogVO.countBySql(YwlogVO.class, conditionsSql, null);
		   	 items = YwlogVO.findBySql(YwlogVO.class, sql, null, "id desc", rows, (page-1)*rows);
			 }
			 else{
				 sql ="select id,userName,context,subTime from (select * from TwoCodeHisYwlog  where subTime > dateadd(month,-3,GETDATE()) union select * from TwoCodeYwlog)  t ";
				 conditionsSql="select count(1) from (select * from TwoCodeHisYwlog  where subTime > dateadd(month,-3,GETDATE()) union select * from TwoCodeYwlog)  t  ";
				 total =YwlogVO.countBySql(YwlogVO.class, conditionsSql, null);
			   	 items = YwlogVO.findBySql(YwlogVO.class, sql, null, "id desc", rows, (page-1)*rows); 
			 }
		 }
		 
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	 }
    
    public View ywtj(int id){
    //	YwInfoVO myYwInfoVO =null;
    	 Map<String, Object> obj = new HashMap<String, Object>();
    //	String sql ="select te.map_x,te.map_y,t.map_X0,t.map_Y0,t.map_X1,t.map_Y1,t.map_X2,t.map_Y2 from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber where t.id = ?";
    //	try {
	//		myYwInfoVO =YwInfoVO.findFirstBySql(YwInfoVO.class, sql, new Object[] {id});
	//		if(myYwInfoVO != null){
				obj.put("xcb",35);
				obj.put("wlb",25);
				obj.put("cwb", 25);
				obj.put("jsb", 15);
				
	//		}
	//	} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	}
        return new JsonView(obj);
    }
    
    public View lkywlist(int userId,int page, int rows) throws Exception{
    	  long total=0;
    	  List<YwInfoVO> items = null;
    	  Map<String, Object> result = new HashMap<String, Object>();
    	  total =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from YwManagerInfo where userId = ? and   datediff(day,CONVERT(Datetime,startTime,120),getdate()) < 31 and  ywResult<=50", new Object[] { userId });
    	  String sql ="select t.id,t.registNumber,isnull(te.address,'') as address,t.ywResult,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when t.ePosition = '0' then '轿厢' else '机房' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,tu.userid as userId,isnull(tc.companyName,'') as companyName from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.ywKind ='0' and t.userId = ? and   datediff(day,CONVERT(Datetime,t.startTime,120),getdate()) < 31 and t.ywResult<=50 ";
	      items = YwInfoVO.findBySql(YwInfoVO.class, sql, new Object[]{userId}, "t.id desc", rows, (page-1)*rows);	
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
    }
    
    public View  csywlist(String registNumber,String beginTime,int page, int rows) throws Exception{
	    /*
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 System.out.println("ElevatorController---34---登陆名称---"+userName);
		 System.out.println("ElevatorController---36---role---"+role);
		 */
		 long total=0;
		 List<YwInfoVO> items = null;
		 if("".equals(beginTime)){
	     total =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from YwManagerInfo where ywKind = '0' and datediff(month,subTime,getdate()) <= 3 and registNumber=? ", new Object[]{registNumber});
	//   String sql ="select t.id,t.registNumber,isnull(te.address,'') as address,t.ywResult,t.flexStartx,t.flexStarty,t.flexEndx,t.flexEndy,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when t.ePosition = '0' then '轿厢' else '机房' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,tu.userid as userId,isnull(tc.companyName,'') as companyName from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.ywKind ='0' and datediff(month,t.subTime,getdate()) <= 3 and t.registNumber =?";
	     String sql ="select t.id,t.registNumber,te.address,t.ywResult,t.flexStartx,t.flexStarty,t.flexEndx,t.flexEndy,t.maintainTypecode,t.startTime,t.endTime,t.subTime, t.startTime,t.endTime,t.ywstatus,t.picNum,tu.userName,tu.userId,tc.companyName from hisYwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.ywKind ='0' and datediff(month,t.endTime,getdate()) <= 3 and t.registNumber =?";
	     items = YwInfoVO.findBySql(YwInfoVO.class, sql, new Object[]{registNumber}, "t.id desc", rows, (page-1)*rows);  
		 }
		 else{
			 total =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from YwManagerInfo where ywKind = '0' and datediff(month,subTime,getdate()) <= 3 and registNumber=? and subTime >= ?", new Object[]{registNumber,beginTime});
		//	 String sql ="select t.id,t.registNumber,isnull(te.address,'') as address,t.ywResult,t.flexStartx,t.flexStarty,t.flexEndx,t.flexEndy,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when t.ePosition = '0' then '轿厢' else '机房' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,tu.userid as userId,isnull(tc.companyName,'') as companyName from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.ywKind ='0' and datediff(month,t.subTime,getdate()) <= 3 and t.registNumber =? and t.subTime >= ?";
			 String sql ="select t.id,t.registNumber,te.address,t.ywResult,t.flexStartx,t.flexStarty,t.flexEndx,t.flexEndy,t.maintainTypecode,t.startTime,t.endTime,t.subTime, t.startTime,t.endTime,t.ywstatus,t.picNum,tu.userName,tu.userId,tc.companyName from hisYwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.ywKind ='0' and datediff(month,t.endTime,getdate()) <= 3 and t.registNumber =? and t.endTime >= ?";		
			 items = YwInfoVO.findBySql(YwInfoVO.class, sql, new Object[]{registNumber,beginTime}, "t.id desc", rows, (page-1)*rows);  
				 
		 }  
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  
		  return new JsonView(result);
	    }
    
    public View csquery(YwInfoVO info,int page, int rows)throws Exception{
       String registNumber ="";
       String qstartTime ="";
	   String qendTime="";
	   String beginTime="";
	   registNumber =info.getRegistNumber();
	   qstartTime =info.getQstartTime();
	   qendTime = info.getQendTime();
	   beginTime=info.getBeginTime();
	   String conditions="";
	   Object[] param=null;
	   if("".equals(beginTime)){
	   conditions ="t.registNumber = ? and datediff(month,t.subTime,getdate()) <= 3";
	   param = new Object[]{registNumber};
	   }
	   else{
	   conditions ="t.registNumber = ? and datediff(month,t.subTime,getdate()) <= 3 and t.subTime >= ?";   
	   param = new Object[]{registNumber,beginTime};
	   }
	   String sql="";
	   List<YwInfoVO>  items2 =null;
	   List<YwInfoVO>  items =null;
	   if("".equals(qstartTime) && "".equals(qendTime))
	   sql ="select t.id,t.registNumber,t.ywResult,t.flexStartx,t.flexStarty,t.flexEndx,t.flexEndy,isnull(te.address,'') as address,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when t.ePosition = '0' then '轿厢' else '机房' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,isnull(tc.companyName,'') as companyName from hisYwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId where "+conditions;
	   if("".equals(qstartTime) && !"".equals(qendTime))
	   sql ="select t.id,t.registNumber,t.ywResult,t.flexStartx,t.flexStarty,t.flexEndx,t.flexEndy,isnull(te.address,'') as address,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when t.ePosition = '0' then '轿厢' else '机房' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,isnull(tc.companyName,'') as companyName from hisYwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId where left(t.startTime,10) <= '"+qendTime+"' and "+conditions;
	   if(!"".equals(qstartTime) && "".equals(qendTime))
	    sql ="select t.id,t.registNumber,t.ywResult,t.flexStartx,t.flexStarty,t.flexEndx,t.flexEndy,isnull(te.address,'') as address,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when t.ePosition = '0' then '轿厢' else '机房' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,isnull(tc.companyName,'') as companyName from hisYwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId where left(t.startTime,10) >= '"+qstartTime+"' and "+conditions;
		if(!"".equals(qstartTime) && !"".equals(qendTime))
		sql ="select t.id,t.registNumber,t.ywResult,t.flexStartx,t.flexStarty,t.flexEndx,t.flexEndy,isnull(te.address,'') as address,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '轿厢' else '机房' end) as sPosition, (case when t.ePosition = '0' then '轿厢' else '机房' end) as ePosition,t.ywstatus,t.picNum,isnull(tu.userName,'')as userName,isnull(tc.companyName,'') as companyName from hisYwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId where left(t.startTime,10) >= '"+qstartTime+"' and left(t.startTime,10) <= '"+qendTime+"' and "+conditions;
		items2 = YwInfoVO.findBySql(YwInfoVO.class, sql, param, null);
		items = YwInfoVO.findBySql(YwInfoVO.class, sql, param, "t.id desc", rows, (page-1)*rows);
		Map<String, Object> result = new HashMap<String, Object>();
		if(items2!=null)
		  result.put("total", items2.size());
		else
		  result.put("total", 0);
		  result.put("rows", items);
		  return new JsonView(result);	
    }
    
    protected boolean  ywlistlog(){
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
				twoCodeLogInfo.setLogAction("维保");
				twoCodeLogInfo.setLogContext("维保记录");
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
    
    protected boolean newtempYwRukulog(){
    	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
    	 String userName =userinfo.getLoginName();
    	 if("success".equals(this.rukuresult)){
    	 java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
    	 String s = format1.format(new Date()); 
    	 YwlogVO rukuLog  = new  YwlogVO();
    	 rukuLog.setUserName(userName);
    	 rukuLog.setContext(userName+"入库:("+this.registNumberStr+")"+"<br>"+"入库根据:"+this.rukureason);
    	 rukuLog.setCmd("rk");
    	 rukuLog.setSubTime(s);
    	 try {
			rukuLog.save();	
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
    	 }
    	 return true;
    		 
    }
	 
    protected boolean  nywcqlistlog(){
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
				twoCodeLogInfo.setLogAction("维保");
				twoCodeLogInfo.setLogContext("当前超期");
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
    
    protected boolean  ywstatisticslistlog(){
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
				twoCodeLogInfo.setLogAction("维保");
				twoCodeLogInfo.setLogContext("运维统计");
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
    
    public View pcloglist(int page, int rows){
		   
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 long total=0;
		 List<TwoCodePCLogInfoVO> items = null;
		 try {
	     if(role==2 || role==1){ //系统管理员  
			 total =TwoCodePCLogInfoVO.countBySql(TwoCodePCLogInfoVO.class, "select count(*) from TwoCodePCloginfo  ", null);
	    	 String sql ="select t.logPerson,t.logTime,t.logContext,t.beginTime,t.endTime,tc.companyName as logPersonCompany from TwoCodePCloginfo  t  left join tcuserinfo tc on  t.logPerson = tc.loginName  ";
	    	 items = TwoCodePCLogInfoVO.findBySql(TwoCodePCLogInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
	    	
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
    
    public View pclogquery(YwlogVO info,int page, int rows)throws Exception{
    	//	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
    		 String userName ="";
    	//	 int  role = userinfo.getRole();
    		// System.out.println("ElevatorController---query---登陆名称---"+userName);
    		// System.out.println("ElevatorController---query---role---"+role);
    		 
    		
    		 userName ="%"+info.getUserName()+"%";
    		 long total=0;
    		 
    		 
    		 total =TwoCodePCLogInfoVO.countBySql(TwoCodePCLogInfoVO.class, "select count(*) from TwoCodePCloginfo where logPerson like ?", new Object[]{userName});
    	   	 List<TwoCodePCLogInfoVO>  items = TwoCodePCLogInfoVO.findBySql(TwoCodePCLogInfoVO.class, "select t.logPerson,t.logTime,t.logContext,t.beginTime,t.endTime,tc.companyName as logPersonCompany from TwoCodePCloginfo  t  left join tcuserinfo tc on  t.logPerson = tc.loginName  where t.logPerson like ?", new Object[]{userName}, "t.id desc", rows, (page-1)*rows);
    		
    		 Map<String, Object> result = new HashMap<String, Object>();
    		 result.put("total", total);
    		 result.put("rows", items);
    		 return new JsonView(result);
    	 }
    
    public View pcywsofteffInitial(int id){
    	String sql ="select ywCompanyId,effective,begintime,daytime from TwoCodeYWEffective where ywCompanyid =? ";
    	TwoCodePCYwEffectiveVO  twoCodePCYwEffectiveVO = null;
    	Map<String, Object> result = new HashMap<String, Object>();
    	try {
			  twoCodePCYwEffectiveVO =TwoCodePCYwEffectiveVO.findFirstBySql(TwoCodePCYwEffectiveVO.class, sql,new Object[] { id });
			  if(twoCodePCYwEffectiveVO != null){
				  result.put("effective", twoCodePCYwEffectiveVO.getEffective());
				  result.put("begintime", twoCodePCYwEffectiveVO.getBegintime());
				  result.put("daytime", twoCodePCYwEffectiveVO.getDaytime());
			  }
			  else{
				  result.put("effective", 0);
				  result.put("begintime", "");
				  result.put("daytime", 60); 
			  }
			
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	 return new JsonView(result);
    }
    
    public String pcywsoftupdate(TwoCodePCYwEffectiveVO twoCodePCYwEffectiveVO){
    	String result = "failure";
    	int num = 0;
    	try {
    		ActiveRecordBase.beginTransaction();
			num = TwoCodePCYwEffectiveVO.updateAll(TwoCodePCYwEffectiveVO.class, "effective = ?,begintime=?,daytime=? ", new Object[]{twoCodePCYwEffectiveVO.getEffective(),twoCodePCYwEffectiveVO.getBegintime(),twoCodePCYwEffectiveVO.getDaytime()}, "ywCompanyid = ? ", new Object[]{twoCodePCYwEffectiveVO.getYwCompanyIdinfo2()});
			
		    if(num < 1){
		    	TwoCodePCYwEffective  twoCodePCYwEffective  = new TwoCodePCYwEffective();
		    	twoCodePCYwEffective.setBegintime(twoCodePCYwEffectiveVO.getBegintime());
		    	twoCodePCYwEffective.setDaytime(twoCodePCYwEffectiveVO.getDaytime());
		    	twoCodePCYwEffective.setEffective(twoCodePCYwEffectiveVO.getEffective());
		    	twoCodePCYwEffective.setYwCompanyId(twoCodePCYwEffectiveVO.getYwCompanyIdinfo2());
		    	if(twoCodePCYwEffective.save()){
		    		 result ="success";	
		    	}
		    }
		    else{
		    	result ="success";
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
    
    public View ywranking(){
    	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
    	 long total=0;
		 List<YwRankingInfoVO> items = null;
		 
		 int companyId = 0;
	//	 List<ImageVO>  items = ImageVO.findBySql(ImageVO.class, "exec 按用户获取图片信息  ?", new Object[] { "test" });  
 		 try {
 			if(role == 11 || role == 10){
 		    UserExtInfo user =UserExtInfo.findFirstBySql(UserExtInfo.class, "select companyId from TwoCodeUserExtInfo where userid= ?",new Object[] {userid});
 			if(user != null)
 				companyId = user.getCompanyid();
 		    items =YwRankingInfoVO.findBySql(YwRankingInfoVO.class, "exec pro_MaintainEnterpriseScoreByArea ?",new Object[] {companyId});	
 			}
 			else{
			items =YwRankingInfoVO.findBySql(YwRankingInfoVO.class, "exec pro_MaintainEnterpriseScore");
 			}
			if(items != null)
				total = items.size();
			
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	      Map<String, Object> result = new HashMap<String, Object>();
	   //   result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
    }
    
    public View qsywranking(){
   	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
   	 long total=0;
		 List<YwRankingInfoVO> items = null;
		 
		 int companyId = 0;  
		 try {
			
			items =YwRankingInfoVO.findBySql(YwRankingInfoVO.class, "exec pro_MaintainEnterpriseScore");
			
			if(items != null)
				total = items.size();
			
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	      Map<String, Object> result = new HashMap<String, Object>();
	   //   result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
   }
    
    public View ywranking2(int sv){
     UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
	 int userid = userinfo.getId();
	 String userName =userinfo.getLoginName();
	 int  role = userinfo.getRole();
	 List<YwRankingInfoVO> items = null;
	 int companyId = 0;
		 try {
			 if(role == 11 || role == 10){
		 		    UserExtInfo user =UserExtInfo.findFirstBySql(UserExtInfo.class, "select companyId from TwoCodeUserExtInfo where userid= ?",new Object[] {userid});
		 			if(user != null)
		 				companyId = user.getCompanyid();
		 		    items =YwRankingInfoVO.findBySql(YwRankingInfoVO.class, "exec pro_MaintainEnterpriseScoreByAreaYwCount ?,?",new Object[] {sv,companyId});	
		 			}
		 			else{
			items =YwRankingInfoVO.findBySql(YwRankingInfoVO.class, "exec pro_MaintainEnterpriseScoreByYwCount ?", new Object[] {sv});
		 			}
			
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	      Map<String, Object> result = new HashMap<String, Object>();
	  
		  result.put("rows", items);
		  return new JsonView(result);
   }
    
   
    
    public View qsywranking2(int sv){
        UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
   	 int userid = userinfo.getId();
   	 String userName =userinfo.getLoginName();
   	 int  role = userinfo.getRole();
   	 List<YwRankingInfoVO> items = null;
   	 int companyId = 0;
   		 try {		
   			items =YwRankingInfoVO.findBySql(YwRankingInfoVO.class, "exec pro_MaintainEnterpriseScoreByYwCount ?", new Object[] {sv});
   		 			
   		} catch (ActiveRecordException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   	  
   	      Map<String, Object> result = new HashMap<String, Object>();
   	  
   		  result.put("rows", items);
   		  return new JsonView(result);
      }
    
    public View ywRankingtjtime(){
		 Map<String, Object> result = new HashMap<String, Object>();
		 String tjtime="";
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
		  tjtime =df.format(new Date());// new Date()为获取当前系统时间
		 result.put("tjtime",tjtime);
		 return new JsonView(result);
	 } 
    
    public View ywrankingByCompanyName(YwRankingInfoVO info ){
    	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
    	 int userid = userinfo.getId();
    	 String userName =userinfo.getLoginName();
    	 int  role = userinfo.getRole();
   	     long total=0;
   	     
   	     int zjcompanyId = 0;
   	     int companyId =0;
   	     companyId =info.getYwCompanyId();
		 List<YwRankingInfoVO> items = null;
	     
		 try {
			if(companyId > 0){
				if(role == 11 || role == 10){
		 		    UserExtInfo user =UserExtInfo.findFirstBySql(UserExtInfo.class, "select companyId from TwoCodeUserExtInfo where userid= ?",new Object[] {userid});
		 			if(user != null)
		 				zjcompanyId = user.getCompanyid();
		 		    items =YwRankingInfoVO.findBySql(YwRankingInfoVO.class, "exec pro_MaintainEnterpriseScoreByAreaYwName ?,?",new Object[] {companyId,zjcompanyId});	
		 			}
		 			else{	
			         items =YwRankingInfoVO.findBySql(YwRankingInfoVO.class, "exec pro_MaintainEnterpriseScoreByYwName ?", new Object[] {companyId}); }
		 }
			else{ 
				if(role == 11 || role == 10){
		 		    UserExtInfo user =UserExtInfo.findFirstBySql(UserExtInfo.class, "select companyId from TwoCodeUserExtInfo where userid= ?",new Object[] {userid});
		 			if(user != null)
		 				zjcompanyId = user.getCompanyid();
		 		    items =YwRankingInfoVO.findBySql(YwRankingInfoVO.class, "exec MaintainEnterpriseScoreByArea ?",new Object[] {zjcompanyId});	
		 			}
		 			else{	
				items =YwRankingInfoVO.findBySql(YwRankingInfoVO.class, "exec pro_MaintainEnterpriseScore");
		 			}
			}
			if(items != null)
				total = items.size();
			
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	      Map<String, Object> result = new HashMap<String, Object>();
	   //   result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
   }
    
    public View qsywrankingByCompanyName(YwRankingInfoVO info ){
   	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
   	 int userid = userinfo.getId();
   	 String userName =userinfo.getLoginName();
   	 int  role = userinfo.getRole();
  	     long total=0;
  	     
  	     int zjcompanyId = 0;
  	     int companyId =0;
  	     companyId =info.getYwCompanyId();
		 List<YwRankingInfoVO> items = null;
	     
		 try {
			if(companyId > 0){
			         items =YwRankingInfoVO.findBySql(YwRankingInfoVO.class, "exec pro_MaintainEnterpriseScoreByYwName ?", new Object[] {companyId}); 
			         }
		 
			else{ 
				
				items =YwRankingInfoVO.findBySql(YwRankingInfoVO.class, "exec pro_MaintainEnterpriseScore");
		 		
			}
			if(items != null)
				total = items.size();
			
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	      Map<String, Object> result = new HashMap<String, Object>();
	   //   result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
  }
    
    /*
    public String ywstatexportExcel()
    {   
   	 String result = "failure"; 
   	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 List<YwstatisticsVO> items = new ArrayList<YwstatisticsVO>();
		 String sql = "";
	     sql ="select t2.*, -1 as uncqetotal,isnull(tc.companyName,'') as companyName,isnull(t4.utotal,0) as utotal,isnull(t6.nutotal,0) as nutotal,(select  count(distinct t.registNumber)  from TCYEUView  t where t.ywcompanyid  = t2.ywcompanyid and dailingflag =0 ) as eywtotal,(select count(t1.registnumber)  from YwManagerInfo t1 left join twoCodeelevatorinfo te on t1.registNumber =  te.registNumber  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 and te.dailingFlag = 0  and  te.ywcompanyid =t2.ywcompanyid ) as ncqetotal  from  (select count(*) as etotal,sum(case when t.dailingFlag = 1 then 1 else 0 end) as dailingetotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 and t.isinvalid = 0 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t5.userid) as nutotal, t5.companyid from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role =18) t5 group by t5.companyid) t6 on  t4.companyid =t6.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id ";
			
		 try { 
			 items = YwstatisticsVO.findBySql(YwstatisticsVO.class, sql, null,null);
			 Iterator<YwstatisticsVO> it = items.iterator();
			 while(it.hasNext()){
				 YwstatisticsVO   ywvo =it.next();
				 int eywtotal = ywvo.getEywtotal();
				 int nutotal =  ywvo.getNutotal();
				 ywvo.setUncqetotal(ywvo.getEtotal()-ywvo.getEywtotal()-ywvo.getDailingetotal());
				 if(eywtotal > 0 && nutotal > 0)
					 ywvo.setYwcountbyperson(String.format("%10.2f",eywtotal*1.0/nutotal));	
			//		 ywvo.setYwcountbyperson(String.valueOf(eywtotal/nutotal));	 
				 else
					 ywvo.setYwcountbyperson("0.0");
			 }
			 String[] hearders = new String[] {"维保单位","电梯数量", "运维电梯数", "停用电梯数", "未运维电梯数", "当前超期数", "总维保人员数","在维保人员数","人均维保数"};//表头数组 
			 YwstatExportExcel<YwstatisticsVO> ex = new YwstatExportExcel<YwstatisticsVO>(); 
			 SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");  
			 String filename = "运维统计表_"+timeFormat.format(new Date())+".xls";  
			 response.setContentType("application/ms-excel;charset=UTF-8");  
			 response.setHeader("Content-Disposition", "attachment;filename="  
			            .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
			 OutputStream out = response.getOutputStream();  
		     ex.exportExcel(hearders, items, out);  
		//	 ex.exportExcel(tjtime,hearders, items, out);
		     out.close(); 
		   //这一行非常关键，否则在实际中有可能出现莫名其妙的问题！！！
		     response.flushBuffer();//强行将响应缓存中的内容发送到目的地 
		     System.out.println("excel导出成功！"); 
		     
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
    }  */
    
    public String ywstatexportExcel(YwInfoVO info)
    {    String result = "failure"; 
    	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 int zjcompanyId2 =0;  //非查询用的，是登陆过程中系统后台根据role不同展示不同列表用的
		 int ywCompanyId =0;
		 String areaName="";
		 String qstartTime ="";
		 String qendTime="";
		 
		 ywCompanyId =info.getCompanyId();
		 areaName =info.getArea();
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 String conditions2="";
		 
		 List<YwstatisticsVO> items = new ArrayList<YwstatisticsVO>();
	
	 
		 long total=0;
		 UserInfoVO userInfoVO =null;
		 if(role==10 || role==11){    
				try {
					userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
				} catch (ActiveRecordException e) {
					e.printStackTrace();
				}
		         if(userInfoVO != null){
		             zjcompanyId2 = userInfoVO.getCompanyId();
		             conditions = " t.zjcompanyId = "+zjcompanyId2;
		            
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
		 
		 if(!"".equals(areaName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.area like '%"+areaName+"%'";	
				} 
				else{
					conditions =" t.area like '%"+areaName+"%'";	
				}
			
			
		 }
		 
		 if(!"".equals(qstartTime)){
			 if(!"".equals(conditions2)){
				 conditions2 =conditions2+" and convert(datetime,endTime,120) >= '"+qstartTime+"'";	
				} 
				else{
					conditions2 =" convert(datetime,endTime,120) >= '"+qstartTime+"'";	
				}
			
			
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions2)){
				 conditions2 =conditions2+" and convert(datetime,endTime,120) <= '"+qendTime+"'";	
				} 
				else{
					conditions2 =" convert(datetime,endTime,120) <= '"+qendTime+"'";	
				}
			
			
		 }
		 
		 if(!"".equals(conditions)){
			  if(!"".equals(conditions2)){
			//  sql ="select t2.*,tc.companyName,isnull(t7.ncqetotal,0) as ncqetotal,isnull(t4.utotal,0) as utotal,isnull(t8.nutotal,0) as nutotal,(select  count(t.registNumber)  from TwoCodeElevatorInfo  t left join (select * from ywmanagerinfo where  " + conditions2+ " ) y  on t.registNumber = y.registNumber where   t.ywcompanyid = t2.ywcompanyid and y.registNumber is null  ) as uncqetotal  from  (select count(*) as etotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t where "+ conditions + " group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t6.userId) as nutotal,t6.companyId from (select tyce.userId,tc.companyId from (select  y.userid,max(y.endTime) as endTime  from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role=18   ) tyc  left join ywmanagerinfo y on tyc.userid = y.userid left join TwoCodeElevatorInfo  t on y.registNumber = t.registNumber where "+conditions + " group by y.userid  ) tyce left join tcuserinfo tc on tyce.userid = tc.id  ) t6 group by t6.companyId) t8 on  t4.companyid =t8.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id left join (select count(t3.registNumber) as ncqetotal,t.ywcompanyId from (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo where "+conditions2+" group by registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  inner join TwoCodeElevatorInfo  t on t.registNumber = t3.registNumber where "+conditions + " group  by ywcompanyid) t7  on t2.ywcompanyid =t7.ywcompanyId ";
				  sql ="select t2.*,-1 as uncqetotal,isnull(tc.companyName,'') as  companyName,isnull(t7.ncqetotal,0) as ncqetotal,isnull(t4.utotal,0) as utotal,isnull(t8.nutotal,0) as nutotal, (select  count(distinct t.registNumber)  from TCYEUView  t where " + conditions2+ " and t.dailingFlag = 0 and t.ywcompanyid  = t2.ywcompanyid and "+ conditions+" )  as eywtotal  from  (select count(*) as etotal,sum(case when t.dailingFlag = 1 then 1 else 0 end) as dailingetotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t where "+ conditions + " group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 and t.isinvalid = 0 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t6.userId) as nutotal,t6.companyId from (select tyce.userId,tc.companyId from (select  y.userid,max(y.endTime) as endTime  from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role=18   ) tyc  left join ywmanagerinfo y on tyc.userid = y.userid left join TwoCodeElevatorInfo  t on y.registNumber = t.registNumber where "+conditions + " group by y.userid  ) tyce left join tcuserinfo tc on tyce.userid = tc.id  ) t6 group by t6.companyId) t8 on  t4.companyid =t8.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id left join (select count(t3.registNumber) as ncqetotal,t.ywcompanyId from (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo where "+conditions2+" group by registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  inner join TwoCodeElevatorInfo  t on t.registNumber = t3.registNumber where t.dailingFlag  = 0  and "+conditions + " group  by ywcompanyid) t7  on t2.ywcompanyid =t7.ywcompanyId ";
				   	  
			   }else{
			   //  sql ="select t2.*,tc.companyName,isnull(t7.ncqetotal,0) as ncqetotal,isnull(t4.utotal,0) as utotal,isnull(t8.nutotal,0) as nutotal,(select  count(t.registNumber)  from TwoCodeElevatorInfo  t left join ywmanagerinfo y  on t.registNumber = y.registNumber where  t.ywcompanyid = t2.ywcompanyid and y.registNumber is null  ) as uncqetotal  from  (select count(*) as etotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t where "+ conditions + " group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t6.userId) as nutotal,t6.companyId from (select tyce.userId,tc.companyId from (select  y.userid,max(y.endTime) as endTime  from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role=18   ) tyc  left join ywmanagerinfo y on tyc.userid = y.userid left join TwoCodeElevatorInfo  t on y.registNumber = t.registNumber where "+conditions + " group by y.userid  ) tyce left join tcuserinfo tc on tyce.userid = tc.id  ) t6 group by t6.companyId) t8 on  t4.companyid =t8.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id left join (select count(t3.registNumber) as ncqetotal,t.ywcompanyId from (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  inner join TwoCodeElevatorInfo  t on t.registNumber = t3.registNumber where "+conditions + " group  by ywcompanyid) t7  on t2.ywcompanyid =t7.ywcompanyId ";
				   sql ="select t2.*,-1 as uncqetotal,isnull(tc.companyName,'') as  companyName,isnull(t7.ncqetotal,0) as ncqetotal,isnull(t4.utotal,0) as utotal,isnull(t8.nutotal,0) as nutotal,(select  count(distinct t.registNumber)  from TCYEUView  t where t.dailingFlag = 0 and t.ywcompanyid  = t2.ywcompanyid and "+ conditions+" )  as eywtotal  from  (select count(*) as etotal,sum(case when t.dailingFlag = 1 then 1 else 0 end) as dailingetotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t where "+ conditions + " group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 and t.isinvalid = 0 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t6.userId) as nutotal,t6.companyId from (select tyce.userId,tc.companyId from (select  y.userid,max(y.endTime) as endTime  from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role=18   ) tyc  left join ywmanagerinfo y on tyc.userid = y.userid left join TwoCodeElevatorInfo  t on y.registNumber = t.registNumber where "+conditions + " group by y.userid  ) tyce left join tcuserinfo tc on tyce.userid = tc.id  ) t6 group by t6.companyId) t8 on  t4.companyid =t8.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id left join (select count(t3.registNumber) as ncqetotal,t.ywcompanyId from (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  inner join TwoCodeElevatorInfo  t on t.registNumber = t3.registNumber where  t.dailingFlag  = 0 and "+conditions + " group  by ywcompanyid) t7  on t2.ywcompanyid =t7.ywcompanyId ";
				      
			   }
		//	  conditionsSql = "select count(*) from  (select t.ywcompanyId from TwoCodeElevatorInfo  t where  t.dailingFlag  = 0 and " +conditions + " group by t.ywcompanyId) t2 ";
			  }
		 else{
			 if(!"".equals(conditions2)){
		//	  sql ="select t2.*,tc.companyName,isnull(t7.ncqetotal,0) as ncqetotal,isnull(t4.utotal,0) as utotal,isnull(t6.nutotal,0) as nutotal,(select  count(t.registNumber)  from TwoCodeElevatorInfo  t left join (select * from ywmanagerinfo where  " + conditions2+ " ) y   on t.registNumber = y.registNumber where   t.ywcompanyid = t2.ywcompanyid and y.registNumber is null  ) as uncqetotal    from  (select count(*) as etotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t5.userid) as nutotal, t5.companyid from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role =18) t5 group by t5.companyid) t6 on  t4.companyid =t6.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id left join (select count(t3.registNumber) as ncqetotal,t.ywcompanyId from (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo where "+conditions2+" group by registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  inner join TwoCodeElevatorInfo  t on t.registNumber = t3.registNumber  group  by ywcompanyid) t7  on t2.ywcompanyid =t7.ywcompanyId ";	 
			  sql ="select t2.*,-1 as uncqetotal,isnull(tc.companyName,'') as  companyName,isnull(t7.ncqetotal,0) as ncqetotal,isnull(t4.utotal,0) as utotal,isnull(t6.nutotal,0) as nutotal,(select  count(distinct t.registNumber)  from TCYEUView  t where " + conditions2+ " and t.dailingFlag = 0 and t.ywcompanyid  = t2.ywcompanyid)  as eywtotal    from  (select count(*) as etotal,sum(case when t.dailingFlag = 1 then 1 else 0 end) as dailingetotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 and t.isinvalid = 0 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t5.userid) as nutotal, t5.companyid from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role =18) t5 group by t5.companyid) t6 on  t4.companyid =t6.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id left join (select count(t3.registNumber) as ncqetotal,t.ywcompanyId from (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo where "+conditions2+" group by registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  inner join TwoCodeElevatorInfo  t on t.registNumber = t3.registNumber where t.dailingFlag  = 0 group  by ywcompanyid) t7  on t2.ywcompanyid =t7.ywcompanyId ";	 		
			 }
			 else{
		//	   sql ="select t2.*,tc.companyName,isnull(t7.ncqetotal,0) as ncqetotal,isnull(t4.utotal,0) as utotal,isnull(t6.nutotal,0) as nutotal,(select  count(t.registNumber)  from TwoCodeElevatorInfo  t left join ywmanagerinfo y  on t.registNumber = y.registNumber where t.ywcompanyid = t2.ywcompanyid and y.registNumber is null  ) as uncqetotal  from  (select count(*) as etotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t5.userid) as nutotal, t5.companyid from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role =18) t5 group by t5.companyid) t6 on  t4.companyid =t6.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id left join (select count(t3.registNumber) as ncqetotal,t.ywcompanyId from (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  inner join TwoCodeElevatorInfo  t on t.registNumber = t3.registNumber  group  by ywcompanyid) t7  on t2.ywcompanyid =t7.ywcompanyId ";	
			  sql ="select t2.*,-1 as uncqetotal,isnull(tc.companyName,'') as  companyName,isnull(t7.ncqetotal,0) as ncqetotal,isnull(t4.utotal,0) as utotal,isnull(t6.nutotal,0) as nutotal,(select  count(distinct t.registNumber)  from TCYEUView  t where t.dailingFlag = 0 and t.ywcompanyid  = t2.ywcompanyid)  as eywtotal  from  (select count(*) as etotal,sum(case when t.dailingFlag = 1 then 1 else 0 end) as dailingetotal,t.ywcompanyid  as ywcompanyid from TwoCodeElevatorInfo  t group by t.ywcompanyId ) t2 left join (select count(*) as utotal,t.companyid from tcuserinfo t where t.role = 18 and t.isinvalid = 0 group by t.companyid ) t4  on t2.ywcompanyid =t4.companyid left join (select count(t5.userid) as nutotal, t5.companyid from (select ty.*,tc.companyid from ( select t.userid from ywmanagerinfo t group by t.userid ) ty left join tcuserinfo tc on ty.userid =tc.id where tc.role =18) t5 group by t5.companyid) t6 on  t4.companyid =t6.companyid left join twocodecompanyinfo  tc on t2.ywcompanyid = tc.id left join (select count(t3.registNumber) as ncqetotal,t.ywcompanyId from (select t1.registNumber,t1.endTime  from (select  registNumber as registNumber,max(endTime) as endTime from  YwManagerInfo group by registNumber) t1  where DATEDIFF(dd,convert(datetime,t1.endTime,120),getdate()) > 15 ) t3  inner join TwoCodeElevatorInfo  t on t.registNumber = t3.registNumber where t.dailingFlag  = 0 group  by ywcompanyid) t7  on t2.ywcompanyid =t7.ywcompanyId ";	
					
			 }
		//	  conditionsSql ="select count(*) from  (select t.ywcompanyId from TwoCodeElevatorInfo  t where t.dailingFlag  = 0 group by t.ywcompanyId) t2  ";
			}
		 try {
		//		total =YwInfoVO.countBySql(YwInfoVO.class, conditionsSql, null);
				items = YwstatisticsVO.findBySql(YwstatisticsVO.class, sql, null, "t2.etotal desc,t4.utotal desc ");
				 Iterator<YwstatisticsVO> it = items.iterator();
				 while(it.hasNext()){
					 YwstatisticsVO   ywvo =it.next();
					 int eywtotal = ywvo.getEywtotal();
					 int nutotal =  ywvo.getNutotal();
					 ywvo.setUncqetotal(ywvo.getEtotal()-ywvo.getEywtotal()-ywvo.getDailingetotal());
					 if(eywtotal > 0 && nutotal > 0)
						 ywvo.setYwcountbyperson(String.format("%10.2f",eywtotal*1.0/nutotal));	
				//		 ywvo.setYwcountbyperson(String.valueOf(eywtotal/nutotal));	 
					 else
						 ywvo.setYwcountbyperson("0.0");
				 }
				 String[] hearders = new String[] {"维保单位","电梯数量", "运维电梯数", "停用电梯数", "未运维电梯数", "当前超期数", "总维保人员数","在维保人员数","人均维保数"};//表头数组 
				 YwstatExportExcel<YwstatisticsVO> ex = new YwstatExportExcel<YwstatisticsVO>(); 
				 SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");  
				 String filename = "运维统计表_"+timeFormat.format(new Date())+".xls";  
				 response.setContentType("application/ms-excel;charset=UTF-8");  
				 response.setHeader("Content-Disposition", "attachment;filename="  
				            .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
				 OutputStream out = response.getOutputStream();  
			     ex.exportExcel(hearders, items, out);  
			//	 ex.exportExcel(tjtime,hearders, items, out);
			     out.close(); 
			   //这一行非常关键，否则在实际中有可能出现莫名其妙的问题！！！
			     response.flushBuffer();//强行将响应缓存中的内容发送到目的地 
			     System.out.println("excel导出成功！"); 
			     
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
    
    
    public View newywranking(){
   	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
   	     long total=0;
		 List<YwQuaCredRatingVO> items = null;
		 
		 int companyId = 0;
		 try {
			if(role == 11 || role == 10){
		    UserExtInfo user =UserExtInfo.findFirstBySql(UserExtInfo.class, "select companyId from TwoCodeUserExtInfo where userid= ?",new Object[] {userid});
			if(user != null)
				companyId = user.getCompanyid();
		//    items =YwQuaCredRatingVO.findBySql(YwRankingInfoVO.class, "exec pro_MaintainEnterpriseScoreByArea ?",new Object[] {companyId});
			items =YwQuaCredRatingVO.findBySql(YwQuaCredRatingVO.class, "exec pro_MaintainEnterNewpriseScoreByArea ?",new Object[] {companyId});
		 		
			}
			else{
		//	items =YwQuaCredRatingVO.findBySql(YwRankingInfoVO.class, "exec pro_MaintainEnterpriseScore");
		//	items = YwQuaCredRatingVO.findBySql(YwQuaCredRatingVO.class, "select officeSpacesj,headQuarterssj,maintenanceEleCountsj,avgmaintenanceEleCountsj,fixedTelOnDutysj,telOnDutyunattendedsj,enterpriseChangesj,enterpriseRecordsj,infoComRatesj,sweepCodeRatesj,sweepCodeInTimeRatesj,alarmDealwithsj,regularInspectionsj,inspectElevatorsj,acceptInspElevatorsj,maintenSceneInfosj,malignantEventssj,complaintsEventssj,maintenBusinesssj,honestsj,punishmentsj,firstRescuesj,secondRescuesj,secondRescuePointsj,rescueResponsesj,tiringPeoplesj,positiveEnergysj,expertsSuggestionsj,positiveWorksj,remoteMonitorsj,elevatorInsurancesj,techinnovationsj from TwoCodeYwQuaCredRating");
		    items =YwQuaCredRatingVO.findBySql(YwQuaCredRatingVO.class, "exec pro_MaintainEnterNewpriseScore");
			}
			if(items != null)
				total = items.size();
			
		} catch (ActiveRecordException e) {
		
			e.printStackTrace();
		}
	  
	      Map<String, Object> result = new HashMap<String, Object>();
		  result.put("rows", items);
		  return new JsonView(result);
   }
    
    public View qsywnewranking(){
     	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
  		 int userid = userinfo.getId();
  		 String userName =userinfo.getLoginName();
  		 int  role = userinfo.getRole();
     	 long total=0;
  		 List<YwQuaCredRatingVO> items = null;
  		 
  		 int companyId = 0;  
  		 try {
  			
  			items =YwQuaCredRatingVO.findBySql(YwQuaCredRatingVO.class, "exec pro_MaintainEnterNewpriseScore");
  			
  			if(items != null)
  				total = items.size();
  			
  		} catch (ActiveRecordException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  	  
  	      Map<String, Object> result = new HashMap<String, Object>();
  	   //   result.put("total", total);
  		  result.put("rows", items);
  		  return new JsonView(result);
     }
    
    public View qsywnewranking2(int sv,String ratingDate){
        UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
   	 int userid = userinfo.getId();
   	 String userName =userinfo.getLoginName();
   	 int  role = userinfo.getRole();
   	 List<YwQuaCredRatingVO> items = null;
   	 int companyId = 0;
   		 try {		
   	//		items =YwQuaCredRatingVO.findBySql(YwQuaCredRatingVO.class, "exec pro_MaintainEnterNewpriseScoreByYwCount ?", new Object[] {sv});
   			items =YwQuaCredRatingVO.findBySql(YwQuaCredRatingVO.class, "exec pro_MaintainEnterNewpriseScoreByYwCountAndDate ?,?", new Object[] {sv,ratingDate});		
   		} catch (ActiveRecordException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   	  
   	      Map<String, Object> result = new HashMap<String, Object>();
   	  
   		  result.put("rows", items);
   		  return new JsonView(result);
      }
    
    public View newywranking2(int sv,String ratingDate){
     System.out.println("ratingDate---"+ratingDate);
     UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
   	 int userid = userinfo.getId();
   	 String userName =userinfo.getLoginName();
   	 int  role = userinfo.getRole();
   	 List<YwQuaCredRatingVO> items = null;
   	 int companyId = 0;
   		 try {
   			 if(role == 11 || role == 10){
   		 		    UserExtInfo user =UserExtInfo.findFirstBySql(UserExtInfo.class, "select companyId from TwoCodeUserExtInfo where userid= ?",new Object[] {userid});
   		 			if(user != null)
   		 				companyId = user.getCompanyid();
   		 	//	    items =YwRankingInfoVO.findBySql(YwRankingInfoVO.class, "exec pro_MaintainEnterpriseScoreByAreaYwCount ?,?",new Object[] {sv,companyId});	
   		 	//	items =YwQuaCredRatingVO.findBySql(YwQuaCredRatingVO.class, "exec pro_MaintainEnterNewpriseScoreByAreaYwCount ?,?",new Object[] {sv,companyId});
   		 		items =YwQuaCredRatingVO.findBySql(YwQuaCredRatingVO.class, "exec pro_MaintainEnterNewpriseScoreByAreaYwCountAndDate ?,?,?",new Object[] {sv,companyId,ratingDate});	
   			     }
   		 			else{
   		//	items =YwRankingInfoVO.findBySql(YwRankingInfoVO.class, "exec pro_MaintainEnterpriseScoreByYwCount ?", new Object[] {sv});
   		// 	items =YwQuaCredRatingVO.findBySql(YwQuaCredRatingVO.class, "exec pro_MaintainEnterNewpriseScoreByYwCount ?", new Object[] {sv});
   		 	items =YwQuaCredRatingVO.findBySql(YwQuaCredRatingVO.class, "exec pro_MaintainEnterNewpriseScoreByYwCountAndDate ?,?", new Object[] {sv,ratingDate});	 	
   		 			}
   			
   		} catch (ActiveRecordException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   	  
   	      Map<String, Object> result = new HashMap<String, Object>();
   	  
   		  result.put("rows", items);
   		  return new JsonView(result);
      }
     
    public View newywrankingByCompanyName(YwRankingInfoVO info ){
     System.out.println("ratingDate----"+info.getRatingDate());
   	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
   	 int userid = userinfo.getId();
   	 String userName =userinfo.getLoginName();
   	 int  role = userinfo.getRole();
  	     long total=0;
  	     
  	     int zjcompanyId = 0;
  	     int companyId =0;
  	     companyId =info.getYwCompanyId();
		 List<YwQuaCredRatingVO> items = null;
	     
		 try {
			if(companyId > 0){
				if(role == 11 || role == 10){
		 		    UserExtInfo user =UserExtInfo.findFirstBySql(UserExtInfo.class, "select companyId from TwoCodeUserExtInfo where userid= ?",new Object[] {userid});
		 			if(user != null)
		 				zjcompanyId = user.getCompanyid();
		 	//	    items =YwRankingInfoVO.findBySql(YwRankingInfoVO.class, "exec pro_MaintainEnterpriseScoreByAreaYwName ?,?",new Object[] {companyId,zjcompanyId});	
		 			  items =YwQuaCredRatingVO.findBySql(YwQuaCredRatingVO.class, "exec pro_MaintainEnterNewpriseScoreByAreaYwName ?,?", new Object[] {companyId,zjcompanyId}); 
				   }
		 			else{	
			 //        items =YwQuaCredRatingVO.findBySql(YwQuaCredRatingVO.class, "exec pro_MaintainEnterNewpriseScoreByYwName ?", new Object[] {companyId}); 
		 				items =YwQuaCredRatingVO.findBySql(YwQuaCredRatingVO.class, "exec pro_MaintainEnterNewpriseScoreByYwNameAndDate ?,?", new Object[] {companyId,info.getRatingDate()}); 
		 			}
		 }
			else{ 
				if(role == 11 || role == 10){
		 		    UserExtInfo user =UserExtInfo.findFirstBySql(UserExtInfo.class, "select companyId from TwoCodeUserExtInfo where userid= ?",new Object[] {userid});
		 			if(user != null)
		 				zjcompanyId = user.getCompanyid();
		 	//	    items =YwRankingInfoVO.findBySql(YwRankingInfoVO.class, "exec MaintainEnterpriseScoreByArea ?",new Object[] {zjcompanyId});	
		 			items =YwQuaCredRatingVO.findBySql(YwQuaCredRatingVO.class, "exec pro_MaintainEnterNewpriseScoreByArea ?",new Object[] {zjcompanyId});	
			 			
				    }
		 			else{	
				items =YwQuaCredRatingVO.findBySql(YwQuaCredRatingVO.class, "exec pro_MaintainEnterNewpriseScore");
		 			}
			}
			if(items != null)
				total = items.size();
			
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	      Map<String, Object> result = new HashMap<String, Object>();
	   //   result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
  }
    
    public View qsnewywrankingByCompanyName(YwRankingInfoVO info ){
      	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
      	 int userid = userinfo.getId();
      	 String userName =userinfo.getLoginName();
      	 int  role = userinfo.getRole();
     	     long total=0;
     	     
     	     int zjcompanyId = 0;
     	     int companyId =0;
     	     companyId =info.getYwCompanyId();
   		 List<YwQuaCredRatingVO> items = null;
   	     
   		 try {
   			if(companyId > 0){
   		//	         items =YwQuaCredRatingVO.findBySql(YwQuaCredRatingVO.class, "exec pro_MaintainEnterNewpriseScoreByYwName ?", new Object[] {companyId}); 
   				items =YwQuaCredRatingVO.findBySql(YwQuaCredRatingVO.class, "exec pro_MaintainEnterNewpriseScoreByYwNameAndDate ?,?", new Object[] {companyId,info.getRatingDate()}); 
			         
   			     }
   		 
   			else{ 
   				
   			//	items =YwQuaCredRatingVO.findBySql(YwQuaCredRatingVO.class, "exec pro_MaintainEnterNewpriseScore"); 
   				items =YwQuaCredRatingVO.findBySql(YwQuaCredRatingVO.class, "exec pro_MaintainEnterNewpriseScoreAndDate ?",new Object[] {info.getRatingDate()});
   			}
   			if(items != null)
   				total = items.size();
   			
   		} catch (ActiveRecordException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   	  
   	      Map<String, Object> result = new HashMap<String, Object>();
   	   //   result.put("total", total);
   		  result.put("rows", items);
   		  return new JsonView(result);
     }
    
    public String ywshenheMessage(int ywuserId,String ywshenheMessage){
    	String result = "failure";
    	try {
			ywshenheMessage = URLEncoder.encode(ywshenheMessage,"GBK");
		} catch (UnsupportedEncodingException e) {
		
			e.printStackTrace();
		}
    //	String serverPath =GlobalFunction.ywshenhetuisongServer + "userId="+ywuserId+"&ywshenheMessage="+ywshenheMessage;
    //	System.out.println("serverPath---"+serverPath);
    	
    	 String sr=sendGet(GlobalFunction.ywshenhetuisongServer, "userId="+ywuserId+"&ywshenheMessage="+ywshenheMessage);
         System.out.println(sr);
         
     	 result = "success";

    	 return result;
    }
    
    
    public  String sendGet(String url, String param) {
        PrintWriter out = null;
       
        String result = "";
        try {
        	String urlNameString = url + "?" + param;

        	 URL realUrl = new URL(urlNameString);
             // 打开和URL之间的连接
             URLConnection connection = realUrl.openConnection();
             // 设置通用的请求属性
             connection.setRequestProperty("accept", "*/*");
             connection.setRequestProperty("connection", "Keep-Alive");
             connection.setRequestProperty("user-agent",
                     "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
             // 建立实际的连接
             connection.connect();
             // 获取所有响应头字段
             Map<String, List<String>> map = connection.getHeaderFields();
             // 遍历所有的响应头字段
             for (String key : map.keySet()) {
                 System.out.println(key + "--->" + map.get(key));
             }

           
        } catch (Exception e) {
            System.out.println("发送请求出现异常！"+e);
            e.printStackTrace();
        }
       
        return result;
    } 
    
    public View ywshenhetj(YwInfoVO info){
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		  Date d=new Date();   
		  SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		  String mrTime = df.format(new Date(d.getTime() - 16 * 24 * 60 * 60 * 1000));
		   	 
		 YwInfoVO item =  null;
		 
		 String sql ="";
		 String qstartTime ="";
		 String qendTime="";
		 String conditions="";
		 
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 
		 if(!"".equals(qstartTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and left(endTime,10)  >= '"+qstartTime+"'" ;	 
			 } 
			 else{
				 conditions =" left(endTime,10)  >= '"+qstartTime+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and left(endTime,10)  <= '"+qendTime+"'" ;	 
			 } 
			 else{
				 conditions =" left(endTime,10)  <= '"+qendTime+"'" ;	 
			 }
		 }
		 
		 if("".equals(qstartTime) && "".equals(qendTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and left(endTime,10)  >= '"+mrTime+"'" ;	 
			 } 
			 else{
				 conditions =" left(endTime,10)  >= '"+mrTime+"'" ;	 
			 }
			 		 
		 }
		 
		 if("".equals(conditions)){	 
	/*	 sql = "select count(*) as ywCount,sum(case when ywimageValidty =25 then 1 else 0 end) as ywfwmobileCount,sum(case when (ywstatus ='1' and ywimageValidty = 16 )  then 1 else 0 end) as ywmobileshCount,sum(case when ((ywstatus ='1' or ywstatus ='4') and ywimageValidty < 16 )  then 1 else 0 end) as ywautoshCount,sum(case when ((ywstatus ='1' or ywstatus ='4') and ywimageValidty > 16 and  ywimageValidty != 25 )  then 1 else 0 end) as ywptsheCount,sum(case when ywstatus ='1' then 1 else 0 end) as ywtgCount,sum(case when ywstatus ='0' then 1 else 0 end) as ywundoCount,sum(case when ywstatus ='4' then 1 else 0 end) as ywuntgCount,sum(case when ywstatus ='8' then 1 else 0 end) as ywnoimageCount," +
		 		" sum(case when (ywimageValidty =1 and ywstatus = '1') then 1 else 0 end) as ywpcyxCount, sum(case when (ywimageValidty =2 and ywstatus = '1') then 1 else 0 end) as ywpcwxCount,sum(case when (ywimageValidty =4 and ywstatus = '1') then 1 else 0 end) as ywpcysCount,sum(case when (ywimageValidty =8 and ywstatus = '1') then 1 else 0 end) as ywpcnoiCount,sum(case when (ywimageValidty =17 and ywstatus = '1') then 1 else 0 end) as ywimageqxCount,sum(case when (ywimageValidty =18 and ywstatus = '1') then 1 else 0 end) as ywwffbdataCount,sum(case when (ywimageValidty =19 and ywstatus = '1') then 1 else 0 end) as ywzddataCount,sum(case when (ywimageValidty =20 and ywstatus = '1') then 1 else 0 end) as ywnoimagedataCount," +
		 		" sum(case when (ywimageValidty =1 and ywstatus = '4') then 1 else 0 end) as ywpcnoyxCount, sum(case when (ywimageValidty =2 and ywstatus = '4') then 1 else 0 end) as ywpcnowxCount,sum(case when (ywimageValidty =4 and ywstatus = '4') then 1 else 0 end) as ywpcnoysCount,sum(case when (ywimageValidty =8 and ywstatus = '4') then 1 else 0 end) as ywpcnonoiCount,sum(case when (ywimageValidty =21 and ywstatus = '4') then 1 else 0 end) as ywgiswrongCount,sum(case when (ywimageValidty =22 and ywstatus = '4') then 1 else 0 end) as ywfeixianchangCount,sum(case when (ywimageValidty =23 and ywstatus = '4') then 1 else 0 end) as ywzhifuyinCount,sum(case when (ywimageValidty =24 and ywstatus = '4') then 1 else 0 end) as ywelefuyinCount from   hisYwManagerInfo where DateDiff(month, endTime, GetDate()) = 0";
		 */
			 sql = "select count(*) as ywCount,sum(case when ywimageValidty =25 then 1 else 0 end) as ywfwmobileCount,sum(case when ywstatus ='1' and ywimageValidty =0 then 1 else 0 end) as ywtempfwCount,sum(case when (ywstatus ='1' and (ywimageValidty = 16 or ywimageValidty = 26 ))  then 1 else 0 end) as ywmobileshCount,sum(case when ((ywstatus ='1' or ywstatus ='4' or ywstatus ='8') and ( ywimageValidty < 16 and ywimageValidty > 0 ) )  then 1 else 0 end) as ywautoshCount,sum(case when ((ywstatus ='1' or ywstatus ='4') and ywimageValidty > 16 and  ywimageValidty != 25 and ywimageValidty != 26 )  then 1 else 0 end) as ywptsheCount," +
		 		" sum(case when (ywimageValidty =1 and ywstatus = '1') then 1 else 0 end) as ywpcyxCount, sum(case when (ywimageValidty =2 and ywstatus = '1') then 1 else 0 end) as ywpcwxCount,sum(case when (ywimageValidty =4 and ywstatus = '1') then 1 else 0 end) as ywpcysCount,sum(case when (ywimageValidty =8 and ywstatus = '1') then 1 else 0 end) as ywpcnoiCount,sum(case when (ywimageValidty =17 and ywstatus = '1') then 1 else 0 end) as ywimageqxCount,sum(case when (ywimageValidty =18 and ywstatus = '1') then 1 else 0 end) as ywwffbdataCount,sum(case when (ywimageValidty =19 and ywstatus = '1') then 1 else 0 end) as ywzddataCount,sum(case when (ywimageValidty =20 and ywstatus = '1') then 1 else 0 end) as ywnoimagedataCount," +
		 		" sum(case when (ywimageValidty =1 and ywstatus = '4') then 1 else 0 end) as ywpcnoyxCount, sum(case when (ywimageValidty =2 and ywstatus = '4') then 1 else 0 end) as ywpcnowxCount,sum(case when (ywimageValidty =4 and ywstatus = '4') then 1 else 0 end) as ywpcnoysCount,sum(case when (ywimageValidty =8 and ywstatus = '4') then 1 else 0 end) as ywpcnonoiCount,sum(case when (ywimageValidty =21 and ywstatus = '4') then 1 else 0 end) as ywgiswrongCount,sum(case when (ywimageValidty =22 and ywstatus = '4') then 1 else 0 end) as ywfeixianchangCount,sum(case when (ywimageValidty =23 and ywstatus = '4') then 1 else 0 end) as ywzhifuyinCount,sum(case when (ywimageValidty =24 and ywstatus = '4') then 1 else 0 end) as ywelefuyinCount from   hisYwManagerInfo where DateDiff(day, endTime, GetDate()) <= 16";
		
		 }
		 else
		 {
		/*	 sql = "select count(*) as ywCount,sum(case when ywimageValidty =16 then 1 else 0 end) as ywfwmobileCount,sum(case when (ywstatus ='1' and ywimageValidty = 16 )  then 1 else 0 end) as ywmobileshCount,sum(case when ((ywstatus ='1' or ywstatus ='4') and ywimageValidty < 16 )  then 1 else 0 end) as ywautoshCount,sum(case when ((ywstatus ='1' or ywstatus ='4') and ywimageValidty > 16 and  ywimageValidty != 25 )  then 1 else 0 end) as ywptsheCount,sum(case when ywstatus ='1' then 1 else 0 end) as ywtgCount,sum(case when ywstatus ='0' then 1 else 0 end) as ywundoCount,sum(case when ywstatus ='4' then 1 else 0 end) as ywuntgCount,sum(case when ywstatus ='8' then 1 else 0 end) as ywnoimageCount," +
		 		" sum(case when (ywimageValidty =1 and ywstatus = '1') then 1 else 0 end) as ywpcyxCount, sum(case when (ywimageValidty =2 and ywstatus = '1') then 1 else 0 end) as ywpcwxCount,sum(case when (ywimageValidty =4 and ywstatus = '1') then 1 else 0 end) as ywpcysCount,sum(case when (ywimageValidty =8 and ywstatus = '1') then 1 else 0 end) as ywpcnoiCount,sum(case when (ywimageValidty =17 and ywstatus = '1') then 1 else 0 end) as ywimageqxCount,sum(case when (ywimageValidty =18 and ywstatus = '1') then 1 else 0 end) as ywwffbdataCount,sum(case when (ywimageValidty =19 and ywstatus = '1') then 1 else 0 end) as ywzddataCount,sum(case when (ywimageValidty =20 and ywstatus = '1') then 1 else 0 end) as ywnoimagedataCount," +
		 		" sum(case when (ywimageValidty =1 and ywstatus = '4') then 1 else 0 end) as ywpcnoyxCount, sum(case when (ywimageValidty =2 and ywstatus = '4') then 1 else 0 end) as ywpcnowxCount,sum(case when (ywimageValidty =4 and ywstatus = '4') then 1 else 0 end) as ywpcnoysCount,sum(case when (ywimageValidty =8 and ywstatus = '4') then 1 else 0 end) as ywpcnonoiCount,sum(case when (ywimageValidty =21 and ywstatus = '4') then 1 else 0 end) as ywgiswrongCount,sum(case when (ywimageValidty =22 and ywstatus = '4') then 1 else 0 end) as ywfeixianchangCount,sum(case when (ywimageValidty =23 and ywstatus = '4') then 1 else 0 end) as ywzhifuyinCount,sum(case when (ywimageValidty =24 and ywstatus = '4') then 1 else 0 end) as ywelefuyinCount from   hisYwManagerInfo where "+conditions;
		*/	 
			 sql = "select count(*) as ywCount,sum(case when ywimageValidty =25 then 1 else 0 end) as ywfwmobileCount,sum(case when ywstatus ='1' and ywimageValidty =0 then 1 else 0 end) as ywtempfwCount,sum(case when (ywstatus ='1' and (ywimageValidty = 16 or ywimageValidty = 26 ))  then 1 else 0 end) as ywmobileshCount,sum(case when ((ywstatus ='1' or ywstatus ='4' or ywstatus ='8') and ( ywimageValidty < 16 and ywimageValidty > 0 ) )  then 1 else 0 end) as ywautoshCount,sum(case when ((ywstatus ='1' or ywstatus ='4') and ywimageValidty > 16 and  ywimageValidty != 25 and ywimageValidty != 26 )  then 1 else 0 end) as ywptsheCount," +
		 		" sum(case when (ywimageValidty =1 and ywstatus = '1') then 1 else 0 end) as ywpcyxCount, sum(case when (ywimageValidty =2 and ywstatus = '1') then 1 else 0 end) as ywpcwxCount,sum(case when (ywimageValidty =4 and ywstatus = '1') then 1 else 0 end) as ywpcysCount,sum(case when (ywimageValidty =8 and ywstatus = '1') then 1 else 0 end) as ywpcnoiCount,sum(case when (ywimageValidty =17 and ywstatus = '1') then 1 else 0 end) as ywimageqxCount,sum(case when (ywimageValidty =18 and ywstatus = '1') then 1 else 0 end) as ywwffbdataCount,sum(case when (ywimageValidty =19 and ywstatus = '1') then 1 else 0 end) as ywzddataCount,sum(case when (ywimageValidty =20 and ywstatus = '1') then 1 else 0 end) as ywnoimagedataCount," +
		 		" sum(case when (ywimageValidty =1 and ywstatus = '4') then 1 else 0 end) as ywpcnoyxCount, sum(case when (ywimageValidty =2 and ywstatus = '4') then 1 else 0 end) as ywpcnowxCount,sum(case when (ywimageValidty =4 and ywstatus = '4') then 1 else 0 end) as ywpcnoysCount,sum(case when (ywimageValidty =8 and ywstatus = '4') then 1 else 0 end) as ywpcnonoiCount,sum(case when (ywimageValidty =21 and ywstatus = '4') then 1 else 0 end) as ywgiswrongCount,sum(case when (ywimageValidty =22 and ywstatus = '4') then 1 else 0 end) as ywfeixianchangCount,sum(case when (ywimageValidty =23 and ywstatus = '4') then 1 else 0 end) as ywzhifuyinCount,sum(case when (ywimageValidty =24 and ywstatus = '4') then 1 else 0 end) as ywelefuyinCount from   hisYwManagerInfo where "+conditions;
	
		 }
		 try {
			item = YwInfoVO.findFirstBySql(YwInfoVO.class, sql, null);
		} catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 if(item != null){
		     result.put("ywCount", item.getYwCount());  
		     result.put("ywfwmobileCount", item.getYwfwmobileCount());  
		     result.put("ywtempfwCount", item.getYwtempfwCount());  
		     
		     result.put("ywmobileshCount", item.getYwmobileshCount());  
		     result.put("ywautoshCount", item.getYwautoshCount());  
		     result.put("ywptsheCount", item.getYwptsheCount());  
		     
		     result.put("ywtgCount", item.getYwtgCount());  
		     result.put("ywuntgCount", item.getYwuntgCount());  
		     result.put("ywundoCount", item.getYwundoCount());  
		     result.put("ywnoimageCount", item.getYwnoimageCount());  
		    
		     result.put("ywpcyxCount",item.getYwpcyxCount());
		     result.put("ywpcwxCount",item.getYwpcwxCount());
		     result.put("ywpcysCount",item.getYwpcysCount());
		     result.put("ywpcnoiCount",item.getYwpcnoiCount());
		     result.put("ywimageqxCount",item.getYwimageqxCount());
		     result.put("ywwffbdataCount",item.getYwwffbdataCount());
		     result.put("ywzddataCount",item.getYwzddataCount());
		     result.put("ywnoimagedataCount",item.getYwnoimagedataCount());
		     
		     result.put("ywpcnoyxCount",item.getYwpcnoyxCount());
		     result.put("ywpcnowxCount",item.getYwpcnowxCount());
		     result.put("ywpcnoysCount",item.getYwpcnoysCount());
		     result.put("ywpcnonoiCount",item.getYwpcnonoiCount());
		     result.put("ywgiswrongCount",item.getYwgiswrongCount());
		     result.put("ywfeixianchangCount",item.getYwfeixianchangCount());
		     result.put("ywzhifuyinCount",item.getYwzhifuyinCount());
		     result.put("ywelefuyinCount",item.getYwelefuyinCount());
		     
		    
			 }
		 else{
			  result.put("ywCount", "");  
			  result.put("ywfwmobileCount", ""); 
			  result.put("ywtempfwCount", "");  
			  
			  result.put("ywmobileshCount", item.getYwmobileshCount());  
			  result.put("ywautoshCount", item.getYwautoshCount());  
			  result.put("ywptsheCount", item.getYwptsheCount());  
			  
			  result.put("ywtgCount", "");  
			  result.put("ywuntgCount", "");  
			  result.put("ywundoCount", "");  
			  result.put("ywnoimageCount", ""); 
			  
			  result.put("ywpcyxCount","");
			  result.put("ywpcwxCount","");
			  result.put("ywpcysCount","");
			  result.put("ywpcnoiCount","");
			  result.put("ywimageqxCount","");
			  result.put("ywwffbdataCount","");
			  result.put("ywzddataCount","");
			  result.put("ywnoimagedataCount","");
			  
			  result.put("ywpcnoyxCount","");
			  result.put("ywpcnowxCount","");
			  result.put("ywpcnoysCount","");
			  result.put("ywpcnonoiCount","");
			  result.put("ywgiswrongCount","");
			  result.put("ywfeixianchangCount","");
			  result.put("ywzhifuyinCount","");
			  result.put("ywelefuyinCount","");
		 }
			 return new JsonView(result);	
	 }


}     

