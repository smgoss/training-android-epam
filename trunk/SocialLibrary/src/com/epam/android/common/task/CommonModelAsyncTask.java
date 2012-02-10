package com.epam.android.common.task;

import java.io.IOException;
import java.io.ObjectStreamField;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.epam.android.common.http.Loader;
import com.epam.android.common.model.IModelCreator;
import com.epam.android.social.model.User;

public abstract class CommonModelAsyncTask<B> extends CommonAsyncTask<B> {

	protected Loader mLoader;

	protected IModelCreator<B> mModelCreator;

	public CommonModelAsyncTask(String url, IDelegate delegate) {
		super(url, delegate);

		Class someClass = (Class) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		Field modelCreator = someClass.getDeclaredFields()[1];

		try {
			mModelCreator = (IModelCreator<B>) modelCreator.get(this);
		} catch (IllegalArgumentException e) {
			// TODO what about this error
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO what about this error
			e.printStackTrace();
		}

		mLoader = (Loader) delegate.getContext().getApplicationContext()
				.getSystemService(Loader.LOADER);

	}

	public abstract B load() throws IOException, JSONException;

	public abstract void success(B result);

}
