package com.epam.android.social;

import java.io.IOException;

import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.IDelegate;

import android.app.Activity;
import android.content.Context;

public class DelegateActivity extends Activity implements IDelegate {

	public void showloading() {
		// TODO show loading dialog
		
	}

	public void hideloading() {
		// TODO hide loading dialog
		
	}

	public void handleError(CommonAsyncTask task, IOException e) {
		// TODO show toast message with error
		
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
