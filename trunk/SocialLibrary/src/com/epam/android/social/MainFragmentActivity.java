package com.epam.android.social;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.epam.android.social.fragments.AddAccountsFragment;

public class MainFragmentActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		isOnline();
		addAccountFragment();

	}

	private void addAccountFragment() {
		FragmentTransaction manager = getSupportFragmentManager()
				.beginTransaction();
		manager.add(R.id.accountLayout, AddAccountsFragment.getInstance());
		manager.commit();
	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		Toast.makeText(this, R.string.not_internet, Toast.LENGTH_LONG).show();
		return false;
	};

	public void onAddAccountClick(View view) {
		startActivity(new Intent(this, TwitterLoginActivity.class));
	}
}
