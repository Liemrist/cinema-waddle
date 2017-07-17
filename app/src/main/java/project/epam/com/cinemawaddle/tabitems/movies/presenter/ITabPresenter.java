package project.epam.com.cinemawaddle.tabitems.movies.presenter;

import android.content.Context;

import java.util.List;

import project.epam.com.cinemawaddle.tabitems.BaseTabPresenter;
import project.epam.com.cinemawaddle.util.base.BasePresenter;
import project.epam.com.cinemawaddle.util.movies.Movie;

public interface ITabPresenter extends BaseTabPresenter<Movie> {

    void loadFavorites(int page);

    void loadWatchlist(int page);

    void onMovieClicked(Context context, Movie movie);

    void onSwipeRefreshInteractionBlank(int position);

}