package co.wm21.https.fragments.shops;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import co.wm21.https.databinding.FragmentShowroomBinding;

public  class Showroom extends Fragment {
    FragmentShowroomBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShowroomBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}