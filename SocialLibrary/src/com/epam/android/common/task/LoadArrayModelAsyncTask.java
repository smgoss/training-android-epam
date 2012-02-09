package com.epam.android.common.task;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import com.epam.android.common.model.IModelCreator;

public abstract class LoadArrayModelAsyncTask<B> extends
		CommonModelAsyncTask<List<B>> {

	public LoadArrayModelAsyncTask(String url, IDelegate delegate,
			IModelCreator modelCreator) {
		super(url, delegate, modelCreator);

	}

	public List<B> load() throws IOException, JSONException {
		return (List<B>) mLoader.loadArrayModel(getUrl(), mModelCreator);
	}

	@Override
	public abstract void success(List<B> result);

}
