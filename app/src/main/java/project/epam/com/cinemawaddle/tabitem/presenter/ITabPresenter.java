package project.epam.com.cinemawaddle.tabitem.presenter;

import project.epam.com.cinemawaddle.util.base.BasePresenter;
import project.epam.com.cinemawaddle.util.movies.Movie;

public interface ITabPresenter extends BasePresenter {
    void loadMovies();

    void onMovieClicked(Movie movie);
}