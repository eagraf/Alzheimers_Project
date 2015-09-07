package graf.ethan.alzheimers_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Created by Ethan on 7/20/2015.
 */
public class MapPreferenceManager {

    //Write the last known location to shared preferences.
    public static void writeLastLocation(Context context, Location location) {
        SharedPreferences preferences = context.getSharedPreferences("alzheimers_location_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //Double value is converted to long for storage.
        editor.putLong("last_location_longitude", Double.doubleToRawLongBits(location.getLongitude()));
        editor.putLong("last_location_latitude", Double.doubleToRawLongBits(location.getLatitude()));
        editor.commit();
    }

    //Read the last location that was retrieved by Google Maps.
    public static Location readLastLocation(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("alzheimers_location_data", Context.MODE_PRIVATE);
        //Convert long values back to double values.
        Double longitude = Double.longBitsToDouble(preferences.getLong("last_location_longitude", 0));
        Double latitude = Double.longBitsToDouble(preferences.getLong("last_location_latitude", 0));

        //Create the location object.
        Location location = new Location("alzheimer's_app");
        location.setLongitude(longitude);
        location.setLatitude(latitude);
        return location;
    }

    public static String readLastLocationName(Context context) {
        Location location = MapPreferenceManager.readLastLocation(context);

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if(null!=listAddresses&&listAddresses.size()>0){
                String _Location = listAddresses.get(0).getLocality();
                System.out.println(_Location);
                return _Location;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Write a safe zone to shared preferences.
    public static void writeSafeZone(Context context, SafeZone safeZone) {
        SharedPreferences preferences = context.getSharedPreferences("alzheimers_location_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        //The array of names of safe zones.
        Set<String> names = preferences.getStringSet("safe_zone_names", new HashSet<String>());
        names.add(safeZone.getName());
        editor.putStringSet("safe_zone_names", names);

        //Increment the number of safe zones.
        int safeZoneNum = (preferences.getInt("safe_zone_number", 0))+1;
        editor.putInt("safe_zone_number", safeZoneNum);

        ArrayList<LatLng> markers = (ArrayList) safeZone.getPoints();
        editor.putInt("safe_zone_" + safeZone.getName() + "_size", markers.size()-1);
        for(int i = 0; i < safeZone.getPoints().size()-1; i++) {
            //Convert the double coordinates to longs.
            Long latitude = Double.doubleToRawLongBits(markers.get(i).latitude);
            Long longitude = Double.doubleToRawLongBits(markers.get(i).longitude);

            //Put the coordinates of a marker into shared preferences.
            editor.putLong("safe_zone_" + safeZone.getName() +"_marker_#" + i + "_latitude", latitude);
            editor.putLong("safe_zone_" + safeZone.getName() +"_marker_#" + i + "_longitude", longitude);
        }
        editor.commit();
    }

    //Retrieve a safe zone from shared preferences.
    public static SafeZone getSafeZone(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences("alzheimers_location_data", Context.MODE_PRIVATE);

        //The array of names of safe zones.
        Set<String> names = preferences.getStringSet("safe_zone_names", new HashSet<String>());

        //If the name is the name of a safe zone.
        if(names.contains(name)) {
            ArrayList<LatLng> markers = new ArrayList<>();
            int num = preferences.getInt("safe_zone_" + name + "_size", 0);

            for(int i = 0; i < num; i++) {
                //Convert long values back to double values. Individually get each coordinate value.
                Double latitude = Double.longBitsToDouble(preferences.getLong("safe_zone_" + name + "_marker_#" + i + "_latitude", 0));
                Double longitude = Double.longBitsToDouble(preferences.getLong("safe_zone_" + name + "_marker_#" + i + "_longitude", 0));

                LatLng marker = new LatLng(latitude, longitude);
                markers.add(marker);
            }
            return new SafeZone(markers, name);
        }
        return null;
    }

    //Remove a safe zone from the list.
    public static void removeSafeZone(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences("alzheimers_location_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        //Decrement the number of safe zones.
        int safeZoneNum = (preferences.getInt("safe_zone_number", 0))+1;
        editor.putInt("safe_zone_number", safeZoneNum);

        //The array of names of safe zones.
        Set<String> names = preferences.getStringSet("safe_zone_names", new HashSet<String>());
        if(names.contains(name)) {
            names.remove(name);
            int num = preferences.getInt("safe_zone_" + name + "_size", 0);

            //Remove each coordinate value.
            for(int i = 0; i < num; i++) {
                editor.remove("safe_zone_" + name + "_marker_#" + i + "_latitude");
                editor.remove("safe_zone_" + name + "_marker_#" + i + "_latitude");
            }
            editor.putStringSet("safe_zone_names", names);
        }
        editor.commit();
    }

    public static Set<String> getNames(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("alzheimers_location_data", Context.MODE_PRIVATE);
        return preferences.getStringSet("safe_zone_names", new HashSet<String>());
    }
}
