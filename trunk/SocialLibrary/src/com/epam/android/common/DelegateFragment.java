package com.epam.android.common;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.epam.android.common.task.AsyncTaskManager;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.IDelegate;
import com.epam.android.common.task.ITaskCreator;

public abstract class DelegateFragment extends Fragment implements IDelegate {

	private static final String TAG = DelegateFragment.class.getSimpleName();

	private static final String TITLE = "Please wait";

	private static final String MSG = "Loading...";

	private BroadcastReceiver receiver;

	private AsyncTaskManager mAsyncTaskManager;

	private ProgressDialog mProgressDialog;

	public abstract String getUrl();
	
	private View view = null;
	
	@Override
	public void showLoading() {

		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(getActivity());
			Log.d("dialog", "create" + this.toString());
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(true);
			mProgressDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					getActivity().finish();
				}
			});
		} else {
			Log.d("dialog", "not null" + this.toString());
		}
		if (!mProgressDialog.isShowing() && this.getActivity().getWindow() != null) {
			mProgressDialog.setTitle(TITLE);
			mProgressDialog.setMessage(MSG);
			mProgressDialog.show();
			Log.d("dialog", "show" + this.toString());
		}
	}

	@Override
	public void showProgress(String textMessage) {
		if (mProgressDialog == null) {
			Log.d("dialog", "progress " + this.toString());
			showLoading();
		}
		mProgressDialog.setMessage(textMessage);
	}

	@Override
	public void hideLoading() {
		if (mProgressDialog != null && mProgressDialog.isShowing()
				&&  getActivity().getWindow() != null) {
			mProgressDialog.dismiss();
			Log.d("dialog", "dismiss " + this.toString());
			if (!mAsyncTaskManager.isLastTask(getDelegateKey())) {
				Log.d("dialog", "other tasks " + this.toString());
				showLoading();
			}
		}
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
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		mAsyncTaskManager = AsyncTaskManager.get(getActivity());
		mAsyncTaskManager.addActivityTasks(getDelegateKey());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		if (view == null) {
			view = inflater.inflate(getLayoutResource(), container, false);
			return view;
		}
		
		return view.getRootView();
		
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
		Log.d(TAG, "onDestroy");

		mAsyncTaskManager.setDeleteStatus(true, getDelegateKey());
		super.onDestroy();
	}

	@Override
	public void onResume() {
		
		Log.d(TAG, "onResume");
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
						if (isLoaded(getUrl())) {
							onTaskPostExecute(intent);
						}
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
		startTasks();
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
		Log.d(TAG, "onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		Log.d(TAG, "onSaveInstanseState");
		super.onSaveInstanceState(outState);
		
	}
}
