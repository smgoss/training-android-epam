package com.epam.android.common.task;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import com.epam.android.common.model.IModelCreator;

public abstract class LoadArrayModelAsyncTask<B> extends
		CommonModelAsyncTask<B> {

	public LoadArrayModelAsyncTask(String url, IDelegate delegate) {
		super(url, delegate);

	}
	
	public LoadArrayModelAsyncTask(String url, IDelegate delegate,
			IModelCreator<B> iModelCreator) {
		super(url, delegate, iModelCreator);
	}

	public B load() throws IOException, JSONException {
		return (B) getLoader().loadArrayModel(getUrl(), getModelCreator());
	}

	@Override
	public abstract void success(B result);

}
