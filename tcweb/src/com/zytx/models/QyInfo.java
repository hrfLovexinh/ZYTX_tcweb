package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;

@Table(name = "QYINFO")
public class QyInfo extends ActiveRecordBase{
	@Id
	private int data_id;
	@Column
	private String  qy_id;
	@Column
	private String  qy_name;
	@Column
	private String  ssqy_id;
	@Column
	private String  creator;
	@Column
	private java.sql.Date  create_time; 
	@Column
	private String  person;
	@Column
	private String  phone;
	@Column
	private String  addr;
	@Column
	private String  zip;
	@Column
	private String  email;
	@Column
	private String  memo;
	public int getData_id() {
		return data_id;
	}
	public void setData_id(int dataId) {
		data_id = dataId;
	}
	public String getQy_id() {
		return qy_id;
	}
	public void setQy_id(String qyId) {
		qy_id = qyId;
	}
	public String getQy_name() {
		return qy_name;
	}
	public void setQy_name(String qyName) {
		qy_name = qyName;
	}
	public String getSsqy_id() {
		return ssqy_id;
	}
	public void setSsqy_id(String ssqyId) {
		ssqy_id = ssqyId;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public java.sql.Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(java.sql.Date createTime) {
		create_time = createTime;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
	

}
