package co.wm21.https.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Collection;

public class TabAdapter extends FragmentPagerAdapter {

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<Fragment> fragmentList = new ArrayList<>();

    public boolean addFragment(Fragment fragment, String title) {
        return arrayList.add(title) & fragmentList.add(fragment);
    }

    public boolean addAllFragment(Collection<? extends Fragment> fragments, Collection<? extends String> titles) {
        return fragmentList.addAll(fragments) & arrayList.addAll(titles);
    }

    public TabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public TabAdapter(@NonNull FragmentManager fm, Collection<? extends Fragment> fragments, Collection<? extends String> titles) {
        super(fm);
        addAllFragment(fragments, titles);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return arrayList.get(position);
    }
}