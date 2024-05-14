package co.wm21.https.activities;

import android.annotation.SuppressLint;
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
import co.wm21.https.fragments.member.CurrentMonthFragment;
import co.wm21.https.fragments.member.DetailsFragment;
import co.wm21.https.fragments.member.IncomeBalanceFragment;
import co.wm21.https.fragments.member.LastMonthFragment;
import co.wm21.https.fragments.member.LastStatusFragment;
import co.wm21.https.fragments.member.MyLoanFragment;
import co.wm21.https.fragments.member.ShopBalanceFragment;

public class EarningActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> fragmentTitleList;
    private TabLayout tabLayout;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earning);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar_shadow));
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_earning);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("My Account Status");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        getFragmentList();
        getFragmentTitleList();

        viewPager = findViewById(R.id.earning_view_pager);
        tabLayout = findViewById(R.id.earning_tab_layout);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList,fragmentTitleList);
        viewPager.setOffscreenPageLimit(10);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void getFragmentTitleList() {

        fragmentTitleList = new ArrayList<>();
        fragmentTitleList.add("E-Wallet Status");
        fragmentTitleList.add("E-Account Status");
        fragmentTitleList.add("Last Status");
        fragmentTitleList.add("Details");
        fragmentTitleList.add("Last Month");
        fragmentTitleList.add("Current Month");
        fragmentTitleList.add("My Loan");
    }

    private void getFragmentList() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new IncomeBalanceFragment());
        fragmentList.add(new ShopBalanceFragment());

        fragmentList.add(new LastStatusFragment());
        fragmentList.add(new DetailsFragment());
        fragmentList.add(new LastMonthFragment());
        fragmentList.add(new CurrentMonthFragment());
        fragmentList.add(new MyLoanFragment());
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
