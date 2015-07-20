package graf.ethan.alzheimers_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

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
}
