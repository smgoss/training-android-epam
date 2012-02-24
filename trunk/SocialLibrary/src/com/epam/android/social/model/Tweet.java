package com.epam.android.social.model;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.epam.android.common.annotation.JSON;
import com.epam.android.common.annotation.Tag;
import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

@Tag(keys = {"results"}, types = {JSON.JSONArray})
public class Tweet extends BaseModel{

	
	@SuppressWarnings("unused")
	private static final String TAG = Tweet.class.getSimpleName();

	public static final IModelCreator<Tweet> MODEL_CREATOR = new IModelCreator<Tweet>() {

		public Tweet create(JSONObject jsonObject) {
			return new Tweet(jsonObject);
		}

	};

	public static final Parcelable.Creator<Tweet> CREATOR = new Creator<Tweet>() {

		public Tweet[] newArray(int size) {
			return new Tweet[size];
		}

		public Tweet createFromParcel(Parcel in) {
			return new Tweet(in);
		}
	};

	public Tweet() {
		super();
	}

	public Tweet(JSONObject json) {
		super(json);
	}

	public Tweet(Parcel in) {
		super(in);
	}

	public Tweet(String json) {
		super(json);
		
	}

	
	public String getProfileUrl(){
		return getString("profile_image_url");
		
	}
	
	public String getPublicdDate(){
		return getString("created_at");
	}
	
	public String getUserName(){
		return getString("from_user_name");
	}
	
	public Long getUserID(){
		return getLong("from_user_id_str");
	}

	public String getText(){
		return getString("text");
	}
}
