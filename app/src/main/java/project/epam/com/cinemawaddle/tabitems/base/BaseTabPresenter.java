package project.epam.com.cinemawaddle.tabitems.base;

import android.content.Context;

import java.util.List;

import project.epam.com.cinemawaddle.util.base.BasePresenter;

interface BaseTabPresenter<T> extends BasePresenter {

    void loadObjects(int position, int page);

    void loadMoviesOnScroll(int selectedItemPosition, int page);

    void loadFavorites(int page);

    void loadWatchlist(int page);


    void onPrimeSwipeRefresh(int position);

    void onSubSwipeRefresh(int position);


    void onSortByNameClick(List<T> items);

    void onSortByRatingClick(List<T> items);


    void onMovieClicked(Context context, T movie);
}
