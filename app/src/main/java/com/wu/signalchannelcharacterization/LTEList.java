package com.wu.signalchannelcharacterization;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 2/27/16.
 */
public class LTEList {

    final String CELL_TYPE_LTE = "class android.telephony.CellInfoLte";
    private final static String TAG = "LTEList";

    private static LTEList sLTEList;
    private ArrayList<LTE> mLTEList = new ArrayList<>();

    public static LTEList get(Context context){
        if (sLTEList == null){
            sLTEList = new LTEList(context);
        }
        sLTEList.getLTEList(context);
        return sLTEList;
    }

    public List<LTE> getLTEs() {
        return mLTEList;
    }

    private LTEList(Context context){
        getLTEList(context);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void getLTEList(Context context){
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        List<CellInfo> cellInfoList = telephonyManager.getAllCellInfo();
        updateLTEList(cellInfoList);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void updateLTEList(List<CellInfo> cellInfos){
        if(!cellInfos.isEmpty())
        {
            for (CellInfo _cellInfo : cellInfos) {
                if (!_cellInfo.getClass().toString().equals(CELL_TYPE_LTE))
                    continue;

                boolean existedCell = false;

                for (LTE _lte : mLTEList) {
                    if (((CellInfoLte) _cellInfo).getCellIdentity().getPci() == _lte.getmPci()) {
                        existedCell = true;
                        retriveCellInfo(((CellInfoLte) _cellInfo), _lte, existedCell);
                        break;
                    }
                }

                if (existedCell == false) {
                    LTE temp = new LTE();
                    retriveCellInfo(((CellInfoLte) _cellInfo), temp, existedCell);
                    mLTEList.add(temp);
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void retriveCellInfo(CellInfoLte _cellInfoLte, LTE _lte, boolean existed) {
        if (existed==false)
        {    _lte.setmPci(_cellInfoLte.getCellIdentity().getPci());
        }

        _lte.setmAsuLevel(_cellInfoLte.getCellSignalStrength().getAsuLevel());
        _lte.setmDbm(_cellInfoLte.getCellSignalStrength().getDbm());
        _lte.setmTimingAdvance(_cellInfoLte.getCellSignalStrength().getTimingAdvance());
    }
}
