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
import com.zytx.converters.DateConverter2;
import com.zytx.converters.TimestampConverter;

/** userInfo . */
@Table(name = "TwoCodeloginfo")
public class TwoCodeLogInfoVO extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	/** 用户账号 */
	@Column
	private java.lang.String logPerson ;
	/** 公司 */
	@Column
	private java.lang.String logPersonCompany;
	@Column
	private java.lang.String logTime;
	@Column
	private String logAction;
	@Column
	private String logContext;
	
	private String qstartTime;
	private String qendTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public java.lang.String getLogPerson() {
		return logPerson;
	}
	public void setLogPerson(java.lang.String logPerson) {
		this.logPerson = logPerson;
	}
	public java.lang.String getLogPersonCompany() {
		return logPersonCompany;
	}
	public void setLogPersonCompany(java.lang.String logPersonCompany) {
		this.logPersonCompany = logPersonCompany;
	}
	
	public String getLogAction() {
		return logAction;
	}
	public void setLogAction(String logAction) {
		this.logAction = logAction;
	}
	public String getLogContext() {
		return logContext;
	}
	public void setLogContext(String logContext) {
		this.logContext = logContext;
	}
	public java.lang.String getLogTime() {
		return logTime;
	}
	public void setLogTime(java.lang.String logTime) {
		this.logTime = logTime;
	}
	public String getQstartTime() {
		return qstartTime;
	}
	public void setQstartTime(String qstartTime) {
		this.qstartTime = qstartTime;
	}
	public String getQendTime() {
		return qendTime;
	}
	public void setQendTime(String qendTime) {
		this.qendTime = qendTime;
	}
	
	
	
   
	
}