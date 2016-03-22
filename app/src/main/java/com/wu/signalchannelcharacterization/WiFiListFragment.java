package com.wu.signalchannelcharacterization;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by wu on 2/20/16.
 */

public class WiFiListFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        WiFiList wiFiList = WiFiList.get(getActivity());
        List<WiFi> wiFis = wiFiList.getWiFis();

        if (mAdapter == null) {
            mAdapter = new Adapter(wiFis);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class Holder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mSSID;
        private TextView mBSSID;
        private TextView mSignalLevel;
        private TextView mTimestamp;

        private WiFi mWiFi;

        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mSSID           = (TextView) itemView.findViewById(R.id.list_ssid_text_view);
            mBSSID          = (TextView) itemView.findViewById(R.id.list_bssid_text_view);
            mSignalLevel    = (TextView) itemView.findViewById(R.id.list_signal_level_text_view);
            mTimestamp      = (TextView) itemView.findViewById(R.id.list_timestamp_text_view);
        }

        public void bindWiFi(WiFi wifi) {
            mWiFi = wifi;
            mSSID.setText(mWiFi.getSSID());
            mBSSID.setText(mWiFi.getBSSID());
            mSignalLevel.setText(String.valueOf(mWiFi.getSignalLevel()));
            mTimestamp.setText(String.valueOf(mWiFi.getTimestamp()));


        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),
            mWiFi.getSSID() + " clicked!", Toast.LENGTH_SHORT)
            .show();
        }
    }


    private class Adapter extends RecyclerView.Adapter<Holder> {

        private List<WiFi> mWiFis;

        public Adapter(List<WiFi> WiFis) {
            mWiFis = WiFis;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_wifi, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder,int position) {
            WiFi wifi = mWiFis.get(position);
            holder.bindWiFi(wifi);
        }

        @Override
        public int getItemCount() {
            return mWiFis.size();
        }
    }
}
