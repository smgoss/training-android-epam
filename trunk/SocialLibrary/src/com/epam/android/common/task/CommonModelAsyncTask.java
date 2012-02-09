package com.epam.android.common.task;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;

import org.json.JSONException;

import com.epam.android.common.http.Loader;
import com.epam.android.common.model.IModelCreator;

public abstract class CommonModelAsyncTask<B> extends CommonAsyncTask<B> {

	protected Loader mLoader;

	protected IModelCreator<B> mModelCreator;

	public CommonModelAsyncTask(String url, IDelegate delegate,
			IModelCreator<B> modelCreator) {
		super(url, delegate);
		
		Class someClass = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//		mModelCreator = someClass.getDeclaredFields()[1];
		
		mModelCreator = modelCreator;
		mLoader = (Loader) delegate.getContext().getApplicationContext()
				.getSystemService(Loader.LOADER);

	}

	public abstract B load() throws IOException, JSONException;

	public abstract void success(B result);

}
