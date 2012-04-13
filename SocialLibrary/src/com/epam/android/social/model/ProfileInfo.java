package com.epam.android.social.model;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

public class ProfileInfo extends BaseModel {

	public static final IModelCreator<ProfileInfo> MODEL_CREATOR = new IModelCreator<ProfileInfo>() {

		public ProfileInfo create(JSONObject jsonObject) {
			return new ProfileInfo(jsonObject);
		}

	};

	public static final Parcelable.Creator<ProfileInfo> CREATOR = new Creator<ProfileInfo>() {

		public ProfileInfo[] newArray(int size) {
			return new ProfileInfo[size];
		}

		public ProfileInfo createFromParcel(Parcel in) {
			return new ProfileInfo(in);
		}
	};

	public ProfileInfo() {
		super();
	}

	public ProfileInfo(JSONObject json) {
		super(json);
	}

	public ProfileInfo(Parcel in) {
		super(in);
	}

	public ProfileInfo(String json) {
		super(json);
	}

	public String getProfileAvatarUrl() {
		return getString("profile_image_url_https");
	}

	public Integer getCountTweets() {
		return getInt("statuses_count");
	}

	public Integer getCountFollowing() {
		return getInt("friends_count");
	}

	public Integer getCountFollowers() {
		return getInt("followers_count");
	}

	public Boolean youFollowing() {
		return getBoolean("following");
	}

	public String getScreenName() {
		return getString("screen_name");
	}

	public String getUrl() {
		return getString("url");
	}

	public String getName() {
		return getString("name");
	}

	public String getDescription() {
		return getString("description");
	}

}
