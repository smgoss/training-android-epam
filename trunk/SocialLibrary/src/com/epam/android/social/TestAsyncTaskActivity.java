package com.epam.android.social;

import java.io.IOException;

import org.json.JSONException;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epam.android.common.BaseModelActivity;
import com.epam.android.common.task.LoadModelAsyncTask;
import com.epam.android.social.model.User;
import com.google.android.imageloader.ImageLoader;

public class TestAsyncTaskActivity extends BaseModelActivity<User> {

	public static final String URL = "http://dl.dropbox.com/u/52289508/object2.json";

	private static final String TAG = TestAsyncTaskActivity.class
			.getSimpleName();

	@Override
	public void startTasks() {
		executeActivityTasks(new LoadModelAsyncTask<User>(URL,
				TestAsyncTaskActivity.this) {
			@Override
			protected User doInBackground(String... params) {
				try {
					for (int i = 10; i > 0; --i) {
						if (isCancelled()) {
							return null;
						}
						try {
							publishProgress(TestAsyncTaskActivity.this
									.getString(R.string.task_working, i));
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
							return null;
						}
					}
					publishProgress("Loading from http");
					return load();
				} catch (IOException e) {
					return null;
				} catch (JSONException e1) {
					return null;
				}
			}
		});

	}

	@Override
	public int getLayoutResource() {
		return R.layout.load_model;
	}

	@Override
	public String getUrl() {
		return URL;
	}

	@Override
	protected void success(User result) {
		TextView userName = (TextView) findViewById(R.id.userName);
		ImageView userAvatar = (ImageView) findViewById(R.id.userAvatar);

		userName.setText(result.getName());
		ImageLoader imageLoader = ImageLoader.get(TestAsyncTaskActivity.this);
		imageLoader.bind(userAvatar, result.getImageUrl(), null);
		Toast.makeText(TestAsyncTaskActivity.this,
				getString(R.string.task_completed), Toast.LENGTH_LONG).show();
	}

}
