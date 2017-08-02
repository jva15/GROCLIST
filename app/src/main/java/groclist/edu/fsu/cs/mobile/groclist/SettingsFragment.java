package groclist.edu.fsu.cs.mobile.groclist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SettingsFragment extends Fragment {


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View SEview = (View) inflater.inflate(R.layout.fragment_settings, container, false);
        Button startservice = (Button) SEview.findViewById(R.id.expcheckbutton);
        Button stopservice = (Button) SEview.findViewById(R.id.stopcheck);

        Intent expirationcheck = new Intent(getContext(), ExpirationService.class);


        startservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent expirationcheck = new Intent(getContext(), ExpirationService.class);
                getActivity().startService(expirationcheck);

            }
        });
        stopservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stopcheck = new Intent(getContext(), ExpirationService.class);
                getActivity().stopService(stopcheck);

            }
        });
        return SEview;

    }


}
