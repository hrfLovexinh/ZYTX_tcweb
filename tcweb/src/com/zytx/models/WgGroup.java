package com.zytx.models;

/***********************************************************************
 * Module:  UserInfo.java
 * Author:  Administrator
 * Purpose: Defines the Class UserInfo
 ***********************************************************************/

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
@Table(name = "TwoCodeCompanyInfo")
public class WgGroup extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	/** 公司代码 */
	@Column
	private java.lang.String gCode;
	/** 公司名称 */
	@Column
	private java.lang.String gName;
	/** 负责人 */
	@Column
	private java.lang.String gheader;
		
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public java.lang.String getgCode() {
		return gCode;
	}
	public void setgCode(java.lang.String gCode) {
		this.gCode = gCode;
	}
	public java.lang.String getgName() {
		return gName;
	}
	public void setgName(java.lang.String gName) {
		this.gName = gName;
	}
	public java.lang.String getGheader() {
		return gheader;
	}
	public void setGheader(java.lang.String gheader) {
		this.gheader = gheader;
	}
	
    
	
}