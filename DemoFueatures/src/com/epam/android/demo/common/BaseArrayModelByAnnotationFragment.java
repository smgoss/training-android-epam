package com.epam.android.demo.common;

import java.util.List;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.epam.android.demo.common.model.BaseModel;
import com.epam.android.demo.common.task.CommonAsyncTask;
import com.epam.android.demo.common.task.LoadArrayModelByAnnotationAsyncTask;

public abstract class BaseArrayModelByAnnotationFragment<B extends BaseModel> extends
		DelegateFragment {

	private static final String TAG = BaseArrayModelByAnnotationFragment.class.getName();

	public abstract String getUrl();

	@Override
	public void startTasks() {
		executeActivityTasks(new LoadArrayModelByAnnotationAsyncTask<B>(getUrl(), this));
	}

	@Override
	public void success(Intent intent) {
		if (isAsyncTaskResult(getUrl(), intent)) {
			List<B> result = intent
					.getParcelableArrayListExtra(CommonAsyncTask.RESULT);
			success(result);
		} else {
			Toast.makeText(getActivity(), "Nothing to show", Toast.LENGTH_SHORT);
			Log.d(TAG, "Nothing to show");
		}
	}

	protected abstract void success(List<B> result);

}
