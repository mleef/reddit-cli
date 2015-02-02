package com.marcleef.redditcli.Shell;

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
    private static String USER_NAME;
    private static String PASSWORD;
    private static boolean CONNECTED = false;
    public static void main(String[] args) {
        Console console = System.console();

        while(!CONNECTED) {
            USER_NAME = console.readLine("Username:");
            PASSWORD = console.readLine("Password:");
            RestClient restClient = new HttpRestClient();
            restClient.setUserAgent("bot/1.0 by name");
            // Connect the user
            User user = new User(restClient, USER_NAME, PASSWORD);

            try {
                user.connect();
                CONNECTED = true;

            } catch (Exception e) {
                System.out.println("Invalid Username/Password Combination");
            }
        }



    }

}
