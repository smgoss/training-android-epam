package com.epam.android.social;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;
import com.epam.android.common.task.AsyncTaskManager;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.ITaskCreator;
import com.epam.android.common.task.LoadModelAsyncTask;

public abstract class BaseModelActivity<B extends BaseModel> extends
		DelegateActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResource());
		mAsyncTaskManager = AsyncTaskManager.get(this);

	}

	public abstract int getLayoutResource();

	public abstract String getUrl();

	@Override
	protected void onResume() {
		super.onResume();
		if (mAsyncTaskManager.getTask(getKey()) == null) {

			executeTask(new ITaskCreator() {

				public CommonAsyncTask<B> create() {

					return new LoadModelAsyncTask<B>(
							getUrl(),
							BaseModelActivity.this,
							(IModelCreator<B>) BaseModel
									.getModelCreatorFromTemplate(BaseModelActivity.this)) {

					};
				}

			});
		}
	}

}
