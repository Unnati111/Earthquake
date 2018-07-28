package com.example.ujjawaljain.earthquake;


public class Earthquake {
    private  double mMagnitude;
    private  String mLocation;
    private long mTimeInMilliseconds;
    private String mUrl;

    public Earthquake(double mMagnitude, String mLocation, long mTimeInMilliseconds,String murl) {
        this.mMagnitude = mMagnitude;
        this.mLocation = mLocation;
        this.mTimeInMilliseconds = mTimeInMilliseconds;
        mUrl = murl;
    }

    public double getMagnitude() {

        return mMagnitude;
    }

    public String getLocation() {

        return mLocation;
    }

    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }
    public String getUrl() {
        return mUrl;
    }
}
