package com.epam.android.social;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.IDelegate;
import com.epam.android.common.task.ITaskCreator;

public class DelegateActivity extends Activity implements IDelegate {

	private static final String TAG = DelegateActivity.class.getSimpleName();

	private static final String TITLE = "Please wait";

	private static final String MSG = "Loading...";
	
	protected ProgressDialog mProgressDialog;

	public void showloading() {
		if (mProgressDialog == null) {
			mProgressDialog = ProgressDialog.show(getContext(), TITLE, MSG,
					false, true);
		} else {
			mProgressDialog.show();
		}
	}

	public void hideloading() {
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	public void handleError(CommonAsyncTask task, Exception e) {
		Log.e(TAG, "http client err: " + e.getMessage(), e);
		Toast.makeText(getContext(), "http client err: " + e.getMessage(),
				Toast.LENGTH_LONG).show();
		taskCreatorStorage.get(task).create().execute();
		
	}

	public Context getContext() {
		return this;
	}

	private HashMap<CommonAsyncTask, ITaskCreator> taskCreatorStorage = new HashMap<CommonAsyncTask, ITaskCreator>();
	

	public void removeTask(CommonAsyncTask task) {
		// TODO remove task if task ends or canceled
		taskCreatorStorage.remove(task);
	}

	public void executeTask(ITaskCreator taskCreator) {
		CommonAsyncTask task = taskCreator.create();
		taskCreatorStorage.put(task, taskCreator);
		task.execute();
	}

	@Override
	protected void onDestroy() {
		taskCreatorStorage.clear();
		taskCreatorStorage = null;
		super.onDestroy();
	}

	
	
}
