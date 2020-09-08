package com.ihsinformatics.endtb.utils;

import android.content.Context;

import com.ihsinformatics.endtb.R;

/**
 * Created by Naveed Iqbal on 10/26/2017.
 * Email: h.naveediqbal@gmail.com
 */

public class ErrorMessages {

    public static enum ERROR_TYPE {
        WENT_WRONG,
        HOST_CONNECT,
        NOT_CONNECTED,
        UNAUTHORIZED,
        INVALID_REQUEST
    }

    public static String getErrorMessage(Context context, ERROR_TYPE errorType) {

        String errorMessage = null;

        switch (errorType) {
            case WENT_WRONG:
                errorMessage = context.getResources().getString(R.string.went_wrong);
                break;
            case NOT_CONNECTED:
                errorMessage = context.getResources().getString(R.string.not_connected);
                break;
            case UNAUTHORIZED:
                errorMessage = context.getResources().getString(R.string.login_un_successful);
                break;
            case HOST_CONNECT:
                errorMessage = context.getResources().getString(R.string.unable_to_connect);
                break;
            case INVALID_REQUEST:
                errorMessage = context.getResources().getString(R.string.invalid_request);
                break;
        }

        return errorMessage;
    }
}
