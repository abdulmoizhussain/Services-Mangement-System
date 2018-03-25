package com.example.abdul.servicesmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class login extends AppCompatActivity {
	private EditText user,pass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		user = findViewById(R.id.username);
		pass = findViewById(R.id.password);
	}

	public void onSubmit (View v) {

	}
}
