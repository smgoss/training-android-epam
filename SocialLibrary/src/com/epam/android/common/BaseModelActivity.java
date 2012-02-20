package com.epam.android.common;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.task.AsyncTaskManager;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.ITaskCreator;
import com.epam.android.common.task.LoadModelAsyncTask;

public abstract class BaseModelActivity<B extends BaseModel> extends
		DelegateActivity {

	private static final String TAG = BaseModelActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResource());
		mAsyncTaskManager = AsyncTaskManager.get(this);
		mAsyncTaskManager.addActivityTasks(this.getClass()
				.getName());
	}

	public abstract int getLayoutResource();

	public abstract String getUrl();

	@SuppressWarnings("rawtypes")
	@Override
	protected void onResume() {
		super.onResume();
		CommonAsyncTask task = mAsyncTaskManager.getTask(this.getClass()
				.getName(), getKey());
		if (task != null) {
			getResult(task);
		} else {
			executeAsyncTask();
		}
	}

	protected void executeAsyncTask() {
		final CommonAsyncTask task = new LoadModelAsyncTask<B>(getUrl(),
				BaseModelActivity.this) {
		};

		executeTask(new ITaskCreator() {
			@SuppressWarnings("unchecked")
			public CommonAsyncTask create() {
				return task;
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void getResult(CommonAsyncTask task) {
		if (task.getStatus().equals(AsyncTask.Status.FINISHED)) {
			try {
				Intent intent = new Intent();
				intent.putExtra(CommonAsyncTask.RESULT, (B) task.get());
				onTaskPostExecute(intent);

			} catch (InterruptedException e) {
				Log.d(TAG,
						"crash thread waiting, sleeping, and the thread is aborted");

			} catch (ExecutionException e) {

				Log.d(TAG, "crash get result on aborted task ", e);
			}
		} else {
			// TODO send some status of task
		}
	}

	public List<CommonAsyncTask> getTasks() {
		// TODO Auto-generated method stub
		return null;
	}
}
