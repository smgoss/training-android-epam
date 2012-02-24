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
					"[{'number'='1','title'='Сообщения', 'text'='новых сообщений'},  {'number'='2','title'='Друзья','text'='друзей онлайн'}, {'number'='3','title'='Стена','text'='моя стена'},{'number'='4','title'='Дни рождения', 'text'='сегодня нет'},  {'number'='5','title'='Видео','text'='всего видео'}, {'number'='6','title'='Фотографии','text'='всего фотографий'}]");
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
