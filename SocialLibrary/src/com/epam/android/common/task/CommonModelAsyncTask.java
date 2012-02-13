package com.epam.android.common.task;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import org.json.JSONException;

import android.content.Context;

import com.epam.android.common.http.Loader;
import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;
import com.epam.android.social.BaseModelActivity;

public abstract class CommonModelAsyncTask<B> extends CommonAsyncTask<B> {

	//TODO private and gets, sets
	private Loader mLoader;

	private IModelCreator<B> mModelCreator;

	public Loader getLoader() {
		return mLoader;
	}

	public void setLoader(Loader loader) {
		this.mLoader = loader;
	}

	public IModelCreator<B> getModelCreator() {
		return mModelCreator;
	}

	public void setModelCreator(IModelCreator<B> modelCreator) {
		this.mModelCreator = modelCreator;
	}

	public CommonModelAsyncTask(String url, IDelegate delegate) {
		super(url, delegate);

		setModelCreator((IModelCreator<B>) BaseModel.getModelCreatorFromTemplate(this));
		setLoader(Loader.get((Context) getDelegate()));
	}
	
	public CommonModelAsyncTask(String url, IDelegate delegate, IModelCreator<B> modelCreator) {
		super(url, delegate);
		setModelCreator(modelCreator);
		setLoader(Loader.get((Context) getDelegate()));
		
	}

	public abstract B load() throws IOException, JSONException;

	public abstract void success(B result);

}
