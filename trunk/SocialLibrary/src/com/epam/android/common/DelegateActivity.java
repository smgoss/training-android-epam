package com.epam.android.common;

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

	private static final Integer TASK_LIFETIME = 10001;

	private BroadcastReceiver receiver;

	protected AsyncTaskManager mAsyncTaskManager;

	protected ProgressDialog mProgressDialog;

	public void showLoading() {

		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(true);
			mProgressDialog.setOnCancelListener(this);
		}
		mProgressDialog.setTitle(TITLE);
		mProgressDialog.setMessage(MSG);
		mProgressDialog.show();
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
		mAsyncTaskManager.addTask(getKey(), task);
		task.start();
	}

	public abstract String getKey();

	@Override
	protected void onPause() {
		unregisterReceiver(receiver);

		if (mAsyncTaskManager.getTask(getKey()) != null
				&& mAsyncTaskManager.getTask(getKey()).isCancellableOnPause()) {
			mAsyncTaskManager.killTask(getKey(), TASK_LIFETIME);
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		Log.d("my DA", "onResume");

		if (mAsyncTaskManager.getTask(getKey()) != null) {
			mAsyncTaskManager.getTask(getKey()).setToBeCancelled(false);
		}

		IntentFilter filter = new IntentFilter();
		filter.addAction(CommonAsyncTask.ON_PRE_EXECUTE);
		filter.addAction(CommonAsyncTask.ON_POST_EXECUTE);
		filter.addAction(CommonAsyncTask.ON_PROGRESS_UPDATE);

		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getStringExtra(CommonAsyncTask.TASK)
						.equals(getKey())) {
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
		mAsyncTaskManager.removeTask(getKey());
		success(intent);
	}

	protected void onTaskProgressUpdate(Intent intent) {
		showProgress(intent.getStringExtra(CommonAsyncTask.TEXT));
	}

}
