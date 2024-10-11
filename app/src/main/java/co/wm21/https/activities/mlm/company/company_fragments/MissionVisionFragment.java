package co.wm21.https.activities.mlm.company.company_fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import co.wm21.https.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MissionVisionFragment extends Fragment {


    public MissionVisionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mission_vision, container, false);
    }

}
