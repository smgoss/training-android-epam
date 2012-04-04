package com.epam.android.social.model;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

public class TwitterUserInfo extends BaseModel{
	public static final IModelCreator<TwitterUserInfo> MODEL_CREATOR = new IModelCreator<TwitterUserInfo>() {

		public TwitterUserInfo create(JSONObject jsonObject) {
			return new TwitterUserInfo(jsonObject);
		}

	};

	public static final Parcelable.Creator<TwitterUserInfo> CREATOR = new Creator<TwitterUserInfo>() {

		public TwitterUserInfo[] newArray(int size) {
			return new TwitterUserInfo[size];
		}

		public TwitterUserInfo createFromParcel(Parcel in) {
			return new TwitterUserInfo(in);
		}
	};

	public TwitterUserInfo() {
		super();
	}

	public TwitterUserInfo(JSONObject json) {
		super(json);
	}

	public TwitterUserInfo(Parcel in) {
		super(in);
	}

	public TwitterUserInfo(String json) {
		super(json);
	}
	
	public String getUserName(){
		return getString("screen_name");
	}
	
	public String getProfileUrl(){
		return getString("profile_image_url");
	}
}
