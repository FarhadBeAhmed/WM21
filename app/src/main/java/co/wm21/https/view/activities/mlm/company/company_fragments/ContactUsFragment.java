package co.wm21.https.view.activities.mlm.company.company_fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.wm21.https.R;
import co.wm21.https.databinding.FragmentContactUsBinding;

public class ContactUsFragment extends Fragment {


    FragmentContactUsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentContactUsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.footerId.MyAccExpandableLayout.collapse();
        binding.footerId.CustomerExpandableLayout.collapse();
        binding.footerId.infoExpandableLayout.collapse();

        binding.footerId.footerCompany.setOnClickListener(v -> {
            if (binding.footerId.infoExpandableLayout.isExpanded()) {
                binding.footerId.infoExpandableLayout.collapse();
                binding.footerId.infExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.footerId.infoExpandableLayout.expand();
                binding.footerId.infExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });
        binding.footerId.footerMyAccount.setOnClickListener(v -> {
            if (binding.footerId.MyAccExpandableLayout.isExpanded()) {
                binding.footerId.MyAccExpandableLayout.collapse();
                binding.footerId.MyAccExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.footerId.MyAccExpandableLayout.expand();
                binding.footerId.MyAccExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });
        binding.footerId.footerCustomer.setOnClickListener(v -> {
            if (binding.footerId.CustomerExpandableLayout.isExpanded()) {
                binding.footerId.CustomerExpandableLayout.collapse();
                binding.footerId.CustomerExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.footerId.CustomerExpandableLayout.expand();
                binding.footerId.CustomerExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });

        binding.q1ExpandableLayout.collapse();
        binding.q1headerLayout.setOnClickListener(v -> {
            if (binding.q1ExpandableLayout.isExpanded()) {
                binding.q1ExpandableLayout.collapse();
                binding.q1ExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.q1ExpandableLayout.expand();
                binding.q1ExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });

        binding.q2ExpandableLayout.collapse();
        binding.q2headerLayout.setOnClickListener(v -> {
            if (binding.q2ExpandableLayout.isExpanded()) {
                binding.q2ExpandableLayout.collapse();
                binding.q2ExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.q2ExpandableLayout.expand();
                binding.q2ExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });

        binding.q3ExpandableLayout.collapse();
        binding.q3headerLayout.setOnClickListener(v -> {
            if (binding.q3ExpandableLayout.isExpanded()) {
                binding.q3ExpandableLayout.collapse();
                binding.q3ExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.q3ExpandableLayout.expand();
                binding.q3ExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });

        binding.q4ExpandableLayout.collapse();
        binding.q4headerLayout.setOnClickListener(v -> {
            if (binding.q4ExpandableLayout.isExpanded()) {
                binding.q4ExpandableLayout.collapse();
                binding.q4ExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.q4ExpandableLayout.expand();
                binding.q4ExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });

        binding.q5ExpandableLayout.collapse();
        binding.q5headerLayout.setOnClickListener(v -> {
            if (binding.q5ExpandableLayout.isExpanded()) {
                binding.q5ExpandableLayout.collapse();
                binding.q5ExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.q5ExpandableLayout.expand();
                binding.q5ExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });

        binding.q5ExpandableLayout.collapse();
        binding.q5headerLayout.setOnClickListener(v -> {
            if (binding.q5ExpandableLayout.isExpanded()) {
                binding.q5ExpandableLayout.collapse();
                binding.q5ExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.q5ExpandableLayout.expand();
                binding.q5ExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });


        binding.footerId.aboutUs.setOnClickListener(view1 -> {
            switchFragment(new AboutUsFragment(),"AboutUsFragment");
        });
        binding.footerId.contactUs.setOnClickListener(view1 -> {
            switchFragment(new ContactUsFragment(),"ContactUsFragment");
        });

    }
    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = getFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).addToBackStack(tag).commit();
    }
}