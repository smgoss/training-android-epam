package com.epam.android.social.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

public class Following extends BaseModel {

	private static final String TAG = Following.class.getSimpleName();

	public static final IModelCreator<Following> MODEL_CREATOR = new IModelCreator<Following>() {

		public Following create(JSONObject jsonObject) {
			return new Following(jsonObject);
		}

	};

	public static final Parcelable.Creator<Following> CREATOR = new Creator<Following>() {

		public Following[] newArray(int size) {
			return new Following[size];
		}

		public Following createFromParcel(Parcel in) {
			return new Following(in);
		}
	};

	public Following() {
		super();
	}

	public Following(JSONObject json) {
		super(json);
	}

	public Following(Parcel in) {
		super(in);
	}

	public Following(String json) {
		super(json);
	}

	public String getName() {
		return getString("name");
	}

	public String getScreenName() {
		return getString("screen_name");
	}

	public String getIdUser() {
		return getString("id_str");
	}

	public Boolean isFollow() {
		for (int i = 0; i < getJSONArray("connections").length(); i++) {
			try {
				if (getJSONArray("connections").get(i).equals("following")) {
					return true;
				}
			} catch (JSONException e) {
				Log.e(TAG, "error on get connections array");
			}
		}

		return false;
	}

}
