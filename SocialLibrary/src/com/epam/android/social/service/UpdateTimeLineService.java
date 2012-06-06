package com.epam.android.social.service;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;

import com.epam.android.common.task.HttpGetJsonArrayAsyncTask;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.constants.ReceiverConstants;
import com.epam.android.social.model.Tweet;

public class UpdateTimeLineService extends Service {

	private static final String TAG = UpdateTimeLineService.class.getSimpleName();

	@Override
	public IBinder onBind(Intent paramIntent) {
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.d(TAG, "start service");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if ((intent != null)
				&& intent.getExtras().containsKey(ReceiverConstants.ACTION)
				&& ReceiverConstants.UPDATE_FEEDS.equals(intent.getExtras()
						.getString(ReceiverConstants.ACTION))) {
			String query = intent.getExtras().getString(
					ApplicationConstants.ARG_QUERY);
			updateHomeTimeLine(query);
		}
		return START_STICKY;
	}

	private void updateHomeTimeLine(String query) {

		Log.d(TAG, "start update TimeLIne");
		new HttpGetJsonArrayAsyncTask<Tweet>(this) {

			@Override
			public void success(List<Tweet> result) {
				sendBroadcast(result);
			}
		}.execute(query);

	}

	private void sendBroadcast(List<Tweet> list) {
		Intent intent = new Intent(ReceiverConstants.ON_UPDATE_COMLETE);
		intent.putParcelableArrayListExtra(
				ApplicationConstants.LIST_FROM_SERVICE,
				(ArrayList<? extends Parcelable>) list);
		sendBroadcast(intent);
	}
}
