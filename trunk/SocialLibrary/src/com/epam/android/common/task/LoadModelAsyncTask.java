package com.epam.android.common.task;

import java.io.IOException;

import org.json.JSONException;

public abstract class LoadModelAsyncTask<B> extends CommonModelAsyncTask<B> {

	// TODO get model creator with reflection
	public LoadModelAsyncTask(String url, IDelegate delegate) {
		super(url, delegate);

	}

	@Override
	public B load() throws IOException, JSONException {
		return mLoader.loadModel(getUrl(), mModelCreator);
	}

	@Override
	public abstract void success(B result);

}
