package project.epam.com.cinemawaddle.tabitem.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import okhttp3.ResponseBody;
import project.epam.com.cinemawaddle.BuildConfig;
import project.epam.com.cinemawaddle.details.DetailsActivity;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.ResponseHelper;
import project.epam.com.cinemawaddle.util.ServiceGenerator;
import project.epam.com.cinemawaddle.util.movies.IMoviesService;
import project.epam.com.cinemawaddle.util.movies.Movie;
import project.epam.com.cinemawaddle.util.movies.Movies;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabModel implements ITabModel {

    private Call<Movies> call;


    public TabModel(Context context, int type) {
        IMoviesService client = ServiceGenerator.createService(IMoviesService.class);
        switch (type) {
            case Constants.NOW_PLAYING:
                call = client.getNowPlaying(BuildConfig.TMDB_API_KEY, null,
                        Constants.DEFAULT_PAGE, Constants.REGION_US);
                break;
            case Constants.UPCOMING:
                call = client.getUpcoming(BuildConfig.TMDB_API_KEY, null,
                        Constants.DEFAULT_PAGE, Constants.REGION_US);
                break;
            case Constants.FAVOURITES:
                SharedPreferences preferences =
                        context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);

                String sessionId = preferences.getString(Constants.PREF_SESSION_ID, null);
                int accountId = preferences.getInt(Constants.PREF_ACCOUNT_ID, Constants.DEFAULT_ACCOUNT_ID);

                call = client.getFavourites(
                        accountId,
                        BuildConfig.TMDB_API_KEY,
                        sessionId,
                        null, null,
                        Constants.DEFAULT_PAGE);
                break;
        }
    }

    @Override
    public void fetchMovies(OnFinishedListener listener) {
        if (call == null) {
            listener.onResponse(null);
            return;
        }

        if (call.isExecuted()) return;

        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(@NonNull Call<Movies> call, @NonNull Response<Movies> response) {
                Movies body = response.body();
                ResponseBody errorBody = response.errorBody();

                if (body != null) {
                    listener.onResponse(body.getResults());
                } else if (errorBody != null) {
                    ResponseHelper.onResponseError(
                            errorBody,
                            Constants.ERROR_MESSAGE_FETCHING_MOVIES,
                            Constants.TAG_TAB_MODEL,
                            listener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movies> call, @NonNull Throwable t) {
                // the network call was a failure
                listener.onFailed(Constants.ERROR_MESSAGE_NETWORK);
            }
        });
    }

    @Override
    public Intent createIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(Constants.EXTRA_MOVIE_ID, movie.getId());
        return intent;
    }
}
