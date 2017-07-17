package project.epam.com.cinemawaddle.tabitems;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import project.epam.com.cinemawaddle.util.service.BaseServiceArrayItem;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.ResponseHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class BaseTabModelImpl<T extends BaseServiceArrayItem, S/* extends MovieServiceResult*/>
        implements BaseTabModel<T, S> {

    protected SharedPreferences preferences;


    public BaseTabModelImpl(Context context) {
        preferences = context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void sortMoviesByRating(List<T> items) {
        Collections.sort(items, (o1, o2) -> {
            if (o2.getVoteAverage() < o1.getVoteAverage()) return -1;
            else if (o2.getVoteAverage() > o1.getVoteAverage()) return 1;
            else return 0;
        });
    }

    @Override
    public void sortMoviesByName(List<T> items) {
        Collections.sort(items, (o1, o2) -> o1.getName().compareTo(o2.getName()));
    }

    @NonNull
    protected Callback<S> getCallback(final OnFinishedListener<S> listener) {
        return new Callback<S>() {
            @Override
            public void onResponse(@NonNull Call<S> call,
                                   @NonNull Response<S> response) {
                S movieServiceResult = response.body();
                ResponseBody errorBody = response.errorBody();

                if (movieServiceResult != null) {
                    listener.onFetchingEnd(movieServiceResult);
                } else if (errorBody != null) {
                    ResponseHelper.onResponseError(
                            errorBody,
                            Constants.ERROR_MESSAGE_FETCHING_MOVIES,
                            Constants.TAG_TAB_MODEL,
                            listener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<S> call, @NonNull Throwable t) {
                // the network call was a failure
                listener.onFailed(Constants.ERROR_MESSAGE_NETWORK);
            }
        };
    }
}
