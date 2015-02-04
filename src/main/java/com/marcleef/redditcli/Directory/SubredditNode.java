package com.marcleef.redditcli.Directory;

import com.github.jreddit.entity.Subreddit;
import com.marcleef.redditcli.Action.Retrieval;
import java.util.ArrayList;

/**
 * Node subclass that acts as Subreddit object container
 * Created by marc_leef on 2/2/15.
 */
public class SubredditNode extends Node {
    public Subreddit subreddit;
    private boolean populated = true;
    public SubredditNode(String n, Node p, Subreddit sub) {
        super(n, p);
        subreddit = sub;
    }

    @Override
    public boolean needsToBePopulated() {
        return populated;
    }

    @Override
    public void populate(Retrieval retrieval) {
        ArrayList<SubmissionNode> results = retrieval.buildSubmissions(this);
        for(SubmissionNode node : results) {
            this.addBranch(node);
        }
        populated = false;
    }
}
