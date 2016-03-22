package com.wu.signalchannelcharacterization;

/**
 * Created by wu on 2/20/16.
 */

public class WiFi {

    private String mSSID;
    private String mBSSID;
    private int mSignalLevel;
    private long mTimestamp;

    public String getSSID() {
        return mSSID;
    }

    public String getBSSID() {
        return mBSSID;
    }

    public int getSignalLevel() {
        return mSignalLevel;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public void setSSID(String SSID) {
        this.mSSID = SSID;
    }

    public void setTimestamp(long timestamp) {
        this.mTimestamp = timestamp;
    }

    public void setBSSID(String BSSID) {
        this.mBSSID = BSSID;
    }

    public void setSignalLevel(int signalLevel) {
        this.mSignalLevel = signalLevel;
    }
}
