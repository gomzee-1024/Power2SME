package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseSKUCategoryFetchDto extends ResponseBaseDTO
{
	@JsonProperty("Data")
	private ArrayList<String> categories;
	public ArrayList<String> getCategories()
	{
		return categories;
	}
	public void setCategories(ArrayList<String> categories) {
		this.categories = categories;
	}
}
