package com.wu.signalchannelcharacterization;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.view.View;
import android.content.Context;
import android.widget.TextView;
import android.util.Log;

import java.util.List;

public class testActivity extends AppCompatActivity {

    private static final String TAG = "WuDebug";

    TelephonyManager telephonyManager;
    List<CellInfo> cellInfos;

    TextView tv;
    TextView tv2;
    TextView tv3;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "cellInfos is Init");

        tv = (TextView) findViewById(R.id.BSSID);
        tv2 = (TextView) findViewById(R.id.SSID);
        tv3 = (TextView) findViewById(R.id.Level);

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        cellInfos = telephonyManager.getAllCellInfo();

        LTEList lteList = LTEList.get(this);
        List<LTE> ltes = lteList.getLTEs();

        tv.setText(String.valueOf(ltes.size()));
//        tv.setText(String.valueOf((() cellInfo).getmPci()));
//        tv2.setText(cellInfo1.getClass().toString());


//        tv2.setText(String.valueOf(((CellInfoWcdma) cellInfo).getCellSignalStrength().getDbm()));
//        tv3.setText(String.valueOf(((CellInfoWcdma) cellInfo).getCellSignalStrength().getAsuLevel()));
        }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
