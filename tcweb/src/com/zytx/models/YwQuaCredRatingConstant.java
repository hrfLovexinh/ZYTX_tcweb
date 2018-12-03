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
import com.et.ar.annotations.GeneratorType;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.et.ar.exception.ActiveRecordException;
import com.zytx.converters.DateConverter;
import com.zytx.converters.TimestampConverter;

/** userInfo . */
@Table(name = "TwoCodeYwQuaCredRatConstant")
public class YwQuaCredRatingConstant extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;
    @Column
    private int ywCompanyID;
    @Column
    private int officeSpace;
    @Column
    private int officeSpacesj;
	@Column
	private String  fixedTelOnDuty;
	@Column
	private int fixedTelOnDutysj;
	
	@Column
	private String officeSpacebz;
	@Column
	private String fixedTelOnDutybz;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYwCompanyID() {
		return ywCompanyID;
	}
	public void setYwCompanyID(int ywCompanyID) {
		this.ywCompanyID = ywCompanyID;
	}
	public int getOfficeSpace() {
		return officeSpace;
	}
	public void setOfficeSpace(int officeSpace) {
		this.officeSpace = officeSpace;
	}
	public String getFixedTelOnDuty() {
		return fixedTelOnDuty;
	}
	public void setFixedTelOnDuty(String fixedTelOnDuty) {
		this.fixedTelOnDuty = fixedTelOnDuty;
	}
	public int getOfficeSpacesj() {
		return officeSpacesj;
	}
	public void setOfficeSpacesj(int officeSpacesj) {
		this.officeSpacesj = officeSpacesj;
	}
	public int getFixedTelOnDutysj() {
		return fixedTelOnDutysj;
	}
	public void setFixedTelOnDutysj(int fixedTelOnDutysj) {
		this.fixedTelOnDutysj = fixedTelOnDutysj;
	}
	public String getOfficeSpacebz() {
		return officeSpacebz;
	}
	public void setOfficeSpacebz(String officeSpacebz) {
		this.officeSpacebz = officeSpacebz;
	}
	public String getFixedTelOnDutybz() {
		return fixedTelOnDutybz;
	}
	public void setFixedTelOnDutybz(String fixedTelOnDutybz) {
		this.fixedTelOnDutybz = fixedTelOnDutybz;
	}
    
	
	
	
	
	
	
	
	
}