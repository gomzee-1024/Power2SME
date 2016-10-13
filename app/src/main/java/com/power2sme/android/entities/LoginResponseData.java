package com.power2sme.android.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.v3.CustomerLogin_v3;
import com.power2sme.android.entities.v3.Employee_v3;
import com.power2sme.android.utilities.Utils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponseData implements Parcelable
{
    @JsonProperty("object_type_id")
    private String object_type_id;
    @JsonProperty("employee")
    private Employee_v3 employee;
    @JsonProperty("customerLogin")
    private CustomerLogin_v3 customerLogin;


    public LoginResponseData(){}
    public LoginResponseData(Parcel in)
    {
        object_type_id = in.readString();
        employee = in.readParcelable(Employee_v3.class.getClassLoader());
        customerLogin = in.readParcelable(CustomerLogin_v3.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(object_type_id);
        dest.writeParcelable(employee, flags);
        dest.writeParcelable(customerLogin, flags);
    }

    public static final Creator<LoginResponseData> CREATOR = new Creator<LoginResponseData>()
    {
        public LoginResponseData createFromParcel(Parcel in)
        {
            return new LoginResponseData(in);
        }

        public LoginResponseData[] newArray (int size)
        {
            return new LoginResponseData[size];
        }
    };

    public String getObject_type_id() {
        return Utils.checkStringForNull(object_type_id);
    }

    public void setObject_type_id(String object_type_id) {
        this.object_type_id = object_type_id;
    }

    public Employee_v3 getEmployee() {
        return employee;
    }

    public void setEmployee(Employee_v3 employee) {
        this.employee = employee;
    }

    public CustomerLogin_v3 getCustomerLogin() {
        return customerLogin;
    }

    public void setCustomerLogin(CustomerLogin_v3 customerLogin) {
        this.customerLogin = customerLogin;
    }
}
