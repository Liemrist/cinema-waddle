package project.epam.com.cinemawaddle.tabitems;

import java.util.List;

import project.epam.com.cinemawaddle.util.base.BasePresenter;

public interface BaseTabPresenter<T> extends BasePresenter {

    void loadMovies(int position, int page);

    void onSortByNameClick(List<T> items);

    void onSortByRatingClick(List<T> items);

    void onSwipeRefreshInteraction(int position);
}
