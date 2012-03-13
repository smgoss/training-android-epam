package com.epam.android.common.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import android.content.Context;

import com.epam.android.common.annotation.JSON;
import com.epam.android.common.annotation.Tag;
import com.epam.android.common.model.IModelCreator;
import com.epam.android.common.utils.GetSystemService;
import com.epam.android.common.utils.JsonModelConverter;

public class Loader {

	public interface IRule {

		void applyRule(HttpUriRequest request);

	}

	public static final String LOADER = "++LOADER+";

	@SuppressWarnings("unused")
	private static final String TAG = Loader.class.getSimpleName();

	private HttpClient mHttpClient;

	private List<IRule> listRule;

	public static Loader get(Context context) {
		return (Loader) GetSystemService.get(context, LOADER);
	}

	public Loader(HttpClient httpClient) {
		this.mHttpClient = httpClient;
	}

	public <T> T loadModel(String url, IModelCreator<T> modelCreator)
			throws ClientProtocolException, IOException, JSONException {

		JSONObject jsonObject = new JSONObject(execute(url));
		return modelCreator.create(jsonObject);

	}

	public <T> List<T> loadArrayModel(String url, IModelCreator<T> modelCreator)
			throws ClientProtocolException, JSONException, IOException {
		JSONArray jsonArray = new JSONArray(execute(url));
		return JsonModelConverter.convertJSONArrayToList(jsonArray,
				modelCreator);

	}

	public <T> List<T> loadArrayModelByAnnotation(String url,
			IModelCreator<T> modelCreator) throws ClientProtocolException,
			JSONException, IOException {
		JSONObject jsonObject = new JSONObject(execute(url));
		JSONArray jsonArray = getJSONArrayFromAnnotation(jsonObject,
				modelCreator);
		return JsonModelConverter.convertJSONArrayToList(jsonArray,
				modelCreator);
	}

	private <T> JSONArray getJSONArrayFromAnnotation(JSONObject jsonObject,
			IModelCreator<T> modelCreator) throws JSONException {

		T fake = modelCreator.create(new JSONObject());
		Tag annotation = fake.getClass().getAnnotation(Tag.class);
		String[] keys = annotation.keys();
		JSON[] types = annotation.types();
		JSONObject resultObject = null;
		JSONArray resultArray = null;

		for (int i = 0; i < keys.length; i++) {
			JSON type = types[i];
			if (type == JSON.JSONObject) {
				if (resultObject == null) {
					resultObject = jsonObject.getJSONObject(keys[i]);
				} else {
					resultObject = resultObject.getJSONObject(keys[i]);
				}
			} else {
				if (resultArray == null) {
					if (resultObject == null) {
						resultArray = jsonObject.getJSONArray(keys[i]);
					} else {
						resultArray = resultObject.getJSONArray(keys[i]);
					}
				}
			}
		}

		return resultArray;
	}

	public JSONObject createJsonFromXml(String url) throws JSONException,
			ClientProtocolException, IOException {
		return XML.toJSONObject(execute(url));

	}

	public <T> List<T> loadArrayModelFromXmlByAnnotation(String url,
			IModelCreator<T> modelCreator) throws ClientProtocolException,
			JSONException, IOException {
		JSONObject jsonObject = createJsonFromXml(url);
		JSONArray resultArray = getJSONArrayFromAnnotation(jsonObject,
				modelCreator);
		return JsonModelConverter.convertJSONArrayToList(resultArray,
				modelCreator);

	}

	private String execute(String url) throws ClientProtocolException,
			IOException {
		HttpGet request = new HttpGet(url);
		if (listRule != null) {
			for (IRule rule : listRule) {
				rule.applyRule(request);
			}
		}
		return mHttpClient.execute(request);
	}

	public void addRule(IRule rule) {
		if (listRule == null) {
			listRule = new ArrayList<Loader.IRule>();
		}
		listRule.add(rule);
	}

}
