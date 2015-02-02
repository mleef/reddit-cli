package com.marcleef.redditcli.Shell;

import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.*;
import com.github.jreddit.entity.User;
import com.github.jreddit.message.*;
import org.json.simple.JSONObject;

import java.util.*;
/**
 * Created by marc_leef on 1/31/15.
 */
public class Driver {
    public static void main(String[] args) {
        // Testing API
        // Initialize REST Client
        RestClient restClient = new HttpRestClient();
        restClient.setUserAgent("bot/1.0 by name");

        // Connect the user
        User user = new User(restClient, "horseradisher", "jordan");

        try {
            user.connect();
            JSONObject jsonObject = (JSONObject) restClient.get(ApiEndpointUtils.USER_GET_SUBSCRIBED, user.getCookie()).getResponseObject();
            System.out.println(jsonObject.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
