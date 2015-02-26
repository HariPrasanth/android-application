package code.awa;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

public class MainSplashScreen extends Activity {
	static String entry = "true";
	private AlertDialog alertDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_splash_screen);
		entry = getSharedPreferences("Flag", 0).getString("Flag", "");
		// METHOD 1

		/****** Create Thread that will sleep for 1 seconds *************/
		Thread background = new Thread() {
			public void run() {

				try {
					// Thread will sleep for 3 seconds
					sleep(2 * 1000);

					// After 3 seconds redirect to another intent
					if (entry.equals("false")) {
						if (checkInternetConnection()) {
							Intent i = new Intent(getBaseContext(),
									DrawerHome.class);
							startActivity(i);
						}
						else{
							Toast.makeText(getBaseContext(), "Connect to internet and try again", Toast.LENGTH_SHORT).show();
						}
					} else {
						Intent i = new Intent(getBaseContext(), Register.class);
						startActivity(i);
					}
					// Remove activity
					finish();

				} catch (Exception e) {

				}
			}
		};
		background.start();
	}

	public boolean isConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected())
			return true;
		else
			return false;
	}

	public void alert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainSplashScreen.this);
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
			alert();
			return false;
		}
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();

	}
}
