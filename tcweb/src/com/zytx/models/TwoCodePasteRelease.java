package com.zytx.models;

/***********************************************************************
 * Module:  UserInfo.java
 * Author:  Administrator
 * Purpose: Defines the Class UserInfo
 ***********************************************************************/

import java.util.*;
import java.io.File;
import java.sql.Timestamp;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.GeneratorType;
import com.et.ar.annotations.HasOne;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;
import com.zytx.converters.TimestampConverter;

/** userInfo . */
@Table(name = "TwoCodePasteReleaseTab")
public class TwoCodePasteRelease extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	/** ��˾���� */
	@Column
	private int companyId;
	/** ���� */
	@Column
	private int rcount;  
	/** ��ȡ�� */
	@Column
	private java.lang.String rhaoduan;
	@Column
	private java.lang.String receivor;
	/** ��ȡʱ�� */
	@Column
	private java.lang.String receiveTime;
	
	
	/*
	@HasOne(foreignKey="id")
	private UserExtInfo userExtInfo;
	*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getRcount() {
		return rcount;
	}
	public void setRcount(int rcount) {
		this.rcount = rcount;
	}
	public java.lang.String getReceivor() {
		return receivor;
	}
	public void setReceivor(java.lang.String receivor) {
		this.receivor = receivor;
	}
	public java.lang.String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(java.lang.String receiveTime) {
		this.receiveTime = receiveTime;
	}
	public java.lang.String getRhaoduan() {
		return rhaoduan;
	}
	public void setRhaoduan(java.lang.String rhaoduan) {
		this.rhaoduan = rhaoduan;
	}
	
	
	
	
	/*
	public UserExtInfo getUserExtInfo() {
		return userExtInfo;
	}
	public void setUserExtInfo(UserExtInfo userExtInfo) {
		this.userExtInfo = userExtInfo;
	}
	*/
    
	
}