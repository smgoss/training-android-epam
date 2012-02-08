package com.epam.android.common.task;

import java.io.IOException;


public abstract class LoadModelAsyncTask <B> extends CommonAsyncTask <B>{



	public LoadModelAsyncTask(IDelegate delegate) {
		super(delegate);
		// TODO Auto-generated constructor stub
	}


	@Override
	public B load(String url) throws IOException {
		// TODO Load json to model 
		return null;
	}


	@Override
	public abstract void success(B result);
		


}
