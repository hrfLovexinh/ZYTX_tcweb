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
@Table(name = "TwoCodeYwcompanyHMD")
public class YwKaoHmdInfo extends ActiveRecordBase {

	
	@Id
	private int id;
	/** ¹«Ë¾Ãû³Æ */
	@Column
	private java.lang.String ywCompanyName;
	@Column
	private int ywcompanyId;
	@Column
	private java.lang.String hmdDate;
	@Column
	private java.lang.String hmdReason;
	@Column
	private int operatoruid;
	
	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.lang.String getYwCompanyName() {
		return ywCompanyName;
	}

	public void setYwCompanyName(java.lang.String ywCompanyName) {
		this.ywCompanyName = ywCompanyName;
	}

	public int getYwcompanyId() {
		return ywcompanyId;
	}

	public void setYwcompanyId(int ywcompanyId) {
		this.ywcompanyId = ywcompanyId;
	}

	public java.lang.String getHmdDate() {
		return hmdDate;
	}

	public void setHmdDate(java.lang.String hmdDate) {
		this.hmdDate = hmdDate;
	}

	public java.lang.String getHmdReason() {
		return hmdReason;
	}

	public void setHmdReason(java.lang.String hmdReason) {
		this.hmdReason = hmdReason;
	}

	public int getOperatoruid() {
		return operatoruid;
	}

	public void setOperatoruid(int operatoruid) {
		this.operatoruid = operatoruid;
	}
	
	
	
	
	
	
	
	
}