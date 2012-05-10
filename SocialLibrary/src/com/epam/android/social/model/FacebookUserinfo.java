package com.epam.android.social.model;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

public class FacebookUserinfo extends BaseModel {

	@SuppressWarnings("unused")
	private static final String TAG = FacebookUserinfo.class.getSimpleName();

	public static final IModelCreator<FacebookUserinfo> MODEL_CREATOR = new IModelCreator<FacebookUserinfo>() {

		public FacebookUserinfo create(JSONObject jsonObject) {
			return new FacebookUserinfo(jsonObject);
		}

	};

	public static final Parcelable.Creator<FacebookUserinfo> CREATOR = new Creator<FacebookUserinfo>() {

		public FacebookUserinfo[] newArray(int size) {
			return new FacebookUserinfo[size];
		}

		public FacebookUserinfo createFromParcel(Parcel in) {
			return new FacebookUserinfo(in);
		}
	};

	public FacebookUserinfo() {
		super();
	}

	public FacebookUserinfo(JSONObject json) {
		super(json);
	}

	public FacebookUserinfo(Parcel in) {
		super(in);
	}

	public FacebookUserinfo(String json) {
		super(json);
	}

	public String getId() {
		return getString("id");
	}

	public String getName() {
		return getString("name");
	}

	public String getFirstName() {
		return getString("first_name");
	}

	public String getMiddleName() {
		return getString("middle_name");
	}

	public String getLastName() {
		return getString("last_name");
	}

	public String getGender() {
		return getString("gender");
	}

	public String getLocale() {
		return getString("locale");
	}

	// TODO: LANGUAGES
	// public JSONArray getLanguages() {
	// return getJSONArray("languages");
	// }
	//
	// public String getLanguagesId() {
	// try {
	// return getLanguages().getString("id");
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// return "";
	// }

	public String getLink() {
		return getString("link");
	}

	public String getUsername() {
		return getString("username");
	}

	// TODO: third_party_id
	// TODO: installed

	public Integer getTimezone() {
		return getInt("timezone");
	}

	// TODO: updated_time

	public Boolean getVerified() {
		return getBoolean("verified");
	}

	public String getBio() {
		return getString("bio");
	}

	public String getBirthday() {
		return getString("birthday");
	}

	// TODO: education

	public String getEmail() {
		return getString("email");
	}

	// TODO: hometown
	// TODO: interested_in
	// TODO: location

	public String getPolitical() {
		return getString("political");
	}

	// TODO: favorite_athletes
	// TODO: favorite_teams

	public String getPicture() {
		return getString("picture");
	}

	public String getQuotes() {
		return getString("quotes");
	}

	public String getRelationshipStatus() {
		return getString("relationship_status");
	}

	public String getReligion() {
		return getString("religion");
	}

	// TODO: significant_other
	// TODO: video_upload_limits
	public String getWebsite() {
		return getString("website");
	}

	// TODO: work

}
