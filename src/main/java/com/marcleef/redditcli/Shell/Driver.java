package com.marcleef.redditcli.Shell;

import com.github.jreddit.action.ProfileActions;
import com.github.jreddit.entity.UserInfo;
import com.github.jreddit.utils.restclient.*;
import com.github.jreddit.entity.User;
import com.marcleef.redditcli.Directory.*;
import java.io.Console;
/**
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
    private static ContainerNode currentNode;
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
                parser = new Parser();
                CONNECTED = true;
                RUNNING = true;

            } catch (Exception e) {
                System.out.println("Authentication Failed: Invalid Username/Password Combination");
            }
        }

        // Set up user's home directory
        currentNode = buildHomeDirectory();

        // Primary loop
        while(RUNNING) {
            String result;
            try {
                result = parser.handle(new Command(console.readLine(USER_NAME + "> ")), currentNode);
                System.out.println(result);
            }
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }



    }

    /**
     * Returns new container node that has been populated with user info and subscribed subreddits.
     *
     * @return Home node ContainerNode
     */
    public static ContainerNode buildHomeDirectory() {
        profActions = new ProfileActions(restClient, user);
        ContainerNode home = new ContainerNode("home", null);
        ContainerNode subreddits = new ContainerNode("subreddits", home);
        try {
            UserInfo ui = profActions.getUserInformation();
            FileNode userInfo = new FileNode("user_info.txt", home, ui.toString());

            home.addBranch(userInfo);
            home.addBranch(subreddits);
        }
        catch (Exception e) {
            System.out.println("Couldn't Get User Information: " + e.getMessage());
        }
        return home;
    }

}
