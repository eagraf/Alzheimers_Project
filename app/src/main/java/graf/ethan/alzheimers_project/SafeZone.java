package graf.ethan.alzheimers_project;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 7/20/2015.
 */
public class SafeZone {

    private List<LatLng> points;
    private List<Marker> markers;
    private GoogleMap map;
    private Polyline polyLine;
    private String name;

    public SafeZone(GoogleMap map, String name) {
        this.name = name;
        points = new ArrayList<>();
        markers = new ArrayList<>();

        this.map = map;
        this.polyLine = map.addPolyline(new PolylineOptions());
    }

    public SafeZone(List<LatLng> markers, String name) {
        this.points = markers;
        this.name = name;
    }

    //Display the bounds on the Google Map View.
    public void display(GoogleMap map) {
        this.map = map;
        this.polyLine = map.addPolyline(new PolylineOptions());

        //Close the poly line and show it.
        points.add(points.get(0));
        polyLine.setPoints(points);
    }

    //Add a marker (a corner point in the boundary definition.
    public void addMarker(LatLng marker) {
        points.add(marker);
        markers.add(map.addMarker(new MarkerOptions()
                .position(marker)
                .title("Safe Zone Boundary Point #" + points.size())));
        polyLine.setPoints(points);
    }

    //Confirm that the current selection is a safe zone.
    public void confirm(Context context) {
        //Close the poly line.
        points.add(points.get(0));
        polyLine.setPoints(points);

        //Write the safe zone to shared preferences.
        MapPreferenceManager.writeSafeZone(context, this);

        //Remove markers
        for(int i = 0; i < markers.size(); i++) {
            markers.get(i).remove();
        }
    }

    //Remove from the display.
    public void remove() {
        polyLine.setVisible(false);
    }

    public List<LatLng> getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }
}
