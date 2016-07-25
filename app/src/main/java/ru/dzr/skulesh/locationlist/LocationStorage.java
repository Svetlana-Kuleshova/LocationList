package ru.dzr.skulesh.locationlist;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LocationStorage {
    ArrayList<LatLng> mLocations;
    ArrayList<String> mLabels;
    Context mContext;

    public LocationStorage(Context context) {
        mLocations = new ArrayList<LatLng>();
        mLabels = new ArrayList<String>();
        mContext = context;
        loadFromFile();
    }

    public void addLocation(double latitude, double longitude, String label) {
        mLocations.add(new LatLng(latitude, longitude));
        mLabels.add(label);
    }

    public void addLocation(LatLng location, String label) {
        mLocations.add(location);
        mLabels.add(label);
    }

    public LatLng getLocation(int position) {
        return mLocations.get(position);
    }

    public ArrayList<String> getLabels() {
        return mLabels;
    }

    private void loadFromFile() {
        InputStream is = mContext.getResources().openRawResource(R.raw.locations);
        JsonReader reader = new JsonReader(new InputStreamReader(is));
        try {
            readLocationsArray(reader);

            reader.close();
            is.close();
        } catch (IOException e) {
            Log.e("JSON", e.toString());
        } catch (Exception e) {
            Log.e("JSON", e.toString());
        }
    }

    private void readLocationsArray(JsonReader reader) throws IOException{
        reader.beginArray();
        while(reader.hasNext()) {
            readLocation(reader);
        }
        reader.endArray();
    }

    private void readLocation(JsonReader reader) throws IOException {
        String label = null;
        double lat = -400;
        double lng = -400;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("label")){
                label = reader.nextString();
            }
            else if (name.equals("latitude")) {
                lat = reader.nextDouble();
            }
            else if (name.equals("longitude")){
                lng = reader.nextDouble();
            }
            else {
                reader.skipValue();
            }
        }
        addLocation(lat, lng, label);
        reader.endObject();
    }
}
