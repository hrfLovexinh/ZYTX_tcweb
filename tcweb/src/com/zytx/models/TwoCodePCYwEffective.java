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
@Table(name = "TwoCodeYWEffective")
public class TwoCodePCYwEffective extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	/** 用户账号 */
	@Column
	private int ywCompanyId ;
	/** 公司 */
	@Column
	private java.lang.String effective;
	
	@Column
	private java.lang.String begintime;
	@Column
	private int daytime;
	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYwCompanyId() {
		return ywCompanyId;
	}
	public void setYwCompanyId(int ywCompanyId) {
		this.ywCompanyId = ywCompanyId;
	}
	public java.lang.String getEffective() {
		return effective;
	}
	public void setEffective(java.lang.String effective) {
		this.effective = effective;
	}
	public java.lang.String getBegintime() {
		return begintime;
	}
	public void setBegintime(java.lang.String begintime) {
		this.begintime = begintime;
	}
	public int getDaytime() {
		return daytime;
	}
	public void setDaytime(int daytime) {
		this.daytime = daytime;
	}
	
	
   
	
}