package com.epam.android.social;

import java.io.IOException;

import com.epam.android.common.task.IDelegate;

import android.app.Activity;
import android.content.Context;

public class DelegateActivity extends Activity implements IDelegate {

	public void showloading() {
		// TODO show loading dialog
		
	}

	public void hideloading() {
		// TODO hide loading dialog
		
	}

	public void handleError(IOException e) {
		// TODO show toast message with error
		
	}

	public Context getContext() {
		return this;
	}

}
