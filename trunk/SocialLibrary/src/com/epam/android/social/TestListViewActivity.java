package com.epam.android.social;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class TestListViewActivity extends Activity{

	@SuppressWarnings("unused")
	private static final String TAG = ArrayModelSampleActivity.class
			.getSimpleName();

	public static final String URL = "http://dl.dropbox.com/u/16403954/array_bm.json";

	private ListView mListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_array_model);
//		Login login = new Login();
		
	}
	
}
