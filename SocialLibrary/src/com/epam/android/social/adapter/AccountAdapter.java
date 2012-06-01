package com.epam.android.social.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.epam.android.common.utils.GetSystemService;
import com.epam.android.social.R;
import com.epam.android.social.model.Account;
import com.google.android.imageloader.ImageLoader;

public class AccountAdapter extends BaseAdapter {

	private List<Account> list;

	private Context mContext;

	private ImageLoader mImageLoader;

	private int avatarSizeOnDip = 60;

	private int paddingOnDip = 40;

	public AccountAdapter(List<Account> list, Context context) {
		this.list = list;
		mContext = context;
		mImageLoader = (ImageLoader) GetSystemService.get(mContext,
				ImageLoader.IMAGE_LOADER_SERVICE);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return Long.valueOf(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		int avatarSize = convertDipToPix(avatarSizeOnDip);

		int padding = convertDipToPix(paddingOnDip);

		ImageView avatar = new ImageView(mContext);
		avatar.setLayoutParams(new LayoutParams(avatarSize, avatarSize));
		mImageLoader.bind(avatar, list.get(position).getProfileUrl(), null);
		avatar.setId(100);
//		avatar.setBackgroundResource(R.drawable.bg_shadow_photo);
//		avatar.setPadding(0, 0, 0, convertDipToPix(3));

		TextView name = new TextView(mContext);
		name.setText(list.get(position).getUserName());
		name.setTextAppearance(mContext, R.style.AccountTextStyle);

		RelativeLayout layout = new RelativeLayout(mContext);
		layout.setPadding(padding, 0, padding, 0);
		layout.addView(avatar);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.BELOW, 100);
		layout.addView(name, lp);

		return layout;

	}

	private int convertDipToPix(int dip) {
		final float scale = mContext.getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}
}
