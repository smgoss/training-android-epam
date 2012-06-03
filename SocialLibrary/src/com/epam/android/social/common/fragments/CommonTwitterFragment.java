package com.epam.android.social.common.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.epam.android.common.model.BaseModel;
import com.epam.android.social.R;
import com.epam.android.social.constants.ApplicationConstants;

public abstract class CommonTwitterFragment<T extends BaseModel> extends
		BaseArrayModelFragmentWithCustomLoadAndSaveItems<T> {

	private static final String TAG = CommonTwitterFragment.class
			.getSimpleName();

	private String delegateKey;

	private List<T> currentList;

	private BaseAdapter adapter;

	private int loadedPage = 1;

	private static final int loadedItems = 20;

	private boolean loadingMore = false;

	private View footerView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		footerView = LayoutInflater.from(getContext()).inflate(
				R.layout.progress_on_list, null, false);
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

			adapter.notifyDataSetChanged();

		}

		if (result.size() < loadedItems) {
			hideFooterView();
		}

		loadingMore = false;
	}

	private void hideFooterView() {

		getListView().removeFooterView(footerView);
	}

	@Override
	public int getLayoutResource() {
		return R.layout.load_array_model;
	}

	public abstract BaseAdapter createAdapter(List<? extends BaseModel> list);

	@Override
	public <B extends BaseModel> void setList(List<B> list) {
		adapter = createAdapter(list);
		if (list.size() >= loadedItems) {

			getListView().addFooterView(footerView);
			getListView().setOnScrollListener(new OnScrollListener() {

				@Override
				public void onScrollStateChanged(AbsListView view,
						int scrollState) {

				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					int lastInScreen = firstVisibleItem + visibleItemCount;

					if ((lastInScreen == totalItemCount) && !(loadingMore) && totalItemCount != 0) {
						loadedPage++;
						startTasks();
						loadingMore = true;
					}

				}
			});
		}
		getListView().setAdapter(adapter);

	}

	@Override
	public List<T> getCurrentList() {
		return currentList;
	}

	@Override
	public void onRefreshStart() {
		Toast.makeText(getContext(), "onRefreesh", Toast.LENGTH_SHORT).show();
		onRefreshCompele();
	}

}
