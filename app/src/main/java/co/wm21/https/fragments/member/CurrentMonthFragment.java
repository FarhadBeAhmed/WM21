package co.wm21.https.fragments.member;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import co.wm21.https.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentMonthFragment extends Fragment {


    public CurrentMonthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_month, container, false);
    }

}
