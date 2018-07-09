package com.example.abdul.servicesmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * LIST OF SELECTED CATEGORY'S PRODUCTS
 */

public class Products extends BaseActivity {
	
	RecyclerView recyclerView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categorized_products);
		
		String rendered = getIntent().getStringExtra(All.CATEGORIZED_PRODUCTS);
//		Log.v("rendered: ", rendered);
		
		//receiving the rendered string.
		String selectedCategory = getIntent().getStringExtra(All.SELECTED_CATEGORY);
		
		if (getSupportActionBar() != null)
			getSupportActionBar().setTitle(
					getIntent().getStringExtra(All.SELECTED_CATEGORY_NICE_NAME)
			);
		
		List<String> img_sources = All.getStringList(
				// to get the image URLs of the products.
				rendered,
				"src=\"",
				"\""
		);
		
		List<String> names = All.getStringList(
				// to get names of the workers/products from the rendered string.
				rendered,
				"class=\"woocommerce-loop-product__title\">",
				"</h2>"
		);
		
		List<String> categories = All.getStringList(
				//to get category of every individual product in the string.
				rendered,
				"product_cat-",
				" "
		);
		
		List<String> LIs = All.getStringList(rendered, "<li ", "</li>");
		// taking out all available products in <li> tags.
		List<Data> dataList = new ArrayList<>();
		String LItoString = "";
		
		// taking out selected/tapped category from all available categories.
		// and adding those values in data-list
		for (int i = 0; i < categories.size(); i++) {
			if (categories.get(i).equals(selectedCategory)) {
//				Log.v("src: ", img_sources.get(i));
//				Log.v("name: ", names.get(i));
//				Log.v("category: ", categories.get(i));
				LItoString = LItoString.concat( "420" + LIs.get(i) ); // '||' separator for splitting it in RVAProducts.java
				
				dataList.add(
						new Data(
								img_sources.get(i).replace("localhost", All.LOCALHOST),
								names.get(i)
						)
				);
			}
		}
		
		if ( ! LItoString.equals("") ) { // in case if LItString is an empty string.
			LItoString = LItoString.substring(3);
		}
		
		All.setSharedSTR(All.SELECTED_CATEGORY_LIST, LItoString, this);
		
		recyclerView = findViewById(R.id.recycler2);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

//		for (int i=0; i<names.size(); i++) {
//			dataList.add( new Data(
//					https.get(i).replace("localhost", All.LOCALHOST)
//					,names.get(i)) );
//		}

//		RVACategories adapter = new RVACategories(dataList);
		RVAProducts adapter = new RVAProducts(dataList);
		
		recyclerView.setAdapter(adapter);
	}
	
	
}
