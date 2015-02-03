package com.marcleef.redditcli.Shell;

import com.github.jreddit.action.ProfileActions;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.*;
import com.github.jreddit.entity.User;
import com.github.jreddit.message.*;
import org.json.simple.JSONObject;
import java.io.Console;

import java.util.*;
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
                CONNECTED = true;
                RUNNING = true;

            } catch (Exception e) {
                System.out.println("Authentication Failed: Invalid Username/Password Combination");
            }
        }

        while(RUNNING) {
            String result;
            try {
                result = Parser.handle(new Command(console.readLine(USER_NAME + "> ")));
                System.out.println(result);
            }
            catch (Exception e) {
                System.out.println("Malformed Command: " + e.getMessage());
            }
        }



    }

}
