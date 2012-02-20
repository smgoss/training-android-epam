package com.epam.android.common.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.epam.android.common.utils.GetSystemService;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

public class AsyncTaskManager {

	public static final String ASYNC_TASK_MANAGER = "++AsyncTaskManager++";

	@SuppressWarnings("rawtypes")
	private HashMap<String, HashMap<String, CommonAsyncTask>> mAsyncTaskActivity;

	private static final String TAG = AsyncTaskManager.class.getSimpleName();

	@SuppressWarnings("rawtypes")
	public AsyncTaskManager() {
		mAsyncTaskActivity = new HashMap<String, HashMap<String, CommonAsyncTask>>();

	}

	public static AsyncTaskManager get(Context context) {
		return (AsyncTaskManager) GetSystemService.get(context,
				ASYNC_TASK_MANAGER);
	}

	public void addActivity(String activityKey) {
		if (!checkActivity(activityKey)) {
			mAsyncTaskActivity.put(activityKey,
					new HashMap<String, CommonAsyncTask>());
		}
	}

	public boolean checkActivity(String activityKey) {
		return mAsyncTaskActivity.containsKey(activityKey);
	}

	public HashMap<String, CommonAsyncTask> getActivity(String activityKey) {
		return mAsyncTaskActivity.get(activityKey);
	}

	@SuppressWarnings("rawtypes")
	public void addTask(String activityKey, String taskKey, CommonAsyncTask task) {
		getActivity(activityKey).put(taskKey, task);
	}

	public void removeTask(String activityKey, String taskKey) {
		getTask(activityKey, taskKey).cancel(true);
		getActivity(activityKey).remove(taskKey);
	}

	public boolean checkTask(String activityKey, String taskKey) {
		return getActivity(activityKey).containsKey(taskKey);
	}

	public CommonAsyncTask getTask(String activityKey, String taskKey) {
		return getActivity(activityKey).get(taskKey);
	}

	public boolean isLastTask(Context context) {
		Boolean result = true;
		Collection<CommonAsyncTask> taskCollection = getActivity(context.getClass().getName()).values();
		Object[] keys = taskCollection.toArray();   
		for (int i = 0; i < keys.length; i++) {
			if (((CommonAsyncTask) keys[i]).getStatus().equals(AsyncTask.Status.RUNNING)) {
				result = false;
			}
		}
		return result;
	}
	
	
	public void doNotKillTask(String activityKey, String key) {
		if (checkTask(activityKey, key)) {
			getTask(activityKey, key).setToBeCancelled(false);
			Log.d("my-killing",
					"set not to be killed"
							+ getTask(activityKey, key).toString());
		}
	}

	public void killTask(final String activityKey, final String key,
			final Integer time) {
		if (checkTask(activityKey, key)
				&& getTask(activityKey, key).isCancellableOnPause()) {
			Log.d("my-killing", "set to be killed "
					+ getTask(activityKey, key).toString());
			getTask(activityKey, key).setToBeCancelled(true);
			// TODO read about handler
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					if (checkTask(activityKey, key)
							&& getTask(activityKey, key).isToBeCancelled()) {
						Log.d("my-killing",
								"killed " + getTask(activityKey, key));
						removeTask(activityKey, key);

					}
				}

			}, time);
		}
	}

}
