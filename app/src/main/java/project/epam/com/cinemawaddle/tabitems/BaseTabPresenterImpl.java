package project.epam.com.cinemawaddle.tabitems;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import project.epam.com.cinemawaddle.details.DetailsActivity;
import project.epam.com.cinemawaddle.util.service.BaseServiceArrayItem;
import project.epam.com.cinemawaddle.util.service.ServiceResult;
import project.epam.com.cinemawaddle.util.Constants;


public abstract class BaseTabPresenterImpl<T extends BaseServiceArrayItem,
        S extends BaseTabModel<T, ServiceResult<T>>> implements
        BaseTabPresenter<T>, BaseTabModel.OnFinishedListener<ServiceResult<T>> {

    protected BaseTabView<T> view;
    protected S model;


    @Override
    public void loadMovies(int position, int page) {
        if (view != null) {
            view.showProgress();
        }
    }

    public BaseTabPresenterImpl(BaseTabView<T> view, S model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void loadFavorites(int page) {
        if (view != null) {
            view.showProgress();
        }
        model.fetchFavorites(this, null, null, page);
    }

    @Override
    public void loadWatchlist(int page) {
        view.showProgress();
        model.fetchWatchlist(this, null, null, page);
    }

    @Override
    public void onMovieClicked(Context context, T movie) {
        // fixme: this
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(Constants.EXTRA_MOVIE_ID, movie.getId());
        context.startActivity(intent);
    }

    @Override
    public void onSortByNameClick(List<T> items) {
        model.sortMoviesByName(items);
        view.showMoviesSet(new ArrayList<>(items));

    }

    @Override
    public void onSortByRatingClick(List<T> items) {
        model.sortMoviesByRating(items);
        view.showMoviesSet(new ArrayList<>(items));
    }

    @Override
    public void detach() {
        model = null;
        view = null;
    }


    @Override
    public void onFetchingEnd(ServiceResult<T> movieServiceResult) {
        if (view != null && movieServiceResult != null) {
            ArrayList<T> items = movieServiceResult.getResults();
            if (items.size() != 0) {
                view.showMoviesAfterUpdate(items, movieServiceResult.getTotalPages(),
                        movieServiceResult.getTotalResults());
                view.hideProgress();
            } else {
                view.showMessage(Constants.TEXT_FAVOURITES);
            }
        } else {
            Log.e("TAG", "FETCHING ERROR");
        }
    }

    @Override
    public void onFailed(String errorMessage) {
        if (view != null) {
            view.showMessage(errorMessage);
        }
    }

    @Override
    public void onSwipeRefreshInteractionBlank(int position) {
        S.OnFinishedListener<ServiceResult<T>> listener =
                new S.OnFinishedListener<ServiceResult<T>>() {
                    @Override
                    public void onFetchingEnd(ServiceResult<T> movies) {
                        if (view != null && movies != null) {
                            ArrayList<T> items = movies.getResults();
                            if (items.size() != 0) {
                                view.showMoviesSet(items);
                                view.hideProgress();
                            } else {
                                view.showMessage(Constants.TEXT_FAVOURITES);
                            }
                        } else {
                            Log.e("TAG", "FETCHING ERROR");
                        }

                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        if (view != null) {
                            view.showMessage(errorMessage);
                        }
                    }
                };

        if (position == Constants.WATCHLIST)
            model.fetchWatchlist(listener, null, null, Constants.DEFAULT_PAGE);
        else if (position == Constants.FAVORITES)
            model.fetchFavorites(listener, null, null, Constants.DEFAULT_PAGE);
    }
}
