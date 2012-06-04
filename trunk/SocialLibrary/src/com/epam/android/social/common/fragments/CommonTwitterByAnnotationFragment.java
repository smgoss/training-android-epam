package com.epam.android.social.common.fragments;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.task.LoadArrayModelByAnnotationAsyncTask;

public abstract class CommonTwitterByAnnotationFragment<T extends BaseModel>
		extends CommonTwitterFragment<T> {

	@Override
	public void startTasks() {
		executeActivityTasks(new LoadArrayModelByAnnotationAsyncTask<T>(getUrl(), this));
	}
}
