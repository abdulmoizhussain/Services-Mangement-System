package com.example.abdul.servicesmanagementsystem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Abdul on 3/24/2018.
 */

public class All {
	public static String LOCALHOST_PHY = "192.168.1.100";
	public static String LOCALHOST_VIR = "10.0.2.2";
	public static String LOCALHOST;
	public static String URL_PRE = "http://" + All.LOCALHOST;
	public static String URL_AUTH = "http://" + All.LOCALHOST+"/fixmyhome/wp-json/jwt-auth/v1/token";
	public static String WP_JSON_URL = "http://" + All.LOCALHOST+"/fixmyhome/wp-json/wp/v2";
	public static String WC_JSON_URL = "http://" + All.LOCALHOST+"/fixmyhome/wp-json/wc/v2";
	public static String URL_PAGES = "/fixmyhome/wp-json/wp/v2/pages";
	public static String SERVICES_STRING = "SERVICES_STRING";
	public static String TOKEN = "TOKEN";
	static String LOADING_MSG = "Please Wait";
	static String BASIC_TOKEN = "BASIC_TOKEN";
	static String CATEGORIZED_PRODUCTS = "CATEGORIZED_PRODUCTS";
	static String SELECTED_CATEGORY_NICE_NAME = "SELECTED_CATEGORY_NICE_NAME";
	static String SELECTED_CATEGORY = "SELECTED_CATEGORY";
	static String SELECTED_PRODUCT = "SELECTED_PRODUCT";
	static String SELECTED_CATEGORY_LIST = "SELECTED_CATEGORY_LIST";
	static String WORKER_ID = "WORKER_ID";
	static String WORKER_NAME = "WORKER_NAME";
	static String USER_PIC = "USER_PIC";
	static String USER_NAME = "USER_NAME";
	static String USER_ID = "USER_ID";
	static String USER_EMAIL = "USER_EMAIL";
	static String DELIMITER = "DELIMITER";
	
	public static List<String> getStringList(String sourceSTR, String startOfSearch, String endOfSearch) {
		List<String> stringList = new ArrayList<>();
		if (sourceSTR == null || sourceSTR.isEmpty()) {
			stringList.add("");
			return stringList;
		}
		
		Pattern p = Pattern.compile(startOfSearch);
		Matcher m = p.matcher(sourceSTR);
		while (m.find()) {
//			String temp = sourceSTR.substring( isFromStart? m.start(): m.end() );
			String temp = sourceSTR.substring(m.end());
			
			Pattern pp = Pattern.compile(endOfSearch);
			Matcher mm = pp.matcher(temp);
			
			String temp2 = "";
			
			if (mm.find()) {
				temp2 = temp.substring(0,
//						isTillEnd? mm.end() : mm.start()
						mm.start()
				);
			}
			stringList.add(temp2);
			
			if (stringList.size() < 1) { // if no match was found.
				stringList.add("");
			}
		}
		return stringList;
	}
	
	public static void setSharedSTR(String key, String value, Context context) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value).apply();
	}
	
	public static String getSharedSTR(String key, Context context) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//		Toast.makeText(context,sharedPreferences.getStringList(All.TOKEN, ""),
//				Toast.LENGTH_SHORT).show();
		return sharedPreferences.getString(key, "");
	}
	
	public static void handleVolleyError(Context context, VolleyError error) {
		error.printStackTrace();
		
		if (error instanceof NoConnectionError) {
			Toast.makeText(context, "No Connection Found !", Toast.LENGTH_SHORT).show();
		}
		else if (error instanceof NetworkError) {
			Toast.makeText(context, "Network Error !", Toast.LENGTH_SHORT).show();
		}
		else if (error instanceof ServerError) {
			Toast.makeText(context, "Server Error !", Toast.LENGTH_SHORT).show();
		}
		else if (error instanceof AuthFailureError) {
			Toast.makeText(context, "Authentication Failure !", Toast.LENGTH_SHORT).show();
		}
		else if (error instanceof TimeoutError) {
			Toast.makeText(context, "Timeout !", Toast.LENGTH_LONG).show();
		}
		else if (error instanceof ParseError) {
			Toast.makeText(context, "Parse Error !", Toast.LENGTH_SHORT).show();
		}
	}
	
	public static void handleNormalResponse (Context context, VolleyError error) {
		if (error.networkResponse == null) {
			return;
		}

		try {
			String response = new String(error.networkResponse.data, "UTF8");
			String msg = All.getStringList(response, "<h1>", "</h1>").get(0);
			Toast.makeText(context, Integer.toString(
					error.networkResponse.statusCode
			) + "\n" + msg, Toast.LENGTH_LONG).show();
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		}
		
	}
	
	public static void setLOCALHOST(boolean isVirtual) {
		LOCALHOST = isVirtual? LOCALHOST_VIR: LOCALHOST_PHY;
	}
	
	public static void changeLocalhost(Context context) {
		final EditText input = new EditText(context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		input.setLayoutParams(lp);
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setView(input);
		
		alertDialog.setTitle("New Localhost IP");
		alertDialog.setMessage("");
		
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Change",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						All.LOCALHOST = input.getText().toString().trim();
						dialog.dismiss();
					}
				});
		alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		alertDialog.show();
	}
	
	public static boolean checkLoginStatus(Context context) {
		String Token = All.getSharedSTR(All.TOKEN, context);
		String Username = All.getSharedSTR(All.USER_NAME, context);
		
		if (Token.isEmpty() || Username.isEmpty()) {
			Toast.makeText(context, "Session not found, please Login first.", Toast.LENGTH_LONG).show();
			return false;
		}
		else
			return true;
	}
}