package hr.foi.air.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.LocationListener;
import android.util.Log;

import androidx.annotation.NonNull;

import hr.foi.air.core.TokenJudge;

public class LocationJudge implements TokenJudge {
    private final LocationTrack locationTrack;
    private final Float objectLatitude;
    private final Float objectLongitude;

    public LocationJudge(Context context, Float latitude, Float longitude) {
        this.locationTrack = new LocationTrack(context);
        this.objectLatitude = latitude;
        this.objectLongitude = longitude;
    }


    @SuppressLint("WrongConstant")
    @Override
    public boolean canGetToken() {
        if (locationTrack.canGetLocation()){
            double userLongitude = locationTrack.getLongitude();
            double userLatitude = locationTrack.getLatitude();
            double distance = DistanceCalculator.getDistance((double)objectLatitude, (double)objectLongitude, userLatitude, userLongitude);
            Log.println(0, "Dis", ""+distance);
            return distance <= 1;
        }
        return false;
    }

    @Override
    public String getDenialMessage() {
        return LocationApp.getAppResources().getString(R.string.location_denial_message);
    }
}
