package com.epam.android.layouts.fragments;

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

import com.epam.android.common.utils.JsonModelConverter;
import com.epam.android.layouts.adapter.MyListAdapter;
import com.epam.android.layouts.models.MyLayoutModel;
import com.epam.android.social.R;

public class FragmentOne extends ListFragment {

	private List<MyLayoutModel> mList;

	private TextView status;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.custom_my_layout, container, false);
		String foo = "foo";
		TextView fio = (TextView) v.findViewById(R.id.fio);
		fio.setText("Имя Фамилия");
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
					"[{'number'='1223','title'='Сообщения', 'text'='новых сообщений'},  {'number'='2','title'='Друзья','text'='друзей онлайн'}, {'number'='3','title'='Стена','text'='моя стена'},{'number'='4','title'='Дни рождения', 'text'='сегодня нет'},  {'number'='5','title'='Видео','text'='всего видео'}, {'number'='6','title'='Фотографии','text'='всего фотографий'}]");
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
