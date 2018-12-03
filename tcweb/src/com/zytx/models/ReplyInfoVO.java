package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;
import com.zytx.converters.DateConverter2;

@Table(name = "TwoCodeReplyTab")
public class ReplyInfoVO extends ActiveRecordBase{
	@Id
	private int id;
	@Column
	private int rId;
	@Column
	private String registNumber;
	@Column
	private String replyinfo;
	@Column
	private String replyTime;
	@Column
	private int replyUserid;
	@Column
	private String userName;
	
	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getrId() {
		return rId;
	}

	public void setrId(int rId) {
		this.rId = rId;
	}

	public String getRegistNumber() {
		return registNumber;
	}

	public void setRegistNumber(String registNumber) {
		this.registNumber = registNumber;
	}

	public String getReplyinfo() {
		return replyinfo;
	}

	public void setReplyinfo(String replyinfo) {
		this.replyinfo = replyinfo;
	}

	public String getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	public int getReplyUserid() {
		return replyUserid;
	}

	public void setReplyUserid(int replyUserid) {
		this.replyUserid = replyUserid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	
	
}
