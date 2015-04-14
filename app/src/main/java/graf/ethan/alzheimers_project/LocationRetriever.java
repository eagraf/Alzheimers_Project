package graf.ethan.alzheimers_project;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Ethan on 4/13/2015.
 * This class is responsible for calling the Google Api Client and retrieving the location.
 */
public class LocationRetriever implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleApiClient mGoogleApiClient;

    private Context context;
    private PowerManager.WakeLock wakeLock;

    public LocationRetriever(Context context, PowerManager.WakeLock wl) {
        this.context = context;
        this.wakeLock = wl;
        buildGoogleApiClient();
    }

    protected synchronized void buildGoogleApiClient() {
        //Retrieve the Google API client from the context.
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(Bundle bundle) {
        //Get the location
        Location mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        //Send a small notification.
        Toast.makeText(context, "Location: " + mCurrentLocation.getLongitude() + ", " + mCurrentLocation.getLatitude(), Toast.LENGTH_LONG).show();

        //Release the WakeLock. This is important because it ensures that the battery is not drained excessively.
        wakeLock.release();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //Release the WakeLock. This is important because it ensures that the battery is not drained excessively.
        wakeLock.release();
    }
}
