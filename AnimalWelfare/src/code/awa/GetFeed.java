package code.awa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GetFeed extends Fragment {
	EditText getFeed;
	static String Gfeed;
	Button post, cancel;
	public String[] nameArray;
	ProgressDialog pdialog;
	static String id;
	static InputStream inputStream = null;
	static String jsonstring;
	static JSONObject jsonObject = new JSONObject();

	public GetFeed() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.activity_get_feed, container,
				false);

		getFeed = (EditText) rootView.findViewById(R.id.getFeed);
		post = (Button) rootView.findViewById(R.id.postFeed);
		cancel = (Button) rootView.findViewById(R.id.cancelbutton);
		id = this.getActivity().getSharedPreferences("UserId", 0)
				.getString("UserId", "");		
		post.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Gfeed = getFeed.getText().toString();
				if (!Gfeed.equals("")) {
					jsonstring = POST("http://paw.url.ph/post.php");
					new HttpPostFeed().execute();
					Fragment mFragment = new HomeFragment();
					getActivity().getActionBar().setTitle("Home");
					getFragmentManager().beginTransaction()
							.replace(R.id.frag_getfeed, mFragment).commit();
					
				} else {
					Toast.makeText(getActivity(), "Entet Some Text to post!!!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Fragment mFragment = new HomeFragment();
				getActivity().getActionBar().setTitle("Home");
				getFragmentManager().beginTransaction()
						.replace(R.id.frag_getfeed, mFragment).commit();				
			}
		});
		return rootView;

	}
	

	public static String POST(String url) {
		String result = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			String json = "";
			jsonObject.accumulate("userid", id);
			jsonObject.accumulate("Post", Gfeed);
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

	private class HttpPostFeed extends AsyncTask<String, Void, String> {
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
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			JSONArray ja = null;
			try {
				ja = new JSONArray(jsonstring);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pdialog.dismiss();
		}
	}
}
