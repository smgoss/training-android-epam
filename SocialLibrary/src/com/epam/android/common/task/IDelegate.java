package com.epam.android.common.task;

import java.io.IOException;

import android.content.Context;

public interface IDelegate {

	void showloading();
	
	void hideloading();
	
	void handleError(IOException e);
	
	Context getContext();

}
