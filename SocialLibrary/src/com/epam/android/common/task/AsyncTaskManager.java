package com.epam.android.common.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.epam.android.common.DelegateActivity;
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

	public void addActivityTasks(String activityKey) {
		if (!checkActivityTasks(activityKey)) {
			mAsyncTaskActivity.put(activityKey,
					new HashMap<String, CommonAsyncTask>());
		}
	}

	public boolean checkActivityTasks(String activityKey) {
		return mAsyncTaskActivity.containsKey(activityKey);
	}

	public HashMap<String, CommonAsyncTask> getActivityTasks(String activityKey) {
		return mAsyncTaskActivity.get(activityKey);
	}

	@SuppressWarnings("rawtypes")
	public void addTask(String activityKey, String taskKey, CommonAsyncTask task) {
		getActivityTasks(activityKey).put(taskKey, task);
	}

	public void removeTask(String activityKey, String taskKey) {
		getTask(activityKey, taskKey).cancel(true);
		getActivityTasks(activityKey).remove(taskKey);
	}

	public boolean checkTask(String activityKey, String taskKey) {
		return getActivityTasks(activityKey).containsKey(taskKey);
	}

	public CommonAsyncTask getTask(String activityKey, String taskKey) {
		return getActivityTasks(activityKey).get(taskKey);
	}

	public boolean isLastTask(Context context) {
		Boolean result = true;
		Collection<CommonAsyncTask> taskCollection = getActivityTasks(
				context.getClass().getName()).values();
		Object[] keys = taskCollection.toArray();
		for (int i = 0; i < keys.length; i++) {
			if (((CommonAsyncTask) keys[i]).getStatus().equals(
					AsyncTask.Status.RUNNING)) {
				result = false;
			}
		}
		return result;
	}

	public void setDeleteStatus(boolean b, Context context) {
		String activityKey = context.getClass().getName();
		Set<String> keys = getActivityTasks(activityKey).keySet();
		Object[] arrayKeys = keys.toArray();
		for (int i = 0; i < arrayKeys.length; i++) {
			getTask(activityKey, (String) arrayKeys[i]).setToBeCancelled(b);
			if (b) {
				killTask(activityKey, (String) arrayKeys[i]);
			}
		}
	}

	protected void killTask(final String activityKey, final String taskKey) {
		// TODO read about handler
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (checkTask(activityKey, taskKey)
						&& getTask(activityKey, taskKey).isToBeCancelled()) {
					removeTask(activityKey, taskKey);
				}
			}
		}, DelegateActivity.TASK_LIFETIME);
	}
}
