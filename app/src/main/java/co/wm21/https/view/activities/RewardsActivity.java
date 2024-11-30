package co.wm21.https.view.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import co.wm21.https.R;
import co.wm21.https.view.adapters.ViewPagerAdapter2;
import co.wm21.https.view.fragments.member.RewardAchievementFragment;
import co.wm21.https.view.fragments.member.RewardMyAchievementFragment;
import co.wm21.https.view.fragments.member.RewardPolicyFragment;

public class RewardsActivity extends AppCompatActivity {

   // private ViewPager viewPager;
    private ViewPager2 viewPager2;
    //private ViewPagerAdapter viewPagerAdapter;
    ViewPagerAdapter2 viewPager2Adapter;
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
        viewPager2 = findViewById(R.id.reward_view_pager2);
       // viewPager = findViewById(R.id.reward_view_pager);
        tabLayout = findViewById(R.id.reward_tab_layout);
        //viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList,fragmentTitleList);
      //  viewPager.setOffscreenPageLimit(3);
      //  viewPager.setAdapter(viewPagerAdapter);
       // tabLayout.setupWithViewPager(viewPager);

        viewPager2Adapter = new ViewPagerAdapter2(this,fragmentList,fragmentTitleList);
        viewPager2.setAdapter(viewPager2Adapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(viewPager2Adapter.getPageTitle(position));

            }
        }).attach();
        //tabLayout.setupWithViewPager(viewPager2);
    }

    private void getFragmentTitleList() {

        fragmentTitleList = new ArrayList<>();
        fragmentTitleList.add("Reward Policy");
        fragmentTitleList.add("My Achieve");
        fragmentTitleList.add("Team Achieve");
    }

    private void getFragmentList() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new RewardPolicyFragment());
        fragmentList.add(new RewardMyAchievementFragment());
        fragmentList.add(new RewardAchievementFragment());
/*        fragmentList.add(new RewardFundFragment());
        fragmentList.add(new RewardAchievementFragment());
        fragmentList.add(new RewardRegistrationFragment());
        fragmentList.add(new RewardGalleryFragment());*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
