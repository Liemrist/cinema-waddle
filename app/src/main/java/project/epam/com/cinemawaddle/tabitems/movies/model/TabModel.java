package project.epam.com.cinemawaddle.tabitems.movies.model;


import android.content.Context;

import project.epam.com.cinemawaddle.BuildConfig;
import project.epam.com.cinemawaddle.tabitems.base.BaseTabModelImpl;
import project.epam.com.cinemawaddle.util.service.ServiceResult;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.service.ServiceGenerator;
import project.epam.com.cinemawaddle.util.service.movies.IMoviesService;
import project.epam.com.cinemawaddle.util.service.movies.Movie;
import retrofit2.Call;


public class TabModel extends BaseTabModelImpl<Movie, ServiceResult<Movie>> implements ITabModel {

    private final IMoviesService client;


    public TabModel(Context context) {
        super(context);
        client = ServiceGenerator.createService(IMoviesService.class);
    }

    @Override
    public void fetchMovies(int type, String language, int page, String region,
                            OnFinishedListener<ServiceResult<Movie>> listener) {
        Call<ServiceResult<Movie>> call;

        switch (type) {
            case Constants.NOW_PLAYING:
                call = client.getNowPlaying(BuildConfig.TMDB_API_KEY, language, page, region);
                break;
            case Constants.TOP_RATED:
                call = client.getTopRated(BuildConfig.TMDB_API_KEY, language, page, region);
                break;
            case Constants.UPCOMING:
                call = client.getUpcoming(BuildConfig.TMDB_API_KEY, language, page, region);
                break;
            default:
                call = null;
        }

        if (call == null) listener.onFetchingEnd(null);
        else if (!call.isExecuted()) call.enqueue(getCallback(listener));
    }

    @Override
    public void fetchFavorites(OnFinishedListener<ServiceResult<Movie>> listener, String language,
                               String sortBy, int page) {

        String sessionId = preferences.getString(Constants.PREF_SESSION_ID, null);
        int accountId = preferences.getInt(Constants.PREF_ACCOUNT_ID, Constants.DEFAULT_ACCOUNT_ID);

        Call<ServiceResult<Movie>> call = client.getFavourites(accountId,
                BuildConfig.TMDB_API_KEY,
                language,
                sessionId,
                sortBy,
                page);

        if (!call.isExecuted()) call.enqueue(getCallback(listener));
    }

    @Override
    public void fetchWatchlist(OnFinishedListener<ServiceResult<Movie>> listener, String language,
                               String sortBy, int page) {
        String sessionId = preferences.getString(Constants.PREF_SESSION_ID, null);
        int accountId = preferences.getInt(Constants.PREF_ACCOUNT_ID, Constants.DEFAULT_ACCOUNT_ID);

        Call<ServiceResult<Movie>> call = client.getWatchlist(accountId,
                BuildConfig.TMDB_API_KEY,
                language,
                sessionId,
                sortBy,
                page);

        if (!call.isExecuted()) call.enqueue(getCallback(listener));
    }
}
