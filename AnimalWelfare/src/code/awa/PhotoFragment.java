package code.awa;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class PhotoFragment extends Fragment {
	ImageView dbimage;
	static String scaption;
	EditText caption;
	Button gallery;
	Button submit,back;
	ProgressDialog pdialog;
	static InputStream inputStream = null;
	static JSONObject jsonObject = new JSONObject();
	static String id;
	static String jsonstring;
	static String image;
	static String encodedImage;
	static boolean isPhotokept = false;
	private static final int PICK_FROM_GALLERY = 2;
	byte[] imageName;

	public PhotoFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		View rootView = inflater.inflate(R.layout.fragment_photo, container,
				false);
		id = this.getActivity().getSharedPreferences("UserId", 0)
				.getString("UserId", "");
		dbimage = (ImageView) rootView.findViewById(R.id.dbimage);
		caption = (EditText) rootView.findViewById(R.id.photocaption);
		gallery = (Button) rootView.findViewById(R.id.choose);
		submit = (Button) rootView.findViewById(R.id.postimage);
		back = (Button) rootView.findViewById(R.id.back);
		// jsonstring = GET("http://192.168.236.1/paw/dsiplayimage.php");
		// new HttpDisplayFeed().execute();
		gallery.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callGallery();
			}
		});
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Fragment mFragment = new HomeFragment();
				getActivity().getActionBar().setTitle("Home");
				getFragmentManager().beginTransaction()
						.replace(R.id.frag_photo, mFragment).commit();
				
			}
		});
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isPhotokept) {
					scaption = caption.getText().toString();
					new HttpDisplayFeed()
							.execute("http://paw.url.ph/postimage.php");
					Fragment mFragment = new HomeFragment();
					getActivity().getActionBar().setTitle("Home");
					getFragmentManager().beginTransaction()
							.replace(R.id.frag_photo, mFragment).commit();
					
				}
				else{
					Toast.makeText(getActivity(), "Choose Photo to share!!!", Toast.LENGTH_SHORT).show();
				}
			}
		});
		return rootView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		getActivity();
		if (resultCode != Activity.RESULT_OK)
			return;

		switch (requestCode) {
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
				Bitmap theImage = BitmapFactory.decodeStream(imageStream);
				dbimage.setImageBitmap(theImage);
				encodedImage = Base64.encodeToString(imageInByte,
						Base64.NO_WRAP);
				isPhotokept = true;
			}
			break;
		}

	}

	// call gallery
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

	public static String GET(String url) {
		String result = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			String json = "";
			jsonObject.accumulate("userid", id);
			jsonObject.accumulate("caption", scaption);
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
			} else
				result = "Did not work!";
		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}
		return result;
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

	private class HttpDisplayFeed extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pdialog = new ProgressDialog(getActivity());
			pdialog.setMessage("Loading ...");
			pdialog.setIndeterminate(false);
			pdialog.setCancelable(false);
			pdialog.show();
		}
		@Override
		protected String doInBackground(String... urls) {
			return GET(urls[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pdialog.dismiss();
			Toast.makeText(getActivity().getBaseContext(), "Post Succesfull",
					Toast.LENGTH_LONG).show();
			JSONObject json = null;			
		}
	}
}
