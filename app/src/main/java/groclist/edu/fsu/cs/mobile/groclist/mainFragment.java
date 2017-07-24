package groclist.edu.fsu.cs.mobile.groclist;

import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

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


        return view;
    }

}
