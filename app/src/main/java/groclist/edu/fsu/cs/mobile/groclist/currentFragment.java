
package groclist.edu.fsu.cs.mobile.groclist;

        import android.database.Cursor;
        import android.content.ContentProvider;
        import android.content.ContentValues;
        import android.net.Uri;
        import android.os.Bundle;
        //import android.app.Fragment;
        import android.support.v4.app.Fragment;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.webkit.WebView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;

        import java.util.ArrayList;
        import java.util.List;
        import android.database.sqlite.SQLiteDatabase;
        import android.widget.ArrayAdapter;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.Button;

        import groclist.edu.fsu.cs.mobile.groclist.MyContentProvider;
        import java.util.ArrayList;

public class currentFragment extends Fragment implements View.OnClickListener {
    private static final String DB_NAME = "GROC_DB";
    private ListView listView;


    public currentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO:





    }
    public static currentFragment newInstance(){
        return new currentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.current_list, container, false);

        Button b = (Button) view.findViewById(R.id.clear_button);
        b.setOnClickListener(this);


        listView = (ListView) view.findViewById(R.id.current_listview);
        String[] collumns = new String[]{"DESCRIPTION", "TOTALPRICE", "TIMESTAMP", "LOCATION"};
        Cursor C = getActivity().getContentResolver().query(MyContentProvider.CONTENT_URI,collumns,null,null,null);
        List<String> str=  new ArrayList<String>();

        int i=0;


        if(C!=null)
        {
            while(C.moveToNext())
            {
                str.add(i, (C.getString(0) + " : $" + C.getFloat(1) + " : " + C.getString(2) + " : @" + C.getString(3)));
                i++;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, str);
            listView.setBackgroundColor(778899);


            listView.setAdapter(adapter);


            C.close();
        }


        return view;
    }


    public void onClick(View v) {
        //clear the listview - should be working
        listView.setAdapter(null);
        listView.deferNotifyDataSetChanged();


        //set liststatus=1
        ContentValues values= new ContentValues();
        values.put("LISTSTATUS", 1); //not sure how to access liststatus since I only see it in private SQL_CREATE_MAIN
        String[] liststatus = {"0"};
        getContext().getContentResolver().update(MyContentProvider.CONTENT_URI, values, "LISTSTATUS = ?", liststatus);

    }
}
