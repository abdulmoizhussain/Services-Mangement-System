package com.example.abdul.servicesmanagementsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
	String startURL = "http://";
	//"192.168.1.110"
	//http://10.0.2.2
//	String endURL = "/FixMyHome/wp-json/wp/v2/posts?fields=id,title,content";
//	String endURL = "/FixMyHome/wp-json/wp/v2/users?fields=id";
	String endURL = "/FixMyHome/wp-json/wp/v2/posts";
	String loginURL = "/fixmyhome/wp-json/jwt-auth/v1/token";
	//String url = "http://192.168.1.110/wp-json/wp/v2/posts?filter[posts_per_page]=10&fields=id,title";
	List<Object> list;
	Gson gson;
	ProgressDialog progressDialog;
	ListView postList;
	Map<String,Object> mapTitle;
	Map<String,Object> mapPost;
	Map<String,Object> mapContent;
	int postID;
	String postTitle[], url;
	TextView textView, textView3;
	EditText editText;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		textView = findViewById(R.id.textView);
		textView3 = findViewById(R.id.textView3);
		editText = findViewById(R.id.editText);
		postList = findViewById(R.id.listView);
	}


	public void onLoginButton (View v) {
		startActivity(new Intent(this, login.class));
	}
}
