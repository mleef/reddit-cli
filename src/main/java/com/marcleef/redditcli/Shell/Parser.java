package com.marcleef.redditcli.Shell;

import com.marcleef.redditcli.Exceptions.MalformedCommandException;
import com.marcleef.redditcli.Directory.Node;
import com.marcleef.redditcli.Exceptions.NodeNotFoundException;

import java.util.ArrayList;

/**
 * Primary logic processing takes place here.
 *
 * Created by marc_leef on 2/2/15.
 */
public final class Parser {

    private static final String HELP_INFO = "Help";
    private static Node currentNode;
    private static Node rootNode;

    public Parser(Node root) {
        rootNode = root;
        currentNode = root;
    }

    /**
     * Returns output based on user input.
     * @throws com.marcleef.redditcli.Exceptions.MalformedCommandException
     * @throws com.marcleef.redditcli.Exceptions.NodeNotFoundException
     * @return Output of command
     */
    public static String handle(Command c, Node curNode) throws MalformedCommandException, NodeNotFoundException {
        String primaryCommand = c.getPrimaryCommand();
        ArrayList<String> args = c.getArgs();
        ArrayList<String> flags = c.getFlags();
        int argLength = args.size();
        int flagLength = flags.size();


        // Basic help command
        // TODO: Should display available commands and other information
        if (primaryCommand.equals("help")) {

            if(argLength == 0 && flagLength == 0) {
                return HELP_INFO;
            }
            else {
                throw (new MalformedCommandException("'help' takes no arguments or flags"));
            }
        }

        // Quits program
        else if (primaryCommand.equals("quit")) {
            System.exit(0);
        }

        // Lists files in current directory
        // TODO: Should be able to process argument (target directory)
        else if (primaryCommand.equals("ls")) {
            if (argLength == 0 && flagLength == 0) {
                return curNode.getBranches();
            } else {
                throw (new MalformedCommandException("'ls' takes no arguments or flags"));
            }
        }

        // Returns current path
        else if (primaryCommand.equals("pwd")) {
            if(argLength == 0 && flagLength == 0) {
                return curNode.getPath();
            }
            else {
                throw (new MalformedCommandException("'pwd' takes no arguments or flags"));
            }
        }

        // Returns contents of a node
        else if (primaryCommand.equals("cat")) {
            if(argLength == 1 && flagLength == 0) {
                String catTarget = args.get(0);
                try {
                    return curNode.getBranchByName(catTarget).getData();
                }
                catch (Exception e) {
                    throw (new NodeNotFoundException(catTarget + " not found in current directory"));
                }

            }
            else {
                throw (new MalformedCommandException("'cat' takes one argument"));
            }
        }

        // Move directories
        else if (primaryCommand.equals("cd")) {
            if(argLength == 1 && flagLength == 0) {
                String cdTarget = args.get(0);
                if(cdTarget.equals("..") && curNode.getParent() != null) {
                    currentNode = curNode.getParent();
                    return null;
                }
                else {
                    try {
                        currentNode = curNode.getBranchByName(cdTarget);
                        return null;
                    } catch (Exception e) {
                        throw (new NodeNotFoundException(cdTarget + " not found in current directory"));
                    }
                }
            }

            else {
                if(argLength == 0 && flagLength == 0) {
                    currentNode = rootNode;
                    return null;
                }
                else {
                    throw (new MalformedCommandException("'cd' not used properly"));
                }
            }
        }

        throw (new MalformedCommandException("No matching commands - '" + primaryCommand + "'"));
    }

    /**
     * Returns updated reference to current node
     * @return Current node.
     */
    public static Node updateCurrentNode() {
        return currentNode;
    }
}
