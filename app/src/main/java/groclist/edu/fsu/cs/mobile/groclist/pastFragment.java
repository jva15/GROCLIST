package groclist.edu.fsu.cs.mobile.groclist;


import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;


public class pastFragment extends Fragment {
    private XYPlot plot1;
    String lbsOritem;
    Boolean updateFlag = false;
    float janVals;
    float febVals;
    float marVals;
    float aprVals;
    float mayVals;
    float junVals;
    float julVals;
    float augVals;
    float sepVals;
    float octVals;
    float novVals;
    float decVals;
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

    private int getmonthfromentry(String str)//gets month from cursor result from "LOCATION"
    {
        Log.i("M", str);
        Log.i("M", str.substring(5, 7));
        str = str.substring(5, 7);
        return Integer.parseInt(str);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout ll = (LinearLayout )inflater.inflate(R.layout.past_list, container, false);
        itemSearch = ll.findViewById(R.id.graph_item_select);
        updateButton = ll.findViewById(R.id.graph_update_button);

        final Spinner sp1 = (Spinner) ll.findViewById(R.id.spinner1);

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
                        else if (i == places.size() - 1) {

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

        plot1 = ll.findViewById(R.id.plot1);
        plot1.setTitle("");
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String placename = sp1.getSelectedItem().toString();
                String[] nprojection = new String[]{"PRICE", "LOCATION", "TIMESTAMP"};


        final float[] monthslot = new float[12];
        for(int i=0;i<12;i++)monthslot[i]=0;
        int month = 0;
        float price = 0;
                String select = itemSearch.getText().toString();
                Cursor kursor = getActivity().getContentResolver().query(MyContentProvider.CONTENT_URI, nprojection, "DESCRIPTION LIKE '%" + select + "%' AND LOCATION LIKE '%" + placename + "%'", null, "TIMESTAMP");
                if (kursor != null) {

                    if (kursor.getCount() != 0) {
                updateFlag = true;
                        while (kursor.moveToNext()) {

                            Log.i("month", kursor.getString(2));
                            month = getmonthfromentry(kursor.getString(2));
                            price = kursor.getFloat(0);

                    if(monthslot[month - 1] == 0) {
                        monthslot[month - 1] = price;
                    }
                    else{
                        monthslot[month - 1] = (price + monthslot[month - 1])/2;
                    }

                }


            }
            else{
                Toast.makeText(getContext(),"Store/item not found.",Toast.LENGTH_SHORT).show();
            }

                    kursor.close();
        }


                if (updateFlag == true) {
                plot1.clear();
                    janVals = monthslot[0];
                    febVals = monthslot[1];
                    marVals = monthslot[2];
                    aprVals = monthslot[3];
                    mayVals = monthslot[4];
                    junVals = monthslot[5];
                    julVals = monthslot[6];
                    augVals = monthslot[7];
                    sepVals = monthslot[8];
                    octVals = monthslot[9];
                    novVals = monthslot[10];
                    decVals = monthslot[11];
                lbsOritem = "Price/lbs";
                currentYear = "2017";


                Number[] series1Numbers = {janVals, febVals, marVals, aprVals, mayVals,
                        junVals, julVals, augVals, sepVals, octVals, novVals, decVals};


                    XYSeries series1 = new SimpleXYSeries(Arrays.asList(monthNums), Arrays.asList(series1Numbers), sp1.getSelectedItem().toString());

                    LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.BLUE, Color.BLUE, null, null);


                    plot1.addSeries(series1, series1Format);

                    plot1.setTitle(itemSearch.getText().toString() + " at " + sp1.getSelectedItem().toString() + " in " + currentYear);

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

            }
        });

        return ll;

    }

}
