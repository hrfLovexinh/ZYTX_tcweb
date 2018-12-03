package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;

/** userInfo . */
@Table(name = "TwoCodeYuanShiNewData")
public class DdYuanShiElevaltorInfo extends ActiveRecordBase{
	@Id
	private int id ;
	@Column
	private String registNumber; 		            //电梯编号
	@Column
	private String shibieCode ;		            //识别码
	@Column
	private String useNumber ;				            //单位内部编号
	@Column
	private String registCode ;			            //注册代码
	@Column
	private String registCode2;    			            //登记编号
	@Column
	private String registor ;					            //注册登记人员
	@Column
	private String registDate; 				            //注册日期*
	@Column
	private String propertyRightsUnit ;		            //产权单位
	@Column
	private String propertyRightsUnitCompanyCode ;			//产权单位代码*
	@Column
	private String propertyRightsUnitAddress 	;			//产权单位地址*
	private int wgCompanyId 	;					            //使用单位（使用单位，使用单位代码，使用单位地址，使用单位邮政编码）
	@Column
	private String wgCompanyName;							//使用单位
	@Column
	private String wgCompanyCode;							//使用单位代码
	@Column
	private String wgCompanyAddress;						//使用单位地址
	@Column
	private String wgCompanyZip;							//使用单位邮政编码
	@Column
	private String safetyManDepart ;			            //安全管理部门
	@Column
	private String safetyManPerson; 			            //安全管理人员
	@Column
	private String safetyManTel; 			            //安全管理人员联系电话
	@Column
	private String area; 						            //所属区域
	@Column
	private String townshipStreets; 					            //所在乡镇/街道 
	@Column
	private String address ;					            //地址
	@Column
	private String buildingName 	;		            //楼盘名称
	@Column
	private String building; 				            //所在栋
	@Column
	private String unit; 					            //所在单元
	@Column
	private String eleType; 					            //电梯类别
	@Column
	private String name; 						            //电梯名称
	@Column
	private String eleMode; 					            //电梯型号
	private int zzCompanyId; 					            //制造单位（制造单位，制造单位代码，制造单位资格证书名称，制造单位联系电话）
	@Column
	private String zzCompanyName; 							//制造单位
	@Column
	private String zzCompanyCode;							//制造单位代码
	@Column
	private String zzCompanyCertificateName;				//制造单位资格证书名称
	@Column
	private String zzCompanyTel;							//制造单位联系电话
	@Column
	private String manufactDate; 				            //制造日期
	@Column
	private String factoryNum; 				            //出厂编号
	private int azCompanyId; 						            //安装单位（安装单位，安装单位代码，安装资格证书编号，安装单位联系电话）
	@Column
	private String azCompanyName;								//安装单位
	@Column
	private String azCompanyCode; 								//安装单位代码
	@Column
	private String azCompanyCertificateCode;					 //安装资格证书编号
	@Column
	private String azCompanyTel;					 			//安装单位联系电话
	@Column
	private String azCompanyMan;        		            //安装单位项目负责人*
	@Column
	private String completeAcceptanceDate; 		            //竣工验收日期
	@Column
	private String acceptanceDateDepart; 		            //竣工检验机构
	@Column
	private String acceptanceReportNum ;		            //验收报告编号
	@Column
	private String useDate; 					            //投用日期
	private int ywCompanyId ;						            //维保单位（维保单位，维保单位代码，资格证书编号）
	@Column
	private String ywCompanyName;							//维保单位
	@Column
	private String ywCompanyCode;							//维保单位代码
	@Column
	private String ywCompanyCertificateCode;				//资格证书编号
	@Column
	private String ywMan; 			                    //维保人员*
	@Column
	private String ywManTel ;			                    //维保人员电话*
	@Column
	private String mContractVdate; 				            //维保合同有效期
	@Column
	private String speed ;						            //速度
	@Column
	private String eleLoad ;					            //额定载荷
	@Column
	private String eleStop; 					            //层站
	private int jyCompanyId; 					            //检验单位（检验单位，检验单位代码）
	@Column
	private String jyCompanyName;							//检验单位
	@Column
	private String jyCompanyCode;							//检验单位代码
	@Column
	private String checkCategory; 			            //检验类别
	@Column
	private String checkResult; 				            //检验结果
	@Column
	private String checkReportNum ;			            //报告编号
	@Column
	private String inspectDate; 				            //检验日期
	@Column
	private String nextInspectDate ;			            //下次检验日期
	@Column
	private String changeWay ;				            //电梯变更方式
	@Column
	private String handleProject 	;			            //电梯变更项目*
	@Column
	private String handleDate ;					            //电梯变更日期*
	@Column
	private String handleCompany 	;			            //电梯变更承办单位
	@Column
	private String handleCompanyCode; 			            //变更承办单位代码
	@Column
	private String useRegistCode;                          //使用登记证号码*
	@Column
	private int isusedFlag ;				            //是否被使用
	@Column
	private int dailingFlag;                          //停用标志
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
	public String getShibieCode() {
		return shibieCode;
	}
	public void setShibieCode(String shibieCode) {
		this.shibieCode = shibieCode;
	}
	public String getUseNumber() {
		return useNumber;
	}
	public void setUseNumber(String useNumber) {
		this.useNumber = useNumber;
	}
	public String getRegistCode() {
		return registCode;
	}
	public void setRegistCode(String registCode) {
		this.registCode = registCode;
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
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	public String getPropertyRightsUnit() {
		return propertyRightsUnit;
	}
	public void setPropertyRightsUnit(String propertyRightsUnit) {
		this.propertyRightsUnit = propertyRightsUnit;
	}
	public String getPropertyRightsUnitCompanyCode() {
		return propertyRightsUnitCompanyCode;
	}
	public void setPropertyRightsUnitCompanyCode(String propertyRightsUnitCompanyCode) {
		this.propertyRightsUnitCompanyCode = propertyRightsUnitCompanyCode;
	}
	public String getPropertyRightsUnitAddress() {
		return propertyRightsUnitAddress;
	}
	public void setPropertyRightsUnitAddress(String propertyRightsUnitAddress) {
		this.propertyRightsUnitAddress = propertyRightsUnitAddress;
	}
	public int getWgCompanyId() {
		return wgCompanyId;
	}
	public void setWgCompanyId(int wgCompanyId) {
		this.wgCompanyId = wgCompanyId;
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
	public String getSafetyManTel() {
		return safetyManTel;
	}
	public void setSafetyManTel(String safetyManTel) {
		this.safetyManTel = safetyManTel;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getTownshipStreets() {
		return townshipStreets;
	}
	public void setTownshipStreets(String townshipStreets) {
		this.townshipStreets = townshipStreets;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getEleType() {
		return eleType;
	}
	public void setEleType(String eleType) {
		this.eleType = eleType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEleMode() {
		return eleMode;
	}
	public void setEleMode(String eleMode) {
		this.eleMode = eleMode;
	}
	public int getZzCompanyId() {
		return zzCompanyId;
	}
	public void setZzCompanyId(int zzCompanyId) {
		this.zzCompanyId = zzCompanyId;
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
	public int getAzCompanyId() {
		return azCompanyId;
	}
	public void setAzCompanyId(int azCompanyId) {
		this.azCompanyId = azCompanyId;
	}
	public String getAzCompanyMan() {
		return azCompanyMan;
	}
	public void setAzCompanyMan(String azCompanyMan) {
		this.azCompanyMan = azCompanyMan;
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
	public String getUseDate() {
		return useDate;
	}
	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}
	public int getYwCompanyId() {
		return ywCompanyId;
	}
	public void setYwCompanyId(int ywCompanyId) {
		this.ywCompanyId = ywCompanyId;
	}
	public String getYwMan() {
		return ywMan;
	}
	public void setYwMan(String ywMan) {
		this.ywMan = ywMan;
	}
	public String getYwManTel() {
		return ywManTel;
	}
	public void setYwManTel(String ywManTel) {
		this.ywManTel = ywManTel;
	}
	public String getmContractVdate() {
		return mContractVdate;
	}
	public void setmContractVdate(String mContractVdate) {
		this.mContractVdate = mContractVdate;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getEleLoad() {
		return eleLoad;
	}
	public void setEleLoad(String eleLoad) {
		this.eleLoad = eleLoad;
	}
	public String getEleStop() {
		return eleStop;
	}
	public void setEleStop(String eleStop) {
		this.eleStop = eleStop;
	}
	public int getJyCompanyId() {
		return jyCompanyId;
	}
	public void setJyCompanyId(int jyCompanyId) {
		this.jyCompanyId = jyCompanyId;
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
	public String getNextInspectDate() {
		return nextInspectDate;
	}
	public void setNextInspectDate(String nextInspectDate) {
		this.nextInspectDate = nextInspectDate;
	}
	public String getChangeWay() {
		return changeWay;
	}
	public void setChangeWay(String changeWay) {
		this.changeWay = changeWay;
	}
	public String getHandleProject() {
		return handleProject;
	}
	public void setHandleProject(String handleProject) {
		this.handleProject = handleProject;
	}
	public String getHandleDate() {
		return handleDate;
	}
	public void setHandleDate(String handleDate) {
		this.handleDate = handleDate;
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
	public String getUseRegistCode() {
		return useRegistCode;
	}
	public void setUseRegistCode(String useRegistCode) {
		this.useRegistCode = useRegistCode;
	}
	public int getIsusedFlag() {
		return isusedFlag;
	}
	public void setIsusedFlag(int isusedFlag) {
		this.isusedFlag = isusedFlag;
	}
	public String getWgCompanyName() {
		return wgCompanyName;
	}
	public void setWgCompanyName(String wgCompanyName) {
		this.wgCompanyName = wgCompanyName;
	}
	public String getWgCompanyCode() {
		return wgCompanyCode;
	}
	public void setWgCompanyCode(String wgCompanyCode) {
		this.wgCompanyCode = wgCompanyCode;
	}
	public String getWgCompanyAddress() {
		return wgCompanyAddress;
	}
	public void setWgCompanyAddress(String wgCompanyAddress) {
		this.wgCompanyAddress = wgCompanyAddress;
	}
	public String getWgCompanyZip() {
		return wgCompanyZip;
	}
	public void setWgCompanyZip(String wgCompanyZip) {
		this.wgCompanyZip = wgCompanyZip;
	}
	public String getZzCompanyName() {
		return zzCompanyName;
	}
	public void setZzCompanyName(String zzCompanyName) {
		this.zzCompanyName = zzCompanyName;
	}
	public String getZzCompanyCode() {
		return zzCompanyCode;
	}
	public void setZzCompanyCode(String zzCompanyCode) {
		this.zzCompanyCode = zzCompanyCode;
	}
	public String getZzCompanyCertificateName() {
		return zzCompanyCertificateName;
	}
	public void setZzCompanyCertificateName(String zzCompanyCertificateName) {
		this.zzCompanyCertificateName = zzCompanyCertificateName;
	}
	public String getZzCompanyTel() {
		return zzCompanyTel;
	}
	public void setZzCompanyTel(String zzCompanyTel) {
		this.zzCompanyTel = zzCompanyTel;
	}
	public String getAzCompanyName() {
		return azCompanyName;
	}
	public void setAzCompanyName(String azCompanyName) {
		this.azCompanyName = azCompanyName;
	}
	public String getAzCompanyCode() {
		return azCompanyCode;
	}
	public void setAzCompanyCode(String azCompanyCode) {
		this.azCompanyCode = azCompanyCode;
	}
	public String getAzCompanyCertificateCode() {
		return azCompanyCertificateCode;
	}
	public void setAzCompanyCertificateCode(String azCompanyCertificateCode) {
		this.azCompanyCertificateCode = azCompanyCertificateCode;
	}
	public String getAzCompanyTel() {
		return azCompanyTel;
	}
	public void setAzCompanyTel(String azCompanyTel) {
		this.azCompanyTel = azCompanyTel;
	}
	public String getYwCompanyName() {
		return ywCompanyName;
	}
	public void setYwCompanyName(String ywCompanyName) {
		this.ywCompanyName = ywCompanyName;
	}
	public String getYwCompanyCode() {
		return ywCompanyCode;
	}
	public void setYwCompanyCode(String ywCompanyCode) {
		this.ywCompanyCode = ywCompanyCode;
	}
	public String getYwCompanyCertificateCode() {
		return ywCompanyCertificateCode;
	}
	public void setYwCompanyCertificateCode(String ywCompanyCertificateCode) {
		this.ywCompanyCertificateCode = ywCompanyCertificateCode;
	}
	public String getJyCompanyName() {
		return jyCompanyName;
	}
	public void setJyCompanyName(String jyCompanyName) {
		this.jyCompanyName = jyCompanyName;
	}
	public String getJyCompanyCode() {
		return jyCompanyCode;
	}
	public void setJyCompanyCode(String jyCompanyCode) {
		this.jyCompanyCode = jyCompanyCode;
	}
	public int getDailingFlag() {
		return dailingFlag;
	}
	public void setDailingFlag(int dailingFlag) {
		this.dailingFlag = dailingFlag;
	}
	
}
