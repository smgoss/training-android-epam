package com.epam.android.common.task;

import java.io.IOException;

import org.json.JSONException;

import com.epam.android.common.model.IModelCreator;
import com.epam.android.common.model.Loader;

public abstract class LoadModelAsyncTask<B> extends CommonAsyncTask<B> {

	private IModelCreator mModelCreator;
	
	public LoadModelAsyncTask(String url, IDelegate delegate, IModelCreator modelCreator) {
		super(url, delegate);
		mModelCreator = modelCreator;
	}

	@Override
	public B load(String url) throws IOException, JSONException {
		// TODO Load json to model
		return (B)new Loader(getDelegate().getContext()).load(url, mModelCreator);
	}

	@Override
	public abstract void success(B result);

}
