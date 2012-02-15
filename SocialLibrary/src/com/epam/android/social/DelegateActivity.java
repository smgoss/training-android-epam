package com.epam.android.social;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

	protected AsyncTaskManager mAsyncTaskManager;

	protected ProgressDialog mProgressDialog;

	public void showLoading() {
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(true);
		}
		mProgressDialog.setTitle(TITLE);
		mProgressDialog.setMessage(MSG);
		mProgressDialog.show();

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

	public void handleError(CommonAsyncTask task, Exception e) {
		Log.e(TAG, "http client err: " + e.getMessage(), e);
		Toast.makeText(getContext(), "http client err: " + e.getMessage(),
				Toast.LENGTH_LONG).show();
	}

	public Context getContext() {
		return this;
	}

	public void removeTask() {
		Log.d("my", "task removed");
		mAsyncTaskManager.removeTask(getKey());
	}

	public void executeTask(ITaskCreator taskCreator) {
		CommonAsyncTask task = taskCreator.create();
		Log.d("my", "added" + task.toString());
		mAsyncTaskManager.addTask(getKey(), task);
		task.start();
	}

	public abstract String getKey();

	@Override
	protected void onPause() {
		Log.d("my", "paused");
		unregisterReceiver(receiver);
		// CommonAsyncTask task = mAsyncTaskManager.getTask(getKey());
		// task.cancel(true);
		// hideLoading();
		// TODO Do smth with task on pause, on BackKeyPressed
		super.onPause();
	}

	@Override
	protected void onResume() {

		IntentFilter filter = new IntentFilter();
		filter.addAction(CommonAsyncTask.ON_PRE_EXECUTE);
		filter.addAction(CommonAsyncTask.ON_POST_EXECUTE);
		filter.addAction(CommonAsyncTask.ON_PROGRESS_UPDATE);

		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.d("bcr",
						"broadcast recieved, action " + intent.getAction()
								+ ", key "
								+ intent.getStringExtra(CommonAsyncTask.TASK));
				if (intent.getStringExtra(CommonAsyncTask.TASK)
						.equals(getKey())) {
					Log.d("bcr",
							"broadcast recieved, action " + intent.getAction());
					if (intent.getAction().equals(
							CommonAsyncTask.ON_PRE_EXECUTE)) {
						Log.d("bcr", "pre");
						showLoading();
					} else if (intent.getAction().equals(
							CommonAsyncTask.ON_POST_EXECUTE)) {
						Log.d("bcr", "post");
						hideLoading();
						removeTask();
						success(intent);
					} else if (intent.getAction().equals(
							CommonAsyncTask.ON_PROGRESS_UPDATE)) {
						Log.d("bcr", "progress");
						showProgress(intent
								.getStringExtra(CommonAsyncTask.TEXT));
					}
				}
			}

		};
		registerReceiver(receiver, filter);
		super.onResume();
	}

	protected abstract void success(Intent intent);

	@Override
	protected void onDestroy() {
		Log.d("my", "destroyed");
		// taskCreatorStorage.clear();
		// taskCreatorStorage = null;
		super.onDestroy();
	}

}
