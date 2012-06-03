package com.epam.android.social.common.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.epam.android.common.BaseArrayModelByAnnotationFragment;
import com.epam.android.common.model.BaseModel;
import com.epam.android.social.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public abstract class BaseArrayModelByAnnotationFragmentWithCustomLoadAndSaveItems<B extends BaseModel>
		extends BaseArrayModelByAnnotationFragment<B> {

	private View mProgressBar;

	private ListView mListView;

	private PullToRefreshListView mPullRefreshListView;

	@Override
	public void showLoading() {
		if (getListView().getCount() == 0) {
			mProgressBar = (View) getView().findViewById(
					R.id.progress_bar_on_listView);
			mProgressBar.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void showProgress(String textMessage) {
		if (getListView().getCount() == 0) {
			mProgressBar = (View) getView().findViewById(
					R.id.progress_bar_on_listView);
			mProgressBar.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void hideLoading() {
		if (getListView().getCount() == 0) {
			if (mProgressBar != null
					&& mProgressBar.getVisibility() == View.VISIBLE) {
				mProgressBar.setVisibility(View.INVISIBLE);
			}

			LinearLayout linearLayout = (LinearLayout) getView().findViewById(
					R.id.changeProfile_mainLayout);
			if (linearLayout != null) {
				linearLayout.setVisibility(View.VISIBLE);
			}
		}

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
