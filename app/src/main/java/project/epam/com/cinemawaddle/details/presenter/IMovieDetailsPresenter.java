package project.epam.com.cinemawaddle.details.presenter;

import project.epam.com.cinemawaddle.util.base.BasePresenter;

interface IMovieDetailsPresenter extends BasePresenter {
    void loadDetails(int id);

    void onMarkAsFavouriteClick(String mediaType, int mediaId, boolean isFavorite);
}
