package com.epam.android.social.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ListView;

import com.epam.android.common.model.BaseModel;
import com.epam.android.social.R;

public abstract class BaseArrayModelFragmentWithCustomLoadAndSaveItems<B extends BaseModel>
		extends BaseArrayModelFragmentWithCustomLoad<B> {

	private ListView mListView;

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (getCurrentList() != null && getCurrentList().size() != 0) {
			outState.putParcelableArrayList(getDelegateKey(),
					(ArrayList<? extends Parcelable>) getCurrentList());
		}
		super.onSaveInstanceState(outState);

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
		mListView = (ListView) getView().findViewById(R.id.array_model_list);
		if (savedInstanceState != null) {
			restoreFragment(savedInstanceState);
		}

	}

	public ListView getListView() {
		return mListView;
	}

	public abstract void setList(List<B> list);
	
	public abstract List<B> getCurrentList();

}
