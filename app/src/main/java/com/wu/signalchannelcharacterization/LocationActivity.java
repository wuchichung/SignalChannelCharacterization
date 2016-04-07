package com.wu.signalchannelcharacterization;

/**
 * Created by wu on 4/7/16.
 */

import android.app.Dialog;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class LocationActivity extends SingleFragmentActivity {
    private static final int REQUEST_ERROR = 0;
    private static final String TAG = "LocationActivity";

    @Override
    protected Fragment createFragment() {
        return LocationFragment.newInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int errorCode = googleAPI.isGooglePlayServicesAvailable(this);

        if(errorCode != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(errorCode)) {
                googleAPI.getErrorDialog(this, errorCode,
                        REQUEST_ERROR).show();
            }
        }else
        {
            Log.d(TAG,"Get Google Service Successfully");
        }
    }
}
