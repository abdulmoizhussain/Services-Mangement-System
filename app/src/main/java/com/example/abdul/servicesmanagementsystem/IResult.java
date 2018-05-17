package com.example.abdul.servicesmanagementsystem;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by Abdul on 3/28/2018.
 *
 */

public interface IResult {
	public void notifySuccess( String response);
	public void notifyError( VolleyError error);
}
