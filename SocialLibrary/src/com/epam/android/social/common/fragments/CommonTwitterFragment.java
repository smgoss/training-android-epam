package com.epam.android.social.common.fragments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.task.AsyncTaskManager;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.social.R;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.constants.ReceiverConstants;
import com.epam.android.social.model.Tweet;
import com.epam.android.social.receiver.AlarmUpdateTimeLineReceiver;

public abstract class CommonTwitterFragment<T extends BaseModel> extends
		BaseArrayModelFragmentWithCustomLoadAndSaveItems<T> {

	private static final String TAG = CommonTwitterFragment.class
			.getSimpleName();

	private String delegateKey;

	private List<T> currentList;

	private BaseAdapter adapter;

	private static final int itemsOnPage = 19;

	private View footerView;

	private BroadcastReceiver mReceiver;

	private static CommonTwitterFragment.IRefreshQuery refreshQueryInterface;

	private AsyncTaskManager mAsyncTaskManager;

	private List<T> updateList;

	private boolean loadMore;

	private enum STATUS_LOAD {
		REFRESHING, LOADING
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		footerView = LayoutInflater.from(getContext()).inflate(
				R.layout.progress_on_list, null, false);
		if (getArguments().getString(ApplicationConstants.ARG_BASE_QUERY) == null) {
			getArguments().putString(ApplicationConstants.ARG_BASE_QUERY,
					getArguments().getString(ApplicationConstants.ARG_QUERY));
		}

		if (getArguments().getBoolean(
				ApplicationConstants.GET_MESSAGES_FROM_RECEIVER)) {
			registerReceiver();
			AlarmUpdateTimeLineReceiver.setAlarm(getContext());
			refreshQueryInterface = new IRefreshQuery() {

				@Override
				public String getRefreshQuery() {

					if (currentList != null) {

						Long itemID = currentList.get(0).getItemID();
						if (itemID != null) {
							mAsyncTaskManager.removeTask(
									getDelegateKey(),
									getArguments().getString(
											ApplicationConstants.ARG_QUERY));
							return getArguments().getString(
									ApplicationConstants.ARG_BASE_QUERY)
									+ "&since_id=" + itemID;

						}
					}

					return "";
				}
			};
		}
		mAsyncTaskManager = AsyncTaskManager.get(getActivity());
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
	public void success(Intent intent) {
		super.success(intent);
		String url = intent.getStringExtra(CommonAsyncTask.TASK_KEY);
		if (currentList == null) {
			currentList = new ArrayList<T>();
		}

		if (url.contains("since_id")) {
			currentList.addAll(0, updateList);
			onRefreshCompele();

		}

		else if (url.contains("max_id")) {
			updateList.remove(0);
			currentList.addAll(currentList.size(), updateList);
			loadMore = false;

		}

		else {
			currentList.addAll(updateList);
		}

		if (!url.contains("since_id")) {
			if (updateList.size() >= itemsOnPage) {
				addFooterView();
			} else {
				removeFooterView();
				loadMore = true;
			}
		}

		if (adapter == null) {
			setList(currentList);
		} else {
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	protected void success(List<T> result) {
		updateList = result;
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

					if ((lastInScreen == totalItemCount) && totalItemCount != 0
							&& !loadMore) {
						loadMore = true;
						generateQuery(STATUS_LOAD.LOADING);

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
		mAsyncTaskManager.removeTask(getDelegateKey(), getArguments()
				.getString(ApplicationConstants.ARG_QUERY));
		generateQuery(STATUS_LOAD.REFRESHING);
	}

	protected synchronized void generateQuery(STATUS_LOAD status) {
		if (currentList != null) {
			Long itemID;
			if (status == STATUS_LOAD.REFRESHING) {
				itemID = currentList.get(0).getItemID();
				if (itemID != null) {
					getArguments().remove(ApplicationConstants.ARG_QUERY);
					getArguments().putString(
							ApplicationConstants.ARG_QUERY,
							getArguments().getString(
									ApplicationConstants.ARG_BASE_QUERY)
									+ "&since_id=" + itemID);
				}
			}
			if (status == STATUS_LOAD.LOADING) {
				itemID = currentList.get(currentList.size() - 1).getItemID();
				if (itemID != null) {
					getArguments().remove(ApplicationConstants.ARG_QUERY);
					getArguments().putString(
							ApplicationConstants.ARG_QUERY,
							getArguments().getString(
									ApplicationConstants.ARG_BASE_QUERY)
									+ "&max_id=" + itemID);
				}
			}

			Log.d(TAG, "!start task with url = " + getUrl());
			startTasks();
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

	private void registerReceiver() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(ReceiverConstants.ON_UPDATE_COMLETE);

		mReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context paramContext, Intent paramIntent) {
				List<Tweet> list = paramIntent
						.getParcelableArrayListExtra(ApplicationConstants.LIST_FROM_SERVICE);
				if (list != null && list.size() > 0) {
					currentList.addAll(0, (Collection<? extends T>) list);
					adapter.notifyDataSetChanged();

				}
			}
		};

		getActivity().registerReceiver(mReceiver, filter);

	}

	@Override
	public void onDestroy() {
		if (getArguments().getBoolean(
				ApplicationConstants.GET_MESSAGES_FROM_RECEIVER)) {
			getActivity().unregisterReceiver(mReceiver);
		}
		super.onDestroy();

	}

	public static interface IRefreshQuery {
		public String getRefreshQuery();
	}

	public static CommonTwitterFragment.IRefreshQuery getRefreshQueryInterface() {
		return refreshQueryInterface;
	}

}
