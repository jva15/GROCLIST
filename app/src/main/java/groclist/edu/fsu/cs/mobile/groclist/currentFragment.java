
package groclist.edu.fsu.cs.mobile.groclist;

        import android.os.Bundle;
        //import android.app.Fragment;
        import android.support.v4.app.Fragment;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.webkit.WebView;

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


        return view;
    }

}