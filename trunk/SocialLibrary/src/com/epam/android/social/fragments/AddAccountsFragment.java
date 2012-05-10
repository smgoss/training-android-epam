package com.epam.android.social.fragments;

import java.io.IOException;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.epam.android.social.FacebookLoginActivity;
import com.epam.android.social.R;
import com.epam.android.social.TwitterLoginActivity;
import com.epam.android.social.TwitterTimeLineFragmentActivity;
import com.epam.android.social.constants.AccountType;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.facebook.ListStatusesActivity;
import com.epam.android.social.helper.ImageGetHelper;
import com.epam.android.social.helper.TwitterOAuthHelper;
import com.epam.android.social.model.Account;
import com.epam.android.social.prefs.AccountsListPrefs;

public class AddAccountsFragment extends Fragment {

	private static final String TAG = AddAccountsFragment.class.getSimpleName();

	private RelativeLayout relativeLayout;

	private ImageButton addAccountButton;

	private int lastAccountPictureID = 100500;

	private boolean isFirst = true;

	private static AddAccountsFragment.ILogin login;

	private AccountsListPrefs accountsListPrefs;

	private List<Account> listAccounts;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		accountsListPrefs = AccountsListPrefs.getInstanse();
		restoreAccounts();
		addAccountButton = (ImageButton) getView().findViewById(
				R.id.accountPicture);
		addAccountButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(getView().getContext())
						.setTitle(
								getResources().getString(
										R.string.choise_social_network))
						.setPositiveButton(
								getResources().getString(R.string.facebook),
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int whichButton) {
										startActivity(new Intent(getView()
												.getContext(),
												FacebookLoginActivity.class));

									}
								})
						.setNegativeButton(
								getResources().getString(R.string.twitter),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										startActivity(new Intent(getView()
												.getContext(),
												TwitterLoginActivity.class));
									}
								}).create().show();

			};
		});

		login = new ILogin() {

			@Override
			public void onSuccessLogin(final Account account) {
				addNewAccount(account);
			}
		};

	}

	private void restoreAccounts() {
		listAccounts = accountsListPrefs.getListAccounts();
		if (listAccounts != null) {
			for (Account oneAccount : listAccounts) {
				addNewAccount(oneAccount);
			}
		}

	}

	private void addNewAccount(final Account account) {
		relativeLayout = (RelativeLayout) getView().findViewById(
				R.id.accountLayout);
		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout layoutItem = (RelativeLayout) inflater.inflate(
				R.layout.account, null, false);
		TextView accountName = (TextView) layoutItem
				.findViewById(R.id.accountName);
		accountName.setText(account.getUserName());
		ImageView accountPicture = (ImageView) layoutItem
				.findViewById(R.id.accountPicture);
		ImageGetHelper.getInstance()
				.setAvatar(account.getProfileUrl(), accountPicture);
		
		// BAG
		if (account.getAccountType() == AccountType.FACEBOOK){
			accountPicture.setTag(account.getToken());			
		} else if (account.getAccountType() == AccountType.TWITTER){
			accountPicture.setTag(account.getUserName());			
		}
		
		layoutItem.setId(lastAccountPictureID);
		accountPicture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Intent intent = null;
					if (account.getAccountType() == AccountType.FACEBOOK) {
						intent = new Intent(getView().getContext(),
								ListStatusesActivity.class);

					} else if (account.getAccountType() == AccountType.TWITTER) {
						TwitterOAuthHelper.getInstanse().restoreToken(
								(String) v.getTag());
						intent = new Intent(getView().getContext(),
								TwitterTimeLineFragmentActivity.class);

					}
					intent.putExtra(ApplicationConstants.ARG_PROFILE_NAME,
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

	public static interface ILogin {
		public void onSuccessLogin(final Account account);
	}

	public static AddAccountsFragment.ILogin getLogin() {
		return login;
	}

}
