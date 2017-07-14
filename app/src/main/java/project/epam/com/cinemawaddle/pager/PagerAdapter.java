package project.epam.com.cinemawaddle.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.tabitem.view.MovieListFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[];


    public PagerAdapter(Context context, FragmentManager manager) {
        super(manager);
        tabTitles = new String[]{context.getString(R.string.now_playing),
                context.getString(R.string.upcoming),
                context.getString(R.string.favourites)};
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        return MovieListFragment.newInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
