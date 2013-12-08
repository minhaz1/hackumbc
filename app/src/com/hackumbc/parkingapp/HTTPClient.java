package com.hackumbc.parkingapp;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class HTTPClient extends AsyncTask{

	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		String data = (String)params[0];

		try{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet("http://parking.ngrok.com/getparking");

			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			data = EntityUtils.toString(httpEntity);
			//            Log.d("DEBUG", data);

		}
		catch(Exception e){
			e.printStackTrace();
			Log.d("DEBUG", "ERRORED OUT!");
		}
		

    	Intent intent = new Intent(MainActivity.getContext(), MainActivity.class);
    	final PendingIntent pIntent = PendingIntent.getActivity(MainActivity.getContext(), 0, intent, 0);

    	String[] stuff = data.split(" ");
    	int value = Integer.parseInt(stuff[0]);
    	int max = Integer.parseInt(stuff[1]);
    	
    	String notification = "Lot 1: ";
    	
    	if(((double)value / max) >= 1){
    		notification += value + " out of " + max + " occupied.";
    		notification += " All spots filled!!";
    	}
    	else{
    		notification += value + " out of " + max + " occupied.";
    	}
    	
		Notification n  = new Notification.Builder(MainActivity.getContext())
		.setContentTitle("ParkUMBC")
		.setContentText(notification)
		.setSmallIcon(R.drawable.ic_launcher)
		.setContentIntent(pIntent)
		.setAutoCancel(true).build();

		NotificationManager notificationManager = 
				(NotificationManager) MainActivity.getContext().getSystemService(Context.NOTIFICATION_SERVICE);

		notificationManager.notify(0, n); 

		return null;
	}





}
