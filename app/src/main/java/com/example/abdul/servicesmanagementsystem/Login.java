package com.example.abdul.servicesmanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends BaseActivity {
	private EditText user, pass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		if (getSupportActionBar() != null)
			getSupportActionBar().setTitle("Login");
		
		user = findViewById(R.id.username);
		pass = findViewById(R.id.password);
	}
	
	public void onSubmit(View v) {
		String username = user.getText().toString().trim();
		String password = pass.getText().toString();
		if (username.isEmpty()) {
			Toast.makeText(this, "Username is empty", Toast.LENGTH_SHORT).show();
			return;
		}
		if (password.isEmpty()) {
			Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
			return;
		}
		
		CustomRequest customRequest = new CustomRequest(
				new IResult() {
					@Override
					public void notifySuccess(String response) {

						if (response.length() < 10) {
							Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
							return;
						}
//						String[] props = {"token", "user_email", "user_nicename", "user_display_name"};
						
						All.setSharedSTR(All.TOKEN, getProperty(response, "token"), Login.this);
						
						String nice_name = getProperty(response, "user_nicename");
						
						// user email will be used later.
						All.setSharedSTR(All.USER_EMAIL, getProperty(response, "user_email"), Login.this);
						
						String url = "http://" + All.LOCALHOST+"/fixmyhome/wp-json/wp/v2/users?slug=" + nice_name;
						
						CustomRequest customRequest1 = new CustomRequest(new IResult() {
							@Override
							public void notifySuccess(String response) {
								
								if (response.length() < 6 ) {
									Toast.makeText(Login.this, "User not recognized.", Toast.LENGTH_SHORT).show();
									return;
								}
								
								All.setSharedSTR(All.USER_PIC,
										All.getStringList(response, "\"96\":\"", "\"").get(0),
										Login.this
								);
								All.setSharedSTR(All.USER_NAME,
										All.getStringList(response, "\"name\":\"", "\"").get(0), Login.this);
								
								All.setSharedSTR(All.USER_ID,
										All.getStringList(response, "\"id\":", ",").get(0), Login.this);
								
								Log.v("response", response);
								startActivity(new Intent(Login.this, UserProfile.class));
							}
							
							@Override
							public void notifyError(VolleyError error) {
								All.handleVolleyError(Login.this, error);
							}
						},
								Login.this,
								url,
								Request.Method.GET
						);
						customRequest1.setHeaderAndValue("Authorization", "Bearer " +
								All.getSharedSTR(All.BASIC_TOKEN, Login.this));
						customRequest1.executeRequest();
					}
					
					@Override
					public void notifyError(VolleyError error) {
						All.handleVolleyError(Login.this, error);
					}
				},
				this,
				"http://" + All.LOCALHOST+"/fixmyhome/wp-json/jwt-auth/v1/token",
				Request.Method.POST);
		
		customRequest.setParamAndValue("username", username);
		customRequest.setParamAndValue("password", password);
		
		customRequest.executeRequest();
	}
	
	private String getProperty(String source, String key) {

		String value = "";
		try {
			JSONObject object = new JSONObject(source);
			value = object.getString(key);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (value != null) {
			return value;
		}
		return "";
	}
}
