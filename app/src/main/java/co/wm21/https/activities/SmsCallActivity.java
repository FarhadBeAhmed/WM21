package com.wm21ltd.wm21.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.adapters.ViewPagerAdapter;
import com.wm21ltd.wm21.fragments.BrandAmbassadorFragment;
import com.wm21ltd.wm21.fragments.CommonBloodFragment;
import com.wm21ltd.wm21.fragments.CustomerCareFragment;
import com.wm21ltd.wm21.fragments.TeamMemberFragment;

import java.util.ArrayList;

public class SmsCallActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> fragmentTitleList;
    private TabLayout tabLayout;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_call);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_sms_call);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("SMS & Call");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        getFragmentList();
        getFragmentTitleList();

        viewPager = findViewById(R.id.sms_call_view_pager);
        tabLayout = findViewById(R.id.sms_call_tab_layout);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList,fragmentTitleList);
        viewPager.setOffscreenPageLimit(10);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void getFragmentTitleList() {

        fragmentTitleList = new ArrayList<>();
        fragmentTitleList.add("Customer Care");
        fragmentTitleList.add("Team Member");
        fragmentTitleList.add("Blood request");
        fragmentTitleList.add("Brand Ambassador");
    }

    private void getFragmentList() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new CustomerCareFragment());
        fragmentList.add(new TeamMemberFragment());
        fragmentList.add(new CommonBloodFragment());
        fragmentList.add(new BrandAmbassadorFragment());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void searchMember(View view) {
        startActivity(new Intent(this,SearchActivity.class));
    }
}
