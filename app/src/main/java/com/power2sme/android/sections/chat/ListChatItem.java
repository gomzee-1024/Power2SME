package com.power2sme.android.sections.chat;

/**
 * Created by sysadmin on 15/9/16.
 */
public class ListChatItem {

    private String message;
    private String[] list;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setList(String[] list) {
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public String[] getList() {
        return list;
    }
}
