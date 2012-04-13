package com.epam.android.social.fragments;

import java.util.List;

import com.epam.android.common.BaseModelFragment;
import com.epam.android.social.R;
import com.epam.android.social.api.TwitterAPI;
import com.epam.android.social.model.Follow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FollowFragment extends BaseModelFragment<Follow> {

	private static final String TAG = FollowFragment.class.getSimpleName();

	private static final String ARG_QUERY = "query";

	private static final String ARG_PROFILE_NAME = "profile_name";

	public static FollowFragment newInstance(String query, String accountName) {
		Bundle bundle = new Bundle();
		FollowFragment fragment = new FollowFragment();
		bundle.putString(ARG_QUERY, query);
		bundle.putString(ARG_PROFILE_NAME, accountName);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public String getUrl() {
		return getArguments().getString(ARG_QUERY);
	}

	@Override
	public int getLayoutResource() {
		return R.layout.follow;
	}

	@Override
	protected void success(Follow result) {
		List<Integer> idList = result.getFriendsID();
		TwitterAPI.getInstance().getShorProfileInfo(idList);
	}

}
