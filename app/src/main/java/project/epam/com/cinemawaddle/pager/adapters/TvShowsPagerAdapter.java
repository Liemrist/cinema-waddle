package project.epam.com.cinemawaddle.pager.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import project.epam.com.cinemawaddle.tabitems.movies.view.MovieBlankFragment;
import project.epam.com.cinemawaddle.tabitems.tv.view.TvShowsFragment;
import project.epam.com.cinemawaddle.util.Constants;


public class TvShowsPagerAdapter extends BasePagerAdapter {

    private TvShowsFragment movieListFragment;
    private MovieBlankFragment blankFragment1;
    private MovieBlankFragment blankFragment2;


    public TvShowsPagerAdapter(FragmentManager manager, String[] tabTitles) {
        super(manager, tabTitles);

        movieListFragment = new TvShowsFragment();
        blankFragment1 = MovieBlankFragment.newInstance(Constants.WATCHLIST);
        blankFragment2 = MovieBlankFragment.newInstance(Constants.FAVORITES);
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
