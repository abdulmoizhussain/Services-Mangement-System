package com.example.abdul.servicesmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Worker extends BaseActivity {
	private Context context;
	String response;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_worker);
		context = Worker.this;
		
		response = getIntent().getStringExtra(All.SELECTED_PRODUCT);
		Log.v("worker: ", response);
		
		TextView workerNameView = findViewById(R.id.worker_name);
		String workerNameString = All.getStringList(response, "<h2 class=\"woocommerce-loop-product__title\">", "</h2>").get(0);
		
		if (getSupportActionBar() != null)
			getSupportActionBar().setTitle(workerNameString);
		
		All.setSharedSTR(All.WORKER_NAME, workerNameString, context);
		
		workerNameView.setText(workerNameString); // setting name
		
		workerNameView = findViewById(R.id.worker_fee); // overwriting it with worker_fee_view
		
		workerNameView.setText("");
		workerNameView.append("Fee: " +
				All.getStringList(response, "</span>", "</span></span>").get(0)
		);
	}
	public void onRequestService (View v) {
		
		String worker_id = All.getStringList(response, "product_id=\"", "\"").get(0);
		All.setSharedSTR(All.WORKER_ID, worker_id, context);
		
		Intent intent = new Intent(context, RequestService.class);
		startActivity(intent);
	}
}
