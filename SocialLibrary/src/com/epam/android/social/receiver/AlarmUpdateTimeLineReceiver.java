package com.epam.android.social.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.epam.android.social.common.fragments.CommonTwitterFragment;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.constants.ReceiverConstants;
import com.epam.android.social.service.UpdateTimeLineService;

public class AlarmUpdateTimeLineReceiver extends BroadcastReceiver {

	private static final String TAG = AlarmUpdateTimeLineReceiver.class.getSimpleName();

	@Override
	public void onReceive(final Context context, final Intent paramIntent) {
		new Runnable() {

			@Override
			public void run() {
				Intent serviceIntent = new Intent(context, UpdateTimeLineService.class);

				if (CommonTwitterFragment.getRefreshQueryInterface() != null
						&& !CommonTwitterFragment.getRefreshQueryInterface()
								.getRefreshQuery().equals("")) {
					serviceIntent.putExtra(ReceiverConstants.ACTION,
							ReceiverConstants.UPDATE_FEEDS);
					serviceIntent.putExtra(ApplicationConstants.ARG_QUERY,
							CommonTwitterFragment.getRefreshQueryInterface()
									.getRefreshQuery());
					context.startService(serviceIntent);
				}

			}
		}.run();

	}

	public static void setAlarm(Context context) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmUpdateTimeLineReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
				ReceiverConstants.REFRESH_INTERVAL, pi);

	}

	public static void cancelAlarm(Context context) {
		Intent intent = new Intent(context, AlarmUpdateTimeLineReceiver.class);
		PendingIntent sender = PendingIntent
				.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}
}
