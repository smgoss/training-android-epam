package com.epam.android.social;

import java.util.List;

import android.os.Bundle;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.ITaskCreator;
import com.epam.android.common.task.LoadArrayModelAsyncTask;

public abstract class BaseArrayModelActivity<B extends BaseModel> extends
		DelegateActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResource());
		executeTask(new ITaskCreator() {

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

}
