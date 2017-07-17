package project.epam.com.cinemawaddle.tabitems;

import android.content.Context;

import java.util.List;

import project.epam.com.cinemawaddle.util.base.BasePresenter;

interface BaseTabPresenter<T> extends BasePresenter {

    void loadFavorites(int page);

    void loadWatchlist(int page);

    void onSwipeRefreshInteractionBlank(int position);


    void loadMovies(int position, int page);

    void onSortByNameClick(List<T> items);

    void onSortByRatingClick(List<T> items);

    void onSwipeRefreshInteraction(int position);

    void onMovieClicked(Context context, T movie);
}
