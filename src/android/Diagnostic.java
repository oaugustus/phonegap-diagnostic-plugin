/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/
package com.neton.cordova.diagnostic;

import java.util.TimeZone;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.LOG;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.location.LocationManager;
import android.location.LocationListener;

public class Diagnostic extends CordovaPlugin {
    public static final String TAG = "Diagnostic";

    /**
     * Constructor.
     */
    public Diagnostic() {
    }

    /**
     * Sets the context of the Command. This can then be used to do things like
     * get file paths associated with the Activity.
     *
     * @param cordova The context of the main Activity.
     * @param webView The CordovaWebView Cordova is running in.
     */
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArry of arguments for the plugin.
     * @param callbackContext   The callback id used when calling back into JavaScript.
     * @return                  True if the action was valid, false if not.
     */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
      JSONObject r = new JSONObject();

        if (action.equals("switchToLocationSettings")){
          switchToLocationSettings();
          callbackContext.success();            
        } else
        if (action.equals("isGpsEnabled")) {
            r.put("success", isGpsEnabled());
            callbackContext.success(r);
        }
        else {
            return false;
        }
        return true;
    }

    /**
     * Check device settings for GPS.
     * 
     * @returns {boolean} The status of GPS in device settings.
     */
    public boolean isGpsEnabled() {
            boolean result = isLocationProviderEnabled(LocationManager.GPS_PROVIDER);
            Log.d(TAG, "GPS enabled: " + result);
            return result;
    }    

    /**
     * Requests that the user enable the location in device settings.
     */
    public void switchToLocationSettings() {
          Context ctx = this.cordova.getActivity().getApplicationContext();      
          
          LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
          LocationListener locationListener = new MyLocationListener();  

          locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
    }

    private boolean isLocationProviderEnabled(String provider) {
        Context ctx = this.cordova.getActivity().getApplicationContext();      
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(provider);
    }


}
