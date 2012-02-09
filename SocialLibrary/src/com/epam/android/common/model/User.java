package com.epam.android.common.model;


import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class User extends BaseModel{
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
