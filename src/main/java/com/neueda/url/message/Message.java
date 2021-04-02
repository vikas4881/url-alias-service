package com.neueda.url.message;

public interface Message {

    interface ErrorMessage {
        String EMPTY_ORIGINAL_URL = "The Original URL can not be EMPTY.";
        String INVALID_ORIGINAL_URL = "The Original URL is not a valid URL";
        String ERROR_CREATING_URL = "Issue found while creating the URL alias.";
        String CODE_NOT_FOUND = "No Data found for given Code";
    }

    interface Constants {
        String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    }
}
