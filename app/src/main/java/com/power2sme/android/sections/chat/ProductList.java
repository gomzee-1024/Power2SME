package com.power2sme.android.sections.chat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sysadmin on 16/9/16.
 */
public class ProductList {
    ArrayList<ProductListItem> list;

    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public ProductList(ArrayList<ProductListItem> list){
        this.list = list;
    }

    public List<ProductListItem> getList() {
        return list;
    }
}
