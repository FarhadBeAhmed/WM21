package co.wm21.https.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import co.wm21.https.R;
import co.wm21.https.adapters.ViewPagerAdapter;
import co.wm21.https.fragments.member.RewardAchievementFragment;
import co.wm21.https.fragments.member.RewardFundFragment;
import co.wm21.https.fragments.member.RewardGalleryFragment;
import co.wm21.https.fragments.member.RewardPolicyFragment;
import co.wm21.https.fragments.member.RewardRegistrationFragment;

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
            getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar_shadow));
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
