package com.example.abdul.servicesmanagementsystem;

/**
 * Created by Abdul on 3/24/2018.
 *
 */

public class Global {

	private String TOKEN;
	public static String URL_PRE_PHY = "http://192.168.0.101";
	public static String URL_PRE_VIR = "http://10.0.2.2";
	public static String URL_AUTH_POST = "/fixmyhome/wp-json/jwt-auth/v1/token";

	public void setTOKEN(String TOKEN) {
		this.TOKEN = TOKEN;
	}

	public String getTOKEN() {
		return TOKEN;
	}
}
