package com.epam.android.social;

import java.util.List;

import android.os.Bundle;
import android.widget.Toast;

import com.commonsware.cwac.tlv.demo.TouchListView;
import com.commonsware.cwac.tlv.demo.TouchListView.DropListener;
import com.commonsware.cwac.tlv.demo.TouchListView.RemoveListener;
import com.epam.android.common.BaseArrayModelActivity;
import com.epam.android.social.adapter.ArrayModelListAdapter;
import com.epam.android.social.model.User;

public class ArrayModelSampleActivity extends BaseArrayModelActivity<User>
		implements RemoveListener, DropListener{

	@SuppressWarnings("unused")
	private static final String TAG = ArrayModelSampleActivity.class
			.getSimpleName();

	public static final String URL = "http://dl.dropbox.com/u/16403954/array_bm.json";

	private TouchListView mListView;

	private ArrayModelListAdapter adapter;

	@Override
	public int getLayoutResource() {
		return R.layout.touch_list;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public String getUrl() {
		return URL;
	}

	@Override
	protected void success(List<User> users) {
		mListView = (TouchListView) findViewById(R.id.m_touch_list);
		adapter = new ArrayModelListAdapter(ArrayModelSampleActivity.this,
				R.layout.load_model, users);
		mListView.setAdapter(adapter);
		mListView.setDropListener(this);
		mListView.setRemoveListener(this);

	}

	@Override
	public void remove(int which) {
		Toast.makeText(this, "remove", Toast.LENGTH_SHORT).show();
		adapter.remove(which);
		adapter.notifyDataSetChanged();

	}

	@Override
	public void drop(int from, int to) {

	}

	

}
