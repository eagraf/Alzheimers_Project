package graf.ethan.alzheimers_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set the alarm when the activity is started */
        LocationAlarm.setAlarm(this);
        Toast.makeText(this, "Alarm Scheduled", Toast.LENGTH_LONG)
                .show();
    }

    /* Open a Google Maps activity */
    public void openMap(View view) {
        Toast.makeText(this, "Opening Map", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
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
}