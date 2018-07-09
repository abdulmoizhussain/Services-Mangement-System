package com.example.abdul.servicesmanagementsystem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RVAAllRequests extends RecyclerView.Adapter<RecyclerViewHolderAllRequest> {
	private List<AllRequestsData> allRequestsData = new ArrayList<AllRequestsData>();
	
	public RVAAllRequests(List<AllRequestsData> allRequestsData) {
		this.allRequestsData = allRequestsData;
	}
	
	@Override
	public RecyclerViewHolderAllRequest onCreateViewHolder(ViewGroup viewGroup, int i) {
		
		LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
		View itemView = inflater.inflate(R.layout.all_requests_layout, viewGroup, false);
		
		return new RecyclerViewHolderAllRequest(itemView,
				new RecyclerViewHolderAllRequest.ViewHolderClicks() {

					@Override
					public void onCategoryClick(TextView ID, final TextView textViewCategoryName, final TextView name) {
					
					}
				});
	}
	
	@Override
	public void onBindViewHolder(RecyclerViewHolderAllRequest recyclerViewHolderAllRequest, int i) {
		
		recyclerViewHolderAllRequest.tvID.setText("Request ID: "+allRequestsData.get(i).getOrderID());
		recyclerViewHolderAllRequest.tvDate.setText("Date: "+allRequestsData.get(i).getOrderDate());
		recyclerViewHolderAllRequest.tvDescription.setText("Details: "+allRequestsData.get(i).getOrderDescription());
		recyclerViewHolderAllRequest.tvStatus.setText("Status: "+allRequestsData.get(i).getOrderStatus());
		recyclerViewHolderAllRequest.tvTime.setText("Time: "+allRequestsData.get(i).getOrderTime());
		recyclerViewHolderAllRequest.tvTotal.setText("Total Amount: "+allRequestsData.get(i).getOrderTotal());
		recyclerViewHolderAllRequest.tvWorkerName.setText("Provider: "+allRequestsData.get(i).getOrderWorkerName());
	}
	
	@Override
	public int getItemCount() {
		return allRequestsData.size();
	}
}

class RecyclerViewHolderAllRequest extends RecyclerView.ViewHolder implements View.OnClickListener {
	
	public TextView tvID;
	public TextView tvStatus;
	public TextView tvWorkerName;
	public TextView tvTotal;
	public TextView tvDate;
	public TextView tvTime;
	public TextView tvDescription;
	
	public ViewHolderClicks mListener;
	
	public RecyclerViewHolderAllRequest(View itemView, ViewHolderClicks listener) {
		super(itemView);
		this.mListener = listener;
		
		tvID = itemView.findViewById(R.id.all_requests_id);
		tvStatus = itemView.findViewById(R.id.all_requests_status);
		tvWorkerName = itemView.findViewById(R.id.all_requests_worker_name);
		tvTotal = itemView.findViewById(R.id.all_requests_total);
		tvDate = itemView.findViewById(R.id.all_requests_date);
		tvTime = itemView.findViewById(R.id.all_requests_time);
		tvDescription = itemView.findViewById(R.id.all_requests_details);
		
		itemView.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		mListener.onCategoryClick(tvStatus, tvWorkerName, tvID);
	}
	
	public static interface ViewHolderClicks {
		void onCategoryClick(TextView ID, TextView textViewURL, TextView TVName);
	}
}