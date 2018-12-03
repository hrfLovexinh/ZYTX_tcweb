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
@Table(name = "TwoCodeYwQuaCredRating")
public class YwQuaCredRatingHyxh extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;
    @Column
    private int ywCompanyID;
    @Column
    private String ratingDate;
	@Column
	private int officeSpace ;
	@Column
	private int officeSpacesj;
	@Column
	private String officeSpacebz;

	@Column
	private int  headQuarters;
	@Column
	private int  headQuarterssj;
	@Column
	private String  headQuartersbz;
	
	
    
	@Column 
	private java.lang.String fixedTelOnDuty;
	@Column 
	private int fixedTelOnDutysj;
	@Column 
	private String fixedTelOnDutybz;
	
	@Column 
	private int telOnDutyunattendedTimes;
	@Column 
	private int telOnDutyunattendedsj;
	@Column 
	private String telOnDutyunattendedbz;
	
	@Column
	private int enterpriseChangeTimes;
	@Column 
	private int enterpriseChangesj;
	@Column 
	private String enterpriseChangebz;

	@Column
	private int enterpriseRecord;
	@Column
	private int enterpriseRecordsj;
	@Column
	private String enterpriseRecordbz;
	@Column
	private String ratingUserName;
	@Column
	private int ratingCompanyId;
	@Column
	private int ratingType;
	
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
	
	
	public String getRatingDate() {
		return ratingDate;
	}
	public void setRatingDate(String ratingDate) {
		this.ratingDate = ratingDate;
	}
	public int getOfficeSpace() {
		return officeSpace;
	}
	public void setOfficeSpace(int officeSpace) {
		this.officeSpace = officeSpace;
	}
	public int getOfficeSpacesj() {
		return officeSpacesj;
	}
	public void setOfficeSpacesj(int officeSpacesj) {
		this.officeSpacesj = officeSpacesj;
	}
	public int getHeadQuarters() {
		return headQuarters;
	}
	public void setHeadQuarters(int headQuarters) {
		this.headQuarters = headQuarters;
	}
	public int getHeadQuarterssj() {
		return headQuarterssj;
	}
	public void setHeadQuarterssj(int headQuarterssj) {
		this.headQuarterssj = headQuarterssj;
	}
	public java.lang.String getFixedTelOnDuty() {
		return fixedTelOnDuty;
	}
	public void setFixedTelOnDuty(java.lang.String fixedTelOnDuty) {
		this.fixedTelOnDuty = fixedTelOnDuty;
	}
	public int getFixedTelOnDutysj() {
		return fixedTelOnDutysj;
	}
	public void setFixedTelOnDutysj(int fixedTelOnDutysj) {
		this.fixedTelOnDutysj = fixedTelOnDutysj;
	}
	public int getTelOnDutyunattendedTimes() {
		return telOnDutyunattendedTimes;
	}
	public void setTelOnDutyunattendedTimes(int telOnDutyunattendedTimes) {
		this.telOnDutyunattendedTimes = telOnDutyunattendedTimes;
	}
	public int getTelOnDutyunattendedsj() {
		return telOnDutyunattendedsj;
	}
	public void setTelOnDutyunattendedsj(int telOnDutyunattendedsj) {
		this.telOnDutyunattendedsj = telOnDutyunattendedsj;
	}
	public int getEnterpriseChangeTimes() {
		return enterpriseChangeTimes;
	}
	public void setEnterpriseChangeTimes(int enterpriseChangeTimes) {
		this.enterpriseChangeTimes = enterpriseChangeTimes;
	}
	public int getEnterpriseChangesj() {
		return enterpriseChangesj;
	}
	public void setEnterpriseChangesj(int enterpriseChangesj) {
		this.enterpriseChangesj = enterpriseChangesj;
	}
	public int getEnterpriseRecord() {
		return enterpriseRecord;
	}
	public void setEnterpriseRecord(int enterpriseRecord) {
		this.enterpriseRecord = enterpriseRecord;
	}
	public int getEnterpriseRecordsj() {
		return enterpriseRecordsj;
	}
	public void setEnterpriseRecordsj(int enterpriseRecordsj) {
		this.enterpriseRecordsj = enterpriseRecordsj;
	}
	
	public String getOfficeSpacebz() {
		return officeSpacebz;
	}
	public void setOfficeSpacebz(String officeSpacebz) {
		this.officeSpacebz = officeSpacebz;
	}
	public String getHeadQuartersbz() {
		return headQuartersbz;
	}
	public void setHeadQuartersbz(String headQuartersbz) {
		this.headQuartersbz = headQuartersbz;
	}
	public String getFixedTelOnDutybz() {
		return fixedTelOnDutybz;
	}
	public void setFixedTelOnDutybz(String fixedTelOnDutybz) {
		this.fixedTelOnDutybz = fixedTelOnDutybz;
	}
	public String getTelOnDutyunattendedbz() {
		return telOnDutyunattendedbz;
	}
	public void setTelOnDutyunattendedbz(String telOnDutyunattendedbz) {
		this.telOnDutyunattendedbz = telOnDutyunattendedbz;
	}
	public String getEnterpriseChangebz() {
		return enterpriseChangebz;
	}
	public void setEnterpriseChangebz(String enterpriseChangebz) {
		this.enterpriseChangebz = enterpriseChangebz;
	}
	public String getEnterpriseRecordbz() {
		return enterpriseRecordbz;
	}
	public void setEnterpriseRecordbz(String enterpriseRecordbz) {
		this.enterpriseRecordbz = enterpriseRecordbz;
	}
	public String getRatingUserName() {
		return ratingUserName;
	}
	public void setRatingUserName(String ratingUserName) {
		this.ratingUserName = ratingUserName;
	}
	public int getRatingCompanyId() {
		return ratingCompanyId;
	}
	public void setRatingCompanyId(int ratingCompanyId) {
		this.ratingCompanyId = ratingCompanyId;
	}
	public int getRatingType() {
		return ratingType;
	}
	public void setRatingType(int ratingType) {
		this.ratingType = ratingType;
	}
	
	
	
	
	
}