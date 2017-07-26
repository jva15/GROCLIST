package groclist.edu.fsu.cs.mobile.groclist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EntryActivity extends AppCompatActivity implements ExistingEntryFragment.OnFragmentInteractionListener,NewEntryFragment.OnFragmentInteractionListener {

    public FragmentManager FM = getSupportFragmentManager();
    public FragmentTransaction FT;

    String BarContent;
    String BarFormat;
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

        /*TODO: check if works
        */
        exists = false;
        Cursor itemsearch = getContentResolver().query(MyContentProvider.CONTENT_URI,null,null,null,null);
        if(itemsearch!=null) {
            while (itemsearch.moveToNext()) {
                if (itemsearch.getString(0).equals(BarContent)) {
                    exists = true;
                    pluorupc = 1;


                }
                if (itemsearch.getString(1).equals(BarContent)) {
                    exists = true;
                    pluorupc = 2;
                }
            }
            itemsearch.close();
        }

        //
        if(!exists)
            onnewEntryfound();
        else {
            onexistingEntryfound();
            name = itemsearch.getString(5);};
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

        //TODO add Entry to user database
        ContentValues CV = new ContentValues();
        if(pluorupc==1)
            CV.put("PLU",BarContent);
        if(pluorupc==2)
            CV.put("UPC",BarContent);
        CV.put("PRICE",itemprice);
        CV.put("DESCRIPTION",itemname);
        CV.put("TOTALPRICE",itemprice);
        CV.put("LISTSTATUS",0);
        getContentResolver().insert(MyContentProvider.CONTENT_URI,CV);



        returntomain();
    }

    public void onnewprice(float newprice)
    {


        ContentValues CV = new ContentValues();
        if(pluorupc==1)
        CV.put("PLU",BarContent);
        if(pluorupc==2)
            CV.put("UPC",BarContent);
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
