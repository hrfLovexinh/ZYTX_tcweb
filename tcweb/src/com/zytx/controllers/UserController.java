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
import com.et.mvc.filter.AfterFilter;
import com.et.mvc.filter.AfterFilters;
import com.et.mvc.filter.BeforeFilter;
import com.zytx.converters.DateConverter;
import com.zytx.converters.TimestampConverter;
import com.zytx.models.CompanyInfo;
import com.zytx.models.ImageVO;
import com.zytx.models.CarDevCard;
import com.zytx.models.TCUserInfoView;
import com.zytx.models.UserExtInfo;
import com.zytx.models.UserInfo;
import com.zytx.models.ImageInfo;
import com.zytx.models.ImageInfo2;
import com.zytx.models.UserInfo2;
import com.zytx.models.UserInfoVO;
import com.zytx.models.UserRinghtInfo;
import com.zytx.models.YwInfo;
import com.zytx.models.YwInfoVO;
import com.zytx.models.YwlogVO;

@AfterFilters({
	@AfterFilter(execute="unbindinglog",only={"unbinding"})
})
public class UserController extends ApplicationController{  
	 protected String unbindingNames = "";
	 protected int unbindingUId =0;

	 public View userlist(int page, int rows) throws Exception{
	  
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		
		 long total=0;
		 List<UserInfoVO> items = null;
		 String sql = "";
		 
	     if(role==2){ //系统管理员 
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.ispcsuper from  TwoCodeUserInfo t left join  TwoCodeUserExtInfo tu  on t.id =tu.userid where t.id = ? ", new Object[] { userid });
	    	 if(userInfoVO != null){
	    		 if(userInfoVO.getIspcsuper() ==1){  //超级管理员
	    			 total  = UserInfoVO.countBySql(UserInfoVO.class, "select count(*) from  tcuserinfo where isinvalid = ?", new Object[]{0});
	    	    	 sql ="select te.ispcsuper,te.isyanshi,te.isliulan,t.id as id, t.loginName,t.password,t.role,isnull(te.userName,'') as userName,isnull(te.contactPhone,'') as contactPhone,isnull(te.telephonemobile,'') as telephonemobile,isnull(te.qualificationvalidate,'') as qualificationvalidate,isnull(te.qregistereddate,'') as qregistereddate,(case when t.role =18 then '员工' when t.role %2 =0  then '管理员' else '操作员' end) as type,isnull(tc.companyName,'') as companyName,te.idCard,tc.type as comanyType from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid left join  TwoCodeCompanyInfo tc on te.companyid = tc.id where t.isinvalid = 0";
	    	    	 items = UserInfoVO.findBySql(UserInfoVO.class, sql, null, null, rows, (page-1)*rows);
	    		 }
	    		 else{  //不是超级管理员
	    			 total  = UserInfoVO.countBySql(UserInfoVO.class, "select count(*) from  tcuserinfo where isinvalid = ? and ispcsuper= ? and (isyanshi = ?  or (isyanshi = 1 and role != 1 and role != 2  ))", new Object[]{0,0,0});
	    	    	 sql ="select te.ispcsuper,te.isyanshi,te.isliulan,t.id as id, t.loginName,t.password,t.role,isnull(te.userName,'') as userName,isnull(te.contactPhone,'') as contactPhone,isnull(te.telephonemobile,'') as telephonemobile,isnull(te.qualificationvalidate,'') as qualificationvalidate,isnull(te.qregistereddate,'') as qregistereddate,(case when t.role =18 then '员工' when t.role %2 =0  then '管理员' else '操作员' end) as type,isnull(tc.companyName,'') as companyName,te.idCard,tc.type as comanyType from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid left join  TwoCodeCompanyInfo tc on te.companyid = tc.id where t.isinvalid = 0 and te.ispcsuper= 0 and (te.isyanshi = 0 or (te.isyanshi = 1 and t.role != 1 and t.role != 2 ) or ( t.id ="+userid+"))";
	    	    	 items = UserInfoVO.findBySql(UserInfoVO.class, sql, null, null, rows, (page-1)*rows);
	    	 
	    		 }
	    	 }
	        
	    	
	     }
	     
	     if(role==10 || role==11){  //qu质监局
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId,tu.area from  tcuserinfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         String area = userInfoVO.getArea();
	         total  = UserInfoVO.countBySql(UserInfoVO.class, "select count(*) from  tcuserinfo te  where  ( te.companyid=? or te.parea = ? ) and isinvalid = 0  and te.ispcsuper= 0 and te.isyanshi = 0 ", new Object[]{zjcompanyId,area});
	      // String sql ="select t.id as id, t.loginName,t.password,t.role,isnull(te.userName,'') as userName,isnull(te.contactPhone,'') as contactPhone,(case when t.role =18 then '员工' else '管理人员' end) as type,isnull(tc.companyName,'') as companyName,te.idCard from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid left join  TwoCodeCompanyInfo tc on te.companyid = tc.id where te.companyid =?";
	         sql ="select te.ispcsuper,te.isyanshi,te.isliulan,t.id as id, t.loginName,t.password,t.role,isnull(te.userName,'') as userName,isnull(te.contactPhone,'') as contactPhone,isnull(te.telephonemobile,'') as telephonemobile,isnull(te.qualificationvalidate,'') as qualificationvalidate,isnull(te.qregistereddate,'') as qregistereddate,(case when t.role =18 then '员工' when t.role %2 =0  then '管理员' else '操作员' end) as type,isnull(tc.companyName,'') as companyName,te.idCard,tc.type as comanyType from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid left join  TwoCodeCompanyInfo tc on te.companyid = tc.id where (te.companyid =?  or parea = ? )and t.isinvalid = 0 and te.ispcsuper= 0 and te.isyanshi = 0 ";
	         items = UserInfoVO.findBySql(UserInfoVO.class, sql, new Object[]{zjcompanyId,area}, null, rows, (page-1)*rows);
	    	 }  	 
	     }
	     
	     if(role==22 || role==23){ //顶层质监局
	   //  	 total = UserInfoVO.countBySql(UserInfoVO.class, "select count(*) from TwoCodeUserInfo  where  isinvalid = 0  and (role =22 or role =23 or role =10 or role =11 or role =16 or role=17 )", null);
	    	 total = UserInfoVO.countBySql(UserInfoVO.class, "select count(*) from tcuserinfo  where  isinvalid = 0  and (role =22 or role =23 or role =10 or role =11 or role =16 or role=17 or role =30 or role =31) and  ispcsuper= 0 and isyanshi = 0 ", null);
	    	 sql ="select te.ispcsuper,te.isyanshi,te.isliulan,t.iskaoping, t.id as id,  t.loginName,t.password,t.role,isnull(te.userName,'') as userName,isnull(te.contactPhone,'') as contactPhone,isnull(te.telephonemobile,'') as telephonemobile,isnull(te.qualificationvalidate,'') as qualificationvalidate,isnull(te.qregistereddate,'') as qregistereddate,(case when t.role =18 then '员工' when t.role %2 =0  then '管理员' else '操作员' end) as type,isnull(tc.companyName,'') as companyName,te.idCard,tc.type as comanyType from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid left join  TwoCodeCompanyInfo tc on te.companyid = tc.id where  t.isinvalid = 0 and (t.role =22 or t.role=23 or t.role=10 or t.role=11 or t.role=16 or t.role=17  or role =30 or role =31)  and te.ispcsuper= 0 and te.isyanshi = 0  ";
	    	 items = UserInfoVO.findBySql(UserInfoVO.class, sql, null, "t.id", rows, (page-1)*rows);    	
	    	    	 
	     }
	     
	     if(role ==20 || role ==21){   //街道办
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	   //      total  = UserInfoVO.countBySql(UserInfoVO.class, "select count(*) from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid where te.companyid=?", new Object[]{zjcompanyId});
	         total  = UserInfoVO.countBySql(UserInfoVO.class, "select count(*) from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid where te.companyid=? and  te.ispcsuper= 0 and te.isyanshi = 0 ", new Object[]{zjcompanyId});
	         sql ="select te.ispcsuper,te.isyanshi,te.isliulan,t.id as id, t.loginName,t.password,t.role,isnull(te.userName,'') as userName,isnull(te.contactPhone,'') as contactPhone,isnull(te.telephonemobile,'') as telephonemobile,isnull(te.qualificationvalidate,'') as qualificationvalidate,isnull(te.qregistereddate,'') as qregistereddate,(case when t.role =18 then '员工' when t.role %2 =0  then '管理员' else '操作员' end) as type,isnull(tc.companyName,'') as companyName,te.idCard,tc.type as comanyType from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid left join  TwoCodeCompanyInfo tc on te.companyid = tc.id where te.companyid =? and t.isinvalid = 0 and  te.ispcsuper= 0 and te.isyanshi = 0";
	         items = UserInfoVO.findBySql(UserInfoVO.class, sql, new Object[]{zjcompanyId}, null, rows, (page-1)*rows);
	    	 }  	 
	     }
	     
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	 
	 
	 public String add(UserInfoVO userInfo){  
		 String result = "failure";   
		 UserInfo userInfoExit = null;
		 int companyId =0;
		 CompanyInfo companyInfo =null;
		 String threedscanning="0";
		 threedscanning = userInfo.getThreedscanning();
		 if(threedscanning == null)
			  threedscanning="0";
		 try {
		//	 if("员工".equals(userInfo.getType())){ //不同公司的职员账号可以重复
		//	String sql="select t.* from twoCodeuserinfo t left join twoCodeUserExtInfo te on t.id = te.userid where t.loginName=? and te.companyid=?";
		//	userInfoExit =UserInfo.findFirstBySql(UserInfo.class, sql,new Object[] {userInfo.getLoginName(),userInfo.getCompanyId()});	 
		//	 }
		//	 else{ //除了维保公司的运维员工不同维保公司账号可以重复外，其余账号均不能重复
			 userInfoExit =UserInfo.findFirst(UserInfo.class, "loginName=? and isinvalid =?", new Object[] {userInfo.getLoginName(),0});
		//	 }
			 if(userInfoExit != null){
				 result = "exist";
				 return result;
			 }else{
				companyId = userInfo.getCompanyId();
				
				if(companyId>0){
					companyInfo=CompanyInfo.findFirst(CompanyInfo.class, "id=? ", new Object[] {companyId});
					if(companyInfo !=null){
						String type =companyInfo.getType();  //公司类型
						if("员工".equals(userInfo.getType())){
							if(!"维保".equals(type)){  //目前运维公司才能添加职员 
							result ="failure2";
							 return result;
							}
						}
						if("维保".equals(type)){   //运维人员
							if("管理员".equals(userInfo.getType()))
							   userInfo.setRole(6);
							else if("操作员".equals(userInfo.getType()))
							   userInfo.setRole(7);
						    else
						      userInfo.setRole(18);
					      }  	
						else if("使用".equals(type)){   //物管
							if("管理员".equals(userInfo.getType()))
								   userInfo.setRole(8);
								else
								   userInfo.setRole(9);
								}
						else if("系统".equals(type)){   //系统组
							if("管理员".equals(userInfo.getType()))
								   userInfo.setRole(2);
								else
								   userInfo.setRole(1);
								}
						else if("制造".equals(type)){   //制造
							if("管理员".equals(userInfo.getType()))
								   userInfo.setRole(12);
								else
								   userInfo.setRole(13);
								}
						else if("安装".equals(type)){   //安装
							if("管理员".equals(userInfo.getType()))
								   userInfo.setRole(14);
								else
								   userInfo.setRole(15);
								}
						else if("检验".equals(type)){   //检验
							if("管理员".equals(userInfo.getType()))
								   userInfo.setRole(16);
								else
								   userInfo.setRole(17);
								}
						else if("街道办".equals(type)){   //街道办
							if("管理员".equals(userInfo.getType()))
								   userInfo.setRole(20);
								else
								   userInfo.setRole(21);
								}
						else if("市质监".equals(type)){   //市质监
							if("管理员".equals(userInfo.getType()))
								   userInfo.setRole(22);
								else
									userInfo.setRole(23);
								}
						else if("质监".equals(type)){   //质监
							if("管理员".equals(userInfo.getType()))
								   userInfo.setRole(10);
								else
									userInfo.setRole(11);
								}
						else if("粘贴".equals(type)){   //粘贴
							if("管理员".equals(userInfo.getType()))
								   userInfo.setRole(24);
								else
									userInfo.setRole(25);
								}
						else if("系统组".equals(type)){   //系统组
							if("管理员".equals(userInfo.getType()))
								   userInfo.setRole(2);
								else
									userInfo.setRole(1);
								}
						else if("行业协会".equals(type)){   //行业协会
							if("管理员".equals(userInfo.getType()))
								   userInfo.setRole(30);
								else
									userInfo.setRole(31);
								}
						else {
							 return result;  //没有改类型的单位，直接返回，添加失败
						}
					}
					UserInfo2 myuserInfo = new UserInfo2();
					myuserInfo.setLoginName(userInfo.getLoginName());
					myuserInfo.setPassword(userInfo.getPassword());
					myuserInfo.setRole(userInfo.getRole());	
				//	myuserInfo.setiMEI(userInfo.getiMEI());
				//	myuserInfo.setiMSI(userInfo.getiMSI());
					myuserInfo.setBinding(1);
					
					UserExtInfo  userExtInfo =new UserExtInfo();
					userExtInfo.setUserName(userInfo.getUserName());
					userExtInfo.setCompanyid(companyId);
				//	userExtInfo.setThreedscanning(userInfo.getThreedscanning());
					userExtInfo.setThreedscanning(threedscanning);
					userExtInfo.setIdCard(userInfo.getIdCard());
					userExtInfo.setSpeEquQualification(userInfo.getSpeEquQualification());
					userExtInfo.setContactPhone(userInfo.getContactPhone());
					userExtInfo.setTelephonemobile(userInfo.getTelephonemobile());
					userExtInfo.setQualificationvalidate(userInfo.getQualificationvalidate());
					userExtInfo.setQregistereddate(userInfo.getQregistereddate());
					myuserInfo.setUserExtInfo(userExtInfo);
			        if(myuserInfo.save())   
				     result ="success";	     
				}
				
				
		      }
		 }catch(Exception e){
			 e.printStackTrace();
		 }   
		 return result;  
	 }
	 
	 public  String delete(int id){
		 String result = "failure";
		 int num = 0;
		 try {
			UserInfo userInfo=UserInfo.findFirst(UserInfo.class, "id=?", new Object[] {id});
			if(userInfo!=null){
		//		userInfo.destroy();  不能删除用户
				num =UserInfo.updateAll(UserInfo.class, "isinvalid = ? ", new Object[]{1}, "id=?", new Object[] {id});
				if(num > 0)
				result = "success";
			}
			
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return result;
	 }
	 
	 public  String updateKPRightsById(int id){
		 String result = "failure";
		 int num = 0;
		 try {
			UserInfo userInfo=UserInfo.findFirst(UserInfo.class, "id=?", new Object[] {id});
			if(userInfo!=null){
				num =UserInfo.updateAll(UserInfo.class, "iskaoping = ? ", new Object[]{1}, "id=?", new Object[] {id});
				if(num > 0)
				result = "success";
			}
			
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return result;
	 }
	 
	 public  String updateKPRightsById2(int id){
		 String result = "failure";
		 int num = 0;
		 try {
			UserInfo userInfo=UserInfo.findFirst(UserInfo.class, "id=?", new Object[] {id});
			if(userInfo!=null){
				num =UserInfo.updateAll(UserInfo.class, "iskaoping = ? ", new Object[]{0}, "id=?", new Object[] {id});
				if(num > 0)
				result = "success";
			}
			
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return result;
	 }
	 
	 
	 public View edit(int id)throws Exception{ 
		 System.out.println("edit---id:"+id);
		 String sql ="select t.id,isnull(te.qualificationvalidate,'') as qualificationvalidate,isnull(te.qregistereddate,'') as qregistereddate,isnull(t.loginName,'') as loginName,t.password,t.role,t.binding,isnull(te.userName,'') as userName ,te.threedscanning,isnull(te.idCard,'') as idCard,isnull(te.speEquQualification,'') as speEquQualification,isnull(te.contactPhone,'') as contactPhone,isnull(te.telephonemobile,'') as telephonemobile,tc.id as companyId,tc.companyName,tc.type from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id = te.userid left join TwoCodeCompanyInfo  tc on te.companyid = tc.id where t.id = ?";
		 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, sql, new Object[] { id });
		 Map<String, Object> result = new HashMap<String, Object>();
		 if(userInfoVO != null){ 
			 result.put("loginName", userInfoVO.getLoginName());
			 result.put("password", userInfoVO.getPassword());
			 result.put("userName", userInfoVO.getUserName());
			 result.put("threedscanning", userInfoVO.getThreedscanning());
			 result.put("idCard", userInfoVO.getIdCard());
			 result.put("speEquQualification", userInfoVO.getSpeEquQualification());
			 result.put("contactPhone", userInfoVO.getContactPhone());
			 result.put("telephonemobile", userInfoVO.getTelephonemobile());
			 result.put("binding",userInfoVO.getBinding());
			 result.put("qualificationvalidate", userInfoVO.getQualificationvalidate());
			 result.put("qregistereddate", userInfoVO.getQregistereddate());
			 int role =userInfoVO.getRole(); 
			 if (role >0){
				 switch(role){
				 case 1: 
					 result.put("type", "操作员");
					 break;
				 case 2: 
					 result.put("type", "管理员");
					 break;
				 case 6:
					 result.put("type", "管理员");
					 break;
				 case 7:
					 result.put("type", "操作员");
					 break;
				 case 8:
					 result.put("type", "管理员");
					 break;
				 case 9:
					 result.put("type", "操作员");
					 break;
				 case 10:
					 result.put("type", "管理员");
					 break;
				 case 11:
					 result.put("type", "操作员");
					 break;
				 case 12:
					 result.put("type", "管理员");
					 break;
				 case 13:
					 result.put("type", "操作员");
					 break;
				 case 14:
					 result.put("type", "管理员");
					 break;
				 case 15:
					 result.put("type", "操作员");
					 break;
				 case 16:
					 result.put("type", "管理员");
					 break;
				 case 17:
					 result.put("type", "操作员");
					 break;
				 case 18:
					 result.put("type", "员工");
					 break;
				 case 20:
					 result.put("type", "管理员");
					 break;
				 case 21:
					 result.put("type", "操作员");
					 break;
				 case 22:
					 result.put("type", "管理员");
					 break;
				 case 23:
					 result.put("type", "操作员");
					 break;
				 case 24:
					 result.put("type", "管理员");
					 break;
				 case 25:
					 result.put("type", "操作员");
					 break;
				 case 30:
					 result.put("type", "管理员");
					 break;
				 case 31:
					 result.put("type", "管理员");
					 break;	 
				 default:
					 result.put("type", "操作员");
					 break;
				 }		 
			 }
			 result.put("companyType", userInfoVO.getType());
			 result.put("companyId", userInfoVO.getCompanyId());
			 result.put("companyName",userInfoVO.getCompanyName());
		//	 result.put("companyId2",userInfoVO.getCompanyName());
		 }
		 return new JsonView(result);
	 }
	 
	 public String update(UserInfoVO userInfoVO){
		 System.out.println("update---id:"+userInfoVO.getId());
		 String result = "failure";
		 int num = 0;
		 int num2 =0;
		 CompanyInfo companyInfo =null;
		 int companyId =0;
		 int role =0;
		 String type="";
		 UserInfoVO userInfoVOExit =null;
		 String threedscanning="0";
		 threedscanning = userInfoVO.getThreedscanning();
		 if(threedscanning == null)
			  threedscanning="0";
		 try {  
			 companyId = userInfoVO.getCompanyId();   //得到所选公司
			 if(companyId>0){
				companyInfo=CompanyInfo.findFirst(CompanyInfo.class, "id=? ", new Object[] {companyId});
				if(companyInfo !=null){
					type =companyInfo.getType();    //得到所选公司类型
				}
			 }
			
			 userInfoVOExit = UserInfoVO.findFirst(UserInfoVO.class,"loginName=? and id!=? and isinvalid =0", new Object[] { userInfoVO.getLoginName(),userInfoVO.getId() }); 
			 if(userInfoVOExit!=null){
				 result = "exist";
				 return result;
			 }
			 
						if(companyInfo !=null){
							if("维保".equals(type)){   //运维人员
								if("管理员".equals(userInfoVO.getType()))
									 role=6;
								else if("操作员".equals(userInfoVO.getType()))
									 role=7;
								else 
									role =18;
								}
							else if("使用".equals(type)){   //物管
								if("管理员".equals(userInfoVO.getType()))
									role=8;
									else
										role=9;
									}
							else if("系统组".equals(type)){   //系统组
								if("管理员".equals(userInfoVO.getType()))
									role=2;
									else
										role=1;
									}
							else if("制造".equals(type)){   //制造
								if("管理员".equals(userInfoVO.getType()))
									role=12;
									else
										role=13;
									}
							else if("安装".equals(type)){   //安装
								if("管理员".equals(userInfoVO.getType()))
									role=14;
									else
										role=15;
									}
							else if("检验".equals(type)){   //检验
								if("管理员".equals(userInfoVO.getType()))
									role=16;
									else
										role=17;
									}
							
							else if("街道办".equals(type)){   //街道办
								if("管理员".equals(userInfoVO.getType()))
									role=20;
									else
									role=21;
									}
							else if("质监".equals(type)){   //质监
								if("管理员".equals(userInfoVO.getType()))
									role=10;
									else
										role=11;
									}
							else if("市质监".equals(type)){   //质监
								if("管理员".equals(userInfoVO.getType()))
									role=22;
									else
										role=23;
									}
							else if("粘贴".equals(type)){  //粘贴
								if("管理员".equals(userInfoVO.getType()))
									role=24;
									else
										role=25;
							}
							else if("行业协会".equals(type)){  //行业协会
								if("管理员".equals(userInfoVO.getType()))
									role=30;
									else
										role=31;
							}
							
							else {
								 return result;  //没有该类型的单位，直接返回，添加失败
							}
						}
				 
			
				 ActiveRecordBase.beginTransaction();
				 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
				
				 if( userinfo.getRole() == 1 ||  userinfo.getRole() ==2){
					 num =UserInfoVO.updateAll(UserInfoVO.class, "loginName=?,password=?,role=?", new Object[]{userInfoVO.getLoginName(),userInfoVO.getPassword(),role}, "id=?", new Object[]{userInfoVO.getId()});	 
							 
				 }
				 else{
				 num =UserInfoVO.updateAll(UserInfoVO.class, "password=?,role=?", new Object[]{userInfoVO.getPassword(),role}, "id=?", new Object[]{userInfoVO.getId()});	 
				 }
				 if(num>0){
					 //更新扩展信息表
					 if(userinfo.getIspcsuper() == 1){
					 num2 = UserExtInfo.updateAll(UserExtInfo.class, "companyid=?,userName=?,threedscanning=?,contactPhone=?,telephonemobile=?,idCard=?,speEquQualification=?,qualificationvalidate=?,qregistereddate=?", new Object[]{userInfoVO.getCompanyId(),userInfoVO.getUserName(),threedscanning,userInfoVO.getContactPhone(),userInfoVO.getTelephonemobile(),userInfoVO.getIdCard(),userInfoVO.getSpeEquQualification(),userInfoVO.getQualificationvalidate(),userInfoVO.getQregistereddate()}, "userid=?", new Object[]{userInfoVO.getId()});
					 }
					 else
					 {
					 num2 = UserExtInfo.updateAll(UserExtInfo.class, "companyid=?,userName=?,contactPhone=?,telephonemobile=?,idCard=?,speEquQualification=?,qualificationvalidate=?,qregistereddate=?", new Object[]{userInfoVO.getCompanyId(),userInfoVO.getUserName(),userInfoVO.getContactPhone(),userInfoVO.getTelephonemobile(),userInfoVO.getIdCard(),userInfoVO.getSpeEquQualification(),userInfoVO.getQualificationvalidate(),userInfoVO.getQregistereddate()}, "userid=?", new Object[]{userInfoVO.getId()});
								 
					 }
					 if(num2>0){
						 result = "success"; 
						 ActiveRecordBase.commit();
					 } 
				 }			
			 }
		   catch (ActiveRecordException e1) {
			// TODO Auto-generated catch block
			try {
				ActiveRecordBase.rollback();
			} catch (TransactionException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		 return result;
	 }
	 
	 public View getYwUserList(int page, int rows) throws Exception {
			List<UserInfoVO> items = UserInfoVO.findBySql(UserInfoVO.class,
					"select t.userid as id,t.userName from TwoCodeUserExtInfo t left join TwoCodeCompanyInfo tc on t.companyid = tc.id  where tc.type=?", new Object[] { "维保" });
			return new JsonView(items);
		}
	 
	 public View getUserListByCompanyId(int companyId,int page, int rows) throws Exception {
			List<UserInfoVO> items = UserInfoVO.findBySql(UserInfoVO.class,
					"select t.userid as id,t.userName from TwoCodeUserExtInfo t  where t.companyid=?", new Object[] { companyId });
			return new JsonView(items);
		}
	 
	 public View getUserListByCompanyId2(int companyId,String q,int limit) throws Exception {
			List<UserInfoVO> items = UserInfoVO.findBySql(UserInfoVO.class,
					"select t.userid as id,t.userName from TwoCodeUserExtInfo t  where t.companyid=? and t.userName like ? ", new Object[] { companyId,"%"+q+"%" });
			return new JsonView(items);
		}
	 
	 public View query(UserInfoVO  info,int page, int rows)throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName2 =userinfo.getLoginName();  //userName2不用于查询条件
		 int  role = userinfo.getRole();
		 
		 
		 int zjcompanyId2 =0;  //非查询用的，是登陆过程中系统后台根据role不同展示不同列表用的
		 String loginName="";
		 String userName ="";
		 String companyName ="";
		 String idCard="";
		 
		 loginName="%"+info.getLoginName()+"%";
		 idCard="%"+info.getIdCard()+"%";
		 userName ="%"+info.getUserName()+"%";
		 companyName ="%"+info.getCompanyName()+"%";
		
		
		 String conditions="";
		 Object[] param=null;
		 if(role==2){ 
		 conditions ="(t.loginName like ? or t.idCard like ? ) and t.userName like  ? and t.companyName like ? ";
		 param = new Object[]{loginName,idCard,userName,companyName};
		 }
		 
		 if(role==10 || role==11){
			 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	    	 zjcompanyId2 = userInfoVO.getCompanyId();
			 conditions =" (t.loginName like ? or t.idCard like ?) and t.userName like  ? and t.companyName like ?   and t.companyid=?";
			 param = new Object[]{loginName,idCard,userName,companyName,zjcompanyId2};
		 }
			 
		 }
		 
		 if(role==22 || role==23){
		//	 conditions =" (t.loginName like ? or t.idCard like ?) and t.userName like  ? and t.companyName like ?   and (t.type ='质监' or t.type='市质监') ";
			 conditions =" (t.loginName like ? or t.idCard like ?) and t.userName like  ? and t.companyName like ?   and (t.type ='质监' or t.type='市质监' or t.type='检验' or t.type='行业协会') and t.companyName != '未知'";
			 param = new Object[]{loginName,idCard,userName,companyName};
			 
		 }
		 
		 
		 if(role==20 || role==21){
			 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	    	 zjcompanyId2 = userInfoVO.getCompanyId();
			 conditions =" (t.loginName like ? or t.idCard like ?) and t.userName like  ? and t.companyName like ?   and t.companyid=?";
			 param = new Object[]{loginName,idCard,userName,companyName,zjcompanyId2};
		 }
	    	
		 }
	    
		 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.ispcsuper from  TwoCodeUserInfo t left join  TwoCodeUserExtInfo tu  on t.id =tu.userid where t.id = ? ", new Object[] { userid });
    	 if(userInfoVO != null){
    		 if(userInfoVO.getIspcsuper() ==0){  //不是超级管理员
    			if(!"".equals(conditions)){
    				conditions = conditions + " and t.ispcsuper= 0  and (t.isyanshi = 0 or (t.isyanshi = 1 and t.role != 1 and t.role != 2  ) or ( t.userid ="+userid+"))";
    			} 
    		 }
    	 }
		 long total = TCUserInfoView.countBySql(TCUserInfoView.class, "select count(*) from TCUserInfo t where t.isinvalid = 0  and "+conditions, param);
    	 String sql ="select t.ispcsuper,t.isyanshi,t.isliulan,t.iskaoping,t.id as id, isnull(t.loginName,'') as loginName,t.password,t.role,isnull(t.userName,'') as userName,isnull(t.contactPhone,'') as contactPhone,isnull(t.telephonemobile,'') as telephonemobile,isnull(t.qualificationvalidate,'') as qualificationvalidate,isnull(t.qregistereddate,'') as qregistereddate,(case when t.role =18 then '员工' else '管理人员' end) as type,isnull(t.companyName,'') as companyName,t.idCard,t.type as comanyType  from TCUserInfo t where t.isinvalid = 0  and "+conditions;
			
		 List<TCUserInfoView>  items = TCUserInfoView.findBySql(TCUserInfoView.class, sql, param, "t.id", rows, (page-1)*rows);
		 
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	 }
	 
	 public String unbinding(String loginName,int id){
		 String resutlt ="0";
		 this.unbindingUId = id;
		 this.unbindingNames =loginName;
		 int num =0;
		 try {
			num =UserInfoVO.updateAll(UserInfoVO.class, "iMSI=?,iMEI=?,binding=?", new Object[]{"","",1}, "loginName=?", new Object[]{loginName});
			if(num>0){
				resutlt ="1";	
			}
		} catch (ActiveRecordException e) {
			
			e.printStackTrace();
		}
		 return resutlt;
		 
	 }
	 
	 public boolean unbindinglog(){
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
				     String userName =userinfo.getLoginName();
				     TCUserInfoView tCUserInfoView =null;
				     try {
						 tCUserInfoView =TCUserInfoView.findFirst(TCUserInfoView.class, "userid = ?", new Object[]{this.unbindingUId});
					} catch (ActiveRecordException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 if(tCUserInfoView != null){
			    	 java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
			    	 
			    	 String s = format1.format(new Date()); 
			    	 YwlogVO rukuLog  = new  YwlogVO();
			    	 rukuLog.setUserName(userName);
			    	 rukuLog.setContext(userName+"解绑:"+this.unbindingNames+"("+tCUserInfoView.getCompanyName()+")");
			    	 rukuLog.setCmd("unbind");
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
	 
	 public String queryUnameByLname(String loginName){
		 String userName ="";
		 try {
			 UserInfoVO user =UserInfoVO.findFirstBySql(UserInfoVO.class, "select t.*,te.userName from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id=te.userid where loginName= ?",new Object[] { loginName });
			if(user!=null){
				userName= user.getUserName();
			}
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userName;
	 }
	 
	 public View ywpersonlist(int page, int rows) throws Exception{
		 
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 System.out.println("UserController---34---登陆名称---"+userName);
		 System.out.println("UserController---36---role---"+role);
		 long total=0;
		 List<UserInfoVO> items = null;
		 
	  //   if(role==2){ //系统管理员 
	    	 total  = UserInfoVO.countBySql(UserInfoVO.class, "select count(*) from TwoCodeUserInfo where role =18 and isinvalid = 0 ", null);
	    //	 items = CompanyInfo.findAll(CompanyInfo.class);
	    	 String sql ="select t.id as id, t.loginName,t.password,t.role,isnull(te.userName,'') as userName,isnull(te.contactPhone,'') as contactPhone,isnull(te.qualificationvalidate,'') as qualificationvalidate,isnull(te.qregistereddate,'') as qregistereddate,(case when t.role =18 then '职员' else '管理人员' end) as type,isnull(tc.companyName,'') as companyName,te.idCard,te.speEquQualification from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid left join  TwoCodeCompanyInfo tc on te.companyid = tc.id where t.role=18  and isinvalid = 0 ";
	    //	 items = CompanyInfo.findAll(CompanyInfo.class, null, null, null, rows, (page-1)*rows);
	    	 items = UserInfoVO.findBySql(UserInfoVO.class, sql, null, null, rows, (page-1)*rows);
	 //    }
	     /*
	     if(role==10 || role==11){
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         total  = UserInfoVO.countBySql(UserInfoVO.class, "select count(*) from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid where te.companyid=?", new Object[]{zjcompanyId});
	         String sql ="select t.id as id, t.loginName,t.password,t.role,isnull(te.userName,'') as userName,isnull(te.contactPhone,'') as contactPhone,(case when t.role =18 then '职员' else '管理人员' end) as type,isnull(tc.companyName,'') as companyName,te.idCard from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid left join  TwoCodeCompanyInfo tc on te.companyid = tc.id where te.companyid =? and t.role=18";
	 	     items = UserInfoVO.findBySql(UserInfoVO.class, sql, new Object[]{zjcompanyId}, null, rows, (page-1)*rows);
	    	 }  	 
	     }
	     
	     if(role==22 || role==23){
	    	 UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	    	 if(userInfoVO != null){
	         int zjcompanyId = userInfoVO.getCompanyId();
	         total  = UserInfoVO.countBySql(UserInfoVO.class, "select count(*) from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid where te.companyid=?", new Object[]{zjcompanyId});
	         String sql ="select t.id as id, t.loginName,t.password,t.role,isnull(te.userName,'') as userName,isnull(te.contactPhone,'') as contactPhone,(case when t.role =18 then '职员' else '管理人员' end) as type,isnull(tc.companyName,'') as companyName,te.idCard from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid left join  TwoCodeCompanyInfo tc on te.companyid = tc.id where te.companyid =? and t.role=18";
	 	     items = UserInfoVO.findBySql(UserInfoVO.class, sql, new Object[]{zjcompanyId}, null, rows, (page-1)*rows);
	    	 }  	 
	     }
	     */
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	    }
	 
	 //根据公司查询维保人员
	 public View ywpersonlistByCompany(int id,int page, int rows) throws Exception {
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 int userid = userinfo.getId();
		 String userName =userinfo.getLoginName();
		 int  role = userinfo.getRole();
		 System.out.println("UserController---34---登陆名称---"+userName);
		 System.out.println("UserController---36---role---"+role);
		 long total=0;
		 List<UserInfoVO> items = null;
	    	 total  = UserInfoVO.countBySql(UserInfoVO.class, "select count(*) from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid left join  TwoCodeCompanyInfo tc on te.companyid = tc.id where t.role =18 and isinvalid = 0 and tc.id=" + id, null);
	    	 String sql ="select t.id as id,t.isbeian,isnull(te.userName,'') as userName,te.idCard,isnull(te.contactPhone,'') as contactPhone,isnull(te.qregistereddate,'') as qregistereddate,(case when t.role =18 then '职员' else '管理人员' end) as type from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid left join  TwoCodeCompanyInfo tc on te.companyid = tc.id where t.role=18  and isinvalid = 0 and tc.id=" + id;
	    	 items = UserInfoVO.findBySql(UserInfoVO.class, sql, null, null, rows, (page-1)*rows);
	    	 //items = UserInfoVO.findBySql(UserInfoVO.class, sql);
	      Map<String, Object> result = new HashMap<String, Object>();
	      result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	 }
	 
	 //确认维保人员备案
	 public View wbpersonBa(UserInfoVO user,int id) {
		 Map<String,Object> result = new HashMap<String,Object>();
		 String userName = user.getUserName();
		 String idCard = user.getIdCard();
		 String contactPhone = user.getContactPhone();
		 String update = "";
		 UserInfoVO infoVO = null;
		 List<Object> params = new ArrayList<Object>();
			 try {
				 infoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select isnull(te.userName,'') as userName,te.idCard,isnull(te.contactPhone,'') as contactPhone from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid where t.id=?", new Object[]{id});
				if(infoVO != null) {
					if(!userName.equals(infoVO.getUserName())) {
						update += ",userName=?"; 
						params.add(userName);
					}
					if(!idCard.equals(infoVO.getIdCard())) {
						update += ",idCard=?";
						params.add(idCard);
					}
					if(!contactPhone.equals(infoVO.getContactPhone())) {
						update += ",contactPhone=?,oldContactPhone=?";
						params.add(contactPhone);
						params.add(infoVO.getContactPhone());
					}
					if(!"".equals(update)) {
						update = update.substring(1);
						UserExtInfo.updateAll(UserExtInfo.class, update, params.toArray(), "userid=?", new Object[]{id});
					}
				}
				UserInfoVO.updateAll(UserInfoVO.class, "isBeian=?", new Object[]{1}, "id=?", new Object[]{id});
				result.put("msg", "success");
			} catch (ActiveRecordException e) {
				e.printStackTrace();
				result.put("msg", "failer");
			}
			 return new JsonView(result);
	 }
	 
	 
	 public View ywpersonquery(UserInfoVO  info,int page, int rows)throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
	     int userid = userinfo.getId();
		 String userName2 =userinfo.getLoginName();  //userName2不用于查询条件
		 int  role = userinfo.getRole();
		 
		
		 String loginName="";
		 String userName ="";
		 int ywCompanyId =0;
		 int zjcompanyId2 =0;  //非查询用的，是登陆过程中系统后台根据role不同展示不同列表用的
		 String idCard="";
		 String qstartTime ="";
		 
		 /*
		 loginName="%"+info.getLoginName()+"%";
		 idCard="%"+info.getIdCard()+"%";
		 userName ="%"+info.getUserName()+"%";
		 ywCompanyId =info.getCompanyId();
		 */
		 
		 loginName=info.getLoginName();
		 userName=info.getUserName();
		 ywCompanyId=info.getCompanyId();
		 qstartTime =info.getQstartTime();
		 
		 /*
		 String conditions="";
		 Object[] param=null;
		
		 if(ywCompanyId>0){
		     conditions ="(t.loginName like ? or t.idCard like ? ) and t.userName like  ? and t.companyId  =? and t.role=18";
		     param = new Object[]{loginName,idCard,userName,ywCompanyId};
		 }
		 else{
			 conditions ="(t.loginName like ? or t.idCard like ? ) and t.userName like  ? and t.role=18";
			 param = new Object[]{loginName,idCard,userName};	 
		 }			 
		 */
		 String sql = "";
		 String conditions="";
		 String conditionsSql="";
		 
		 /*
		 if(role==10 || role==11){   
	           UserInfoVO userInfoVO = UserInfoVO.findFirstBySql(UserInfoVO.class, "select tu.companyId from  TwoCodeUserExtInfo tu  where tu.userid = ?", new Object[] { userid });
	         if(userInfoVO != null){
	             zjcompanyId2 = userInfoVO.getCompanyId();
	             conditions = " te.zjcompanyId = "+zjcompanyId2;
	             }	  
	          } */
		 
		 if(!"".equals(loginName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.loginName like '%"+loginName+"%'";	
				} 
				else{
					conditions =" t.loginName like '%"+loginName+"%'";	
				}
			 
		 }
		 
		 if(!"".equals(userName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.userName like '%"+userName+"%'";	
				} 
				else{
					conditions =" t.userName like '%"+userName+"%'";	
				}
			 
		 }
         
         if(ywCompanyId > 0){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.companyId = "+ywCompanyId;	
				} 
				else{
					conditions =" t.companyId = "+ywCompanyId;	
				}
			 
			 
		 }
         
         if(!"".equals(qstartTime)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and convert(datetime,t.qualificationvalidate,120)  <= '"+qstartTime+"'" ;	 
			 } 
			 else{
				 conditions =" convert(datetime,t.qualificationvalidate,120)  <= '"+qstartTime+"'" ;	 
			 }
		 }
         
         if(!"".equals(conditions)){
			  sql ="select t.id as id, isnull(t.loginName,'') as loginName,t.password,t.role,t.speEquQualification,isnull(t.userName,'') as userName,isnull(t.contactPhone,'') as contactPhone,isnull(t.qualificationvalidate,'') as qualificationvalidate,isnull(t.qregistereddate,'') as qregistereddate,(case when t.role =18 then '员工' else '管理人员' end) as type,isnull(t.companyName,'') as companyName,t.idCard from TCUserInfo t  where "+ conditions+" and t.type ='维保' and t.role =18 and t.isinvalid = 0 ";
			  conditionsSql = "select count(1) from TCUserInfo t  where "+ conditions+" and t.type ='维保' and t.role =18 and t.isinvalid = 0 ";
			 }
			 else{
			  sql ="select t.id as id, isnull(t.loginName,'') as loginName,t.password,t.role,t.speEquQualification,isnull(t.userName,'') as userName,isnull(t.contactPhone,'') as contactPhone,isnull(t.qualificationvalidate,'') as qualificationvalidate,isnull(t.qregistereddate,'') as qregistereddate,(case when t.role =18 then '员工' else '管理人员' end) as type,isnull(t.companyName,'') as companyName,t.idCard from TCUserInfo t where   t.type ='维保' and t.role =18 and t.isinvalid =0 ";
			  conditionsSql = "select count(1) from TCUserInfo t where   t.type ='维保' and t.role =18 and t.isinvalid = 0 ";
						 
			 }
         
         
          long total =TCUserInfoView.countBySql(TCUserInfoView.class, conditionsSql, null);
		  List<TCUserInfoView> items=TCUserInfoView.findBySql(TCUserInfoView.class, sql, null, "t.id desc", rows, (page-1)*rows);
			
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	 }
	 
	 
	 public View attributeById(int id){
		 UserInfoVO userinfo =null;
	    	Map<String, Object> obj = new HashMap<String, Object>();
	    	String sql ="select  te.isyanshi,te.isliulan from TwoCodeUserInfo  t left join TwoCodeUserExtInfo te on t.id =te.userId where t.id = ?";
	    	try {
	    		userinfo =UserInfoVO.findFirstBySql(UserInfoVO.class, sql, new Object[] {id});
				if(userinfo != null){
					obj.put("isyanshi",userinfo.getIsyanshi());
					obj.put("isliulan",userinfo.getIsliulan());
				}
			} catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return new JsonView(obj);
	    }
	 
	 public String updateattributeById(int id,int isyanshi,int isliulan){
    	 System.out.println("updateattributeById--->");
    	 String result = "failure";
    	 int num = 0;
    	 
 		try {
			num = UserExtInfo.updateAll(UserExtInfo.class,"isyanshi=?,isliulan=?", new Object[]{isyanshi,isliulan}, "userId=?", new Object[] { id});
		    } 
 		catch (ActiveRecordException e) {		
				e.printStackTrace();
			}
 			if(num>0){
 				
 				result = "success";
 			}
 				
    	 return result;
    }
	 
	 public View rightDistributById(int id){
		    UserRinghtInfo userRinghtInfo =null;
	    	Map<String, Object> obj = new HashMap<String, Object>();
	    	String sql ="select  isnull(tr.ywtx,0) as ywtx from TwoCodeUserInfo  t left join TwoCodeRightsTable tr on t.id =tr.userId where t.id = ?";
	    	try {
	    		userRinghtInfo =UserRinghtInfo.findFirstBySql(UserRinghtInfo.class, sql, new Object[] {id});
				if(userRinghtInfo != null){
					obj.put("ywtx",userRinghtInfo.getYwtx());
					
				}
			} catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return new JsonView(obj);
	    }
	 
	 public String updateRightsById(int id,int ywtx){
    	 System.out.println("ywtx--->"+ywtx);
    	 String result = "failure";
    	 int num = 0;
    	 
    	
    		 
 			try {
				num = UserRinghtInfo.updateAll(UserRinghtInfo.class,"ywtx=?", new Object[]{ywtx}, "userId=?", new Object[] { id});
			} catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 			if(num>0){
 				
 				result = "success";
 			}
 			else{
 				UserRinghtInfo userRinghtInfo = new UserRinghtInfo();
 				userRinghtInfo.setUserId(id);
 				userRinghtInfo.setYwtx(ywtx);
 				try {
					if(userRinghtInfo.save())
						result = "success";
				} catch (ActiveRecordException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 				
 			}
 			
    	 return result;
    }
	 
	 public View userapplist(int page, int rows){
		 long total=0;
		 List<UserInfoVO> items = null;
		 String sql = "";
		 
		 sql ="select t.loginName,t.role,isnull(te.userName,'') as userName,isnull(te.contactPhone,'') as contactPhone,isnull(te.telephonemobile,'') as telephonemobile,(case when t.role =18 then '员工' when t.role %2 =0  then '管理员' else '操作员' end) as type,isnull(tc.companyName,'') as companyName,te.idCard,tc.type as comanyType,isnull(te.telephoneType,'') as telephoneType,isnull(te.versionCode,'') as versionCode from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid left join  TwoCodeCompanyInfo tc on te.companyid = tc.id where t.isinvalid = 0 and (t.role = 18 or t.role =6 or t.role =7) ";
    	  
		 try {
			total  = UserInfoVO.countBySql(UserInfoVO.class, "select count(*) from  TwoCodeUserInfo where isinvalid = ?  and (role = 18 or role =6 or role =7)", new Object[]{0});
			items = UserInfoVO.findBySql(UserInfoVO.class, sql, null, "companyName asc", rows, (page-1)*rows);
			
		 } catch (ActiveRecordException e) {
			e.printStackTrace();
		}
    	
		 Map<String, Object> result = new HashMap<String, Object>();
	     result.put("total", total);
		 result.put("rows", items);
		 return new JsonView(result);
	 }
	 
	 public View applistquery(UserInfoVO  info,int page, int rows){
		 long total=0;
		 List<UserInfoVO> items = null;
		 String sql = "";
		 
		 String loginName="";
	     String userName ="";
	     String companyName ="";
	     String telephoneType="";
	     String versionCode ="";
	     
	     loginName = info.getLoginName();
	     userName = info.getUserName();
	     companyName = info.getCompanyName();
	     telephoneType = info.getTelephoneType();
	     versionCode = info .getVersionCode();
	     
		 String conditions="";
		 String conditionsSql="";
		 
		 
		 if(!"".equals(loginName)){
			 if(!"".equals(conditions)){
				 conditions =conditions+" and t.loginName like '%"+loginName+"%'";	
				} 
				else{
				 conditions =" t.loginName like '%"+loginName+"%'";	
				}
		 }
			 
		if(!"".equals(userName)){
			if(!"".equals(conditions)){
				conditions =conditions+" and te.userName like '%"+userName+"%'";	
			} 
			else{
				conditions =" te.userName like '%"+userName+"%'";	
			}
			 
		 }
		
		if(!"".equals(companyName)){
			if(!"".equals(conditions)){
				conditions =conditions+" and tc.companyName like '%"+companyName+"%'";	
			} 
			else{
				conditions =" tc.companyName like '%"+companyName+"%'";	
			}
			 
		 }
		

		if(!"".equals(telephoneType)){
			if(!"".equals(conditions)){
				conditions =conditions+" and te.telephoneType like '%"+telephoneType+"%'";	
			} 
			else{
				conditions =" te.telephoneType like '%"+telephoneType+"%'";	
			}
			 
		 }
		

		if(!"".equals(versionCode)){
			if(!"".equals(conditions)){
				conditions =conditions+" and te.versionCode like '%"+versionCode+"%'";	
			} 
			else{
				conditions =" te.versionCode like '%"+versionCode+"%'";	
			}
			 
		 }
		
		 if(!"".equals(conditions)){
		conditionsSql ="select count(*)  from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid left join  TwoCodeCompanyInfo tc on te.companyid = tc.id  where "+ conditions+" and isinvalid = ?  and (role = 18 or role =6 or role =7)";
		 sql ="select t.loginName,t.role,isnull(te.userName,'') as userName,isnull(te.contactPhone,'') as contactPhone,isnull(te.telephonemobile,'') as telephonemobile,(case when t.role =18 then '员工' when t.role %2 =0  then '管理员' else '操作员' end) as type,isnull(tc.companyName,'') as companyName,te.idCard,tc.type as comanyType,isnull(te.telephoneType,'') as telephoneType,isnull(te.versionCode,'') as versionCode from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid left join  TwoCodeCompanyInfo tc on te.companyid = tc.id where  "+ conditions+" and  t.isinvalid = 0 and (t.role = 18 or t.role =6 or t.role =7) ";
			 	 
		 }
		 else{
			 conditionsSql = "select count(*)   from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid left join  TwoCodeCompanyInfo tc on te.companyid = tc.id  where  isinvalid = ?  and (role = 18 or role =6 or role =7)";
		 sql ="select t.loginName,t.role,isnull(te.userName,'') as userName,isnull(te.contactPhone,'') as contactPhone,isnull(te.telephonemobile,'') as telephonemobile,(case when t.role =18 then '员工' when t.role %2 =0  then '管理员' else '操作员' end) as type,isnull(tc.companyName,'') as companyName,te.idCard,tc.type as comanyType,isnull(te.telephoneType,'') as telephoneType,isnull(te.versionCode,'') as versionCode from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id =te.userid left join  TwoCodeCompanyInfo tc on te.companyid = tc.id where t.isinvalid = 0 and (t.role = 18 or t.role =6 or t.role =7) ";
		 }
		 try {
			total  = UserInfoVO.countBySql(UserInfoVO.class, conditionsSql, new Object[]{0});
			items = UserInfoVO.findBySql(UserInfoVO.class, sql, null, "companyName asc", rows, (page-1)*rows);
			
		 } catch (ActiveRecordException e) {
			e.printStackTrace();
		}
    	
		 Map<String, Object> result = new HashMap<String, Object>();
	     result.put("total", total);
		 result.put("rows", items);
		 return new JsonView(result);
	 }
	 
	 public View versionCodeList() {
			List<UserInfoVO> items = null;
			try {
				items = UserInfoVO.findBySql(UserInfoVO.class,
						"select  distinct isnull(versionCode,'') as versionCode from twocodeuserinfo t left join  TwoCodeUserExtInfo te on t.id = te.userid  where t.isinvalid = 0  and (t.role = 18 or t.role =6 or t.role =7) group by versionCode", null);
			} catch (ActiveRecordException e) {
				
				e.printStackTrace();
			}
			return new JsonView(items);
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
} 
