package com.epam.android.social.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.epam.android.social.R;
import com.epam.android.social.adapter.TweetDialogAdapter;
import com.epam.android.social.constants.ApplicationConstants;

public class TweetDialogFragment extends DialogFragment {

	private String pattern_url = "https?://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	private String pattern_hashtag = "(?:(?<=\\s)|^)#(\\w*[\\w0-9A-Za-z_]+\\w*)";
	private String pattern_profile = "(?:(?<=\\s)|^)@(\\w*[\\w0-9A-Za-z_]+\\w*)";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.tweet_dialog, null, false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_TITLE, getTheme());

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ListView listView = (ListView) getView().findViewById(
				R.id.tweet_dialog_list);
		String tweetText = getArguments().getString(
				ApplicationConstants.TWEET_TEXT);

		TweetDialogAdapter adapter = new TweetDialogAdapter(getActivity(),
				getUrls(tweetText), getHashTags(tweetText),
				getProfiles(tweetText));
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

			}
		});
	}

	private List<String> getHashTags(final String input) {
		return getRegex(pattern_hashtag, input);
	}

	private List<String> getProfiles(final String input) {
		return getRegex(pattern_profile, input);
	}

	private List<String> getUrls(final String input) {
		return getRegex(pattern_url, input);
	}

	private List<String> getRegex(final String regex, final String input) {
		List<String> output = new ArrayList<String>();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input.trim());
		while (matcher.find()) {
			output.add(matcher.group());
		}
		return output;
	}

}
