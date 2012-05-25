package com.epam.android.social.fragments;

import java.util.List;

import android.os.Bundle;
import android.widget.BaseAdapter;

import com.epam.android.common.model.BaseModel;
import com.epam.android.social.R;
import com.epam.android.social.adapter.TwitterDialogsAdapter;
import com.epam.android.social.common.fragments.CommonTwitterFragment;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.model.TwitterDialogs;

public class TwitterDialogsFragment extends CommonTwitterFragment<TwitterDialogs>{

	private static final String TAG = TwitterDialogsFragment.class
			.getSimpleName();

	
	public static TwitterDialogsFragment newInstance(String query,
			String accountName) {
		Bundle bundle = new Bundle();
		TwitterDialogsFragment fragment = new TwitterDialogsFragment();
		bundle.putString(ApplicationConstants.ARG_QUERY, query);
		bundle.putString(ApplicationConstants.ARG_PROFILE_NAME, accountName);
		fragment.setArguments(bundle);
		return fragment;
	}

	
	private TwitterDialogsFragment() {

	}
	
	
	@Override
	public BaseAdapter createAdapter(List<? extends BaseModel> list) {
		return new TwitterDialogsAdapter(getContext(), R.layout.tweet,
				(List<TwitterDialogs>) list);
	}


	@Override
	public int getProgressBarResource() {
		return R.id.progress_bar_on_listView;
	}

}
