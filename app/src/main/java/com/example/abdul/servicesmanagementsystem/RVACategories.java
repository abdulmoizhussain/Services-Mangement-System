package com.example.abdul.servicesmanagementsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdul on 4/22/2018.
 * <p>
 * LIST OF ALL AVAILABLE CATEGORIES
 */

public class RVACategories extends RecyclerView.Adapter<RecyclerViewHolder> {
	
	private List<Data> listData = new ArrayList<Data>();
	private Context context_;
	
	public RVACategories(List<Data> data_) {
		this.listData = data_;
	}
	
	@Override
	public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		
		LayoutInflater inflater = LayoutInflater.from(context_ = viewGroup.getContext());
		View itemView = inflater.inflate(R.layout.list_item_layout, viewGroup, false);
		
		return new RecyclerViewHolder(itemView,
				new RecyclerViewHolder.ViewHolderClicks() {
					
					@Override
					public void onCategoryClick(TextView ID, final TextView textViewCategoryName, final TextView name) {
						
						String url = "http://" + All.LOCALHOST + "/fixmyhome/wp-json/wp/v2/pages?slug=products";
						
						final ProgressDialog progressDialog = new ProgressDialog(context_);
						progressDialog.setMessage(All.LOADING_MSG);
						progressDialog.show();
						
						new CustomRequest(
								new IResult() {
									@Override
									public void notifySuccess(String response) {
										
										String rendered = Categories.getRendered(response);
										
										Intent intent = new Intent(context_, Products.class);
										
										intent.putExtra(All.CATEGORIZED_PRODUCTS, rendered);
										// passing the rendered string to next activity. To categorized_products.class
										
										intent.putExtra(All.SELECTED_CATEGORY,
												textViewCategoryName.getText().toString());
										// passing the selected category to next activity
										
										intent.putExtra(All.SELECTED_CATEGORY_NICE_NAME,
												name.getText().toString());
										// sending readable name to Products.class to set it in Action Bar as title.
										
										progressDialog.dismiss();
										
										context_.startActivity(intent);
									}
									
									@Override
									public void notifyError(VolleyError error) {
										progressDialog.dismiss();
										All.handleVolleyError(context_, error);
										All.handleNormalResponse(context_, error);
									}
								},
								context_,
								url,
								Request.Method.GET
						).executeRequest();
					}
				});
	}
	
	@Override
	public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int i) {
		
		Picasso.get().load(listData.get(i).getImageURL()).into(recyclerViewHolder.imageView);
		//setting image's URL
		
		recyclerViewHolder.textViewName.setText(listData.get(i).getName());
		//setting sentence case- Readable name of the category.
		
		recyclerViewHolder.textViewID.setText("" + i);
		// setting ID to use the data through it, when it will be clicked.
		
		recyclerViewHolder.textViewCategoryName.setText(listData.get(i).getCategory());
		// setting category name from category for matching-purpose-in-if-condition, which will be used, when clicked/tapped.
	}
	
	@Override
	public int getItemCount() {
		return listData.size();
	}
}

class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
	public ImageView imageView;
	public TextView textViewName;
	public TextView textViewID;
	public TextView textViewCategoryName;
	public ViewHolderClicks mListener;
	
	public RecyclerViewHolder(View itemView, ViewHolderClicks listener) {
		super(itemView);
		this.mListener = listener;
		
		imageView = itemView.findViewById(R.id.image_item);
		textViewName = itemView.findViewById(R.id.text_item);
		textViewID = itemView.findViewById(R.id.textViewID);
		textViewCategoryName = itemView.findViewById(R.id.category);
		
		itemView.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		mListener.onCategoryClick(textViewID, textViewCategoryName, textViewName);
	}
	
	public static interface ViewHolderClicks {
		void onCategoryClick(TextView ID, TextView textViewURL, TextView TVName);
	}
}
