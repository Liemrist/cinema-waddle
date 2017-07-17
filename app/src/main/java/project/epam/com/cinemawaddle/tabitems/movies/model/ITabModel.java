package project.epam.com.cinemawaddle.tabitems.movies.model;

import project.epam.com.cinemawaddle.tabitems.BaseTabModel;
import project.epam.com.cinemawaddle.util.movies.Movie;
import project.epam.com.cinemawaddle.util.movies.MovieServiceResult;


public interface ITabModel extends BaseTabModel<Movie> {

    void fetchMovies(int type, String language, int page, String region, OnFinishedListener<MovieServiceResult> listener);

    void fetchFavorites(OnFinishedListener<MovieServiceResult> listener, int page);

    void fetchWatchlist(OnFinishedListener<MovieServiceResult> listener, int page);
}
