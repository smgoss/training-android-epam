package com.epam.android.social.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.epam.android.common.annotation.JSON;
import com.epam.android.common.annotation.Tag;
import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

@Tag(keys = { "data", "paging" }, types = { JSON.JSONArray, JSON.JSONObject })
public class FacebookStatus extends BaseModel {

	@SuppressWarnings("unused")
	private static final String TAG = FacebookStatus.class.getSimpleName();

	public static final IModelCreator<FacebookStatus> MODEL_CREATOR = new IModelCreator<FacebookStatus>() {

		public FacebookStatus create(JSONObject jsonObject) {
			return new FacebookStatus(jsonObject);
		}

	};

	public static final Parcelable.Creator<FacebookStatus> CREATOR = new Creator<FacebookStatus>() {

		public FacebookStatus[] newArray(int size) {
			return new FacebookStatus[size];
		}

		public FacebookStatus createFromParcel(Parcel in) {
			return new FacebookStatus(in);
		}
	};

	public FacebookStatus() {
		super();
	}

	public FacebookStatus(JSONObject json) {
		super(json);
	}

	public FacebookStatus(Parcel in) {
		super(in);
	}

	public FacebookStatus(String json) {
		super(json);
	}

	public String getId() {
		return getString("id");
	}

	public JSONObject getFrom() {
		return getJSONObject("from");
	}

	public String getFromId() {
		try {
			return getFrom().getString("id");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getFromName() {
		try {
			return getFrom().getString("name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getMessage() {
		return getString("message");
	}

	public JSONObject getPlace() {
		return getJSONObject("place");
	}

	public String getPlaceId() {
		try {
			return getPlace().getString("id");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getPlaceName() {
		try {
			return getPlace().getString("name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getPlaceLocation() {
		try {
			return getPlace().getString("location");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getUpdatedTime() {
		return getString("updated_time");
	}

	public String getType() {
		return getString("type");
	}
}
