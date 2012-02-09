package com.epam.android.common.model;


import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class BaseModel implements Parcelable{
	private static final String TAG = BaseModel.class.getSimpleName();
		
	private JSONObject jo;

	public BaseModel() {
		jo = new JSONObject();
	}

	public BaseModel(final String json) {
		try {
			jo = new JSONObject(json);
		} catch (JSONException e) {
			Log.d(TAG, "JSON error", e);
		}
	}

	public BaseModel(final JSONObject json) {
		if (json == null) {
			throw new IllegalArgumentException("JSONObject argument is null");
		}
		jo = json;
	}
	
	public BaseModel(final Parcel in) {
		readFromParcel(in);
	}

	public void writeToParcel(final Parcel parcel, int flags) {
		parcel.writeSerializable(jo.toString());
		
	}
	
	public int describeContents() {
		return 0;
	}

	protected void readFromParcel(final Parcel in) {
		Serializable serializable = in.readSerializable();
		try {
			jo = new JSONObject((String) serializable);
		} catch (Exception e) {
			Log.e(TAG, "Cannot serialize to JSONObject");
		}
	}
	
	protected final void set(final String key, final Object value) {
		try {
			jo.put(key, value);
		} catch (JSONException e) {
			Log.e(TAG, "set error", e);
		}
	}

	protected final Object get(final String key) {
		try {
			if (!jo.isNull(key)) {
				return jo.get(key);
			}
		} catch (JSONException e) {
			Log.e(TAG, "get error", e);
		}
		return null;
	}

	protected final String getString(final String key) {
		try {
			if (!jo.isNull(key)) {
				return jo.getString(key);
			}
		} catch (JSONException e) {
			Log.e(TAG, "get string error", e);
		}
		return null;
	}

	protected final Integer getInt(final String key) {
		try {
			if (!jo.isNull(key)) {
				return jo.getInt(key);
			}
		} catch (JSONException e) {
			Log.e(TAG, "get int error", e);
		}
		return null;
	}

	protected final Double getDouble(final String key) {
		try {
			if (!jo.isNull(key)) {
				return jo.getDouble(key);
			}
		} catch (JSONException e) {
			Log.e(TAG, "get double error", e);
		}
		return null;
	}

	protected final Boolean getBoolean(final String key) {
		try {
			if (!jo.isNull(key)) {
				return jo.getBoolean(key);
			}
		} catch (JSONException e) {
			Log.e(TAG, "get boolean error", e);
		}
		return null;
	}

	protected final Long getLong(final String key) {
		try {
			if (!jo.isNull(key)) {
				return jo.getLong(key);
			}
		} catch (JSONException e) {
			Log.e(TAG, "get long error", e);
		}
		return null;
	}

	protected final JSONObject getJSONObject() {
		return jo;
	}
	
	protected final JSONObject getJSONObject(final String key) {
		try {
			if (!jo.isNull(key)) {
				return jo.getJSONObject(key);
			}
		} catch (JSONException e) {
			Log.e(TAG, "get JSONObject error", e);
		}
		return null;
	}

	protected final JSONArray getJSONArray(final String key) {
		try {
			if (!jo.isNull(key)) {
				return jo.getJSONArray(key);
			}
		} catch (JSONException e) {
			Log.e(TAG, "get JSONArray error", e);
		}
		return null;
	}

}