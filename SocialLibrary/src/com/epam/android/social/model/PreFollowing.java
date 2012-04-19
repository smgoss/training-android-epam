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

public class PreFollowing extends BaseModel {

	private static final String TAG = PreFollowing.class.getSimpleName();
	
	public static final IModelCreator<PreFollowing> MODEL_CREATOR = new IModelCreator<PreFollowing>() {

		public PreFollowing create(JSONObject jsonObject) {
			return new PreFollowing(jsonObject);
		}

	};

	public static final Parcelable.Creator<PreFollowing> CREATOR = new Creator<PreFollowing>() {

		public PreFollowing[] newArray(int size) {
			return new PreFollowing[size];
		}

		public PreFollowing createFromParcel(Parcel in) {
			return new PreFollowing(in);
		}
	};


	public PreFollowing() {
		super();
	}

	public PreFollowing(JSONObject json) {
		super(json);
	}

	public PreFollowing(Parcel in) {
		super(in);
	}

	public PreFollowing(String json) {
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
