package com.epam.android.common;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.LoadModelAsyncTask;

public abstract class BaseModelFragment<B extends BaseModel>  extends DelegateFragment{

	private static final String TAG = BaseModelFragment.class.getName();

	public abstract String getUrl();

	@Override
	public void startTasks() {
		executeActivityTasks(new LoadModelAsyncTask<B>(getUrl(), this));

	}

	@Override
	public void success(Intent intent) {
		if (isAsyncTaskResult(getUrl(), intent)) {
			B result = intent.getParcelableExtra(CommonAsyncTask.RESULT);
			success(result);
		} else {
			Toast.makeText(getContext(), "Nothing to show", Toast.LENGTH_SHORT).show();
			Log.d(TAG, "Nothing to show");
		}
	}

	protected abstract void success(B result);

}
