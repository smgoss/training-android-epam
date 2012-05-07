package com.epam.android.social.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

public class FacebookProfileInfo extends BaseModel {

	// private final String
	private static final String TAG = FacebookProfileInfo.class.getSimpleName();

	public static final IModelCreator<FacebookProfileInfo> MODEL_CREATOR = new IModelCreator<FacebookProfileInfo>() {

		public FacebookProfileInfo create(JSONObject jsonObject) {
			return new FacebookProfileInfo(jsonObject);
		}

	};

	public static final Parcelable.Creator<FacebookProfileInfo> CREATOR = new Creator<FacebookProfileInfo>() {

		public FacebookProfileInfo[] newArray(int size) {
			return new FacebookProfileInfo[size];
		}

		public FacebookProfileInfo createFromParcel(Parcel in) {
			return new FacebookProfileInfo(in);
		}
	};

	public FacebookProfileInfo() {
		super();
	}

	public FacebookProfileInfo(JSONObject json) {
		super(json);
	}

	public FacebookProfileInfo(Parcel in) {
		super(in);
	}

	public FacebookProfileInfo(String json) {
		super(json);
	}

	public String getProfileAvatarUrl() {
		return getString("picture");
	}

	public String getUrl() {
		return getString("website");
	}

	public String getName() {
		return getString("name");
	}

}
