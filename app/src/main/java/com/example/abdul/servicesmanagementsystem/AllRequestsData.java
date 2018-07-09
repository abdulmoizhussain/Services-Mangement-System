package com.example.abdul.servicesmanagementsystem;

/**
 * Created by Abdul on 4/22/2018.
 *
 */

public class AllRequestsData {
	private String orderID;
	private String orderStatus;
	private String orderWorkerName;
	private String orderTotal;
	private String orderTime;
	private String orderDate;
	private String orderDescription;
	
	public AllRequestsData(String orderID, String orderStatus, String orderWorkerName, String orderTotal, String orderTime, String orderDate, String orderDescription) {
		this.orderID = orderID;
		this.orderStatus = orderStatus;
		this.orderWorkerName = orderWorkerName;
		this.orderTotal = orderTotal;
		this.orderTime = orderTime;
		this.orderDate = orderDate;
		this.orderDescription = orderDescription;
	}
	
	public String getOrderID() {
		return orderID;
	}
	
	public String getOrderStatus() {
		return orderStatus;
	}
	
	public String getOrderWorkerName() {
		return orderWorkerName;
	}
	
	public String getOrderTotal() {
		return orderTotal;
	}
	
	public String getOrderTime() {
		return orderTime;
	}
	
	public String getOrderDate() {
		return orderDate;
	}
	
	public String getOrderDescription() {
		return orderDescription;
	}
}
