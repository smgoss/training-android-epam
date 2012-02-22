package com.epam.android.social.model;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.epam.android.common.annotation.Tag;
import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;
import com.epam.android.common.model.JSON;


//http://partners.mtvnservices.com/dextr/partner/wireless/daily_show_most_popular_videos_changed/full.xml
@Tag(keys = {"rss", "channel"}, types = {JSON.JSONObject, JSON.JSONObject})
public class Item extends BaseModel {

	@SuppressWarnings("unused")
	private static final String TAG = Item.class.getSimpleName();

	public static final IModelCreator<Item> MODEL_CREATOR = new IModelCreator<Item>() {

		public Item create(JSONObject jsonObject) {
			return new Item(jsonObject);
		}

	};

	public static final Parcelable.Creator<Item> CREATOR = new Creator<Item>() {

		public Item[] newArray(int size) {
			return new Item[size];
		}

		public Item createFromParcel(Parcel in) {
			return new Item(in);
		}
	};

	public Item() {
		super();
	}

	public Item(JSONObject json) {
		super(json);
	}

	public Item(Parcel in) {
		super(in);
	}

	public Item(String json) {
		super(json);
	}
		
	private JSONObject getMediaGroup(){
		return getJSONObject("media:group");
	}
	
	
	public String getTitle(){
		return getString("title");
	}
	
	public String getLink() {
		return getString("link");
	}
	
	public String getKeywords(){
		try {
			return getMediaGroup().getString("media:keywords");
		} catch (JSONException e) {
			Log.e(TAG, "get keywords error", e);
		}
		return "";
	}
	
	
	
	public String getCopiright(){
		try {
			return getMediaGroup().getString("media:copyright");
		} catch (JSONException e) {
			Log.e(TAG, "get copyright error", e);
		}
		return "";
	}
	
	public String getMediaTitle(){
		try {
			return getMediaGroup().getString("media:title");
		} catch (JSONException e) {
			Log.e(TAG, "get title error", e);
		}
		return "";
	}
	
	public String getThumbnail(){
		try {
			return getMediaGroup().getJSONObject("media:thumbnail").getString("url");
			
		} catch (JSONException e) {
			Log.e(TAG, "get thumbnail url error", e);
		}
		return "";
	}
	
	public String getMedia(){
		try {
			return getMediaGroup().getString("xmlns:media");
		} catch (JSONException e) {
			Log.e(TAG, "get media error", e);
		}
		return "";
	}
	
	public String getMediaDuration(){
		try {
			return getMediaGroup().getString("media:duration");
		} catch (JSONException e) {
			Log.e(TAG, "get duration error", e);
		}
		return "";
	}
	
	public int getCategoryLength(){
		try {
			return getMediaGroup().getJSONArray("media:category").length();
		} catch (JSONException e) {
			Log.e(TAG, "get category length error", e);
		}
		return 0;
	}
	
	public String getCategorySchema(int i){
		try {
			if(!getMediaGroup().getJSONArray("media:category").getJSONObject(i)
					.isNull("schema"))
			return getMediaGroup().getJSONArray("media:category").getJSONObject(i).getString("schema");
		} catch (JSONException e) {
			Log.e(TAG, "get category schema error", e);
		}
		return "";
	}
	
	public String getCategory(int i) {
		try {
			if (!getMediaGroup().getJSONArray("media:category").getJSONObject(i)
					.isNull("content")) {
				return getMediaGroup().getJSONArray("media:category")
						.getJSONObject(i).getString("content");
			}
			
		} catch (JSONException e) {
			Log.e(TAG, "get category error", e);
		}
		return "";
	}
	
	public Date getPubDate(){
		return new Date(getString("pubDate"));
	}
	
	public String getDescription(){
		return getString("description");
	}
	
	public String getGuid(){
		try {
			return getJSONObject("guid").getString("content");
		} catch (JSONException e) {
			Log.e(TAG, "get guid error", e);
		}
		return "";
	}
	
	public Boolean getGuidIsPermaLink(){
		try {
			return getJSONObject("guid").getBoolean("isPermaLink");
		} catch (JSONException e) {
			Log.e(TAG, "get guid is perma link error", e);
		}
		
		return false;
	}
}
