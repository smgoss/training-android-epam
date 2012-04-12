package com.epam.android.social.fragments;

import java.util.List;

import android.os.Bundle;
import android.util.Log;

import com.epam.android.common.BaseArrayModelFragment;
import com.epam.android.social.R;
import com.epam.android.social.adapter.ProfileInfoHeaderAdapter;
import com.epam.android.social.model.ProfileInfo;

public class ProfileInfoHeaderFragment extends
		BaseArrayModelFragmentWithCustonLoad<ProfileInfo> {

	private static final String TAG = ProfileInfoHeaderFragment.class
			.getSimpleName();

	private static final String ARG_QUERY = "query";

	private String query;

	public static ProfileInfoHeaderFragment newInstance(String query) {
		Bundle bundle = new Bundle();
		ProfileInfoHeaderFragment fragment = new ProfileInfoHeaderFragment();
		bundle.putString(ARG_QUERY, query);
		fragment.setArguments(bundle);
		return fragment;
	}

	public ProfileInfoHeaderFragment() {
		Log.d(TAG, "constructor ProfileInfoHeaderFragment");
	}

	@Override
	public String getUrl() {
		return getArguments().getString(ARG_QUERY);
	}

	@Override
	protected void success(List<ProfileInfo> result) {
		initView(result);
	}

	@Override
	public int getLayoutResource() {
		return R.layout.profile_info_header_fragment;
	}

	private void initView(List<ProfileInfo> result) {
		ProfileInfoHeaderAdapter adapter = new ProfileInfoHeaderAdapter(
				getContext(), getView(), result.get(0));
	}

}
