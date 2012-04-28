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
						// Toast.makeText(getView().getContext(),
						// "onMessageButtonClick", Toast.LENGTH_SHORT).show();
						WindowManager mWindowManager;

						WindowManager.LayoutParams mWindowParams;

						View view = LayoutInflater.from(getActivity()).inflate(
								R.layout.gallery_camera_item, null);
						mWindowParams = new WindowManager.LayoutParams();

						mWindowParams.gravity = Gravity.TOP | Gravity.LEFT;
						// mWindowParams.x = 50;
						mWindowParams.y = 50;
						mWindowParams.y = getView().findViewById(
								R.id.changeProfile_avatarLayout).getScrollY();

						mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
						mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
						mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
								| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
								| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
						mWindowParams.format = PixelFormat.TRANSLUCENT;
						mWindowParams.windowAnimations = 0;

						mWindowManager = (WindowManager) getView().getContext()
								.getApplicationContext()
								.getSystemService("window");
						mWindowManager.addView(view, mWindowParams);
					}
				});
	}

}
