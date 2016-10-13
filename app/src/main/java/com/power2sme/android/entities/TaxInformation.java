package com.power2sme.android.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaxInformation 
{
	@JsonProperty("AnyLowerTaxRateAllowed")
	private String anyLowerTaxRateAllowed;
	@JsonProperty("AnyOtherRegistrationNumber")
	private String anyOtherRegistrationNumber;
	@JsonProperty("AnyOtherRegistrationNumber2")
	private String anyOtherRegistrationNumber2;
	@JsonProperty("CECommissionerate")
	private String cECommissionerate;
	@JsonProperty("CEDivision")
	private String cEDivision;
	@JsonProperty("CERange")
	private String cERange;
	@JsonProperty("CETHNumber")
	private String cETHNumber;
	@JsonProperty("CSTNumber")
	private String cSTNumber;
	@JsonProperty("ECCNumber")
	private String eCCNumber;
	@JsonProperty("PANNumberofOwner")
	private String pANNumberofOwner;
	@JsonProperty("ROCRegistrationNumber")
	private String rOCRegistrationNumber;
	@JsonProperty("SalesTaxRegistrationNo")
	private String salesTaxRegistrationNo;
	@JsonProperty("ServiceTaxCategoryDesc")
	private String serviceTaxCategoryDesc;
	@JsonProperty("TaxExemption")
	private String taxExemption;
	@JsonProperty("TradeLicenceNumber")
	private String tradeLicenceNumber;
	@JsonProperty("VATNumber")
	private String vATNumber;
	
	public String getAnyLowerTaxRateAllowed() {
		return Utils.checkStringForNull(anyLowerTaxRateAllowed);
	}
	public void setAnyLowerTaxRateAllowed(String anyLowerTaxRateAllowed) {
		this.anyLowerTaxRateAllowed = anyLowerTaxRateAllowed;
	}
	public String getAnyOtherRegistrationNumber() {
		return Utils.checkStringForNull(anyOtherRegistrationNumber);
	}
	public void setAnyOtherRegistrationNumber(String anyOtherRegistrationNumber) {
		this.anyOtherRegistrationNumber = anyOtherRegistrationNumber;
	}
	public String getAnyOtherRegistrationNumber2() {
		return Utils.checkStringForNull(anyOtherRegistrationNumber2);
	}
	public void setAnyOtherRegistrationNumber2(String anyOtherRegistrationNumber2) {
		this.anyOtherRegistrationNumber2 = anyOtherRegistrationNumber2;
	}
	public String getcECommissionerate() {
		return Utils.checkStringForNull(cECommissionerate);
	}
	public void setcECommissionerate(String cECommissionerate) {
		this.cECommissionerate = cECommissionerate;
	}
	public String getcEDivision() {
		return Utils.checkStringForNull(cEDivision);
	}
	public void setcEDivision(String cEDivision) {
		this.cEDivision = cEDivision;
	}
	public String getcERange() {
		return Utils.checkStringForNull(cERange);
	}
	public void setcERange(String cERange) {
		this.cERange = cERange;
	}
	public String getcETHNumber() {
		return Utils.checkStringForNull(cETHNumber);
	}
	public void setcETHNumber(String cETHNumber) {
		this.cETHNumber = cETHNumber;
	}
	public String getcSTNumber() {
		return Utils.checkStringForNull(cSTNumber);
	}
	public void setcSTNumber(String cSTNumber) {
		this.cSTNumber = cSTNumber;
	}
	public String geteCCNumber() {
		return Utils.checkStringForNull(eCCNumber);
	}
	public void seteCCNumber(String eCCNumber) {
		this.eCCNumber = eCCNumber;
	}
	public String getpANNumberofOwner() {
		return Utils.checkStringForNull(pANNumberofOwner);
	}
	public void setpANNumberofOwner(String pANNumberofOwner) {
		this.pANNumberofOwner = pANNumberofOwner;
	}
	public String getrOCRegistrationNumber() {
		return Utils.checkStringForNull(rOCRegistrationNumber);
	}
	public void setrOCRegistrationNumber(String rOCRegistrationNumber) {
		this.rOCRegistrationNumber = rOCRegistrationNumber;
	}
	public String getSalesTaxRegistrationNo() {
		return Utils.checkStringForNull(salesTaxRegistrationNo);
	}
	public void setSalesTaxRegistrationNo(String salesTaxRegistrationNo) {
		this.salesTaxRegistrationNo = salesTaxRegistrationNo;
	}
	public String getServiceTaxCategoryDesc() {
		return Utils.checkStringForNull(serviceTaxCategoryDesc);
	}
	public void setServiceTaxCategoryDesc(String serviceTaxCategoryDesc) {
		this.serviceTaxCategoryDesc = serviceTaxCategoryDesc;
	}
	public String getTaxExemption() {
		return Utils.checkStringForNull(taxExemption);
	}
	public void setTaxExemption(String taxExemption) {
		this.taxExemption = taxExemption;
	}
	public String getTradeLicenceNumber() {
		return Utils.checkStringForNull(tradeLicenceNumber);
	}
	public void setTradeLicenceNumber(String tradeLicenceNumber) {
		this.tradeLicenceNumber = tradeLicenceNumber;
	}
	public String getvATNumber() {
		return Utils.checkStringForNull(vATNumber);
	}
	public void setvATNumber(String vATNumber) {
		this.vATNumber = vATNumber;
	}
}
