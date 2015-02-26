package com.jwetherell.quick_response_code;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.Toast;

public class FirstPage extends Activity implements OnGestureListener {
	private GestureDetector gDetector;
	Intent i;
	int id;
	Database db;
	Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_firstpage);
		gDetector = new GestureDetector(this);
		db = new Database(this);
		i = new Intent(FirstPage.this, CaptureActivity.class);
	}

	
	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent start, MotionEvent finish,
			float xVelocity, float yVelocity) {
		if (start.getRawY() <= finish.getRawY()) {
			startActivity(i);
		} else {

		}
		return true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		db.deleteAll();
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	// 6. An important and often overlooked step in correctly wiring the gesture
	// detector is overriding the activity's on touch handler and feeding the
	// result to the gesture detector; this is how all gesture detections occur,
	// as the result of re-routing an on touch. Without this step, your gesture
	// detector will never fire.

	@Override
	public boolean onTouchEvent(MotionEvent me) {
		return gDetector.onTouchEvent(me);
	}
}
