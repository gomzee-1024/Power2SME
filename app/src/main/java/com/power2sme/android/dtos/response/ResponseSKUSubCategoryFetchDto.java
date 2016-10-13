package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseSKUSubCategoryFetchDto extends ResponseBaseDTO
{
	@JsonProperty("Data")
	private ArrayList<String> subCategories;

	public ArrayList<String> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(ArrayList<String> subCategories) {
		this.subCategories = subCategories;
	}
}
