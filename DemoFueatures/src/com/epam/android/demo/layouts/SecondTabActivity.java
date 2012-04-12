package com.epam.android.demo.layouts;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.epam.android.demo.common.utils.JsonModelConverter;
import com.epam.android.demo.layouts.adapter.List2Adapter;
import com.epam.android.demo.social.R;
import com.epam.android.demo.social.model.Other;

public class SecondTabActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_second_tab_layout);
		List<Other> mList = null;
		try {
			JSONArray array = new JSONArray(
					"[{'number'='1','userName'='Vladimir', 'image'='http://cs10183.vk.com/u308327/a_4de259c4.jpg'},  {'number'='2','userName'='Vova','image'='http://cs10183.vk.com/u308327/a_4de259c4.jpg'}, {'number'='3','userName'='Istin','image'='http://cs10183.vk.com/u308327/a_4de259c4.jpg'}]");
			mList = JsonModelConverter.convertJSONArrayToList(array,
					Other.MODEL_CREATOR);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ListView mListView = (ListView) findViewById(R.id.listView2);
		mListView.setAdapter(new List2Adapter(
				SecondTabActivity.this, R.layout.custom_list2_item, mList));
	}
}
