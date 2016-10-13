package com.power2sme.android.entities.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by tausif on 27/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee_v3 implements Parcelable
{
    @JsonProperty("id")
    private String id;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("userHash")
    private String userHash;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("reportsToId")
    private String reportsToId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("email")
    private String email;
    @JsonProperty("department")
    private String department;
    @JsonProperty("sessionId")
    private String sessionId;

    public Employee_v3(){}
    public Employee_v3(Parcel in)
    {
        id = in.readString();
        userName = in.readString();
        userHash = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        reportsToId = in.readString();
        title = in.readString();
        mobile = in.readString();
        email = in.readString();
        department = in.readString();
        sessionId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userName);
        dest.writeString(userHash);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(reportsToId);
        dest.writeString(title);
        dest.writeString(mobile);
        dest.writeString(email);
        dest.writeString(department);
        dest.writeString(sessionId);
    }
    public static final Creator<Employee_v3> CREATOR = new Creator<Employee_v3>()
    {
        public Employee_v3 createFromParcel(Parcel in)
        {
            return new Employee_v3(in);
        }

        public Employee_v3[] newArray (int size)
        {
            return new Employee_v3[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHash() {
        return userHash;
    }

    public void setUserHash(String userHash) {
        this.userHash = userHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getReportsToId() {
        return reportsToId;
    }

    public void setReportsToId(String reportsToId) {
        this.reportsToId = reportsToId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
