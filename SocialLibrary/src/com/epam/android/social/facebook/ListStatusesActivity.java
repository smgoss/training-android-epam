package com.epam.android.social.facebook;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.epam.android.common.http.Loader;
import com.epam.android.social.api.FacebookAPI;
import com.epam.android.social.model.Account;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class ListStatusesActivity extends ListActivity {

	private ListView lv;
	private String token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		lv = getListView();
		token = getIntent().getStringExtra("token");
		Toast.makeText(this, getUser(), 1).show();
	}

	private String getUser() {
		Loader loader = Loader.get(this);
		String oneUser;
		try {
			oneUser = loader.execute(FacebookAPI.getInstance().getStatuses()
					+ token);
			return oneUser;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

}
