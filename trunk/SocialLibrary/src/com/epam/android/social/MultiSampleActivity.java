package com.epam.android.social;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.epam.android.common.BaseModelActivity;
import com.epam.android.common.MultiTaskActivity;
import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.LoadArrayModelAsyncTask;
import com.epam.android.common.task.LoadModelAsyncTask;
import com.epam.android.social.model.User;
import com.epam.android.social.model.Other;
import com.google.android.imageloader.ImageLoader;

public class MultiSampleActivity extends MultiTaskActivity {

	private ArrayList<CommonAsyncTask> tasks = new ArrayList<CommonAsyncTask>();
	
	public static final String URL1 = "http://dl.dropbox.com/u/52289508/object1.json";
	
	public static final String URL2 = "http://dl.dropbox.com/u/52289508/array.json";

	private static final String TAG = MultiSampleActivity.class.getSimpleName();

	public List<CommonAsyncTask> getTasks() {
		tasks.add(new LoadModelAsyncTask<User>(URL1, this, User.MODEL_CREATOR));
		tasks.add(new LoadArrayModelAsyncTask<Other>(URL2, this, Other.MODEL_CREATOR));
		return tasks;
	}

	@Override
	public int getLayoutResource() {
		return R.layout.load_multi_model;
	}

	// TODO success(User user)
	@Override
	protected void success(Intent intent) {
		Log.d(TAG, "sucessed " + intent.getStringExtra(CommonAsyncTask.TASK));
//		// TODO if (intent.getStringExtra(CommonAsyncTask.TASK))
//		TextView userName = (TextView) findViewById(R.id.userName);
//		ImageView userAvatar = (ImageView) findViewById(R.id.userAvatar);
//		User result = intent.getParcelableExtra(CommonAsyncTask.RESULT);
//		userName.setText(result.getName());
//		ImageLoader imageLoader = ImageLoader.get(MultiSampleActivity.this);
//		imageLoader.bind(userAvatar, result.getImageUrl(), null);
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
