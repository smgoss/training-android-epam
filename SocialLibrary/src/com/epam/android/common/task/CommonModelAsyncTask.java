package com.epam.android.common.task;

import java.io.IOException;

import org.json.JSONException;

import android.content.Context;

import com.epam.android.common.http.Loader;
import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

public abstract class CommonModelAsyncTask<B> extends CommonAsyncTask<B> {

	private Loader mLoader;

	private IModelCreator<B> mModelCreator;

	public Loader getLoader() {
		if (mLoader == null) {
			this.mLoader = Loader.get((Context) getDelegate());
		}
		return mLoader;
	}

	@SuppressWarnings({ "unchecked" })
	public IModelCreator<B> getModelCreator() {
		if (mModelCreator == null) {
			this.mModelCreator = (IModelCreator<B>) BaseModel
					.getModelCreatorFromTemplate(this);
		}
		return mModelCreator;
	}

	 public CommonModelAsyncTask(String url, IDelegate delegate) {
	 super(url, delegate);
	 this.mModelCreator = getModelCreator();
	 }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CommonModelAsyncTask(String url, IDelegate delegate,
			IModelCreator modelCreator) {
		super(url, delegate);
		this.mModelCreator = modelCreator;
	}

	public abstract B load() throws IOException, JSONException;

}