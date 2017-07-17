package project.epam.com.cinemawaddle.tabitems.movies.presenter;


import android.util.Log;

import java.util.ArrayList;

import project.epam.com.cinemawaddle.tabitems.BaseTabPresenterImpl;
import project.epam.com.cinemawaddle.tabitems.BaseTabView;
import project.epam.com.cinemawaddle.tabitems.movies.model.ITabModel;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.service.ServiceResult;
import project.epam.com.cinemawaddle.util.service.movies.Movie;


public class MovieListPresenter extends BaseTabPresenterImpl<Movie, ITabModel> {

    public MovieListPresenter(BaseTabView<Movie> view, ITabModel model) {
        super(view, model);
    }

    @Override
    public void loadMovies(int position, int page) {
        super.loadMovies(position, page);
        model.fetchMovies(position, "en-US", page, Constants.REGION_US, this);
    }

    @Override
    public void onSwipeRefreshInteraction(int position) {
        model.fetchMovies(position, "en-US", Constants.DEFAULT_PAGE, Constants.REGION_US,
                new ITabModel.OnFinishedListener<ServiceResult<Movie>>() {
                    @Override
                    public void onFetchingEnd(ServiceResult<Movie> movieServiceResult) {
                        if (view != null && movieServiceResult != null) {
                            ArrayList<Movie> items = movieServiceResult.getResults();
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
