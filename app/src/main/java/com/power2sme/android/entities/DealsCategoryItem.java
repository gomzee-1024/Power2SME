package com.power2sme.android.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DealsCategoryItem 
{
	@JsonProperty("categoryDrawable")
	private int categoryDrawable;
	@JsonProperty("categoryTitle")
	private String categoryTitle;
	
	public DealsCategoryItem(int categoryDrawable, String categoryTitle)
	{
		this.categoryDrawable=categoryDrawable;
		this.categoryTitle=categoryTitle;
	}
	/**
	 * @return the categoryDrawable
	 */
	public int getCategoryDrawable() {
		return categoryDrawable;
	}
	/**
	 * @param categoryDrawable the categoryDrawable to set
	 */
	public void setCategoryDrawable(int categoryDrawable) {
		this.categoryDrawable = categoryDrawable;
	}
	/**
	 * @return the categoryTitle
	 */
	public String getCategoryTitle() {
		return Utils.checkStringForNull(categoryTitle);
	}
	/**
	 * @param categoryTitle the categoryTitle to set
	 */
	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}
	
	
}
