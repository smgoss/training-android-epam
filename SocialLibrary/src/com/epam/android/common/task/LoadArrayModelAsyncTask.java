package com.epam.android.common.task;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

public abstract class LoadArrayModelAsyncTask<B> extends
		CommonModelAsyncTask<List<B>> {

	public LoadArrayModelAsyncTask(String url, IDelegate delegate) {
		super(url, delegate);

	}

	public List<B> load() throws IOException, JSONException {
		return (List<B>) mLoader.loadArrayModel(getUrl(), mModelCreator);
	}

	@Override
	public abstract void success(List<B> result);

}
