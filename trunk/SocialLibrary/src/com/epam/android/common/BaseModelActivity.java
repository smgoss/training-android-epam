package com.epam.android.common;

import android.content.Intent;
import android.util.Log;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.task.LoadModelAsyncTask;

public abstract class BaseModelActivity<B extends BaseModel> extends
		MultiTaskActivity {

	private static final String TAG = BaseModelActivity.class.getName();

	public abstract String getUrl();

	@Override
	public void setTasks() {
		if (!addToList(getUrl())) {
			mTasks.add(new LoadModelAsyncTask<B>(getUrl(), this));
		}
	}

	@Override
	protected void success(Intent intent) {
		B result = (B) sucessResult(intent, getUrl());
		if (result != null) {
			success(result);
		} else {
			// TODO what if no result
			Log.d(TAG, "Nothing to show");
		}
	}

	protected abstract void success(B result);

}
