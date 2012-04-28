package com.epam.android.social.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.epam.android.social.R;
import com.google.android.imageloader.ImageLoader;
import com.google.android.imageloader.ImageLoader.Callback;

public class ImageGetHelper {

	private static final String TAG = ImageGetHelper.class.getSimpleName();

	private Context context;

	private String filePath = "%s/";

	public ImageGetHelper(Context context) {
		this.context = context;
	}

	private void saveBitmap(Bitmap bitmap, String fileName) {
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

	public void setAvatar(final String fileName, final ImageView imageView) {

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

	private Bitmap getAvatarFromSdCard(String fileName, ImageView imageView) {
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

}
