package com.epam.android.social.fragments;

import com.epam.android.social.R;
import com.epam.android.social.api.TwitterAPI;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProfileFragment extends Fragment {

	private static final String TAG = ProfileFragment.class.getSimpleName();

	private static final String ARG_QUERY = "query";

	private static final String ARG_PROFILE_NAME = "profile_name";

	public static ProfileFragment newInstance(String query, String accountName) {
		Bundle bundle = new Bundle();
		ProfileFragment fragment = new ProfileFragment();
		bundle.putString(ARG_QUERY, query);
		bundle.putString(ARG_PROFILE_NAME, accountName);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "onActivityCreated");
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.d(TAG, "onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ProfileInfoHeaderFragment.newInstance(TwitterAPI.getInstance()
				.fullProgileInfo("keddr"));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.profile, container, false);
	}

}
