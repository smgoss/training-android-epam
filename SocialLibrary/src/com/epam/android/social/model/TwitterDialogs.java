package com.epam.android.social.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

public class TwitterDialogs extends BaseModel {
	private static final String TAG = TwitterDialogs.class.getSimpleName();

	public static final IModelCreator<TwitterDialogs> MODEL_CREATOR = new IModelCreator<TwitterDialogs>() {

		public TwitterDialogs create(JSONObject jsonObject) {
			return new TwitterDialogs(jsonObject);
		}

	};

	public static final Parcelable.Creator<TwitterDialogs> CREATOR = new Creator<TwitterDialogs>() {

		public TwitterDialogs[] newArray(int size) {
			return new TwitterDialogs[size];
		}

		public TwitterDialogs createFromParcel(Parcel in) {
			return new TwitterDialogs(in);
		}
	};

	public TwitterDialogs() {
		super();
	}

	public TwitterDialogs(JSONObject json) {
		super(json);
	}

	public TwitterDialogs(Parcel in) {
		super(in);
	}

	public TwitterDialogs(String json) {
		super(json);
	}

	public String getRecipientScreenName() {
		return getString("recipient_screen_name");
	}

	public Long getRecipientID() {
		return getLong("recipient_id");
	}

	public String getRecipientProfileUrl() {
		try {
			return getJSONObject("recipient").getString(
					"profile_image_url_https");
		} catch (JSONException e) {
			Log.e(TAG, "Error when get profile recipient image url", e);
		}
		return "";
	}

	public String getSenderName() {
		try {
			return getJSONObject("sender").getString("name");
		} catch (JSONException e) {
			Log.e(TAG, "Error when get sender name", e);
		}
		return "";
	}

	public String getSenderScreenName() {
		return getString("sender_screen_name");
	}

	public String getSenderProfileUrl() {
		try {
			return getJSONObject("sender").getString("profile_image_url_https");
		} catch (JSONException e) {
			Log.e(TAG, "Error when get sender screen profile url", e);
		}
		return "";
	}

	public String getText() {
		return getString("text");
	}

	public String getPublicdDate() {
		return getString("created_at");
	}
}
