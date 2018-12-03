package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;

@Table(name = "hisYwManagerInfo")
public class HisYwInfoVO extends ActiveRecordBase{
	@Id
	private int id;
	@Column
	private String registNumber;
	@Column
	private int userId;
	@Column
	private String ywKind;
	@Column
	private String startTime;
	@Column
	private String endTime;
	@Column
	private String sPosition;
	@Column
	private String ePosition;
	@Column
	private String subTime;
	@Column
	private String address;
	@Column
	private String maintainTypecode;
	@Column
	private String dateSpan;
	@Column
	private String userName;
	@Column
	private String companyName;
	@Column
	private String wgcompanyName;
	@Column
	private int companyId;
	@Column
	private String remark;
	@Column
	private String ywstatus;
	@Column
	private int picNum;
	@Column
	private String contactPhone;
	
	
	//2014-08-01  为了利用地理位置进行审核
	@Column
	private String map_X;
	@Column
	private String map_Y;
	@Column
	private String map_X0;
	@Column
	private String map_Y0;
	@Column
	private String map_X1;
	@Column
	private String map_Y1;
	@Column
	private String map_X2;
	@Column
	private String map_Y2;
	@Column 
	private int flexStartx;
	@Column
	private int flexStarty;
	@Column
	private int flexEndx;
	@Column
	private int flexEndy;
	
	private String qstartTime;
	private String qendTime;
	@Column
	private String area;
	private String beginTime;
	@Column
	private String buildingName;
	@Column
	private int ywResult;
	
	@Column
	private java.lang.String loginName;
	@Column
	private int townshipStreets;
	@Column
	private String jdbCompanyName;
	
	
	private String ncqtype;
	
    private int companyId2;   //运维记录中物管单位查询
    
    @Column
    private int etotal;
    @Column
    private int eywtotal;
    @Column
    private int ncqetotal;
    @Column
    private int uncqetotal;
    @Column
    private int utotal;
    @Column
    private int nutotal;
    @Column
    private int uywetotal;  //运维员工在某个公司运维的总电梯数  
    @Column
    private int uywcqetotal; //运维员工在某个公司运维的超期电梯数 
    @Column  
    private int ywcompanyId;
    @Column 
	private String qualificationvalidate;  
	
	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRegistNumber() {
		return registNumber;
	}
	public void setRegistNumber(String registNumber) {
		this.registNumber = registNumber;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getYwKind() {
		return ywKind;
	}
	public void setYwKind(String ywKind) {
		this.ywKind = ywKind;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getsPosition() {
		return sPosition;
	}
	public void setsPosition(String sPosition) {
		this.sPosition = sPosition;
	}
	public String getePosition() {
		return ePosition;
	}
	public void setePosition(String ePosition) {
		this.ePosition = ePosition;
	}
	public String getSubTime() {
		return subTime;
	}
	public void setSubTime(String subTime) {
		this.subTime = subTime;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMaintainTypecode() {
		return maintainTypecode;
	}
	public void setMaintainTypecode(String maintainTypecode) {
		this.maintainTypecode = maintainTypecode;
	}
	public String getDateSpan() {
		return dateSpan;
	}
	public void setDateSpan(String dateSpan) {
		this.dateSpan = dateSpan;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getYwstatus() {
		return ywstatus;
	}
	public void setYwstatus(String ywstatus) {
		this.ywstatus = ywstatus;
	}
	public int getPicNum() {
		return picNum;
	}
	public void setPicNum(int picNum) {
		this.picNum = picNum;
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
	public String getMap_X0() {
		return map_X0;
	}
	public void setMap_X0(String mapX0) {
		map_X0 = mapX0;
	}
	public String getMap_Y0() {
		return map_Y0;
	}
	public void setMap_Y0(String mapY0) {
		map_Y0 = mapY0;
	}
	public String getMap_X1() {
		return map_X1;
	}
	public void setMap_X1(String mapX1) {
		map_X1 = mapX1;
	}
	public String getMap_Y1() {
		return map_Y1;
	}
	public void setMap_Y1(String mapY1) {
		map_Y1 = mapY1;
	}
	public String getMap_X2() {
		return map_X2;
	}
	public void setMap_X2(String mapX2) {
		map_X2 = mapX2;
	}
	public String getMap_Y2() {
		return map_Y2;
	}
	public void setMap_Y2(String mapY2) {
		map_Y2 = mapY2;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
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
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getYwResult() {
		return ywResult;
	}
	public void setYwResult(int ywResult) {
		this.ywResult = ywResult;
	}
	public java.lang.String getLoginName() {
		return loginName;
	}
	public void setLoginName(java.lang.String loginName) {
		this.loginName = loginName;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public int getFlexStartx() {
		return flexStartx;
	}
	public void setFlexStartx(int flexStartx) {
		this.flexStartx = flexStartx;
	}
	public int getFlexStarty() {
		return flexStarty;
	}
	public void setFlexStarty(int flexStarty) {
		this.flexStarty = flexStarty;
	}
	public int getFlexEndx() {
		return flexEndx;
	}
	public void setFlexEndx(int flexEndx) {
		this.flexEndx = flexEndx;
	}
	public int getFlexEndy() {
		return flexEndy;
	}
	public void setFlexEndy(int flexEndy) {
		this.flexEndy = flexEndy;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getNcqtype() {
		return ncqtype;
	}
	public void setNcqtype(String ncqtype) {
		this.ncqtype = ncqtype;
	}
	public int getCompanyId2() {
		return companyId2;
	}
	public void setCompanyId2(int companyId2) {
		this.companyId2 = companyId2;
	}
	public String getWgcompanyName() {
		return wgcompanyName;
	}
	public void setWgcompanyName(String wgcompanyName) {
		this.wgcompanyName = wgcompanyName;
	}
	public int getTownshipStreets() {
		return townshipStreets;
	}
	public void setTownshipStreets(int townshipStreets) {
		this.townshipStreets = townshipStreets;
	}
	public String getJdbCompanyName() {
		return jdbCompanyName;
	}
	public void setJdbCompanyName(String jdbCompanyName) {
		this.jdbCompanyName = jdbCompanyName;
	}
	public int getEtotal() {
		return etotal;
	}
	public void setEtotal(int etotal) {
		this.etotal = etotal;
	}
	public int getNcqetotal() {
		return ncqetotal;
	}
	public void setNcqetotal(int ncqetotal) {
		this.ncqetotal = ncqetotal;
	}
	public int getUtotal() {
		return utotal;
	}
	public void setUtotal(int utotal) {
		this.utotal = utotal;
	}
	public int getNutotal() {
		return nutotal;
	}
	public void setNutotal(int nutotal) {
		this.nutotal = nutotal;
	}
	public int getYwcompanyId() {
		return ywcompanyId;
	}
	public void setYwcompanyId(int ywcompanyId) {
		this.ywcompanyId = ywcompanyId;
	}
	public String getQualificationvalidate() {
		return qualificationvalidate;
	}
	public void setQualificationvalidate(String qualificationvalidate) {
		this.qualificationvalidate = qualificationvalidate;
	}
	public int getUncqetotal() {
		return uncqetotal;
	}
	public void setUncqetotal(int uncqetotal) {
		this.uncqetotal = uncqetotal;
	}
	public int getUywetotal() {
		return uywetotal;
	}
	public void setUywetotal(int uywetotal) {
		this.uywetotal = uywetotal;
	}
	public int getUywcqetotal() {
		return uywcqetotal;
	}
	public void setUywcqetotal(int uywcqetotal) {
		this.uywcqetotal = uywcqetotal;
	}
	public int getEywtotal() {
		return eywtotal;
	}
	public void setEywtotal(int eywtotal) {
		this.eywtotal = eywtotal;
	}
	
	
	
}
