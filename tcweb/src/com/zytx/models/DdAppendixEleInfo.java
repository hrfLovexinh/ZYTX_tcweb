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
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;


/** userInfo . */
@Table(name = "TwoCodeDdEleappendix")
public class DdAppendixEleInfo extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;


	@Column
	private java.lang.String userName;

	@Column
	private java.lang.String registNumber;

	@Column
	private java.sql.Date pasteTime;
	/** µç»° */
	@Column
	private java.sql.Date subTime;
	
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

	public java.lang.String getRegistNumber() {
		return registNumber;
	}

	public void setRegistNumber(java.lang.String registNumber) {
		this.registNumber = registNumber;
	}

	public java.sql.Date getPasteTime() {
		return pasteTime;
	}

	public void setPasteTime(java.sql.Date pasteTime) {
		this.pasteTime = pasteTime;
	}

	public java.sql.Date getSubTime() {
		return subTime;
	}

	public void setSubTime(java.sql.Date subTime) {
		this.subTime = subTime;
	}

	
	
	
    
	
}