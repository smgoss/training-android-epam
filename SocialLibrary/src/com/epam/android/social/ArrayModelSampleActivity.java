package com.epam.android.social;

import java.util.List;

import android.widget.ListView;

import com.epam.android.common.task.LoadArrayModelAsyncTask;
import com.epam.android.social.adapter.ArrayModelListAdapter;
import com.epam.android.social.model.User;

public class ArrayModelSampleActivity extends DelegateActivity {

	private static final String TAG = ArrayModelSampleActivity.class
			.getSimpleName();

	public static final String URL = "http://dl.dropbox.com/u/16403954/array_bm.json";

	public static final String MODEL = "User";

	private ListView mListView;
	
	@Override
	public void onCreate() {
		setContentView(R.layout.load_array_model);

		new LoadArrayModelAsyncTask<User>(URL, this) {

			public void success(List<User> result) {
				mListView = (ListView) findViewById(R.id.array_model_list);
				mListView.setAdapter(new ArrayModelListAdapter(
						ArrayModelSampleActivity.this, R.layout.load_model,
						result));
			}

		}.execute();
	}



}
