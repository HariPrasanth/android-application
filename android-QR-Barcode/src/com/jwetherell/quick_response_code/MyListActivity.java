package com.jwetherell.quick_response_code;

import java.util.ArrayList;
import java.util.Locale;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyListActivity extends ListActivity {
	static int length;
	public static SQLiteDatabase db;
	static Cursor cur;
	static String[] pro = null;
	static boolean canGo = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getListView().setDivider(null);
		length = 1;
		db = openOrCreateDatabase("Qrdb.db",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
		db.setVersion(1);
		db.setLocale(Locale.getDefault());
		getValue();

	}

	public void getValue() {
		String select = "SELECT * FROM products";
		cur = db.rawQuery(select, null);
		cur.moveToFirst();
		while (!cur.isLast()) {
			length++;
			cur.moveToNext();
		}
		pro = new String[length];
		cur.moveToFirst();
		for (int i = 0; i < length; i++) {
			pro[i] = cur.getString(0).toString();
			Log.i("code",pro[i]);
			cur.moveToNext();
		}
	}

	protected ArrayAdapter<String> createListAdapter() {
		return new MyListAdapter(this, getItems(), pro);
	}

	public static ArrayList<String> getItems() {
		ArrayList<String> items = new ArrayList<String>();
		for (int i = 0; i < length; i++) {
			// items.add(String.valueOf(i));
			items.add(pro[i]);
		}
		return items;
	}

	private static class MyListAdapter extends ArrayAdapter<String> {

		private Context mContext;
		String[] items;

		public MyListAdapter(Context context, ArrayList<String> items,
				String[] pro) {
			super(items);
			mContext = context;
			this.items = pro;
		}

		@Override
		public long getItemId(int position) {
			return getItem(position).hashCode();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv = (TextView) convertView;
			if (tv == null) {
				tv = (TextView) LayoutInflater.from(mContext).inflate(
						R.layout.list_row, parent, false);
			}
			tv.setText(getItem(position));
			return tv;
		}
	}
}
