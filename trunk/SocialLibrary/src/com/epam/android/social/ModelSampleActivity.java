package com.epam.android.social;

import android.os.Bundle;
import android.util.Log;
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

		//TODO remove last param
		new LoadModelAsyncTask<User>(URL, this, User.MODEL_CREATOR, this) {

			@Override
			public void success(User result) {
				Log.d("AST", "successed");
				TextView userName = (TextView) findViewById(R.id.userName);
				ImageView userAvatar = (ImageView) findViewById(R.id.userAvatar);
				userName.setText(result.getName());
				ImageLoader imageLoader = ImageLoader.get(ModelSampleActivity.this);
				imageLoader.bind(userAvatar, result.getImageUrl(), null);

			}
		}.execute();
	}

}
