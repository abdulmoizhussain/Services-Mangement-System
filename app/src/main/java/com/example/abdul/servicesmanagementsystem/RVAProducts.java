package com.example.abdul.servicesmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdul on 5/5/2018.
 * LIST OF SELECTED CATEGORY'S PRODUCTS
 */

public class RVAProducts extends RecyclerView.Adapter<SelectedCategoryRecyclerViewHolder> {
	
	private List<Data> listData = new ArrayList<Data>();
	private Context context_;
	
	public RVAProducts(List<Data> data_) {
		this.listData = data_;
	}
	
	@Override
	public SelectedCategoryRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		context_ = viewGroup.getContext();
		LayoutInflater inflater = LayoutInflater.from( context_ );
		View itemView = inflater.inflate(R.layout.list_item_layout, viewGroup, false);
		
		return new SelectedCategoryRecyclerViewHolder(itemView,
				new SelectedCategoryRecyclerViewHolder.ViewHolderClicks() {
					
					@Override
					public void onProductClick(TextView ID, final TextView categoryName, TextView title, ImageView image) {
						
						
						String selectedCategoryList = All.getSharedSTR(All.SELECTED_CATEGORY_LIST, context_);
						// getting joined (concatenated) string of all the LIs passed from Products.java.

						String[] LIs = selectedCategoryList.split("420");
						// converting the string to stringArray with a delimiter in it as "420"
						
						Intent intent = new Intent(context_, Worker.class);
						
						intent.putExtra(All.SELECTED_PRODUCT,
								LIs[Integer.parseInt(
										ID.getText().toString()
								)]
								// passing the LI element of the clicked index.
						);
						
						context_.startActivity(intent);
						
					}
				});
	}
	
	@Override
	public void onBindViewHolder(SelectedCategoryRecyclerViewHolder recyclerViewHolder, int i) {
		
		Picasso.get().load(listData.get(i).getImageURL()).into(recyclerViewHolder.imageView);
		//setting image's URL
		
		recyclerViewHolder.textViewName.setText(listData.get(i).getName());
		//setting sentence case- Readable name of the category.
		
		recyclerViewHolder.textViewID.setText("" + i);
		// setting index as ID to use the data through it, when it will be clicked.
		
		recyclerViewHolder.textViewCategoryName.setText(listData.get(i).getCategory());
		// setting category name from category for matching-purpose-in-if-condition will be used, when clicked/tapped.
	}
	
	@Override
	public int getItemCount() {
		return listData.size();
	}
}

class SelectedCategoryRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
	public ImageView imageView;
	public TextView textViewName;
	public TextView textViewID;
	public TextView textViewCategoryName;
	public ViewHolderClicks mListener;
	
	public SelectedCategoryRecyclerViewHolder(View itemView, ViewHolderClicks listener) {
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
		mListener.onProductClick(textViewID, textViewCategoryName, textViewName, imageView);
	}
	
	public static interface ViewHolderClicks {
		void onProductClick(TextView ID, TextView textViewURL, TextView TVName, ImageView image);
	}
}
