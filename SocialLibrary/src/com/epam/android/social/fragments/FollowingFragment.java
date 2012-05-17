package com.epam.android.social.fragments;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.LoadArrayModelAsyncTask;
import com.epam.android.common.task.LoadModelAsyncTask;
import com.epam.android.social.R;
import com.epam.android.social.adapter.FollowingAdapter;
import com.epam.android.social.api.TwitterAPI;
import com.epam.android.social.common.fragments.DelegateFragmentWithCustomLoad;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.model.Following;
import com.epam.android.social.model.PreFollowing;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

public class FollowingFragment extends DelegateFragmentWithCustomLoad {

	public static final String TAG = FollowingFragment.class.getSimpleName();

	private static String followingURL;

	private FollowingAdapter adapter;

	private List<Following> followingList;

	private PullToRefreshListView mPullRefreshListView;

	private ListView mListView;

	public static FollowingFragment newInstance(String query) {
		Bundle bundle = new Bundle();
		FollowingFragment fragment = new FollowingFragment();
		bundle.putString(ApplicationConstants.ARG_QUERY, query);
		fragment.setArguments(bundle);
		return fragment;
	}

	private FollowingFragment() {

	}

	@Override
	public int getLayoutResource() {
		return R.layout.load_array_model;
	}

	@Override
	public void startTasks() {
		executeActivityTasks(new LoadModelAsyncTask<PreFollowing>(
				getArguments().getString(ApplicationConstants.ARG_QUERY), this,
				PreFollowing.MODEL_CREATOR));
	}

	@Override
	public void success(Intent intent) {
		if (isAsyncTaskResult(
				getArguments().getString(ApplicationConstants.ARG_QUERY),
				intent)) {
			PreFollowing preFollowing = intent
					.getParcelableExtra(CommonAsyncTask.RESULT);

			followingURL = TwitterAPI.getInstance().getShortProfileInfo(
					preFollowing.getFriendsID());
			executeActivityTasks(new LoadArrayModelAsyncTask<Following>(
					followingURL, this, Following.MODEL_CREATOR));

		}

		if (isAsyncTaskResult(followingURL, intent)) {
			followingList = intent
					.getParcelableArrayListExtra(CommonAsyncTask.RESULT);
			adapter = new FollowingAdapter(getView().getContext(),
					R.layout.follow, followingList);
			mPullRefreshListView = (PullToRefreshListView) getView()
					.findViewById(R.id.pull_refresh_list);
			mPullRefreshListView.setOnRefreshListener(new OnRefreshListener() {

				@Override
				public void onRefresh() {
					mPullRefreshListView.onRefreshComplete();

				}
			});

			mListView = mPullRefreshListView.getRefreshableView();
			mListView.setAdapter(adapter);
		}

	}

	public FollowingAdapter getAdapter() {
		return adapter;
	}

	public List<Following> getFollowingList() {
		return followingList;
	}

}
