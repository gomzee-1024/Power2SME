package com.power2sme.android.sections.chat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sysadmin on 16/9/16.
 */
public class ProductList {
    ArrayList<Object> list;

    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addToList(ProductListItem p ){
        list.add(p);
    }

    public ProductList(ArrayList<Object> list){
        this.list = list;
    }

    public List<Object> getList() {
        return list;
    }
}
