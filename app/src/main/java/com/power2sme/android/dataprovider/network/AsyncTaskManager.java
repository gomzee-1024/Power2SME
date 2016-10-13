package com.power2sme.android.dataprovider.network;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class AsyncTaskManager 
{
	private static AsyncTaskManager asyncTaskManager;
	private Hashtable<String, List<AsyncTask>> tasksMap;
	private AsyncTaskManager()
	{
		tasksMap=new Hashtable<String, List<AsyncTask>>();
	}
	
	public static AsyncTaskManager getInstance()
	{
		if(asyncTaskManager==null)
			asyncTaskManager=new AsyncTaskManager();
		return asyncTaskManager;
	}
	
	public void registerTask(Fragment fragment, AsyncTask asyncTask)
	{
		String key = fragment.getClass().getName();
		_registerTask(key, asyncTask);
	}
	
	public void unregisterTask(Fragment fragment)
	{
		String key = fragment.getClass().getName();
		_unregisterTask(key);
	}
	
	private void _registerTask(String key, AsyncTask asyncTask)
	{
		if(tasksMap.containsKey(key))
		{
			List<AsyncTask> taskQueue = tasksMap.get(key);
			taskQueue.add(asyncTask);
		}
		else
		{
			List<AsyncTask> queue = new ArrayList<AsyncTask>();
			queue.add(asyncTask);
			tasksMap.put(key, queue);	
		}
	}
	
	private void _unregisterTask(String key)
	{
		if(tasksMap.containsKey(key))
		{
			List<AsyncTask> taskQueue = tasksMap.get(key);
			Iterator<AsyncTask> listEntryIterator = taskQueue.iterator();
			while (listEntryIterator.hasNext()) 
			{
				AsyncTask asyncTask = listEntryIterator.next();
				asyncTask.cancel(true);	
			}
			tasksMap.remove(key);
		}
	}
	
	public boolean removeAllTask()
	{
		Set<Entry<String, List<AsyncTask>>> entrySet = tasksMap.entrySet();
		Iterator<Entry<String, List<AsyncTask>>>  mapEntryIterator = entrySet.iterator();
		while (mapEntryIterator.hasNext()) 
		{
			Entry<String, List<AsyncTask>> entry = mapEntryIterator.next();
			List<AsyncTask>  asyncTaskQueue = entry.getValue();
			Iterator<AsyncTask> listEntryIterator = asyncTaskQueue.iterator();
			while (listEntryIterator.hasNext()) 
			{
				AsyncTask asyncTask = listEntryIterator.next();
				asyncTask.cancel(true);	
			}
		}
		tasksMap.clear();
		return true;
	}
}
