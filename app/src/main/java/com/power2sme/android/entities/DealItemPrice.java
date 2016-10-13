package com.power2sme.android.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DealItemPrice implements Parcelable
{
    @JsonProperty("markup")
    private String markup;

    @JsonProperty("minqty")
    private String minqty;

    @JsonProperty("ratevisibletocustomer")
    private String ratevisibletocustomer;

    @JsonProperty("maxqty")
    private String maxqty;

	public DealItemPrice(){}
	public DealItemPrice(Parcel in)
	{
        markup = in.readString();
        minqty = in.readString();
        ratevisibletocustomer = in.readString();
        maxqty = in.readString();
	}
	@Override
	public int describeContents()
	{
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
        dest.writeString(markup);
        dest.writeString(minqty);
        dest.writeString(ratevisibletocustomer);
        dest.writeString(maxqty);
	}
	public static final Creator<DealItemPrice> CREATOR = new Creator<DealItemPrice>()
	{
		public DealItemPrice createFromParcel(Parcel in)
		{
			return new DealItemPrice(in);
		}

		public DealItemPrice[] newArray (int size)
		{
			return new DealItemPrice[size];
		}
	};
    //////////////////////////////////////////////////////////////////


    public String getMarkup() {
        return Utils.checkStringForNull(markup);
    }

    public void setMarkup(String markup) {
        this.markup = markup;
    }

    public double getMinqty()
    {
        try
        {
            return Double.parseDouble(minqty);
        }
        catch(Exception ex){ex.printStackTrace();}
        return 0d;
    }

    public void setMinqty(String minqty) {
        this.minqty = minqty;
    }

    public String getRatevisibletocustomer() {
        return Utils.checkStringForNull(ratevisibletocustomer);
    }

    public void setRatevisibletocustomer(String ratevisibletocustomer) {
        this.ratevisibletocustomer = ratevisibletocustomer;
    }

    public double getMaxqty() {
        try
        {
            return Double.parseDouble(maxqty);
        }
        catch(Exception ex){ex.printStackTrace();}
        return 0d;
    }

    public void setMaxqty(String maxqty) {
        this.maxqty = maxqty;
    }
}
