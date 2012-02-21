/**
 * 
 */
package com.epam.android.common.task;


/**
 * @author Uladzimir_Klyshevich
 *
 */
public abstract class AbstractTaskCreator implements ITaskCreator {

	private CommonAsyncTask task;

	
	public CommonAsyncTask getTask() {
		if (task == null) {
			task = create();
		}
		return task;
	}

}
