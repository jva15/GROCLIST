package groclist.edu.fsu.cs.mobile.groclist;

import android.*;
import android.Manifest;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContentResolverCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;

import groclist.edu.fsu.cs.mobile.groclist.mainFragment;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import groclist.edu.fsu.cs.mobile.groclist.IntentIntegrator;
import groclist.edu.fsu.cs.mobile.groclist.IntentResult;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, mainFragment.OnFragmentInteractionListener {

    LocationManager lm;
    final public int REQUEST = 290;
    int maximumlocationresults = 5;
    Location lastloc;
    List<Address> addresses_1;
    ArrayList<String> addresses = new ArrayList<String>(0);
    Boolean locationset = false;
    String store = "";

    LocationListener LL = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    public void getlocations() {
        String Add = "";
        addresses = new ArrayList<String>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, LL);

            lastloc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Log.i("coords", lastloc.getLatitude() + "" + lastloc.getLongitude());
            Geocoder geo = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                addresses_1 = geo.getFromLocation(lastloc.getLatitude(), lastloc.getLongitude(), maximumlocationresults);
            } catch (IOException e) {
                Log.d("UNACCEPTABLE!", "getfromlocationfailed");
            }
            if (addresses_1 != null) {
                for (int i = 0; i < addresses_1.size(); i++) {
                    addresses.add(addresses_1.get(i).getAddressLine(0));
                    Add = addresses.get(i);
                    if (Add.contains(","))
                        addresses.set(i, Add.substring(0, Add.indexOf(",")));
                    else addresses.set(i, Add);
                    Log.i("coords", addresses.get(i));
                }
            }
            FragmentManager m = getSupportFragmentManager();
            FragmentTransaction tran = m.beginTransaction();
            //showing mainFragment at first
            Bundle addressitems = new Bundle();
            addressitems.putStringArrayList("address", addresses);
            mainFragment mf = mainFragment.newInstance();
            mf.setArguments(addressitems);
            tran.add(R.id.main_frame, mf, "MAIN_FRAG");
            tran.commit();
            locationset = true;


        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                    , REQUEST);

            /*ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}
                    , REQUEST);*/

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST: {
                //relaunch main frag with items for the spinner.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    while (!locationset)//wait for current frag to load;
                        getlocations();
                   /* FragmentManager m = getSupportFragmentManager();
                    FragmentTransaction tran = m.beginTransaction();
                    Bundle addressitems = new Bundle();
                    addressitems.putStringArrayList("address", addresses);
                    mainFragment mf = mainFragment.newInstance();
                    mf.setArguments(addressitems);
                    tran.replace(R.id.main_frame, mf);
                    tran.commit();
*/

                }
            }
        }
    }


    public void Scanitems(View V)
    {
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();
    }

    public void setplace(String str) {
        store = str;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
        //we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            Intent toEA = new Intent(this,EntryActivity.class);
            toEA.putExtra("Content",scanContent);
            toEA.putExtra("Format",scanFormat);
            toEA.putExtra("Place", store);

            startActivity(toEA);

        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }




    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getlocations();



      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            FragmentManager m = getSupportFragmentManager();
            FragmentTransaction tran = m.beginTransaction();

            SettingsFragment sf = new SettingsFragment();
            //tran.replace(R.id.main_frame, cf, "CURRENT_FRAG");
            tran.replace(R.id.main_frame, sf);
            tran.commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager m = getSupportFragmentManager();
        FragmentTransaction tran;


        if (id == R.id.current_cart) {

            tran = m.beginTransaction();
            currentFragment cf = currentFragment.newInstance();
            tran.replace(R.id.main_frame, cf);
            tran.commit();


        } else if (id == R.id.past_purchases) {
            //check to see if current frag is main_frag
            tran = m.beginTransaction();
            pastFragment pf = pastFragment.newInstance();
            tran.replace(R.id.main_frame, pf);
            tran.commit();



        } else if (id == R.id.my_pantry) {

            //check to see if current frag is main_frag
            tran = m.beginTransaction();
            pantryFragment pf = pantryFragment.newInstance();
            tran.replace(R.id.main_frame, pf);
            tran.commit();
        }
        else if(id==R.id.home_button){
            //checking to see if its in current_frag
            tran = m.beginTransaction();
            getlocations();
            Bundle addressitems = new Bundle();
            addressitems.putStringArrayList("address", addresses);
            mainFragment mf = mainFragment.newInstance();
            mf.setArguments(addressitems);

            tran.replace(R.id.main_frame, mf);
            tran.commit();



        }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    }

