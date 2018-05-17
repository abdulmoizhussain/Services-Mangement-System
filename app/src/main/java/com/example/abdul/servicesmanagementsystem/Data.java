package com.example.abdul.servicesmanagementsystem;

/**
 * Created by Abdul on 4/22/2018.
 *
 */

public class Data {
	private String imageUrl;
	private String name;
	private String category;

//	public Data() {
//	}

	public Data(String imageData, String name_) {
		this.imageUrl = imageData;
		this.name = name_;
	}

	public Data(String imageURL, String name_, String category_) {
		this.imageUrl = imageURL;
		this.name = name_;
		this.category = category_;
	}

	public String getImageURL() {
		return imageUrl;
	}

	public String getName() {
		return name;
	}

	public String getCategory() { return category; }

}
