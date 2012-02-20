package com.epam.android.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;
import com.epam.android.common.task.AsyncTaskManager;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.ITaskCreator;
import com.epam.android.common.task.LoadModelAsyncTask;

public abstract class MultiTaskActivity extends
		DelegateActivity {

	private static final String TAG = MultiTaskActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResource());
		mAsyncTaskManager = AsyncTaskManager.get(this);
		if (!mAsyncTaskManager.checkActivity(TAG)) {
			mAsyncTaskManager.addActivity(TAG);
		}
		String foo="foo";
	}

	public abstract int getLayoutResource();

	protected void onResume() {
		super.onResume();
		//TODO get list of created tasks
		// CommonAsyncTask task = mAsyncTaskManager.getTask(getKey());
		// if (task != null) {
		// TODO getResult(task);
		// } else {
		executeListAsyncTask(getTasks());
		//}
		// TODO getListOfTasks();
	}

	
	protected abstract List<CommonAsyncTask> getTasks();
	

	protected void executeListAsyncTask(List<CommonAsyncTask> tasks) {
		for (int i=0; i < tasks.size(); i++) {
			executeAsyncTask(tasks.get(i));
			Log.d(TAG, tasks.get(i).toString());
		}
	}
	
	protected void executeAsyncTask(final CommonAsyncTask task) {
				
		executeTask(new ITaskCreator() {
			@SuppressWarnings("unchecked")
			public CommonAsyncTask create() {
				return task;
			}
		});
	}
	
	// FIXME what to do with tasks on resume
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	protected void getResult(CommonAsyncTask task) {
//		if (task.getStatus().equals(AsyncTask.Status.FINISHED)) {
//			try {
//				Intent intent = new Intent();
//				intent.putExtra(CommonAsyncTask.RESULT, (B) task.get());
//				onTaskPostExecute(intent);
//
//			} catch (InterruptedException e) {
//				Log.d(TAG,
//						"crash thread waiting, sleeping, and the thread is aborted");
//
//			} catch (ExecutionException e) {
//
//				Log.d(TAG, "crash get result on aborted task ", e);
//			}
//		} else {
//			// TODO send some status of task
//		}
//	}

}
