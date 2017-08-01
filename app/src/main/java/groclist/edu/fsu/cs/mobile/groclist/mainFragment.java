package groclist.edu.fsu.cs.mobile.groclist;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
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
        final EditText UPCinput = (EditText) view.findViewById(R.id.type);
        final EditText priceperpoundinpute = (EditText) view.findViewById(R.id.price);
        final EditText poundsinput = (EditText) view.findViewById(R.id.pounds);
        Button ADD_Button = (Button) view.findViewById(R.id.add_button);

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


        ADD_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = "";
                float price=0;
                int pound= 0;
                float total= 0;
                String code= UPCinput.getText().toString();

                int PLU = Integer.parseInt(code);

                String PPPinp = priceperpoundinpute.getText().toString();
                String LBSinp = poundsinput.getText().toString();
                DatabaseAccess DATA = DatabaseAccess.getInstance(getContext());

                price = Float.parseFloat(PPPinp);
                pound = Integer.parseInt(LBSinp);
                total = pound*price;
                Cursor CURSOR = DATA.getQuotes("PLU = "+ PLU);
                ContentValues CVs= new ContentValues();

                //check the produce database and retreive name;
                if(CURSOR!=null) {
                    while (CURSOR.moveToNext()) {
                        name = CURSOR.getString(2)+" "+CURSOR.getString(1);
                    }
                    CURSOR.close();
                }
                DATA.close();

                CVs.put("PLU",code);
                CVs.put("UPC",0);
                CVs.put("PRICE", price);
                CVs.put("DESCRIPTION",name);
                CVs.put("LISTSTATUS",0);
                CVs.put("TOTALPRICE",total);
                getActivity().getContentResolver().insert(MyContentProvider.CONTENT_URI,CVs);


            }
        });

        return view;
    }

}
