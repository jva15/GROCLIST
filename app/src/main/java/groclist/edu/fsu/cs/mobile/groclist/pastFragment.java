package groclist.edu.fsu.cs.mobile.groclist;


import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.androidplot.Plot;
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
        Button graphbutton = (Button) ll.findViewById(R.id.graph_update_button);
        ArrayList<String> places = new ArrayList<String>();
        String[] projection = {"LOCATION"};
        Cursor cursor = getActivity().getContentResolver().query(MyContentProvider.CONTENT_URI, projection, null, null, null);
        String place;
        String[] p;
        if (cursor != null) {

            //this part is for getting the items for the spinner
            while (cursor.moveToNext()) {
                //check its already on the list, if not, lose it.
                place = getplacenamefromentry(cursor.getString(0));
                Log.i("graph", "o");
                for (int i = 0; i < places.size(); i++) {
                    if (places.get(i).equals(place)) break;
                    if (i == (places.size() - 1)) places.add(place);
                }
            }
            cursor.close();
        }

        p = new String[places.size()];

        for (int i = 0; i < places.size(); i++) p[i] = places.get(i);
        ArrayAdapter<String> storeAdaptor = new ArrayAdapter<String>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, p);

        sp1.setAdapter(storeAdaptor);
        //sp2.setAdapter(storeAdaptor);

        projection = new String[]{"PRICE", "LOCATION", "TIMESTAMP"};
        cursor = getActivity().getContentResolver().query(MyContentProvider.CONTENT_URI, projection, null, null, null);

        if (cursor != null) {


            cursor.close();
        }



        plot1 = (XYPlot) ll.findViewById(R.id.plot1);
        //plot2 = (XYPlot) getView().findViewById(R.id.plot2);

        final String[] domainLabels = {"Jan", "Feb", "Mar", "Apr", "May",
        "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        Number[] series1Numbers = {1.27, 1.25, 1.23, 1.23, 1.24,
                1.23, 1.26, 1.25, 1.26, 1.24, 1.22, 1.23};

        Number[] series2Numbers = {1.29, 1.28, 1.27, 1.27, 1.26,
                1.28, 1.26, 1.25, 1.24, null, 1.27, 1.29};


        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Walmart");

        XYSeries series2 = new SimpleXYSeries(
                Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Target");

        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.BLUE, Color.BLUE, null, null);

        LineAndPointFormatter series2Format = new LineAndPointFormatter(Color.RED, Color.RED, null, null);


        plot1.addSeries(series1, series1Format);
        plot1.addSeries(series2, series2Format);
        plot1.setTitle("Gala Apples 2017");
        plot1.setDomainLabel("Month");
        plot1.setRangeLabel("Price($)/lbs");


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

        return ll;
    }

}
