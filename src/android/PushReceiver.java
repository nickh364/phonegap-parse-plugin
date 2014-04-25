package com.onenewsnow.app;
import org.apache.cordova.core.*;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PushReceiver extends BroadcastReceiver {
	private static final String TAG = "PushReceiver";
	 
	  @Override
	  public void onReceive(Context context, Intent intent) {
	    try {
	      String action = intent.getAction();
	      String channel = intent.getExtras().getString("com.parse.Channel");
	      JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
	 
	      Log.d(TAG, "got action " + action + " on channel " + channel + " with:");
	      Iterator itr = json.keys();
	      while (itr.hasNext()) {
	        String key = (String) itr.next();
	        if(key.equals("url")){
	        	Log.d(TAG, "url");
	        	ParsePlugin.url = json.getString(key);
	        }
	        Log.d(TAG, "..." + key + " => " + json.getString(key));
	      }
	    } catch (JSONException e) {
	      Log.d(TAG, "JSONException: " + e.getMessage());
	    }
	  }
	}