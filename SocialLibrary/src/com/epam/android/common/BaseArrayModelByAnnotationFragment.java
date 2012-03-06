package com.epam.android.common;

import java.util.Collection;
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
	
	private List<B> result = null;

	@Override
	public void startTasks() {
		executeActivityTasks(new LoadArrayModelByAnnotationAsyncTask<B>(getUrl(), this));
	}

	@Override
	public void success(Intent intent) {

		if (isAsyncTaskResult(getUrl(), intent)) {
			if (result == null) {
				result = intent
						.getParcelableArrayListExtra(CommonAsyncTask.RESULT);
			}
			else{
				Log.d(TAG, "work success");
					result.addAll((Collection<? extends B>) intent
							.getParcelableArrayListExtra(CommonAsyncTask.RESULT));
			}
			success(result);
		} else {
			Toast.makeText(getActivity(), "Nothing to show", Toast.LENGTH_SHORT);
			Log.d(TAG, "Nothing to show");
		}
	}

	protected abstract void success(List<B> result);

}
