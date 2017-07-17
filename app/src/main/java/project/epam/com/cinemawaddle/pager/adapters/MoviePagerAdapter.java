package project.epam.com.cinemawaddle.pager.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import project.epam.com.cinemawaddle.tabitems.movies.view.MovieBlankFragment;
import project.epam.com.cinemawaddle.tabitems.movies.view.MovieListFragment;
import project.epam.com.cinemawaddle.util.Constants;

public class MoviePagerAdapter extends BasePagerAdapter {

    private MovieListFragment movieListFragment;
    private MovieBlankFragment movieBlankFragment1;
    private MovieBlankFragment movieBlankFragment2;


    public MoviePagerAdapter(FragmentManager manager, String[] tabTitles) {
        super(manager, tabTitles);

        movieListFragment = new MovieListFragment();
        movieBlankFragment1 = MovieBlankFragment.newInstance(Constants.WATCHLIST);
        movieBlankFragment2 = MovieBlankFragment.newInstance(Constants.FAVORITES);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case Constants.MOVIES:
                return movieListFragment;
            case Constants.WATCHLIST:
                return movieBlankFragment1;
            case Constants.FAVORITES:
                return movieBlankFragment2;
        }
        return null;
    }
}
