package com.epam.android.common;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import com.epam.android.common.task.AsyncTaskManager;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.IDelegate;
import com.epam.android.common.task.ITaskCreator;

public abstract class DelegateActivity extends Activity implements IDelegate,
		OnCancelListener {

	private static final String TAG = DelegateActivity.class.getSimpleName();

	private static final String TITLE = "Please wait";

	private static final String MSG = "Loading...";

	public static final Integer TASK_LIFETIME = 10001;

	private BroadcastReceiver receiver;

	protected AsyncTaskManager mAsyncTaskManager;

	protected ProgressDialog mProgressDialog;

	protected List<CommonAsyncTask> mTasks = new ArrayList<CommonAsyncTask>();;

	public void showLoading() {

		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(true);
			mProgressDialog.setOnCancelListener(this);
		}
		if (!mProgressDialog.isShowing() && this.getWindow() != null) {
			mProgressDialog.setTitle(TITLE);
			mProgressDialog.setMessage(MSG);
			mProgressDialog.show();
			
		}
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		finish();
	}

	public void showProgress(String textMessage) {
		if (mProgressDialog == null) {
			showLoading();
		}
		mProgressDialog.setMessage(textMessage);
	}

	public void hideLoading() {
		if (mProgressDialog != null && mProgressDialog.isShowing()
				&& !isFinishing()) {
			mProgressDialog.dismiss();
			if (!mAsyncTaskManager.isLastTask(this)) {
				mProgressDialog.show();
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void handleError(CommonAsyncTask task, Exception e) {
		Log.e(TAG, "http client err: " + e.getMessage(), e);
		Toast.makeText(getContext(), "http client err: " + e.getMessage(),
				Toast.LENGTH_LONG).show();
	}

	public Context getContext() {
		return this;
	}

	@SuppressWarnings("rawtypes")
	public void executeTask(ITaskCreator taskCreator) {
		CommonAsyncTask task = taskCreator.create();
		mAsyncTaskManager.addTask(this.getClass().getName(), task.getUrl(),
				task);
		task.start();
	}

	public List<CommonAsyncTask> getTasks() {
		return mTasks;
	}

	public abstract void setTasks();

	protected boolean addToList(String url) {
		if (mAsyncTaskManager.checkTask(this.getClass().getName(), url)) {
			mTasks.add(mAsyncTaskManager
					.getTask(this.getClass().getName(), url));
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void onPause() {
		unregisterReceiver(receiver);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		mAsyncTaskManager.setDeleteStatus(true, this);
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		if (!mAsyncTaskManager.isLastTask(this)) {
			showLoading();
		}

		mAsyncTaskManager.setDeleteStatus(false, this);

		IntentFilter filter = new IntentFilter();
		filter.addAction(CommonAsyncTask.ON_PRE_EXECUTE);
		filter.addAction(CommonAsyncTask.ON_POST_EXECUTE);
		filter.addAction(CommonAsyncTask.ON_PROGRESS_UPDATE);

		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getStringExtra(CommonAsyncTask.ACTIVITY_KEY).equals(
						context.getClass().getName())) {
					if (intent.getAction().equals(
							CommonAsyncTask.ON_PRE_EXECUTE)) {
						onTaskPreExecute(intent);
					} else if (intent.getAction().equals(
							CommonAsyncTask.ON_POST_EXECUTE)) {
						onTaskPostExecute(intent);
					} else if (intent.getAction().equals(
							CommonAsyncTask.ON_PROGRESS_UPDATE)) {
						onTaskProgressUpdate(intent);
					}
				}
			}
		};

		registerReceiver(receiver, filter);
		super.onResume();
	}

	protected abstract void success(Intent intent);

	protected void onTaskPreExecute(Intent intent) {
		showLoading();
	}

	protected void onTaskPostExecute(Intent intent) {
		hideLoading();
		success(intent);
	}

	protected void onTaskProgressUpdate(Intent intent) {
		showProgress(intent.getStringExtra(CommonAsyncTask.TEXT));
	}

	protected Object sucessResult(Intent intent, String url) {
		String taskKey = intent.getStringExtra(CommonAsyncTask.TASK_KEY);
		CommonAsyncTask task = mAsyncTaskManager.getTask(this.getClass()
				.getName(), taskKey);
		if (taskKey.equals(url)) {
			return task.getResult();
		}

		return null;
	}

}
