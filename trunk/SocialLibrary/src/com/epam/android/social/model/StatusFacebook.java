package com.epam.android.social.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

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

	public String getName() {
		return getString("name");
	}

	private JSONObject getJSONData() {
		return getJSONObject("data");
	}
}
