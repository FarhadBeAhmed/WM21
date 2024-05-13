package com.wm21ltd.wm21.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wm21ltd.wm21.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyLoanFragment extends Fragment {


    public MyLoanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_loan, container, false);
    }

}
