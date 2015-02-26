package code.awa;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Contact extends Activity {
	EditText address, city, email, phone;
	String name, roll, dept, gender, res;
	String sAddress, sCity, sState, sEmail, sPhone;
	Spinner state;
	Button next2;
	boolean isValid=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		Intent intent = getIntent();
		name = intent.getExtras().getString("Name");
		roll = intent.getExtras().getString("Roll");
		dept = intent.getExtras().getString("Dept");
		gender = intent.getExtras().getString("Gender");
		res = intent.getExtras().getString("Resident");
		address = (EditText) findViewById(R.id.address);
		city = (EditText) findViewById(R.id.city);
		state = (Spinner) findViewById(R.id.state);
		email = (EditText) findViewById(R.id.email);
		phone = (EditText) findViewById(R.id.phone);
		next2 = (Button) findViewById(R.id.next2);
		
		List<String> list = new ArrayList<String>();
		list.add("Andhra Pradesh");
		list.add("Arunachal Pradesh");
		list.add("Assam");
		list.add("Bihar");
		list.add("Chhattisgarh");
		list.add("Goa");
		list.add("Gujarat");
		list.add("Haryana");
		list.add("Himachal Pradesh");
		list.add("Jammu and Kashmir");
		list.add("Jharkhand");
		list.add("Karnataka");
		list.add("Kerala");
		list.add("Madhya Pradesh");
		list.add("Maharashtra");
		list.add("Manipur");
		list.add("Meghalaya");
		list.add("Mizoram");
		list.add("Nagaland");
		list.add("Odisha");
		list.add("Punjab");
		list.add("Rajasthan");
		list.add("Sikkim");
		list.add("Tamil Nadu");
		list.add("Tripura");
		list.add("Uttar Pradesh");
		list.add("Uttarakhand");
		list.add("West Bengal");
		list.add("Andaman and Nicobar Islands");
		list.add("Chandigarh");
		list.add("Dadra and Nagar Haveli");
		list.add("Daman and Diu");
		list.add("Delhi");
		list.add("Lakshadweep");
		list.add("Pondicherry");
		list.add("Other");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		state.setAdapter(dataAdapter);

		next2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sAddress = address.getText().toString();
				sCity = city.getText().toString();
				sState = state.getSelectedItem().toString();
				sEmail = email.getText().toString();
				sPhone = phone.getText().toString();
				if(isEmailNotValid(sEmail)){
					Toast.makeText(getApplicationContext(),
							"Invalid Email Address...",
							Toast.LENGTH_SHORT).show();
					isValid = true;
				}
				if(validatePhoneNumber(sPhone)){
					Toast.makeText(getApplicationContext(),
							"Invalid Mobile Number...",
							Toast.LENGTH_SHORT).show();
					isValid = true;
				}
				if (sAddress.equals("") || sCity.equals("")
						|| sState.equals("") || sEmail.equals("")
						|| sPhone.equals("") || isValid) {
					Toast.makeText(getApplicationContext(),
							"Enter Correct Details",
							Toast.LENGTH_SHORT).show();
					isValid = false;
				} else {
					Intent i = new Intent(Contact.this, Pet.class);
					i.putExtra("Name", name);
					i.putExtra("Roll", roll);
					i.putExtra("Dept", dept);
					i.putExtra("Gender", gender);
					i.putExtra("Resident", res);
					i.putExtra("Address", sAddress);
					i.putExtra("City", sCity);
					i.putExtra("State", sState);
					i.putExtra("Email", sEmail);
					i.putExtra("Phone", sPhone);
					startActivity(i);
					finish();
				}
			}
		});
	}

	public boolean isEmailNotValid(String email1) {
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		Boolean b = email1.matches(EMAIL_REGEX);
		System.out.println("is e-mail: " + email1 + " :Valid = " + b);
		if (b) {
			return false;
		} else {
			return true;
		}
	}

	private static boolean validatePhoneNumber(String phoneNo) {		
				
        System.out.println(phoneNo.length());
        String regex = "^\\+?[0-9. ()-]{10,25}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNo);
 
        if (matcher.matches()) {
            return false;
        } else {
            return true;
        }
	}
}
