package com.epam.android.social.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;

import com.epam.android.social.R;
import com.epam.android.social.TwitterTimeLineFragmentActivity;
import com.epam.android.social.adapter.AccountAdapter;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.helper.TwitterOAuthHelper;
import com.epam.android.social.model.Account;
import com.epam.android.social.prefs.AccountsListPrefs;

public class AddAccountsFragment extends Fragment {

	public static final String TAG = AddAccountsFragment.class.getSimpleName();

	private static AddAccountsFragment.ILogin login;

	private AccountsListPrefs accountsListPrefs;

	private static List<Account> listAccounts;

	private static AccountAdapter accountAdapter;

	private Gallery accountGallery;

	public AddAccountsFragment() {

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		accountsListPrefs = AccountsListPrefs.getInstanse();
		accountGallery = (Gallery) getView().findViewById(R.id.gallery);
		listAccounts = new ArrayList<Account>();
		accountGallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> paramAdapterView,
					View paramView, int position, long paramLong) {
				try {
					Intent intent = null;
					TwitterOAuthHelper.getInstanse().restoreToken(
							listAccounts.get(position).getUserName());
					intent = new Intent(getActivity(),
							TwitterTimeLineFragmentActivity.class);

					intent.putExtra(ApplicationConstants.ARG_PROFILE_NAME,
							listAccounts.get(position).getUserName());

					startActivity(intent);

				} catch (IOException e) {
					Log.d(TAG, "crash when loading data", e);
				} catch (ClassNotFoundException e) {
					Log.d(TAG, "crash when converting data", e);
				}
			}
		});
		restoreAccounts();

		login = new ILogin() {

			@Override
			public void onSuccessLogin(final Account account) {
				if (!listContainAcccount(account)) {
					listAccounts.add(account);
					if (accountAdapter == null) {
						accountAdapter = new AccountAdapter(listAccounts,
								getActivity());
						accountGallery.setAdapter(accountAdapter);
					}
					accountAdapter.notifyDataSetChanged();
				}

			}
		};

	}

	private boolean listContainAcccount(Account account) {
		for (int i = 0; i < listAccounts.size(); i++) {
			if (listAccounts.get(i).getUserName().equals(account.getUserName())) {
				return true;
			}
		}
		return false;
	}

	private void restoreAccounts() {
		if (accountsListPrefs.getListAccounts() != null) {
			listAccounts = accountsListPrefs.getListAccounts();
			accountAdapter = new AccountAdapter(listAccounts, getActivity());
			accountGallery.setAdapter(accountAdapter);
		}

	}

	public static interface ILogin {
		public void onSuccessLogin(final Account account);
	}

	public static AddAccountsFragment.ILogin getLogin() {
		return login;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.gallery, container);
	}

	public static List<Account> getList() {
		return listAccounts;
	}

	public static AccountAdapter getAdapter() {
		return accountAdapter;
	}

}
