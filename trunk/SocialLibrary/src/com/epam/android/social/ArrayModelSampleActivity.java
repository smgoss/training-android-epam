package com.epam.android.social;

import java.util.List;

import android.content.Intent;
import android.widget.ListView;

import com.epam.android.common.BaseArrayModelActivity;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.social.adapter.ArrayModelListAdapter;
import com.epam.android.social.model.User;

public class ArrayModelSampleActivity extends BaseArrayModelActivity<User> {

	@SuppressWarnings("unused")
	private static final String TAG = ArrayModelSampleActivity.class
			.getSimpleName();

	public static final String URL = "http://dl.dropbox.com/u/16403954/array_bm.json";

	private ListView mListView;

	@Override
	public int getLayoutResource() {
		return R.layout.load_array_model;
	}

	@Override
	public void success(Intent intent) {
		mListView = (ListView) findViewById(R.id.array_model_list);
		List<User> users = intent
				.getParcelableArrayListExtra(CommonAsyncTask.RESULT);
		mListView.setAdapter(new ArrayModelListAdapter(
				ArrayModelSampleActivity.this, R.layout.load_model, users));
	}

	@Override
	public String getUrl() {
		return URL;
	}

	public String getKey() {
		return URL;
	}

}
