package com.wu.signalchannelcharacterization;

import android.support.v4.app.Fragment;

/**
 * Created by wu on 3/14/16.
 */

public class LTEListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new LTEListFragment();
    }
}

