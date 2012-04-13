package com.epam.android.social.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

public class Follow extends BaseModel {

	private static final String TAG = Follow.class.getSimpleName();
	
	public static final IModelCreator<Follow> MODEL_CREATOR = new IModelCreator<Follow>() {

		public Follow create(JSONObject jsonObject) {
			return new Follow(jsonObject);
		}

	};

	public static final Parcelable.Creator<Follow> CREATOR = new Creator<Follow>() {

		public Follow[] newArray(int size) {
			return new Follow[size];
		}

		public Follow createFromParcel(Parcel in) {
			return new Follow(in);
		}
	};


	public Follow() {
		super();
	}

	public Follow(JSONObject json) {
		super(json);
	}

	public Follow(Parcel in) {
		super(in);
	}

	public Follow(String json) {
		super(json);
	}

	public List<Integer> getFriendsID() {
		List<Integer> resultList = new ArrayList<Integer>();
		JSONArray array = getJSONArray("ids");

		try {
			for (int i = 0; i < array.length(); i++) {
				resultList.add(array.getInt(i));
			}
			return resultList;
		} catch (JSONException e) {
			Log.e(TAG, "error when convert ids user", e);
		}
		return null;
	}
}
