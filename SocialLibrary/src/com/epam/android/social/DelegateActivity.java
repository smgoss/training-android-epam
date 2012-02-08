package com.epam.android.social;

import java.io.IOException;

import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.IDelegate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class DelegateActivity extends Activity implements IDelegate {

	private static final String TAG = DelegateActivity.class.getSimpleName();

	private static final String TITLE = "Basic title";

	private static final String MSG = "Basic message";

	private ProgressDialog mProgressDialog;

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

	public void handleError(CommonAsyncTask task, IOException e) {
		Log.e(TAG, "http client err: " + e.getMessage(), e);
		Toast.makeText(getContext(), "http client err: " + e.getMessage(),
				Toast.LENGTH_SHORT).show();
	}

	public Context getContext() {
		return this;
	}

	public void addTask(CommonAsyncTask task) {
		// TODO list storage of task if task started

	}

	public void removeTask(CommonAsyncTask task) {
		// TODO remove task if task ends or canceled

	}

}
