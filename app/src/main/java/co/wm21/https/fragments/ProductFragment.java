package co.wm21.https.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.wm21.https.R;
import co.wm21.https.databinding.FragmentProductBinding;


public class ProductFragment extends Fragment {
    FragmentProductBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false);


        return binding.getRoot();
    }
}