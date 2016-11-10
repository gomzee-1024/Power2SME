package com.power2sme.android.sections.chat;

/**
 * Created by sysadmin on 6/10/16.
 */
public class RequestDto {
    private String input;
    private String category;
    private String subcategory;
    private String brand;
    private boolean credit;
    private boolean advancestage;
    private boolean numans;
    private boolean pin_ans;
    private boolean qtyans;
    private boolean creditans;
    private String smeid;
    private String email;
    private String phone;
    private int chatid;
    private CardRequest cardRequest;

    public boolean isCreditans() {
        return creditans;
    }

    public void setCreditans(boolean creditans) {
        this.creditans = creditans;
    }
    public CardRequest getCardRequest() {
        return cardRequest;
    }
    public void setCardRequest(CardRequest cardRequest) {
        this.cardRequest = cardRequest;
    }
    public int getChatid() {
        return chatid;
    }
    public void setChatid(int chatid) {
        this.chatid = chatid;
    }

    public String getSmeid() {
        return smeid;
    }
    public void setSmeid(String smeid) {
        this.smeid = smeid;
    }

    public String getInput() {
        return input;
    }
    public void setInput(String input) {
        this.input = input;
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
    public boolean isCredit() {
        return credit;
    }
    public void setCredit(boolean credit) {
        this.credit = credit;
    }
    public boolean isAdvancestage() {
        return advancestage;
    }
    public void setAdvancestage(boolean advancestage) {
        this.advancestage = advancestage;
    }
    public boolean isNumans() {
        return numans;
    }
    public void setNumans(boolean numans) {
        this.numans = numans;
    }
    public boolean isPin_ans() {
        return pin_ans;
    }
    public void setPin_ans(boolean pin_ans) {
        this.pin_ans = pin_ans;
    }
    public boolean isQtyans() {
        return qtyans;
    }
    public void setQtyans(boolean qtyans) {
        this.qtyans = qtyans;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
