package graf.ethan.alzheimers_project;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.SystemClock;

/**
 * Created by Ethan on 4/6/2015.
 * This class represents an Alarm that is called at a dynamic interval, to evaluate the algorithm
 * to determine whether assistance is needed.
 */
public class LocationAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Get the WakeLock.
        //The WakeLock forces the device to stay on, until the release method is called.
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        setAlarm(context);

        //This will automatically retrieve and display the location, then release the wakeLock.
        LocationRetriever retriever = new LocationRetriever(context, wl);
    }

    public static void setAlarm(Context context)  {
        //Set the alarm to every 60 seconds.
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, LocationAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 60000, 60000, pi);
    }

    public static void cancelAlarm(Context context) {
        //Cancel the alarm.
        Intent intent = new Intent(context, LocationAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

}
