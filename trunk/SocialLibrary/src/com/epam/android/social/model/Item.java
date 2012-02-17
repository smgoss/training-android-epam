package com.epam.android.social.model;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

//@Tag("item")
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
/*TODO from xml
	public String getName() {
		return getString("name");
	}

	public String getImageUrl() {
		return getString("imageUrl");
	}*/

}
