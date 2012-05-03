package com.epam.android.social.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.epam.android.social.R;
import com.epam.android.social.SearchFragmentActivity;
import com.epam.android.social.constants.ApplicationConstants;

public class SearchLineFragment extends Fragment {

	public static final String TAG = SearchLineFragment.class.getSimpleName();

	private EditText queryText;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		queryText = (EditText) getView().findViewById(
				R.id.searchLineFragment_editText);
		queryText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
					Intent intent = new Intent(getView().getContext(),
							SearchFragmentActivity.class);
					intent.putExtra(ApplicationConstants.ARG_QUERY, queryText
							.getText().toString().trim());
					if (getActivity().getIntent().getBooleanExtra(
							ApplicationConstants.IS_FIRST_SEARCH, true)) {
						intent.putExtra(ApplicationConstants.IS_FIRST_SEARCH,
								false);
					} else {
						getActivity().finish();
						intent.putExtra(ApplicationConstants.IS_FIRST_SEARCH,
								false);
					}
					startActivity(intent);

					getFragmentManager().popBackStack();
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater
				.inflate(R.layout.search_line_fragment, container, false);
	}

}
