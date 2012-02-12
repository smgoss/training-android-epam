package com.epam.android.social;

import java.security.PublicKey;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.common.task.IDelegate;
import com.epam.android.common.task.ITaskCreator;

public abstract class DelegateActivity extends Activity implements IDelegate {

	private static final String TAG = DelegateActivity.class.getSimpleName();

	private static final String TITLE = "Please wait";

	private static final String MSG = "Loading...";
	
	protected ProgressDialog mProgressDialog;

	public void showloading() {
		if (mProgressDialog == null) {
			mProgressDialog = ProgressDialog.show(getContext(), TITLE, MSG,
					false, true);
		} else {
			mProgressDialog.show();
		}
	}

	public void hideloading() {
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	public void handleError(CommonAsyncTask task, Exception e) {
		Log.e(TAG, "http client err: " + e.getMessage(), e);
		Toast.makeText(getContext(), "http client err: " + e.getMessage(),
				Toast.LENGTH_LONG).show();
		taskCreatorStorage.get(task).create().execute();
		
	}

	public Context getContext() {
		return this;
	}

	private HashMap<CommonAsyncTask, ITaskCreator> taskCreatorStorage = new HashMap<CommonAsyncTask, ITaskCreator>();
	

	public void removeTask(CommonAsyncTask task) {
		// TODO remove task if task ends or canceled
		taskCreatorStorage.remove(task);
	}

	public void executeTask(ITaskCreator taskCreator) {
		CommonAsyncTask task = taskCreator.create();
		taskCreatorStorage.put(task, taskCreator);
		task.execute();
	}

	@Override
	protected void onDestroy() {
		taskCreatorStorage.clear();
		taskCreatorStorage = null;
		super.onDestroy();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(isOnline()){
			onCreate();
		}
		
		
	}

	private boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    
	    Toast.makeText(getApplicationContext(), R.string.not_internet, Toast.LENGTH_LONG).show();
	    return false;
	}
	//TODO rename method onCreate and adeed parametrs Bundle savedInstanceState
	public abstract void onCreate();
	
	
}
