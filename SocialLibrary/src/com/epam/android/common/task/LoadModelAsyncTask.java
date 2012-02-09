package com.epam.android.common.task;

import java.io.IOException;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.epam.android.common.http.HttpClient;
import com.epam.android.common.model.IModelCreator;
import com.epam.android.common.model.Loader;
import com.epam.android.social.ModelSampleActivity;
import com.google.android.imageloader.ImageLoader;

public abstract class LoadModelAsyncTask<B> extends CommonAsyncTask<B> {

	protected Loader mLoader;

	private IModelCreator mModelCreator;

	public LoadModelAsyncTask(String url, IDelegate delegate,
			IModelCreator modelCreator, Context context) {
		super(url, delegate);
		mModelCreator = modelCreator;
		mLoader = (Loader) context.getApplicationContext().getSystemService(
				Loader.LOADER);
	}

	@Override
	public B load() throws IOException, JSONException {
		return (B) mLoader.loadModel(getUrl(), mModelCreator);
	}

	@Override
	public abstract void success(B result);

}
