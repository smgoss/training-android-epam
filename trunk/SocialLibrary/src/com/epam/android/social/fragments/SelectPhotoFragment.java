package com.epam.android.social.fragments;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.epam.android.common.utils.ImageManager;
import com.epam.android.social.R;
import com.epam.android.social.adapter.SelectPhotoAdapter;

public class SelectPhotoFragment extends DialogFragment {

	private static final int CAMERA_POSITION = 0;

	private static final int GALLERY_POSITION = 1;

	private static final int REMOVE_POSITION = 2;

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 12312;

	private static final int IMAGE_PICK = 1598;

	private Uri imageUri;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.select_photo, null, false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_TITLE, getTheme());

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ListView listView = (ListView) getView().findViewById(
				R.id.selectPhoto_listView);
		SelectPhotoAdapter adapter = new SelectPhotoAdapter(getActivity());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (position == CAMERA_POSITION) {
					String fileName = "new-photo-name.jpg";
					ContentValues values = new ContentValues();
					values.put(MediaStore.Images.Media.TITLE, fileName);
					values.put(MediaStore.Images.Media.DESCRIPTION,
							"Image capture by camera");

					if (Environment.getExternalStorageDirectory().canWrite()) {
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
					} else {
						Toast.makeText(
								getView().getContext(),
								getResources().getString(
										R.string.insert_sd_card),
								Toast.LENGTH_SHORT).show();
					}
				}

				if (position == GALLERY_POSITION) {
					if (Environment.getExternalStorageDirectory().canRead()) {
						Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
						intent.setType("image/*");
						startActivityForResult(intent, IMAGE_PICK);
					} else {
						Toast.makeText(
								getView().getContext(),
								getResources().getString(
										R.string.insert_sd_card),
								Toast.LENGTH_SHORT).show();
					}
				}

				if (position == REMOVE_POSITION) {
					Uri uri = Uri
							.parse("android.resource://com.epam.android.social/"
									+ R.drawable.bg_no_photo);
					getDialog().dismiss();
					
					setImageAvatar(uri);
				}
			}
		});
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
			}
			break;
		}

		getDialog().dismiss();
	}

	private void setImageAvatar(Uri uri) {

		ChangeProfileFragment.getSuccesChoisePhoto().onSuccessChoisePhoto(uri);
	}
}
