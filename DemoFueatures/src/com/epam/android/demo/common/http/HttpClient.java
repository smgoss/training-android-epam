package com.epam.android.demo.common.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.content.Context;
import android.util.Log;

import com.epam.android.demo.common.http.cookie.CookieManager;
import com.epam.android.demo.common.http.cookie.DefaultCookieStore;
import com.epam.android.demo.common.utils.GetSystemService;

public class HttpClient {

	public static final String HTTP_CLIENT = "++HTTP_CLIENT++";

	private static final String TAG = HttpClient.class.getSimpleName();

	private static final String UTF_8 = "UTF-8";

	private static final int BUFFER_SIZE = 1024;

	private static final int SO_TIMEOUT = 15000;

	private DefaultHttpClient client;

	public static HttpClient get(Context context) {
		return (HttpClient) GetSystemService.get(context, HTTP_CLIENT);
	}

	public HttpClient(Context context) {
		HttpParams params = new BasicHttpParams();

		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, UTF_8);
		HttpProtocolParams.setHttpElementCharset(params, UTF_8);
		HttpConnectionParams.setConnectionTimeout(params, SO_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);

		// REGISTERS SCHEMES FOR BOTH HTTP AND HTTPS
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		final SSLSocketFactory sslSocketFactory = SSLSocketFactory
				.getSocketFactory();
		sslSocketFactory
				.setHostnameVerifier(SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		registry.register(new Scheme("https", sslSocketFactory, 443));
		ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(
				params, registry);

		client = new DefaultHttpClient(manager, params);
		CookieManager cookieManager = new CookieManager(context);
		client.setCookieStore(new DefaultCookieStore(cookieManager));
	}

	public String execute(HttpUriRequest request)
			throws ClientProtocolException, IOException {

		
		HttpResponse response = client.execute(request);
		String result = readString(response.getEntity().getContent());

		int status = response.getStatusLine().getStatusCode();

		if (status == HttpStatus.SC_OK) {
			Log.d(TAG, result);
			return result;
		} else {
			Log.d(TAG, "Http request status = " + status);
			Log.d(TAG, result);
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
