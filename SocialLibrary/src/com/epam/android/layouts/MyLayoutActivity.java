package com.epam.android.layouts;

//TODO menu, context menu, params, expandable list, gridview mediacontroller
//TODO chronometr, digital clock, analog clock
//TODO read about transitions
//TODO sliding tabs

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.epam.android.layouts.adapter.CommonFragmentPagerAdapter;
import com.epam.android.social.R;

public class MyLayoutActivity extends FragmentActivity {

	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_main_fragment);
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(new CommonFragmentPagerAdapter(
				getSupportFragmentManager()));
		viewPager.setCurrentItem(0);
String foo = "foo";

	}

	public void onFirstButtonClick(View view) {
		viewPager.setCurrentItem(0);
	}

	public void onLeftButtonClick(View view) {
		viewPager.setCurrentItem(1);
	}

	public void onRightButtonClick(View view) {
		viewPager.setCurrentItem(3);
	}

	public void onOtherButtonClick(View view) {
		Intent intent = new Intent(this, OtherActivity.class);
		startActivity(intent);
	}

	public void onTabsButtonClick(View view) {
		Intent intent = new Intent(this, TabsActivity.class);
		startActivity(intent);
	}

	public void onToastButtonClick(View view) {
		Toast.makeText(this, "Toast message to customize", Toast.LENGTH_LONG)
				.show();
	}

	public void onProgressButtonClick(View view) {
		ProgressDialog.show(this, "Progress", "To be custoized", true, true);

	}
}
