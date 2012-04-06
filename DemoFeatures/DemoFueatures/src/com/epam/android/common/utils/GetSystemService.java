package com.epam.android.common.utils;

import android.content.Context;

public class GetSystemService {
	public static Object get(Context context, String name) {
		Object systemService = context.getSystemService(name);
		if (systemService == null) {
			context = context.getApplicationContext();
			systemService = context.getSystemService(name);
		}
		if (systemService == null) {
			throw new IllegalStateException(name + " not available");
		}
		return systemService;
	}

}
