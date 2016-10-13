package com.power2sme.android.dataprovider.network;


public interface INetworkAsyncTaskCallbacks<T>
{
	void onPreExecute();
	NetworkAsyncTaskResponse<T> doInBackground();
	void onPostExecute(NetworkAsyncTaskResponse<T> networkAsyncTaskResponse);
}
