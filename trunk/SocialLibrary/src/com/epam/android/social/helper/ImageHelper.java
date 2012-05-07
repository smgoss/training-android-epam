package com.epam.android.social.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.epam.android.social.R;
import com.google.android.imageloader.ImageLoader;
import com.google.android.imageloader.ImageLoader.Callback;

public class ImageHelper {

	private static final String TAG = ImageHelper.class.getSimpleName();

	private static Context context;

	private static String filePath = "%s/";
	
	private static ImageHelper instance;

	public static ImageHelper getInstance() {
		
		return instance;
	}

	public static ImageHelper newInstance(Context context){
		if(instance== null){
			instance = new ImageHelper(context);
		}
		return instance;
	}
	
	private ImageHelper(Context context){
		ImageHelper.context = context;
	}
	
	private static void saveBitmap(Bitmap bitmap, String fileName) {
		OutputStream outStream = null;
		if (Environment.getExternalStorageDirectory().canWrite()) {
			filePath = String.format(filePath,
					context.getExternalFilesDir(null));
			File file = new File(filePath, fileName);
			if (file != null) {
				try {
					outStream = new FileOutputStream(file);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
					outStream.flush();
					outStream.close();

				} catch (FileNotFoundException e) {
					Log.e(TAG, "File not found error", e);
				} catch (IOException e) {
					Log.e(TAG, "IO exeption", e);
				}
			}
		}
	}

	public static void setAvatar(final String fileName, final ImageView imageView) {

		if (getAvatarFromSdCard(String.valueOf(fileName.hashCode()), imageView) != null) {
			imageView.setImageBitmap(getAvatarFromSdCard(
					String.valueOf(fileName.hashCode()), imageView));
		} else {
			ImageLoader imageLoader = new ImageLoader();
			imageLoader.bind(imageView, fileName, new Callback() {

				@Override
				public void onImageLoaded(ImageView view, String url) {
					saveBitmap(
							((BitmapDrawable) view.getDrawable()).getBitmap(),
							String.valueOf(fileName.hashCode()));
				}

				@Override
				public void onImageError(ImageView view, String url,
						Throwable error) {
					imageView.setImageDrawable(context.getResources()
							.getDrawable(R.drawable.ic_launcher));
				}
			});
		}

	}

	private static Bitmap getAvatarFromSdCard(String fileName, ImageView imageView) {
		if (Environment.getExternalStorageDirectory().canRead()) {
			filePath = String.format(filePath,
					context.getExternalFilesDir(null));
			Bitmap bitmap = BitmapFactory.decodeFile(filePath + fileName);
			return bitmap;
		}
		else{
			return null;
		}
	}

	
	public static Uri tryGetImageFromBadDevice(Activity acivity) {
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
		Cursor myCursor = acivity.managedQuery(
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
		myCursor = acivity.managedQuery(
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
