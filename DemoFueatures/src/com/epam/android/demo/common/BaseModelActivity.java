package com.epam.android.demo.common;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.epam.android.demo.common.model.BaseModel;
import com.epam.android.demo.common.task.CommonAsyncTask;
import com.epam.android.demo.common.task.LoadModelAsyncTask;

public abstract class BaseModelActivity<B extends BaseModel> extends
		DelegateActivity {

	private static final String TAG = BaseModelActivity.class.getName();

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
			Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT);
			Log.d(TAG, "Nothing to show");
		}
	}

	protected abstract void success(B result);

}
