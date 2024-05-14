package co.wm21.https.fragments.productDetails;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.wm21.https.R;
import co.wm21.https.databinding.FragmentOtherDetailsBinding;

public class OtherDetailsFragment extends Fragment {
    FragmentOtherDetailsBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_other_details, container, false);

        return binding.getRoot();
    }
}