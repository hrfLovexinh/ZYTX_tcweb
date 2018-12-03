package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;

@Table(name = "TwoCode96333Info")
public class Twocode96333 extends ActiveRecordBase{
	@Id
	private int id;
	@Column
	private int puserId;
	@Column
	private String puserName;
	@Column
	private String ptime;  //Èë¿â
	@Column
	private int pcount;
	@Column
	private String pbeizhu;
   
	

	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPuserId() {
		return puserId;
	}
	public void setPuserId(int puserId) {
		this.puserId = puserId;
	}
	public String getPuserName() {
		return puserName;
	}
	public void setPuserName(String puserName) {
		this.puserName = puserName;
	}
	public String getPtime() {
		return ptime;
	}
	public void setPtime(String ptime) {
		this.ptime = ptime;
	}
	public int getPcount() {
		return pcount;
	}
	public void setPcount(int pcount) {
		this.pcount = pcount;
	}
	public String getPbeizhu() {
		return pbeizhu;
	}
	public void setPbeizhu(String pbeizhu) {
		this.pbeizhu = pbeizhu;
	}
	
	
	
	
		
}
