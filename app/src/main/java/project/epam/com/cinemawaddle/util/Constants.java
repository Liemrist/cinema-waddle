package project.epam.com.cinemawaddle.util;

public class Constants {

    public static final String URL_BASE = "https://api.themoviedb.org/3/";
    public static final String URL_AUTH = "https://www.themoviedb.org/authenticate/";
    public static final String URL_GRAVATAR = "https://www.gravatar.com/avatar/";
    public static final String URL_DEFAULT_IMAGE = "?d=https://nothing.here.jpg";
    // fixme: Get this from getConfig request param, that you should store.
    public static final String SECURE_BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500";

    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_MOVIE_ID = 0;
    public static final int DEFAULT_ACCOUNT_ID = 0;

    // Should not do enum, but why?
    public static final int NOW_PLAYING = 0;
    public static final int TOP_RATED = 1;
    public static final int UPCOMING = 2;

    public static final int TV_SHOWS = 1;

    public static final int MOVIES = 0;
    public static final int WATCHLIST = 1;
    public static final int FAVORITES = 2;

    public static final String RU = "ru-RU";
    public static final String EN = "en-US";

    public static final String PREFERENCES_NAME = "WaddlePrefs";
    public static final String PREF_SESSION_ID = "PREF_SESSION_ID";
    public static final String PREF_ACCOUNT_ID = "ACCOUNT_ID";
    public static final String PREF_LOCALE = "PREF_LOCALE";

    public static final String ERROR_MESSAGE_TOKEN = "Error creating request token";
    public static final String ERROR_MESSAGE_NETWORK = "Network error";
    public static final String ERROR_MESSAGE_SESSION = "Error creating new session";
    public static final String ERROR_MESSAGE_FETCHING_MOVIES = "Error fetching objects";
    public static final String ERROR_MESSAGE_SESSION_DENIED = "Session denied";
    public static final String MESSAGE_ALREADY_CONNECTED = "Already connected to TMDb";

    public static final String ARGUMENT_MOVIE_ID = "ARGUMENT_MOVIE_ID";
    public static final String ARGUMENT_POSITION_INDEX = "ARGUMENT_POSITION_INDEX";
    public static final String ARGUMENT_TYPE = "ARGUMENT_TYPE";

    public static final String EXTRA_MOVIE_ID = "com.epam.project.cinemawaddle.EXTRA_MOVIE_ID";
    public static final String EXTRA_TYPE = "com.epam.project.cinemawaddle.EXTRA_TYPE";

    public static final int ACTIVITY_REQUEST_CODE = 1;

    public static final String REGION_US = "us";
    public final static String TEXT_FAVOURITES = "Here will be your favourites";

    public static final int NUMBER_OF_TABS = 3;

    public static final String BUNDLE_CURRENT_POSITION = "BUNDLE_CURRENT_POSITION";
}
