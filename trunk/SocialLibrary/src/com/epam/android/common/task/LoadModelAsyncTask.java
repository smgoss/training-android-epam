package com.epam.android.common.task;

import java.io.IOException;

import org.json.JSONException;

import com.epam.android.common.model.IModelCreator;

public abstract class LoadModelAsyncTask<B> extends CommonModelAsyncTask<B> {

	public LoadModelAsyncTask(String url, IDelegate delegate) {
		super(url, delegate);

	}

	public LoadModelAsyncTask(String url, IDelegate delegate,
			IModelCreator<B> modelCreator) {
		super(url, delegate, modelCreator);
	}

	@Override
	public B load() throws IOException, JSONException {
		return mLoader.loadModel(getUrl(), mModelCreator);
	}

	@Override
	public abstract void success(B result);

}
