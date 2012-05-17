package com.epam.android.social.common.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.epam.android.common.BaseArrayModelByAnnotationFragment;
import com.epam.android.common.model.BaseModel;
import com.epam.android.social.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

public abstract class BaseArrayModelByAnnotationFragmentWithCustomLoadAndSaveItems<B extends BaseModel>
		extends BaseArrayModelByAnnotationFragment<B> {

	private ProgressBar mProgressBar;

	private boolean isLoading;

	private ListView mListView;

	private PullToRefreshListView mPullRefreshListView;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (isLoading) {
			showLoading();
		}
	}

	@Override
	public void showLoading() {
		mProgressBar = (ProgressBar) getView().findViewById(
				R.id.progress_bar_on_listView);
		mProgressBar.setVisibility(View.VISIBLE);

		isLoading = true;
	}

	@Override
	public void showProgress(String textMessage) {
		mProgressBar = (ProgressBar) getView().findViewById(
				R.id.progress_bar_on_listView);
		mProgressBar.setVisibility(View.VISIBLE);
		isLoading = true;
	}

	@Override
	public void hideLoading() {
		if (mProgressBar != null
				&& mProgressBar.getVisibility() == View.VISIBLE) {
			mProgressBar.setVisibility(View.INVISIBLE);
		}

		RelativeLayout relativeLayout = (RelativeLayout) getView()
				.findViewById(R.id.changeProfile_mainLayout);
		if (relativeLayout != null) {
			relativeLayout.setVisibility(View.VISIBLE);
		}

		isLoading = false;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (getCurrentList() != null && getCurrentList().size() != 0) {
			outState.putParcelableArrayList(getDelegateKey(),
					(ArrayList<? extends Parcelable>) getCurrentList());
		}

	}

	private void restoreFragment(Bundle savedInstanceState) {

		if (savedInstanceState != null) {
			if (getCurrentList() != null && getCurrentList().size() != 0) {
				setList((List<B>) savedInstanceState
						.getParcelableArrayList(getDelegateKey()));
			}
		}

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mPullRefreshListView = (PullToRefreshListView) getView().findViewById(
				R.id.pull_refresh_list);
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				onRefreshStart();

			}
		});

		mListView = mPullRefreshListView.getRefreshableView();
		if (savedInstanceState != null) {
			restoreFragment(savedInstanceState);
		}

	}

	public ListView getListView() {
		return mListView;
	}

	protected void onRefreshCompele() {
		mPullRefreshListView.onRefreshComplete();
	}
	
	public abstract <B extends BaseModel> void setList(List<B> list);

	public abstract List<B> getCurrentList();
	
	public abstract void onRefreshStart();

}
