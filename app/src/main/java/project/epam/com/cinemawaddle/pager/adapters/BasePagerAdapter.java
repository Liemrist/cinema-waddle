package project.epam.com.cinemawaddle.pager.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

abstract class BasePagerAdapter extends FragmentPagerAdapter {
    private String[] tabTitles;

    BasePagerAdapter(FragmentManager manager, String[] tabTitles) {
        super(manager);
        this.tabTitles = tabTitles;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
