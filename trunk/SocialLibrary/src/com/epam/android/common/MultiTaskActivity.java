package com.epam.android.common;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.epam.android.common.task.AsyncTaskManager;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.ITaskCreator;

//TODO move logic to delegate activity
public abstract class MultiTaskActivity extends DelegateActivity {

	private static final String TAG = MultiTaskActivity.class.getName();


	protected void onResume() {
		super.onResume();

//		for (int i = 0; i < mTasks.size(); i++) {
//			if (mAsyncTaskManager.checkTask(this.getClass().getName(), mTasks
//					.get(i).getUrl())) {
//				getResult(mAsyncTaskManager.getTask(this.getClass().getName(),
//						mTasks.get(i).getUrl()));
//			} else {
//				executeAsyncTask(mTasks.get(i));
//			}
//		}
	}

	protected void executeAsyncTask(final CommonAsyncTask task) {

		executeTask(new ITaskCreator() {
			public CommonAsyncTask create() {
				return task;
			}
		});
	}

	protected void getResult(CommonAsyncTask task) {
		if (task.getStatus().equals(AsyncTask.Status.RUNNING)) {
			showLoading();
		} else if (task.getStatus().equals(AsyncTask.Status.FINISHED)) {

			Intent intent = new Intent();
			intent.putExtra(CommonAsyncTask.TASK_KEY, task.getUrl());
			onTaskPostExecute(intent);
		}
	}


	//TODO read about it
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	
	
	
}
