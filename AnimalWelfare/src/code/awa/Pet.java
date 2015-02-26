package code.awa;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class Pet extends Activity {
	Spinner havePet, interest;
	static String sInterest;
	static String sHavePet;
	static String address;
	static String name;
	static String roll;
	static String dept;
	static String gender;
	static String res;
	static String city;
	static String state;
	static String email;
	static String phone;
	static boolean isPhotokept = false;
	Button uploadImage;
	Button Submit;
	String item = null;
	ProgressDialog pdialog;
	Bitmap theImage;
	static String entry = "true";
	static boolean initial;
	static InputStream inputStream = null;
	private static final int CAMERA_REQUEST = 1;
	private static final int PICK_FROM_GALLERY = 2;
	byte[] imageName;
	ImageView imageDetail;
	static String encodedImage;
	static JSONObject jsonObject = new JSONObject();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pet_detail);
		Intent intent = getIntent();
		name = intent.getExtras().getString("Name");
		roll = intent.getExtras().getString("Roll");
		dept = intent.getExtras().getString("Dept");
		gender = intent.getExtras().getString("Gender");
		res = intent.getExtras().getString("Resident");
		address = intent.getExtras().getString("Address");
		city = intent.getExtras().getString("City");
		state = intent.getExtras().getString("State");
		email = intent.getExtras().getString("Email");
		phone = intent.getExtras().getString("Phone");

		havePet = (Spinner) findViewById(R.id.havepet);
		interest = (Spinner) findViewById(R.id.interest);
		uploadImage = (Button) findViewById(R.id.uplaodpetimage);
		imageDetail = (ImageView) findViewById(R.id.imageView);
		Submit = (Button) findViewById(R.id.finish);
		List<String> list = new ArrayList<String>();
		list.add("No");
		list.add("Yes");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		havePet.setAdapter(dataAdapter);

		havePet.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				item = arg0.getSelectedItem().toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		List<String> list1 = new ArrayList<String>();
		list1.add("Organize awareness programs");
		list1.add("Build fauna zone");
		list1.add("PAW Website & App");
		list1.add("News updates");
		list1.add("Photography");
		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list1);
		dataAdapter1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		interest.setAdapter(dataAdapter1);

		// on upload button clicked
		final String[] option = new String[] {"Select from Gallery" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, option);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Select Option");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Log.e("Selected Item", String.valueOf(which));
				if (which == 0) {
					callGallery();
				}

			}
		});
		final AlertDialog dialog = builder.create();

		uploadImage.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialog.show();
			}
		});

		// on submit button clicked
		Submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isPhotokept) {
					Intent i;
					sHavePet = havePet.getSelectedItem().toString();
					sInterest = interest.getSelectedItem().toString();
					new HttpAsyncTask()
							.execute("http://paw.url.ph/Register.php");
					initial = getSharedPreferences("Flag", 0).getBoolean(
							"initial", true);
					getSharedPreferences("Flag", 0).edit()
							.putString("Flag", "false").commit();
					i = new Intent(Pet.this, DrawerHome.class);
					startActivity(i);
					finish();
				} else {
					Toast.makeText(getBaseContext(),
							"Choose a profile Picture!!!", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;

		switch (requestCode) {
		case CAMERA_REQUEST:

			Bundle extras = data.getExtras();

			if (extras != null) {
				Bitmap yourImage = extras.getParcelable("data");
				// convert bitmap to byte
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte imageInByte[] = stream.toByteArray();
				ByteArrayInputStream imageStream = new ByteArrayInputStream(
						imageInByte);
				theImage = BitmapFactory.decodeStream(imageStream);
				imageDetail.setImageBitmap(theImage);
				encodedImage = Base64.encodeToString(imageInByte,
						Base64.DEFAULT);
				isPhotokept = true;
				// Log.i("image", encodedImage);
			}
			break;
		case PICK_FROM_GALLERY:
			Bundle extras2 = data.getExtras();

			if (extras2 != null) {
				Bitmap yourImage = extras2.getParcelable("data");
				// convert bitmap to byte
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte imageInByte[] = stream.toByteArray();
				ByteArrayInputStream imageStream = new ByteArrayInputStream(
						imageInByte);
				theImage = BitmapFactory.decodeStream(imageStream);
				imageDetail.setImageBitmap(theImage);
				encodedImage = Base64.encodeToString(imageInByte,
						Base64.NO_WRAP);
				isPhotokept = true;
				// Log.d("Insert: ", "Inserting ..");
			}

			break;
		}
	}

	/**
	 * open camera method
	 */
	public void callCamera() {
		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra("crop", "true");
		cameraIntent.putExtra("aspectX", 0);
		cameraIntent.putExtra("aspectY", 0);
		cameraIntent.putExtra("outputX", 200);
		cameraIntent.putExtra("outputY", 150);
		startActivityForResult(cameraIntent, CAMERA_REQUEST);

	}

	/**
	 * open gallery method
	 */

	public void callGallery() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 0);
		intent.putExtra("aspectY", 0);
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(
				Intent.createChooser(intent, "Complete action using"),
				PICK_FROM_GALLERY);

	}

	public static String POST(String url) {

		String result = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			String json = "";
			jsonObject.accumulate("name", name);
			jsonObject.accumulate("roll", roll);
			jsonObject.accumulate("dept", dept);
			jsonObject.accumulate("gender", gender);
			jsonObject.accumulate("resident", res);
			jsonObject.accumulate("address", address);
			jsonObject.accumulate("city", city);
			jsonObject.accumulate("state", state);
			jsonObject.accumulate("email", email);
			jsonObject.accumulate("phone", phone);
			jsonObject.accumulate("havepet", sHavePet);
			jsonObject.accumulate("interest", sInterest);
			jsonObject.accumulate("image", encodedImage);
			json = jsonObject.toString();
			StringEntity se = new StringEntity(json);
			httpPost.setEntity(se);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			HttpResponse httpResponse = httpclient.execute(httpPost);
			inputStream = httpResponse.getEntity().getContent();

			if (inputStream != null) {
				result = convertInputStreamToString(inputStream);
			} else {
				result = "Did not work!";
			}
		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}
		return result;
	}

	public boolean isConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected())
			return true;
		else
			return false;
	}

	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;
		inputStream.close();
		return result;
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			return POST(urls[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			// System.out.println(result);
			
			String id = null;
			JSONObject json = null;
			try {
				json = new JSONObject(result);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				id = json.getString("userId");
			} catch (JSONException e) {
				// Log.e("not", "id");
				e.printStackTrace();
			}
			initial = getSharedPreferences("UserId", 0).getBoolean("initial",
					true);
			getSharedPreferences("UserId", 0).edit().putString("UserId", id)
					.commit();
			Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG)
					.show();
		}
	}

}
