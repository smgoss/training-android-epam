package com.epam.android.social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void onModelButtonClick(View view) {
		Intent intent = new Intent(this, ModelSampleActivity.class);
		startActivity(intent);
	}
	
	public void onArrayModelButtonClick(View view) {
		Intent intent = new Intent(this, ArrayModelSampleActivity.class);
		startActivity(intent);
	}
}