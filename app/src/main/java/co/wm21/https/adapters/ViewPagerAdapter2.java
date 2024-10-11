package co.wm21.https.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter2 extends FragmentStateAdapter {

    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> fragmentTitleList;

    public ViewPagerAdapter2(FragmentActivity fm, ArrayList<Fragment> fragmentList, ArrayList<String> fragmentTitleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.fragmentTitleList = fragmentTitleList;
    }

    @Override
    public Fragment createFragment(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }


}

