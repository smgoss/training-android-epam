package com.epam.android.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.epam.android.social.R;

public final class Utils {

	public static void showMessage(Context context, String title, String message) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		View layout = inflater.inflate(R.layout.v_toast,
		                               null);

		TextView toastTitleView = (TextView) layout.findViewById(R.id.toastTitle);
		toastTitleView.setText(title);
		
		TextView toastTextView = (TextView) layout.findViewById(R.id.toastText);
		toastTextView.setText(message);

		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
		
	}
	
}
