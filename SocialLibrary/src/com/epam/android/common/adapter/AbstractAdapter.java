/**
 * 
 */
package com.epam.android.common.adapter;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.epam.android.common.model.BaseModel;
import com.epam.android.common.model.IModelCreator;
import com.epam.android.social.helper.ImageGetHelper;
import com.google.android.imageloader.ImageLoader;

public abstract class AbstractAdapter<T> extends BaseAdapter {

	private static final String TAG = AbstractAdapter.class.getSimpleName();
	
	private final Context mContext;

	private final List<T> mList;

	private final int mItemResource;

	private int currentPosition = 0;

	protected ImageGetHelper mImageLoader;

	public AbstractAdapter(Context c, int pItemResource, List<T> pList) {
		mList = pList;
		mContext = c;
		mItemResource = pItemResource;
		mImageLoader = ImageGetHelper.getInstance();
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
	
	public void remove(int position){
		this.mList.remove(position);
	}
	
}
