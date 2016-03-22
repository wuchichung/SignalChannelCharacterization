package com.wu.signalchannelcharacterization;

/**
 * Created by wu on 2/20/16.
 */

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;

public class WiFiList {

    /* Create a Singleton of WiFiList Class */
    private static WiFiList sWiFiList;

    public static WiFiList get(Context context){

        if (sWiFiList == null){
            sWiFiList = new WiFiList(context);
        }
        return sWiFiList;
    }

    /* Member variables and functions */
    private ArrayList<WiFi> mWiFiList = new ArrayList<>();

    public WiFi getWiFi(){
        return mWiFiList.get(1);
    }

    public List<WiFi> getWiFis() {
        return mWiFiList;
    }

    public WiFi getWiFi(String BSSID) {
        for (WiFi wiFi : mWiFiList) {
            if (wiFi.getBSSID().equals(BSSID)) {
                return wiFi;
            }
        }
        return null;
    }

    private WiFiList(Context context){
        getWiFiList(context);
    }

    private void getWiFiList(Context context){
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifi.startScan();

        List<ScanResult> wifiScanList = wifi.getScanResults();
        updateWiFiList(wifiScanList);
    }

    private void updateWiFiList(List<ScanResult> wifiScanList){
        boolean existedWiFi;

        for(ScanResult _scanResult : wifiScanList){
            existedWiFi = false;
            for(WiFi _wifi:mWiFiList){
                if(_scanResult.BSSID.equals(_wifi.getBSSID())) {
                    existedWiFi = true;
                    retriveScanResult(_scanResult,_wifi,existedWiFi);
                    break;
                }
            }
            if(!existedWiFi){
                WiFi temp = new WiFi();
                retriveScanResult(_scanResult, temp,existedWiFi);
                mWiFiList.add(temp);
            }
        }
    }

    private void retriveScanResult(ScanResult _scanResult, WiFi _wifi, boolean existed){

        if(existed == false)
            _wifi.setBSSID(_scanResult.BSSID);

        _wifi.setSSID(_scanResult.SSID);
        _wifi.setSignalLevel(_scanResult.level);
        _wifi.setTimestamp(_scanResult.timestamp);

    }
}

