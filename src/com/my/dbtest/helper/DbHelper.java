package com.my.dbtest.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	private SQLiteDatabase db = null;
	private Cursor cursor = null;
	private String TAG = "DBTest";
	private boolean isInit;
	private String createString;

	public DbHelper(Context context, String dbName, CursorFactory factory,
			int version) {
		super(context, dbName, factory, version);
	}

	// 建表
	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		db.execSQL(createString);
	}

	public void inItDb(String createString) {
		if (isInit)
			return;
		this.createString = createString;
		this.isInit = true;
	}

	// 增
	public void insert(ContentValues values, String tableName) {
		SQLiteDatabase db = getWritableDatabase();
		System.out.println("values" + values.get("id") + values.get("name")
				+ values.get("age"));
		db.insert(tableName, null, values);
		Log.i(TAG, "增加一行");
		db.close();
	}

	// 删除某一行
	public void delete(String id, String tableName) {
		// if (db == null) {
		SQLiteDatabase db = getWritableDatabase();
		// }
		db.delete(tableName, "id=?", new String[] { String.valueOf(id) });
		Log.i(TAG, "删除一行");
	}

	// 更新某一行
	public void update(ContentValues values, String string, String tableName) {
		SQLiteDatabase db = getWritableDatabase();
		db.update(tableName, values, "id=?", new String[] { string });
		db.close();
		Log.i(TAG, "更新一行");
	}

	// 按id查询
	public Cursor query(String string, String tableName) {
		SQLiteDatabase db = getWritableDatabase();
		System.out.println("id---->" + string);
		cursor = db.query(tableName, null, "id=?", new String[] { string },
				null, null, null);
		Log.i(TAG, "按id查询一行");
		return cursor;
	}

	public Cursor query(String tableName) {
		SQLiteDatabase db = getWritableDatabase();
		cursor = db.query(tableName, null, null, null, "id", null, "id");
		Log.i(TAG, "查询所有");
		return cursor;
	}

	// 按sql语句操作数据库
	public void handleBySql(String sql) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(sql);
		Log.i(TAG, "执行sql语句");
	}

	// 关闭数据库
	public void close() {
		if (db != null) {
			db.close();
			db = null;
		}
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
