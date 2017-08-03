package groclist.edu.fsu.cs.mobile.groclist;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class ExistingEntryFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "Content";
    private static final String ARG_PARAM2 = "Format";
    private static final String ARG_PARAM3 = "Place";


    private String mParam1;
    private String mParam2;
    private String mParam3;

    public String ip = null;
    public float itemprice = 0;

    public OnFragmentInteractionListener mListener;

    public ExistingEntryFragment() {
        // Required empty public constructor
    }


    public static ExistingEntryFragment newInstance(String param1, String param2) {
        ExistingEntryFragment fragment = new ExistingEntryFragment();
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


        View EEview =  inflater.inflate(R.layout.fragment_existing_entry, container, false);
        TextView TV = (TextView) EEview.findViewById(R.id.textView2);
        Button addbutton = (Button) EEview.findViewById(R.id.add_existing);
        final TextView itempricev = (TextView) EEview.findViewById(R.id.new_entered_price);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ip = itempricev.getText().toString();
                if(!ip.equals(""))
                {
                    itemprice = Float.parseFloat(ip);
                    mListener.onnewprice(itemprice);
                }
            }
        });
        return EEview;
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
        void onnewprice(float newprice);
    }
}
