package com.epam.android.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.epam.android.common.model.IModelCreator;

public class JsonModelConverter {

	public static <T> List<T> convertJSONArrayToList(JSONArray jsonArray,
			IModelCreator<T> modelCreator) throws JSONException {
		List<T> result = new ArrayList<T>();

		for (int i = 0; i < jsonArray.length(); i++) {
			result.add(modelCreator.create(jsonArray.getJSONObject(i)));
		}
		return result;
	}

}
