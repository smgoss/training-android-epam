package com.epam.android.demo.common.task;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import com.epam.android.demo.common.utils.GetSystemService;

public class AsyncTaskManager {

	public static final String ASYNC_TASK_MANAGER = "++AsyncTaskManager++";

	public static final Integer TASK_LIFETIME = 10001;
	
	@SuppressWarnings("rawtypes")
	/*
	 * Key of delegate, and key of tasks
	 */
	private HashMap<String, HashMap<String, CommonAsyncTask>> mAsyncTaskActivity;

	private HashMap<String, Runnable> mRunnableStorage;

	private static final String TAG = AsyncTaskManager.class.getSimpleName();

	private Handler mHandler;

	@SuppressWarnings("rawtypes")
	public AsyncTaskManager() {
		mAsyncTaskActivity = new HashMap<String, HashMap<String, CommonAsyncTask>>();
		mHandler = new Handler();
		mRunnableStorage = new HashMap<String, Runnable>();
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

	public boolean isLastTask(String key) {
		Boolean result = true;
		Collection<CommonAsyncTask> taskCollection = getActivityTasks(
				key).values();
		Object[] keys = taskCollection.toArray();
		for (int i = 0; i < keys.length; i++) {
			if (((CommonAsyncTask) keys[i]).getStatus().equals(
					AsyncTask.Status.RUNNING)) {
				result = false;
			}
		}
		return result;
	}

	public void setDeleteStatus(boolean b, String key) {
		String activityKey = key;
		Set<String> keys = getActivityTasks(activityKey).keySet();
		Object[] arrayKeys = keys.toArray();
		for (int i = 0; i < arrayKeys.length; i++) {
			if (b) {
				killTask(activityKey, (String) arrayKeys[i]);
			} else {
				mHandler.removeCallbacks(mRunnableStorage.get(activityKey
						+ (String) arrayKeys[i]));
				mRunnableStorage.remove(activityKey + (String) arrayKeys[i]);
			}
		}
	}

	protected void killTask(final String activityKey, final String taskKey) {
		// TODO read about handler
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				if (checkTask(activityKey, taskKey)) {
					removeTask(activityKey, taskKey);
				}
				mRunnableStorage.remove(activityKey + taskKey);
			}
		};
		mRunnableStorage.put(activityKey + taskKey, runnable);
		mHandler.postDelayed(runnable, TASK_LIFETIME);
	}
}
