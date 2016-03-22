package com.wu.signalchannelcharacterization;

import android.telephony.CellInfo;

import java.util.List;

/**
 * Created by wu on 2/20/16.
 */
public class LTE {
    private int mPci;
    private int mDbm;
    private int mAsuLevel;
    private int mTimingAdvance;

    public int getmAsuLevel() {
        return mAsuLevel;
    }

    public int getmDbm() {
        return mDbm;
    }

    public int getmPci() {
        return mPci;
    }

    public int getmTimingAdvance() {
        return mTimingAdvance;
    }

    public void setmAsuLevel(int mAsuLevel) {
        this.mAsuLevel = mAsuLevel;
    }

    public void setmDbm(int mDbm) {
        this.mDbm = mDbm;
    }

    public void setmPci(int mPci) {
        this.mPci = mPci;
    }

    public void setmTimingAdvance(int mTimingAdvance) {
        this.mTimingAdvance = mTimingAdvance;
    }
}
