package com.power2sme.android.dataprovider.network;

import org.apache.http.cookie.Cookie;

import java.io.Serializable;

public class SerializedCookie implements Serializable {
	 
    private static final long serialVersionUID = 5327445113190674523L; //arbitrary
 
    private String name;
    private String value;
    private String domain;
     
    public SerializedCookie(Cookie cookie){
        this.name = cookie.getName();
        this.value = cookie.getValue();
        this.domain = cookie.getDomain();
    }
     
    public String getName(){
        return name;
    }
     
    public String getValue(){
        return value;
    }
    public String getDomain(){
        return domain;
    }
    @Override
    public String toString() 
    {
    	return name+" : "+value+" : "+domain;
    }
}
