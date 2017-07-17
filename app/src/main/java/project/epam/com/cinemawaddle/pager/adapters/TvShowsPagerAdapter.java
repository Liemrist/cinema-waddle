package project.epam.com.cinemawaddle.pager.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import project.epam.com.cinemawaddle.tabitems.tv.view.TvShowSubFragment;
import project.epam.com.cinemawaddle.tabitems.tv.view.TvShowPrimeFragment;
import project.epam.com.cinemawaddle.util.Constants;


public class TvShowsPagerAdapter extends BasePagerAdapter {

    private final TvShowPrimeFragment movieListFragment;
    private final TvShowSubFragment blankFragment1;
    private final TvShowSubFragment blankFragment2;


    public TvShowsPagerAdapter(FragmentManager manager, String[] tabTitles) {
        super(manager, tabTitles);

        movieListFragment = new TvShowPrimeFragment();
        blankFragment1 = TvShowSubFragment.newInstance(Constants.WATCHLIST);
        blankFragment2 = TvShowSubFragment.newInstance(Constants.FAVORITES);
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
