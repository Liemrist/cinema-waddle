package project.epam.com.cinemawaddle.details.view;

import project.epam.com.cinemawaddle.util.base.BaseView;
import project.epam.com.cinemawaddle.util.service.movies.Details;

interface IMovieDetails extends BaseView {

    void showDetails(Details movie);
}
