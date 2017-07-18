package project.epam.com.cinemawaddle.tabitems.tv.presenter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

import project.epam.com.cinemawaddle.details.DetailsActivity;
import project.epam.com.cinemawaddle.tabitems.base.BaseTabPresenterImpl;
import project.epam.com.cinemawaddle.tabitems.base.BaseTabView;
import project.epam.com.cinemawaddle.tabitems.tv.model.ITvShowModel;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.service.ServiceResult;
import project.epam.com.cinemawaddle.util.service.tvshows.TvShow;


public class TvShowPresenter extends BaseTabPresenterImpl<TvShow, ITvShowModel> {

    public TvShowPresenter(BaseTabView<TvShow> view, ITvShowModel model) {
        super(view, model);
    }

    @Override
    public void loadObjects(int position, int page) {
        super.loadObjects(position, page);
        model.fetchTvShows(position, "en-US", page, this);
    }

    @Override
    public void loadMoviesOnScroll(int selectedItemPosition, int page) {
        if (view != null) {
            view.showProgressOnScroll();
        }
        model.fetchTvShows(selectedItemPosition, "en-US", page, this);
    }

    @Override
    public void onPrimeSwipeRefresh(int position) {
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
                        if (view != null) view.showTextViewMessage(errorMessage);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        if (view != null) view.showMessage(errorMessage);
                    }
                });
    }


    @Override
    public void onMovieClicked(Context context, TvShow movie) {
        // fixme: move this somewhere else.
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(Constants.EXTRA_MOVIE_ID, movie.getId());
        intent.putExtra(Constants.EXTRA_TYPE, Constants.TV_SHOWS);
        context.startActivity(intent);
    }
}
