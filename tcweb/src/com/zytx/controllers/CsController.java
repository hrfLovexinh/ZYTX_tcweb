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
import com.zytx.models.UserInfoVO;
import com.zytx.models.YwInfoVO;
import com.zytx.models.YwQuaCredRatingVO;
import com.et.mvc.filter.AfterFilter;
import com.et.mvc.filter.AfterFilters;


public class CsController extends ApplicationController{ 
	
	
	 public View contractlist(int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 long total=0;
		 List<ContractInfoVO> items = null;
		 
		 String sql="select companyId from TCUserInfo ";
		 int contractCompanyId =0;
		 try {
			TCUserInfoView user =TCUserInfoView.findFirst(TCUserInfoView.class, "userid= ?", new Object[]{userid});
			if(user != null){   //设置合同所属的运维公司id
				contractCompanyId = user.getCompanyId();
			}
	
		 
		
			total  =ContractInfoVO.count(ContractInfoVO.class, null, null);
			String sql2 ="select t.* from TwoCodeYwContractinfo t where t.contractCompanyId = ?";
			items = ContractInfoVO.findBySql(ContractInfoVO.class, sql2, new Object[]{contractCompanyId}, "t.id desc", rows, (page-1)*rows);
		    	
		} catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		 
	 }
	
	 
	 public String add(ContractInfo contractInfo){ System.out.println("csadd");
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int contractCompanyId =0;
		 String result = "failure";
		 boolean flag = false;
		 try {
			 ActiveRecordBase.beginTransaction();
			TCUserInfoView user =TCUserInfoView.findFirst(TCUserInfoView.class, "userid= ?", new Object[]{userid});
			if(user != null){   //设置合同所属的运维公司id
				contractCompanyId = user.getCompanyId();
				contractInfo.setContractCompanyId(contractCompanyId);
			}
	
				flag =contractInfo.save();
				if(flag){
					 result ="success"; 
					 ActiveRecordBase.commit();
				 }
				else{
					ActiveRecordBase.rollback();
				}
				
			} catch (Exception e) {
				super.dbrollback();
			}
			 	
		 return result;
	 }
	
	 public View query(ContractInfoVO info,int page, int rows){
		 String contractNumber ="";
		 String contractAttribute ="";
		 String contractCustomerName="";
		 String qstartTime ="";
		 String qendTime=""; 
		 
		 contractNumber ="%"+info.getContractNumber()+"%";
		 contractAttribute =info.getContractAttribute();
		 contractCustomerName ="%"+info.getContractCustomerName()+"%";
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 
		 String sql="";
		 Object[] param=null;
		 
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int contractCompanyId =0;
		 long total =0;
		 List<ContractInfoVO> items =null;
		 Map<String, Object> result = new HashMap<String, Object>();
		 try {
			TCUserInfoView user =TCUserInfoView.findFirst(TCUserInfoView.class, "userid= ?", new Object[]{userid});
			if(user != null){   //设置合同所属的运维公司id
				contractCompanyId = user.getCompanyId();
			}
		 if("".equals(qendTime)){
		     sql  ="select t.id,t.contractNumber,t.contractName,t.contractAttribute,t.contractCompanyName,t.contractCustomerName,t.contractSigneddate,t.contractEnddate from TwoCodeYwContractinfo t where t.contractNumber like ? and t.contractAttribute = ? and t.contractCustomerName like ? and t.contractSigneddate >= ?  and t.contractCompanyId = ?";
		     param = new Object[] { contractNumber, contractAttribute,contractCustomerName,qstartTime,contractCompanyId};
		 }
		 else{
			 sql  ="select t.id,t.contractNumber,t.contractName,t.contractAttribute,t.contractCompanyName,t.contractCustomerName,t.contractSigneddate,t.contractEnddate from TwoCodeYwContractinfo t where t.contractNumber like ? and t.contractAttribute = ? and t.contractCustomerName like ? and t.contractSigneddate >= ? and t.contractSigneddate <= ? and t.contractCompanyId = ?";
			 param = new Object[] { contractNumber, contractAttribute,contractCustomerName,qstartTime,qendTime,contractCompanyId};
				 
		 }
		
		    items =ContractInfoVO.findBySql(ContractInfoVO.class, sql, param, "t.id desc", rows, (page-1)*rows);
			if(items != null){
				total = items.size();	
				
			}
					
		} catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		result.put("total", total);
		result.put("rows", items);
		return new JsonView(result);
		 
	 }
	 
	 public View  contractPaymentList(int id,int page, int rows){
		 
		  long total=0;
		  List<ContractPaymentInfoVO> items = null;
		  String sql ="select count(*) from  TwoCodeYwContractPaymentinfo where contractinfoId  = ? ";
		  String sql2 ="select category,paymentiname,actualAmount,conditiondate,directionPayment from TwoCodeYwContractPaymentinfo where contractinfoId  = ? ";
		  try {
			total = ContractPaymentInfoVO.countBySql(ContractPaymentInfoVO.class, sql, new Object[]{id});
			items = ContractPaymentInfoVO.findBySql(ContractPaymentInfoVO.class, sql2, new Object[]{id} , "id", rows, (page-1)*rows);
		  } catch (ActiveRecordException e) {
			
			e.printStackTrace();
		  } 
		  System.out.println("contractPaymentList");
		  Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", 0);
		  result.put("rows", items);
		  return new JsonView(result);
	 }
	 
	
	 public String paymentadd(ContractPaymentInfo contractPaymentInfo){
		System.out.println("paymentadd");
		String result = "failure";
		boolean flag = false;
		
		 try {
			ActiveRecordBase.beginTransaction();
				flag =contractPaymentInfo.save();
				if(flag){
					 result ="success"; 
					 ActiveRecordBase.commit();
				 }
				else{
					ActiveRecordBase.rollback();
				}
				
			} catch (Exception e) {
				super.dbrollback();
			}
			 	
		 return result;
	 }
	 
	 public View dthz(int ywcompanyId,int cqday){
		 Map<String, Object> result = new HashMap<String, Object>();
		 CSElevaltorInfoVO item =  null;
		 String sql ="";
		 sql = "select tc.id,(select count(*) from TwoCodeElevatorInfo where ywcompanyId = tc.id ) as dtzs,(select count(*) from tcuserinfo where companyId = tc.id) as rysl,(select count(*) from TwoCodeElevatorInfo where ywcompanyId = tc.id and dailingFlag =1) as dailingCount,(select count(*) from TwoCodeElevatorInfo where ywcompanyId = tc.id and shemiFlag =1) as shemiCount," +
		 		"(select COUNT(*) from TwoCodeElevatorInfo t left join  YwManagerInfo y on t.registNumber = y.registNumber where t.ywcompanyId = tc.id and t.dailingFlag =0 and  DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15) as ywcqCount," +
		 		"(select COUNT(*) from TwoCodeElevatorInfo t left join  YwManagerInfo y on t.registNumber = y.registNumber where t.ywcompanyId = tc.id and t.dailingFlag =0 and  DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > "+(15-cqday)+" and DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) < 16) as ywjjcqCount," +
		 				"(select COUNT(*) from TwoCodeRemark where process_type = 0 and  registNumber in (select registNumber from TwoCodeElevatorInfo where ywCompanyId = ?)) as remarkCount   from TwoCodeCompanyInfo tc where tc.id = ?";
		 try {
			item = CSElevaltorInfoVO.findFirstBySql(CSElevaltorInfoVO.class, sql, new Object[]{ywcompanyId,ywcompanyId});
		} catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 if(item != null){
		     result.put("dtzs", item.getDtzs());  
		     result.put("rysl", item.getRysl());  
		     result.put("rtpb", Integer.parseInt(item.getDtzs())/Integer.parseInt(item.getRysl()));
		     result.put("dailingCount", item.getDailingCount()); 
		     result.put("shemiCount", item.getShemiCount()); 
		     result.put("ywcqCount", item.getYwcqCount()); 
		     result.put("ywjjcqCount", item.getYwjjcqCount()); 
		     result.put("remarkCount", item.getRemarkCount()); 
		    
			 }
		 else{
			 result.put("dtzs", "");  
		     result.put("rysl", "");  
		     result.put("rtpb", ""); 
		     result.put("dailingCount", ""); 
		     result.put("shemiCount", ""); 
		     result.put("ywcqCount", ""); 
		     result.put("ywjjcqCount", ""); 
		     result.put("remarkCount", ""); 
		 }
			 return new JsonView(result);	
	 }
	 
	 public View dthz2(int ywcompanyId,int cqday){
		 Map<String, Object> result = new HashMap<String, Object>();
		 CSElevaltorInfoVO item =  null;
		 String sql ="";
		 sql = "select tc.id,(select count(*) from TwoCodeElevatorInfo where ywcompanyId = tc.id ) as dtzs,(select count(*) from tcuserinfo where companyId = tc.id) as rysl,(select count(*) from TwoCodeElevatorInfo where ywcompanyId = tc.id and dailingFlag =1) as dailingCount,(select count(*) from TwoCodeElevatorInfo where ywcompanyId = tc.id and shemiFlag =1) as shemiCount," +
		 		"(select COUNT(*) from TwoCodeElevatorInfo t left join  YwManagerInfo y on t.registNumber = y.registNumber where t.ywcompanyId = tc.id and t.dailingFlag =0 and  DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > 15) as ywcqCount," +
		 		"(select COUNT(*) from TwoCodeElevatorInfo t left join  YwManagerInfo y on t.registNumber = y.registNumber where t.ywcompanyId = tc.id and t.dailingFlag =0 and  DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) > "+(15-cqday)+" and DATEDIFF(dd,convert(datetime,y.endTime,120),getdate()) < 16) as ywjjcqCount," +
		 				"(select COUNT(*) from TwoCodeRemark where process_type = 0 and  registNumber in (select registNumber from TwoCodeElevatorInfo where ywCompanyId = ?)) as remarkCount," +
		 				"(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='锦江区') as jjdtCount," +
		 		       "(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='青羊区') as qydtCount," +
		 		       "(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='金牛区') as jndtCount," +
		 		       "(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='武侯区') as whdtCount," +
		 		       "(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='成华区') as chdtCount," +
		 		       "(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='高新区') as gxdtCount," +
		 		       "(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='天府新区') as tfxdtCount," +
		 		       "(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='龙泉驿区') as lqydtCount," +
		 		       "(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='青白江区') as qbjdtCount," +
		 		       "(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='新都区') as xddtCount," +
		 		       "(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='温江区') as wjdtCount," +
		 		       "(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='金堂县') as jtdtCount," +
		 		       "(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='双流区') as sldtCount," +
		 		       "(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='郫都区') as pddtCount," +
		 		       "(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='大邑县') as dydtCount," +
		 		       "(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='蒲江县') as pjdtCount," +
		 		       "(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='新津县') as xjdtCount," +
		 		       "(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='都江堰市') as djydtCount," +
		 		       "(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='彭州市') as pzdtCount," +
		 		       "(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='邛崃市') as qldtCount," +
		 		       "(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='崇州市') as czdtCount," +
		 		       "(select COUNT(*) from TwoCodeElevatorInfo  where ywCompanyId =tc.id and area ='简阳市') as jydtCount   from TwoCodeCompanyInfo tc where tc.id = ?";
		 try {
			item = CSElevaltorInfoVO.findFirstBySql(CSElevaltorInfoVO.class, sql, new Object[]{ywcompanyId,ywcompanyId});
		} catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 if(item != null){
		     result.put("dtzs", item.getDtzs());  
		     result.put("rysl", item.getRysl());  
		     result.put("rtpb", Integer.parseInt(item.getDtzs())/Integer.parseInt(item.getRysl()));
		     result.put("dailingCount", item.getDailingCount()); 
		     result.put("shemiCount", item.getShemiCount()); 
		     result.put("ywcqCount", item.getYwcqCount()); 
		     result.put("ywjjcqCount", item.getYwjjcqCount()); 
		     result.put("remarkCount", item.getRemarkCount()); 
		     
		     result.put("jjdtCount", item.getJjdtCount()); 
		     result.put("qydtCount", item.getQydtCount());
		     result.put("jndtCount", item.getJndtCount());
		     result.put("whdtCount", item.getWhdtCount());
		     result.put("chdtCount", item.getChdtCount());
		     result.put("gxdtCount", item.getGxdtCount());
		     result.put("tfxdtCount", item.getTfxdtCount());
		     result.put("lqydtCount", item.getLqydtCount());
		     result.put("qbjdtCount", item.getQbjdtCount());
		     result.put("xddtCount", item.getXddtCount());
		     result.put("wjdtCount", item.getWjdtCount());
		     result.put("pddtCount", item.getPddtCount());
		     result.put("sldtCount", item.getSldtCount());
		     
		     result.put("jtdtCount", item.getJtdtCount());
		     result.put("dydtCount", item.getDydtCount());
		     result.put("pjdtCount", item.getPjdtCount());
		     result.put("xjdtCount", item.getXjdtCount());
		     result.put("djydtCount", item.getDjydtCount());
		     result.put("pzdtCount", item.getPzdtCount());
		     result.put("qldtCount", item.getQldtCount());
		     result.put("czdtCount", item.getCzdtCount());
		     result.put("jydtCount", item.getJydtCount());
		     
		     result.put("yqcdtCount",item.getJjdtCount()+item.getQydtCount()+item.getJndtCount()+item.getWhdtCount()+item.getChdtCount()+item.getGxdtCount()+item.getTfxdtCount());
		     result.put("erqcdtCount",item.getLqydtCount()+item.getQbjdtCount()+item.getXddtCount()+item.getWjdtCount()+item.getPddtCount()+item.getSldtCount());
		     result.put("sqcdtCount",item.getJtdtCount()+item.getDydtCount()+item.getPjdtCount()+item.getPjdtCount()+item.getDjydtCount()+item.getPzdtCount()+item.getQldtCount()+item.getCzdtCount()+item.getJydtCount());
		     
		    
			 }
		 else{
			 result.put("dtzs", "");  
		     result.put("rysl", "");  
		     result.put("rtpb", ""); 
		     result.put("dailingCount", ""); 
		     result.put("shemiCount", ""); 
		     result.put("ywcqCount", ""); 
		     result.put("ywjjcqCount", ""); 
		     result.put("remarkCount", ""); 
		     result.put("jjdtCount", ""); 
		     result.put("qydtCount", "");
		     result.put("jndtCount", "");
		     result.put("whdtCount", "");
		     result.put("chdtCount", "");
		     result.put("gxdtCount", "");
		     result.put("tfxdtCount", "");
		     result.put("lqydtCount", "");
		     result.put("qbjdtCount", "");
		     result.put("xddtCount", "");
		     result.put("wjdtCount", "");
		     result.put("jtdtCount", "");
		     result.put("sldtCount", "");
		     result.put("pddtCount", "");
		     result.put("dydtCount", "");
		     result.put("pjdtCount", "");
		     result.put("xjdtCount", "");
		     result.put("djydtCount", "");
		     result.put("pzdtCount", "");
		     result.put("qldtCount", "");
		     result.put("czdtCount", "");
		     result.put("jydtCount","");
		     result.put("yqcdtCount","");
		     result.put("erqcdtCount","");
		     result.put("sqcdtCount","");
		 }
			 return new JsonView(result);	
	 }
	 
	 
	 public View ywxyhz(int ywcompanyId){
		 List<CSElevaltorInfoVO> items = new ArrayList<CSElevaltorInfoVO>();
		 Map<String, Object> result = new HashMap<String, Object>();
		 String sql ="";
		 sql = "select top 12 ywCompanyID,ratingDate,tScore,tSort from TwoCodeYwQuaCredRatingTotal where ywCompanyID = ? order by ratingDate asc";
		 try {
			 items = CSElevaltorInfoVO.findBySql(CSElevaltorInfoVO.class, sql, new Object[]{ywcompanyId});
			} catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}
		 result.put("ywxyhz", items);  
		 return new JsonView(result);
	 }
	 
	 
	 public View ywkp(int ywcompanyId,String ratingDate){
		 CSElevaltorInfoVO item =  null;
		 Map<String, Object> result = new HashMap<String, Object>();
		 String sql ="";
		 sql = "select ratingDate,(tScore-1500) as tScore,tSort from TwoCodeYwQuaCredRatingTotal where ywCompanyID = ? and ratingDate  = ?";
		 try {
			 item = CSElevaltorInfoVO.findFirstBySql(CSElevaltorInfoVO.class, sql, new Object[]{ywcompanyId,ratingDate});
		 } catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}	
	//	 result.put("ywkphz", item);
	    if(item != null){
	     result.put("ratingDate", item.getRatingDate());
	     result.put("tScore", item.gettScore());
	     result.put("tSort", item.gettSort());	
		
	    }
	    else{
	    	 result.put("ratingDate", ratingDate);
	 	     result.put("tScore", "");
	 	     result.put("tSort", "");
	    }
	    return new JsonView(result);
		 
	 }
	 
	 
	 public View kpDetail(int ywcompanyId,String ratingDate){
		 YwQuaCredRatingVO item =  null;
		 Map<String, Object> result = new HashMap<String, Object>();
		 String sql ="";
		 sql = "select * from  TwoCodeYwQuaCredRating where ywCompanyID = ? and ratingDate  = ? and ratingType =0";
		 try {
			 item = YwQuaCredRatingVO.findFirstBySql(YwQuaCredRatingVO.class, sql, new Object[]{ywcompanyId,ratingDate});
		 } catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}	
		 result.put("ywkpDetail", item);
	   
	    return new JsonView(result);
		 
	 }
	 
	 public View ywkptable(int ywcompanyId,int page, int rows){
		 List<CSElevaltorInfoVO> items = null;
		 Map<String, Object> result = new HashMap<String, Object>();
		 String sql ="";
		 long total=0;
		 sql = "select ratingDate, tScore,tSort from TwoCodeYwQuaCredRatingTotal where ywCompanyID = ?";
		 try {
			 total = CSElevaltorInfoVO.countBySql(CSElevaltorInfoVO.class, "select count(*) from TwoCodeYwQuaCredRatingTotal where ywCompanyID = ?", new Object[]{ywcompanyId});
			 items = CSElevaltorInfoVO.findBySql(CSElevaltorInfoVO.class, sql, new Object[]{ywcompanyId},null, rows, (page-1)*rows);
		 } catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}	
		 
		 
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    
	    
	 }
	 
	 public View ywkptablequery(int ywcompanyId,String ratingDate,int page, int rows){
		 List<CSElevaltorInfoVO> items = null;
		 Map<String, Object> result = new HashMap<String, Object>();
		 String sql ="";
		 String conditions ="";
		 String countSql="";
		 long total=0;
		 
		 if(!"".equals(ratingDate)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and ratingDate = ' "+ratingDate+"'";
			 }
			 else
				 conditions =" ratingDate = '"+ratingDate+"'";	
			 
		 }
			 
		 if(!"".equals(conditions)){
		    sql = "select ratingDate, tScore,tSort from TwoCodeYwQuaCredRatingTotal where ywCompanyID = ? and " + conditions;
		    countSql ="select count(*) from TwoCodeYwQuaCredRatingTotal where ywCompanyID = ? and " + conditions;
		 }
		 else{
			 sql = "select ratingDate, tScore,tSort from TwoCodeYwQuaCredRatingTotal where ywCompanyID = ? "; 
			 countSql ="select count(*) from TwoCodeYwQuaCredRatingTotal where ywCompanyID = ? ";
		 }
		 try {
			 total = CSElevaltorInfoVO.countBySql(CSElevaltorInfoVO.class, countSql, new Object[]{ywcompanyId});
			 items = CSElevaltorInfoVO.findBySql(CSElevaltorInfoVO.class, sql, new Object[]{ywcompanyId},null, rows, (page-1)*rows);
		 } catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}	
		 
		 
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    
	    
	 }
		
} 
