package com.epam.android.social.fragments;

import java.io.IOException;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.epam.android.social.R;
import com.epam.android.social.TwitterTimeLineFragmentActivity;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.helper.ImageGetHelper;
import com.epam.android.social.helper.TwitterOAuthHelper;
import com.epam.android.social.model.Account;
import com.epam.android.social.prefs.AccountsListPrefs;

public class AddAccountsFragment extends Fragment {

	public static final String TAG = AddAccountsFragment.class.getSimpleName();

	private int lastAccountPictureID = 100500;

	private static AddAccountsFragment.ILogin login;

	private AccountsListPrefs accountsListPrefs;

	private List<Account> listAccounts;

	private boolean isFirst = true;

	private static AddAccountsFragment instance;

	public static AddAccountsFragment getInstance() {
		if (instance == null) {
			instance = new AddAccountsFragment();
		}

		return instance;
	}

	private AddAccountsFragment() {

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		accountsListPrefs = AccountsListPrefs.getInstanse();
		restoreAccounts();

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
		LinearLayout accountLayout = (LinearLayout) getActivity().findViewById(
				R.id.accountLayout);

		LinearLayout accountItem = (LinearLayout) LayoutInflater.from(
				getActivity()).inflate(R.layout.account_item, null, false);

		TextView accountName = (TextView) accountItem
				.findViewById(R.id.accountName);

		ImageView accountPicture = (ImageView) accountItem
				.findViewById(R.id.accountPicture);

		accountName.setText(account.getUserName());

		ImageGetHelper.getInstance().setAvatar(account.getProfileUrl(),
				accountPicture);

		accountItem.setId(lastAccountPictureID);

		accountPicture.setTag(account.getUserName());

		accountPicture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Intent intent = null;
					TwitterOAuthHelper.getInstanse().restoreToken(
							(String) v.getTag());
					intent = new Intent(getActivity(),
							TwitterTimeLineFragmentActivity.class);

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
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT, 1f);

		// if (isFirst) {
		// layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,
		// lastAccountPictureID);
		// layoutParams.
		// isFirst = false;
		// } else {
		// layoutParams.addRule(RelativeLayout.RIGHT_OF,
		// lastAccountPictureID - 1);
		// }

		accountLayout.addView(accountItem, layoutParams);
		lastAccountPictureID++;
	}

	public static interface ILogin {
		public void onSuccessLogin(final Account account);
	}

	public static AddAccountsFragment.ILogin getLogin() {
		return login;
	}

}
