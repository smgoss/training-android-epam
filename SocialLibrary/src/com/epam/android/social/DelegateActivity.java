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

	private static final Integer TASK_LIFETIME = 10001;

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
		Log.d("my DA", "task removed" + mAsyncTaskManager.getTask(getKey()));
		mAsyncTaskManager.removeTask(getKey());
	}

	public void executeTask(ITaskCreator taskCreator) {
		CommonAsyncTask task = taskCreator.create();
		Log.d("my DA", "added " + task.toString());
		mAsyncTaskManager.addTask(getKey(), task);
		task.start();
	}

	public abstract String getKey();

	@Override
	protected void onPause() {
		Log.d("my DA", "paused on Pause");
		unregisterReceiver(receiver);
		if (mAsyncTaskManager.getTask(getKey()) != null) {
			mAsyncTaskManager.getTask(getKey()).setToBeCancelled(true);
		}
		killTask();
		// CommonAsyncTask task = mAsyncTaskManager.getTask(getKey());
		// task.cancel(true);
		// hideLoading();
		// TODO Do smth with task on pause, on BackKeyPressed

		super.onPause();
	}

	public void killTask() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(TASK_LIFETIME);
					if (mAsyncTaskManager.getTask(getKey()) != null
							&& mAsyncTaskManager.getTask(getKey())
									.isToBeCancelled()) {
						removeTask();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
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
						showLoading();
					} else if (intent.getAction().equals(
							CommonAsyncTask.ON_POST_EXECUTE)) {
						hideLoading();
						removeTask();
						success(intent);
					} else if (intent.getAction().equals(
							CommonAsyncTask.ON_PROGRESS_UPDATE)) {
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
		Log.d("my DA", "destroyed onDestroy");
		// taskCreatorStorage.clear();
		// taskCreatorStorage = null;
		super.onDestroy();
	}

}
