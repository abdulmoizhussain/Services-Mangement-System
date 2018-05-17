package com.example.abdul.servicesmanagementsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.security.AccessController.getContext;

/**
 * Created by Abdul on 4/22/2018.
 *
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

	private List<Data> listData = new ArrayList<Data>();
	private Context context_;

	public RecyclerViewAdapter(List<Data> data_) {
		this.listData = data_;
	}

	@Override
	public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		LayoutInflater inflater = LayoutInflater.from(context_ = viewGroup.getContext());
		View itemView = inflater.inflate(R.layout.list_item_layout, viewGroup, false);

		return new RecyclerViewHolder(itemView,
				new RecyclerViewHolder.ViewHolderClicks() {

			@Override
			public void onPotato(TextView ID, final TextView textViewCategoryName, TextView name) {
//				Log.d("VEGETABLES", "Poh-tah-tos" + "int i==" + ID.getText().toString());

				String url = Global.URL_PRE + Global.FixMyHome+"/wp-json/wp/v2/pages?slug=products";

				final ProgressDialog progressDialog = new ProgressDialog(context_);
				progressDialog.setMessage("Please Wait");
				progressDialog.show();

				new RequestClass(
						new IResult() {
							@Override
							public void notifySuccess(String response) {

								String rendered = Home.getRendered(response);

								Intent intent = new Intent(context_, categorized_products.class);

								intent.putExtra(Global.CATEGORIZED_PRODUCTS, rendered);
								// passing the rendered string to next activity. i.e categorized_products.class

								intent.putExtra(Global.SELECTED_CATEGORY,
										textViewCategoryName.getText().toString());
								// passing the selected category to next activity

								progressDialog.dismiss();

								context_.startActivity(intent);
							}

							@Override
							public void notifyError(VolleyError error) {
								Log.v("notify-error:", error.getMessage());

								progressDialog.dismiss();
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

		recyclerViewHolder.textViewID.setText(""+i);
		// setting ID to use the data through it, when it will be clicked.

		recyclerViewHolder.textViewCategoryName.setText( listData.get(i).getCategory() );
		// setting category name from category for matching-purpose-in-if-condition will be used, when clicked/tapped.
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

		mListener.onPotato(textViewID, textViewCategoryName, textViewName);
	}

	public static interface ViewHolderClicks {
		void onPotato(TextView ID, TextView textViewURL, TextView TVName);
	}
}
