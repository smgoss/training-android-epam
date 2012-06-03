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

	private static final int loadedItems = 19;

	private boolean loadingMore = false;

	private View footerView;

	private String query;

	private STATUS_LOAD status = STATUS_LOAD.LOADING;

	private enum STATUS_LOAD {
		REFRESHING, LOADING
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		footerView = LayoutInflater.from(getContext()).inflate(
				R.layout.progress_on_list, null, false);
		query = getArguments().getString(ApplicationConstants.ARG_QUERY);
		Log.d(TAG, "onCreate");
	}

	@Override
	public String getUrl() {
		return getArguments().getString(ApplicationConstants.ARG_QUERY);
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
			addItems(result, status);
			setList(currentList);
		} else {
			addItems(result, status);

			adapter.notifyDataSetChanged();

		}

		if (result.size() < loadedItems) {
			hideFooterView();
		}

		loadingMore = false;
		if (status == STATUS_LOAD.REFRESHING) {
			onRefreshCompele();
			status = STATUS_LOAD.LOADING;
		}
	}

	private void addItems(List<T> list, STATUS_LOAD status) {
		if (status == STATUS_LOAD.LOADING) {
			if (currentList.size() != 0) {
				list.remove(0);
			}
			currentList.addAll(list);
		}

		else {
			currentList.addAll(0, list);
		}
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

					if ((lastInScreen == totalItemCount) && !(loadingMore)
							&& totalItemCount != 0) {
						generateQuery(STATUS_LOAD.LOADING);
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
		status = STATUS_LOAD.REFRESHING;
		generateQuery(STATUS_LOAD.REFRESHING);
		startTasks();
	}

	private void generateQuery(STATUS_LOAD status) {
		getArguments().remove(ApplicationConstants.ARG_QUERY);
		Long itemID;
		if (status == STATUS_LOAD.REFRESHING) {
			itemID = currentList.get(0).getItemID();
			if (itemID != null) {
				getArguments().putString(ApplicationConstants.ARG_QUERY,
						query + "&since_id=" + itemID);
			}
		}
		if (status == STATUS_LOAD.LOADING) {
			itemID = currentList.get(currentList.size() - 1).getItemID();
			if (itemID != null) {
				getArguments().putString(ApplicationConstants.ARG_QUERY,
						query + "&max_id=" + itemID);
			}
		}
	}

}
