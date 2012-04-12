package com.epam.android.demo.common.model;

import org.json.JSONObject;

public interface IModelCreator<B> {
	
	B create(JSONObject jsonObject);
	
}
