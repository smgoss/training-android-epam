package com.epam.android.layouts.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epam.android.common.utils.JsonModelConverter;
import com.epam.android.layouts.adapter.MyListAdapter;
import com.epam.android.layouts.models.MyLayoutModel;
import com.epam.android.social.R;

public class FragmentThree extends Fragment {

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.custom_my_layout_main, container, false);
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
