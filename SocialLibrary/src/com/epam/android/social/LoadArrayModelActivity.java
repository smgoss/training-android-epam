package com.epam.android.social;


import com.epam.android.common.adapter.ArrayModelListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class LoadArrayModelActivity extends Activity {

	private ListView mListView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loadarraymodel);
		Toast.makeText(this, "TODO LoadArrayModel undone!", Toast.LENGTH_SHORT)
				.show();
		// TODO Load username and avatar

		mListView = (ListView) findViewById(R.id.arraymodellist);
		
		
		 //setListAdapter(new ArrayModelListAdapter(
		 //LoadArrayModelActivity.this,
		 //R.layout.loadmodel, list<T>));
	}
	
	protected void setListAdapter(ArrayModelListAdapter friendListAdapter) {
		if (mListView != null) {
			mListView.setAdapter(friendListAdapter);
		}
	}
}
