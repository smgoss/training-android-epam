package com.epam.android.common.task;

import java.io.IOException;

import org.json.JSONException;

import android.content.Intent;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

public  class LoadModelAsyncTask<B extends BaseModel> extends
		CommonModelAsyncTask<B> {

	public LoadModelAsyncTask(String url, IDelegate delegate) {
		super(url, delegate, (IModelCreator<B>) BaseModel
				.getModelCreatorFromTemplate(delegate));
	}

	public LoadModelAsyncTask(String url, IDelegate delegate,
			IModelCreator<B> modelCreator) {
		super(url, delegate, modelCreator);
	}

	@Override
	public B load() throws IOException, JSONException {
		return getLoader().loadModel(getUrl(), getModelCreator());
	}

	@Override
	protected void initIntentResult(Intent intent, B result) {
		intent.putExtra(RESULT, result);
	}

}
