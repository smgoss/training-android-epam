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
