package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.MaterialCategory;

import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseMaterialCategoryListDto  extends ResponseBaseDTO
{
	@JsonProperty("Data")
	private ArrayList<MaterialCategory> data;

	/**
	 * @return the data
	 */
	public List<MaterialCategory> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(ArrayList<MaterialCategory> data) {
		this.data = data;
	}


	public ArrayList<String> getCategories()
	{
		ArrayList<String> tmpCatList = new ArrayList<String>();
		if(data!=null)
		{
			for(int i=0;i<data.size();i++)
			{
				tmpCatList.add(data.get(i).getItemCategoryName());
			}
		}
		return tmpCatList;
	}
	
}
