package project.epam.com.cinemawaddle.tabitems.movies.model;

import project.epam.com.cinemawaddle.tabitems.BaseTabModel;
import project.epam.com.cinemawaddle.util.service.ServiceResult;
import project.epam.com.cinemawaddle.util.service.movies.Movie;

public interface ITabModel extends BaseTabModel<Movie, ServiceResult<Movie>> {

    void fetchMovies(int type, String language, int page, String region,
                     OnFinishedListener<ServiceResult<Movie>> listener);
}
