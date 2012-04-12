package com.epam.android.social.fragments;

import com.epam.android.social.R;
import com.epam.android.social.R.color;
import com.epam.android.social.api.TwitterAPI;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AnalogClock;

public class ProfileFragment extends Fragment {

	private static final String TAG = ProfileFragment.class.getSimpleName();

	private static final String ARG_QUERY = "query";

	private static final String ARG_PROFILE_NAME = "profile_name";

	public static ProfileFragment newInstance(String profileName) {
		Bundle bundle = new Bundle();
		ProfileFragment fragment = new ProfileFragment();
		bundle.putString(ARG_PROFILE_NAME, profileName);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction
				.add(R.id.profileInfoFragment, ProfileInfoHeaderFragment
						.newInstance(TwitterAPI.getInstance().fullProgileInfo(
								getArguments().getString(ARG_PROFILE_NAME))));
		transaction.commit();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.profile, container, false);
	}

}
