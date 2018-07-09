package com.example.abdul.servicesmanagementsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllRequests extends BaseActivity {
	ProgressDialog progressDialog;
	Context context;
	private RecyclerView RVListAllCategories;
	private RVAAllRequests adapter;
	private RecyclerView.LayoutManager layoutManager;
	private List<AllRequestsData> dataList = new ArrayList<>();
	List<String> orderID = new ArrayList<String>();
	List<String> orderStatus = new ArrayList<String>();
	List<String> orderWorkerName = new ArrayList<String>();
	List<String> orderTotal = new ArrayList<String>();
	List<String> orderDate = new ArrayList<String>();
	List<String> orderTime = new ArrayList<String>();
	List<String> orderDescription = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_requests);
		context = AllRequests.this;
		
		if ( !All.checkLoginStatus(context) ) {
			finish();
			startActivity(new Intent(context, Login.class));
			return;
		}
		
		setRecyclerView();
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage(All.LOADING_MSG);
		
		final String basicToken = All.getSharedSTR(All.BASIC_TOKEN, context);
		String userEmail = All.getSharedSTR(All.USER_EMAIL, context);
		String userID = All.getSharedSTR(All.USER_ID, context);
		
		StringRequest stringRequest = new StringRequest(
				Request.Method.GET,
				"http://" + All.LOCALHOST + "/fixmyhome/wp-json/wc/v2/orders?search=" + userEmail + "&customer=" + userID,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						progressDialog.dismiss();
						Log.v("Allresuests:", response);
						if ( response.length() > 10)
							setResponse(response);
						else
							Toast.makeText(context, response, Toast.LENGTH_LONG).show();
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						All.handleVolleyError(context, error);
						progressDialog.dismiss();
					}
				}
		) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("Authorization", "Bearer " + basicToken);
				return headers;
			}
		};
		int MY_SOCKET_TIMEOUT_MS = 30000;
		stringRequest.setRetryPolicy(new DefaultRetryPolicy(
				MY_SOCKET_TIMEOUT_MS,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		progressDialog.show();
		Volley.newRequestQueue(context).add(stringRequest);
	}
	
	private void setResponse(String response) {
		if ( !response.isEmpty() ) {
			Gson gson = new Gson();
			List orderObjects = (List) gson.fromJson(response, List.class);
//			String[] postContent = new String[orderObjects.size()];
			for (int i = 0; i < orderObjects.size(); ++i) {
				Map<String, Object> anObject = (Map<String, Object>) orderObjects.get(i);
				
				String id = (Double)anObject.get("id") + "";
				id = id.substring(0, id.indexOf('.'));
				orderID.add( id );
				orderStatus.add( (String)anObject.get("status"));
				
				List line_items = (List) anObject.get("line_items");
				Map<String, Object> line_object = (Map<String, Object>)line_items.get(0);
				
				orderWorkerName.add( (String)line_object.get("name") );
				orderTotal.add( (String)line_object.get("total") );
				
				List meta_data = ((List)line_object.get("meta_data") );
				
				orderTime.add( (String)((Map<String, Object>)meta_data.get(0)).get("value") );
				orderDate.add( (String)((Map<String, Object>)meta_data.get(1)).get("value") );
				orderDescription.add( (String)((Map<String, Object>)meta_data.get(2)).get("value") );
			}
			
			for (int i=0; i<orderID.size(); i++ ) {
			
				dataList.add(
						new AllRequestsData(
								orderID.get(i),
								orderStatus.get(i),
								orderWorkerName.get(i),
								orderTotal.get(i),
								orderTime.get(i),
								orderDate.get(i),
								orderDescription.get(i)
						)
				);
			}
			
			setRecyclerView();
		}
		else {
			Toast.makeText(context, "No Response String !", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void setRecyclerView() {
		RVListAllCategories = findViewById(R.id.recycler3);
		RVListAllCategories.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(this);
		RVListAllCategories.setLayoutManager(layoutManager);
		adapter = new RVAAllRequests(dataList);
		
		RVListAllCategories.setAdapter(adapter);
	}
}


