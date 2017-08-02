package groclist.edu.fsu.cs.mobile.groclist;


import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Selection;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.androidplot.Plot;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.io.StringBufferInputStream;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class pastFragment extends Fragment {
    private XYPlot plot1;
    String lbsOritem;
    int janVals;
    int febVals;
    int marVals;
    int aprVals;
    int mayVals;
    int junVals;
    int julVals;
    int augVals;
    int sepVals;
    int octVals;
    int novVals;
    int decVals;
    String currentYear;
    Button updateButton;
    EditText itemSearch;
    EditText storeSearch;
    final String[] domainLabels = {"Jan", "Feb", "Mar", "Apr", "May",
            "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    final Number[] monthNums = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};



    public pastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);






    }
    public static pastFragment newInstance(){
        return new pastFragment();
    }

    private String getplacenamefromentry(String str)//theres an address AND a set of coordinates:this extracts the address
    {
        return str.substring(0, str.indexOf(":"));
    }

    private String getdatefromentryonly(String str)//theres both a date AND a time:this extracts the time
    {
        return str.substring(0, 10);
    }

    private int getdayentry(String str)//gets day from cursor result from "LOCATION"
    {
        return Integer.getInteger(str.substring(8, 10));
    }

    private int getmonthfromentry(String str)//gets month from cursor result from "LOCATION"
    {
        return Integer.getInteger(str.substring(5, 7));
    }

    private int getyearfromentry(String str)//gets year from cursor result from "LOCATION"
    {
        return Integer.getInteger(str.substring(0, 4));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout ll = (LinearLayout )inflater.inflate(R.layout.past_list, container, false);

        Spinner sp1 = (Spinner) ll.findViewById(R.id.spinner1);
        Spinner sp2 = (Spinner) ll.findViewById(R.id.spinner2);
        ArrayList<String> places = new ArrayList<String>();
        String[] projection = {"LOCATION"};
        Cursor cursor = getActivity().getContentResolver().query(MyContentProvider.CONTENT_URI, projection, null, null, null);
        String place = "";
        String[] p;
        if (cursor != null) {

            //this part is for getting the items for the spinner
            while (cursor.moveToNext()) {
                //check its already on the list, if not, lose it.
                place = getplacenamefromentry(cursor.getString(0));
                if (places.size() == 0) {
                    places.add(place);
                } else {

                    for (int i = 0; i < places.size(); i++) {

                        if (places.get(i).equals(place)) break;
                        else {

                            places.add(place);
                            break;
                        }
                    }
                }
            }
            cursor.close();
        }

        p = new String[places.size()];

        for (int i = 0; i < places.size(); i++) p[i] = places.get(i);
        ArrayAdapter<String> storeAdaptor = new ArrayAdapter<String>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, p);
        sp1.setAdapter(storeAdaptor);


        projection = new String[]{"PRICE", "LOCATION", "TIMESTAMP"};
        String nameselection;//where you put the spinner result
        String locationselection;//
        float[] monthslot1 = new float[12];
        int month = 0;
        float price = 0;/*
        String[] selectionargs= {nameselection,locationselection};
        cursor = getActivity().getContentResolver().query(MyContentProvider.CONTENT_URI, projection, "DESCRIPTION = ? AND LOCATION LIKE %?%", selectionargs, "TIMESTAMP");
        if(cursor!=null) {

            while(cursor.moveToNext())
            {
                month=getmonthfromentry(cursor.getString(2));
                price=cursor.getFloat(0);


                monthslot[month-1]=price;

            }



            cursor.close();
        }

*/
        plot1 = (XYPlot) ll.findViewById(R.id.plot1);

        itemSearch = ll.findViewById(R.id.graph_item_select);
        storeSearch = ll.findViewById(R.id.graph_store_select);
        updateButton = ll.findViewById(R.id.graph_update_button);
        plot1 = ll.findViewById(R.id.plot1);
        plot1.setTitle("");
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        plot1.clear();
        janVals = 0;
        febVals = 0;
        marVals = 0;
        aprVals = 0;
        mayVals = 0;
        junVals = 0;
        julVals = 0;
        augVals = 0;
        sepVals = 0;
        octVals = 0;
        novVals = 0;
        decVals = 0;
        lbsOritem = "Price/lbs";
        currentYear = "2017";




        Number[] series1Numbers = {janVals, febVals, marVals, aprVals, mayVals,
                junVals, julVals, augVals, sepVals, octVals, novVals, decVals};


        XYSeries series1 = new SimpleXYSeries(Arrays.asList(monthNums), Arrays.asList(series1Numbers), storeSearch.getText().toString());

        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.BLUE, Color.BLUE, null, null);



        plot1.addSeries(series1, series1Format);

        plot1.setTitle(itemSearch.getText().toString() + " at " + storeSearch.getText().toString() + " in " + currentYear);

        plot1.setDomainLabel("Month");
        plot1.setRangeLabel(lbsOritem);
        plot1.setDomainStep(StepMode.INCREMENT_BY_VAL, 1);



        plot1.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                int i = Math.round(((Number) obj).floatValue());
                return toAppendTo.append(domainLabels[i]);
            }

            @Override
            public Object parseObject(String source, ParsePosition pos) {

                return null;
            }
        });
                plot1.redraw();

            }
        });

        return ll;
    }

}
