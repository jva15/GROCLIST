package groclist.edu.fsu.cs.mobile.groclist;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.IBinder;

import java.util.Calendar;

public class ExpirationService extends Service {
    public ExpirationService() {
    }

    MyReceiver timecheck;

    @Override
    public int onStartCommand(Intent intent, int A, int B) {
        //check the time daily to see if anything may be spoiled


        timecheck = new MyReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(timecheck, filter);


        stopSelf();
        return Service.START_STICKY;


    }

    @Override
    public void onDestroy() {
        unregisterReceiver(timecheck);
        super.onDestroy();


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
