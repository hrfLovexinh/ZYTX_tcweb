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
public class YwQuaCredRatingQzj extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;
    @Column
    private int ywCompanyID;
    @Column
    private String ratingDate;
	
	
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
	private int complaintsEventsTimes;
	@Column
	private int complaintsEventsTimes2;
	@Column
	private int complaintsEventssj;
	@Column
	private String complaintsEventsbz;
	
	@Column
	private int maintenBusinessTimes;
	@Column
	private int maintenBusinesssj;
	@Column
	private String maintenBusinessbz;
	
	@Column
	private int honestTimes;
	@Column
	private int honestsj;
	@Column
	private String honestbz;
	
	@Column
	private int punishmentTimes;
	@Column
	private int punishmentTimes2;
	@Column
	private int punishmentTimes3;
	@Column
	private int punishmentTimes4;
	@Column
	private int punishmentsj;
	@Column
	private String punishmentbz;
	
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
	public int getComplaintsEventsTimes() {
		return complaintsEventsTimes;
	}
	public void setComplaintsEventsTimes(int complaintsEventsTimes) {
		this.complaintsEventsTimes = complaintsEventsTimes;
	}
	public int getComplaintsEventsTimes2() {
		return complaintsEventsTimes2;
	}
	public void setComplaintsEventsTimes2(int complaintsEventsTimes2) {
		this.complaintsEventsTimes2 = complaintsEventsTimes2;
	}
	public int getComplaintsEventssj() {
		return complaintsEventssj;
	}
	public void setComplaintsEventssj(int complaintsEventssj) {
		this.complaintsEventssj = complaintsEventssj;
	}
	public String getComplaintsEventsbz() {
		return complaintsEventsbz;
	}
	public void setComplaintsEventsbz(String complaintsEventsbz) {
		this.complaintsEventsbz = complaintsEventsbz;
	}
	public int getMaintenBusinessTimes() {
		return maintenBusinessTimes;
	}
	public void setMaintenBusinessTimes(int maintenBusinessTimes) {
		this.maintenBusinessTimes = maintenBusinessTimes;
	}
	public int getMaintenBusinesssj() {
		return maintenBusinesssj;
	}
	public void setMaintenBusinesssj(int maintenBusinesssj) {
		this.maintenBusinesssj = maintenBusinesssj;
	}
	public String getMaintenBusinessbz() {
		return maintenBusinessbz;
	}
	public void setMaintenBusinessbz(String maintenBusinessbz) {
		this.maintenBusinessbz = maintenBusinessbz;
	}
	public int getHonestTimes() {
		return honestTimes;
	}
	public void setHonestTimes(int honestTimes) {
		this.honestTimes = honestTimes;
	}
	public int getHonestsj() {
		return honestsj;
	}
	public void setHonestsj(int honestsj) {
		this.honestsj = honestsj;
	}
	public String getHonestbz() {
		return honestbz;
	}
	public void setHonestbz(String honestbz) {
		this.honestbz = honestbz;
	}
	public int getPunishmentTimes() {
		return punishmentTimes;
	}
	public void setPunishmentTimes(int punishmentTimes) {
		this.punishmentTimes = punishmentTimes;
	}
	public int getPunishmentTimes2() {
		return punishmentTimes2;
	}
	public void setPunishmentTimes2(int punishmentTimes2) {
		this.punishmentTimes2 = punishmentTimes2;
	}
	public int getPunishmentTimes3() {
		return punishmentTimes3;
	}
	public void setPunishmentTimes3(int punishmentTimes3) {
		this.punishmentTimes3 = punishmentTimes3;
	}
	public int getPunishmentTimes4() {
		return punishmentTimes4;
	}
	public void setPunishmentTimes4(int punishmentTimes4) {
		this.punishmentTimes4 = punishmentTimes4;
	}
	public int getPunishmentsj() {
		return punishmentsj;
	}
	public void setPunishmentsj(int punishmentsj) {
		this.punishmentsj = punishmentsj;
	}
	public String getPunishmentbz() {
		return punishmentbz;
	}
	public void setPunishmentbz(String punishmentbz) {
		this.punishmentbz = punishmentbz;
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