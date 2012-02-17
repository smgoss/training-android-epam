package com.epam.android.common.http.cookie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class CookieManager {

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "cookie.store.db";
	
	private DBHelper dbHelper;
	
	private SQLiteDatabase dbReader;
	
	private SQLiteDatabase dbWriter;
	
	public CookieManager(final Context context){
		dbHelper = new DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		dbReader = dbHelper.getReadableDatabase();
		dbWriter = dbHelper.getWritableDatabase();
	}
	
	public void addCookie(final SavedCookies cookies) {
		dbWriter.insert(DBHelper.COOKIE_TABLE, null, getContentValues(cookies));
		
	}

	public SavedCookies getCookie(String name){
		Cursor cursor = dbReader.query(DBHelper.COOKIE_TABLE, null, DBHelper.COOKIE_SELECTION,
				new String[] { name }, null, null, null);
		SavedCookies cookie = null;
		if(cursor.moveToFirst()){
			cookie = getCookie(cursor);
			
			cursor.close();
		}
		
		
		return cookie;
	}
	
	public List<SavedCookies> getCookies() {
		Cursor cursor = dbReader.query(DBHelper.COOKIE_TABLE, null, null, null, null, null, null);
		List<SavedCookies> cookies = null;
		
		if(cursor.moveToFirst()){
			cookies = new ArrayList<SavedCookies>();
			do {
				cookies.add(getCookie(cursor));
			} while (cursor.moveToNext());
			
			cursor.close();
		}
		
		return cookies;		
	}
	
	private SavedCookies getCookie(Cursor cursor){
		BasicClientCookie cookie = new BasicClientCookie(
				cursor.getString(DBHelper.NAME_COL_INDEX),
				cursor.getString(DBHelper.VALUE_COL_INDEX));
		cookie.setDomain(cursor.getString(DBHelper.DOMAIN_COL_INDEX));
		cookie.setPath(cursor.getString(DBHelper.PATH_COL_INDEX));
		cookie.setVersion(cursor.getInt(DBHelper.VERSION_COL_INDEX));

		long time = cursor.getLong(DBHelper.EXPIRATION_TIME_COL_INDEX);

		if (time > 0) {
			cookie.setExpiryDate(new Date(time));
		}

		return new SavedCookies(cookie);
	}

	public void clear() {
		dbWriter.beginTransaction();
		
		try {
			dbWriter.delete(DBHelper.COOKIE_TABLE, null, null);
			dbWriter.setTransactionSuccessful();
		} finally {
			dbWriter.endTransaction();
		}
	}

	public int clearExpired(Date date) {
		return dbWriter.delete(DBHelper.COOKIE_TABLE, DBHelper.COOKIE_EXPIRED_SELECTION,
				new String[] { String.valueOf(date.getTime()) });
		 
	}

	private ContentValues getContentValues(SavedCookies cookies) {
		ContentValues values = new ContentValues();
		Cookie cookie = cookies.getCookie();
		values.put(DBHelper.COOKIE_COLUMNS[DBHelper.NAME_COL_INDEX], cookie.getName());
		values.put(DBHelper.COOKIE_COLUMNS[DBHelper.VALUE_COL_INDEX], cookie.getValue());
		values.put(DBHelper.COOKIE_COLUMNS[DBHelper.DOMAIN_COL_INDEX], cookie.getDomain());
		values.put(DBHelper.COOKIE_COLUMNS[DBHelper.PATH_COL_INDEX], cookie.getPath());
		Date date = cookie.getExpiryDate();
		if (date != null) {
			values.put(DBHelper.COOKIE_COLUMNS[DBHelper.EXPIRATION_TIME_COL_INDEX], date.getTime());
		}
		values.put(DBHelper.COOKIE_COLUMNS[DBHelper.VERSION_COL_INDEX], cookie.getVersion());
		return values;
	}

	public void updateCookie(SavedCookies cookie) {
		dbWriter.update(DBHelper.COOKIE_TABLE, getContentValues(cookie),
				DBHelper.COOKIE_SELECTION, new String[] {cookie.getCookie()
						.getName()});
		
	}

}
