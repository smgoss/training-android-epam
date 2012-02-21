package com.epam.android.common.task;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

public class LoadArrayModelAsyncTask<B extends BaseModel> extends
		CommonModelAsyncTask<List<B>> {

	public LoadArrayModelAsyncTask(String url, IDelegate delegate) {
		super(url, delegate, (IModelCreator<B>) BaseModel
				.getModelCreatorFromTemplate(delegate));
	}

	@SuppressWarnings("rawtypes")
	public LoadArrayModelAsyncTask(String url, IDelegate delegate,
			IModelCreator iModelCreator) {
		super(url, delegate, iModelCreator);
	}

	@SuppressWarnings("unchecked")
	public List<B> load() throws IOException, JSONException {
		return (List<B>) getLoader()
				.loadArrayModel(getUrl(), getModelCreator());
	}

//	@Override
//	protected void initIntentResult(Intent intent, List<B> result) {
//		intent.putParcelableArrayListExtra(RESULT, (ArrayList<B>) result);
//	}

}
