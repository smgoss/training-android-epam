/**
 * 
 */
package com.epam.android.common.adapter;

import java.util.List;

import com.google.android.imageloader.ImageLoader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class AbstractAdapter<T> extends BaseAdapter {

	private final Context mContext;

	private final List<T> mList;

	private final int mItemResource;

	private int currentPosition = 0;

	protected ImageLoader mImageLoader;

	public AbstractAdapter(Context c, int pItemResource, List<T> pList) {
		mList = pList;
		mContext = c;
		mItemResource = pItemResource;
		mImageLoader = (ImageLoader) c.getApplicationContext()
				.getSystemService(ImageLoader.IMAGE_LOADER_SERVICE);
	}

	public int getCount() {
		if (mList == null)
			return 0;
		return mList.size();
	}

	public T getItem(int position) {
		return mList.get(position);
	}

	public long getItemId(int position) {
		return Long.valueOf(position);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = createView();
		}
		final T item = mList.get(position);
		init(convertView, item);
		return convertView;
	}

	public abstract void init(View convertView, T item);

	public View createView() {
		return View.inflate(mContext, mItemResource, null);
	}

	public Context getContext() {
		return mContext;
	}

	public List<T> getList() {
		return mList;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

}
