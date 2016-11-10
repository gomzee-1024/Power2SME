package com.power2sme.android.sections.chat;

import android.util.Base64;
import android.util.Log;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.power2sme.android.MyAccountApplication;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sysadmin on 9/8/16.
 */
public class ApiRequest {

    public void sendJsonRequest(String url,String payload ,final SyntaxnetCallback callback) {
        String tag_json_obj = "json_obj_req";
        JSONObject request=null;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url,payload,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("response", "Error: " + error.getMessage());
                        callback.onError(error);

                    }
                }
        ){
            @Override
            public String getBodyContentType() {
                return "application/json";
            }


            /* @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d("response","got headers");
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }*/
        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(90*1000,0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        MyAccountApplication.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    public void sendskuapirequest(String url, final SkuApiCallback callback){
        String tag_json_obj = "json_obj_req";
        JSONObject nullStr=null;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                url,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("responce", response.toString());
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("responce", "Error: " + error.getMessage());
                Log.d("response", "onErrorResponse: "+ error.getCause());
                callback.onError(error);
                // hide the progress dialog
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d("response","got headers");
                HashMap<String, String> headers = new HashMap<String, String>();
                String creds = String.format("%s:%s","admin","admin");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(90*1000,0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyAccountApplication.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    public void sendAddRfqRequest(String url,JSONObject payload, final SkuApiCallback callback){
        String tag_json_obj = "json_obj_req";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, payload,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("responce", response.toString());
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d("responce", "Error: " + error.getMessage());
                // hide the progress dialog
                Log.d("response", "onErrorResponse: "+ error.networkResponse.statusCode);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                String creds = String.format("%s:%s","admin","admin");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(90*1000,0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyAccountApplication.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    public interface SyntaxnetCallback{
        public void onSuccess(JSONObject result);
        public void onError(VolleyError error);
    }

    public interface SkuApiCallback{
        public void onSuccess(JSONObject result);
        public void onError(VolleyError error);
    }
}
