package com.zytx.models;

/***********************************************************************
 * Module:  UserInfo.java
 * Author:  Administrator
 * Purpose: Defines the Class UserInfo
 ***********************************************************************/

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Timestamp;

import net.sf.json.JSONObject;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.GeneratorType;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;
import com.zytx.converters.TimestampConverter;

/** userInfo . */
@Table(name = "TwoCodeElevatorInfo")
public class ElevaltorInfoVO extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	/** 二维码ID */
	@Column
	private java.lang.String twoCodeId;
	/** 电梯编号 */
	@Column
	private java.lang.String registNumber;
	/** 地址 */
	@Column
	private java.lang.String address;
	/** 内部使用编号 */
	@Column
	private java.lang.String useNumber;
	/** 电梯注册码 */
	@Column
	private java.lang.String registCode;
	/** 电梯类别 */
	@Column
	private java.lang.String eleType;
	/** 名称 */
	@Column
	private java.lang.String name;
	/** 电梯类型 */
	@Column
	private java.lang.String eleMode;
	/** 检验人员 */
	@Column
	private java.lang.String inspector;
	/** 下次检验日期 */
	@Column
	private java.lang.String nextInspectDate;
	/** 物管公司ID */
	@Column
	private int wgCompanyId;
	/** 运维公司ID */
	@Column
	private int ywCompanyId;
	/** 制造公司ID */
	@Column
	private int zzCompanyId;
	/** 安装公司ID */
	@Column
	private int azCompanyId;
	/** 检验公司ID */
	@Column
	private int jyCompanyId;
	/** 质监公司ID */
	@Column
	private int zjCompanyId;
	/** 电梯速度 */
	@Column
	private java.lang.String speed;
	/** 额定荷载 */
	@Column
	private java.lang.String eleLoad;
	/** 层站 */
	@Column
	private java.lang.String eleStop;
	/** 室内外 */
	@Column
	private java.lang.String inoutDoor;
	/** 高度 */
	@Column
	private java.lang.String eleheight;
	/** 宽度 */
	@Column
	private java.lang.String elewidth;
	/** 手机唯一码 */
	@Column
	private java.lang.String mobileCode;
	/** 入库时间 */
	@Column
	private java.sql.Date ruKudate;
	/** 数据传输标志 */
	@Column
	private int isVaild;
	
	@Column
	private String wgCompanyName;
	@Column
	private String ywCompanyName;
	@Column
	private String zzCompanyName;
	@Column
	private String azCompanyName;
	@Column
	private String jyCompanyName;
	@Column
	private String zjCompanyName;
	@Column 
	private String jdbCompanyName;
	
	@Column
	private String deviceId;
	@Column
	private String deviceId2;
	
	/**所属区域*/
	@Column
	private String area;  
	/**所在乡镇/街道*/
	@Column
	private int townshipStreets;
	/**楼盘名称*/
	@Column
	private String buildingName;
	/**所在栋*/
	@Column
	private String building;
	/**所在单元*/
	@Column
	private String unit;
	/**坐标*/
	@Column
	private String coordinates;
	/**制造日期*/
	@Column
	private String manufactDate;
	/**出厂编号*/
	@Column
	private String factoryNum;
	/**投用日期*/
	@Column
	private String useDate;
	/**检验类别*/
	@Column
	private String checkCategory;
	/**检验结论*/
	@Column
	private String checkResult;
	/**报告编号*/
	@Column
	private String checkReportNum;
	/**检验日期*/
	@Column
	private String inspectDate;
	/**注册代码*/
	@Column
	private String registCode2;
	/**注册登记人员*/
	@Column
	private String registor;
	/**备注*/
	@Column
	private String note;
	@Column
	private String map_X;
	@Column
	private String map_Y;
	
	@Column
	private int ischangInfo;
	
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
	private String subTime;   //最近一次运维时间
	
	@Column
	private String shenheTime2;
	private String qstartTime;
	private String qendTime;
	private String qstartTime2;
	private String qendTime2;
	@Column
	private int dailingFlag;
	@Column
	private int shemiFlag;
	@Column
    private int etotal;
	@Column
	private int setotal;
	@Column
	private int ncqetotal;
	@Column
	private int tsrtotal;
	@Column
	private int undotsrtotal;
	@Column
	private int ywcompanytotal;
	@Column
	private String endTime;
	@Column
	private String remarkDate;
	@Column
	private String remarkInfo;
	@Column
	private int smetotal;
	@Column 
	private String contactPhone; 
	@Column 
	private String userName;
	@Column
	private String telephone;
	@Column
	private String area2;
	@Column
	private String address2;
	@Column
	private String buildingName2;
	@Column
	private String building2;
	@Column
	private String unit2;
	@Column
	private String useNumber2;
	@Column
	private int resouceFlag;
	@Column
	private int jyjyFlag;
	@Column
	private String registCode3;
	@Column
	private String area3;
	@Column
	private String address3;
	@Column
	private String buildingName3;
	@Column
	private String building3;
	@Column
	private String unit3;
	@Column
	private String useNumber3;
	@Column
	private String shibieCode;
	@Column 
	private String neleType;
	
	private String ratingDate;
	
	@Column
	private String usePlace;
	
	public String getUsePlace() {
		return usePlace;
	}

	public void setUsePlace(String usePlace) {
		this.usePlace = usePlace;
	}
	
	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
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
  /*	String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\s*|\t|\r|\n]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(address);
		return m.replaceAll("").trim();
    */
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

	public String getWgCompanyName() {
		return wgCompanyName;
	}

	public void setWgCompanyName(String wgCompanyName) {
		this.wgCompanyName = wgCompanyName;
	}

	public String getYwCompanyName() {
		return ywCompanyName;
	}

	public void setYwCompanyName(String ywCompanyName) {
		this.ywCompanyName = ywCompanyName;
	}

	public String getZzCompanyName() {
		return zzCompanyName;
	}

	public void setZzCompanyName(String zzCompanyName) {
		this.zzCompanyName = zzCompanyName;
	}

	public String getAzCompanyName() {
		return azCompanyName;
	}

	public void setAzCompanyName(String azCompanyName) {
		this.azCompanyName = azCompanyName;
	}

	public String getJyCompanyName() {
		return jyCompanyName;
	}

	public void setJyCompanyName(String jyCompanyName) {
		this.jyCompanyName = jyCompanyName;
	}

	public String getZjCompanyName() {
		return zjCompanyName;
	}

	public void setZjCompanyName(String zjCompanyName) {
		this.zjCompanyName = zjCompanyName;
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

	public String getJdbCompanyName() {
		return jdbCompanyName;
	}

	public void setJdbCompanyName(String jdbCompanyName) {
		this.jdbCompanyName = jdbCompanyName;
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

	public int getIschangInfo() {
		return ischangInfo;
	}

	public void setIschangInfo(int ischangInfo) {
		this.ischangInfo = ischangInfo;
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

	public String getSubTime() {
		return subTime;
	}

	public void setSubTime(String subTime) {
		this.subTime = subTime;
	}

	public String getDeviceId2() {
		return deviceId2;
	}

	public void setDeviceId2(String deviceId2) {
		this.deviceId2 = deviceId2;
	}

	public String getShenheTime2() {
		return shenheTime2;
	}

	public void setShenheTime2(String shenheTime2) {
		this.shenheTime2 = shenheTime2;
	}

	public String getQstartTime() {
		return qstartTime;
	}

	public void setQstartTime(String qstartTime) {
		this.qstartTime = qstartTime;
	}

	public String getQendTime() {
		return qendTime;
	}

	public void setQendTime(String qendTime) {
		this.qendTime = qendTime;
	}

	public String getQstartTime2() {
		return qstartTime2;
	}

	public void setQstartTime2(String qstartTime2) {
		this.qstartTime2 = qstartTime2;
	}

	public String getQendTime2() {
		return qendTime2;
	}

	public void setQendTime2(String qendTime2) {
		this.qendTime2 = qendTime2;
	}

	public int getDailingFlag() {
		return dailingFlag;
	}

	public void setDailingFlag(int dailingFlag) {
		this.dailingFlag = dailingFlag;
	}

	public int getShemiFlag() {
		return shemiFlag;
	}

	public void setShemiFlag(int shemiFlag) {
		this.shemiFlag = shemiFlag;
	}

	public int getEtotal() {
		return etotal;
	}

	public void setEtotal(int etotal) {
		this.etotal = etotal;
	}

	public int getSetotal() {
		return setotal;
	}

	public void setSetotal(int setotal) {
		this.setotal = setotal;
	}

	public int getNcqetotal() {
		return ncqetotal;
	}

	public void setNcqetotal(int ncqetotal) {
		this.ncqetotal = ncqetotal;
	}

	public int getTsrtotal() {
		return tsrtotal;
	}

	public void setTsrtotal(int tsrtotal) {
		this.tsrtotal = tsrtotal;
	}

	public int getUndotsrtotal() {
		return undotsrtotal;
	}

	public void setUndotsrtotal(int undotsrtotal) {
		this.undotsrtotal = undotsrtotal;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRemarkDate() {
		return remarkDate;
	}

	public void setRemarkDate(String remarkDate) {
		this.remarkDate = remarkDate;
	}

	public String getRemarkInfo() {
		return remarkInfo;
	}

	public void setRemarkInfo(String remarkInfo) {
		this.remarkInfo = remarkInfo;
	}

	public int getSmetotal() {
		return smetotal;
	}

	public void setSmetotal(int smetotal) {
		this.smetotal = smetotal;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getYwcompanytotal() {
		return ywcompanytotal;
	}

	public void setYwcompanytotal(int ywcompanytotal) {
		this.ywcompanytotal = ywcompanytotal;
	}

	public String getArea2() {
		return area2;
	}

	public void setArea2(String area2) {
		this.area2 = area2;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getBuildingName2() {
		return buildingName2;
	}

	public void setBuildingName2(String buildingName2) {
		this.buildingName2 = buildingName2;
	}

	public String getBuilding2() {
		return building2;
	}

	public void setBuilding2(String building2) {
		this.building2 = building2;
	}

	public String getUnit2() {
		return unit2;
	}

	public void setUnit2(String unit2) {
		this.unit2 = unit2;
	}

	public String getUseNumber2() {
		return useNumber2;
	}

	public void setUseNumber2(String useNumber2) {
		this.useNumber2 = useNumber2;
	}

	public int getResouceFlag() {
		return resouceFlag;
	}

	public void setResouceFlag(int resouceFlag) {
		this.resouceFlag = resouceFlag;
	}

	public int getJyjyFlag() {
		return jyjyFlag;
	}

	public void setJyjyFlag(int jyjyFlag) {
		this.jyjyFlag = jyjyFlag;
	}

	public String getRegistCode3() {
		return registCode3;
	}

	public void setRegistCode3(String registCode3) {
		this.registCode3 = registCode3;
	}

	public String getArea3() {
		return area3;
	}

	public void setArea3(String area3) {
		this.area3 = area3;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getBuildingName3() {
		return buildingName3;
	}

	public void setBuildingName3(String buildingName3) {
		this.buildingName3 = buildingName3;
	}

	public String getBuilding3() {
		return building3;
	}

	public void setBuilding3(String building3) {
		this.building3 = building3;
	}

	public String getUnit3() {
		return unit3;
	}

	public void setUnit3(String unit3) {
		this.unit3 = unit3;
	}

	public String getUseNumber3() {
		return useNumber3;
	}

	public void setUseNumber3(String useNumber3) {
		this.useNumber3 = useNumber3;
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

	public String getRatingDate() {
		return ratingDate;
	}

	public void setRatingDate(String ratingDate) {
		this.ratingDate = ratingDate;
	}
    
	
	
	
}