package com.example.abdul.servicesmanagementsystem;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class UserProfile extends BaseActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		String userName;
		String userPic;
		String email;
		
		userName = All.getSharedSTR(All.USER_NAME, UserProfile.this);
		if (getSupportActionBar() != null)
			getSupportActionBar().setTitle("Welcome " + userName);
		
		ImageView profilePic = findViewById(R.id.userPic);
		userPic = All.getSharedSTR(All.USER_PIC, UserProfile.this);
		Picasso.get().load(userPic).into(profilePic);
		
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		
		Display display = null;
		if (wm != null) {
			display = wm.getDefaultDisplay();
		}
		if (display != null) {
			display.getMetrics(metrics);
		}
		Integer width = metrics.widthPixels / 2; // 50% width
		Integer height = metrics.heightPixels / 3; // 33% height
		
		
		profilePic.getLayoutParams().width = width;
		profilePic.getLayoutParams().height = height;
//		profilePic.requestLayout();
		
		email = All.getSharedSTR(All.USER_EMAIL, UserProfile.this);
		TextView userEmail = findViewById(R.id.textViewEmail);
		
		userEmail.setText("Email: ");
		userEmail.append(email);
	}
}
