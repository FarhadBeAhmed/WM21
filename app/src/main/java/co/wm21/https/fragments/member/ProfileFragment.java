package co.wm21.https.fragments.member;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.wm21.https.R;
import co.wm21.https.helpers.Constant;
import co.wm21.https.helpers.User;


public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.basicInfo).setOnClickListener(v -> switchFragment(new ProfileInfoFragment()));
        view.findViewById(R.id.personalInfo).setOnClickListener(v -> switchFragment(new ProfileInfoFragment()));
        view.findViewById(R.id.documentInfo).setOnClickListener(v -> switchFragment(new ProfileInfoFragment()));
        view.findViewById(R.id.addressInfo).setOnClickListener(v -> switchFragment(new ProfileInfoFragment()));
        view.findViewById(R.id.nomineeInfo).setOnClickListener(v -> switchFragment(new ProfileInfoFragment()));
        view.findViewById(R.id.additionalInfo).setOnClickListener(v -> switchFragment(new ProfileInfoFragment()));
    }

    public void switchFragment(Fragment fragment) {
        FragmentManager fm = getParentFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
    }
}