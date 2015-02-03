package com.marcleef.redditcli.Shell;

import com.marcleef.redditcli.Exceptions.MalformedCommandException;

import java.util.ArrayList;

/**
 * Created by marc_leef on 2/2/15.
 */
public class Parser {

    private static final String HELP_INFO = "Help";

    public static String handle(Command c) throws MalformedCommandException {
        String primaryCommand = c.getPrimaryCommand();
        ArrayList<String> args = c.getArgs();
        ArrayList<String> flags = c.getFlags();
        int argLength = args.size();
        int flagLength = flags.size();

        if(primaryCommand.equals("help")) {

            if(argLength == 0 && flagLength == 0) {
                return HELP_INFO;
            }
            else {
                throw (new MalformedCommandException("'help' takes no arguments or flags"));
            }
        }

        throw (new MalformedCommandException("No matching commands"));
    }
}
