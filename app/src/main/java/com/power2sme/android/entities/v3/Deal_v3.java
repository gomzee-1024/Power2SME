package com.power2sme.android.entities.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.DealItemPrice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tausif on 14/7/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Deal_v3 implements Parcelable
{
    @JsonProperty("id")
    private String id;
    @JsonProperty("product")
    private String product;
    @JsonProperty("product_sku")
    private String product_sku;
    @JsonProperty("grade")
    private String grade;
    @JsonProperty("brand")
    private String brand;
    @JsonProperty("avlqty")
    private String avlqty;
    @JsonProperty("status")
    private String status;
    @JsonProperty("remarks")
    private String remarks;
    @JsonProperty("purchasePrice")
    private String purchasePrice;
    @JsonProperty("margin_strategy_id")
    private String margin_strategy_id;
    @JsonProperty("margin_strategy_name")
    private String margin_strategy_name;
    @JsonProperty("categoryId")
    private String categoryId;
    @JsonProperty("uom")
    private String uom;
    @JsonProperty("longDescription")
    private String longDescription;
    @JsonProperty("categoryName")
    private String categoryName;
    @JsonProperty("qty")
    private String qty;
    @JsonProperty("targetStateCodes")
    private ArrayList<String> targetStateCodes;
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("endDate")
    private String endDate;
    @JsonProperty("startTimestamp")
    private String startTimestamp;
    @JsonProperty("endTimestamp")
    private String endTimestamp;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("locationType")
    private String locationType;
    @JsonProperty("locationValue")
    private String locationValue;
    @JsonProperty("priceSpace")
    private List<DealItemPrice> priceSpace;
    @JsonProperty("canned")
    private String canned;
    @JsonProperty("taxationPref")
    private TaxationPreference_v3 taxationPref;
    @JsonProperty("freight")
    private String freight;
    @JsonProperty("sku")
    private SKU_v3 sku;
    @JsonProperty("urgency")
    private Urgency_v3 urgency;
    @JsonProperty("targetStateNames")
    private ArrayList<String> targetStateNames;
    @JsonProperty("createdOn")
    private String createdOn;
    @JsonProperty("formC")
    private String formC;


    public Deal_v3(){}
    public Deal_v3(Parcel in)
    {
        targetStateCodes=new ArrayList<String>();
        priceSpace= new ArrayList<DealItemPrice>();
        targetStateNames=new ArrayList<String>();
//        taxationPref=new TaxationPreference_v3();
//        sku= new SKU_v3();
//        urgency=new Urgency_v3();

        id = in.readString();
        product = in.readString();
        product_sku = in.readString();
        grade = in.readString();
        brand = in.readString();
        avlqty = in.readString();
        status = in.readString();
        remarks = in.readString();
        purchasePrice = in.readString();
        margin_strategy_id = in.readString();
        margin_strategy_name = in.readString();
        categoryId = in.readString();
        uom = in.readString();
        longDescription = in.readString();
        categoryName = in.readString();
        qty = in.readString();
        in.readStringList(targetStateCodes);
        startDate = in.readString();
        endDate = in.readString();
        startTimestamp = in.readString();
        endTimestamp = in.readString();
        createdBy = in.readString();
        locationType = in.readString();
        locationValue = in.readString();
        in.readTypedList(priceSpace, DealItemPrice.CREATOR);
        canned = in.readString();
        taxationPref = in.readParcelable(TaxationPreference_v3.class.getClassLoader());
        freight = in.readString();
        sku = in.readParcelable(SKU_v3.class.getClassLoader());
        urgency = in.readParcelable(Urgency_v3.class.getClassLoader());
        in.readStringList(targetStateNames);
        createdOn = in.readString();
        formC = in.readString();
    }
    @Override
    public int describeContents()
    {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(id);
        dest.writeString(product);
        dest.writeString(product_sku);
        dest.writeString(grade);
        dest.writeString(brand);
        dest.writeString(avlqty);
        dest.writeString(status);
        dest.writeString(remarks);
        dest.writeString(purchasePrice);
        dest.writeString(margin_strategy_id);
        dest.writeString(margin_strategy_name);
        dest.writeString(categoryId);
        dest.writeString(uom);
        dest.writeString(longDescription);
        dest.writeString(categoryName);
        dest.writeString(qty);
        dest.writeStringList(targetStateCodes);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeString(startTimestamp);
        dest.writeString(endTimestamp);
        dest.writeString(createdBy);
        dest.writeString(locationType);
        dest.writeString(locationValue);
        dest.writeTypedList(priceSpace);
        dest.writeString(canned);
        dest.writeParcelable(taxationPref, flags);
        dest.writeString(freight);
        dest.writeParcelable(sku, flags);
        dest.writeParcelable(urgency, flags);
        dest.writeStringList(targetStateNames);
        dest.writeString(createdOn);
        dest.writeString(formC);
    }
    public static final Parcelable.Creator<Deal_v3> CREATOR = new Parcelable.Creator<Deal_v3>()
    {
        public Deal_v3 createFromParcel(Parcel in)
        {
            return new Deal_v3(in);
        }

        public Deal_v3[] newArray (int size)
        {
            return new Deal_v3[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProduct_sku() {
        return product_sku;
    }

    public void setProduct_sku(String product_sku) {
        this.product_sku = product_sku;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getAvlqty() {
        return avlqty;
    }

    public void setAvlqty(String avlqty) {
        this.avlqty = avlqty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getMargin_strategy_id() {
        return margin_strategy_id;
    }

    public void setMargin_strategy_id(String margin_strategy_id) {
        this.margin_strategy_id = margin_strategy_id;
    }

    public String getMargin_strategy_name() {
        return margin_strategy_name;
    }

    public void setMargin_strategy_name(String margin_strategy_name) {
        this.margin_strategy_name = margin_strategy_name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getUom() {
        return uom!=null?uom:"";
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public ArrayList<String> getTargetStateCodes() {
        return targetStateCodes;
    }

    public void setTargetStateCodes(ArrayList<String> targetStateCodes) {
        this.targetStateCodes = targetStateCodes;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(String startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public String getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(String endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLocationValue() {
        return locationValue;
    }

    public void setLocationValue(String locationValue) {
        this.locationValue = locationValue;
    }

    public List<DealItemPrice> getPriceSpace() {
        return priceSpace;
    }

    public void setPriceSpace(List<DealItemPrice> priceSpace) {
        this.priceSpace = priceSpace;
    }

    public String getCanned() {
        return canned;
    }

    public void setCanned(String canned) {
        this.canned = canned;
    }

    public TaxationPreference_v3 getTaxationPref() {
        return taxationPref;
    }

    public void setTaxationPref(TaxationPreference_v3 taxationPref) {
        this.taxationPref = taxationPref;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public SKU_v3 getSku() {
        return sku;
    }

    public void setSku(SKU_v3 sku) {
        this.sku = sku;
    }

    public Urgency_v3 getUrgency() {
        return urgency;
    }

    public void setUrgency(Urgency_v3 urgency) {
        this.urgency = urgency;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getFormC() {
        return formC;
    }

    public void setFormC(String formC) {
        this.formC = formC;
    }

    public ArrayList<String> getTargetStateNames() {
        return targetStateNames;
    }

    public void setTargetStateNames(ArrayList<String> targetStateNames) {
        this.targetStateNames = targetStateNames;
    }
}
