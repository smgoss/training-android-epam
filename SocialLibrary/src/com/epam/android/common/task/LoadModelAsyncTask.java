package com.epam.android.common.task;

import java.io.IOException;

import org.json.JSONException;

import android.content.Context;

import com.epam.android.common.http.Loader;
import com.epam.android.common.model.IModelCreator;
//TODO create common model asynk task
public abstract class LoadModelAsyncTask<B> extends CommonAsyncTask<B> {

	protected Loader mLoader;

	private IModelCreator<B> mModelCreator;

	//TODO get model creator with reflection
	public LoadModelAsyncTask(String url, IDelegate delegate,
			IModelCreator<B> modelCreator, Context context) {
		super(url, delegate);
		mModelCreator = modelCreator;
		mLoader = (Loader) context.getApplicationContext().getSystemService(
				Loader.LOADER);
	}

	@Override
	public B load() throws IOException, JSONException {
		return mLoader.loadModel(getUrl(), mModelCreator);
	}

	@Override
	public abstract void success(B result);

}
