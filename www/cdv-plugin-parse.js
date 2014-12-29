var parsePlugin = {
    initialize: function(appId, clientKey, successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            'ParsePlugin',
            'initialize',
            [appId, clientKey]
        );
    },

    getInstallationId: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            'ParsePlugin',
            'getInstallationId',
            []
        );
    },

    getInstallationObjectId: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            'ParsePlugin',
            'getInstallationObjectId',
            []
        );
    },

    getSubscriptions: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            'ParsePlugin',
            'getSubscriptions',
            []
        );
    },

    subscribe: function(channel, successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            'ParsePlugin',
            'subscribe',
            [ channel ]
        );
    },

    unsubscribe: function(channel, successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            'ParsePlugin',
            'unsubscribe',
            [ channel ]
        );
    },
	getNotification: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            'ParsePlugin',
            'getNotification',
            []
        );
    },
	getConfig: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            'ParsePlugin',
            'getConfig',
            []
        );
    },
	getConfigItem: function(configItemName, defaultItem, callback) {
        cordova.exec(
            function(result) {
            	callback(result);
			},
			function(error) {
				callback(error);
			},
            'ParsePlugin',
            'getConfigItem',
            [configItemName, defaultItem]
        );
    },
	getConfigPlatformItem: function(configItemName, defaultItem, callback) {
        cordova.exec(
            function(result) {
            	callback(result);
			},
			function(error) {
				callback(error);
			},
            'ParsePlugin',
            'getConfigPlatformItem',
            [configItemName, defaultItem]
        );
    }

};
module.exports = parsePlugin;