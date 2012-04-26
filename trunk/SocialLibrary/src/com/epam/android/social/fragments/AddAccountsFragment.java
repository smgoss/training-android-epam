package com.epam.android.social.fragments;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.epam.android.common.utils.ObjectSerializer;
import com.epam.android.social.R;
import com.epam.android.social.TwitterLoginActivity;
import com.epam.android.social.TwitterTimeLineFragmentActivity;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.helper.ImageHelper;
import com.epam.android.social.helper.OAuthHelper;
import com.epam.android.social.model.TwitterUserInfo;

public class AddAccountsFragment extends Fragment {

	private static final String TAG = AddAccountsFragment.class.getSimpleName();

	private RelativeLayout relativeLayout;

	private RelativeLayout.LayoutParams params;

	private ImageButton addAccountButton;

	private int lastAccountPictureID = 100500;

	private boolean isFirst = true;
	
	private static AddAccountsFragment.ILogin login;
	
	private ImageHelper imageHelper;

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		restoreAccounts();
		addAccountButton = (ImageButton) getView().findViewById(
				R.id.accountPicture);
		addAccountButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getView().getContext(),
						TwitterLoginActivity.class));

				};
		});
		
		login = new ILogin() {
			
			@Override
			public void onSuccessLogin(String accontName, String accountAvatarUrl) {
				addNewAccount(accontName, accountAvatarUrl);
			}
		};

	}

	private void restoreAccounts() {
		SharedPreferences preferences = getActivity().getSharedPreferences(
				ApplicationConstants.SHARED_PREFERENSE, Context.MODE_PRIVATE);
		String userInfoSerialized = preferences.getString(
				ApplicationConstants.ACCOUNT_LIST, null);
		ObjectSerializer serializer = new ObjectSerializer();
		try {
			List<TwitterUserInfo> listAccounts = (List<TwitterUserInfo>) serializer
					.deserialize(userInfoSerialized);

			if (listAccounts != null) {
				for (int i = 0; i < listAccounts.size(); i++) {
					addNewAccount(listAccounts.get(i).getUserName(),
							listAccounts.get(i).getProfileUrl());
				}
			}

		} catch (IOException e) {
			Log.d(TAG, "crash when desirialize", e);
		} catch (ClassNotFoundException e) {
			Log.d(TAG, "crash when convert deserialize object", e);
		}

	}

	private void addNewAccount(String accontName, String accountAvatarUrl) {
		relativeLayout = (RelativeLayout) getView().findViewById(
				R.id.accountLayout);
		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout layoutItem = (RelativeLayout) inflater.inflate(
				R.layout.account, null, false);
		TextView accountName = (TextView) layoutItem
				.findViewById(R.id.accountName);
		accountName.setText(accontName);
		ImageView accountPicture = (ImageView) layoutItem
				.findViewById(R.id.accountPicture);
		imageHelper = new ImageHelper(getActivity());
		imageHelper.setAvatar(accountAvatarUrl, accountPicture);
		accountPicture.setTag(accontName);
		layoutItem.setId(lastAccountPictureID);
		accountPicture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					OAuthHelper.getInstanse().restoreToken((String) v.getTag());
					Intent intent = new Intent(getView().getContext(),
							TwitterTimeLineFragmentActivity.class);
					intent.putExtra(ApplicationConstants.USER_NAME,
							String.valueOf(v.getTag()));
					startActivity(intent);
				} catch (IOException e) {
					Log.d(TAG, "crash when loading data", e);
				} catch (ClassNotFoundException e) {
					Log.d(TAG, "crash when converting data", e);
				}
			}
		});
		RelativeLayout.LayoutParams layoutParams = new LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);

		if (isFirst) {
			layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.accountPicture);
			isFirst = false;
		} else {
			layoutParams.addRule(RelativeLayout.RIGHT_OF,
					lastAccountPictureID - 1);
		}
		relativeLayout.addView(layoutItem, layoutParams);
		lastAccountPictureID++;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.account, null, false);
	}
	
	public static interface ILogin{
		public void onSuccessLogin(String accontName, String accountAvatar);
	}
	
	public static AddAccountsFragment.ILogin getLogin(){
		return login;
	}
	
	
	
	
}
