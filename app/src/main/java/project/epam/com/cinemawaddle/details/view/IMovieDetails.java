package project.epam.com.cinemawaddle.details.view;

import project.epam.com.cinemawaddle.util.base.BaseView;
import project.epam.com.cinemawaddle.util.service.movies.Details;
import project.epam.com.cinemawaddle.util.service.tvshows.TvShowDetails;

public interface IMovieDetails<T> extends BaseView {

    void showDetails(T movie);
}
