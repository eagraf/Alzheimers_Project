package graf.ethan.alzheimers_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
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
}