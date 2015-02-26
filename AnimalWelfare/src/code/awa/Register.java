package code.awa;

import java.util.ArrayList;
import java.util.List;

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

public class Register extends Activity {
	EditText name, roll;
	String sName, sRoll, sDept, sGender, sRes;
	Spinner gender, res, dept;
	Button next1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		name = (EditText) findViewById(R.id.name);
		roll = (EditText) findViewById(R.id.RollNumber);
		dept = (Spinner) findViewById(R.id.department);
		gender = (Spinner) findViewById(R.id.sex);
		res = (Spinner) findViewById(R.id.resident);
		next1 = (Button) findViewById(R.id.next1);
		
		List<String> list2 = new ArrayList<String>();
		list2.add("Automobile Engineering");
		list2.add("Applied Mathematics and Computational Sciences");
		list2.add("Apparel and Fashion Design");
		list2.add("Bio Technology");
		list2.add("Biomedical Engineering");
		list2.add("Chemistry");
		list2.add("Civil Engineering");
		list2.add("Computer Applications");
		list2.add("Computer Science and Engineering");
		list2.add("Electrical and Electronics Engineering");
		list2.add("Electronics & Communication Engineering");
		list2.add("English");
		list2.add("Fashion Technology");
		list2.add("Humanities");
		list2.add("Information Technology");
		list2.add("Instrumentation and Control Engineering");
		list2.add("Management Sciences");
		list2.add("Mathematics");
		list2.add("Mechanical Engineering");
		list2.add("Metallurgical Engineering");
		list2.add("Physics");
		list2.add("Applied Science");
		list2.add("Physical Education");
		list2.add("Production Engineering");
		list2.add("Robotics and Automation Engineering");
		list2.add("Textile Technology");
		
		ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list2);
		dataAdapter2
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dept.setAdapter(dataAdapter2);
		List<String> list = new ArrayList<String>();
		list.add("Male");
		list.add("Female");
		list.add("Others");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		gender.setAdapter(dataAdapter);

		List<String> list1 = new ArrayList<String>();
		list1.add("Hosteller");
		list1.add("Day Scholar");
		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list1);
		dataAdapter1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		res.setAdapter(dataAdapter1);
		next1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sName = name.getText().toString();
				sRoll = roll.getText().toString();
				sDept = dept.getSelectedItem().toString();
				sGender = gender.getSelectedItem().toString();
				sRes = res.getSelectedItem().toString();
				if (sName.equals("") || sRoll.equals("") || sDept.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Enter All The Details", Toast.LENGTH_SHORT).show();
				} else {
					Intent i = new Intent(Register.this, Contact.class);
					i.putExtra("Name", sName);
					i.putExtra("Roll", sRoll);
					i.putExtra("Dept", sDept);
					i.putExtra("Gender", sGender);
					i.putExtra("Resident", sRes);
					startActivity(i);
					finish();
				}
			}
		});
	}
}
