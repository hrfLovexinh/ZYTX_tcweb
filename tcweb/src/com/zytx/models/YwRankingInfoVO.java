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
	private String ywCompanyName;    //ά����˾��
	@Column
	private int ywManagerEleCount;  //���������
	@Column
	private int ywEleCount;   //ɨ��ά��������
	@Column
	private int ywEleExpireCount;   //����ά��������
	@Column
	private float tScanMaintainPercent;   //ɨ��ά����
	@Column
	private float tScanUnexpireMaintainPercent;   //����ɨ��ά����
	@Column
	private float tComplaintRate;    //Ͷ����
	@Column
	private float t1Percent; //��Ϣ������
	@Column
	private float t2Percent;   //��ͬ��Ч��
	@Column
	private float tSave30Percent;   //��Ԯ���ﳬ30����
	@Column
	private float t1LevelNotPercent;  //δʵʩһ����Ԯ
	@Column
	private float t2LevelPercent;  //ʵʩ������Ԯ
	@Column
	private float tPunish;   //�ܵ���������
	@Column
	private float tScore;  //�ۺϵ÷�
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
