package com.power2sme.android.entities.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by tausif on 14/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RFQLineItem_v3 implements Parcelable
{
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("uom")
    private String uom;
    @JsonProperty("remarks")
    private String remarks;
    @JsonProperty("sku")
    private SKU_v3 sku;
    @JsonProperty("itemCategory")
    private String itemCategory;
    @JsonProperty("itemSubCategory1")
    private String itemSubCategory1;
    @JsonProperty("itemSubCategory2")
    private String itemSubCategory2;
    @JsonProperty("itemSubCategory3")
    private String itemSubCategory3;
    @JsonProperty("itemSubCategory4")
    private String itemSubCategory4;
    @JsonProperty("description")
    private String description;

    @JsonProperty("totalItemPrice")
    private String itemPrice;
    @JsonProperty("itemRate")
    private String unitPrice;


    public RFQLineItem_v3(){}
    public RFQLineItem_v3(Parcel in)
    {
        quantity = in.readString();
        uom = in.readString();
        remarks = in.readString();
        sku = in.readParcelable(SKU_v3.class.getClassLoader());
        itemCategory = in.readString();
        itemSubCategory1 = in.readString();
        itemSubCategory2 = in.readString();
        itemSubCategory3 = in.readString();
        itemSubCategory4 = in.readString();
        description = in.readString();
        itemPrice = in.readString();
        unitPrice = in.readString();
    }
    @Override
    public int describeContents()
    {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(quantity);
        dest.writeString(uom);
        dest.writeString(remarks);
        dest.writeParcelable(sku, flags);
        dest.writeString(itemCategory);
        dest.writeString(itemSubCategory1);
        dest.writeString(itemSubCategory2);
        dest.writeString(itemSubCategory3);
        dest.writeString(itemSubCategory4);
        dest.writeString(description);
        dest.writeString(itemPrice);
        dest.writeString(unitPrice);
    }
    public static final Creator<RFQLineItem_v3> CREATOR = new Creator<RFQLineItem_v3>()
    {
        public RFQLineItem_v3 createFromParcel(Parcel in)
        {
            return new RFQLineItem_v3(in);
        }

        public RFQLineItem_v3[] newArray (int size)
        {
            return new RFQLineItem_v3[size];
        }
    };

    public String getQuantity() {
        return quantity!=null?quantity:"0";
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public SKU_v3 getSku() {
        return sku;
    }

    public void setSku(SKU_v3 sku) {
        this.sku = sku;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemSubCategory1() {
        return itemSubCategory1;
    }

    public void setItemSubCategory1(String itemSubCategory1) {
        this.itemSubCategory1 = itemSubCategory1;
    }

    public String getItemSubCategory2() {
        return itemSubCategory2;
    }

    public void setItemSubCategory2(String itemSubCategory2) {
        this.itemSubCategory2 = itemSubCategory2;
    }

    public String getItemSubCategory3() {
        return itemSubCategory3;
    }

    public void setItemSubCategory3(String itemSubCategory3) {
        this.itemSubCategory3 = itemSubCategory3;
    }

    public String getItemSubCategory4() {
        return itemSubCategory4;
    }

    public void setItemSubCategory4(String itemSubCategory4) {
        this.itemSubCategory4 = itemSubCategory4;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemPrice() {
        return itemPrice!=null?itemPrice:"0.0";
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getUnitPrice() {
        return unitPrice!=null?unitPrice:"0.0";
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
}
