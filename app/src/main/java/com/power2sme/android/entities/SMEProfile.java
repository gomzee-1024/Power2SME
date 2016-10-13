package com.power2sme.android.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class SMEProfile 
{
	@JsonProperty("CompanyInformation")
	private CompanyInformation companyInformation;
	@JsonProperty("GeneralInformation")
	private GeneralInformation generalInformation;
	@JsonProperty("TaxInformation")
	private TaxInformation taxInformation;
	@JsonProperty("intLeadid")
	private long intLeadid;
	
	
	public CompanyInformation getCompanyInformation() {
		return companyInformation;
	}
	public void setCompanyInformation(CompanyInformation companyInformation) {
		this.companyInformation = companyInformation;
	}
	public GeneralInformation getGeneralInformation() {
		return generalInformation;
	}
	public void setGeneralInformation(GeneralInformation generalInformation) {
		this.generalInformation = generalInformation;
	}
	public TaxInformation getTaxInformation() {
		return taxInformation;
	}
	public void setTaxInformation(TaxInformation taxInformation) {
		this.taxInformation = taxInformation;
	}
	public long getIntLeadid() {
		return intLeadid;
	}
	public void setIntLeadid(long intLeadid) {
		this.intLeadid = intLeadid;
	}
	
	
}
