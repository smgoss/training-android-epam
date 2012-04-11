package com.epam.android.social.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.epam.android.social.R;

public class HeaderFramgent extends Fragment {

	private static final String TAG = HeaderFramgent.class.getSimpleName();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.header, null, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getView().findViewById(R.id.tweetButton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						AddTweetFragment addTweetFragment = new AddTweetFragment();
						addTweetFragment.show(getFragmentManager(), TAG);
					}
				});
		getView().findViewById(R.id.searchButton).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getView().getContext(), "onSearhcButtonClick", Toast.LENGTH_SHORT).show();
			}
		});
		
		getView().findViewById(R.id.homeButton).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getView().getContext(), "onHomeButtonClick", Toast.LENGTH_SHORT).show();
			}
		});
		
		getView().findViewById(R.id.favoriteButton).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getView().getContext(), "onFavoriteButtonClick", Toast.LENGTH_SHORT).show();
				
			}
		});
		
		getView().findViewById(R.id.messagesButton).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getView().getContext(), "onMessageButtonClick", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
