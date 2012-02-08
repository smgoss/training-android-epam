package com.epam.android.social;


import com.epam.android.common.adapter.ArrayModelListAdapter;
import com.epam.android.common.model.BaseModel;
import com.epam.android.common.task.LoadModelAsyncTask;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class ArrayModelSampleActivity extends DelegateActivity {

	private ListView mListView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_array_model);
		Toast.makeText(this, "TODO LoadArrayModel undone!", Toast.LENGTH_SHORT)
				.show();
		// TODO Load username and avatar

		mListView = (ListView) findViewById(R.id.array_model_list);
		
		
		 //setListAdapter(new ArrayModelListAdapter(
		 //LoadArrayModelActivity.this,
		 //R.layout.loadmodel, list<T>));
		new LoadModelAsyncTask<BaseModel>(this) {

			@Override
			public void success(BaseModel result) {
				// TODO Auto-generated method stub
				
			}
			
		};
	}
	
	protected void setListAdapter(ArrayModelListAdapter friendListAdapter) {
		if (mListView != null) {
			mListView.setAdapter(friendListAdapter);
		}
	}
}
