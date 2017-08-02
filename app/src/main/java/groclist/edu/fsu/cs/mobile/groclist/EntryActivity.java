package groclist.edu.fsu.cs.mobile.groclist;

import android.*;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import static java.security.AccessController.getContext;

public class EntryActivity extends AppCompatActivity implements ExistingEntryFragment.OnFragmentInteractionListener,NewEntryFragment.OnFragmentInteractionListener {

    public FragmentManager FM = getSupportFragmentManager();
    public FragmentTransaction FT;

    String BarContent;
    String BarFormat;
    String location;
    int pluorupc=0;
    String name;
    boolean exists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        Intent entryI = getIntent();
        BarContent = entryI.getStringExtra("Content");
        BarFormat = entryI.getStringExtra("Format");
        location = entryI.getStringExtra("Place");


        exists = false;
        Cursor itemsearch = getContentResolver().query(MyContentProvider.CONTENT_URI,null,null,null,null);

        if(itemsearch!=null&&itemsearch.getCount()!=0) {
            while (itemsearch.moveToNext()) {
                if (itemsearch.getString(0).equals(BarContent)) {
                    exists = true;
                    pluorupc = 1;
                    name = itemsearch.getString(5);

                }
                else if (itemsearch.getString(1).equals(BarContent)) {
                    exists = true;
                    pluorupc = 2;
                    name = itemsearch.getString(5);
                }

            }
            itemsearch.close();
        }
        Location lastloc;
        //getlocation
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            lastloc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.i("EActivity", location);
            location = location + " : " + lastloc.getLatitude() + "," + lastloc.getLongitude();

        } else {
            location = "NONE : " + location;
        }




        if(!exists)
            onnewEntryfound();
        else {
            onexistingEntryfound();
        }
    }



    public void onnewEntryfound(){
        FT = FM.beginTransaction();
        NewEntryFragment newentry = NewEntryFragment.newInstance(BarContent,BarFormat);
        FT.replace(R.id.fragment_frame,newentry);
        FT.commit();
    }
    public void onexistingEntryfound(){
        FT = FM.beginTransaction();
        ExistingEntryFragment existingentry = ExistingEntryFragment.newInstance(BarContent,BarFormat);
        FT.replace(R.id.fragment_frame,existingentry);
        FT.commit();

    }
    public void oninsertnewEntry(String itemname,float itemprice ){

        // add Entry to user database
        ContentValues CV = new ContentValues();

        if (BarFormat.equals("UPC_A") || BarFormat.equals("UPC_B")) {
            pluorupc = 0;
        } else if (BarFormat.equals("PLU")) {
            pluorupc = 1;
        } else {
            pluorupc = 2;
        }

        if(pluorupc==1){
            CV.put("PLU",BarContent);
        CV.put("UPC","0");}
        if(pluorupc==2||pluorupc==0){
            CV.put("UPC",BarContent);
            CV.put("PLU","0");}


        Log.i("entry", BarContent);
        Log.i("entry", itemname);
        Log.i("entry", itemprice + "");
        CV.put("LOCATION", location);
        CV.put("PRICE",itemprice);
        CV.put("DESCRIPTION",itemname);
        CV.put("TOTALPRICE",itemprice);
        CV.put("LISTSTATUS",0);

        getContentResolver().insert(MyContentProvider.CONTENT_URI, CV);

        returntomain();
    }

    public void onnewprice(float newprice)
    {


        ContentValues CV = new ContentValues();
        if(pluorupc==1) {
            CV.put("PLU", BarContent);
            CV.put("UPC","0");
        }
            if(pluorupc==2){
            CV.put("UPC",BarContent);
        CV.put("PLU","0");}

        CV.put("LOCATION", location);
        CV.put("PRICE",newprice);
        CV.put("DESCRIPTION",name);
        CV.put("TOTALPRICE",newprice);
        CV.put("LISTSTATUS",0);
        getContentResolver().insert(MyContentProvider.CONTENT_URI,CV);



        returntomain();
    }

    public void returntomain(){
        Intent RTM = new Intent(this,MainActivity.class);
        startActivity(RTM);
        finish();

    }


}
