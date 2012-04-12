package com.epam.android.demo.common.task;

public abstract class AbstractTaskCreator implements ITaskCreator {

	private CommonAsyncTask task;

	public CommonAsyncTask getTask() {
		if (task == null) {
			task = create();
		}
		return task;
	}

}
