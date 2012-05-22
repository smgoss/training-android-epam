package com.epam.android.common;

import java.io.IOException;
import java.net.URLConnection;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.epam.android.common.http.HttpClient;
import com.epam.android.common.http.Loader;
import com.epam.android.common.task.AsyncTaskManager;
import com.google.android.imageloader.BitmapContentHandler;
import com.google.android.imageloader.ImageLoader;

public class CommonApplication extends Application {

	private HttpClient mHttpClient;

	private Loader mLoader;

	private ImageLoader mImageLoader;

	private AsyncTaskManager mAsyncTaskManager;

	@Override
	public void onCreate() {
		mHttpClient = new HttpClient(this);
		mLoader = new Loader(mHttpClient);
		mImageLoader = new ImageLoader(new BitmapContentHandler() {

			@Override
			public Bitmap getContent(URLConnection connection)
					throws IOException {
				// todo if file exists get from file
				// getCacheDir()
				Bitmap result = null;
				if (result == null) {
					Bitmap content = super.getContent(connection);
					result = getRoundedCornersImage(content, 10);
					// store to file
				}
				return result;
			}

			public Bitmap getRoundedCornersImage(Bitmap source, int radiusPixels) {
				if (source == null) {
					// we cant proccess null image, go out
					return null;
				}
				final int sourceWidth = source.getWidth();
				final int sourceHeight = source.getHeight();
				final Bitmap output = Bitmap.createBitmap(sourceWidth,
						sourceHeight, Bitmap.Config.ARGB_8888);
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
		}, null);

		mAsyncTaskManager = new AsyncTaskManager();

	}

	@Override
	public Object getSystemService(String name) {
		if (name.equals(HttpClient.HTTP_CLIENT)) {
			return mHttpClient;
		}
		if (name.equals(ImageLoader.IMAGE_LOADER_SERVICE)) {
			return mImageLoader;
		}
		if (name.equals(Loader.LOADER)) {
			return mLoader;
		}
		if (name.equals(AsyncTaskManager.ASYNC_TASK_MANAGER)) {
			return mAsyncTaskManager;
		}

		return super.getSystemService(name);
	}

}
