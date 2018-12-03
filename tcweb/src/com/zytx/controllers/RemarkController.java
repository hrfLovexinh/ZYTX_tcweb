package com.zytx.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.text.DateFormat;
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
import com.zytx.models.RemarkInfo;
import com.zytx.models.RemarkInfoVO;
import com.zytx.models.ReplyInfoVO;
import com.zytx.models.TCUserInfoView;
import com.zytx.models.UserExtInfo;
import com.zytx.models.UserInfo;
import com.zytx.models.ImageInfo;
import com.zytx.models.ImageInfo2;
import com.zytx.models.TwoCodeDeviceRelationInfo;
import com.zytx.models.UserInfoVO;
import com.zytx.models.YwInfo;
import com.zytx.models.YwInfoVO;

public class RemarkController extends ApplicationController{  
	

	 public View liuyanlist(int page, int rows) throws Exception{
		 
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 long total=0;
		 List<RemarkInfoVO> items = null;
	     if(role==2 || role==1){ //系统管理员  
	     	
	    //	 items = ElevaltorInfoVO.findAll(ElevaltorInfoVO.class, null, null, null, rows, (page-1)*rows);
	    //	 String sql ="select t.id,t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,isnull(t.twoCodeId,'') as twoCodeId,t.remarkDate,isnull(t.warnTelephone,'') as warnTelephone,t.remarkInfo,t.remarkLevel,t.process_time,t.process_remark,tc.companyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =te.townshipStreets and tc.type ='街道办') as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile  from TwoCodeRemark  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on te.ywCompanyId = tc.id";
	   // 	 total  =RemarkInfoVO.count(RemarkInfoVO.class, null, null);
	    	 total  =RemarkInfoVO.countBySql(RemarkInfoVO.class, "select count(*)  from TwoCodeRemark  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber where te.address !=''", null);
	    	 String sql ="select t.process_type,isnull(t.checkadvice,'') as checkadvice,t.checkadviceTime,t.id,t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,isnull(t.twoCodeId,'') as twoCodeId,t.remarkDate,isnull(t.warnTelephone,'') as warnTelephone,t.remarkInfo,t.remarkLevel,t.process_time,t.process_remark,tc.companyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =te.townshipStreets and tc.type ='街道办') as jdbCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =te.wgcompanyId and tc.type ='使用') as wgCompanyName,tcu.userName, tcu.contactPhone as telephonemobile from TwoCodeRemark  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on te.ywCompanyId = tc.id left join YwManagerInfo  y on  t.registNumber = y.registNumber left join TCuserInfo  tcu on y.userid = tcu.id where te.address !='' ";	 
	    	 items = RemarkInfoVO.findBySql(RemarkInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
	    	
	     } 
	     if(role==22 || role==23){ //系统管理员  
	    //	 total  =RemarkInfo.count(RemarkInfo.class, null, null);
	    //	 String sql ="select t.id,t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,isnull(t.twoCodeId,'') as twoCodeId,t.remarkDate,isnull(t.warnTelephone,'') as warnTelephone,t.remarkInfo,t.remarkLevel,t.process_time,t.process_remark,tc.companyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =te.townshipStreets and tc.type ='街道办') as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile from twocodeRemark t left join twocodeElevatorInfo te on t.registNumber = te.registNumber left join twoCodeYWEffective tye on te.ywCompanyId = tye.ywCompanyid left join TwoCodeCompanyInfo tc on te.ywCompanyId = tc.id where  (tye.effective is null) or (tye.effective ='1' and process_userid >0) or (tye.effective ='1' and datediff(hour,t.remarkDate,getDate())>72)";
	    	 String sql ="select t.process_type,isnull(t.checkadvice,'') as checkadvice,t.checkadviceTime,t.id,t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,isnull(t.twoCodeId,'') as twoCodeId,t.remarkDate,isnull(t.warnTelephone,'') as warnTelephone,t.remarkInfo,t.remarkLevel,t.process_time,t.process_remark,tc.companyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =te.townshipStreets and tc.type ='街道办') as jdbCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =te.wgcompanyId and tc.type ='使用') as wgCompanyName,tcu.userName, tcu.contactPhone as telephonemobile  from twocodeRemark t left join twocodeElevatorInfo te on t.registNumber = te.registNumber left join twoCodeYWEffective tye on te.ywCompanyId = tye.ywCompanyid left join TwoCodeCompanyInfo tc on te.ywCompanyId = tc.id left join YwManagerInfo  y on  t.registNumber = y.registNumber left join tcuserinfo  tcu on y.userid = tcu.id where  (te.registNumber is not null) and ((tye.effective is null) or (tye.effective ='0') or (tye.effective ='1' and process_userid >0) or (tye.effective ='1' and datediff(hour,t.remarkDate,getDate())>72)) and te.address !=''";
		       
	    	
	         total = RemarkInfoVO.countBySql(RemarkInfoVO.class, "select count(*) from twocodeRemark t left join twocodeElevatorInfo te on t.registNumber = te.registNumber left join twoCodeYWEffective tye on te.ywCompanyId = tye.ywCompanyid left join TwoCodeCompanyInfo tc on te.ywCompanyId = tc.id where  (te.registNumber is not null) and ((tye.effective is null) or (tye.effective ='0') or (tye.effective ='1' and process_userid >0) or (tye.effective ='1' and datediff(hour,t.remarkDate,getDate())>72))  and te.address !=''", null);
	    	 items = RemarkInfoVO.findBySql(RemarkInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
	    	
	     } 
	     if(role==10 || role ==11){ //质监局
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         total  =RemarkInfoVO.countBySql(RemarkInfoVO.class, "select count(*) from TwoCodeRemark y left join TwoCodeElevatorInfo t on y.registNumber = t.registNumber left join  twoCodeYWEffective tye on t.ywCompanyId = tye.ywCompanyid  where (t.zjCompanyId=?) and (t.registNumber is not null) and ((tye.effective is null) or (tye.effective ='0') or (tye.effective ='1' and process_userid >0) or (tye.effective ='1' and datediff(hour,y.remarkDate,getDate())>72)) and  t.address !=''", new Object[]{zjcompanyId});
	   //    String sql ="select t.id,t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,isnull(t.twoCodeId,'') as twoCodeId,t.remarkDate,isnull(t.warnTelephone,'') as warnTelephone,t.remarkInfo,t.remarkLevel,t.process_time,t.process_remark,tc.companyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =te.townshipStreets and tc.type ='街道办') as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile from TwoCodeRemark  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on te.ywCompanyId = tc.id left join twoCodeYWEffective tye on te.ywCompanyId = tye.ywCompanyid where (te.zjCompanyId=?) and ((tye.effective is null) or (tye.effective ='1' and process_userid >0) or (tye.effective ='1' and datediff(hour,t.remarkDate,getDate())>72))";
	         String sql ="select t.process_type,isnull(t.checkadvice,'') as checkadvice,t.checkadviceTime,t.id,t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,isnull(t.twoCodeId,'') as twoCodeId,t.remarkDate,isnull(t.warnTelephone,'') as warnTelephone,t.remarkInfo,t.remarkLevel,t.process_time,t.process_remark,tc.companyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =te.townshipStreets and tc.type ='街道办') as jdbCompanyName,tcu.userName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =te.wgcompanyId and tc.type ='使用') as wgCompanyName, tcu.contactPhone as telephonemobile  from TwoCodeRemark  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on te.ywCompanyId = tc.id left join twoCodeYWEffective tye on te.ywCompanyId = tye.ywCompanyid left join YwManagerInfo  y on  t.registNumber = y.registNumber left join tcuserinfo  tcu on y.userid = tcu.id where (te.registNumber is not null) and (te.zjCompanyId=?) and ((tye.effective is null) or (tye.effective ='0') or (tye.effective ='1' and process_userid >0) or (tye.effective ='1' and datediff(hour,t.remarkDate,getDate())>72))  and te.address !=''";
	    	 
	         items = RemarkInfoVO.findBySql(RemarkInfoVO.class, sql, new Object[]{zjcompanyId}, "t.id desc", rows, (page-1)*rows);
	    	 }
	     }  
	     
	     if(role==20 || role ==21){ //街道办
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         total  =RemarkInfoVO.countBySql(RemarkInfoVO.class, "select count(*) from TwoCodeRemark y left join TwoCodeElevatorInfo t on y.registNumber = t.registNumber left join  twoCodeYWEffective tye on t.ywCompanyId = tye.ywCompanyid  where (t.townshipStreets=?) and (t.registNumber is not null) and ((tye.effective is null) or (tye.effective ='0') or (tye.effective ='1' and process_userid >0) or (tye.effective ='1' and datediff(hour,y.remarkDate,getDate())>72))  and t.address !=''", new Object[]{zjcompanyId});
	         
	   //    String sql ="select t.id,t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,isnull(t.twoCodeId,'') as twoCodeId,t.remarkDate,isnull(t.warnTelephone,'') as warnTelephone,t.remarkInfo,t.remarkLevel,t.process_time,t.process_remark,tc.companyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =te.townshipStreets and tc.type ='街道办') as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile from TwoCodeRemark  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on te.ywCompanyId = tc.id left join twoCodeYWEffective tye on te.ywCompanyId = tye.ywCompanyid where (te.townshipStreets=?) and ((tye.effective is null) or (tye.effective ='1' and process_userid >0) or (tye.effective ='1' and datediff(hour,t.remarkDate,getDate())>72))";
	    	 String sql ="select t.process_type,isnull(t.checkadvice,'') as checkadvice,t.id,t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,isnull(t.twoCodeId,'') as twoCodeId,t.remarkDate,isnull(t.warnTelephone,'') as warnTelephone,t.remarkInfo,t.remarkLevel,t.process_time,t.process_remark,tc.companyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =te.townshipStreets and tc.type ='街道办') as jdbCompanyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =te.wgcompanyId and tc.type ='使用') as wgCompanyName,tcu.userName, tcu.contactPhone as telephonemobile  from TwoCodeRemark  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeCompanyInfo tc on te.ywCompanyId = tc.id left join twoCodeYWEffective tye on te.ywCompanyId = tye.ywCompanyid left join YwManagerInfo  y on  t.registNumber = y.registNumber left join tcuserinfo  tcu on y.userid = tcu.id where (te.registNumber is not null) and (te.townshipStreets=?) and ((tye.effective is null) or (tye.effective ='0')  or (tye.effective ='1' and process_userid >0) or (tye.effective ='1' and datediff(hour,t.remarkDate,getDate())>72)) and te.address !=''";
	         items = RemarkInfoVO.findBySql(RemarkInfoVO.class, sql, new Object[]{zjcompanyId}, "t.id desc", rows, (page-1)*rows);
	    	 }
	     }  
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	 
	 
	 public View ywlistByreg(String registNumber,int page, int rows) throws Exception{
		 System.out.println("执行ywlistByreg方法");
	  
		
		 long total=0;
		 List<YwInfoVO> items = null;
	    
	//    	 total  =YwInfoVO.count(YwInfoVO.class, null, null);
		     total = YwInfoVO.countBySql(YwInfoVO.class, "select count(*)  from YwManagerInfo  t where t.registNumber=?", new Object[]{registNumber});
	    	 String sql ="select t.id,t.registNumber,isnull(te.address,'') as address,(case when t.ywKind ='0' then '维保' else '巡检' end) as ywKind,isnull(t.maintainTypecode,'') as maintainTypecode,t.startTime,t.endTime,t.subTime, (case when datediff(minute,t.startTime,t.endTime)>0 then datediff(minute,t.startTime,t.endTime) else '0' end)  as dateSpan, (case when t.sPosition='0' then '机房' else '轿厢' end) as sPosition, (case when t.ePosition = '0' then '机房' else '轿厢' end) as ePosition,isnull(tu.userName,'')as userName,isnull(tc.companyName,'') as companyName from YwManagerInfo  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber left join TwoCodeUserExtInfo tu on tu.userid = t.userId left join TwoCodeCompanyInfo tc on tc.id =tu.companyid where t.registNumber=?";
	    	 items = YwInfoVO.findBySql(YwInfoVO.class, sql, new Object[]{registNumber}, "t.id desc", rows, (page-1)*rows);
	    	
	  
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
	
	 public String remarkzjReply(int id,String  checkadvice){
		 String result = "failure";
		 DateFormat format1 =new SimpleDateFormat("yyyy-MM-dd HH:mm");
		 String s = format1.format(new Date());
		 int num = 0 ;
		 try {
			num = RemarkInfoVO.updateAll(RemarkInfoVO.class, "checkadvice = ?,checkadviceTime = ? ", new Object[]{checkadvice,s},"id = ? ",new Object[] {id});
			if(num > 0){   //geng xin ping lun huifu 
				result = "success"; 
			}
			
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		 return result;
	 }
	 
	 public View query(RemarkInfoVO info,int page, int rows)throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 
		 String registNumber ="";
		 String  remarkLevel ="";
		 int zjcompanyId2 =0;  //非查询用的，是登陆过程中系统后台根据role不同展示不同列表用的
		 String address ="";
		 String buildingName ="";
		 String areaName="";
		 String qstartTime ="";
		 String qendTime="";
		 int townshipStreets =0;
		 
	//	 registNumber ="%"+info.getRegistNumber()+"%";   
		 registNumber =info.getRegistNumber();
		 address =info.getAddress();
		 areaName =info.getArea();
		 buildingName =info.getBuildingName();
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 townshipStreets =info.getTownshipStreets();
		 remarkLevel = info.getRemarkLevel();
         
		 
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
				 conditions =conditions+" and t.remarkDate  >= '"+qstartTime+"'" ;	 
			 } 
			 else{
				 conditions =" t.remarkDate  >= '"+qstartTime+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.remarkDate  <= '"+qendTime+"'" ;	 
			 } 
			 else{
				 conditions =" t.remarkDate  <= '"+qendTime+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(remarkLevel)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.remarkLevel = '"+remarkLevel+"'";	
				} 
				else{
					conditions =" t.remarkLevel = '"+remarkLevel+" '";	
				}
			 
		 }
		 /*
		 Object[] param=null;
		 if(role==2 || role==1){
			 if("".equals(remarkLevel)){
		 conditions ="t.registNumber like  ? ";
		 param = new Object[]{registNumber};
			 }
			 else{
				 conditions ="t.registNumber like  ? and remarkLevel=?";
				 param = new Object[]{registNumber,remarkLevel};
			 }
		 }
		 if(role==22 || role==23){
			 if("".equals(remarkLevel)){
		 conditions ="t.registNumber like  ? and ((tye.effective is null) or (tye.effective ='1' and process_userid >0) or (tye.effective ='1' and datediff(hour,t.remarkDate,getDate())>72))";
		 param = new Object[]{registNumber};
			 }
			 else{
				 conditions ="t.registNumber like  ? and remarkLevel=? and ((tye.effective is null) or (tye.effective ='1' and process_userid >0) or (tye.effective ='1' and datediff(hour,t.remarkDate,getDate())>72))";
				 param = new Object[]{registNumber,remarkLevel};
			 }
		 }
		 if(role==10 || role==11){
			 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	    	 zjcompanyId2 = userInfoVO.getCompanyId();
	    	 if("".equals(remarkLevel)){
			 conditions =" t.registNumber like  ?   and te.zjcompanyId=? and ((tye.effective is null) or (tye.effective ='1' and process_userid >0) or (tye.effective ='1' and datediff(hour,t.remarkDate,getDate())>72))";
			 param = new Object[]{registNumber,zjcompanyId2};
			 }
	    	 else{
	    		 conditions =" t.registNumber like  ? and  t.remarkLevel=?  and te.zjcompanyId=? and ((tye.effective is null) or (tye.effective ='1' and process_userid >0) or (tye.effective ='1' and datediff(hour,t.remarkDate,getDate())>72))";
				 param = new Object[]{registNumber,remarkLevel,zjcompanyId2}; 
	    	 }
		 }
			 
		 }
		 
		 if(role==20 || role==21){
			 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	    	 zjcompanyId2 = userInfoVO.getCompanyId();
	    	 if("".equals(remarkLevel)){
			 conditions =" t.registNumber like  ?   and te.townshipStreets=? and ((tye.effective is null) or (tye.effective ='1' and process_userid >0) or (tye.effective ='1' and datediff(hour,t.remarkDate,getDate())>72))";
			 param = new Object[]{registNumber,zjcompanyId2};
			 }
	    	 else{
	    		 conditions =" t.registNumber like  ? and  t.remarkLevel=?  and te.townshipStreets=? and ((tye.effective is null) or (tye.effective ='1' and process_userid >0) or (tye.effective ='1' and datediff(hour,t.remarkDate,getDate())>72))";
				 param = new Object[]{registNumber,remarkLevel,zjcompanyId2}; 
	    	 }
		 }
			 
		 }
		 
	//	 long total = TCUserInfoView.count(TCUserInfoView.class, conditions, param);
	//   String sql ="select t.id,t.registNumber,isnull(te.address,'') as address,isnull(t.twoCodeId,'') as twoCodeId,t.remarkDate,t.remarkInfo,t.remarkLevel,tc.companyName from TwoCodeRemark  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber   left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId where "+conditions;
		 String sql ="select t.id,t.registNumber,isnull(te.address,'') as address,isnull(t.twoCodeId,'') as twoCodeId,t.remarkDate,isnull(t.warnTelephone,'') as warnTelephone,t.remarkInfo,t.remarkLevel,t.process_time,t.process_remark,tc.companyName from TwoCodeRemark  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber  left join twoCodeYWEffective tye on te.ywCompanyId = tye.ywCompanyid  left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId where "+conditions;
		 */
		 
		 if(!"".equals(conditions)){
			  sql ="select isnull(t.checkadvice,'') as checkadvice,t.checkadviceTime,t.id,t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,isnull(t.twoCodeId,'') as twoCodeId,t.remarkDate,t.remarkInfo,isnull(t.warnTelephone,'') as warnTelephone,t.remarkLevel,t.process_time,t.process_remark,tc.companyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =te.townshipStreets and tc.type ='街道办') as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile from TwoCodeRemark  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber   left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId where te.address != '' and "+ conditions;
			  conditionsSql = "select count(1) from TwoCodeRemark t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber where te.address != '' and "+ conditions;
			 }
			 else{
			  sql ="select isnull(t.checkadvice,'') as checkadvice,t.checkadviceTime,t.id,t.registNumber,isnull(te.address,'') as address,isnull(te.buildingName,'') as buildingName,isnull(te.area,'') as area,isnull(t.twoCodeId,'') as twoCodeId,t.remarkDate,t.remarkInfo,isnull(t.warnTelephone,'') as warnTelephone,t.remarkLevel,t.process_time,t.process_remark,tc.companyName,(select tc.companyName from TwoCodeCompanyInfo tc where   tc.id =te.townshipStreets and tc.type ='街道办') as jdbCompanyName,(select userName from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as userName,(select telephonemobile from TCuserInfo where id = (select userId from YwManagerInfo where id =(select max(id) from YwManagerInfo where registNumber = t.registNumber) ) ) as telephonemobile from TwoCodeRemark  t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber   left join TwoCodeCompanyInfo tc on tc.id =te.ywCompanyId where te.address != ''";
			  conditionsSql = "select count(1) from TwoCodeRemark t left join TwoCodeElevatorInfo te on t.registNumber =te.registNumber where te.address != ''";
						 
			 }
		 
		 long total =RemarkInfoVO.countBySql(RemarkInfoVO.class, conditionsSql, null);
		  List<RemarkInfoVO> items=RemarkInfoVO.findBySql(RemarkInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
			
		  Map<String, Object> result = new HashMap<String, Object>();
		  
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		
		
		 
	 }
	 
    public String shenheByid(int id,String map_x,String map_y){
    	 String result = "failure";
    	 int num = 0;
    	 int num2 =0;
    	 
    	 try {
    		 ActiveRecordBase.beginTransaction();
 			num = YwInfo.updateAll(YwInfo.class, "ywstatus=?", new Object[]{"1"}, "id=?", new Object[] { id});
 			if(num>0){
 				YwInfoVO ywInfoVO =(YwInfoVO)YwInfoVO.findFirstBySql(YwInfoVO.class, "select t.registNumber  from YwManagerInfo t where t.id=?", new Object[] { id});
 				if(ywInfoVO != null){
 				num2 =ElevaltorInfo.updateAll(ElevaltorInfo.class, "map_X=?,map_Y=?", new Object[]{map_x,map_y}, "registNumber=?", new Object[] {ywInfoVO.getRegistNumber()});
 				if(num2>0)
 				result = "success";
 				}
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
    
    public String shenheByid2(int id){
   	 String result = "failure";
   	 int num = 0;
   	 try {
			num = YwInfo.updateAll(YwInfo.class, "ywstatus=?", new Object[]{"1"}, "id=?", new Object[] { id});
			if(num>0){
				result = "success";
				}
			}
			
		catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			  e.printStackTrace();
		  } 
   	 return result;
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
    
    public String remarkDelete(RemarkInfoVO remarkInfoVO){
		 String result = "failure";
		 int id = remarkInfoVO.getId();
		 String registNumber =remarkInfoVO.getRegistNumber();
		 
		 RemarkInfoVO myRemarkInfoVO =null;
		 try {
			 myRemarkInfoVO = RemarkInfoVO.findFirst(RemarkInfoVO.class, "id = ? and registNumber = ?", new Object[]{id,registNumber});
			if(myRemarkInfoVO != null){   //删除评论信息
			
				myRemarkInfoVO.destroy();
				result = "success"; 
			}
			
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		 return result;
	 }
    
    
    public View replylistById( int id,int page, int rows){ 
		 String countSql="";
		 String sql ="";
		 
		 List<ReplyInfoVO> items = new ArrayList<ReplyInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
	 
		 long total=0;
		 countSql ="select count(*) from TwoCodeReplyTab  t    where t.rId = "+id;
		 sql ="select t.rId,t.replyinfo,t.replyTime,tc.userName  from TwoCodeReplyTab  t   left join tcuserinfo  tc on  t.replyUserid = tc.userid  where t.rId = "+id;
		 
		 try {
				
		     total =ReplyInfoVO.countBySql(ReplyInfoVO.class, countSql, null);
		     items = ReplyInfoVO.findBySql(ReplyInfoVO.class, sql, null,"t.id desc", rows, (page-1)*rows);
		
	 } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 
		 result.put("total", total);
		 result.put("rows", items);  
		 return new JsonView(result);	 
		 
	 }
	 
} 
