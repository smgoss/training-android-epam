package com.epam.android.social.model;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;

public class ErrorModel extends BaseModel {

	private static final String TAG = ErrorModel.class.getSimpleName();

	public static final IModelCreator<ErrorModel> MODEL_CREATOR = new IModelCreator<ErrorModel>() {

		public ErrorModel create(JSONObject jsonObject) {
			return new ErrorModel(jsonObject);
		}

	};

	public static final Parcelable.Creator<ErrorModel> CREATOR = new Creator<ErrorModel>() {

		public ErrorModel[] newArray(int size) {
			return new ErrorModel[size];
		}

		public ErrorModel createFromParcel(Parcel in) {
			return new ErrorModel(in);
		}
	};

	public ErrorModel() {
		super();
	}

	public ErrorModel(JSONObject json) {
		super(json);
	}

	public ErrorModel(Parcel in) {
		super(in);
	}

	public ErrorModel(String json) {
		super(json);
	}
	
	public String getTextError(){
		return getString("error");
	}
}
