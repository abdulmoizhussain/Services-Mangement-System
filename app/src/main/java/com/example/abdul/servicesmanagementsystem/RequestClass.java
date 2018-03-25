package com.example.abdul.servicesmanagementsystem;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abdul on 3/24/2018.
 *
 */

public class RequestClass {

	private Map<String,String> params;
	private String responseString;
	private String URL;
	private int METHOD;
	private Context context;

	RequestClass (Context context, String url, int method) {
		this.context = context;
		this.URL = url;
		this.METHOD = method;
		this.params = new HashMap<>();
	}

	public void executeRequest() {
		StringRequest request = new StringRequest(this.METHOD,
				this.URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String responseStr) {
						responseString = responseStr;
					}
				},new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				volleyError.printStackTrace();
//				volleyError.
//				pass.append( volleyError.toString() );
			}
		})
		{
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
//				if (params.length != 0) {
//					for (int i=0; i<params.length; i++) {
//						// getting params and their values from paramValues...
//						// an putting them into hash Map.
//						parameters.put(params[i], paramValues[i] );
//				    }
//				}
				return params;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<>();
//				String authToken = "Bearer " + ;
//				headers.put("Content-Type", "application/json");
				headers.put("Content-Type", "application/x-www-form-urlencoded");
//				headers.put("Authorization", authToken);
				return headers;
			}
		};
		RequestQueue rQueue = Volley.newRequestQueue(this.context);
		rQueue.add(request);
	}

	public void putSendingParam(String param, String paramValue) {
		this.params.put(param, paramValue);
	}

	public String getResponseString() {
		return this.responseString;
	}

	public void setAuthorization (String token) {
//		"Authorization "
//		"Bearer "+token;
//		status = private;
	}
}
