package com.zytx.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.exception.ActiveRecordException;
import com.et.ar.exception.TransactionException;
import com.et.mvc.Controller;
import com.et.mvc.JsonView;
import com.et.mvc.MultipartFile;
import com.et.mvc.MultipartRequest;
import com.et.mvc.View;
import com.et.mvc.filter.BeforeFilter;
import com.zytx.converters.DateConverter;
import com.zytx.converters.TimestampConverter;
import com.zytx.init.GlobalFunction;
import com.zytx.models.CompanyChangeInfo;
import com.zytx.models.CompanyInfo;
import com.zytx.models.CompanyInfoNew;
import com.zytx.models.CompanyInfoVO;
import com.zytx.models.DdElevaltorInfoVO;
import com.zytx.models.EleChangeInfoVO;
import com.zytx.models.ImageVO;
import com.zytx.models.CarDevCard;
import com.zytx.models.TCUserInfoView;
import com.zytx.models.TwoCodeLogInfoVO;
import com.zytx.models.TwoCodeNewsInfoVO;
import com.zytx.models.TwoCodePasteRelease;
import com.zytx.models.TwoCodePasteReleaseVO;
import com.zytx.models.UserExtInfo;
import com.zytx.models.UserInfo;
import com.zytx.models.ImageInfo;
import com.zytx.models.ImageInfo2;
import com.zytx.models.UserInfoVO;
import com.zytx.models.YwCompanyInfo;
import com.zytx.models.YwCompanyInfoVO;

public class CompanyController extends ApplicationController{  
	
	public View companylist(int page, int rows) throws Exception{
		
	 	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 
		 long total=0;
		 List<CompanyInfoNew> items = null;
	     if(role==2){ //系统管理员 TwoCodeCompanyInfo
	    	 total  = CompanyInfoNew.count(CompanyInfoNew.class, null, null);
	    //	 items = CompanyInfo.findAll(CompanyInfo.class);
	    	 String sql ="select t.isyanshi,t.id as id, isnull(t.companyCode,'') as companyCode,isnull(t.companyName,'') as companyName,isnull(t.address,'') as address,isnull(t.phone,'') as phone,isnull(t.contact,'') as contact,isnull(t.zip,'') as zip,isnull(t.certificateName,'') as certificateName,isnull(t.certificateCode,'') as certificateCode,t.type,t.ischangeFlag,isnull(t.telephone,'') as telephone,isnull(t.qualification,'') as qualification,isnull(t.validity,'') as validity,isnull(t.qlevel,'') as qlevel,isnull(t.representativor,'') as representativor from TwoCodeCompanyInfo t";
	    	
	    //	 items = CompanyInfo.findAll(CompanyInfo.class, null, null, null, rows, (page-1)*rows);
	    	 items = CompanyInfoNew.findBySql(CompanyInfoNew.class, sql, null, null, rows, (page-1)*rows);
	     }
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	
	public View zjtypecompanylist(String type,int page, int rows) throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 int userid = userinfo.getId();
		
		 long total=0;
		 List<CompanyInfo> items = null;
		 if(!"".equals(type))
	     if(role==22 || role == 23){ //系统管理员 TwoCodeCompanyInfo
	    	 total  = CompanyInfo.count(CompanyInfo.class, "type = ? and ispasteyw =? and companyName != ?", new Object[]{type,0,"未知"});
	    //	 items = CompanyInfo.findAll(CompanyInfo.class);
	    	 String sql ="select t.hmdFlag, t.id as id, isnull(t.companyCode,'') as companyCode,isnull(t.companyName,'') as companyName,isnull(t.address,'') as address,isnull(t.phone,'') as phone,isnull(t.contact,'') as contact,isnull(t.zip,'') as zip,isnull(t.certificateName,'') as certificateName,isnull(t.certificateCode,'') as certificateCode,t.type,t.ischangeFlag,isnull(t.telephone,'') as telephone,isnull(t.qualification,'') as qualification,isnull(t.validity,'') as validity,isnull(t.qlevel,'') as qlevel,isnull(t.representativor,'') as representativor from TwoCodeCompanyInfo t where t.type = ? and t.ispasteyw =0 and t.companyName!='未知' ";
	    //	 items = CompanyInfo.findAll(CompanyInfo.class, null, null, null, rows, (page-1)*rows);
	    	 items = CompanyInfo.findBySql(CompanyInfo.class, sql, new Object[]{type}, "t.id desc", rows, (page-1)*rows);
	     }
		 
		 if(role==10 || role == 11){ //区县质监
			   String companyArea="";
			   String sql="";
			   UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
			      if(userInfoVO != null){
			          companyArea = userInfoVO.getArea();
			          }	 
	     if("维保".equals(type)){
	     total  = CompanyInfo.count(CompanyInfo.class, "type = ? and ispasteyw =? and companyName != ? and id in (select ywCompanyId from TwoCodeElevatorInfo where area = ? group by ywCompanyId )", new Object[]{type,0,"未知",companyArea});
	   	 sql ="select t.id as id, isnull(t.companyCode,'') as companyCode,isnull(t.companyName,'') as companyName,isnull(t.address,'') as address,isnull(t.phone,'') as phone,isnull(t.contact,'') as contact,isnull(t.zip,'') as zip,isnull(t.certificateName,'') as certificateName,isnull(t.certificateCode,'') as certificateCode,t.type,t.ischangeFlag,isnull(t.telephone,'') as telephone,isnull(t.qualification,'') as qualification,isnull(t.validity,'') as validity,isnull(t.qlevel,'') as qlevel,isnull(t.representativor,'') as representativor from TwoCodeCompanyInfo t where t.type = ?  and t.ispasteyw =0  and t.companyName!='未知' and t.id in (select ywCompanyId from TwoCodeElevatorInfo where area = ? group by ywCompanyId ) ";
	 //   	 String sql ="select t.id as id, isnull(t.companyCode,'') as companyCode,isnull(t.companyName,'') as companyName,isnull(t.address,'') as address,isnull(t.phone,'') as phone,isnull(t.contact,'') as contact,isnull(t.zip,'') as zip from TwoCodeCompanyInfo t where t.type = ?  and t.ispasteyw =0  and t.companyName!='未知' and t.id in (select ywCompanyId from TwoCodeElevatorInfo where area = ? group by ywCompanyId ) ";
	     }
	     if("使用".equals(type)){
	    	 total  = CompanyInfo.count(CompanyInfo.class, "type = ? and ispasteyw =? and companyName != ? and id in (select wgCompanyId from TwoCodeElevatorInfo where area = ? group by wgCompanyId )", new Object[]{type,0,"未知",companyArea});
		   	 sql ="select t.id as id, isnull(t.companyCode,'') as companyCode,isnull(t.companyName,'') as companyName,isnull(t.address,'') as address,isnull(t.phone,'') as phone,isnull(t.contact,'') as contact,isnull(t.zip,'') as zip,isnull(t.certificateName,'') as certificateName,isnull(t.certificateCode,'') as certificateCode,t.type,t.ischangeFlag,isnull(t.telephone,'') as telephone,isnull(t.qualification,'') as qualification,isnull(t.validity,'') as validity,isnull(t.qlevel,'') as qlevel,isnull(t.representativor,'') as representativor from TwoCodeCompanyInfo t where t.type = ?  and t.ispasteyw =0  and t.companyName!='未知' and t.id in (select wgCompanyId from TwoCodeElevatorInfo where area = ? group by wgCompanyId ) ";
		 
	     }
	    	 items = CompanyInfo.findBySql(CompanyInfo.class, sql, new Object[]{type,companyArea}, "t.id desc", rows, (page-1)*rows);
	     }
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	 
	 
	 public String add(CompanyInfo companyInfo){
		 System.out.println("companyInfo---"+companyInfo.getIspasteyw());
		 String result = "failure";
		 CompanyInfo companyInfoExit = null;
		 try {
			 companyInfoExit =CompanyInfo.findFirst(CompanyInfo.class, "companyName=? and type=?", new Object[] {companyInfo.getCompanyName(),companyInfo.getType()});
			 if(companyInfoExit != null){
				 result = "exist";
				 return result;
			 }else{
				 companyInfo.setIschangeFlag(0);
				 
				if(!"街道办".equals(companyInfo.getType())){
					companyInfo.setParea("");
				}
		        if(companyInfo.save())
			     result ="success";
		      }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return result;
	 }
	 
	 public View edit(int id)throws Exception{ 
		 System.out.println("edit---id:"+id);
		 String sql ="select t.ispasteyw,isnull(t.companyCode,'') as companyCode, isnull(t.companyName,'') as companyName, isnull(t.address,'') as address,isnull(t.phone,'') as phone,isnull(t.contact,'') as contact,isnull(t.zip,'') as zip,isnull(t.certificateName,'') as certificateName,isnull(t.certificateCode,'') as certificateCode,isnull(t.type,'') as type,isnull(t.telephone,'') as telephone,isnull(t.qualification,'') as qualification,isnull(t.validity,'') as validity,isnull(t.qlevel,'') as qlevel,isnull(t.representativor,'') as representativor   from TwoCodeCompanyInfo t where t.id = ?";
		 CompanyInfo companyInfo = CompanyInfo.findFirstBySql(CompanyInfo.class, sql, new Object[] { id });
		 Map<String, Object> result = new HashMap<String, Object>();
		 if(companyInfo != null){ 
			 result.put("companyCode", companyInfo.getCompanyCode());
			 result.put("companyName", companyInfo.getCompanyName());
			 result.put("address", companyInfo.getAddress());
			 result.put("phone", companyInfo.getPhone());
			 result.put("contact", companyInfo.getContact());
			 result.put("zip", companyInfo.getZip());
			 result.put("certificateName", companyInfo.getCertificateName());
			 result.put("certificateCode", companyInfo.getCertificateCode());
			 result.put("type", companyInfo.getType());
			 result.put("ispasteyw", companyInfo.getIspasteyw());
			 result.put("telephone", companyInfo.getTelephone());
			 result.put("qualification", companyInfo.getQualification());
			 result.put("validity", companyInfo.getValidity());
			 result.put("qlevel", companyInfo.getQlevel());
			 result.put("representativor", companyInfo.getRepresentativor());
			 System.out.println("ispasteyw---->"+ companyInfo.getIspasteyw());
		 }
		 return new JsonView(result);
	 }
	 
	 public String update(CompanyInfo companyInfo){
		 System.out.println("update---id:"+companyInfo.getId());
		 String result = "failure";
		 CompanyInfo companyInfoExit = null;
		 CompanyInfo oldCompanyInfo  = null;
		 String oldCompanyName ="";
		 String nowCompanyName ="";
		 nowCompanyName = companyInfo.getCompanyName();
		 int num = 0;
		 int num2=0;
		 MultipartFile picFile = null;
	//	 picFile =userPic;
		 
		 MultipartRequest req = (MultipartRequest)request; 
		 picFile = req.getFile("userPic"); 
		   
		 System.out.println("picFile---->"+picFile.getContentType());
		 System.out.println("picFile---->"+picFile.getSize());
		 if(picFile!= null && picFile.getSize()>0){ System.out.println("contentType--->"+picFile.getContentType());
		 try {  
			if(!"image/jpeg".equals(picFile.getContentType()) && !"image/pjpeg".equals(picFile.getContentType()))
			{
				result ="ImageType";
				return result;
			}
			if(picFile.getSize()>2000000)
			{
				result ="ImageSize";
				return result;
			}
			picFile.transferTo(new File(GlobalFunction.tcCompanyPath+"/"+companyInfo.getId()+".jpg"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 }
		 
		 try {
			  
			companyInfoExit =CompanyInfo.findFirst(CompanyInfo.class, "companyName=? and type=? and id !=?", new Object[] {companyInfo.getCompanyName(),companyInfo.getType(),companyInfo.getId()});
			 if(companyInfoExit != null){
				 result = "exist";
				 return result;
			 }
			 else{ 
				 oldCompanyInfo =CompanyInfo.findFirst(CompanyInfo.class, "id =?", new Object[] {companyInfo.getId()});
				 java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				 String s = format1.format(new Date());
				 num = CompanyInfo.updateAll(CompanyInfo.class, "companyCode=?,companyName=?,address=?,phone=?,contact=?,zip=?,certificateName=?,certificateCode=?,type=?,updateTime=?,ispasteyw=?,telephone=?,qualification=?,validity=?,qlevel=?,representativor=?", new Object[]{companyInfo.getCompanyCode(),companyInfo.getCompanyName(),companyInfo.getAddress(),companyInfo.getPhone(),companyInfo.getContact(),companyInfo.getZip(),companyInfo.getCertificateName(),companyInfo.getCertificateCode(),companyInfo.getType(),s,companyInfo.getIspasteyw(),companyInfo.getTelephone(),companyInfo.getQualification(),companyInfo.getValidity(),companyInfo.getQlevel(),companyInfo.getRepresentativor()}, "id=?", new Object[] {companyInfo.getId()});
				 if(num>0){
				 result = "success";
				 if(oldCompanyInfo != null){
					 oldCompanyName = oldCompanyInfo.getCompanyName();
					 System.out.println("nowCompanyName---"+nowCompanyName);
					 System.out.println("oldCompanyName---"+oldCompanyName);
					 if(!nowCompanyName.equals(oldCompanyName)){ //公司名称发生改变，要写公司名称变更表
						 System.out.println("公司名称变更");
						 CompanyChangeInfo companyChangeInfo = new CompanyChangeInfo();
						 companyChangeInfo.setCompanyid(companyInfo.getId());
						 companyChangeInfo.setOldName(oldCompanyName);
						 companyChangeInfo.setNowName(nowCompanyName);
						 companyChangeInfo.setUpdateTime(s);
						 if(companyChangeInfo.save()){ //写变更表成功后 ，更新基本表中ischangeFlag字段值
							num2= CompanyInfo.updateAll(CompanyInfo.class, "ischangeFlag=?", new Object[]{1}, "id=?", new Object[] {companyInfo.getId()});
							if(num2>0)
								System.out.println("公司名称标志修改成功！");
						 }
					 }
				 }
				 }
				 
			 }
		} catch (ActiveRecordException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		 return result;
	 }
	 
	 public View query(CompanyInfo info,int page, int rows)throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole(); 
		 int userid = userinfo.getId();
		 String companyCode ="";
		 String companyName ="";
		 String type ="";
		 
		
		 companyCode =info.getCompanyCode(); 
		 companyName =info.getCompanyName();
		 type=info.getType();
		 String conditions ="";
		 String conditionsSql="";
		 
		 if(!"".equals(companyName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.companyName like '%"+companyName+"%'";	 
		     }
		     else{   
		    	 conditions =" t.companyName like '%"+companyName+"%'";	
		      }
		 }
		 
		
		 if(!"".equals(companyCode)){ 
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.companyCode like '%"+companyCode+"%'";	 
		     }
		     else{   
		    	 conditions =" t.companyCode like '%"+companyCode+"%'";	
		      }
		 }
		 
		 if(!"".equals(type)){ 
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.type = '"+type+"'";	 
		     }
		     else{   
		    	 conditions =" t.type like '"+type+"'";	
		      }
		 }
		 
		 if(role==10 || role == 11){ //区县质监
			 if("使用".equals(type)){
			   String companyArea="";
			   UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
			      if(userInfoVO != null){
			          companyArea = userInfoVO.getArea();
			          }	 
		 
			  if(!"".equals(conditions)){
					 conditions =conditions+" and t.id in (select wgCompanyId from TwoCodeElevatorInfo where area = '"+companyArea+"' group by wgCompanyId ) " ;	 
					 conditionsSql ="select count(*) from TwoCodeCompanyInfo t  where "+ conditions+"  and t.companyName!='未知' ";
			  } 
				 else{
					 conditions =" t.id in (select wgCompanyId from TwoCodeElevatorInfo where area = '"+companyArea+"' group by wgCompanyId ) " ;
					 conditionsSql ="select count(*) from TwoCodeCompanyInfo t  where t.type ='使用'  and t.companyName!='未知' ";
				 }
			  
		  }
		 }
		 else{
			 if(!"".equals(conditions)){
				 conditionsSql ="select count(*) from TwoCodeCompanyInfo t  where "+ conditions+"  and t.companyName!='未知' "; 
				 
			 }
			 else{
				 conditionsSql ="select count(*) from TwoCodeCompanyInfo t  where  t.companyName!='未知' ";
			 }
			 
		 }
		 
		
		 
		 
		 String sql ="";
		 if(!"".equals(conditions))
		 {
		  sql ="select t.isyanshi,t.id as id, isnull(t.companyCode,'') as companyCode,isnull(t.companyName,'') as companyName,isnull(t.address,'') as address,isnull(t.phone,'') as phone,isnull(t.contact,'') as contact,isnull(t.zip,'') as zip,isnull(t.certificateName,'') as certificateName,isnull(t.certificateCode,'') as certificateCode,t.type,t.ischangeFlag,t.ispasteyw,isnull(t.telephone,'') as telephone,isnull(t.qualification,'') as qualification,isnull(t.validity,'') as validity,isnull(t.qlevel,'') as qlevel,isnull(t.representativor,'') as representativor from TwoCodeCompanyInfo t where " +conditions;
		 }
		 else{
		  sql ="select t.isyanshi,t.id as id, isnull(t.companyCode,'') as companyCode,isnull(t.companyName,'') as companyName,isnull(t.address,'') as address,isnull(t.phone,'') as phone,isnull(t.contact,'') as contact,isnull(t.zip,'') as zip,isnull(t.certificateName,'') as certificateName,isnull(t.certificateCode,'') as certificateCode,t.type,t.ischangeFlag,t.ispasteyw,isnull(t.telephone,'') as telephone,isnull(t.qualification,'') as qualification,isnull(t.validity,'') as validity,isnull(t.qlevel,'') as qlevel,isnull(t.representativor,'') as representativor from TwoCodeCompanyInfo t ";
		 }
		 
		 long total = CompanyInfoNew.countBySql(CompanyInfoNew.class, conditionsSql, null);
		 List<CompanyInfoNew>  items = CompanyInfoNew.findBySql(CompanyInfoNew.class, sql, null, "t.id desc", rows, (page-1)*rows);
		
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	 }
	 
	 public View zjtypequery(CompanyInfoVO info,int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole(); 
		 int userid = userinfo.getId();
		 String companyName ="";
		 String address = "";
		 String qstartTime ="";
		 String qendTime="";
		 
		 companyName =info.getCompanyName();
		 address = info.getAddress();
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		
		 String sql = "";
		 String conditions ="";
		 String conditionsSql="";
		
		 if(!"".equals(companyName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.companyName like '%"+companyName+"%'";	 
		     }
		     else{   
		    	 conditions =" t.companyName like '%"+companyName+"%'";	
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
		 
		 if(!"".equals(qstartTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.validity  >= '"+qstartTime+"'" ;	 
			 } 
			 else{
				 conditions =" t.validity  >= '"+qstartTime+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.validity  <= '"+qendTime+"'" ;	 
			 } 
			 else{
				 conditions =" t.validity  <= '"+qendTime+"'" ;	 
			 }
		 }
		 
		
	    long total=0;
	    List<CompanyInfoVO>  items =null;
		try {
	    if(role==10 || role == 11){ //区县质监
			   String companyArea="";
			   UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tc.area,tu.companyId from  TwoCodeUserExtInfo tu  left join TwoCodeCompanyInfo tc on tu.companyId = tc.id where tu.userid = ? ", new Object[] { userid });
			      if(userInfoVO != null){
			          companyArea = userInfoVO.getArea();
			          }	 
			      if(!"".equals(conditions)){
			    	  conditions =conditions+ " and  t.id in (select ywCompanyId from TwoCodeElevatorInfo where area = '"+companyArea+"' group by ywCompanyId )";
			      } 
			      else{
			    	  conditions = "   t.id in (select ywCompanyId from TwoCodeElevatorInfo where area = '"+companyArea+"' group by ywCompanyId )";
						 
			      }
	    }
	    
	    if(!"".equals(conditions)){
	    	sql ="select t.hmdFlag,t.id as id, isnull(t.companyCode,'') as companyCode,isnull(t.companyName,'') as companyName,isnull(t.address,'') as address,isnull(t.phone,'') as phone,isnull(t.contact,'') as contact,isnull(t.representativor,'') as representativor,isnull(t.zip,'') as zip,isnull(t.certificateName,'') as certificateName,isnull(t.certificateCode,'') as certificateCode,t.type,t.ischangeFlag,t.ispasteyw,isnull(t.telephone,'') as telephone,isnull(t.qualification,'') as qualification,isnull(t.validity,'') as validity,isnull(t.qlevel,'') as qlevel,isnull(t.representativor,'') as representativor from TwoCodeCompanyInfo t where  "+ conditions+"and t.type ='维保' and t.ispasteyw =0 and t.companyName!='未知' ";
	    	conditionsSql ="select count(*) from TwoCodeCompanyInfo t  where "+ conditions+"and t.type ='维保' and t.ispasteyw =0 and t.companyName!='未知' ";
	    }
	    else{
	    	sql ="select t.hmdFlag,t.id as id, isnull(t.companyCode,'') as companyCode,isnull(t.companyName,'') as companyName,isnull(t.address,'') as address,isnull(t.phone,'') as phone,isnull(t.contact,'') as contact,isnull(t.representativor,'') as representativor,isnull(t.zip,'') as zip,isnull(t.certificateName,'') as certificateName,isnull(t.certificateCode,'') as certificateCode,t.type,t.ischangeFlag,t.ispasteyw,isnull(t.telephone,'') as telephone,isnull(t.qualification,'') as qualification,isnull(t.validity,'') as validity,isnull(t.qlevel,'') as qlevel,isnull(t.representativor,'') as representativor from TwoCodeCompanyInfo t where  t.type ='维保' and t.ispasteyw =0 and t.companyName!='未知'";
	    	conditionsSql ="select count(*) from TwoCodeCompanyInfo t  where  t.type ='维保' and t.ispasteyw =0 and t.companyName!='未知'";
	
	    }
	    
	
			total = CompanyInfoVO.countBySql(CompanyInfoVO.class, conditionsSql, null);
		    items = CompanyInfoVO.findBySql(CompanyInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
		
		} catch (ActiveRecordException e) {	
			e.printStackTrace();
		}
		
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	 }
	 
	 public View wbtypequery(CompanyInfoVO info,int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String companyName ="";
		 String address = "";
		 String qstartTime ="";
		 String qendTime="";
		 int isBeian = -1;
		 
		 companyName =info.getCompanyName();
		 address = info.getAddress();
		 qstartTime =info.getQstartTime();
		 qendTime = info.getQendTime();
		 isBeian = info.getIsBeian();
		
		 String sql = "";
		 String conditions ="";
		 String conditionsSql="";
		 
		 long total=0;
		 List<YwCompanyInfoVO>  items =null;
		
		 if(!"".equals(companyName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.companyName like '%"+companyName+"%'";	 
		     }
		     else{   
		    	 conditions =" t.companyName like '%"+companyName+"%'";	
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
		 
		 if(!"".equals(qstartTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.validity  >= '"+qstartTime+"'" ;	 
			 } 
			 else{
				 conditions =" t.validity  >= '"+qstartTime+"'" ;	 
			 }
		 }
		 
		 if(!"".equals(qendTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.validity  <= '"+qendTime+"'" ;	 
			 } 
			 else{
				 conditions =" t.validity  <= '"+qendTime+"'" ;	 
			 }
		 }
		 if(isBeian == 1) {
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.isBeian="+isBeian;	 
			 } 
			 else{
				 conditions =" t.isBeian="+isBeian;	 
			 }
			 //未备案
		 } else if(isBeian == 2){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and (t.isBeian != 1 or t.isBeian is null) " ;	 
			 } 
			 else{
				 conditions =" (t.isBeian != 1 or t.isBeian is null) " ;	 
			 }
		 }
		 
		
		try {
	    if(!"".equals(conditions)){
	    	sql ="select t.hmdFlag as hmdFlag,t.id as id,t.isBeian as isBeian, isnull(t.companyCode,'') as companyCode,isnull(t.companyName,'') as companyName,isnull(t.address,'') as address,isnull(t.phone,'') as phone,isnull(t.contact,'') as contact,isnull(t.representativor,'') as representativor,isnull(t.certificateCode,'') as certificateCode,isnull(t.telephone,'') as telephone,isnull(t.validity,'') as validity,isnull(t.qlevel,'') as qlevel,t.area as area,te.filingDate as filingDate,te.registAddress as registAddress,te.representativorTel as representativorTel,te.contactTel as contactTel,te.officeProof as officeProof,te.safeyManPerson as safeyManPerson,te.type as type,te.filingPerson as filingPerson,te.filingPersonTel as filingPersonTel,te.note as note from TwoCodeCompanyInfo t left join TwoCodeYwCompanyInfo te on t.id=te.id where  "+ conditions+" and t.type ='维保' and t.ispasteyw =0 and t.companyName!='未知' ";
	    	conditionsSql ="select count(*) from TwoCodeCompanyInfo t  where "+ conditions+" and t.type ='维保' and t.ispasteyw =0 and t.companyName!='未知' ";
	    }
	    else{
	    	sql ="select t.hmdFlag as hmdFlag,t.id as id,t.isBeian as isBeian, isnull(t.companyCode,'') as companyCode,isnull(t.companyName,'') as companyName,isnull(t.address,'') as address,isnull(t.phone,'') as phone,isnull(t.contact,'') as contact,isnull(t.representativor,'') as representativor,isnull(t.certificateCode,'') as certificateCode,isnull(t.telephone,'') as telephone,isnull(t.validity,'') as validity,isnull(t.qlevel,'') as qlevel,t.area as area,te.filingDate as filingDate,te.registAddress as registAddress,te.representativorTel as representativorTel,te.contactTel as contactTel,te.officeProof as officeProof,te.safeyManPerson as safeyManPerson,te.type as type,te.filingPerson as filingPerson,te.filingPersonTel as filingPersonTel,te.note as note from TwoCodeCompanyInfo t left join TwoCodeYwCompanyInfo te on t.id=te.id where  t.type ='维保' and t.ispasteyw =0 and t.companyName!='未知'";
	    	conditionsSql ="select count(*) from TwoCodeCompanyInfo t  where  t.type ='维保' and t.ispasteyw =0 and t.companyName!='未知'";
	
	    }
	    
	
			total = CompanyInfoVO.countBySql(CompanyInfoVO.class, conditionsSql, null);
		    items = YwCompanyInfoVO.findBySql(YwCompanyInfoVO.class, sql, null, "t.id desc", rows, (page-1)*rows);
		
		} catch (ActiveRecordException e) {	
			e.printStackTrace();
		}
		
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	 }
	 
	 public View companyChangelist(int companyid,int page, int rows) throws Exception{
		 System.out.println("执行companyChangelist方法");
		 long total=0;
		 List<CompanyChangeInfo> items = null;
	     
	     total  = CompanyChangeInfo.count(CompanyChangeInfo.class, "companyid=?", new Object[]{companyid});
	  	 items = CompanyChangeInfo.findAll(CompanyChangeInfo.class, "companyid=?", new Object[]{companyid},"updateTime asc", rows, (page-1)*rows);
	    
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	 
	 
	 public String newsadd(String zjCompanyId,String newsTitle,String newsContext){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		
		 String result = "failure";
		 int newsSenderID =userinfo.getId();
		 String newsSender =userinfo.getLoginName();
		 System.out.println("newsSender---"+newsSender);
		 java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
		 String newsSendTime = format1.format(new Date()); 
		 System.out.println("zjCompanyId---"+zjCompanyId);
		 String[] ary = zjCompanyId.split("%2C"); 
		 System.out.println("ary--->"+ary.length);
		 String zjCompanyId2 ="";
		 for(String item: ary){  
			 zjCompanyId2 = zjCompanyId2+","+item; 
		 }
		 
		 if(!"".equals(zjCompanyId2))
			 zjCompanyId2 = zjCompanyId2.substring(1, zjCompanyId2.length());
		 
		 System.out.println("zjCompanyId2---"+zjCompanyId2);
		 try {
			    ActiveRecordBase.beginTransaction();
				ActiveRecordBase.execute("exec pro_zhijianSendnews ?,?,?,?,?,?", new Object[] { zjCompanyId2,newsTitle,newsContext,newsSenderID,newsSender,newsSendTime});
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
	 
	 public View zhijiannewslist(int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 long total=0;
		 int  companyId = 0;
	//	 List<TwoCodeNewsInfoVO> items = null; 
		 List<TwoCodeNewsInfoVO> items = new ArrayList<TwoCodeNewsInfoVO>();
		 try {
			if(role == 22 || role == 23){
			total  =TwoCodeNewsInfoVO.countBySql(TwoCodeNewsInfoVO.class, "select count(*) from TwoCodenewsinfo where newsinvaild = ?", new Object[]{0});
			String sql ="select id,newsSender,CONVERT(CHAR(19), newsSendTime, 20) as newsSendTime,newsTitle,newsContext from TwoCodenewsinfo where newsinvaild =0 ";
			items = TwoCodeNewsInfoVO.findBySql(TwoCodeNewsInfoVO.class, sql, null, "id desc", rows, (page-1)*rows);
			}
			if(role == 10 || role == 11){
				TCUserInfoView  userview =TCUserInfoView.findFirst(TCUserInfoView.class, "loginName = ?", new Object[]{userName});
				if(userview != null)
					companyId =userview.getCompanyId();
				if(companyId > 0){
					total  =TwoCodeNewsInfoVO.countBySql(TwoCodeNewsInfoVO.class, "select count(*) from TwoCodenewsReceiverinfo where newsReceiverID = ? and newsinvaild = 0 ", new Object[]{companyId});
					String sql ="select t.id,tr.id as id2,t.newsSender,CONVERT(CHAR(19), t.newsSendTime, 20) as newsSendTime,t.newsTitle,t.newsContext, (case when tr.newsState = 1 then '已读'  else '未读'  end )  as newsStateName from TwoCodenewsReceiverinfo tr left join  TwoCodenewsinfo t on  tr.newsID  = t.id  where tr.newsReceiverID = ? and tr.newsinvaild =0";
					items = TwoCodeNewsInfoVO.findBySql(TwoCodeNewsInfoVO.class, sql, new Object[]{companyId}, "t.id desc", rows, (page-1)*rows);	
				}
			}
		    	
		} catch (ActiveRecordException e) {	
			e.printStackTrace();
		}
		Map<String, Object> result = new HashMap<String, Object>();
		  if(items != null)
		  result.put("total", items.size());
		  else
	      result.put("total", total);
	      
		  result.put("rows", items);
		  return new JsonView(result);
		 
	 }
	 
	 public View newsinfotishi(int id,int id2){
		 Map<String, Object> result = new HashMap<String, Object>();
		 TwoCodeNewsInfoVO twoCodeNewsInfoVO =null;
		 String  newsdetailContext = "";
		 if(id >0){
			 try {
					
				    String sql ="select t.newsContext  from TwoCodenewsinfo t where t.id = ?";
				    twoCodeNewsInfoVO  =  TwoCodeNewsInfoVO.findFirstBySql(TwoCodeNewsInfoVO.class, sql, new Object[]{id});
					if(twoCodeNewsInfoVO != null){
						newsdetailContext =twoCodeNewsInfoVO.getNewsContext(); 
						ActiveRecordBase.execute("update TwoCodenewsReceiverinfo set newsState = 1 where id = ?", new Object[]{id2});
					}
				} catch (ActiveRecordException e) {
					e.printStackTrace();
				}
		 }
		 result.put("newsdetailContext", newsdetailContext);
		 return new JsonView(result);
	 }
	 
	 
	 public View zhijianreceiverlist(int id,int page, int rows){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 long total=0;
		 int  companyId = 0;
		 List<TwoCodeNewsInfoVO> items = null; 
		 try {
			if(role == 22 || role == 23){
			total  =TwoCodeNewsInfoVO.countBySql(TwoCodeNewsInfoVO.class, "select count(*) from TwoCodenewsReceiverinfo where newsID = ? ", new Object[]{id});
			String sql ="select tc.companyName as newsReceiverName, (case when tr.newsState = 1 then '已读'  else '未读'  end )  as newsStateName from TwoCodenewsReceiverinfo tr left join  TwoCodeCompanyInfo tc  on tr.newsReceiverID = tc.id where tr.newsID = ?";
			items = TwoCodeNewsInfoVO.findBySql(TwoCodeNewsInfoVO.class, sql, new Object[]{id}, "tr.id desc", rows, (page-1)*rows);
			}
		    	
		} catch (ActiveRecordException e) {	
			e.printStackTrace();
		}
		Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		 
	 }
	 
	 public String newsdelete(int id){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 String result = "failure";
		 try {
			if(role ==22 || role == 23)
			ActiveRecordBase.execute("update TwoCodenewsinfo set newsinvaild = 1 where id = ?", new Object[]{id});
			if(role ==10 || role == 11)
			ActiveRecordBase.execute("update TwoCodenewsReceiverinfo set newsinvaild = 1 where id = ?", new Object[]{id});
				
		    result ="success";
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		 
		 return result;
		 
	 }
	 
	 
	 public String newsIshave(String  loginName){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 long total=0;
		 int  companyId = 0;
		 String result = "failure";
		 try {
			if(role ==10 || role == 11){
				TCUserInfoView  userview =TCUserInfoView.findFirst(TCUserInfoView.class, "loginName = ?", new Object[]{loginName});
				if(userview != null)
					companyId =userview.getCompanyId();
				if(companyId > 0){
					total  =TwoCodeNewsInfoVO.countBySql(TwoCodeNewsInfoVO.class, "select count(*) from TwoCodenewsReceiverinfo where newsReceiverID = ? and newsState = 0 ", new Object[]{companyId});	
					if(total > 0)
					result ="success"; 
				}
				
		   
			}
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		 
		 return result;
		 
	 }
	 
	 public String updateIssueetotalById(CompanyInfoVO companyInfoVO){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 int  role = userinfo.getRole();
		 String result = "failure";
		 int companyId = companyInfoVO.getId();
		 int issueetotal = companyInfoVO.getIssueetotal();
		 try {
			int num=CompanyInfoVO.updateAll(CompanyInfoVO.class, "issueetotal = ?", new Object[]{issueetotal}, "id = ?", new Object[]{companyId});
			if(num > 0)
			{
				result ="success";
				return result;
			}
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		 
	 }
	 
	 public String pastereleaseadd(TwoCodePasteRelease pasterelease){
		 
		 String result = "failure";
		 try {
			
		      if(pasterelease.save())
			     result ="success";
		     
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return result;
	 }
	 
	 public View pastereleaselist(int id){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
	
		 List<TwoCodePasteReleaseVO> items = null; 
		 try {
			
		//	total  =TwoCodePasteReleaseVO.countBySql(TwoCodePasteReleaseVO.class, "select count(*) from TwoCodePasteReleaseTab where companyId = ? ", new Object[]{id});
			String sql ="select row_number() over(order by t.id asc) as indexId,t.id,tc.companyName as companyName,t.companyId, t.rcount,t.rhaoduan,t.receivor,t.receiveTime from TwoCodePasteReleaseTab t left join  TwoCodeCompanyInfo tc  on t.companyId = tc.id where t.companyId = ?";
			items = TwoCodePasteReleaseVO.findBySql(TwoCodePasteReleaseVO.class, sql, new Object[]{id}, null);
			
		    	
		} catch (ActiveRecordException e) {	
			e.printStackTrace();
		}
		Map<String, Object> result = new HashMap<String, Object>();
		  result.put("rows", items);
		  return new JsonView(result);
		 
	 }
	 
	 public String pastereleasedelete(TwoCodePasteReleaseVO vo){
	//	 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
	//	 int userid = userinfo.getId();
	//	 String userName =userinfo.getLoginName();
	//	 int  role = userinfo.getRole();
		 String result = "failure";
		 try {
			 TwoCodePasteReleaseVO tvo = TwoCodePasteReleaseVO.findFirst(TwoCodePasteReleaseVO.class, "id =? and companyId = ?", new Object[]{vo.getId(),vo.getCompanyId()});
			 if(tvo != null){
			   int num = 0;
			   num =tvo.destroy(); 
			   if(num > 0)
		       result ="success";
			   
			 }
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		 
		 return result;
		 
	 }
	 
	 public View pastereleaseedit(int id){ 	 
		 String sql ="select row_number() over(order by t.id asc) as indexId,t.id,tc.companyName as companyName,t.companyId, t.rcount,t.rhaoduan,t.receivor,t.receiveTime from TwoCodePasteReleaseTab t left join  TwoCodeCompanyInfo tc  on t.companyId = tc.id  where t.id = ?";
		 TwoCodePasteReleaseVO tprvo = null;
		try {
			tprvo = TwoCodePasteReleaseVO.findFirstBySql(TwoCodePasteReleaseVO.class, sql, new Object[] { id });
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 Map<String, Object> result = new HashMap<String, Object>();
		 if(tprvo != null){ 
			 result.put("companyId", tprvo.getCompanyId());
			 result.put("rcount", tprvo.getRcount());
			 result.put("rhaoduan", tprvo.getRhaoduan());
			 result.put("receivor", tprvo.getReceivor());
			 result.put("receiveTime", tprvo.getReceiveTime());
		 }
		 return new JsonView(result);
	 }
	 
	 public String pastereleaseupdate(TwoCodePasteRelease tpr){
		 String result = "failure";
		 int num = 0;
		 try {
				 num = TwoCodePasteRelease.updateAll(TwoCodePasteRelease.class, "rcount=?,rhaoduan=?,receivor=?,receiveTime=?", new Object[]{tpr.getRcount(),tpr.getRhaoduan(),tpr.getReceivor(),tpr.getReceiveTime()}, "id=?", new Object[] {tpr.getId()});
				 if(num>0){
				 result = "success";
				 }			 
			 }
		 catch (ActiveRecordException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		 return result;
	 }
	 
	 public String updateattributeById(int id,int isyanshi,String threedscanning,int isshengji){
    	 String result = "failure";
    	 int num = 0;
    	 int num2 = 0;
    	 
 		try {
 			 ActiveRecordBase.beginTransaction();
			num = CompanyInfoVO.updateAll(CompanyInfoVO.class,"isyanshi=?,threedscanning=?,isshengji=?", new Object[]{isyanshi,threedscanning,isshengji}, "id=?", new Object[] { id});
			if(num > 0)
			   num2	= UserExtInfo.updateAll(UserExtInfo.class,"isyanshi=?,threedscanning=?,isshengji=?", new Object[]{isyanshi,threedscanning,isshengji}, "companyId=?", new Object[] { id});
			result = "success";
			ActiveRecordBase.commit();
 		   } 		    
 		catch (ActiveRecordException e) {		
 			try {
				ActiveRecordBase.rollback();
			} catch (TransactionException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
				
			}
 				
    	 return result;
    }
	 
	 public View attributeById(int id){
		 CompanyInfoVO companyinfo =null;
	    	Map<String, Object> obj = new HashMap<String, Object>();
	    	String sql ="select  t.isyanshi,t.threedscanning,t.isshengji from TwoCodeCompanyInfo t where t.id = ?";
	    	try {
	    		companyinfo =CompanyInfoVO.findFirstBySql(CompanyInfoVO.class, sql, new Object[] {id});
				if(companyinfo != null){
					obj.put("isyanshi",companyinfo.getIsyanshi());
					obj.put("threedscanning",companyinfo.getThreedscanning());
					obj.put("isshengji", companyinfo.getIsshengji());
				}
			} catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return new JsonView(obj);
	    }
	 
	 /*
	 protected String carNumStrByUserName()throws Exception{
		 String carNumStr ="";
		 String qyID ="";
		 String qyStr ="";
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String userName =userinfo.getUSER_NAME();
		 System.out.println("ImageController---81---登陆名称---"+userName);
		 UserInfo user =UserInfo.findFirstBySql(UserInfo.class, "select * from UserInfo where USER_NAME= ?",new Object[] { userName });
	//	 UserInfo user =UserInfo.findFirstBySql(UserInfo.class, "select * from UserInfo where USER_NAME= ?",new Object[] { "tjt" });
		 if(user!=null){
			 qyID = user.getQY_ID();
			 List<ImageVO>  items = ImageVO.findBySql(ImageVO.class, "exec get_childid2  ?", new Object[] { qyID });
			 if(items!=null && items.size()>0){
			 Iterator<ImageVO> it =	items.iterator();
			 while(it.hasNext()){
				 ImageVO myvo =it.next();
				 qyStr+=myvo.getQy_id();
				 qyStr+=",";
			 }
			 }
		 }
		 if(!"".equals(qyStr)){
			 qyStr = qyStr.substring(0, qyStr.length()-1);
		 }
		 System.out.println("---qyStr---"+qyStr);
		
		 List<CarDevCard>  items2 = CarDevCard.findAll(CarDevCard.class, "QY_ID in "+"("+qyStr+")");
		 System.out.println("------"+items2.size());
		 if(items2!=null && items2.size()>0){
			 Iterator<CarDevCard> it2=items2.iterator();
			 while(it2.hasNext()){
				 CarDevCard carDevCard = it2.next();
				 carNumStr+="'"+carDevCard.getCarnum()+"'";
				 carNumStr+=",";
			 }
		 }
		 if(!"".equals(carNumStr)){
			 carNumStr =carNumStr.substring(0, carNumStr.length()-1);
		 }
		 System.out.println("---carNumStr---"+carNumStr);	
		 return carNumStr;
	 }
	 
	
	 public View query(ImageVO info,int page, int rows)throws Exception{
		 String carnum ="";
		 String dev_id ="";
		 String startTime ="";
		 String endTime ="";
		 
		 carnum ="%"+info.getCarnum()+"%";
		 dev_id ="%"+info.getDev_id()+"%";
		 startTime=info.getStartTime();
		 endTime=info.getEndTime();
		 System.out.println("查询条件1-----"+info.getCarnum());
		 
		 String conditions ="carnum like  ? and dev_id like ? and image_time >= ? ";
		 Object[] param;
		 if(!"".equals(endTime)){
			 conditions +=" and image_time <= ? ";
			 param = new Object[]{carnum,dev_id,startTime,endTime};
		 }
		 else{
			 param = new Object[]{carnum,dev_id,startTime};
		 }
		 
		 String carNumStr ="";
		 carNumStr=carNumStrByUserName();
		 if(!"".equals(carNumStr)){
			 carNumStr =" and carnum in "+"("+carNumStr+")";
			 conditions +=carNumStr;
		 }
		 System.out.println("最后查询条件-----"+conditions);
		 
		 String tableNameStr ="";
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		 String nowTime =String.valueOf(df.format(new Date()));
		 String startTime2=nowTime.substring(0, 7)+"-01 00:00:00";
		 String endTime2 =nowTime;
		 System.out.println("默认开始时间---"+startTime2+"---默认结束时间---"+endTime2);
		
		 if(!"".equals(startTime)){
			 startTime2 = startTime+" 00:00:00";
		 }
		 if(!"".equals(endTime)){
			 endTime2 = endTime+" 23:59:59";
		 }
		 System.out.println("输入开始时间---"+startTime2+"---输入结束时间---"+endTime2);
		 
		 List<ImageInfo2> tableList =ImageInfo2.findBySql(ImageInfo2.class, "exec getImgTables  ?,?", new Object[] { startTime2,endTime2 });
		 System.out.println("---tableList---"+tableList.size());
		 
		 int length =0;
		 length = tableList.size();
		 Object[] tableNameObject = new Object[length];
		 for(int i=0;i<length;i++){
			 tableNameObject[i]=tableList.get(i).getTablename();
			 System.out.println("第"+i+"个"+"表名---"+tableNameObject[i]);
		 }
		 
		 if(length==0){
		  Map<String, Object> result = new HashMap<String, Object>();	 
		  result.put("total", 0);
		  result.put("rows", null);
		  return new JsonView(result);
		 }
		 else{
		 String sqlStr =" select * from  "+tableNameObject[0];
		 String sqlStr2="";
		 for(int i=0;i<length-1;i++)
		 {
			 sqlStr +=" union all select * from "+tableNameObject[i+1];
		 }
		 sqlStr2 ="select * from ( "+sqlStr+" ) a where "+conditions;
		 sqlStr = "select count(*) from ( "+sqlStr+" ) a where "+conditions;
		 
		 System.out.println("最终sql---"+sqlStr);
	//	 System.arraycopy(param, 0,tableNameObject, length, param.length);
		 long total =ImageInfo2.countBySql(ImageInfo2.class, sqlStr, param);
		 List<ImageInfo2> items=ImageInfo2.findBySql(ImageInfo2.class, sqlStr2, param, null, rows, (page-1)*rows);
		
		
	   // long total = ImageInfo.count(ImageInfo.class, conditions, param);
	  //  List<ImageInfo>  items = ImageInfo.findAll(ImageInfo.class, conditions, param,null,rows, (page-1)*rows);	
		 
		  System.out.println("------"+items.size());
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		 }
	 }
	 
	 
	 public View edit(int id,String image_time)throws Exception{
		    System.out.println("--image_time--"+image_time);
		    
		    String tableName="image_"+image_time.substring(0, 4)+image_time.substring(5, 7);
		    String sql ="select * from " + tableName + " where data_id = ? ";
		    
		    ImageInfo2 imageInfo = ImageInfo2.findFirstBySql(ImageInfo2.class, sql, new Object[]{id});
		  //  ImageInfo imageInfo = ImageInfo.find(ImageInfo.class, id); 
	    	Map<String, Object> result = new HashMap<String, Object>();
	    	if(imageInfo!=null){
	    		
	    	if(imageInfo.getCarnum()==null)
	    		result.put("carnum", "");
	    	else
	    	result.put("carnum", imageInfo.getCarnum());
	    	
	    	if(imageInfo.getDev_id()==null)
	    		result.put("dev_id", "");
	    	else	
	    	result.put("dev_id", imageInfo.getDev_id());
	    	
	    	
	    	result.put("image_id", imageInfo.getImage_id());
	    	
	    	if(imageInfo.getImage_type()==0){
	    		result.put("image_type", "未知");
	    	}
	    	if(imageInfo.getImage_type()==1){
	    		result.put("image_type", "实时提取");
	    	}
	    	if(imageInfo.getImage_type()==2){
	    		result.put("image_type", "紧急报警图片");
	    	}
	    	if(imageInfo.getImage_type()==3){
	    		result.put("image_type", "事故报警图片");
	    	}
	    //	result.put("image_type", imageInfo.getImage_type());
	    	
	    	if(imageInfo.getImage_format()==0){
	    		result.put("image_format", "未知");	
	    	}
	    	
	    	if(imageInfo.getImage_format()==1){
	    		result.put("image_format", "352*288");	
	    	}
	    	
	    	if(imageInfo.getImage_format()==2){
	    		result.put("image_format", "640*480");	
	    	}
	    	
	    	if(imageInfo.getImage_time()==null)
	    		result.put("image_time", "");
	    	else
	    	result.put("image_time", (imageInfo.getImage_time()).substring(0, 19));
	    	
	    	if(imageInfo.getReceive_time()==null)
	    		result.put("receive_time", "");
	    	else
	    	result.put("receive_time", (imageInfo.getReceive_time()).substring(0, 19));
	    	
	    	if(imageInfo.getImage_path()==null)
	    	   result.put("image_path", "");
	    	else
	    	result.put("image_path", imageInfo.getImage_path());
	    	}
	    	return new JsonView(result);
	    }
	 
	 public String findImagePath(int id,String image_time)throws  Exception{
		 String tableName="image_"+image_time.substring(0, 4)+image_time.substring(5, 7);
		 String sql ="select * from " + tableName + " where data_id = ? ";
		    
		 ImageInfo2 imageInfo = ImageInfo2.findFirstBySql(ImageInfo2.class, sql, new Object[]{id});
	//	 ImageInfo imageInfo = ImageInfo.find(ImageInfo.class, id); 
		 if(imageInfo!=null){
			 return imageInfo.getImage_path();
		 }
		 return "";
	 }      */
	 
	 
	 //维保企业信息查询
	 public View wbBeian(int id) throws Exception {
		 System.out.println("id:"+id);
		 //Cookie[] cookies =  request.getCookies();
		 //String filingPerson = "0";
		// String filingPersonTel = "0";
		 //String userName = "";
		// UserInfoVO user = null;
			/*if (cookies != null) {
			   for (Cookie c : cookies) {
				if (c.getName().equals("filingPerson")) {
					filingPerson = URLDecoder.decode(c.getValue(), "utf-8");
			      }
				if (c.getName().equals("filingPersonTel")) {
					filingPersonTel = c.getValue();
			      }
			    }
	    }
		//获取登陆人信息
		if("0".equals(filingPerson) && "0".equals(filingPersonTel)) {
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
			user =UserInfoVO.findFirstBySql(UserInfoVO.class, "select isnull(te.contactPhone,'') as contactPhone,isnull(te.userName,'') as userName from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid where loginName= ?",new Object[] { userName });
			filingPerson = user.getUserName();
			filingPersonTel = user.getContactPhone();
		}*/
		 String sql ="select t.ispasteyw,isnull(t.companyCode,'') as companyCode, isnull(t.companyName,'') as companyName, isnull(t.address,'') as address,t.area,isnull(t.phone,'') as phone,isnull(t.contact,'') as contact,isnull(t.certificateCode,'') as certificateCode,isnull(t.telephone,'') as telephone,isnull(t.validity,'') as validity,isnull(t.qlevel,'') as qlevel,isnull(t.representativor,'') as representativor   from TwoCodeCompanyInfo t where t.id = ?";
		 CompanyInfoVO companyInfo = CompanyInfo.findFirstBySql(CompanyInfoVO.class, sql, new Object[] { id });
		 Map<String, Object> result = new HashMap<String, Object>();
		 if(companyInfo != null){ 
			 result.put("id", id);
			 result.put("companyCode", companyInfo.getCompanyCode());
			 result.put("companyName", companyInfo.getCompanyName());
			 result.put("address", companyInfo.getAddress());
			 result.put("phone", companyInfo.getPhone());
			 result.put("contact", companyInfo.getContact());
			 result.put("certificateCode", companyInfo.getCertificateCode());
			 result.put("ispasteyw", companyInfo.getIspasteyw());
			 result.put("telephone", companyInfo.getTelephone());
			 result.put("validity", companyInfo.getValidity());
			 result.put("qlevel", companyInfo.getQlevel());
			 result.put("representativor", companyInfo.getRepresentativor());
			 result.put("area", companyInfo.getArea());
			 //result.put("filingPerson",filingPerson);
			 //result.put("filingPersonTel",filingPersonTel);
			 System.out.println("ispasteyw---->"+ companyInfo.getIspasteyw());
		 }
		 return new JsonView(result);
	 }
	 
	 	//维保企业备案信息查询
		 public View wbBeiand(int id) throws Exception {
			 System.out.println("id:"+id);
			 String sql ="select t.registAddress,t.representativorTel,t.contactTel,t.officeProof,t.safeyManPerson,t.type,t.note,isnull(t.companyCode,'') as companyCode, isnull(t.companyName,'') as companyName, isnull(t.address,'') as address,t.area,isnull(t.phone,'') as phone,isnull(t.contact,'') as contact,isnull(t.certificateCode,'') as certificateCode,isnull(t.telephone,'') as telephone,isnull(t.validity,'') as validity,isnull(t.qlevel,'') as qlevel,isnull(t.representativor,'') as representativor   from TwoCodeYwCompanyInfo t where t.id = ?";
			 YwCompanyInfoVO companyInfo = YwCompanyInfoVO.findFirstBySql(YwCompanyInfoVO.class, sql, new Object[] { id });
			 Map<String, Object> result = new HashMap<String, Object>();
			 if(companyInfo != null){ 
				 result.put("id", id);
				 result.put("companyCode", companyInfo.getCompanyCode());
				 result.put("companyName", companyInfo.getCompanyName());
				 result.put("address", companyInfo.getAddress());
				 result.put("phone", companyInfo.getPhone());
				 result.put("contact", companyInfo.getContact());
				 result.put("certificateCode", companyInfo.getCertificateCode());
				 result.put("telephone", companyInfo.getTelephone());
				 result.put("validity", companyInfo.getValidity());
				 result.put("qlevel", companyInfo.getQlevel());
				 result.put("representativor", companyInfo.getRepresentativor());
				 result.put("area", companyInfo.getArea());
				 result.put("registAddress", companyInfo.getRegistAddress());
				 result.put("representativorTel", companyInfo.getRepresentativorTel());
				 result.put("contactTel", companyInfo.getContactTel());
				 result.put("officeProof", companyInfo.getOfficeProof());
				 result.put("safeyManPerson", companyInfo.getSafeyManPerson());
				 result.put("type", companyInfo.getType());
				 result.put("note", companyInfo.getNote());
				 //result.put("filingPerson",filingPerson);
				 //result.put("filingPersonTel",filingPersonTel);
			 }
			 return new JsonView(result);
		 }
	 
	 //企业维保信息备案
	 public String wbCompanyBa(YwCompanyInfo ywCompanyInfo) {
		 try{
			 String filingPerson = "";
			 String userName = "";
			 String filingPersonTel = "";
			 //Cookie newCookie = null;
			 Cookie[] cookies =  request.getCookies();
			 //更新公司信息
			 CompanyInfoVO.updateAll(CompanyInfoVO.class, "companyCode=?,companyName=?,address=?,phone=?,contact=?,certificateCode=?,telephone=?,validity=?,qlevel=?,representativor=?,area=?,isbeian=?", new Object[]{ywCompanyInfo.getCompanyCode(),
					 ywCompanyInfo.getCompanyName(),ywCompanyInfo.getAddress(),ywCompanyInfo.getPhone(),ywCompanyInfo.getContact(),
					 ywCompanyInfo.getCertificateCode(),ywCompanyInfo.getTelephone(),ywCompanyInfo.getValidity(),
					 ywCompanyInfo.getQlevel(),ywCompanyInfo.getRepresentativor(),ywCompanyInfo.getArea(),1}, "id=?", new Object[]{ywCompanyInfo.getId()});
			 //将备案人信息保存到cookie中
			/* newCookie = new Cookie("filingPerson",URLEncoder.encode(ywCompanyInfo.getFilingPerson(), "utf-8"));
			 newCookie.setMaxAge(60*30);
			 newCookie.setPath("/");
			 response.addCookie(newCookie);
			 newCookie = new Cookie("filingPersonTel",ywCompanyInfo.getFilingPersonTel());
			 newCookie.setMaxAge(60*30);
			 newCookie.setPath("/");
			 response.addCookie(newCookie);*/
			 //保存信息
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
				UserInfoVO user =UserInfoVO.findFirstBySql(UserInfoVO.class, "select isnull(te.contactPhone,'') as contactPhone,isnull(te.userName,'') as userName from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid where loginName= ?",new Object[] { userName });
				filingPerson = user.getUserName();
				filingPersonTel = user.getContactPhone();
				ywCompanyInfo.setFilingPerson(filingPerson);
				ywCompanyInfo.setFilingPersonTel(filingPersonTel);
			 ywCompanyInfo.setFilingNumber(ywCompanyInfo.getCompanyCode());
			 Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			 String date = format.format(new Date());
			 ywCompanyInfo.setFilingDate(date);
			 YwCompanyInfo companyInfo = YwCompanyInfo.findFirst(YwCompanyInfo.class, "id=?" ,new Object[]{ywCompanyInfo.getId()} );
			 if(companyInfo == null) {
				 //插入新数据
				 ywCompanyInfo.save();
			 } else {
				 //更新
				 YwCompanyInfo.updateAll(YwCompanyInfo.class, "companyCode=?,companyName=?,address=?,phone=?,contact=?,certificateCode=?,telephone=?,validity=?,qlevel=?,representativor=?,area=?,registAddress=?,representativorTel=?,contactTel=?,officeProof=?,safeyManPerson=?,type=?,note=?,filingPerson=?,filingPersonTel=?,filingDate=?,filingNumber=?", new Object[]{ywCompanyInfo.getCompanyCode(),
						 ywCompanyInfo.getCompanyName(),ywCompanyInfo.getAddress(),ywCompanyInfo.getPhone(),ywCompanyInfo.getContact(),
						 ywCompanyInfo.getCertificateCode(),ywCompanyInfo.getTelephone(),ywCompanyInfo.getValidity(),ywCompanyInfo.getQlevel(),
						 ywCompanyInfo.getRepresentativor(),ywCompanyInfo.getArea(),ywCompanyInfo.getRegistAddress(),ywCompanyInfo.getRepresentativorTel(),ywCompanyInfo.getContactTel(),ywCompanyInfo.getOfficeProof(),ywCompanyInfo.getSafeyManPerson(),ywCompanyInfo.getType(),ywCompanyInfo.getNote(),ywCompanyInfo.getFilingPerson(),ywCompanyInfo.getFilingPersonTel(),ywCompanyInfo.getFilingDate(),ywCompanyInfo.getFilingNumber()}, "id=?", new Object[]{ywCompanyInfo.getId()});
			 }
			 return "success";
		 }catch(Exception e){
			 e.printStackTrace();
			 return "failer";
		 }
	 }
		 
	/*//维保单位备案信息条件查询
	public View badCompanyList(YwCompanyInfo info,int page, int rows) {
		 String companyName = info.getCompanyName();
		 String address =  info.getAddress();
		 String filingPerson = info.getFilingPerson();
		 String companyCode = info.getCompanyCode();
		
		 String sql = "";
		 String conditions ="";
		 String conditionsSql="";
		
		 if(!"".equals(companyName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.companyName like '%"+companyName+"%'";	 
		     }
		     else{   
		    	 conditions =" t.companyName like '%"+companyName+"%'";	
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
		 
		 if(!"".equals(filingPerson)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.filingPerson like '%"+filingPerson+"%'";	 
		     }
		     else{   
		    	 conditions =" t.filingPerson like '%"+filingPerson+"%'";	
		      }
		 }
		 
		 if(!"".equals(companyCode)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.companyCode like '%"+companyCode+"%'";	 
		     }
		     else{   
		    	 conditions =" t.companyCode like '%"+companyCode+"%'";	
		      }
		 }
		 
		
	    long total=0;
	    List<YwCompanyInfo>  items =null;
		try {
	    if(!"".equals(conditions)){
	    	sql ="select * from TwoCodeYwCompanyInfo t where  "+ conditions;
	    	conditionsSql ="select count(*) from TwoCodeYwCompanyInfo t  where "+ conditions;
	    }
	    else{
	    	sql ="select * from TwoCodeYwCompanyInfo";
	    	conditionsSql ="select count(*) from TwoCodeYwCompanyInfo";
	
	    }
	    
	
			total = YwCompanyInfo.countBySql(YwCompanyInfo.class, conditionsSql, null);
		    items = YwCompanyInfo.findBySql(YwCompanyInfo.class, sql, null, "t.id desc", rows, (page-1)*rows);
		
		} catch (ActiveRecordException e) {	
			e.printStackTrace();
		}
		
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	}*/
	 
	 /**
	  * 备案单位的excel表导出
	  * @return
	  */
	 public String wbCompanyDc() {
		 try {
			 //获取模板的输入流
	        //InputStream inputStream = CompanyController.class.getClassLoader().getResourceAsStream("templates/companyinfo.xlsx");
			 InputStream inputStream = new FileInputStream("C:/Users/HRF/Desktop/维保单位核实信息.xlsx");
	        //导出数据
	        List<YwCompanyInfo> items = YwCompanyInfo.findAll(YwCompanyInfo.class);
	        //获取模板的工作薄
	        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
	        XSSFSheet sheetAt = workbook.getSheetAt(0);
	        //创建标题栏
	        XSSFRow row = null;
	        XSSFCell cell = null;
	        for (int i = 0; i < items.size(); i ++) {
	        	row = sheetAt.createRow(i + 1);
	        	cell = row.createCell(0);
	        	cell.setCellValue(items.get(i).getCompanyName());
		        cell = row.createCell(1);
		        cell.setCellValue(items.get(i).getCompanyCode());
		        cell = row.createCell(2);
		        cell.setCellValue(items.get(i).getRegistAddress());
		        cell = row.createCell(3);
		        cell.setCellValue(items.get(i).getAddress());
		        cell = row.createCell(4);
		        cell.setCellValue(items.get(i).getContact());
		        cell = row.createCell(5);
		        cell.setCellValue(items.get(i).getContactTel());
		        cell = row.createCell(6);
		        cell.setCellValue(items.get(i).getRepresentativor());
		        cell = row.createCell(7);
		        cell.setCellValue(items.get(i).getRepresentativorTel());
		        cell = row.createCell(8);
		        cell.setCellValue(items.get(i).getPhone());
		        cell = row.createCell(9);
		        cell.setCellValue(items.get(i).getTelephone());
		        cell = row.createCell(10);
		        cell.setCellValue(items.get(i).getOfficeProof());
		        cell = row.createCell(11);
		        cell.setCellValue(items.get(i).getArea());
		        cell = row.createCell(12);
		        cell.setCellValue(items.get(i).getSafeyManPerson());
		        cell = row.createCell(13);
		        cell.setCellValue(items.get(i).getCertificateCode());
		        cell = row.createCell(14);
		        cell.setCellValue(items.get(i).getType());
		        cell = row.createCell(15);
		        cell.setCellValue(items.get(i).getQlevel());
		        cell = row.createCell(16);
		        cell.setCellValue(items.get(i).getValidity());
		        cell = row.createCell(17);
		        cell.setCellValue(items.get(i).getNote());
		        cell = row.createCell(18);
		        cell.setCellValue(items.get(i).getFilingPerson());
		        cell = row.createCell(19);
		        cell.setCellValue(items.get(i).getFilingDate());
			}
	        String filename = "维保单位信息.xlsx";
	        response.setContentType("application/ms-excel;charset=UTF-8");  
	   	 	response.setHeader("Content-Disposition", "attachment;filename="  
	   	            .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
	   	 	OutputStream out = response.getOutputStream();  
	        workbook.write(out);
	        out.flush();  
	        out.close();
	        inputStream.close();
	        workbook.close();
	        return "导出成功!";
		 }catch(Exception e) {
			e.printStackTrace();
			return "导出异常,请尽快与管理员联系!";
		 }
	 }
} 
