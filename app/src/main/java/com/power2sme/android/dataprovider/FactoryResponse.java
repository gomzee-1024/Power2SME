package com.power2sme.android.dataprovider;

import com.power2sme.android.utilities.logging.ACError;

/**
 * Created by power2sme on 25/08/15.
 */
public class FactoryResponse
{
    private IDataProvider iDataProvider;
    private ACError error;

    public FactoryResponse(IDataProvider iDataProvider, ACError error)
    {
        this.iDataProvider=iDataProvider;
        this.error=error;
    }

    public IDataProvider getiDataProvider() {
        return iDataProvider;
    }

    public void setiDataProvider(IDataProvider iDataProvider) {
        this.iDataProvider = iDataProvider;
    }

    public ACError getError() {
        return error;
    }

    public void setError(ACError error) {
        this.error = error;
    }
}
