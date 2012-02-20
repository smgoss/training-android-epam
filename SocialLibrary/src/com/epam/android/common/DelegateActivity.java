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
import android.os.AsyncTask;
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

	private static final Integer TASK_LIFETIME = 10001;

	private BroadcastReceiver receiver;

	protected AsyncTaskManager mAsyncTaskManager;

	protected ProgressDialog mProgressDialog;

	protected List<CommonAsyncTask> mTasks;

	public void showLoading() {

		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(true);
			mProgressDialog.setOnCancelListener(this);
		}
		if (!mProgressDialog.isShowing()) {
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
		Log.d("my-killing", "added " + task.toString());
		task.start();
	}

	public abstract String getKey();

	public List<CommonAsyncTask> getTasks() {
		return mTasks;
	}

	@Override
	protected void onPause() {
		unregisterReceiver(receiver);
		super.onPause();
	}

	
	
	@Override
	protected void onDestroy() {
		for (int i = 0; i < mTasks.size(); i++) {
			if (mAsyncTaskManager.checkTask(this.getClass().getName(), mTasks
					.get(i).getUrl())) {
				mAsyncTaskManager.killTask(this.getClass().getName(), mTasks
						.get(i).getUrl(), TASK_LIFETIME);
			}
		}
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		if (!mAsyncTaskManager.isLastTask(this)) {
			showLoading();
		}
		
		for (int i = 0; i < mTasks.size(); i++) {
			if (mAsyncTaskManager.checkTask(this.getClass().getName(), mTasks
					.get(i).getUrl())) {
				mAsyncTaskManager.doNotKillTask(this.getClass().getName(),
						mTasks.get(i).getUrl());
			}
		}

		IntentFilter filter = new IntentFilter();
		filter.addAction(CommonAsyncTask.ON_PRE_EXECUTE);
		filter.addAction(CommonAsyncTask.ON_POST_EXECUTE);
		filter.addAction(CommonAsyncTask.ON_PROGRESS_UPDATE);

		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(CommonAsyncTask.ON_PRE_EXECUTE)) {
					onTaskPreExecute(intent);
				} else if (intent.getAction().equals(
						CommonAsyncTask.ON_POST_EXECUTE)) {
					onTaskPostExecute(intent);
				} else if (intent.getAction().equals(
						CommonAsyncTask.ON_PROGRESS_UPDATE)) {
					onTaskProgressUpdate(intent);
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

}
