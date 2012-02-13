package com.epam.android.social;

import java.util.List;

import android.os.Bundle;
import android.util.Log;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.CommonModelAsyncTask;
import com.epam.android.common.task.ITaskCreator;
import com.epam.android.common.task.LoadArrayModelAsyncTask;
import com.epam.android.common.task.LoadModelAsyncTask;

public abstract class BaseArrayModelActivity<B> extends DelegateActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResource());

		CommonAsyncTask task = new LoadArrayModelAsyncTask<B>(
				getUrl(),
				BaseArrayModelActivity.this,
				(IModelCreator<B>) BaseModel
						.getModelCreatorFromTemplate(BaseArrayModelActivity.this)) {

			@Override
			public void success(B result) {
				BaseArrayModelActivity.this.success(result);
			}

		};

	}

	public abstract int getLayoutResource();

	public abstract void success(B result);

	public abstract String getUrl();

}
