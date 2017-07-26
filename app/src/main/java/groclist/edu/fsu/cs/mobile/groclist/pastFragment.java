package groclist.edu.fsu.cs.mobile.groclist;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androidplot.Plot;
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

        plot1 = (XYPlot) ll.findViewById(R.id.plot1);
        //plot2 = (XYPlot) getView().findViewById(R.id.plot2);

        final String[] domainLabels = {"Jan", "Feb", "Mar", "Apr", "May",
        "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        Number[] series1Numbers = {1.27, 1.25, 1.23, 1.23, 1.24,
                1.23, 1.26, 1.25, 1.26, 1.24, 1.22, 1.23};

        Number[] series2Numbers = {1.29, 1.28, 1.27, 1.27, 1.26,
                1.28, 1.26, 1.25, 1.24, 1.26, 1.27, 1.29};


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
