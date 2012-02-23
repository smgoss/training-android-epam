package com.epam.android.layouts;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.epam.android.social.R;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class OtherActivity extends Activity {
	
	private List<View> mPages;
    private View mRuPage;
    private View mEnPage;
    
    private ViewPager mPager;
    private TitlePageIndicator mTitleIndicator;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other);
        
        initUi();
    }

    /**
     * Initialize the User Interface
     */
    private void initUi() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mPages = new ArrayList<View>();

        mRuPage = inflater.inflate(R.layout.ru, null);
        mRuPage.setTag(getString(R.string.russian));
        
        mEnPage = inflater.inflate(R.layout.en, null);
        mEnPage.setTag(getString(R.string.english));
        
        mPages.add(mRuPage);
        mPages.add(mEnPage);
        
        TitlePagerAdapter adapter = new TitlePagerAdapter(mPages);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(0);

        mTitleIndicator = (TitlePageIndicator) findViewById(R.id.indicator);
        mTitleIndicator.setViewPager(mPager);
        mTitleIndicator.setCurrentItem(0);
    }

}
