package com.loanlender.app.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestException extends Exception {
    private static final long serialVersionUID = -470180507998010368L;

    Logger logger = LoggerFactory.getLogger(RequestException.class);

    public RequestException() {

        super();
    }

    public RequestException(final String message) {
        super(message);
    }
}
