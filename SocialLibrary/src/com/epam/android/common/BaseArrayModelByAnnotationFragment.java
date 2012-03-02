package com.epam.android.common;

import java.util.List;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.LoadArrayModelByAnnotationAsyncTask;
import com.epam.android.social.model.Tweet;

public abstract class BaseArrayModelByAnnotationFragment<B extends BaseModel> extends
		DelegateFragment {

	private static final String TAG = BaseArrayModelByAnnotationFragment.class.getName();

	public abstract String getUrl();

	@Override
	public void startTasks() {
		executeActivityTasks(new LoadArrayModelByAnnotationAsyncTask<B>(getUrl(), this,getActivity()));
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
