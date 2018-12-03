package com.zytx.models;

/***********************************************************************
 * Module:  UserInfo.java
 * Author:  Administrator
 * Purpose: Defines the Class UserInfo
 ***********************************************************************/



import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;

/** userInfo . */
@Table(name = "TwoCodeCompanyChangeInfo")
public class CompanyChangeInfo extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;
	@Column
	private int companyid;
	/** 公司名称 */
	@Column
	private java.lang.String oldName;
	/** 公司名称 */
	@Column
	private java.lang.String nowName;
	/** 地址 */
	@Column
	private java.lang.String updateTime;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCompanyid() {
		return companyid;
	}

	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}

	public java.lang.String getOldName() {
		return oldName;
	}

	public void setOldName(java.lang.String oldName) {
		this.oldName = oldName;
	}

	public java.lang.String getNowName() {
		return nowName;
	}

	public void setNowName(java.lang.String nowName) {
		this.nowName = nowName;
	}

	public java.lang.String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.lang.String updateTime) {
		this.updateTime = updateTime;
	}

	
	
	
    
	
}