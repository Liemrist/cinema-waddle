package project.epam.com.cinemawaddle.details.presenter;


import project.epam.com.cinemawaddle.details.view.DetailsFragment;
import project.epam.com.cinemawaddle.details.model.IDetailsModel;
import project.epam.com.cinemawaddle.util.service.movies.Details;
import project.epam.com.cinemawaddle.util.service.movies.PostResponse;


public class DetailsPresenter implements IMovieDetailsPresenter, IDetailsModel.OnFinishedListener {

    private DetailsFragment view;
    private IDetailsModel model;


    public DetailsPresenter(DetailsFragment view, IDetailsModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void loadDetails(int id) {
        if (view != null) {
            view.showProgress();
        }
        model.fetchDetails(id, this);
    }

    @Override
    public void onMarkAsFavouriteClick(String mediaType, int mediaId, boolean isFavorite) {
        model.setFavourite(view.getContext(), mediaType, mediaId, isFavorite, this);
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
    public void onSetFavoriteFinished(PostResponse details) {

    }

    @Override
    public void onFailed(String errorMessage) {
        view.showMessage(errorMessage);
    }
}
