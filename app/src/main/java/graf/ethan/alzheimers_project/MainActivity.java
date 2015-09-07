package graf.ethan.alzheimers_project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends ActionBarActivity {

    EnvironmentState state;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set the alarm when the activity is started */
        LocationAlarm.setAlarm(this);
        Toast.makeText(this, "Alarm Scheduled", Toast.LENGTH_LONG)
                .show();

        state = new EnvironmentState(this, MapPreferenceManager.readLastLocationName(this));
        showWeather();
    }

    /* Open a Google Maps activity */
    public void openMap(View view) {
        Toast.makeText(this, "Opening Map", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
        /*
        // Creates an Intent that will load a map of San Francisco
        Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);*/
    }

    /*Toggles the alarm on or off*/
    public void toggleAlarm(View view) {
        if(((Button) findViewById(R.id.toggleAlarmButton)).getText().equals("Cancel Alarm")) {
            Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_LONG).show();
            LocationAlarm.cancelAlarm(this);
            ((Button) findViewById(R.id.toggleAlarmButton)).setText("Set Alarm");
        }
        else if(((Button) findViewById(R.id.toggleAlarmButton)).getText().equals("Set Alarm")) {
            Toast.makeText(this, "Alarm Set", Toast.LENGTH_LONG).show();
            LocationAlarm.setAlarm(this);
            ((Button) findViewById(R.id.toggleAlarmButton)).setText("Cancel Alarm");
        }
    }

    public void showWeather() {
        ((TextView) findViewById(R.id.temperature)).setText(state.getTemperature() + " " + state.getUnits());
        ((TextView) findViewById(R.id.location)).setText(state.getLocation());
        ((TextView) findViewById(R.id.description)).setText(state.getDescription());
    }
}