package com.epam.android.social;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.epam.android.common.MultiTaskActivity;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.LoadArrayModelAsyncTask;
import com.epam.android.common.task.LoadModelAsyncTask;
import com.epam.android.social.adapter.MultiModelListAdapter;
import com.epam.android.social.model.Other;
import com.epam.android.social.model.User;
import com.google.android.imageloader.ImageLoader;

public class MultiSampleActivity extends MultiTaskActivity {

	private ListView mListView;

	public static final String URL1 = "http://dl.dropbox.com/u/52289508/object1.json";

	public static final String URL2 = "http://dl.dropbox.com/u/52289508/array.json";

	private static final String TAG = MultiSampleActivity.class.getSimpleName();

	public List<CommonAsyncTask> setTasks() {
		mTasks.clear();

		if (!mAsyncTaskManager.checkTask(this.getClass().getName(), URL1)) {
			mTasks.add(new LoadModelAsyncTask<User>(URL1, this,
					User.MODEL_CREATOR) {

				@Override
				protected User doInBackground(String... params) {
					for (int i = 5; i > 0; --i) {
						// Check if task is cancelled
						if (isCancelled()) {
							// This return causes onPostExecute call on
							// UI thread
							return null;
						}

						try {
							// This call causes onProgressUpdate call on
							// UI thread
							publishProgress(MultiSampleActivity.this.getString(
									R.string.task_working, i));
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
							// This return causes onPostExecute call on
							// UI thread

							return null;
						}
					}
					return super.doInBackground(params);
				}
			});
		} else {
			mTasks.add(mAsyncTaskManager.getTask(this.getClass().getName(),
					URL1));
		}
		if (!mAsyncTaskManager.checkTask(this.getClass().getName(), URL2)) {
			mTasks.add(new LoadArrayModelAsyncTask<Other>(URL2, this,
					Other.MODEL_CREATOR));
		} else {
			mTasks.add(mAsyncTaskManager.getTask(this.getClass().getName(),
					URL2));
		}
		return mTasks;
	}

	@Override
	public int getLayoutResource() {
		return R.layout.load_multi_model;
	}

	// TODO success(User user)
	// FIXME result = null
	@Override
	protected void success(Intent intent) {
		String taskKey = intent.getStringExtra(CommonAsyncTask.TASK_KEY);
		CommonAsyncTask task = mAsyncTaskManager.getTask(this.getClass()
				.getName(), taskKey);
		if (taskKey.equals(URL1)) {
			TextView userName = (TextView) findViewById(R.id.userModelName);
			ImageView userAvatar = (ImageView) findViewById(R.id.userModelAvatar);
			User result = (User) task.getResult();
			userName.setText(result.getName());
			ImageLoader imageLoader = ImageLoader.get(MultiSampleActivity.this);
			imageLoader.bind(userAvatar, result.getImageUrl(), null);
		} else if (taskKey.equals(URL2)) {
			mListView = (ListView) findViewById(R.id.array_multi_list);
			List<Other> other = (List<Other>) task.getResult();
			mListView.setAdapter(new MultiModelListAdapter(
					MultiSampleActivity.this, R.layout.load_multi_model_item,
					other));
		}
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
