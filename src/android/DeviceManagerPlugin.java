// Import necessary Android libraries and Cordova classes
package com.symfast.devicemanager;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// Define the main class for the Cordova plugin
public class DeviceManagerPlugin extends CordovaPlugin {

    // Define constants and variables
    public static final String TAG = "DeviceManager";
    private static final int REQUEST_CODE_ENABLE_ADMIN = 1;
    private DevicePolicyManager devicePolicyManager;
    private ComponentName cordovaDeviceAdmin;
    private CallbackContext listener = null;
    private CallbackContext callbackContext;
    private PowerManager powerManager;
    private PowerManager.WakeLock wakeLock;

    // Initialize the plugin with the Cordova interface and web view
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        initializeComponents(cordova.getActivity());
    }

    // Initialize necessary components, including PowerManager, DevicePolicyManager, and ComponentName
    private void initializeComponents(Activity activity) {
        powerManager = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock((PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE), "TAG");
        devicePolicyManager = (DevicePolicyManager) activity.getSystemService(Context.DEVICE_POLICY_SERVICE);
        cordovaDeviceAdmin = new ComponentName(activity, DeviceManagerReceiver.class);
    }

    // Handle plugin execution based on the specified action
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("addAdmin")) {
            this.addAdmin(callbackContext);
            return true;
        } else if (action.equals("removeAdmin")) {
            this.removeAdmin(callbackContext);
            return true;
        } else if (action.equals("isAdmin")) {
            this.isAdmin(callbackContext);
            return true;
        } else if (action.equals("lockScreen")) {
            this.lockScreen(callbackContext);
            return true;
        } else if (action.equals("offScreen")) {
            this.offScreen(callbackContext);
            return true;
        } else if (action.equals("onScreen")) {
            this.onScreen(callbackContext);
            return true;
        } else if (action.equals("rootReboot")) {
            this.rootReboot(callbackContext);
            return true;
        } else if (action.equals("rootPowerOn")) {
            this.rootPowerOn(callbackContext);
            return true;
        } else if (action.equals("rootShutdown")) {
            this.rootShutdown(callbackContext);
            return true;
        }
        return false;
    }

    // Turn off the screen using DevicePolicyManager
    private void offScreen(final CallbackContext callbackContext) {
        devicePolicyManager.lockNow();
        callbackContext.success();        
    }

    // Turn on the screen and keep it on if specified
    private void onScreen(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Window window = cordova.getActivity().getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
                window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

                if (wakeLock.isHeld()) {
                    wakeLock.release();
                }

                wakeLock.acquire();

                Log.v(TAG, "Screen has been turned on.");
                callbackContext.success();
            }
        });
    }

    // Check if the app has DeviceAdmin privileges
    private void isAdmin(CallbackContext callbackContext) {
        boolean isAdmin = devicePolicyManager.isAdminActive(cordovaDeviceAdmin);
        PluginResult dataResult = new PluginResult(PluginResult.Status.OK, isAdmin);
        if (callbackContext != null) callbackContext.sendPluginResult(dataResult);
    }

    // Lock the screen using DevicePolicyManager
    private void lockScreen(CallbackContext callbackContext) {
        devicePolicyManager.lockNow();
        callbackContext.success();   
    }

    // Remove DeviceAdmin privileges
    private void removeAdmin(CallbackContext callbackContext) {
        devicePolicyManager.removeActiveAdmin(cordovaDeviceAdmin);
    }

    // Initiate the process of adding DeviceAdmin privileges
    private void addAdmin(CallbackContext callbackContext) {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cordovaDeviceAdmin);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "");

        cordova.setActivityResultCallback(this);
        cordova.getActivity().startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN);

        this.callbackContext = callbackContext;
    }

    // Power on the device using root privileges
    private void rootPowerOn(CallbackContext callbackContext) {
        try {
            Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "input keyevent 26"});
            proc.waitFor();
            callbackContext.success("Rebooting...");
        } catch (Exception ex) {
            Log.i(TAG, "Could not reboot", ex);
            callbackContext.error("Power failed.");
        }
    }

    // Reboot the device using root privileges
    private void rootReboot(CallbackContext callbackContext) {
        try {
            Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot"});
            proc.waitFor();
            callbackContext.success("Rebooting...");
        } catch (Exception ex) {
            Log.i(TAG, "Could not reboot", ex);
            callbackContext.error("Reboot failed.");
        }
    }

    // Shut down the device using root privileges
    private void rootShutdown(CallbackContext callbackContext) {
        try {
            Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot -p"});
            proc.waitFor();
            callbackContext.success("Rebooting...");
        } catch (Exception ex) {
            Log.i(TAG, "Could not reboot", ex);
            callbackContext.error("Shutdown failed.");
        }
    }
}
