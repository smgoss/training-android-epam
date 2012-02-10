package com.epam.android.social;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.epam.android.common.task.LoadModelAsyncTask;
import com.epam.android.social.model.User;
import com.google.android.imageloader.ImageLoader;

public class ModelSampleActivity extends DelegateActivity {

	public static final String URL = "http://dl.dropbox.com/u/16403954/bm.json";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_model);

		new LoadModelAsyncTask<User>(URL, this) {

			@Override
			public void success(User result) {
				TextView userName = (TextView) findViewById(R.id.userName);
				ImageView userAvatar = (ImageView) findViewById(R.id.userAvatar);
				userName.setText(result.getName());
				ImageLoader imageLoader = ImageLoader
						.get(ModelSampleActivity.this);
				imageLoader.bind(userAvatar, result.getImageUrl(), null);

			}
		}.execute();
	}

}
