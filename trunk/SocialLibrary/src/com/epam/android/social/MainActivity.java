package com.epam.android.social;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.epam.android.layouts.MyLayoutActivity;
import com.epam.android.social.helper.OAuthHelper;

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
	
	public void onTwitterButtonClick(View view){
		if(!OAuthHelper.getInstanse().isLogin(getApplicationContext())){
			startActivity(new Intent(this, TwitterLoginActivity.class));
		}
		else{
			startActivity(new Intent(this, TwitterMainFragmentActivity.class));
		}
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