package com.wu.signalchannelcharacterization;

/**
 * Created by wu on 4/7/16.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class LocationActivity extends SingleFragmentActivity {
    private static final int REQUEST_ERROR = 0;
    private static final String TAG = "LocationActivity";
    private static final String GET_PCI =
            "com.wu.signalchannelcharacterization.get_pci";

    public static Intent newIntent(Context packageContext,int pci)
    {
        Intent i = new Intent(packageContext,LocationActivity.class);
        i.putExtra(GET_PCI,pci);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        int pci = getIntent().getIntExtra(GET_PCI,0);
        return LocationFragment.newInstance(pci);
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
