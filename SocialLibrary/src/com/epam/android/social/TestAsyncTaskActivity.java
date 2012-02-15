package com.epam.android.social;

import java.io.IOException;

import org.json.JSONException;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.epam.android.common.task.AsyncTaskManager;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.ITaskCreator;
import com.epam.android.common.task.LoadModelAsyncTask;
import com.epam.android.social.model.User;

public class TestAsyncTaskActivity extends DelegateActivity {

	private Exception e;
	public static final String URL = "http://dl.dropbox.com/u/16403954/bm.json";

	private static final String TAG = TestAsyncTaskActivity.class
			.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("my", "Created");
		setContentView(R.layout.run_asynktask);
		mAsyncTaskManager = AsyncTaskManager.get(this);
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setCancelable(true);

		Log.d("PrDialog", "create " + mProgressDialog.toString());
		// mProgressDialog = setProgressDialog();
		if (mAsyncTaskManager.getTask(getKey()) == null) {
			executeAsyncTask();
		} else {
			mAsyncTaskManager.getTask(getKey());
		}
	}

	private void executeAsyncTask() {

		executeTask(new ITaskCreator() {

			public CommonAsyncTask<User> create() {
				return new LoadModelAsyncTask<User>(URL,
						TestAsyncTaskActivity.this, User.MODEL_CREATOR) {

					@Override
					protected User doInBackground(String... params) {
						try {
							for (int i = 15; i > 0; --i) {
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
							return load();
						} catch (IOException e) {
							Log.e(TAG, "crash during loading data", e);
							return null;
						} catch (JSONException e1) {
							Log.e(TAG, "crash during loading data", e1);
							return null;
						}
					}

					@Override
					public void success(User result) {
						String mResultText;
						if (result == null) {
							mResultText = "null";
						} else {
							mResultText = getString(R.string.task_completed);
						}
						Toast.makeText(TestAsyncTaskActivity.this, mResultText,
								Toast.LENGTH_LONG).show();
					}

				};
			}

		});
	}

	@Override
	public String getKey() {
		return URL;
	}

}
