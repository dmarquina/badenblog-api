package com.badenblog.common;

public class BadenblogException extends RuntimeException {

    private static final long serialVersionUID = 1192191899409394684L;

    private static final String ERROR_NAME = "Internal Server Error.";
    private static final String ERROR_MSG = "There was an internal error.";

    private String errorName;

    public BadenblogException() {
        super(ERROR_MSG);
        errorName = ERROR_NAME;
    }

    public BadenblogException(final Throwable cause) {
        super(ERROR_MSG, cause);
        errorName = ERROR_NAME;
    }

    public BadenblogException(final String message, final Throwable cause) {
        super(message, cause);
        errorName = ERROR_NAME;
    }

    public BadenblogException(final String errorMsg) {
        super(errorMsg);
        errorName = ERROR_NAME;
    }

    public BadenblogException(final String errorName, final String errorMsg) {
        super(errorMsg);
        this.errorName = errorName;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(final String errorName) {
        this.errorName = errorName;
    }

}