package graf.ethan.alzheimers_project;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;

public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private TextView latitudeField;
    private TextView longitudeField;
    private TextView addressField;

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    public static final String TAG = LocationProvider.class.getSimpleName();

    private LocationRequest mLocationRequest;

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latitudeField = (TextView) findViewById(R.id.TextViewLatitudeCoordinate);
        longitudeField = (TextView) findViewById(R.id.TextViewLongitudeCoordinate);
        addressField = (TextView) findViewById(R.id.TextViewAddressString);

        LocationAlarm.setAlarm(this);


        Toast.makeText(this, "Alarm Scheduled", Toast.LENGTH_LONG)
                .show();

        buildGoogleApiClient();
        createLocationRequest();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        super.onPause();
        stopLocationUpdates();
    }

    public void openMap(View view) {

        Toast.makeText(this, "Opening Map", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        latitudeField.setText(String.valueOf(mCurrentLocation.getLatitude()));
        longitudeField.setText(String.valueOf(mCurrentLocation.getLongitude()));
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
    }
}