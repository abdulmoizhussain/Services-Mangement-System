package com.example.abdul.servicesmanagementsystem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RequestService extends BaseActivity {
	ProgressDialog progressDialog;
	private final int DATE_DIALOGUE_ID = 1;
	private final int TIME_DIALOGUE_ID = 2;
	Context context;
	private String customerName;
	private String customerID;
	private String workerName;
	private String workerID;
	private String country;
	private String address;
	private String city;
	private String province;
	private String zipcode;
	private String phone;
	private String customerEmail;
	private String time;
	private String date;
	private String description;
	private Calendar cal = Calendar.getInstance();
	private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
			
			Calendar selected = Calendar.getInstance();
			selected.set(Calendar.YEAR, year);
			selected.set(Calendar.MONTH, month);
			selected.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			
			try {
				Date currentDate = dateFormat().parse(dateFormat().format(Calendar.getInstance().getTime()));
				
				Date selectedDate = dateFormat().parse(dateFormat().format(selected.getTime()));
				
				if (currentDate.after(selectedDate)) {
					Toast.makeText(context,
							"Invalid: Must be a Future Date !",
							Toast.LENGTH_LONG).show();
					showDialog(DATE_DIALOGUE_ID);
				} else {
					dateViewUpdate(year, month, dayOfMonth);
				}
				
			} catch (ParseException pe) {
				pe.printStackTrace();
			} catch (NullPointerException npe) {
				npe.printStackTrace();
			}
			
		}
	};
	private TimePickerDialog.OnTimeSetListener onTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfDay) {
			
			timeViewUpdate(hourOfDay, minuteOfDay);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_complaint);
		context = RequestService.this;
		
		if ( !All.checkLoginStatus(context) ) {
			finish();
			startActivity(new Intent(context, Login.class));
			return;
		}
		
		progressDialog = new ProgressDialog(context);
		
		TextView to_from = findViewById(R.id.textViewCustomerName); // writing with "From: " textView
		customerName = All.getSharedSTR(All.USER_NAME, context);
		to_from.setText(""); to_from.append("From: " + customerName);
		
		to_from = findViewById(R.id.textViewWorkerName); // now overwriting with "To: " textView
		workerName = All.getSharedSTR(All.WORKER_NAME, context);
		to_from.setText(""); to_from.append("To: "+ workerName);
		
		workerID = All.getSharedSTR(All.WORKER_ID, context); // calling worker ID for submission
		
		customerEmail = All.getSharedSTR(All.USER_EMAIL, context);
		
		customerID = All.getSharedSTR(All.USER_ID, context);
		
		setCountries();
		
		cal.setTime(new Date());
		
		dateViewUpdate(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH)
		);
		
		timeViewUpdate(
				cal.get(Calendar.HOUR_OF_DAY),
				cal.get(Calendar.MINUTE)
		);
	}
	
	public void onTimeTextClick(View v) {
		showDialog(TIME_DIALOGUE_ID);
	}
	
	public void onDateTextClick(View v) {
		showDialog(DATE_DIALOGUE_ID);
	}
	
	private void dateViewUpdate(int year, int month, int dayOfMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		
		TextView textView = findViewById(R.id.textViewDate);
		textView.setText(dateFormat().format(calendar.getTime()));
		date = dateFormat().format(calendar.getTime());
	}
	
	private void timeViewUpdate(int hours, int minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hours);
		calendar.set(Calendar.MINUTE, minutes);
		
		TextView textView = findViewById(R.id.textViewTime);
		textView.setText(timeFormat().format(calendar.getTime()));
		time = timeFormat().format(calendar.getTime());
	}
	
	private SimpleDateFormat dateFormat() {
		return new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
	}
	
	private SimpleDateFormat timeFormat() {
//		if (DateFormat.is24HourFormat(context))
		if (false)
			return new SimpleDateFormat("HH:mm", Locale.getDefault());
		else
			return new SimpleDateFormat("hh:mm a", Locale.getDefault());
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Calendar cal = Calendar.getInstance();
//		cal.setTime(new Date());
		switch (id) {
			case DATE_DIALOGUE_ID:
				return new DatePickerDialog(context, onDateSetListener,
						cal.get(Calendar.YEAR),
						cal.get(Calendar.MONTH),
						cal.get(Calendar.DAY_OF_MONTH)
				);
			
			case TIME_DIALOGUE_ID:
				return new TimePickerDialog(context, onTimePickerListener,
						cal.get(Calendar.HOUR_OF_DAY),
						cal.get(Calendar.MINUTE),
						false);
		}
		return null;
	}
	
	public void onSubmitComplaint(View v) {
		final String basicToken = All.getSharedSTR(All.BASIC_TOKEN, context);
		
		EditText tt = findViewById(R.id.editTextAddress);
		address = tt.getText().toString();
		
		tt = findViewById(R.id.editTextPhone);
		phone = tt.getText().toString();
		
		tt = findViewById(R.id.editTextCity);
		city = tt.getText().toString();
		
		tt = findViewById(R.id.editTextProvince);
		province = tt.getText().toString();
		
		tt = findViewById(R.id.editTextZIP);
		zipcode = tt.getText().toString();
		
		tt = findViewById(R.id.editTextDetails);
		description = tt.getText().toString();
		
		StringRequest stringRequest = new StringRequest(
				Request.Method.POST,
				"http://" + All.LOCALHOST+"/fixmyhome/wp-json/wc/v2/orders",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						progressDialog.dismiss();
						try {
							String msg = All.getStringList(response, "<p>", "Their").get(0);
							
							AlertDialog alertDialog = new AlertDialog.Builder(context).create();
							alertDialog.setTitle("Request Successful");
							alertDialog.setMessage(msg + "\n\n");
							
							alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {
											startActivity(new Intent(context, MainActivity.class));
											finish();
											dialog.dismiss();
										}
									});
							alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "All Request",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {
											startActivity(new Intent(context, AllRequests.class));
											finish();
											dialog.dismiss();
										}
									});
							alertDialog.show();
							
							Log.v("orders: ", response);
						} catch ( Exception e) { e.printStackTrace(); }
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						progressDialog.dismiss();
						All.handleVolleyError(context, error);
					}
				}
		) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("Authorization", "Bearer " + basicToken);
				return headers;
			}
			
			@Override
			public byte[] getBody() throws AuthFailureError {
				String body =
						"{\n" +
								"\t\"payment_method\": \"cod\",\n" +
								"\t\"payment_method_title\": \"Cash on Delivery\",\n" +
								"\t\"status\": \"processing\",\n" +
								"\t\"currency\": \"PKR\",\n" +
								"\t\"customer_id\": "+customerID+",\n" +
								"\t\"billing\": {\n" +
								"\t\t\"first_name\": \""+customerName+"\",\n" +
								"\t\t\"last_name\": \"\",\n" +
								"\t\t\"company\": \"\",\n" +
								"\t\t\"address_1\": \""+address+"\",\n" +
								"\t\t\"address_2\": \"\",\n" +
								"\t\t\"city\": \""+city+"\",\n" +
								"\t\t\"state\": \""+province+"\",\n" +
								"\t\t\"postcode\": \""+zipcode+"\",\n" +
								"\t\t\"country\": \""+country+"\",\n" +
								"\t\t\"email\": \""+customerEmail+"\",\n" +
								"\t\t\"phone\": \""+phone+"\"\n" +
								"\t},\n" +
								"\t\"line_items\": [\n" +
								"\t\t{\n" +
								"\t\t\t\"product_id\": "+workerID+",\n" +
								"\t\t\t\"quantity\": 1,\n" +
								"\t\t\t\"meta_data\": [\n" +
								"\t\t\t\t{\n" +
								"\t\t\t\t\t\"key\": \"Time\",\n" +
								"\t\t\t\t\t\"value\": \""+time+"\"\n" +
								"\t\t\t\t},\n" +
								"\t\t\t\t{\n" +
								"\t\t\t\t\t\"key\": \"Date\",\n" +
								"\t\t\t\t\t\"value\": \""+date+"\"\n" +
								"\t\t\t\t},\n" +
								"\t\t\t\t{\n" +
								"\t\t\t\t\t\"key\": \"Description\",\n" +
								"\t\t\t\t\t\"value\": \""+description+"\"\n" +
								"\t\t\t\t}\n" +
								"\t\t\t]\n" +
								"\t\t}\n" +
								"\t]\n" +
								"}";
				return body.getBytes();
			}
			
			@Override
			public String getBodyContentType() {
				return "application/json; charset=utf-8";
			}
		};
		
		int MY_SOCKET_TIMEOUT_MS = 30000; // setting connection timeout to 10 seconds
		stringRequest.setRetryPolicy(new DefaultRetryPolicy(
				MY_SOCKET_TIMEOUT_MS,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		
		progressDialog.setMessage("Submitting request, please wait.");
		progressDialog.show();
		Volley.newRequestQueue(context).add(stringRequest);
	}
	
	private void setCountries() {
		Spinner spinner = findViewById(R.id.spinner_country);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				country = parent.getItemAtPosition(position).toString();
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			
			}
		});
		List<String> countries = new ArrayList<String>();
		countries.add("Norway");
		countries.add("Oman");
		countries.add("Pakistan");
		countries.add("Palestinian Territory");
		countries.add("Panama");
		countries.add("Papua New Guinea");
		countries.add("Paraguay");
		countries.add("Peru");
		countries.add("Philippines");
		countries.add("Qatar");
		countries.add("Romania");
		countries.add("Russia");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(RequestService.this, android.R.layout.simple_spinner_item, countries);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}
}
