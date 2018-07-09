package com.example.abdul.servicesmanagementsystem;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Abdul on 3/24/2018.
 */

public class CustomRequest {
	
	private Map<String, String> mParams;
	private String responseString;
	private String mURL;
	private int mMETHOD;
	private Context mContext;
	private Map<String, String> mHeaders;
	private IResult mResultCallback = null;
	private StringRequest request;
	
	CustomRequest(IResult resultCallback, Context context, String url, int method) {
		mResultCallback = resultCallback;
		mContext = context;
		mURL = url;
		mMETHOD = method;
		mParams = new HashMap<>();
		mHeaders = new HashMap<>();
	}
	
	public void cancelRequest() {
		if (request != null) {
			request.cancel();
			Toast.makeText(mContext, "Request cancelled", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(mContext, "Error: empty request.", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void executeRequest() {
		Toast.makeText(mContext, ":::" + mURL, Toast.LENGTH_SHORT).show();
		this.request = new StringRequest(this.mMETHOD,
				mURL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String responseStr) {
						responseString = responseStr;
						
						if (mResultCallback != null)
							mResultCallback.notifySuccess(responseStr);
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						volleyError.printStackTrace();
						
						if (mResultCallback != null)
							mResultCallback.notifyError(volleyError);
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return mParams;
			}
			
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
//				String authToken = "Bearer " + ;
//				headers.put("Authorization", authToken);
//				mHeaders.put("Content-Type", "application/json");
//				mHeaders.put("Content-Type", "application/x-www-form-urlencoded");
				return mHeaders;
			}
		};
		RequestQueue rQueue = Volley.newRequestQueue(this.mContext);
		rQueue.add(this.request);
	}
	
	public void setParamAndValue(String param, String value) {
		this.mParams.put(param, value);
	}
	
	public void setHeaderAndValue(String param, String value) {
		this.mHeaders.put(param, value);
	}
	
//	public String getResponseString() {
//		return this.responseString;
//	}
}
