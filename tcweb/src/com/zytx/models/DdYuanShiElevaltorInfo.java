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
	private String registNumber; 		            //���ݱ��
	@Column
	private String shibieCode ;		            //ʶ����
	@Column
	private String useNumber ;				            //��λ�ڲ����
	@Column
	private String registCode ;			            //ע�����
	@Column
	private String registCode2;    			            //�ǼǱ��
	@Column
	private String registor ;					            //ע��Ǽ���Ա
	@Column
	private String registDate; 				            //ע������*
	@Column
	private String propertyRightsUnit ;		            //��Ȩ��λ
	@Column
	private String propertyRightsUnitCompanyCode ;			//��Ȩ��λ����*
	@Column
	private String propertyRightsUnitAddress 	;			//��Ȩ��λ��ַ*
	private int wgCompanyId 	;					            //ʹ�õ�λ��ʹ�õ�λ��ʹ�õ�λ���룬ʹ�õ�λ��ַ��ʹ�õ�λ�������룩
	@Column
	private String wgCompanyName;							//ʹ�õ�λ
	@Column
	private String wgCompanyCode;							//ʹ�õ�λ����
	@Column
	private String wgCompanyAddress;						//ʹ�õ�λ��ַ
	@Column
	private String wgCompanyZip;							//ʹ�õ�λ��������
	@Column
	private String safetyManDepart ;			            //��ȫ������
	@Column
	private String safetyManPerson; 			            //��ȫ������Ա
	@Column
	private String safetyManTel; 			            //��ȫ������Ա��ϵ�绰
	@Column
	private String area; 						            //��������
	@Column
	private String townshipStreets; 					            //��������/�ֵ� 
	@Column
	private String address ;					            //��ַ
	@Column
	private String buildingName 	;		            //¥������
	@Column
	private String building; 				            //���ڶ�
	@Column
	private String unit; 					            //���ڵ�Ԫ
	@Column
	private String eleType; 					            //�������
	@Column
	private String name; 						            //��������
	@Column
	private String eleMode; 					            //�����ͺ�
	private int zzCompanyId; 					            //���쵥λ�����쵥λ�����쵥λ���룬���쵥λ�ʸ�֤�����ƣ����쵥λ��ϵ�绰��
	@Column
	private String zzCompanyName; 							//���쵥λ
	@Column
	private String zzCompanyCode;							//���쵥λ����
	@Column
	private String zzCompanyCertificateName;				//���쵥λ�ʸ�֤������
	@Column
	private String zzCompanyTel;							//���쵥λ��ϵ�绰
	@Column
	private String manufactDate; 				            //��������
	@Column
	private String factoryNum; 				            //�������
	private int azCompanyId; 						            //��װ��λ����װ��λ����װ��λ���룬��װ�ʸ�֤���ţ���װ��λ��ϵ�绰��
	@Column
	private String azCompanyName;								//��װ��λ
	@Column
	private String azCompanyCode; 								//��װ��λ����
	@Column
	private String azCompanyCertificateCode;					 //��װ�ʸ�֤����
	@Column
	private String azCompanyTel;					 			//��װ��λ��ϵ�绰
	@Column
	private String azCompanyMan;        		            //��װ��λ��Ŀ������*
	@Column
	private String completeAcceptanceDate; 		            //������������
	@Column
	private String acceptanceDateDepart; 		            //�����������
	@Column
	private String acceptanceReportNum ;		            //���ձ�����
	@Column
	private String useDate; 					            //Ͷ������
	private int ywCompanyId ;						            //ά����λ��ά����λ��ά����λ���룬�ʸ�֤���ţ�
	@Column
	private String ywCompanyName;							//ά����λ
	@Column
	private String ywCompanyCode;							//ά����λ����
	@Column
	private String ywCompanyCertificateCode;				//�ʸ�֤����
	@Column
	private String ywMan; 			                    //ά����Ա*
	@Column
	private String ywManTel ;			                    //ά����Ա�绰*
	@Column
	private String mContractVdate; 				            //ά����ͬ��Ч��
	@Column
	private String speed ;						            //�ٶ�
	@Column
	private String eleLoad ;					            //��غ�
	@Column
	private String eleStop; 					            //��վ
	private int jyCompanyId; 					            //���鵥λ�����鵥λ�����鵥λ���룩
	@Column
	private String jyCompanyName;							//���鵥λ
	@Column
	private String jyCompanyCode;							//���鵥λ����
	@Column
	private String checkCategory; 			            //�������
	@Column
	private String checkResult; 				            //������
	@Column
	private String checkReportNum ;			            //������
	@Column
	private String inspectDate; 				            //��������
	@Column
	private String nextInspectDate ;			            //�´μ�������
	@Column
	private String changeWay ;				            //���ݱ����ʽ
	@Column
	private String handleProject 	;			            //���ݱ����Ŀ*
	@Column
	private String handleDate ;					            //���ݱ������*
	@Column
	private String handleCompany 	;			            //���ݱ���а쵥λ
	@Column
	private String handleCompanyCode; 			            //����а쵥λ����
	@Column
	private String useRegistCode;                          //ʹ�õǼ�֤����*
	@Column
	private int isusedFlag ;				            //�Ƿ�ʹ��
	@Column
	private int dailingFlag;                          //ͣ�ñ�־
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
