package com.epam.android.layouts.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epam.android.common.utils.JsonModelConverter;
import com.epam.android.layouts.adapter.MyListAdapter;
import com.epam.android.layouts.models.MyLayoutModel;
import com.epam.android.social.R;

public class FragmentOne extends ListFragment {
private List<MyLayoutModel> mList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.custom_my_layout, container, false);
        String foo = "foo";
		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		mList = new ArrayList<MyLayoutModel>();
		
		try {
			JSONArray array = new JSONArray(
					"[{'number'='1','title'='���������', 'text'='����� ���������'},  {'number'='2','title'='������','text'='������ ������'}, {'number'='3','title'='�����','text'='��� �����'},{'number'='4','title'='��� ��������', 'text'='������� ���'},  {'number'='5','title'='�����','text'='����� �����'}, {'number'='6','title'='����������','text'='����� ����������'}]");
			mList = JsonModelConverter.convertJSONArrayToList(array,
					MyLayoutModel.MODEL_CREATOR);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        super.onCreate(savedInstanceState);
        String foo = "foo";
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		setListAdapter(new MyListAdapter(getActivity(), R.layout.custom_my_layout_list_item, mList));
		super.onActivityCreated(savedInstanceState);
		String foo = "foo";
	}
	
	
}
