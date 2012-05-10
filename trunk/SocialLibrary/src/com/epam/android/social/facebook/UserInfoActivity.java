package com.epam.android.social.facebook;

import android.widget.TextView;

import com.epam.android.common.BaseModelActivity;
import com.epam.android.social.R;
import com.epam.android.social.api.FacebookAPI;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.model.FacebookUserinfo;

public class UserInfoActivity extends BaseModelActivity<FacebookUserinfo> {

	@Override
	public String getUrl() {
		return FacebookAPI.getInstance().getFullProfileInfo(null)
				+ getIntent().getStringExtra(
						ApplicationConstants.ARG_PROFILE_NAME);
	}

	@Override
	protected void success(FacebookUserinfo result) {
		TextView userName = (TextView) findViewById(R.id.tvUserName);
		userName.setText(result.getName());
	}

	@Override
	public int getLayoutResource() {
		return R.layout.facebook_user_info;
	}

}
