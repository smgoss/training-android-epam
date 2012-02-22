package com.epam.android.layouts;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.epam.android.social.ModelSampleActivity;
import com.epam.android.social.R;
import com.epam.android.layouts.CommonPagerAdapter;

public class MyLayoutActivity extends Activity {

	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = LayoutInflater.from(this);
		List<View> pages = new ArrayList<View>();

		View page = inflater.inflate(R.layout.my_layout, null);
		TextView textView = (TextView) page.findViewById(R.id.text_view);
		textView.setText("Страница");
		pages.add(page);

		page = inflater.inflate(R.layout.my_layout_left, null);
		textView = (TextView) page.findViewById(R.id.text_view);
		textView.setText("Страница left");
		pages.add(page);

		page = inflater.inflate(R.layout.my_layout_main, null);
		textView = (TextView) page.findViewById(R.id.text_view);
		textView.setText("Страница main");
		pages.add(page);

		page = inflater.inflate(R.layout.my_layout_right, null);
		textView = (TextView) page.findViewById(R.id.text_view);
		textView.setText("Страница right");
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
		// Intent intent = new Intent(this, OtherActivity.class);
		// startActivity(intent);
	}
}
