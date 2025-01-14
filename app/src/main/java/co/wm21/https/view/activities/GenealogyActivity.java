package co.wm21.https.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.ArrayList;

import co.wm21.https.R;
import co.wm21.https.view.adapters.ViewPagerAdapter;
import co.wm21.https.databinding.ActivityGenealogyBinding;
import co.wm21.https.view.fragments.GenealogyListFragment;
import co.wm21.https.view.fragments.member.TreeViewGnLogyFragment;

public class GenealogyActivity extends AppCompatActivity {

    //private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> fragmentTitleList;
    //private TabLayout tabLayout;
    ActivityGenealogyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGenealogyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary_red));
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_news_event);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Genealogy");
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
        fragmentTitleList.add("Tree View");
        fragmentTitleList.add("Genealogy List");
        //fragmentTitleList.add("DailyReport View");
       // fragmentTitleList.add("Team View");
    }

    private void getFragmentList() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new TreeViewGnLogyFragment());
        fragmentList.add(new GenealogyListFragment());
        // fragmentList.add(new DailyReportViewFragment());
      //  fragmentList.add(new TeamInfoFragment());
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}