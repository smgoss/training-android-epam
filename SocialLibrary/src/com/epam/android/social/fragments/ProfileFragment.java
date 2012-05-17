package com.epam.android.social.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epam.android.social.R;
import com.epam.android.social.api.TwitterAPI;
import com.epam.android.social.constants.ApplicationConstants;

public class ProfileFragment extends Fragment {

	private static final String TAG = ProfileFragment.class.getSimpleName();

	private static final String ARG_CURRENT_ACCOUNT_NAME = "accountName";

	public static ProfileFragment newInstance(String profileName,
			String currentAccountName) {
		Bundle bundle = new Bundle();
		ProfileFragment fragment = new ProfileFragment();
		bundle.putString(ARG_CURRENT_ACCOUNT_NAME, currentAccountName);
		bundle.putString(ApplicationConstants.ARG_PROFILE_NAME, profileName);
		fragment.setArguments(bundle);
		return fragment;
	}

	private ProfileFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction
				.add(R.id.profileInfoFragment,
						ProfileInfoHeaderFragment
								.newInstance(
										TwitterAPI
												.getInstance()
												.getFullProfileInfo(
														getArguments()
																.getString(
																		ApplicationConstants.ARG_PROFILE_NAME)),
										getArguments().getString(
												ARG_CURRENT_ACCOUNT_NAME)));

		transaction.commit();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.profile, container, false);
	}

}
