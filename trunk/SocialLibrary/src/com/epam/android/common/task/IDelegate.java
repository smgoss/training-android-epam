package com.epam.android.common.task;

import java.util.List;

import android.content.Context;
import android.content.Intent;

public interface IDelegate {

	void showLoading();

	void hideLoading();
	
	void showProgress(String textMessage);

	@SuppressWarnings("rawtypes")
	void handleError(CommonAsyncTask task, Exception e);

	Context getContext();

	void executeTask(ITaskCreator taskCreator);

	void startTasks();

	void success(Intent intent);





}
