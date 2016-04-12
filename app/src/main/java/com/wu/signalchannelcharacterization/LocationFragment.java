package com.wu.signalchannelcharacterization;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by wu on 4/7/16.
 */
public class LocationFragment extends SupportMapFragment {
    private static final String TAG = "LocationFragment";
    private static final String ARG_PCI = "pci";

    private GoogleApiClient mClient;
    private GoogleMap mMap;
    private Location mCurrentLocation;
    private LTE mLte = null;

    public static LocationFragment newInstance(int pci) {
        Bundle args = new Bundle();
        args.putInt(ARG_PCI,pci);

        LocationFragment fragment = new LocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void getLTE(int pci) {

        LTEList lteList = LTEList.get(getActivity());
        List<LTE> ltes = lteList.getLTEs();

        for(LTE lte : ltes )
        {
            if(lte.getmPci() == pci)
            {
               mLte = lte;
            }
        }
    }

    private CircleOptions decideCircleColor(LatLng locaiton)
    {
        CircleOptions co = new CircleOptions();
        int dbm = mLte.getmDbm();



        return co;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int pci = getArguments().getInt(ARG_PCI);
        getLTE(pci);

        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        findLocation();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                }).build();

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mClient.disconnect();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findLocation() {
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);

        if (ActivityCompat.checkSelfPermission
                (getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&  ActivityCompat.checkSelfPermission
                (getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }else
        {
            LocationServices.FusedLocationApi
                    .requestLocationUpdates(mClient, request, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            mCurrentLocation = location;
                            updateUI();
                        }
                    });
        }
    }

    private void updateUI() {

        if (mMap == null) {
            Log.d(TAG,"Map is null");
            return;
        }

        if(mCurrentLocation == null) {
            Log.d(TAG,"CurrentLocation is null");
            return;
        }

        if(mLte == null)
        {
            Log.d(TAG,"LTE is null");
            return;
        }

        LatLng myPoint = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());

        CircleOptions circleOptions = new CircleOptions()
                .center(myPoint)
                .radius(1)
                .fillColor(Color.BLUE);

        mMap.clear();
        mMap.addCircle(circleOptions);

        LatLngBounds bounds = new LatLngBounds.Builder().include(myPoint).build();
        int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);

        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, margin);
        mMap.animateCamera(update);
    }
}

