package com.epam.android.common;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;
import com.epam.android.common.task.AsyncTaskManager;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.ITaskCreator;
import com.epam.android.common.task.LoadArrayModelAsyncTask;

//TODO move to some common activity
public abstract class BaseArrayModelActivity<B extends BaseModel> extends
		DelegateActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResource());
		mAsyncTaskManager = AsyncTaskManager.get(this);

	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void onResume() {
		super.onResume();
		CommonAsyncTask task = mAsyncTaskManager.getTask(getKey());
		if (task != null) {
			getResult(task);
		} else {
			executeAsyncTask();
		}
	}

	protected void executeAsyncTask() {
		executeTask(new ITaskCreator() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public CommonAsyncTask create() {
				return new LoadArrayModelAsyncTask<B>(
						getUrl(),
						BaseArrayModelActivity.this,
						(IModelCreator<B>) BaseModel
								.getModelCreatorFromTemplate(BaseArrayModelActivity.this)) {

				};
			}
		});

	}

	public abstract int getLayoutResource();

	public abstract String getUrl();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void getResult(CommonAsyncTask task) {
		if (task.getStatus().equals(AsyncTask.Status.FINISHED)) {
			try {
				Intent intent = new Intent();
				intent.putParcelableArrayListExtra(CommonAsyncTask.RESULT,
						(ArrayList<B>) task.get());

				onTaskPostExecute(intent);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
