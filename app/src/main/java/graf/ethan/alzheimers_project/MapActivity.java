package graf.ethan.alzheimers_project;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements GoogleMap.OnMapClickListener,
    NewSafeZoneDialogFragment.NewSafeZoneDialogListener {

    private GoogleMap map;
    private SafeZone safeZone;

    private List<SafeZone> displayZones;

    private ListView safeZoneListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //Get the GoogleMap object.
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setOnMapClickListener(this);
        setCameraToLastPosition();

        //Display all of the existing safe zones.
        displayZones = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>(MapPreferenceManager.getNames(this));
        for(int i = 0; i < names.size(); i++) {
            SafeZone zone = MapPreferenceManager.getSafeZone(this, names.get(i));
            zone.display(map);
            displayZones.add(zone);
        }

        safeZoneListView = (ListView) findViewById(R.id.list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.safe_zone_list_item, R.id.safe_zone, names);
        safeZoneListView.setAdapter(adapter);

        safeZoneListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String name = (String) ((TextView) view.findViewById(R.id.safe_zone)).getText();
                SafeZone zone = MapPreferenceManager.getSafeZone(MapActivity.this, name);

                //Move to the last location with a zoom of 20.
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(zone.getPoints().get(0), 18));
            }
        });
    }

    public void setCameraToLastPosition() {
        //Get the last location to move to.
        Location lastLocation = MapPreferenceManager.readLastLocation(this);
        LatLng lastPosition = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());

        //Move to the last location with a zoom of 20.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(lastPosition, 18));
    }

    public void defineSafeZone(View v) {
        findViewById(R.id.define_safe_zone).setVisibility(View.GONE);
        findViewById(R.id.confirm).setVisibility(View.VISIBLE);

        // Create an instance of the new safe zone fragment and show it
        NewSafeZoneDialogFragment dialog = new NewSafeZoneDialogFragment();
        dialog.setTitle(getResources().getString(R.string.dialog_new_safe_zone));
        dialog.show(getSupportFragmentManager(), "NewSafeZoneDialogFragment");
    }

    public void confirmSafeZone(View v) {
        findViewById(R.id.confirm).setVisibility(View.GONE);
        findViewById(R.id.define_safe_zone).setVisibility(View.VISIBLE);

        safeZone.confirm(this);
        displayZones.add(safeZone);
        safeZone = null;

        ArrayList<String> names = new ArrayList<>(MapPreferenceManager.getNames(this));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.safe_zone_list_item, R.id.safe_zone, names);
        safeZoneListView.setAdapter(adapter);
    }

    public void removeSafeZone(View v) {
        String name = (String) ((TextView) ((RelativeLayout) v.getParent()).findViewById(R.id.safe_zone)).getText();
        MapPreferenceManager.removeSafeZone(this, name);

        for(int i = 0; i < displayZones.size(); i++) {
            if(displayZones.get(i).getName().equals(name)) {
                displayZones.get(i).remove();
            }
        }

        ArrayList<String> names = new ArrayList<>(MapPreferenceManager.getNames(this));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.safe_zone_list_item, R.id.safe_zone, names);
        safeZoneListView.setAdapter(adapter);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if(safeZone != null) {
            safeZone.addMarker(latLng);
        }
    }

    public GoogleMap getMap() {
        return map;
    }

    public void onDialogPositiveClick(NewSafeZoneDialogFragment dialog, String safeZoneName) {
        safeZone = new SafeZone(map, safeZoneName);
    }

    public void onDialogNegativeClick(NewSafeZoneDialogFragment dialog) {

    }
}
