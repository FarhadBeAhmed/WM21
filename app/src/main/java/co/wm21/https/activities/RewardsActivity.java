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
import com.wm21ltd.wm21.fragments.RewardAchievementFragment;
import com.wm21ltd.wm21.fragments.RewardFundFragment;
import com.wm21ltd.wm21.fragments.RewardGalleryFragment;
import com.wm21ltd.wm21.fragments.RewardPolicyFragment;
import com.wm21ltd.wm21.fragments.RewardRegistrationFragment;

import java.util.ArrayList;

public class RewardsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> fragmentTitleList;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_reward);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Rewards");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        getFragmentList();
        getFragmentTitleList();

        viewPager = findViewById(R.id.reward_view_pager);
        tabLayout = findViewById(R.id.reward_tab_layout);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList,fragmentTitleList);
        viewPager.setOffscreenPageLimit(10);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void getFragmentTitleList() {

        fragmentTitleList = new ArrayList<>();
        fragmentTitleList.add("Reward Policy");
        fragmentTitleList.add("Reward Fund");
        fragmentTitleList.add("Achievement");
        fragmentTitleList.add("Reward Registration");
        fragmentTitleList.add("Reward Gallery");
    }

    private void getFragmentList() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new RewardPolicyFragment());
        fragmentList.add(new RewardFundFragment());
        fragmentList.add(new RewardAchievementFragment());
        fragmentList.add(new RewardRegistrationFragment());
        fragmentList.add(new RewardGalleryFragment());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
