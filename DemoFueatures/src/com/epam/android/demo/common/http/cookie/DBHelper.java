package com.epam.android.demo.common.http.cookie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * The Class DBHelper.
 */
public class DBHelper extends SQLiteOpenHelper {

	/** The Constant COOKIE_TABLE. */
	public static final String COOKIE_TABLE = "COOKIE";

	/** The Constant COOKIE_COLUMNS. */
	public static final String[] COOKIE_COLUMNS = {"ID", "NAME", "VALUE", "DOMAIN",
			"PATH", "EXPIRATION_TIME", "VERSION"};
	
	/** The Constant ID_COL_INDEX. */
	public static final int ID_COL_INDEX = 0;
	/** The Constant NAME_COL_INDEX. */
	public static final int NAME_COL_INDEX = 1;
	/** The Constant VALUE_COL_INDEX. */
	public static final int VALUE_COL_INDEX = 2;
	/** The Constant DOMAIN_COL_INDEX. */
	public static final int DOMAIN_COL_INDEX = 3;
	/** The Constant PATH_COL_INDEX. */
	public static final int PATH_COL_INDEX = 4;
	/** The Constant EXPIRATION_TIME_COL_INDEX. */
	public static final int EXPIRATION_TIME_COL_INDEX = 5;
	/** The Constant VERSION_COL_INDEX. */
	public static final int VERSION_COL_INDEX = 6;

	/** The Constant CREATE_COOKIE_TABLE_SQL. */
	public static final String CREATE_COOKIE_TABLE_SQL = "CREATE TABLE "
			+ COOKIE_TABLE + " (" + COOKIE_COLUMNS[ID_COL_INDEX] + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COOKIE_COLUMNS[NAME_COL_INDEX] + " VARCHAR NOT NULL, " + COOKIE_COLUMNS[VALUE_COL_INDEX]
			+ " VARCHAR NOT NULL, " + COOKIE_COLUMNS[DOMAIN_COL_INDEX] + " VARCHAR, "
			+ COOKIE_COLUMNS[PATH_COL_INDEX] + " VARCHAR, " + COOKIE_COLUMNS[EXPIRATION_TIME_COL_INDEX] + " LONG, "
			+ COOKIE_COLUMNS[VERSION_COL_INDEX] + " VARCHAR)";

	/** The Constant DROP_COOKIE_TABLE_SQL. */
	public static final String DROP_COOKIE_TABLE_SQL = "DROP TABLE IF EXISTS "
			+ COOKIE_TABLE;

	/** The Constant COOKIE_SELECTION. */
	public static final String COOKIE_SELECTION = "NAME=?";

	/** The Constant COOKIE_EXPIRED_SELECTION. */
	public static final String COOKIE_EXPIRED_SELECTION = "EXPIRATION_TIME<=?";

	/**
	 * Instantiates a new dB helper.
	 * 
	 * @param context
	 *            the context
	 * @param name
	 *            the name
	 * @param factory
	 *            the factory
	 * @param version
	 *            the version
	 */
	public DBHelper(final Context context, final String name, final CursorFactory factory,
			final int version) {
		super(context, name, factory, version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public final void onCreate(final SQLiteDatabase db) {
		try {
			db.beginTransaction();
			db.execSQL(CREATE_COOKIE_TABLE_SQL);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public final void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
		try {
			db.beginTransaction();
			db.execSQL(DROP_COOKIE_TABLE_SQL);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		onCreate(db);
	}

}
