package com.epam.android.social;

import java.util.List;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.epam.android.common.adapter.ArrayModelListAdapter;
import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.User;
import com.epam.android.common.task.LoadArrayModelAsyncTask;
import com.epam.android.common.task.LoadModelAsyncTask;

public class ArrayModelSampleActivity extends DelegateActivity {

	public static final String URL = "http://dl.dropbox.com/u/16403954/array_bm.json";
	
	private ListView mListView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_array_model);

		mListView = (ListView) findViewById(R.id.array_model_list);

		new LoadArrayModelAsyncTask<User>(URL, this, User.MODEL_CREATOR, this) {

			public void success(List<User> result) {
				setListAdapter(new ArrayModelListAdapter(
						ArrayModelSampleActivity.this, R.layout.load_model,
						result));
			}

		};
	}

	protected void setListAdapter(ArrayModelListAdapter friendListAdapter) {
		if (mListView != null) {
			mListView.setAdapter(friendListAdapter);
		}
	}
}
