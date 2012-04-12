package com.epam.android.demo.layouts.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epam.android.demo.social.R;

public class FragmentFour extends Fragment {

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.custom_my_layout_right, container, false);
        String foo = "foo";
		return v;
		
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		     String foo = "foo";
        super.onCreate(savedInstanceState);
   
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		String foo = "foo";
	}
	
	
}
