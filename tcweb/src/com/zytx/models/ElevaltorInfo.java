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
@Table(name = "TwoCodeElevatorInfo")
public class ElevaltorInfo extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	/** ��ά��ID */
	@Column
	private java.lang.String twoCodeId;
	/** ���ݱ�� */
	@Column
	private java.lang.String registNumber;
	/** ��ַ */
	@Column
	private java.lang.String address;
	/** �ڲ�ʹ�ñ�� */
	@Column
	private java.lang.String useNumber;
	/** ע����� */
	@Column
	private java.lang.String registCode;
	/** ������� */
	@Column
	private java.lang.String eleType;
	/** ���� */
	@Column
	private java.lang.String name;
	/** �������� */
	@Column
	private java.lang.String eleMode;
	/** ������Ա */
	@Column
	private java.lang.String inspector;
	/** �´μ������� */
	@Column
	private java.lang.String nextInspectDate;
	/** ��ܹ�˾ID */
	@Column
	private int wgCompanyId;
	/** ��ά��˾ID */
	@Column
	private int ywCompanyId;
	/** ���칫˾ID */
	@Column
	private int zzCompanyId;
	/** ��װ��˾ID */
	@Column
	private int azCompanyId;
	/** ���鹫˾ID */
	@Column
	private int jyCompanyId;
	/** �ʼ๫˾ID */
	@Column
	private int zjCompanyId;
	/** �����ٶ� */
	@Column
	private java.lang.String speed;
	/** ����� */
	@Column
	private java.lang.String eleLoad;
	/** ��վ */
	@Column
	private java.lang.String eleStop;
	/** ������ */
	@Column
	private java.lang.String inoutDoor;
	/** �߶� */
	@Column
	private java.lang.String eleheight;
	/** ��� */
	@Column
	private java.lang.String elewidth;
	/** �ֻ�Ψһ�� */
	@Column
	private java.lang.String mobileCode;
	/** ���ʱ�� */
	@Column
	private java.sql.Date ruKudate;
	/** ���ݴ����־ */
	@Column
	private int isVaild;
	
	private String deviceId;
	private String deviceId2;
	
	/**��������*/
	@Column
	private String area;  
	/**��������/�ֵ�*/
	@Column
	private int townshipStreets;
	/**¥������*/
	@Column
	private String buildingName;
	/**���ڶ�*/
	@Column
	private String building;
	/**���ڵ�Ԫ*/
	@Column
	private String unit;
	/**����*/
	@Column
	private String coordinates;
	/**��������*/
	@Column
	private String manufactDate;
	/**�������*/
	@Column
	private String factoryNum;
	/**Ͷ������*/
	@Column
	private String useDate;
	/**�������*/
	@Column
	private String checkCategory;
	/**�������*/
	@Column
	private String checkResult;
	/**������*/
	@Column
	private String checkReportNum;
	/**��������*/
	@Column
	private String inspectDate;
	/**�ǼǱ��*/
	@Column
	private String registCode2;
	/**ע��Ǽ���Ա*/
	@Column
	private String registor;
	/**��ע*/
	@Column
	private String note;
	@Column
	private String map_X;
	@Column
	private String map_Y;
	@Column
	private String updateTime;
	@Column
	private int ischangInfo;
	
	private int oldywCompanyId;
	@Column
	private String safetyManDepart;
	@Column
	private String safetyManPerson;
	@Column
	private String safetyManPersonTel;
	@Column
	private String propertyRightsUnit;
	@Column
	private String completeAcceptanceDate;
	@Column
	private String acceptanceDateDepart;
	@Column
	private String acceptanceReportNum;
	@Column
	private String mContractVdate;
	@Column
	private String handleCompany;
	@Column
	private String handleCompanyCode;
	@Column
	private String changeWay;
	@Column
	private String shibieCode;
	@Column 
	private String neleType;
	
	@Column
	private String usePlace;
	
	@Column
	private String precise;
	
	
	public String getPrecise() {
		return precise;
	}

	public void setPrecise(String precise) {
		this.precise = precise;
	}

	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }
	
	

	public String getUsePlace() {
		return usePlace;
	}

	public void setUsePlace(String usePlace) {
		this.usePlace = usePlace;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.lang.String getTwoCodeId() {
		return twoCodeId;
	}

	public void setTwoCodeId(java.lang.String twoCodeId) {
		this.twoCodeId = twoCodeId;
	}

	public java.lang.String getRegistNumber() {
		return registNumber;
	}

	public void setRegistNumber(java.lang.String registNumber) {
		this.registNumber = registNumber;
	}

	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public java.lang.String getUseNumber() {
		return useNumber;
	}

	public void setUseNumber(java.lang.String useNumber) {
		this.useNumber = useNumber;
	}

	public java.lang.String getRegistCode() {
		return registCode;
	}

	public void setRegistCode(java.lang.String registCode) {
		this.registCode = registCode;
	}

	public java.lang.String getEleType() {
		return eleType;
	}

	public void setEleType(java.lang.String eleType) {
		this.eleType = eleType;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getEleMode() {
		return eleMode;
	}

	public void setEleMode(java.lang.String eleMode) {
		this.eleMode = eleMode;
	}

	public java.lang.String getInspector() {
		return inspector;
	}

	public void setInspector(java.lang.String inspector) {
		this.inspector = inspector;
	}

	public java.lang.String getNextInspectDate() {
		return nextInspectDate;
	}

	public void setNextInspectDate(java.lang.String nextInspectDate) {
		this.nextInspectDate = nextInspectDate;
	}

	public int getWgCompanyId() {
		return wgCompanyId;
	}

	public void setWgCompanyId(int wgCompanyId) {
		this.wgCompanyId = wgCompanyId;
	}

	public int getYwCompanyId() {
		return ywCompanyId;
	}

	public void setYwCompanyId(int ywCompanyId) {
		this.ywCompanyId = ywCompanyId;
	}

	public int getZzCompanyId() {
		return zzCompanyId;
	}

	public void setZzCompanyId(int zzCompanyId) {
		this.zzCompanyId = zzCompanyId;
	}

	public int getAzCompanyId() {
		return azCompanyId;
	}

	public void setAzCompanyId(int azCompanyId) {
		this.azCompanyId = azCompanyId;
	}

	public int getJyCompanyId() {
		return jyCompanyId;
	}

	public void setJyCompanyId(int jyCompanyId) {
		this.jyCompanyId = jyCompanyId;
	}

	public int getZjCompanyId() {
		return zjCompanyId;
	}

	public void setZjCompanyId(int zjCompanyId) {
		this.zjCompanyId = zjCompanyId;
	}

	public java.lang.String getSpeed() {
		return speed;
	}

	public void setSpeed(java.lang.String speed) {
		this.speed = speed;
	}

	public java.lang.String getEleLoad() {
		return eleLoad;
	}

	public void setEleLoad(java.lang.String eleLoad) {
		this.eleLoad = eleLoad;
	}

	public java.lang.String getEleStop() {
		return eleStop;
	}

	public void setEleStop(java.lang.String eleStop) {
		this.eleStop = eleStop;
	}

	public java.lang.String getMobileCode() {
		return mobileCode;
	}

	public void setMobileCode(java.lang.String mobileCode) {
		this.mobileCode = mobileCode;
	}

	public java.sql.Date getRuKudate() {
		return ruKudate;
	}

	public void setRuKudate(java.sql.Date ruKudate) {
		this.ruKudate = ruKudate;
	}

	public int getIsVaild() {
		return isVaild;
	}

	public void setIsVaild(int isVaild) {
		this.isVaild = isVaild;
	}

	public java.lang.String getInoutDoor() {
		return inoutDoor;
	}

	public void setInoutDoor(java.lang.String inoutDoor) {
		this.inoutDoor = inoutDoor;
	}

	public java.lang.String getEleheight() {
		return eleheight;
	}

	public void setEleheight(java.lang.String eleheight) {
		this.eleheight = eleheight;
	}

	public java.lang.String getElewidth() {
		return elewidth;
	}

	public void setElewidth(java.lang.String elewidth) {
		this.elewidth = elewidth;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getTownshipStreets() {
		return townshipStreets;
	}

	public void setTownshipStreets(int townshipStreets) {
		this.townshipStreets = townshipStreets;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public String getManufactDate() {
		return manufactDate;
	}

	public void setManufactDate(String manufactDate) {
		this.manufactDate = manufactDate;
	}

	public String getFactoryNum() {
		return factoryNum;
	}

	public void setFactoryNum(String factoryNum) {
		this.factoryNum = factoryNum;
	}

	public String getUseDate() {
		return useDate;
	}

	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}

	public String getCheckCategory() {
		return checkCategory;
	}

	public void setCheckCategory(String checkCategory) {
		this.checkCategory = checkCategory;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getCheckReportNum() {
		return checkReportNum;
	}

	public void setCheckReportNum(String checkReportNum) {
		this.checkReportNum = checkReportNum;
	}

	public String getInspectDate() {
		return inspectDate;
	}

	public void setInspectDate(String inspectDate) {
		this.inspectDate = inspectDate;
	}

	public String getRegistCode2() {
		return registCode2;
	}

	public void setRegistCode2(String registCode2) {
		this.registCode2 = registCode2;
	}

	public String getRegistor() {
		return registor;
	}

	public void setRegistor(String registor) {
		this.registor = registor;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getMap_X() {
		return map_X;
	}

	public void setMap_X(String mapX) {
		map_X = mapX;
	}

	public String getMap_Y() {
		return map_Y;
	}

	public void setMap_Y(String mapY) {
		map_Y = mapY;
	}
	
	
	
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	


	public int getIschangInfo() {
		return ischangInfo;
	}

	public void setIschangInfo(int ischangInfo) {
		this.ischangInfo = ischangInfo;
	}

	public int getOldywCompanyId() {
		return oldywCompanyId;
	}

	public void setOldywCompanyId(int oldywCompanyId) {
		this.oldywCompanyId = oldywCompanyId;
	}

	public String getSafetyManDepart() {
		return safetyManDepart;
	}

	public void setSafetyManDepart(String safetyManDepart) {
		this.safetyManDepart = safetyManDepart;
	}

	public String getSafetyManPerson() {
		return safetyManPerson;
	}

	public void setSafetyManPerson(String safetyManPerson) {
		this.safetyManPerson = safetyManPerson;
	}

	public String getSafetyManPersonTel() {
		return safetyManPersonTel;
	}
 
    
	public void setSafetyManPersonTel(String safetyManPersonTel) {
		this.safetyManPersonTel = safetyManPersonTel;
	}

	public String getPropertyRightsUnit() {
		return propertyRightsUnit;
	}

	public void setPropertyRightsUnit(String propertyRightsUnit) {
		this.propertyRightsUnit = propertyRightsUnit;
	}

	public String getCompleteAcceptanceDate() {
		return completeAcceptanceDate;
	}

	public void setCompleteAcceptanceDate(String completeAcceptanceDate) {
		this.completeAcceptanceDate = completeAcceptanceDate;
	}

	public String getAcceptanceDateDepart() {
		return acceptanceDateDepart;
	}

	public void setAcceptanceDateDepart(String acceptanceDateDepart) {
		this.acceptanceDateDepart = acceptanceDateDepart;
	}

	public String getAcceptanceReportNum() {
		return acceptanceReportNum;
	}

	public void setAcceptanceReportNum(String acceptanceReportNum) {
		this.acceptanceReportNum = acceptanceReportNum;
	}

	public String getmContractVdate() {
		return mContractVdate;
	}

	public void setmContractVdate(String mContractVdate) {
		this.mContractVdate = mContractVdate;
	}

	public String getHandleCompany() {
		return handleCompany;
	}

	public void setHandleCompany(String handleCompany) {
		this.handleCompany = handleCompany;
	}

	public String getHandleCompanyCode() {
		return handleCompanyCode;
	}

	public void setHandleCompanyCode(String handleCompanyCode) {
		this.handleCompanyCode = handleCompanyCode;
	}

	public String getChangeWay() {
		return changeWay;
	}

	public void setChangeWay(String changeWay) {
		this.changeWay = changeWay;
	}

	public String getDeviceId2() {
		return deviceId2;
	}

	public void setDeviceId2(String deviceId2) {
		this.deviceId2 = deviceId2;
	}

	public String getShibieCode() {
		return shibieCode;
	}

	public void setShibieCode(String shibieCode) {
		this.shibieCode = shibieCode;
	}

	public String getNeleType() {
		return neleType;
	}

	public void setNeleType(String neleType) {
		this.neleType = neleType;
	}
	
	
	
   /*
	public void afterUpdate() throws ActiveRecordException {
		System.out.println("������Ϣ�б��,ԭ��ά��˾ID��"+this.oldywCompanyId+",����ά��˾��ID��"+this.ywCompanyId);
		
	}
   */    
	
}