package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;

@Table(name = "YwManagerInfo")
public class YwRankingInfoVO extends ActiveRecordBase{
	@Column
	private int ywCompanyId;
	@Column
	private String ywCompanyName;    //维保公司名
	@Column
	private int ywManagerEleCount;  //管理电梯数
	@Column
	private int ywEleCount;   //扫码维保电梯数
	@Column
	private int ywEleExpireCount;   //超期维保电梯数
	@Column
	private float tScanMaintainPercent;   //扫码维保率
	@Column
	private float tScanUnexpireMaintainPercent;   //按期扫码维保率
	@Column
	private float tComplaintRate;    //投诉率
	@Column
	private float t1Percent; //信息完整率
	@Column
	private float t2Percent;   //合同有效率
	@Column
	private float tSave30Percent;   //救援到达超30分钟
	@Column
	private float t1LevelNotPercent;  //未实施一级救援
	@Column
	private float t2LevelPercent;  //实施二级救援
	@Column
	private float tPunish;   //受到行政处罚
	@Column
	private float tScore;  //综合得分
	@Column
	private float tSort;  //tSort
	
	private String ratingDate;
	
	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }


	public int getYwCompanyId() {
		return ywCompanyId;
	}


	public void setYwCompanyId(int ywCompanyId) {
		this.ywCompanyId = ywCompanyId;
	}


	public String getYwCompanyName() {
		return ywCompanyName;
	}


	public void setYwCompanyName(String ywCompanyName) {
		this.ywCompanyName = ywCompanyName;
	}


	public int getYwManagerEleCount() {
		return ywManagerEleCount;
	}


	public void setYwManagerEleCount(int ywManagerEleCount) {
		this.ywManagerEleCount = ywManagerEleCount;
	}


	public int getYwEleCount() {
		return ywEleCount;
	}


	public void setYwEleCount(int ywEleCount) {
		this.ywEleCount = ywEleCount;
	}


	public int getYwEleExpireCount() {
		return ywEleExpireCount;
	}


	public void setYwEleExpireCount(int ywEleExpireCount) {
		this.ywEleExpireCount = ywEleExpireCount;
	}


	public float gettScanMaintainPercent() {
		return tScanMaintainPercent;
	}


	public void settScanMaintainPercent(float tScanMaintainPercent) {
		this.tScanMaintainPercent = tScanMaintainPercent;
	}


	public float gettScanUnexpireMaintainPercent() {
		return tScanUnexpireMaintainPercent;
	}


	public void settScanUnexpireMaintainPercent(float tScanUnexpireMaintainPercent) {
		this.tScanUnexpireMaintainPercent = tScanUnexpireMaintainPercent;
	}


	public float gettComplaintRate() {
		return tComplaintRate;
	}


	public void settComplaintRate(float tComplaintRate) {
		this.tComplaintRate = tComplaintRate;
	}


	public float getT1Percent() {
		return t1Percent;
	}


	public void setT1Percent(float t1Percent) {
		this.t1Percent = t1Percent;
	}


	public float getT2Percent() {
		return t2Percent;
	}


	public void setT2Percent(float t2Percent) {
		this.t2Percent = t2Percent;
	}


	public float gettSave30Percent() {
		return tSave30Percent;
	}


	public void settSave30Percent(float tSave30Percent) {
		this.tSave30Percent = tSave30Percent;
	}


	public float getT1LevelNotPercent() {
		return t1LevelNotPercent;
	}


	public void setT1LevelNotPercent(float t1LevelNotPercent) {
		this.t1LevelNotPercent = t1LevelNotPercent;
	}


	public float getT2LevelPercent() {
		return t2LevelPercent;
	}


	public void setT2LevelPercent(float t2LevelPercent) {
		this.t2LevelPercent = t2LevelPercent;
	}


	public float gettPunish() {
		return tPunish;
	}


	public void settPunish(float tPunish) {
		this.tPunish = tPunish;
	}


	public float gettScore() {
		return tScore;
	}


	public void settScore(float tScore) {
		this.tScore = tScore;
	}


	public float gettSort() {
		return tSort;
	}


	public void settSort(float tSort) {
		this.tSort = tSort;
	}


	public String getRatingDate() {
		return ratingDate;
	}


	public void setRatingDate(String ratingDate) {
		this.ratingDate = ratingDate;
	}
	
	
	
	
	
}
