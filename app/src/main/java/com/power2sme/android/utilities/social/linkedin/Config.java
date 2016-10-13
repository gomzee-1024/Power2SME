package com.power2sme.android.utilities.social.linkedin;

/**
 * Created by tausif on 22/4/15.
 */
public class Config
{
    public static String LINKEDIN_CONSUMER_KEY = "75j15qyhls73bo";
    public static String LINKEDIN_CONSUMER_SECRET = "DzqIcur8T0UctcI9";
    public static String scopeParams = "r_basicprofile+r_emailaddress";

    public static String OAUTH_CALLBACK_SCHEME = "x-oauthflow-linkedin";
    public static String OAUTH_CALLBACK_HOST = "callback";
    public static String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;
}
