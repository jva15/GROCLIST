package groclist.edu.fsu.cs.mobile.groclist;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyReceiver extends BroadcastReceiver {

    public Notification exnotifi;
    public NotificationManager nManager;
    private Context contex;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        contex = context;
        String[] projection = {"DESCRIPTION", "UPC", "TIMESTAMP"};//if something is a plu item, "UPC" holds an expiration instead;
        String Selectionclause = "(LISTSTATUS = ?) AND (UPC <> ?) AND (PLU <> ?)";
        String[] Selections = {"1", "null", "0"};
        int minexp, maxexp;
        long time;
        int i = 0;
        Cursor iteminspector;
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        int seconds = c.get(Calendar.SECOND);
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cday = c.get(Calendar.DAY_OF_MONTH);
        String entry;
        int year, month, day;
        List<String> elist = new ArrayList<>();
        int Lastcheck;
        Boolean notify1 = false;
        Boolean notify2 = false;

        String[] message = {
                "Some Fridge/Outside Item(s) may spoil within a weak",
                "Some Fridge/outside item(s) maybe spoiled"
        };

        if ((hour + minutes + seconds) < 2) {


            iteminspector = context.getContentResolver().query(MyContentProvider.CONTENT_URI, projection, Selectionclause, Selections, null);
            if (iteminspector != null) {
                while (iteminspector.moveToNext()) {
                    i++;
                    entry = iteminspector.getString(1);
                    minexp = Integer.getInteger(entry.substring(0, entry.indexOf(":") - 1));
                    maxexp = Integer.getInteger(entry.substring(entry.indexOf(":") + 1));
                    entry = iteminspector.getString(2);
                    year = Integer.getInteger(entry.substring(0, entry.indexOf("-") - 1));
                    month = Integer.getInteger(entry.substring(entry.indexOf("-") + 1, entry.lastIndexOf("-") - 1));
                    day = Integer.getInteger(entry.substring(entry.lastIndexOf("-") + 1));
                    time = (cyear * 365 + cmonth * 100 + cday) - (year * 365 + month * 100 + day);


                    if (time >= maxexp) {
                        //notify
                        notify2 = true;
                        elist.add(i, iteminspector.getString(0) + " | Potentially spoiled");

                    } else if (time >= maxexp - 7) {
                        //notify
                        notify1 = true;
                        elist.add(i, iteminspector.getString(0) + " | " + (maxexp - time) + " Days before potential fridge spoilage");
                    } else if (time >= minexp && time <= minexp + 3) {
                        //notify
                        notify2 = true;
                        elist.add(i, iteminspector.getString(0) + " | " + (maxexp - time) + " Days(spoiled if item was left outside) before potential fridge spoilage");
                    } else if (time >= minexp - 7) {
                        //notify
                        notify1 = true;
                        elist.add(i, iteminspector.getString(0) + " | " + (minexp - time) + " Days(if left outside) before potential outside spoilage");
                    }


                }
                if (notify2) sendnotification(message[0], elist);
                else if (notify1) sendnotification(message[0], elist);
            }


        }


    }

    //sends notification
    public void sendnotification(String message, List<String> items) {

        String[] expireditems = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            expireditems[i] = items.get(i);
        }
        Intent intent = new Intent(contex, MainActivity.class);
        intent.putExtra("list", expireditems);
        PendingIntent PenIntent = PendingIntent.getActivity(contex, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        exnotifi = new Notification.Builder(contex)
                .setContentTitle("Pantry status: ")//check
                .setContentText(message)//set
                .setSmallIcon(R.drawable.expiring)
                .setContentIntent(PenIntent)
                .setAutoCancel(true).build();
        nManager.notify(1, exnotifi);

    }

}
