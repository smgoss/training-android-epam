package com.epam.android.social.fragments;

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

public abstract class BaseArrayModelByAnnotationFragmentWithCustomLoadAndSaveItems<B extends BaseModel>
		extends BaseArrayModelByAnnotationFragment<B> {

	private ProgressBar mProgressBar;

	private boolean isLoading;

	private List<B> currentList;

	private ListView mListView;
	
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
		if (currentList != null && currentList.size() != 0) {
			outState.putParcelableArrayList(getDelegateKey(),
					(ArrayList<? extends Parcelable>) currentList);
		}

	}

	private void restoreFragment(Bundle savedInstanceState) {

		if (savedInstanceState != null) {
			currentList = savedInstanceState
					.getParcelableArrayList(getDelegateKey());
			if (currentList != null && currentList.size() != 0) {
				setList(currentList);
			}
		}

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mListView = (ListView) getView().findViewById(R.id.array_model_list);
		if (savedInstanceState != null) {
			restoreFragment(savedInstanceState);
		}

	}

	public ListView getListView() {
		return mListView;
	}

	public abstract void setList(List<B> list);


}
