package com.bhsconsultants.vonneumanns.dataimport;

public class GameDbException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public GameDbException(String message) {
        super(message);
    }
    
    public GameDbException(String message, Throwable cause) {
        super(message, cause);
    }
}
