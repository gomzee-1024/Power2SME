package com.power2sme.android.dataprovider.network;

public enum MethodTypes
{
	GET(0),POST(1),MULTIPART(3);
	int value;
	private MethodTypes(int value)
	{
		this.value=value;
	}

	@Override
	public String toString()
	{
		if(value==0)
			return "GET";
		else if(value==1)
			return "POST";
		else if(value==1)
			return "MULTIPART";
		else
			return "UNDEFINED";
	}
}
