package com.power2sme.android.dataprovider;

import android.os.AsyncTask;

import java.util.Hashtable;

/**
 * Created by tausif on 29/6/15.
 */
public class AsyncTaskQueue
{
    public interface OnAsyncTaskQueueListener
    {
        void onAdded(AsyncTask asyncTask);
        void onRemove(AsyncTask asyncTask);
    }
    OnAsyncTaskQueueListener callback=null;
    public void setOnAsyncTaskQueueListener(OnAsyncTaskQueueListener callback)
    {
        this.callback=callback;
    }

    private static AsyncTaskQueue asyncTaskQueue;
    private Hashtable<String , AsyncTask> taskQueue = null;

    private AsyncTaskQueue()
    {
        taskQueue = new Hashtable<String , AsyncTask>();
    }
    public static AsyncTaskQueue getInstance()
    {
        if(asyncTaskQueue==null)
        {
            asyncTaskQueue=new AsyncTaskQueue();
        }
        return asyncTaskQueue;
    }

    public void addTask(String key, AsyncTask task)
    {
        taskQueue.put(key, task);
        if(callback!=null)
            callback.onAdded(task);
    }

    public AsyncTask removeTask(String key)
    {
        AsyncTask task = taskQueue.remove(key);
        if(callback!=null)
            callback.onAdded(task);
        return task;
    }

    public boolean isEmpty()
    {
        if(taskQueue.size()==0)
        {
            return true;
        }
        return false;
    }
}
