package com.power2sme.android.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyInformation 
{
	@JsonProperty("ChequeReceived")
	private boolean chequeReceived;
	@JsonProperty("CompanySize")
	private int companySize;
	@JsonProperty("CompanyStatus")
	private int companyStatus;
	@JsonProperty("CreditRatingDone")
	private String creditRatingDone;
	@JsonProperty("FoundedBy")
	private String foundedBy;
	@JsonProperty("HowManyPartners")
	private int howManyPartners;
	@JsonProperty("IndustryCategory")
	private String industryCategory;
	@JsonProperty("IndustrySubCategory")
	private String industrySubCategory;
	@JsonProperty("LeadSource")
	private String leadSource;
	@JsonProperty("MemberofTradeAssociation")
	private String memberofTradeAssociation;
	@JsonProperty("ModeOfSendingCheque")
	private String modeOfSendingCheque;
	@JsonProperty("NameofTradeAssociation")
	private String nameofTradeAssociation;
	@JsonProperty("NumberofEmployees")
	private int numberofEmployees;
	@JsonProperty("NumberofYearsatthatPlace")
	private int numberofYearsatthatPlace;
	@JsonProperty("OtherBusinessEntityAddress")
	private String otherBusinessEntityAddress;
	@JsonProperty("OtherRelatedBusinessEntity")
	private String otherRelatedBusinessEntity;
	@JsonProperty("Ownership")
	private String ownership;
	@JsonProperty("RatingAgency")
	private float ratingAgency;
	@JsonProperty("TurnoverAnnualinCrs")
	private double turnoverAnnualinCrs;
	@JsonProperty("TurnoverFY")
	private String turnoverFY;
	public boolean isChequeReceived() {
		return chequeReceived;
	}
	public void setChequeReceived(boolean chequeReceived) {
		this.chequeReceived = chequeReceived;
	}
	public int getCompanySize() {
		return companySize;
	}
	public void setCompanySize(int companySize) {
		this.companySize = companySize;
	}
	public int getCompanyStatus() {
		return companyStatus;
	}
	public void setCompanyStatus(int companyStatus) {
		this.companyStatus = companyStatus;
	}
	public String getCreditRatingDone() {
		return Utils.checkStringForNull(creditRatingDone);
	}
	public void setCreditRatingDone(String creditRatingDone) {
		this.creditRatingDone = creditRatingDone;
	}
	public String getFoundedBy() {
		return Utils.checkStringForNull(foundedBy);
	}
	public void setFoundedBy(String foundedBy) {
		this.foundedBy = foundedBy;
	}
	public int getHowManyPartners() {
		return howManyPartners;
	}
	public void setHowManyPartners(int howManyPartners) {
		this.howManyPartners = howManyPartners;
	}
	public String getIndustryCategory() {
		return Utils.checkStringForNull(industryCategory);
	}
	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}
	public String getIndustrySubCategory() {
		return Utils.checkStringForNull(industrySubCategory);
	}
	public void setIndustrySubCategory(String industrySubCategory) {
		this.industrySubCategory = industrySubCategory;
	}
	public String getLeadSource() {
		return Utils.checkStringForNull(leadSource);
	}
	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}
	public String getMemberofTradeAssociation() {
		return Utils.checkStringForNull(memberofTradeAssociation);
	}
	public void setMemberofTradeAssociation(String memberofTradeAssociation) {
		this.memberofTradeAssociation = memberofTradeAssociation;
	}
	public String getModeOfSendingCheque() {
		return Utils.checkStringForNull(modeOfSendingCheque);
	}
	public void setModeOfSendingCheque(String modeOfSendingCheque) {
		this.modeOfSendingCheque = modeOfSendingCheque;
	}
	public String getNameofTradeAssociation() {
		return Utils.checkStringForNull(nameofTradeAssociation);
	}
	public void setNameofTradeAssociation(String nameofTradeAssociation) {
		this.nameofTradeAssociation = nameofTradeAssociation;
	}
	public int getNumberofEmployees() {
		return numberofEmployees;
	}
	public void setNumberofEmployees(int numberofEmployees) {
		this.numberofEmployees = numberofEmployees;
	}
	public int getNumberofYearsatthatPlace() {
		return numberofYearsatthatPlace;
	}
	public void setNumberofYearsatthatPlace(int numberofYearsatthatPlace) {
		this.numberofYearsatthatPlace = numberofYearsatthatPlace;
	}
	public String getOtherBusinessEntityAddress() {
		return Utils.checkStringForNull(otherBusinessEntityAddress);
	}
	public void setOtherBusinessEntityAddress(String otherBusinessEntityAddress) {
		this.otherBusinessEntityAddress = otherBusinessEntityAddress;
	}
	public String getOtherRelatedBusinessEntity() {
		return Utils.checkStringForNull(otherRelatedBusinessEntity);
	}
	public void setOtherRelatedBusinessEntity(String otherRelatedBusinessEntity) {
		this.otherRelatedBusinessEntity = otherRelatedBusinessEntity;
	}
	public String getOwnership() {
		return Utils.checkStringForNull(ownership);
	}
	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}
	public float getRatingAgency() {
		return ratingAgency;
	}
	public void setRatingAgency(float ratingAgency) {
		this.ratingAgency = ratingAgency;
	}
	public double getTurnoverAnnualinCrs() {
		return turnoverAnnualinCrs;
	}
	public void setTurnoverAnnualinCrs(double turnoverAnnualinCrs) {
		this.turnoverAnnualinCrs = turnoverAnnualinCrs;
	}
	public String getTurnoverFY() {
		return Utils.checkStringForNull(turnoverFY);
	}
	public void setTurnoverFY(String turnoverFY) {
		this.turnoverFY = turnoverFY;
	}
	
	
}
