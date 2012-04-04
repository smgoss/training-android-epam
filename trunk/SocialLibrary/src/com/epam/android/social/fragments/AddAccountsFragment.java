package com.epam.android.social.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.epam.android.social.R;

public class AddAccountsFragment extends Fragment {

	private static final String TAG = AddAccountsFragment.class.getSimpleName();

	private RelativeLayout relativeLayout;

	private RelativeLayout.LayoutParams params;

	private ImageButton addAccountButton;

	private int sizeAvatarOnPixel;

	private final int sizeAvatarOnDip = 40;

	private String[] userAccount = new String[] { "Ilya.shknaj", "Shknaj" };

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		addAccountButton = (ImageButton) getView().findViewById(
				R.id.addAccountButton);
		addAccountButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				addPictureNewAccount();
				// startActivity(new Intent(getView().getContext(),
				// TwitterLoginActivity.class));
			}
		});
	}

	private void addPictureNewAccount() {

		 relativeLayout = (RelativeLayout) getView().findViewById(
		 R.id.accountLayout);
		// Resources r = getResources();
		// sizeAvatarOnPixel = (int) TypedValue.applyDimension(
		// TypedValue.COMPLEX_UNIT_DIP, sizeAvatarOnDip,
		// r.getDisplayMetrics());
		// params = new LayoutParams(sizeAvatarOnPixel, sizeAvatarOnPixel);
		// ImageView imageView = new ImageView(getView().getContext());
		// imageView.setImageDrawable(getResources().getDrawable(R.drawable.ava));
		// imageView.setId(5);
		// imageView.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Toast.makeText(getView().getContext(), "text",
		// Toast.LENGTH_SHORT).show();
		// }
		// });
		// params.addRule(RelativeLayout.RIGHT_OF, R.id.addAccountButton);
		// params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		// params.leftMargin = 5;
		// relativeLayout.addView(imageView, params);
		LayoutInflater inflater = (LayoutInflater)
			    getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout layoutItem = (RelativeLayout) inflater.inflate(
		        R.layout.account, null, false);
		((TextView)layoutItem.findViewById(R.id.accountName)).setText("hello");
		relativeLayout.addView(layoutItem);
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.account, null, false);
	}

}
