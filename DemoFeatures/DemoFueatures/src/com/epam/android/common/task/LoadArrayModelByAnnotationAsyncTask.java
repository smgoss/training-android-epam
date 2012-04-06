package com.epam.android.common.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.content.Intent;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

public class LoadArrayModelByAnnotationAsyncTask<B extends BaseModel> extends
		CommonModelAsyncTask<List<B>> {

	public LoadArrayModelByAnnotationAsyncTask(String url, IDelegate delegate) {
		super(url, delegate, (IModelCreator<B>) BaseModel
				.getModelCreatorFromTemplate(delegate));
	}

	@SuppressWarnings("rawtypes")
	public LoadArrayModelByAnnotationAsyncTask(String url, IDelegate context,
			IModelCreator iModelCreator) {
		super(url, context, iModelCreator);
	}

	@SuppressWarnings("unchecked")
	public List<B> load() throws IOException, JSONException {
		return (List<B>) getLoader()
				.loadArrayModelByAnnotation(getUrl(), getModelCreator());
	}

	@Override
	protected void initIntentResult(Intent intent, List<B> result) {
		intent.putParcelableArrayListExtra(RESULT, (ArrayList<B>) result);
	}

}
