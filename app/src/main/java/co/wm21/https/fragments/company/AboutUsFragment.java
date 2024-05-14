package co.wm21.https.fragments.company;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.R;
import co.wm21.https.adapters.TeamLeadersAdapter;
import co.wm21.https.databinding.FragmentAboutUsBinding;
import co.wm21.https.model.TeamLeadersModel;


public class AboutUsFragment extends Fragment {


    FragmentAboutUsBinding binding;
    ArrayList<TeamLeadersModel> teamLeaders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAboutUsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Picasso.get().load(ConstantValues.web_url + "assets/images/shop/banner2.jpg").into(binding.header.headImage);


        binding.footerId.MyAccExpandableLayout.collapse();
        binding.footerId.CustomerExpandableLayout.collapse();
        binding.footerId.infoExpandableLayout.collapse();

        binding.whoWeAreExpandableLayout.collapse();
        binding.omExpandableLayout.collapse();
        binding.ovExpandableLayout.collapse();
        binding.oevExpandableLayout.collapse();
        binding.osExpandableLayout.collapse();
        binding.olExpandableLayout.collapse();
        binding.ompExpandableLayout.collapse();
        binding.smExpandableLayout.collapse();

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

        binding.whoWeAre.setOnClickListener(v -> {
            if (binding.whoWeAreExpandableLayout.isExpanded()) {
                binding.whoWeAreExpandableLayout.collapse();
                binding.whoWeAreExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.whoWeAreExpandableLayout.expand();
                binding.whoWeAreExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });
        binding.ourMission.setOnClickListener(v -> {
            if (binding.omExpandableLayout.isExpanded()) {
                binding.omExpandableLayout.collapse();
                binding.omExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.omExpandableLayout.expand();
                binding.omExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });
        binding.ourVision.setOnClickListener(v -> {
            if (binding.ovExpandableLayout.isExpanded()) {
                binding.ovExpandableLayout.collapse();
                binding.ovExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.ovExpandableLayout.expand();
                binding.ovExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });
        binding.ethicsAndValues.setOnClickListener(v -> {
            if (binding.oevExpandableLayout.isExpanded()) {
                binding.oevExpandableLayout.collapse();
                binding.oevExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.oevExpandableLayout.expand();
                binding.oevExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });
        binding.ourSpeciality.setOnClickListener(v -> {
            if (binding.osExpandableLayout.isExpanded()) {
                binding.osExpandableLayout.collapse();
                binding.osExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.osExpandableLayout.expand();
                binding.osExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });
        binding.ourLegality.setOnClickListener(v -> {
            if (binding.olExpandableLayout.isExpanded()) {
                binding.olExpandableLayout.collapse();
                binding.olExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.olExpandableLayout.expand();
                binding.olExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });
        binding.onlineMarketPlace.setOnClickListener(v -> {
            if (binding.ompExpandableLayout.isExpanded()) {
                binding.ompExpandableLayout.collapse();
                binding.ompExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.ompExpandableLayout.expand();
                binding.ompExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });
        binding.salesManagement.setOnClickListener(v -> {
            if (binding.smExpandableLayout.isExpanded()) {
                binding.smExpandableLayout.collapse();
                binding.smExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.smExpandableLayout.expand();
                binding.smExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });


        teamLeaders = new ArrayList<>(Arrays.asList(
                new TeamLeadersModel("Uttam kumar", "Chief Coordinator", "9.jpg"),
                new TeamLeadersModel("Feroz al Mamun", "Coordinator, Tour", "10.jpg"),
                new TeamLeadersModel("Anowar Hossain", "Coordinator, Finance", "11.jpg"),
                new TeamLeadersModel("Md Saogat Hossain", "Coordinator, Marketing", "3.jpg"),
                new TeamLeadersModel("Alamgir Hossain", "Zonal Admin, Dhaka", "4.jpg"),
                new TeamLeadersModel("Jakariea Chowdhury", "Zonal Admin, Rajshahi", "5.jpg"),
                new TeamLeadersModel("Akbar Hussain", "Zonal Admin, Sylhet", "6.jpg"),
                new TeamLeadersModel("MD Abu Sayed", "Zonal Admin, Chittagong", "7.jpg"),
                new TeamLeadersModel("Khalid Hasan", "Zonal Admin, Khulna", "8.jpg"),
                new TeamLeadersModel("Mazharul Islam", "Zonal Admin, Cumilla", "12.jpg"),
                new TeamLeadersModel("Biplop Hossain", "Zonal Admin, Faridpur", "13.jpg"),
                new TeamLeadersModel("Mizanur Rahman", "Zonal Admin, Mymensingh", "14.jpg"),
                new TeamLeadersModel("Hamidul Islam Jewel", "Zonal Admin, Bogura", "15.jpg"),
                new TeamLeadersModel("Humayun Kabir", "Zonal Admin, Rangpur", "16.jpg"),
                new TeamLeadersModel("Md Azadul Haque", "Zonal Admin, Barisal", "17.jpg")
        ));
        binding.teamLeadersRecView.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.teamLeadersRecView.setAdapter(new TeamLeadersAdapter(getContext(),teamLeaders, R.layout.layout_item_team_leader));

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