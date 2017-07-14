package project.epam.com.cinemawaddle.util;

import project.epam.com.cinemawaddle.main.model.MainModel;
import project.epam.com.cinemawaddle.tabitem.model.TabModel;

public class Constants {

    static final String ERROR_MESSAGE_PARSING = "There was an error parsing the errorMessage ";

    static final String URL_BASE = "https://api.themoviedb.org/3/";
    public static final String URL_AUTH = "https://www.themoviedb.org/authenticate/";

    public static final String TAG_MAIN_MODEL = MainModel.class.getName();
    public static final String TAG_TAB_MODEL = TabModel.class.getName();

    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_MOVIE_ID = 0;
    public static final int DEFAULT_ACCOUNT_ID = 0;

    // Should not do enum, but why?
    public static final int NOW_PLAYING = 0;
    public static final int UPCOMING = 1;
    public static final int FAVOURITES = 2;

    public static final String PREFERENCES_NAME = "WaddlePrefs";
    public static final String PREF_SESSION_ID = "PREF_SESSION_ID";
    public static final String PREF_ACCOUNT_ID = "ACCOUNT_ID";

    public static final String ERROR_MESSAGE_TOKEN = "Error creating requestToken";
    public static final String ERROR_MESSAGE_NETWORK = "The network call was a failure";
    public static final String ERROR_MESSAGE_SESSION = "Error creating new session";
    public static final String ERROR_MESSAGE_FETCHING_MOVIES = "Error fetching movies";
    public static final String ERROR_MESSAGE_FETCHING_ACCOUNT_DETAILS = "Error fetching account details";

    public static final String ARGUMENT_MOVIE_ID = "ARGUMENT_MOVIE_ID";
    public static final String ARGUMENT_POSITION_INDEX = "ARGUMENT_POSITION_INDEX";

    public static final String EXTRA_MOVIE_ID = "com.epam.project.cinemawaddle.EXTRA_MOVIE_ID";

    public static final int ACTIVITY_REQUEST_CODE = 1;

    public static final String MEDIA_TYPE_MOVIE = "movie";

    public static final String REGION_US = "us";

    public final static String TEXT_FAVOURITES = "Here will be your favourites";

}
