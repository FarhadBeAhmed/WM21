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
import com.wm21ltd.wm21.fragments.FranchiseAccountsFragment;
import com.wm21ltd.wm21.fragments.FranchiseAddressFragment;
import com.wm21ltd.wm21.fragments.FranchiseApplicationFragment;
import com.wm21ltd.wm21.fragments.MyFranchiseFragment;

import java.util.ArrayList;

public class FranchiseActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> fragmentTitleList;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchise);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_franchise);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Franchise");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        getFragmentList();
        getFragmentTitleList();

        viewPager = findViewById(R.id.franchise_view_pager);
        tabLayout = findViewById(R.id.franchise_tab_layout);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList,fragmentTitleList);
        viewPager.setOffscreenPageLimit(10);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void getFragmentTitleList() {

        fragmentTitleList = new ArrayList<>();
        fragmentTitleList.add("My Franchise");
        fragmentTitleList.add("Franchise Info");
        fragmentTitleList.add("Franchise Application");
        fragmentTitleList.add("Franchise Accounts");
    }

    private void getFragmentList() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new MyFranchiseFragment());
        fragmentList.add(new FranchiseAddressFragment());
        fragmentList.add(new FranchiseApplicationFragment());
        fragmentList.add(new FranchiseAccountsFragment());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
