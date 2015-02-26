package com.example.studzone;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Window;

public class SplashScreen extends Activity {
	private long ms = 0;
	private long splashDuration = 700;
	private boolean splashActive = true;
	private boolean paused = false;
	Boolean isInternetPresent = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splashscreen);
		try {
			Thread mythread = new Thread() {
				public void run() {
					try {
						while (splashActive && ms < splashDuration) {
							if (!paused)
								ms = ms + 100;
							sleep(100);
						}
					} catch (Exception e) {
					} finally {
						Intent intent = new Intent(SplashScreen.this,
								MainActivity.class);
						startActivity(intent);
						finish();
					}
				}
			};
			mythread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}