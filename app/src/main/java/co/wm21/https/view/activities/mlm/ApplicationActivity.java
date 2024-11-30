package co.wm21.https.view.activities.mlm;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import co.wm21.https.R;
import co.wm21.https.view.adapters.ViewPagerAdapter;
import co.wm21.https.databinding.ActivityApplicationBinding;
import co.wm21.https.view.fragments.GenealogyListFragment;
import co.wm21.https.view.fragments.member.BDPStatusFragment;
import co.wm21.https.view.fragments.member.EShopRefComFragment;
import co.wm21.https.view.fragments.member.EShopStatusFragment;
import co.wm21.https.view.fragments.member.TreeViewGnLogyFragment;

public class ApplicationActivity extends AppCompatActivity {

    private ViewPagerAdapter viewPagerAdapter;
    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> fragmentTitleList;
    //private TabLayout tabLayout;
    ActivityApplicationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityApplicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary_red));
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_news_event);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Application");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        getFragmentList();
        getFragmentTitleList();

        //viewPager = findViewById(R.id.news_event_view_pager);
        //tabLayout = findViewById(R.id.news_event_tab_layout);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList,fragmentTitleList);
        binding.newsEventViewPager.setOffscreenPageLimit(10);
        binding.newsEventViewPager.setAdapter(viewPagerAdapter);
        binding.newsEventTabLayout.setupWithViewPager(binding.newsEventViewPager);

    }

    private void getFragmentTitleList() {

        fragmentTitleList = new ArrayList<>();
        fragmentTitleList.add("E-Shop Status");
        fragmentTitleList.add("E-Shop Ref Com");
        fragmentTitleList.add("BDP Status");
        //fragmentTitleList.add("DailyReport View");
        // fragmentTitleList.add("Team View");
    }

    private void getFragmentList() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new EShopStatusFragment());
        fragmentList.add(new EShopRefComFragment());
        fragmentList.add(new BDPStatusFragment());
        // fragmentList.add(new DailyReportViewFragment());
        //  fragmentList.add(new TeamInfoFragment());
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}