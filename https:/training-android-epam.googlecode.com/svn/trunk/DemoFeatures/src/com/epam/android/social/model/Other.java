package com.epam.android.social.model;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

public class Other extends BaseModel {

	@SuppressWarnings("unused")
	private static final String TAG = Other.class.getSimpleName();

	public static final IModelCreator<Other> MODEL_CREATOR = new IModelCreator<Other>() {

		public Other create(JSONObject jsonObject) {
			return new Other(jsonObject);
		}

	};

	public static final Parcelable.Creator<Other> CREATOR = new Creator<Other>() {

		public Other[] newArray(int size) {
			return new Other[size];
		}

		public Other createFromParcel(Parcel in) {
			return new Other(in);
		}
	};

	public Other() {
		super();
	}

	public Other(JSONObject json) {
		super(json);
	}

	public Other(Parcel in) {
		super(in);
	}

	public Other(String json) {
		super(json);
	}

	public String getName() {
		return getString("userName");
	}

	public String getImageUrl() {
		return getString("image");
	}
	
	public Integer getNumber() {
		return getInt("number");
	}

}
