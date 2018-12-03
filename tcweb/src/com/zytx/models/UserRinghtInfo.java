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
@Table(name = "TwoCodeRightsTable")
public class UserRinghtInfo extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	/** ”√ªß√˚≥∆ */
	@Column
	private int  userId;
	/** √‹¬Î */
	@Column
	private int ywtx;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getYwtx() {
		return ywtx;
	}
	public void setYwtx(int ywtx) {
		this.ywtx = ywtx;
	}
	
	
	

  
	
	

}