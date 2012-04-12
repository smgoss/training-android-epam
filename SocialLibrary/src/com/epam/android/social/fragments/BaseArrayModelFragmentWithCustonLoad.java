package com.epam.android.social.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.epam.android.common.BaseArrayModelFragment;
import com.epam.android.common.model.BaseModel;
import com.epam.android.social.R;

public abstract class BaseArrayModelFragmentWithCustonLoad<B extends BaseModel>
		extends BaseArrayModelFragment<B> {
	private ProgressBar mProgressBar;

	private boolean isLoading;

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
		isLoading = false;
	}

}
