package com.epam.android.social;

import android.os.Bundle;

import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.ITaskCreator;
import com.epam.android.common.task.LoadModelAsyncTask;

public abstract class BaseModelActivity<B> extends DelegateActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResource());
		executeTask(new ITaskCreator() {

			public CommonAsyncTask create() {

				return new LoadModelAsyncTask<B>(getUrl(),
						BaseModelActivity.this) {

					@Override
					public void success(B result) {
						BaseModelActivity.this.success(result);
					}
					
				};
			}

		});
	}

	public abstract int getLayoutResource();

	public abstract void success(B result);

	public abstract String getUrl();
	

}
