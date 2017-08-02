package groclist.edu.fsu.cs.mobile.groclist;

import android.*;
import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class mainFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    public mainFragment() {
    }

    String store = "";
    LocationManager lm;
    ArrayList<String> addresses = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null)//args goes null sometimes for some reason.
        addresses = args.getStringArrayList("address");


        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

    }
    public static mainFragment newInstance(){
        return new mainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_frag, container, false);
        String[] totalcollumn = {"TOTALPRICE"};
        float total=0;
        TextView TP = (TextView) view.findViewById(R.id.total);
        final EditText UPCinput = (EditText) view.findViewById(R.id.type);
        final EditText priceperpoundinpute = (EditText) view.findViewById(R.id.price);
        final EditText poundsinput = (EditText) view.findViewById(R.id.pounds);
        Button ADD_Button = (Button) view.findViewById(R.id.add_button);
        Spinner storeSpinner = (Spinner) view.findViewById(R.id.store);

        if (addresses == null) {
            addresses = new ArrayList<String>();
            addresses.add("NONE");
        }
        ArrayAdapter<String> storeAdaptor = new ArrayAdapter<String>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, addresses);
        storeSpinner.setAdapter(storeAdaptor);


        storeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String place;
                place = adapterView.getSelectedItem().toString();
                store = place;
                mListener.setplace(store);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mListener.setplace("NONE");

            }
        });


        //Tally up the total expenses and display them
        Cursor C = getActivity().getContentResolver().query(MyContentProvider.CONTENT_URI, totalcollumn, "LISTSTATUS = 0", null, null);
        String str ="";
        if(C!=null)
        {
            while(C.moveToNext())
            {
                total=total+C.getFloat(0);
            }
            C.close();

        }
        str = ""+total;
        TP.setText(str);


        //code for the second set of fields
        ADD_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //get loc ahead of time
                Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                int PLU;
                String name = "";
                float price = 0;
                int pound = 0;
                float total = 0;
                String code = UPCinput.getText().toString();

                Location previouscoords;
                Boolean permission_granted =
                        ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED;


                //retrieve
                String PPPinp = priceperpoundinpute.getText().toString();
                String LBSinp = poundsinput.getText().toString();
                DatabaseAccess DATA = DatabaseAccess.getInstance(getContext());
                Boolean incomplete = false;
                if (UPCinput.getText().toString().equals("")) {
                    UPCinput.setError("Please specify");
                    incomplete = true;
                }
                if (PPPinp.equals("")) {
                    priceperpoundinpute.setError("Please specify");
                    incomplete = true;
                    }
                if (LBSinp.equals("")) {
                    poundsinput.setError("Please specify");
                    incomplete = true;
                }
                if (incomplete) {
                    incomplete = false;
                    return;
                }

                PLU = Integer.parseInt(code);

                //reset
                priceperpoundinpute.setText("");
                poundsinput.setText("");
                UPCinput.setText("");

                price = Float.parseFloat(PPPinp);
                pound = Integer.parseInt(LBSinp);
                total = pound * price;

                //check the produce database and retreive name;
                Cursor CURSOR = DATA.getQuotes("PLU = " + PLU);
                ContentValues CVs = new ContentValues();

                if (CURSOR != null && CURSOR.getCount() != 0) {

                    while (CURSOR.moveToNext()) {
                        name = CURSOR.getString(2) + " " + CURSOR.getString(1);
                    }
                    CURSOR.close();
                    Toast.makeText(getContext(), name + " added", Toast.LENGTH_LONG).show();
                } else Toast.makeText(getContext(), "invalid PLU", Toast.LENGTH_LONG).show();
                DATA.close();

                //place values into user database

                if (permission_granted) {
                    previouscoords = loc;
                    if (previouscoords != null)
                        CVs.put("LOCATION", store + " : " + previouscoords.getLatitude() + "," +
                                previouscoords.getLongitude());
                } else {
                    CVs.put("LOCATION", "NONE");
                }


                CVs.put("PLU", code);
                CVs.put("UPC", 0);
                CVs.put("PRICE", price);
                CVs.put("DESCRIPTION", name);
                CVs.put("LISTSTATUS", 0);
                CVs.put("TOTALPRICE", total);
                getActivity().getContentResolver().insert(MyContentProvider.CONTENT_URI, CVs);

            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void setplace(String place);
    }
}
