package project.epam.com.cinemawaddle.tabitems;

import java.util.List;

import project.epam.com.cinemawaddle.util.base.ModelBaseListener;

public interface BaseTabModel<T, S> {

    void fetchFavorites(OnFinishedListener<S> listener, String language, String sortBy, int page);

    void fetchWatchlist(OnFinishedListener<S> listener, String language, String sortBy, int page);

    void sortMoviesByName(List<T> items);

    void sortMoviesByRating(List<T> items);

    interface OnFinishedListener<S> extends ModelBaseListener {
        void onFetchingEnd(S movies);
    }
}
