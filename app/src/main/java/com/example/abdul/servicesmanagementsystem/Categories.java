package com.example.abdul.servicesmanagementsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Categories extends BaseActivity {
	
	ProgressDialog progressDialog;
	private RecyclerView RVListAllCategories;
	private RVACategories adapter; // Recycler View Adapter Categories
	private RecyclerView.LayoutManager layoutManager;
	private List<Data> dataList = new ArrayList<>();
	private CustomRequest customRequest;
	
	public static String getRendered(String response) {
		/**
		 * This function is made according to an expected output, its not universally functional.
		 */
		if ( !response.equals("") ) {
			Gson gson = new Gson();
			List list = (List)gson.fromJson(response, List.class);
			String[] postContent = new String[list.size()];
			for (int i = 0; i < list.size(); ++i) {
				Map<String, Object> mapPost = (Map<String, Object>) list.get(i);
				Map<String, Object> mapContent = (Map<String, Object>) mapPost.get("content");
				postContent[i] = (String) mapContent.get("rendered");
			}
			return postContent[0];
		}
		return "";
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		setRecyclerView(); //starting it here first, without any data because of a warning in logcat.
		
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage(All.LOADING_MSG);
		progressDialog.show();
		
		String url = "http://" + All.LOCALHOST + "/fixmyhome/wp-json/wp/v2/pages?slug=services";
		final Context context_=this;
		
		customRequest = new CustomRequest(new IResult() {
			@Override
			public void notifySuccess(String response) {
				
				String rendered = getRendered(response);
				
				List<String> https = All.getStringList(
						rendered,
						"srcset=\"",
						" "
				);
				
				List<String> names = All.getStringList(
						rendered,
						"alt=\"",
						"\""
				);
				
				List<String> categoryNames = All.getStringList(
						rendered,
						"product-category/",
						"/"
				);
				
				for (int i = 0; i < names.size(); i++) {
					dataList.add(
							new Data(
									https.get(i).replace("localhost", All.LOCALHOST),
									names.get(i),
									categoryNames.get(i)
							)
					);
				}
				setRecyclerView();
				progressDialog.dismiss();
			}
			
			@Override
			public void notifyError(VolleyError error) {
				progressDialog.dismiss();
				All.handleVolleyError(context_, error);
				All.handleNormalResponse(context_, error);
				startActivity(new Intent(context_, MainActivity.class));
			}
		}
				, this,
				url,
				Request.Method.GET);
		Log.v("URL: ", url);
		
		customRequest.executeRequest();
	}
	
	private void setRecyclerView() {
		
		RVListAllCategories = findViewById(R.id.recycler1);
		RVListAllCategories.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(this);
		RVListAllCategories.setLayoutManager(layoutManager);
		adapter = new RVACategories(dataList);
		
		RVListAllCategories.setAdapter(adapter);
	}
	
	@Override
	public void onBackPressed() {
		if (progressDialog.isShowing()) {
			customRequest.cancelRequest();
			progressDialog.cancel();
		} else {
			super.onBackPressed();
		}
	}
}
