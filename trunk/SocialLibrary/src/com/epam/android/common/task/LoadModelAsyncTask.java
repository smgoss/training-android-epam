package com.epam.android.common.task;

import java.io.IOException;

import org.json.JSONException;

import android.content.Context;
import android.content.Intent;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

public  class LoadModelAsyncTask<B extends BaseModel> extends
		CommonModelAsyncTask<B> {

	public LoadModelAsyncTask(String url, IDelegate delegate,Context context) {
		super(url, context, (IModelCreator<B>) BaseModel
				.getModelCreatorFromTemplate(delegate));
	}

	public LoadModelAsyncTask(String url, Context context,
			IModelCreator<B> modelCreator) {
		super(url, context, modelCreator);
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
