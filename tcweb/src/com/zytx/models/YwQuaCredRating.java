package com.zytx.models;

/***********************************************************************
 * Module:  UserInfo.java
 * Author:  Administrator
 * Purpose: Defines the Class UserInfo
 ***********************************************************************/

import java.util.*;
import java.sql.Timestamp;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.GeneratorType;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.et.ar.exception.ActiveRecordException;
import com.zytx.converters.DateConverter;
import com.zytx.converters.TimestampConverter;

/** userInfo . */
@Table(name = "TwoCodeYwQuaCredRating")
public class YwQuaCredRating extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;
    @Column
    private int ywCompanyID;
    @Column
    private String ratingDate;
	@Column
	private int officeSpace ;
	@Column
	private int officeSpacesj;
	@Column
	private String officeSpacebz;

	@Column
	private int  headQuarters;
	@Column
	private int  headQuarterssj;
	@Column
	private String  headQuartersbz;
	
	@Column
	private String  maintenanceEleCount;
	@Column
	private int  maintenanceEleCountsj;
	
	@Column
	private String  avgmaintenanceEleCount;
	@Column
	private int  avgmaintenanceEleCountsj;
	
    
	@Column 
	private java.lang.String fixedTelOnDuty;
	@Column 
	private int fixedTelOnDutysj;
	@Column 
	private String fixedTelOnDutybz;
	
	@Column 
	private int telOnDutyunattendedTimes;
	@Column 
	private int telOnDutyunattendedsj;
	@Column 
	private String telOnDutyunattendedbz;
	
	@Column
	private int enterpriseChangeTimes;
	@Column 
	private int enterpriseChangesj;
	@Column 
	private String enterpriseChangebz;

	@Column
	private int enterpriseRecord;
	@Column
	private int enterpriseRecordsj;
	@Column
	private String enterpriseRecordbz;
	
	@Column
	private java.math.BigDecimal infoComRate;
	@Column
	private java.math.BigDecimal infoComRatesj;
	
	@Column
	private java.math.BigDecimal sweepCodeRate;
	@Column
	private java.math.BigDecimal sweepCodeRatesj;
	
	@Column
	private java.math.BigDecimal sweepCodeInTimeRate;
	@Column
	private java.math.BigDecimal sweepCodeInTimeRatesj;
	
	@Column
	private int alarmDealwith; 
	@Column
	private int alarmDealwithsj;
	
	@Column
	private int regularInspectionTimes;
	@Column
	private int regularInspectionsj;
	@Column
	private String regularInspectionbz;
	
	@Column
	private int  inspectElevatorTimes;
	@Column
	private int  inspectElevatorTimes2;
	@Column 
	private int inspectElevatorsj;
	@Column 
	private String inspectElevatorbz;
	
	@Column
	private int acceptInspElevatorTimes;
	@Column
	private int acceptInspElevatorsj;
	@Column
	private String acceptInspElevatorbz;
	
	@Column 
	private int maintenSceneInfoTimes;
	@Column
	private int  maintenSceneInfosj;
	@Column
	private String  maintenSceneInfobz;
	
	
	@Column
	private int malignantEventsTimes;
	@Column
	private int malignantEventsTimes2;
	@Column
	private int malignantEventsTimes3;
	@Column
	private int malignantEventssj;
	@Column
	private String  malignantEventsbz;
	
	
	@Column
	private int complaintsEventsTimes;
	@Column
	private int complaintsEventsTimes2;
	@Column
	private int complaintsEventssj;
	@Column
	private String complaintsEventsbz;
	
	@Column
	private int maintenBusinessTimes;
	@Column
	private int maintenBusinesssj;
	@Column
	private String maintenBusinessbz;
	
	@Column
	private int honestTimes;
	@Column
	private int honestsj;
	@Column
	private String honestbz;
	
	@Column
	private int punishmentTimes;
	@Column
	private int punishmentTimes2;
	@Column
	private int punishmentTimes3;
	@Column
	private int punishmentTimes4;
	@Column
	private int punishmentsj;
	@Column
	private String punishmentbz;
	
	@Column
	private int firstRescueTimes;
	@Column
	private int firstRescuesj;
	@Column
	private String firstRescuebz;
	
	@Column
	private int secondRescueTimes;
	@Column
	private int secondRescueTimes2;
	@Column
	private int secondRescuesj;
	@Column
	private String  secondRescuebz;
	
	@Column
	private int secondRescuePoint;
	@Column
	private int secondRescuePointsj;
	@Column
	private String secondRescuePointbz;
	
	@Column
	private int rescueResponseTimes;
	@Column
	private int rescueResponsesj;
	@Column
	private String rescueResponsebz;
	
	@Column
	private int tiringPeopleTimes;
	@Column
	private int tiringPeoplesj;
	@Column
	private String tiringPeoplebz;
	
	@Column
	private int positiveEnergyTimes;
	@Column
	private int  positiveEnergysj;
	@Column
	private String positiveEnergybz;
	
	@Column
	private int expertsSuggestionTimes;
	@Column
	private int  expertsSuggestionsj;
	@Column
	private String  expertsSuggestionbz;
	
	@Column
	private int positiveWork;
	@Column
	private int positiveWorksj;
	@Column
	private String positiveWorkbz;
	
	@Column
	private int remoteMonitor;
	@Column
	private int  remoteMonitorsj;
	@Column
	private String remoteMonitorbz;
	
	@Column
	private int elevatorInsurance;
	@Column
	private int  elevatorInsurancesj;
	@Column
	private String elevatorInsurancebz;
	
	@Column
	private int techinnovationTimes;
	@Column
	private int techinnovationTimes2;
	@Column
	private int techinnovationTimes3;
	@Column
	private int techinnovationTimes4;
	@Column
	private int techinnovationTimes5;
	@Column
	private int techinnovationsj;
	@Column
	private String techinnovationbz;
	@Column
	private String ratingUserName;
	@Column
	private int ratingCompanyId;
	@Column
	private int ratingType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYwCompanyID() {
		return ywCompanyID;
	}
	public void setYwCompanyID(int ywCompanyID) {
		this.ywCompanyID = ywCompanyID;
	}
	
	
	public String getRatingDate() {
		return ratingDate;
	}
	public void setRatingDate(String ratingDate) {
		this.ratingDate = ratingDate;
	}
	public int getOfficeSpace() {
		return officeSpace;
	}
	public void setOfficeSpace(int officeSpace) {
		this.officeSpace = officeSpace;
	}
	public int getOfficeSpacesj() {
		return officeSpacesj;
	}
	public void setOfficeSpacesj(int officeSpacesj) {
		this.officeSpacesj = officeSpacesj;
	}
	public int getHeadQuarters() {
		return headQuarters;
	}
	public void setHeadQuarters(int headQuarters) {
		this.headQuarters = headQuarters;
	}
	public int getHeadQuarterssj() {
		return headQuarterssj;
	}
	public void setHeadQuarterssj(int headQuarterssj) {
		this.headQuarterssj = headQuarterssj;
	}
	public java.lang.String getMaintenanceEleCount() {
		return maintenanceEleCount;
	}
	public void setMaintenanceEleCount(java.lang.String maintenanceEleCount) {
		this.maintenanceEleCount = maintenanceEleCount;
	}
	public int getMaintenanceEleCountsj() {
		return maintenanceEleCountsj;
	}
	public void setMaintenanceEleCountsj(int maintenanceEleCountsj) {
		this.maintenanceEleCountsj = maintenanceEleCountsj;
	}
	public java.lang.String getAvgmaintenanceEleCount() {
		return avgmaintenanceEleCount;
	}
	public void setAvgmaintenanceEleCount(java.lang.String avgmaintenanceEleCount) {
		this.avgmaintenanceEleCount = avgmaintenanceEleCount;
	}
	public int getAvgmaintenanceEleCountsj() {
		return avgmaintenanceEleCountsj;
	}
	public void setAvgmaintenanceEleCountsj(int avgmaintenanceEleCountsj) {
		this.avgmaintenanceEleCountsj = avgmaintenanceEleCountsj;
	}
	public java.lang.String getFixedTelOnDuty() {
		return fixedTelOnDuty;
	}
	public void setFixedTelOnDuty(java.lang.String fixedTelOnDuty) {
		this.fixedTelOnDuty = fixedTelOnDuty;
	}
	public int getFixedTelOnDutysj() {
		return fixedTelOnDutysj;
	}
	public void setFixedTelOnDutysj(int fixedTelOnDutysj) {
		this.fixedTelOnDutysj = fixedTelOnDutysj;
	}
	public int getTelOnDutyunattendedTimes() {
		return telOnDutyunattendedTimes;
	}
	public void setTelOnDutyunattendedTimes(int telOnDutyunattendedTimes) {
		this.telOnDutyunattendedTimes = telOnDutyunattendedTimes;
	}
	public int getTelOnDutyunattendedsj() {
		return telOnDutyunattendedsj;
	}
	public void setTelOnDutyunattendedsj(int telOnDutyunattendedsj) {
		this.telOnDutyunattendedsj = telOnDutyunattendedsj;
	}
	public int getEnterpriseChangeTimes() {
		return enterpriseChangeTimes;
	}
	public void setEnterpriseChangeTimes(int enterpriseChangeTimes) {
		this.enterpriseChangeTimes = enterpriseChangeTimes;
	}
	public int getEnterpriseChangesj() {
		return enterpriseChangesj;
	}
	public void setEnterpriseChangesj(int enterpriseChangesj) {
		this.enterpriseChangesj = enterpriseChangesj;
	}
	public int getEnterpriseRecord() {
		return enterpriseRecord;
	}
	public void setEnterpriseRecord(int enterpriseRecord) {
		this.enterpriseRecord = enterpriseRecord;
	}
	public int getEnterpriseRecordsj() {
		return enterpriseRecordsj;
	}
	public void setEnterpriseRecordsj(int enterpriseRecordsj) {
		this.enterpriseRecordsj = enterpriseRecordsj;
	}
	
	public java.math.BigDecimal getInfoComRate() {
		return infoComRate;
	}
	public void setInfoComRate(java.math.BigDecimal infoComRate) {
		this.infoComRate = infoComRate;
	}
	public java.math.BigDecimal getInfoComRatesj() {
		return infoComRatesj;
	}
	public void setInfoComRatesj(java.math.BigDecimal infoComRatesj) {
		this.infoComRatesj = infoComRatesj;
	}
	public java.math.BigDecimal getSweepCodeRate() {
		return sweepCodeRate;
	}
	public void setSweepCodeRate(java.math.BigDecimal sweepCodeRate) {
		this.sweepCodeRate = sweepCodeRate;
	}
	public java.math.BigDecimal getSweepCodeRatesj() {
		return sweepCodeRatesj;
	}
	public void setSweepCodeRatesj(java.math.BigDecimal sweepCodeRatesj) {
		this.sweepCodeRatesj = sweepCodeRatesj;
	}
	public java.math.BigDecimal getSweepCodeInTimeRate() {
		return sweepCodeInTimeRate;
	}
	public void setSweepCodeInTimeRate(java.math.BigDecimal sweepCodeInTimeRate) {
		this.sweepCodeInTimeRate = sweepCodeInTimeRate;
	}
	public java.math.BigDecimal getSweepCodeInTimeRatesj() {
		return sweepCodeInTimeRatesj;
	}
	public void setSweepCodeInTimeRatesj(java.math.BigDecimal sweepCodeInTimeRatesj) {
		this.sweepCodeInTimeRatesj = sweepCodeInTimeRatesj;
	}
	public int getAlarmDealwith() {
		return alarmDealwith;
	}
	public void setAlarmDealwith(int alarmDealwith) {
		this.alarmDealwith = alarmDealwith;
	}
	public int getAlarmDealwithsj() {
		return alarmDealwithsj;
	}
	public void setAlarmDealwithsj(int alarmDealwithsj) {
		this.alarmDealwithsj = alarmDealwithsj;
	}
	public int getRegularInspectionTimes() {
		return regularInspectionTimes;
	}
	public void setRegularInspectionTimes(int regularInspectionTimes) {
		this.regularInspectionTimes = regularInspectionTimes;
	}
	public int getRegularInspectionsj() {
		return regularInspectionsj;
	}
	public void setRegularInspectionsj(int regularInspectionsj) {
		this.regularInspectionsj = regularInspectionsj;
	}
	public int getInspectElevatorTimes() {
		return inspectElevatorTimes;
	}
	public void setInspectElevatorTimes(int inspectElevatorTimes) {
		this.inspectElevatorTimes = inspectElevatorTimes;
	}
	public int getInspectElevatorTimes2() {
		return inspectElevatorTimes2;
	}
	public void setInspectElevatorTimes2(int inspectElevatorTimes2) {
		this.inspectElevatorTimes2 = inspectElevatorTimes2;
	}
	public int getInspectElevatorsj() {
		return inspectElevatorsj;
	}
	public void setInspectElevatorsj(int inspectElevatorsj) {
		this.inspectElevatorsj = inspectElevatorsj;
	}
	public int getAcceptInspElevatorTimes() {
		return acceptInspElevatorTimes;
	}
	public void setAcceptInspElevatorTimes(int acceptInspElevatorTimes) {
		this.acceptInspElevatorTimes = acceptInspElevatorTimes;
	}
	public int getAcceptInspElevatorsj() {
		return acceptInspElevatorsj;
	}
	public void setAcceptInspElevatorsj(int acceptInspElevatorsj) {
		this.acceptInspElevatorsj = acceptInspElevatorsj;
	}
	public int getMaintenSceneInfoTimes() {
		return maintenSceneInfoTimes;
	}
	public void setMaintenSceneInfoTimes(int maintenSceneInfoTimes) {
		this.maintenSceneInfoTimes = maintenSceneInfoTimes;
	}
	public int getMaintenSceneInfosj() {
		return maintenSceneInfosj;
	}
	public void setMaintenSceneInfosj(int maintenSceneInfosj) {
		this.maintenSceneInfosj = maintenSceneInfosj;
	}
	public int getMalignantEventsTimes() {
		return malignantEventsTimes;
	}
	public void setMalignantEventsTimes(int malignantEventsTimes) {
		this.malignantEventsTimes = malignantEventsTimes;
	}
	public int getMalignantEventsTimes2() {
		return malignantEventsTimes2;
	}
	public void setMalignantEventsTimes2(int malignantEventsTimes2) {
		this.malignantEventsTimes2 = malignantEventsTimes2;
	}
	public int getMalignantEventsTimes3() {
		return malignantEventsTimes3;
	}
	public void setMalignantEventsTimes3(int malignantEventsTimes3) {
		this.malignantEventsTimes3 = malignantEventsTimes3;
	}
	public int getMalignantEventssj() {
		return malignantEventssj;
	}
	public void setMalignantEventssj(int malignantEventssj) {
		this.malignantEventssj = malignantEventssj;
	}
	public int getComplaintsEventsTimes() {
		return complaintsEventsTimes;
	}
	public void setComplaintsEventsTimes(int complaintsEventsTimes) {
		this.complaintsEventsTimes = complaintsEventsTimes;
	}
	public int getComplaintsEventsTimes2() {
		return complaintsEventsTimes2;
	}
	public void setComplaintsEventsTimes2(int complaintsEventsTimes2) {
		this.complaintsEventsTimes2 = complaintsEventsTimes2;
	}
	public int getComplaintsEventssj() {
		return complaintsEventssj;
	}
	public void setComplaintsEventssj(int complaintsEventssj) {
		this.complaintsEventssj = complaintsEventssj;
	}
	public int getMaintenBusinessTimes() {
		return maintenBusinessTimes;
	}
	public void setMaintenBusinessTimes(int maintenBusinessTimes) {
		this.maintenBusinessTimes = maintenBusinessTimes;
	}
	public int getMaintenBusinesssj() {
		return maintenBusinesssj;
	}
	public void setMaintenBusinesssj(int maintenBusinesssj) {
		this.maintenBusinesssj = maintenBusinesssj;
	}
	public int getHonestTimes() {
		return honestTimes;
	}
	public void setHonestTimes(int honestTimes) {
		this.honestTimes = honestTimes;
	}
	public int getHonestsj() {
		return honestsj;
	}
	public void setHonestsj(int honestsj) {
		this.honestsj = honestsj;
	}
	public int getPunishmentTimes() {
		return punishmentTimes;
	}
	public void setPunishmentTimes(int punishmentTimes) {
		this.punishmentTimes = punishmentTimes;
	}
	public int getPunishmentTimes2() {
		return punishmentTimes2;
	}
	public void setPunishmentTimes2(int punishmentTimes2) {
		this.punishmentTimes2 = punishmentTimes2;
	}
	public int getPunishmentTimes3() {
		return punishmentTimes3;
	}
	public void setPunishmentTimes3(int punishmentTimes3) {
		this.punishmentTimes3 = punishmentTimes3;
	}
	public int getPunishmentTimes4() {
		return punishmentTimes4;
	}
	public void setPunishmentTimes4(int punishmentTimes4) {
		this.punishmentTimes4 = punishmentTimes4;
	}
	public int getPunishmentsj() {
		return punishmentsj;
	}
	public void setPunishmentsj(int punishmentsj) {
		this.punishmentsj = punishmentsj;
	}
	public int getFirstRescueTimes() {
		return firstRescueTimes;
	}
	public void setFirstRescueTimes(int firstRescueTimes) {
		this.firstRescueTimes = firstRescueTimes;
	}
	public int getFirstRescuesj() {
		return firstRescuesj;
	}
	public void setFirstRescuesj(int firstRescuesj) {
		this.firstRescuesj = firstRescuesj;
	}
	public int getSecondRescueTimes() {
		return secondRescueTimes;
	}
	public void setSecondRescueTimes(int secondRescueTimes) {
		this.secondRescueTimes = secondRescueTimes;
	}
	public int getSecondRescueTimes2() {
		return secondRescueTimes2;
	}
	public void setSecondRescueTimes2(int secondRescueTimes2) {
		this.secondRescueTimes2 = secondRescueTimes2;
	}
	public int getSecondRescuesj() {
		return secondRescuesj;
	}
	public void setSecondRescuesj(int secondRescuesj) {
		this.secondRescuesj = secondRescuesj;
	}
	public int getSecondRescuePoint() {
		return secondRescuePoint;
	}
	public void setSecondRescuePoint(int secondRescuePoint) {
		this.secondRescuePoint = secondRescuePoint;
	}
	public int getSecondRescuePointsj() {
		return secondRescuePointsj;
	}
	public void setSecondRescuePointsj(int secondRescuePointsj) {
		this.secondRescuePointsj = secondRescuePointsj;
	}
	public int getRescueResponseTimes() {
		return rescueResponseTimes;
	}
	public void setRescueResponseTimes(int rescueResponseTimes) {
		this.rescueResponseTimes = rescueResponseTimes;
	}
	public int getRescueResponsesj() {
		return rescueResponsesj;
	}
	public void setRescueResponsesj(int rescueResponsesj) {
		this.rescueResponsesj = rescueResponsesj;
	}
	public int getTiringPeopleTimes() {
		return tiringPeopleTimes;
	}
	public void setTiringPeopleTimes(int tiringPeopleTimes) {
		this.tiringPeopleTimes = tiringPeopleTimes;
	}
	public int getTiringPeoplesj() {
		return tiringPeoplesj;
	}
	public void setTiringPeoplesj(int tiringPeoplesj) {
		this.tiringPeoplesj = tiringPeoplesj;
	}
	public int getPositiveEnergyTimes() {
		return positiveEnergyTimes;
	}
	public void setPositiveEnergyTimes(int positiveEnergyTimes) {
		this.positiveEnergyTimes = positiveEnergyTimes;
	}
	public int getPositiveEnergysj() {
		return positiveEnergysj;
	}
	public void setPositiveEnergysj(int positiveEnergysj) {
		this.positiveEnergysj = positiveEnergysj;
	}
	public int getExpertsSuggestionTimes() {
		return expertsSuggestionTimes;
	}
	public void setExpertsSuggestionTimes(int expertsSuggestionTimes) {
		this.expertsSuggestionTimes = expertsSuggestionTimes;
	}
	public int getExpertsSuggestionsj() {
		return expertsSuggestionsj;
	}
	public void setExpertsSuggestionsj(int expertsSuggestionsj) {
		this.expertsSuggestionsj = expertsSuggestionsj;
	}
	public int getPositiveWork() {
		return positiveWork;
	}
	public void setPositiveWork(int positiveWork) {
		this.positiveWork = positiveWork;
	}
	public int getPositiveWorksj() {
		return positiveWorksj;
	}
	public void setPositiveWorksj(int positiveWorksj) {
		this.positiveWorksj = positiveWorksj;
	}
	public int getRemoteMonitor() {
		return remoteMonitor;
	}
	public void setRemoteMonitor(int remoteMonitor) {
		this.remoteMonitor = remoteMonitor;
	}
	public int getRemoteMonitorsj() {
		return remoteMonitorsj;
	}
	public void setRemoteMonitorsj(int remoteMonitorsj) {
		this.remoteMonitorsj = remoteMonitorsj;
	}
	public int getElevatorInsurance() {
		return elevatorInsurance;
	}
	public void setElevatorInsurance(int elevatorInsurance) {
		this.elevatorInsurance = elevatorInsurance;
	}
	public int getElevatorInsurancesj() {
		return elevatorInsurancesj;
	}
	public void setElevatorInsurancesj(int elevatorInsurancesj) {
		this.elevatorInsurancesj = elevatorInsurancesj;
	}
	public int getTechinnovationTimes() {
		return techinnovationTimes;
	}
	public void setTechinnovationTimes(int techinnovationTimes) {
		this.techinnovationTimes = techinnovationTimes;
	}
	public int getTechinnovationTimes2() {
		return techinnovationTimes2;
	}
	public void setTechinnovationTimes2(int techinnovationTimes2) {
		this.techinnovationTimes2 = techinnovationTimes2;
	}
	public int getTechinnovationTimes3() {
		return techinnovationTimes3;
	}
	public void setTechinnovationTimes3(int techinnovationTimes3) {
		this.techinnovationTimes3 = techinnovationTimes3;
	}
	public int getTechinnovationTimes4() {
		return techinnovationTimes4;
	}
	public void setTechinnovationTimes4(int techinnovationTimes4) {
		this.techinnovationTimes4 = techinnovationTimes4;
	}
	public int getTechinnovationTimes5() {
		return techinnovationTimes5;
	}
	public void setTechinnovationTimes5(int techinnovationTimes5) {
		this.techinnovationTimes5 = techinnovationTimes5;
	}
	public int getTechinnovationsj() {
		return techinnovationsj;
	}
	public void setTechinnovationsj(int techinnovationsj) {
		this.techinnovationsj = techinnovationsj;
	}
	public String getOfficeSpacebz() {
		return officeSpacebz;
	}
	public void setOfficeSpacebz(String officeSpacebz) {
		this.officeSpacebz = officeSpacebz;
	}
	public String getHeadQuartersbz() {
		return headQuartersbz;
	}
	public void setHeadQuartersbz(String headQuartersbz) {
		this.headQuartersbz = headQuartersbz;
	}
	public String getFixedTelOnDutybz() {
		return fixedTelOnDutybz;
	}
	public void setFixedTelOnDutybz(String fixedTelOnDutybz) {
		this.fixedTelOnDutybz = fixedTelOnDutybz;
	}
	public String getTelOnDutyunattendedbz() {
		return telOnDutyunattendedbz;
	}
	public void setTelOnDutyunattendedbz(String telOnDutyunattendedbz) {
		this.telOnDutyunattendedbz = telOnDutyunattendedbz;
	}
	public String getEnterpriseChangebz() {
		return enterpriseChangebz;
	}
	public void setEnterpriseChangebz(String enterpriseChangebz) {
		this.enterpriseChangebz = enterpriseChangebz;
	}
	public String getEnterpriseRecordbz() {
		return enterpriseRecordbz;
	}
	public void setEnterpriseRecordbz(String enterpriseRecordbz) {
		this.enterpriseRecordbz = enterpriseRecordbz;
	}
	public String getRegularInspectionbz() {
		return regularInspectionbz;
	}
	public void setRegularInspectionbz(String regularInspectionbz) {
		this.regularInspectionbz = regularInspectionbz;
	}
	public String getInspectElevatorbz() {
		return inspectElevatorbz;
	}
	public void setInspectElevatorbz(String inspectElevatorbz) {
		this.inspectElevatorbz = inspectElevatorbz;
	}
	public String getAcceptInspElevatorbz() {
		return acceptInspElevatorbz;
	}
	public void setAcceptInspElevatorbz(String acceptInspElevatorbz) {
		this.acceptInspElevatorbz = acceptInspElevatorbz;
	}
	public String getMaintenSceneInfobz() {
		return maintenSceneInfobz;
	}
	public void setMaintenSceneInfobz(String maintenSceneInfobz) {
		this.maintenSceneInfobz = maintenSceneInfobz;
	}
	public String getMalignantEventsbz() {
		return malignantEventsbz;
	}
	public void setMalignantEventsbz(String malignantEventsbz) {
		this.malignantEventsbz = malignantEventsbz;
	}
	public String getComplaintsEventsbz() {
		return complaintsEventsbz;
	}
	public void setComplaintsEventsbz(String complaintsEventsbz) {
		this.complaintsEventsbz = complaintsEventsbz;
	}
	public String getMaintenBusinessbz() {
		return maintenBusinessbz;
	}
	public void setMaintenBusinessbz(String maintenBusinessbz) {
		this.maintenBusinessbz = maintenBusinessbz;
	}
	public String getHonestbz() {
		return honestbz;
	}
	public void setHonestbz(String honestbz) {
		this.honestbz = honestbz;
	}
	public String getPunishmentbz() {
		return punishmentbz;
	}
	public void setPunishmentbz(String punishmentbz) {
		this.punishmentbz = punishmentbz;
	}
	public String getFirstRescuebz() {
		return firstRescuebz;
	}
	public void setFirstRescuebz(String firstRescuebz) {
		this.firstRescuebz = firstRescuebz;
	}
	public String getSecondRescuebz() {
		return secondRescuebz;
	}
	public void setSecondRescuebz(String secondRescuebz) {
		this.secondRescuebz = secondRescuebz;
	}
	public String getSecondRescuePointbz() {
		return secondRescuePointbz;
	}
	public void setSecondRescuePointbz(String secondRescuePointbz) {
		this.secondRescuePointbz = secondRescuePointbz;
	}
	public String getRescueResponsebz() {
		return rescueResponsebz;
	}
	public void setRescueResponsebz(String rescueResponsebz) {
		this.rescueResponsebz = rescueResponsebz;
	}
	public String getTiringPeoplebz() {
		return tiringPeoplebz;
	}
	public void setTiringPeoplebz(String tiringPeoplebz) {
		this.tiringPeoplebz = tiringPeoplebz;
	}
	public String getPositiveEnergybz() {
		return positiveEnergybz;
	}
	public void setPositiveEnergybz(String positiveEnergybz) {
		this.positiveEnergybz = positiveEnergybz;
	}
	public String getExpertsSuggestionbz() {
		return expertsSuggestionbz;
	}
	public void setExpertsSuggestionbz(String expertsSuggestionbz) {
		this.expertsSuggestionbz = expertsSuggestionbz;
	}
	public String getPositiveWorkbz() {
		return positiveWorkbz;
	}
	public void setPositiveWorkbz(String positiveWorkbz) {
		this.positiveWorkbz = positiveWorkbz;
	}
	public String getRemoteMonitorbz() {
		return remoteMonitorbz;
	}
	public void setRemoteMonitorbz(String remoteMonitorbz) {
		this.remoteMonitorbz = remoteMonitorbz;
	}
	public String getElevatorInsurancebz() {
		return elevatorInsurancebz;
	}
	public void setElevatorInsurancebz(String elevatorInsurancebz) {
		this.elevatorInsurancebz = elevatorInsurancebz;
	}
	public String getTechinnovationbz() {
		return techinnovationbz;
	}
	public void setTechinnovationbz(String techinnovationbz) {
		this.techinnovationbz = techinnovationbz;
	}
	public String getRatingUserName() {
		return ratingUserName;
	}
	public void setRatingUserName(String ratingUserName) {
		this.ratingUserName = ratingUserName;
	}
	public int getRatingCompanyId() {
		return ratingCompanyId;
	}
	public void setRatingCompanyId(int ratingCompanyId) {
		this.ratingCompanyId = ratingCompanyId;
	}
	public int getRatingType() {
		return ratingType;
	}
	public void setRatingType(int ratingType) {
		this.ratingType = ratingType;
	}
	
	
	
	
	
}