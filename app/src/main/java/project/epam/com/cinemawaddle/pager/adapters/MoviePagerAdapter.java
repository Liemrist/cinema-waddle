package project.epam.com.cinemawaddle.pager.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import project.epam.com.cinemawaddle.tabitems.movies.view.MovieSubFragment;
import project.epam.com.cinemawaddle.tabitems.movies.view.MoviePrimeFragment;
import project.epam.com.cinemawaddle.util.Constants;

public class MoviePagerAdapter extends BasePagerAdapter {

    private final MoviePrimeFragment moviePrimeFragment;
    private final MovieSubFragment movieSubFragment1;
    private final MovieSubFragment movieSubFragment2;


    public MoviePagerAdapter(FragmentManager manager, String[] tabTitles) {
        super(manager, tabTitles);

        moviePrimeFragment = new MoviePrimeFragment();
        movieSubFragment1 = MovieSubFragment.newInstance(Constants.WATCHLIST);
        movieSubFragment2 = MovieSubFragment.newInstance(Constants.FAVORITES);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case Constants.MOVIES:
                return moviePrimeFragment;
            case Constants.WATCHLIST:
                return movieSubFragment1;
            case Constants.FAVORITES:
                return movieSubFragment2;
        }
        return null;
    }
}
