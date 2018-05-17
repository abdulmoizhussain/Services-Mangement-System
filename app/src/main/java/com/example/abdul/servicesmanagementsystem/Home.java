package com.example.abdul.servicesmanagementsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Home extends AppCompatActivity {

	private RecyclerView recyclerView;
	private RecyclerViewAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private List<Data> dataList = new ArrayList<>();

	IResult iResult ;


	public static List<String> getString (String sourceSTR, String startOfSearch, Boolean isFromStart,String endOfSearch, Boolean isTillEnd) {

		List<String> stringList = new ArrayList<>();
		Pattern p = Pattern.compile(startOfSearch);
		Matcher m = p.matcher(sourceSTR);
		while (m.find()) {

			String temp = sourceSTR.substring( isFromStart? m.start(): m.end() );
			Pattern pp = Pattern.compile(endOfSearch);
			Matcher mm = pp.matcher(temp);

			String temp2="";

			if (mm.find()) {
				temp2 = temp.substring(0,
						isTillEnd? mm.end() : mm.start()
						);
			}
			stringList.add(temp2);
		}
		return stringList;
	}

	public static String getRendered (String response) {
		/**
		 * This function is made according to the expected-required output, its not universally functional.
		 */
		Gson gson = new Gson();
		List list = (List) gson.fromJson(response, List.class);
		String[] postContent = new String[list.size()];
		for (int i=0; i<list.size(); ++i) {
			Map<String, Object> mapPost = (Map<String,Object>)list.get(i);
			Map<String, Object> mapContent = (Map<String,Object>) mapPost.get("content");
			postContent[i] = (String) mapContent.get("rendered");
		}
		return postContent[0];
	}

	ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		setRecyclerView(); //starting it here first, without any data because of a warning logcat.

		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Loading...");
		progressDialog.show();

		String url = Global.URL_PRE + Global.URL_PAGES + "?slug=services";

		RequestClass requestClass = new RequestClass(new IResult() {
			@Override
			public void notifySuccess(String response) {

				String rendered = getRendered(response);

				SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(Home.this).edit();
				editor.putString(Global.SERVICES_STRING, rendered);
				editor.apply();

				List<String> https = Home.getString(
						rendered,
						"srcset=\"",
						false,
						" ",
						false
				);

				List<String> names = Home.getString(
						rendered,
						"alt=\"",
						false,
						"\"",
						false
				);

				List<String> categoryNames = Home.getString(
						rendered,
						"product-category/", false,
						"/", false
				);

				for (int i=0; i<names.size(); i++) {
					dataList.add(
							new Data(
									https.get(i).replace("localhost", Global.LOCALHOST),
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
				Toast.makeText(Home.this, error.toString(), Toast.LENGTH_SHORT).show();
			}
		}
				,this,
				url,
				Request.Method.GET);
		Log.v("URL: ", url);

		requestClass.executeRequest();
	}

	private void setRecyclerView () {
		recyclerView = findViewById(R.id.recycler1);
		recyclerView.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		adapter = new RecyclerViewAdapter(dataList);

		recyclerView.setAdapter(adapter);
	}
}
