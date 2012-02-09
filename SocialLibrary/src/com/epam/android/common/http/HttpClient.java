package com.epam.android.common.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.util.Log;

public class HttpClient {
	public static final String HTTP_CLIENT = "++HTTP_CLIENT++";
	private static final String TAG = HttpClient.class.getSimpleName();
	private static final String UTF_8 = "UTF-8";
	private static final int BUFFER_SIZE = 1024;

	private DefaultHttpClient client;

	// TODO save cookie
	public HttpClient() {
		HttpParams params = new BasicHttpParams();

		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, UTF_8);

		client = new DefaultHttpClient(params);
	}

	public String execute(HttpUriRequest request)
			throws ClientProtocolException, IOException {

		HttpResponse response = client.execute(request);
		String result = readString(response.getEntity().getContent());

		int status = response.getStatusLine().getStatusCode();

		if (status == HttpStatus.SC_OK) {
			return result;
		} else {
			Log.d(TAG, "Http request status = " + status);
			throw new IOException("status is not OK");
		}

	}

	public String loadAsString(String url) throws IOException {
		return execute(new HttpGet(url));
	}

	private String readString(InputStream is) throws IOException {

		if (is == null) {
			return null;
		}

		String text = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(is),
				BUFFER_SIZE);
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			text = sb.toString();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				Log.d(TAG, "IOException : " + e.getMessage());
			}
		}
		return text;
	}
}
