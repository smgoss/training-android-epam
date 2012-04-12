package com.epam.android.demo.common;

import java.util.List;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.epam.android.demo.common.model.BaseModel;
import com.epam.android.demo.common.task.CommonAsyncTask;
import com.epam.android.demo.common.task.LoadArrayModelFromXMLAsyncTask;

public abstract class BaseArrayModelFromXmlAcivity<B extends BaseModel> extends DelegateActivity {

	
	private static final String TAG = BaseArrayModelFromXmlAcivity.class.getName();
	
	public abstract String getUrl();
	
	@Override
	public void startTasks() {
		executeActivityTasks(new LoadArrayModelFromXMLAsyncTask<B>(getUrl(), this));
	}


	@Override
	public void success(Intent intent) {
		if (isAsyncTaskResult(getUrl(), intent)) {
			List<B> result = intent
					.getParcelableArrayListExtra(CommonAsyncTask.RESULT);
			success(result);
		} else {
			Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT);
			Log.d(TAG, "Nothing to show");
		}
		
	}
	
	protected abstract void success(List<B> result);

}
