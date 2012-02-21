package com.epam.android.common;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.epam.android.common.task.AsyncTaskManager;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.ITaskCreator;

public abstract class MultiTaskActivity extends DelegateActivity {

	private static final String TAG = MultiTaskActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResource());
		mAsyncTaskManager = AsyncTaskManager.get(this);
		mAsyncTaskManager.addActivityTasks(this.getClass().getName());
		if (mTasks == null) {
			mTasks = new ArrayList<CommonAsyncTask>();
		}
		setTasks();
	}

	public abstract int getLayoutResource();

	protected void onResume() {
		super.onResume();

		for (int i = 0; i < mTasks.size(); i++) {
			if (mAsyncTaskManager.checkTask(this.getClass().getName(), mTasks
					.get(i).getUrl())) {
				getResult(mAsyncTaskManager.getTask(this.getClass().getName(),
						mTasks.get(i).getUrl()));
			} else {
				executeAsyncTask(mTasks.get(i));
			}
		}
	}

	protected void executeAsyncTask(final CommonAsyncTask task) {

		executeTask(new ITaskCreator() {
			public CommonAsyncTask create() {
				return task;
			}
		});
	}

	// FIXME what to do with tasks on resume
	// @SuppressWarnings({ "unchecked", "rawtypes" })
	protected void getResult(CommonAsyncTask task) {
		if (task.getStatus().equals(AsyncTask.Status.RUNNING)) {
			showLoading();
		} else if (task.getStatus().equals(AsyncTask.Status.FINISHED)) {

			Intent intent = new Intent();
			intent.putExtra(CommonAsyncTask.TASK_KEY, task.getUrl());
			onTaskPostExecute(intent);
		}

	}

}
