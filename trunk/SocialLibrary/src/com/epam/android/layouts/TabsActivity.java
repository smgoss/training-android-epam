package com.epam.android.layouts;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.epam.android.social.R;

public class TabsActivity extends TabActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_layout_tabs);

		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, FirstTabActivity.class);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("artists").setIndicator("Artists", null)
				.setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, SecondTabActivity.class);
		spec = tabHost.newTabSpec("albums").setIndicator("Albums", null)
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, ThirdTabActivity.class);
		spec = tabHost.newTabSpec("songs").setIndicator("Songs", null)
				.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(2);
	}

}
