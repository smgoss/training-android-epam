package com.epam.android.social.facebook;

import java.util.List;

import android.widget.ListView;

import com.epam.android.common.BaseArrayModelByAnnotationActivity;
import com.epam.android.social.R;
import com.epam.android.social.api.FacebookAPI;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.model.StatusFacebook;

public class ListStatusesActivity extends
		BaseArrayModelByAnnotationActivity<StatusFacebook> {

	private static final String TAG = ListStatusesActivity.class
			.getSimpleName();

	private ListView mListView;

	private ArrayModelListAdapter adapter;

	@Override
	public String getUrl() {
		return FacebookAPI.getInstance().getStatuses()
				+ getIntent().getStringExtra(
						ApplicationConstants.ARG_PROFILE_NAME);
	}

	@Override
	public int getLayoutResource() {
		return R.layout.statuses;
	}

	@Override
	protected void success(List<StatusFacebook> result) {
		mListView = (ListView) findViewById(R.id.lvStatuses);
		adapter = new ArrayModelListAdapter(ListStatusesActivity.this,
				R.layout.load_model, result);
		mListView.setAdapter(adapter);

	}

}
