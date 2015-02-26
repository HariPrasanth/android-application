package com.example.studzone;

import android.content.Context;
import android.net.ConnectivityManager;

public class CheckInternet {
	private Context _context;

	public CheckInternet(Context context) {
		this._context = context;
	}

	public boolean isAvailable() {

		ConnectivityManager conMgr = (ConnectivityManager) _context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (conMgr.getActiveNetworkInfo() != null

		&& conMgr.getActiveNetworkInfo().isAvailable()

		&& conMgr.getActiveNetworkInfo().isConnected()) {

			return true;

		} else {

			return false;

		}

	}

}
