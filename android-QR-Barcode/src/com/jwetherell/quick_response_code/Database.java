package com.jwetherell.quick_response_code;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

	public Database(Context context) {
		super(context, "Qrdb.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public void DeleteRecord(String x) {
		SQLiteDatabase db = this.getWritableDatabase();
		String delete = "DELETE from products where code = '" + x + "';";
		// db.delete("products", "code ="+x, null);
		db.execSQL(delete);
	}

	public void deleteAll() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("products", null, null);
	}

	public boolean checkCount() {
		boolean hasEntry = false;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cur = db.rawQuery("SELECT COUNT(*) FROM products", null);
		if (cur != null) {
			cur.moveToFirst();
			if (cur.getInt(0) == 0) {
				hasEntry = false;
			} else {
				hasEntry = true;
			}
		}
		return hasEntry;
	}

}
