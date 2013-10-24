/**
 *  Plugin diagnostic
 *
 *  Copyright (c) 2012 AVANTIC ESTUDIO DE INGENIEROS
 *  
**/

package com.neton;

import org.apache.cordova.api.PluginResult.Status;
import org.json.JSONArray;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import org.apache.cordova.*;
import org.apache.cordova.api.*;  // for Cordova 2.9

public class Diagnostic extends CordovaPlugin {

    private static final String LOG_TAG = "Diagnostic";

    
    @Override
    public boolean execute(String action, JSONArray args, String callbackId) {
        Log.d(LOG_TAG, "Executing Diagnostic Plugin");

        
        if ("isLocationEnabled".equals(action))
            isLocationEnabled(data, callbackContext);
        else if ("switchToLocationSettings".equals(action)) {
            switchToLocationSettings(data, callbackContext);
        } else if ("isGpsEnabled".equals(action))
            isGpsEnabled(data, callbackContext);
        else if ("isWirelessNetworkLocationEnabled".equals(action))
            isWirelessNetworkLocationEnabled(data, callbackContext);
        else if ("isWifiEnabled".equals(action))
            isWifiEnabled(data, callbackContext);
        else if ("switchToWifiSettings".equals(action)) {
            switchToWifiSettings(data, callbackContext);
        } else if ("isBluetoothEnabled".equals(action))
            isBluetoothEnabled(data, callbackContext);
        else if ("switchToBluetoothSettings".equals(action)) {
            switchToBluetoothSettings(data, callbackContext);
        } else {
            Log.d(LOG_TAG, "Invalid action");
            callbackContext.error("Invalid action");

            return false;
        }

        return true;
    }

    
    /**
     * Check device settings for location.
     * 
     * @returns {boolean} The status of location services in device settings.
     */
    public void isLocationEnabled(JSONArray data, CallbackContext callbackContext) throws JSONException {
        boolean result = (isGpsEnabled() || isWirelessNetworkLocationEnabled());
        Log.d(LOG_TAG, "Location enabled: " + result);

        if (result)
            callbackContext.success("true");
        else
            callbackContext.success("false");
    }
    
    /**
     * Requests that the user enable the location in device settings.
     */
    public void switchToLocationSettings(JSONArray data, CallbackContext callbackContext) throws JSONException {
        Log.d(LOG_TAG, "Switch to Location Settings");
        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        ctx.startActivity(settingsIntent);
        callbackContext.success();
    }
    
    /**
     * Check device settings for GPS.
     * 
     * @returns {boolean} The status of GPS in device settings.
     */
    public void isGpsEnabled(JSONArray data, CallbackContext callbackContext) throws JSONException {
        boolean result = isLocationProviderEnabled(LocationManager.GPS_PROVIDER);
        Log.d(LOG_TAG, "GPS enabled: " + result);
        if (result)
            callbackContext.success("true");
        else
            callbackContext.success("false");

    }

    /**
     * Check device settings for wireless network location (Wi-Fi and/or mobile
     * networks).
     * 
     * @returns {boolean} The status of wireless network location in device
     *          settings.
     */
    public void isWirelessNetworkLocationEnabled(JSONArray data, CallbackContext callbackContext) throws JSONException {
        boolean result = isLocationProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Log.d(LOG_TAG, "Wireless Network Location enabled: " + result);

        if (result)
            callbackContext.success("true");
        else
            callbackContext.success("false");

    }

    private void isLocationProviderEnabled(JSONArray data, CallbackContext callbackContext) throws JSONException {
        //LocationManager locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        //return locationManager.isProviderEnabled(provider);
        callbackContext.success();
    }

    /**
     * Check device settings for Wi-Fi.
     * 
     * @returns {boolean} The status of Wi-Fi in device settings.
     */
    public void isWifiEnabled(JSONArray data, CallbackContext callbackContext) throws JSONException {
        WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        boolean result = wifiManager.isWifiEnabled();
        Log.d(LOG_TAG, "Wifi enabled: " + result);

        if (result)
            callbackContext.success("true");
        else
            callbackContext.success("false");

    }
    
    /**
     * Requests that the user enable the Wi-Fi in device settings.
     */
    public void switchToWifiSettings(JSONArray data, CallbackContext callbackContext) throws JSONException {
        Log.d(LOG_TAG, "Switch to Wifi Settings");
        Intent settingsIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        ctx.startActivity(settingsIntent);
        callbackContext.success();
    }
    
    /**
     * Check device settings for Bluetooth.
     * 
     * @returns {boolean} The status of Bluetooth in device settings.
     */
    public void isBluetoothEnabled(JSONArray data, CallbackContext callbackContext) throws JSONException {
        Looper.prepare();
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean result = bluetoothAdapter.isEnabled();
        Log.d(LOG_TAG, "Bluetooth enabled: " + result);

        if (result)
            callbackContext.success("true");
        else
            callbackContext.success("false");

    }
    
    /**
     * Requests that the user enable the Bluetooth in device settings.
     */
    public void switchToBluetoothSettings(JSONArray data, CallbackContext callbackContext) throws JSONException {
        Log.d(LOG_TAG, "Switch to Bluetooth Settings");
        Intent settingsIntent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        ctx.startActivity(settingsIntent);
        callbackContext.success();
    }
}