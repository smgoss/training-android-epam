package com.epam.android.common.model;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONObject;

import android.content.Context;

import com.epam.android.common.http.HttpClient;


public class Loader<B> implements IModelCreator<B>{
	private static final String TAG = Loader.class.getSimpleName();
	private HttpClient httpClient;
	
	public Loader(Context context){
		httpClient = (HttpClient) context.getApplicationContext().getSystemService(HttpClient.HTTP_CLIENT);
	}
	
	public void load(String url) throws ClientProtocolException, IOException{
		
		String result = httpClient.execute(new HttpGet(url));
		
	}
				

	public B create(JSONObject jsonObject) {		
		return null;
	}
		
}