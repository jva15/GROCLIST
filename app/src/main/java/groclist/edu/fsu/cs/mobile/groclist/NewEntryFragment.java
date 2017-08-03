package groclist.edu.fsu.cs.mobile.groclist;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class NewEntryFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "Content";
    private static final String ARG_PARAM2 = "Format";

    private String mParam1;
    private String mParam2;

    public String itemname = null;
    float itemprice = 0;
    private OnFragmentInteractionListener mListener;

    public NewEntryFragment() {
        // Required empty public constructor
    }


    public static NewEntryFragment newInstance(String param1, String param2) {
        NewEntryFragment fragment = new NewEntryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View NEview = inflater.inflate(R.layout.fragment_new_entry, container, false);

        Button addbutton = (Button) NEview.findViewById(R.id.add_existing);
        final TextView itemnamev = (TextView) NEview.findViewById(R.id.new_item);
        final TextView itempricev = (TextView) NEview.findViewById(R.id.How_much);



        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemname = itemnamev.getText().toString();
                String ip = itempricev.getText().toString();
                itemprice = Float.parseFloat(ip);

                if(itemname !=null)
                {
                    mListener.oninsertnewEntry(itemname,itemprice);

                } else {
                    mListener.oninsertnewEntry("nulled", itemprice);
                }


            }
        });

        return NEview;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {

        void oninsertnewEntry(String P1,float P2);
    }
}
