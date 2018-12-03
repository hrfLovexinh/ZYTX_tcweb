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
import com.zytx.converters.DateConverter;
import com.zytx.converters.TimestampConverter;

/** userInfo . */
@Table(name = "DdTwoCodeElevatorYwCompanyInfo")
public class DdTwoCodeElevatorYwCompanyInfo extends ActiveRecordBase {

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
	private String updateTime;
	@Column
	private int ischangInfo;
	@Column
	private String subTime;
	@Column
	private int ruKuValid;
	@Column
	private String userName;
	@Column
	private java.sql.Date arrangeTime; //任务安排时间
	@Column
	private java.sql.Date pasteTime;  //粘贴时间
	@Column
	private String pasteTime2; //粘贴时间
	@Column
	private int recordSate;
	@Column
	private String subTime2;  //安装的时间查看图片时拼路径用
	
	@Column
	private int pastePersonID;
	@Column
	private String pastePersonName;
	@Column
	private String arrangeTime2;
	@Column
	private int arrageType; 
	@Column
	private String pasteNote;
	@Column
	private String arrangePersonName;
	@Column
	private String rukubeizhu;
	@Column
	private String mobileUploadbeizhu;
	
	@Column
	private int subPersonID;
	@Column
	private String subPersonName;  
	@Column
	private int shenhe;
	@Column
	private String isnormalFlag;
	@Column
	private String picregistNumber;
	@Column 
	private String deviceId2;
	@Column
    private int jyjyFlag;
	@Column
    private String shibieCode;
    @Column
    private String neleType;
	
	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }
  
	
	
	public int getJyjyFlag() {
		return jyjyFlag;
	}

	public void setJyjyFlag(int jyjyFlag) {
		this.jyjyFlag = jyjyFlag;
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

	public String getSubTime() {
		return subTime;
	}

	public void setSubTime(String subTime) {
		this.subTime = subTime;
	}

	public int getRuKuValid() {
		return ruKuValid;
	}

	public void setRuKuValid(int ruKuValid) {
		this.ruKuValid = ruKuValid;
	}

	public java.sql.Date getPasteTime() {
		return pasteTime;
	}

	public void setPasteTime(java.sql.Date pasteTime) {
		this.pasteTime = pasteTime;
	}

	public String getSubTime2() {
		return subTime2;
	}

	public void setSubTime2(String subTime2) {
		this.subTime2 = subTime2;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public java.sql.Date getArrangeTime() {
		return arrangeTime;
	}

	public void setArrangeTime(java.sql.Date arrangeTime) {
		this.arrangeTime = arrangeTime;
	}

	public int getRecordSate() {
		return recordSate;
	}

	public void setRecordSate(int recordSate) {
		this.recordSate = recordSate;
	}

	public int getPastePersonID() {
		return pastePersonID;
	}

	public void setPastePersonID(int pastePersonID) {
		this.pastePersonID = pastePersonID;
	}

	public String getPastePersonName() {
		return pastePersonName;
	}

	public void setPastePersonName(String pastePersonName) {
		this.pastePersonName = pastePersonName;
	}

	public String getArrangeTime2() {
		return arrangeTime2;
	}

	public void setArrangeTime2(String arrangeTime2) {
		this.arrangeTime2 = arrangeTime2;
	}

	public int getArrageType() {
		return arrageType;
	}

	public void setArrageType(int arrageType) {
		this.arrageType = arrageType;
	}

	public String getPasteNote() {
		return pasteNote;
	}

	public void setPasteNote(String pasteNote) {
		this.pasteNote = pasteNote;
	}

	public String getArrangePersonName() {
		return arrangePersonName;
	}

	public void setArrangePersonName(String arrangePersonName) {
		this.arrangePersonName = arrangePersonName;
	}

	public String getRukubeizhu() {
		return rukubeizhu;
	}

	public void setRukubeizhu(String rukubeizhu) {
		this.rukubeizhu = rukubeizhu;
	}

	public String getMobileUploadbeizhu() {
		return mobileUploadbeizhu;
	}

	public void setMobileUploadbeizhu(String mobileUploadbeizhu) {
		this.mobileUploadbeizhu = mobileUploadbeizhu;
	}

	public int getSubPersonID() {
		return subPersonID;
	}

	public void setSubPersonID(int subPersonID) {
		this.subPersonID = subPersonID;
	}

	public String getSubPersonName() {
		return subPersonName;
	}

	public void setSubPersonName(String subPersonName) {
		this.subPersonName = subPersonName;
	}

	public String getPasteTime2() {
		return pasteTime2;
	}

	public void setPasteTime2(String pasteTime2) {
		this.pasteTime2 = pasteTime2;
	}

	public int getShenhe() {
		return shenhe;
	}

	public void setShenhe(int shenhe) {
		this.shenhe = shenhe;
	}

	public String getIsnormalFlag() {
		return isnormalFlag;
	}

	public void setIsnormalFlag(String isnormalFlag) {
		this.isnormalFlag = isnormalFlag;
	}

	public String getPicregistNumber() {
		return picregistNumber;
	}

	public void setPicregistNumber(String picregistNumber) {
		this.picregistNumber = picregistNumber;
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
    
    
	
	
    
	
}