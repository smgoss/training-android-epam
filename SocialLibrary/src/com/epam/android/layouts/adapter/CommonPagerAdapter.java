package com.epam.android.layouts.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class CommonPagerAdapter extends PagerAdapter {

	List<View> pages = null;

	public CommonPagerAdapter(List<View> pages) {
		this.pages = pages;
	}

	@Override
	public int getCount() {
		return pages.size();
	}

	
	@Override
	public Object instantiateItem(View collection, int position) {
		View v = pages.get(position);
		((ViewPager) collection).addView(v, 0);
		return v;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}
	
	
	

	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((View) view);
	}

	
	

}
