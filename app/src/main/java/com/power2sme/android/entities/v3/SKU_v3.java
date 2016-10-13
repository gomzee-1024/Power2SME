package com.power2sme.android.entities.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by tausif on 13/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SKU_v3 implements Parcelable
{
    @JsonProperty("category")
    private String category;
    @JsonProperty("subcategory")
    private String subcategory;
    @JsonProperty("longDescription")
    private String longDescription;
    @JsonProperty("grade")
    private String grade;
    @JsonProperty("brand")
    private String brand;
    @JsonProperty("otherProperties")
    private OtherProperties_v3 otherProperties;
    @JsonProperty("skucode")
    private String skucode;

    public SKU_v3(){}
    public SKU_v3(Parcel in)
    {
//        otherProperties = new OtherProperties_v3();

        skucode = in.readString();
        category = in.readString();
        subcategory = in.readString();
        brand = in.readString();
        grade = in.readString();
        otherProperties = in.readParcelable(OtherProperties_v3.class.getClassLoader());
        longDescription = in.readString();
    }
    @Override
    public int describeContents()
    {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(skucode);
        dest.writeString(category);
        dest.writeString(subcategory);
        dest.writeString(brand);
        dest.writeString(grade);
        dest.writeParcelable(otherProperties, flags);
        dest.writeString(longDescription);
    }
    public static final Parcelable.Creator<SKU_v3> CREATOR = new Parcelable.Creator<SKU_v3>()
    {
        public SKU_v3 createFromParcel(Parcel in)
        {
            return new SKU_v3(in);
        }

        public SKU_v3[] newArray (int size)
        {
            return new SKU_v3[size];
        }
    };

    @Override
    public String toString()
    {
        return longDescription;
    }

    public String getSkucode() {
        return skucode;
    }

    public void setSkucode(String skucode) {
        this.skucode = skucode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public OtherProperties_v3 getOtherProperties() {
        return otherProperties;
    }

    public void setOtherProperties(OtherProperties_v3 otherProperties) {
        this.otherProperties = otherProperties;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
}
