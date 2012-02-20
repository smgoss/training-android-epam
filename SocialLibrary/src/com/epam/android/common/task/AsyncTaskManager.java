package com.epam.android.common.task;

import java.util.HashMap;

import com.epam.android.common.utils.GetSystemService;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class AsyncTaskManager {

	public static final String ASYNC_TASK_MANAGER = "++AsyncTaskManager++";

	@SuppressWarnings("rawtypes")
	
	private HashMap<String, CommonAsyncTask> mAsyncTaskList;
		
	private HashMap<String, HashMap<String, CommonAsyncTask>> mAsyncTaskActivity;
	

	private static final String TAG = AsyncTaskManager.class.getSimpleName();

	@SuppressWarnings("rawtypes")
	public AsyncTaskManager() {
		mAsyncTaskActivity = new HashMap<String, HashMap<String, CommonAsyncTask>>();
		
	}

	public static AsyncTaskManager get(Context context) {
		return (AsyncTaskManager) GetSystemService.get(context, ASYNC_TASK_MANAGER);
	}
	
	public void addActivity(String activityKey) {
		mAsyncTaskActivity.put(activityKey, new HashMap<String, CommonAsyncTask>());
	}
	
	public boolean checkActivity(String activityKey) {
		return mAsyncTaskActivity.containsKey(activityKey);
	}
	
	public HashMap<String, CommonAsyncTask> getActivity(String activityKey) {
		return mAsyncTaskActivity.get(activityKey);
	}

	@SuppressWarnings("rawtypes")
	public void addTask(String key, CommonAsyncTask task) {
		mAsyncTaskList.put(key, task);
	}

	public void removeTask(String key) {
		Log.d(TAG, "remove " + key);
		getTask(key).cancel(true);
		mAsyncTaskList.remove(key);
	}

	public CommonAsyncTask getTask(String key) {
		return mAsyncTaskList.get(key);
	}

	public void killTask(final String key, final Integer time) {
		if (this.getTask(key) != null) {
			this.getTask(key).setToBeCancelled(true);
		}
		
		//TODO read about handler
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if (getTask(key) != null && getTask(key).isToBeCancelled()) {
					removeTask(key);
				}				
			}
			
		}, time);
	}

}
