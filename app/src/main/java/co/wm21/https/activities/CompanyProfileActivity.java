package com.wm21ltd.wm21.activities;

import android.os.Build;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.WindowManager;

import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.adapters.ViewPagerAdapter;
import com.wm21ltd.wm21.fragments.AboutUsFragment;
import com.wm21ltd.wm21.fragments.BusinessOpportunityFragment;
import com.wm21ltd.wm21.fragments.CompanyManagementFragment;
import com.wm21ltd.wm21.fragments.FuturePlanFragment;
import com.wm21ltd.wm21.fragments.MissionVisionFragment;

import java.util.ArrayList;

public class CompanyProfileActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> fragmentTitleList;
    private TabLayout companyProfileTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_companyProfile);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Company Profile");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        getFragmentList();
        getFragmentTitleList();

        viewPager = findViewById(R.id.company_profile_pager);
        companyProfileTabLayout = findViewById(R.id.company_profile_tab_layout);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList,fragmentTitleList);
        viewPager.setOffscreenPageLimit(10);
        viewPager.setAdapter(viewPagerAdapter);
        companyProfileTabLayout.setupWithViewPager(viewPager);
    }

    private void getFragmentTitleList() {

        fragmentTitleList = new ArrayList<>();
        fragmentTitleList.add("About Us");
        fragmentTitleList.add("Mission & Vision");
        fragmentTitleList.add("Management");
        fragmentTitleList.add("Business Opportunity");
        fragmentTitleList.add("Future Plan");

    }

    private void getFragmentList() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new AboutUsFragment());
        fragmentList.add(new MissionVisionFragment());
        fragmentList.add(new CompanyManagementFragment());
        fragmentList.add(new BusinessOpportunityFragment());
        fragmentList.add(new FuturePlanFragment());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
