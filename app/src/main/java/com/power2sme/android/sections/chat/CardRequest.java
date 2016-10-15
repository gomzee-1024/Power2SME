package com.power2sme.android.sections.chat;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sysadmin on 12/10/16.
 */
public class CardRequest {
    private LinkedHashSet<String> skucodeList;
    private int noOfCardsToDisplay;
    private int pincode;
    private int creditDays;
    private double quantity;
    private String smeid;

    public int getPincode() {
        return pincode;
    }
    public void setPincode(int pincode) {
        this.pincode = pincode;
    }
    public int getCreditDays() {
        return creditDays;
    }
    public void setCreditDays(int creditDays) {
        this.creditDays = creditDays;
    }
    public double getQuantity() {
        return quantity;
    }
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    public String getSmeid() {
        return smeid;
    }
    public void setSmeid(String smeid) {
        this.smeid = smeid;
    }
    public int getNoOfCardsToDisplay() {
        return noOfCardsToDisplay;
    }
    public void setNoOfCardsToDisplay(int noOfCardsToDisplay) {
        this.noOfCardsToDisplay = noOfCardsToDisplay;
    }
    public LinkedHashSet<String> getSkucodeList() {
        return skucodeList;
    }
    public void setSkucodeList(LinkedHashSet<String> skucodeList) {
        this.skucodeList = skucodeList;
    }
}
