package com.power2sme.android.utilities.customviews;

import android.text.InputFilter;
import android.text.Spanned;

import com.power2sme.android.utilities.logging.ACLogger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by power2sme on 03/02/16.
 */
public class RegexInputFilter implements InputFilter
{
    private Pattern mPattern;
    public RegexInputFilter(String regext)
    {
        mPattern = Pattern.compile(regext);
    }
    @Override
    public CharSequence filter(
                            CharSequence source,
                            int sourceStart,
                            int sourceEnd,
                            Spanned destination,
                            int destinationStart,
                            int destinationEnd
                            )
    {

        Matcher matcher = mPattern.matcher(source.toString()+destination.toString());
        if(matcher.matches())
        {
            ACLogger.log("VALID = source="+source+", destination="+destination);
            return null;
        }
        ACLogger.log("INVALID = source="+source+", destination="+destination);
        return destination;
    }
}
