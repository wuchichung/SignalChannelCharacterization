package com.wu.signalchannelcharacterization;

import android.support.v4.app.Fragment;

public class WiFiListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new WiFiListFragment();
    }
}
