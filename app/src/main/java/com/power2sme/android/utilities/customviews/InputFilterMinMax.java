package com.power2sme.android.utilities.customviews;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterMinMax implements InputFilter 
{
    private int maxRange;
    public InputFilterMinMax(int maxRange) 
    {
        this.maxRange = maxRange;
    }
    public InputFilterMinMax(String maxRange) 
    {
        this.maxRange = Integer.parseInt(maxRange);
    }
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) 
    {   
//    	ACLogger.log("==source:"+source+", start:"+start+", end:"+end+", dest:"+dest+", dstart:"+dstart+", dend"+dend);
        try 
        {
            if(dend < maxRange)
            {
            	return source;
            }
        } 
        catch (NumberFormatException nfe)
        { 
        	
        }     
        return "";
    }

    private boolean isInRange(int min, int max, int input) 
    {
        return max > min ? (input >= min && input <= max) : (input >= max && input <= min);
    }
}