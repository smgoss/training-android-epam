package com.epam.android.social.fragments;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.epam.android.common.task.HttpPostAsyncTask;
import com.epam.android.social.R;
import com.epam.android.social.api.TwitterAPI;
import com.epam.android.social.common.fragments.BaseArrayModelFragmentWithCustomLoad;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.model.ProfileInfo;
import com.google.android.imageloader.ImageLoader;
import com.google.android.imageloader.ImageLoader.Callback;

public class ChangeProfileFragment extends
		BaseArrayModelFragmentWithCustomLoad<ProfileInfo> {

	private static final String TAG = ChangeProfileFragment.class
			.getSimpleName();

	private static final String DATA_ON_EDIT_TEXT_LIST = "dataOnEditTextList";

	private ImageView avatar;

	private Drawable loadedAvatar;

	private List<EditText> editTextList;

	private static ChangeProfileFragment.ISuccesChoisePhoto iSuccesPhoto;

	public static ChangeProfileFragment newInstance(String query) {
		Bundle bundle = new Bundle();
		ChangeProfileFragment fragment = new ChangeProfileFragment();
		bundle.putString(ApplicationConstants.ARG_QUERY, query);
		fragment.setArguments(bundle);
		return fragment;
	}

	private ChangeProfileFragment() {

	}

	@Override
	public String getUrl() {
		return getArguments().getString(ApplicationConstants.ARG_QUERY);
	}

	@Override
	protected void success(List<ProfileInfo> result) {
		if (editTextList == null) {
			initView(result.get(0));
		} else {
			restoreTextOnEditBox();
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		List<String> dataList = new ArrayList<String>();
		if (editTextList != null) {
			for (int i = 0; i < editTextList.size(); i++) {
				dataList.add(editTextList.get(i).getText().toString());
			}
		}
		getArguments().putStringArrayList(DATA_ON_EDIT_TEXT_LIST,
				(ArrayList<String>) dataList);
	}

	private void restoreTextOnEditBox() {
		List<String> dataEditText = getArguments().getStringArrayList(
				DATA_ON_EDIT_TEXT_LIST);
		if (editTextList != null) {
			for (int i = 0; i < editTextList.size(); i++) {
				editTextList.get(i).setText(dataEditText.get(i));
			}
		}
	}

	private void initView(ProfileInfo result) {
		avatar = (ImageView) getView()
				.findViewById(R.id.changeProfile_userItem).findViewById(
						R.id.profileInfoUserItem_avatar);

		if (loadedAvatar == null) {
			ImageLoader.get(getContext()).bind(
					avatar,
					TwitterAPI.getInstance().getUserAvatarBig(
							result.getScreenName()), new Callback() {

						@Override
						public void onImageLoaded(ImageView view, String url) {
							loadedAvatar = avatar.getDrawable();
						}

						@Override
						public void onImageError(ImageView view, String url,
								Throwable error) {

						}
					});
		}

		TextView name = (TextView) getView().findViewById(
				R.id.changeProfile_userItem).findViewById(
				R.id.profileInfoUserItem_name);
		name.setText(result.getName());

		TextView screenName = (TextView) getView().findViewById(
				R.id.changeProfile_userItem).findViewById(
				R.id.profileInfoUserItem_screenName);
		screenName.setText("@" + result.getScreenName());

		TextView url = (TextView) getView().findViewById(
				R.id.changeProfile_userItem).findViewById(
				R.id.profileInfoUserItem_url);

		url.setText(result.getUrl());

		editTextList = new ArrayList<EditText>();
		final EditText nameEditText = (EditText) getView().findViewById(
				R.id.changeProfile_name);
		nameEditText.setText(result.getName());
		editTextList.add(nameEditText);

		final EditText descriptionEditText = (EditText) getView().findViewById(
				R.id.changeProfile_description);
		descriptionEditText.setText(result.getDescription());
		editTextList.add(descriptionEditText);

		final EditText urlEditText = (EditText) getView().findViewById(
				R.id.changeProfile_url);
		urlEditText.setText(result.getUrl());
		editTextList.add(urlEditText);

		final EditText locationEditText = (EditText) getView().findViewById(
				R.id.changeProfile_location);
		locationEditText.setText(result.getLocation());
		editTextList.add(locationEditText);

		Button saveButton = (Button) getView().findViewById(
				R.id.changeProfile_saveButton);
		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				Bitmap bitmap = ((BitmapDrawable) avatar.getDrawable())
						.getBitmap();
				try {
					new HttpPostAsyncTask(getContext()).execute(TwitterAPI
							.getInstance().getUpdateProfileRequest(
									nameEditText.getText().toString(),
									descriptionEditText.getText().toString(),
									urlEditText.getText().toString(),
									locationEditText.getText().toString()));
					if (!loadedAvatar.equals(avatar.getDrawable())) {
						new HttpPostAsyncTask(getContext()).execute(TwitterAPI
								.getInstance().getUpdateProfileAvatarRequest(
										bitmap));
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}

		});

		Button cancelButton = (Button) getView().findViewById(
				R.id.changeProfile_cancelButton);
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				getFragmentManager().popBackStack();
			}
		});

		avatar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				SelectPhotoFragment selectPhotoFragment = new SelectPhotoFragment();
				selectPhotoFragment.show(getFragmentManager(), TAG);

			}
		});

		iSuccesPhoto = new ISuccesChoisePhoto() {

			@Override
			public void onSuccessChoisePhoto(Uri uri) {
				avatar.setImageURI(uri);
			}
		};
	}

	@Override
	public int getLayoutResource() {
		return R.layout.profile_change;
	}

	@Override
	public int getProgressBarResource() {
		return R.id.progress_bar_on_listView;
	}

	public static interface ISuccesChoisePhoto {
		public void onSuccessChoisePhoto(final Uri uri);
	}

	public static ChangeProfileFragment.ISuccesChoisePhoto getSuccesChoisePhoto() {
		return iSuccesPhoto;
	}

}
