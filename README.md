Working on updating
------------

Phonegap Parse.com Plugin
=========================

Cordova/Phonegap 3.0.0 plugin for Parse.com push service

Using [Parse.com's](http://parse.com) REST API for push requires the installation id, which isn't available in JS

This plugin exposes the four native Android API push services to JS:
* <a href="https://www.parse.com/docs/android/api/com/parse/ParseInstallation.html#getInstallationId()">getInstallationId</a>
* <a href="https://www.parse.com/docs/android/api/com/parse/PushService.html#getSubscriptions(android.content.Context)">getSubscriptions</a>
* <a href="https://www.parse.com/docs/android/api/com/parse/PushService.html#subscribe(android.content.Context, java.lang.String, java.lang.Class, int)">subscribe</a>
* <a href="https://www.parse.com/docs/android/api/com/parse/PushService.html#unsubscribe(android.content.Context, java.lang.String)">unsubscribe</a>

Installation
------------
cordova plugin add https://github.com/nickh364/phonegap-parse-plugin.git

phonegap local plugin add https://github.com/nickh364/phonegap-parse-plugin.git

Usage Android
-----

#### Add this import to your activity
```
import com.parse.Parse;
```
#### Add this under onCreate()
```
Parse.initialize(this, "Your Application ID", "Your Client Key");
```

#### Use this to get custom data like a story url from your notification
<hr />

##### Add this to your apps manifest and change com.example to your package name
```
<receiver android:name="com.example.PushReceiver" android:exported="false">
  <intent-filter>
    <action android:name="com.example.push" />
  </intent-filter>
</receiver>
```
##### Drag PushReceiver.java from org.apache.cordova.core to your package

##### You can change the key from something other than url under PushReceiver.java
```
if(key.equals("url")){
	ParsePlugin.key = json.getString(key);
}
```
<hr/>
Usage iOS
-----

#### Add this to your AppDelegates didFinishLaunchingWithOptions
```
    #if __IPHONE_OS_VERSION_MAX_ALLOWED >= 80000
    if ([application respondsToSelector:@selector(registerUserNotificationSettings:)]) {
        UIUserNotificationType userNotificationTypes = (UIUserNotificationTypeAlert |
                                                        UIUserNotificationTypeBadge |
                                                        UIUserNotificationTypeSound);
        UIUserNotificationSettings *settings = [UIUserNotificationSettings settingsForTypes:userNotificationTypes
                                                                                 categories:nil];
        [application registerUserNotificationSettings:settings];
        [application registerForRemoteNotifications];
    } else
    #endif
    {
        [application registerForRemoteNotificationTypes:(UIRemoteNotificationTypeBadge |
                                                         UIRemoteNotificationTypeAlert |
                                                         UIRemoteNotificationTypeSound)];
    }
    
    [Parse setApplicationId:@"Your Application ID"
                  clientKey:@"Your Client Key"];
```
#### Add this to your appdelegate.m
```
- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo
{
    if (application.applicationState == UIApplicationStateInactive) {
        // The application was just brought from the background to the foreground,
        // so we consider the app as having been "opened by a push notification."
        [PFAnalytics trackAppOpenedWithRemoteNotificationPayload:userInfo];
        NSDictionary *notificationPayload = userInfo;
        CDVParsePlugin *parsePlugin = [[CDVParsePlugin alloc] init];
        [parsePlugin handleBackgroundNotification:notificationPayload];
        
    }

}
```
#### Use this to get custom data like a story url from your notification
<hr />

##### Add this import to your AppDelegate
```
#import "CDVParsePlugin.h"
```
##### Add this to your AppDelegates didFinishLaunchingWithOptions
```
 NSDictionary *notificationPayload = launchOptions[UIApplicationLaunchOptionsRemoteNotificationKey];
CDVParsePlugin *parsePlugin = [[CDVParsePlugin alloc] init];
[parsePlugin handleBackgroundNotification:notificationPayload];

if (application.applicationState != UIApplicationStateBackground) {
	// Track an app open here if we launch with a push, unless
	// "content_available" was used to trigger a background push (introduced
	// in iOS 7). In that case, we skip tracking here to avoid double
	// counting the app-open.
	BOOL preBackgroundPush = ![application respondsToSelector:@selector(backgroundRefreshStatus)];
	BOOL oldPushHandlerOnly = ![self respondsToSelector:@selector(application:didReceiveRemoteNotification:fetchCompletionHandler:)];
	BOOL noPushPayload = ![launchOptions objectForKey:UIApplicationLaunchOptionsRemoteNotificationKey];
	if (preBackgroundPush || oldPushHandlerOnly || noPushPayload) {
	    [PFAnalytics trackAppOpenedWithLaunchOptions:launchOptions];
	}
}

```

##### You can change the key from something other than url under CDVParsePlugin.m
```
- (void)handleBackgroundNotification:(NSDictionary *)notification
{
    if ([notification objectForKey:@"id"])
    {
        // do something with job id
        storyURL = [notification objectForKey:@"id"];
    }
}
```
##### Notification JSON with custom data
```
{
    "aps": {
         "badge": 1,
         "alert": "Hello world!",
         "sound": "cat.caf"
    },
    "url": http://example.com
}
```
<hr />
#### iOS Frameworks used 

- AudioToolbox.framework
- CFNetwork.framework
- CoreGraphics.framework
- CoreLocation.framework
- libz.1.1.3.dylib
- MobileCoreServices.framework
- QuartzCore.framework
- Security.framework
- StoreKit.framework
- SystemConfiguration.framework
- src/ios/Frameworks/Parse.framework

Javascript Functions
-----
```
<script type="text/javascript>
	var parsePlugin =  = window.parsePlugin;
	
	parsePlugin.getInstallationId(function(id) {
		alert(id);
	}, function(e) {
		alert('error');
	});
	
	parsePlugin.getSubscriptions(function(subscriptions) {
		alert(subscriptions);
	}, function(e) {
		alert('error');
	});
	
	parsePlugin.subscribe('SampleChannel', function() {
		alert('OK');
	}, function(e) {
		alert('error');
	});
	
	parsePlugin.unsubscribe('SampleChannel', function(msg) {
		alert('OK');
	}, function(e) {
		alert('error');
	});
	// I am using this to get a url from the notification
	parsePlugin.getNotification(function(url) {
		alert(url);
	}, function(e) {
		alert('error');
	});
</script>
```

Compatibility
-------------
Phonegap/cordova > 3.0.0
