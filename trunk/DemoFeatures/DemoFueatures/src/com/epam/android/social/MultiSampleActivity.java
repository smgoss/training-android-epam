package com.epam.android.social;

import java.util.List;

import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.epam.android.common.DelegateActivity;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.LoadArrayModelByAnnotationAsyncTask;
import com.epam.android.common.task.LoadModelAsyncTask;
import com.epam.android.social.adapter.MultiModelListAdapter;
import com.epam.android.social.model.Other;
import com.epam.android.social.model.User;
import com.google.android.imageloader.ImageLoader;

public class MultiSampleActivity extends DelegateActivity {

	private ListView mListView;

	public static final String URL1 = "http://dl.dropbox.com/u/52289508/object1.json";

	public static final String URL2 = "http://dl.dropbox.com/u/52289508/array.json";

	private static final String TAG = MultiSampleActivity.class.getSimpleName();

	public void startTasks() {

		executeActivityTasks(new LoadModelAsyncTask<User>(URL1, this,
				User.MODEL_CREATOR) {
			@Override
			protected User doInBackground(String... params) {
				for (int i = 10; i > 0; --i) {
					if (isCancelled()) {
						return null;
					}
					try {
						publishProgress(MultiSampleActivity.this.getString(
								R.string.task_working, i));
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
						return null;
					}
				}
				return super.doInBackground(params);
			}
		});

		executeActivityTasks(new LoadArrayModelByAnnotationAsyncTask<Other>(URL2, this,
				Other.MODEL_CREATOR));
	}

	@Override
	public int getLayoutResource() {
		return R.layout.load_multi_model;
	}

	@Override
	public void success(Intent intent) {
		if (isAsyncTaskResult(URL1, intent)) {
			User user = intent.getParcelableExtra(CommonAsyncTask.RESULT);

			TextView userName = (TextView) findViewById(R.id.userModelName);
			ImageView userAvatar = (ImageView) findViewById(R.id.userModelAvatar);
			userName.setText(user.getName());
			ImageLoader imageLoader = ImageLoader.get(MultiSampleActivity.this);
			imageLoader.bind(userAvatar, user.getImageUrl(), null);

		} else if (isAsyncTaskResult(URL2, intent)) {
			List<Other> other = intent
					.getParcelableArrayListExtra(CommonAsyncTask.RESULT);

			mListView = (ListView) findViewById(R.id.array_multi_list);
			mListView.setAdapter(new MultiModelListAdapter(
					MultiSampleActivity.this, R.layout.load_multi_model_item,
					other));

		} else {
			Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT);
			Log.d(TAG, "Nothing to show");
		}
	}

}
