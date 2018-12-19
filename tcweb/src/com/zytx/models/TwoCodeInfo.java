package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;

@Table(name = "TwoCodeInfo")
public class TwoCodeInfo extends ActiveRecordBase{

	@Id
	private int id;
	@Column
	private String registNumber;
	@Column
	private int infoState;  //0:入库，1领用，2粘贴,3核实
	@Column
	private String ptime;  //入库
	@Column 
	private String rtime;  //领用
	@Column
	private String ltime;   //粘贴
	@Column
	private String ztime;   //核实
	@Column
	private int ywCompanyId;     //领用人单位
	@Column
	private String userName;    //领用人
	@Column
	private String loginName;  //领用人账号
	@Column
	private String puserName;
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
	public int getInfoState() {
		return infoState;
	}
	public void setInfoState(int infoState) {
		this.infoState = infoState;
	}
	public String getPtime() {
		return ptime;
	}
	public void setPtime(String ptime) {
		this.ptime = ptime;
	}
	public String getRtime() {
		return rtime;
	}
	public void setRtime(String rtime) {
		this.rtime = rtime;
	}
	public String getLtime() {
		return ltime;
	}
	public void setLtime(String ltime) {
		this.ltime = ltime;
	}
	public String getZtime() {
		return ztime;
	}
	public void setZtime(String ztime) {
		this.ztime = ztime;
	}
	public int getYwCompanyId() {
		return ywCompanyId;
	}
	public void setYwCompanyId(int ywCompanyId) {
		this.ywCompanyId = ywCompanyId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPuserName() {
		return puserName;
	}
	public void setPuserName(String puserName) {
		this.puserName = puserName;
	}
	
	
}
