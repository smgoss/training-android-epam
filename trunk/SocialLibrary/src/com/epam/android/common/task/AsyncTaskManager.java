package com.epam.android.common.task;

import java.util.HashMap;

import com.epam.android.social.DelegateActivity;

import android.content.Context;
import android.util.Log;



public class AsyncTaskManager {
	
	public static final String ASYNC_TASK_MANAGER = "++AsyncTaskManager++";

	private HashMap<String, CommonAsyncTask> mAsyncTaskStorage;

	private static final String TAG = AsyncTaskManager.class.getSimpleName();
	
	public AsyncTaskManager() {
		mAsyncTaskStorage = new HashMap<String, CommonAsyncTask>();
	}
	
	public static AsyncTaskManager get(Context context) {
        AsyncTaskManager asyncTaskManager = (AsyncTaskManager) context.getSystemService(ASYNC_TASK_MANAGER);
        if (asyncTaskManager == null) {
            context = context.getApplicationContext();
            asyncTaskManager = (AsyncTaskManager) context.getSystemService(ASYNC_TASK_MANAGER);
        }
        if (asyncTaskManager == null) {
            throw new IllegalStateException("AsyncTaskManager client not available");
        }
        return asyncTaskManager;
    }
	
	public void addTask(String key, CommonAsyncTask task) {
		Log.d(TAG, "added ");
		mAsyncTaskStorage.put(key, task);
	}
	
	public void removeTask(String key) {
		mAsyncTaskStorage.remove(key);
		Log.d(TAG, "removed ");
	}
	
	public CommonAsyncTask getTask(String key) {
		return mAsyncTaskStorage.get(key);
	}
}
