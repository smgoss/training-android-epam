package com.epam.android.social.fragments;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.epam.android.common.task.HttpPostAsyncTask;
import com.epam.android.common.utils.ImageManager;
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

	private static final int IMAGE_PICK = 1598;

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 12312;

	private WindowManager mWindowManager;

	private WindowManager.LayoutParams mWindowParams;

	private View addedItem;

	private Uri imageUri;

	private ImageView avatar;

	private Drawable loadedAvatar;

	private List<EditText> editTextList;

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
		avatar = (ImageView) getView().findViewById(R.id.changeProfile_avatar);
		if (loadedAvatar == null) {
			ImageLoader.get(getContext()).bind(avatar,
					result.getProfileAvatarUrl(), new Callback() {

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

		editTextList = new ArrayList<EditText>();
		final EditText name = (EditText) getView().findViewById(
				R.id.changeProfile_name);
		name.setText(result.getName());
		editTextList.add(name);

		final EditText description = (EditText) getView().findViewById(
				R.id.changeProfile_description);
		description.setText(result.getDescription());
		editTextList.add(description);

		final EditText url = (EditText) getView().findViewById(
				R.id.changeProfile_url);
		url.setText(result.getUrl());
		editTextList.add(url);

		final EditText location = (EditText) getView().findViewById(
				R.id.changeProfile_location);
		location.setText(result.getLocation());
		editTextList.add(location);

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
									name.getText().toString(),
									description.getText().toString(),
									url.getText().toString(),
									location.getText().toString()));
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
				hideChoiseItem();
			}
		});

		RelativeLayout avatarLayout = (RelativeLayout) getView().findViewById(
				R.id.changeProfile_avatarLayout);
		avatarLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				showChoiseItem();
				ImageView galleryButton = (ImageView) addedItem
						.findViewById(R.id.galleryCamera_galleryButton);
				galleryButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (Environment.getExternalStorageDirectory().canRead()) {
							Intent intent = new Intent(
									Intent.ACTION_GET_CONTENT);
							intent.setType("image/*");
							startActivityForResult(intent, IMAGE_PICK);
						} else {
							Toast.makeText(
									getContext(),
									getResources().getString(
											R.string.insert_sd_card),
									Toast.LENGTH_SHORT).show();
						}
					}
				});

				ImageView cameraButton = (ImageView) addedItem
						.findViewById(R.id.galleryCamera_cameraButton);
				cameraButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String fileName = "new-photo-name.jpg";
						ContentValues values = new ContentValues();
						values.put(MediaStore.Images.Media.TITLE, fileName);
						values.put(MediaStore.Images.Media.DESCRIPTION,
								"Image capture by camera");

						if (Environment.getExternalStorageDirectory()
								.canWrite()) {
							imageUri = getView()
									.getContext()
									.getContentResolver()
									.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
											values);
							Intent intent = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							if (imageUri != null) {
								intent.putExtra(MediaStore.EXTRA_OUTPUT,
										imageUri);
							}
							intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
							startActivityForResult(intent,
									CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
						} else {
							Toast.makeText(
									getContext(),
									getResources().getString(
											R.string.insert_sd_card),
									Toast.LENGTH_SHORT).show();
						}
					}
				});

			}
		});

	}

	private void showChoiseItem() {
		if (addedItem == null) {
			addedItem = LayoutInflater.from(getActivity()).inflate(
					R.layout.gallery_camera_item, null);
		}
		mWindowParams = new WindowManager.LayoutParams();

		mWindowParams.gravity = Gravity.TOP | Gravity.LEFT;
		mWindowParams.x = 80;

		mWindowParams.y = 80;

		mWindowParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		mWindowParams.height = getView().findViewById(
				R.id.changeProfile_avatarLayout).getHeight();

		mWindowParams.width = LayoutParams.FILL_PARENT;

		if (mWindowManager == null) {
			mWindowManager = (WindowManager) getContext().getSystemService(
					Context.WINDOW_SERVICE);
		}
		mWindowManager.addView(addedItem, mWindowParams);

	}

	private void hideChoiseItem() {
		if (addedItem != null) {
			mWindowManager.removeView(addedItem);
		}
		addedItem = null;
	}

	private void setImageAvatar(Uri uri) {
		avatar.setImageURI(uri);
	}

	@Override
	public int getLayoutResource() {
		return R.layout.change_profile;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

		switch (requestCode) {
		case IMAGE_PICK:
			if (resultCode == Activity.RESULT_OK) {
				Uri selectedImage = imageReturnedIntent.getData();
				setImageAvatar(selectedImage);
				hideChoiseItem();
			}
			break;
		case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
			if (resultCode == Activity.RESULT_OK) {
				if (imageReturnedIntent != null) {
					Log.d("VA", "" + imageReturnedIntent.getData());
				}
				if (imageUri == null) {
					if (imageReturnedIntent != null) {
						imageUri = imageReturnedIntent.getData();
					}
					if (imageUri == null) {
						try {
							imageUri = ImageManager
									.tryGetImageFromBadDevice(getActivity());
						} catch (Exception e) {
							Log.e("VA", "error get image uri", e);
						}
					}
					if (imageUri == null) {
						Toast.makeText(getView().getContext(),
								"Sorry, your device not supported this action",
								Toast.LENGTH_SHORT).show();
						return;
					}
				}

				setImageAvatar(imageUri);
				imageUri = null;
				hideChoiseItem();
			}
			break;
		}
	}

	@Override
	public int getProgressBarResource() {
		return R.id.progress_bar_on_listView;
	}

}
