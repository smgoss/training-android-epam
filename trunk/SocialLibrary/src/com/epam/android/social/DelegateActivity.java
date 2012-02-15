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

import com.epam.android.common.CommonApplication;
import com.epam.android.common.task.AsyncTaskManager;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.IDelegate;
import com.epam.android.common.task.ITaskCreator;

public abstract class DelegateActivity extends Activity implements IDelegate {

	private static final String TAG = DelegateActivity.class.getSimpleName();

	private static final String TITLE = "Please wait";

	private static final String MSG = "Loading...";

	private static final String URL = "No url";

	private BroadcastReceiver receiver;

	protected AsyncTaskManager mAsyncTaskManager;

	protected ProgressDialog mProgressDialog;

	// public AsyncTaskManager getAsyncTaskManager() {
	// return mAsyncTaskManager;
	// }

	// public ProgressDialog getProgressDialog() {
	// return mProgressDialog;
	// }
	//
	// public ProgressDialog setProgressDialog() {
	// // mAsyncTaskManager = AsyncTaskManager.get(this);
	// mProgressDialog = new ProgressDialog(this);
	// mProgressDialog.setIndeterminate(true);
	// mProgressDialog.setCancelable(true);
	// return mProgressDialog;
	// }

	public void showLoading() {
		Log.d("my", "show progress");
		Log.d("PrDialog", "show " + mProgressDialog.toString());
		if (mProgressDialog != null) {
			mProgressDialog.setTitle(TITLE);
			mProgressDialog.setMessage(MSG);
			mProgressDialog.show();
		}
	}

	public void showProgress(String textMessage) {
		Log.d("my", "progress" + textMessage);
		if (mProgressDialog != null && !mProgressDialog.isShowing()) {
			mProgressDialog.setTitle(TITLE);
			mProgressDialog.setMessage(textMessage);
			mProgressDialog.show();
		}
		Log.d("PrDialog", "progress " + mProgressDialog.toString());
		mProgressDialog.setMessage(textMessage);
	}

	public void hideLoading() {
		Log.d("my", "hide progress");
		Log.d("PrDialog", "hide " + mProgressDialog.toString());
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
		// TODO remove task if task ends or canceled
		mAsyncTaskManager.removeTask(getKey());
	}

	public void executeTask(ITaskCreator taskCreator) {
		CommonAsyncTask task = taskCreator.create();
		Log.d("my", "added" + task.toString());
		mAsyncTaskManager.addTask(getKey(), task);
		task.start();
	}

	public abstract String getKey();

	// public String getKey() {
	// Log.d("my", URL);
	// return URL;
	// }

	@Override
	protected void onPause() {
		Log.d("my", "paused");
		unregisterReceiver(receiver);
		// CommonAsyncTask task = mAsyncTaskManager.getTask(getKey());
		// task.cancel(true);
		// hideLoading();
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		Log.d("my", "restarted");
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.d("my", "resumed");

		IntentFilter filter = new IntentFilter();
		filter.addAction(CommonApplication.ON_POST_EXECUTE);
		filter.addAction(CommonApplication.ON_PRE_EXECUTE);
		filter.addAction(CommonApplication.ON_PROGRESS_UPDATE);

		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				// do something based on the intent's action
				// Log.d("my-", intent.getAction());
				if (intent.getAction()
						.equals(CommonApplication.ON_POST_EXECUTE)) {
					hideLoading();
					removeTask();
					//success();
				} else if (intent.getAction().equals(
						CommonApplication.ON_PRE_EXECUTE)) {
					showLoading();
				} else if (intent.getAction().equals(
						CommonApplication.ON_PROGRESS_UPDATE)) {
					showProgress(intent.getStringExtra(CommonApplication.TEXT));
				}
			}

		};
		registerReceiver(receiver, filter);

		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		// TODO Auto-generated method stub
		Log.d("my", "config changed");
		return super.onRetainNonConfigurationInstance();
	}

	@Override
	protected void onDestroy() {
		Log.d("my", "destroyed");
		// taskCreatorStorage.clear();
		// taskCreatorStorage = null;
		super.onDestroy();
	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}

		Toast.makeText(getApplicationContext(), R.string.not_internet,
				Toast.LENGTH_LONG).show();
		return false;
	}

}
