package com.epam.android.social.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.epam.android.common.task.AsyncTaskManager;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.IDelegate;
import com.epam.android.common.task.ITaskCreator;
import com.epam.android.social.R;

public abstract class DelegateFragmentWithCustomLoad extends Fragment implements IDelegate {

	private static final String TAG = DelegateFragmentWithCustomLoad.class.getSimpleName();

	private static final String TITLE = "Please wait";

	private static final String MSG = "Loading...";

	private BroadcastReceiver receiver;

	private AsyncTaskManager mAsyncTaskManager;

	private ProgressDialog mProgressDialog;

	private boolean isFragmentStateSaved = false;

	private HashMap<String, CommonAsyncTask> activityTasks;
	
	private ProgressBar mProgressBar;
	
	private boolean isLoading;

	@Override
	public void showLoading() {
		mProgressBar = (ProgressBar) getView().findViewById(
				R.id.progress_bar_on_listView);
		mProgressBar.setVisibility(View.VISIBLE);

		isLoading = true;
	}

	@Override
	public void showProgress(String textMessage) {
		mProgressBar = (ProgressBar) getView().findViewById(
				R.id.progress_bar_on_listView);
		mProgressBar.setVisibility(View.VISIBLE);
		isLoading = true;
	}

	@Override
	public void hideLoading() {
		if (mProgressBar != null
				&& mProgressBar.getVisibility() == View.VISIBLE) {
			mProgressBar.setVisibility(View.INVISIBLE);
		}
		isLoading = false;
	}

	@Override
	public void handleError(CommonAsyncTask task, Exception e) {
		Log.e(TAG, "http client err: " + e.getMessage(), e);
		Toast.makeText(getContext(), "http client err: " + e.getMessage(),
				Toast.LENGTH_LONG).show();
	}

	@Override
	public Context getContext() {
		return getActivity();
	}

	@Override
	public void executeTask(ITaskCreator taskCreator) {
		CommonAsyncTask task = taskCreator.create();
		mAsyncTaskManager.addTask(getDelegateKey(), getTaskKey(task), task);
		task.start();
	}

	protected void executeActivityTasks(final CommonAsyncTask task) {
		if (mAsyncTaskManager.checkTask(getDelegateKey(), getTaskKey(task))) {
			getResult(mAsyncTaskManager.getTask(getDelegateKey(),
					getTaskKey(task)));
		} else {
			executeTask(new ITaskCreator() {
				public CommonAsyncTask create() {
					return task;
				}
			});
		}
	}

	@Override
	public String getDelegateKey() {
		return this.getClass().getName();
	}

	public String getTaskKey(final CommonAsyncTask task) {
		return task.getUrl();
	}

	private void getResult(CommonAsyncTask task) {
		if (task.getStatus().equals(AsyncTask.Status.RUNNING)) {
			showLoading();
		} else if (task.getStatus().equals(AsyncTask.Status.FINISHED)) {
			task.sendResult();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		mAsyncTaskManager = AsyncTaskManager.get(getActivity());
		mAsyncTaskManager.addActivityTasks(getDelegateKey());

		if (savedInstanceState != null) {
			isFragmentStateSaved = true;
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		return inflater.inflate(getLayoutResource(), container, false);

	}

	public abstract int getLayoutResource();

	@Override
	public void onPause() {
		Log.d(TAG, "onPause");
		// TODO check receiver
		getActivity().unregisterReceiver(receiver);
		super.onPause();
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy " + getDelegateKey());
		saveActivityTasks();
		mAsyncTaskManager.setDeleteStatus(true, getDelegateKey());

		super.onDestroy();
	}

	private void saveActivityTasks() {
		activityTasks = mAsyncTaskManager.getActivityTasks(getDelegateKey());
	}

	private void restoreActivityTask() {
		mAsyncTaskManager.restoreActivityTask(getDelegateKey(), activityTasks);
	}

	@Override
	public void onResume() {

		Log.d(TAG, "onResume");
		if (activityTasks != null) {
			restoreActivityTask();
		}
		mAsyncTaskManager.setDeleteStatus(false, getDelegateKey());

		IntentFilter filter = new IntentFilter();
		filter.addAction(CommonAsyncTask.ON_ERROR);
		filter.addAction(CommonAsyncTask.ON_PRE_EXECUTE);
		filter.addAction(CommonAsyncTask.ON_POST_EXECUTE);
		filter.addAction(CommonAsyncTask.ON_PROGRESS_UPDATE);

		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.d(TAG, intent.getStringExtra(CommonAsyncTask.ACTIVITY_KEY)
						+ " " + getDelegateKey());
				if (intent.getStringExtra(CommonAsyncTask.ACTIVITY_KEY).equals(
						getDelegateKey())) {
					if (intent.getAction().equals(
							CommonAsyncTask.ON_PRE_EXECUTE)) {
						onTaskPreExecute(intent);
					} else if (intent.getAction().equals(
							CommonAsyncTask.ON_POST_EXECUTE)) {
//						if (isLoaded(getUrl())) {
							onTaskPostExecute(intent);
//						}
					} else if (intent.getAction().equals(
							CommonAsyncTask.ON_PROGRESS_UPDATE)) {
						onTaskProgressUpdate(intent);
					} else if (intent.getAction().equals(
							CommonAsyncTask.ON_ERROR)) {
						onTaskError(intent);
					}
				}
			}
		};

		getActivity().registerReceiver(receiver, filter);
		if (!isFragmentStateSaved) {
			Log.d(TAG, "!!!!start Task !!!!!!!");
			startTasks();
		}
		super.onResume();
	}

	@Override
	public abstract void startTasks();

	protected void onTaskError(Intent intent) {
		hideLoading();
		handleError((Exception) intent
				.getSerializableExtra(CommonAsyncTask.ERROR));
	}

	public void handleError(Exception e) {
		Log.e(TAG, "http client err: " + e.getMessage(), e);
		Toast.makeText(getContext(), "http client err: " + e.getMessage(),
				Toast.LENGTH_LONG).show();
	}

	protected void onTaskPreExecute(Intent intent) {
		showLoading();
	}

	public void onTaskPostExecute(Intent intent) {
		hideLoading();
		success(intent);
	}

	private List<String> loadedList;

	private boolean isLoaded(String key) {

		if (loadedList == null) {
			loadedList = new ArrayList<String>();
			loadedList.add(key);
			return true;
		} else {
			if (loadedList.contains(key)) {
				return false;
			} else {
				loadedList.add(key);
				return true;

			}
		}
	}

	public void onTaskProgressUpdate(Intent intent) {
		showProgress(intent.getStringExtra(CommonAsyncTask.TEXT));
	}

	@Override
	public abstract void success(Intent intent);

	protected static boolean isAsyncTaskResult(String asyncTaskKey,
			Intent intent) {
		String taskKey = intent.getStringExtra(CommonAsyncTask.TASK_KEY);
		Log.d(TAG, asyncTaskKey + " s " + taskKey);
		return (taskKey.equals(asyncTaskKey));
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "onActivityCreated");
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(TAG, "onSaveInstanseState");
	}
}
