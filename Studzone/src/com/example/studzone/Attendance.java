package com.example.studzone;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Attendance extends Activity {
	TableLayout t1;
	String rollno, name, course, sem, php_file, url, option, status;
	int pos;
	TextView stud_rollno, sroll, stud_name, sname, stud_course, scourse, stud_sem, semester, detailsNotFound;
	JSONObject json, obj_values;
	JSONArray obj, obj1;
	ProgressDialog pdialog;
	JSONParser jParser = new JSONParser();
	JSONArray studentsArray = null;
	private AlertDialog alertDialog;
	DBHandler db;
	ProgressDialog pDialog;
	int success;
	JSONArray StudentsArray = null;
	List<LocalDBModel> stud_list_values;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attendance);
	
		db = new DBHandler(Attendance.this);
		Intent intent = getIntent();
		stud_rollno = (TextView) findViewById(R.id.rollno);
		sroll = (TextView) findViewById(R.id.roll);
		stud_name = (TextView) findViewById(R.id.stud_name);
		sname = (TextView) findViewById(R.id.name);
		stud_course = (TextView) findViewById(R.id.stud_course);
		scourse = (TextView) findViewById(R.id.course);
		stud_sem = (TextView) findViewById(R.id.sem);
		semester = (TextView) findViewById(R.id.semester);
		
		detailsNotFound = (TextView)findViewById(R.id.detailsNotFound);
		
		rollno = intent.getStringExtra(Links.ROLLNO);
		pos = intent.getIntExtra(Links.POS, 0);
		php_file = intent.getStringExtra(Links.PHP_FILE);
		
		detailsNotFound.setVisibility(TextView.INVISIBLE);
		semester.setVisibility(TextView.VISIBLE);
		sroll.setVisibility(TextView.VISIBLE);
		sname.setVisibility(TextView.VISIBLE);
		scourse.setVisibility(TextView.VISIBLE);
		
		if (pos == 1) {
			option = "Attendance";
		} else if (pos == 2) {
			option = "CA Marks";
		} else if (pos == 3) {
			option = "Test TimeTable";
		} else if (pos == 4) {
			option = "Exam Result";
		} else if (pos == 5) {
			option = "Semester TimeTable";
		} else if (pos == 6) {
			option = "Seating Arrangement";
		}
		final ActionBar actionBar = getActionBar();
		actionBar.setTitle(option);
		actionBar.setIcon(R.drawable.ic_launcher);
		actionBar.setDisplayHomeAsUpEnabled(true);
		if (!checkInternetConnection()) {
			rollno = Preference.readString(getBaseContext(), Preference.rollno,
					"0");
			if (rollno.equals("0")) {
				Toast.makeText(getBaseContext(), "No Roll no registered",
						Toast.LENGTH_SHORT).show();
				finish();
			} else {
				new StudentDetails().execute();
			}
		} else {
			new StudentDetails().execute();
		}
	}

	public void alert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(Attendance.this);
		builder.setTitle("No Internet Connection");
		builder.setMessage("Please Turn on Mobile data or Wifi");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int i) {
				// Show location settings when the user
				// acknowledges the alert dialog
				Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
				startActivity(intent);
			}
		});
		alertDialog = builder.create();
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.show();
	}

	private boolean checkInternetConnection() {

		ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conMgr.getActiveNetworkInfo() != null
				&& conMgr.getActiveNetworkInfo().isAvailable()
				&& conMgr.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	class StudentDetails extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pdialog = new ProgressDialog(Attendance.this);
			pdialog.setMessage("Loading ...");
			pdialog.setIndeterminate(false);
			pdialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				if (checkInternetConnection()) {
					List<NameValuePair> param = new ArrayList<NameValuePair>();
					param.add(new BasicNameValuePair(Links.ROLLNO, rollno));
					url = Links.URL + php_file;
					json = jParser.makeHttpRequest(url, "GET", param);

				} else {
					stud_list_values = db.getAllStudentValues();
					String str_json = stud_list_values.get(pos - 1).getJson();
					json = new JSONObject(str_json);
				}
				status = json.getString(Links.STATUS);
				rollno = json.getString(Links.ROLLNO);
				name = json.getString(Links.STUD_NAME);
				course = json.getString(Links.COURSE);
				if (pos == 1 || pos == 2) {
					sem = json.getString(Links.SEMESTER);
				}
				studentsArray = json.getJSONArray(Links.STUD_CA_LIST);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			pdialog.dismiss();
			try {
				if (status.equals("404")) {
					Toast.makeText(getBaseContext(), "Details not available",
							Toast.LENGTH_SHORT).show();
					detailsNotFound.setVisibility(TextView.VISIBLE);
					semester.setVisibility(TextView.INVISIBLE);
					sroll.setVisibility(TextView.INVISIBLE);
					sname.setVisibility(TextView.INVISIBLE);
					scourse.setVisibility(TextView.INVISIBLE);
				} else {
					int inc = 0;
					stud_rollno.setText(rollno);
					stud_name.setText(name);
					stud_course.setText(course);
					if (pos == 1 || pos == 2) {
						stud_sem.setText(sem);
					}
					else{
						semester.setVisibility(TextView.INVISIBLE);
					}
					for (int j = 0; j < studentsArray.length(); j++) {
						TableLayout tl = (TableLayout) findViewById(R.id.main_table);
						TableRow tr_head = new TableRow(getBaseContext());
						tr_head.setId(inc);
						inc++;
						tr_head.setBackgroundColor(0xFF14b9d6);
						tr_head.setLayoutParams(new LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT));
						obj = studentsArray.getJSONArray(j);
						obj1 = obj.getJSONArray(0);
						for (int i = 0; i < obj1.length(); i++) {
							TextView heading = new TextView(getBaseContext());
							heading.setId(inc);
							inc++;
							heading.setText(obj1.getString(i));
							heading.setGravity(Gravity.CENTER);
							heading.setTextColor(Color.WHITE);
							heading.setPadding(5, 5, 5, 5);
							tr_head.addView(heading);
						}
						tl.addView(tr_head, new TableLayout.LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT));

						for (int k = 1; k < obj.length(); k++) {
							TableRow tr_values = new TableRow(getBaseContext());
							tr_values.setId(inc);
							inc++;
							tr_values.setBackgroundColor(Color.WHITE);
							tr_values.setLayoutParams(new LayoutParams(
									LayoutParams.FILL_PARENT,
									LayoutParams.WRAP_CONTENT));
							obj1 = obj.getJSONArray(k);
							for (int i = 0; i < obj1.length(); i++) {
								TextView value = new TextView(getBaseContext());
								value.setId(inc);
								inc++;
								value.setText(obj1.getString(i));
								value.setTextColor(Color.BLACK);
								value.setGravity(Gravity.CENTER);
								value.setPadding(5, 5, 5, 5);
								tr_values.addView(value);
							}
							tl.addView(tr_values, new TableLayout.LayoutParams(
									LayoutParams.FILL_PARENT,
									LayoutParams.WRAP_CONTENT));
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}