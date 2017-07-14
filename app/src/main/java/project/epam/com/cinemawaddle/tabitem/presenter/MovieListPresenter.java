package project.epam.com.cinemawaddle.tabitem.presenter;

import android.content.Intent;

import java.util.List;

import project.epam.com.cinemawaddle.tabitem.model.ITabModel;
import project.epam.com.cinemawaddle.tabitem.view.MovieListFragment;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.movies.Movie;

public class MovieListPresenter implements ITabPresenter, ITabModel.OnFinishedListener {
    private MovieListFragment view;
    private ITabModel model;


    public MovieListPresenter(MovieListFragment view, ITabModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void loadMovies() {
        if (view != null) {
            view.showProgress();
        }
        model.fetchMovies(this);
    }

    @Override
    public void onMovieClicked(Movie movie) {
        Intent intent = model.createIntent(view.getContext(), movie);
        view.startActivity(intent);
    }

    @Override
    public void detach() {
        view = null;
    }

    @Override
    public void onResponse(List<Movie> items) {
        if (view != null) {
            if (items != null) {
                view.showMovies(items);
                view.hideProgress();
            } else {
                view.showMessage(Constants.TEXT_FAVOURITES);
            }
        }
    }

    @Override
    public void onFailed(String errorMessage) {
        if (view != null) {
            view.showMessage(errorMessage);
        }
    }
}
