package com.power2sme.android;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

/**
 * Created by tausif on 9/7/15.
 */
public class TestUtilits
{
    public static int getResourceId(String s)
    {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        String packageName = targetContext.getPackageName();
        return targetContext.getResources().getIdentifier(s, "id", packageName);
    }
}
