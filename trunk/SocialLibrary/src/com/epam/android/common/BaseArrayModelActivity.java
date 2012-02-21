package com.epam.android.common;

import java.util.List;

import android.content.Intent;
import android.util.Log;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.task.LoadArrayModelAsyncTask;

public abstract class BaseArrayModelActivity<B extends BaseModel> extends
		MultiTaskActivity {

	private static final String TAG = BaseArrayModelActivity.class.getName();

	public abstract String getUrl();

	public void setTasks() {
		if (!isAddToList(getUrl())) {
			mTasks.add(new LoadArrayModelAsyncTask<B>(getUrl(), this));
		}
	}

	@Override
	protected void success(Intent intent) {
		List<B> result = (List<B>) sucessResult(intent, getUrl());
		if (result != null) {
			success(result);
		} else {
			// TODO what if no result
			Log.d(TAG, "Nothing to show");
		}
	}

	protected abstract void success(List<B> result);

}
