package com.epam.android.common.task;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import android.content.Context;

import com.epam.android.common.model.IModelCreator;
import com.epam.android.common.model.Loader;

public abstract class LoadArrayModelAsyncTask<B> extends
		CommonAsyncTask<List<B>> {

	protected Loader mLoader;

	private IModelCreator mModelCreator;

	public LoadArrayModelAsyncTask(String url, IDelegate delegate,
			IModelCreator modelCreator, Context context) {
		super(url, delegate);
		mModelCreator = modelCreator;
		mLoader = (Loader) context.getApplicationContext().getSystemService(
				Loader.LOADER);
	}

	public List<B> load() throws IOException, JSONException {
		return  mLoader.loadArrayModel(getUrl(), mModelCreator);
	}

	@Override
	public abstract void success(List<B> result);

}
