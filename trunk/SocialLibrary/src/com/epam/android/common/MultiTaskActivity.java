package com.epam.android.common;

import java.util.ArrayList;
import java.util.List;

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
		mAsyncTaskManager.addActivity(this.getClass().getName());
		if (mTasks == null) {
			mTasks = new ArrayList<CommonAsyncTask>();
		}
		setTasks();
	}

	public abstract int getLayoutResource();

	public abstract List<CommonAsyncTask> setTasks();

	protected void onResume() {
		super.onResume();

		for (int i = 0; i < mTasks.size(); i++) {
			if (mAsyncTaskManager.checkTask(this.getClass().getName(), mTasks
					.get(i).getUrl())) {
				Log.d(TAG, "task exists");
				// getResult(mAsyncTaskManager.getTask(this.getClass().getName(),
				// tasks.get(i).getUrl()));
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
		// if (task.getStatus().equals(AsyncTask.Status.FINISHED)) {
		// try {
		// Intent intent = new Intent();
		// intent.putExtra(CommonAsyncTask.RESULT, (B) task.get());
		// onTaskPostExecute(intent);
		//
		// } catch (InterruptedException e) {
		// Log.d(TAG,
		// "crash thread waiting, sleeping, and the thread is aborted");
		//
		// } catch (ExecutionException e) {
		//
		// Log.d(TAG, "crash get result on aborted task ", e);
		// }
		// } else {
		// TODO send some status of task
		// }
	}

}
