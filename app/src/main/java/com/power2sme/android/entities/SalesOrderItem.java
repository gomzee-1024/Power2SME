package com.power2sme.android.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.v3.SKU_v3;
import com.power2sme.android.utilities.Utils;
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesOrderItem implements Parcelable
{
    @JsonProperty("subcat3")
	private String subcat3;
    @JsonProperty("subcat2")
    private String subcat2;
    @JsonProperty("itemCat")
    private String itemCat;
    @JsonProperty("itemOutstandingAmt")
    private String itemOutstandingAmt;
    @JsonProperty("subcat4")
    private String subcat4;
    @JsonProperty("docNo")
    private String docNo;
    @JsonProperty("uom")
    private String uom;
    @JsonProperty("subcat1")
    private String subcat1;
    @JsonProperty("description")
    private String description;
    @JsonProperty("subCat")
    private String subCat;
    @JsonProperty("qty")
    private String qty;
    @JsonProperty("itemAmt")
    private String itemAmt;
    @JsonProperty("itemCode")
    private String itemCode;
    @JsonProperty("rate")
    private String rate;
    @JsonProperty("itemCatName")
    private String itemCatName;
    @JsonProperty("sku")
    private SKU_v3 sku;

    public SalesOrderItem(){}
	public SalesOrderItem(Parcel in)
	{
        subcat3 = in.readString();
        subcat2 = in.readString();
        itemCat = in.readString();
        itemOutstandingAmt = in.readString();
        subcat4 = in.readString();
        docNo = in.readString();
        uom = in.readString();
        subcat1 = in.readString();
        description = in.readString();
        subCat = in.readString();
        qty = in.readString();
        itemAmt = in.readString();
        itemCode = in.readString();
        rate = in.readString();
        itemCatName = in.readString();
        sku = in.readParcelable(SKU_v3.class.getClassLoader());
	}
	@Override
	public int describeContents()
	{
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
        dest.writeString(subcat3);
        dest.writeString(subcat2);
        dest.writeString(itemCat);
        dest.writeString(itemOutstandingAmt);
        dest.writeString(subcat4);
        dest.writeString(docNo);
        dest.writeString(uom);
        dest.writeString(subcat1);
        dest.writeString(description);
        dest.writeString(subCat);
        dest.writeString(qty);
        dest.writeString(itemAmt);
        dest.writeString(itemCode);
        dest.writeString(rate);
        dest.writeString(itemCatName);
        dest.writeParcelable(sku, flags);
    }

    public static final Creator<SalesOrderItem> CREATOR = new Creator<SalesOrderItem>()
	{
		public SalesOrderItem createFromParcel(Parcel in)
		{
			return new SalesOrderItem(in);
		}

		public SalesOrderItem[] newArray (int size)
		{
			return new SalesOrderItem[size];
		}
	};


    public String getItemCatName() {
        return Utils.checkStringForNull(itemCatName);
    }

    public void setItemCatName(String itemCatName) {
        this.itemCatName = itemCatName;
    }

    public String getItemRate() {
        return Utils.getCommaSeparatedNumber(rate);
    }

    public void setItemRate(String itemRate) {
        this.rate = itemRate;
    }

    public String getSubcat3() {
        return Utils.checkStringForNull(subcat3);
    }

    public void setSubcat3(String subcat3) {
        this.subcat3 = subcat3;
    }

    public String getSubcat2() {
        return Utils.checkStringForNull(subcat2);
    }

    public void setSubcat2(String subcat2) {
        this.subcat2 = subcat2;
    }

    public String getItemCat() {
        return Utils.checkStringForNull(itemCat);
    }

    public void setItemCat(String itemCat) {
        this.itemCat = itemCat;
    }

    public String getItemOutstandingAmt() {
        return Utils.checkStringForNull(itemOutstandingAmt);
    }

    public void setItemOutstandingAmt(String itemOutstandingAmt) {
        this.itemOutstandingAmt = itemOutstandingAmt;
    }

    public String getSubcat4() {
        return Utils.checkStringForNull(subcat4);
    }

    public void setSubcat4(String subcat4) {
        this.subcat4 = subcat4;
    }

    public String getDocNo() {
        return Utils.checkStringForNull(docNo);
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getUom() {
        return Utils.checkStringForNull(uom);
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getSubcat1() {
        return Utils.checkStringForNull(subcat1);
    }

    public void setSubcat1(String subcat1) {
        this.subcat1 = subcat1;
    }

    public String getDescription() {
        return Utils.checkStringForNull(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubCat() {
        return Utils.checkStringForNull(subCat);
    }

    public void setSubCat(String subCat) {
        this.subCat = subCat;
    }

    public String getQty() {
        return Utils.checkStringForNull(qty);
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getItemAmt() {
        return Utils.getCommaSeparatedNumber(itemAmt);
    }

    public void setItemAmt(String itemAmt) {
        this.itemAmt = itemAmt;
    }

    public String getItemCode() {
        return Utils.checkStringForNull(itemCode);
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public SKU_v3 getSku() {
        return sku;
    }

    public void setSku(SKU_v3 sku) {
        this.sku = sku;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    //	@JsonProperty("Description")
//	private String description;
//	@JsonProperty("DocumentNumber")
//	private String documentNumber;
//	@JsonProperty("ItemAmount")
//	private String ItemAmount;
//	@JsonProperty("ItemCategory")
//	private String itemCategory;
//	@JsonProperty("ItemCode")
//	private String ItemCode;
//	@JsonProperty("Quantity")
//	private String quantity;
//	@JsonProperty("UnitOfMeasure")
//	private String unitOfMeasure;
//	@JsonProperty("SubCategory")
//    private String subCategory;
//	@JsonProperty("SubCategory1")
//    private String subCategory1;
//	@JsonProperty("SubCategory2")
//    private String subCategory2;
//	@JsonProperty("SubCategory3")
//    private String subCategory3;
//	@JsonProperty("SubCategory4")
//    private String subCategory4;
//	@JsonProperty("Rate")
//    private String rate;
//
//
//	public SalesOrderItem(){}
//	public SalesOrderItem(Parcel in)
//	{
//		description=in.readString();
//		documentNumber=in.readString();
//		ItemAmount=in.readString();
//		itemCategory=in.readString();
//		quantity=in.readString();
//		unitOfMeasure=in.readString();
//	    subCategory=in.readString();
//	    subCategory1=in.readString();
//	    subCategory2=in.readString();
//	    subCategory3=in.readString();
//	    subCategory4=in.readString();
//	    rate=in.readString();
//	}
//	@Override
//	public int describeContents()
//	{
//		return 0;
//	}
//	@Override
//	public void writeToParcel(Parcel dest, int flags)
//	{
//		dest.writeString(description);
//		dest.writeString(documentNumber);
//		dest.writeString(ItemAmount);
//		dest.writeString(itemCategory);
//		dest.writeString(quantity);
//		dest.writeString(unitOfMeasure);
//		dest.writeString(subCategory);
//		dest.writeString(subCategory1);
//		dest.writeString(subCategory2);
//		dest.writeString(subCategory3);
//		dest.writeString(subCategory4);
//		dest.writeString(rate);
//	}
//	public static final Creator<SalesOrderItem> CREATOR = new Creator<SalesOrderItem>()
// 	{
// 		public SalesOrderItem createFromParcel(Parcel in)
// 		{
// 			return new SalesOrderItem(in);
// 		}
//
// 		public SalesOrderItem[] newArray (int size)
// 		{
// 			return new SalesOrderItem[size];
// 		}
// 	};
//
//	/**
//	 * @return the itemAmount
//	 */
//	public String getItemAmount()
//	{
//		return Utils.getCommaSeparatedNumber(ItemAmount);
//
////		try
////		{
////			if(ItemAmount!=null && ItemAmount.length()>0)
////				return Double.parseDouble(ItemAmount.trim());
////		}
////		catch(Exception ex)
////		{
////		}
////		return 0;
//	}
//	/**
//	 * @param itemAmount the itemAmount to set
//	 */
//	public void setItemAmount(String itemAmount) {
//		ItemAmount = itemAmount;
//	}
//	/**
//	 * @return the description
//	 */
//	public String getDescription() {
//		return description;
//	}
//	/**
//	 * @param description the description to set
//	 */
//	public void setDescription(String description) {
//		this.description = description;
//	}
//	/**
//	 * @return the documentNumber
//	 */
//	public String getDocumentNumber() {
//		return documentNumber;
//	}
//	/**
//	 * @param documentNumber the documentNumber to set
//	 */
//	public void setDocumentNumber(String documentNumber) {
//		this.documentNumber = documentNumber;
//	}
//	/**
//	 * @return the itemCategory
//	 */
//	public String getItemCategory() {
//		return itemCategory;
//	}
//	/**
//	 * @param itemCategory the itemCategory to set
//	 */
//	public void setItemCategory(String itemCategory) {
//		this.itemCategory = itemCategory;
//	}
//	/**
//	 * @return the quantity
//	 */
//	public String getQuantity()
//	{
//		try
//		{
//			float quantityVal = Float.parseFloat(quantity);
//			return String.valueOf(quantityVal);
//		}
//		catch(Exception ex)
//		{
//
//		}
//		return quantity;
//	}
//	/**
//	 * @param quantity the quantity to set
//	 */
//	public void setQuantity(String quantity) {
//		this.quantity = quantity;
//	}
//	/**
//	 * @return the subCategory
//	 */
//	public String getSubCategory() {
//		return subCategory;
//	}
//	/**
//	 * @param subCategory the subCategory to set
//	 */
//	public void setSubCategory(String subCategory) {
//		this.subCategory = subCategory;
//	}
//	/**
//	 * @return the subCategory1
//	 */
//	public String getSubCategory1() {
//		return subCategory1;
//	}
//	/**
//	 * @param subCategory1 the subCategory1 to set
//	 */
//	public void setSubCategory1(String subCategory1) {
//		this.subCategory1 = subCategory1;
//	}
//	/**
//	 * @return the subCategory2
//	 */
//	public String getSubCategory2() {
//		return subCategory2;
//	}
//	/**
//	 * @param subCategory2 the subCategory2 to set
//	 */
//	public void setSubCategory2(String subCategory2) {
//		this.subCategory2 = subCategory2;
//	}
//	/**
//	 * @return the subCategory3
//	 */
//	public String getSubCategory3() {
//		return subCategory3;
//	}
//	/**
//	 * @param subCategory3 the subCategory3 to set
//	 */
//	public void setSubCategory3(String subCategory3) {
//		this.subCategory3 = subCategory3;
//	}
//	/**
//	 * @return the subCategory4
//	 */
//	public String getSubCategory4() {
//		return subCategory4;
//	}
//	/**
//	 * @param subCategory4 the subCategory4 to set
//	 */
//	public void setSubCategory4(String subCategory4) {
//		this.subCategory4 = subCategory4;
//	}
//	/**
//	 * @return the unitOfMeasure
//	 */
//	public String getUnitOfMeasure() {
//		return unitOfMeasure;
//	}
//	/**
//	 * @param unitOfMeasure the unitOfMeasure to set
//	 */
//	public void setUnitOfMeasure(String unitOfMeasure) {
//		this.unitOfMeasure = unitOfMeasure;
//	}
//	/**
//	 * @return the itemCode
//	 */
//	public String getItemCode() {
//		return ItemCode;
//	}
//	/**
//	 * @param itemCode the itemCode to set
//	 */
//	public void setItemCode(String itemCode) {
//		ItemCode = itemCode;
//	}
//	public String getRate()
//	{
//		try
//		{
//			if(ItemAmount!=null && ItemAmount.length()>0 && quantity!=null && quantity.length()>0)
//			{
//				Double rfqTotalPrice = Double.parseDouble(ItemAmount);
//				Double rfqItemQuantity = Double.parseDouble(quantity);
//				double unitPrice = (double)rfqTotalPrice/rfqItemQuantity;
//				return Utils.getCommaSeparatedNumber(String.valueOf(unitPrice));
//			}
//		}
//		catch(Exception ex)
//		{
//			ex.printStackTrace();
//		}
//		return "";
//	}
//	public void setRate(String rate)
//	{
//		this.rate = rate;
//	}
//
//
}
