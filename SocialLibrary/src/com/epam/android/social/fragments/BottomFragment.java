package com.epam.android.social.fragments;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import com.epam.android.social.R;

public class BottomFragment extends Fragment {

	private static final String TAG = BottomFragment.class.getSimpleName();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.bottom, null, false);
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
		getView().findViewById(R.id.searchButton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						FragmentTransaction fragmentTransaction = getFragmentManager()
								.beginTransaction();
						fragmentTransaction.addToBackStack(getTag());
						fragmentTransaction
								.setCustomAnimations(android.R.anim.fade_in,
										android.R.anim.fade_out);
						fragmentTransaction.add(R.id.twitter_timeline_fragment,
								new SearchLineFragment(),
								SearchLineFragment.TAG);
						fragmentTransaction.commit();
					}
				});

		getView().findViewById(R.id.homeButton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(getView().getContext(),
								"onHomeButtonClick", Toast.LENGTH_SHORT).show();
					}
				});

		getView().findViewById(R.id.favoriteButton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(getView().getContext(),
								"onFavoriteButtonClick", Toast.LENGTH_SHORT)
								.show();

					}
				});

		getView().findViewById(R.id.messagesButton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(getView().getContext(),
								"onMessageButtonClick", Toast.LENGTH_SHORT)
								.show();
					}
				});
	}

}
