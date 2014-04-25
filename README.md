Working on updating the readme
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

Usage iOS
-----

#### Add this to your AppDelegates didFinishLaunchingWithOptions
```
[application registerForRemoteNotificationTypes:UIRemoteNotificationTypeBadge|
     UIRemoteNotificationTypeAlert|
     UIRemoteNotificationTypeSound];
    
    [Parse setApplicationId:@"Your Application ID"
                  clientKey:@"Your Client Key"];
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

```

##### You can change the key from url under CDVParsePlugin.m
```
- (void)handleBackgroundNotification:(NSDictionary *)notification
{
    if ([notification objectForKey:@"url"])
    {
        // do something with job id
        storyURL = [notification objectForKey:@"url"];
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
