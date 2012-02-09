package com.epam.android.common.task;

import android.content.Context;

public interface IDelegate {

	void showloading();
	
	void hideloading();
	
	void handleError(CommonAsyncTask task, Exception e);
	
	Context getContext();

	void addTask(CommonAsyncTask task);
	
	void removeTask(CommonAsyncTask task);
}
