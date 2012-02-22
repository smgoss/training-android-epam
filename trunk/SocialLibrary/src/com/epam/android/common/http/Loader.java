package com.epam.android.common.http;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import android.content.Context;
import android.util.Log;
import android.webkit.JsPromptResult;

import com.epam.android.common.annotation.Tag;
import com.epam.android.common.model.IModelCreator;
import com.epam.android.common.model.JSON;
import com.epam.android.common.utils.GetSystemService;
import com.epam.android.common.utils.JsonModelConverter;

public class Loader {
	
	
	public static final String LOADER = "++LOADER+";

	@SuppressWarnings("unused")
	private static final String TAG = Loader.class.getSimpleName();

	private HttpClient mHttpClient;
	
    public static Loader get(Context context) {
    	return (Loader) GetSystemService.get(context,LOADER);
    }
	

	public Loader(HttpClient httpClient) {
		this.mHttpClient = httpClient;
	}

	public <T> T loadModel(String url, IModelCreator<T> modelCreator)
			throws ClientProtocolException, IOException, JSONException {

		JSONObject jsonObject = new JSONObject(mHttpClient.execute(new HttpGet(
				url)));
		return modelCreator.create(jsonObject);

	}

	public <T> List<T> loadArrayModel(String url, IModelCreator<T> modelCreator)
			throws ClientProtocolException, JSONException, IOException {
		JSONArray jsonArray = new JSONArray(
				mHttpClient.execute(new HttpGet(url)));
		return JsonModelConverter.convertJSONArrayToList(jsonArray,
				modelCreator);
	}
	
	public <T> List<T> loadArrayModelFromXml(String url, IModelCreator<T> modelCreator)
			throws ClientProtocolException, JSONException, IOException {
		JSONObject jsonObject = createJsonFromXml(url);
		//TODO get @Tag annotation get value,  
		/*return JsonModelConverter.convertJSONArrayToList(jsonArray,
				modelCreator);*/
		
		
		return null;
	}

	
	public JSONObject createJsonFromXml(String url) throws JSONException, ClientProtocolException, IOException {
		
		JSONObject jsonObject = XML.toJSONObject(mHttpClient.execute(new HttpGet(url)));
		JSONArray names = jsonObject.names();
		String name = (String) names.get(0);
		Log.d(TAG, name);
		JSONArray names2 = jsonObject.getJSONObject(name).names();
		String name2 = (String) names2.get(0);
		JSONArray names3 = jsonObject.getJSONObject(name).getJSONObject(name2).names();
		String name4 = (String) names3.get(0);
		JSONArray names31 = jsonObject.getJSONObject(name).getJSONObject(name2).getJSONArray(name4);
		Log.d(TAG, "" + names31);
		
		
		return jsonObject;
	}
	
	private JSONArray searchJSONArray(String search, JSONObject inputJSONObject) throws JSONException{
		
		JSONArray names = inputJSONObject.names();
		String name = null;
		for (int i = 0; i < names.length(); i++) {
			name = (String) names.get(i);
			
			
		}
		
		return null;
	}
	
	public <T> List<T> loadArrayModelFromXmlByAnnotation(String url,
			IModelCreator<T> modelCreator) throws ClientProtocolException, JSONException, IOException {
		JSONObject jsonObject = createJsonFromXml(url);
		T fake = modelCreator.create(new JSONObject());
		Tag annotation = fake.getClass().getAnnotation(Tag.class);
		String[] keys = annotation.keys();
		JSON[] types = annotation.types();
		JSONObject resultObject = null;
		JSONArray array = null;
		
		for (int i = 0; i < keys.length; i++) {
			JSON type = types[i];
			if (type == JSON.JSONObject) {
				if (resultObject == null) {
					resultObject = jsonObject.getJSONObject(keys[i]);
				} else {
					resultObject = resultObject.getJSONObject(keys[i]);
				}
			} else {
				array = jsonObject.getJSONObject("rss").getJSONObject("channel").getJSONArray(keys[i]);
			}
		}
		return JsonModelConverter.convertJSONArrayToList(array,
				modelCreator);
		
	}
	

}
