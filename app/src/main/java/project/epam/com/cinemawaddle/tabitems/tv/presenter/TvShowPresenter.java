package project.epam.com.cinemawaddle.tabitems.tv.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import project.epam.com.cinemawaddle.details.DetailsActivity;
import project.epam.com.cinemawaddle.tabitems.tv.model.ITvShowModel;
import project.epam.com.cinemawaddle.tabitems.tv.view.ITvShowView;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.tvshows.TvListResult;
import project.epam.com.cinemawaddle.util.tvshows.TvServiceResult;


public class TvShowPresenter implements ITvShowsPresenter, ITvShowModel.OnFinishedListener<TvServiceResult>{
    private ITvShowView view;
    private ITvShowModel model;


    public TvShowPresenter(ITvShowView view, ITvShowModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void loadMovies(int position, int page) {
        if (view != null) {
            view.showProgress();
        }
        model.fetchTvShows(position, "en-US", page, this);
    }

    @Override
    public void loadFavorites() {
        view.showProgress();
        model.fetchFavorites(this);
    }

    @Override
    public void loadWatchlist() {
        view.showProgress();
        model.fetchWatchlist(this);
    }

    @Override
    public void onMovieClicked(Context context, TvListResult movie) {
        // fixme: this
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(Constants.EXTRA_MOVIE_ID, movie.getId());
        context.startActivity(intent);
    }

    @Override
    public void onSortByNameClick(List<TvListResult> items) {
        model.sortMoviesByName(items);
        view.showMoviesSet(new ArrayList<>(items));

    }

    @Override
    public void onSortByRatingClick(List<TvListResult> items) {
        model.sortMoviesByRating(items);
        view.showMoviesSet(new ArrayList<>(items));
    }

    @Override
    public void onSwipeRefreshInteraction(int position) {
        model.fetchTvShows(position, "en-US", Constants.DEFAULT_PAGE,
                new ITvShowModel.OnFinishedListener<TvServiceResult>() {
                    @Override
                    public void onFetchingEnd(TvServiceResult movies) {
                        if (view != null && movies != null) {
                            ArrayList<TvListResult> items = movies.getResults();
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
                });
    }

    @Override
    public void detach() {
        model = null;
        view = null;
    }

    @Override
    public void onFetchingEnd(TvServiceResult movies) {
        if (view != null && movies != null) {
            ArrayList<TvListResult> items = movies.getResults();
            if (items.size() != 0) {
                view.showMoviesAfterUpdate(items, movies.getTotalPages());
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
}
