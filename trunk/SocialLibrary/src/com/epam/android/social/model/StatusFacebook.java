package com.epam.android.social.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

public class StatusFacebook extends BaseModel {
	@SuppressWarnings("unused")
	private static final String TAG = StatusFacebook.class.getSimpleName();

	public static final IModelCreator<StatusFacebook> MODEL_CREATOR = new IModelCreator<StatusFacebook>() {

		public StatusFacebook create(JSONObject jsonObject) {
			return new StatusFacebook(jsonObject);
		}

	};

	public static final Parcelable.Creator<StatusFacebook> CREATOR = new Creator<StatusFacebook>() {

		public StatusFacebook[] newArray(int size) {
			return new StatusFacebook[size];
		}

		public StatusFacebook createFromParcel(Parcel in) {
			return new StatusFacebook(in);
		}
	};

	public StatusFacebook() {
		super();
	}

	public StatusFacebook(JSONObject json) {
		super(json);
	}

	public StatusFacebook(Parcel in) {
		super(in);
	}

	public StatusFacebook(String json) {
		super(json);
	}

	public String getId() {
		return getString("id");
	}

	public String getProfileUrl() {
		try {
			return getJSONObject().getJSONObject("user").getString(
					"profile_image_url");
		} catch (JSONException e) {
			Log.e(TAG, "error on json when get Profile Url", e);
		}

		return "";
	}

	public String getPublicdDate() {
		return getString("updated_time");
	}

	public String getFromUserName() {
		try {
			return getJSONObject().getJSONObject("from").getString("name");
		} catch (JSONException e) {
			Log.e(TAG, "error on json when get Profile Url", e);
		}

		return "";
	}

	public String getFromUserId() {
		try {
			return getJSONObject().getJSONObject("from").getString("id");
		} catch (JSONException e) {
			Log.e(TAG, "error on json when get Profile Url", e);
		}

		return "";
	}

	public String getUserName() {
		try {
			return getJSONObject().getJSONObject("user").getString("name");
		} catch (JSONException e) {
			Log.e(TAG, "error on gson when get User Name", e);
		}
		return "";
	}

	public String getText() {
		return getString("message");
	}
}
