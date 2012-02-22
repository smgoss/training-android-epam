package com.epam.android.layouts;

import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.epam.android.social.R;
import com.epam.android.social.model.Other;

public class SecondTabActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_tab_layout);
		
//		JSONArray jsonArray = new JSONArray([{"number"="1","userName"="Vladimir","image"="http://cs10183.vk.com/u308327/a_4de259c4.jpg"},
//		                                     {"number"="2","userName"="Vova","image"="http://cs10183.vk.com/u308327/a_4de259c4.jpg"},
//		                                     {"number"="3","userName"="Istin","image"="http://cs10183.vk.com/u308327/a_4de259c4.jpg"}]);
		
		List<Other> mList = null;
		ListView mListView = (ListView) findViewById(R.id.listView2);
		mListView.setAdapter(new List2Adapter(
				SecondTabActivity.this, R.layout.list2_item, mList));
	}
}
