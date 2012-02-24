package com.epam.android.social;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.epam.android.common.http.HttpClient;
import com.epam.android.common.http.Loader;
import com.epam.android.common.annotation.Tag;
import com.epam.android.layouts.MyLayoutActivity;
import com.epam.android.social.model.Item;
import com.epam.android.social.model.Tweet;
import com.epam.android.social.model.User;

public class MainActivity extends Activity {
	
	private static final String TAG = MainActivity.class.getSimpleName();

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		isOnline();

	}

	public void onModelButtonClick(View view) {
		Intent intent = new Intent(this, ModelSampleActivity.class);
		startActivity(intent);
	}

	public void onArrayModelButtonClick(View view) {
		Intent intent = new Intent(this, ArrayModelSampleActivity.class);
		startActivity(intent);
	}

	public void onParseXMLtoJSONButtonClick(View view)
			throws ClientProtocolException, JSONException, IOException {
		
		Intent intent = new Intent(this, ArrayModelFromXMLSampleActivity.class);
		startActivity(intent);
	}

	public void onRunAsynkTaskButtonClick(View view) {
		Intent intent = new Intent(this, TestAsyncTaskActivity.class);
		startActivity(intent);
	}

	public void onTestListViewButtonClick(View view) {
		startActivity(new Intent(this, TestListViewActivity.class));
	}
	
	public void onMultiSampleButtonClick(View view) {
		startActivity(new Intent(this, MultiSampleActivity.class));
	}
	
	
	public void onMyLayoutButtonClick(View view){
		startActivity(new Intent(this, MyLayoutActivity.class));
	}
	
	public void onTwitterSearchButtonClick(View view){
//		Loader loader = new Loader((HttpClient) getApplicationContext().getSystemService(HttpClient.HTTP_CLIENT));
//		try {
//			List<Tweet> tweet = loader.loadArrayModel("http://search.twitter.com/search.json?q=android", Tweet.MODEL_CREATOR);
//			
//			for (int i = 0; i < tweet.size(); i++) {
//				Log.d(TAG, "" + tweet.get(i).getProfileUrl());
//				Log.d(TAG, "" + tweet.get(i).getText());
//				Log.d(TAG, "" + tweet.get(i).getUserName());
//				Log.d(TAG, "" + tweet.get(i).getUserID());
//				Log.d(TAG, "" + tweet.get(i).getPublicdDate());
//			}
//			
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		startActivity(new Intent(this, TweeterActivity.class));
	}
	
	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		Toast.makeText(this, R.string.not_internet, Toast.LENGTH_LONG).show();
		return false;
	}
}