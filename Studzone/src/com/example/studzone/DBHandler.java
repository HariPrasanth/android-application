package com.example.studzone;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "Studzone";

	// Table name
	private static final String STUDENT_DETAILS = "student";

	// Contacts Table Columns names

	// Creating Student table
	String CREATE_STUDENT_TABLE = "CREATE TABLE " + STUDENT_DETAILS + "("
			+ Links.POSITION + " INTEGER PRIMARY KEY," + Links.JSON + " TEXT)";

	public void addStudentDetails(LocalDBModel students) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Links.POSITION, students.getPosition());
		values.put(Links.JSON, students.getJson());
		db.insert(STUDENT_DETAILS, null, values);
		db.close();
	}

	public DBHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_STUDENT_TABLE);
	}

	public List<LocalDBModel> getAllStudentValues() {
		List<LocalDBModel> students = new ArrayList<LocalDBModel>();
		String selectQuery = "SELECT  * FROM " + STUDENT_DETAILS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				LocalDBModel stud = new LocalDBModel();
				stud.setPosition(c.getInt(c.getColumnIndex(Links.POSITION)));
				stud.setJson(c.getString(c.getColumnIndex(Links.JSON)));
				students.add(stud);
			} while (c.moveToNext());
		}

		return students;
	}

	// Dropping and Creating again
	public void DeleteTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DELETE FROM " + STUDENT_DETAILS);
		db.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + STUDENT_DETAILS);
		// create new tables
		onCreate(db);
	}

	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}
}