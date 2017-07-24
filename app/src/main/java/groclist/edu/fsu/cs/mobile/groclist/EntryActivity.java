package groclist.edu.fsu.cs.mobile.groclist;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EntryActivity extends AppCompatActivity {

    public FragmentManager FM = getSupportFragmentManager();
    public FragmentTransaction FT;

    String BarContent;
    String BarFormat;

    boolean exists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        Intent entryI = getIntent();
        BarContent = entryI.getStringExtra("Content");
        BarFormat = entryI.getStringExtra("Format");

        /*TODO: replace 'exist' statment with code for querying the database for the entry
        * set exists to true if in the database or false otherwise
        */
        exists = true;
        //
        if(!exists) onnewEntryfound();
        else onexistingEntryfound();
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
    public void oninsertnewEntry(String itemname,double itemprice ){

        //TODO add Entry to user database


    }

    public void onnewprice(double newprice)
    {

        //TODO update Entry in database with new price in user table




    }




}
