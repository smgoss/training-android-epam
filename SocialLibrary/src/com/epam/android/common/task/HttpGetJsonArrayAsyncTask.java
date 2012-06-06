package com.epam.android.common.task;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.epam.android.common.http.Loader;
import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

public abstract class HttpGetJsonArrayAsyncTask<B extends BaseModel> extends
		AsyncTask<String, Void, List<B>> {
	private static final String TAG = HttpGetJsonArrayAsyncTask.class.getSimpleName();

	private Context mContext;

	private IModelCreator<B> mModelCreator;

	public HttpGetJsonArrayAsyncTask(Context context) {
		mContext = context;
	}

	@Override
	protected List<B> doInBackground(String... params) {
		for (int i = 0; i < params.length; i++) {
			try {
				return Loader.get(mContext).loadArrayModel(params[i],
						getModelCreator());
			} catch (ClientProtocolException e) {
				Log.e(TAG, "Error on HTTP protocol", e);
			} catch (IOException e) {
				Log.e(TAG, "crash during loading data", e);
			} catch (JSONException e) {
				Log.d(TAG, "eror on get json array");
			}
		}
		return null;
	}

	public abstract void success(List<B> result);

	@Override
	protected void onPostExecute(List<B> result) {
		super.onPostExecute(result);
		success(result);
	}

	@SuppressWarnings({ "unchecked" })
	public IModelCreator<B> getModelCreator() {
		if (mModelCreator == null) {
			this.mModelCreator = (IModelCreator<B>) BaseModel
					.getModelCreatorFromTemplate(this);
		}
		return mModelCreator;
	}

}
