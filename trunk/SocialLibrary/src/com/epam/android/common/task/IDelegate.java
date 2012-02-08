package com.epam.android.common.task;

import java.io.IOException;

import android.content.Context;

public interface IDelegate {

	public void showloading();
	public void hideloading();
	public void handleError(IOException e);
	public Context getContext();

}
