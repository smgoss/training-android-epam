package com.epam.android.common.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface IModelCreator<B> {
	
	B create(JSONObject jsonObject);
	
}
