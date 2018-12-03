package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;
import com.zytx.converters.DateConverter2;

@Table(name = "TwoCodeRemark")
public class RemarkInfo extends ActiveRecordBase{
	@Id
	private int id;
	@Column
	private String twoCodeId;
	@Column
	private String remarkDate;
	@Column
	private String remarkLevel;
	@Column
	private String remarkInfo;
	@Column
	private String registNumber;
	@Column
	private int is_Process;
	@Column
	private String process_time;
	@Column
	private int process_userid;
	@Column
	private String process_remark;
	@Column
	private String address;
	@Column
	private String userName;
	@Column
	private String companyName;
	@Column 
	private String  warnTelephone;
	
	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTwoCodeId() {
		return twoCodeId;
	}
	public void setTwoCodeId(String twoCodeId) {
		this.twoCodeId = twoCodeId;
	}
	public String getRemarkDate() {
		return remarkDate;
	}
	public void setRemarkDate(String remarkDate) {
		this.remarkDate = remarkDate;
	}
	public String getRemarkLevel() {
		return remarkLevel;
	}
	public void setRemarkLevel(String remarkLevel) {
		this.remarkLevel = remarkLevel;
	}
	public String getRemarkInfo() {
		return remarkInfo;
	}
	public void setRemarkInfo(String remarkInfo) {
		this.remarkInfo = remarkInfo;
	}
	public String getRegistNumber() {
		return registNumber;
	}
	public void setRegistNumber(String registNumber) {
		this.registNumber = registNumber;
	}
	public int getIs_Process() {
		return is_Process;
	}
	public void setIs_Process(int isProcess) {
		is_Process = isProcess;
	}
	
	public String getProcess_time() {
		return process_time;
	}
	public void setProcess_time(String processTime) {
		process_time = processTime;
	}
	public int getProcess_userid() {
		return process_userid;
	}
	public void setProcess_userid(int processUserid) {
		process_userid = processUserid;
	}
	public String getProcess_remark() {
		return process_remark;
	}
	public void setProcess_remark(String processRemark) {
		process_remark = processRemark;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getWarnTelephone() {
		return warnTelephone;
	}
	public void setWarnTelephone(String warnTelephone) {
		this.warnTelephone = warnTelephone;
	}
	
	
}
