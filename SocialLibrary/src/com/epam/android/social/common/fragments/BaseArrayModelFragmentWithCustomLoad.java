package com.epam.android.social.common.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.epam.android.common.BaseArrayModelFragment;
import com.epam.android.common.model.BaseModel;
import com.epam.android.social.R;

public abstract class BaseArrayModelFragmentWithCustomLoad<B extends BaseModel>
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
				getProgressBarResource());
		mProgressBar.setVisibility(View.VISIBLE);

		isLoading = true;
	}

	@Override
	public void showProgress(String textMessage) {
		mProgressBar = (ProgressBar) getView().findViewById(
				getProgressBarResource());
		mProgressBar.setVisibility(View.VISIBLE);
		isLoading = true;
	}

	@Override
	public void hideLoading() {
		if (mProgressBar != null
				&& mProgressBar.getVisibility() == View.VISIBLE) {
			mProgressBar.setVisibility(View.INVISIBLE);
		}

		LinearLayout linearLayout = (LinearLayout) getView().findViewById(
				R.id.changeProfile_mainLayout);
		if (linearLayout != null) {
			linearLayout.setVisibility(View.VISIBLE);
		}

		isLoading = false;
	}

	public abstract int getProgressBarResource();

}
