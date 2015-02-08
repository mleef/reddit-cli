package com.marcleef.redditcli.Directory;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.exception.RetrievalFailedException;
import com.marcleef.redditcli.Action.Retrieval;

import java.util.ArrayList;

/**
 * Created by marc_leef on 2/2/15.
 */
public class SubmissionNode extends Node {
    public Submission submission;
    private boolean populated = false;
    public SubmissionNode(String n, Node p, Submission sub) {
        super(n, p);
        submission = sub;
    }

    @Override
    public String getName() {
        return String.format("[%s]--[%s]" + submission.getTitle(), submission.getIdentifier(), submission.getScore());
    }

    @Override
    public String getCdID() {
        return submission.getIdentifier();
    }

    @Override
    public boolean needsToBePopulated() {
        return !populated;
    }

    @Override
    public void populate(Retrieval retrieval) {
        try {
            ArrayList<CommentNode> results = retrieval.buildComments(this);
            for (CommentNode node : results) {
                this.addBranch(node);
            }
            populated = false;
        }
        catch (Exception e) {
            throw(new RetrievalFailedException("Could not fetch comments from " + submission.getIdentifier()));
        }
    }


}


