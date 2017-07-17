package project.epam.com.cinemawaddle.tabitems.movies.view;

import java.util.List;

import project.epam.com.cinemawaddle.tabitems.BaseTabView;
import project.epam.com.cinemawaddle.util.movies.Movie;
import project.epam.com.cinemawaddle.util.base.BaseView;
import project.epam.com.cinemawaddle.util.tvshows.TvListResult;

public interface ITabView extends BaseTabView<Movie> {
    // only for blank fragment, so check
    void showMoviesAfterUpdate(List<Movie> items, int totalPages, int totalResults);

}