
package groclist.edu.fsu.cs.mobile.groclist;

        import android.database.Cursor;
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

public class currentFragment extends Fragment {

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


        ListView listView = (ListView) view.findViewById(R.id.current_listview);
        String[] collumns = new String[] {"DESCRIPTION","TOTALPRICE","TIMESTAMP"};
        Cursor C = getActivity().getContentResolver().query(MyContentProvider.CONTENT_URI,collumns,null,null,null);
        List<String> str=  new ArrayList<String>();

        int i=0;


        if(C!=null)
        {
            while(C.moveToNext())
            {

                str.add(i, (C.getString(0)+ " : "+ C.getFloat(1)+ " : "+ C.getInt(2)));
                i++;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,str);
            listView.setAdapter(adapter);

            C.close();
        }


        return view;
    }

}