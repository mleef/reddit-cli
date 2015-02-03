package com.marcleef.redditcli.Shell;

import java.util.ArrayList;
/**
 * Command object.
 * Created by marc_leef on 2/2/15.
 */


public class Command {
    private String primaryCommand;
    private ArrayList<String> args;
    private ArrayList<String> flags;

    /**
     * Constructor.
     * @param line String of user input to be processed.
     */
    public Command(String line) {
        String[] tokens = line.split(" ");
        primaryCommand = tokens[0];
        flags = new ArrayList<String>();
        args = new ArrayList<String>();
        if(tokens.length > 1)  {
            for(int i = 1; i < tokens.length; i++) {
                if(tokens[i].contains("-")) {
                    flags.add(tokens[i].replace("-",""));
                }
                else {
                    args.add(tokens[i]);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Command{" +
                "primaryCommand='" + primaryCommand + '\'' +
                ", args=" + args +
                ", flags=" + flags +
                '}';
    }

    /**
     * Returns primary command of the input.
     *
     * @return primaryCommand String
     */
    public String getPrimaryCommand() {
        return primaryCommand;
    }

    /**
     * Returns arguments of the input.
     *
     * @return List of args ArrayList<String>
     */
    public ArrayList<String> getArgs() {
        return args;
    }

    /**
     * Returns flags of the input.
     *
     * @return List of flags ArrayList<String>
     */
    public ArrayList<String> getFlags() {
        return flags;
    }
}
