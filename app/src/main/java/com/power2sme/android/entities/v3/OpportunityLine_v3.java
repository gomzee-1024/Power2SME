package com.power2sme.android.entities.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by tausif on 13/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpportunityLine_v3 implements Parcelable
{
    @JsonProperty("id")
    private String id;
    @JsonProperty("item_seq_id")
    private String item_seq_id;
    @JsonProperty("potential_id")
    private String potential_id;
    @JsonProperty("is_deleted")
    private String is_deleted;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("uom")
    private String uom;
    @JsonProperty("dispatch_from")
    private String dispatch_from;
    @JsonProperty("remarks")
    private String remarks;
    @JsonProperty("created_by")
    private String created_by;
    @JsonProperty("created_time")
    private String created_time;
    @JsonProperty("updated_time")
    private String updated_time;
    @JsonProperty("sku")
    private SKU_v3 sku;

    public OpportunityLine_v3(){}
    public OpportunityLine_v3(Parcel in)
    {
        id = in.readString();
        item_seq_id = in.readString();
        potential_id = in.readString();
        is_deleted = in.readString();
        quantity = in.readString();
        uom = in.readString();
        dispatch_from = in.readString();
        remarks = in.readString();
        created_by = in.readString();
        created_time = in.readString();
        updated_time = in.readString();
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
        dest.writeString(id);
        dest.writeString(item_seq_id);
        dest.writeString(potential_id);
        dest.writeString(is_deleted);
        dest.writeString(quantity);
        dest.writeString(uom);
        dest.writeString(dispatch_from);
        dest.writeString(remarks);
        dest.writeString(created_by);
        dest.writeString(created_time);
        dest.writeString(updated_time);
        dest.writeParcelable(sku, flags);
    }
    public static final Parcelable.Creator<OpportunityLine_v3> CREATOR = new Parcelable.Creator<OpportunityLine_v3>()
    {
        public OpportunityLine_v3 createFromParcel(Parcel in)
        {
            return new OpportunityLine_v3(in);
        }

        public OpportunityLine_v3[] newArray (int size)
        {
            return new OpportunityLine_v3[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem_seq_id() {
        return item_seq_id;
    }

    public void setItem_seq_id(String item_seq_id) {
        this.item_seq_id = item_seq_id;
    }

    public String getPotential_id() {
        return potential_id;
    }

    public void setPotential_id(String potential_id) {
        this.potential_id = potential_id;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getQuantity() {
        return quantity;
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

    public String getDispatch_from() {
        return dispatch_from;
    }

    public void setDispatch_from(String dispatch_from) {
        this.dispatch_from = dispatch_from;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    public SKU_v3 getSku() {
        return sku;
    }

    public void setSku(SKU_v3 sku) {
        this.sku = sku;
    }
}
