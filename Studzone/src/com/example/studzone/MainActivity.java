package com.example.studzone;

import org.json.JSONObject;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button attendance, ca, exam, test, semester, seating, submit;
	EditText rollno_et;
	String link, url, rollno, first_register;
	int pos = 0;
	int success = 0;
	JSONObject json;
	CheckInternet ci;
	JSONParser jParser = new JSONParser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ci = new CheckInternet(getBaseContext());
		attendance = (Button) findViewById(R.id.attendance);
		ca = (Button) findViewById(R.id.ca);
		exam = (Button) findViewById(R.id.exam);
		test = (Button) findViewById(R.id.test);
		semester = (Button) findViewById(R.id.semester);
		seating = (Button) findViewById(R.id.seating);
		submit = (Button) findViewById(R.id.submit);
		rollno_et = (EditText) findViewById(R.id.rollno);
		first_register = Preference.readString(getBaseContext(),
				Preference.first_register, "0");
		if (first_register.equals("0")) {
			AlertDialog("register");
		}
		if (ci.isAvailable() && !isMyServiceRunning(StudService.class)) {
			final Intent intent = new Intent(getBaseContext(),
					StudService.class);
			startService(intent);
		}
		if (!Preference.readString(getBaseContext(), Preference.first_register,
				"0").equals("0")) {
			if (ci.isAvailable() && !isMyServiceRunning(StudService.class)) {
				final Intent intent = new Intent(getBaseContext(),
						StudService.class);
				startService(intent);
			}
		}
		if (!ci.isAvailable()) {
			rollno_et.setText(rollno);
			rollno_et.setEnabled(false);
		}

		ca.setBackgroundResource(R.drawable.blue_btn_selector);
		ca.setTextColor(0xFFFFFFFF);
		pos = 2;
		link = "getcamark.php";

		attendance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					pos = 1;
					link = "getstudentattendance.php";
					attendance.setTextColor(0xFFFFFFFF);
					ca.setTextColor(0xFF000000);
					exam.setTextColor(0xFF000000);
					test.setTextColor(0xFF000000);
					semester.setTextColor(0xFF000000);
					seating.setTextColor(0xFF000000);
					attendance
							.setBackgroundResource(R.drawable.blue_btn_selector);
					ca.setBackgroundResource(R.drawable.save_selector);
					exam.setBackgroundResource(R.drawable.save_selector);
					test.setBackgroundResource(R.drawable.save_selector);
					semester.setBackgroundResource(R.drawable.save_selector);
					seating.setBackgroundResource(R.drawable.save_selector);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		ca.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					pos = 2;
					link = "getcamark.php";
					attendance.setTextColor(0xFF000000);
					ca.setTextColor(0xFFFFFFFF);
					exam.setTextColor(0xFF000000);
					test.setTextColor(0xFF000000);
					semester.setTextColor(0xFF000000);
					seating.setTextColor(0xFF000000);
					ca.setBackgroundResource(R.drawable.blue_btn_selector);
					attendance.setBackgroundResource(R.drawable.save_selector);
					exam.setBackgroundResource(R.drawable.save_selector);
					test.setBackgroundResource(R.drawable.save_selector);
					semester.setBackgroundResource(R.drawable.save_selector);
					seating.setBackgroundResource(R.drawable.save_selector);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		exam.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					pos = 3;
					link = "gettesttimetable.php";
					attendance.setTextColor(0xFF000000);
					ca.setTextColor(0xFF000000);
					exam.setTextColor(0xFFFFFFFF);
					test.setTextColor(0xFF000000);
					semester.setTextColor(0xFF000000);
					seating.setTextColor(0xFF000000);
					exam.setBackgroundResource(R.drawable.blue_btn_selector);
					attendance.setBackgroundResource(R.drawable.save_selector);
					ca.setBackgroundResource(R.drawable.save_selector);
					test.setBackgroundResource(R.drawable.save_selector);
					semester.setBackgroundResource(R.drawable.save_selector);
					seating.setBackgroundResource(R.drawable.save_selector);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		test.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					pos = 4;
					link = "getexamresult.php";
					attendance.setTextColor(0xFF000000);
					ca.setTextColor(0xFF000000);
					exam.setTextColor(0xFF000000);
					test.setTextColor(0xFFFFFFFF);
					semester.setTextColor(0xFF000000);
					seating.setTextColor(0xFF000000);
					test.setBackgroundResource(R.drawable.blue_btn_selector);
					attendance.setBackgroundResource(R.drawable.save_selector);
					ca.setBackgroundResource(R.drawable.save_selector);
					exam.setBackgroundResource(R.drawable.save_selector);
					semester.setBackgroundResource(R.drawable.save_selector);
					seating.setBackgroundResource(R.drawable.save_selector);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		semester.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					pos = 5;
					link = "getexamtimetable.php";
					attendance.setTextColor(0xFF000000);
					ca.setTextColor(0xFF000000);
					test.setTextColor(0xFF000000);
					exam.setTextColor(0xFF000000);
					semester.setTextColor(0xFFFFFFFF);
					seating.setTextColor(0xFF000000);
					attendance.setBackgroundResource(R.drawable.save_selector);
					ca.setBackgroundResource(R.drawable.save_selector);
					exam.setBackgroundResource(R.drawable.save_selector);
					test.setBackgroundResource(R.drawable.save_selector);
					semester.setBackgroundResource(R.drawable.blue_btn_selector);
					seating.setBackgroundResource(R.drawable.save_selector);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		seating.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					pos = 6;
					link = "getseatingarrangement.php";
					attendance.setTextColor(0xFF000000);
					ca.setTextColor(0xFF000000);
					test.setTextColor(0xFF000000);
					exam.setTextColor(0xFF000000);
					semester.setTextColor(0xFF000000);
					seating.setTextColor(0xFFFFFFFF);
					attendance.setBackgroundResource(R.drawable.save_selector);
					ca.setBackgroundResource(R.drawable.save_selector);
					exam.setBackgroundResource(R.drawable.save_selector);
					test.setBackgroundResource(R.drawable.save_selector);
					semester.setBackgroundResource(R.drawable.save_selector);
					seating.setBackgroundResource(R.drawable.blue_btn_selector);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (pos == 0) {
					Toast.makeText(getBaseContext(), "Select any option",
							Toast.LENGTH_SHORT).show();
				} else if (rollno_et.getText().toString().equals("")) {
					Toast.makeText(getBaseContext(), "Please Enter Rollno",
							Toast.LENGTH_SHORT).show();
				} else {
					rollno = rollno_et.getText().toString();
					Intent intent = new Intent(getBaseContext(),
							Attendance.class);
					intent.putExtra(Links.ROLLNO, rollno);
					intent.putExtra(Links.PHP_FILE, link);
					intent.putExtra(Links.POS, pos);
					startActivity(intent);
				}
			}
		});
	}

	public void AlertDialog(final String option) {
		// Get the layout inflater
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.register_dialog, null);
		builder.setView(layout);
		final EditText rollnoInput = (EditText) layout
				.findViewById(R.id.register_rollno);
		final TextView confirm = (TextView) layout
				.findViewById(R.id.confirm_register);
		confirm.setVisibility(View.GONE);

		builder.setPositiveButton("Register",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						if (option.equals("menu")) {
							rollno = rollnoInput.getText().toString();
							ConfirmAlert(rollno);
						} else {
							rollno = rollnoInput.getText().toString();
							Preference.writeString(getBaseContext(),
									Preference.first_register, "1");
							Preference.writeString(getBaseContext(),
									Preference.rollno, rollno);
							if (ci.isAvailable()
									&& !isMyServiceRunning(StudService.class)) {
								final Intent intent = new Intent(
										getBaseContext(), StudService.class);
								startService(intent);
							}
						}
					}
				}).setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// MainActivity.this.getDialog().cancel();
						dialog.cancel();
					}
				});
		builder.setCancelable(false);
		builder.create().show();
	}

	public void ConfirmAlert(final String rollno) {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.register_dialog, null);
		builder.setView(layout);
		final TextView confirm = (TextView) layout
				.findViewById(R.id.confirm_register);
		final EditText rollnoInput = (EditText) layout
				.findViewById(R.id.register_rollno);
		rollnoInput.setVisibility(View.GONE);
		confirm.setText(Html.fromHtml("Do you really want to confirm "
				+ "<u><font color=\"#5C77B4\">" + rollno + "</font></u>"
				+ " as Registered Rollno ?"));
		builder.setPositiveButton("Confirm",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						Preference.writeString(getBaseContext(),
								Preference.first_register, "1");
						Preference.writeString(getBaseContext(),
								Preference.rollno, rollno);
						if (ci.isAvailable()
								&& !isMyServiceRunning(StudService.class)) {
							final Intent intent = new Intent(getBaseContext(),
									StudService.class);
							startService(intent);
						}
					}
				}).setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// MainActivity.this.getDialog().cancel();
						dialog.cancel();
					}
				});
		builder.setCancelable(false);
		builder.create().show();
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getTitle().equals("Edit Rollno")) {
			AlertDialog("menu");
		} else if (item.getTitle().equals("About Us")) {
			Intent intent = new Intent(getBaseContext(), AboutUs.class);
			startActivity(intent);
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private boolean isMyServiceRunning(Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (serviceClass.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!ci.isAvailable()) {
			rollno = Preference.readString(getBaseContext(), Preference.rollno,
					"0");
			rollno_et.setText(rollno);
			rollno_et.setEnabled(false);
		} else {
			rollno_et.setEnabled(true);
		}
	}
}