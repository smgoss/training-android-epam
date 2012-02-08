package com.epam.android.common.task;

import java.io.IOException;
import java.util.List;

public abstract class LoadArrayModelAsyncTask <B> extends CommonAsyncTask<List<B>>  {
	
	public LoadArrayModelAsyncTask(IDelegate delegate) {
		super(delegate);
	}


	@Override
	public List<B> load(String url) throws IOException {
		// TODO Load json to model 
		return null;
	}


	@Override
	public abstract void success(List<B> result);
		
	
}
	

