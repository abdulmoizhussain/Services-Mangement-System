package com.example.abdul.servicesmanagementsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseActivity {
	Integer basicTokenCounter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		oneTimeCode();
		
		basicTokenCounter = 0;
		getAndSaveBasicTOKEN(); // to check internet connectivity and set basic working token.
	}
	
	
	private void getAndSaveBasicTOKEN() {
		CustomRequest customRequest = new CustomRequest(new IResult() {
			@Override
			public void notifySuccess(String response) {
				
				try {
					String value;
					JSONObject object = new JSONObject(response);
					value = object.getString("token");
					All.setSharedSTR(All.BASIC_TOKEN, value, MainActivity.this);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void notifyError(VolleyError error) {
				All.handleVolleyError(MainActivity.this, error);
				Toast.makeText(MainActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
				if (basicTokenCounter < 3) {
					getAndSaveBasicTOKEN();
				}
			}
		},
				MainActivity.this,
				"http://" + All.LOCALHOST + "/fixmyhome/wp-json/jwt-auth/v1/token",
				Request.Method.POST
		);
		customRequest.setParamAndValue("username", "admin");
		customRequest.setParamAndValue("password", "admin");
		customRequest.executeRequest();
		basicTokenCounter++;
	}
	
	public void onHome(View v) {
		startActivity(new Intent(this, Categories.class));
	}
	
	private void oneTimeCode() {
		Log.v("MODEL:", Build.MODEL);
		if ((Build.MODEL).equals("Android SDK built for x86")) {
			All.setLOCALHOST(true);
		} else {
			All.setLOCALHOST(false);
		}
	}
}

