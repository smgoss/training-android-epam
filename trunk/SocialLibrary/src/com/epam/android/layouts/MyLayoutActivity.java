package com.epam.android.layouts;
//TODO menu, context menu, params, expandable list, gridview mediacontroller
//TODO chronometr, digital clock, analog clock
//TODO read about transitions
//TODO sliding tabs



import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.epam.android.social.R;

public class MyLayoutActivity extends Activity {

	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = LayoutInflater.from(this);
		List<View> pages = new ArrayList<View>();

		View page = inflater.inflate(R.layout.custom_my_layout, null);
		pages.add(page);

		page = inflater.inflate(R.layout.custom_my_layout_left, null);
		pages.add(page);

		page = inflater.inflate(R.layout.custom_my_layout_main, null);
		pages.add(page);

		page = inflater.inflate(R.layout.custom_my_layout_right, null);
		pages.add(page);

		CommonPagerAdapter pagerAdapter = new CommonPagerAdapter(pages);
		viewPager = new ViewPager(this);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(2);

		setContentView(viewPager);
		
		
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
