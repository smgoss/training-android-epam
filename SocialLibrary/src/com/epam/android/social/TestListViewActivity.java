package com.epam.android.social;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.epam.android.common.http.HttpClient;
import com.epam.android.common.http.Loader;
import com.epam.android.social.adapter.ArrayModelListAdapter;
import com.epam.android.social.model.User;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class TestListViewActivity extends Activity{

	private static final String TAG = ArrayModelSampleActivity.class
			.getSimpleName();

	public static final String URL = "http://dl.dropbox.com/u/16403954/array_bm.json";

	private ListView mListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_array_model);
		Loader load = (Loader) getApplicationContext().getSystemService(Loader.LOADER);
		mListView = (ListView) findViewById(R.id.array_model_list);
		try {
			mListView.setAdapter(new ArrayModelListAdapter(
					TestListViewActivity.this, R.layout.load_model, load.loadArrayModel(URL, User.MODEL_CREATOR)));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
