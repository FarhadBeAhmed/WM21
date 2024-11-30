package co.wm21.https.view.fragments.shops;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import co.wm21.https.databinding.FragmentMissionBazarBinding;

public  class MissionBazar extends Fragment {
    FragmentMissionBazarBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMissionBazarBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}
