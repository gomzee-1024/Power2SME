package com.power2sme.android.dataprovider;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.power2sme.android.dataprovider.database.SQLiteDataProvider;
import com.power2sme.android.dataprovider.network.NetworkDataProvider;
import com.power2sme.android.dataprovider.network.NetworkUtils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACErrorCodes;

public class DataProviderFactory 
{
	public static FactoryResponse getDataProvider(Fragment fragment, DataProviderTypes dataProviderTypes)
	{
		return getDataProvider(fragment.getActivity(), dataProviderTypes);
	}
	
	public static FactoryResponse getDataProvider(Context context, DataProviderTypes dataProviderTypes)
	{
		switch(dataProviderTypes)
		{
			case NETWORK:
			{
				return getNetworkDataProvider(context);
			}
			case DATABASE:
			{
				return new FactoryResponse(getSQLiteDataProvider(context), null);
			}
		};
		return null;
	}
	
	private static FactoryResponse getNetworkDataProvider(Context context)
	{
		if(NetworkUtils.isNetworkAvailable(context))
		{
			return new FactoryResponse(NetworkDataProvider.getInstance(context), null);
		}
		else
		{
			return new FactoryResponse(NetworkDataProvider.getInstance(context), new ACError(ACErrorCodes.NETWORK_NOTAVAILABLE_ERROR, "Notwork not available"));
		}
	}
	
	private static SQLiteDataProvider getSQLiteDataProvider(Context context)
	{
		return SQLiteDataProvider.getInstance(context);
	}
}
