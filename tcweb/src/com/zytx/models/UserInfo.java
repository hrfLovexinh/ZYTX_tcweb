package com.zytx.models;

/***********************************************************************
 * Module:  UserInfo.java
 * Author:  Administrator
 * Purpose: Defines the Class UserInfo
 ***********************************************************************/

import java.util.*;
import java.sql.Timestamp;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.DependentType;
import com.et.ar.annotations.GeneratorType;
import com.et.ar.annotations.HasOne;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;
import com.zytx.converters.TimestampConverter;

/** userInfo . */
@Table(name = "TwoCodeUserInfo")
public class UserInfo extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	/** 用户名称 */
	@Column
	private java.lang.String loginName;
	/** 密码 */
	@Column
	private java.lang.String password;
	/** 角色 */
	@Column
	private int role;
	@Column
	private String iMSI;
	@Column
	private String iMEI;
	@Column
	private int binding;
	
	private int companyId;
	
	private String type;
	
	private String userName;
	@Column
	private int iskaoping;
	@Column
	private int ispcsuper;
	@Column
	private int isliulan;
	@Column
	private int isyanshi;
	
	@HasOne(foreignKey="userid",dependent=DependentType.DELETE,order = "id")
	private UserExtInfo userExtInfo;
	
    
  
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.lang.String getLoginName() {
		return loginName;
	}

	public void setLoginName(java.lang.String loginName) {
		this.loginName = loginName;
	}

	public java.lang.String getPassword() {
		return password;
	}

	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
	 
	public UserExtInfo getUserExtInfo() {
		return userExtInfo;
	}

	public void setUserExtInfo(UserExtInfo userExtInfo) {
		this.userExtInfo = userExtInfo;
	}
	
	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getiMSI() {
		return iMSI;
	}

	public void setiMSI(String iMSI) {
		this.iMSI = iMSI;
	}

	public String getiMEI() {
		return iMEI;
	}

	public void setiMEI(String iMEI) {
		this.iMEI = iMEI;
	}

	public int getBinding() {
		return binding;
	}

	public void setBinding(int binding) {
		this.binding = binding;
	}

	public int getIskaoping() {
		return iskaoping;
	}

	public void setIskaoping(int iskaoping) {
		this.iskaoping = iskaoping;
	}

	public int getIspcsuper() {
		return ispcsuper;
	}

	public void setIspcsuper(int ispcsuper) {
		this.ispcsuper = ispcsuper;
	}

	public int getIsliulan() {
		return isliulan;
	}

	public void setIsliulan(int isliulan) {
		this.isliulan = isliulan;
	}

	public int getIsyanshi() {
		return isyanshi;
	}

	public void setIsyanshi(int isyanshi) {
		this.isyanshi = isyanshi;
	}
	
	

}