package com.example.abdul.servicesmanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class categorized_products extends AppCompatActivity {

	RecyclerView recyclerView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categorized_products);


		String rendered = getIntent().getStringExtra(Global.CATEGORIZED_PRODUCTS); //receiving the rendered string.
		String selectedCategory = getIntent().getStringExtra(Global.SELECTED_CATEGORY); //receiving the rendered string.

		List<String> img_sources = Home.getString( // to get the image URLs of the products.
				rendered,
				"<img src=\"",false,
				"\"",false
		);


		List<String> names = Home.getString( // to get names of the workers/products from the rendered string.
				rendered,
				"class=\"woocommerce-loop-product__title\">", false,
				"</h2>",false
		);

		List<String> categories = Home.getString( //to get category of every individual product in the string.
				rendered,
				"product_cat-", false,
				" ",false
		);

		List<Data> dataList = new ArrayList<>();

		for (int i=0; i<categories.size(); i++) {
			if ( categories.get(i).equals(selectedCategory) ) {
				Log.v("src: ", img_sources.get(i));
				Log.v("name: ", names.get(i));
				Log.v("category: ", categories.get(i));

				dataList.add(
						new Data(
								img_sources.get(i).replace("localhost", Global.LOCALHOST),
								names.get(i)
						)
				);
			}
		}

		recyclerView = findViewById(R.id.recycler2);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

//		for (int i=0; i<names.size(); i++) {
//			dataList.add( new Data(
//					https.get(i).replace("localhost", Global.LOCALHOST)
//					,names.get(i)) );
//		}

		RecyclerViewAdapter adapter = new RecyclerViewAdapter(dataList);

		recyclerView.setAdapter(adapter);
	}
}
