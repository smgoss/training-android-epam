package com.epam.android.demo.layouts;

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

import com.epam.android.demo.common.utils.Utils;
import com.epam.android.demo.layouts.adapter.MyLayoutPagerAdapter;
import com.epam.android.demo.social.R;

public class MyLayoutActivity extends FragmentActivity {

	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_main_fragment);
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(new MyLayoutPagerAdapter(
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
					
			Utils.showMessage(this, "Title", "no message");
			
		
	}

	public void onProgressButtonClick(View view) {
		ProgressDialog.show(this, "Progress", "To be custoized", true, true);

	}

}
