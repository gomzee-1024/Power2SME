package com.power2sme.android.dataprovider.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.R;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACErrorCodes;
import com.power2sme.android.utilities.logging.ACLogger;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class NetworkUtils {
    private static int timeoutConnection = 10 * 60 * 1000; //10min
    private static int timeoutSocket = 10 * 60 * 1000; //10min

    private static Hashtable<String, String> defaultHeaders = null;
    private static onNetworkStateListener mOnNetworkStateListener = null;

    public interface onNetworkStateListener {
        public void onNetworkStateChange(Boolean state);
    }

    public static void setNetworkListener(onNetworkStateListener NetworkStateListener) {
        mOnNetworkStateListener = NetworkStateListener;
    }

    public static void removeNetworkListerner() {
        mOnNetworkStateListener = null;
    }

    public static void setNetworkState(Boolean state) {
        if (mOnNetworkStateListener != null) {
            mOnNetworkStateListener.onNetworkStateChange(state);
        }
    }


    public static boolean isNetworkAvailable(Context context) {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;
    }

    private static String getServerAPIKey(Context context) {
        //TODO hit server for retrive apiKey and expiry time and save at preference
//        initDefaultHeaders(context);
        return "";
    }

    private static String getAPIKey(Context context) {
        SharedPreferences sharedPreferences = ((MyAccountApplication) context.getApplicationContext()).getPrefs();
        String apiKey = sharedPreferences.getString("API-Key", null);
        if (apiKey == null) {
            return getServerAPIKey(context);
        } else {
            if (isServerKeyExpired(context)) {
                return getServerAPIKey(context);
            } else {
                return apiKey;
            }
        }
    }

    private static boolean isServerKeyExpired(Context context) {
        SharedPreferences sharedPreferences = ((MyAccountApplication) context.getApplicationContext()).getPrefs();
        long apiKeyExpiry = sharedPreferences.getLong("API-Key-Expiry", 0l);
        long apiKeyCreationTime = sharedPreferences.getLong("API-Key-Creation-Time", 0l);
        long currentTime = System.currentTimeMillis();
        if (apiKeyCreationTime != 0l && currentTime - apiKeyCreationTime >= apiKeyExpiry) {
            return true;
        }
        return false;
    }

    private static String getPower2SMEToken() {
//        TODO provide body of getPower2SMEToken()
        return "";
    }

    private static void initDefaultHeaders(Context context) {
        defaultHeaders = new Hashtable<String, String>();
        defaultHeaders.put("App-Version-Code", Utils.getAppVersionCode(context));
//        defaultHeaders.put("App-Version-Name",Utils.getAppVersionName(context));
        defaultHeaders.put("Platform", "Android");
        defaultHeaders.put("Device-ID", Utils.getDeviceId(context));
        defaultHeaders.put("API-Key", getAPIKey(context));
        defaultHeaders.put("x-power2sme-token", getPower2SMEToken());
        defaultHeaders.put("Date", "" + System.currentTimeMillis());
    }

    private static Hashtable<String, String> getDefaultHeaders(Context context) {
        if (isServerKeyExpired(context)) {
            defaultHeaders = null;
        }

        if (defaultHeaders == null)
            initDefaultHeaders(context);
        return defaultHeaders;
    }

//	private static DefaultHttpClient setKAMCookies(Context context, DefaultHttpClient httpClient)
//	{
//		try
//		{
//			FileInputStream fis = context.openFileInput("kam_cookie.ser");
//			ObjectInputStream ois = new ObjectInputStream(fis);
//			SerializedCookie serializedCookie = (SerializedCookie) ois.readObject();
//			ACLogger.log("KAM COOKIE = " + serializedCookie.getName()+", "+serializedCookie.getDomain()+", "+serializedCookie.getValue());
//			BasicClientCookie newCookie = new BasicClientCookie(serializedCookie.getName(),serializedCookie.getValue());
//			newCookie.setDomain(serializedCookie.getDomain());
//
//			CookieStore cookieStore = new BasicCookieStore();
//			cookieStore.addCookie(newCookie);
//			httpClient.setCookieStore(cookieStore);
//		}
//		catch(Exception ex)
//		{
//		}
//		return httpClient;
//	}

    private static DefaultHttpClient setCookies(Context context, DefaultHttpClient httpClient) {
        try {
            FileInputStream fis = context.openFileInput("cookie.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            SerializedCookie serializedCookie = (SerializedCookie) ois.readObject();
            ACLogger.log("COOKIE : SEND = " + serializedCookie.getName() + ", " + serializedCookie.getDomain() + ", " + serializedCookie.getValue());
            BasicClientCookie newCookie = new BasicClientCookie(serializedCookie.getName(), serializedCookie.getValue());
            newCookie.setDomain(serializedCookie.getDomain());

            CookieStore cookieStore = new BasicCookieStore();
            cookieStore.addCookie(newCookie);
            httpClient.setCookieStore(cookieStore);
        } catch (Exception ex) {
        }
        return httpClient;
    }

    public static void deleteCookie(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput("cookie.ser", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(null);
            oos.close();
            fos.close();
            ACLogger.log("COOKIE : Deleted");
        } catch (Exception ex) {
            ACLogger.log("COOKIE : Cookie Deletion failed : " + ex.getMessage());
        }
    }

    private static boolean saveCookie(Context context, DefaultHttpClient httpClient) {
        List<Cookie> cookies = httpClient.getCookieStore().getCookies();
        if (!cookies.isEmpty()) {
            Cookie sessionInfo = cookies.get(0);
            SerializedCookie serializedCookie = new SerializedCookie(sessionInfo);
            try {
                FileOutputStream fos = context.openFileOutput("cookie.ser", Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(serializedCookie);
                oos.close();
                fos.close();
                ACLogger.log("COOKIE : Received : " + serializedCookie);
                return true;
            } catch (Exception ex) {
                ACLogger.log("COOKIE : Received saved failed : " + ex.getMessage());
            }
        } else {
            return true;
        }
        return false;
    }

	/*public static void saveLastCookieAsKAMCookie(Context context)
    {
		try
		{
			FileInputStream fis = context.openFileInput("cookie.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			SerializedCookie serializedCookie = (SerializedCookie) ois.readObject();

			FileOutputStream fos = context.openFileOutput("kam_cookie.ser", Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(serializedCookie);
			oos.close();
			fos.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}*/

    private static HttpRequestBase setHeadersInPostRequest(Context context, HttpRequestBase httpRequestBase, Hashtable<String, String> headers) {
        httpRequestBase = _setHeaders(httpRequestBase, headers);
        Hashtable<String, String> defaultHeaders = getDefaultHeaders(context);
        httpRequestBase = _setHeaders(httpRequestBase, defaultHeaders);
        return httpRequestBase;
    }

    private static HttpRequestBase _setHeaders(HttpRequestBase httpRequestBase, Hashtable<String, String> headers) {
        if (headers != null) {
            Set<Entry<String, String>> entrySet = headers.entrySet();
            Iterator<Entry<String, String>> itr = entrySet.iterator();
            while (itr.hasNext()) {
                Entry<String, String> entry = itr.next();
                httpRequestBase.setHeader(entry.getKey(), entry.getValue());
            }
        }
        return httpRequestBase;
    }

    private static Header[] getHeaders(HttpRequestBase httpRequestBase) {
        return httpRequestBase.getAllHeaders();
    }

    private static void printHeaders(Header[] headers) {
        for (Header header : headers) {
            ACLogger.log("Header : " + header.getName() + "=>" + header.getValue());
        }
    }

    //	public static NetworkAsyncTaskResponse<String> getResponseWithKAMCookie(Context context, String postUrl, String requestJsonContent)
//	{
//		NetworkAsyncTaskResponse<String> response = new NetworkAsyncTaskResponse<String>();
//		try
//		{
//			ACLogger.log("URL = " + postUrl);
//			ACLogger.log("METHOD = " + postUrl);
//			ACLogger.log("PARAMS = "+requestJsonContent);
//
//			HttpResponse httpResponse=null;
//			StringEntity se = new StringEntity(requestJsonContent);
//			DefaultHttpClient httpClient = new DefaultHttpClient();
//
////			MyAccountApplication myAccountApplication = (MyAccountApplication)context.getApplicationContext();
////			SharedPreferences prefs = myAccountApplication.getPrefs();
////			boolean isKam = prefs.getBoolean(Constants.PREFERENCE_IS_KAM, false);
////			if(isKam)
////			{
//				httpClient = setKAMCookies(context, httpClient);
////			}
//
//			HttpPost httpPost = new HttpPost(postUrl);
//			httpPost = (HttpPost) setHeadersInPostRequest(context, httpPost, null);
//			System.err.println("reached");
//			httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY,new UsernamePasswordCredentials("admin","admin"));
//			HttpParams httpParameters = httpPost.getParams();
//			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
//			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
//			httpPost.setEntity(se);
//			httpPost.setHeader("Accept", "application/json");
//			httpPost.setHeader("Content-type", "application/json");
//
//			ACLogger.log("REQUEST HEADERS : ");
//			printHeaders(getHeaders(httpPost));
//
//			httpResponse = httpClient.execute(httpPost);
//
//			ACLogger.log("RESPONSE HEADERS : ");
//			printHeaders(httpResponse.getAllHeaders());
//
//			boolean isSuccess = saveCookie(context, httpClient);
//			if(!isSuccess)
//			{
//				httpResponse.getEntity().getContent().close();
//				ACError error=new ACError(ACErrorCodes.IO_EXCEPTION, context.getString(R.string.server_error));
//				response.setError(error);
//				return response;
//			}
//			if(httpResponse!=null)
//			{
//				StatusLine statusLine = httpResponse.getStatusLine();
//
//				ACLogger.log("RESPONSE STATUS = "+statusLine);
//
//				if(statusLine.getStatusCode() == HttpStatus.SC_OK)
//				{
//					ByteArrayOutputStream out = new ByteArrayOutputStream();
//					httpResponse.getEntity().writeTo(out);
//					out.close();
//					String serverResponse = out.toString();
//
//					ACLogger.log("RESPONSE = "+serverResponse);
//
//					response.setResultObject(serverResponse);
//				}
//				else if(statusLine.getStatusCode() == HttpStatus.SC_FORBIDDEN)
//				{
//					httpResponse.getEntity().getContent().close();
//					ACError error=new ACError(ACErrorCodes.API_ERRORCODE_FORBIDDEN, context.getString(R.string.server_error_access_forbidden));
//					response.setError(error);
//				}
//				else
//				{
//					httpResponse.getEntity().getContent().close();
//					ACError error=new ACError(ACErrorCodes.RESPONSE_NOT_FOUND_EXCEPTION, context.getString(R.string.server_error));
//					response.setError(error);
//				}
//			}
//			else
//			{
//				ACError error=new ACError(ACErrorCodes.RESPONSE_NOT_FOUND_EXCEPTION, context.getString(R.string.server_error));
//				response.setError(error);
//			}
//		}
//		catch (UnsupportedEncodingException e)
//		{
//			e.printStackTrace();
//			ACError error=new ACError(ACErrorCodes.UNSUPPORTED_ENCODING_EXCEPTION, context.getString(R.string.server_error));
//			response.setError(error);
//		}
//		catch (ClientProtocolException e)
//		{
//			e.printStackTrace();
//			ACError error=new ACError(ACErrorCodes.CLIENT_PROTOCOL_EXCEPTION, context.getString(R.string.server_error));
//			response.setError(error);
//		}
//		catch (SocketTimeoutException e)
//		{
//			e.printStackTrace();
//			ACError error=new ACError(ACErrorCodes.SOCKET_TIMEOUT_EXCEPTION, context.getString(R.string.server_error));
//			response.setError(error);
//		}
//		catch (ConnectTimeoutException e)
//		{
//			e.printStackTrace();
//			ACError error=new ACError(ACErrorCodes.CONNECTION_TIMEOUT_EXCEPTION, context.getString(R.string.server_error));
//			response.setError(error);
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//			ACError error=new ACError(ACErrorCodes.IO_EXCEPTION, context.getString(R.string.server_error));
//			response.setError(error);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			ACError error=new ACError(ACErrorCodes.IO_EXCEPTION, context.getString(R.string.server_error));
//			response.setError(error);
//		}
//
//		if(response.getError()!=null)
//		{
//			ACLogger.log("ERROR = "+response.getError().getErrorCodes()+" : "+response.getError().getMessage());
//		}
//
//		return response;
//	}
    public static NetworkAsyncTaskResponse<String> getResponse(Context context, String postUrl, String requestJsonContent) {
        NetworkAsyncTaskResponse<String> response = new NetworkAsyncTaskResponse<String>();
        try {
            ACLogger.log("URL = " + postUrl);
            ACLogger.log("METHOD = POST");
            ACLogger.log("PARAMS = " + requestJsonContent);

            if (!isNetworkAvailable(context)) {
                ACError error = new ACError(ACErrorCodes.NETWORK_NOTAVAILABLE_ERROR, context.getString(R.string.network_not_available));
                response.setError(error);
                return response;
            }

            HttpResponse httpResponse = null;
            StringEntity se = new StringEntity(requestJsonContent);
            DefaultHttpClient httpClient = new DefaultHttpClient();

//			MyAccountApplication myAccountApplication = (MyAccountApplication)context.getApplicationContext();
//			SharedPreferences prefs = myAccountApplication.getPrefs();
//			boolean isKam = prefs.getBoolean(Constants.PREFERENCE_IS_KAM, false);
//			if(isKam)
//			{
//				httpClient = setKAMCookies(context, httpClient);
//			}

            httpClient = setCookies(context, httpClient);
            HttpPost httpPost = new HttpPost(postUrl);
            httpPost = (HttpPost) setHeadersInPostRequest(context, httpPost, null);
            System.err.println("reached");
            httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("admin", "admin"));
            HttpParams httpParameters = httpPost.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            ACLogger.log("REQUEST HEADERS : ");
            printHeaders(getHeaders(httpPost));

            httpResponse = httpClient.execute(httpPost);

            ACLogger.log("RESPONSE HEADERS : ");
            printHeaders(httpResponse.getAllHeaders());

            boolean isSuccess = saveCookie(context, httpClient);
            if (!isSuccess) {
                httpResponse.getEntity().getContent().close();
                ACError error = new ACError(ACErrorCodes.IO_EXCEPTION, context.getString(R.string.server_error));
                response.setError(error);
                return response;
            }
            if (httpResponse != null) {
                StatusLine statusLine = httpResponse.getStatusLine();

                ACLogger.log("RESPONSE STATUS = " + statusLine);

                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    httpResponse.getEntity().writeTo(out);
                    out.close();
                    String serverResponse = out.toString();

                    ACLogger.log("RESPONSE = " + serverResponse);

                    response.setResultObject(serverResponse);
                } else if (statusLine.getStatusCode() == HttpStatus.SC_FORBIDDEN) {
                    httpResponse.getEntity().getContent().close();
                    ACError error = new ACError(ACErrorCodes.API_ERRORCODE_FORBIDDEN, context.getString(R.string.server_error_access_forbidden));
                    response.setError(error);
                }
                else
                {
                    if(httpResponse!=null && httpResponse.getEntity()!=null && httpResponse.getEntity().getContent()!=null)
                        httpResponse.getEntity().getContent().close();
                    ACError error = new ACError(ACErrorCodes.RESPONSE_NOT_FOUND_EXCEPTION, context.getString(R.string.server_error));
                    response.setError(error);
                }
            } else {
                ACError error = new ACError(ACErrorCodes.RESPONSE_NOT_FOUND_EXCEPTION, context.getString(R.string.server_error));
                response.setError(error);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ACError error = new ACError(ACErrorCodes.UNSUPPORTED_ENCODING_EXCEPTION, context.getString(R.string.server_error));
            response.setError(error);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            ACError error = new ACError(ACErrorCodes.CLIENT_PROTOCOL_EXCEPTION, context.getString(R.string.server_error));
            response.setError(error);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            ACError error = new ACError(ACErrorCodes.SOCKET_TIMEOUT_EXCEPTION, context.getString(R.string.server_error));
            response.setError(error);
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
            ACError error = new ACError(ACErrorCodes.CONNECTION_TIMEOUT_EXCEPTION, context.getString(R.string.server_error));
            response.setError(error);
        } catch (IOException e) {
            e.printStackTrace();
            ACError error = new ACError(ACErrorCodes.IO_EXCEPTION, context.getString(R.string.server_error));
            response.setError(error);
        } catch (Exception e) {
            e.printStackTrace();
            ACError error = new ACError(ACErrorCodes.IO_EXCEPTION, context.getString(R.string.server_error));
            response.setError(error);
        }

        if (response.getError() != null) {
            ACLogger.log("ERROR = " + response.getError().getErrorCodes() + " : " + response.getError().getMessage());
        }

        return response;
    }

    public static NetworkAsyncTaskResponse<String> getResponse(Context context, MethodTypes methodTypes, String absPostUrl, Map<String, String> params) {
        NetworkAsyncTaskResponse<String> response = new NetworkAsyncTaskResponse<String>();

        ACLogger.log("URL = " + absPostUrl);
        ACLogger.log("METHOD = " + methodTypes);
        ACLogger.log("PARAMS = " + params);

        try {
            if (!isNetworkAvailable(context)) {
                ACError error = new ACError(ACErrorCodes.NETWORK_NOTAVAILABLE_ERROR, context.getString(R.string.network_not_available));
                response.setError(error);
                return response;
            }

            HttpResponse httpResponse = null;
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

            if (params != null) {
                Set<Entry<String, String>> entrySet = params.entrySet();
                Iterator<Entry<String, String>> entryIterator = entrySet.iterator();
                while (entryIterator.hasNext()) {
                    Entry<String, String> entry = entryIterator.next();
                    String key = entry.getKey();
                    String value = entry.getValue();
                    nameValuePairs.add(new BasicNameValuePair(key, value));
                }
            }

            if (methodTypes == MethodTypes.GET) {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                httpClient = setCookies(context, httpClient);
                String paramString = URLEncodedUtils.format(nameValuePairs, "utf-8");
                if (paramString != null && paramString.length() > 0)
                    absPostUrl += "?" + paramString;

                ACLogger.log("Encoded URL = " + absPostUrl);

                HttpGet httpGet = new HttpGet(absPostUrl);
                httpGet = (HttpGet) setHeadersInPostRequest(context, httpGet, null);
                httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("admin", "admin"));
                HttpParams httpParameters = httpGet.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

                ACLogger.log("REQUEST HEADERS : ");
                printHeaders(getHeaders(httpGet));

                httpResponse = httpClient.execute(httpGet);

                ACLogger.log("RESPONSE HEADERS : ");
                printHeaders(httpResponse.getAllHeaders());

                saveCookie(context, httpClient);
            } else if (methodTypes == MethodTypes.POST) {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                httpClient = setCookies(context, httpClient);
                httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("admin", "admin"));
                HttpPost httpPost = new HttpPost(absPostUrl);
                httpPost = (HttpPost) setHeadersInPostRequest(context, httpPost, null);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpParams httpParameters = httpPost.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

                ACLogger.log("REQUEST HEADERS : ");
                printHeaders(getHeaders(httpPost));

                httpResponse = httpClient.execute(httpPost);

                ACLogger.log("RESPONSE HEADERS : ");
                printHeaders(httpResponse.getAllHeaders());

                saveCookie(context, httpClient);
            }

            if (httpResponse != null) {
                StatusLine statusLine = httpResponse.getStatusLine();

                ACLogger.log("RESPONSE STATUS = " + statusLine);

                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    httpResponse.getEntity().writeTo(out);
                    try
                    {  // TODO This is not fix ,It is just prevent ,Here serverResponse object is having very big String data,This is cause of OOM
                        String serverResponse = out.toString();
                        if (absPostUrl.indexOf("http://blog.power2sme.com/feed/") == -1)
                            ACLogger.log("RESPONSE = " + serverResponse);
                        response.setResultObject(serverResponse);
                    }
                    catch (OutOfMemoryError e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        out.close();
                    }

                } else if (statusLine.getStatusCode() == HttpStatus.SC_FORBIDDEN) {
                    httpResponse.getEntity().getContent().close();
                    ACError error = new ACError(ACErrorCodes.API_ERRORCODE_FORBIDDEN, context.getString(R.string.server_error_access_forbidden));
                    response.setError(error);
                } else {
                    httpResponse.getEntity().getContent().close();
                    ACError error = new ACError(ACErrorCodes.RESPONSE_NOT_FOUND_EXCEPTION, context.getString(R.string.server_error));
                    response.setError(error);
                }
            } else {
                ACError error = new ACError(ACErrorCodes.RESPONSE_NOT_FOUND_EXCEPTION, context.getString(R.string.server_error));
                response.setError(error);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ACError error = new ACError(ACErrorCodes.UNSUPPORTED_ENCODING_EXCEPTION, context.getString(R.string.server_error));
            response.setError(error);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            ACError error = new ACError(ACErrorCodes.CLIENT_PROTOCOL_EXCEPTION, context.getString(R.string.server_error));
            response.setError(error);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            ACError error = new ACError(ACErrorCodes.SOCKET_TIMEOUT_EXCEPTION, context.getString(R.string.server_error));
            response.setError(error);
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
            ACError error = new ACError(ACErrorCodes.CONNECTION_TIMEOUT_EXCEPTION, context.getString(R.string.server_error));
            response.setError(error);
        } catch (IOException e) {
            e.printStackTrace();
            ACError error = new ACError(ACErrorCodes.IO_EXCEPTION, context.getString(R.string.server_error));
            response.setError(error);
        } catch (Exception e) {
            e.printStackTrace();
            ACError error = new ACError(ACErrorCodes.IO_EXCEPTION, context.getString(R.string.server_error));
            response.setError(error);
        }

        if (response.getError() != null) {
            ACLogger.log("ERROR = " + response.getError().getErrorCodes() + " : " + response.getError().getMessage());
        }

        return response;
    }


    public static NetworkAsyncTaskResponse<String> getMultipartResponse(Context context, String absPostUrl, Map<String, String> params, Map<String, File> filesParams) {
        NetworkAsyncTaskResponse<String> response = new NetworkAsyncTaskResponse<String>();
        try {
            ACLogger.log("METHOD = Multipart");
            ACLogger.log("URL = " + absPostUrl);
            ACLogger.log("PARAMS = " + params);
            ACLogger.log("FILE PARAMS = " + filesParams);

            if (!isNetworkAvailable(context)) {
                ACError error = new ACError(ACErrorCodes.NETWORK_NOTAVAILABLE_ERROR, context.getString(R.string.network_not_available));
                response.setError(error);
                return response;
            }

            HttpResponse httpResponse = null;
            DefaultHttpClient httpClient = new DefaultHttpClient();
            httpClient = setCookies(context, httpClient);
            HttpPost httpPost = new HttpPost(absPostUrl);
            httpPost = (HttpPost) setHeadersInPostRequest(context, httpPost, null);
            httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("admin", "admin"));
            MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            if (params != null) {
                Set<Entry<String, String>> entrySet = params.entrySet();
                Iterator<Entry<String, String>> entryIterator = entrySet.iterator();
                while (entryIterator.hasNext()) {
                    Entry<String, String> entry = entryIterator.next();
                    String key = entry.getKey();
                    String value = entry.getValue();
                    multipartEntity.addPart(key, new StringBody(value));
                }
            }
            if (filesParams != null) {
                Set<Entry<String, File>> entrySet = filesParams.entrySet();
                Iterator<Entry<String, File>> entryIterator = entrySet.iterator();
                while (entryIterator.hasNext()) {
                    Entry<String, File> entry = entryIterator.next();
                    String key = entry.getKey();
                    File value = entry.getValue();
                    multipartEntity.addPart(key, new FileBody(value));
                }
            }
            httpPost.setEntity(multipartEntity);
            HttpParams httpParameters = httpPost.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

            ACLogger.log("REQUEST HEADERS : ");
            printHeaders(getHeaders(httpPost));

            httpResponse = httpClient.execute(httpPost);

            ACLogger.log("RESPONSE HEADERS : ");
            printHeaders(httpResponse.getAllHeaders());

            saveCookie(context, httpClient);
            if (httpResponse != null) {
                StatusLine statusLine = httpResponse.getStatusLine();

                ACLogger.log("RESPONSE STATUS = " + statusLine);

                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    httpResponse.getEntity().writeTo(out);
                    out.close();
                    String serverResponse = out.toString();
                    response.setResultObject(serverResponse);
                    ACLogger.log("RESPONSE = " + serverResponse);
                } else if (statusLine.getStatusCode() == HttpStatus.SC_FORBIDDEN) {
                    httpResponse.getEntity().getContent().close();
                    ACError error = new ACError(ACErrorCodes.API_ERRORCODE_FORBIDDEN, context.getString(R.string.server_error_access_forbidden));
                    response.setError(error);
                } else {
                    httpResponse.getEntity().getContent().close();
                    ACError error = new ACError(ACErrorCodes.RESPONSE_NOT_FOUND_EXCEPTION, context.getString(R.string.server_error));
                    response.setError(error);
                }
            } else {
                ACError error = new ACError(ACErrorCodes.RESPONSE_NOT_FOUND_EXCEPTION, context.getString(R.string.server_error));
                response.setError(error);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ACError error = new ACError(ACErrorCodes.UNSUPPORTED_ENCODING_EXCEPTION, context.getString(R.string.server_error));
            response.setError(error);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            ACError error = new ACError(ACErrorCodes.CLIENT_PROTOCOL_EXCEPTION, context.getString(R.string.server_error));
            response.setError(error);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            ACError error = new ACError(ACErrorCodes.SOCKET_TIMEOUT_EXCEPTION, context.getString(R.string.server_error));
            response.setError(error);
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
            ACError error = new ACError(ACErrorCodes.CONNECTION_TIMEOUT_EXCEPTION, context.getString(R.string.server_error));
            response.setError(error);
        } catch (IOException e) {
            e.printStackTrace();
            ACError error = new ACError(ACErrorCodes.IO_EXCEPTION, context.getString(R.string.server_error));
            response.setError(error);
        } catch (Exception e) {
            e.printStackTrace();
            ACError error = new ACError(ACErrorCodes.IO_EXCEPTION, context.getString(R.string.server_error));
            response.setError(error);
        }

        if (response.getError() != null) {
            ACLogger.log("ERROR = " + response.getError().getErrorCodes() + " : " + response.getError().getMessage());
        }

        return response;
    }
}
