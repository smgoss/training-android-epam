package com.epam.android.social.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.epam.android.common.annotation.JSON;
import com.epam.android.common.annotation.Tag;
import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

@Tag(keys = { "results" }, types = { JSON.JSONArray })
public class SearchResult extends BaseModel {

	@SuppressWarnings("unused")
	private static final String TAG = SearchResult.class.getSimpleName();

	public static final IModelCreator<SearchResult> MODEL_CREATOR = new IModelCreator<SearchResult>() {

		public SearchResult create(JSONObject jsonObject) {
			return new SearchResult(jsonObject);
		}

	};

	public static final Parcelable.Creator<SearchResult> CREATOR = new Creator<SearchResult>() {

		public SearchResult[] newArray(int size) {
			return new SearchResult[size];
		}

		public SearchResult createFromParcel(Parcel in) {
			return new SearchResult(in);
		}
	};

	public SearchResult() {
		super();
	}

	public SearchResult(JSONObject json) {
		super(json);
	}

	public SearchResult(Parcel in) {
		super(in);
	}

	public SearchResult(String json) {
		super(json);

	}

	public Long getTweetID(){
		return getLong("id");
	}
	
	public String getProfileUrl() {
		return getString("profile_image_url");

	}

	public String getPublicdDate() {
		return getString("created_at");
	}

	public String getUserName() {
		return getString("from_user_name");
	}

	public String getText() {
		return getString("text");
	}
}
