package com.epam.android.social;

import java.io.IOException;

import org.json.JSONException;

import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epam.android.common.BaseModelActivity;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.ITaskCreator;
import com.epam.android.common.task.LoadModelAsyncTask;
import com.epam.android.social.model.User;
import com.google.android.imageloader.ImageLoader;

public class TestAsyncTaskActivity extends BaseModelActivity<User> {

	public static final String URL = "http://dl.dropbox.com/u/52289508/array.json";

	private static final String TAG = TestAsyncTaskActivity.class
			.getSimpleName();

	@Override
	protected void executeAsyncTask() {

		executeTask(new ITaskCreator() {

			public CommonAsyncTask<User> create() {
				return new LoadModelAsyncTask<User>(URL,
						TestAsyncTaskActivity.this, User.MODEL_CREATOR) {

					@Override
					protected User doInBackground(String... params) {
						try {
							for (int i = 10; i > 0; --i) {
								// Check if task is cancelled
								if (isCancelled()) {
									// This return causes onPostExecute call on
									// UI thread
									return null;
								}

								try {
									// This call causes onProgressUpdate call on
									// UI thread
									publishProgress(TestAsyncTaskActivity.this
											.getString(R.string.task_working, i));
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
									// This return causes onPostExecute call on
									// UI thread

									return null;
								}
							}
							Log.d(TAG, "back");
							publishProgress("Loading from http");
							Log.d(TAG, load().toString());
							return load();
						} catch (IOException e) {
							Log.e(TAG, "crash during loading data", e);
							return null;
						} catch (JSONException e1) {
							Log.e(TAG, "crash during loading data", e1);
							return null;
						}
					}
				};
			}

		});
	}

	@Override
	protected void success(Intent intent) {
		TextView userName = (TextView) findViewById(R.id.userName);
		ImageView userAvatar = (ImageView) findViewById(R.id.userAvatar);
		User result = intent.getParcelableExtra(CommonAsyncTask.RESULT);
		userName.setText(result.getName());
		ImageLoader imageLoader = ImageLoader.get(TestAsyncTaskActivity.this);
		imageLoader.bind(userAvatar, result.getImageUrl(), null);
		Toast.makeText(TestAsyncTaskActivity.this,
				getString(R.string.task_completed), Toast.LENGTH_LONG).show();
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
	public String getKey() {
		return URL;
	}

}
