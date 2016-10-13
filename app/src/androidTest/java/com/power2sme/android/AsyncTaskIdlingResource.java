package com.power2sme.android;

import android.content.Context;
import android.support.test.espresso.IdlingResource;

import com.power2sme.android.dataprovider.AsyncTaskQueue;

/**
 * Created by tausif on 29/6/15.
 */
public class AsyncTaskIdlingResource implements IdlingResource
{
    private final Context context;
    IdlingResource.ResourceCallback callback;

    public AsyncTaskIdlingResource(Context context)
    {
        this.context=context;
    }

    @Override
    public String getName()
    {
        return AsyncTaskIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow()
    {
        boolean isIdle = AsyncTaskQueue.getInstance().isEmpty();
        if (isIdle && callback != null) {
            callback.onTransitionToIdle();
        }
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(IdlingResource.ResourceCallback callback) {
        this.callback = callback;
    }
}
