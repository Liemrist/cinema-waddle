package project.epam.com.cinemawaddle.tabitems.movies.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import project.epam.com.cinemawaddle.details.DetailsActivity;
import project.epam.com.cinemawaddle.tabitems.movies.model.ITabModel;
import project.epam.com.cinemawaddle.tabitems.movies.view.ITabView;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.movies.Movie;
import project.epam.com.cinemawaddle.util.movies.MovieServiceResult;

public class MovieListPresenter implements ITabPresenter, ITabModel.OnFinishedListener<MovieServiceResult> {
    private ITabView view;
    private ITabModel model;


    public MovieListPresenter(ITabView view, ITabModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void loadMovies(int position, int page) {
        if (view != null) {
            view.showProgress();
        }
        model.fetchMovies(position, "en-US", page, Constants.REGION_US, this);
    }

    @Override
    public void loadFavorites(int page) {
        if (view != null) {
            view.showProgress();
        }
        model.fetchFavorites(this, page);
    }

    @Override
    public void loadWatchlist(int page) {
        if (view != null) {
            view.showProgress();
        }
        model.fetchWatchlist(this, page);
    }

    @Override
    public void onMovieClicked(Context context, Movie movie) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(Constants.EXTRA_MOVIE_ID, movie.getId());
        context.startActivity(intent);
    }

    @Override
    public void onSortByNameClick(List<Movie> items) {
        model.sortMoviesByName(items);
        view.showMoviesSet(new ArrayList<>(items));

    }

    @Override
    public void onSortByRatingClick(List<Movie> items) {
        model.sortMoviesByRating(items);
        view.showMoviesSet(new ArrayList<>(items));
    }

    @Override
    public void onSwipeRefreshInteractionBlank(int position) {
//        ITabModel.OnFinishedListener<MovieServiceResult> listener = new ITabModel.OnFinishedListener<MovieServiceResult>() {
//            @Override
//            public void onFetchingEnd(MovieServiceResult movies) {
//
//            }
//
//            @Override
//            public void onFailed(String errorMessage) {
//
//            }
//        };
        ITabModel.OnFinishedListener<MovieServiceResult> listener = new ITabModel.OnFinishedListener<MovieServiceResult>() {
            @Override
            public void onFetchingEnd(MovieServiceResult movies) {
                if (view != null && movies != null) {
                    ArrayList<Movie> items = movies.getResults();
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

        if (position == Constants.WATCHLIST) model.fetchWatchlist(listener, Constants.DEFAULT_PAGE);
        else if (position == Constants.FAVORITES)
            model.fetchFavorites(listener, Constants.DEFAULT_PAGE);
    }

    @Override
    public void onSwipeRefreshInteraction(int position) {
        model.fetchMovies(position, "en-US", Constants.DEFAULT_PAGE, Constants.REGION_US,
                new ITabModel.OnFinishedListener<MovieServiceResult>() {
                    @Override
                    public void onFetchingEnd(MovieServiceResult movieServiceResult) {
                        if (view != null && movieServiceResult != null) {
                            ArrayList<Movie> items = movieServiceResult.getResults();
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
    public void onFetchingEnd(MovieServiceResult movieServiceResult) {
        if (view != null && movieServiceResult != null) {
            ArrayList<Movie> items = movieServiceResult.getResults();
            if (items.size() != 0) {
                view.showMoviesAfterUpdate(items, movieServiceResult.getTotalPages(), movieServiceResult.getTotalResults());
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
