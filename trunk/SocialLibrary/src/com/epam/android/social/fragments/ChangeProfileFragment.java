package com.epam.android.social.fragments;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
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

import com.epam.android.common.BaseArrayModelFragment;
import com.epam.android.common.task.HttpPostAsyncTask;
import com.epam.android.social.R;
import com.epam.android.social.api.TwitterAPI;
import com.epam.android.social.model.ProfileInfo;
import com.google.android.imageloader.ImageLoader;
import com.google.android.imageloader.ImageLoader.Callback;

public class ChangeProfileFragment extends BaseArrayModelFragment<ProfileInfo> {

	private static final String TAG = ChangeProfileFragment.class
			.getSimpleName();

	private static final String ARG_QUERY = "query";

	private static final String ARG_ACCOUNT_NAME = "accountName";

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
		bundle.putString(ARG_QUERY, query);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public String getUrl() {
		return getArguments().getString(ARG_QUERY);
	}

	@Override
	protected void success(List<ProfileInfo> result) {
		if (loadedAvatar == null) {
			initView(result.get(0));
		} else {
			restoreTextOnEditBox();
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		List<String> dataList = new ArrayList<String>();
		for (int i = 0; i < editTextList.size(); i++) {
			dataList.add(editTextList.get(i).getText().toString());
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
		Log.d(TAG, "initView");
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
				try {
					new HttpPostAsyncTask(getActivity()).execute(TwitterAPI
							.getInstance().getUpdateProfileRequest(
									name.getText().toString(),
									description.getText().toString(),
									url.getText().toString(),
									location.getText().toString()));
				} catch (UnsupportedEncodingException e) {
					Log.e(TAG,
							"particular character converter not unavailable", e);
				}
			}
		});

		Button cancelButton = (Button) getView().findViewById(
				R.id.changeProfile_cancelButton);
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				// TODO hide fragment
				hideChoiseItem();
			}
		});

		RelativeLayout avatarLayout = (RelativeLayout) getView().findViewById(
				R.id.changeProfile_avatarLayout);
		avatarLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				showChoiseItem();
				Button galleryButton = (Button) addedItem
						.findViewById(R.id.galleryCamera_galleryButton);
				galleryButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
						intent.setType("image/*");
						startActivityForResult(intent, IMAGE_PICK);
					}
				});

				Button cameraButton = (Button) addedItem
						.findViewById(R.id.galleryCamera_cameraButton);
				cameraButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String fileName = "new-photo-name.jpg";
						ContentValues values = new ContentValues();
						values.put(MediaStore.Images.Media.TITLE, fileName);
						values.put(MediaStore.Images.Media.DESCRIPTION,
								"Image capture by camera");

						imageUri = getView()
								.getContext()
								.getContentResolver()
								.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
										values);
						Intent intent = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						if (imageUri != null) {
							intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
						}
						intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
						startActivityForResult(intent,
								CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
					}
				});

			}
		});

	}

	private void showChoiseItem() {
		addedItem = LayoutInflater.from(getActivity()).inflate(
				R.layout.gallery_camera_item, null);
		mWindowParams = new WindowManager.LayoutParams();

		mWindowParams.gravity = Gravity.TOP | Gravity.LEFT;
		mWindowParams.x = 80;

		mWindowParams.y = 80;

		mWindowParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		mWindowParams.height = getView().findViewById(
				R.id.changeProfile_avatarLayout).getHeight();

		mWindowParams.width = LayoutParams.FILL_PARENT;

		mWindowManager = (WindowManager) getContext()
				.getSystemService("window");
		mWindowManager.addView(addedItem, mWindowParams);

	}

	private void hideChoiseItem() {
		mWindowManager.removeView(addedItem);
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
							imageUri = tryGetImageFromBadDevice();
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
				hideChoiseItem();
			}
			break;
		}
	}

	private Uri tryGetImageFromBadDevice() {
		// Describe the columns you'd like to have returned.
		// Selecting from the Thumbnails location gives you both
		// the Thumbnail Image ID, as well as the original image
		// ID
		String[] projection = {
				MediaStore.Images.Thumbnails._ID, // The columns
				// we want
				MediaStore.Images.Thumbnails.IMAGE_ID,
				MediaStore.Images.Thumbnails.KIND,
				MediaStore.Images.Thumbnails.DATA };
		String selection = MediaStore.Images.Thumbnails.KIND + "=" + // Select
																		// only
																		// mini's
				MediaStore.Images.Thumbnails.MINI_KIND;

		String sort = MediaStore.Images.Thumbnails._ID + " DESC";

		// At the moment, this is a bit of a hack, as I'm
		// returning ALL images, and just taking the latest one.
		// There is a better way to narrow this down I think
		// with a WHERE clause which is currently the selection
		// variable
		Cursor myCursor = getActivity().managedQuery(
				MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection,
				selection, null, sort);

		long imageId = 0l;
		long thumbnailImageId = 0l;
		String thumbnailPath = "";

		try {
			myCursor.moveToFirst();
			imageId = myCursor
					.getLong(myCursor
							.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.IMAGE_ID));
			thumbnailImageId = myCursor.getLong(myCursor
					.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID));
			thumbnailPath = myCursor.getString(myCursor
					.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA));
		} finally {
			myCursor.close();
		}

		// Create new Cursor to obtain the file Path for the
		// large image

		String[] largeFileProjection = { MediaStore.Images.ImageColumns._ID,
				MediaStore.Images.ImageColumns.DATA };

		String largeFileSort = MediaStore.Images.ImageColumns._ID + " DESC";
		myCursor = getActivity().managedQuery(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				largeFileProjection, null, null, largeFileSort);
		String largeImagePath = "";

		try {
			myCursor.moveToFirst();

			// This will actually give yo uthe file path
			// location of the image.
			largeImagePath = myCursor
					.getString(myCursor
							.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA));
			Log.d("BD", "lmp" + largeImagePath);
		} finally {
			myCursor.close();
		}
		// These are the two URI's you'll be interested in. They
		// give you a handle to the actual images
		Uri uriLargeImage = Uri.withAppendedPath(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				String.valueOf(imageId));
		Log.d("BD", "uriLargeImage" + uriLargeImage);
		return uriLargeImage;
		/*
		 * Uri uriThumbnailImage = Uri .withAppendedPath(
		 * MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
		 * String.valueOf(thumbnailImageId));
		 */

		// I've left out the remaining code, as all I do is
		// assign the URI's to my own objects anyways...
	}

}
