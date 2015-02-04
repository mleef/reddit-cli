package com.marcleef.redditcli.Shell;

import com.github.jreddit.action.ProfileActions;
import com.github.jreddit.entity.UserInfo;
import com.github.jreddit.utils.restclient.*;
import com.github.jreddit.entity.User;
import com.marcleef.redditcli.Directory.*;
import com.marcleef.redditcli.Action.Retrieval;
import java.io.Console;
/**
 * Main driver class of program.
 * Created by marc_leef on 1/31/15.
 */
public class Driver {
    private static String USER_NAME = "horseradisher";
    private static String PASSWORD = "jordan";
    private static boolean CONNECTED = false;
    private static boolean RUNNING = false;
    private static User user;
    private static ProfileActions profActions;
    private static RestClient restClient;
    private static Retrieval retriever;
    private static Node currentNode;
    private static Parser parser;
    public static void main(String[] args) {
        Console console = System.console();

        while(!CONNECTED) {
            //USER_NAME = console.readLine("Username:");
            //PASSWORD = console.readLine("Password:");
            restClient = new HttpRestClient();
            restClient.setUserAgent("bot/1.0 by name");

            // Connect the user
            user = new User(restClient, USER_NAME, PASSWORD);

            try {
                user.connect();
                profActions = new ProfileActions(restClient, user);
                retriever = new Retrieval(restClient, user, profActions);
                CONNECTED = true;
                RUNNING = true;

            } catch (Exception e) {
                System.out.println("Authentication Failed: Invalid Username/Password Combination");
            }
        }

        // Set up user's home directory
        currentNode = retriever.buildHomeDirectory();
        parser = new Parser(retriever, currentNode);

        // Primary loop
        while(RUNNING) {
            String result;
            try {
                result = parser.handle(new Command(console.readLine(USER_NAME + "> ")), currentNode);
                if(result != null) {
                    System.out.println(result);
                }
                currentNode = parser.updateCurrentNode();
            }
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }



    }


}
