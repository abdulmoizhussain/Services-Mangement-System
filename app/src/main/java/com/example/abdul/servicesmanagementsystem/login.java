package com.example.abdul.servicesmanagementsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

public class login extends AppCompatActivity {

	private EditText user,pass;
	IResult iResult = new IResult() {
		@Override
		public void notifySuccess(String response) {

			String token = getToken(response);
			if (!token.equals("")) {
				SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(login.this);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putString(Global.TOKEN,
						token
				).apply();

				Toast.makeText(login.this,
						sharedPreferences.getString(Global.TOKEN, ""),
						Toast.LENGTH_SHORT).show();
			}

		}

		@Override
		public void notifyError(VolleyError error) {
			Toast.makeText(login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

//		initializeCallback();


		final String CONSUMER_KEY = "ck_ff6976252c41d4fb2a080853f5fe1ce04763fdf2";
		final String CONSUMER_SECRET = "cs_4a20f83cd0aba4082864cb12d668683fafcef97c";

		String url = Global.URL_PRE_VIR+Global.FixMyHome+"/wp-json/wc/v2/products";
		DefaultOAuthConsumer consumer = new DefaultOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);

		String URL__ ="";

		try {
			URL__ = consumer.sign(url);
			Log.v("signed", URL__);
		} catch (OAuthMessageSignerException mse) {
			mse.printStackTrace();
		} catch (OAuthExpectationFailedException efe) {
			efe.printStackTrace();
		} catch (OAuthCommunicationException ce) {
			ce.printStackTrace();
		}

		RequestClass n1 = new RequestClass(
				new IResult() {
					@Override
					public void notifySuccess(String response) {
						Log.v("main_log",response);
						Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();
					}

					@Override
					public void notifyError(VolleyError error) {
						Toast.makeText(login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
						error.printStackTrace();
						Log.v("main_error", error.getMessage());
					}
				},
				this,
				URL__,
				Request.Method.GET
		);
		n1.setHeaderAndValue("Cache-Control","no-cache");
		n1.executeRequest();

		user = findViewById(R.id.username);
		pass = findViewById(R.id.password);
	}

	public void onSubmit (View v) {
		RequestClass requestClass = new RequestClass(iResult,this,
				Global.URL_PRE_VIR + Global.URL_AUTH,
				Request.Method.POST);
		requestClass.setParamAndValue("username", user.getText().toString());
		requestClass.setParamAndValue("password", pass.getText().toString());
		requestClass.executeRequest();
	}

	private String getToken(String responseStr) {
		String string="";
		try {
			JSONObject object = new JSONObject( responseStr );
			string = object.getString("token");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (!string.equals("")) {
			return string;
		}
		return "";
	}

//	private void initializeCallback() {
//
//	}
}
