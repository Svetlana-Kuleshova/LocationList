package ru.dzr.skulesh.locationlist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapPresenter {
    private Context mContext;
    private GoogleMap mMap;
    private Bitmap mMainMarker;

    private int mPadding;

    public MapPresenter(Context context, GoogleMap map) {
        mMap = map;
        mContext = context;

        mPadding = context.getResources().getInteger(R.integer.map_padding);
        mMainMarker = createMainMarker();
    }
    public void setMarkersAndZoom(LatLng mainLocation, List<LatLng> locations ) {
        double south = mainLocation.latitude;
        double west = mainLocation.longitude;
        double north = mainLocation.latitude;
        double east = mainLocation.longitude;

        // Set markers
        mMap.addMarker(new MarkerOptions()
                .position(mainLocation)
                .icon(BitmapDescriptorFactory.fromBitmap(mMainMarker)));

        for(LatLng location : locations) {
            mMap.addMarker(new MarkerOptions()
                    .position(location));

            // Find bounds
            if (location.latitude < south) {
                south = location.latitude;
            }
            else if (location.latitude > north) {
                north = location.latitude;
            }
            if (location.longitude < west) {
                west = location.longitude;
            }
            else if (location.longitude > east) {
                east = location.longitude;
            }
        }

        // Zoom
        LatLng southWest = new LatLng(south, west);
        LatLng northEast = new LatLng(north, east);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(new LatLngBounds(southWest, northEast), mPadding);
        mMap.moveCamera(cameraUpdate);
    }

    public void clearMarkers() {
        mMap.clear();
    }

    private Bitmap createMainMarker() {
        TypedValue dimenValue = new TypedValue();
        mContext.getResources().getValue(R.dimen.marker_scale, dimenValue, true);
        float scale = dimenValue.getFloat();

        return scaleBitmap(R.mipmap.big_map_marker, scale);
    }

    private Bitmap scaleBitmap(int resourceId, float scale){
        Bitmap imageBitmap = BitmapFactory.decodeResource(mContext.getResources(),
                resourceId);
        int width = (int)(imageBitmap.getWidth() * scale);
        int height = (int)(imageBitmap.getHeight() * scale);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }
}
