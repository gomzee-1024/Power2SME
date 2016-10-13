package com.power2sme.android.dataprovider.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.power2sme.android.dataprovider.AsyncTaskQueue;

public class NetworkAsyncTask<T> extends AsyncTask<Void, Void, NetworkAsyncTaskResponse<T>>
{

	INetworkAsyncTaskCallbacks iNetworkAsyncTaskCallbacks;
	Context context;
	ProgressDialog progressDialog;

	public NetworkAsyncTask(Context context, INetworkAsyncTaskCallbacks iNetworkAsyncTaskCallbacks)
	{
		this.iNetworkAsyncTaskCallbacks = iNetworkAsyncTaskCallbacks;
		this.context=context;
	}

	public NetworkAsyncTask(INetworkAsyncTaskCallbacks iNetworkAsyncTaskCallbacks)
	{
		this.iNetworkAsyncTaskCallbacks = iNetworkAsyncTaskCallbacks;
		this.context=context;
	}
	
	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();
		AsyncTaskQueue.getInstance().addTask(String.valueOf(this.hashCode()), this);
		iNetworkAsyncTaskCallbacks.onPreExecute();
	}
	
	@Override
	protected NetworkAsyncTaskResponse<T> doInBackground(Void... params) 
	{
		return iNetworkAsyncTaskCallbacks.doInBackground();
	}
	
	@Override
	protected void onPostExecute(NetworkAsyncTaskResponse<T> result) 
	{
		super.onPostExecute(result);
		iNetworkAsyncTaskCallbacks.onPostExecute(result);
		AsyncTaskQueue.getInstance().removeTask(String.valueOf(this.hashCode()));
	}
}
