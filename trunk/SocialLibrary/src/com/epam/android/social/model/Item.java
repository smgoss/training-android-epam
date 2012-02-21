package com.epam.android.social.model;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.epam.android.common.annotation.Tag;
import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;


//http://partners.mtvnservices.com/dextr/partner/wireless/daily_show_most_popular_videos_changed/full.xml
@Tag(keys="items", types="jsonobject")
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
	
	private JSONObject getItem(int i){
		try {
			
			return getJSONObject().getJSONObject("rss").getJSONObject("channel").getJSONArray("item").getJSONObject(i);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private JSONObject getMediaGroup(int i){
		try {
			return getItem(i).getJSONObject("media:group");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int getLength(){
		try {
			return getJSONObject().getJSONObject("rss").getJSONObject("channel").getJSONArray("item").length();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	
	
	public String getTitle(int i){
		try {
			return getItem(i).getString("title");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getLink(int i){
		try {
			return getItem(i).getString("link");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getKeywords(int i){
		try {
			return getMediaGroup(i).getString("media:keywords");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public String getCopiright(int i){
		try {
			return getMediaGroup(i).getString("media:copyright");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getMediaTitle(int i){
		try {
			return getMediaGroup(i).getString("media:title");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getThumbnail(int i){
		try {
			return getMediaGroup(i).getJSONObject("media:thumbnail").getString("url");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getMedia(int i){
		try {
			return getMediaGroup(i).getString("xmlns:media");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getMediaDuration(int i){
		try {
			return getMediaGroup(i).getString("media:duration");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int getCategoryLength(int currentItem){
		try {
			return getMediaGroup(currentItem).getJSONArray("media:category").length();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public String getCategorySchmema(int currentItem,int currentCategory){
		try {
			return getMediaGroup(currentItem).getJSONArray("media:category").getJSONObject(currentCategory).getString("schema");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getCategory(int currentItem,int currentCategory){
		try {
			return getMediaGroup(currentItem).getJSONArray("media:category").getJSONObject(currentCategory).getString("content");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Date getPubDate(int i){
		try {
			return new Date(getItem(i).getString("pubDate"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getDescription(int i){
		try {
			return getItem(i).getString("description");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getGuid(int i){
		try {
			return getItem(i).getJSONObject("guid").getString("content");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean getGuidIsPermaLink(int i){
		try {
			return getItem(i).getJSONObject("guid").getBoolean("isPermaLink");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
}
