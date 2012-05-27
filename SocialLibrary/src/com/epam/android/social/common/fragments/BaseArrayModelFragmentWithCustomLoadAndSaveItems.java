package com.epam.android.social.common.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ListView;

import com.epam.android.common.model.BaseModel;
import com.epam.android.social.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public abstract class BaseArrayModelFragmentWithCustomLoadAndSaveItems<B extends BaseModel>
		extends BaseArrayModelFragmentWithCustomLoad<B> {

	private ListView mListView;
	private PullToRefreshListView mPullRefreshListView;

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (getCurrentList() != null && getCurrentList().size() != 0) {
			outState.putParcelableArrayList(getDelegateKey(),
					(ArrayList<? extends Parcelable>) getCurrentList());
		}
		super.onSaveInstanceState(outState);

	}

	private void restoreFragment(Bundle savedInstanceState) {

		if (getCurrentList() != null && getCurrentList().size() != 0) {
			setList((List<B>) savedInstanceState
					.getParcelableArrayList(getDelegateKey()));
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
