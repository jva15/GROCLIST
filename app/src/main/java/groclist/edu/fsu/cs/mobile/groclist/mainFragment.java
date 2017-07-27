package groclist.edu.fsu.cs.mobile.groclist;

import android.database.Cursor;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class mainFragment extends Fragment {

    public mainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO:





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
        Cursor C = getActivity().getContentResolver().query(MyContentProvider.CONTENT_URI,totalcollumn,null,null,null);
        TextView TP = (TextView) view.findViewById(R.id.total);
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
        return view;
    }

}
