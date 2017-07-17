package project.epam.com.cinemawaddle.pager.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import project.epam.com.cinemawaddle.tabitems.tv.view.TvShowsBlankFragment;
import project.epam.com.cinemawaddle.tabitems.tv.view.TvShowsFragment;
import project.epam.com.cinemawaddle.util.Constants;


public class TvShowsPagerAdapter extends BasePagerAdapter {

    private TvShowsFragment movieListFragment;
    private TvShowsBlankFragment blankFragment1;
    private TvShowsBlankFragment blankFragment2;


    public TvShowsPagerAdapter(FragmentManager manager, String[] tabTitles) {
        super(manager, tabTitles);

        movieListFragment = new TvShowsFragment();
        blankFragment1 = TvShowsBlankFragment.newInstance(Constants.WATCHLIST);
        blankFragment2 = TvShowsBlankFragment.newInstance(Constants.FAVORITES);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case Constants.MOVIES:
                return movieListFragment;
            case Constants.WATCHLIST:
                return blankFragment1;
            case Constants.FAVORITES:
                return blankFragment2;
        }
        return null;
    }
}
