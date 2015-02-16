import org.apache.cordova.core.*;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.parse.ParsePushBroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PushReceiver extends ParsePushBroadcastReceiver {
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
                if (key.equals("id")) {
                    Log.d(TAG, "id");
                    ParsePlugin.key = json.getString(key);
                }
                Intent i = new Intent(context, YOUR_CORDOVA_ACTIVITY.class);
                i.putExtras(intent.getExtras());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                Log.d(TAG, "..." + key + " => " + json.getString(key));
            }
        } catch (JSONException e) {
            Log.d(TAG, "JSONException: " + e.getMessage());
        }
    }
}
