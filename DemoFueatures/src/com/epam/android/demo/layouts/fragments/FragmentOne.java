package com.epam.android.demo.layouts.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.epam.android.demo.common.utils.JsonModelConverter;
import com.epam.android.demo.layouts.adapter.MyListAdapter;
import com.epam.android.demo.layouts.models.MyLayoutModel;
import com.epam.android.demo.social.R;

public class FragmentOne extends ListFragment {

	private List<MyLayoutModel> mList;

	private TextView status;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.custom_my_layout, container, false);
		String foo = "foo";
		TextView fio = (TextView) v.findViewById(R.id.fio);
		fio.setText("Vkontakte");
		fio.setVisibility(View.VISIBLE);

		View button = (View) v.findViewById(R.id.linearLayout3);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ProgressBar mProgressBar = (ProgressBar) getView()
						.findViewById(R.id.progressActionBar);
				if (mProgressBar.getVisibility() == View.VISIBLE) {
					mProgressBar.setVisibility(View.GONE);
				} else {
					mProgressBar.setVisibility(View.VISIBLE);
				}
			}
		});

		status = (TextView) v.findViewById(R.id.status);
		status.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						getActivity());

				alert.setTitle("Edit status");
				// alert.setMessage("Message");
				Drawable icon = getResources()
						.getDrawable(R.drawable.alert_red);
				alert.setIcon(icon);

				Context mContext = getActivity();
				LayoutInflater inflater = (LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(
						R.layout.custom_dialog_with_input,
						(ViewGroup) getView().findViewById(R.id.layout_root));

				alert.setView(layout);
				
				final EditText editStatus = (EditText) layout.findViewById(R.id.editStatus);
				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String value = editStatus.getText().toString();
								status.setText(value);
							}
						});

				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Canceled.
							}
						});

				alert.show();
			}
		});
		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		mList = new ArrayList<MyLayoutModel>();

		try {
			JSONArray array = new JSONArray(
				     "[{'number'='1223','title'='Messages', 'text'='new messages'},  {'number'='2','title'='Friends','text'='Friends Online'}, {'number'='3','title'='Wall','text'='My Wall'},{'number'='4','title'='Birthdays', 'text'='No birthdays today'},  {'number'='5','title'='Video','text'='Total video'}, {'number'='6','title'='photos','text'='Total photos'}]");
				   mList = JsonModelConverter.convertJSONArrayToList(array,
				     MyLayoutModel.MODEL_CREATOR);
			mList = JsonModelConverter.convertJSONArrayToList(array,
					MyLayoutModel.MODEL_CREATOR);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		super.onCreate(savedInstanceState);
		String foo = "foo";
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		setListAdapter(new MyListAdapter(getActivity(),
				R.layout.custom_my_layout_list_item, mList));

		super.onActivityCreated(savedInstanceState);
		String foo = "foo";
	}

}
