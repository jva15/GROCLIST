package groclist.edu.fsu.cs.mobile.groclist;



import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FragmentManager m = getSupportFragmentManager();
        FragmentTransaction tran = m.beginTransaction();

        //showing mainFragment at first
        mainFragment mf = mainFragment.newInstance();
        tran.add(R.id.main_frame, mf, "MAIN_FRAG");
        tran.commit();



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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
       // Toast toast = Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_LONG);
        //toast.show();
        int id = item.getItemId();

        if (id == R.id.current_cart) {



            //check and see if the current fragment is mainFrag
            Fragment f = this.getSupportFragmentManager().findFragmentByTag("MAIN_FRAG");
            if(f instanceof mainFragment){
                FragmentManager m = getSupportFragmentManager();
                FragmentTransaction tran = m.beginTransaction();

                currentFragment cf = currentFragment.newInstance();
                //tran.replace(R.id.main_frame, cf, "CURRENT_FRAG");
                //tran.addToBackStack(null);
                m.beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("MAIN_FRAG")).commit();


                tran.add(R.id.current_frame, cf, "CURRENT_FRAG");
                tran.commit();

            }

            //check to see if the the current fragment is past_frag
            Fragment f1 = this.getSupportFragmentManager().findFragmentByTag("PAST_FRAG");
            if(f1 instanceof pastFragment){
                FragmentManager m = getSupportFragmentManager();
                FragmentTransaction tran = m.beginTransaction();

                currentFragment cf = currentFragment.newInstance();
                //tran.replace(R.id.main_frame, cf, "CURRENT_FRAG");
                //tran.addToBackStack(null);
                m.beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("PAST_FRAG")).commit();


                tran.add(R.id.current_frame, cf, "CURRENT_FRAG");
                tran.commit();

            }

            //check to see if current visible frag is pantry_frag
            Fragment f2 = this.getSupportFragmentManager().findFragmentByTag("PANTRY_FRAG");
            if(f2 instanceof pantryFragment) {
                FragmentManager m = getSupportFragmentManager();
                FragmentTransaction tran = m.beginTransaction();

                currentFragment cf = currentFragment.newInstance();
                //tran.replace(R.id.main_frame, cf, "CURRENT_FRAG");
                //tran.addToBackStack(null);
                m.beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("PANTRY_FRAG")).commit();


                tran.add(R.id.current_frame, cf, "CURRENT_FRAG");
                tran.commit();
            }





        } else if (id == R.id.past_purchases) {
            //check to see if current frag is main_frag
            Fragment f = this.getSupportFragmentManager().findFragmentByTag("MAIN_FRAG");
            if(f instanceof mainFragment){
                FragmentManager m = getSupportFragmentManager();
                FragmentTransaction tran = m.beginTransaction();

                pastFragment pf = pastFragment.newInstance();

                m.beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("MAIN_FRAG")).commit();


                tran.add(R.id.past_frame, pf, "PAST_FRAG");
                tran.commit();

            }

            //check to see if the the current fragment is current_frag
            Fragment f1 = this.getSupportFragmentManager().findFragmentByTag("CURRENT_FRAG");
            if(f1 instanceof currentFragment){
                FragmentManager m = getSupportFragmentManager();
                FragmentTransaction tran = m.beginTransaction();

                pastFragment pf = pastFragment.newInstance();
                //tran.replace(R.id.main_frame, cf, "CURRENT_FRAG");
                //tran.addToBackStack(null);
                m.beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("CURRENT_FRAG")).commit();


                tran.add(R.id.past_frame, pf, "PAST_FRAG");
                tran.commit();

            }

            //check to see if current visible frag is pantry_frag
            Fragment f2 = this.getSupportFragmentManager().findFragmentByTag("PANTRY_FRAG");
            if(f2 instanceof pantryFragment) {
                FragmentManager m = getSupportFragmentManager();
                FragmentTransaction tran = m.beginTransaction();

                pastFragment pf = pastFragment.newInstance();
                //tran.replace(R.id.main_frame, cf, "CURRENT_FRAG");
                //tran.addToBackStack(null);
                m.beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("PANTRY_FRAG")).commit();


                tran.add(R.id.past_frame, pf, "PAST_FRAG");
                tran.commit();
            }

        } else if (id == R.id.my_pantry) {

            //check to see if current frag is main_frag
            Fragment f = this.getSupportFragmentManager().findFragmentByTag("MAIN_FRAG");
            if(f instanceof mainFragment){
                FragmentManager m = getSupportFragmentManager();
                FragmentTransaction tran = m.beginTransaction();

                pantryFragment pf = pantryFragment.newInstance();

                m.beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("MAIN_FRAG")).commit();


                tran.add(R.id.pantry_frame, pf, "PANTRY_FRAG");
                tran.commit();

            }

            //check to see if the the current fragment is current_frag
            Fragment f1 = this.getSupportFragmentManager().findFragmentByTag("CURRENT_FRAG");
            if(f1 instanceof currentFragment){
                FragmentManager m = getSupportFragmentManager();
                FragmentTransaction tran = m.beginTransaction();

                pantryFragment pf = pantryFragment.newInstance();
                //tran.replace(R.id.main_frame, cf, "CURRENT_FRAG");
                //tran.addToBackStack(null);
                m.beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("CURRENT_FRAG")).commit();


                tran.add(R.id.pantry_frame, pf, "PANTRY_FRAG");
                tran.commit();

            }

            //check to see if current visible fragment is past_frag
            Fragment f2 = this.getSupportFragmentManager().findFragmentByTag("PAST_FRAG");
            if(f2 instanceof pastFragment){
                FragmentManager m = getSupportFragmentManager();
                FragmentTransaction tran = m.beginTransaction();

                pantryFragment pf = pantryFragment.newInstance();
                //tran.replace(R.id.main_frame, cf, "CURRENT_FRAG");
                //tran.addToBackStack(null);
                m.beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("PAST_FRAG")).commit();


                tran.add(R.id.pantry_frame, pf, "PANTRY_FRAG");
                tran.commit();

            }
        }
        else if(id==R.id.home_button){
            //checking to see if its in current_frag
            Fragment f = this.getSupportFragmentManager().findFragmentByTag("CURRENT_FRAG");
            if(f instanceof currentFragment){
                FragmentManager m = getSupportFragmentManager();
                FragmentTransaction tran = m.beginTransaction();
                m.beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("CURRENT_FRAG")).commit();


                mainFragment mf = mainFragment.newInstance();
                tran.add(R.id.main_frame, mf, "MAIN_FRAG");
                tran.commit();
               // tran.add(R.id.current_frame, mf, "MAIN_TAG");
                //tran.addToBackStack(null);

               // tran.commit();
            }

            //check to see if current frame is past_frag
            Fragment f1 = this.getSupportFragmentManager().findFragmentByTag("PAST_FRAG");
            if(f1 instanceof pastFragment){
                FragmentManager m = getSupportFragmentManager();
                FragmentTransaction tran = m.beginTransaction();
                m.beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("PAST_FRAG")).commit();


                mainFragment mf = mainFragment.newInstance();
                tran.add(R.id.main_frame, mf, "MAIN_FRAG");
                tran.commit();
                // tran.add(R.id.current_frame, mf, "MAIN_TAG");
                //tran.addToBackStack(null);

                // tran.commit();
            }
            //check to see if current visible frag is pantry_frag
            Fragment f2 = this.getSupportFragmentManager().findFragmentByTag("PANTRY_FRAG");
            if(f2 instanceof pantryFragment) {
                FragmentManager m = getSupportFragmentManager();
                FragmentTransaction tran = m.beginTransaction();

                mainFragment mf = mainFragment.newInstance();
                //tran.replace(R.id.main_frame, cf, "CURRENT_FRAG");
                //tran.addToBackStack(null);
                m.beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("PANTRY_FRAG")).commit();


                tran.add(R.id.main_frame, mf, "MAIN_FRAG");
                tran.commit();
            }


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}


