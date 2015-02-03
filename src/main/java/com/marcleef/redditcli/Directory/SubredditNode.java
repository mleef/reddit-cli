package com.marcleef.redditcli.Directory;

import com.github.jreddit.entity.Subreddit;

/**
 * Node subclass that acts as Subreddit object container
 * Created by marc_leef on 2/2/15.
 */
public class SubredditNode extends Node {
    public Subreddit subreddit;
    public SubredditNode(String n, Node p, Subreddit sub) {
        super(n, p);
        subreddit = sub;
    }
}
