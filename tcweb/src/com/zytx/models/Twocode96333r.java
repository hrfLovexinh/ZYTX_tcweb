package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;

@Table(name = "TwoCode96333rInfo")
public class Twocode96333r extends ActiveRecordBase{
	@Id
	private int id;
	@Column
	private int rcompanyId;
	@Column
	private int ruserId;
	@Column
	private String ruserName;
	@Column
	private String rtime;  //Èë¿â
	@Column
	private int rcount;
	@Column
	private String rbeizhu;
	@Column
	private int rtype;
   
	

	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public int getRuserId() {
		return ruserId;
	}
	public void setRuserId(int ruserId) {
		this.ruserId = ruserId;
	}
	public int getRcompanyId() {
		return rcompanyId;
	}
	public void setRcompanyId(int rcompanyId) {
		this.rcompanyId = rcompanyId;
	}
	public String getRuserName() {
		return ruserName;
	}
	public void setRuserName(String ruserName) {
		this.ruserName = ruserName;
	}
	public String getRtime() {
		return rtime;
	}
	public void setRtime(String rtime) {
		this.rtime = rtime;
	}
	public int getRcount() {
		return rcount;
	}
	public void setRcount(int rcount) {
		this.rcount = rcount;
	}
	public String getRbeizhu() {
		return rbeizhu;
	}
	public void setRbeizhu(String rbeizhu) {
		this.rbeizhu = rbeizhu;
	}
	public int getRtype() {
		return rtype;
	}
	public void setRtype(int rtype) {
		this.rtype = rtype;
	}
	
	
	
		
}
