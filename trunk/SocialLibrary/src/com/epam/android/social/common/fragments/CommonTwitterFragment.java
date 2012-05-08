package com.epam.android.social.common.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.epam.android.common.model.BaseModel;
import com.epam.android.social.R;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.fragments.TweetTimeLineFragment;

public abstract class CommonTwitterFragment<T extends BaseModel> extends
		BaseArrayModelFragmentWithCustomLoadAndSaveItems<T> {

	private static final String TAG = CommonTwitterFragment.class
			.getSimpleName();

	private Button loadMore;

	private String delegateKey;

	private List<T> currentList;

	private BaseAdapter adapter;

	private int loadedPage = 1;
		
	private static final int loadedItems = 20;
	
	private static final int loadMoreButtonID = 444;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadMore = new Button(getContext());
		loadMore.setText("load more");
		loadMore.setId(loadMoreButtonID);
		loadMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loadedPage++;
				startTasks();

			}
		});
		Log.d(TAG, "onCreate");
	}

	@Override
	public String getUrl() {
		return getArguments().getString(ApplicationConstants.ARG_QUERY)
				+ loadedPage;
	}

	@Override
	public String getDelegateKey() {
		if (delegateKey == null) {
			delegateKey = getArguments().getString(
					ApplicationConstants.ARG_QUERY)
					+ getArguments().getString(
							ApplicationConstants.ARG_PROFILE_NAME);
			Log.d(TAG, "delegate key=" + delegateKey);
		}
		return delegateKey;
	}

	@Override
	protected void success(List<T> result) {

		if (currentList == null) {
			currentList = new ArrayList<T>();
			currentList.addAll(result);
			setList(currentList);
		} else {
			currentList.addAll(result);
			if(result.size() != loadedItems){
				hideLoadMoreButton();
			}
			adapter.notifyDataSetChanged();

		}
	}

	private void hideLoadMoreButton() {
		if(loadMore!=null && getView().findViewById(loadMoreButtonID) != null){
			getListView().removeView(loadMore);
		}
	}

	@Override
	public int getLayoutResource() {
		return R.layout.load_array_model;
	}

	public abstract BaseAdapter createAdapter(List<? extends BaseModel> list);

	@Override
	public <B extends BaseModel> void setList(List<B> list) {
		adapter = createAdapter(list);
		if (list.size() == loadedItems) {
			getListView().addFooterView(loadMore);
		}
		getListView().setAdapter(adapter);

	}

	@Override
	public List<T> getCurrentList() {
		return currentList;
	}

}
