package code.awa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class GalleryFragment extends Fragment {
	static final String KEY_NAME = "name";
	static final String KEY_CAPTION = "caption";
	static final String KEY_IMAGE = "image";

	String[] nameArray;
	String[] captionArray;
	String[] imageArray;
	ListView list;
	ProgressDialog pdialog;
	GalleryListAdapter adapter;
	int length = 0;
	static InputStream inputStream = null;
	static JSONObject jsonObject = new JSONObject();
	static String id;
	ArrayList<HashMap<String, String>> classList = new ArrayList<HashMap<String, String>>();
	static String jsonstring;

	public GalleryFragment() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		View rootView = inflater.inflate(R.layout.fragment_gallery, container,
				false);
		id = this.getActivity().getSharedPreferences("UserId", 0)
				.getString("UserId", "");
		jsonstring = GET("http://paw.url.ph/displayimage.php");		
		new HttpDisplayFeed().execute();
		return rootView;
	}

	public static String GET(String url) {
		String result = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			String json = "";
			jsonObject.accumulate("userid", "23");
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
		protected String doInBackground(String... args) {
			JSONArray ja = null;
			try {
				ja = new JSONArray(jsonstring);
				length = ja.length();
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			try {
				nameArray = new String[ja.length()];
				captionArray = new String[ja.length()];
				imageArray = new String[ja.length()];
				for (int i = 0; i < ja.length(); i++) {
					nameArray[i] = ((JSONObject) ja.get(i)).getString("name");
					captionArray[i] = ((JSONObject) ja.get(i)).getString("caption");
					imageArray[i] = ((JSONObject) ja.get(i)).getString("photo");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pdialog.dismiss();
			int i = length-1;
			while (i >=0) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(KEY_NAME, nameArray[i]);
				map.put(KEY_CAPTION, captionArray[i]);
				map.put(KEY_IMAGE, imageArray[i]);
				classList.add(map);
				i--;
			}
			list = (ListView) getActivity().findViewById(R.id.photo_list);
			adapter = new GalleryListAdapter(getActivity(), classList);
			list.setAdapter(adapter);
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
				}
			});

		}
	}

}
