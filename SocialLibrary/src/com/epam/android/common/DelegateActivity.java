package com.epam.android.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.epam.android.common.task.AsyncTaskManager;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.IDelegate;
import com.epam.android.common.task.ITaskCreator;

public abstract class DelegateActivity extends Activity implements IDelegate {

	private static final String TAG = DelegateActivity.class.getSimpleName();

	private static final String TITLE = "Please wait";

	private static final String MSG = "Loading...";

	private BroadcastReceiver receiver;

	private AsyncTaskManager mAsyncTaskManager;

	private ProgressDialog mProgressDialog;

	public ProgressDialog getProgressDialog() {
		return mProgressDialog;
	}

	public void setProgressDialog(ProgressDialog mProgressDialog) {
		this.mProgressDialog = mProgressDialog;
	}

	@Override
	public void showLoading() {

		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(this);
			Log.d("dialog", "create" + this.toString());
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(true);
			mProgressDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					finish();
				}
			});
		} else {
			Log.d("dialog", "not null" + this.toString());
		}
		if (!mProgressDialog.isShowing() && this.getWindow() != null) {
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
				&& !isFinishing() && this.getWindow() != null) {
			mProgressDialog.dismiss();
			Log.d("dialog", "dismiss " + this.toString());
			if (!mAsyncTaskManager.isLastTask(this)) {
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
		return this;
	}

	@Override
	public void executeTask(ITaskCreator taskCreator) {
		CommonAsyncTask task = taskCreator.create();
		mAsyncTaskManager.addTask(this.getClass().getName(), task.getUrl(),
				task);
		task.start();
	}

	protected void executeActivityTasks(final CommonAsyncTask task) {

		if (mAsyncTaskManager.checkTask(this.getClass().getName(),
				task.getUrl())) {
			getResult(mAsyncTaskManager.getTask(this.getClass().getName(),
					task.getUrl()));
		} else {
			executeTask(new ITaskCreator() {
				public CommonAsyncTask create() {
					return task;
				}
			});
		}
	}

	private void getResult(CommonAsyncTask task) {
		if (task.getStatus().equals(AsyncTask.Status.RUNNING)) {
			showLoading();
		} else if (task.getStatus().equals(AsyncTask.Status.FINISHED)) {
			task.sendResult();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");

		super.onCreate(savedInstanceState);
		setContentView(getLayoutResource());
		mAsyncTaskManager = AsyncTaskManager.get(this);
		mAsyncTaskManager.addActivityTasks(this.getClass().getName());

	}

	public abstract int getLayoutResource();

	@Override
	protected void onPause() {
		Log.d(TAG, "onPause");

		unregisterReceiver(receiver);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy");

		mAsyncTaskManager.setDeleteStatus(true, this);
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "onResume");

		mAsyncTaskManager.setDeleteStatus(false, this);

		IntentFilter filter = new IntentFilter();
		filter.addAction(CommonAsyncTask.ON_ERROR);
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
					} else if (intent.getAction().equals(
							CommonAsyncTask.ON_ERROR)) {
						onTaskError(intent);
					}
				}
			}
		};

		registerReceiver(receiver, filter);
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

	public void onTaskProgressUpdate(Intent intent) {
		showProgress(intent.getStringExtra(CommonAsyncTask.TEXT));
	}

	@Override
	public abstract void success(Intent intent);

	protected static boolean isAsyncTaskResult(String asyncTaskKey,
			Intent intent) {
		String taskKey = intent.getStringExtra(CommonAsyncTask.TASK_KEY);
		return (taskKey.equals(asyncTaskKey));
	}

	// TODO read about onRestoreInstanceState
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.d(TAG, "onRestoreInstanseState");
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.d(TAG, "onSaveInstanseState");
		super.onSaveInstanceState(outState);
	}
}
