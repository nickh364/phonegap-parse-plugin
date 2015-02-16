package org.apache.cordova.core;

import java.util.List;
import java.util.Locale;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.parse.ConfigCallback;
import com.parse.ParseConfig;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseException;

public class ParsePlugin extends CordovaPlugin {
	public static final String ACTION_GET_INSTALLATION_ID = "getInstallationId";
	public static final String ACTION_GET_INSTALLATION_OBJECT_ID = "getInstallationObjectId";
	public static final String ACTION_GET_SUBSCRIPTIONS = "getSubscriptions";
	public static final String ACTION_SUBSCRIBE = "subscribe";
    public static final String ACTION_UNSUBSCRIBE = "unsubscribe";
	public static final String ACTION_GET_Notification = "getNotification";
	public static final String ACTION_GET_Config = "getConfig";
	public static final String ACTION_GET_Config_Item = "getConfigItem";
	public static final String ACTION_GET_Config_Platform_Item = "getConfigPlatformItem";
	public static String key;
	public static ParseConfig conf;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    	Log.d("plugin", "in parse plugin");
    	Log.d("plugin", "action - " + action);
        if (action.equals(ACTION_GET_INSTALLATION_ID)) {
            this.getInstallationId(callbackContext);
            return true;
        }

        if (action.equals(ACTION_GET_INSTALLATION_OBJECT_ID)) {
            this.getInstallationObjectId(callbackContext);
            return true;
        }
        if (action.equals(ACTION_GET_SUBSCRIPTIONS)) {
            this.getSubscriptions(callbackContext);
            return true;
        }
        if (action.equals(ACTION_SUBSCRIBE)) {
            this.subscribe(args.getString(0), callbackContext);
            return true;
        }
        if (action.equals(ACTION_UNSUBSCRIBE)) {
            this.unsubscribe(args.getString(0), callbackContext);
            return true;
        }
		if (action.equals(ACTION_GET_Notification)) {
            this.getNotification(callbackContext);
            return true;
        }
		if (action.equals(ACTION_GET_Config)) {
			Log.d("plugin", "config");
            this.getConfig(callbackContext);
            return true;
        }
		if (action.equals(ACTION_GET_Config_Item)) {
            this.getConfigItem(args.getString(0), args.getString(1), callbackContext);
            return true;
        }
		if (action.equals(ACTION_GET_Config_Platform_Item)) {
            this.getConfigPlatformItem(args.getString(0), args.getString(1), callbackContext);
            return true;
        }
        return false;
    }

    private void getInstallationId(final CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                String installationId = ParseInstallation.getCurrentInstallation().getInstallationId();
                callbackContext.success(installationId);
            }
        });
    }

    private void getInstallationObjectId(final CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                String objectId = ParseInstallation.getCurrentInstallation().getObjectId();
                callbackContext.success(objectId);
            }
        });
    }

    private void getSubscriptions(final CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
            	List<String> subscriptions = ParseInstallation.getCurrentInstallation().getList("channels");
                 callbackContext.success(subscriptions.toString());
            }
        });
    }

    private void subscribe(final String channel, final CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
            	ParsePush.subscribeInBackground(channel);
                callbackContext.success();
            }
        });
    }

    private void unsubscribe(final String channel, final CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
            	ParsePush.unsubscribeInBackground(channel);
                callbackContext.success();
            }
        });
    }
	private void getNotification(final CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                callbackContext.success(key);
                key = null;
            }
        });
    }
	private void getConfig(final CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
            	Log.d("plugin", "in config");
            	ParseConfig.getInBackground(new ConfigCallback() {
            	  @Override
            	  public void done(ParseConfig config, ParseException e) {
            	    if (e == null) {
            	    	Log.d("TAG", "Yay! Config was fetched from the server.");
            	    	conf = config;
            	    } else {
            	    	Log.e("TAG", "Failed to fetch. Using Cached Config.");
            	      	config = ParseConfig.getCurrentConfig();
            	      	conf = config;
            	    }
            	    callbackContext.success();
            	  }
            	});
                
            }
        });
    }
	private void getConfigItem(final String configItemName, final String configDefault, final CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
            	
            	callbackContext.success(conf.getString(configItemName, configDefault));
            	
            }    
            
        });
    }
	private void getConfigPlatformItem(final String configItemName, final String configDefault, final CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
            	Log.d("plugin", "arg1 " + configItemName + " arg2 " + configDefault);
            	if (getPlatform() == 1){
            		callbackContext.success(conf.getString("Android_" + configItemName, configDefault));
            	}else{
            		callbackContext.success(conf.getString("FireOS_" + configItemName, configDefault));
            	}
            	
            }    
            
        });
    }
    public static int getPlatform(){
        if(android.os.Build.BRAND.toLowerCase(Locale.getDefault()).contains("blackberry")){
            return 3;
        }else if(android.os.Build.MODEL.toLowerCase(Locale.getDefault()).contains("kindle")){
            return 2;
        }else{
            return 1;
        }           
    }
}
