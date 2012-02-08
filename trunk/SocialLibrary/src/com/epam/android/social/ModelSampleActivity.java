package com.epam.android.social;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class ModelSampleActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_model);
		Toast.makeText(this, "TODO LoadModel undone!", Toast.LENGTH_SHORT)
				.show();
		// TODO Load username and avatar
	}

}
