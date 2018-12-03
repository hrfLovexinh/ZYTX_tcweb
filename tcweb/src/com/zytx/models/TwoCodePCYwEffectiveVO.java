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
public class TwoCodePCYwEffectiveVO extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	/** 用户账号 */
	@Column
	private String ywCompanyIdinfo ;
	/** 公司 */
	@Column
	private java.lang.String effective;
	
	@Column
	private java.lang.String begintime;
	@Column
	private int daytime;
	
	private int ywCompanyIdinfo2;
	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getYwCompanyIdinfo() {
		return ywCompanyIdinfo;
	}
	public void setYwCompanyIdinfo(String ywCompanyIdinfo) {
		this.ywCompanyIdinfo = ywCompanyIdinfo;
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
	public int getYwCompanyIdinfo2() {
		return ywCompanyIdinfo2;
	}
	public void setYwCompanyIdinfo2(int ywCompanyIdinfo2) {
		this.ywCompanyIdinfo2 = ywCompanyIdinfo2;
	}
	
	
   
	
}