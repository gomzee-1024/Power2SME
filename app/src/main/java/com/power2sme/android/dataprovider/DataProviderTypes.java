package com.power2sme.android.dataprovider;

public enum DataProviderTypes
{
	NETWORK(0),DATABASE(1);
	private final int value;
	private DataProviderTypes(final int value)
	{
		this.value=value;
	}
}
