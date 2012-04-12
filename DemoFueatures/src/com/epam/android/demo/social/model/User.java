package com.epam.android.demo.social.model;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.epam.android.demo.common.model.BaseModel;
import com.epam.android.demo.common.model.IModelCreator;

public class User extends BaseModel {

	@SuppressWarnings("unused")
	private static final String TAG = User.class.getSimpleName();

	public static final IModelCreator<User> MODEL_CREATOR = new IModelCreator<User>() {

		public User create(JSONObject jsonObject) {
			return new User(jsonObject);
		}

	};

	public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {

		public User[] newArray(int size) {
			return new User[size];
		}

		public User createFromParcel(Parcel in) {
			return new User(in);
		}
	};

	public User() {
		super();
	}

	public User(JSONObject json) {
		super(json);
	}

	public User(Parcel in) {
		super(in);
	}

	public User(String json) {
		super(json);
	}

	public String getName() {
		return getString("name");
	}

	public String getImageUrl() {
		return getString("imageUrl");
	}

}
