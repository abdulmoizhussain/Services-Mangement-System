package com.example.abdul.servicesmanagementsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Base64;
import org.apache.commons.codec.binary.Base64;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.HmacSha1MessageSigner;

public class MainActivity extends AppCompatActivity {

	//String url = "http://192.168.1.110/wp-json/wp/v2/posts?filter[posts_per_page]=10&fields=id,title";


	private final String HMAC_SHA1 = "HmacSHA1";
	private final String CONSUMER_KEY = "ck_ff6976252c41d4fb2a080853f5fe1ce04763fdf2";
	private final String CONSUMER_SECRET = "cs_4a20f83cd0aba4082864cb12d668683fafcef97c";
	private final String UTF8 = "UTF-8";

	Base64 base64 = new Base64();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onLoginButton (View v) {
		startActivity(new Intent(this, login.class));
	}

	public void onHome(View v) {
		startActivity(new Intent(this, Home.class));
	}

	public void onDraw(View v) {
		startActivity(new Intent(this, Main2Activity.class));
	}
}
