package com.epam.android.common.task;

import java.util.HashMap;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class AsyncTaskManager {

	public static final String ASYNC_TASK_MANAGER = "++AsyncTaskManager++";

	@SuppressWarnings("rawtypes")
	private HashMap<String, CommonAsyncTask> mAsyncTaskStorage;
	
	

	private static final String TAG = AsyncTaskManager.class.getSimpleName();

	@SuppressWarnings("rawtypes")
	public AsyncTaskManager() {
		mAsyncTaskStorage = new HashMap<String, CommonAsyncTask>();
	}

	//TODO move to some util class
	public static AsyncTaskManager get(Context context) {
		AsyncTaskManager asyncTaskManager = (AsyncTaskManager) context
				.getSystemService(ASYNC_TASK_MANAGER);
		if (asyncTaskManager == null) {
			context = context.getApplicationContext();
			asyncTaskManager = (AsyncTaskManager) context
					.getSystemService(ASYNC_TASK_MANAGER);
		}
		if (asyncTaskManager == null) {
			throw new IllegalStateException(
					"AsyncTaskManager client not available");
		}
		return asyncTaskManager;
	}

	@SuppressWarnings("rawtypes")
	public void addTask(String key, CommonAsyncTask task) {
		mAsyncTaskStorage.put(key, task);
	}

	public void removeTask(String key) {
		Log.d(TAG, "remove " + key);
		getTask(key).cancel(true);
		mAsyncTaskStorage.remove(key);
	}

	@SuppressWarnings("rawtypes")
	public CommonAsyncTask getTask(String key) {
		return mAsyncTaskStorage.get(key);
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
