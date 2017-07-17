package project.epam.com.cinemawaddle.tabitems;

import java.util.List;

import project.epam.com.cinemawaddle.util.base.ModelBaseListener;

public interface BaseTabModel<T> {

    void sortMoviesByName(List<T> items);

    void sortMoviesByRating(List<T> items);

    interface OnFinishedListener<T> extends ModelBaseListener {
        void onFetchingEnd(T movies);
    }
}
