package com.epam.android.common.task;

import android.content.Context;

public interface IDelegate {

	void showLoading();

	void hideLoading();
	
	void showProgress(String textMessage);

	@SuppressWarnings("rawtypes")
	void handleError(CommonAsyncTask task, Exception e);

	Context getContext();

	void executeTask(ITaskCreator taskCreator);

	String getKey();

}