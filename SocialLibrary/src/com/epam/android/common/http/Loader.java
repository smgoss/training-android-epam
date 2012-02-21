package com.epam.android.common.http;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import android.content.Context;

import com.epam.android.common.annotation.Tag;
import com.epam.android.common.model.IModelCreator;
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
		
		return XML.toJSONObject(mHttpClient.execute(new HttpGet(url)));
	}
	
	public <T> List<T> loadArrayModelFromXmlByAnnotation(String url,
			IModelCreator<T> modelCreator) throws ClientProtocolException, JSONException, IOException {
		JSONObject jsonObject = createJsonFromXml(url);
		T fake = modelCreator.create(new JSONObject());
		Tag annotation = fake.getClass().getAnnotation(Tag.class);
		String[] keys = annotation.keys();
		String[] types = annotation.types();
		JSONObject resultObject = null;
		JSONArray array = null;
		for (int i = 0; i < keys.length; i++) {
			String type = types[i];
			if (type.equals("jsonobject")) {
				if (resultObject == null) {
					resultObject = jsonObject.getJSONObject(keys[i]);
				} else {
					resultObject = resultObject.getJSONObject(keys[i]);
				}
			} else {
				array = resultObject.getJSONArray(keys[i]);
			}
		}
		//TODO get @Tag annotation get value,  
		return JsonModelConverter.convertJSONArrayToList(array,
				modelCreator);
		
		//return null;
	}
	/*public void testAnnotation(){
		try {
			try {
				Method method = Loader.class.getMethod("testAnnotation", null);
				Annotation[] annotations = method.getAnnotations();
				if(annotations.length > 0)
					for(int i = 0; i < ((Tag)annotations[0]).key().length; i++)
						Log.d(TAG, "" + ((Tag)annotations[0]).key()[i]);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}*/
}
