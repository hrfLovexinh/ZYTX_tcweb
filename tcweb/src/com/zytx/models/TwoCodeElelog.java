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
@Table(name = "TwoCodeElelog")
public class TwoCodeElelog extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	/** ”√ªß’À∫≈ */
	@Column
	private java.lang.String userName ;
	/** ƒ⁄»› */
	@Column
	private java.lang.String context;
	
	@Column
	private String subTime;
	@Column
	private String cmd;
	
	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public java.lang.String getUserName() {
		return userName;
	}
	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}
	public java.lang.String getContext() {
		return context;
	}
	public void setContext(java.lang.String context) {
		this.context = context;
	}
	public String getSubTime() {
		return subTime;
	}
	public void setSubTime(String subTime) {
		this.subTime = subTime;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	
	
  
	
}