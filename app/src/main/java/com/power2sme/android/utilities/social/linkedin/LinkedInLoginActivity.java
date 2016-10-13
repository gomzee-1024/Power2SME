package com.power2sme.android.utilities.social.linkedin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.utilities.logging.ACLogger;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tausif on 21/5/15.
 */
public class LinkedInLoginActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkedin_login);

        getUserProfileData(this, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode==Activity.RESULT_CANCELED)
        {
            finish();
        }

        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
    }


    public void login(final Activity activity)
    {
        List<String> scope = new ArrayList<String>();
        scope.add("r_basicprofile");
        scope.add("r_emailaddress");

        LISessionManager.getInstance(activity.getApplicationContext()).init(activity, buildScope(), new AuthListener()
        {
            @Override
            public void onAuthSuccess()
            {
                getUserProfileData(activity, false);
            }

            @Override
            public void onAuthError(LIAuthError error)
            {
                ACLogger.log("error : " + error);
            }
        }, true);
    }
    private Scope buildScope()
    {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
    }

    public void getUserProfileData(final Activity activity, final boolean goForLogin)
    {
        String url = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,email-address)";

        APIHelper apiHelper = APIHelper.getInstance(activity.getApplicationContext());
        apiHelper.getRequest(activity, url, new ApiListener()
        {
            @Override
            public void onApiSuccess(ApiResponse apiResponse)
            {
                ACLogger.log("apiResponse: " + apiResponse.getResponseDataAsString());

                try
                {
                    JSONObject jsonObject = apiResponse.getResponseDataAsJson();
                    Intent intent = new Intent();
                    intent.putExtra(Constants.PREFERENCE_CUSTOMER_EMAIL, jsonObject.getString("emailAddress"));

                    String fullName = jsonObject.getString("firstName")!=null && jsonObject.getString("firstName").length()>0?jsonObject.getString("firstName"):"";
                    fullName = fullName+jsonObject.getString("lastName")!=null && jsonObject.getString("lastName").length()>0?" "+jsonObject.getString("lastName"):"";
                    intent.putExtra(Constants.PREFERENCE_CUSTOMER_FULLNAME, fullName);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onApiError(LIApiError liApiError)
            {
                ACLogger.log("liApiError: " + liApiError);
                if (goForLogin)
                {
                    login(activity);
                }
            }
        });
    }
}
