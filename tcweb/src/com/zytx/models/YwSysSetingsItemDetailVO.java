package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;

@Table(name = "TwoCodeYwQuaCredRating")
public class YwSysSetingsItemDetailVO extends ActiveRecordBase{
	
	@Id
	private int id;
	@Column
    private  String itemName;
	@Column
    private  String itemContent;
	@Column
    private  String itembz;
	@Column
    private  String ratingCompanyName;
	@Column
    private  String  ratingUserName;
	@Column
    private  String detailratingDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemContent() {
		return itemContent;
	}
	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}
	public String getItembz() {
		return itembz;
	}
	public void setItembz(String itembz) {
		this.itembz = itembz;
	}
	public String getRatingCompanyName() {
		return ratingCompanyName;
	}
	public void setRatingCompanyName(String ratingCompanyName) {
		this.ratingCompanyName = ratingCompanyName;
	}
	public String getRatingUserName() {
		return ratingUserName;
	}
	public void setRatingUserName(String ratingUserName) {
		this.ratingUserName = ratingUserName;
	}
	public String getDetailratingDate() {
		return detailratingDate;
	}
	public void setDetailratingDate(String detailratingDate) {
		this.detailratingDate = detailratingDate;
	}
	
	

}
