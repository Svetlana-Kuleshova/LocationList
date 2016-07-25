package ru.dzr.skulesh.locationlist;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class ListToMapAdapter {
    private MapPresenter mMapPresenter;
    private LocationStorage mStorage;

    public ListToMapAdapter(MapPresenter presenter, LocationStorage storage) {
        mMapPresenter = presenter;
        mStorage = storage;
    }

    public void setMarkers(int firstPosition, int itemsNumber) {
        LatLng mainLocation = mStorage.getLocation(firstPosition);
        ArrayList<LatLng> locations = new ArrayList<>();
        int lastPosition = firstPosition + itemsNumber;
        for (int i = firstPosition + 1; i < lastPosition; i++) {
            locations.add(mStorage.getLocation(i));
        }

        mMapPresenter.clearMarkers();
        mMapPresenter.setMarkersAndZoom(mainLocation, locations);
    }
}
