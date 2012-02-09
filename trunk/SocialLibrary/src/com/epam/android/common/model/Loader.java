package com.epam.android.common.model;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.epam.android.common.http.HttpClient;
  

public class Loader {
	public static final String LOADER = "++LOADER+";
	private static final String TAG = Loader.class.getSimpleName();
	private HttpClient httpClient;
	
	public Loader(Context context){
		httpClient = (HttpClient) context.getApplicationContext().getSystemService(HttpClient.HTTP_CLIENT);
	}
	
	
	public <T> T load(String url, IModelCreator<T> modelCreator) throws ClientProtocolException, IOException, JSONException{
		
		return modelCreator.create(new JSONObject(httpClient.execute(new HttpGet(url))));
		
	}
	
				
}