package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;

@Table(name = "TwoCodeYwlog")
public class YwlogVO extends ActiveRecordBase{
	@Id
	private int id;
	@Column
	private String userName;
	@Column
	private String context;
	@Column
	private String cmd;
	@Column
	private String subTime;
	
	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getSubTime() {
		return subTime;
	}

	public void setSubTime(String subTime) {
		this.subTime = subTime;
	}
	
	
}
