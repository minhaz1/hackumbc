package com.hackumbc.parkingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
	private static MainActivity instance;

	public static MainActivity getInstance() {
		return instance;
	}

	public static Context getContext(){
		return instance;
		// or return instance.getApplicationContext();
	}

	private WebView webView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		instance = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		webView = (WebView) findViewById(R.id.webView1);
		webView.setWebViewClient(new MyWebViewClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://parking.ngrok.com");

		doStuff();

	}


	public void doStuff(){
		// prepare intent which is triggered if the
		// notification is selected

		// build notification 
		// the addAction re-use the same intent to keep the example short
		final String data = new String();

		new Thread(){ public void run(){ new HTTPClient().execute(data);}}.run();

		//		Log.d("DEBUG", data);

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	private class MyWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    	view.loadUrl(url);
	        return true;
	    }
	}
}
