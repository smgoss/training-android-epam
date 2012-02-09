package com.epam.android.social;

import android.os.Bundle;
import android.widget.Toast;

import com.epam.android.common.model.User;
import com.epam.android.common.task.LoadModelAsyncTask;

public class ModelSampleActivity extends DelegateActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_model);
		Toast.makeText(this, "TODO LoadModel undone!", Toast.LENGTH_SHORT)
				.show();
		// TODO Load username and avatar
		
		new LoadModelAsyncTask<User>("url", this, User.MODEL_CREATOR) {

			@Override
			public void success(User result) {
				// TODO if success
				
			}
		};
		
	}

}
