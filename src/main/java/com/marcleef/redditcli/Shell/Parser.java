package com.marcleef.redditcli.Shell;

import com.marcleef.redditcli.Exceptions.MalformedCommandException;

/**
 * Created by marc_leef on 2/2/15.
 */
public class Parser {


    public static void handle(Command c) throws MalformedCommandException {
        System.out.println(c);
    }
}
