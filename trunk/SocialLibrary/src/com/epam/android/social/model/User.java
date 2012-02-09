package com.epam.android.social.model;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

import android.graphics.Paint.Join;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class User extends BaseModel{
	private static final String TAG = User.class.getSimpleName();
	
	public static final IModelCreator<User> MODEL_CREATOR = new IModelCreator<User>() {

		public User create(JSONObject jsonObject) {
			return new User(jsonObject);
		}

		public List<User> createArray(JSONArray jsonArray) throws JSONException {
			List<User> userList = new ArrayList<User>();
			
			for(int i = 0; i < jsonArray.length(); i++){
				userList.add(new User(jsonArray.getJSONObject(i)));
			}
			return userList;
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
	

	public User(){
		super();
	}
	
	public User(JSONObject json){
		super(json);
	}
	
	public User(Parcel in){
		super(in);
	}
	
	public User(String json){
		super(json);
	}
	
	public String getName(){
		return getString("name");
	}
	
	public String getImageUrl(){
		return getString("imageUrl");
	}
	
	

}
