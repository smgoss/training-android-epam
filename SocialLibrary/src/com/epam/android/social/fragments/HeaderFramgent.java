package com.epam.android.social.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.epam.android.social.R;

public class HeaderFramgent extends Fragment{

	private static final String TAG = HeaderFramgent.class.getSimpleName();
	
	private Button tweetButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.header, null, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if(tweetButton == null){
			tweetButton = (Button) getView().findViewById(R.id.tweetButton);
			tweetButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AddTweetFragment addTweetFragment = new AddTweetFragment();
					addTweetFragment.show(getFragmentManager(), TAG);
				}
			});
		}
	}
	
	

}
