package com.epam.android.social.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class ImageManager {

	private static final String TAG = ImageManager.class.getSimpleName();

	private static Context mContext;

	private static ImageManager instance;

	public static ImageManager newInstance(Context context) {
		if (instance == null) {
			instance = new ImageManager(context);
		}
		return instance;
	}

	public static ImageManager getInstance() {
		return instance;
	}

	private ImageManager(Context context) {
		ImageManager.mContext = context;
	}

	public static Uri tryGetImageFromBadDevice(Activity activity) {
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
		Cursor myCursor = activity.managedQuery(
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
		myCursor = activity.managedQuery(
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

	public static String getHashUrl(String url) {
		byte[] defaultBytes = url.getBytes();
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte messageDigest[] = algorithm.digest();

			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException nsae) {

		} catch (StackOverflowError e) {

		}
		return null;
	}

	public static boolean saveImageToFile(String absolutePath, Bitmap bitmap,
			String url) throws IOException {
		if (bitmap == null) {
			return false;
		}
		String imageName = getHashUrl(url);
		if (imageName == null) {
			return false;
		}
		OutputStream outStream = null;
		Log.d(TAG, "save to file image : " + absolutePath + File.separator
				+ imageName);
		File file = new File(absolutePath, imageName);
		outStream = new FileOutputStream(file);
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
		outStream.flush();
		outStream.close();
		System.gc();
		return true;
	}

	public static Bitmap loadFromFile(String absolutePath, String path) {
		if (mContext.getCacheDir().canRead()) {
			String imageName = getHashUrl(path);
			if (imageName == null) {
				return null;
			}
			Bitmap bitmap = BitmapFactory.decodeFile(absolutePath
					+ File.separator + imageName);
			if (bitmap != null) {
				Log.d(TAG, "load from file " + absolutePath + File.separator
						+ imageName);
			}
			return bitmap;
		}
		return null;
	}

	public static Bitmap getRoundedCornersImage(Bitmap source, int radiusPixels) {
		if (source == null) {
			// we cant proccess null image, go out
			return null;
		}
		final int sourceWidth = source.getWidth();
		final int sourceHeight = source.getHeight();
		final Bitmap output = Bitmap.createBitmap(sourceWidth, sourceHeight,
				Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(output);

		final int color = 0xFF000000;
		final Paint paint = new Paint();
		paint.setColor(color);

		final Rect rect = new Rect(0, 0, sourceWidth, sourceHeight);
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawRoundRect(rectF, radiusPixels, radiusPixels, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(source, 0, 0, paint);

		return output;
	}

}
