package com.power2sme.android.sections.chat;

/**
 * Created by sysadmin on 16/9/16.
 */
public class ProductListItem {
    private String product_name;
    private String prices;
    private String sku_code;
    private String locations;
    private DetectedProduct prod;

    public void setPrices(String prices) {
        this.prices = prices;
    }
    public DetectedProduct getProd() {
        return prod;
    }

    public void setProd(DetectedProduct prod) {
        this.prod = prod;
    }

    public void setSku_code(String sku_code) {
        this.sku_code = sku_code;
    }

    public String getSku_code() {
        return sku_code;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getLocations() {
        return locations;
    }

    public String getPrices() {
        return prices;
    }

    public void makePriceStatement(String price){
        prices = "Rs "+price;
    }
}
