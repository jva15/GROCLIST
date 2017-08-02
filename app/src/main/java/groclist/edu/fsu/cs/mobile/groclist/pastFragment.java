package groclist.edu.fsu.cs.mobile.groclist;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.androidplot.Plot;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout ll = (LinearLayout )inflater.inflate(R.layout.past_list, container, false);


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
        febVals = 2;
        marVals = 5;
        aprVals = 4;
        mayVals = 3;
        junVals = 6;
        julVals = 9;
        augVals = 3;
        sepVals = 4;
        octVals = 1;
        novVals = 5;
        decVals = 2;
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
                //int i = Math.round(((Number) obj).floatValue());
                return toAppendTo.append(domainLabels[1]);
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
