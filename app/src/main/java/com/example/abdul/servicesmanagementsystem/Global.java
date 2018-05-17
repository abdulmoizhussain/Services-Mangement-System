package com.example.abdul.servicesmanagementsystem;

/**
 * Created by Abdul on 3/24/2018.
 *
 */

public class Global {

	public static String LOCALHOST_PHY = "192.168.0.104";
	public static String URL_PRE_PHY = "http://192.168.0.104";

	public static String LOCALHOST_VIR = "10.0.2.2";
	public static String URL_PRE_VIR = "http://10.0.2.2";

	public static String LOCALHOST = Global.LOCALHOST_PHY;

	public static String URL_PRE = "http://"+ Global.LOCALHOST;

	public static String FixMyHome = "/FixMyHome";

	public static String URL_AUTH = "/fixmyhome/wp-json/jwt-auth/v1/token";
	public static String URL_POSTS = "/fixmyhome/wp-json/wp/v2/posts";
	public static String URL_PAGES = "/fixmyhome/wp-json/wp/v2/pages";
	public static String TOKEN = "TOKEN";
	public static String SERVICES_STRING = "SERVICES_STRING";
	static String CATEGORIZED_PRODUCTS = "CATEGORIZED_PRODUCTS";
	static String SELECTED_CATEGORY = "SELECTED_CATEGORY";
}