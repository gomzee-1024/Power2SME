package com.power2sme.android.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewRFQ implements Parcelable
{
	@JsonProperty("ShippingAddress")
	private String ShippingAddress;
	@JsonProperty("CompanyName")
	private String CompanyName;
	@JsonProperty("ContactPersonFirstName")
	private String ContactPersonFirstName;
	@JsonProperty("ContactPersonLastName")
	private String ContactPersonLastName;
	@JsonProperty("MobileNumber")
	private String MobileNumber;
	@JsonProperty("Email")
	private String Email;
	@JsonProperty("State")
	private String State;
	@JsonProperty("City")
	private String City;
	@JsonProperty("Pincode")
	private String Pincode;
	@JsonProperty("TaxationPref")
	private String TaxationPref;
	@JsonProperty("ShippingAddressCode")
	private String ShippingAddressCode;
	@JsonProperty("FreightRequire")
	private int FreightRequire;
	@JsonProperty("FormC")
	private int FormC;
	@JsonProperty("PaymentTerms")
	private String PaymentTerms;
	@JsonProperty("AdvancePayment")
	private String AdvancePayment;
	@JsonProperty("CreatedBy")
	private String CreatedBy;
	@JsonProperty("PaymentMethod")
	private String PaymentMethod;
	@JsonProperty("TaxationRemarks")
	private String TaxationRemarks;
	@JsonProperty("RFQLine")
	private List<NewRFQItem> RFQLine;

    @JsonProperty("LeadSource")
    private String LeadSource;


	
	public NewRFQ(){}
	public NewRFQ(Parcel in)
	{
		RFQLine =new ArrayList<NewRFQItem>();


		ShippingAddress = in.readString();
		CompanyName = in.readString();
		ContactPersonFirstName = in.readString();
		ContactPersonLastName = in.readString();
		MobileNumber = in.readString();
		Email = in.readString();
		State = in.readString();
		City = in.readString();
		Pincode = in.readString();
		TaxationPref = in.readString();
		ShippingAddressCode = in.readString();
		FreightRequire = in.readInt();
		FormC = in.readInt();
		PaymentTerms = in.readString();
		AdvancePayment = in.readString();
		CreatedBy = in.readString();
		PaymentMethod = in.readString();
		TaxationRemarks = in.readString();
		in.readTypedList(RFQLine, NewRFQItem.CREATOR);
        LeadSource = in.readString();
	}
	@Override
	public int describeContents() 
	{
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeString(ShippingAddress);
		dest.writeString(CompanyName);
		dest.writeString(ContactPersonFirstName);
		dest.writeString(ContactPersonLastName);
		dest.writeString(MobileNumber);
		dest.writeString(Email);
		dest.writeString(State);
		dest.writeString(City);
		dest.writeString(Pincode);
		dest.writeString(TaxationPref);
		dest.writeString(ShippingAddressCode);
		dest.writeInt(FreightRequire);
		dest.writeInt(FormC);
		dest.writeString(PaymentTerms);
		dest.writeString(AdvancePayment);
		dest.writeString(CreatedBy);
		dest.writeString(PaymentMethod);
		dest.writeString(TaxationRemarks);
		dest.writeTypedList(RFQLine);
        dest.writeString(LeadSource);
	}
	public static final Creator<NewRFQ> CREATOR = new Creator<NewRFQ>()
 	{
 		public NewRFQ createFromParcel(Parcel in) 
 		{
 			return new NewRFQ(in);
 		}
 	
 		public NewRFQ[] newArray (int size) 
 		{
 			return new NewRFQ[size];
 		}
 	};
	//////////////////////////////////////////////////////////////////////////


    public String getLeadSource() {
        return Utils.checkStringForNull(LeadSource);
    }

    public void setLeadSource(String leadSource) {
        LeadSource = leadSource;
    }

    /**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return Utils.checkStringForNull(CompanyName);
	}
	/**
	 * @return the shippingAddress
	 */
	public String getShippingAddress() {
		return Utils.checkStringForNull(ShippingAddress);
	}
	/**
	 * @param shippingAddress the shippingAddress to set
	 */
	public void setShippingAddress(String shippingAddress) {
		ShippingAddress = shippingAddress;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	/**
	 * @return the contactPersonFirstName
	 */
	public String getContactPersonFirstName() {
		return Utils.checkStringForNull(ContactPersonFirstName);
	}
	/**
	 * @param contactPersonFirstName the contactPersonFirstName to set
	 */
	public void setContactPersonFirstName(String contactPersonFirstName) {
		ContactPersonFirstName = contactPersonFirstName;
	}
	/**
	 * @return the contactPersonLastName
	 */
	public String getContactPersonLastName() {
		return ContactPersonLastName;
	}
	/**
	 * @param contactPersonLastName the contactPersonLastName to set
	 */
	public void setContactPersonLastName(String contactPersonLastName) {
		ContactPersonLastName = contactPersonLastName;
	}
	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return Utils.checkStringForNull(MobileNumber);
	}
	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		MobileNumber = mobileNumber;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return Utils.checkStringForNull(Email);
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		Email = email;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return Utils.checkStringForNull(State);
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		State = state;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return Utils.checkStringForNull(City);
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		City = city;
	}
	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return Utils.checkStringForNull(Pincode);
	}
	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(String pincode) {
		Pincode = pincode;
	}
	/**
	 * @return the taxationPref
	 */
	public String getTaxationPref() {
		return Utils.checkStringForNull(TaxationPref);
	}
	/**
	 * @param taxationPref the taxationPref to set
	 */
	public void setTaxationPref(String taxationPref) {
		TaxationPref = taxationPref;
	}
	/**
	 * @return the shippingAddressCode
	 */
	public String getShippingAddressCode() {
		return Utils.checkStringForNull(ShippingAddressCode);
	}
	/**
	 * @param shippingAddressCode the shippingAddressCode to set
	 */
	public void setShippingAddressCode(String shippingAddressCode) {
		ShippingAddressCode = shippingAddressCode;
	}
	/**
	 * @return the freightRequire
	 */
	public int getFreightRequire() {
		return FreightRequire;
	}
	/**
	 * @param freightRequire the freightRequire to set
	 */
	public void setFreightRequire(int freightRequire) {
		FreightRequire = freightRequire;
	}
	/**
	 * @return the formC
	 */
	public int getFormC() {
		return FormC;
	}
	/**
	 * @param formC the formC to set
	 */
	public void setFormC(int formC) {
		FormC = formC;
	}
	/**
	 * @return the paymentTerms
	 */
	public String getPaymentTerms() {
		return Utils.checkStringForNull(PaymentTerms);
	}
	/**
	 * @param paymentTerms the paymentTerms to set
	 */
	public void setPaymentTerms(String paymentTerms) {
		PaymentTerms = paymentTerms;
	}
	/**
	 * @return the advancePayment
	 */
	public String getAdvancePayment() {
		return Utils.checkStringForNull(AdvancePayment);
	}
	/**
	 * @param advancePayment the advancePayment to set
	 */
	public void setAdvancePayment(String advancePayment) {
		AdvancePayment = advancePayment;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return Utils.checkStringForNull(CreatedBy);
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}
	/**
	 * @return the paymentMethod
	 */
	public String getPaymentMethod() {
		return Utils.checkStringForNull(PaymentMethod);
	}
	/**
	 * @param paymentMethod the paymentMethod to set
	 */
	public void setPaymentMethod(String paymentMethod) {
		PaymentMethod = paymentMethod;
	}
	/**
	 * @return the taxationRemarks
	 */
	public String getTaxationRemarks() {
		return Utils.checkStringForNull(TaxationRemarks);
	}
	/**
	 * @param taxationRemarks the taxationRemarks to set
	 */
	public void setTaxationRemarks(String taxationRemarks) {
		TaxationRemarks = taxationRemarks;
	}
	/**
	 * @return the rFQLine
	 */
	public List<NewRFQItem> getRFQLine() {
		return RFQLine;
	}
	/**
	 * @param rFQLine the rFQLine to set
	 */
	public void setRFQLine(List<NewRFQItem> rFQLine) {
		RFQLine = rFQLine;
	}
 	
}
