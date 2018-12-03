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
@Table(name = "TwoCodenewsinfo")
public class TwoCodeNewsInfoVO extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	/** ”√ªß’À∫≈ */
	@Column
	private java.lang.String newsSender ;
	
	@Column
	private java.lang.String newsSendTime;
	@Column
	private String newsTitle;
	@Column
	private String newsContext;
	
	@Column
	private int newsState;
	
	@Column
	private String newsStateName;
	@Column
	private int id2;
	@Column
	private String newsReceiverName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public java.lang.String getNewsSender() {
		return newsSender;
	}
	public void setNewsSender(java.lang.String newsSender) {
		this.newsSender = newsSender;
	}
	public java.lang.String getNewsSendTime() {
		return newsSendTime;
	}
	public void setNewsSendTime(java.lang.String newsSendTime) {
		this.newsSendTime = newsSendTime;
	}
	public String getNewsTitle() {
		return newsTitle;
	}
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}
	public String getNewsContext() {
		return newsContext;
	}
	public void setNewsContext(String newsContext) {
		this.newsContext = newsContext;
	}
	public int getNewsState() {
		return newsState;
	}
	public void setNewsState(int newsState) {
		this.newsState = newsState;
	}
	public String getNewsStateName() {
		return newsStateName;
	}
	public void setNewsStateName(String newsStateName) {
		this.newsStateName = newsStateName;
	}
	public int getId2() {
		return id2;
	}
	public void setId2(int id2) {
		this.id2 = id2;
	}
	public String getNewsReceiverName() {
		return newsReceiverName;
	}
	public void setNewsReceiverName(String newsReceiverName) {
		this.newsReceiverName = newsReceiverName;
	}
	
	
	
   
	
}