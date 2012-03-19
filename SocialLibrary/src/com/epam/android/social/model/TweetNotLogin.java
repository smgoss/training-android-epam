package com.epam.android.social.model;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.epam.android.common.annotation.JSON;
import com.epam.android.common.annotation.Tag;
import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

@Tag(keys = {"results"}, types = {JSON.JSONArray})
public class TweetNotLogin extends BaseModel{

	
	@SuppressWarnings("unused")
	private static final String TAG = TweetNotLogin.class.getSimpleName();

	public static final IModelCreator<TweetNotLogin> MODEL_CREATOR = new IModelCreator<TweetNotLogin>() {

		public TweetNotLogin create(JSONObject jsonObject) {
			return new TweetNotLogin(jsonObject);
		}

	};

	public static final Parcelable.Creator<TweetNotLogin> CREATOR = new Creator<TweetNotLogin>() {

		public TweetNotLogin[] newArray(int size) {
			return new TweetNotLogin[size];
		}

		public TweetNotLogin createFromParcel(Parcel in) {
			return new TweetNotLogin(in);
		}
	};

	public TweetNotLogin() {
		super();
	}

	public TweetNotLogin(JSONObject json) {
		super(json);
	}

	public TweetNotLogin(Parcel in) {
		super(in);
	}

	public TweetNotLogin(String json) {
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

	public String getText(){
		return getString("text");
	}
}
