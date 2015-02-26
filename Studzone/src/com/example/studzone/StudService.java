package com.example.studzone;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

public class StudService extends Service {
	String json_string, url;
	String rollno, unsyncedCust;
	JSONObject json = null, stud_json;
	String[] php_file = { "getstudentattendance.php", "getcamark.php",
			"getexamresult.php", "gettesttimetable.php" };
	JSONParser jparser;
	CheckInternet ci;
	DBHandler db;
	String last_sync_time;
	List<LocalDBModel> stud_list_values;

	@Override
	public void onCreate() {
		super.onCreate();
		try {
			jparser = new JSONParser();
			ci = new CheckInternet(StudService.this);
			db = new DBHandler(StudService.this);
			last_sync_time = Preference.readString(StudService.this,
					Preference.LAST_SYNC_AT, "0");

			Long sync_time = Long.parseLong(last_sync_time);
			rollno = Preference.readString(getBaseContext(), Preference.rollno,
					"0");
			if (ci.isAvailable()) {
				if (rollno.equals("0")) {
					Toast.makeText(getBaseContext(),
							"No Roll no is registered", Toast.LENGTH_SHORT)
							.show();
				} else {
					new FirstSync().execute();
				}
			} else if (Math.abs(System.currentTimeMillis() - sync_time) > 300000
					&& ci.isAvailable()) {
				new FirstSync().execute();
			} else {
				stopSelf();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// First Sync Async Task
	class FirstSync extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {

			try {
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair(Links.TAG, Links.FIRST_SYNC));
				param.add(new BasicNameValuePair(Links.ROLLNO, rollno));
				db.DeleteTable();
				for (int i = 0; i < 4; i++) {
					url = Links.URL + php_file[i];
					json = jparser.makeHttpRequest(url, "POST", param);
					LocalDBModel Sobj = new LocalDBModel();
					Sobj.setPosition(i + 1);
					Sobj.setJson(json + "");
					db.addStudentDetails(Sobj);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			try {
				stopSelf();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}