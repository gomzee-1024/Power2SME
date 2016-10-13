package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.v3.Customer;

import java.util.ArrayList;

/**
 * Created by tausif on 15/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseCustomersDto extends ResponseBaseDTO
{
    @JsonProperty("Data")
    ArrayList<Customer> customers;

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }
}