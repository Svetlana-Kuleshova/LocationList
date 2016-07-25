package ru.dzr.skulesh.locationlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LabelledLocation implements Serializable{

    @Expose
    @SerializedName("latitude")
    private double mLatitude;

    @Expose
    @SerializedName("longitude")
    private double mLongitude;

    @Expose
    @SerializedName("label")
    private String mLabel;

    public LabelledLocation(double latitude, double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public LabelledLocation(double latitude, double longitude, String label) {
        mLatitude = latitude;
        mLongitude = longitude;
        mLabel = label;
    }

    private void setLabel(String label) {
        mLabel = label;
    }

    private String getLabel(){
        return mLabel;
    }

    private double getLatitude() {
        return mLatitude;
    }

    private double getLongitude() {
        return mLongitude;
    }
}
