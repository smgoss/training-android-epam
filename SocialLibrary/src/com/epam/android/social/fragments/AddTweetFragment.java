package com.epam.android.social.fragments;

import java.io.UnsupportedEncodingException;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.epam.android.common.task.HttpPostAsyncTask;
import com.epam.android.social.R;
import com.epam.android.social.api.TwitterAPI;
import com.epam.android.social.constants.TwitterConstants;

public class AddTweetFragment extends DialogFragment {

	private static final String TAG = AddTweetFragment.class.getSimpleName();

	private Button tweetButton;

	private Button cancelButton;

	private EditText tweetText;

	private TextView messageCountSymbols;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.tweet_add, null, false);
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);

		tweetButton = (Button) getView().findViewById(R.id.tweetButton);
		cancelButton = (Button) getView().findViewById(R.id.canselButton);
		tweetText = (EditText) getView().findViewById(R.id.tweetText);
		messageCountSymbols = (TextView) getView().findViewById(
				R.id.messageCoutSymbols);
		messageCountSymbols.setText(String
				.valueOf(TwitterConstants.MAX_LENGTH_TWEET));

		tweetButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				try {
					new HttpPostAsyncTask(getActivity()) {

					}.execute(TwitterAPI.getInstance().getUpdateStatusRequest(
							tweetText.getText().toString()));
				} catch (UnsupportedEncodingException e) {
					Log.e(TAG, "unsuported encoding");
				}
				getDialog().cancel();
			}
		});

		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDialog().cancel();
			}
		});

		tweetText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				messageCountSymbols.setText(String
						.valueOf(TwitterConstants.MAX_LENGTH_TWEET - start
								- after));
			}

		});

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_TITLE, getTheme());
	}

}
