package com.ns.app.exception;

public class UnauthorizedException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1843868571030349620L;

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(final String message) {
        super(message);
    }
}
