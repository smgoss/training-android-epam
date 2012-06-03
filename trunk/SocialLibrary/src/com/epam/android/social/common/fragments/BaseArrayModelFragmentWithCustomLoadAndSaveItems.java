package com.epam.android.social.common.fragments;

import android.view.View;
import android.widget.LinearLayout;

import com.epam.android.common.model.BaseModel;
import com.epam.android.social.R;

public abstract class BaseArrayModelFragmentWithCustomLoadAndSaveItems<B extends BaseModel>
		extends BaseArrayModelFragmentWithSaveItems<B> {

	private View mProgressBar;

	@Override
	public void showLoading() {
		if (getListView().getCount() == 0) {
			mProgressBar = (View) getView().findViewById(
					getProgressBarResource());
			mProgressBar.setVisibility(View.VISIBLE);

		}
	}

	@Override
	public void showProgress(String textMessage) {
		if (getListView().getCount() == 0) {
			mProgressBar = (View) getView().findViewById(
					getProgressBarResource());
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

	public abstract int getProgressBarResource();

}
