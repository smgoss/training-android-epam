package com.epam.android.social.fragments;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Gallery;

import com.epam.android.common.utils.ObjectSerializer;
import com.epam.android.social.R;
import com.epam.android.social.TwitterTimeLineFragmentActivity;
import com.epam.android.social.adapter.AccountAdapter;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.helper.TwitterOAuthHelper;
import com.epam.android.social.model.Account;
import com.epam.android.social.prefs.AccountsListPrefs;

public class AccountsFragment extends Fragment {

	public static final String TAG = AccountsFragment.class.getSimpleName();

	private static AccountsFragment.ILogin login;

	private AccountsListPrefs accountsListPrefs;

	private static List<Account> listAccounts;

	private static AccountAdapter accountAdapter;

	private Gallery accountGallery;

	private SharedPreferences settings;

	public AccountsFragment() {

	}

	private void goToAccount(int position) {
		if (position == -1)
			return;
		try {
			Intent intent = null;
			TwitterOAuthHelper.getInstanse().restoreToken(
					listAccounts.get(position).getUserName());
			intent = new Intent(getActivity(),
					TwitterTimeLineFragmentActivity.class);

			intent.putExtra(ApplicationConstants.ARG_PROFILE_NAME, listAccounts
					.get(position).getUserName());

			SharedPreferences.Editor editor = settings.edit();
			editor.putInt(ApplicationConstants.KEY_CURRENT_ACCOUNT, position);
			editor.commit();

			startActivity(intent);
			getActivity().finish();
		} catch (IOException e) {
			Log.d(TAG, "crash when loading data", e);
		} catch (ClassNotFoundException e) {
			Log.d(TAG, "crash when converting data", e);
		}

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

				goToAccount(position);

			}

		});

		accountGallery
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(
							AdapterView<?> paramAdapterView, View paramView,
							final int position, long paramLong) {

						new AlertDialog.Builder(getActivity())
								.setTitle("Remove account?")
								.setMessage("Are you sure you want to delete?")
								.setPositiveButton("Yes",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int which) {
												removeAccount(position);
											}
										}).setNegativeButton("No", null)
								.create().show();

						return true;
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

		settings = getActivity().getSharedPreferences(ApplicationConstants.PREFS_SETTINGS_NAME, 0);
		if (settings.getInt(ApplicationConstants.KEY_CURRENT_ACCOUNT, -1) != -1) {
			goToAccount(settings.getInt(ApplicationConstants.KEY_CURRENT_ACCOUNT, -1));
		}

	}

	// Remove Account
	private void removeAccount(int position) {
		try {
			listAccounts.remove(position);
			ObjectSerializer serializer = new ObjectSerializer();
			SharedPreferences.Editor editor = getActivity()
					.getSharedPreferences(
							ApplicationConstants.SHARED_PREFERENSE,
							Context.MODE_PRIVATE).edit();
			editor.putString(ApplicationConstants.ACCOUNT_LIST,
					serializer.serialize((Serializable) listAccounts));
			editor.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
		accountAdapter.notifyDataSetChanged();
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

	public static AccountsFragment.ILogin getLogin() {
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
