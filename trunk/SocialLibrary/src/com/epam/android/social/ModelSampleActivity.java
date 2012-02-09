package com.epam.android.social;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epam.android.common.model.User;
import com.epam.android.common.task.LoadModelAsyncTask;
import com.google.android.imageloader.ImageLoader;

public class ModelSampleActivity extends DelegateActivity {

	protected ImageLoader mImageLoader;

	public static final String URL = "http://dl.dropbox.com/u/16403954/bm.json";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_model);

		final TextView userName = (TextView) findViewById(R.id.userName);
		final ImageView userAvatar = (ImageView) findViewById(R.id.userAvatar);

		new LoadModelAsyncTask<User>(URL, this, User.MODEL_CREATOR) {

			@Override
			public void success(User result) {
				Log.d("AST", "successed");
				userName.setText(result.getName());
				mImageLoader = ImageLoader.get(ModelSampleActivity.this);
				mImageLoader.bind(userAvatar, result.getImageUrl(), null);

			}
		}.execute();
	}

}
