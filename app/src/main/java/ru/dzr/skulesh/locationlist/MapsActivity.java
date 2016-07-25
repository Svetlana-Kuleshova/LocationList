package ru.dzr.skulesh.locationlist;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private ListView mList;
    private ArrayAdapter<String> mListAdapter;
    private LocationStorage mStorage;
    private ListToMapAdapter mListToMapInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mStorage = new LocationStorage(this);
        initList();
        setUpMapIfNeeded();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call setUpMap() once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                initMapMarkers();
            }
        });
    }

    private void initMapMarkers() {
        if ((mMap != null) && (mList != null))  {
            if (mListToMapInteractor == null) {
                mListToMapInteractor = new ListToMapAdapter(new MapPresenter(this, mMap), mStorage);
            }

            mList.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    switch (scrollState) {
                        case SCROLL_STATE_IDLE:
                            break;
                        case SCROLL_STATE_TOUCH_SCROLL:
                            break;
                        case SCROLL_STATE_FLING:
                            break;
                    }

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (visibleItemCount > 0) {
                        mListToMapInteractor.setMarkers(firstVisibleItem, visibleItemCount);
                    }
                }
            });

        }
    }

    private void initList() {
        mList = (ListView)findViewById(R.id.list);
        mListAdapter = new ArrayAdapter<String>(this, R.layout.location_list_item);
        ArrayList locNameList = mStorage.getLabels();
        mListAdapter.addAll(locNameList);
        mList.setAdapter(mListAdapter);
    }
}
