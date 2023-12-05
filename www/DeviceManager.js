// Importing the 'cordova/exec' module, which is typically used for executing native code from JavaScript
var exec = require('cordova/exec');

// Defining the plugin name as a constant
var PLUGIN_NAME = 'DeviceManager';

// Creating an object to encapsulate the functionality of the DeviceManager plugin
var DeviceManager = {
    // Function to add an admin
    addAdmin: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, PLUGIN_NAME, 'addAdmin', []);
    },

    // Function to lock the screen
    lockScreen: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, PLUGIN_NAME, 'lockScreen', []);
    },

    // Function to remove an admin
    removeAdmin: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, PLUGIN_NAME, 'removeAdmin', []);
    },

    // Function to check if the current user is an admin
    isAdmin: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, PLUGIN_NAME, 'isAdmin', []);
    },

    // Function to turn on the screen
    onScreen: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, PLUGIN_NAME, 'onScreen', []);
    },

    // Function to turn off the screen
    offScreen: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, PLUGIN_NAME, 'offScreen', []);
    },

    // Function to reboot the device with root access
    rootReboot: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, PLUGIN_NAME, 'rootReboot', []);
    },

    // Function to power on/off the device with root access
    rootPowerOn: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, PLUGIN_NAME, 'rootPowerOn', []);
    },

    // Function to shut down the device with root access
    rootShutdown: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, PLUGIN_NAME, 'rootShutdown', []);
    },
}; 

// Exporting the DeviceManager object to make it accessible to other modules
module.exports = DeviceManager;
