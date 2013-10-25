/**
 *  Plugin diagnostic
 *
 *  Copyright (c) 2012 AVANTIC ESTUDIO DE INGENIEROS
 *  
**/

package com.neton;

import org.json.JSONArray;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.app.Activity;
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
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) {
        Log.d(LOG_TAG, "Executing Diagnostic Plugin");

        boolean result;
        
        if ("isLocationEnabled".equals(action)){
            result = isLocationEnabled();

            if (result){
                callbackContext.success("true");
            } else {
                callbackContext.success("false");
            }
        }

        else if ("switchToLocationSettings".equals(action)) {
            switchToLocationSettings(data, callbackContext);
        } else if ("isGpsEnabled".equals(action)){
            result = isGpsEnabled();

            if (result){
                callbackContext.success("true");
            } else {
                callbackContext.success("false");
            }
        }
        else {
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
    public boolean isLocationEnabled() {
        boolean result = (isGpsEnabled());
        Log.d(LOG_TAG, "Location enabled: " + result);

        return result;
    }
    
    /**
     * Requests that the user enable the location in device settings.
     */
    public void switchToLocationSettings(JSONArray data, CallbackContext callbackContext) {
        Context ctx = this.cordova.getActivity().getApplicationContext();
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
    public boolean isGpsEnabled() {
        boolean result = isLocationProviderEnabled(LocationManager.GPS_PROVIDER);
        Log.d(LOG_TAG, "GPS enabled: " + result);

        return result;
    }


    private boolean isLocationProviderEnabled(String provider) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(provider);
    }

}