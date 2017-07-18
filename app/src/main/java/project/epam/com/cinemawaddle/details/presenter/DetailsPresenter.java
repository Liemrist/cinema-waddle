package project.epam.com.cinemawaddle.details.presenter;


import project.epam.com.cinemawaddle.details.view.DetailsFragment;
import project.epam.com.cinemawaddle.details.model.IDetailsModel;
import project.epam.com.cinemawaddle.details.view.IMovieDetails;
import project.epam.com.cinemawaddle.util.service.BaseDetailsItem;
import project.epam.com.cinemawaddle.util.service.movies.Details;
import project.epam.com.cinemawaddle.util.service.movies.Movie;
import project.epam.com.cinemawaddle.util.service.movies.PostResponse;
import project.epam.com.cinemawaddle.util.service.tvshows.TvShow;
import project.epam.com.cinemawaddle.util.service.tvshows.TvShowDetails;


public class DetailsPresenter implements IMovieDetailsPresenter, IDetailsModel.OnFinishedListener {

    private IMovieDetails view;
    private final IDetailsModel model;


    public DetailsPresenter(IMovieDetails view, IDetailsModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void loadDetails(int id) {
        if (view != null) {
            view.showProgress();
        }
        model.fetchMovieDetails(id, this);
    }

    @Override
    public void loadTvShowDetails(int id) {
        if (view != null) {
            view.showProgress();
        }
        model.fetchTvShowDetails(id, this);
    }

    @Override
    public void onMarkAsFavouriteClick(String mediaType, int mediaId, boolean isFavorite) {
        model.setFavourite(mediaType, mediaId, isFavorite, this);
    }

    @Override
    public void detach() {
        view = null;
    }

    @Override
    public void onFetchDetailsFinished(Details details) {
        if (view != null) {
            view.showDetails(details);
            view.hideProgress();
        }
    }

    @Override
    public void onFetchDetailsFinished(TvShowDetails details) {
        if (view != null) {
            view.showDetails(details);
            view.hideProgress();
        }
    }

    @Override
    public void onSetFavoriteFinished(PostResponse details) {

    }

    @Override
    public void onFailed(String errorMessage) {
        view.showMessage(errorMessage);
    }

    @Override
    public void onFailure(String errorMessage) {
        view.showMessage(errorMessage);
    }
}
