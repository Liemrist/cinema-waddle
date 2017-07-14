package project.epam.com.cinemawaddle.util;

import android.util.Log;

import java.io.IOException;

import okhttp3.ResponseBody;
import project.epam.com.cinemawaddle.util.base.ModelBaseListener;

public class ResponseHelper {

    public static void onResponseError(ResponseBody errorBody, String defaultMessage, String logTag,
                                       ModelBaseListener listener) {
        String errorMessage;
        try {
            errorMessage = errorBody.string();
        } catch (IOException e) {
            errorMessage = defaultMessage;
            Log.e(logTag, Constants.ERROR_MESSAGE_PARSING + e);
        }
        listener.onFailed(errorMessage);
    }
}
