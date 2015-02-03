package com.marcleef.redditcli.Exceptions;

/**
 * Exception for improperly formatted commands
 * Created by marc_leef on 2/2/15.
 */
public class MalformedCommandException extends Exception {

    public MalformedCommandException() {
        super();
    }

    public MalformedCommandException(String exception) {
        super(exception);
    }
}
