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
public class YwQuaCredRatingTjy extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;
    @Column
    private int ywCompanyID;
    @Column
    private String ratingDate;
	
    @Column
	private int regularInspectionTimes;
	@Column
	private int regularInspectionsj;
	@Column
	private String regularInspectionbz;
	@Column
	private int  inspectElevatorTimes;
	@Column
	private int  inspectElevatorTimes2;
	@Column 
	private int inspectElevatorsj;
	@Column 
	private String inspectElevatorbz;
	
	@Column
	private int acceptInspElevatorTimes;
	@Column
	private int acceptInspElevatorsj;
	@Column
	private String acceptInspElevatorbz;
	
	@Column 
	private int maintenSceneInfoTimes;
	@Column
	private int  maintenSceneInfosj;
	@Column
	private String  maintenSceneInfobz;
	
	
	
	
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
	
	public int getRegularInspectionTimes() {
		return regularInspectionTimes;
	}
	public void setRegularInspectionTimes(int regularInspectionTimes) {
		this.regularInspectionTimes = regularInspectionTimes;
	}
	public int getRegularInspectionsj() {
		return regularInspectionsj;
	}
	public void setRegularInspectionsj(int regularInspectionsj) {
		this.regularInspectionsj = regularInspectionsj;
	}
	public String getRegularInspectionbz() {
		return regularInspectionbz;
	}
	public void setRegularInspectionbz(String regularInspectionbz) {
		this.regularInspectionbz = regularInspectionbz;
	}
	public int getInspectElevatorTimes() {
		return inspectElevatorTimes;
	}
	public void setInspectElevatorTimes(int inspectElevatorTimes) {
		this.inspectElevatorTimes = inspectElevatorTimes;
	}
	public int getInspectElevatorTimes2() {
		return inspectElevatorTimes2;
	}
	public void setInspectElevatorTimes2(int inspectElevatorTimes2) {
		this.inspectElevatorTimes2 = inspectElevatorTimes2;
	}
	public int getInspectElevatorsj() {
		return inspectElevatorsj;
	}
	public void setInspectElevatorsj(int inspectElevatorsj) {
		this.inspectElevatorsj = inspectElevatorsj;
	}
	public String getInspectElevatorbz() {
		return inspectElevatorbz;
	}
	public void setInspectElevatorbz(String inspectElevatorbz) {
		this.inspectElevatorbz = inspectElevatorbz;
	}
	public int getAcceptInspElevatorTimes() {
		return acceptInspElevatorTimes;
	}
	public void setAcceptInspElevatorTimes(int acceptInspElevatorTimes) {
		this.acceptInspElevatorTimes = acceptInspElevatorTimes;
	}
	public int getAcceptInspElevatorsj() {
		return acceptInspElevatorsj;
	}
	public void setAcceptInspElevatorsj(int acceptInspElevatorsj) {
		this.acceptInspElevatorsj = acceptInspElevatorsj;
	}
	public String getAcceptInspElevatorbz() {
		return acceptInspElevatorbz;
	}
	public void setAcceptInspElevatorbz(String acceptInspElevatorbz) {
		this.acceptInspElevatorbz = acceptInspElevatorbz;
	}
	public int getMaintenSceneInfoTimes() {
		return maintenSceneInfoTimes;
	}
	public void setMaintenSceneInfoTimes(int maintenSceneInfoTimes) {
		this.maintenSceneInfoTimes = maintenSceneInfoTimes;
	}
	public int getMaintenSceneInfosj() {
		return maintenSceneInfosj;
	}
	public void setMaintenSceneInfosj(int maintenSceneInfosj) {
		this.maintenSceneInfosj = maintenSceneInfosj;
	}
	public String getMaintenSceneInfobz() {
		return maintenSceneInfobz;
	}
	public void setMaintenSceneInfobz(String maintenSceneInfobz) {
		this.maintenSceneInfobz = maintenSceneInfobz;
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