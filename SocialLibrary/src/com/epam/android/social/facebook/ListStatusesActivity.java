package com.epam.android.social.facebook;

import java.util.List;

import android.widget.ListView;
import android.widget.Toast;

import com.epam.android.common.BaseModelActivity;
import com.epam.android.social.R;
import com.epam.android.social.api.FacebookAPI;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.model.StatusFacebook;

public class ListStatusesActivity extends
		BaseModelActivity<StatusFacebook> {

	private static final String TAG = ListStatusesActivity.class
			.getSimpleName();

	private ListView mListView;

	private ArrayModelListAdapter adapter;

	@Override
	public String getUrl() {
		// return "http://dl.dropbox.com/u/16403954/array_bm.json";
		return FacebookAPI.getInstance().getUser()
				+ getIntent().getStringExtra(
						ApplicationConstants.ARG_PROFILE_NAME);
	}


	@Override
	public int getLayoutResource() {
		return R.layout.statuses;
	}

	@Override
	protected void success(StatusFacebook result) {
		mListView = (ListView) findViewById(R.id.lvStatuses);
		Toast.makeText(ListStatusesActivity.this, result.getName(), 1).show();
//		adapter = new ArrayModelListAdapter(ListStatusesActivity.this,
//				R.layout.load_model, result);
//		mListView.setAdapter(adapter);
		
	}

}
