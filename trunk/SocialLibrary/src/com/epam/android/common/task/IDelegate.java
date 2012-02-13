package com.epam.android.common.task;

import android.content.Context;

public interface IDelegate {

	void showLoading();

	void hideLoading();

	void handleError(CommonAsyncTask task, Exception e);

	Context getContext();

	void removeTask(CommonAsyncTask task);

	void executeTask(ITaskCreator taskCreator);

}
