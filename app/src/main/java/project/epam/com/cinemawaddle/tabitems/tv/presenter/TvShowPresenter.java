package project.epam.com.cinemawaddle.tabitems.tv.presenter;


import android.util.Log;

import java.util.ArrayList;

import project.epam.com.cinemawaddle.tabitems.BaseTabPresenterImpl;
import project.epam.com.cinemawaddle.tabitems.BaseTabView;
import project.epam.com.cinemawaddle.tabitems.tv.model.ITvShowModel;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.service.ServiceResult;
import project.epam.com.cinemawaddle.util.service.tvshows.TvShow;


public class TvShowPresenter extends BaseTabPresenterImpl<TvShow, ITvShowModel> {

    public TvShowPresenter(BaseTabView<TvShow> view, ITvShowModel model) {
        super(view, model);
    }

    @Override
    public void loadMovies(int position, int page) {
        super.loadMovies(position, page);
        model.fetchTvShows(position, "en-US", page, this);
    }

    @Override
    public void onSwipeRefreshInteraction(int position) {
        model.fetchTvShows(position, "en-US", Constants.DEFAULT_PAGE,
                new ITvShowModel.OnFinishedListener<ServiceResult<TvShow>>() {
                    @Override
                    public void onFetchingEnd(ServiceResult<TvShow> movies) {
                        if (view != null && movies != null) {
                            ArrayList<TvShow> items = movies.getResults();
                            if (items.size() != 0) {
                                view.showMoviesSet(items);
                                view.hideProgress();
                            } else {
                                view.showMessage(Constants.TEXT_FAVOURITES);
                            }
                        } else {
                            Log.e("TAG", "FETCHING ERROR");
                        }

                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        if (view != null) {
                            view.showMessage(errorMessage);
                        }
                    }
                });
    }
}
