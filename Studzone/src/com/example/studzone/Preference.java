package com.example.studzone;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Preference {
	public static final String PREF_NAME = "agentState";
	public static final int MODE = Context.MODE_PRIVATE;

	public static final String rollno = "rollno";
	public static final String first_register = "first_register";
	public static final String LAST_SYNC_AT = "last_sync_at";

	public static void writeInteger(Context context, String key, int value) {
		getEditor(context).putInt(key, value).commit();

	}

	public static int readInteger(Context context, String key, int defValue) {
		return getPreferences(context).getInt(key, defValue);
	}

	public static void writeString(Context context, String key, String value) {
		getEditor(context).putString(key, value).commit();

	}

	public static String readString(Context context, String key, String defValue) {
		return getPreferences(context).getString(key, defValue);
	}

	public static SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences(PREF_NAME, MODE);
	}

	public static Editor getEditor(Context context) {
		return getPreferences(context).edit();
	}

	public static void deletePref(Context context, String key) {
		getEditor(context).clear().commit();
	}
}