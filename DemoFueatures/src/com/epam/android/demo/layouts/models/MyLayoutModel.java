package com.epam.android.demo.layouts.models;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.epam.android.demo.common.model.BaseModel;
import com.epam.android.demo.common.model.IModelCreator;

public class MyLayoutModel extends BaseModel {

	@SuppressWarnings("unused")
	private static final String TAG = MyLayoutModel.class.getSimpleName();

	public static final IModelCreator<MyLayoutModel> MODEL_CREATOR = new IModelCreator<MyLayoutModel>() {

		public MyLayoutModel create(JSONObject jsonObject) {
			return new MyLayoutModel(jsonObject);
		}

	};

	public static final Parcelable.Creator<MyLayoutModel> CREATOR = new Creator<MyLayoutModel>() {

		public MyLayoutModel[] newArray(int size) {
			return new MyLayoutModel[size];
		}

		public MyLayoutModel createFromParcel(Parcel in) {
			return new MyLayoutModel(in);
		}
	};

	public MyLayoutModel() {
		super();
	}

	public MyLayoutModel(JSONObject json) {
		super(json);
	}

	public MyLayoutModel(Parcel in) {
		super(in);
	}

	public MyLayoutModel(String json) {
		super(json);
	}

	public String getTitle() {
		return getString("title");
	}

	public String getText() {
		return getString("text");
	}
	
	public Integer getNumber() {
		return getInt("number");
	}

}
