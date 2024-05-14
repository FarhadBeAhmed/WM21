package co.wm21.https.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;

import co.wm21.https.R;
import co.wm21.https.adapters.ViewPagerAdapter;
import co.wm21.https.fragments.member.BrandAmbassadorFragment;
import co.wm21.https.fragments.member.CommonBloodFragment;
import co.wm21.https.fragments.member.CustomerCareFragment;
import co.wm21.https.fragments.member.TeamMemberFragment;

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
