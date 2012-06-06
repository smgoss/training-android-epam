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
import com.epam.android.common.task.AsyncTaskManager;
import com.epam.android.social.R;
import com.epam.android.social.constants.ApplicationConstants;

public abstract class CommonTwitterFragment<T extends BaseModel> extends
		BaseArrayModelFragmentWithCustomLoadAndSaveItems<T> {

	private static final String TAG = CommonTwitterFragment.class
			.getSimpleName();

	private String delegateKey;

	private List<T> currentList;

	private BaseAdapter adapter;

	private static final int itemsOnPage = 19;

	private String query;

	private STATUS_LOAD status;

	private View footerView;

	private enum STATUS_LOAD {
		REFRESHING, LOADING
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		query = getArguments().getString(ApplicationConstants.ARG_QUERY);
		footerView = LayoutInflater.from(getContext()).inflate(
				R.layout.progress_on_list, null, false);
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

		if (status == STATUS_LOAD.LOADING || status == null) {
			if (result.size() >= itemsOnPage) {
				addFooterView();
			} else {
				removeFooterView();
			}
		}

		if (status == STATUS_LOAD.REFRESHING) {
			onRefreshCompele();
			status = STATUS_LOAD.LOADING;
		}
	}

	private void addItems(List<T> list, STATUS_LOAD status) {
		if (status == STATUS_LOAD.LOADING) {
			if (list.size() != 0) {
				list.remove(0);
			}
			currentList.addAll(list);
		}

		else {
			currentList.addAll(0, list);
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
		if (list.size() >= itemsOnPage) {

			getListView().setOnScrollListener(new OnScrollListener() {

				@Override
				public void onScrollStateChanged(AbsListView view,
						int scrollState) {

				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					int lastInScreen = firstVisibleItem + visibleItemCount;

					if ((lastInScreen == totalItemCount) && totalItemCount != 0) {
						status = STATUS_LOAD.LOADING;
						generateQuery();
						startTasks();

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
		AsyncTaskManager.get(getActivity()).removeTask(getDelegateKey(),
				getArguments().getString(ApplicationConstants.ARG_QUERY));
		generateQuery();
		startTasks();
	}

	protected void generateQuery() {
		if (currentList != null) {
			Long itemID;
			if (status == STATUS_LOAD.REFRESHING) {
				itemID = currentList.get(0).getItemID();
				if (itemID != null) {
					getArguments().remove(ApplicationConstants.ARG_QUERY);
					getArguments().putString(ApplicationConstants.ARG_QUERY,
							query + "&since_id=" + itemID);
				}
			}
			if (status == STATUS_LOAD.LOADING) {
				itemID = currentList.get(currentList.size() - 1).getItemID();
				if (itemID != null) {
					getArguments().remove(ApplicationConstants.ARG_QUERY);
					getArguments().putString(ApplicationConstants.ARG_QUERY,
							query + "&max_id=" + itemID);
				}
			}
		}
	}

	private void addFooterView() {
		if (getListView().getFooterViewsCount() < 2) {
			getListView().addFooterView(footerView);
		}
	}

	private void removeFooterView() {
		if (getListView().getFooterViewsCount() == 2) {
			getListView().removeFooterView(footerView);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(ApplicationConstants.FOOTER_COUNT, getListView()
				.getFooterViewsCount());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null) {
			int footerCount = savedInstanceState
					.getInt(ApplicationConstants.FOOTER_COUNT);
			if (footerCount > 1) {
				addFooterView();
			}
		}
	}

}
