package groclist.edu.fsu.cs.mobile.groclist;


import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class pantryFragment extends Fragment {

    public pantryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public static pantryFragment newInstance(){
        return new pantryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pantry_list, container, false);

        final ListView listView = (ListView) view.findViewById(R.id.pantry_listview);
        String[] collumns = new String[]{"DESCRIPTION", "TOTALPRICE", "TIMESTAMP"};
        String clause = "LISTSTATUS = ?";
        String[] selargs = {"1"};
        Cursor C = getActivity().getContentResolver().query(MyContentProvider.CONTENT_URI, collumns, clause, selargs, null);
        List<String> str = new ArrayList<String>();


        int i = 0;

        //populate the list
        if (C != null) {
            while (C.moveToNext()) {
                str.add(i, (C.getString(0) + " : " + C.getFloat(1) + " : " + C.getString(2)));
                i++;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, str);
            listView.setAdapter(adapter);

            C.close();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ContentValues CV = new ContentValues();
                CV.put("LISTSTATUS", 1);

                String date = getdate(adapterView.getItemAtPosition(i).toString());
                adapterView.removeViewInLayout(view);
                getActivity().getContentResolver().update(MyContentProvider.CONTENT_URI, CV, "TIMESTAMP = '" + date + "'", null);
                listView.deferNotifyDataSetChanged();
            }
        });

        return view;
    }

    private String getdate(String str)//theres both a date AND a time:this extracts the time
    {
        str = str.substring(str.indexOf(":"));
        str = str.substring(str.indexOf(":") + 2);
        str = str.substring(str.indexOf(":") + 2);
        return str;
    }

}
