package project.epam.com.cinemawaddle.tabitem.view;

import java.util.List;

import project.epam.com.cinemawaddle.util.movies.Movie;
import project.epam.com.cinemawaddle.util.base.BaseView;

public interface ITabView extends BaseView {

    void showMovies(List<Movie> items);
}